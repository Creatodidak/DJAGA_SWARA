package id.creatodidak.djaga_swara.API.Models;

public class Profile {
    private String nrp;
    private String nama;
    private String pangkat;
    private String satker;
    private String satfung;
    private String jabatan;
    private String tanggal_lahir;
    private String foto;
    private String wa;
    private String created_at;
    private String updated_at;

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPangkat() {
        return pangkat;
    }

    public void setPangkat(String pangkat) {
        this.pangkat = pangkat;
    }

    public String getSatker() {
        return satker;
    }

    public void setSatker(String satker) {
        this.satker = satker;
    }

    public String getSatfung() {
        return satfung;
    }

    public void setSatfung(String satfung) {
        this.satfung = satfung;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getTanggalLahir() {
        return tanggal_lahir;
    }

    public void setTanggalLahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
}