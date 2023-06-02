package id.creatodidak.djaga_swara.API.Models.Multi;

public class DPDRI {
    private int id;
    private String id_calon;
    private String id_prov;
    private String nomorurut;
    private String nama;
    private String tahun;
    private String periode;
    private String created_at;
    private String updated_at;
    private String id_tps;
    private String suara;
    private String status;


    public DPDRI(int id, String id_calon, String id_prov, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at, String id_tps, String suara, String status) {
        this.id = id;
        this.id_calon = id_calon;
        this.id_prov = id_prov;
        this.nomorurut = nomorurut;
        this.nama = nama;
        this.tahun = tahun;
        this.periode = periode;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.id_tps = id_tps;
        this.suara = suara;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_calon() {
        return id_calon;
    }

    public void setId_calon(String id_calon) {
        this.id_calon = id_calon;
    }

    public String getId_prov() {
        return id_prov;
    }

    public void setId_prov(String id_prov) {
        this.id_prov = id_prov;
    }

    public String getNomorurut() {
        return nomorurut;
    }

    public void setNomorurut(String nomorurut) {
        this.nomorurut = nomorurut;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
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

    public String getId_tps() {
        return id_tps;
    }

    public void setId_tps(String id_tps) {
        this.id_tps = id_tps;
    }

    public String getSuara() {
        return suara;
    }

    public void setSuara(String suara) {
        this.suara = suara;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
