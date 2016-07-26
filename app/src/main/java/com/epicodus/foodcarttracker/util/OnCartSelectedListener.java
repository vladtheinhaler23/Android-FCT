package com.epicodus.foodcarttracker.util;

import android.support.v7.app.AppCompatActivity;

import com.epicodus.foodcarttracker.models.Cart;

import java.util.ArrayList;

/**
 * Created by Joshua on 7/25/16.
 */
public interface OnCartSelectedListener {
    public void onCartSelected(Integer position, ArrayList<Cart> carts, String source);
}
