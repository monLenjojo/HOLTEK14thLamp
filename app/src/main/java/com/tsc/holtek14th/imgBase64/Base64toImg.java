package com.tsc.holtek14th.imgBase64;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

public class Base64toImg {
    ImageView imageView;
    String imageFile;

    public Base64toImg(ImageView imageView, String imageFile) {
        this.imageView = imageView;
        this.imageFile = imageFile;

        byte[] decodeByte = Base64.decode(imageFile, 0);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length));
    }
}
