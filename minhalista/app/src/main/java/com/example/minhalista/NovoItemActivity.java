package com.example.minhalista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class NovoItemActivity extends AppCompatActivity {

    static int GALERIA_REQUEST = 1; //código da requisição
    Uri fotoSelecionadaURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        //recuperando os elementos da tela
        ImageButton imbGaleria = findViewById(R.id.imbGaleria);
        EditText etTitulo = findViewById(R.id.etTitulo);
        EditText etDescricao = findViewById(R.id.etDescricao);
        Button btnAdd = findViewById(R.id.btnAdicionar);

        //criando função para clique no image button
        imbGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para abrir a galeria do celular
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT); //Intent implícito: apenas a ação, e não o local de origem e destino

                //para obter apenas imagens, de qualquer extensão
                i.setType("image/*");

                //inicia o Intent com o código da requisição para devolver uma resposta
                startActivityForResult(i, GALERIA_REQUEST);
            }
        });

        //programando clique do botão adicionar
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperando os valores preenchidos nos EditText
                String titulo = etTitulo.getText().toString();
                String descricao = etDescricao.getText().toString();

                //verificação se titulo, descricao e foto foram preenchidos
                if(titulo.isEmpty()){
                    Toast.makeText(NovoItemActivity.this, "Você não adicionou nenhum título! :(", Toast.LENGTH_SHORT).show();
                    //para e sai da função
                    return;
                }
                if(descricao.isEmpty()){
                    Toast.makeText(NovoItemActivity.this, "Você não adicionou nenhuma descrição! :(", Toast.LENGTH_SHORT).show();
                    //para e sai da função
                    return;
                }
                if(fotoSelecionadaURI == null){
                    Toast.makeText(NovoItemActivity.this, "Você não selecionou nenhuma imagem! :(", Toast.LENGTH_SHORT).show();
                    //para e sai da função
                    return;
                }

                //criação do Intent para transmitir os dados para a próxima activity
                Intent i = new Intent();
                i.setData(fotoSelecionadaURI);
                i.putExtra("titulo", titulo);
                i.putExtra("descricao", descricao);
                setResult(Activity.RESULT_OK, i); //função de activity: resultado OK e enviar o Intent i
                finish(); //fechar a activity e voltar à MainActivity

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //verificando o código da requisição
        if(requestCode == GALERIA_REQUEST){
            //verificando se o status da requisição == OK
            if(resultCode == Activity.RESULT_OK){
                //selecionando a URI (caminho nas pastas privadas do app/endereço de um dado que não pertence à app) da imagem escolhida
                fotoSelecionadaURI = data.getData();

                //recuperando o campo de preview da foto
                ImageView fotoPreview = findViewById(R.id.imvFotoPreview);
                //colocando a foto escolhida no campo de preview
                fotoPreview.setImageURI(fotoSelecionadaURI);
            }
        }
    }
}