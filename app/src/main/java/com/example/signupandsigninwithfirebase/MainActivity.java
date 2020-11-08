package com.example.signupandsigninwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText signInEmailEditText,signInPasswordEditText;
    Button signInButton;
    TextView signUpTextView,resetPasswordTextView;
    ProgressBar progressBar;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In Activity");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //view finding
        signInEmailEditText=findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditText=findViewById(R.id.signInPasswordEditTextId);
        signInButton=findViewById(R.id.signInButtonId);
        signUpTextView=findViewById(R.id.signUpTextViewId);
        resetPasswordTextView=findViewById(R.id.resetPasswordTextViewId);
        progressBar=findViewById(R.id.signInProgressBarId);


        // listener set
        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);
        resetPasswordTextView.setOnClickListener(this);
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
            case R.id.resetPasswordTextViewId:
                AlertDialog.Builder builder     =new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater   =LayoutInflater.from(MainActivity.this);
                View view                       =layoutInflater.inflate(R.layout.reset_layout,null);
                builder.setView(view);
                final AlertDialog alertDialog   = builder.create();
                final EditText resetMailEditText=view.findViewById(R.id.resetEmailEditTextId);
                Button saveButton=view.findViewById(R.id.resetPassOkButtonId);
                Button cancelButton=view.findViewById(R.id.resetPassCancelButtonId);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // extract email and send reset link
                            String mail=resetMailEditText.getText().toString();
                            if (TextUtils.isEmpty(mail)){
                                resetMailEditText.setError("Enter your email");
                                resetMailEditText.requestFocus();
                                return;
                            }

                            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                                resetMailEditText.setError("Enter a valid  email address");
                                resetMailEditText.requestFocus();
                                return;
                            }
                            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    alertDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Reset link not sent to your email"+e.getMessage().toString(), Toast.LENGTH_SHORT).show();


                                }
                            });

                        }
                    });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
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

        mAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()){
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(MainActivity.this, "please verify your email address.", Toast.LENGTH_LONG).show();
                            }

                        }
                        else if (mAuth.getCurrentUser()==null){
                            Toast.makeText(MainActivity.this, "have't account. please create your account", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "error: "+task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
}