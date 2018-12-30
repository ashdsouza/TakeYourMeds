package com.example.ashleydsouza.takeyourmeds.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.MedicineViewModel;
import com.example.ashleydsouza.takeyourmeds.utils.CalendarEvent;
import com.example.ashleydsouza.takeyourmeds.utils.CalendarEventManager;
import com.example.ashleydsouza.takeyourmeds.utils.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomePageActivity extends AppCompatActivity
        implements  AddPrescription.OnFragmentInteractionListener,
                    Settings.OnFragmentInteractionListener,
                    ShowCalender.OnFragmentInteractionListener,
                    UserHome.OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView navigationView;
    private Session session;
    private CalendarEventManager instance;
    private int userId;
    private List<MedicineInformation> newMeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        session = new Session(HomePageActivity.this);
        String email = session.getEmail();
        String name = session.getName();
        userId = session.getUserId();

        //set Home as default fragment
        UserHome homeFragment = new UserHome();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, homeFragment).addToBackStack("UserHome").commit();

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //email for the Navigation Tab under the Icon and App Name
        navigationView = findViewById(R.id.navigation_drawer);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserEmail = headerView.findViewById(R.id.user_email);
        navUserEmail.setText(email);
        TextView navUsername = headerView.findViewById(R.id.user_name);
        navUsername.setText(name);


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
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Sets the right fragment based on the user selection
     * @param menuItem
     */
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        String fragmentName = "";

        //check if logout is selected
        if(menuItem.getItemId() == R.id.logout) logoutUser();

        else {
            switch (menuItem.getItemId()) {
                case R.id.home_page:
                    fragmentClass = UserHome.class;
                    fragmentName = "UserHome";
                    break;
                case R.id.add_prescription:
                    fragmentClass = AddPrescription.class;
                    fragmentName = "AddPrescription";
                    break;
                case R.id.show_calender:
                    fragmentClass = ShowCalender.class;
                    fragmentName = "ShowCalender";
                    break;
                case R.id.settings:
                    fragmentClass = Settings.class;
                    fragmentName = "Settings";
                    break;
                default:
                    fragmentClass = UserHome.class;
                    fragmentName = "UserHome";
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fragment != null) {
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(fragmentName).commit();

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
    }

    public void logoutUser() {
        if(session.isUserLoggedIn()) {
            session.logoutUser();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantedResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults);
    }

    public void onFragmentInteraction(Uri uri) {
        //Do nothing
    }

    public void setEventDailyForAMonth(int userId, List<MedicineInformation> meds) {
        instance = CalendarEventManager.getInstance();

        for(int i=0; i< meds.size(); i++) {
            MedicineInformation med = meds.get(i);
            if(med.getTime().equals("Daily") || med.getTime().equals("Hourly")) {
                String eventString = "Please take " + med.getAmount() + " of " + med.getName() + " today";
                Calendar now = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.MONTH, 1);
                for (Date dt = now.getTime(); !now.after(end);
                     now.add(Calendar.DATE, 1), dt = now.getTime()) {
                    CalendarEvent event = new CalendarEvent(userId, med.getMedId(), Color.GREEN, dt.getTime(), eventString);
                    instance.saveEvents(event);
                }
            }
        }
    }

    @Override
    public void saveToCalendar(List<MedicineInformation> meds) {
        setEventDailyForAMonth(userId, meds);
    }
}
