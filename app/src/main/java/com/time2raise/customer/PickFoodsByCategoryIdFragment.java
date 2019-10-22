package com.time2raise.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.EventDetailed;
import com.time2raise.customer.data.model.EventInf;
import com.time2raise.customer.data.model.FoodSize;
import com.time2raise.customer.data.model.FoodSizesInformation;
import com.time2raise.customer.data.model.ResFood;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PickFoodsByCategoryIdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PickFoodsByCategoryIdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickFoodsByCategoryIdFragment extends Fragment {

    int categoryId;
    int eventId;
    String token;

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;

    RelativeLayout layoutFoodsList;

    List<ResFood> foods;

    ListView foodList;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<FoodSizesInformation>> expandableListDetail;

    private OnFragmentInteractionListener mListener;

    public PickFoodsByCategoryIdFragment() {
        // Required empty public constructor
    }


    public PickFoodsByCategoryIdFragment newInstance(int categoryId, int eventId) {
        PickFoodsByCategoryIdFragment fragment = new PickFoodsByCategoryIdFragment();
        Bundle args = new Bundle();
        this.categoryId = categoryId;
        this.eventId = eventId;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_foods_by_category_id, container, false);
        skipLoginPhone = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        token = skipLoginPhone.getString(APP_TOKEN, "");

        System.out.println("--------------" + categoryId);

        layoutFoodsList = view.findViewById(R.id.layout_foods_list);
        foodList = view.findViewById(R.id.foods_list);
        getRestaurantId();
        return view;
    }


    private void loadFoods(int restaurantId) {

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getFoodsByResIdAndCategory(token, restaurantId, categoryId);

        System.out.println("Идеально");

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    List<ResFood> resFoods = (List<ResFood>) response.body();
                    for (ResFood resFood : resFoods) {
                        System.out.println("--------------" + resFood.getFoodName());
                    }

                    startShowFoodsToPick(resFoods);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("Ffffffffffffffffffffffff");
                t.printStackTrace();
            }
        });
    }

    private void startShowFoodsToPick(List<ResFood> resFoods) {

        // Тут каждый элемент списка будет один класс "FoodAdapter", куда мы отправляем нужные значения во время создании экземпляра.
        /*ArrayList<ResFood> ff = new ArrayList<>(resFoods);

        for (ResFood food : ff){
            System.out.println(food.toString());
        }

        FoodAdapter foodAdapter = new FoodAdapter(getActivity().getApplicationContext(), R.layout.food_list_item, ff);
        System.out.println(foodAdapter.getCount());
        foodAdapter.setEventId(eventId);
        foodList.setAdapter(foodAdapter);*/

        expandableListDetail = new HashMap<>();
        for (ResFood food : resFoods){
            expandableListDetail.put(food.getFoodName(), food.getFoodSizesInformation());
        }
        expandableListView = (ExpandableListView) foodList;

        //expandableListDetail = ExpandableListData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        //System.out.println("count chaild :" + expandableListAdapter.getChildView(1, 0, new View(getContext()), null);
        /*int i = 0 ;
        for (ResFood resFood : resFoods){
            i ++ ;
            final LinearLayout food = new LinearLayout(getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            Resources r = getContext().getResources();
            int left = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    r.getDisplayMetrics()
            );

            int top = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );

            params.setMargins(left, top , 0 , 0 );
            food.setLayoutParams(params);
            food.setOrientation(LinearLayout.VERTICAL);




            TextView foodName = new TextView(getContext());

            LinearLayout.LayoutParams foodNameParams = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            foodName.setLayoutParams(foodNameParams);
            foodName.setText(resFood.getFoodName());
            foodName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            foodName.setTextColor(getContext().getColor(R.color.food_list_color));
            foodName.setCompoundDrawablesWithIntrinsicBounds(null, null , getContext().getDrawable(R.drawable.icon_polygon), null);
            foodName.setGravity(Gravity.CENTER_VERTICAL);
            int drawablePadding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5,
                    r.getDisplayMetrics()
            );
            foodName.setCompoundDrawablePadding(drawablePadding);

            food.addView(foodName);

            final LinearLayout foodSizes = new LinearLayout(getContext());

            final LinearLayout.LayoutParams foodSizeParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            foodSizes.setLayoutParams(foodSizeParams);

            for (FoodSizesInformation foodSize : resFood.getFoodSizesInformation()){
                TextView foodSizeName = new TextView(getContext());
                foodSizeName.setText(foodSize.getFoodSizes().getSizeName());
                foodSizes.addView(foodSizeName);
            }

            //foodSizes.setEnabled(false);

            foodName.setId(i + 1);

            foodName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Ebabled!");
                    if (foodSizes.getVisibility() == View.VISIBLE)
                        foodSizes.setVisibility(View.GONE);
                    else foodSizes.setVisibility(View.VISIBLE);
                }
            });

            food.addView(foodSizes);

            /*TextView foodPrice = new TextView(getContext());

            RelativeLayout.LayoutParams foodPriceParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            foodPriceParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            foodPrice.setLayoutParams(foodPriceParams);


            List<FoodSizesInformation> foodSizesInformation = resFood.getFoodSizesInformation();

            foodPrice.setText("+ $" + foodSizesInformation.get(0).getPrice());

            food.addView(foodPrice);

            View line = new View(getContext());

            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getContext().getResources().getDimensionPixelOffset(R.dimen.line_height)
            );

            lineParams.setMargins(left, top, 0 , 0);

            line.setLayoutParams(lineParams);
            line.setBackgroundColor(getContext().getColor(R.color.line_color));

            layoutFoodsList.addView(food);
            layoutFoodsList.addView(line);

        }*/
    }

    private void getRestaurantId() {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getEventById(token, eventId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    EventDetailed eventInformation = (EventDetailed) response.body();
                    loadFoods(eventInformation.getRestaurantInfo().getResId());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
