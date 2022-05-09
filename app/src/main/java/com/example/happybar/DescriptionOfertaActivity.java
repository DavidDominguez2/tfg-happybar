package com.example.happybar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.happybar.databinding.ActivityDescriptionOfertaBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class DescriptionOfertaActivity extends AppCompatActivity implements OnMapReadyCallback {
    //OBJETOS MAPA
    private GoogleMap mMap;
    private ActivityDescriptionOfertaBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView title, price, desciption;
    private Button onGo, onFav;

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

        onGo = findViewById(R.id.buttonOferta);
        onGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        onFav = findViewById(R.id.buttonFavOferta);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}