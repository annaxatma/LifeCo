package com.example.LifeCo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ambulance implements Parcelable{
    String uid;
    String email;
    String password;
    String name;
    String phoneNumber;
    String hospital;
    String imageURl;
    String search;
    String account;
    String status;
    String g;
    Location location;

    public Ambulance(){

    }

    public Ambulance(String uid, String email, String password, String name, String phoneNumber, String hospital, String imageURl, String search, String account, String status, String g, Location location) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.hospital = hospital;
        this.imageURl = imageURl;
        this.search = search;
        this.account = account;
        this.status = status;
        this.g = g;
        this.location = location;
    }

    protected Ambulance(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        hospital = in.readString();
        imageURl = in.readString();
        search = in.readString();
        account = in.readString();
        status = in.readString();
        g = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(hospital);
        dest.writeString(imageURl);
        dest.writeString(search);
        dest.writeString(account);
        dest.writeString(status);
        dest.writeString(g);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ambulance> CREATOR = new Creator<Ambulance>() {
        @Override
        public Ambulance createFromParcel(Parcel in) {
            return new Ambulance(in);
        }

        @Override
        public Ambulance[] newArray(int size) {
            return new Ambulance[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
