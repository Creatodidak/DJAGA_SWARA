package id.creatodidak.djaga_swara.API.NEWADAPTER;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.Penugasan.TpsavailableItem;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.TextHelper;

public class TpsSelectAdapter extends RecyclerView.Adapter<TpsSelectAdapter.TpsavailableItemViewHolder> {

    private final OnCheckedChangeListener oncheck;
    private final List<TpsavailableItem> listTps;
    private final Context context;

    public TpsSelectAdapter(Context context, List<TpsavailableItem> listTps, OnCheckedChangeListener oncheck) {
        this.context = context;
        this.listTps = listTps;
        this.oncheck = oncheck;
    }

    @NonNull
    @Override
    public TpsavailableItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.availabletps, parent, false);
        return new TpsavailableItemViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull TpsavailableItemViewHolder holder, int position) {
        TpsavailableItem item = listTps.get(position);
        holder.cb.setText("TPS "+item.getNomorTps()+" Desa "+ TextHelper.capitalize(item.getNamaDes()));
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.cb.setTextColor(Color.parseColor("#900c0b"));
                    oncheck.onCheckItem(item.getIdTps());
                }else{
                    holder.cb.setTextColor(Color.parseColor("#1b1c1e"));
                    oncheck.onUncheckItem(item.getIdTps());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTps.size();
    }

    static class TpsavailableItemViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        TpsavailableItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.checkBox);
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckItem(String idtps);
        void onUncheckItem(String idtps);
    }

}
