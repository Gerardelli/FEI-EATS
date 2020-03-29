package com.eats.fei.ui.registrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eats.fei.R;
import com.eats.fei.ui.login.LoginActivity;

public class RegistrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        final TextView textViewLogin = findViewById(R.id.textLogin);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });
    }
}
