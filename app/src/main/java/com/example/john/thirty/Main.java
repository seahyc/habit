package com.example.john.thirty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.List;

import static com.parse.Parse.*;

public class Main extends Activity {


    public final static String EXTRA_MESSAGE = "thirtydays.main";
    private static final String PREFS = "prefs";
    private static final String PREF_NAME = "name";
    int current_id;
    SharedPreferences mSharedPreferences;
    EditText username_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "xsm18wbqaquudpKVem8xVN2QjL9XgpMsCQtOY88y", "EnNw10WrCSrpwSvr6ebCmp310MiXBbgXy8e0wAmq");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("habit");
        ParseQuery<ParseObject> query2 = query.addDescendingOrder("ID").setLimit(1);
        ParseObject object1;
        try {
            object1 = query2.getFirst();
            current_id = object1.getInt("ID") +1;
            Log.d("Current ID", Integer.toString(current_id));
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

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

    public void submitName(View view) {
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        username_input = (EditText) findViewById(R.id.username_field);
        String inputName = username_input.getText().toString();

        // Put it into memory (don't forget to commit!)
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(PREF_NAME, inputName);
        e.putInt("id", current_id);
        e.commit();

        // See if stuff got saved
//        mTextView = (TextView) findViewById(R.id.debug_field);
//        String debugPrint = mSharedPreferences.getString(PREF_NAME, "You failed.");
//        mTextView.setText(debugPrint);

        Intent intent = new Intent(this, One.class);
        intent.putExtra(EXTRA_MESSAGE, inputName);
        startActivity(intent);

    }
}
