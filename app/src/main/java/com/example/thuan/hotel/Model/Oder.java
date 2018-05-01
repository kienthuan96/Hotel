package com.example.thuan.hotel.Model;

public class Oder {
    private  String name;
    private  String email;
    private String phone;
    private String DateStartOrder;
    private String DateEndOrder;
    private  int RoomOrder;
    private float totalMoney;

    public Oder() {
    }

    public Oder(String name, String email, String phone, String dateStartOrder, String dateEndOrder, int roomOrder, float totalMoney) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        DateStartOrder = dateStartOrder;
        DateEndOrder = dateEndOrder;
        RoomOrder = roomOrder;
        this.totalMoney = totalMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateStartOrder() {
        return DateStartOrder;
    }

    public void setDateStartOrder(String dateStartOrder) {
        DateStartOrder = dateStartOrder;
    }

    public String getDateEndOrder() {
        return DateEndOrder;
    }

    public void setDateEndOrder(String dateEndOrder) {
        DateEndOrder = dateEndOrder;
    }

    public int getRoomOrder() {
        return RoomOrder;
    }

    public void setRoomOrder(int roomOrder) {
        RoomOrder = roomOrder;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }
}
