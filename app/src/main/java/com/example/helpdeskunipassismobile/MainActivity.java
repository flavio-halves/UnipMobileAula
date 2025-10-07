package com.example.helpdeskunipassismobile;


import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Ticket;
import com.example.helpdeskunipassismobile.ui.TicketAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TicketAdapter adapter;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ApiClient.getService();

        RecyclerView rv = findViewById(R.id.recyclerTickets);
        adapter = new TicketAdapter();
        rv.setAdapter(adapter);

        Button btnCreate = findViewById(R.id.btnCreateTicket);
        btnCreate.setOnClickListener(v -> createTicket());

        loadTickets();
    }

    private void loadTickets() {
        api.getTickets().enqueue(new Callback<List<Ticket>>() {
            @Override public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> resp) {
                if (resp.isSuccessful()) {
                    adapter.setData(resp.body());
                } else {
                    Toast.makeText(MainActivity.this, "Falha ao carregar: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createTicket() {
        Ticket t = new Ticket();
        t.titulo = "Ticket via Android";
        t.descricao = "Criado do app";
        // t.usuarioId = 1; // opcional, se quiser vincular

        api.createTicket(t).enqueue(new Callback<Ticket>() {
            @Override public void onResponse(Call<Ticket> call, Response<Ticket> resp) {
                if (resp.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Criado: #" + resp.body().id, Toast.LENGTH_SHORT).show();
                    loadTickets();
                } else {
                    Toast.makeText(MainActivity.this, "Falha ao criar: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Ticket> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
