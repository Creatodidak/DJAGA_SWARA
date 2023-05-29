package id.creatodidak.djaga_swara.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import id.creatodidak.djaga_swara.API.Adapter.ApiClient;
import id.creatodidak.djaga_swara.API.Adapter.SprintAdapter;
import id.creatodidak.djaga_swara.API.Adapter.SprintOfflineAdapter;
import id.creatodidak.djaga_swara.API.Interface.ApiService;
import id.creatodidak.djaga_swara.API.Models.SprintList;
import id.creatodidak.djaga_swara.API.Models.SprintListOffline;
import id.creatodidak.djaga_swara.Helper.AESHelper;
import id.creatodidak.djaga_swara.Helper.DatabaseHelper;
import id.creatodidak.djaga_swara.Helper.NotificationHelper;
import id.creatodidak.djaga_swara.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sprin extends AppCompatActivity implements SprintAdapter.OnItemClickListener, SprintOfflineAdapter.OnItemClickListener {

    ApiService apiService;
    private List<SprintList> sprints;
    private List<SprintListOffline> localSprints;
    private RecyclerView recyclerView;
    private SprintAdapter sprintAdapter;
    private SprintOfflineAdapter sprintOfflineAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    DatabaseHelper databaseHelper;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprin);
        apiService = ApiClient.getClient().create(ApiService.class);
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rvSprin);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sprints = new ArrayList<>();
        localSprints = new ArrayList<>();

        sprintAdapter = new SprintAdapter(this, sprints);
        sprintAdapter.setOnItemClickListener(this);
        sprintOfflineAdapter = new SprintOfflineAdapter(this, localSprints);
        sprintOfflineAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(sprintAdapter);

        showDialog("Memuat Data Dari Server...");
        fetchFromServer();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            showDialog("Refresh...");
            fetchFromServer();
        });

        ImageView profile = findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sprin.this, First.class);
                startActivity(intent);
            }
        });

    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void fetchFromServer() {
        if (isInternetAvailable()) {
            Call<List<SprintList>> call = apiService.getSprints();
            call.enqueue(new Callback<List<SprintList>>() {
                @Override
                public void onResponse(Call<List<SprintList>> call, Response<List<SprintList>> response) {
                    if (response.isSuccessful()) {
                        databaseHelper.resetTables();
                        progressDialog.dismiss();
                        sprints.clear();
                        sprints.addAll(response.body());
                        sprintAdapter.notifyDataSetChanged();
                    } else {
                        // Tangani kesalahan respons jika diperlukan
                    }
                    swipeRefreshLayout.setRefreshing(false); // Hentikan indikator refresh
                }

                @Override
                public void onFailure(Call<List<SprintList>> call, Throwable t) {
                    progressDialog.dismiss();
                    fetchLocal();
                    swipeRefreshLayout.setRefreshing(false); // Hentikan indikator refresh
                    Toast.makeText(Sprin.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            progressDialog.dismiss();
            fetchLocal();
            swipeRefreshLayout.setRefreshing(false); // Hentikan indikator refresh
            Toast.makeText(Sprin.this, "Tidak Ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchLocal() {
        localSprints.clear();
        localSprints.addAll(databaseHelper.getAllSprintList());
        recyclerView.setAdapter(sprintOfflineAdapter);
        sprintOfflineAdapter.notifyDataSetChanged();
    }

    private void showDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onItemClick(SprintList sprint) {

        if (sprint.getStatus().equals("OPEN")){
            Intent intent = new Intent(Sprin.this, Dashboard.class);
            intent.putExtra("sprint_id", String.valueOf(sprint.getId()));
            startActivity(intent);
        }else {
            notifikasi("INFO", "Surat Perintah Ini Sudah Tidak Berlaku Lagi!");
        }

    }

    private void notifikasi(String info, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Sprin.this);
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

    @Override
    public void onItemClick(SprintListOffline sprint) {
        if (sprint.getStatus().equals("OPEN")){
            Intent intent = new Intent(Sprin.this, Dashboard.class);
            intent.putExtra("sprint_id", sprint.getId());
            startActivity(intent);
        }else {
            notifikasi("INFO", "Surat Perintah Ini Sudah Tidak Berlaku Lagi!");
        }
    }


}