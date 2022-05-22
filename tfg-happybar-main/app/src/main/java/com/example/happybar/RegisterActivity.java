package com.example.happybar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happybar.DAO.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private TextView avisoLegal, politicaPrivacidad, condicionesGenerales;
    private TextInputEditText inputNombre, inputApellidos, inputCorreo, inputPwd;
    private Button btnRegistrar;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> favoritos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNombre = findViewById(R.id.nombreInput);
        inputApellidos = findViewById(R.id.apellidosInput);
        inputCorreo = findViewById(R.id.correoInput);
        inputPwd = findViewById(R.id.pwdInputR);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();




        auth = FirebaseAuth.getInstance();


        btnRegistrar = findViewById(R.id.Confirmar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String nombre = inputNombre.getText().toString();
                    String apellidos = inputApellidos.getText().toString();
                    String correo = inputCorreo.getText().toString();
                    String pwd = inputPwd.getText().toString();
                //mDatabaseReference.setValue(user);

                if(nombre.equalsIgnoreCase("") || apellidos.equalsIgnoreCase("") || correo.equalsIgnoreCase("") || pwd.equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Algún campo está vacío. Revisa tus datos", Toast.LENGTH_LONG);
                    toast.show();


                } else{



                auth.createUserWithEmailAndPassword(correo, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Te has registrado correctamente", Toast.LENGTH_LONG);
                            toast.show();
                            FirebaseUser userF = auth.getCurrentUser();
                            favoritos.add("bar01");
                            Usuario user = new Usuario(inputNombre.getText().toString(), inputApellidos.getText().toString(), inputCorreo.getText().toString(),favoritos);
                            mDatabase.getReference().child("Usuario").child(userF.getUid()).setValue(user);


                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);

                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Algún dato no es correcto.", Toast.LENGTH_LONG);
                            toast.show();
                        }


                    }



                }


                );

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
}