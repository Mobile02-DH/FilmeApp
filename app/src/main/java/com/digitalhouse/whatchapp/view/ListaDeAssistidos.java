package com.digitalhouse.whatchapp.view;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.AdapterAssistidos;
import com.digitalhouse.whatchapp.model.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListaDeAssistidos extends AppCompatActivity {

    RecyclerView recyclerAssistidos;
    FirebaseAuth atenticacao;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_assistidos);


        recyclerAssistidos = findViewById(R.id.recyclerAssistidos);

        AdapterAssistidos assistidos = new AdapterAssistidos(new ArrayList<Movie>());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerAssistidos.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerAssistidos.setLayoutManager(new LinearLayoutManager(this));
        }

        recyclerAssistidos.setItemAnimator(new DefaultItemAnimator());
        recyclerAssistidos.setAdapter(assistidos);

        Movie movie = getIntent().getParcelableExtra("MOVIE");
        List<Movie> list = new ArrayList<>();
        list.add(movie);
        assistidos.update(list);

    }
}
