package com.epicodus.foodcarttracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Bind(R.id.showCartsButton) Button mShowCartsButton;
    @Bind(R.id.quadSelectSpinner) Spinner mQuadSelectSpinner;
    @Bind(R.id.signInButton) Button mSignInButton;

    private String mSelection;

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selection = (String) parent.getItemAtPosition(pos);
        mSelection = selection;

    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quadrants_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mQuadSelectSpinner.setAdapter(adapter);
        mQuadSelectSpinner.setOnItemSelectedListener(this);

        mShowCartsButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view == mShowCartsButton) {
            String location = mSelection;
            addToSharedPreferences(location);
            Intent intent = new Intent(MainActivity.this, CartListActivity.class);
            intent.putExtra("selection", mSelection);
            startActivity(intent);
        }
        if (view == mSignInButton) {
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
        }

    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}

