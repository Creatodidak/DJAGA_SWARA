package id.creatodidak.djaga_swara.API.Adapter.Draft;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraTidakSah;
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraData;
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraTidakSah;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaraTidakSahAdapter extends RecyclerView.Adapter<SuaraTidakSahAdapter.ViewHolder> {

    private final Activity mActivity;
    private final List<SuaraTidakSah> suaratidaksahList;

    ApiService apiService;

    ProgressDialog progressDialog;

    DatabaseHelper databaseHelper;

    boolean cek, upd;

    public SuaraTidakSahAdapter(List<SuaraTidakSah> suaratidaksahList, Activity activity) {
        this.suaratidaksahList = suaratidaksahList;
        mActivity = activity;
    }

    public void setData(List<SuaraTidakSah> data) {
        suaratidaksahList.clear();
        if (data != null) {
            suaratidaksahList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView judul, nourut, nama, suara;
        LinearLayout kirimdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judulDraft);
            nourut = itemView.findViewById(R.id.tvNourut);
            nama = itemView.findViewById(R.id.tvNama);
            suara = itemView.findViewById(R.id.etSuara);
            kirimdata = itemView.findViewById(R.id.kirimdata);
        }
    }

    @NonNull
    @Override
    public SuaraTidakSahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draftsuara, parent, false);
        return new SuaraTidakSahAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuaraTidakSahAdapter.ViewHolder holder, int position) {
        SuaraTidakSah suaratidaksah = suaratidaksahList.get(position);
        databaseHelper = new DatabaseHelper(holder.itemView.getContext());
        TpsList tpsList =databaseHelper.getSprindetail(suaratidaksah.getIdTps());

        holder.judul.setText("Laporan Jumlah Suara Tidak Sah dalam Pemilihan "+suaratidaksah.getType()+" di TPS "+tpsList.getNomorTps()+" Desa "+tpsList.getNamaDes());
        holder.suara.setText(String.valueOf(suaratidaksah.getJumlah()));
        holder.nama.setText("TPS "+tpsList.getNomorTps()+" Desa "+tpsList.getNamaDes());

        holder.kirimdata.setOnClickListener(new View.OnClickListener() {
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
                        showprogress("Mengirim data ke server...", v);
                        sendsuaradata(tpsList.getIdTps(), suaratidaksah.getJumlah(), suaratidaksah.getType(), suaratidaksah.getId(), v);
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

    private void sendsuaradata(String tpsId, int suaratidaksah, String type, int id, View v) {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<UpdResponse> call = apiService.sendTidaksah(tpsId, suaratidaksah, type);
        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                progressDialog.dismiss();
                if (response.body().getMsg().equals("ok")){
                    if(databaseHelper.updateSuaraTidakSah(id)){
                        notifikasi("BERHASIL", "Data Berhasil Dikirim Ke Server!", true, v);
                    }else{
                        notifikasi("GAGAL", "Data Berhasil Dikirim, Namun Gagal Menghapus Dari Draft!", true, v);
                    }
                }else{
                    notifikasi("GAGAL", "Data gagal dikirim, tidak ada perubahan pada Draft!", true, v);
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                progressDialog.dismiss();
                notifikasi("GAGAL", "Data gagal dikirim, tidak ada perubahan pada Draft!", true, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return suaratidaksahList.size();
    }
    private void showprogress(String msg, View view) {
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void recreateActivity() {
        mActivity.recreate();
    }
    private void notifikasi(String info, String s, Boolean cancel, View v) {
        progressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
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

}
