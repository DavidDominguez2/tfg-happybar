package com.example.happybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.happybar.DAO.Usuario;
import com.example.happybar.databinding.ActivityDescriptionOfertaBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DescriptionOfertaActivity extends AppCompatActivity implements OnMapReadyCallback {
    //OBJETOS MAPA
    private GoogleMap mMap;
    private ActivityDescriptionOfertaBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView title, price, desciption;
    private Button onGo, onFav;

    private FirebaseAuth auth;
    private FirebaseDatabase bbdd;
    private DatabaseReference reference, referenceUsuario;
    private String user;
    private static ArrayList<String> favs;
    private Usuario usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDescriptionOfertaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapOferta);
        mapFragment.getMapAsync(this);

        title = findViewById(R.id.titleOferta);
        title.setText(getIntent().getStringExtra("titulo"));

        price = findViewById(R.id.priceOferta);
        price.setText(getIntent().getStringExtra("precio"));

        desciption = findViewById(R.id.descriptionOferta);
        desciption.setText(getIntent().getStringExtra("descripcion"));

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();

        //INSTANCIAR LA BBDD
        //RECOGER EL USUARIO
        bbdd = FirebaseDatabase.getInstance("https://happybar-tfg-default-rtdb.europe-west1.firebasedatabase.app/");
        referenceUsuario = bbdd.getReference().child("Usuario");

        referenceUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot hijo : snapshot.getChildren()) {
                    if (hijo.getKey().equalsIgnoreCase(user)) {
                        usu = hijo.getValue(Usuario.class);
                        if(usu.getFavoritos() == null){
                            favs = new ArrayList<String>();
                        }else{
                            favs = usu.getFavoritos();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        onGo = findViewById(R.id.buttonOferta);
        onGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        onFav = findViewById(R.id.buttonFavOferta);
        onFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favs.isEmpty()){
                    favs.add(getIntent().getStringExtra("oferta"));
                    usu.setFavoritos(favs);

                    referenceUsuario.child(user).setValue(usu);
                    Snackbar.make(view, "Bar añadido correctamente a sus favoritos", Snackbar.LENGTH_SHORT).show();
                }else{
                    if(favs.contains(getIntent().getStringExtra("oferta"))){
                        Snackbar.make(view, "El bar que quiere añadir, ya está en sus favoritos", Snackbar.LENGTH_SHORT).show();
                    }else{
                        favs.add(getIntent().getStringExtra("oferta"));
                        usu.setFavoritos(favs);

                        referenceUsuario.child(user).setValue(usu);
                        Snackbar.make(view, "Bar añadido correctamente a sus favoritos", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng barOferta = new LatLng(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("long", 0));
        mMap.addMarker(new MarkerOptions()
                .position(barOferta)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(barOferta));
    }

}