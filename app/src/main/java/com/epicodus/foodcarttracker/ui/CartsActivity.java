package com.epicodus.foodcarttracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.services.YelpService;
import com.epicodus.foodcarttracker.models.Cart;

import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class CartsActivity extends AppCompatActivity {

    public static final String TAG = CartsActivity.class.getSimpleName();


    @Bind(R.id.cartListView) ListView mCartListView;
    public ArrayList<Cart> mCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_carts);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String selection = intent.getStringExtra("selection");

        getCarts(selection);

        Toast.makeText(CartsActivity.this, selection, Toast.LENGTH_LONG).show();
    }

    private void getCarts(String location) {
        final YelpService yelpService = new YelpService();

        yelpService.findCarts(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mCarts = yelpService.processResults(response);

                CartsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String[] cartNames = new String[mCarts.size()];
                        for (int i = 0; i < cartNames.length; i++) {
                            cartNames[i] = mCarts.get(i).getName();
                        }

                        ArrayAdapter adapter = new ArrayAdapter(CartsActivity.this,
                                android.R.layout.simple_list_item_1, cartNames);
                        mCartListView.setAdapter(adapter);

                        for (Cart cart : mCarts) {
                            Log.d(TAG, "Name: " + cart.getName());
                            Log.d(TAG, "Phone: " + cart.getPhone());
                            Log.d(TAG, "Website: " + cart.getWebsite());
                            Log.d(TAG, "Image url: " + cart.getImageUrl());
                            Log.d(TAG, "Rating: " + Double.toString(cart.getRating()));
                            Log.d(TAG, "Address: " + android.text.TextUtils.join(", ", cart.getAddress()));
                            Log.d(TAG, "Categories: " + cart.getCategories().toString());
                        }
                    }
                });

            }
        });
    }
}
