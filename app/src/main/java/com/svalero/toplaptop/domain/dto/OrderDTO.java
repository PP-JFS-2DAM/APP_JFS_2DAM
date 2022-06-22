package com.svalero.toplaptop.domain.dto;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.Technical;
import com.svalero.toplaptop.domain.User;

import java.time.LocalDate;

public class OrderDTO {

    private LocalDate orderDate;
    private String description;
    private int technical;
    private int computer;

    public OrderDTO() {
    }

    public OrderDTO(LocalDate orderDate, String description, int technical, int computer) {
        this.orderDate = orderDate;
        this.description = description;
        this.technical = technical;
        this.computer = computer;
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

    public int getTechnical() {
        return technical;
    }

    public void setTechnical(int technical) {
        this.technical = technical;
    }

    public int getComputer() {
        return computer;
    }

    public void setComputer(int computer) {
        this.computer = computer;
    }
}