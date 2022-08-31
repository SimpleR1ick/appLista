package com.example.minhalista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//preencher o recycle view com elementos de interface
public class MeuAdapter extends RecyclerView.Adapter {

    MainActivity mainActivity;
    List<MeuItem> itens;

    public MeuAdapter(MainActivity mainActivity, List<MeuItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    //constrói o item, retorna a interface gráfica e guarda no guardador de view
    //viewType -> se os itens tiverem layouts diferentes, é usado como um id para indicar o layout a ser usado
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflador = cria um elemento de interface referente a um objeto
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        //constrói o item a partir do layout (.xml) definido
        View v = inflater.inflate(R.layout.item, parent, false);
        return new MeuViewHolder(v);
    }

    @Override
    //recebe a interface gráfica e preenche-a com os itens na posição escolhida
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //dados do item a ser preenchido
        MeuItem item = itens.get(position);

        //view que estava guardada
        View v = holder.itemView;

        //pegando os elementos de dentro da View v guardada pelo holder
        TextView tvTitulo = v.findViewById(R.id.tvTitulo);
        TextView tvDescricao = v.findViewById(R.id.tvDescricao);
        ImageView imgView = v.findViewById(R.id.imvFoto);

        //setando os elementos do item a ser criado com os dados de dentro da view
        tvTitulo.setText(item.titulo);
        tvDescricao.setText(item.descricao);
        imgView.setImageBitmap(item.img);

    }

    @Override
    //retorna o total de itens atual
    public int getItemCount() {
        return itens.size();
    }
}
