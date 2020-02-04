package com.dsetanzania.dse.helperClasses;

public class UserImageUpdloads {

    private String UserId;
    private String ImageUrl;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public UserImageUpdloads(String userId, String imageUrl) {
        UserId = userId;
        ImageUrl = imageUrl;
    }

    public UserImageUpdloads() {

    }
}
