package com.digitalhouse.whatchapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAssistidos extends RecyclerView.Adapter<AdapterAssistidos.ViewHolder>{

   private List<Movie> listAssistidos;

    public AdapterAssistidos(List<Movie> listAssistidos) {
        this.listAssistidos = listAssistidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new AdapterAssistidos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(listAssistidos.get(position).getOriginalTitle());
        String vote = Double.toString(listAssistidos.get(position).getVoteAverage());
        holder.userrating.setText(vote);

        Picasso.get().load(listAssistidos.get(position).getPosterPath())
                .placeholder(R.drawable.load)
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return listAssistidos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView userrating;
        public ImageView thumbnail;
        public ImageView imageAssistido;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            userrating = itemView.findViewById(R.id.userrating);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            imageAssistido = itemView.findViewById(R.id.imageAssistidos);
        }
    }

    public  void update(List<Movie> list){
        this.listAssistidos = list;

        notifyDataSetChanged();
    }
}
