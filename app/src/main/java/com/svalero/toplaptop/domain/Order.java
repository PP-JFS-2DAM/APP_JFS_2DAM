package com.svalero.toplaptop.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svalero.toplaptop.database.TimestampConverter;

@Entity
public class Order implements Comparable<Order> {

    @PrimaryKey(autoGenerate = true)
    private int id;     // TODO en la API es LONG!!!
    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private String orderDate;
    @ColumnInfo
    private String description;
    @Ignore
    private Technical technical;
    @Ignore
    private Computer computer;

    public Order() {
    }

    @Ignore
    public Order(int id, String date, String description, Technical technical, Computer computer) {
        this.id = id;
        this.orderDate = date;
        this.description = description;
        this.technical = technical;
        this.computer = computer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + orderDate +
                ", technicalId=" + technical +
                ", computerId=" + computer +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return orderDate.compareTo(o.orderDate);
    }
}
