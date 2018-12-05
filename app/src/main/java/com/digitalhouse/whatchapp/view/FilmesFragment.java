package com.digitalhouse.whatchapp.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digitalhouse.whatchapp.BuildConfig;
import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.MoviesAdapter;
import com.digitalhouse.whatchapp.api.Client;
import com.digitalhouse.whatchapp.api.Service;
import com.digitalhouse.whatchapp.model.Movie;
import com.digitalhouse.whatchapp.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmesFragment extends Fragment {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = MoviesAdapter.class.getName();


    public FilmesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filmes, container, false);
        swipeContainer = view.findViewById(R.id.main_content);
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new MoviesAdapter(getContext(), movieList);

        initViews();

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(getContext(), "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initViews() {
        pd = new ProgressDialog(getContext());
        pd.setMessage("Fetching movies...");
        pd.setCancelable(false);
        pd.show();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getContext().getApplicationContext(), "Please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getContext().getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                return true;
            case R.id.menu_entrar:
                showLogin();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showLogin() { startActivity(new Intent(getContext(), LoginActivity.class));}

}
