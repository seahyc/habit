package com.example.john.thirty;

/**
 * Created by John on 9/13/2014.
 */
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class Habit {

    private static int userID;
    private String userName;
    private String habitName;
    private String habitDescription;
    private static Date startTime;


    public Habit (int _userID, String _userName,String _habitName,String _habitDescription,
                  String _partnerName ) {
        userID = _userID;
        userName = _userName;
        habitName = _habitName;
        habitDescription = _habitDescription;

        Calendar datetime = Calendar.getInstance();
        startTime = datetime.getTime();
        List<HabitDay> thirtydays = new ArrayList<HabitDay>();

        int x = 0;
        while (x < 30) {
            thirtydays.add(new HabitDay(x));
            x += 1;
        }

    }


    //The "GET" Functions
    //
    public int getUserID () {
        return userID;
    }

    public String getUserName () {
        return userName;
    }

    public String getHabitName() {
        return habitName;
    }

    public String getHabitDescription() {
        return habitDescription;
    }
    public Date getStartTime() {
        return startTime;
    }

    //The "SET" Functions
    //
    public void setUserID(int newID) {
        userID = newID;
    }

    public void setUserName(String newUserName) {
        userName = newUserName;
    }

    public void setHabitName(String newHabitName){
        habitName = newHabitName;
    }

    public void setHabitDescription(String newHabitDescription) {
        habitDescription = newHabitDescription;
    }
}


