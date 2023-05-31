package id.creatodidak.djaga_swara.API.Models.Draft;

public class Lokasi {
    private int id;
    private String id_tps;
    private String latitude;
    private String longitude;
    private String status;
    private String created_at;

    public Lokasi(int id, String id_tps, String latitude, String longitude, String status, String created_at) {
        this.id = id;
        this.id_tps = id_tps;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getId_tps() {
        return id_tps;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }
}
