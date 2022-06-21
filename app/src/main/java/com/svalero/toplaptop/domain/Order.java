package com.svalero.toplaptop.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svalero.toplaptop.database.TimestampConverter;

import java.time.LocalDate;

@Entity
public class Order implements Comparable<Order> {

    @PrimaryKey(autoGenerate = true)
    private int id;     // TODO en la API es LONG!!!
    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private LocalDate orderDate;
    @ColumnInfo
    private String description;
    @Ignore
    private User user;   // TODO en API contiene un objeto usere entero, sacar el ID
    @Ignore
    private Computer computer; // TODO en API contiene un objeto computer entero, sacar el ID

    public Order() {
    }

    @Ignore
    public Order(int id, LocalDate date, String description, User user, Computer computer) {
        this.id = id;
        this.orderDate = date;
        this.description = description;
        this.user = user;
        this.computer = computer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", userId=" + user +
                ", computerId=" + computer +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Order o) {
        return orderDate.compareTo(o.orderDate);
    }
}
