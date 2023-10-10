package id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN;

import com.google.gson.annotations.SerializedName;

public class MFigur {

	@SerializedName("id_calon")
	private String idCalon;
	@SerializedName("id_partai")
	private String idPartai;

	@SerializedName("nomorurut")
	private String nomorurut;

	@SerializedName("nama")
	private String nama;

	@SerializedName("suara")
	private String suara;

	public String getSuara() {
		return suara;
	}

	public void setSuara(String suara) {
		this.suara = suara;
	}

	public String getIdCalon() {
		return idCalon;
	}

	public void setIdCalon(String idCalon) {
		this.idCalon = idCalon;
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

}