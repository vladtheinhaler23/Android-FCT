package com.epicodus.foodcarttracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.adapters.CartListAdapter;
import com.epicodus.foodcarttracker.services.YelpService;
import com.epicodus.foodcarttracker.models.Cart;

import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class CartListActivity extends AppCompatActivity {

    public static final String TAG = CartListActivity.class.getSimpleName();


    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private CartListAdapter mAdapter;

    public ArrayList<Cart> mCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String selection = intent.getStringExtra("selection");

        getCarts(selection);

        Toast.makeText(CartListActivity.this, selection, Toast.LENGTH_LONG).show();
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

                CartListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new CartListAdapter(getApplicationContext(), mCarts);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(CartListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });

            }
        });
    }
}
