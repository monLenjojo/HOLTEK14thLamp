package com.tsc.holtek14th;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tsc.holtek14th.discoverFunction.DiscoverRecyclerFunction;

public class DiscoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView.setSelectedItemId(R.id.navigation_discover);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        RecyclerView recyclerView = findViewById(R.id.discoverRecycler);
        new DiscoverRecyclerFunction(this,recyclerView,user.getUid());
    }

    //---navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myLibrary:
                    Intent mainPage = new Intent(DiscoverActivity.this, MainActivity.class);
                    startActivity(mainPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    return true;
                case R.id.navigation_discover:
                    return true;
                case R.id.navigation_me:
                    Intent userInfoPage = new Intent(DiscoverActivity.this, UserInfoActivity.class);
                    startActivity(userInfoPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    return true;
                case R.id.navigation_more:
                    return true;
            }
            return false;
        }
    };
}
