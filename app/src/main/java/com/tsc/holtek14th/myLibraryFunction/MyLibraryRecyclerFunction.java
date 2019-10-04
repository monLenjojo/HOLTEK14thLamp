package com.tsc.holtek14th.myLibraryFunction;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsc.holtek14th.R;
import com.tsc.holtek14th.dialog.LoadingDialog;
import com.tsc.holtek14th.javaBean.AllStoryFormat;
import com.tsc.holtek14th.myLibraryFunction.MyLibraryRecyclerAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MyLibraryRecyclerFunction {

    private static final String TAG = MyLibraryRecyclerFunction.class.getSimpleName();
    Context context;
    RecyclerView recyclerView;
    String userId;
    ArrayList<AllStoryFormat> arrayList = new ArrayList();
    LoadingDialog loadingDialog;

    public MyLibraryRecyclerFunction(Context context, final RecyclerView recyclerView, @Nullable String userId) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.userId = userId;
        loadingDialog = new LoadingDialog(context,R.style.LoadingDialog,"Loading...",false);
        loadingDialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        if (userId != null) {
            Log.d(TAG, "onEvent: "+"data");
            firestore.collection("userData")
                .document(userId).collection("myLib").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        Log.d("TAG", "onEvent: " + queryDocumentSnapshots.getDocuments());
                        if (queryDocumentSnapshots.getDocuments().size() !=0) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                AllStoryFormat data = snapshot.toObject(AllStoryFormat.class);
                                Log.d(TAG, "onEvent: "+data.getStoryDepiction()+data.getStoryName()+data.getStoryPhoto());
                                arrayList.add(data);
                                upData();
                            }
                        }else {
                            recyclerView.setBackgroundResource(R.drawable.nopic);
                            recyclerView.setScaleY(0.5f);
                        }
                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
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
