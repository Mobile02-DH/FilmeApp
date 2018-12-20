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
import com.digitalhouse.whatchapp.model.Series;
import com.digitalhouse.whatchapp.view.DetailActivity;
import com.digitalhouse.whatchapp.view.ListaDeAssistidos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Series> seriesList;

    public SeriesAdapter(Context mContext, List<Series> seriesList){
        this.mContext = mContext;
        this.seriesList = seriesList;
    }

    @Override
    public SeriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);

        return new SeriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SeriesAdapter.MyViewHolder viewHolder, int i) {

        Series series = seriesList.get(i);
        viewHolder.bind(series);
    }

    @Override
    public int getItemCount(){
        return seriesList.size();
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

        public void bind(final Series series){

            Picasso.get().load(series.getPosterPath())
                    .placeholder(R.drawable.load)
                    .into(thumbnail);


            title.setText(series.getName());
            String vote = Double.toString(series.getVoteAverage());
            userrating.setText(vote);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Series clickedDataItem = seriesList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title", seriesList.get(pos).getName());
                        intent.putExtra("poster_path", seriesList.get(pos).getPosterPath());
                        intent.putExtra("overview", seriesList.get(pos).getOverview());
                        intent.putExtra("vote_average", Double.toString(seriesList.get(pos).getVoteAverage()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "Você clicou em " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
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

                        mDatabase.child(mAuth.getUid()).push().setValue(series);
                    } else {
                        Toast.makeText(mContext, "Faça Login para Adicionar aos Favoritos", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    public void setSeries(List<Series> series) {
        //verificar se o movies já tem informação
        if (series.size() == 0) {
            this.seriesList = series;
        } else {
            this.seriesList.addAll(series);
            notifyDataSetChanged();
        }
    }
}
