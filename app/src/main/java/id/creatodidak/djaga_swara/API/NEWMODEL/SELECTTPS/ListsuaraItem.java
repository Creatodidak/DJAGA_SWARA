package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListsuaraItem{

	@SerializedName("id_tps")
	private String idTps;

	@SerializedName("type")
	private String type;

	@SerializedName("id_calon")
	private String idCalon;

	private String suara;

	private int local;

	public void setLocal(int local) {
		this.local = local;
	}

	public int getLocal() {
		return local;
	}

	public void setSuara(String suara) {
		this.suara = suara;
	}

	public String getSuara() {
		return suara;
	}

	public void setIdTps(String idTps){
		this.idTps = idTps;
	}

	public String getIdTps(){
		return idTps;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setIdCalon(String idCalon){
		this.idCalon = idCalon;
	}

	public String getIdCalon(){
		return idCalon;
	}
}