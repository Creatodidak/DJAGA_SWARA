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
import id.creatodidak.djaga_swara.API.Models.Multi.Gubernur;
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraData;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GubernurAdapter extends RecyclerView.Adapter<GubernurAdapter.ViewHolder> {

    private final Activity mActivity;
    private final List<Gubernur> gubernurList;

    ApiService apiService;

    ProgressDialog progressDialog;

    DatabaseHelper databaseHelper;

    boolean cek, upd;
    public GubernurAdapter(List<Gubernur> gubernurList, Activity activity) {
        this.gubernurList = gubernurList;
        mActivity = activity;
    }

    public void setData(List<Gubernur> data) {
        gubernurList.clear();
        if (data != null) {
            gubernurList.addAll(data);
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
    public GubernurAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draftsuara, parent, false);
        return new GubernurAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GubernurAdapter.ViewHolder holder, int position) {
        Gubernur gubernur = gubernurList.get(position);
        databaseHelper = new DatabaseHelper(holder.itemView.getContext());
        TpsList tpsList =databaseHelper.getSprindetail(gubernur.getId_tps());

        holder.judul.setText("Laporan Jumlah Suara Pemilihan Gubernur Nomor Urut "+gubernur.getNo_urut()+" di TPS "+tpsList.getNomorTps()+" Desa "+tpsList.getNamaDes());
        holder.nourut.setText(gubernur.getNo_urut());
        holder.suara.setText(gubernur.getSuara());
        holder.nama.setText(gubernur.getCagub()+"\n"+gubernur.getCawagub());
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
                        SuaraData suaraData = new SuaraData(tpsList.getIdTps(), gubernur.getId_calon(), Integer.parseInt(gubernur.getSuara().trim()), "gubernur");
                        sendsuaradata(suaraData, v, "gubernur", gubernur.getId_calon());
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
    private void sendsuaradata(SuaraData suaraData, View v, String type, String idCalon) {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<UpdResponse> call = apiService.sendSuaraData(suaraData);
        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                progressDialog.dismiss();
                if (response.body().getMsg().equals("ok")){
                    if(databaseHelper.updateSuara(type, suaraData, "SERVER", idCalon)){
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
        return gubernurList.size();
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

}