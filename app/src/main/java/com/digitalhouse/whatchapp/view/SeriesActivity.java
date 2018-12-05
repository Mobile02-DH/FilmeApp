package com.digitalhouse.whatchapp.view;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.digitalhouse.whatchapp.BuildConfig;
import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.SeriesAdapter;
import com.digitalhouse.whatchapp.api.Client;
import com.digitalhouse.whatchapp.api.Service;
import com.digitalhouse.whatchapp.model.Series;
import com.digitalhouse.whatchapp.model.SeriesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int pag =1;
    private RecyclerView recyclerView;
    private SeriesAdapter adapter;
    private List<Series> seriesList = new ArrayList<>();
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainerSeries;
    public static final String LOG_TAG = SeriesAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        swipeContainerSeries = findViewById(R.id.containt_series);
        swipeContainerSeries.setColorSchemeResources(android.R.color.holo_orange_light);
        swipeContainerSeries.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(SeriesActivity.this, "Atualizando Series", Toast.LENGTH_SHORT).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViews() {
            pd = new ProgressDialog(this);
            pd.setMessage("Buscando Series...");
            pd.setCancelable(false);
            pd.show();

            recyclerView = findViewById(R.id.recycler_view_series);


            adapter = new SeriesAdapter(this, seriesList);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        loadJSON();

    }
    private void loadJSON() {

        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please obtain API Key firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;
            }
            Client client = new Client();
            Service apiService =
                    client.getClient().create(Service.class);
            Call<SeriesResponse> call = apiService.getSeries("popular", BuildConfig.THE_MOVIE_DB_API_TOKEN, "pt-BR", pag );
            call.enqueue(new Callback<SeriesResponse>() {
                @Override
                public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {
                    List<Series> series = response.body().getResults();
                    recyclerView.setAdapter(new SeriesAdapter(getApplicationContext(), series));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainerSeries.isRefreshing()) {
                        swipeContainerSeries.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<SeriesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(SeriesActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.series, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
