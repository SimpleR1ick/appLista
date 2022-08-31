package com.example.minhalista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST = 1; // Código da requisição

    // Array de itens
    List<MeuItem> itens = new ArrayList<>();
    MeuAdapter meuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Constrói o layout (.xml)
        setContentView(R.layout.activity_main);

        // Seleciona o botão fabAdicionar na MainActivity
        FloatingActionButton fabAdicionar = findViewById(R.id.fabAdicionar);

        // Função click do botão
        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Criando um objeto Intent de troca de activity
                Intent i = new Intent(MainActivity.this, NovoItemActivity.class);
                // Trocando de activity e espera-se receber dados dela
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });
        RecyclerView rvLista = findViewById(R.id.rvLista);
        // Mantem todos os itens o mesmo tamanho)
        rvLista.setHasFixedSize(true);

        // Define e  controla como os itens vão aparecer na lista
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvLista.setLayoutManager(layoutManager);

        // Cria uma grade de itens de mesmo tamanho
        meuAdapter = new MeuAdapter(MainActivity.this, itens);
        rvLista.setAdapter(meuAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_ITEM_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                // Obtendo os dados do Intent data
                Uri fotoSelecionadaURI = data.getData();
                String titulo = data.getStringExtra("titulo");
                String descricao = data.getStringExtra("descricao");

                // Convertendo URI para Bitmap
                try {
                    Bitmap fotoSelecionadaBitmap = Utils.getBitmap(MainActivity.this, fotoSelecionadaURI, 3000, 300);
                    MeuItem item = new MeuItem(fotoSelecionadaBitmap, titulo, descricao);

                    // Adiciona o item à lista de itens
                    itens.add(item);
                    meuAdapter.notifyDataSetChanged();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}