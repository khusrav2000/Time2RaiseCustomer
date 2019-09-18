package com.time2raise.customer.orders;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.time2raise.customer.R;
import com.time2raise.customer.data.model.Order;
import com.time2raise.customer.data.model.OrderInformation;

import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link ListOngoingOrdersFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListPastOrdersRecyclerViewAdapter extends RecyclerView.Adapter<MyListPastOrdersRecyclerViewAdapter.ViewHolder> {


    private final List<Order > mValues;

    private final ListPastOrdersFragment.OnListFragmentInteractionListener mListener;


    String StorageUrl = "https://drive.google.com/uc?export=download&id=";

    View getContexts;


    public MyListPastOrdersRecyclerViewAdapter(List<Order> orders, ListPastOrdersFragment.OnListFragmentInteractionListener listener) {
        mValues = orders;
        mListener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Скопируем layout, которое будем помещать в каждую строку ListView.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_past_order_line, parent, false);
        getContexts = view;
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.orderId.setText("Order id: " + holder.mItem.getOrderId());
        holder.date.setText(holder.mItem.getCreateDate());
        holder.totalPrice.setText("$ " + String.valueOf(holder.mItem.getTotalPrice()));

        List<OrderInformation> orderInformationList = holder.mItem.getOrderInformationList();
        LayoutInflater inflater = LayoutInflater.from(holder.mView.getContext());
        if (orderInformationList != null){
            for (int i = 0 ; i < orderInformationList.size(); i++){

                View view = inflater.inflate(R.layout.food_information, holder.listFoods, false);

                TextView foodName = view.findViewById(R.id.food_name);
                foodName.setText(orderInformationList.get(i).getFoodName() + "(" + orderInformationList.get(i).getFoodSizeName() + ")");

                TextView foodCount = view.findViewById(R.id.food_count);
                foodCount.setText("Qty: " + orderInformationList.get(i).getAmount());;

                TextView foodPrice = view.findViewById(R.id.food_price);
                foodPrice.setText("$ " + orderInformationList.get(i).getPrice());

                holder.listFoods.addView(view);

            }
        }

    }

    // Получения количество event-ов с списке.
    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        Order mItem;

        TextView orderId;
        TextView date;
        TextView totalPrice;
        LinearLayout listFoods;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            orderId = view.findViewById(R.id.ongoing_order_id);
            totalPrice = view.findViewById(R.id.total_price);
            date = view.findViewById(R.id.order_date);
            listFoods = view.findViewById(R.id.foods);

        }

        @Override
        public String toString() {
            return "1";
        }
    }
}
