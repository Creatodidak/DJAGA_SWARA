package id.creatodidak.djaga_swara.API.Models.Multi;

public class SuaraData {
    private String tpsId;
    private String calonId;
    private String type;
    private int suara;

    public SuaraData(String tpsId, String calonId, int suara, String type) {
        this.tpsId = tpsId;
        this.calonId = calonId;
        this.suara = suara;
        this.type = type;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getCalonId() {
        return calonId;
    }

    public void setCalonId(String calonId) {
        this.calonId = calonId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSuara() {
        return suara;
    }

    public void setSuara(int suara) {
        this.suara = suara;
    }
}
