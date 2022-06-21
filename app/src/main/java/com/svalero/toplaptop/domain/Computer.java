package com.svalero.toplaptop.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Computer implements Comparable<Computer> {

    @PrimaryKey(autoGenerate = true)
    private int id;     // TODO en la API es LONG!!!
    @ColumnInfo
    private String brand;
    @ColumnInfo
    private String model;
    @ColumnInfo
    private String licensePlate;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] computerImage;
    @Ignore
    private User user;   // TODO en API contiene un objeto usere entero, sacar el ID


    public Computer() {
    }


    @Ignore
    public Computer(Computer computer) {
        this.id = computer.getId();
        this.brand = computer.getBrand();
        this.model = computer.getModel();
        this.licensePlate = computer.getLicensePlate();
        this.user = computer.getUser();
        this.computerImage = computer.getComputerImage();
    }

    @Ignore
    public Computer(int id, String brand, String model, String licensePlate, User user, byte[] computerImage) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.user = user;
        this.computerImage = computerImage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getComputerImage() {
        return computerImage;
    }

    public void setComputerImage(byte[] computerImage) {
        this.computerImage = computerImage;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", userId=" + user +
                ", computerImage=" + Arrays.toString(computerImage) +
                '}';
    }

    @Override
    public int compareTo(Computer o) {
        return brand.compareTo(o.brand);
    }
}

