package com.louanimashaun.fattyzgrill.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;
import com.louanimashaun.fattyzgrill.util.StringUtil;

import org.w3c.dom.Text;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 24/06/2017.
 */

public class CheckoutAdapter extends ArrayAdapter<Meal> {

    private Context mContext;
    private List<Meal> mMeals;
    private List<Integer> mQuantities;
    private Listeners.CheckoutItemClickListener mIncrButtonListener;
    private Listeners.CheckoutItemClickListener mDecButoonListener;

    public CheckoutAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Meal> meals) {
        super(context, resource, meals);
        mContext = context;
        mMeals = meals;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_checkout_meal, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.title_tv);
        TextView priceTextView = (TextView) rowView.findViewById(R.id.price_tv);
        ImageView incrButton = (ImageView) rowView.findViewById(R.id.quantity_plus_ib);
        ImageView decButton = (ImageView) rowView.findViewById(R.id.quantity_minus_ib);
        TextView quantityTextView = (TextView) rowView.findViewById(R.id.quantity_tv);


        incrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIncrButtonListener.onClick(mMeals.get(position).getId(),true);
            }
        });


        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDecButoonListener.onClick(mMeals.get(position).getId(), false);
            }
        });

        Meal meal = mMeals.get(position);
        /*String price = "£ ";

        String price2 = String.valueOf(meal.getPrice());

        String[] split = price2.split("\\.");
        if(split[1].length() == 1) {
            price2 += "0";
        }

        price += price2;
        */

        int quantity = mQuantities.get(position);


        String price = StringUtil.formatPrice(meal.getPrice() * quantity);


        titleTextView.setText(meal.getTitle());
        priceTextView.setText(price);

        //TODO index out of bounds
        if(mQuantities != null && mQuantities.size() != 0) quantityTextView.setText("x "+ mQuantities.get(position));

//        FloatingActionButton addBtn = (FloatingActionButton)rowView.findViewById(R.id.add_checkout);
//        addBtn.setVisibility(View.GONE);

        return rowView;
    }

    public void refreshData(List<Meal> meals, List<Integer> quantities){
        setMeals(meals);
        mQuantities = checkNotNull(quantities);
        notifyDataSetChanged();

    }

    private void setMeals(List<Meal> meals){
        mMeals = checkNotNull(meals);
    }

    @Override
    public int getCount() {
       return mMeals.size();
    }


    public void setIncrButtonListener(Listeners.CheckoutItemClickListener listener){
       mIncrButtonListener = checkNotNull(listener);
    }

    public void setDecButtonListener(Listeners.CheckoutItemClickListener listener){
        mDecButoonListener = checkNotNull(listener);
    }

}
