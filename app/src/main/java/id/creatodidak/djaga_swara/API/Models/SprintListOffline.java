package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class SprintListOffline {
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
    @SerializedName("tanggal")
    private String tanggalString;
    @SerializedName("tanggalmulai")
    private String tanggalmulaiString;
    @SerializedName("tanggalberakhir")
    private String tanggalberakhirString;
    private String type;
    @SerializedName("created_at")
    private String createdAtString;
    @SerializedName("updated_at")
    private String updatedAtString;

    public SprintListOffline(int id, String nomor, String judul, String kode, String tahun, String penerbit, String tandatangan, String status, String dasar, String namaops, String perintah, String tanggalString, String tanggalmulaiString, String tanggalberakhirString, String type, String createdAtString, String updatedAtString) {
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
        this.tanggalString = tanggalString;
        this.tanggalmulaiString = tanggalmulaiString;
        this.tanggalberakhirString = tanggalberakhirString;
        this.type = type;
        this.createdAtString = createdAtString;
        this.updatedAtString = updatedAtString;
    }

    public int getId() {
        return id;
    }

    public String getNomor() {
        return nomor;
    }

    public String getJudul() {
        return judul;
    }

    public String getKode() {
        return kode;
    }

    public String getTahun() {
        return tahun;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public String getTandatangan() {
        return tandatangan;
    }

    public String getStatus() {
        return status;
    }

    public String getDasar() {
        return dasar;
    }

    public String getNamaops() {
        return namaops;
    }

    public String getPerintah() {
        return perintah;
    }

    public String getTanggalString() {
        return tanggalString;
    }

    public String getTanggalmulaiString() {
        return tanggalmulaiString;
    }

    public String getTanggalberakhirString() {
        return tanggalberakhirString;
    }

    public String getType() {
        return type;
    }

    public String getCreatedAtString() {
        return createdAtString;
    }

    public String getUpdatedAtString() {
        return updatedAtString;
    }
}