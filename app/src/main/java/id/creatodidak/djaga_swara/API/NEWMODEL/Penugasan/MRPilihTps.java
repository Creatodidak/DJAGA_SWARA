package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MRPilihTps{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("berhasil")
	private boolean berhasil;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setBerhasil(boolean berhasil){
		this.berhasil = berhasil;
	}

	public boolean isBerhasil(){
		return berhasil;
	}
}