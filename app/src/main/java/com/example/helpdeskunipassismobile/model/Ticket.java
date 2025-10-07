package com.example.helpdeskunipassismobile.model;

import com.google.gson.annotations.SerializedName;

public class Ticket {
    @SerializedName("id")
    public int id;

    @SerializedName("titulo")
    public String titulo;

    @SerializedName("descricao")
    public String descricao;

    @SerializedName("usuarioId")
    public Integer usuarioId;

    @SerializedName("usuario")
    public Usuario usuario;

    @SerializedName("dataAbertura")
    public String dataAbertura; // pode mapear como String (ISO) para simplicidade
}
