package com.example.teaknowledge;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.teaknowledge.utils.ImageLoaderUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.example.teaknowledge.utils.AppUtils;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private BottomNavigationView bottomNavView;
    private NavigationView drawerNavView;
    private NavController navController;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();
        AppUtils.initDatabase(context);
        ImageLoaderUtil.initImageLoader(context);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavView = findViewById(R.id.buttom_nav_view);
        drawerNavView = findViewById(R.id.drawer_nav_view);
        drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_headlines, R.id.navigation_knowledge, R.id.navigation_consulting,
                R.id.navigation_business, R.id.navigation_chart)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);
        NavigationUI.setupWithNavController(drawerNavView, navController);

        visibilityNavElements(navController);
    }

    private void visibilityNavElements(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();
            if (id == R.id.nav_search) {
                hideBottomNavigation();
            } else if (id == R.id.nav_favorite) {
                hideBottomNavigation();
            } else if (id == R.id.nav_version) {
                hideBottomNavigation();
            } else if (id == R.id.nav_feedback) {
                hideBottomNavigation();
            } else if (id == R.id.nav_history) {
                hideBottomNavigation();
            } else {
                showBottomNavigation();
            }
        });
    }

    private void showBottomNavigation() {
        bottomNavView.setVisibility(View.VISIBLE);
    }

    private void hideBottomNavigation() {
        bottomNavView.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
