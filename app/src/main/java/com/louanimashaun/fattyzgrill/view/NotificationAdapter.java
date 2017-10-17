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
import com.louanimashaun.fattyzgrill.util.StringUtil;
import com.louanimashaun.fattyzgrill.util.Util;

import java.util.Date;
import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 10/09/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> mNotifications;

    private Listeners.NotificationOnClickListener mClickListener;


    public NotificationAdapter(List<Notification> notifications){
        setList(notifications);
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
        holder.title_tv.setText(StringUtil.convertToCamelCase(notification.getTitle()));
        holder.message_tv.setText(notification.getMessage());
        Date date = notification.getCreatedAt();

        if(date == null) date = new Date();

        holder.date_tv.setText(date.toString());


        int backgroundRID = (notification.isHasBeenOpened() ?  R.drawable.bg_item_meal : R.drawable.bg_notification_item_unopened);
        Drawable background = Util.getApp().getResources().getDrawable(backgroundRID);
        holder.background_ll.setBackground(background);

    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public void replaceData(List<Notification> notifications){
        setList(notifications);
        notifyDataSetChanged();
    }

    public void addData(Notification notification){
        checkNotNull(notification);
        mNotifications.add(notification);
        notifyDataSetChanged();
    }

    private void setList(List<Notification> notifications){
        mNotifications = checkNotNull(notifications);
    }



    public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title_tv, message_tv, date_tv;
        public LinearLayout background_ll;


        public NotificationViewHolder(View itemView) {
            super(itemView);

            title_tv = (TextView) itemView.findViewById(R.id.notification_title_tv);
            message_tv = (TextView) itemView.findViewById(R.id.notifcation_message_tv);
            date_tv = (TextView) itemView.findViewById(R.id.time_difference_tv);
            background_ll = (LinearLayout) itemView.findViewById(R.id.notification_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null){
                int position = getAdapterPosition();
                String id = mNotifications.get(position).getId();
                mClickListener.onClick(id);
            }
        }
    }

    public void setNotificationClickListener(Listeners.NotificationOnClickListener listener){
        mClickListener = checkNotNull(listener);
    }

}
