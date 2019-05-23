package com.tsc.holtek14th;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tsc.holtek14th.FIrebaseAddData.AddTestData;
import com.tsc.holtek14th.recyclerFunction.MyLibraryRecyclerFunction;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myLibrary:
//                    mTextMessage.setText(R.string.title_my_library);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("bob@stu.edu.tw", "123456");

        RecyclerView recyclerView = findViewById(R.id.myLibraryRecycler);
        new MyLibraryRecyclerFunction(this, recyclerView, auth.getUid());
        new AddTestData();
    }
}
