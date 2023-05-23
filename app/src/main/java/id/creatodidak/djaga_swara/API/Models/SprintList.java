package id.creatodidak.djaga_swara.API.Models;

import java.util.Date;
import java.util.List;

public class SprintList {
    private int id;
    private String nomor;
    private String judul;
    private String kode;
    private String tahun;
    private String penerbit;
    private String tandatangan;
    private String status;
    private String dasar;
    private String namaops;
    private String perintah;
    private Date tanggal;
    private Date tanggalmulai;
    private Date tanggalberakhir;
    private String type;
    private Date created_at;
    private Date updated_at;

    // Constructor
    public SprintList(int id, String nomor, String judul, String kode, String tahun, String penerbit, String tandatangan,
                      String status, String dasar, String namaops, String perintah, Date tanggal, Date tanggalmulai,
                      Date tanggalberakhir, String type, Date created_at, Date updated_at) {
        this.id = id;
        this.nomor = nomor;
        this.judul = judul;
        this.kode = kode;
        this.tahun = tahun;
        this.penerbit = penerbit;
        this.tandatangan = tandatangan;
        this.status = status;
        this.dasar = dasar;
        this.namaops = namaops;
        this.perintah = perintah;
        this.tanggal = tanggal;
        this.tanggalmulai = tanggalmulai;
        this.tanggalberakhir = tanggalberakhir;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getTandatangan() {
        return tandatangan;
    }

    public void setTandatangan(String tandatangan) {
        this.tandatangan = tandatangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDasar() {
        return dasar;
    }

    public void setDasar(String dasar) {
        this.dasar = dasar;
    }

    public String getNamaops() {
        return namaops;
    }

    public void setNamaops(String namaops) {
        this.namaops = namaops;
    }

    public String getPerintah() {
        return perintah;
    }

    public void setPerintah(String perintah) {
        this.perintah = perintah;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Date getTanggalmulai() {
        return tanggalmulai;
    }

    public void setTanggalmulai(Date tanggalmulai) {
        this.tanggalmulai = tanggalmulai;
    }

    public Date getTanggalberakhir() {
        return tanggalberakhir;
    }

    public void setTanggalberakhir(Date tanggalberakhir) {
        this.tanggalberakhir = tanggalberakhir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}