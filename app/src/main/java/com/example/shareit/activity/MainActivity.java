package com.example.shareit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.shareit.InfoDialog;
import com.example.shareit.LanguageDialog;
import com.example.shareit.R;

public class MainActivity extends AppCompatActivity {

    Button btnInfo, btnLang, btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInfo = findViewById(R.id.btn_info);

        btnInfo.setOnClickListener(view -> {
            DialogFragment infoDialog = new InfoDialog();
            infoDialog.show(getSupportFragmentManager(), "etiqueta");
        });

        btnLang = findViewById(R.id.btn_lang);

        btnLang.setOnClickListener(view -> {
            DialogFragment langDialog = new LanguageDialog();
            langDialog.show(getSupportFragmentManager(), "etiqueta");
        });

        btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}