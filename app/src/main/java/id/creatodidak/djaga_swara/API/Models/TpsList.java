package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class TpsList {
    @SerializedName("id")
    private int id;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("id_prov")
    private String idProv;
    @SerializedName("id_sprin")
    private String idSprin;
    @SerializedName("id_kab")
    private String idKab;
    @SerializedName("id_kec")
    private String idKec;
    @SerializedName("id_des")
    private String idDes;
    @SerializedName("id_tps")
    private String idTps;
    @SerializedName("nomor_tps")
    private String nomorTps;
    @SerializedName("ketua_kpps")
    private String ketuaKpps;
    @SerializedName("hp_kpps")
    private String hpKpps;
    @SerializedName("dpt_sementara")
    private String dptSementara;
    @SerializedName("dpt_tetap")
    private String dptTetap;
    @SerializedName("dpt_final")
    private String dptFinal;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("status")
    private String status;
    @SerializedName("lokasikotaksuara")
    private String lokasiKotakSuara;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("nama_des")
    private String namaDes;
    @SerializedName("nama_kec")
    private String namaKec;
    @SerializedName("nama_kab")
    private String namaKab;

    // Constructor
    public TpsList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIdProv() {
        return idProv;
    }

    public void setIdProv(String idProv) {
        this.idProv = idProv;
    }

    public String getIdSprin() {
        return idSprin;
    }

    public void setIdSprin(String idSprin) {
        this.idSprin = idSprin;
    }

    public String getIdKab() {
        return idKab;
    }

    public void setIdKab(String idKab) {
        this.idKab = idKab;
    }

    public String getIdKec() {
        return idKec;
    }

    public void setIdKec(String idKec) {
        this.idKec = idKec;
    }

    public String getIdDes() {
        return idDes;
    }

    public void setIdDes(String idDes) {
        this.idDes = idDes;
    }

    public String getIdTps() {
        return idTps;
    }

    public void setIdTps(String idTps) {
        this.idTps = idTps;
    }

    public String getNomorTps() {
        return nomorTps;
    }

    public void setNomorTps(String nomorTps) {
        this.nomorTps = nomorTps;
    }

    public String getKetuaKpps() {
        return ketuaKpps;
    }

    public void setKetuaKpps(String ketuaKpps) {
        this.ketuaKpps = ketuaKpps;
    }

    public String getHpKpps() {
        return hpKpps;
    }

    public void setHpKpps(String hpKpps) {
        this.hpKpps = hpKpps;
    }

    public String getDptSementara() {
        return dptSementara;
    }

    public void setDptSementara(String dptSementara) {
        this.dptSementara = dptSementara;
    }

    public String getDptTetap() {
        return dptTetap;
    }

    public void setDptTetap(String dptTetap) {
        this.dptTetap = dptTetap;
    }

    public String getDptFinal() {
        return dptFinal;
    }

    public void setDptFinal(String dptFinal) {
        this.dptFinal = dptFinal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLokasiKotakSuara() {
        return lokasiKotakSuara;
    }

    public void setLokasiKotakSuara(String lokasiKotakSuara) {
        this.lokasiKotakSuara = lokasiKotakSuara;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNamaDes() {
        return namaDes;
    }

    public void setNamaDes(String namaDes) {
        this.namaDes = namaDes;
    }

    public String getNamaKec() {
        return namaKec;
    }

    public void setNamaKec(String namaKec) {
        this.namaKec = namaKec;
    }

    public String getNamaKab() {
        return namaKab;
    }

    public void setNamaKab(String namaKab) {
        this.namaKab = namaKab;
    }
}