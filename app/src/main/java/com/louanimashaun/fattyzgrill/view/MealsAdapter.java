package com.louanimashaun.fattyzgrill.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.model.Meal;

import org.w3c.dom.Text;

import java.util.List;

import static com.louanimashaun.fattyzgrill.util.PreconditonUtil.checkNotNull;

/**
 * Created by louanimashaun on 14/06/2017.
 */

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {

    private List<Meal> mMeals;

    public MealsAdapter(List<Meal> meals){
        setList(meals);
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        Meal meal = mMeals.get(position);
        holder.title_tv.setText(meal.getTitle());
        holder.price_tv.setText(String.valueOf(meal.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public void replaceData(List<Meal> meals) {
        setList(meals);
        notifyDataSetChanged();
    }

    private void setList(List<Meal> meals) {
        mMeals = checkNotNull(meals);
    }




    public class MealViewHolder extends RecyclerView.ViewHolder{

        //TODO use butterknife
        public TextView title_tv, price_tv;

        public MealViewHolder(View itemView) {
            super(itemView);

            title_tv = (TextView) itemView.findViewById(R.id.title_tv);
            price_tv = (TextView) itemView.findViewById(R.id.price_tv);
        }
    }
}
