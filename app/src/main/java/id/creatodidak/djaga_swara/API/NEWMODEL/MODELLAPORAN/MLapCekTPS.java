package id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN;

public class MLapCekTPS {
    private int id; // ID dari data
    private String idTps;
    private String fakta;
    private String analisa;
    private String prediksi;
    private String rekomendasi;
    private String latitude;
    private String longitude;
    private String dokumentasi;
    private int local;

    public MLapCekTPS() {
    }

    public MLapCekTPS(String idTps, String fakta, String analisa, String prediksi, String rekomendasi, String latitude, String longitude, String dokumentasi, int local) {
        this.idTps = idTps;
        this.fakta = fakta;
        this.analisa = analisa;
        this.prediksi = prediksi;
        this.rekomendasi = rekomendasi;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getFakta() {
        return fakta;
    }

    public void setFakta(String fakta) {
        this.fakta = fakta;
    }

    public String getAnalisa() {
        return analisa;
    }

    public void setAnalisa(String analisa) {
        this.analisa = analisa;
    }

    public String getPrediksi() {
        return prediksi;
    }

    public void setPrediksi(String prediksi) {
        this.prediksi = prediksi;
    }

    public String getRekomendasi() {
        return rekomendasi;
    }

    public void setRekomendasi(String rekomendasi) {
        this.rekomendasi = rekomendasi;
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
