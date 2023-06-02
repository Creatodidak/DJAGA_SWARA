package id.creatodidak.djaga_swara.API.Models.Multi;

public class Kades {
    private int id;
    private String id_des;
    private String id_calon;
    private String no_urut;
    private String cakades;
    private String tahun;
    private String periode;
    private String created_at;
    private String updated_at;
    private String id_tps;
    private String suara;
    private String status;


    public Kades(int id, String id_des, String id_calon, String no_urut, String cakades, String tahun, String periode, String created_at, String updated_at, String id_tps, String suara, String status) {
        this.id = id;
        this.id_des = id_des;
        this.id_calon = id_calon;
        this.no_urut = no_urut;
        this.cakades = cakades;
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

    public String getId_des() {
        return id_des;
    }

    public void setId_des(String id_des) {
        this.id_des = id_des;
    }

    public String getId_calon() {
        return id_calon;
    }

    public void setId_calon(String id_calon) {
        this.id_calon = id_calon;
    }

    public String getNo_urut() {
        return no_urut;
    }

    public void setNo_urut(String no_urut) {
        this.no_urut = no_urut;
    }

    public String getCakades() {
        return cakades;
    }

    public void setCakades(String cakades) {
        this.cakades = cakades;
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
