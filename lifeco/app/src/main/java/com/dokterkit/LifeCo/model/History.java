package com.dokterkit.LifeCo.model;

public class History{
    private String UserID;
    private String Aktivitas;
    private String Tanggal;
    private String Waktu;

    public History(){

    }

    public History(String aktivitas, String tanggal, String waktu) {
        Aktivitas = aktivitas;
        Tanggal = tanggal;
        Waktu = waktu;
    }

    public String getAktivitas() {
        return Aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        Aktivitas = aktivitas;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getWaktu() {
        return Waktu;
    }

    public void setWaktu(String waktu) {
        Waktu = waktu;
    }
}

