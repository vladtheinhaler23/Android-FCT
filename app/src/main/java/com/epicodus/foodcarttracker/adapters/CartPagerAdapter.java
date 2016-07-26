package com.epicodus.foodcarttracker.adapters;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.ui.CartDetailFragment;

import java.util.ArrayList;

/**
 * Created by Joshua on 7/12/16.
 */
public class CartPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Cart> mCarts;
    private String mSource;

    public CartPagerAdapter(FragmentManager fm, ArrayList<Cart> carts, String source) {
        super(fm);
        mCarts = carts;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return CartDetailFragment.newInstance(mCarts, position, mSource);
    }

    @Override
    public int getCount() {
        return mCarts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCarts.get(position).getName();
    }


}
