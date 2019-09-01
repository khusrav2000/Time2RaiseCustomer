package com.time2raise.customer.orders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.time2raise.customer.R;

public class OrdersSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.ongoing_orders, R.string.past_orders};
    private final Context mContext;

    public OrdersSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        System.out.println("WORKED ORDER SECTION PAGER");
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        System.out.println("GEt Items");
        if (position == 0) {
            return ListOngoingOrdersFragment.newInstance(1);
        } else if (position == 1){
            return ListPastOrdersFragment.newInstance(1);
        } else {
            return ListPastOrdersFragment.newInstance(1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
