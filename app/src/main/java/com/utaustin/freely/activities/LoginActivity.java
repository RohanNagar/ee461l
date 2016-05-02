package com.utaustin.freely.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.utaustin.freely.R;
import com.utaustin.freely.data.UserData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final int RC_SIGN_IN = 9001;
    private static final String serverClientId
            = "292287318292-49ifkmri2u33g87ijdfa7nacbcpsuo58.apps.googleusercontent.com";
    private static final String serverClientSecret
            = "qinfghlkr3PpzkhqGJkkwvFY";

    private ArrayList<String> contacts;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId, false)
                .requestScopes(
                        new Scope(CalendarScopes.CALENDAR),
                        new Scope(PeopleScopes.CONTACTS_READONLY))
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customize sign-in button
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        contacts = new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            //Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("signIn", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("signIn", "name:" + acct.getDisplayName());

            // Get auth code
            String authCode = acct.getServerAuthCode();
            Log.d("signIn", "authCode:" + authCode);
            // TODO: send auth code to backend

            // Get Google contacts asynchronously
            new GoogleContactsClass().execute(authCode);

            // Set UserData
            UserData.setEmail(acct.getEmail());
            UserData.setName(acct.getDisplayName());
            UserData.setAuthCode(acct.getServerAuthCode());

            // Go to MeetingsActivity
            Intent intent = new Intent(this, MeetingsActivity.class);
            intent.putStringArrayListExtra("contacts", contacts);
            startActivity(intent);
            finish();
        } else {
            // Unsuccessful
            Log.d("signIn", "failure");
        }
    }

    private class GoogleContactsClass extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {
            String authCode = data[0];

            String accessToken;
            try {
                GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                        "https://www.googleapis.com/oauth2/v4/token", serverClientId,
                        serverClientSecret, authCode, "").execute();
                accessToken = response.getAccessToken();
            } catch (IOException e) {
                Log.d("auth", "error");
                return "Bad";
            }

            // Get contacts
            GoogleCredential credential = new GoogleCredential.Builder()
                    .build().setAccessToken(accessToken);

            People peopleService = new People.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                    .build();

            try {
                ListConnectionsResponse response = peopleService.people().connections().list("people/me").execute();
                List<Person> connections = response.getConnections();

                List<String> names = new ArrayList<>();
                for (Person p : connections) {
                    for (Name name : p.getNames()) {
                        names.add(name.getDisplayName());
                    }
                }

                Log.d("contacts", names.toString());
                return names.toString();
            } catch (IOException e) {
                Log.d("contacts", "error");
                return "Bad";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> foundNames = Arrays.asList(result.split("\\s*,\\s*"));
            contacts.addAll(foundNames);
            Log.d("DONE", contacts.toString());
        }
    }
}
