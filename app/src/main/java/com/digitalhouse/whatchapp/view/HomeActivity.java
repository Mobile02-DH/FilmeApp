package com.digitalhouse.whatchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.digitalhouse.whatchapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new FilmesFragment());
    }

    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
        // [END check_current_user]
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_filmes) {
            replaceFragment(new FilmesFragment());
        } else if (id == R.id.item_filme_categoria) {
            startActivity(new Intent(HomeActivity.this, CategoriasActivity.class));

        } else if (id == R.id.item_series) {
            startActivity(new Intent(HomeActivity.this, SeriesActivity.class));

        }else if (id == R.id.item_favoritos) {
            verificarFavoritosEstarLogado();

        }else if (id == R.id.item_logar) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));

        } else if (id == R.id.item_deslogar){
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut() {
        if (user != null) {
            // [START auth_sign_out]
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Deslogado com sucesso", Toast.LENGTH_LONG).show();
            // [END auth_sign_out]
        } else {
            Toast.makeText(this, "Você não está logado", Toast.LENGTH_LONG).show();
        }
    }

    public void verificarFavoritosEstarLogado() {

        if (user != null) {
            startActivity(new Intent(HomeActivity.this, FavoritosActivity.class));
            } else {
            Toast.makeText(this, "Faça Login para Acessar os Favoritos", Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }


    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
