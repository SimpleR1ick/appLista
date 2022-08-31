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
    //código da requisição
    static int NEW_ITEM_REQUEST = 1;
    //lista de itens
    List<MeuItem> itens = new ArrayList<>();
    //colocando em escopo da classe para que ele não fique só dentro do Result
    MeuAdapter meuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //constrói o layout (.xml) definido, como um interpretador de HTML
        setContentView(R.layout.activity_main);

        //selecionando o botão fabAdicionar da MainActivity
        FloatingActionButton fabAdicionar = findViewById(R.id.fabAdicionar);

        //criando uma função para o click do botão
        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //criando um objeto Intent para trocar de atividade
                Intent i = new Intent(MainActivity.this, NovoItemActivity.class);
                //trocando de atividade e esperando receber dados
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });


        /* para usar RecyclerView precisa de:
        LayoutManager - Ensinar a como dispor os itens
        Adapter - Classe para ensinar a como criar e preencher item da lista
        ViewHolder - Guardador de view (item [elemento gráfico])

        o recyclerview cuida do scroll e da reciclagem dos itens na tela
        */

        RecyclerView rvLista = findViewById(R.id.rvLista);
        //para manter o mesmo tamanho para todos os itens (nesse caso; otimização; usar sempre que os itens tiverem o mesmo tamanho)
        //default: true
        rvLista.setHasFixedSize(true);

        //LinearLayoutManager controla como os itens vão aparecer na lista (vertical ou horizontal)
        //default: vertical
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvLista.setLayoutManager(layoutManager);
        //GridLayoutManager cria uma grade de itens de mesmo tamanho, é só passar a quantidade de colunas

        meuAdapter = new MeuAdapter(MainActivity.this, itens);
        rvLista.setAdapter(meuAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_ITEM_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                //pegando os dados do Intent data
                Uri fotoSelecionadaURI = data.getData();
                String titulo = data.getStringExtra("titulo");
                String descricao = data.getStringExtra("descricao");

                //convertendo URI para Bitmap
                try {
                    //criando um bitmap para copiar a imagem, e não só obtendo seu endereço
                    //nesse caso, copiar = copiando da memória em disco para memória RAM/volátil
                    Bitmap fotoSelecionadaBitmap = Utils.getBitmap(MainActivity.this, fotoSelecionadaURI, 3000, 300);
                    MeuItem item = new MeuItem(fotoSelecionadaBitmap, titulo, descricao);

                    //adicionando o item à lista de itens
                    itens.add(item);
                    //notificando o adapter de modificações na lista (adicionar/excluir/alterar itens)
                    meuAdapter.notifyDataSetChanged();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}