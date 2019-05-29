package com.tsc.holtek14th;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tsc.holtek14th.FIrebaseAddData.AddTestData;

public class UserInfoActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myLibrary:
//                    mTextMessage.setText(R.string.title_my_library);
//                    Log.d(TAG, "onNavigationItemSelected: "+ new AddTestData());
                    Intent mainPage = new Intent(UserInfoActivity.this, MainActivity.class);
                    startActivity(mainPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    return true;
                case R.id.navigation_discover:
//                    mTextMessage.setText(R.string.title_discover);
                    return true;
                case R.id.navigation_me:
//                    mTextMessage.setText(R.string.title_me);
                    return true;
                case R.id.navigation_more:
//                    mTextMessage.setText(R.string.title_more);
                    return true;
            }
            return false;
        }
    };
    private String TAG = UserInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_me);
    }

    public void logout(View view){
        Log.d(TAG, "auth is logout");
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
