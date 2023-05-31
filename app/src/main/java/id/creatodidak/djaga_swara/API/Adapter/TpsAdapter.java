package id.creatodidak.djaga_swara.API.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.R;

public class TpsAdapter extends RecyclerView.Adapter<TpsAdapter.ViewHolder> {
    private final List<TpsList> tpsList;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public TpsAdapter(List<TpsList> tpsList, Context context) {
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
        TpsList tps = tpsList.get(position);
        holder.txtNamatps.setText("TPS "+tps.getNomorTps());
        holder.txtIdtps.setText("ID: "+tps.getIdTps());
        holder.txtLokasitps.setText(tps.getNamaKab()+", KEC. "+tps.getNamaKec()+", DESA "+tps.getNamaDes());
        holder.txtStatustps.setText(tps.getStatus());
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
        TextView txtIdtps, txtLokasitps, txtNamatps, txtStatustps;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdtps = itemView.findViewById(R.id.idtps);
            txtLokasitps = itemView.findViewById(R.id.lokasitps);
            txtStatustps = itemView.findViewById(R.id.statustps);
            txtNamatps = itemView.findViewById(R.id.namatps);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TpsList tps);
    }
}
