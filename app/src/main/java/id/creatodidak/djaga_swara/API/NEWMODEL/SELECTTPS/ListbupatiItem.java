package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListbupatiItem{

	@SerializedName("id_kab")
	private String idKab;

	@SerializedName("no_urut")
	private String noUrut;

	@SerializedName("tahun")
	private String tahun;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("cawabup")
	private String cawabup;

	@SerializedName("id_calon")
	private String idCalon;

	@SerializedName("cabup")
	private String cabup;

	@SerializedName("periode")
	private String periode;

	public void setIdKab(String idKab){
		this.idKab = idKab;
	}

	public String getIdKab(){
		return idKab;
	}

	public void setNoUrut(String noUrut){
		this.noUrut = noUrut;
	}

	public String getNoUrut(){
		return noUrut;
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

	public void setCawabup(String cawabup){
		this.cawabup = cawabup;
	}

	public String getCawabup(){
		return cawabup;
	}

	public void setIdCalon(String idCalon){
		this.idCalon = idCalon;
	}

	public String getIdCalon(){
		return idCalon;
	}

	public void setCabup(String cabup){
		this.cabup = cabup;
	}

	public String getCabup(){
		return cabup;
	}

	public void setPeriode(String periode){
		this.periode = periode;
	}

	public String getPeriode(){
		return periode;
	}
}