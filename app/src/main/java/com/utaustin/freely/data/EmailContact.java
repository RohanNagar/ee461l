package com.utaustin.freely.data;

import java.io.Serializable;

public class EmailContact implements Serializable {
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

    @Override
    public String toString() {
        return name + " " + email;
    }
}
