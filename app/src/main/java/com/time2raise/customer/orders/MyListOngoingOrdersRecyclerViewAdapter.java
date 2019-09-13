package com.time2raise.customer.orders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.time2raise.customer.R;
import com.time2raise.customer.data.model.Order;
import com.time2raise.customer.data.model.OrderInformation;

import org.w3c.dom.Text;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link ListOngoingOrdersFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListOngoingOrdersRecyclerViewAdapter extends RecyclerView.Adapter<MyListOngoingOrdersRecyclerViewAdapter.ViewHolder> {

    // Список event-ов, которые мы получаем из сервера.
    // Events - Это модель для помещения в него получаемых данных из сервера.
    //private final List<RestaurantInformation> mValues;

    private final ListOngoingOrdersFragment.OnListFragmentInteractionListener mListener;

    // Ссылка на хранилище фотографий этого проета.
    // Фотографии находяться в GoogleDrive-е.
    String StorageUrl = "https://drive.google.com/uc?export=download&id=";
    List<Order> mValues;
    View getContexts;

    public MyListOngoingOrdersRecyclerViewAdapter(List<Order> items, ListOngoingOrdersFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MyListOngoingOrdersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Скопируем layout, которое будем помещать в каждую строку ListView.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ongoing_order_line, parent, false);
        getContexts = view;
        return new MyListOngoingOrdersRecyclerViewAdapter.ViewHolder(view);
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
                foodName.setText(orderInformationList.get(i).getFoodName());

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
