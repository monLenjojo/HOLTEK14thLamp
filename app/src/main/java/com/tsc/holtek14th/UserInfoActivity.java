package com.tsc.holtek14th;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class UserInfoActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private String TAG = UserInfoActivity.class.getSimpleName();
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ImageView userPhoto = findViewById(R.id.imgUserPhoto);
        TextView txName = findViewById(R.id.txName);
        TextView txEmail = findViewById(R.id.txEmail);

        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        String photoStr = sharedPreferences.getString("PHOTO", null);
        Log.d(TAG, "onCreatePhotoUri: " + photoStr);
        if (photoStr != null) {
            Uri photoUri = Uri.parse(photoStr);
            Picasso.get().load(photoUri).resize(300,300).into(userPhoto);
        }
        txName.setText(sharedPreferences.getString("NAME","null"));
        txEmail.setText(sharedPreferences.getString("EMAIL","null"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        navView.setSelectedItemId(R.id.navigation_me);
    }

    public void logout(View view){
        Log.d(TAG, "auth is logout");
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    //---navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myLibrary:
                    Intent mainPage = new Intent(UserInfoActivity.this, MainActivity.class);
                    startActivity(mainPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    UserInfoActivity.this.finish();
                    return true;
                case R.id.navigation_discover:
                    Intent discoverPage = new Intent(UserInfoActivity.this,DiscoverActivity.class);
                    startActivity(discoverPage);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
                    UserInfoActivity.this.finish();
                    return true;
                case R.id.navigation_me:
                    return true;
                case R.id.navigation_more:
                    Intent morePage = new Intent(UserInfoActivity.this, MoreActivity.class);
                    startActivity(morePage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    return true;
            }
            return false;
        }
    };
}
