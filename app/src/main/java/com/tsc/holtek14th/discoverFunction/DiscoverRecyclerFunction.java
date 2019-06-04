package com.tsc.holtek14th.discoverFunction;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsc.holtek14th.javaBean.AllStoryFormat;
import com.tsc.holtek14th.myLibraryFunction.MyLibraryRecyclerAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class DiscoverRecyclerFunction {
    Context context;
    RecyclerView recyclerView;
    String userId;
    ArrayList<AllStoryFormat> arrayList = new ArrayList<>();


    public DiscoverRecyclerFunction(Context context, RecyclerView recyclerView, String userId) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.userId = userId;
        FirebaseFirestore story = FirebaseFirestore.getInstance();
        story.collection("allStory")
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                Log.d("TAG", "onEvent: "+queryDocumentSnapshots.getDocuments().size());
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        AllStoryFormat data = documentSnapshot.toObject(AllStoryFormat.class);
                        arrayList.add(data);
                    }
                    upData();
                }
            }
        });
    }

    private void upData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        DiscoverRecyclerAdapter adapter = new DiscoverRecyclerAdapter(context,arrayList);
        recyclerView.setAdapter(adapter);
    }
}
