package com.example.happybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.happybar.DAO.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AjustesActivity extends AppCompatActivity {

    private BottomNavigationView bmenu;
    private FirebaseAuth auth;
    private String user;

    private FirebaseDatabase bbdd;
    private DatabaseReference reference;

    private TextInputEditText nombreInput, apellidosInput, correoInput;
    private Button btnConfirmar, btnCambiarPwd;
    private Usuario usu;
    private ArrayList<String> favs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid();


        nombreInput = findViewById(R.id.nombreInput);
        apellidosInput = findViewById(R.id.apellidosInput);
        correoInput = findViewById(R.id.correoInput);
        correoInput.setEnabled(false);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCambiarPwd = findViewById(R.id.btnContraseñaR);

        //INSTANCIAR LA BBDD
        bbdd = FirebaseDatabase.getInstance("https://happybar-tfg-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = bbdd.getReference().child("Usuario");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot hijo : snapshot.getChildren()) {

                    if (hijo.getKey().equalsIgnoreCase(user)) {
                        usu = hijo.getValue(Usuario.class);
                        nombreInput.setText(usu.getNombre());
                        apellidosInput.setText(usu.getApellido());
                        correoInput.setText(usu.getCorreo());
                        favs = usu.getFavoritos();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCambiarPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "No está funcional, se incluirá en siguientes versiones", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usu.setNombre(nombreInput.getText().toString());
                usu.setApellido(apellidosInput.getText().toString());
                reference.child(user).setValue(usu);
                Toast toast = Toast.makeText(getApplicationContext(), "Cambios guardados con éxito", Toast.LENGTH_SHORT);
                toast.show();
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
                        Intent intent = new Intent(getApplicationContext(), FavoritosActivity.class);
                        intent.putExtra("favoritos", favs);
                        startActivity(intent);
                        return true;

                    case R.id.Ajustes:


                        return true;

                    case R.id.Salir:

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        auth.signOut();
                        return true;
                }
                return false;
            }
        });
        bmenu.setSelectedItemId(R.id.Ajustes);

    }
}