package com.utaustin.freely.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.api.Status;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.utaustin.freely.R;
import com.utaustin.freely.Server;
import com.utaustin.freely.adapters.MeetingsAdapter;
import com.utaustin.freely.data.EmailContact;
import com.utaustin.freely.data.SessionData;
import com.utaustin.freely.data.UserData;
import com.utaustin.freely.responses.MeetingsResponse;

import org.w3c.dom.UserDataHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class MeetingsActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<EmailContact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Serializable s = getIntent().getSerializableExtra("contacts");
        if (s != null) {
            contacts = (ArrayList<EmailContact>) s;
        } else {
            contacts = new ArrayList<>();
        }

        Server.init(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateMeetingActivity.class);
                intent.putExtra("contacts", contacts);

                startActivity(intent);
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_meetings_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<SessionData> names = new ArrayList<>();

        mAdapter = new MeetingsAdapter(names, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        loadMeetings();
    }

    private void loadMeetings(){
        Server.getMeetings(UserData.getEmail(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MeetingsResponse res = new MeetingsResponse(response);

                mAdapter = new MeetingsAdapter(res.Meetings, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("f", error.toString());
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("connection", "onConnectionFailed:" + connectionResult);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Log.d("signOut", "true");
                    } else {
                        Log.d("signOut", "false");
                    }
                }
            });

        Log.d("LOGOUT", "we did that shit");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meetings_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                // Do sign out
                signOut();

                // Go back to sign in
                Intent intent = new Intent(this, LoginActivity.class);

                startActivity(intent);
                finish(); // prevent user from returning
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
