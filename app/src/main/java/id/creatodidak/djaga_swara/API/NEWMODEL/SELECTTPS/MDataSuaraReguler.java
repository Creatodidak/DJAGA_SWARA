package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class MDataSuaraReguler {
	private String idcalon;
	private String noUrut;
	private String calon1;
	private String calon2;
	private String jSuara;
	private int local;

	public void setNoUrut(String noUrut) {
		this.noUrut = noUrut;
	}

	public String getNoUrut() {
		return noUrut;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public int getLocal() {
		return local;
	}

	public void setIdcalon(String idcalon) {
		this.idcalon = idcalon;
	}

	public String getIdcalon() {
		return idcalon;
	}

	public void setCalon1(String calon1) {
		this.calon1 = calon1;
	}

	public String getCalon1() {
		return calon1;
	}

	public void setCalon2(String calon2) {
		this.calon2 = calon2;
	}

	public String getCalon2() {
		return calon2;
	}

	public void setjSuara(String jSuara) {
		this.jSuara = jSuara;
	}

	public String getjSuara() {
		return jSuara;
	}
}