package id.creatodidak.djaga_swara.API.Models;

public class Pangkat {
    private final String id;
    private final String lengkap;
    private final String singkat;

    public Pangkat(String id, String lengkap, String singkat) {
        this.id = id;
        this.lengkap = lengkap;
        this.singkat = singkat;
    }

    public String getId() {
        return id;
    }

    public String getLengkap() {
        return lengkap;
    }

    public String getSingkat() {
        return singkat;
    }

    // Override toString() method to display the desired text in the Spinner adapter
    @Override
    public String toString() {
        return singkat;
    }
}