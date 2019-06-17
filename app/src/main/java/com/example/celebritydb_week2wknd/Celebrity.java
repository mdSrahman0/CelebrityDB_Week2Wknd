package com.example.celebritydb_week2wknd;

import android.os.Parcel;
import android.os.Parcelable;

public class Celebrity implements Parcelable {
    private String name;
    private String age;
    private String profession;

    // default constructor
    public Celebrity(){

    }

    public Celebrity(String name, String age, String profession) {
        this.name = name;
        this.age = age;
        this.profession = profession;
    }

    protected Celebrity(Parcel in) {
        name = in.readString();
        age = in.readString();
        profession = in.readString();
    }

    public static final Creator<Celebrity> CREATOR = new Creator<Celebrity>() {
        @Override
        public Celebrity createFromParcel(Parcel in) {
            return new Celebrity(in);
        }

        @Override
        public Celebrity[] newArray(int size) {
            return new Celebrity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(profession);
    }
}
