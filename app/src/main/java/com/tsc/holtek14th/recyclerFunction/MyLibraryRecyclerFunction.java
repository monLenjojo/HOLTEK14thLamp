package com.tsc.holtek14th.recyclerFunction;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.tsc.holtek14th.javaBean.StoryFormat;
import com.tsc.holtek14th.recyclerAdapter.MyLibraryRecyclerAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MyLibraryRecyclerFunction {

    Context context;
    RecyclerView recyclerView;
    String userId;
    ArrayList<StoryFormat> arrayList = new ArrayList();

    public MyLibraryRecyclerFunction(Context context, RecyclerView recyclerView, String userId) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.userId = userId;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("userData")
                .document(userId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        StoryFormat data = documentSnapshot.toObject(StoryFormat.class);
                        arrayList.add(data);
                        upData();
                    }
                });
    }

    private void upData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MyLibraryRecyclerAdapter adapter = new MyLibraryRecyclerAdapter(context,arrayList);
        recyclerView.setAdapter(adapter);
    }
}
