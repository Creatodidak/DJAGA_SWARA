package id.creatodidak.djaga_swara.API.Models;

public class Drafts {
    private final String situasi;
    private final String prediksi;

    private final String idTps;

    private final String createdAt;

    public Drafts(String situasi, String prediksi, String idTps, String createdAt) {
        this.situasi = situasi;
        this.prediksi = prediksi;
        this.idTps = idTps;
        this.createdAt = createdAt;
    }


    public String getSituasi() {
        return situasi;
    }

    public String getPrediksi() {
        return prediksi;
    }

    public String getIdTps() {
        return idTps;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}