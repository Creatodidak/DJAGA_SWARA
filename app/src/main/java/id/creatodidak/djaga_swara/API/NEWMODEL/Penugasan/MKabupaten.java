package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MKabupaten{

	@SerializedName("kab")
	private List<KabItem> kab;

	public void setKab(List<KabItem> kab){
		this.kab = kab;
	}

	public List<KabItem> getKab(){
		return kab;
	}
}