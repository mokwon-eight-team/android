package com.example.loginregister.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDTO {
    @SerializedName("message")
    String message;
    @SerializedName("count")
    int count;
    @SerializedName("value")
    String value;
    @SerializedName("users")
    List<UserInfo> users;
    @SerializedName("user")
    UserInfo user;

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }

    public String getValue() {
        return value;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public UserInfo getUser() {
        return user;
    }
}
