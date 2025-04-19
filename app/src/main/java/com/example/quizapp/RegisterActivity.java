package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail,etPassword,etConfirmPassword;
    Button bSignUp;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etEmail=findViewById(R.id.email);
        etPassword=findViewById(R.id.password);
        etConfirmPassword=findViewById(R.id.confirmPassword);
        bSignUp=findViewById(R.id.bSignUp);
        tvLogin=findViewById(R.id.tvLogin);

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().equals("abde") &&
                        etPassword.getText().toString().equals("2025") &&
                        etConfirmPassword.getText().toString().equals("2025")
                ){
                    Intent i1=new Intent(RegisterActivity.this, Quiz1.class);
                    startActivity(i1);
                }else{
                    Toast.makeText(getApplicationContext(),"Login or password or confirmation incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}