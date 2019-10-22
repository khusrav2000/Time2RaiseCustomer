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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.time2raise.customer.data.model.FoodSize;
import com.time2raise.customer.data.model.FoodSizesInformation;
import com.time2raise.customer.data.model.OneFoodInfo;
import com.time2raise.customer.data.model.OneFoodSubFoodSizes;
import com.time2raise.customer.data.model.Order;
import com.time2raise.customer.data.model.OrderInformation;
import com.time2raise.customer.data.model.ResFood;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FoodAdapter extends ArrayAdapter<ResFood> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<ResFood> resFoods;
    int eventId;


    FoodAdapter(Context context, int resource, ArrayList<ResFood> resFoods) {
        super(context, resource, resFoods);
        System.out.println("Start work!");
        this.resFoods = resFoods;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView == null){

            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

            final ResFood resFood = resFoods.get(position);

            final List<OneFoodSubFoodSizes> oneFoodSubFoodSizesList = new ArrayList<>();

            /////////////////
            final OneFoodInfo oneFoodInfo = new OneFoodInfo(resFood.getFoodName(), 0.0,
                    oneFoodSubFoodSizesList, eventId);

            System.out.println("position!!!" + position);
            System.out.println(resFood.getFoodName());
            viewHolder.foodName.setText(resFood.getFoodName());
            int i = 0 ;

            //FoodSizesInformation foodSizesInformationFirst;
            final CheckBox isPickedFirst;

            for (final FoodSizesInformation foodSizesInformation : resFood.getFoodSizesInformation()){

                System.out.println(foodSizesInformation.toString());


                final OneFoodSubFoodSizes oneFoodSubFoodSizes = new OneFoodSubFoodSizes(0,
                        foodSizesInformation.getFoodSize().getSizeName(), 0.0);

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

                final CheckBox isPicked = new CheckBox(getContext());
                RelativeLayout.LayoutParams isPickedParam = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                isPickedParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                isPickedParam.addRule(RelativeLayout.CENTER_VERTICAL);
                isPicked.setLayoutParams(isPickedParam);

                i ++;

                isPicked.setId(position * 100 + i);
                final int iii = i;

                /*if (i == 1){
                    viewHolder.pickedOneFoodSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b){
                                isPicked.setChecked(true);
                            } else {
                                isPicked.setChecked(false);
                            }
                        }
                    });
                }*/

                    /* viewHolder.pickedOneFoodSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                System.out.println("TRUE = " + b);
                                System.out.println("iii === " + iii);
                                if (iii == 1) {
                                    isPicked.setChecked(true);
                                }
                            } else {
                                isPicked.setChecked(false);
                            }
                        }
                    });*/


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



                final LinearLayout countLayout = new LinearLayout(getContext());
                RelativeLayout.LayoutParams countLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                countLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                countLayout.setLayoutParams(countLayoutParams);
                countLayout.setVisibility(View.GONE);



                TextView minus = new TextView(getContext());
                minus.setText("-  ");


                countLayout.addView(minus);

                final TextView count = new TextView(getContext());
                count.setText("0");
                countLayout.addView(count);


                TextView plus = new TextView(getContext());
                plus.setText("  +");
                countLayout.addView(plus);

                // Создание View для цены определенного размера продукта.
                final TextView foodSizePrice = new TextView(getContext());
                RelativeLayout.LayoutParams foodSizePriceParam = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                foodSizePriceParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                foodSizePriceParam.addRule(RelativeLayout.CENTER_VERTICAL);
                foodSizePrice.setLayoutParams(foodSizePriceParam);
                foodSizePrice.setText(String.valueOf(oneFoodSubFoodSizes.getPrice()));

                isPicked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        System.out.println(isPicked.getId());
                        if (b) {
                            countLayout.setVisibility(View.VISIBLE);

                            oneFoodInfo.setTotalPtice(oneFoodInfo.getTotalPtice() +
                                    foodSizesInformation.getPrice());
                            viewHolder.foodPrice.setText(String.valueOf(oneFoodInfo.getTotalPtice()));

                            count.setText("1");
                            oneFoodSubFoodSizes.setAmount(1);
                            oneFoodSubFoodSizes.setPrice(foodSizesInformation.getPrice());
                            foodSizePrice.setText(String.valueOf(oneFoodSubFoodSizes.getPrice()));

                            if (oneFoodInfo.getTotalPtice() > 0.0){
                                viewHolder.pickedOneFoodSize.setChecked(true);
                            } else {
                                viewHolder.pickedOneFoodSize.setChecked(false);
                            }

                        } else {
                            oneFoodInfo.setTotalPtice(oneFoodInfo.getTotalPtice() -
                                    oneFoodSubFoodSizes.getAmount() * foodSizesInformation.getPrice());
                            viewHolder.foodPrice.setText(String.valueOf(oneFoodInfo.getTotalPtice()));

                            countLayout.setVisibility(View.GONE);
                            count.setText("0");
                            oneFoodSubFoodSizes.setAmount(0);
                            oneFoodSubFoodSizes.setPrice(0.0);
                            foodSizePrice.setText(String.valueOf(oneFoodSubFoodSizes.getPrice()));

                            if (oneFoodInfo.getTotalPtice() > 0.0){
                                viewHolder.pickedOneFoodSize.setChecked(true);
                            } else {
                                viewHolder.pickedOneFoodSize.setChecked(false);
                            }
                        }

                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        oneFoodInfo.setTotalPtice(oneFoodInfo.getTotalPtice() -
                                foodSizesInformation.getPrice());
                        viewHolder.foodPrice.setText(String.valueOf(oneFoodInfo.getTotalPtice()));

                        oneFoodSubFoodSizes.setAmount(oneFoodSubFoodSizes.getAmount() - 1);
                        oneFoodSubFoodSizes.setPrice(oneFoodSubFoodSizes.getAmount() * foodSizesInformation.getPrice());
                        count.setText(String.valueOf(oneFoodSubFoodSizes.getAmount()));
                        foodSizePrice.setText(String.valueOf(oneFoodSubFoodSizes.getPrice()));

                        if (oneFoodSubFoodSizes.getAmount() == 0){
                            countLayout.setVisibility(View.GONE);
                            isPicked.setChecked(false);
                        }

                        if (oneFoodInfo.getTotalPtice() > 0.0){
                            viewHolder.pickedOneFoodSize.setChecked(true);
                        } else {
                            viewHolder.pickedOneFoodSize.setChecked(false);
                        }
                    }
                });

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        oneFoodInfo.setTotalPtice(oneFoodInfo.getTotalPtice() +
                                foodSizesInformation.getPrice());
                        viewHolder.foodPrice.setText(String.valueOf(oneFoodInfo.getTotalPtice()));

                        oneFoodSubFoodSizes.setAmount(oneFoodSubFoodSizes.getAmount() + 1);
                        oneFoodSubFoodSizes.setPrice(oneFoodSubFoodSizes.getAmount() * foodSizesInformation.getPrice());
                        count.setText(String.valueOf(oneFoodSubFoodSizes.getAmount()));
                        foodSizePrice.setText(String.valueOf(oneFoodSubFoodSizes.getPrice()));

                        if (oneFoodInfo.getTotalPtice() > 0.0){
                            viewHolder.pickedOneFoodSize.setChecked(true);
                        } else {
                            viewHolder.pickedOneFoodSize.setChecked(false);
                        }
                    }
                });



                foodOfSize.addView(isPicked);
                foodOfSize.addView(foodSizeName);
                foodOfSize.addView(countLayout);
                foodOfSize.addView(foodSizePrice);

                viewHolder.foodSizesList.addView(foodOfSize);

                oneFoodSubFoodSizesList.add(oneFoodSubFoodSizes);

            }

            viewHolder.pickedOneFoodSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CheckBox pick = viewHolder.foodSizesList.findViewById(position * 100 + 1);
                    if (b){
                        if (oneFoodInfo.getTotalPtice() == 0.0) {
                            pick.setChecked(true);
                        }
                    } else {
                        for (int j = 1; j <= resFood.getFoodSizesInformation().size() ; j++){
                            CheckBox pickAll = viewHolder.foodSizesList.findViewById(position * 100 + j);
                            pickAll.setChecked(false);
                        }
                    }
                }
            });

            oneFoodInfo.setFoodSizes(oneFoodSubFoodSizesList);
            viewHolder.foodPrice.setText(String.valueOf(oneFoodInfo.getTotalPtice()));

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //foodName.setText(resFood.getFoodName());

        viewHolder.foodName.setOnClickListener(new View.OnClickListener() {
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
        final TextView foodPrice;
        final CheckBox pickedOneFoodSize;
        ViewHolder(View view){
            mView = view;
            foodSizesList = view.findViewById(R.id.food_sizes_list);
            foodName = (TextView) view.findViewById(R.id.food_name);
            foodPrice = view.findViewById(R.id.food_sizes_total_price);
            pickedOneFoodSize = view.findViewById(R.id.picked_one_food_sizes);
        }
    }

    @Override
    public int getCount() {
        return resFoods.size();
    }


    public void setEventId(int id){
        eventId = id;
    }
}
