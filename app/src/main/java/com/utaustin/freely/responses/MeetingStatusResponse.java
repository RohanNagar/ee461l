package com.utaustin.freely.responses;

import android.util.Log;

import com.utaustin.freely.data.MeetingData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MeetingStatusResponse {
    public ArrayList<MeetingData> meetingData = new ArrayList<>();

    public MeetingStatusResponse(String response){
        JSONObject res;

        try{
            res = new JSONObject(response);

            JSONArray emails;

            if(res.getBoolean("success")){
                emails = res.getJSONArray("data");

                for(int i = 0;i<emails.length();i++){
                    String email = emails.getJSONObject(i).getString("email");
                    boolean auth = (!emails.getJSONObject(i).getString("google_cal_token").equals("null"));
                    MeetingData data = new MeetingData(email, auth);

                    meetingData.add(data);
                }
            }
        }catch(JSONException e){

        }
    }
}
