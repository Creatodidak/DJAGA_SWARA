package id.creatodidak.djaga_swara.API.Models;

public class Fungsi {
    private final int id;
    private final String satfung;
    private final String satker;

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