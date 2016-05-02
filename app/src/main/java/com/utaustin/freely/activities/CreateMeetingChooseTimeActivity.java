package com.utaustin.freely.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utaustin.freely.R;
import com.utaustin.freely.fragments.EndDatePickerFragment;
import com.utaustin.freely.fragments.EndTimePickerFragment;
import com.utaustin.freely.fragments.StartDatePickerFragment;
import com.utaustin.freely.fragments.StartTimePickerFragment;

import java.util.ArrayList;

public class CreateMeetingChooseTimeActivity extends AppCompatActivity {

    private EditText nameText;
    private Button beginDatePickerButton, beginTimePickerButton, endDatePickerButton, endTimePickerButton;

    private ArrayList<String> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle passedBundle = intent.getBundleExtra("contacts_bundle");

        if(passedBundle != null){
            emails = passedBundle.getStringArrayList("emails");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting_choose_time);

        // Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameText = (EditText) findViewById(R.id.activity_create_meeting_choose_time_name);

        final FragmentManager fragmentManager = getFragmentManager();

        beginDatePickerButton = (Button) findViewById(R.id.create_meeting_choose_activity_open_begin_date_picker);
        beginDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new StartDatePickerFragment();
                newFragment.show(fragmentManager, "datePicker");
            }
        });

        endDatePickerButton = (Button) findViewById(R.id.create_meeting_choose_activity_open_end_date_picker);
        endDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment2 = new EndDatePickerFragment();
                newFragment2.show(fragmentManager, "datePicker");
            }
        });

        beginTimePickerButton = (Button) findViewById(R.id.create_meeting_choose_time_open_begin_time_picker);
        beginTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment3 = new StartTimePickerFragment();
                newFragment3.show(fragmentManager, "timePicker");
            }
        });

        endTimePickerButton = (Button) findViewById(R.id.create_meeting_choose_time_open_end_time_picker);
        endTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment4 = new EndTimePickerFragment();
                newFragment4.show(fragmentManager, "timePicker");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_meeting_choose_time_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_create_meeting_finish:
                // TODO
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setBeginDate(int year, int month, int day){
        TextView startDateTextView = (TextView) findViewById(R.id.activity_create_meeting_date_text_view);

        String date = (month+1) + "/" + day + "/" + year;
        startDateTextView.setText(date);
    }

    public void setEndDate(int year, int month, int day){
        TextView startDateTextView = (TextView) findViewById(R.id.activity_create_meeting_end_date_text_view);

        String date = (month+1) + "/" + day + "/" + year;
        startDateTextView.setText(date);
    }

}