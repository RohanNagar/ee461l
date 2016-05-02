package com.utaustin.freely.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.utaustin.freely.R;
import com.utaustin.freely.adapters.ChoosePeopleAdapter;
import com.utaustin.freely.data.EmailContact;

import java.util.ArrayList;

public class CreateMeetingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ChoosePeopleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EmailContact> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        // Enable back button in action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_create_meeting_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        emails = new ArrayList<>();

        //vijaymanohar4@gmail.com
        //chindianteh@gmail.com

        emails.add(new EmailContact("Vijay Manhor", "vijaymanohar4@gmail.com"));
        emails.add(new EmailContact("Arjun Teh", "chindianteh@gmail.com"));

//        for(int i = 0; i<10; i++){
//            emails.add(new EmailContact("Meiru Che " + i, "asharm95@gmail.com"));
//        }

        mAdapter = new ChoosePeopleAdapter(emails);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_meeting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_create_group_next:
                Intent intent = new Intent(this, CreateMeetingChooseTimeActivity.class);
                Bundle bundle = new Bundle();

                ArrayList<String> checkedNames = new ArrayList<String>();
                ArrayList<String> checkedEmails = new ArrayList<>();

                for(int i = 0; i < emails.size(); i++) {
                    if(mAdapter.isChecked(i)) {
                        checkedNames.add(emails.get(i).getName());
                        checkedEmails.add(emails.get(i).getEmail());
                    }
                }

                //need at least 2 people for this app to work
                if(checkedEmails.size() < 1){
                    return true;
                }

                bundle.putStringArrayList("names", checkedNames);
                bundle.putStringArrayList("emails", checkedEmails);

                intent.putExtra("contacts_bundle", bundle);

                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
