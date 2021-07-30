package com.example.gratitude;

public class GratitudeObject {
    String mType;
    String mTitle;
    String mDescription;
    String mDate;

    public GratitudeObject(String type, String title, String description,String date){
        mTitle=title;
        mType=type;
        mDescription=description;
        mDate=date;
    }

    public String returnType(){
        return mType;
    }
    public String returnTitle(){
        return mTitle;
    }
    public String returnDescription(){
        return mDescription;
    }
    public String returnDate(){
        return mDate;
    }
}
