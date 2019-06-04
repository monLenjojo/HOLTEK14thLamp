package com.tsc.holtek14th.discoverFunction;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsc.holtek14th.R;
import com.tsc.holtek14th.imgBase64.Base64toImg;
import com.tsc.holtek14th.javaBean.AllStoryFormat;

import java.util.ArrayList;

public class DiscoverRecyclerAdapter extends RecyclerView.Adapter<DiscoverRecyclerAdapter.DiscoverViewHolder> {

    Context context;
    ArrayList<AllStoryFormat> arrayList = new ArrayList<>();

    public DiscoverRecyclerAdapter(Context context, ArrayList<AllStoryFormat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.discover_recycler_layout, viewGroup, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder discoverViewHolder, int i) {
        discoverViewHolder.txStoryName.setText(arrayList.get(i).getStoryName());
        discoverViewHolder.txStoryEnglishName.setText(arrayList.get(i).getStoryDepiction());
        new Base64toImg(discoverViewHolder.imgPhoto,arrayList.get(i).getStoryPhoto());
        discoverViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DiscoverViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView txStoryName, txStoryEnglishName;
        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgStoryPhoto);
            txStoryName = itemView.findViewById(R.id.txStoryName);
            txStoryEnglishName = itemView.findViewById(R.id.txStoryEnglishName);
        }
    }
}
