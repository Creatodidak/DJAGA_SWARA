package id.creatodidak.djaga_swara.API.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.Draft.Lapwal;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LapwalAdapter extends RecyclerView.Adapter<LapwalAdapter.ViewHolder> {
    private final List<Lapwal> LapwalList;
    private final Activity mActivity;

    ApiService apiService;

    ProgressDialog progressDialog;

    DatabaseHelper databaseHelper;

    boolean cek, upd;
    public LapwalAdapter(List<Lapwal> LapwalList, Activity activity) {
        this.LapwalList = LapwalList;
        mActivity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoImageView;
        TextView judul, tanggal;
        LinearLayout data, kirim;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImageView = itemView.findViewById(R.id.fotoDraft);
            judul = itemView.findViewById(R.id.judulDraft);
            tanggal = itemView.findViewById(R.id.tanggalDraft);
            data = itemView.findViewById(R.id.datadraft);
            kirim = itemView.findViewById(R.id.kirimdata);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draft, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lapwal Lapwal = LapwalList.get(position);
        databaseHelper = new DatabaseHelper(holder.itemView.getContext());
        TpsList tpsList = databaseHelper.getSprindetail(Lapwal.getId_tps());

        holder.judul.setText("Laporan Pengawalan Kotak Suara TPS " + tpsList.getNomorTps() + " Desa "+tpsList.getNamaDes()+" dari KPPS ke PPS Desa "+tpsList.getNamaDes());
        holder.tanggal.setText(Lapwal.getCreated_at());
        Glide.with(holder.itemView.getContext())
                .load(Lapwal.getFoto())
                .into(holder.fotoImageView);

        TextView situasiTextView = new TextView(holder.itemView.getContext());
        TextView prediksiTextView = new TextView(holder.itemView.getContext());
        situasiTextView.setText("SITUASI:\n" + Lapwal.getSituasi());
        prediksiTextView.setText("PREDIKSI GANGGUAN KAMTIBMAS:\n" + Lapwal.getPrediksi());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16);

        situasiTextView.setLayoutParams(layoutParams);
        prediksiTextView.setLayoutParams(layoutParams);
        holder.data.addView(situasiTextView);
        holder.data.addView(prediksiTextView);
        holder.kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Konfirmasi");
                builder.setMessage("Kirim data ini?");
                builder.setIcon(R.drawable.logosmall); // Ganti dengan ikon yang sesuai

                builder.setPositiveButton("Ya, kirim...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showprogress("Mengupload data ke server", v);
                        String imageFilePath = getLocalFilePathFromUrl(Lapwal.getFoto());


                        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), new File(imageFilePath));
                        MultipartBody.Part image = MultipartBody.Part.createFormData("image", new File(imageFilePath).getName(),
                                imageRequestBody);


                        apiService = ApiClient.getClient().create(ApiService.class);
                        Call<UpdResponse> call = apiService.uploadFoto(tpsList.getIdTps(), "lapwal", Lapwal.getSituasi(),
                                Lapwal.getPrediksi(), image);
                        call.enqueue(new Callback<UpdResponse>() {
                            @Override
                            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                                if (response.body() != null) {
                                    progressDialog.dismiss();
                                    if (response.body().getMsg().equals("Upload Berhasil!")) {
                                        updatedb("YES, ALL", v, Lapwal.getId_tps());
                                    }else{
                                        notifikasi("GAGAL", "Data gagal diupload ke server!", true, v);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdResponse> call, Throwable t) {
                                notifikasi("GAGAL", "Data gagal diupload ke server!\n"+t.getLocalizedMessage(),
                                        true, v);

                                Log.e("onFailure: ", t.getMessage());
                            }
                        });
                    }
                });

                // Tombol "Batalkan"
                builder.setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return LapwalList.size();
    }

    private void showprogress(String msg, View view) {
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void updatedb(String msg, View v, String id_tps) {
        showprogress("Update data di local", v);
        Log.i("updatedb: ", msg+" xx "+id_tps);
        cek = databaseHelper.updateTpsActivity(id_tps, "lapwal", msg);
        upd = databaseHelper.updateStatus("lapwal", id_tps);

        if(cek){
            if(upd){
                notifikasi("BERHASIL", "Data berhasil dikirim!", true, v);
            }else{
                notifikasi("GAGAL", "Database Local gagal diupdate namun sudah terupdate di Server!", true, v);
            }
        }else{
            notifikasi("GAGAL", "Database Local gagal diupdate namun sudah terupdate di Server!", true, v);
        }
    }

    private void notifikasi(String info, String s, Boolean cancel, View v) {
        progressDialog.dismiss();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
        if(cancel){
            builder.setTitle(info)
                    .setMessage(s)
                    .setIcon(R.drawable.logosmall)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            v.invalidate();
                            recreateActivity();
                        }
                    })
                    .show();
        }else{
            builder.setTitle(info)
                    .setMessage(s)
                    .setIcon(R.drawable.logosmall)
                    .setCancelable(false)
                    .show();
        }

    }

    private String getLocalFilePathFromUrl(String imageUrl) {
        Uri uri = Uri.parse(imageUrl);
        String path = uri.getPath();
        return path;
    }

    private void recreateActivity() {
        mActivity.recreate();
    }
}
