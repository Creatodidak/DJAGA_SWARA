package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class ValidationLoginResponse {

    @SerializedName("msg")
    private String message;

    public String getMessage() {
        return message;
    }
}