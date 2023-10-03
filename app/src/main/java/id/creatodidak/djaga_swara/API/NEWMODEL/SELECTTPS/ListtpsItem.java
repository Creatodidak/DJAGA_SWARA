package id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS;

import com.google.gson.annotations.SerializedName;

public class ListtpsItem{

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("dpt_final")
	private String dptFinal;

	@SerializedName("latitude")
	private Object latitude;

	@SerializedName("lokasikotaksuara")
	private String lokasikotaksuara;

	@SerializedName("id_tps")
	private String idTps;

	@SerializedName("nomor_tps")
	private String nomorTps;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("dpt_sementara")
	private String dptSementara;

	@SerializedName("hp_kpps")
	private String hpKpps;

	@SerializedName("id_prov")
	private String idProv;

	@SerializedName("periode")
	private String periode;

	@SerializedName("id_kab")
	private String idKab;

	@SerializedName("personil")
	private String personil;

	@SerializedName("id_des")
	private String idDes;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("dpt_tetap")
	private String dptTetap;

	@SerializedName("longitude")
	private Object longitude;

	@SerializedName("id_kec")
	private String idKec;

	@SerializedName("status")
	private String status;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setDptFinal(String dptFinal){
		this.dptFinal = dptFinal;
	}

	public String getDptFinal(){
		return dptFinal;
	}

	public void setLatitude(Object latitude){
		this.latitude = latitude;
	}

	public Object getLatitude(){
		return latitude;
	}

	public void setLokasikotaksuara(String lokasikotaksuara){
		this.lokasikotaksuara = lokasikotaksuara;
	}

	public String getLokasikotaksuara(){
		return lokasikotaksuara;
	}

	public void setIdTps(String idTps){
		this.idTps = idTps;
	}

	public String getIdTps(){
		return idTps;
	}

	public void setNomorTps(String nomorTps){
		this.nomorTps = nomorTps;
	}

	public String getNomorTps(){
		return nomorTps;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDptSementara(String dptSementara){
		this.dptSementara = dptSementara;
	}

	public String getDptSementara(){
		return dptSementara;
	}

	public void setHpKpps(String hpKpps){
		this.hpKpps = hpKpps;
	}

	public String getHpKpps(){
		return hpKpps;
	}

	public void setIdProv(String idProv){
		this.idProv = idProv;
	}

	public String getIdProv(){
		return idProv;
	}

	public void setPeriode(String periode){
		this.periode = periode;
	}

	public String getPeriode(){
		return periode;
	}

	public void setIdKab(String idKab){
		this.idKab = idKab;
	}

	public String getIdKab(){
		return idKab;
	}

	public void setPersonil(String personil){
		this.personil = personil;
	}

	public String getPersonil(){
		return personil;
	}

	public void setIdDes(String idDes){
		this.idDes = idDes;
	}

	public String getIdDes(){
		return idDes;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDptTetap(String dptTetap){
		this.dptTetap = dptTetap;
	}

	public String getDptTetap(){
		return dptTetap;
	}

	public void setLongitude(Object longitude){
		this.longitude = longitude;
	}

	public Object getLongitude(){
		return longitude;
	}

	public void setIdKec(String idKec){
		this.idKec = idKec;
	}

	public String getIdKec(){
		return idKec;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}