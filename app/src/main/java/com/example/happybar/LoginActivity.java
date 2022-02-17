package com.example.happybar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.example.happybar.databinding.ActivityIndexMapsBinding;
import com.example.happybar.databinding.ActivityLoginBinding;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;

    private Button btnLogin;
    private TextInputEditText inputNombre;
    private TextInputEditText inputPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);

        //Button boton = findViewById(R.id.btnLogin).setBackground(R.drawable.bordes_input);
    }
}
