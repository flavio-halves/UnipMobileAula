package com.example.helpdeskunipassismobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvBemVindo       = findViewById(R.id.tvBemVindo);
        Button btnCriarTicket     = findViewById(R.id.btnCriarTicket);
        Button btnListarTickets   = findViewById(R.id.btnListarTickets);
        Button btnCriarUsuario    = findViewById(R.id.btnCriarUsuario);
        Button btnListarUsuarios  = findViewById(R.id.btnListarUsuarios);
        Button btnSair            = findViewById(R.id.btnSair);

        // saudação
        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        String userName = sp.getString("userName", "Usuário");
        tvBemVindo.setText("Bem-vindo, " + userName + "!");

        // ✅ rotas certas:
        btnCriarTicket.setOnClickListener(v ->
                startActivity(new Intent(this, CreateTicketActivity.class))
        );

        btnListarTickets.setOnClickListener(v ->
                startActivity(new Intent(this, TicketsListActivity.class))
        );

        btnCriarUsuario.setOnClickListener(v ->
                startActivity(new Intent(this, com.example.helpdeskunipassismobile.ui.AddUserActivity.class))
        );

        btnListarUsuarios.setOnClickListener(v ->
                startActivity(new Intent(this, UsersListActivity.class))
        );

        if (btnSair != null) {
            btnSair.setOnClickListener(v -> {
                sp.edit().clear().apply();
                finishAffinity();
            });
        }
        btnSair.setOnClickListener(v -> {
            sp.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class).putExtra("forceLogin", true));
            finish();
        });
    }
    }


