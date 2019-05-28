package com.tsc.holtek14th.recyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.tsc.holtek14th.R;
import com.tsc.holtek14th.imgBase64.Base64toImg;
import com.tsc.holtek14th.javaBean.AllStoryFormat;

import java.util.ArrayList;

public class MyLibraryRecyclerAdapter extends RecyclerView.Adapter<MyLibraryRecyclerAdapter.MyLibraryViewHolder> {

    Context context;
    ArrayList<AllStoryFormat> arrayList = new ArrayList();

    public MyLibraryRecyclerAdapter(Context context, ArrayList<AllStoryFormat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_library_recycler_layout, viewGroup, false);
        return new MyLibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLibraryViewHolder myLibraryViewHolder, int i) {
        myLibraryViewHolder.name.setText(arrayList.get(i).getStoryName());
        myLibraryViewHolder.depiction.setText(arrayList.get(i).getStoryDepiction());
        myLibraryViewHolder.time.setText(arrayList.get(i).getTime());
        myLibraryViewHolder.progressBar.setMax(100);
        myLibraryViewHolder.progressBar.setProgress(60);
        new Base64toImg(myLibraryViewHolder.photo,arrayList.get(i).getStoryPhoto());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyLibraryViewHolder extends RecyclerView.ViewHolder {
        TextView name, depiction, time;
        ProgressBar progressBar;
        ImageView photo;
        public MyLibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txStoryName);
            depiction = itemView.findViewById(R.id.txStoryDepiction);
            time = itemView.findViewById(R.id.txStoryTime);
            progressBar = itemView.findViewById(R.id.storyProgressBar);
            photo = itemView.findViewById(R.id.imgStoryPhoto);
        }
    }
}
