package com.tsc.holtek14th.javaBean;

public class ProductFormat {
    String storyPath;
    String productKey;
    Boolean onUse;
    String userTime;

    public ProductFormat(String storyPath, String productKey, Boolean onUse, String userTime) {
        this.storyPath = storyPath;
        this.productKey = productKey;
        this.onUse = onUse;
        this.userTime = userTime;
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

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }
}
