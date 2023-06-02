package id.creatodidak.djaga_swara.API.Models.Multi;

public class SuaraTidakSah {
    private int id;
    private String idTps;
    private String type;
    private int jumlah;
    private String status;

    public SuaraTidakSah(int id, String idTps, String type, int jumlah, String status) {
        this.id = id;
        this.idTps = idTps;
        this.type = type;
        this.jumlah = jumlah;
        this.status = status;
    }

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

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
