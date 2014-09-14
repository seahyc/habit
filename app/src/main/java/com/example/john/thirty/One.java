package com.example.john.thirty;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class One extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //local saving preparation
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    private static final String PREF_ID = "user_id";
    SharedPreferences mSharedPreferences;

    private String habitName;
    private String habitDescription;
    private String partnerName;

    //UI Items
    EditText habit_name;
    EditText habit_description;
    TextView description_prompt;
    TextView name_prompt;

    //
    TextView partners;
    ListView friendlist;
    ArrayAdapter mArrayAdapter;
    ArrayList mNameList = new ArrayList();
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        habit_name = (EditText) findViewById(R.id.habit_name);
        habit_description = (EditText) findViewById(R.id.habit_description);
        name_prompt = (TextView) findViewById(R.id.name_prompt);
        description_prompt = (TextView) findViewById(R.id.description_prompt);

        Parse.initialize(this, "xsm18wbqaquudpKVem8xVN2QjL9XgpMsCQtOY88y", "EnNw10WrCSrpwSvr6ebCmp310MiXBbgXy8e0wAmq");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("habit");
//      query.whereEqualTo("Username", "objectId");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> habits, ParseException e) {
                if (e == null ) {
                    for (ParseObject user : habits) {
                        mNameList.add(user.getString("username"));
                        mArrayAdapter.notifyDataSetChanged();
                    }

                } else {
                }
            }
        });


        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        partners = (TextView) findViewById(R.id.partner);

        friendlist = (ListView) findViewById(R.id.friend_list);

        mArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                mNameList);

        friendlist.setAdapter(mArrayAdapter);
        friendlist.setOnItemClickListener(this);

    }



    @Override
    public void onClick(View view) {

        //parse parse parse




        name_prompt.setText("BUTTON PRESSED YEAH");
        String habitName = habit_name.getText().toString();
        String habitDescription = habit_description.getText().toString();

        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String userName = mSharedPreferences.getString(PREF_NAME, "John");
        int userID = mSharedPreferences.getInt(PREF_ID, 2);
        //userID needs to be pulled from Shared preferences
        // null is partnerName, which needs to be determined from database call,
        //or input by the user via a text field.




        ParseObject databaseLink = new ParseObject("habit");

        //write the habit data to the database
        databaseLink.put("ID", userID);
        Log.d("UserID is", "" + userID);
        databaseLink.put("habit", habitName);
        Log.d("habit is", habitName);
        databaseLink.put("username",userName );
        Log.d("username is", userName);
        databaseLink.put("pid",pos);
        databaseLink.put("o_progress",0);
        databaseLink.put("r_progress",0);
        databaseLink.saveInBackground();



        Habit currentHabit = new Habit(userID, userName, habitName, habitDescription, null);


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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id){
        Log.d("verify", position + ": " + mNameList.get(position));
        String part = mNameList.get(position).toString();
        partners.setText(part);
        pos = position + 1;

    }
}
