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
    private ArrayList<ResFood> resFoods;
    FoodAdapter(Context context, int resource, ArrayList<ResFood> resFoods) {
        super(context, resource, resFoods);
        System.out.println("Start work!");
        this.resFoods = resFoods;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        ResFood resFood = resFoods.get(position);

        System.out.println("position!!!" + position);
        System.out.println(resFood.getFoodName());

        viewHolder.foodName.setText(resFood.getFoodName());

        //foodName.setText(resFood.getFoodName());



        return convertView;
    }

    private class ViewHolder {
        final TextView foodName;
        ViewHolder(View view){
            foodName = (TextView) view.findViewById(R.id.food_name);
        }
    }

    @Override
    public int getCount() {
        return resFoods.size();
    }
}
