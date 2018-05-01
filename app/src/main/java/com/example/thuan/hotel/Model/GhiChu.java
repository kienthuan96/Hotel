package com.example.thuan.hotel.Model;

import java.util.Arrays;
import java.util.Date;

public class GhiChu {
    public int idGhiChu ;
    public String tenGhiChu ;
    public String  tenKSGhiChu ;
    public String diaChiKSGhiChu ;
    public Date ngayTaoGhiChu ;
    public String moTaGhiChu;
    public byte[] anhKhachSan ;

    public GhiChu() {
    }

    public GhiChu(int idGhiChu, String tenGhiChu, String tenKSGhiChu, String moTaGhiChu,Date ngayTaoGhiChu,String diaChiKSGhiChu,  byte[] anhKhachSan) {
        this.idGhiChu = idGhiChu;
        this.tenGhiChu = tenGhiChu;
        this.tenKSGhiChu = tenKSGhiChu;
        this.diaChiKSGhiChu = diaChiKSGhiChu;
        this.ngayTaoGhiChu = ngayTaoGhiChu;
        this.moTaGhiChu = moTaGhiChu;
        this.anhKhachSan = anhKhachSan;
    }

    @Override
    public String toString() {
        return "GhiChu{" +
                "idGhiChu=" + idGhiChu +
                ", tenGhiChu='" + tenGhiChu + '\'' +
                ", tenKSGhiChu='" + tenKSGhiChu + '\'' +
                ", diaChiKSGhiChu='" + diaChiKSGhiChu + '\'' +
                ", ngayTaoGhiChu=" + ngayTaoGhiChu +
                ", moTaGhiChu='" + moTaGhiChu + '\'' +
                ", anhKhachSan=" + Arrays.toString(anhKhachSan) +
                '}';
    }
}
