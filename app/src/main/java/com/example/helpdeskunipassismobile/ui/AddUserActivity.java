package com.example.helpdeskunipassismobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpdeskunipassismobile.R;
import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha;
    private ProgressBar progress;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        api = ApiClient.getService();

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        progress = findViewById(R.id.progress);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> salvar());
    }

    private void salvar() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha nome, email e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        Usuario u = new Usuario();
        u.nome = nome;
        u.email = email;
        u.senha = senha;

        api.createUsuario(u).enqueue(new Callback<Usuario>() {
            @Override public void onResponse(Call<Usuario> call, Response<Usuario> resp) {
                progress.setVisibility(View.GONE);
                if (resp.isSuccessful() && resp.body() != null) {
                    Toast.makeText(AddUserActivity.this, "Usuário criado: #" + resp.body().id, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddUserActivity.this, "Falha: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Usuario> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(AddUserActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
