package id.creatodidak.djaga_swara.Dashboard;

// import statements

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Adapter.TpsAdapter;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.TpsList;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class Dashboard extends AppCompatActivity implements TpsAdapter.OnItemClickListener {

    ApiService apiService;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper databaseHelper;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String id_sprint, nrp;
    private boolean isInternetAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);

        id_sprint = getIntent().getStringExtra("sprint_id");
        nrp = sharedPreferences.getString("nrp", "");
        apiService = ApiClient.getClient().create(ApiService.class);
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.listTps);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Check internet availability
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        isInternetAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isInternetAvailable) {
            loadDataFromServer();
        } else {
            loadDataFromLocalDatabase();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isInternetAvailable) {
                loadDataFromServer();
            } else {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView profile = findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, First.class);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromServer() {
        showDialog("Memuat Data Dari Server...");

        // Make API request to fetch data from the server
        Call<List<TpsList>> call = apiService.getTPSList(id_sprint, nrp);
        call.enqueue(new Callback<List<TpsList>>() {
            @Override
            public void onResponse(Call<List<TpsList>> call, Response<List<TpsList>> response) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    List<TpsList> tpsList = response.body();
                    if (tpsList != null && !tpsList.isEmpty()) {
                        List<TpsList> tpsList2 = databaseHelper.getSprinDetailData();
                        if (tpsList2.isEmpty()) {
                            for (TpsList tps : tpsList) {
                                databaseHelper.insertSprinDetailData(tps.getId(), tps.getLatitude(), tps.getLongitude(), tps.getIdProv(), tps.getIdSprin(), tps.getIdKab(), tps.getIdKec(), tps.getIdDes(), tps.getIdTps(), tps.getNomorTps(), tps.getKetuaKpps(), tps.getHpKpps(), tps.getDptSementara(), tps.getDptTetap(), tps.getDptFinal(), tps.getKeterangan(), tps.getStatus(), tps.getLokasiKotakSuara(), tps.getCreatedAt(), tps.getUpdatedAt(), tps.getNamaDes(), tps.getNamaKec(), tps.getNamaKab());
                            }
                            loadDataFromLocalDatabase();
                        } else {
                            if(tpsList.size() != tpsList2.size()){
                                databaseHelper.resetTables2();
                                for (TpsList tps : tpsList) {
                                    databaseHelper.insertSprinDetailData(tps.getId(), tps.getLatitude(), tps.getLongitude(), tps.getIdProv(), tps.getIdSprin(), tps.getIdKab(), tps.getIdKec(), tps.getIdDes(), tps.getIdTps(), tps.getNomorTps(), tps.getKetuaKpps(), tps.getHpKpps(), tps.getDptSementara(), tps.getDptTetap(), tps.getDptFinal(), tps.getKeterangan(), tps.getStatus(), tps.getLokasiKotakSuara(), tps.getCreatedAt(), tps.getUpdatedAt(), tps.getNamaDes(), tps.getNamaKec(), tps.getNamaKab());
                                }
                            }
                            loadDataFromLocalDatabase();
                        }
                    } else {
                        Toast.makeText(Dashboard.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Dashboard.this, "Gagal memuat data dari server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TpsList>> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Dashboard.this, "Gagal memuat data dari server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataFromLocalDatabase() {
        showDialog("Memuat Data Dari Local Database...");

        // Retrieve data from local database
        List<TpsList> tpsList = databaseHelper.getSprinDetailData();
        if (tpsList.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(Dashboard.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.dismiss();
            TpsAdapter adapter = new TpsAdapter(tpsList, Dashboard.this);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(Dashboard.this);
        }
    }

    @Override
    public void onItemClick(TpsList tpsListOffline) {
        Intent intent = new Intent(Dashboard.this, Tugas.class);
        intent.putExtra("id_tps", tpsListOffline.getIdTps());
        startActivity(intent);
    }

    private void showDialog(String message) {
        progressDialog = new ProgressDialog(Dashboard.this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}