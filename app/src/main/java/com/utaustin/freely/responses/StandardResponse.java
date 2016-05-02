package com.utaustin.freely.responses;

import org.json.JSONException;
import org.json.JSONObject;

public class StandardResponse {
    public boolean success = false;

    public StandardResponse(String response){
        try{
            JSONObject res = new JSONObject(response);

            success = res.getBoolean("success");
        }catch(JSONException e){

        }
    }
}
