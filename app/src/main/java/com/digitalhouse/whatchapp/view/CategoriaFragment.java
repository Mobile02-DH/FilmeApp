package com.digitalhouse.whatchapp.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.digitalhouse.whatchapp.api.Service;
import com.digitalhouse.whatchapp.model.Movie;
import com.digitalhouse.whatchapp.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digitalhouse.whatchapp.api.Client.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaFragment extends Fragment {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = MoviesAdapter.class.getName();
    int page = 1;
    Service apiService = getClient().create(Service.class);
    String categoria;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    public static CategoriaFragment newInstance(String categoria) {

        Bundle args = new Bundle();

        CategoriaFragment fragment = new CategoriaFragment();
        args.putString("CATEGORIA", categoria);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoria = getArguments().getString("CATEGORIA");
        }
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

        buscarFilmes();

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

    public void showLogin() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    private void buscarFilmes() {

        Call<MoviesResponse> call = apiService.getMovies(categoria, BuildConfig.THE_MOVIE_DB_API_TOKEN, "pt-BR", page);
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
                Toast.makeText(getContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

