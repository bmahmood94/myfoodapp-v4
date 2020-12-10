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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private Button register_register, register_login;
    private EditText register_name, register_email, register_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_email = findViewById(R.id.register_email);
        register_name = findViewById(R.id.register_name);
        register_password = findViewById(R.id.register_password);
        register_register = findViewById(R.id.register_registerbtn);
        register_login = findViewById(R.id.register_loginbtn);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
        register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reg_email = register_email.getText().toString().trim();
                String reg_password = register_password.getText().toString().trim();
                if (TextUtils.isEmpty(reg_email)) {
                    register_email.setError("Email is needed in order to complete registration");
                    return;
                }
                if (TextUtils.isEmpty(reg_password)) {
                    register_password.setError("Please enter a password for security of your account");
                    return;
                }
                if (reg_password.length() < 4) {
                    register_password.setError("Please enter a password that is greater than 4 characters");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(reg_email, reg_password).addOnCompleteListener(Register.this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(Register.this, "Creating new user successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Register.this,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

            }

        });
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

}


