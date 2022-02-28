package com.example.happybar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    TextView avisoLegal, politicaPrivacidad, condicionesGenerales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avisoLegal = findViewById(R.id.avisoLegal);
        politicaPrivacidad = findViewById(R.id.politicaPrivacidad);
        condicionesGenerales = findViewById(R.id.condicionesGenerales);

        avisoLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreditosActivity.class));
            }
        });
        politicaPrivacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreditosActivity.class));
            }
        });
       condicionesGenerales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreditosActivity.class));
            }
        });



    }
}