package id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MTps{

	@SerializedName("isexist")
	private boolean isexist;

	@SerializedName("tpsavailable")
	private List<TpsavailableItem> tpsavailable;

	public void setIsexist(boolean isexist){
		this.isexist = isexist;
	}

	public boolean isIsexist(){
		return isexist;
	}

	public void setTpsavailable(List<TpsavailableItem> tpsavailable){
		this.tpsavailable = tpsavailable;
	}

	public List<TpsavailableItem> getTpsavailable(){
		return tpsavailable;
	}
}