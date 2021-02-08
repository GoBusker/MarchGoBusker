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

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busker_feed);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new BuskerHomeFragment()).commit();

    }

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