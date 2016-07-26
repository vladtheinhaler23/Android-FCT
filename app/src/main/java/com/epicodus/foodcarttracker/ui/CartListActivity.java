package com.epicodus.foodcarttracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.adapters.CartListAdapter;
import com.epicodus.foodcarttracker.services.YelpService;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.util.OnCartSelectedListener;

import org.parceler.Parcels;

import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class CartListActivity extends AppCompatActivity implements OnCartSelectedListener {

    private Integer mPosition;
    ArrayList<Cart> mCarts;
    String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mCarts = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_CARTS));

                if (mPosition != null && mCarts != null) {
                    Intent intent = new Intent(this, CartDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_CARTS, Parcels.wrap(mCarts));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }

            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mCarts != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putString(Constants.KEY_SOURCE, mSource);
            outState.putParcelable(Constants.EXTRA_KEY_CARTS, Parcels.wrap(mCarts));
        }

    }

    @Override
    public void onCartSelected(Integer position, ArrayList<Cart> carts, String source) {
        mPosition = position;
        mCarts = carts;
        mSource = source;
    }

}
