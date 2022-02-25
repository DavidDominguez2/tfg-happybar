package com.example.happybar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.happybar.DAO.Bar;
import com.example.happybar.DAO.Usuario;
import com.example.happybar.databinding.ActivityIndexMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //OBJETOS MAPA
    private GoogleMap mMap;
    private ActivityIndexMapsBinding binding;
    private Marker marcadorUsuario;
    double lat = 0.0;
    double lng = 0.0;

    //RECOGIDA DE DATOS
    private FirebaseDatabase bbdd;
    private DatabaseReference reference,referenceUsuario;
    private BottomNavigationView bmenu;
    private FirebaseAuth auth;
    private String user;
    private ArrayList<String> favs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIndexMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //INSTANCIAR LA BBDD
        bbdd = FirebaseDatabase.getInstance("https://happybar-tfg-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = bbdd.getReference().child("Bares");


        //RECOGER EL USUARIO
        referenceUsuario = bbdd.getReference().child("Ususario");

        referenceUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot hijo: snapshot.getChildren()){

                    if(hijo.getKey().equalsIgnoreCase(user)){
                        Usuario usu = hijo.getValue(Usuario.class);
                        favs = usu.getFavoritos();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //MENU FOOTER
        bmenu = findViewById(R.id.bottom_navigation);

        bmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Mapa:

                        return true;

                    case R.id.Favoritos:
                        Intent intent = new Intent(getApplicationContext(), FavoritosActivity.class);
                        intent.putExtra("favoritos", favs);
                        startActivity(intent);
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
        bmenu.setSelectedItemId(R.id.Mapa);




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot hijo : snapshot.getChildren()) {
                    Bar bar = hijo.getValue(Bar.class);
                    mMap.addMarker(new MarkerOptions().position(latLng(bar.getLatitud(), bar.getLongitud())).title(bar.getNombre()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        miUbicacion();
    }

    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcadorUsuario != null) marcadorUsuario.remove();
        marcadorUsuario = mMap.addMarker(new MarkerOptions().position(coordenadas).icon(BitmapDescriptorFactory.fromResource(R.mipmap.google)));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            actualizarUbicacion(location);
        }
    };

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 0, locListener);
    }

    public LatLng latLng(double lat, double lng){
        LatLng location = new LatLng(lat, lng);
        return location;
    }

}