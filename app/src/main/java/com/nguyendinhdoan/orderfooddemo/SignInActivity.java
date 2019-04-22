package com.nguyendinhdoan.orderfooddemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyendinhdoan.orderfooddemo.common.Common;
import com.nguyendinhdoan.orderfooddemo.model.User;

public class SignInActivity extends AppCompatActivity {

    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private ProgressBar progressBar;

    private FirebaseDatabase db;
    private DatabaseReference userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phoneEditText = findViewById(R.id.phone_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        signInButton = findViewById(R.id.sign_in_button);
        progressBar = findViewById(R.id.progress_bar);

        // init firebase
        db = FirebaseDatabase.getInstance();
        userTable = db.getReference("users");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                userTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.INVISIBLE);

                        // get user information
                        String phone = phoneEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        // check if user exist in database
                        if (dataSnapshot.child(phone).exists()) {
                            User user = dataSnapshot.child(phone).getValue(User.class);
                            if (user.getPassword().equals(password)) {
                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, "login failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //
                    }
                });
            }
        });
    }
}
