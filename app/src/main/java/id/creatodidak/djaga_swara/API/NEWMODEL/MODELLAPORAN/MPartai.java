package id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN;

import com.google.gson.annotations.SerializedName;

public class MPartai {

	@SerializedName("id_partai")
	private String idPartai;

	@SerializedName("nomorurut")
	private String nomorurut;

	@SerializedName("nama")
	private String nama;

	private int logo;

	@SerializedName("suara")
	private String suara;

	public String getSuara() {
		return suara;
	}

	public void setSuara(String suara) {
		this.suara = suara;
	}

	public String getIdPartai() {
		return idPartai;
	}

	public void setIdPartai(String idPartai) {
		this.idPartai = idPartai;
	}

	public String getNomorurut() {
		return nomorurut;
	}

	public void setNomorurut(String nomorurut) {
		this.nomorurut = nomorurut;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getLogo() {
		return logo;
	}

	public void setLogo(int logo) {
		this.logo = logo;
	}
}