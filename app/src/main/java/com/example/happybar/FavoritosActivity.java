package com.example.happybar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happybar.DAO.Bar;
import com.example.happybar.DAO.Oferta;
import com.example.happybar.DAO.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {

    private BottomNavigationView bmenu;
    private FirebaseAuth auth;
    private FirebaseDatabase bbdd;
    private DatabaseReference reference;
    private String user;
    private static ArrayList<String> favs;
    private RecyclerView rv;
    private AdaptadorBares adapter;
    ArrayList<Bar> listaBares;


    private AdaptadorBares.listenersInterfaz goDescription = new AdaptadorBares.listenersInterfaz(){

        @Override
        public void clickEnElementoCard(int pos) {
           System.out.println("Aquí va la acción de ir al resumen de la carta");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);


        listaBares = new ArrayList<Bar>();

        Intent idRecibido = getIntent();
        favs = idRecibido.getStringArrayListExtra("favoritos");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();

        bbdd = FirebaseDatabase.getInstance("https://happybar-tfg-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = bbdd.getReference().child("Bares");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot hijo: snapshot.getChildren()){
                    for (String bar: favs) {
                        if(hijo.getKey().equalsIgnoreCase(bar)){
                            Bar b = hijo .getValue(Bar.class);
                            System.out.println(b);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
       //listaBares.add(new Bar(3.2,0.1,"ElPintxo", new ArrayList<Oferta>()));
        adapter = new AdaptadorBares(listaBares, goDescription);



        bmenu = findViewById(R.id.bottom_navigation);

        bmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Mapa:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        return true;

                    case R.id.Favoritos:

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

