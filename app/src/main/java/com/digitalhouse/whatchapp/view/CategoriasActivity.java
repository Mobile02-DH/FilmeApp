package com.digitalhouse.whatchapp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.CategoriasAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriasActivity extends AppCompatActivity {

    private CategoriasAdapter pagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_categorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pagerAdapter = new CategoriasAdapter(getSupportFragmentManager(), getFragmentsCategorias());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    private List<Fragment> getFragmentsCategorias() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(CategoriaFragment.newInstance("now_playing"));
        fragments.add(CategoriaFragment.newInstance("upcoming"));
        fragments.add(CategoriaFragment.newInstance("top_rated"));

        return fragments;
    }
}
