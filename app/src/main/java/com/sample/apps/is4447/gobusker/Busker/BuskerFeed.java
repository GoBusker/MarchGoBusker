package com.sample.apps.is4447.gobusker.Busker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sample.apps.is4447.gobusker.Fragment.BuskerHomeFragment;
import com.sample.apps.is4447.gobusker.Fragment.BuskerNotificationFragment;
import com.sample.apps.is4447.gobusker.Fragment.BuskerProfileFragment;
import com.sample.apps.is4447.gobusker.Fragment.BuskerSearchFragment;
import com.sample.apps.is4447.gobusker.R;

public class BuskerFeed extends AppCompatActivity {
  //  I referenced this Youtube video for the bottom navigation
 //   https://www.youtube.com/watch?v=Kovj7Xyy6_g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=3&ab_channel=KODDev

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_feed);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // I used this Youtube video as a reference for displaying comments under posts
        //    https://www.youtube.com/watch?v=V2lai8cJIkk&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=10&ab_channel=KODDev
        Bundle intent = getIntent().getExtras();
        if (intent != null){
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BuskerProfileFragment()).commit();
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BuskerHomeFragment()).commit();

        }
        }

// I referenced this Youtube video for the bottom navigation
//https://www.youtube.com/watch?v=Kovj7Xyy6_g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=3&ab_channel=KODDev
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new BuskerHomeFragment();
                            break;
                        case R.id.nav_search:

                            selectedFragment = new BuskerSearchFragment();
                            break;
                        case R.id.nav_add:

                            selectedFragment = null;
                            startActivity(new Intent(BuskerFeed.this, BuskerPost.class));
                            break;
                        case R.id.nav_favourite:
                            selectedFragment = new BuskerNotificationFragment();
                            break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedFragment = new BuskerProfileFragment();
                            break;
                    }
                    if (selectedFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    return true;
                }
            };

}