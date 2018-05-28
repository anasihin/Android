package com.example.warteg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class RegistrasiActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText etEmail, etPassword, etKonfirmPassword;
    private TextView tvLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        mAuth = FirebaseAuth.getInstance();
        FindView();
        HandleEvent();
    }

    private void FindView() {
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etKonfirmPassword = (EditText) findViewById(R.id.et_kPassword);
        btn = (Button) findViewById(R.id.btn_daftar);
        tvLogin = (TextView) findViewById(R.id.tv_login);
    }

    private void HandleEvent() {
        btn.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_daftar:
                daftarproses();
                break;

            case R.id.tv_login:
                Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    private void daftarproses() {
        String email = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());
        String konfirmpassword = String.valueOf(etKonfirmPassword.getText());
        if (!password.equals(konfirmpassword)) {
            Toast.makeText(RegistrasiActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 5) {
            Toast.makeText(RegistrasiActivity.this, "Password harus lebih 5 karakter", Toast.LENGTH_SHORT).show();
        } else if (!email.contains("@")) {
            Toast.makeText(RegistrasiActivity.this, "Masukan email yang benar", Toast.LENGTH_SHORT).show();
        } else if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegistrasiActivity.this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegistrasiActivity.this, "Gagal Registrasi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(RegistrasiActivity.this, "Semua Field harus di isi", Toast.LENGTH_SHORT).show();
        }
    }
}