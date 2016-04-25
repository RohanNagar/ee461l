package com.utaustin.freely.data;

public class SessionData {

    private String name;
    private int id;

    public SessionData(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
}
