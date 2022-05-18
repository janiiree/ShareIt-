package com.example.shareit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText etEmail, etPassword;
    String email, password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view) {
        etEmail = findViewById(R.id.etEmail);
        email = etEmail.getText().toString();

        etPassword = findViewById(R.id.etPassword);
        password = etPassword.getText().toString();

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        if (email == null || password == null) {
            Toast.makeText(getApplicationContext(), R.string.fill_all_gaps, Toast.LENGTH_LONG).show();
        } else {
            Data data = new Data.Builder()
                    .putString("email", email)
                    .putString("password", password)
                    .build();

            Log.d("prueba", username + " " + email + " " + password);

            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(CheckUserLoginWorker.class).setInputData(data).build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, workInfo -> {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            Log.d("prueba", username + " " + email + " " + password);
                            if (workInfo.getOutputData().getString("name") != null) {
                                username = workInfo.getOutputData().getString("name");
                                Toast.makeText(getApplicationContext(), R.string.welcome + username + "!", Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                                intent.putExtra("name", username)
                                        .putExtra("email", email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.incorrect_user_pass, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
        }
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
