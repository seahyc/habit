package com.example.john.thirty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.john.thirty.Dashboard;
import com.example.john.thirty.One;
import com.example.john.thirty.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class Verification extends Activity {

    public final static String EXTRA_MESSAGE = "thirtydays.main";
    private static final String TAG = "Verification";
    SharedPreferences mSharedPreferences;
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    int official_progress;
    int reported_progress;
    int user_ID;
    String debugName;
    String partner_needing_verification;
    String objectID;
    public ParseObject object2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Parse.initialize(this, "xsm18wbqaquudpKVem8xVN2QjL9XgpMsCQtOY88y", "EnNw10WrCSrpwSvr6ebCmp310MiXBbgXy8e0wAmq");

        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String user_name = mSharedPreferences.getString(PREF_NAME, "You failed.");

        Log.d(TAG, "-1: " + user_name);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("habit");
        query.whereEqualTo("username", user_name);
        ParseObject object = null;
        try {
            object = query.getFirst();
            user_ID = object.getInt("ID");
            debugName = object.getString("username");
            Log.d(TAG, "1: " + debugName);
            Log.d(TAG, "2: " + Integer.toString(user_ID));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "0 - Error!");
        }

        Log.d(TAG, "3: " + Integer.toString(user_ID));

        updateVerificationProfile();

    }

    public void updateVerificationProfile() {
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("habit");
//        Log.d(TAG, "6: " + Integer.toString(user_ID));
        query2.whereEqualTo("pid", user_ID);
//        Log.d(TAG, "7: " + Integer.toString(user_ID));
        object2 = null;
        try {
            object2 = query2.getFirst();
            reported_progress = object2.getInt("r_progress");
            official_progress = object2.getInt("o_progress");
            objectID = object2.getString("objectID");
            partner_needing_verification = object2.getString("username");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "5 - Error!");
        }
        createVerifications();
    }

    public void createVerifications() {
        int number_verif = reported_progress - official_progress;
        TextView textView = (TextView) findViewById(R.id.verification_message);
        Button vButton = (Button) findViewById(R.id.verify_button);
        if (number_verif <= 0) {
            textView.setText("You have no verifications.");
            vButton.setVisibility(View.GONE);
        }
        else {
            textView.setText("You have " + number_verif + " verifications from "
                    + partner_needing_verification);
            vButton.setVisibility(View.VISIBLE);
        }
    }

    public void onClickVerify(View view) {

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("habit");
        query3.whereEqualTo("pid", user_ID);
        ParseObject object3 = null;
        try {
            object3 = query3.getFirst();
            object3.put("o_progress", official_progress + 1);
//            object3.put("r_progress", reported_progress);
            Log.d(TAG, "reported progress is " + reported_progress);
            object3.save();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "10 - Your query threw an error.");
        }
        updateVerificationProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_habit) {
            Intent intent = new Intent(this, One.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.notifications) {
            Intent intent = new Intent(this, Verification.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.dashboard) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}