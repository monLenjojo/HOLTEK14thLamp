package com.tsc.holtek14th;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsc.holtek14th.javaBean.AllStoryFormat;
import com.tsc.holtek14th.javaBean.MoreFormat;
import com.tsc.holtek14th.javaBean.ProductFormat;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

public class MoreActivity extends AppCompatActivity {

    private static final String TAG = MoreActivity.class.getSimpleName();
    private MyViewAdapter adapter;
    private ArrayList<MoreFormat> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView.setSelectedItemId(R.id.navigation_more);
        recyclerView = findViewById(R.id.moreRecyclerView);
        arrayList.add(new MoreFormat(R.drawable.add, "新增故事", null));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_more, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            final MoreFormat data = arrayList.get(i);
            myViewHolder.image.setImageResource(data.getImage());
            myViewHolder.title.setText(data.getTitle());
            if (data.getDepiction() != null) {
                myViewHolder.depiction.setText(data.getDepiction());
            } else {
                myViewHolder.depiction.setVisibility(View.GONE);
            }
            myViewHolder.itemView.setBackground(getDrawable(R.drawable.touch_down_color));
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (data.getImage()) {
                        case R.drawable.add:
                            // add story
                            addStory();
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView title, depiction;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.menu_image);
                title = itemView.findViewById(R.id.menu_title);
                depiction = itemView.findViewById(R.id.menu_depiction);
            }
        }
    }

    private void addStory() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.screen_add_story, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final TextInputEditText inputKey = dialogView.findViewById(R.id.productKeyInput);
        alertDialog.setView(dialogView)
                .setTitle("Add new story")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String key = inputKey.getText().toString();
                        if (!key.isEmpty()) {
                            final CollectionReference reference = FirebaseFirestore.getInstance().collection("allProduct").document("onSell").collection(key.substring(0, 1));
                            Query query = reference.whereEqualTo("productKey", key.substring(1, key.length()));
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isComplete()) {
                                        Log.d(TAG + "_Add_Story", "onComplete: OK");
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                ProductFormat data = documentSnapshot.toObject(ProductFormat.class);
                                                if (!data.getOnUse()) {
                                                    reference.document(documentSnapshot.getId()).update("onUse", true);
                                                    reference.document(documentSnapshot.getId()).update("useTime", FieldValue.serverTimestamp());
                                                    //find path story
                                                    findPathStory(data);
                                                }else {
                                                    new AlertDialog.Builder(MoreActivity.this).setTitle("Fail").setMessage("Already used on:\n"+data.getUseTime().toString()).setPositiveButton("ok",null).show();
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MoreActivity.this, "Enter key is null", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void findPathStory(ProductFormat data) {
        FirebaseFirestore.getInstance().collection("allStory").whereEqualTo("storyPath", data.getStoryPath()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            AllStoryFormat storyFormat = queryDocumentSnapshot.toObject(AllStoryFormat.class);
                            Log.d(TAG + "_Add_Story", String.valueOf(queryDocumentSnapshot.getData()));
                            FirebaseFirestore.getInstance().collection("userData")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .collection("myLib")
                                    .add(storyFormat);
                        }
                    }
                }
            }
        });
    }


    //---------------BottomNavigationView set
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myLibrary:
                    Intent mainPage = new Intent(MoreActivity.this, MainActivity.class);
                    startActivity(mainPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    MoreActivity.this.finish();
                    return true;
                case R.id.navigation_discover:
//                    mTextMessage.setText(R.string.title_discover);
                    Intent discoverPage = new Intent(MoreActivity.this, DiscoverActivity.class);
                    startActivity(discoverPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    MoreActivity.this.finish();
                    return true;
                case R.id.navigation_me:
                    Intent userInfoPage = new Intent(MoreActivity.this, UserInfoActivity.class);
                    startActivity(userInfoPage);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    MoreActivity.this.finish();
                    return true;
                case R.id.navigation_more:
//                    Intent morePage = new Intent(MoreActivity.this, MoreActivity.class);
//                    startActivity(morePage);
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
    }
}
