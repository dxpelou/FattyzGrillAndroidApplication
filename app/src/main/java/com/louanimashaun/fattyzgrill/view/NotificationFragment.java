package com.louanimashaun.fattyzgrill.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.contract.NotificationContract;
import com.louanimashaun.fattyzgrill.di.ActivityScoped;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 10/09/2017.
 */

@ActivityScoped
public class NotificationFragment extends DaggerFragment implements NotificationContract.View{

    NotificationAdapter mNotificationAdapter = new NotificationAdapter(new ArrayList<Notification>());



    @Inject
    NotificationContract.Presenter mNotificationPresenter;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        //        Bundle args = new Bundle();
        //        fragment.setArguments(args);

        return fragment;
    }

    @Inject
    public NotificationFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_frag,container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notifications_rv);

        LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mNotificationAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotificationPresenter.takeView(this);
        mNotificationPresenter.start();

        mNotificationAdapter.setNotificationClickListener(new Listeners.NotificationOnClickListener() {
            @Override
            public void onClick(String notificationId) {

                mNotificationPresenter.loadNotifcations(false); //reloads view
                mNotificationPresenter.loadOrderList(notificationId);
            }
        });
    }



    @Override
    public void showNotifications(List<Notification> notifications, List<Order> orders) {
        checkNotNull(notifications);
        checkNotNull(orders);
        mNotificationAdapter.replaceNotificationData(notifications);
        mNotificationAdapter.replaceOrdersData(orders);
    }

    @Override
    public void showNewNotification(Notification notification) {
        checkNotNull(notification);
        mNotificationAdapter.addData(notification);
    }

    @Override
    public void showOrderList(List<Meal> meals, Order order) {
        final OrdersDialogFragment ordersDialogFragment = OrdersDialogFragment.getNewInstance(meals, order);
        ordersDialogFragment.setAcceptClickListener(new Listeners.AcceptOrderClickListener() {
            @Override
            public void onClick(final String orderId) {
                final ETADialog etaDialog = new ETADialog();
                etaDialog.setCompletionCallback(new ETADialog.ETACompletionCallback() {
                    @Override
                    public void onComplete(Calendar calendar) {
                        mNotificationPresenter.acceptOrder(orderId, calendar);
                        Toast.makeText(Util.getApp(), "Confirmation sent to Customer", Toast.LENGTH_SHORT).show();
                    }
                });
                etaDialog.show(getFragmentManager(), "ETA");
            }
        });
        ordersDialogFragment.show(getFragmentManager(), "Order");

    }

    @Override
    public void showNoNotifcations() {

    }

    public void setNotificatioClickListener(Listeners.NotificationOnClickListener listener){
        checkNotNull(listener);
        mNotificationAdapter.setNotificationClickListener(listener);
    }
}
