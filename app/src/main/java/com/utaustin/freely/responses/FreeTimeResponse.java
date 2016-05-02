package com.utaustin.freely.responses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FreeTimeResponse {
    public boolean success = false;
    public ArrayList<String> freeTimes = new ArrayList<>();

    public FreeTimeResponse(String response){
        try{
            JSONObject res = new JSONObject(response);

            success = res.getBoolean("success");

            if(success){
                JSONArray data = res.getJSONArray("data");

                for(int i = 0;i<data.length();i++){
                    freeTimes.add(data.getString(i));
                }
            }

        }catch(JSONException e){

        }
    }
}
