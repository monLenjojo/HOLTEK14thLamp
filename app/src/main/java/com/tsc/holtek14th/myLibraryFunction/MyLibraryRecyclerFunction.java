package com.tsc.holtek14th.myLibraryFunction;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.tsc.holtek14th.R;
import com.tsc.holtek14th.javaBean.AllStoryFormat;
import com.tsc.holtek14th.myLibraryFunction.MyLibraryRecyclerAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MyLibraryRecyclerFunction {

    Context context;
    RecyclerView recyclerView;
    String userId;
    ArrayList<AllStoryFormat> arrayList = new ArrayList();

    public MyLibraryRecyclerFunction(Context context, final RecyclerView recyclerView, @Nullable String userId) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.userId = userId;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        if (userId != null) {
            firestore.collection("userData")
                .document(userId).collection("myLib").document()
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        Log.d("TAG", "onEvent: " + documentSnapshot.getData());
                        if (documentSnapshot.getData() != null) {
                            AllStoryFormat data = documentSnapshot.toObject(AllStoryFormat.class);
                            arrayList.add(data);
                            upData();
                        }else {
                            recyclerView.setBackgroundResource(R.drawable.login_bg_space);
                        }
                    }
                });
        }
    }

    private void upData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyLibraryRecyclerAdapter adapter = new MyLibraryRecyclerAdapter(context,arrayList);
        recyclerView.setAdapter(adapter);
    }
}
