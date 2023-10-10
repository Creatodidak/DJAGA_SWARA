package id.creatodidak.djaga_swara.API.NEWADAPTER;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.NEWMODEL.GridLayoutData;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;

public class GridSuaraAdapter extends BaseAdapter {
    private final Context context;
    private final List<GridLayoutData> data;
    private final OnClickListener onClickListener;
    private final DBHelper dbHelper;
    private final SharedPreferences sh;

    public GridSuaraAdapter(Context context, List<GridLayoutData> data, OnClickListener onClickListener) {
        this.context = context;
        this.data = data;
        this.onClickListener = onClickListener;

        dbHelper = new DBHelper(context);
        sh = context.getSharedPreferences("DATAMANAGER", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }
        GridLayoutData item = data.get(position);
        CardView glWrapper = convertView.findViewById(R.id.glWrapper);
        TextView textView = convertView.findViewById(R.id.glTv);
        ImageView iv = convertView.findViewById(R.id.glImg);
        textView.setText(item.getJudul());
        iv.setImageResource(item.getGambar());
        glWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(item.getIdTps());
            }
        });

        List<String> type = new ArrayList<>();
        if(sh.getBoolean("PRESIDEN", false)){type.add("PRESIDEN");}
        if(sh.getBoolean("GUBERNUR", false)){type.add("GUBERNUR");}
        if(sh.getBoolean("BUPATI", false)){type.add("BUPATI");}
        if(sh.getBoolean("KADES", false)){type.add("KADES");}
        if(sh.getBoolean("DPD-RI", false)){type.add("DPD-RI");}
        if(sh.getBoolean("DPR-RI", false)){type.add("DPR-RI");}
        if(sh.getBoolean("DPRD-PROV", false)){type.add("DPRD-PROV");}
        if(sh.getBoolean("DPRD-KAB", false)){type.add("DPRD-KAB");}

        String status = dbHelper.cektugastps(item.getIdTps(), type);
        if(status.equals("BELUM SELESAI")){
            textView.setTextColor(Color.parseColor("#ffffff"));
            glWrapper.setCardBackgroundColor(Color.parseColor("#e5342f"));
        }else if(status.equals("LOCAL")){
            glWrapper.setCardBackgroundColor(Color.parseColor("#eeba56"));
        }else if(status.equals("SERVER")){
            glWrapper.setCardBackgroundColor(Color.parseColor("#8cc24b"));
        }
        return convertView;
    }

    public interface OnClickListener{
        void onClick(String idTps);
    }
}
