package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;
public class TpsListOffline {
    private int id;
    private String id_sprin;
    private String id_kab;
    private String id_kec;
    private String id_des;
    private String id_tps;
    private String nomor_tps;
    private String ketua_kpps;
    private String hp_kpps;
    private String presiden;
    private String dprri;
    private String dpdri;
    private String gubernur;
    private String dprprov;
    private String bupati;
    private String dprkab;
    private String kades;
    private String dpt_sementara;
    private String dpt_tetap;
    private String dpt_final;
    private String keterangan;
    private String status;
    private String lokasikotaksuara;
    private String created_at;
    private String updated_at;
    @SerializedName("nama_des")
    private String namaDes;
    @SerializedName("nama_kec")
    private String namaKec;
    @SerializedName("nama_kab")
    private String namaKab;

    public TpsListOffline() {
        // konstruktor tanpa parameter
    }

//    public TpsListOffline(int id, String id_sprin, String id_kab, String id_kec, String id_des, String id_tps,
//                          String nomor_tps, String ketua_kpps, String hp_kpps, String presiden, String dprri,
//                          String dpdri, String gubernur, String dprprov, String bupati, String dprkab,
//                          String kades, String dpt_sementara, String dpt_tetap, String dpt_final,
//                          String keterangan, String status, String lokasikotaksuara, String namaDes, String namaKec, String namaKab, String created_at,
//                          String updated_at) {
//        this.id = id;
//        this.id_sprin = id_sprin;
//        this.id_kab = id_kab;
//        this.id_kec = id_kec;
//        this.id_des = id_des;
//        this.id_tps = id_tps;
//        this.nomor_tps = nomor_tps;
//        this.ketua_kpps = ketua_kpps;
//        this.hp_kpps = hp_kpps;
//        this.presiden = presiden;
//        this.dprri = dprri;
//        this.dpdri = dpdri;
//        this.gubernur = gubernur;
//        this.dprprov = dprprov;
//        this.bupati = bupati;
//        this.dprkab = dprkab;
//        this.kades = kades;
//        this.dpt_sementara = dpt_sementara;
//        this.dpt_tetap = dpt_tetap;
//        this.dpt_final = dpt_final;
//        this.keterangan = keterangan;
//        this.status = status;
//        this.lokasikotaksuara = lokasikotaksuara;
//        this.namaDes = namaDes;
//        this.namaKec = namaKec;
//        this.namaKab = namaKab;
//        this.created_at = created_at;
//        this.updated_at = updated_at;
//    }


    // Getters and setters for all the fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_sprin() {
        return id_sprin;
    }

    public void setId_sprin(String id_sprin) {
        this.id_sprin = id_sprin;
    }

    public String getId_kab() {
        return id_kab;
    }

    public void setId_kab(String id_kab) {
        this.id_kab = id_kab;
    }

    public String getId_kec() {
        return id_kec;
    }

    public void setId_kec(String id_kec) {
        this.id_kec = id_kec;
    }

    public String getId_des() {
        return id_des;
    }

    public void setId_des(String id_des) {
        this.id_des = id_des;
    }

    public String getId_tps() {
        return id_tps;
    }

    public void setId_tps(String id_tps) {
        this.id_tps = id_tps;
    }

    public String getNomor_tps() {
        return nomor_tps;
    }

    public void setNomor_tps(String nomor_tps) {
        this.nomor_tps = nomor_tps;
    }

    public String getKetua_kpps() {
        return ketua_kpps;
    }

    public void setKetua_kpps(String ketua_kpps) {
        this.ketua_kpps = ketua_kpps;
    }

    public String getHp_kpps() {
        return hp_kpps;
    }

    public void setHp_kpps(String hp_kpps) {
        this.hp_kpps = hp_kpps;
    }

    public String getPresiden() {
        return presiden;
    }

    public void setPresiden(String presiden) {
        this.presiden = presiden;
    }

    public String getDprri() {
        return dprri;
    }

    public void setDprri(String dprri) {
        this.dprri = dprri;
    }

    public String getDpdri() {
        return dpdri;
    }

    public void setDpdri(String dpdri) {
        this.dpdri = dpdri;
    }

    public String getGubernur() {
        return gubernur;
    }

    public void setGubernur(String gubernur) {
        this.gubernur = gubernur;
    }

    public String getDprprov() {
        return dprprov;
    }

    public void setDprprov(String dprprov) {
        this.dprprov = dprprov;
    }

    public String getBupati() {
        return bupati;
    }

    public void setBupati(String bupati) {
        this.bupati = bupati;
    }

    public String getDprkab() {
        return dprkab;
    }

    public void setDprkab(String dprkab) {
        this.dprkab = dprkab;
    }

    public String getKades() {
        return kades;
    }

    public void setKades(String kades) {
        this.kades = kades;
    }

    public String getDpt_sementara() {
        return dpt_sementara;
    }

    public void setDpt_sementara(String dpt_sementara) {
        this.dpt_sementara = dpt_sementara;
    }

    public String getDpt_tetap() {
        return dpt_tetap;
    }

    public void setDpt_tetap(String dpt_tetap) {
        this.dpt_tetap = dpt_tetap;
    }

    public String getDpt_final() {
        return dpt_final;
    }

    public void setDpt_final(String dpt_final) {
        this.dpt_final = dpt_final;
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

    public String getLokasikotaksuara() {
        return lokasikotaksuara;
    }

    public void setLokasikotaksuara(String lokasikotaksuara) {
        this.lokasikotaksuara = lokasikotaksuara;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
