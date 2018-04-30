package com.example.thuan.hotel.Model;

import java.util.Date;

public class BinhLuan {
public String IDKhachSan ;
public String TenKhachSan;
public Date ngayTaoBL ;
public String Ten;
public String NhanXet;
public float DiemBL;

    public BinhLuan() {
    }

    public BinhLuan(String IDKhachSan, String tenKhachSan, Date ngayTaoBL, String ten, String nhanXet, float diemBL) {
        this.IDKhachSan = IDKhachSan;
        TenKhachSan = tenKhachSan;
        this.ngayTaoBL = ngayTaoBL;
        Ten = ten;
        NhanXet = nhanXet;
        DiemBL = diemBL;
    }

    @Override
    public String toString() {
        return "BinhLuan{" +
                "IDKhachSan='" + IDKhachSan + '\'' +
                ", TenKhachSan='" + TenKhachSan + '\'' +
                ", ngayTaoBL=" + ngayTaoBL +
                ", Ten='" + Ten + '\'' +
                ", NhanXet='" + NhanXet + '\'' +
                ", DiemBL=" + DiemBL +
                '}';
    }
}
