package id.creatodidak.djaga_swara.API.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class TPS implements Parcelable {
    private String id;
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
    private String nama_des;
    private String nama_kec;
    private String nama_kab;

    public TPS(String id, String id_sprin, String id_kab, String id_kec, String id_des, String id_tps, String nomor_tps, String ketua_kpps, String hp_kpps, String presiden, String dprri, String dpdri, String gubernur, String dprprov, String bupati, String dprkab, String kades, String dpt_sementara, String dpt_tetap, String dpt_final, String keterangan, String status, String lokasikotaksuara, String created_at, String updated_at, String nama_des, String nama_kec, String nama_kab) {
        this.id = id;
        this.id_sprin = id_sprin;
        this.id_kab = id_kab;
        this.id_kec = id_kec;
        this.id_des = id_des;
        this.id_tps = id_tps;
        this.nomor_tps = nomor_tps;
        this.ketua_kpps = ketua_kpps;
        this.hp_kpps = hp_kpps;
        this.presiden = presiden;
        this.dprri = dprri;
        this.dpdri = dpdri;
        this.gubernur = gubernur;
        this.dprprov = dprprov;
        this.bupati = bupati;
        this.dprkab = dprkab;
        this.kades = kades;
        this.dpt_sementara = dpt_sementara;
        this.dpt_tetap = dpt_tetap;
        this.dpt_final = dpt_final;
        this.keterangan = keterangan;
        this.status = status;
        this.lokasikotaksuara = lokasikotaksuara;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.nama_des = nama_des;
        this.nama_kec = nama_kec;
        this.nama_kab = nama_kab;
    }

    protected TPS(Parcel in) {
        id = in.readString();
        id_sprin = in.readString();
        id_kab = in.readString();
        id_kec = in.readString();
        id_des = in.readString();
        id_tps = in.readString();
        nomor_tps = in.readString();
        ketua_kpps = in.readString();
        hp_kpps = in.readString();
        presiden = in.readString();
        dprri = in.readString();
        dpdri = in.readString();
        gubernur = in.readString();
        dprprov = in.readString();
        bupati = in.readString();
        dprkab = in.readString();
        kades = in.readString();
        dpt_sementara = in.readString();
        dpt_tetap = in.readString();
        dpt_final = in.readString();
        keterangan = in.readString();
        status = in.readString();
        lokasikotaksuara = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        nama_des = in.readString();
        nama_kec = in.readString();
        nama_kab = in.readString();
    }

    public static final Creator<TPS> CREATOR = new Creator<TPS>() {
        @Override
        public TPS createFromParcel(Parcel in) {
            return new TPS(in);
        }

        @Override
        public TPS[] newArray(int size) {
            return new TPS[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getNama_des() {
        return nama_des;
    }

    public void setNama_des(String nama_des) {
        this.nama_des = nama_des;
    }

    public String getNama_kec() {
        return nama_kec;
    }

    public void setNama_kec(String nama_kec) {
        this.nama_kec = nama_kec;
    }

    public String getNama_kab() {
        return nama_kab;
    }

    public void setNama_kab(String nama_kab) {
        this.nama_kab = nama_kab;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(id_sprin);
        dest.writeString(id_kab);
        dest.writeString(id_kec);
        dest.writeString(id_des);
        dest.writeString(id_tps);
        dest.writeString(nomor_tps);
        dest.writeString(ketua_kpps);
        dest.writeString(hp_kpps);
        dest.writeString(presiden);
        dest.writeString(dprri);
        dest.writeString(dpdri);
        dest.writeString(gubernur);
        dest.writeString(dprprov);
        dest.writeString(bupati);
        dest.writeString(dprkab);
        dest.writeString(kades);
        dest.writeString(dpt_sementara);
        dest.writeString(dpt_tetap);
        dest.writeString(dpt_final);
        dest.writeString(keterangan);
        dest.writeString(status);
        dest.writeString(lokasikotaksuara);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(nama_des);
        dest.writeString(nama_kec);
        dest.writeString(nama_kab);
    }
}