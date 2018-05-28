package com.example.warteg;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelmantics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText etEmail, etPassword;
    private TextView tvRegistrasi, tvLupaPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FindView();
        HandleEvent();
    }

    private void FindView(){
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btn = (Button) findViewById(R.id.btn_login);
        tvRegistrasi = (TextView) findViewById(R.id.tv_registrasi);
        tvLupaPassword = (TextView) findViewById(R.id.tv_lupaPassword);
    }

    private void HandleEvent(){
        btn.setOnClickListener(this);
        tvRegistrasi.setOnClickListener(this);
        tvLupaPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginproses();
                break;

            case R.id.tv_registrasi:
                Intent intent = new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.tv_lupaPassword:
                Toast.makeText(LoginActivity.this, "Text View Lupa Password di Klik", Toast.LENGTH_SHORT).show();
//                intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            default:
                break;
        }
    }

    public void loginproses(){
        String username = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());
        if (!username.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(username,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(LoginActivity.this, "Email dan Password harus d isi", Toast.LENGTH_SHORT).show();
        }
    }

}
