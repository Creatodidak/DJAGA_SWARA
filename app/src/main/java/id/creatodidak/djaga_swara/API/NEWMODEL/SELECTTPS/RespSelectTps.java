package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RespSelectTps{

	@SerializedName("listtps")
	private List<ListtpsItem> listtps;

	@SerializedName("listpartaiprov")
	private List<ListpartaiprovItem> listpartaiprov;

	@SerializedName("listsuarapartai")
	private List<ListsuarapartaiItem> listsuarapartai;

	@SerializedName("listdprdprov")
	private List<ListdprdprovItem> listdprdprov;

	@SerializedName("listpartainas")
	private List<ListpartainasItem> listpartainas;

	@SerializedName("listbupati")
	private List<ListbupatiItem> listbupati;

	@SerializedName("listdprri")
	private List<ListdprriItem> listdprri;

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("listsuara")
	private List<ListsuaraItem> listsuara;

	@SerializedName("listkades")
	private List<ListkadesItem> listkades;

	@SerializedName("listdprdkab")
	private List<ListdprdkabItem> listdprdkab;

	@SerializedName("berhasil")
	private boolean berhasil;

	@SerializedName("listgubernur")
	private List<ListgubernurItem> listgubernur;

	@SerializedName("listpartaikab")
	private List<ListpartaikabItem> listpartaikab;

	@SerializedName("listpresiden")
	private List<ListpresidenItem> listpresiden;

	@SerializedName("listdpdri")
	private List<ListdpdriItem> listdpdri;

	public void setListtps(List<ListtpsItem> listtps){
		this.listtps = listtps;
	}

	public List<ListtpsItem> getListtps(){
		return listtps;
	}

	public void setListpartaiprov(List<ListpartaiprovItem> listpartaiprov){
		this.listpartaiprov = listpartaiprov;
	}

	public List<ListpartaiprovItem> getListpartaiprov(){
		return listpartaiprov;
	}

	public void setListsuarapartai(List<ListsuarapartaiItem> listsuarapartai){
		this.listsuarapartai = listsuarapartai;
	}

	public List<ListsuarapartaiItem> getListsuarapartai(){
		return listsuarapartai;
	}

	public void setListdprdprov(List<ListdprdprovItem> listdprdprov){
		this.listdprdprov = listdprdprov;
	}

	public List<ListdprdprovItem> getListdprdprov(){
		return listdprdprov;
	}

	public void setListpartainas(List<ListpartainasItem> listpartainas){
		this.listpartainas = listpartainas;
	}

	public List<ListpartainasItem> getListpartainas(){
		return listpartainas;
	}

	public void setListbupati(List<ListbupatiItem> listbupati){
		this.listbupati = listbupati;
	}

	public List<ListbupatiItem> getListbupati(){
		return listbupati;
	}

	public void setListdprri(List<ListdprriItem> listdprri){
		this.listdprri = listdprri;
	}

	public List<ListdprriItem> getListdprri(){
		return listdprri;
	}

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setListsuara(List<ListsuaraItem> listsuara){
		this.listsuara = listsuara;
	}

	public List<ListsuaraItem> getListsuara(){
		return listsuara;
	}

	public void setListkades(List<ListkadesItem> listkades){
		this.listkades = listkades;
	}

	public List<ListkadesItem> getListkades(){
		return listkades;
	}

	public void setListdprdkab(List<ListdprdkabItem> listdprdkab){
		this.listdprdkab = listdprdkab;
	}

	public List<ListdprdkabItem> getListdprdkab(){
		return listdprdkab;
	}

	public void setBerhasil(boolean berhasil){
		this.berhasil = berhasil;
	}

	public boolean isBerhasil(){
		return berhasil;
	}

	public void setListgubernur(List<ListgubernurItem> listgubernur){
		this.listgubernur = listgubernur;
	}

	public List<ListgubernurItem> getListgubernur(){
		return listgubernur;
	}

	public void setListpartaikab(List<ListpartaikabItem> listpartaikab){
		this.listpartaikab = listpartaikab;
	}

	public List<ListpartaikabItem> getListpartaikab(){
		return listpartaikab;
	}

	public void setListpresiden(List<ListpresidenItem> listpresiden){
		this.listpresiden = listpresiden;
	}

	public List<ListpresidenItem> getListpresiden(){
		return listpresiden;
	}

	public void setListdpdri(List<ListdpdriItem> listdpdri){
		this.listdpdri = listdpdri;
	}

	public List<ListdpdriItem> getListdpdri(){
		return listdpdri;
	}
}