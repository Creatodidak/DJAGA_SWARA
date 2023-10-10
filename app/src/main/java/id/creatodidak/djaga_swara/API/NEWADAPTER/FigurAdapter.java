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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MFigur;
import id.creatodidak.djaga_swara.API.NEWMODEL.MODELLAPORAN.MPartai;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;

public class FigurAdapter extends RecyclerView.Adapter<FigurAdapter.ViewHolder> {

    private List<MFigur> figurList;
    private Context context;
    public FigurAdapter(Context context, List<MFigur> figurList) {
        this.context = context;
        this.figurList = figurList;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        MFigur data = figurList.get(position);
        h.tvNomorUrut.setText(data.getNomorurut());
        h.tvNama.setText(data.getNama());
    }

    @Override
    public int getItemCount() {
        return figurList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomorUrut;
        TextView tvNama;
        EditText etSuara;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvNomorUrut = v.findViewById(R.id.tvNourut);
            tvNama = v.findViewById(R.id.tvNama);
            etSuara = v.findViewById(R.id.etSuara);
        }
    }
}
