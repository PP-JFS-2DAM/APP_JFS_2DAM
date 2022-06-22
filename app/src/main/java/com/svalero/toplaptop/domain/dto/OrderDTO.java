package com.svalero.toplaptop.domain.dto;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.Technical;
import com.svalero.toplaptop.domain.User;

import java.time.LocalDate;

public class OrderDTO {

    private LocalDate orderDate;
    private String description;
    private Technical technical;
    private Computer computer;

    public OrderDTO() {
    }

    public OrderDTO(LocalDate orderDate, String description, Technical technical, Computer computer) {
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
}
