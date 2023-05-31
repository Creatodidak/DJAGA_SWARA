package id.creatodidak.djaga_swara.API.Models.Draft;

public class DraftDpt {
    private int id;
    private String id_tps;
    private String dpt_final;
    private String keterangan;
    private String status;

    public DraftDpt(String id_tps, String dpt_final, String keterangan, String status) {
        this.id_tps = id_tps;
        this.dpt_final = dpt_final;
        this.keterangan = keterangan;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTps() {
        return id_tps;
    }

    public void setIdTps(String id_tps) {
        this.id_tps = id_tps;
    }

    public String getDptFinal() {
        return dpt_final;
    }

    public void setDptFinal(String dpt_final) {
        this.dpt_final = dpt_final;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
