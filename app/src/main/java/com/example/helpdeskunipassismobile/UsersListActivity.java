package com.example.helpdeskunipassismobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpdeskunipassismobile.data.ApiClient;
import com.example.helpdeskunipassismobile.data.ApiService;
import com.example.helpdeskunipassismobile.model.Usuario;
import com.example.helpdeskunipassismobile.ui.UserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListActivity extends AppCompatActivity implements UserAdapter.OnUserClick {

    private ApiService api;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        api = ApiClient.getService();

        RecyclerView rv = findViewById(R.id.rvUsers);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this);
        rv.setAdapter(adapter);

        loadUsers();
    }

    private void loadUsers() {
        api.getUsuarios().enqueue(new Callback<List<Usuario>>() {
            @Override public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> resp) {
                if (resp.isSuccessful() && resp.body() != null) {
                    adapter.setData(resp.body());
                } else {
                    Toast.makeText(UsersListActivity.this, "Falha: " + resp.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(UsersListActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onUserClick(Usuario u) {
        Intent i = new Intent(this, EditUserActivity.class);
        i.putExtra("id", u.id);
        i.putExtra("nome", u.nome);
        i.putExtra("email", u.email);
        startActivity(i);
    }
}
