package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MDesa{

	@SerializedName("desa")
	private List<DesaItem> desa;

	public void setDesa(List<DesaItem> desa){
		this.desa = desa;
	}

	public List<DesaItem> getDesa(){
		return desa;
	}
}