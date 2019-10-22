package com.time2raise.customer;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.time2raise.customer.data.model.FoodSizesInformation;
import com.time2raise.customer.data.model.OneFoodSubFoodSizes;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<FoodSizesInformation>> expandableListDetail;

    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, List<FoodSizesInformation>> expandableListDetail) {

        //super(context, expandableListTitle, expandableListDetail);

        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        for (FoodSizesInformation f : expandableListDetail.get(expandableListTitle.get(4))){
            System.out.println(f.toString());
        }
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
         return this.expandableListDetail.get(this.expandableListTitle.get(i))
                .size();
    }

    @Override
    public Object getGroup(int i) {
        return this.expandableListTitle.get(i);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
         return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        FoodSizesInformation expandedList = (FoodSizesInformation) getChild(i, i1);

        return expandedList.getFoodId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String listTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.food_list_item, null);
        }
        TextView listTitleTextView = (TextView) view.findViewById(R.id.food_name);
        //listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return view;

    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final FoodSizesInformation expandedList = (FoodSizesInformation) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.food_list_chaild_item, null);
        }
        LinearLayout expandedListTextView = (LinearLayout) convertView//chaild_item
                .findViewById(R.id.chaild_item);

        RelativeLayout foodOfSize = new RelativeLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Resources r = context.getResources();

        int start = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10,
                r.getDisplayMetrics()
        );

        params.setMargins(start, 0 , 0 , 0);
        foodOfSize.setLayoutParams(params);

        final CheckBox isPicked = new CheckBox(context);
        RelativeLayout.LayoutParams isPickedParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        isPickedParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        isPickedParam.addRule(RelativeLayout.CENTER_VERTICAL);
        isPicked.setLayoutParams(isPickedParam);

        //i ++;

        //isPicked.setId(position * 100 + i);

        // Создание View для имени размера продукта.
        TextView foodSizeName = new TextView(context);
        RelativeLayout.LayoutParams foodSizeNameParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        foodSizeNameParam.addRule(RelativeLayout.RIGHT_OF, isPicked.getId());
        foodSizeNameParam.addRule(RelativeLayout.CENTER_VERTICAL);
        foodSizeName.setLayoutParams(foodSizeNameParam);
        foodSizeName.setText(expandedList.getFoodSize().getSizeName());



        final LinearLayout countLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams countLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        countLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        countLayout.setLayoutParams(countLayoutParams);
        countLayout.setVisibility(View.GONE);



        TextView minus = new TextView(context);
        minus.setText("-  ");


        countLayout.addView(minus);

        final TextView count = new TextView(context);
        count.setText("0");
        countLayout.addView(count);


        TextView plus = new TextView(context);
        plus.setText("  +");
        countLayout.addView(plus);

        // Создание View для цены определенного размера продукта.
        final TextView foodSizePrice = new TextView(context);
        RelativeLayout.LayoutParams foodSizePriceParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        foodSizePriceParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        foodSizePriceParam.addRule(RelativeLayout.CENTER_VERTICAL);
        foodSizePrice.setLayoutParams(foodSizePriceParam);
        foodSizePrice.setText(String.valueOf(expandedList.getPrice()));

        /*isPicked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });*/


        System.out.println("OMAAAAAAAAAAAAAAAAAAAAAAAAAD!");
        foodOfSize.addView(isPicked);
        foodOfSize.addView(foodSizeName);
        foodOfSize.addView(countLayout);
        foodOfSize.addView(foodSizePrice);
        //expandedListTextView.setText(expandedListText);

        expandedListTextView.addView(foodOfSize);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
