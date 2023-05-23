package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class UpdateFoto {
    @SerializedName("msg")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}