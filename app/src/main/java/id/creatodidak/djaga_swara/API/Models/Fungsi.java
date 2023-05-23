package id.creatodidak.djaga_swara.API.Models;

public class Fungsi {
    private int id;
    private String satfung;
    private String satker;

    public Fungsi(int id, String satfung, String satker) {
        this.id = id;
        this.satfung = satfung;
        this.satker = satker;
    }

    public int getId() {
        return id;
    }

    public String getSatfung() {
        return satfung;
    }

    public String getSatker() {
        return satker;
    }

    @Override
    public String toString() {
        return satfung;
    }
}