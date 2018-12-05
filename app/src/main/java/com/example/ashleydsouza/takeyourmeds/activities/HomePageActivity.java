package com.example.ashleydsouza.takeyourmeds.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ashleydsouza.takeyourmeds.R;
import com.example.ashleydsouza.takeyourmeds.fragments.AddPrescription;
import com.example.ashleydsouza.takeyourmeds.fragments.Settings;
import com.example.ashleydsouza.takeyourmeds.fragments.ShowCalender;
import com.example.ashleydsouza.takeyourmeds.fragments.UserHome;

public class HomePageActivity extends AppCompatActivity
        implements  AddPrescription.OnFragmentInteractionListener,
                    Settings.OnFragmentInteractionListener,
                    ShowCalender.OnFragmentInteractionListener,
                    UserHome.OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        Intent intent = getIntent();
//        String email = intent.getStringExtra("email");

        //set Home as default fragment
        Bundle bundle = new Bundle();
        bundle.putString("email", "ash@test.com");      //change this to email from intent of MainActivity
        UserHome homeFragment = new UserHome();
        homeFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, homeFragment).commit();

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //email for the Navigation Tab under the Icon and App Name
        navigationView = findViewById(R.id.navigation_drawer);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.user_email);
        navUsername.setText("ash@test.com");  //change this to email from intent of MainActivity


        //Animation for the Hamburger icon to make sure it is clicked during navigation
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        //setup the drawer view
        setupDrawerContent(navigationView);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * This is called when Navigation Drawer is selected
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    /**
     * Sets the right fragment based on the user selection
     * @param menuItem
     */
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.home_page:
                fragmentClass = UserHome.class;
                break;
            case R.id.add_prescription:
                fragmentClass = AddPrescription.class;
                break;
            case R.id.show_calender:
                fragmentClass = ShowCalender.class;
                break;
            case R.id.settings:
                fragmentClass = Settings.class;
                break;
            default:
                fragmentClass = UserHome.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            /**
             * Highlight the selected item has been done by NavigationView,
             * Set Action Bar title
             * Close navigation drawer
             **/
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            mDrawerLayout.closeDrawers();
        }
    }

    public void onFragmentInteraction(Uri uri) {
        //Do nothing
    }
}
