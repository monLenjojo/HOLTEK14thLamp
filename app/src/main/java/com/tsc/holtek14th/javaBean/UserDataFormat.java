package com.tsc.holtek14th.javaBean;

import android.net.Uri;
import android.support.annotation.Nullable;


public class UserDataFormat {
    String name;
    String email;
    String photo;
//    String myLib;

    public UserDataFormat() {
    }

    public UserDataFormat(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDataFormat(String name, String email, String photo) {
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(@Nullable String photo) {
        this.photo = photo;
    }
}
