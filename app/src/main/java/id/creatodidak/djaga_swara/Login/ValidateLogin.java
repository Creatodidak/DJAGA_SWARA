package id.creatodidak.djaga_swara.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ValidateLogin {
    private static final String TAG = "ValidateLogin";

    public interface OnLoginResultListener {
        void onLoginSuccess(boolean isValidLogin);
        void onLoginFailure();
    }

    public static void login(String nrp, String password, OnLoginResultListener listener) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("nrp", nrp);
            requestData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            listener.onLoginFailure();
            return;
        }

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(mediaType, requestData.toString());

        Request request = new Request.Builder()
                .url("https://apiserver.polreslandak.id/login")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onLoginFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject responseData = new JSONObject(response.body().string());
                        boolean isValidLogin = responseData.getBoolean("isValidLogin");
                        listener.onLoginSuccess(isValidLogin);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onLoginFailure();
                    }
                } else {
                    listener.onLoginFailure();
                }
            }
        });
    }
}