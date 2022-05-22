package com.example.happybar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happybar.DAO.Bar;
import com.example.happybar.DAO.Oferta;
import com.example.happybar.DAO.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
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
    private DatabaseReference reference, referenceUsuario;
    private String user;
    private static ArrayList<String> favs;
    private RecyclerView rv;
    private AdaptadorBares adapter;
    ArrayList<Bar> listaBares;
    
    private ImageView logo;
    MediaPlayer mp;


    private AdaptadorBares.listenersInterfaz goDescription = new AdaptadorBares.listenersInterfaz(){

        @Override
        public void clickEnElementoCard(int pos) {

            if(listaBares.size() > 0){
                //ELIMINAR DEL FIREBASE
                String idBorrar = listaBares.get(pos).getId();
                referenceUsuario.child(user).child("favoritos").child(String.valueOf(pos)).removeValue();
                listaBares.remove(pos);
                adapter.notifyItemRemoved(pos);

                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Bar eliminado", Snackbar.LENGTH_SHORT);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.show();
                //finish();
            }else{
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "No hay suficientes bares para eliminar", Snackbar.LENGTH_SHORT);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                });
                snackbar.show();
            }

        }

        @Override
        public void clickCard(int pos) {
            Intent intent = new Intent(getApplicationContext(), OfertasActivity.class);
            intent.putExtra("ofertas", listaBares.get(pos).getOfertas());
            intent.putExtra("nombreBar", listaBares.get(pos).getNombre());
            intent.putExtra("keyBar", listaBares.get(pos).getId());
            intent.putExtra("latitud", listaBares.get(pos).getLatitud());
            intent.putExtra("longitud", listaBares.get(pos).getLongitud());
            startActivity(intent);

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);

        listaBares = new ArrayList<Bar>();
        //btnEliminar = findViewById(R.id.btnEliminar);


        Intent idRecibido = getIntent();
        favs = idRecibido.getStringArrayListExtra("favoritos");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();

        bbdd = FirebaseDatabase.getInstance("https://happybar-tfg-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = bbdd.getReference().child("Bares");
        referenceUsuario = bbdd.getReference().child("Usuario");

        adapter = new AdaptadorBares(listaBares, goDescription);
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!favs.isEmpty()) {
                    for (DataSnapshot hijo : snapshot.getChildren()) {
                        for (String bar : favs) {
                            if (hijo.getKey().equalsIgnoreCase(bar)) {
                                Bar b = hijo.getValue(Bar.class);
                                b.setId(hijo.getKey());
                                listaBares.add(b);
                            }
                        }
                        adapter.notifyItemChanged(0);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

        logo = findViewById(R.id.imagenLogo);
        mp = MediaPlayer.create(this, R.raw.cancion);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    mp.pause();
                }else {
                    mp.start();
                }
            }
        });


    }
}


