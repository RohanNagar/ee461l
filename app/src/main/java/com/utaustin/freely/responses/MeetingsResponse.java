package com.utaustin.freely.responses;

import android.util.Log;

import com.utaustin.freely.data.SessionData;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MeetingsResponse {
    public ArrayList<SessionData> Meetings = new ArrayList<>();

    public MeetingsResponse(String response){
        try{
            JSONObject res = new JSONObject(response);

            if(res.getBoolean("success")){
                JSONArray meetings = res.getJSONArray("data");

                for(int i = 0;i<meetings.length();i++){
                    JSONObject meeting = meetings.getJSONObject(i);
                    SessionData session = new SessionData(meeting.getString("group_name"), meeting.getInt("session_id"));

                    Meetings.add(session);
                }
            }

        }catch(JSONException e){

        }

    }
}
