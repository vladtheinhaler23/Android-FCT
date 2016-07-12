package com.epicodus.foodcarttracker.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.adapters.CartPagerAdapter;
import com.epicodus.foodcarttracker.models.Cart;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CartDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private CartPagerAdapter adapterViewPager;
    ArrayList<Cart> mCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        ButterKnife.bind(this);

        mCarts = Parcels.unwrap(getIntent().getParcelableExtra("carts"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new CartPagerAdapter(getSupportFragmentManager(), mCarts);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
