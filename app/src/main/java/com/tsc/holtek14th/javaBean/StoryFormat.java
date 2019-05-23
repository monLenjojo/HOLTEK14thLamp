package com.tsc.holtek14th.javaBean;

public class StoryFormat {
    String storyName;
    String storyUrl;
    String uuid;
    String storyPhoto;
    String storyDepiction;
    String time;

    public StoryFormat() {
    }

    public StoryFormat(String storyName, String storyUrl, String uuid, String storyPhoto, String storyDepiction, String time) {
        this.storyName = storyName;
        this.storyUrl = storyUrl;
        this.uuid = uuid;
        this.storyPhoto = storyPhoto;
        this.storyDepiction = storyDepiction;
        this.time = time;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStoryPhoto() {
        return storyPhoto;
    }

    public void setStoryPhoto(String storyPhoto) {
        this.storyPhoto = storyPhoto;
    }

    public String getStoryDepiction() {
        return storyDepiction;
    }

    public void setStoryDepiction(String storyDepiction) {
        this.storyDepiction = storyDepiction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
