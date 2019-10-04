package com.tsc.holtek14th.javaBean;

import android.support.annotation.Nullable;

public class MoreFormat {
    int image;
    String title;
    @Nullable String depiction;

    public MoreFormat(int image, String title, @Nullable String depiction) {
        this.image = image;
        this.title = title;
        this.depiction = depiction;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(@Nullable String depiction) {
        this.depiction = depiction;
    }
}
