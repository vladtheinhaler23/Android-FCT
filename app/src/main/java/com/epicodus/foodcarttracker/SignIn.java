package com.epicodus.foodcarttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.signInSubmitbutton) Button mSignInSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mSignInSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mSignInSubmit) {
            String email = mEmailEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            Toast.makeText(SignIn.this, "Sorry, " + email + " this feature is not implemented yet.", Toast.LENGTH_LONG).show();

        }


    }
}
