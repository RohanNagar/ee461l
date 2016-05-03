package com.utaustin.freely.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.utaustin.freely.R;
import com.utaustin.freely.adapters.ChoosePeopleAdapter;
import com.utaustin.freely.data.EmailContact;
import com.utaustin.freely.data.UserData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ChoosePeopleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<EmailContact> emails;
    private ArrayList<EmailContact> contacts; // unused right now, still using emails

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
//        emails.add(new EmailContact("Vijay Manhor", "vijaymanohar4@gmail.com"));
//        emails.add(new EmailContact("Arjun Teh", "chindianteh@gmail.com"));

        List<EmailContact> emailTrans = UserData.getContacts();


        for(int i = 0;i<emailTrans.size();i++){
            emails.add(new EmailContact(emailTrans.get(i).getName(), emailTrans.get(i).getEmail()));
        }

        /* PART OF GOOGLE CONTACTS */
//        Serializable s = getIntent().getSerializableExtra("contacts");
//        if (s != null) {
//            contacts = (ArrayList<EmailContact>) s;
//        } else {
//            contacts = new ArrayList<>();
//        }
        // This gets contacts from the user's phone. Commented out
        // because we're trying to get contacts from Google
//        Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//        if (contacts != null && contacts.getCount() > 0) {
//            while (contacts.moveToNext()) {
//                String id = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                contacts.add(name);
//
////                // Now we need to get email address
////                Cursor emails = getContentResolver().query(
////                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
////                        null,
////                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
////                        new String[]{id}, null);
////
////                if (emails != null) {
////                    while (emails.moveToNext()) {
////                        String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
////                        emailMap.put(name, email);
////                    }
////
////                    emails.close();
////                }
//
//            }
//
//            contacts.close();
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

                ArrayList<String> checkedNames = new ArrayList<>();
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
