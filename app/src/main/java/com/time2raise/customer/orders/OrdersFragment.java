package com.time2raise.customer.orders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.time2raise.customer.R;

public class OrdersFragment extends Fragment {

    private OrdersFragment.OnFragmentInteractionListener mListener;

    public OrdersFragment() {
        System.out.println("ORDERS FRAGMENT WORKED!");
    }

    @SuppressWarnings("unused")
    public static ListOngoingOrdersFragment newInstance(int columnCount) {
        ListOngoingOrdersFragment fragment = new ListOngoingOrdersFragment();
        Bundle args = new Bundle();
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

        View view = inflater.inflate(R.layout.orders_tabbed_fragment, container, false);
        OrdersSectionsPagerAdapter sectionsPagerAdapter = new OrdersSectionsPagerAdapter(getActivity(), getChildFragmentManager());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.orders_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.orders_tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrdersFragment.OnFragmentInteractionListener) {
            mListener = (OrdersFragment.OnFragmentInteractionListener) context;
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
