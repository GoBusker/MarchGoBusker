package com.sample.apps.is4447.gobusker.Fan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sample.apps.is4447.gobusker.Busker.BuskerFeed;
import com.sample.apps.is4447.gobusker.Busker.BuskerPost;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerHomeFragment;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerNotificationFragment;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerProfileFragment;
import com.sample.apps.is4447.gobusker.BuskerFragments.BuskerSearchFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanHomeFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanNotificationFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanOwnProfileFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanProfileFragment;
import com.sample.apps.is4447.gobusker.FanFragments.FanSearchFragment;
import com.sample.apps.is4447.gobusker.R;

public class FanFeed extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_feed);

        bottomNavigationView = findViewById(R.id.fan_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fan,
                new FanHomeFragment()).commit();

    }

        // I referenced this Youtube video for the bottom navigation
//https://www.youtube.com/watch?v=Kovj7Xyy6_g&list=PLzLFqCABnRQduspfbu2empaaY9BoIGLDM&index=3&ab_channel=KODDev
        private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new FanHomeFragment();
                                break;
                            case R.id.nav_search_fan:

                                selectedFragment = new FanSearchFragment();
                                break;
                            case R.id.nav_favourite:
                                selectedFragment = new FanNotificationFragment();
                                break;
                            case R.id.nav_profile:
                                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                                editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                editor.apply();
                                selectedFragment = new FanOwnProfileFragment();
                                break;
                        }
                        if (selectedFragment != null){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_fan,
                                    selectedFragment).commit();
                        }
                        return true;
                    }
                };
    }
