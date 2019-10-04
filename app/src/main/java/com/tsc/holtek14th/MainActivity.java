package com.tsc.holtek14th;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.tsc.holtek14th.dialog.LoadingDialog;
import com.tsc.holtek14th.firebaseAddData.AddTestData;
import com.tsc.holtek14th.myLibraryFunction.MyLibraryRecyclerFunction;

public class MainActivity extends AppCompatActivity {
    public final static int LOGIN_REQUEST = 101;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static FirebaseAuth auth;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginPage = new Intent(MainActivity.this ,LoginActivity.class);
                    startActivityForResult(loginPage, LOGIN_REQUEST);
                }else{
                    RecyclerView recyclerView = findViewById(R.id.myLibraryRecycler);
                    new MyLibraryRecyclerFunction(MainActivity.this, recyclerView, auth.getUid());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        navView.setSelectedItemId(R.id.navigation_myLibrary);
//        new AddTestData();
    }

    //-----------------use intent and get result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case LOGIN_REQUEST:
                if (resultCode!=RESULT_OK) finish();
                break;
        }
    }

    //---------------BottomNavigationView set
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myLibrary:
//                    mTextMessage.setText(R.string.title_my_library);
//                    Log.d("TAG", "onNavigationItemSelected: "+ new AddTestData());
                    return true;
                case R.id.navigation_discover:
//                    mTextMessage.setText(R.string.title_discover);
                    Intent discoverPage = new Intent(MainActivity.this,DiscoverActivity.class);
                    startActivity(discoverPage);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
                    MainActivity.this.finish();
                    return true;
                case R.id.navigation_me:
                    Intent userInfoPage = new Intent(MainActivity.this, UserInfoActivity.class);
                    startActivity(userInfoPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    MainActivity.this.finish();
                    return true;
                case R.id.navigation_more:
                    Intent morePage = new Intent(MainActivity.this, MoreActivity.class);
                    startActivity(morePage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    return true;
            }
            return false;
        }
    };
}
