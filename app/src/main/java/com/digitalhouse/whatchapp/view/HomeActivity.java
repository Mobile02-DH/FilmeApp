package com.digitalhouse.whatchapp.view;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.digitalhouse.whatchapp.BuildConfig;
import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.MoviesAdapter;
import com.digitalhouse.whatchapp.api.Service;
import com.digitalhouse.whatchapp.model.Movie;
import com.digitalhouse.whatchapp.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digitalhouse.whatchapp.api.Client.getClient;

public class HomeActivity extends AppCompatActivity {
    int page = 1;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = MoviesAdapter.class.getName();
    Service apiService = getClient().create(Service.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        swipeContainer = findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(HomeActivity.this, "Atualizando Filmes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Buscando Filmes...");
        pd.setCancelable(false);
        pd.show();

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new MoviesAdapter(this, movieList);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                verifyIsLast(recyclerView);

            }
        });
    }

    private void verifyIsLast(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;

        if (totalItemCount > 0 && endHasBeenReached && page <= 10) {
            //you have reached to the bottom of your recycler view
            page++;
            buscarFilmes();
        }
    }


    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }

            buscarFilmes();


        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void buscarFilmes() {

        Call<MoviesResponse> call = apiService.getMovies("popular", BuildConfig.THE_MOVIE_DB_API_TOKEN, "pt-BR", page);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();

                if (swipeContainer.isRefreshing()) {
                    swipeContainer.setRefreshing(false);
                }
                pd.dismiss();

                adapter.setMovies(movies);
            }


            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(HomeActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}