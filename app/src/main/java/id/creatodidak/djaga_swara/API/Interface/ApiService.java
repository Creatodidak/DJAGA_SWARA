package id.creatodidak.djaga_swara.API.Interface;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Alluser;
import id.creatodidak.djaga_swara.API.Models.Profile;
import id.creatodidak.djaga_swara.API.Models.SprintList;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.API.Models.UpdateApp;
import id.creatodidak.djaga_swara.API.Models.UpdateFoto;
import id.creatodidak.djaga_swara.API.Models.ValidationLoginResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET("user")
    Call<Alluser> getUser();

    @GET("profile/{nrp}")
    Call<Profile> getProfile(@Path("nrp") String nrp);

    @FormUrlEncoded
    @POST("login")
    Call<ValidationLoginResponse> loginUser(
            @Field("nrp") String nrp,
            @Field("password") String password,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("profile/data/update")
    Call<UpdResponse> updateProfil(
            @Field("nrp") String NRP,
            @Field("nama")String NAMA,
            @Field("wa")String WA,
            @Field("pangkat")String PANGKAT,
            @Field("satker")String SATKER,
            @Field("fungsi")String FUNGSI,
            @Field("jabatan")String JABATAN
    );

    @GET("sprin")
    Call<List<SprintList>> getSprints();

    @FormUrlEncoded
    @POST("tps/tpsbyjob")
    Call<List<TpsList>> getTPSList(
            @Field("id_sprin")String sprin,
            @Field("nrp")String nrp
    );

    @GET("cekupd")
    Call<UpdateApp> cekupdate();

    @Multipart
    @POST("profile/data/foto")
    Call<UpdateFoto> uploadImage(
            @Part("nrp") String nrp,
            @Part MultipartBody.Part image);

    @Multipart
    @POST("/android/updatetpsloc")
    Call<UpdResponse> updateloc(
            @Part("id_tps") String id_tps,
            @Part("latitude") String latitude,
            @Part("longitude") String longitude
    );
}

