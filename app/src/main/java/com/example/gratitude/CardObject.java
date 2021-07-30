package com.example.gratitude;

import android.util.Log;

public class CardObject {
    int mImageResource;
    String mTitle;
    String mDescription;
    String mDate;
    String mType;
    int indexNumber;

    public CardObject(int imageResource,String title,String description,String date,String type){
        mImageResource=imageResource;
        mTitle=title;
        mDescription=description;
        mDate=date;
        mType=type;
        int indexNumber=0;
        if(mType=="Career"){

        }else if(mType=="Family"){
            indexNumber=1;
        }else if(mType=="Health"){
            indexNumber=2;
        }else if(mType=="Money"){
            indexNumber=3;
        }else if(mType=="Knowledge"){
            indexNumber=4;
        }else if(mType=="Miscellaneous"){
            indexNumber=5;
        }

        this.indexNumber=indexNumber;
    }

    public int returnImage(){
        return mImageResource;
    }
    public String returnTitle(){
        return mTitle;
    }
    public String returnDescription(){
        return mDescription;
    }
    public String returnDate(){return mDate;}
}
