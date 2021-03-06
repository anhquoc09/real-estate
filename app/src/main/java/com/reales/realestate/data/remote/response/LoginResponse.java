package com.reales.realestate.data.remote.response;

import com.reales.realestate.data.model.Profile;
import com.google.gson.annotations.SerializedName;

/**
 * @author anhquoc09
 * @since 06/03/2019
 */

public class LoginResponse {

    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private Profile data;

    @SerializedName("expireTime")
    private Long expireTime;

    @SerializedName("token")
    private String token;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Profile getProfile() {
        return data;
    }

    public void setProfile(Profile data) {
        this.data = data;
    }

    public Long getExpireTime() {
        return expireTime;
    }
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
