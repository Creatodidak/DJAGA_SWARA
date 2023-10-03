package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListdprdprovItem{

	@SerializedName("nomorurut")
	private String nomorurut;

	@SerializedName("nama")
	private String nama;

	@SerializedName("tahun")
	private String tahun;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_dapil")
	private String idDapil;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_partai")
	private String idPartai;

	@SerializedName("id_calon")
	private String idCalon;

	@SerializedName("periode")
	private String periode;

	public void setNomorurut(String nomorurut){
		this.nomorurut = nomorurut;
	}

	public String getNomorurut(){
		return nomorurut;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setTahun(String tahun){
		this.tahun = tahun;
	}

	public String getTahun(){
		return tahun;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIdDapil(String idDapil){
		this.idDapil = idDapil;
	}

	public String getIdDapil(){
		return idDapil;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setIdPartai(String idPartai){
		this.idPartai = idPartai;
	}

	public String getIdPartai(){
		return idPartai;
	}

	public void setIdCalon(String idCalon){
		this.idCalon = idCalon;
	}

	public String getIdCalon(){
		return idCalon;
	}

	public void setPeriode(String periode){
		this.periode = periode;
	}

	public String getPeriode(){
		return periode;
	}
}