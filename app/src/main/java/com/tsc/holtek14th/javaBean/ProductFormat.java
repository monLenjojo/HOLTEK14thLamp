package com.tsc.holtek14th.javaBean;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import java.util.HashMap;

public class ProductFormat {
    String storyPath;
    String productKey;
    Boolean onUse;
    Object useTime;

    public ProductFormat() {
    }

    public ProductFormat(String storyPath, String productKey, Boolean onUse, Object useTime) {
        this.storyPath = storyPath;
        this.productKey = productKey;
        this.onUse = onUse;
        this.useTime = useTime;
    }

    public String getStoryPath() {
        return storyPath;
    }

    public void setStoryPath(String storyPath) {
        this.storyPath = storyPath;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public Boolean getOnUse() {
        return onUse;
    }

    public void setOnUse(Boolean onUse) {
        this.onUse = onUse;
    }

//    public String getUseTime() {
//        return useTime;
//    }

    public Object getUseTime() {
        return useTime;
    }

    public void setUseTime(Object useTime) {
        this.useTime = useTime;
    }
}
