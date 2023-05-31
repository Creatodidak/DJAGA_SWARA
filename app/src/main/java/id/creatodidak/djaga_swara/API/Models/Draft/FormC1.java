package id.creatodidak.djaga_swara.API.Models.Draft;

public class FormC1 {
    private final int id;
    private final String id_tps;
    private final String foto;
    private final String situasi;
    private final String prediksi;
    private final String status;
    private final String created_at;

    public FormC1(int id, String id_tps, String foto, String situasi, String prediksi, String status, String created_at) {
        this.id = id;
        this.id_tps = id_tps;
        this.foto = foto;
        this.situasi = situasi;
        this.prediksi = prediksi;
        this.status = status;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getId_tps() {
        return id_tps;
    }

    public String getFoto() {
        return foto;
    }

    public String getSituasi() {
        return situasi;
    }

    public String getPrediksi() {
        return prediksi;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }
}
