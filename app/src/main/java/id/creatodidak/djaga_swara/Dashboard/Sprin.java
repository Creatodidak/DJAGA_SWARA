package id.creatodidak.djaga_swara.Dashboard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Adapter.SprintAdapter;
import id.creatodidak.djaga_swara.API.Adapter.SprintOfflineAdapter;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.SprintList;
import id.creatodidak.djaga_swara.API.Models.SprintListOffline;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.MockDetector;
import id.creatodidak.djaga_swara.Helper.NetworkChangeReceiver;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sprin extends AppCompatActivity implements SprintAdapter.OnItemClickListener, SprintOfflineAdapter.OnItemClickListener {

    private NetworkChangeReceiver networkChangeReceiver;
    ApiService apiService;
    private List<SprintList> sprints;
    private List<SprintListOffline> localSprints;
    private RecyclerView recyclerView;
    private SprintAdapter sprintAdapter;
    private SprintOfflineAdapter sprintOfflineAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isInternetAvailable;

    DatabaseHelper databaseHelper;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprin);
        MockDetector mockDetector = new MockDetector(this);
        boolean isMockLocationDetected = mockDetector.checkMockLocation();
        if (!isMockLocationDetected) {
            apiService = ApiClient.getClient().create(ApiService.class);
            databaseHelper = new DatabaseHelper(this);
            networkChangeReceiver = new NetworkChangeReceiver();
            recyclerView = findViewById(R.id.rvSprin);
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            sprints = new ArrayList<>();
            localSprints = new ArrayList<>();

            sprintOfflineAdapter = new SprintOfflineAdapter(this, localSprints);
            sprintOfflineAdapter.setOnItemClickListener(this);

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isInternetAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isInternetAvailable) {
                fetchFromServer();
            } else {
                fetchLocal();
            }

            swipeRefreshLayout.setOnRefreshListener(() -> {
                if (isInternetAvailable) {
                    fetchFromServer();
                } else {
                    fetchLocal();
                }
            });

            ImageView profile = findViewById(R.id.profile);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Sprin.this, First.class);
                    startActivity(intent);
                }
            });

            databaseHelper = new DatabaseHelper(this);
            databaseHelper.startRepeatedNotification(this);
        }
    }

    public void fetchFromServer() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat Data Dari Server...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Call<List<SprintListOffline>> call = apiService.getSprints();
        call.enqueue(new Callback<List<SprintListOffline>>() {
            @Override
            public void onResponse(Call<List<SprintListOffline>> call, Response<List<SprintListOffline>> response) {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<SprintListOffline> sprintListOfflines = response.body();
                    if (sprintListOfflines != null && !sprintListOfflines.isEmpty()) {
                        List<SprintListOffline> sprintListOfflines2 = databaseHelper.getAllSprintList();
                        if (sprintListOfflines2.isEmpty()) {
                            for (SprintListOffline sprint : sprintListOfflines) {
                                databaseHelper.insertSprinListData(sprint.getNomor(), sprint.getJudul(), sprint.getKode(), sprint.getTahun(), sprint.getPenerbit(), sprint.getTandatangan(), sprint.getStatus(), sprint.getDasar(), sprint.getNamaops(), sprint.getPerintah(), sprint.getTanggalString(), sprint.getTanggalmulaiString(), sprint.getTanggalberakhirString(), sprint.getType(), sprint.getCreatedAtString(), sprint.getUpdatedAtString());
                            }
                            fetchLocal();
                        } else {
                            if (sprintListOfflines.size() != sprintListOfflines2.size()) {
                                databaseHelper.resetTables();
                                for (SprintListOffline sprint : sprintListOfflines) {
                                    databaseHelper.insertSprinListData(sprint.getNomor(), sprint.getJudul(), sprint.getKode(), sprint.getTahun(), sprint.getPenerbit(), sprint.getTandatangan(), sprint.getStatus(), sprint.getDasar(), sprint.getNamaops(), sprint.getPerintah(), sprint.getTanggalString(), sprint.getTanggalmulaiString(), sprint.getTanggalberakhirString(), sprint.getType(), sprint.getCreatedAtString(), sprint.getUpdatedAtString());
                                }
                            }
                            fetchLocal();
                        }
                    } else {
                        Toast.makeText(Sprin.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Sprin.this, "Gagal memuat data dari server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SprintListOffline>> call, Throwable t) {
                progressDialog.dismiss();
                fetchLocal();
            }
        });
    }

    private void fetchLocal() {
        showDialog("Memuat Data Dari Local Database...");
        swipeRefreshLayout.setRefreshing(false);

        List<SprintListOffline> sprintListOfflines = databaseHelper.getAllSprintList();
        if (sprintListOfflines.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(Sprin.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.dismiss();
            localSprints.clear();
            localSprints.addAll(databaseHelper.getAllSprintList());
            recyclerView.setAdapter(sprintOfflineAdapter);
            sprintOfflineAdapter.notifyDataSetChanged();
        }
    }

    private void showDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onItemClick(SprintList sprint) {
        if (sprint.getStatus().equals("OPEN")) {
            Intent intent = new Intent(Sprin.this, Dashboard2.class);
            intent.putExtra("sprint_id", String.valueOf(sprint.getId()));
            startActivity(intent);
        } else {
            showNotificationDialog("INFO", "Surat Perintah Ini Sudah Tidak Berlaku Lagi!");
        }
    }

    private void showNotificationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Sprin.this);
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.logosmall)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onItemClick(SprintListOffline sprint) {
        if (sprint.getStatus().equals("OPEN")) {
            Intent intent = new Intent(Sprin.this, Dashboard2.class);
            intent.putExtra("sprint_id", String.valueOf(sprint.getId()));
            intent.putExtra("type", sprint.getType());
            startActivity(intent);
        } else {
            showNotificationDialog("INFO", "Surat Perintah Ini Sudah Tidak Berlaku Lagi!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("KONFIRMASI");
        builder.setIcon(R.drawable.logosmall);

        builder.setMessage("Keluar dari aplikasi?");
        builder.setPositiveButton("YA, KELUAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
