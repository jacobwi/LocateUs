package com.example.jacob.locateus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jacob.locateus.Data.Location;
import com.example.jacob.locateus.Data.User;
import com.example.jacob.locateus.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.email) EditText email;
    @BindView(R.id.passwordsignup) EditText password;
    @BindView(R.id.confirmpasswordsignup) EditText passwordConfirm;
    @BindView(R.id.phonenumber) EditText phonenumber;
    @BindView(R.id.fullname) EditText fullname;
    @BindView(R.id.signupBtn) MaterialButton signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = database.getReference("Users");
        final DatabaseReference location_table = database.getReference("Locations");


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if fields are empty
                        if (!TextUtils.isEmpty(phonenumber.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())
                                && !TextUtils.isEmpty(passwordConfirm.getText().toString()) &&
                                !TextUtils.isEmpty(fullname.getText().toString()) && !TextUtils.isEmpty(phonenumber.getText().toString())) {
                            if (dataSnapshot.child(phonenumber.getText().toString()).exists()) {
                                Toast.makeText(SignupActivity.this, "This email is already associated with an account", Toast.LENGTH_SHORT).show();
                            } else {

                                // Check if both password fields matches
                                if (password.getText().toString().equals(password.getText().toString())) {
                                    User user = new User(email.getText().toString(), fullname.getText().toString(),
                                            password.getText().toString(), phonenumber.getText().toString());
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

                                    Location location = new Location(timeStamp, 0.0, 0.0);



                                    user_table.child(phonenumber.getText().toString()).setValue(user);
                                    location_table.child(phonenumber.getText().toString()).setValue(location);
                                    Toast.makeText(SignupActivity.this, "Sign up is successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    password.setError("Password doesn't match");
                                    password.requestFocus();
                                    return;
                                }

                            }
                        }

                        else {
                            Toast.makeText(SignupActivity.this, "You can't have any empty fields", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
