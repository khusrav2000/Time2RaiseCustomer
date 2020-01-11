package com.time2raise.customer.events;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.time2raise.customer.R;
import com.time2raise.customer.data.model.EventInf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyListEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyListEventsRecyclerViewAdapter.ViewHolder> implements Filterable {

    // Список event-ов, которые мы получаем из сервера.
    // Events - Это модель для помещения в него получаемых данных из сервера.
    private final List<EventInf> mValues;
    private final List<EventInf> mValuesFull;
    private final ListEventsFragment.OnListFragmentInteractionListener mListener;

    // Ссылка на хранилище фотографий этого проета.
    // Фотографии находяться в GoogleDrive-е.
    String StorageUrl = "https://drive.google.com/uc?export=download&id=";

    View getContexts;
    CheckBox searchByName;
    CheckBox searchByZipCode;

    public MyListEventsRecyclerViewAdapter(Activity activity, List<EventInf> items, ListEventsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mValuesFull = new ArrayList<>(items);
        searchByName = activity.findViewById(R.id.search_by_name);
        searchByZipCode = activity.findViewById(R.id.search_by_zip_code);
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

        System.out.println("id : "+ mValues.get(position).getEventId() + "name: " + mValues.get(position).getName());
        holder.nameEvent.setText(mValues.get(position).getName());
        holder.eventDate.setText(getCorrectDate(mValues.get(position).getDate()));
        holder.eventStartEndTime.setText(getCorrectTime(mValues.get(position).getStart()) + " - "
                + getCorrectTime(mValues.get(position).getEnd()));

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
    private String getCorrectTime(String date){
        String format = "HH:mm aa";
        SimpleDateFormat format1= new SimpleDateFormat(format);
        Date date1 = null;
        try {
            date1 =new SimpleDateFormat("HH:mm:ss").parse(date);
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        if (date1 == null) {
            try {
                date1 = new SimpleDateFormat("HH:mm:ss").parse("10:10:00");
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }
        return format1.format(date1);
    }

    private String getCorrectDate(String date){
        String format = "MMM dd yyyy";
        SimpleDateFormat format1= new SimpleDateFormat(format);
        System.out.println(" DATE = " + date);
        Date date1 = null;
        try {
            date1 =new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        if (date1 == null){
            try {
                date1 =new SimpleDateFormat("yyyy-MM-dd").parse("2000-10-10T10:10");
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }
        return format1.format(date1);
    }
    // Получения количество event-ов с списке.
    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return ourFilter;
    }

    private Filter ourFilter = new Filter() {

        int searchBy = 1;
        public void setSearchBy(int i){
            searchBy = i;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EventInf> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mValuesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                if (searchByName.isChecked()) {
                    for (EventInf item : mValuesFull) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                } else {
                    for (EventInf item : mValuesFull) {
                        if (String.valueOf(item.getZipCode()).toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mValues.clear();
            mValues.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView eventMainImage;
        final TextView nameEvent;
        final TextView eventStartEndTime;
        final TextView eventDate;
        final Button eventDetail;


        EventInf mItem;

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
