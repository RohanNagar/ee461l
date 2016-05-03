package com.utaustin.freely.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private static String email;
    private static String name;
    private static String authCode;
    private static List<EmailContact> contacts = new ArrayList<>();

    public static String getAuthCode() {
        return authCode;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
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

    public static void setContacts(List<EmailContact> c){
        contacts = c;
    }

    public static List<EmailContact> getContacts(){
        return contacts;
    }
}
