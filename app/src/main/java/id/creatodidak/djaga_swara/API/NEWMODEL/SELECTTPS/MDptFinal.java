package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class MDptFinal {

	@SerializedName("id_tps")
	private String idTps;

	@SerializedName("dptfinal")
	private String dptFinal;

	@SerializedName("local")
	private int local;

	public String getDptFinal() {
		return dptFinal;
	}

	public void setDptFinal(String dptFinal) {
		this.dptFinal = dptFinal;
	}

	public int getLocal() {
		return local;
	}

	public String getIdTps() {
		return idTps;
	}

	public void setIdTps(String idTps) {
		this.idTps = idTps;
	}

	public void setLocal(int local) {
		this.local = local;
	}
}