package id.creatodidak.djaga_swara.API.NEWMODEL;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MLogin{

	@SerializedName("msg")
	private String msg;

	@SerializedName("logindetail")
	private List<LogindetailItem> logindetail;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setLogindetail(List<LogindetailItem> logindetail){
		this.logindetail = logindetail;
	}

	public List<LogindetailItem> getLogindetail(){
		return logindetail;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}