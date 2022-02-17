package com.example.happybar.Funcionalidades;

import android.content.Intent;
import android.view.View;

import com.example.happybar.Interfaces.InterfacaAuth;
import com.example.happybar.MapsActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class AuthFun implements InterfacaAuth {


    @Override
    public void logIn(FirebaseAuth auth, String email, String password) {
    }

    @Override
    public void register(String email, String password) {

    }
}
