package com.time2raise.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.Message;
import com.time2raise.customer.data.model.Order;
import com.time2raise.customer.data.model.OrderToSend;
import com.time2raise.customer.data.model.OrderToSendItem;
import com.time2raise.customer.data.room.OrderRepository;
import com.time2raise.customer.data.room.OrderToCart;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;
    int eventId;
    TableLayout cartFoodsList;
    RelativeLayout addFoodsToOrder;
    ImageButton backFragment;

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_TOKEN = "getToken";
    SharedPreferences skipLoginPhone;
    int fragmentNumber;

    public CartFragment() {
        // Required empty public constructor
    }


    public CartFragment newInstance(int eventId, int fragmentNumber) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        this.eventId = eventId;
        this.fragmentNumber = fragmentNumber;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartFoodsList = view.findViewById(R.id.cart_food_list);
        addFoodsToOrder = view.findViewById(R.id.add_cart_foods_to_order);
        backFragment = view.findViewById(R.id.back_fragment_button);

        backFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToBackFragment();
            }
        });

        final OrderRepository orderRepository = new OrderRepository(getActivity().getApplication());
        new GetFoods(orderRepository, eventId).execute();

        addFoodsToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StartAddFoods(orderRepository, eventId).execute();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    private class GetFoods extends AsyncTask<Void, Void, List<OrderToCart>> {

        private OrderRepository orderRepository;
        public int eventId;

        GetFoods(OrderRepository orderRepos, int eventId) {
            orderRepository = orderRepos;
            this.eventId = eventId;
        }



        @Override
        protected List<OrderToCart> doInBackground(Void... params) {
            //List<OrderToCart> orderToCarts = orderRepository.getOrdersByEventId(eventId);

            return orderRepository.getOrdersByEventId(eventId);
        }

        @Override
        protected void onPostExecute(List<OrderToCart> orderToCarts) {
            startShowFoods(orderToCarts);
        }
    }

    private class StartAddFoods extends AsyncTask<Void, Void, List<OrderToCart>> {

        private OrderRepository orderRepository;
        public int eventId;

        StartAddFoods(OrderRepository orderRepos, int eventId) {
            orderRepository = orderRepos;
            this.eventId = eventId;
        }



        @Override
        protected List<OrderToCart> doInBackground(Void... params) {
            //List<OrderToCart> orderToCarts = orderRepository.getOrdersByEventId(eventId);

            return orderRepository.getOrdersByEventId(eventId);
        }

        @Override
        protected void onPostExecute(List<OrderToCart> orderToCarts) {
            startAddFoodsToOrder(orderToCarts);
        }
    }

    private void startAddFoodsToOrder(final List<OrderToCart> orderToCarts) {
        List<OrderToSendItem> orderToSendItems = new ArrayList<>();

        for (OrderToCart orderToCart : orderToCarts){
            OrderToSendItem orderToSendItem = new OrderToSendItem(orderToCart.getFoodSizeId(), orderToCart.getAmount());
            orderToSendItems.add(orderToSendItem);
        }
        OrderToSend orderToSend = new OrderToSend(eventId, orderToSendItems);

        skipLoginPhone = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);
        Call call = customer.sendOrder(token, orderToSend);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200){
                    OrderRepository orderRepository = new OrderRepository(getActivity().getApplication());
                    for (OrderToCart orderToCart : orderToCarts){
                        System.out.println("-----------------");
                        System.out.println(orderToCart.toString());
                        //orderRepository.delete(orderToCart.getAutoOrderId());
                    }
                    System.out.println("SUCCESFUL");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    public void startShowFoods(List<OrderToCart> orderToCarts) {
        for(final OrderToCart orderToCart : orderToCarts) {
            final TableRow food = new TableRow(getContext());
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            food.setLayoutParams(params);
            TextView nameFood = new TextView(getContext());
            nameFood.setText(orderToCart.getFoodName() + " (" + orderToCart.getFoodSizeName() + ")");
            nameFood.setTextColor(getActivity().getColor(R.color.cart_food_name));
            nameFood.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            food.addView(nameFood);

            TextView amountFood = new TextView(getContext());
            amountFood.setText("x" + String.valueOf(orderToCart.getAmount()));
            amountFood.setTextColor(getActivity().getColor(R.color.cart_food_name));
            food.addView(amountFood);

            TextView priceFood = new TextView(getContext());
            priceFood.setText("$" + String.valueOf(orderToCart.getPrice()));
            priceFood.setTextColor(getContext().getColor(R.color.green));
            priceFood.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            food.addView(priceFood);

            TableRow.LayoutParams imageParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            imageParams.gravity = Gravity.END;
            ImageView deleteFood = new ImageView(getContext());
            deleteFood.setImageDrawable(getActivity().getDrawable(R.drawable.icon_delete_food));
            deleteFood.setLayoutParams(imageParams);
            food.addView(deleteFood);


            final float scale = getContext().getResources().getDisplayMetrics().density;

            final View view = new View(getContext());
            TableLayout.LayoutParams lineParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    (int) (0.5 * scale + 0.5f)
            );
            lineParams.setMargins(0, (int) (5 * scale + 0.5f), 0, (int) (5 * scale + 0.5f));
            view.setBackgroundColor(getActivity().getColor(R.color.line_color));
            view.setLayoutParams(lineParams);

            deleteFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderRepository orderRepository = new OrderRepository(getActivity().getApplication());
                    orderRepository.delete(orderToCart.getAutoOrderId());
                    food.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }
            });

            System.out.println(orderToCart.toString());
            cartFoodsList.addView(food);
            cartFoodsList.addView(view);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void goToBackFragment();
    }
}
