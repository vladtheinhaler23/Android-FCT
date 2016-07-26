package com.epicodus.foodcarttracker.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.adapters.CartListAdapter;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.services.YelpService;
import com.epicodus.foodcarttracker.util.OnCartSelectedListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import android.support.v7.widget.SearchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
    private CartListAdapter mAdapter;
    public ArrayList<Cart> mCarts = new ArrayList<>();
    private OnCartSelectedListener mOnCartSelectedListener;


    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    public CartListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnCartSelectedListener = (OnCartSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        // Instructs fragment to include menu options:
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        ButterKnife.bind(this, view);

        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        if (mRecentAddress != null) {
            getCarts(mRecentAddress);
        }

        return view;
    }

    public void getCarts(String location) {
        final YelpService yelpService = new YelpService();

        yelpService.findCarts(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mCarts = yelpService.processResults(response);

                getActivity().runOnUiThread(new Runnable() {


                    @Override
                    public void run() {
                        mAdapter = new CartListAdapter(getActivity(), mCarts, mOnCartSelectedListener);

                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getCarts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}




