package id.creatodidak.djaga_swara.API.Models;

public class Jabatan {
    private int id;
    private String jabatan;
    private String level;
    private String satker;

    public Jabatan(int id, String jabatan, String level, String satker) {
        this.id = id;
        this.jabatan = jabatan;
        this.level = level;
        this.satker = satker;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getLevel() {
        return level;
    }

    public String getSatker() {
        return satker;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setSatker(String satker) {
        this.satker = satker;
    }

    @Override
    public String toString() {
        return jabatan;
    }
}