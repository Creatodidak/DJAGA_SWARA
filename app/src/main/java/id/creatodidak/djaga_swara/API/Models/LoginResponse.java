
package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("msg")
    private String message;

    @SerializedName("tokens")
    private String tokens;

    public String getMessage() {
        return message;
    }

    public String getTokens() {
        return tokens;
    }
}
