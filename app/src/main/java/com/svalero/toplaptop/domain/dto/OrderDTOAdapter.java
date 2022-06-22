package com.svalero.toplaptop.domain.dto;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class OrderDTOAdapter implements Comparable {

    private int id;     // Order
    private LocalDate date; // Order
    private String userNameSurname;  // User
    private String computerBrandModel;    // User
    private String computerRam;    // Computer
    private String computerImageOrder;   // Computer
    private String description; // Order

    @Ignore
    public OrderDTOAdapter() {
    }

    public OrderDTOAdapter(int id, LocalDate date, String userNameSurname, String computerBrandModel, String computerRam, String computerImageOrder, String description) {
        this.id = id;
        this.date = date;
        this.userNameSurname = userNameSurname;
        this.computerBrandModel = computerBrandModel;
        this.computerRam = computerRam;
        this.computerImageOrder = computerImageOrder;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUserNameSurname() {
        return userNameSurname;
    }

    public void setUserNameSurname(String userNameSurname) {
        this.userNameSurname = userNameSurname;
    }

    public String getComputerBrandModel() {
        return computerBrandModel;
    }

    public void setComputerBrandModel(String computerBrandModel) {
        this.computerBrandModel = computerBrandModel;
    }

    public String getComputerImageOrder() {
        return computerImageOrder;
    }

    public void setComputerImageOrder(String computerImageOrder) {
        this.computerImageOrder = computerImageOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTOAdapter orderDTOAdapter = (OrderDTOAdapter) o;
        return id == orderDTOAdapter.id && date.equals(orderDTOAdapter.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String toString() {
        return "OrderDTOAdapter{" +
                "id=" + id +
                ", date=" + date +
                ", userNameSurname='" + userNameSurname + '\'' +
                ", computerBrandModel='" + computerBrandModel + '\'' +
                ", description='" + description + '\'' +
                ", computerImageOrder=" + computerImageOrder +
                '}';
    }

    public String getComputerRam() {
        return computerRam;
    }

    public void setComputerRam(String computerRam) {
        this.computerRam = computerRam;
    }
}
