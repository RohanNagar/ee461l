package com.utaustin.freely.data;

public class UserData {
    private static String email;
    private static String name;
    private static String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public static void setEmail(String email) {
        UserData.email = email;
    }

    public static void setName(String name) {
        UserData.name = name;
    }

    public static void setAuthCode(String authCode) {
        UserData.authCode = authCode;
    }
}
