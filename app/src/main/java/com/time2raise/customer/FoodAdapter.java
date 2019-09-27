package com.time2raise.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.time2raise.customer.data.model.ResFood;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<ResFood> {
    private LayoutInflater inflater;
    private int layout;
    private List<ResFood> resFoods;
    FoodAdapter(Context context, int resource, List<ResFood> resFoods) {
        super(context, resource, resFoods);
        this.resFoods = resFoods;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = inflater.inflate(this.layout, parent, false);

        TextView foodName = (TextView) view.findViewById(R.id.food_name);

        ResFood resFood = resFoods.get(position);

        System.out.println(resFood.getFoodName());

        foodName.setText(resFood.getFoodName());


        return view;
    }
}
