package id.creatodidak.djaga_swara.API.Models.Draft;

public class FormC1 {
    private final int id;
    private final String id_tps;
    private final String foto;
    private final String type;
    private final String status;

    public FormC1(int id, String id_tps, String foto, String type, String status) {
        this.id = id;
        this.id_tps = id_tps;
        this.foto = foto;
        this.type = type;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

}
