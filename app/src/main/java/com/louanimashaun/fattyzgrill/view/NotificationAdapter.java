package com.louanimashaun.fattyzgrill.view;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Notification;
import com.louanimashaun.fattyzgrill.model.Order;
import com.louanimashaun.fattyzgrill.util.StringUtil;
import com.louanimashaun.fattyzgrill.util.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> mNotifications;
    private List<Order> mOrders;

    private Listeners.NotificationOnClickListener mClickListener;


    public NotificationAdapter(List<Notification> notifications){
        setNotificationList(notifications);
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.notification_item, parent, false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification =  mNotifications.get(position);
        Order order = mOrders.get(position);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dfTime = new SimpleDateFormat("HH:mm");

        holder.title_tv.setText(StringUtil.convertToCamelCase(notification.getMessage()));
        Date date = notification.getCreatedAt();

        if(notification.getType().equals("order_accepted")){
            holder.date_tv.setText(df.format(order.getAcceptedAt()));
        }else if(notification.getType().equals("new_order")){
            holder.date_tv.setText(df.format(order.getCreatedAt()));

        }


        String orderStatus = (order.isOrderAccepted()? "Order Status: Order Accepted": "Order Status: Order Pending");
        holder.order_status_tv.setText(orderStatus);

        int backgroundRID = (notification.isHasBeenOpened() ?  R.drawable.bg_item_meal : R.drawable.bg_notification_item_unopened);
        Drawable background = Util.getApp().getResources().getDrawable(backgroundRID);
        holder.background_ll.setBackground(background);


        if(notification.getType().equals("order_accepted")){
            String text = "Your order will be ready for Collection at " + dfTime.format(order.getCollectionAt());
            holder.collection_time_tv.setText(text);
        }else if(notification.getType().equals("new_order")){
            holder.collection_time_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public void replaceNotificationData(List<Notification> notifications){
        setNotificationList(notifications);
        notifyDataSetChanged();
    }

    public void replaceOrdersData(List<Order> orders){
        setOrdersList(orders);
        notifyDataSetChanged();
    }


    public void addData(Notification notification){
        checkNotNull(notification);
        mNotifications.add(notification);
        notifyDataSetChanged();
    }

    private void setNotificationList(List<Notification> notifications){
        mNotifications = checkNotNull(notifications);
    }

    private void setOrdersList(List<Order> orders){
        mOrders = checkNotNull(orders);
    }



    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title_tv, date_tv, order_status_tv, collection_time_tv;
        public LinearLayout background_ll;


        public NotificationViewHolder(View itemView) {
            super(itemView);

            title_tv = (TextView) itemView.findViewById(R.id.notification_title_tv);
            date_tv = (TextView) itemView.findViewById(R.id.time_difference_tv);
            background_ll = (LinearLayout) itemView.findViewById(R.id.notification_item);
            order_status_tv = (TextView) itemView.findViewById(R.id.order_status_tv);
            collection_time_tv = (TextView) itemView.findViewById(R.id.collection_time_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null){
                int position = getLayoutPosition();
                String id = mNotifications.get(position).getId();
                mClickListener.onClick(id);
            }
        }
    }

    public void setNotificationClickListener(Listeners.NotificationOnClickListener listener){
        mClickListener = checkNotNull(listener);
    }


}
