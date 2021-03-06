package com.utaustin.freely;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.utaustin.freely.data.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static RequestQueue queue;
    private static final String url = "http://freely.asharmalik.us";

    public static void init(Context c) {
        queue = Volley.newRequestQueue(c);
    }

    private static void post(String endpoint, Map<String, String> params, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        final Map<String, String> variables = params;

        StringRequest sr = new StringRequest(Request.Method.POST, url + endpoint, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                return variables;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        sr.setShouldCache(false);
        queue.add(sr);
    }

    private static void get(String endpoint, Map<String, String> params, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        StringBuilder paramString = new StringBuilder();
        String reqURL;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(paramString.length() == 0){
                paramString.append("?");
            }else{
                paramString.append("&");
            }

            paramString.append(key + "=" + Uri.encode(value));
        }

        reqURL = url + endpoint + paramString.toString();

        StringRequest sr = new StringRequest(Request.Method.GET, reqURL, responseListener, errorListener);

        sr.setShouldCache(false);
        queue.add(sr);
    }

    public static void createMeeting(ArrayList<String> emails, String groupName, String beginTime, String endTime, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        String emailsFormatted = "[";

        emails.add(0, UserData.getEmail());

        for(int i = 0; i<emails.size(); i++){
            emailsFormatted += "\"" + emails.get(i) + "\"";

            if(i+1<emails.size()){
                emailsFormatted+=",";
            }
        }

        emailsFormatted += "]";

        Log.d("freely", emailsFormatted);

        params.put("emails", emailsFormatted);
        params.put("group_name", groupName);
        params.put("calendar_auth", UserData.getAuthCode());
        params.put("begin_time", beginTime);
        params.put("end_time", endTime);

        Server.post("/meeting", params, responseListener, errorListener);
    }

    public static void getMeeting(int sessionId, Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        Map<String, String> params = new HashMap<>();

        params.put("session_id", Integer.toString(sessionId));

        Server.get("/meeting", params, responseListener, errorListener);
    }

    public static void getMeetings(String email, Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        Map<String, String> params = new HashMap<>();

        params.put("email", email);

        Server.get("/meetings", params, responseListener, errorListener);
    }

    public static void getFreeTimes(int sessionId, Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        Map<String, String> params = new HashMap<>();

        params.put("session_id", Integer.toString(sessionId));

        Server.get("/free-times", params, responseListener, errorListener);
    }

    public static void selectMeetingTime(String sessionId, String beginTime, String endTime, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();

        params.put("session_id", sessionId);
        params.put("begin_time", beginTime);
        params.put("end_time", endTime);

        Server.post("/select-time", params, responseListener, errorListener);
    }

}