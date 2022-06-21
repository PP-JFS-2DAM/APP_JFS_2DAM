package com.svalero.toplaptop.domain.dto;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class OrderDTO implements Comparable {

    private int id;     // Order
    private LocalDate date; // Order
    private String userNameSurname;  // User
    private String computerBrandModel;    // User
    private String computerLicensePlate;    // Computer
    private byte[] computerImageOrder;   // Computer
    private String description; // Order

    @Ignore
    public OrderDTO() {
    }

    public OrderDTO(int id, LocalDate date, String userNameSurname, String computerBrandModel, String computerLicensePlate, byte[] computerImageOrder, String description) {
        this.id = id;
        this.date = date;
        this.userNameSurname = userNameSurname;
        this.computerBrandModel = computerBrandModel;
        this.computerLicensePlate = computerLicensePlate;
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

    public byte[] getComputerImageOrder() {
        return computerImageOrder;
    }

    public void setComputerImageOrder(byte[] computerImageOrder) {
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
        OrderDTO orderDTO = (OrderDTO) o;
        return id == orderDTO.id && date.equals(orderDTO.date);
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
        return "OrderDTO{" +
                "id=" + id +
                ", date=" + date +
                ", userNameSurname='" + userNameSurname + '\'' +
                ", computerBrandModel='" + computerBrandModel + '\'' +
                ", description='" + description + '\'' +
                ", computerImageOrder=" + Arrays.toString(computerImageOrder) +
                '}';
    }

    public String getComputerLicensePlate() {
        return computerLicensePlate;
    }

    public void setComputerLicensePlate(String computerLicensePlate) {
        this.computerLicensePlate = computerLicensePlate;
    }
}
