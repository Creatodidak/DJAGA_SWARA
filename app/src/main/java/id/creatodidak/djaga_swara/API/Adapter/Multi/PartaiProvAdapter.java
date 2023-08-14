package id.creatodidak.djaga_swara.API.Adapter.Multi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.creatodidak.djaga_swara.API.Models.Multi.DPRDProv;
import id.creatodidak.djaga_swara.API.Models.Multi.PartaiProv;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;

public class PartaiProvAdapter extends RecyclerView.Adapter<PartaiProvAdapter.ViewHolder> {
    private final List<PartaiProv> partaiProvList;
    private final Context context;
    public PartaiProvAdapter(Context context, List<PartaiProv> partaiProvList) {
        this.context = context;
        this.partaiProvList = partaiProvList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.legislatifform, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PartaiProv partaiProv = partaiProvList.get(position);
        holder.itemView.setTag(partaiProv.getId());
        holder.nourut.setText(partaiProv.getNomorurut());
        holder.namacalon.setText(partaiProv.getNama());

        // Load data for nested RecyclerView
        holder.dprdProvs.clear();
        holder.dprdProvs.addAll(holder.databaseHelper.getDPRDProvData(partaiProv.getIdtps(), partaiProv.getId_partai()));
        holder.dprdProvAdapter.notifyDataSetChanged();

        // Load logo image using its resource ID
        int logoResourceId = getLogoResourceId(partaiProv.getNama());
        if (logoResourceId != 0) {
            holder.logo.setImageDrawable(context.getResources().getDrawable(logoResourceId));
        } else {
            // Set a default logo if resource ID is not found
            holder.logo.setImageDrawable(context.getResources().getDrawable(R.drawable.logosmall));
        }
    }

    // Method to get logo resource ID based on nama partai
    private int getLogoResourceId(String namaPartai) {
        HashMap<String, Integer> logoMapping = new HashMap<>();
        logoMapping.put("PKB", R.drawable.pkb);
        logoMapping.put("GERINDRA", R.drawable.gerindra);
        logoMapping.put("PDI-P", R.drawable.pdip);
        logoMapping.put("GOLKAR", R.drawable.golkar);
        logoMapping.put("NASDEM", R.drawable.nasdem);
        logoMapping.put("PARTAI BURUH", R.drawable.partaiburuh);
        logoMapping.put("GELORA INDONESIA", R.drawable.geloraindonesia);
        logoMapping.put("PKS", R.drawable.pks);
        logoMapping.put("PKN", R.drawable.pkn);
        logoMapping.put("HANURA", R.drawable.hanura);
        logoMapping.put("GARUDA", R.drawable.garuda);
        logoMapping.put("PAN", R.drawable.pan);
        logoMapping.put("PBB", R.drawable.pbb);
        logoMapping.put("DEMOKRAT", R.drawable.demokrat);
        logoMapping.put("PSI", R.drawable.psi);
        logoMapping.put("PERINDO", R.drawable.perindo);
        logoMapping.put("PPP", R.drawable.ppp);
        logoMapping.put("PARTAI UMMAT", R.drawable.partaiummat);


        if (logoMapping.containsKey(namaPartai)) {
            return logoMapping.get(namaPartai);
        } else {
            return 0; // Return 0 for unknown nama partai values
        }
    }


    @Override
    public int getItemCount() {
        return partaiProvList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nourut, namacalon;
        EditText suara;
        RecyclerView rv;
        DPRDProvAdapter dprdProvAdapter;
        List<DPRDProv> dprdProvs;

        ImageView logo;
        DatabaseHelper databaseHelper;

        public ViewHolder(View itemView) {
            super(itemView);
            nourut = itemView.findViewById(R.id.tvNoUrutPartai);
            namacalon = itemView.findViewById(R.id.tvNamaPartai);
            suara = itemView.findViewById(R.id.etSuaraPartai);
            rv = itemView.findViewById(R.id.rvFigur);
            databaseHelper = new DatabaseHelper(itemView.getContext());
            logo = itemView.findViewById(R.id.logopartai);
            dprdProvs = new ArrayList<>();
            dprdProvAdapter = new DPRDProvAdapter(itemView.getContext(), dprdProvs);

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(dprdProvAdapter);
        }
    }
}
