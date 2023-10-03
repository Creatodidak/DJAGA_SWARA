package id.creatodidak.djaga_swara.API.NEWMODEL;

public class GridLayoutData {
    private String judul;
    private int gambar;

    private String idTps;

    public String getIdTps() {
        return idTps;
    }

    public void setIdTps(String idTps) {
        this.idTps = idTps;
    }

    public int getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
