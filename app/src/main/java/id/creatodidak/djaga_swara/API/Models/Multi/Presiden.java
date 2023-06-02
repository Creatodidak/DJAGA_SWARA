package id.creatodidak.djaga_swara.API.Models.Multi;

public class Presiden {
    private int id;
    private String idCalon;
    private String noUrut;
    private String capres;
    private String cawapres;
    private String tahun;
    private String periode;
    private String createdAt;
    private String updatedAt;
    private String idTps;
    private String suara;
    private String status;

    public Presiden(int id, String idCalon, String noUrut, String capres, String cawapres, String tahun,
                    String periode, String createdAt, String updatedAt, String idTps, String suara, String status) {
        this.id = id;
        this.idCalon = idCalon;
        this.noUrut = noUrut;
        this.capres = capres;
        this.cawapres = cawapres;
        this.tahun = tahun;
        this.periode = periode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.idTps = idTps;
        this.suara = suara;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCalon() {
        return idCalon;
    }

    public void setIdCalon(String idCalon) {
        this.idCalon = idCalon;
    }

    public String getNoUrut() {
        return noUrut;
    }

    public void setNoUrut(String noUrut) {
        this.noUrut = noUrut;
    }

    public String getCapres() {
        return capres;
    }

    public void setCapres(String capres) {
        this.capres = capres;
    }

    public String getCawapres() {
        return cawapres;
    }

    public void setCawapres(String cawapres) {
        this.cawapres = cawapres;
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

    public String getIdTps() {
        return idTps;
    }

    public void setIdTps(String idTps) {
        this.idTps = idTps;
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
