package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListpresidenItem{

	@SerializedName("no_urut")
	private String noUrut;

	@SerializedName("tahun")
	private String tahun;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("capres")
	private String capres;

	@SerializedName("cawapres")
	private String cawapres;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_calon")
	private String idCalon;

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

	public void setCapres(String capres){
		this.capres = capres;
	}

	public String getCapres(){
		return capres;
	}

	public void setCawapres(String cawapres){
		this.cawapres = cawapres;
	}

	public String getCawapres(){
		return cawapres;
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

	public void setPeriode(String periode){
		this.periode = periode;
	}

	public String getPeriode(){
		return periode;
	}
}