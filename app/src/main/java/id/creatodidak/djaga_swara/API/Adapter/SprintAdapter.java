package id.creatodidak.djaga_swara.API.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import id.creatodidak.djaga_swara.API.Models.SprintList;
import id.creatodidak.djaga_swara.Helper.AESHelper;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class SprintAdapter extends RecyclerView.Adapter<SprintAdapter.SprintViewHolder> {
    private Context context;
    private List<SprintList> sprints;
    private OnItemClickListener itemClickListener;

    public SprintAdapter(Context context, List<SprintList> sprints) {
        this.context = context;
        this.sprints = sprints;
    }

    public void setSprints(List<SprintList> sprints) {
        this.sprints = sprints;

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public SprintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listsprin, parent, false);
        return new SprintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SprintViewHolder holder, int position) {
        SprintList sprint = sprints.get(position);
        holder.bind(sprint);
    }

    @Override
    public int getItemCount() {
        return sprints.size();
    }

    public class SprintViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, status, tanggal, type;

        public SprintViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judulsprin);
            status = itemView.findViewById(R.id.statussprin);
            tanggal = itemView.findViewById(R.id.tanggalsprin);
            type = itemView.findViewById(R.id.type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                        SprintList sprint = sprints.get(position);
                        itemClickListener.onItemClick(sprint);
                    }
                }
            });
        }

        public void bind(SprintList sprint) {
            judul.setText(sprint.getJudul());
            status.setText(sprint.getStatus());
            tanggal.setText(sprint.getTanggal().toString());

            String jsonString = sprint.getType();

            Gson gson = new Gson();
            String[] stringArray = gson.fromJson(jsonString, String[].class);
            List<String> stringList = Arrays.asList(stringArray);

            String joinedString = TextUtils.join(", ", stringList);

            type.setText(joinedString);

            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.insertSprinListData(sprint.getNomor(), sprint.getJudul(), sprint.getKode(), sprint.getTahun(), sprint.getPenerbit(), sprint.getTandatangan(), sprint.getStatus(), sprint.getDasar(), sprint.getNamaops(), sprint.getPerintah(), sprint.getTanggal().toString(), sprint.getTanggalmulai().toString(), sprint.getTanggalberakhir().toString(), sprint.getType(), sprint.getCreated_at().toString(), sprint.getUpdated_at().toString());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SprintList sprint);
    }
}