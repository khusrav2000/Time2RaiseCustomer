package com.time2raise.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.time2raise.customer.data.model.FoodSizesInformation;
import com.time2raise.customer.data.model.ResFood;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends ArrayAdapter<ResFood> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<ResFood> resFoods;

    ListView foodsList;

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

        final ViewHolder viewHolder;
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
        foodsList = (ListView) viewHolder.foodSizesList;
        ArrayList<FoodSizesInformation> foodSizesInformation = new ArrayList<>(resFood.getFoodSizesInformation());
        FoodSizesAdapter adapter = new FoodSizesAdapter( getContext(), R.layout.food_sizes_list_item, foodSizesInformation);
        foodsList.setAdapter(adapter);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.foodSizesList.getVisibility() == View.VISIBLE){
                    viewHolder.foodSizesList.setVisibility(View.GONE);
                }
                else {
                    viewHolder.foodSizesList.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        final TextView foodName;
        final View mView;
        final ListView foodSizesList;
        ViewHolder(View view){
            mView = view;
            foodSizesList = view.findViewById(R.id.food_sizes_list);
            foodName = (TextView) view.findViewById(R.id.food_name);
        }
    }

    @Override
    public int getCount() {
        return resFoods.size();
    }
}
