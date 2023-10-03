package id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN;

public class MFormC1 {
    private int id; // ID dari data
    private String idTps;
    private String type;
    private String dokumentasi;
    private int local;

    public MFormC1() {
    }

    public MFormC1(String idTps, String type, String analisa, String prediksi, String rekomendasi, String dokumentasi, int local) {
        this.idTps = idTps;
        this.type = type;
        this.dokumentasi = dokumentasi;
        this.local = local;
    }

    // Buat getter dan setter untuk semua atribut
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTps() {
        return idTps;
    }

    public void setIdTps(String idTps) {
        this.idTps = idTps;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getDokumentasi() {
        return dokumentasi;
    }

    public void setDokumentasi(String dokumentasi) {
        this.dokumentasi = dokumentasi;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }
}
