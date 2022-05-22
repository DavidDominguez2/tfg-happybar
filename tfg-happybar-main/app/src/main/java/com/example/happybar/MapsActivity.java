package com.example.happybar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.happybar.DAO.Bar;
import com.example.happybar.DAO.Oferta;
import com.example.happybar.DAO.Usuario;
import com.example.happybar.databinding.ActivityIndexMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //OBJETOS MAPA
    private GoogleMap mMap;
    private ActivityIndexMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;

    //RECOGIDA DE DATOS
    private FirebaseDatabase bbdd;
    private DatabaseReference reference, referenceUsuario;
    private BottomNavigationView bmenu;
    private FirebaseAuth auth;
    private String user;
    private ArrayList<String> favs;
    private TextInputEditText buscador;

    //FILTROS
    RadioGroup filterDistan;
    private Circle circleFiltro;


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
        referenceUsuario = bbdd.getReference().child("Usuario");

        referenceUsuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot hijo : snapshot.getChildren()) {

                    if (hijo.getKey().equalsIgnoreCase(user)) {
                        Usuario usu = hijo.getValue(Usuario.class);
                        if(usu.getFavoritos() == null) {
                            favs = new ArrayList<>();
                        }
                        else {
                            favs = usu.getFavoritos();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Buscador
        buscador = findViewById(R.id.buscador);
        buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "No está funcional, se incluirá en siguientes versiones", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //FILTROS
        filterDistan = findViewById(R.id.idFiltrosDistancia);

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

        getLocalizacion();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void getLocalizacion(){
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
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
        //DISEÑO MAPA
        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));
            if (!success) {
                System.out.println("MapsActivityRaw: " + "Style parsing failed."); }
        } catch (Resources.NotFoundException e) {
            System.out.println("MapsActivityRaw: " + "Can't find style.");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng lct = new LatLng(location.getLatitude(), location.getLongitude());
                //LatLng lct = new LatLng(1, 1);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(lct));

                CameraPosition cPosition = CameraPosition.builder().target(lct).zoom(13).tilt(45).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cPosition));

                filterDistan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        CameraPosition cFilter = null;
                        switch (radioGroup.getCheckedRadioButtonId()){
                            case R.id.radio_unokm:
                                cFilter = CameraPosition.builder().target(lct).zoom(15).tilt(45).build();
                                if(circleFiltro == null){
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(500).strokeWidth(0.0f));
                                }else{
                                    circleFiltro.remove();
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(500).strokeWidth(0.0f));
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                }
                                break;
                            case R.id.radio_cincokm:
                                cFilter = CameraPosition.builder().target(lct).zoom(13).tilt(45).build();
                                if(circleFiltro == null){
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(2500).strokeWidth(0.0f));
                                }else{
                                    circleFiltro.remove();
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(2500).strokeWidth(0.0f));
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                }
                                break;
                            case R.id.radio_diezkm:
                                cFilter = CameraPosition.builder().target(lct).zoom(12).tilt(45).build();
                                if(circleFiltro == null){
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(5000).strokeWidth(0.0f));
                                }else{
                                    circleFiltro.remove();
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(5000).strokeWidth(0.0f));
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                }
                                break;
                            case R.id.radio_quincekm:
                                cFilter = CameraPosition.builder().target(lct).zoom(11).tilt(45).build();
                                if(circleFiltro == null){
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(7500).strokeWidth(0.0f));
                                }else{
                                    circleFiltro.remove();
                                    circleFiltro = mMap.addCircle(new CircleOptions().center(lct).fillColor(Color.argb(64, 255, 252, 0)).radius(7500).strokeWidth(0.0f));
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cFilter));
                                }
                                break;
                        }
                    }
                });

                if (location != null) {
                    // Logic to handle location object
                }
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot hijo : snapshot.getChildren()) {
                    Bar bar = hijo.getValue(Bar.class);
                    switch (bar.getRango()){
                        case "bronce":
                            mMap.addMarker(new MarkerOptions().position(latLng(bar.getLatitud(), bar.getLongitud())).icon(BitmapDescriptorFactory.fromResource(R.drawable.bronce)).title(bar.getNombre()));
                            break;
                        case "plata":
                            mMap.addMarker(new MarkerOptions().position(latLng(bar.getLatitud(), bar.getLongitud())).icon(BitmapDescriptorFactory.fromResource(R.drawable.plata)).title(bar.getNombre()));
                            break;
                        case "oro":
                            mMap.addMarker(new MarkerOptions().position(latLng(bar.getLatitud(), bar.getLongitud())).icon(BitmapDescriptorFactory.fromResource(R.drawable.oro)).title(bar.getNombre()));
                            break;
                        case "diamante":
                            mMap.addMarker(new MarkerOptions().position(latLng(bar.getLatitud(), bar.getLongitud())).icon(BitmapDescriptorFactory.fromResource(R.drawable.diamante)).title(bar.getNombre()));
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot hijo: snapshot.getChildren()){
                            Bar bar = hijo.getValue(Bar.class);
                            if(bar.getNombre().equals(marker.getTitle())){
                                ArrayList<Oferta> ofertas = bar.getOfertas();
                                Intent intent = new Intent(getApplicationContext(), OfertasActivity.class);
                                intent.putExtra("ofertas", ofertas);
                                intent.putExtra("nombreBar", bar.getNombre());
                                intent.putExtra("keyBar", hijo.getKey());
                                intent.putExtra("latitud", bar.getLatitud());
                                intent.putExtra("longitud", bar.getLongitud());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }
        });
    }

    public LatLng latLng(double lat, double lng){
        LatLng location = new LatLng(lat, lng);
        return location;
    }

    @Override
    public void onBackPressed() {
        System.out.println("hola");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}