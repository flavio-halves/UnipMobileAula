package com.example.helpdeskunipassismobile;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Ticket;
import com.example.helpdeskunipassismobile.ui.TicketAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketsListActivity extends AppCompatActivity {

    private ApiService api;
    private TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        api = ApiClient.getService();

        RecyclerView rv = findViewById(R.id.rvTickets);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TicketAdapter();
        rv.setAdapter(adapter);

        loadTickets();
    }

    private void loadTickets() {
        api.getTickets().enqueue(new Callback<List<Ticket>>() {
            @Override public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    adapter.setData(resp.body());
                } else {
                    Toast.makeText(TicketsListActivity.this, "Falha: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(TicketsListActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
