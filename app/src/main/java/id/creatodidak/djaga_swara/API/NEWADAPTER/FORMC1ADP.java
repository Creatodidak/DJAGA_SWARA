package id.creatodidak.djaga_swara.API.NEWADAPTER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFormC1;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;

public class FORMC1ADP extends RecyclerView.Adapter<FORMC1ADP.ViewHolder> {

    private final List<MFormC1> dataList;
    private final Context context;
    private final String IDTPS;
    DBHelper dbHelper;
    SharedPreferences sh;
    private final FORMC1ADP.OnItemClickListener onItemClickListener;

    public FORMC1ADP(Context context, String idTps, List<MFormC1> dataList, FORMC1ADP.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.IDTPS = idTps;
        this.onItemClickListener = onItemClickListener;
        dbHelper = new DBHelper(context);
        sh = context.getSharedPreferences("DATAMANAGER", Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemformc1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MFormC1 type = dataList.get(position);
        holder.tvJudulFormC1.setText("FORM C1 "+type.getType());
        if(dbHelper.cekFormC1Each(IDTPS, type.getType()).equals("BELUM ADA")){
            holder.ivFormC1.setVisibility(View.GONE);
            holder.btFormC1.setVisibility(View.GONE);
        }else if(dbHelper.cekFormC1Each(IDTPS, type.getType()).equals("LOCAL")){
            holder.btTambahDokumentasi.setVisibility(View.GONE);
            showC1(holder.btFormC1, holder.ivFormC1, type.getType(), true);
        }else if(dbHelper.cekFormC1Each(IDTPS, type.getType()).equals("SERVER")){
            holder.btTambahDokumentasi.setVisibility(View.GONE);
            showC1(holder.btFormC1, holder.ivFormC1, type.getType(), false);
        }

        holder.btTambahDokumentasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick((int) getItemId(position));
            }
        });
    }

    private void showC1(Button btFormC1, ImageView ivFormC1, String type, boolean b) {
        if(b){
            btFormC1.setVisibility(View.VISIBLE);
            btFormC1.setText("KIRIM DRAFT");
        }else{
            btFormC1.setVisibility(View.GONE);
        }

        MFormC1 data = dbHelper.getFormC1(IDTPS, type);
        File file = new File(data.getDokumentasi());

        Glide.with(context)
                .load(Uri.parse(data.getDokumentasi()))
                .into(ivFormC1);
        
        btFormC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.kirimDraft(data.getIdTps(), data.getType());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btTambahDokumentasi;
        ImageView ivFormC1;
        Button btFormC1;
        TextView tvJudulFormC1;

        public ViewHolder(View itemView) {
            super(itemView);
            btTambahDokumentasi = itemView.findViewById(R.id.btTambahDokumentasi);
            ivFormC1 = itemView.findViewById(R.id.ivFormC1);
            btFormC1 = itemView.findViewById(R.id.btFormC1);
            tvJudulFormC1 = itemView.findViewById(R.id.tvJudulFormC1);
        }
    }

    public interface OnItemClickListener {
        void onClick(int pos);

        void kirimDraft(String idTps, String type);
    }
}
