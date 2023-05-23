package id.creatodidak.djaga_swara.API.Models;

public class Satker {
    private String id;
    private String nama;
    private String type;
    private String wilayah;

    public Satker(String id, String nama, String type, String wilayah) {
        this.id = id;
        this.nama = nama;
        this.type = type;
        this.wilayah = wilayah;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    @Override
    public String toString() {
        return nama;
    }
}