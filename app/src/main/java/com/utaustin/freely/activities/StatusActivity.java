package com.utaustin.freely.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.utaustin.freely.R;

public class StatusActivity extends AppCompatActivity {
    private String meetingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // Set title
        Intent intent = getIntent();
        this.meetingName = intent.getStringExtra("meetingName");
        getSupportActionBar().setTitle(meetingName);

        // Enable back button in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
