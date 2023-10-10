package id.creatodidak.djaga_swara.API.Interface;

import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MKoleksiSuara;
import id.creatodidak.djaga_swara.API.NEWMODEL.MResponseServer;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MDesa;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MKabupaten;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MKecamatan;
import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.MTps;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.RespSelectTps;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Endpoint {

    @FormUrlEncoded
    @POST("penugasan/getkabupaten")
    Call<MKabupaten> getKab(@Field("id") String id);

    @FormUrlEncoded
    @POST("penugasan/getkecamatan")
    Call<MKecamatan> getKec(@Field("id") String id);

    @FormUrlEncoded
    @POST("penugasan/getdesa")
    Call<MDesa> getDes(@Field("id") String id);

    @FormUrlEncoded
    @POST("penugasan/gettps")
    Call<MTps> getTps(
            @Field("periode") String periode,
            @Field("idprov") String idprov,
            @Field("idkab") String idkab,
            @Field("idkec") String idkec,
            @Field("iddes") String idDes
    );

    @FormUrlEncoded
    @POST("penugasan/updtpsowner")
    Call<RespSelectTps> pilihTps(
            @Field("nrp") String nrp,
            @Field("idtps") String idtps,
            @Field("periode") String periode,
            @Field("idprov") String idprov,
            @Field("idkab") String idkab,
            @Field("idkec") String idkec,
            @Field("iddes") String idDes
    );

    @Multipart
    @POST("laporan/addlaporancektps")
    Call<MResponseServer> uploadLaporanCekTPS(
            @Part("idtps") String idtps,
            @Part("fakta") String fakta,
            @Part("analisa") String analisa,
            @Part("prediksi") String prediksi,
            @Part("rekomendasi") String rekomendasi,
            @Part("latitude") String latitude,
            @Part("longitude") String longitude,
            @Part List<MultipartBody.Part> imageParts);

    @Multipart
    @POST("laporan/addlaporanpamtps")
    Call<MResponseServer> uploadLaporanPamTPS(
            @Part("idtps") String idtps,
            @Part("fakta") String fakta,
            @Part("analisa") String analisa,
            @Part("prediksi") String prediksi,
            @Part("rekomendasi") String rekomendasi,
            @Part List<MultipartBody.Part> imageParts);

    @Multipart
    @POST("laporan/addlaporanwaltps")
    Call<MResponseServer> uploadLaporanWalTPS(
            @Part("idtps") String idtps,
            @Part("fakta") String fakta,
            @Part("analisa") String analisa,
            @Part("prediksi") String prediksi,
            @Part("rekomendasi") String rekomendasi,
            @Part List<MultipartBody.Part> imageParts);

    @Multipart
    @POST("laporan/addlaporanserahtps")
    Call<MResponseServer> uploadLaporanSerahTPS(
            @Part("idtps") String idtps,
            @Part("fakta") String fakta,
            @Part("analisa") String analisa,
            @Part("prediksi") String prediksi,
            @Part("rekomendasi") String rekomendasi,
            @Part List<MultipartBody.Part> imageParts);

    @Multipart
    @POST("laporan/addformc1")
    Call<MResponseServer> uploadFormC1(
            @Part("idtps") String idtps,
            @Part("type") String type,
            @Part MultipartBody.Part imagePart
    );

    @FormUrlEncoded
    @POST("laporan/upddpt")
    Call<MResponseServer> uploadDpt(
            @Field("idtps") String idtps,
            @Field("dpt") String dptFinal
    );

    @POST("laporan/datasuara")
    Call<MResponseServer> uploadSuara(
            @Body List<MKoleksiSuara> data
    );
}
