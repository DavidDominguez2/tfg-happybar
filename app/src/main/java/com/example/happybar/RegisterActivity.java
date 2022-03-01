package com.example.happybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class RegisterActivity extends AppCompatActivity {

    private TextView avisoLegal, politicaPrivacidad, condicionesGenerales;
    private TextInputEditText inputNombre, inputApellidos, inputCorreo, inputPwd;
    private Button btnRegistrar;
    private FirebaseAuth auth;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNombre = findViewById(R.id.nombreInput);

        inputApellidos = findViewById(R.id.apellidosInput);
        inputCorreo = findViewById(R.id.correoInput);
        inputPwd = findViewById(R.id.pwdInput);


        auth = FirebaseAuth.getInstance();


        btnRegistrar = findViewById(R.id.Confirmar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.createUserWithEmailAndPassword(inputCorreo.toString(), inputPwd.toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);

                        }

                    }

                    public void updateUI(FirebaseUser user) {

                        if (user != null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "You Signed In successfully", Toast.LENGTH_LONG);
                            toast.show();

                        } else {
                            Toast.makeText(getApplicationContext(), "You Didnt signed in", Toast.LENGTH_LONG).show();
                        }

                    }
                });


//AVISO LEGAL, POLITICAS y CONDICIONES
                avisoLegal = findViewById(R.id.avisoLegal);
                politicaPrivacidad = findViewById(R.id.politicaPrivacidad);
                condicionesGenerales = findViewById(R.id.condicionesGenerales);

                avisoLegal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), CreditosActivity.class));
                    }
                });
                politicaPrivacidad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), CreditosActivity.class));
                    }
                });
                condicionesGenerales.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), CreditosActivity.class));
                    }
                });


            }


        });

    }}