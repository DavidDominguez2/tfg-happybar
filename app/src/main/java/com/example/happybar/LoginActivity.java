package com.example.happybar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    private Button btnLogin, btnGoogle;
    private TextInputEditText inputNombre;
    private TextInputEditText inputPass;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //LOGIN SENCILLO
        auth = FirebaseAuth.getInstance();

        //LOGIN CON GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        inputNombre = findViewById(R.id.nombreInput);
        inputPass = findViewById(R.id.pwdInput);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = inputNombre.getText().toString();
                String pass = inputPass.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Snackbar.make(view, "Complete todos los campos por favor", Snackbar.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Snackbar.make(view, "Inicio de sesión correcto", Snackbar.LENGTH_SHORT).show();

                            //ENVIO A LA PANTALLA DE INICIO (MAPA)
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            startActivity(intent);
                        }else{
                            Snackbar.make(view, "Inicio de sesión incorrecto, email o contraseña incorrecta", Snackbar.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnGoogle = findViewById(R.id.btnLoginGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUIGoogle(account);

        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);

       /*
         if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, FavoritosActivity.class));
         }
       */
    }

    private void updateUI(FirebaseUser currentUser) {
        System.out.println("CURRENT USER: " + currentUser.getUid());

    }

    private void updateUIGoogle(GoogleSignInAccount currentUser) {
        System.out.println("CURRENT USER GOOGLE: " + currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            // Signed in successfully, show authenticated UI.
            updateUIGoogle(account);
            
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.err.println(e.getStatus());
            updateUI(null);
        }
    }
}
