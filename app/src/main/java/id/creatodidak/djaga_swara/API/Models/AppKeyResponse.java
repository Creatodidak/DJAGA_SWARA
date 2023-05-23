package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class AppKeyResponse {
    @SerializedName("app_key")
    private String appKey;

    public String getAppKey() {
        return appKey;
    }
}