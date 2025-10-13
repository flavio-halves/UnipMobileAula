package com.example.helpdeskunipassismobile.data;

import com.example.helpdeskunipassismobile.model.Ticket;
import com.example.helpdeskunipassismobile.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // TICKETS
    @GET("/api/tickets")
    Call<List<Ticket>> getTickets();

    @POST("/api/tickets")
    Call<Ticket> createTicket(@Body Ticket ticket);

    // USUÁRIOS
    @GET("/api/usuarios")
    Call<List<Usuario>> getUsuarios();

    @POST("/api/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    // >>> ADICIONE ESTE MÉTODO <<<
    @PUT("/api/usuarios/{id}")
    Call<Void> updateUsuario(@Path("id") int id, @Body Usuario usuario);
}
