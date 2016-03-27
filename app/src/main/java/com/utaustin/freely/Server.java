package com.utaustin.freely;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static RequestQueue queue;
    private static final String url = "http://104.236.89.233:4000";

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
                Map<String, String> params = new HashMap<String, String>();
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

            paramString.append(key+"="+ Uri.encode(value));
        }

        reqURL = url + endpoint + paramString.toString();

        StringRequest sr = new StringRequest(Request.Method.GET, reqURL, responseListener, errorListener);

        sr.setShouldCache(false);
        queue.add(sr);
    }

    public static void createMeeting(ArrayList<String> emails, String gcmToken, String calendarToken, String beginTime, String endTime, int duration, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<String, String>();
        String emailsFormatted = "[";

        for(int i = 0;i<emails.size();i++){
            emailsFormatted+="\""+emails.get(i)+"\"";
        }

        emailsFormatted+="]";

        params.put("emails", emailsFormatted);
        params.put("gcm", gcmToken);
        params.put("calendar_token", calendarToken);
        params.put("begin_time", beginTime);
        params.put("end_time", endTime);
        params.put("duration", Integer.toString(duration));

        Server.post("/meeting", params, responseListener, errorListener);
    }

    public static void getMeeting(String sessionId, Response.Listener<String> responseListener, Response.ErrorListener errorListener){
        Map<String, String> params = new HashMap<String, String>();

        params.put("session_id", sessionId);

        Server.get("/meeting", params, responseListener, errorListener);
    }
}