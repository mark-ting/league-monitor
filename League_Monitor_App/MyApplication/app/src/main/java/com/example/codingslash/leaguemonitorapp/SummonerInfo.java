package com.example.codingslash.leaguemonitorapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Operator on 1/9/2015.
 */

// container class for summoner related information
public class SummonerInfo implements Parcelable {

    // summoner information retrieved from API
    private Long id;
    private String name; // summoner name (with proper capitalization--NOT THE MAPPED KEY!)
    private Long level;

//    // class constructor
//    public SummonerInfo(Long id, String name, Long level) {
//        this.id = id;
//        this.name = name;
//        this.level = level;
//    }

    // getters
    public Long getSummId() {
        return this.id;
    }

    public String getSummName() {
        return this.name;
    }

    public Long getSummLevel() {
        return this.level;
    }

    // setters
    public void setSummId(Long id) {
        this.id = id;
    }

    public void setSummName(String name) {
        this.name = name;
    }

    public void setSummLevel(Long level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.name);
        out.writeLong(this.level);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SummonerInfo createFromParcel(Parcel in) {
            SummonerInfo info = new SummonerInfo();

            info.id = in.readLong();
            info.name = in.readString();
            info.level = in.readLong();

            return info;
        }

        public SummonerInfo[] newArray(int size) {
            return new SummonerInfo[size];
        }
    };
}