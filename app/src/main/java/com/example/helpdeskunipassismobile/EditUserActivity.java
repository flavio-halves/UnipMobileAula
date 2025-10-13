package com.example.helpdeskunipassismobile;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha;
    private ProgressBar progress;
    private ApiService api;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        api = ApiClient.getService();

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        progress = findViewById(R.id.progress);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        userId = getIntent().getIntExtra("id", -1);
        String nome = getIntent().getStringExtra("nome");
        String email = getIntent().getStringExtra("email");

        if (userId == -1) {
            Toast.makeText(this, "Usuário inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Preenche campos
        edtNome.setText(nome);
        edtEmail.setText(email);

        btnSalvar.setOnClickListener(v -> salvar());
    }

    private void salvar() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim(); // opcional

        if (nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Nome e email são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        Usuario u = new Usuario();
        u.id = userId;
        u.nome = nome;
        u.email = email;
        // envie senha apenas se for alterar
        if (!senha.isEmpty()) u.senha = senha;

        api.updateUsuario(userId, u).enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> resp) {
                progress.setVisibility(View.GONE);
                if (resp.isSuccessful()) {
                    Toast.makeText(EditUserActivity.this, "Salvo!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditUserActivity.this, "Falha: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Void> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(EditUserActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
