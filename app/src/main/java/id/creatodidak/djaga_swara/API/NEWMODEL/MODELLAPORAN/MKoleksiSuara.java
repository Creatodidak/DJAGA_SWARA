package id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN;

import com.google.gson.annotations.SerializedName;

public class MKoleksiSuara {

	@SerializedName("id_tps")
	private String idTps;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("type")
	private String type;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("local")
	private int local;

	@SerializedName("id_calon")
	private String idCalon;

	public void setIdCalon(String idCalon) {
		this.idCalon = idCalon;
	}

	public String getIdCalon() {
		return idCalon;
	}

	public void setIdTps(String idTps) {
		this.idTps = idTps;
	}

	public String getIdTps() {
		return idTps;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}

	public String getKategori() {
		return kategori;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setJumlah(String jumlah) {
		this.jumlah = jumlah;
	}

	public String getJumlah() {
		return jumlah;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public int getLocal() {
		return local;
	}
}