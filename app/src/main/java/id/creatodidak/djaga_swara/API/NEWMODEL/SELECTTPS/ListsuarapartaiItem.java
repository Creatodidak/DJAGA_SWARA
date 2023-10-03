package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListsuarapartaiItem{

	@SerializedName("id_tps")
	private String idTps;

	@SerializedName("type")
	private String type;

	@SerializedName("id_partai")
	private String idPartai;

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

	public void setIdPartai(String idPartai){
		this.idPartai = idPartai;
	}

	public String getIdPartai(){
		return idPartai;
	}
}