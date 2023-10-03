package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import com.google.gson.annotations.SerializedName;

public class KecItem{

	@SerializedName("nama_kec")
	private String namaKec;

	@SerializedName("id_kab")
	private int idKab;

	@SerializedName("id_kec")
	private int idKec;

	public void setNamaKec(String namaKec){
		this.namaKec = namaKec;
	}

	public String getNamaKec(){
		return namaKec;
	}

	public void setIdKab(int idKab){
		this.idKab = idKab;
	}

	public int getIdKab(){
		return idKab;
	}

	public void setIdKec(int idKec){
		this.idKec = idKec;
	}

	public int getIdKec(){
		return idKec;
	}
}