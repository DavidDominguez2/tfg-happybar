package com.example.happybar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class FavoritosActivity extends AppCompatActivity {

    private BottomNavigationView bmenu;
    private FirebaseAuth auth;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);

        auth = FirebaseAuth.getInstance();

        bmenu = findViewById(R.id.bottom_navigation);

        bmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Mapa:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        return true;

                    case R.id.Favoritos:
                        System.out.println("USUARIO: " + auth.getCurrentUser().getUid());

                        return true;

                    case R.id.Ajustes:

                        startActivity(new Intent(getApplicationContext(), AjustesActivity.class));
                        return true;

                    case R.id.Salir:

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        auth.signOut();
                        return true;
                }
                return false;
            }
        });
        bmenu.setSelectedItemId(R.id.Favoritos);
    }
    }

