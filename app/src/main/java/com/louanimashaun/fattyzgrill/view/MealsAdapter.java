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
    private static final int MEAL_ITEM = 0;
    private static final int CATEGORY_MEAL_ITEM = 1;

    private static int currentViewType;
    private MealOnClickListener mClickListener;


    public MealsAdapter(List<Meal> meals){
        setList(meals);
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == MEAL_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_with_header, parent, false);
        }
        return new MealViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        Meal meal = mMeals.get(position);
        holder.title_tv.setText(meal.getTitle());
        holder.price_tv.setText("£ " + String.valueOf(meal.getPrice()));

        if(currentViewType == CATEGORY_MEAL_ITEM){
            holder.category_tv.setText(meal.getCategory());
        }
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public void replaceData(List<Meal> meals) {
        setList(meals);
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            currentViewType = CATEGORY_MEAL_ITEM;
            return CATEGORY_MEAL_ITEM;
        }

        String previousCategory = mMeals.get(position - 1).getCategory();
        String currentCategory = mMeals.get(position).getCategory();

        if(previousCategory.equals(currentCategory)){
            currentViewType = MEAL_ITEM;
            return currentViewType;
        }else {
            currentViewType = CATEGORY_MEAL_ITEM;
            return currentViewType;
        }
    }

    private void setList(List<Meal> meals) {
        mMeals = checkNotNull(meals);
    }


    public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //TODO use butterknife
        public TextView title_tv, price_tv, category_tv;

        public MealViewHolder(View itemView, int viewType) {
            super(itemView);

            title_tv = (TextView) itemView.findViewById(R.id.title_tv);
            price_tv = (TextView) itemView.findViewById(R.id.price_tv);

            if(viewType == CATEGORY_MEAL_ITEM ) {
                category_tv = (TextView) itemView.findViewById(R.id.category_tv);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            String mealID = mMeals.get(index).getId();
            mClickListener.onClick(mealID);
        }
    }

    public void setItemClickListener(MealOnClickListener listener){
        mClickListener = checkNotNull(listener);
    }
}
