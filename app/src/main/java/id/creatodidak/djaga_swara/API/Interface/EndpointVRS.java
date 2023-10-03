package id.creatodidak.djaga_swara.API.Interface;

import id.creatodidak.djaga_swara.API.NEWMODEL.MLogin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EndpointVRS {
    @FormUrlEncoded
    @POST("auth/login")
    Call<MLogin> login(
            @Field("nrp") String nrp,
            @Field("pass") String pass
    );
}
