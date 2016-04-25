package com.utaustin.freely.data;

public class UserData {
    private String email;
    private String name;
    private String authCode;

    public UserData(String email, String name, String authCode) {
        this.email = email;
        this.name = name;
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
