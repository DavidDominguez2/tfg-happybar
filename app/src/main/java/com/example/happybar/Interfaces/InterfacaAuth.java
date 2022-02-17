package com.example.happybar.Interfaces;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public interface InterfacaAuth {

    void logIn(FirebaseAuth auth, String email, String password);

    void register(String email, String password);


}
