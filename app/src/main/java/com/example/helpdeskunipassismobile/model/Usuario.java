package com.example.helpdeskunipassismobile.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("id")
    public int id;

    @SerializedName("nome")
    public String nome;

    @SerializedName("email")
    public String email;

    @SerializedName("senha")
    public String senha;
}
