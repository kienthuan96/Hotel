package com.example.thuan.hotel.Model;

public class Service {
    private Boolean wifi;
    private Boolean pet;
    private Boolean restaurant;
    private Boolean bar;
    private Boolean swimmingPool;

    public Service() {
        this.wifi=false;
        this.pet=false;
        this.restaurant=false;
        this.bar=false;
        this.swimmingPool=false;
    }

    public Service(Boolean wifi, Boolean pet, Boolean restaurant, Boolean bar, Boolean swimmingPool) {
        this.wifi = wifi;
        this.pet = pet;
        this.restaurant = restaurant;
        this.bar = bar;
        this.swimmingPool = swimmingPool;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getPet() {
        return pet;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
    }

    public Boolean getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Boolean restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getBar() {
        return bar;
    }

    public void setBar(Boolean bar) {
        this.bar = bar;
    }

    public Boolean getSwimmingPool() {
        return swimmingPool;
    }

    public void setSwimmingPool(Boolean swimmingPool) {
        this.swimmingPool = swimmingPool;
    }
}
