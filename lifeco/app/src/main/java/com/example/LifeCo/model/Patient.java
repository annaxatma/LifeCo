package com.example.LifeCo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient implements Parcelable {
    String uid;
    String email;
    String password;
    String name;
    String gender;
    String birthdate;
    String bloodType;
    String bloodPressure;
    String bloodSugar;
    String medicineIntake;
    String foodAllergy;
    String medicineAllergy;
    String address;
    String phoneNumber;
    String KTPNumber;
    String insuranceNumber;
    String BPJSNumber;
    String complaint;
    String geneticDisease;
    String ownDisease;
    String search;
    String status;
    String account;
    String g;
    Location location;
    String imageURL;

    public Patient(){

    }

    public Patient(String uid, String email, String password, String name, String gender, String birthdate, String bloodType, String bloodPressure, String bloodSugar, String medicineIntake, String foodAllergy, String medicineAllergy, String address, String phoneNumber, String KTPNumber, String insuranceNumber, String BPJSNumber, String complaint, String geneticDisease, String ownDisease, String search, String status, String account, String g, Location location, String imageURL) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.bloodType = bloodType;
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
        this.medicineIntake = medicineIntake;
        this.foodAllergy = foodAllergy;
        this.medicineAllergy = medicineAllergy;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.KTPNumber = KTPNumber;
        this.insuranceNumber = insuranceNumber;
        this.BPJSNumber = BPJSNumber;
        this.complaint = complaint;
        this.geneticDisease = geneticDisease;
        this.ownDisease = ownDisease;
        this.search = search;
        this.status = status;
        this.account = account;
        this.g = g;
        this.location = location;
        this.imageURL = imageURL;
    }

    protected Patient(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        name = in.readString();
        gender = in.readString();
        birthdate = in.readString();
        bloodType = in.readString();
        bloodPressure = in.readString();
        bloodSugar = in.readString();
        medicineIntake = in.readString();
        foodAllergy = in.readString();
        medicineAllergy = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        KTPNumber = in.readString();
        insuranceNumber = in.readString();
        BPJSNumber = in.readString();
        complaint = in.readString();
        geneticDisease = in.readString();
        ownDisease = in.readString();
        search = in.readString();
        status = in.readString();
        account = in.readString();
        g = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        imageURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(birthdate);
        dest.writeString(bloodType);
        dest.writeString(bloodPressure);
        dest.writeString(bloodSugar);
        dest.writeString(medicineIntake);
        dest.writeString(foodAllergy);
        dest.writeString(medicineAllergy);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(KTPNumber);
        dest.writeString(insuranceNumber);
        dest.writeString(BPJSNumber);
        dest.writeString(complaint);
        dest.writeString(geneticDisease);
        dest.writeString(ownDisease);
        dest.writeString(search);
        dest.writeString(status);
        dest.writeString(account);
        dest.writeString(g);
        dest.writeParcelable(location, flags);
        dest.writeString(imageURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getMedicineIntake() {
        return medicineIntake;
    }

    public void setMedicineIntake(String medicineIntake) {
        this.medicineIntake = medicineIntake;
    }

    public String getFoodAllergy() {
        return foodAllergy;
    }

    public void setFoodAllergy(String foodAllergy) {
        this.foodAllergy = foodAllergy;
    }

    public String getMedicineAllergy() {
        return medicineAllergy;
    }

    public void setMedicineAllergy(String medicineAllergy) {
        this.medicineAllergy = medicineAllergy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKTPNumber() {
        return KTPNumber;
    }

    public void setKTPNumber(String KTPNumber) {
        this.KTPNumber = KTPNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getBPJSNumber() {
        return BPJSNumber;
    }

    public void setBPJSNumber(String BPJSNumber) {
        this.BPJSNumber = BPJSNumber;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getGeneticDisease() {
        return geneticDisease;
    }

    public void setGeneticDisease(String geneticDisease) {
        this.geneticDisease = geneticDisease;
    }

    public String getOwnDisease() {
        return ownDisease;
    }

    public void setOwnDisease(String ownDisease) {
        this.ownDisease = ownDisease;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
