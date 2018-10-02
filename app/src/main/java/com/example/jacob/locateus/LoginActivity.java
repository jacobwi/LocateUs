package com.example.jacob.locateus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacob.locateus.Data.CurrentUser;
import com.example.jacob.locateus.Data.User;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginBtn) MaterialButton loginBtn;
    @BindView(R.id.emailid) EditText userNameInput;
    @BindView(R.id.password) EditText passwordInput;
    @BindView(R.id.signUpTextView) TextView signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        // Database init
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = database.getReference("Users");

        final FirebaseAuth mAuth =FirebaseAuth.getInstance();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (userNameInput.getText().toString().isEmpty()){
                            Toast.makeText(LoginActivity.this, "Enter your username", Toast.LENGTH_SHORT).show();
                        }

                        else if (passwordInput.getText().toString().isEmpty()){
                            Toast.makeText(LoginActivity.this, "Enter your username", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (dataSnapshot.child(userNameInput.getText().toString()).exists()){
                                User user = dataSnapshot.child(userNameInput.getText().toString()).getValue(User.class);

                                // Confirm Password
                                if (user.getPassword().equals(passwordInput.getText().toString())){
                                    // Login successful
                                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    CurrentUser.currentUser = user;
                                    CurrentUser.phonenumber = userNameInput.getText().toString();

                                    finish();
                                }
                                else {
                                    // Invalid password
                                    Toast.makeText(LoginActivity.this, "You have entered the wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                // Username doesn't exist
                                Toast.makeText(LoginActivity.this, "This username doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }


}
