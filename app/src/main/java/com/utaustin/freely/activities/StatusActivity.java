package com.utaustin.freely.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.utaustin.freely.R;
import com.utaustin.freely.Server;
import com.utaustin.freely.adapters.MeetingsAdapter;
import com.utaustin.freely.adapters.StatusAdapter;
import com.utaustin.freely.data.MeetingData;
import com.utaustin.freely.responses.MeetingStatusResponse;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {
    private String meetingName;
    private int id;
    private RecyclerView recyclerView;
    private StatusAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // Set title
        Intent intent = getIntent();

        this.meetingName = intent.getStringExtra("meetingName");
        id = intent.getIntExtra("id", -1);

        getSupportActionBar().setTitle(meetingName);


        // Enable back button in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.activity_status_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        ArrayList<MeetingData> meetingData = new ArrayList<MeetingData>();

        mAdapter = new StatusAdapter(meetingData, getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        loadMeetingData();
    }

    private void loadMeetingData(){
        Server.getMeeting(id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MeetingStatusResponse res = new MeetingStatusResponse(response);

                mAdapter = new StatusAdapter(res.meetingData, getApplicationContext());
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("f", error.toString());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
