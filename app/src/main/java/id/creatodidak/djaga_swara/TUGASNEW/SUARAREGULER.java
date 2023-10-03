package id.creatodidak.djaga_swara.TUGASNEW;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Interface.Endpoint;
import id.creatodidak.djaga_swara.API.Models.TPS;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListbupatiItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListgubernurItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListkadesItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListpresidenItem;
import id.creatodidak.djaga_swara.API.NEWMODEL.SELECTTPS.ListtpsItem;
import id.creatodidak.djaga_swara.Database.DBHelper;
import id.creatodidak.djaga_swara.R;
import id.creatodidak.djaga_swara.plugin.DATATYPEMANAGER;
import id.creatodidak.djaga_swara.plugin.SessionData;
import id.creatodidak.djaga_swara.plugin.TextHelper;

public class SUARAREGULER extends AppCompatActivity {
    String IDTPS;
    String TYPE;
    RecyclerView rv;
    TextView tvJudulDataSuara;
    DBHelper db;
    SharedPreferences sh;
    Endpoint endpoint;
    List<ListpresidenItem> listpresidenItems = new ArrayList<>();
    List<ListgubernurItem> listgubernurItems = new ArrayList<>();
    List<ListbupatiItem> listbupatiItems = new ArrayList<>();
    List<ListkadesItem> listkadesItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suarareguler);
        Intent i = getIntent();
        IDTPS = i.getStringExtra("IDTPS");
        TYPE = i.getStringExtra("TYPE");
        db = new DBHelper(this);
        sh = getSharedPreferences("DATAMANAGER", MODE_PRIVATE);
        endpoint = ApiClient.getClient().create(Endpoint.class);

        rv = findViewById(R.id.rvCalon);
        tvJudulDataSuara = findViewById(R.id.tvJudulDataSuara);

        loadData(IDTPS, TYPE);
    }

    private void loadData(String idtps, String type) {
        ListtpsItem tps = db.getRincianTpsById(idtps);
        if(type.equals("PRESIDEN")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILPRES TPS "+tps.getNomorTps()+" Desa "+ TextHelper.capitalize(sh.getString("KECAMATAN TPS", null)));
            listpresidenItems.addAll(db.getListCapres(idtps, type));
        }else if(type.equals("GUBERNUR")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILGUB TPS "+tps.getNomorTps()+" Desa "+ TextHelper.capitalize(sh.getString("KECAMATAN TPS", null)));
            listgubernurItems.addAll(db.getListCagub(idtps, type));
        }else if(type.equals("BUPATI")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILBUP TPS "+tps.getNomorTps()+" Desa "+ TextHelper.capitalize(sh.getString("KECAMATAN TPS", null)));
            listbupatiItems.addAll(db.getListCabup(idtps, type));
        }else if(type.equals("KADES")){
            tvJudulDataSuara.setText("INPUT DATA SUARA PILKADES TPS "+tps.getNomorTps()+" Desa "+ TextHelper.capitalize(sh.getString("KECAMATAN TPS", null)));
            listkadesItems.addAll(db.getListCakades(idtps, type));
        }
    }
}