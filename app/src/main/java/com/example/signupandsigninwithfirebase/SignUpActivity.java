package com.example.signupandsigninwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText signUpEmailEditText,signUpPasswordEditText;
    Button signUpButton;
    TextView signInTextView;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up Activity");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()){
            signUpEmailEditText.setError("Enter a valid  email address");
            signUpEmailEditText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(signUpPassword)){
            signUpPasswordEditText.setError("Enter  password");
            signUpPasswordEditText.requestFocus();
            return;
        }
        if (signUpPassword.length()<6){
            signUpPasswordEditText.setError("Minimum length of a password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword( signUpEmail,signUpPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Register is successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Register is not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}