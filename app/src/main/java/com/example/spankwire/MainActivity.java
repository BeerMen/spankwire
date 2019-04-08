package com.example.spankwire;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import com.example.spankwire.network.SearchNetwork;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PornoVideosFragment.OnFragmentInteractionListener,
        PornoStarFragment.OnPornoStarsInteractionListener,
        PreviouslyPornoStarsFragment.OnPreviouslyPornoStarsInteractionListener,
        SearchNetwork.OnSearchInteractionListener {

    private FrameLayout previouslyPornoStarsLayout;
    private FrameLayout pornoVideoLayout;
    private FrameLayout previouslyLayout;
    private FrameLayout categoriesLayout;
    private FrameLayout pornoStarLayout;
    private PornoVideosFragment pornoVideosFragment;
    private CategoriesFragment categoriesFragment;
    private PornoStarFragment pornoStarFragment;
    private PreviouslyPornoStarsFragment previouslyPornoStarsFragment;
    private SearchNetwork searchNetwork = new SearchNetwork();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setupFragments();
    }

    private void setupFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        pornoVideosFragment = new PornoVideosFragment();
        categoriesFragment = new CategoriesFragment();
        pornoStarFragment = new PornoStarFragment();
        previouslyPornoStarsFragment = new PreviouslyPornoStarsFragment();

        fragmentTransaction.add(R.id.previously_porno_stars_fragment,
                previouslyPornoStarsFragment,
                PreviouslyPornoStarsFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.porno_star_fragment, pornoStarFragment, PornoStarFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.categories_fragment, categoriesFragment, CategoriesFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.porno_videos_fragment, pornoVideosFragment, PornoVideosFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.previously_fragment, new PreviouslyFragment(), PreviouslyFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void initView() {
        previouslyPornoStarsLayout = findViewById(R.id.previously_porno_stars_fragment);
        pornoVideoLayout = findViewById(R.id.porno_videos_fragment);
        previouslyLayout = findViewById(R.id.previously_fragment);
        categoriesLayout = findViewById(R.id.categories_fragment);
        pornoStarLayout = findViewById(R.id.porno_star_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchNetwork.initListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_info_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (!s.isEmpty()) {
                        String result = s.replace(" ","_");
                        searchNetwork.setResult(result);
                    }
                    if (!searchView.isIconified()) {
                        searchView.setIconified(true);
                    }
                    if (searchItem != null) {
                        searchItem.collapseActionView();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void visibilityFragments(int idFragment) {
        if (idFragment == 1) {
            previouslyLayout.setVisibility(View.VISIBLE);
            pornoVideoLayout.setVisibility(View.GONE);
        } else if (idFragment == 2) {
            pornoVideoLayout.setVisibility(View.VISIBLE);
            previouslyLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                pornoVideosFragment.recyclerAdapter.clear();
                pornoVideosFragment.getListVideoPopularHome();
                pornoVideoLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.top_rated:
                pornoVideosFragment.recyclerAdapter.clear();
                pornoVideosFragment.getListVideoTopRated();
                pornoVideoLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.categories:
                pornoVideosFragment.recyclerAdapter.clear();
                categoriesFragment.getListCategories();
                pornoVideoLayout.setVisibility(View.GONE);
                previouslyLayout.setVisibility(View.GONE);
                pornoStarLayout.setVisibility(View.GONE);
                categoriesLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.porno_star:
                pornoStarFragment.getListPornoStars();
                pornoVideoLayout.setVisibility(View.GONE);
                previouslyLayout.setVisibility(View.GONE);
                categoriesLayout.setVisibility(View.GONE);
                pornoStarLayout.setVisibility(View.VISIBLE);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCategoriesInteraction(int id) {
        pornoVideosFragment.getListVideoCategories(id);
    }

    @Override
    public void onFragmentInteraction(int link) {
        PreviouslyFragment previouslyFragment = (PreviouslyFragment) getSupportFragmentManager().
                findFragmentByTag(PreviouslyFragment.class.getSimpleName());

        if (previouslyFragment != null) {
            previouslyFragment.getVideoUrl(link);
        }
    }

    @Override
    public void onFragmentInteraction(String ulr) {
        pornoVideosFragment.getListVideoCategories(Integer.parseInt(ulr.substring(ulr.length() - 4)));
    }

    @Override
    public void onPornoStarsInteraction(int id) {
        previouslyPornoStarsFragment.getInfoPornoStars(id);
        pornoStarLayout.setVisibility(View.GONE);
        pornoVideoLayout.setVisibility(View.GONE);
        previouslyLayout.setVisibility(View.GONE);
        categoriesLayout.setVisibility(View.GONE);
        previouslyPornoStarsLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPreviouslyPornoStarsInteraction(int id) {
        pornoVideosFragment.recyclerAdapter.clear();
        categoriesFragment.recyclerAdapter.clear();
        pornoStarFragment.recyclerAdapter.clear();
        pornoStarLayout.setVisibility(View.GONE);
        previouslyLayout.setVisibility(View.GONE);
        categoriesLayout.setVisibility(View.GONE);
        previouslyPornoStarsLayout.setVisibility(View.GONE);
        pornoVideoLayout.setVisibility(View.VISIBLE);
        pornoVideosFragment.getListPornoStars(id);
    }

    @Override
    public void onSearchInteraction(VideoItems result) {
        if (result != null) {
            pornoVideosFragment.setVideoRecyclerView(result);
        }
    }
}
