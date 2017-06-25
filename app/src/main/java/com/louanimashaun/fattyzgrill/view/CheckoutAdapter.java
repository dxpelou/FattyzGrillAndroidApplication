package com.louanimashaun.fattyzgrill.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 24/06/2017.
 */

public class CheckoutAdapter extends ArrayAdapter<Meal> {

    private Context mContext;
    private List<Meal> meals;

    public CheckoutAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Meal> objects) {
        super(context, resource, objects);
        mContext = context;
        meals = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_meal, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.title_tv);
        TextView priceTextView = (TextView) rowView.findViewById(R.id.price_tv);

        Meal meal = meals.get(position);
        String price = "£ " + String.valueOf(meal.getPrice());

        titleTextView.setText(meal.getTitle());
        priceTextView.setText(price);

        return rowView;
    }

    public void refreshData(List<Meal> meals){
        setMeals(meals);
        notifyDataSetChanged();

    }

    private void setMeals(List<Meal> meals){
        meals = checkNotNull(meals);
    }
}
