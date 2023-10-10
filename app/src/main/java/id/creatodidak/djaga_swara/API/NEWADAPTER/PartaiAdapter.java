package id.creatodidak.djaga_swara.API.NEWADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFigur;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MPartai;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.CDialog;

public class PartaiAdapter extends RecyclerView.Adapter<PartaiAdapter.ViewHolder> {

    private final List<MPartai> partaiList;
    private final Context context;
    private final String type;
    private final String idtps;
    private final DBHelper db;

    public PartaiAdapter(Context context, List<MPartai> partaiList, String type, String IDTPS) {
        this.context = context;
        this.partaiList = partaiList;
        this.type = type;
        this.idtps = IDTPS;
        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.legislatifform, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        MPartai data = partaiList.get(position);
        List<MFigur> figurList = db.getFigurList(type, data.getIdPartai(), idtps);

        FigurAdapter adp = new FigurAdapter(context, figurList);

        h.itemView.setTag(data.getIdPartai());
        h.wrapper.setTag(data.getIdPartai());
        h.logopartai.setImageResource(data.getLogo());
        h.logopartai.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        h.logopartai.setDrawingCacheEnabled(true);
        h.tvNPartai.setText("PARTAI "+data.getNama().replace("PARTAI ", ""));
        h.tvNUPartai.setText("NO URUT "+data.getNomorurut());
        LinearLayoutManager lm = new LinearLayoutManager(context);
        h.rvFigur.setLayoutManager(lm);
        h.rvFigur.setAdapter(adp);

        if(!TextUtils.isEmpty(data.getSuara())) {
            h.etSuaraPartai.setText(data.getSuara());
            h.etSuaraPartai.setEnabled(false);
            h.btn.setVisibility(View.GONE);
        }

        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CDialog.up(
                        context,
                        "Konfirmasi",
                        "Data yang sudah disimpan tidak dapat diedit kembali, lanjutkan menyimpan data?",
                        true, false, false,
                        "BATAL", "LANJUTKAN", "",
                        new CDialog.AlertDialogListener() {
                            @Override
                            public void onOpt1(AlertDialog alert) {
                                alert.dismiss();
                                if(!TextUtils.isEmpty(h.etSuaraPartai.getText())) {
                                    db.saveSuaraPartai(idtps, type, data.getIdPartai(), h.etSuaraPartai.getText().toString());

                                    int total = 0;
                                    for (MFigur figur : figurList) {
                                        View vs = h.rvFigur.findViewWithTag(figur.getIdCalon());
                                        EditText et = vs.findViewById(R.id.etSuara);
                                        if (!TextUtils.isEmpty(et.getText())) {
                                            db.saveSuara(idtps, figur.getIdCalon(), et.getText().toString());
                                            et.setEnabled(false);
                                            total++;
                                        } else {
                                            CDialog.up(
                                                    context,
                                                    "Peringatan",
                                                    "Semua suara Figur pada Partai " + data.getNama().replace("PARTAI ", "") + " Harus Di Isi!",
                                                    false, false, false,
                                                    "", "PERBAIKI", "",
                                                    new CDialog.AlertDialogListener() {
                                                        @Override
                                                        public void onOpt1(AlertDialog alert) {
                                                            alert.dismiss();
                                                        }

                                                        @Override
                                                        public void onOpt2(AlertDialog alert) {

                                                        }

                                                        @Override
                                                        public void onCancel(AlertDialog alert) {

                                                        }
                                                    }
                                            ).show();
                                            break;
                                        }


                                    }

                                    if (total == figurList.size()) {
                                        CDialog.up(
                                                context,
                                                "Informasi",
                                                "Berhasil Menyimpan Suara Partai " + data.getNama().replace("PARTAI ", "") + "!",
                                                false, false, false,
                                                "", "LANJUTKAN", "",
                                                new CDialog.AlertDialogListener() {
                                                    @Override
                                                    public void onOpt1(AlertDialog alert) {
                                                        alert.dismiss();
                                                        h.btn.setVisibility(View.GONE);
                                                        h.etSuaraPartai.setEnabled(false);
                                                    }

                                                    @Override
                                                    public void onOpt2(AlertDialog alert) {

                                                    }

                                                    @Override
                                                    public void onCancel(AlertDialog alert) {

                                                    }
                                                }
                                        ).show();
                                    }
                                }else{
                                    CDialog.up(
                                            context,
                                            "Peringatan",
                                            "Jumlah Perolehan Suara Partai " + data.getNama().replace("PARTAI ", "") + " Harus Di Isi!",
                                            false, false, false,
                                            "", "PERBAIKI", "",
                                            new CDialog.AlertDialogListener() {
                                                @Override
                                                public void onOpt1(AlertDialog alert) {
                                                    alert.dismiss();
                                                }

                                                @Override
                                                public void onOpt2(AlertDialog alert) {

                                                }

                                                @Override
                                                public void onCancel(AlertDialog alert) {

                                                }
                                            }
                                    ).show();
                                }
                            }

                            @Override
                            public void onOpt2(AlertDialog alert) {

                            }

                            @Override
                            public void onCancel(AlertDialog alert) {
                                alert.dismiss();
                            }
                        }
                ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return partaiList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNUPartai;
        TextView tvNPartai;
        EditText etSuaraPartai;
        RecyclerView rvFigur;
        ImageView logopartai;
        LinearLayout wrapper;
        Button btn;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvNUPartai = v.findViewById(R.id.tvNoUrutPartai);
            tvNPartai = v.findViewById(R.id.tvNamaPartai);
            etSuaraPartai = v.findViewById(R.id.etSuaraPartai);
            logopartai = v.findViewById(R.id.logopartai);
            rvFigur = v.findViewById(R.id.rvFigur);
            wrapper = v.findViewById(R.id.wrapper);
            btn = v.findViewById(R.id.btnSimpanDataPerPartai);
        }
    }
}
