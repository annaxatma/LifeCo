package com.dokterkit.LifeCo.model;

import androidx.annotation.Keep;

@Keep
public class Users {
    private String id;
    private String nama;
    private String username;
    private String email;
    private String password;
    private String alamat;
    private String noHP;
    private String noBPJS;
    private String noKTP;
    private String tekananDarah;
    private String gulaDarah;
    private String golDarah;
    private String jenisKelamin;
    private String penyakitSendiri;
    private String penyakitKeluarga;
    private String keluhanUtama;
    private String obat;
    private String alergiObat;
    private String alergiMakanan;
    private String tanggalLahir;
    private String noAsuransi;
    private String imageURL;
    private String status;
    private String search;
    private String rumahSakit;

    public Users() {
    }

    public Users(String id, String nama, String username, String email, String password, String alamat, String noHP, String noBPJS, String noKTP, String tekananDarah, String gulaDarah, String golDarah, String jenisKelamin, String penyakitSendiri, String penyakitKeluarga, String keluhanUtama, String obat, String alergiObat, String alergiMakanan, String tanggalLahir, String noAsuransi, String imageURL, String status, String search, String rumahSakit) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.noHP = noHP;
        this.noBPJS = noBPJS;
        this.noKTP = noKTP;
        this.tekananDarah = tekananDarah;
        this.gulaDarah = gulaDarah;
        this.golDarah = golDarah;
        this.jenisKelamin = jenisKelamin;
        this.penyakitSendiri = penyakitSendiri;
        this.penyakitKeluarga = penyakitKeluarga;
        this.keluhanUtama = keluhanUtama;
        this.obat = obat;
        this.alergiObat = alergiObat;
        this.alergiMakanan = alergiMakanan;
        this.tanggalLahir = tanggalLahir;
        this.noAsuransi = noAsuransi;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
        this.rumahSakit = rumahSakit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getNoBPJS() {
        return noBPJS;
    }

    public void setNoBPJS(String noBPJS) {
        this.noBPJS = noBPJS;
    }

    public String getNoKTP() {
        return noKTP;
    }

    public void setNoKTP(String noKTP) {
        this.noKTP = noKTP;
    }

    public String getTekananDarah() {
        return tekananDarah;
    }

    public void setTekananDarah(String tekananDarah) {
        this.tekananDarah = tekananDarah;
    }

    public String getGulaDarah() {
        return gulaDarah;
    }

    public void setGulaDarah(String gulaDarah) {
        this.gulaDarah = gulaDarah;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getPenyakitSendiri() {
        return penyakitSendiri;
    }

    public void setPenyakitSendiri(String penyakitSendiri) {
        this.penyakitSendiri = penyakitSendiri;
    }

    public String getPenyakitKeluarga() {
        return penyakitKeluarga;
    }

    public void setPenyakitKeluarga(String penyakitKeluarga) {
        this.penyakitKeluarga = penyakitKeluarga;
    }

    public String getKeluhanUtama() {
        return keluhanUtama;
    }

    public void setKeluhanUtama(String keluhanUtama) {
        this.keluhanUtama = keluhanUtama;
    }

    public String getObat() {
        return obat;
    }

    public void setObat(String obat) {
        this.obat = obat;
    }

    public String getAlergiObat() {
        return alergiObat;
    }

    public void setAlergiObat(String alergiObat) {
        this.alergiObat = alergiObat;
    }

    public String getAlergiMakanan() {
        return alergiMakanan;
    }

    public void setAlergiMakanan(String alergiMakanan) {
        this.alergiMakanan = alergiMakanan;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNoAsuransi() {
        return noAsuransi;
    }

    public void setNoAsuransi(String noAsuransi) {
        this.noAsuransi = noAsuransi;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setRumahSakit(String search) {
        this.rumahSakit = rumahSakit;
    }
}