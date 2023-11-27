package id.creatodidak.djaga_swara.API.NEWADAPTER;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.MDataSuaraReguler;
import id.creatodidak.djaga_swara.R;

public class SuaraRegAdp extends RecyclerView.Adapter<SuaraRegAdp.ViewHolder> {

    private final List<MDataSuaraReguler> suaraList;
    private final Context context;

    public SuaraRegAdp(Context context, List<MDataSuaraReguler> suaraList) {
        this.context = context;
        this.suaraList = suaraList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MDataSuaraReguler suara = suaraList.get(position);
        Log.i("DATA TAG => ", suara.getIdcalon());
        holder.itemView.setTag(suara.getIdcalon());
        holder.tvNoUrut.setText(suara.getNoUrut());
        if(suara.getCalon2() == null){
            holder.tvNama.setText(suara.getCalon1()+"\n"+suara.getIdcalon());
        }else{
            holder.tvNama.setText(suara.getCalon1()+"\n"+suara.getCalon2());
        }

        if(suara.getLocal() == 0 && !suara.getjSuara().equals("") || suara.getLocal() == 1 && !suara.getjSuara().equals("")){
            holder.etSuara.setEnabled(false);
            holder.etSuara.setText(suara.getjSuara());
        }
    }

    @Override
    public int getItemCount() {
        return suaraList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoUrut, tvNama;
        EditText etSuara;

        public ViewHolder(View itemView) {
            super(itemView);
            etSuara = itemView.findViewById(R.id.etSuara);
            tvNoUrut = itemView.findViewById(R.id.tvNourut);
            tvNama = itemView.findViewById(R.id.tvNama);
        }
    }
}
