package com.example.signupandsigninwithfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText signUpEmailEditText,signUpPasswordEditText;
    Button signUpButton;
    TextView signInTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //view finding
        signUpEmailEditText=findViewById(R.id.signUpEmailEditTextId);
        signUpPasswordEditText=findViewById(R.id.signUpPasswordEditTextId);
        signUpButton=findViewById(R.id.signUpButtonId);
        signInTextView=findViewById(R.id.signInTextViewId);

        // listener set
        signUpButton.setOnClickListener(this);
        signInTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButtonId:
                userRegister();

                break;
            case R.id.signInTextViewId:
                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void userRegister() {
        String signUpEmail= signUpEmailEditText.getText().toString().trim();
        String signUpPassword= signUpPasswordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(signUpEmail)){
            signUpEmailEditText.setError("Enter your email");
            signUpEmailEditText.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()){
            signUpEmailEditText.setError("Enter a valid  email address");
            signUpEmailEditText.requestFocus();
        }
        if (TextUtils.isEmpty(signUpPassword)){
            signUpPasswordEditText.setError("Enter  password");
            signUpPasswordEditText.requestFocus();
        }
        if (signUpPassword.length()<6){
            signUpPasswordEditText.setError("Minimum length of a password should be 6");
            signUpPasswordEditText.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}