package id.creatodidak.djaga_swara.API.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.TpsListOffline;
import id.creatodidak.djaga_swara.R;

public class TpsOfflineAdapter extends RecyclerView.Adapter<TpsOfflineAdapter.ViewHolder> {
    private List<TpsListOffline> tpsList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TpsOfflineAdapter(List<TpsListOffline> tpsList, Context context) {
        this.tpsList = tpsList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listtps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TpsListOffline tps = tpsList.get(position);
        holder.txtNamaDes.setText(tps.getNamaDes());
        holder.txtNamaKab.setText(tps.getNamaKab());
        holder.txtNamaKec.setText(tps.getNamaKec());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(tps);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tpsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamaDes, txtNamaKec, txtNamaKab;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaDes = itemView.findViewById(R.id.idtps);
            txtNamaKec = itemView.findViewById(R.id.lokasitps);
            txtNamaKab = itemView.findViewById(R.id.namatps);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TpsListOffline tps);
    }
}
