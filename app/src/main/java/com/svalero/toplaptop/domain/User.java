package com.svalero.toplaptop.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;     // TODO en la API es LONG!!!
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String surname;
    @ColumnInfo
    private String dni;
    @ColumnInfo
    private boolean vip;
    @ColumnInfo
    private float latitude;
    @ColumnInfo
    private float longitude;
    @ColumnInfo //(typeAffinity = ColumnInfo.BLOB)
    private String userImage;
    //private byte[] userImage;

    @Ignore
    public User() {
    }

    public User(int id, String name, String surname, String dni, boolean vip, float latitude, float longitude, String userImage) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.vip = vip;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userImage = userImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dni='" + dni + '\'' +
                ", vip=" + vip +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", userImage=" + userImage +
                '}';
    }
}
