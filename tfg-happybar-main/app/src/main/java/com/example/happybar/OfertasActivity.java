package com.example.happybar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happybar.DAO.Oferta;

import java.util.ArrayList;

public class OfertasActivity extends AppCompatActivity {

    private ArrayList<Oferta> ofertas;
    private RecyclerView rv;
    private AdaptadorOfertas adapter;
    private TextView title;

    private AdaptadorOfertas.listenersInterfaz goDescription = new AdaptadorOfertas.listenersInterfaz() {
        @Override
        public void clickEnElementoCard(int pos) {
            Intent intent = new Intent(getApplicationContext(), DescriptionOfertaActivity.class);
            intent.putExtra("oferta", getIntent().getStringExtra("keyBar"));
            intent.putExtra("lat", getIntent().getDoubleExtra("latitud", 0));
            intent.putExtra("long", getIntent().getDoubleExtra("longitud", 0));
            intent.putExtra("titulo", ofertas.get(pos).getNombre());
            intent.putExtra("precio", ofertas.get(pos).getPrecio());
            intent.putExtra("descripcion", ofertas.get(pos).getDescripcion());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        title = findViewById(R.id.ofertasTitle);
        title.setText(getIntent().getStringExtra("nombreBar"));

        //ARRAY DE OFERTAS DEL BAR
        ofertas = (ArrayList<Oferta>) getIntent().getSerializableExtra("ofertas");

        adapter = new AdaptadorOfertas(ofertas, goDescription);
        rv = findViewById(R.id.rvOfertas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);


    }
}
