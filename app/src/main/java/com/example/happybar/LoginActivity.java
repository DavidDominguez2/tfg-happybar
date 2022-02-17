package com.example.happybar;

import android.app.Activity;
import android.os.Bundle;

import com.example.happybar.databinding.ActivityIndexMapsBinding;
import com.example.happybar.databinding.ActivityLoginBinding;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
