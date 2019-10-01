package com.time2raise.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.time2raise.customer.data.model.FoodSizesInformation;

import org.w3c.dom.Text;

import java.util.ArrayList;

class FoodSizesAdapter extends ArrayAdapter<FoodSizesInformation> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<FoodSizesInformation> foodSizesList;

    FoodSizesAdapter(Context context, int resource, ArrayList<FoodSizesInformation> foods) {
        super(context, resource, foods);
        this.foodSizesList = foods;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FoodSizesInformation foodBySizes = foodSizesList.get(position);

        viewHolder.foodSizeName.setText(foodBySizes.getFoodSize().getSizeName());
        viewHolder.foodSizePrice.setText(String.valueOf(foodBySizes.getPrice()));

        return convertView;
    }


    private class ViewHolder {
        final TextView foodSizeName;
        final TextView foodSizePrice;
        ViewHolder(View view){
            foodSizePrice = view.findViewById(R.id.food_size_price);
            foodSizeName = view.findViewById(R.id.food_size_name);
        }
    }
}