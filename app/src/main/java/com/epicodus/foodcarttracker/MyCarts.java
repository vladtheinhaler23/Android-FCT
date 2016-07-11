package com.epicodus.foodcarttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

public class MyCarts extends AppCompatActivity {

    public static final String TAG = MyCarts.class.getSimpleName();


    @Bind(R.id.cartListView)
    ListView mCartListView;

    private String[] carts = new String[]{"E-San Thai", "Caspian Kabob",
            "Mama Chow’s Kitchen", "Chez Dodo", "Chop Chop", "Güero",
            "Nong’s Khao Man Gai", "PDX Sliders", "Kim Jong Grillin’", "The Frying Scotsman",
            "Korean Twist", "Steaks Fifth Avenue", "The Angry Unicorn",
            "Bing Mi", "808 Grinds"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_carts);
        ButterKnife.bind(this);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, carts);
        mCartListView.setAdapter(adapter);
        mCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cart = ((TextView) view).getText().toString();
                Toast.makeText(MyCarts.this, cart, Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = getIntent();
        String selection = intent.getStringExtra("selection");

        getCarts(selection);

        Toast.makeText(MyCarts.this, selection, Toast.LENGTH_LONG).show();
    }

    private void getCarts(String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findCarts(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
