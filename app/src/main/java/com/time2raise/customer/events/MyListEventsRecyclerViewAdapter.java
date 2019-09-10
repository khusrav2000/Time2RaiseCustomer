package com.time2raise.customer.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time2raise.customer.R;
import com.time2raise.customer.data.model.EventInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link} and makes a call to the
 * specified {@link ListEventsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyListEventsRecyclerViewAdapter.ViewHolder> {

    // Список event-ов, которые мы получаем из сервера.
    // Events - Это модель для помещения в него получаемых данных из сервера.
    private final List<EventInformation> mValues;

    private final ListEventsFragment.OnListFragmentInteractionListener mListener;

    // Ссылка на хранилище фотографий этого проета.
    // Фотографии находяться в GoogleDrive-е.
    String StorageUrl = "https://drive.google.com/uc?export=download&id=";

    View getContexts;

    public MyListEventsRecyclerViewAdapter(List<EventInformation> items, ListEventsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Скопируем layout, которое будем помещать в каждую строку ListView.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_line, parent, false);
        getContexts = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.nameEvent.setText(mValues.get(position).getName());
        holder.eventDate.setText(mValues.get(position).getDate());

        // Отслеживания на нажатия на кнопку detail для event-ов.
        holder.eventDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    // Информация о event-е идёк в EventClass в метод onListFragmentInteraction через класс ListEventsFragment.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });


    }

    // Получения количество event-ов с списке.
    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView eventMainImage;
        final TextView nameEvent;
        final TextView eventStartEndTime;
        final TextView eventDate;
        final Button eventDetail;


        EventInformation mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            // Поля event-а.
            eventMainImage = (ImageView) view.findViewById(R.id.event_main_image);
            nameEvent = (TextView) view.findViewById(R.id.event_name);
            eventStartEndTime = (TextView) view.findViewById(R.id.event_start_end_time);
            eventDate = (TextView) view.findViewById(R.id.event_date);
            eventDetail = (Button) view.findViewById(R.id.event_detail_button);

        }

        @Override
        public String toString() {
            return "";
        }
    }
}
