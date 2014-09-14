package com.example.john.thirty;

/**
 * Created by John on 9/13/2014.
 */
public class HabitDay {
    boolean selfVerified;
    boolean mentorVerified;
    int dayIndex;

    public HabitDay(int  _dayIndex) {
        selfVerified = false;
        mentorVerified = false;
        dayIndex = _dayIndex;
    }

    public boolean checkSelfVeri () {
        return selfVerified;
    }
    public boolean checkMentorVeri () {
        return mentorVerified;
    }
    public int getDayIndex() {
        return dayIndex;
    }

    public void setSelfVeri (boolean verification) {
        selfVerified = verification;
    }

    public void setMentorVeri (boolean verification) {
        mentorVerified = verification;
    }

}
