package com.example.helpdeskunipassismobile.data;




import com.example.helpdeskunipassismobile.model.Ticket;
import com.example.helpdeskunipassismobile.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // TICKETS
    @GET("/api/tickets")
    Call<List<Ticket>> getTickets();

    @GET("/api/tickets/{id}")
    Call<Ticket> getTicket(@Path("id") int id);

    @POST("/api/tickets")
    Call<Ticket> createTicket(@Body Ticket ticket);

    // USUÁRIOS (se precisar)
    @GET("/api/usuarios")
    Call<List<Usuario>> getUsuarios();

    @POST("/api/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);
}

