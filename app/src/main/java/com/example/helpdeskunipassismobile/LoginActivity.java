package com.example.helpdeskunipassismobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Usuario;
import com.example.helpdeskunipassismobile.ui.AddUserActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private ProgressBar progress;
    private Button btnEntrar, btnIrCadastro;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        api = ApiClient.getService();

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        progress = findViewById(R.id.progress);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnIrCadastro = findViewById(R.id.btnIrCadastro);

        btnEntrar.setOnClickListener(v -> doLogin());
        btnIrCadastro.setOnClickListener(v ->
                startActivity(new Intent(this, AddUserActivity.class))
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Auto-login se já logado
        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        int userId = sp.getInt("userId", -1);
        if (userId != -1) {
            //startActivity(new Intent(this, MainActivity.class));
            startActivity(new Intent(this, HomeActivity.class));

            finish();

        }

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            SharedPreferences sp1 = getSharedPreferences("app", MODE_PRIVATE);
            sp1.edit().clear().apply(); // ou: sp.edit().remove("userId").apply();

            startActivity(new Intent(this, LoginActivity.class)
                    .putExtra("forceLogin", true));
            finishAffinity();
        });

    }

    private void doLogin() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        api.getUsuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> resp) {
                setLoading(false);

                if (!resp.isSuccessful() || resp.body() == null) {
                    Toast.makeText(LoginActivity.this, "Falha: " + resp.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario match = null;
                for (Usuario u : resp.body()) {
                    if (email.equalsIgnoreCase(u.email) && senha.equals(u.senha)) {
                        match = u;
                        break;
                    }
                }

                if (match != null) {
                    saveSession(match);
                   // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnEntrar.setEnabled(!loading);
        btnIrCadastro.setEnabled(!loading);
        edtEmail.setEnabled(!loading);
        edtSenha.setEnabled(!loading);
    }

    private void saveSession(Usuario u) {
        SharedPreferences sp = getSharedPreferences("app", MODE_PRIVATE);
        sp.edit()
                .putInt("userId", u.id)
                .putString("userName", u.nome)
                .putString("userEmail", u.email)
                .apply();
    }
}
