package com.nguyendinhdoan.orderfooddemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyendinhdoan.orderfooddemo.model.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText phoneEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private Button signUpButton;
    private ProgressBar progressBar;

    private FirebaseDatabase db;
    private DatabaseReference userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.phone_2_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        signUpButton = findViewById(R.id.sign_up_button);
        progressBar = findViewById(R.id.progress_bar);

        // init firebase
        db = FirebaseDatabase.getInstance();
        userTable = db.getReference("users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                userTable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         String name = nameEditText.getText().toString();
                         String phone = phoneEditText.getText().toString();
                         String password  = passwordEditText.getText().toString();

                        Log.d("phone: ","" + phone);
                        Log.d("data: ", "" + dataSnapshot.child(phone).exists());
                        // check if already password
                        if (dataSnapshot.child(phone).exists()) {
                            Toast.makeText(SignUpActivity.this, "Phone number already register", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(name, password);
                            userTable.child(phone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Sign up successful",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Sign up failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}


