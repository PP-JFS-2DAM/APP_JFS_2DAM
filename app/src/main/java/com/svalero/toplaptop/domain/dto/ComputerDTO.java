package com.svalero.toplaptop.domain.dto;

import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.Technical;
import com.svalero.toplaptop.domain.User;

import java.time.LocalDate;

public class ComputerDTO {

    private String brand;
    private String model;
    private String ram;
    private String computerImage;
    private int user;

    public ComputerDTO() {
    }

    public ComputerDTO(String brand, String model, String ram, String computerImage, int user) {
        this.brand = brand;
        this.model = model;
        this.ram = ram;
        this.computerImage = computerImage;
        this.user = user;
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

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getComputerImage() {
        return computerImage;
    }

    public void setComputerImage(String computerImage) {
        this.computerImage = computerImage;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
