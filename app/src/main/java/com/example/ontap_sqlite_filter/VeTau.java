package com.example.ontap_sqlite_filter;

public class VeTau {
    private int id;
    private String gadi;
    private String gaden;
    private  float dongia;
    private boolean khuhoi;

    public VeTau(int id, String gadi, String gaden, float dongia, boolean khuhoi) {
        this.id = id;
        this.gadi = gadi;
        this.gaden = gaden;
        this.dongia = dongia;
        this.khuhoi = khuhoi;
    }

    public VeTau(String gadi, String gaden, float dongia, boolean khuhoi) {
        this.gadi = gadi;
        this.gaden = gaden;
        this.dongia = dongia;
        this.khuhoi = khuhoi;
    }

    public VeTau() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGadi() {
        return gadi;
    }

    public void setGadi(String gadi) {
        this.gadi = gadi;
    }

    public String getGaden() {
        return gaden;
    }

    public void setGaden(String gaden) {
        this.gaden = gaden;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public boolean isKhuhoi() {
        return khuhoi;
    }

    public void setKhuhoi(boolean khuhoi) {
        this.khuhoi = khuhoi;
    }
}
