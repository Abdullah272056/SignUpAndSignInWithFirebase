package com.example.signupandsigninwithfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText signInEmailEditText,signInPasswordEditText;
    Button signInButton;
    TextView signUpTextView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In Activity");

        //view finding
        signInEmailEditText=findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText=findViewById(R.id.signInPasswordEditTextId);
        signInButton=findViewById(R.id.signInButtonId);
        signUpTextView=findViewById(R.id.signUpTextViewId);
        progressBar=findViewById(R.id.signInProgressBarId);


        // listener set
        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInButtonId:
                signIn();
                break;
            case R.id.signUpTextViewId:
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void signIn() {
        String signInEmail= signInEmailEditText.getText().toString().trim();
        String signInPassword= signInPasswordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(signInEmail)){
            signInEmailEditText.setError("Enter your email");
            signInEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(signInEmail).matches()){
            signInEmailEditText.setError("Enter a valid  email address");
            signInEmailEditText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(signInPassword)){
            signInPasswordEditText.setError("Enter  password");
            signInPasswordEditText.requestFocus();
            return;
        }
        if (signInPassword.length()<6){
            signInPasswordEditText.setError("Minimum length of a password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        
        // add sign In function


        progressBar.setVisibility(View.GONE);
    }
}