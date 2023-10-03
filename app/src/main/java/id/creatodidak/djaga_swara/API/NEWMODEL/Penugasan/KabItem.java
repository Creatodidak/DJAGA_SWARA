package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import com.google.gson.annotations.SerializedName;

public class KabItem{

	@SerializedName("id_kab")
	private int idKab;

	@SerializedName("nama_kab")
	private String namaKab;

	@SerializedName("id_prov")
	private int idProv;

	public void setIdKab(int idKab){
		this.idKab = idKab;
	}

	public int getIdKab(){
		return idKab;
	}

	public void setNamaKab(String namaKab){
		this.namaKab = namaKab;
	}

	public String getNamaKab(){
		return namaKab;
	}

	public void setIdProv(int idProv){
		this.idProv = idProv;
	}

	public int getIdProv(){
		return idProv;
	}
}