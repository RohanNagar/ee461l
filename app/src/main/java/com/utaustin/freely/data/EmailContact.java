package com.utaustin.freely.data;

public class EmailContact {
    private String name;
    private String email;

    public EmailContact(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }
}
