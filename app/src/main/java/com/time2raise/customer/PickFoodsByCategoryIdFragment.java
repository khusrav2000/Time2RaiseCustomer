package com.time2raise.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.EventDetailed;
import com.time2raise.customer.data.model.EventInf;
import com.time2raise.customer.data.model.FoodSize;
import com.time2raise.customer.data.model.FoodSizesInformation;
import com.time2raise.customer.data.model.OneFoodInfo;
import com.time2raise.customer.data.model.OneFoodSubFoodSizes;
import com.time2raise.customer.data.model.ResFood;
import com.time2raise.customer.data.room.OrderRepository;
import com.time2raise.customer.data.room.OrderToCart;

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
    ImageView backFragment;

    RelativeLayout layoutFoodsList;

    List<ResFood> foods;
    //ArrayList<OrderToCart> foodPicked = new ArrayList<OrderToCart>();
    LinearLayout foodList;
    TextView totalOrderPrice;
    RelativeLayout addFoodsToCard;
    double totalPrice;

    List<OrderToCart> orderToCarts = new ArrayList<>();


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
        totalOrderPrice = view.findViewById(R.id.total_order_price);
        addFoodsToCard = view.findViewById(R.id.add_picked_foods_to_cart);
        getRestaurantId();

        addFoodsToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" HOOO LO ");
                //List<OneFoodInfo> oneFoodInfos = CalculateCount.foods;
                OrderRepository orderRepository = new OrderRepository(getActivity().getApplication());
                System.out.println(eventId);
                for (OrderToCart orderToCart : orderToCarts){
                    if (orderToCart.getAmount() > 0) {
                        orderRepository.insert(orderToCart);
                        System.out.println(orderToCart.toString());
                    }
                }
                mListener.setCountFoodsCart();
                mListener.goToPlaceAndOrder();
            }
        });

        backFragment = view.findViewById(R.id.back_fragment_button_pick_foods);
        backFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToPlaceAndOrder();
            }
        });

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

        int i = 0 ;
        for (ResFood resFood : resFoods){
            i ++ ;
            LinearLayout foodLine = new LinearLayout(getActivity().getApplicationContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            foodLine.setLayoutParams(params);
            foodLine.setOrientation(LinearLayout.VERTICAL);

            System.out.println(resFood.toString());


            // Здесь заканчивается инициализация параметром FoodLine

            RelativeLayout foodNameLiner = new RelativeLayout(getActivity().getApplicationContext());
            foodNameLiner.setLayoutParams(params);

            final CheckBox pickedFood = new CheckBox(getActivity().getBaseContext());
            pickedFood.setId(i);
            RelativeLayout.LayoutParams pickedFoodParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            pickedFoodParams.addRule(RelativeLayout.CENTER_VERTICAL);
            pickedFoodParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            pickedFood.setLayoutParams(pickedFoodParams);
            foodNameLiner.addView(pickedFood);

            TextView nameFood = new TextView(getActivity().getApplicationContext());
            nameFood.setId(i + 100000000);
            nameFood.setText(resFood.getFoodName());
            RelativeLayout.LayoutParams nameFoodParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            nameFoodParams.addRule(RelativeLayout.CENTER_VERTICAL);
            nameFoodParams.addRule(RelativeLayout.RIGHT_OF, pickedFood.getId());
            nameFood.setLayoutParams(nameFoodParams);
            foodNameLiner.addView(nameFood);

            ImageView showSizesImage = new ImageView(getActivity().getApplicationContext());
            showSizesImage.setId(i + 1000000);
            showSizesImage.setImageDrawable(getActivity().getBaseContext().getDrawable(R.drawable.icon_polygon_2));
            RelativeLayout.LayoutParams showSizesImageParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            Resources rr = getActivity().getApplicationContext().getResources();
            int widthS = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    rr.getDisplayMetrics()
            );
            int heightS = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    rr.getDisplayMetrics()
            );

            showSizesImageParams.width = widthS;
            showSizesImageParams.height = heightS;
            showSizesImageParams.addRule(RelativeLayout.CENTER_VERTICAL);
            showSizesImageParams.addRule(RelativeLayout.RIGHT_OF, nameFood.getId());
            showSizesImage.setLayoutParams(showSizesImageParams);
            foodNameLiner.addView(showSizesImage);

            final TextView priceFood = new TextView(getActivity().getApplicationContext());
            priceFood.setText("$0.0");
            RelativeLayout.LayoutParams priceFoodParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            priceFoodParams.addRule(RelativeLayout.CENTER_VERTICAL);
            priceFoodParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            priceFood.setLayoutParams(priceFoodParams);
            foodNameLiner.addView(priceFood);

            foodLine.addView(foodNameLiner);


            View line = new View(getActivity().getApplicationContext());
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getActivity().getBaseContext().getResources().getDimensionPixelOffset(R.dimen.line_height)
            );
            Resources r = getActivity().getApplicationContext().getResources();
            int bottom = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            int top = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    r.getDisplayMetrics()
            );
            lineParams.setMargins(0 , top, 0, bottom);
            line.setLayoutParams(lineParams);
            line.setBackgroundColor(getActivity().getBaseContext().getColor(R.color.line_color));

            final LinearLayout foodSizes = new LinearLayout(getActivity().getApplicationContext());

            LinearLayout.LayoutParams foodSizesParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            foodSizes.setLayoutParams(foodSizesParams);
            foodSizes.setVisibility(View.GONE);
            foodSizes.setOrientation(LinearLayout.VERTICAL);

            final OneFoodInfo oneFoodInfo = new OneFoodInfo(resFood.getFoodName(), 0.0,
                    new ArrayList<OneFoodSubFoodSizes>(), eventId);

            int j = 0;
            for (final FoodSizesInformation foodSizesInformation : resFood.getFoodSizesInformation()){

                System.out.println(foodSizesInformation.toString());


                final OrderToCart orderToCart = new OrderToCart(eventId, 0, resFood.getFoodName(),
                        foodSizesInformation.getFoodSize().getSizeName(), 0.0, categoryId, 0,
                        foodSizesInformation.getFoodId());

                orderToCarts.add(orderToCart);

                RelativeLayout foodOfSize = new RelativeLayout(getActivity().getApplicationContext());
                LinearLayout.LayoutParams foodOfSizeParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                //foodOfSize.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.green));

                int start = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        20,
                        r.getDisplayMetrics()
                );
                foodOfSizeParams.setMargins(start, 0 , 0 , 0);
                foodOfSize.setLayoutParams(foodOfSizeParams);

                final CheckBox isPicked = new CheckBox(getActivity().getApplicationContext());
                RelativeLayout.LayoutParams isPickedParam = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                isPickedParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                isPickedParam.addRule(RelativeLayout.CENTER_VERTICAL);
                isPicked.setLayoutParams(isPickedParam);

                j ++ ;
                isPicked.setId(i * 100 + j);
                System.out.println("sadas =  == " + isPicked.getId());
                final int iii = j;


                // Создание View для имени размера продукта.
                TextView foodSizeName = new TextView(getActivity().getApplicationContext());
                RelativeLayout.LayoutParams foodSizeNameParam = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                foodSizeNameParam.addRule(RelativeLayout.RIGHT_OF, isPicked.getId());
                foodSizeNameParam.addRule(RelativeLayout.CENTER_VERTICAL);
                foodSizeName.setLayoutParams(foodSizeNameParam);
                foodSizeName.setText(foodSizesInformation.getFoodSize().getSizeName());



                final LinearLayout countLayout = new LinearLayout(getActivity().getApplicationContext());
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
                final TextView foodSizePrice = new TextView(getActivity().getApplicationContext());
                RelativeLayout.LayoutParams foodSizePriceParam = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                foodSizePriceParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                foodSizePriceParam.addRule(RelativeLayout.CENTER_VERTICAL);
                foodSizePrice.setLayoutParams(foodSizePriceParam);
                foodSizePrice.setText(String.valueOf(orderToCart.getPrice()));

                isPicked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        System.out.println(isPicked.getId());
                        if (b) {
                            countLayout.setVisibility(View.VISIBLE);

                            oneFoodInfo.setTotalPrice(oneFoodInfo.getTotalPrice() +
                                    foodSizesInformation.getPrice());

                            totalPrice = totalPrice + foodSizesInformation.getPrice();
                            totalOrderPrice.setText(String.valueOf(totalPrice));

                            priceFood.setText(String.valueOf(oneFoodInfo.getTotalPrice()));

                            count.setText("1");
                            orderToCart.setAmount(1);
                            orderToCart.setPrice(foodSizesInformation.getPrice());
                            foodSizePrice.setText(String.valueOf(orderToCart.getPrice()));

                            if (oneFoodInfo.getTotalPrice() > 0.0){
                                pickedFood.setChecked(true);
                            } else {
                                pickedFood.setChecked(false);
                            }

                        } else {
                            oneFoodInfo.setTotalPrice(oneFoodInfo.getTotalPrice() -
                                    orderToCart.getAmount() * foodSizesInformation.getPrice());
                            priceFood.setText(String.valueOf(oneFoodInfo.getTotalPrice()));

                            totalPrice = totalPrice -
                                    orderToCart.getAmount() * foodSizesInformation.getPrice();
                            totalOrderPrice.setText(String.valueOf(totalPrice));

                            countLayout.setVisibility(View.GONE);
                            count.setText("0");
                            orderToCart.setAmount(0);
                            orderToCart.setPrice(0.0);
                            foodSizePrice.setText(String.valueOf(orderToCart.getPrice()));

                            if (oneFoodInfo.getTotalPrice() > 0.0){
                                pickedFood.setChecked(true);
                            } else {
                                pickedFood.setChecked(false);
                            }

                        }

                    }
                });

                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        oneFoodInfo.setTotalPrice(oneFoodInfo.getTotalPrice() -
                                foodSizesInformation.getPrice());
                        priceFood.setText(String.valueOf(oneFoodInfo.getTotalPrice()));

                        totalPrice = totalPrice - foodSizesInformation.getPrice();
                        totalOrderPrice.setText(String.valueOf(totalPrice));

                        orderToCart.setAmount(orderToCart.getAmount() - 1);
                        orderToCart.setPrice(orderToCart.getAmount() * foodSizesInformation.getPrice());
                        count.setText(String.valueOf(orderToCart.getAmount()));
                        foodSizePrice.setText(String.valueOf(orderToCart.getPrice()));

                        if (orderToCart.getAmount() == 0){
                            countLayout.setVisibility(View.GONE);
                            isPicked.setChecked(false);
                        }

                        if (oneFoodInfo.getTotalPrice() > 0.0){
                            pickedFood.setChecked(true);
                        } else {
                            pickedFood.setChecked(false);
                        }

                    }
                });

                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        oneFoodInfo.setTotalPrice(oneFoodInfo.getTotalPrice() +
                                foodSizesInformation.getPrice());
                        priceFood.setText(String.valueOf(oneFoodInfo.getTotalPrice()));

                        totalPrice = totalPrice + foodSizesInformation.getPrice();
                        totalOrderPrice.setText(String.valueOf(totalPrice));

                        orderToCart.setAmount(orderToCart.getAmount() + 1);
                        orderToCart.setPrice(orderToCart.getAmount() * foodSizesInformation.getPrice());
                        count.setText(String.valueOf(orderToCart.getAmount()));
                        foodSizePrice.setText(String.valueOf(orderToCart.getPrice()));

                        if (oneFoodInfo.getTotalPrice() > 0.0){
                            pickedFood.setChecked(true);
                        } else {
                            pickedFood.setChecked(false);
                        }

                    }
                });



                foodOfSize.addView(isPicked);
                foodOfSize.addView(foodSizeName);
                foodOfSize.addView(countLayout);
                foodOfSize.addView(foodSizePrice);

                foodSizes.addView(foodOfSize);

                //oneFoodSubFoodSizesList.add(oneFoodSubFoodSizes);

            }

            final int iii = i ;
            final int sizeFoods = resFood.getFoodSizesInformation().size();
            pickedFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        int t = iii * 100 + 1;
                        CheckBox che = foodSizes.findViewById(t);
                        if (oneFoodInfo.getTotalPrice() <= 0.0) {
                            che.setChecked(true);
                        }
                    } else {

                        for(int g = 0 ; g < sizeFoods; g ++){
                            int t = iii * 100 + g + 1;
                            CheckBox che = foodSizes.findViewById(t);
                            che.setChecked(false);
                        }

                    }
                }
            });

            showSizesImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (foodSizes.getVisibility() == View.VISIBLE){
                        foodSizes.setVisibility(View.GONE);
                    } else {
                        foodSizes.setVisibility(View.VISIBLE);
                    }
                }
            });

            nameFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (foodSizes.getVisibility() == View.VISIBLE){
                        foodSizes.setVisibility(View.GONE);
                    } else {
                        foodSizes.setVisibility(View.VISIBLE);
                    }
                }
            });

            foodLine.addView(foodSizes);

            foodList.addView(foodLine);
            foodList.addView(line);

        }
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
        void goToPlaceAndOrder();
        void setCountFoodsCart();
    }
}
