package id.creatodidak.djaga_swara.plugin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.creatodidak.djaga_swara.R;

public class CSpinner extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> itemList;
    private int selectedPosition = -1; // Untuk melacak elemen terpilih

    public CSpinner(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // Tambahkan metode ini untuk mengatur elemen terpilih
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner, parent, false);
            holder = new ViewHolder();
            holder.item = convertView.findViewById(R.id.spItem);
            holder.img = convertView.findViewById(R.id.spImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Mengatur teks item
        holder.item.setText(itemList.get(position));

        // Mengatur panah hanya untuk elemen terpilih
        if (position == selectedPosition) {
            holder.img.setVisibility(View.VISIBLE);
        } else {
            holder.img.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView item;
        ImageView img;
    }
}
