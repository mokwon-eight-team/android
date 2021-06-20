package com.example.loginregister.retrofit;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("_id")
    String uuid;
    @SerializedName("service_id")
    String service_id;
    @SerializedName("password")
    String password;
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    String phone;
    @SerializedName("birth")
    String birth;
    @SerializedName("delivery_password")
    String delivery_password;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;
    @SerializedName("__v")
    int version;

    // 아이디 중복체크 통신 body 생성을 위한 생성자
    public UserInfo(String service_id) {
        this.service_id = service_id;
    }

    // 로그인 통신 body 생성을 위한 생성자
    public UserInfo(String service_id, String password) {
        this.service_id = service_id;
        this.password = password;
    }

    // 회원가입 인스턴스 생성을 위한 생성자
    public UserInfo(String service_id, String password, String name, String email, String phone, String birth, String delivery_password) {
        this.service_id = service_id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birth = birth;
        this.delivery_password = delivery_password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDelivery_password() {
        return delivery_password;
    }

    public void setDelivery_password(String delivery_password) {
        this.delivery_password = delivery_password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
