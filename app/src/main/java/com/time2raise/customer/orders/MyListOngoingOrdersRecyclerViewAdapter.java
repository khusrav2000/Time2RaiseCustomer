package com.time2raise.customer.orders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time2raise.customer.R;

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
    List<String> mValues;
    View getContexts;

    public MyListOngoingOrdersRecyclerViewAdapter(List<String> items, ListOngoingOrdersFragment.OnListFragmentInteractionListener listener) {
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
    public void onBindViewHolder(final MyListOngoingOrdersRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


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

        String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }

        @Override
        public String toString() {
            return "1";
        }
    }
}
