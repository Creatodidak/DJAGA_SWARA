package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.Presiden;
import id.creatodidak.djaga_swara.Dashboard.TugasForm.FormInputSuara;
import id.creatodidak.djaga_swara.R;

public class PresidenAdapter extends RecyclerView.Adapter<PresidenAdapter.ViewHolder> {
    private List<Presiden> presidenList;

    public PresidenAdapter(FormInputSuara formInputSuara, List<Presiden> presidenList) {
        this.presidenList = presidenList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.formsuara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Presiden presiden = presidenList.get(position);
        holder.itemView.setTag(presiden.getId());
        holder.nourut.setText(presiden.getNoUrut());
        holder.namacalon.setText(presiden.getCapres()+"\n"+presiden.getCawapres());
    }

    @Override
    public int getItemCount() {
        return presidenList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nourut, namacalon;
        EditText suara;

        public ViewHolder(View itemView) {
            super(itemView);
            nourut = itemView.findViewById(R.id.tvNourut);
            namacalon = itemView.findViewById(R.id.tvNama);
            suara = itemView.findViewById(R.id.etSuara);
        }
    }
}
