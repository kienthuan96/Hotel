package com.example.thuan.hotel.Model;

public class Hotel {
    private String id;
    private String name;
    private String city;
    private String district;
    private String address;
    private Integer numberPhone;
    private Float price;
    private Service service;
    private String img1,img2,img3;
    private String id_user;

    public Hotel() {
    }



    public Hotel(String id, String name, String city, String district, String address, Integer numberPhone, Float price, Service service, String img1, String img2, String img3) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.address = address;
        this.numberPhone = numberPhone;
        this.price = price;
        this.service = service;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }
    public void Hotel(Hotel hotel){
        this.id = hotel.id;
        this.name = hotel.name;
        this.city = hotel.city;
        this.district = hotel.district;
        this.address = hotel.address;
        this.numberPhone = hotel.numberPhone;
        this.price = hotel.price;
        this.service = hotel.service;
        this.img1 = hotel.img1;
        this.img2 = hotel.img2;
        this.img3 = hotel.img3;
    }

    public Hotel(String id,String name, String district, Float price, String img1) {
        this.id=id;
        this.name = name;
        this.district = district;
        this.price = price;
        this.img1 = img1;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(Integer numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
