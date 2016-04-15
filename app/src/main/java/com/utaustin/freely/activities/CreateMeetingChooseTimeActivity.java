package com.utaustin.freely.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.utaustin.freely.R;
import com.utaustin.freely.fragments.DatePickerFragment;
import com.utaustin.freely.fragments.TimePickerFragment;

public class CreateMeetingChooseTimeActivity extends AppCompatActivity {

    EditText nameText;
    Button beginTimePickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting_choose_time);

        // Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameText = (EditText) findViewById(R.id.activity_create_meeting_choose_time_name);
        beginTimePickerButton = (Button) findViewById(R.id.create_meeting_choose_activity_open_begin_picker);

        final FragmentManager fragmentManager = getFragmentManager();

        beginTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment newFragment = new TimePickerFragment();
//                newFragment.show(fragmentManager, "timePicker");

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(fragmentManager, "datePicker");
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

}
