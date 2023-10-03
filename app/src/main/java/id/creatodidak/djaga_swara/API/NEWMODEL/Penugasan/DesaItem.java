package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import com.google.gson.annotations.SerializedName;

public class DesaItem{

	@SerializedName("id_des")
	private long idDes;

	@SerializedName("nama_des")
	private String namaDes;

	@SerializedName("id_kec")
	private int idKec;

	public void setIdDes(long idDes){
		this.idDes = idDes;
	}

	public long getIdDes(){
		return idDes;
	}

	public void setNamaDes(String namaDes){
		this.namaDes = namaDes;
	}

	public String getNamaDes(){
		return namaDes;
	}

	public void setIdKec(int idKec){
		this.idKec = idKec;
	}

	public int getIdKec(){
		return idKec;
	}
}