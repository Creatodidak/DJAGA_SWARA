package id.creatodidak.djaga_swara.API.Models.Multi;

public class PartaiKab {
    private final int id;
    private final String id_partai;
    private final String id_kab;
    private final String idtps; // Tambahkan field idtps
    private final String nomorurut;
    private final String nama;
    private final String tahun;
    private final String periode;
    private final String created_at;
    private final String updated_at;
    private final String suara;

    public PartaiKab(int id, String id_partai, String id_kab, String nomorurut, String nama, String tahun, String periode, String created_at, String updated_at, String suara, String idtps) {
        this.id = id;
        this.id_partai = id_partai;
        this.id_kab = id_kab;
        this.nomorurut = nomorurut;
        this.nama = nama;
        this.tahun = tahun;
        this.periode = periode;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.suara = suara;
        this.idtps = idtps;
    }

    public int getId() {
        return id;
    }

    public String getId_partai() {
        return id_partai;
    }

    public String getId_kab() {
        return id_kab;
    }

    public String getIdtps() {
        return idtps;
    }

    public String getNomorurut() {
        return nomorurut;
    }

    public String getNama() {
        return nama;
    }

    public String getTahun() {
        return tahun;
    }

    public String getPeriode() {
        return periode;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getSuara() {
        return suara;
    }
}
