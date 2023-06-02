package id.creatodidak.djaga_swara.Dashboard.TugasForm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Adapter.Multi.BupatiAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.DPRDKabAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.DPRDProvAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.DpdriAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.DprriAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.GubernurAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.KadesAdapter;
import id.creatodidak.djaga_swara.API.Adapter.Multi.PresidenAdapter;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.Multi.Bupati;
import id.creatodidak.djaga_swara.API.Models.Multi.DPDRI;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDKab;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRDProv;
import id.creatodidak.djaga_swara.API.Models.Multi.DPRRI;
import id.creatodidak.djaga_swara.API.Models.Multi.Gubernur;
import id.creatodidak.djaga_swara.API.Models.Multi.Kades;
import id.creatodidak.djaga_swara.API.Models.Multi.Presiden;
import id.creatodidak.djaga_swara.API.Models.Multi.SuaraData;
import id.creatodidak.djaga_swara.API.Models.UpdResponse;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormInputSuara extends AppCompatActivity {

    TextView tvType, tvTps;
    RecyclerView listform;
    DatabaseHelper databaseHelper;
    String idTps, type, namaTps;
    List<Presiden> presidens;
    List<Gubernur> gubernurs;
    List<Bupati> bupatis;
    List<Kades> kadess;
    List<DPRRI> dprris;
    List<DPDRI> dpdris;
    List<DPRDKab> dprdKabs;
    List<DPRDProv> dprdProvs;
    PresidenAdapter presidenAdapter;
    GubernurAdapter gubernurAdapter;
    BupatiAdapter bupatiAdapter;
    DprriAdapter dprriAdapter;
    DpdriAdapter dpdriAdapter;
    DPRDProvAdapter dprdprovAdapter;
    DPRDKabAdapter dprdkabAdapter;
    KadesAdapter kadesAdapter;
    ProgressDialog progressDialog;

    private int totalSuara = 0;
    ApiService apiService;
    Button kirim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_input_suara);
        apiService = ApiClient.getClient().create(ApiService.class);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            progressDialog = new ProgressDialog(this);

            databaseHelper = new DatabaseHelper(this);
            idTps = getIntent().getStringExtra("id_tps");
            type = getIntent().getStringExtra("type");
            namaTps = getIntent().getStringExtra("namatps");

            presidens = new ArrayList<>();
            dprris = new ArrayList<>();
            dpdris = new ArrayList<>();
            gubernurs = new ArrayList<>();
            bupatis = new ArrayList<>();
            dprdProvs = new ArrayList<>();
            dprdKabs = new ArrayList<>();
            kadess = new ArrayList<>();

            tvType = findViewById(R.id.tvType);
            tvTps = findViewById(R.id.tvTps);
            listform = findViewById(R.id.listForm);
            kirim = findViewById(R.id.kirimsuara);
            tvTps.setText(namaTps);
            listform.setLayoutManager(new LinearLayoutManager(this));

            if (type.equals("PRESIDEN")) {
                tvType.setText("DATA SUARA PEMILIHAN PRESIDEN");
                presidenAdapter = new PresidenAdapter(this, presidens);
                fetchPresiden(idTps);
            } else if (type.equals("DPRRI")) {
                tvType.setText("DATA SUARA PEMILIHAN DPR-RI");
                dprriAdapter = new DprriAdapter(this, dprris);
                fetchDprri(idTps);
            } else if (type.equals("DPDRI")) {
                tvType.setText("DATA SUARA PEMILIHAN DPD-RI");
                dpdriAdapter = new DpdriAdapter(this, dpdris);
                fetchDpdri(idTps);
            } else if (type.equals("GUBERNUR")) {
                tvType.setText("DATA SUARA PEMILIHAN GUBERNUR");
                gubernurAdapter = new GubernurAdapter(this, gubernurs);
                fetchGubernur(idTps);
            } else if (type.equals("DPRDPROV")) {
                tvType.setText("DATA SUARA PEMILIHAN DPRD PROVINSI");
                dprdprovAdapter = new DPRDProvAdapter(this, dprdProvs);
                fetchDprdprov(idTps);
            } else if (type.equals("BUPATI")) {
                tvType.setText("DATA SUARA PEMILIHAN BUPATI");
                bupatiAdapter = new BupatiAdapter(this, bupatis);
                fetchBupati(idTps);
            } else if (type.equals("DPRDKAB")) {
                tvType.setText("DATA SUARA PEMILIHAN DPRD KABUPATEN");
                dprdkabAdapter = new DPRDKabAdapter(this, dprdKabs);
                fetchDprdkab(idTps);
            } else if (type.equals("KADES")) {
                tvType.setText("DATA SUARA PEMILIHAN KEPALA DESA");
                kadesAdapter = new KadesAdapter(this, kadess);
                fetchKades(idTps);
            }


            kirim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<SuaraData> suaraDataList = new ArrayList<>();
                    if (type.equals("PRESIDEN")) {
                        for (Presiden presiden : presidens) {
                            View view = listform.findViewWithTag(presiden.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, presiden.getIdCalon(), suara, "presiden");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("DPRRI")) {
                        for (DPRRI dprri : dprris) {
                            View view = listform.findViewWithTag(dprri.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, dprri.getId_calon(), suara, "dprri");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("DPDRI")) {
                        for (DPDRI dpdri : dpdris) {
                            View view = listform.findViewWithTag(dpdri.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, dpdri.getId_calon(), suara, "dpdri");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("GUBERNUR")) {
                        for (Gubernur gubernur : gubernurs) {
                            View view = listform.findViewWithTag(gubernur.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, gubernur.getId_calon(), suara, "gubernur");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("DPRDPROV")) {
                        for (DPRDProv dprdProv : dprdProvs) {
                            View view = listform.findViewWithTag(dprdProv.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, dprdProv.getId_calon(), suara, "dprdprov");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("BUPATI")) {
                        for (Bupati bupati : bupatis) {
                            View view = listform.findViewWithTag(bupati.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, bupati.getId_calon(), suara, "bupati");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("DPRDKAB")) {
                        for (DPRDKab dprdKab : dprdKabs) {
                            View view = listform.findViewWithTag(dprdKab.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, dprdKab.getId_calon(), suara, "dprdkab");
                                suaraDataList.add(suaraData);
                            }
                        }
                    } else if (type.equals("KADES")) {
                        for (Kades kades : kadess) {
                            View view = listform.findViewWithTag(kades.getId());
                            EditText suaraEditText = view.findViewById(R.id.etSuara);
                            String suaraText = suaraEditText.getText().toString().trim();
                            if (!suaraText.isEmpty()) {
                                int suara = Integer.parseInt(suaraText);
                                SuaraData suaraData = new SuaraData(idTps, kades.getId_calon(), suara, "kades");
                                suaraDataList.add(suaraData);
                            }
                        }
                    }

                    sendSuaraData(suaraDataList, 0);
                }
            });
        }
    }
    private void sendSuaraData(List<SuaraData> suaraDataList, int index) {
        showprogress("Mengirim data ke server");


        String urutan = String.valueOf(index);

        if (index >= suaraDataList.size()) {
            tidaksah(idTps, type);
            return;
        }

        SuaraData suaraData = suaraDataList.get(index);


        Call<UpdResponse> call = apiService.sendSuaraData(suaraData);

        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                if (response.body().getMsg().equals("ok")){
                    progressDialog.dismiss();
                    showprogress("Menyimpan Data ke - "+( Integer.parseInt(urutan) + 1)+" ke Database local");
                    if(savedatasuara(suaraData, "SERVER")) {
                        progressDialog.dismiss();
                        sendSuaraData(suaraDataList, index + 1);
                    }else{
                        sendSuaraData(suaraDataList, index + 1);
                        Toast.makeText(FormInputSuara.this, "Data ke - "+( Integer.parseInt(urutan) + 1)+" Gagal disimpan di database local dan data tidak disimpan di Draft!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    progressDialog.dismiss();
                    notifikasi("Gagal Mengirim Data", "Data ke - "+( Integer.parseInt(urutan) + 1)+" Gagal dikirim ke server dan data tidak disimpan di Draft!", true, false);
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                progressDialog.dismiss();
                showprogress("Menyimpan Data ke - "+( Integer.parseInt(urutan) + 1)+" ke Database local");
                if(savedatasuara(suaraData, "LOCAL")) {
                    progressDialog.dismiss();
                    sendSuaraData(suaraDataList, index + 1);
                }else{
                    notifikasi("Gagal Menyimpam Data", "Data ke - "+( Integer.parseInt(urutan) + 1)+" Gagal dikirim ke server dan data tidak disimpan di Draft!", true, true);
                }
            }
        });
    }

    private boolean savedatasuara(SuaraData suaraData, String status) {
        return databaseHelper.updateSuara(suaraData.getType(), suaraData, status, suaraData.getCalonId());
    }

    private void tidaksah(String tpsId, String type) {
        progressDialog.dismiss();
        EditText tidaksah = findViewById(R.id.etSuaratidaksah);
        int suaratidaksah = Integer.parseInt(tidaksah.getText().toString().trim());
        Call<UpdResponse> call = apiService.sendTidaksah(tpsId, suaratidaksah, type);
        showprogress("Menyimpan data ke Local");
        call.enqueue(new Callback<UpdResponse>() {
            @Override
            public void onResponse(Call<UpdResponse> call, Response<UpdResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getMsg().equals("ok")){
                        if(databaseHelper.savesuaratidaksah(tpsId, suaratidaksah, type, "SERVER")){
                            progressDialog.dismiss();
                            notifikasi("Berhasil", "Data Berhasil Dikirim!",true, true);
                        }else{
                            progressDialog.dismiss();
                            notifikasi("Berhasil", "Data Berhasil Dikirim, namun gagal disimpan di Local!",true, true);
                        }
                    }else{
                        progressDialog.dismiss();
                        notifikasi("Gagal!", "Data Suara Tidak Sah Gagal dikirim Ke Server dan tidak disimpan di Draft!",true, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdResponse> call, Throwable t) {
                if(databaseHelper.savesuaratidaksah(tpsId, suaratidaksah, type, "LOCAL")){
                    progressDialog.dismiss();
                    notifikasi("Gagal", "Data Gagal Dikirim Ke Server Namun Tersimpan di Draft!",true, true);
                }else{
                    progressDialog.dismiss();
                    notifikasi("Gagal", "Data Gagal Dikirim dan Tidak Tersimpan di Draft!",true, true);
                }
            }
        });
    }

    private void showprogress(String msg) {
//        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void notifikasi(String info, String s, Boolean cancel, Boolean close) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FormInputSuara.this);
        if(cancel){
            if(close){
                builder.setTitle(info)
                        .setMessage(s)
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show();
            }else{
                builder.setTitle(info)
                        .setMessage(s)
                        .setIcon(R.drawable.logosmall)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }else{
            builder.setTitle(info)
                    .setMessage(s)
                    .setIcon(R.drawable.logosmall)
                    .setCancelable(false)
                    .show();
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchPresiden(String idTps) {
        presidens.addAll(databaseHelper.getPresidenData(idTps));
        listform.setAdapter(presidenAdapter);
        listform.setHasFixedSize(true);
        presidenAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchDprri(String idTps) {
        dprris.addAll(databaseHelper.getDPRRIdata(idTps));
        listform.setAdapter(dprriAdapter);
        listform.setHasFixedSize(true);
        dprriAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchDpdri(String idTps) {
        dpdris.addAll(databaseHelper.getDPDRIdata(idTps));
        listform.setAdapter(dpdriAdapter);
        listform.setHasFixedSize(true);
        dpdriAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchGubernur(String idTps) {
        gubernurs.addAll(databaseHelper.getGubernurData(idTps));
        listform.setAdapter(gubernurAdapter);
        listform.setHasFixedSize(true);
        gubernurAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchDprdprov(String idTps) {
        dprdProvs.addAll(databaseHelper.getDPRDProvData(idTps));
        listform.setAdapter(dprdprovAdapter);
        listform.setHasFixedSize(true);
        dprdprovAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchBupati(String idTps) {
        bupatis.addAll(databaseHelper.getBupatiData(idTps));
        listform.setAdapter(bupatiAdapter);
        listform.setHasFixedSize(true);
        bupatiAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchDprdkab(String idTps) {
        dprdKabs.addAll(databaseHelper.getDPRDKabData(idTps));
        listform.setAdapter(dprdkabAdapter);
        listform.setHasFixedSize(true);
        dprdkabAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchKades(String idTps) {
        kadess.addAll(databaseHelper.getKadesData(idTps));
        listform.setAdapter(kadesAdapter);
        listform.setHasFixedSize(true);
        kadesAdapter.notifyDataSetChanged();
    }


}

//
//if(type.equals("PRESIDEN")){
//
//        }else if(type.equals("DPRRI")){
//
//        }else if(type.equals("DPDRI")){
//
//        }else if(type.equals("GUBERNUR")){
//
//        }else if(type.equals("DPRDPROV")){
//
//        }else if(type.equals("BUPATI")){
//
//        }else if(type.equals("DPRDKAB")){
//
//        }else if(type.equals("KADES")){
//
//        }