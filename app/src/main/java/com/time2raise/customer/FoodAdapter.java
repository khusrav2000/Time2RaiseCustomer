package com.time2raise.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.time2raise.customer.data.model.FoodSizesInformation;
import com.time2raise.customer.data.model.ResFood;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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

        for (FoodSizesInformation foodSizesInformation : resFood.getFoodSizesInformation()){
            RelativeLayout foodOfSize = new RelativeLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            Resources r = getContext().getResources();

            int start = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );

            params.setMargins(start, 0 , 0 , 0);
            foodOfSize.setLayoutParams(params);

            RadioButton isPicked = new RadioButton(getContext());
            RelativeLayout.LayoutParams isPickedParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            isPickedParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            isPickedParam.addRule(RelativeLayout.CENTER_VERTICAL);
            isPicked.setLayoutParams(isPickedParam);
            //isPicked.setId(1);

            // Создание View для имени размера продукта.
            TextView foodSizeName = new TextView(getContext());
            RelativeLayout.LayoutParams foodSizeNameParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            foodSizeNameParam.addRule(RelativeLayout.RIGHT_OF, isPicked.getId());
            foodSizeNameParam.addRule(RelativeLayout.CENTER_VERTICAL);
            foodSizeName.setLayoutParams(foodSizeNameParam);
            foodSizeName.setText(foodSizesInformation.getFoodSize().getSizeName());

            // Создание View для цены определенного размера продукта.
            TextView foodSizePrice = new TextView(getContext());
            RelativeLayout.LayoutParams foodSizePriceParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            foodSizePriceParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            foodSizePriceParam.addRule(RelativeLayout.CENTER_VERTICAL);
            foodSizePrice.setLayoutParams(foodSizePriceParam);
            foodSizePrice.setText(String.valueOf(foodSizesInformation.getPrice()));

            LinearLayout pickCount = new LinearLayout(getContext());

            RelativeLayout.LayoutParams pickCountParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            pickCountParam.addRule(RelativeLayout.CENTER_IN_PARENT);

            pickCount.setLayoutParams(pickCountParam);

            Button plus = new Button(getContext());
            plus.setText("+");
            Button minus = new Button(getContext());
            minus.setText("-");
            TextView count = new TextView(getContext());
            count.setText("0");

            pickCount.addView(minus);
            pickCount.addView(count);
            pickCount.addView(plus);

            foodOfSize.addView(isPicked);
            foodOfSize.addView(foodSizeName);
            foodOfSize.addView(pickCount);
            foodOfSize.addView(foodSizePrice);


            viewHolder.foodSizesList.addView(foodOfSize);
        }

        //foodName.setText(resFood.getFoodName());


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
        final LinearLayout foodSizesList;
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
