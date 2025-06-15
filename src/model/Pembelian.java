package model;

import java.time.LocalDate;

public class Pembelian {
    private int id;
    private int idObat;
    private int jumlah;
    private LocalDate tanggal;

    public Pembelian(int id, int idObat, int jumlah, LocalDate tanggal) {
        this.id = id;
        this.idObat = idObat;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public int getIdObat() {
        return idObat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }
}
