package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MKecamatan{

	@SerializedName("kec")
	private List<KecItem> kec;

	public void setKec(List<KecItem> kec){
		this.kec = kec;
	}

	public List<KecItem> getKec(){
		return kec;
	}
}