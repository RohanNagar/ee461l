package com.utaustin.freely.data;

public class SessionData {

    private String name;
    private String id;

    public SessionData(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
}
