package com.example.helpdeskunipassismobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Ticket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTicketActivity extends AppCompatActivity {
    private EditText edtTitulo, edtDescricao;
    private ProgressBar progress;
    private ApiService api;
    private int userId;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        api = ApiClient.getService();

        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        userId = sp.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Faça login novamente", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescricao = findViewById(R.id.edtDescricao);
        progress = findViewById(R.id.progress);
        Button btnCriar = findViewById(R.id.btnCriar);

        btnCriar.setOnClickListener(v -> criarTicket());
    }

    private void criarTicket() {
        String titulo = edtTitulo.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        if (titulo.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Informe título e descrição", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        Ticket t = new Ticket();
        t.titulo = titulo;
        t.descricao = descricao;
        t.usuarioId = userId;

        api.createTicket(t).enqueue(new Callback<Ticket>() {
            @Override public void onResponse(Call<Ticket> call, Response<Ticket> resp) {
                progress.setVisibility(View.GONE);
                if (resp.isSuccessful() && resp.body() != null) {
                    Toast.makeText(CreateTicketActivity.this,
                            "Ticket #" + resp.body().id + " criado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateTicketActivity.this,
                            "Falha: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Ticket> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(CreateTicketActivity.this,
                        "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
