package com.example.john.thirty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Dashboard extends Activity {

    SharedPreferences mSharedPreferences;
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    public final static String EXTRA_MESSAGE = "thirtydays.main";
    private static final String TAG = "Habit";
    String objectId;
    int streak;
    String habit_name;
    String username_stored;
    TextView hTextView;
    int reported_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        username_stored = mSharedPreferences.getString(PREF_NAME, "You failed.");

        Parse.initialize(this, "xsm18wbqaquudpKVem8xVN2QjL9XgpMsCQtOY88y",
                "EnNw10WrCSrpwSvr6ebCmp310MiXBbgXy8e0wAmq");

        // Set user name
        hTextView = (TextView) findViewById(R.id.habit_username);
        hTextView.setText("Name: " + username_stored);

//        // Create a parse object to store current user profile from database
//        ParseObject habitProfile = new ParseObject("habit");

        updateDashBoardProfile();
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
            Intent intent = new Intent(this, AddHabit.class);
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


    public void onClickAddProgress(View view) {
        reported_progress = reported_progress + 1;

        // Update database r_progress field
        ParseQuery<ParseObject> query = ParseQuery.getQuery("habit");
        query.whereEqualTo("objectId", objectId);
        ParseObject object = null;
        try {
            // increases the database reported progress by 1
            object = query.getFirst();
            object.put("r_progress", reported_progress);
            Log.d(TAG, "reported progress is " + reported_progress);
            object.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updateDashBoardProfile();
    }

    public void updateDashBoardProfile() {
        // Test
        // Populates the query with row 1 of the database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("habit");
        query.whereEqualTo("username", username_stored);

        ParseObject object = null;
        try {
            object = query.getFirst();
            // Pull out habit name, streak
            habit_name = object.getString("habit");
            streak = object.getInt("o_progress");
            reported_progress = object.getInt("r_progress");
            objectId = object.getObjectId();

            // Set habit name and streak
            TextView hHabitName = (TextView) findViewById(R.id.habit_habitName);
            hHabitName.setText("Habit: " + habit_name);
            TextView hStreak = (TextView) findViewById(R.id.habit_streak);
            hStreak.setText("Streak to Date: " + Integer.toString(streak));
            TextView hReported = (TextView) findViewById(R.id.notifications_pending);
            hReported.setText("Notifications Pending: " +
                    Integer.toString(reported_progress - streak));

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Damn it.");
        }
    }

    public void onClickGoToVerification(View view) {
        Intent intent = new Intent(this, Verification.class);
        intent.putExtra(EXTRA_MESSAGE, username_stored);
        startActivity(intent);
    }
}