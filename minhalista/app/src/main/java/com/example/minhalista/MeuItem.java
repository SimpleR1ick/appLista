package com.example.minhalista;

import android.graphics.Bitmap;

public class MeuItem {

    Bitmap img;
    String titulo;
    String descricao;

    // Construtor do item da lista
    public MeuItem(Bitmap img, String titulo, String descricao) {
        this.img = img;
        this.titulo = titulo;
        this.descricao = descricao;
    }
}