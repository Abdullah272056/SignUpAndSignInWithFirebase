package com.example.signupandsigninwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.signOutItemId){
            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent( HomeActivity.this,MainActivity.class);
            startActivity(intent);

        }
        if (item.getItemId()==R.id.deleteAccountItemId){
            FirebaseUser user = mAuth.getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(HomeActivity.this, "Your Account delete successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(HomeActivity.this, "Your Account delete not successful", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }


        return super.onOptionsItemSelected(item);
    }
}