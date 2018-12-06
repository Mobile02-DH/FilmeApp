package com.digitalhouse.whatchapp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.digitalhouse.whatchapp.R;
import com.digitalhouse.whatchapp.adapter.CategoriasageAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriasActivity extends AppCompatActivity {

    private CategoriasageAdapter pagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pagerAdapter = new CategoriasageAdapter(getSupportFragmentManager(), getFragmentsCategorias());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    private List<Fragment> getFragmentsCategorias() {
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(CategoriaFragment.newInstance("top_rated"));
        fragments.add(CategoriaFragment.newInstance("top_rated"));
        fragments.add(CategoriaFragment.newInstance("top_rated"));
        fragments.add(CategoriaFragment.newInstance("popular"));

        return fragments;
    }
}
