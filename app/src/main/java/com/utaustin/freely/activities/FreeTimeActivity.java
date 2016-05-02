package com.utaustin.freely.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.utaustin.freely.R;
import com.utaustin.freely.Server;
import com.utaustin.freely.adapters.FreeTimeAdapter;
import com.utaustin.freely.responses.FreeTimeResponse;

public class FreeTimeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FreeTimeAdapter mAdapter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.free_time_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        if(intent.hasExtra("id")){
            id = intent.getIntExtra("id", -1);
        }

        // Enable back button in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadFreeTimes();
    }

    private void loadFreeTimes(){
        Server.getFreeTimes(id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                FreeTimeResponse res = new FreeTimeResponse(response);

                mAdapter = new FreeTimeAdapter(res.freeTimes, getApplicationContext());

                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("freely", error.toString());
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
