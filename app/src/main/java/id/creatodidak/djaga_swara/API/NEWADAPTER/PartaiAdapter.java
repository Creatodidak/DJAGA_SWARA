package id.creatodidak.djaga_swara.API.NEWADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import id.creatodidak.djaga_swara.R; // Ganti dengan package sesuai proyek Anda

public class PartaiAdapter extends RecyclerView.Adapter<PartaiAdapter.ViewHolder> {

    private List<MPartai> partaiList;
    private Context context;
    private String type;
    private DBHelper db;

    public PartaiAdapter(Context context, List<MPartai> partaiList, String type) {
        this.context = context;
        this.partaiList = partaiList;
        this.type = type;
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

        FigurAdapter adp = new FigurAdapter(context, db.getFigurList(type, data.getIdPartai()));

        h.wrapper.setTag(data.getIdPartai());
        h.logopartai.setImageResource(data.getLogo());
        h.tvNPartai.setText(data.getNama());
        h.tvNUPartai.setText(data.getNomorurut());

        LinearLayoutManager lm = new LinearLayoutManager(context); // Buat LinearLayoutManager di sini
        h.rvFigur.setLayoutManager(lm);
        h.rvFigur.setAdapter(adp);
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

        public ViewHolder(@NonNull View v) {
            super(v);

            tvNUPartai = v.findViewById(R.id.tvNoUrutPartai);
            tvNPartai = v.findViewById(R.id.tvNamaPartai);
            etSuaraPartai = v.findViewById(R.id.etSuaraPartai);
            logopartai = v.findViewById(R.id.logopartai);
            rvFigur = v.findViewById(R.id.rvFigur);
            wrapper = v.findViewById(R.id.wrapper);
        }
    }
}
