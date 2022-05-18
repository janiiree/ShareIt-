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

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText etEmail, etPassword, etUsername;
    String email, password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickRegister(View view) {
        etEmail = findViewById(R.id.etEmail);
        email = etEmail.getText().toString();

        etPassword = findViewById(R.id.etPassword);
        password = etPassword.getText().toString();

        etUsername = findViewById(R.id.etUsername);
        username = etUsername.getText().toString();

        btnRegister = findViewById(R.id.btnRegister);

        if (email == null || password == null || username == null) {
            Toast.makeText(getApplicationContext(), R.string.fill_all_gaps, Toast.LENGTH_LONG).show();
        } else {
            Data data = new Data.Builder()
                    .putString("email", email)
                    .putString("username", username)
                    .putString("password", password)
                    .build();

            Log.d("prueba", username + " " + email + " " + password);

            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(CheckUserLoginWorker.class).setInputData(data).build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, workInfo -> {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            // El email ya estaba registrado
                            Toast.makeText(getApplicationContext(), R.string.email_already_registered   , Toast.LENGTH_LONG).show();
                        } else if (workInfo != null) {
                            // El email no estaba registrado y se va a crear un nuevo usuario
                            OneTimeWorkRequest otwr2 = new OneTimeWorkRequest.Builder(RegisterWorker.class).setInputData(data).build();
                            WorkManager.getInstance(this).enqueue(otwr2);

                            Toast.makeText(getApplicationContext(), R.string.welcome + " " + username + "!", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                            intent.putExtra("name", username)
                                    .putExtra("email", email);
                            startActivity(intent);
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
        }
    }
}