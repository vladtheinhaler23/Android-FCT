package com.epicodus.foodcarttracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.showCartsButton) Button mShowCartsButton;
    @Bind(R.id.quadSelectSpinner) Spinner mQuadSelectSpinner;
    @Bind(R.id.signInButton) Button mSignInButton;
    @Bind(R.id.savedCartsButton) Button mSavedCartsButton;

    private String mSelection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quadrants_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mQuadSelectSpinner.setAdapter(adapter);

        mShowCartsButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mSavedCartsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mShowCartsButton) {
            String location = mSelection;

            Intent intent = new Intent(MainActivity.this, CartListActivity.class);
            intent.putExtra("selection", mSelection);
            startActivity(intent);
        }
        if (view == mSignInButton) {
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
        }
        if (view == mSavedCartsButton) {
            Intent intent = new Intent(MainActivity.this, SavedCartListActivity.class);
            startActivity(intent);
        }

    }
}

