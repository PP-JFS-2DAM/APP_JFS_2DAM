package com.svalero.toplaptop.domain.dto;

public class OrderDTO {

    private String orderDate;
    private String description;
    private int technical;
    private int computer;

    public OrderDTO() {
    }

    public OrderDTO(String orderDate, String description, int user, int computer) {
        this.orderDate = orderDate;
        this.description = description;
        this.technical = user;
        this.computer = computer;
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

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderDate='" + orderDate + '\'' +
                ", description='" + description + '\'' +
                ", technical=" + technical +
                ", computer=" + computer +
                '}';
    }
}