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

    static int GALERIA_REQUEST = 1; // Código da requisição
    Uri fotoSelecionadaURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        // Obtendo os elementos da tela
        ImageButton imbGaleria = findViewById(R.id.imbGaleria);
        EditText etTitulo = findViewById(R.id.etTitulo);
        EditText etDescricao = findViewById(R.id.etDescricao);
        Button btnAdd = findViewById(R.id.btnAdicionar);

        // Função click do img Button
        imbGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre a galeria do celular
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Intent implícito: Executa apenas a ação

                // Obter apenas imagens, de qualquer extensão (JPEG, PNG, BMP, GIF e TIFF)
                i.setType("image/*");

                // Inicia o Intent com o código da requisição
                startActivityForResult(i, GALERIA_REQUEST);
            }
        });

        // Função click do botão adicionar
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtendo os valores preenchidos nos EditText
                String titulo = etTitulo.getText().toString();
                String descricao = etDescricao.getText().toString();

                // Verificação se titulo, descricao e foto foram preenchidos
                if (titulo.isEmpty()){
                    Toast.makeText(NovoItemActivity.this, "Adicione um titulo!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (descricao.isEmpty()){
                    Toast.makeText(NovoItemActivity.this, "Adicione uma descrição!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fotoSelecionadaURI == null){
                    Toast.makeText(NovoItemActivity.this, "Adicione uma imagem!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Criando um Intent para transmitir os dados obtidos para a próxima activity
                Intent i = new Intent();
                i.setData(fotoSelecionadaURI);
                i.putExtra("titulo", titulo);
                i.putExtra("descricao", descricao);
                setResult(Activity.RESULT_OK, i); // Função da activity: resultado OK e enviar o Intent i
                finish(); // Fechar a activity e vai para à MainActivity
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificando o código da requisição
        if (requestCode == GALERIA_REQUEST){

            // Verificando se o status da requisição == OK
            if (resultCode == Activity.RESULT_OK){
                // Obtendo a URI (path do arquivo)
                fotoSelecionadaURI = data.getData();

                // Obtendo o campo de preview da foto
                ImageView fotoPreview = findViewById(R.id.imvFotoPreview);

                // Adicionando a foto no imagepreview
                fotoPreview.setImageURI(fotoSelecionadaURI);
            }
        }
    }
}