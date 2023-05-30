package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class TpsActivity {
    @SerializedName("id")
    private int id;

    @SerializedName("id_tps")
    private String tpsId;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("cektps")
    private String cekTps;

    @SerializedName("dpt")
    private String dpt;

    @SerializedName("lappam")
    private String lappam;

    @SerializedName("laphasil")
    private String laphasil;

    @SerializedName("formc1")
    private String formc1;

    @SerializedName("lapwal")
    private String lapwal;

    @SerializedName("lapserah")
    private String lapserah;

    // Constructors, getters, and setters

    public TpsActivity() {
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getCekTps() {
        return cekTps;
    }

    public void setCekTps(String cekTps) {
        this.cekTps = cekTps;
    }

    public String getDpt() {
        return dpt;
    }

    public void setDpt(String dpt) {
        this.dpt = dpt;
    }

    public String getLappam() {
        return lappam;
    }

    public void setLappam(String lappam) {
        this.lappam = lappam;
    }

    public String getLaphasil() {
        return laphasil;
    }

    public void setLaphasil(String laphasil) {
        this.laphasil = laphasil;
    }

    public String getFormc1() {
        return formc1;
    }

    public void setFormc1(String formc1) {
        this.formc1 = formc1;
    }

    public String getLapwal() {
        return lapwal;
    }

    public void setLapwal(String lapwal) {
        this.lapwal = lapwal;
    }

    public String getLapserah() {
        return lapserah;
    }

    public void setLapserah(String lapserah) {
        this.lapserah = lapserah;
    }
}

