package com.digitalhouse.whatchapp.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.AdapterAssistidos;
import com.digitalhouse.whatchapp.model.Movie;
import com.digitalhouse.whatchapp.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class FavoritosActivity extends AppCompatActivity {

    private static final String TAG = "PostDetailActivity";

    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    //private CommentAdapter mAdapter;

    private TextView mAuthorView;
    private TextView mTitleView;
    private TextView mBodyView;
    private TextView mCommentField;
    private Button mCommentButton;
    private RecyclerView mCommentsRecycler;


    RecyclerView recyclerFavorito;
    List<Movie> listaassistidos = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference().child(firebaseAuth.getCurrentUser().getUid());

        recyclerFavorito = findViewById(R.id.recyclerAssistidosFavoritos);

        final AdapterAssistidos adapterAssistidos = new AdapterAssistidos(new ArrayList<Movie>());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerFavorito.setLayoutManager(linearLayoutManager);
        recyclerFavorito.setHasFixedSize(true);
        recyclerFavorito.setAdapter(adapterAssistidos);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Movie movie = itemSnapshot.getValue(Movie.class);
                    //movie.setChave(itemSnapshot.getKey());
                    listaassistidos.add(movie);
                }
                adapterAssistidos.update(listaassistidos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("DATABASE", "loadPost:onCancelled", databaseError.toException());
            }
        });

        /*recyclerFavorito.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext()
                , recyclerFavorito, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Article article = articles.get(position);
                Intent intent = new Intent(getApplicationContext(),DetalheNewsActivity.class);
                intent.putExtra("url",article.getUrl());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

    }*/
    }
}


