/**

Create an application that has a celebrity database
        1. User can add a new celebrity by “AddNewCelebrity”
        a. Can view all the celebrities (ViewCelebrities)
        b. Can update a celebrity from the list (ViewCelebrity)
        c. Can remove a celebrity from the list (ImageButton)
        d. Can make a celebrity as favorite (ImageButton)
        e. Can view all the favorite celebrity (MyFavorites) activity
        f. Has a feature to write content to a file
        g. Has a feature to read contents from that file.
        2. Create a custom content provider with the celebrity SQL database. Then retrieve data from
            that content provider and populate the recyclerView
        3. Add the following features to the same app
        Use VectorDrawable to create an image using an xml
        Create a button behavior using StateListDrawable
        4. Use NavigationDrawer to navigate between the UI’s for this project
        5. The UI needs to be presentable. Please spend time making the UI your own. Experiment.

 */

package com.example.celebritydb_week2wknd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // DrawerLayout is a top level container that allows for interactive "drawer"
    // views to be pulled from either side of the window.
    private DrawerLayout drawer;

    private RecyclerView rvCelebs;

    public RecyclerViewAdapter recyclerViewAdapter;
    public LinearLayoutManager layoutManager;

    public static final String TAG = "TAG_MAIN_ACTIVITY";

    // an initial list of celebs to be passed to recycler view
    ArrayList<Celebrity> defaultCelebs = new ArrayList<>();

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a new toolbar and link to toolbar we made in main XML
        Toolbar toolbar = findViewById(R.id.toolbar);

        // make the toolbar available for display
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DatabaseHelper(this);

        rvCelebs = findViewById(R.id.rvCelebsRecyclerView);

        Log.d(TAG, "onCreate: ");

        populateCelebsList();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerViewAdapter = new RecyclerViewAdapter(dbHelper.queryForAllCelebrities());
        layoutManager = new LinearLayoutManager(this);
        rvCelebs.setLayoutManager(layoutManager);
        rvCelebs.setAdapter(recyclerViewAdapter);
    }

    // Define what happens when a menu item is clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_celebrity:
                Intent i = new Intent(this, AddCelebActivity.class);
                startActivityForResult(i, 0);
                break;
            // The following cases have not been implemented yet.
            case R.id.remove_celebrity:
                break;
            case R.id.update_celebrity:
                break;
            case R.id.make_favorite:
                break;
            case R.id.all_favorites:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;  // return true to indicate an item was selected
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode);

        // check if we have the correct result code and data exists
        if(resultCode == 0 && data!= null) {
            Bundle passedBundle = data.getExtras();
            Log.d(TAG, "onActivityResult: Inside first if" );

            if(passedBundle != null){
                Celebrity celeb = passedBundle.getParcelable("celeb");
                recyclerViewAdapter.addCelebs(celeb);
                /*
                for(Celebrity celebrity : dbHelper.queryForAllCelebrities()) {
                    Log.d(TAG, String.format(Locale.US, "%s %s %s ", celebrity.getName(), celebrity.getAge(),
                            celebrity.getProfession()));
                } */
            }
        }
    }

    // if our navigation drawer is open, pressing the back button will close it;
    // not exit our app
    @Override
    public void onBackPressed(){
        // first check if the drawer is open. if it is, close it.
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        // If the drawer is not opened, hitting back will close the entire app
        else {
            super.onBackPressed();
        }
    }

    public void onClick(View view) {
    }

    // create a list of celebs that will initially be added to our db
    private void populateCelebsList(){
        defaultCelebs.add(new Celebrity("Leonardo Dicaprio", "40", "Actor"));
        defaultCelebs.add(new Celebrity("Quentin Tarantino", "55", "Director"));

        for (int i = 0; i < defaultCelebs.size(); i++) {
            dbHelper.insertCelebrity(defaultCelebs.get(i));
        }
        dbHelper.close();

        /*
        for(Celebrity celebrity : dbHelper.queryForAllCelebrities()) {
            Log.d(TAG, String.format(Locale.US, "%s %s %s ", celebrity.getName(), celebrity.getAge(),
                    celebrity.getProfession()));
        } // end for */
    }
}
