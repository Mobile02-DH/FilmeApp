package com.digitalhouse.whatchapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.model.Movie;
import com.digitalhouse.whatchapp.view.DetailActivity;
import com.digitalhouse.whatchapp.view.ListaDeAssistidos;
import com.digitalhouse.whatchapp.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> movieList = new ArrayList<>();

    public MoviesAdapter(Context mContext, List<Movie> movieList){
        this.mContext = mContext;
        this.movieList.addAll(movieList);
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i) {

        Movie movie = movieList.get(i);
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount(){
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView userrating;
        public ImageView thumbnail;
        public ImageView imageAssistido;

        public MyViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            userrating = view.findViewById(R.id.userrating);
            thumbnail = view.findViewById(R.id.thumbnail);
            imageAssistido = view.findViewById(R.id.imageAssistidos);
        }

        public void bind(final Movie movie){

            Picasso.get().load(movie.getPosterPath())
                    .placeholder(R.drawable.load)
                    .into(thumbnail);

            title.setText(movie.getOriginalTitle());
            String vote = Double.toString(movie.getVoteAverage());
            userrating.setText(vote);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title", movieList.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                        intent.putExtra("overview", movieList.get(pos).getOverviews());
                        intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
                        intent.putExtra("release_date", movieList.get(pos).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "Você clicou em " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            imageAssistido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        Toast.makeText(mContext, "Adicionado aos Favoritos", Toast.LENGTH_LONG).show();

                        mDatabase.child(mAuth.getUid()).push().setValue(movie);
                    } else {
                        Toast.makeText(mContext, "Faça Login para Adicionar aos Favoritos", Toast.LENGTH_LONG).show();
                    }
                }
            });




        }
    }
    public void setMovies(List<Movie> movies) {
        //verificar se o movies já tem informação
        if (movies.size() == 0) {
            this.movieList = movies;
        } else {
            this.movieList.addAll(movies);
            notifyDataSetChanged();
        }
    }
}
