package com.example.myfoodapp_v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private Button login_login, login_register;//buttons for register and login
    private EditText login_email, login_password;//area for them to type in password and email
    private FirebaseAuth mAuth;//object for firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setting all buttons and textviews equal to ids in layoutpage
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_login = findViewById(R.id.login_loginbtn);
        login_register = findViewById(R.id.login_registerbtn);
        mAuth = FirebaseAuth.getInstance();


        String log_email = login_email.getText().toString().trim();
        String log_password = login_password.getText().toString().trim();

//if the login button is clicked get the string and check if its empty and for the password length to be larger then 4 characters
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String log_email = login_email.getText().toString().trim();
                String log_password = login_password.getText().toString().trim();
                //checks if email box is empty
                if (TextUtils.isEmpty(log_email)) {
                    login_email.setError("Email is needed in order to complete registration");
                    return;
                }
                //checkes if password is empty
                if (TextUtils.isEmpty(log_password)) {
                    login_password.setError("Please enter a password for security of your account");
                    return;
                }
                //checks length of password
                if (log_password.length() < 4) {
                    login_password.setError("Please enter a password that is greater than 4 characters");
                    return;
                }
                //if nothing is wrong sign in else send a quick message with the error
                mAuth.signInWithEmailAndPassword(log_email, log_password).addOnCompleteListener(Login.this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    // ...
                });
                //register button that will return to the register page
                login_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Login.this, Register.class);
                        startActivity(intent);
                    }
                });

            }

        });
    }
}