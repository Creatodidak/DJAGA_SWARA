package id.creatodidak.djaga_swara.API.NEWMODEL;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MResponseServer {

	@SerializedName("msg")
	private String msg;

	@SerializedName("status")
	private boolean status;

	public String getMsg(){
		return msg;
	}

	public boolean isStatus(){
		return status;
	}
}