package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListgubernurItem{

	@SerializedName("no_urut")
	private String noUrut;

	@SerializedName("tahun")
	private String tahun;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("cawagub")
	private String cawagub;

	@SerializedName("cagub")
	private String cagub;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_calon")
	private String idCalon;

	@SerializedName("id_prov")
	private String idProv;

	@SerializedName("periode")
	private String periode;

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

	public void setCawagub(String cawagub){
		this.cawagub = cawagub;
	}

	public String getCawagub(){
		return cawagub;
	}

	public void setCagub(String cagub){
		this.cagub = cagub;
	}

	public String getCagub(){
		return cagub;
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

	public void setIdCalon(String idCalon){
		this.idCalon = idCalon;
	}

	public String getIdCalon(){
		return idCalon;
	}

	public void setIdProv(String idProv){
		this.idProv = idProv;
	}

	public String getIdProv(){
		return idProv;
	}

	public void setPeriode(String periode){
		this.periode = periode;
	}

	public String getPeriode(){
		return periode;
	}
}