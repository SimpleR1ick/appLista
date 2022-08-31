package com.example.minhalista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Prenche o recycle view com elementos de interface
public class MeuAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MeuItem> itens;

    public MeuAdapter(MainActivity mainActivity, List<MeuItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }
    @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflador (Cria um elemento de interface referente a um objeto)
        LayoutInflater inflater = LayoutInflater.from(mainActivity);

        // Constrói (Um item a partir do layout (.xml))
        View v = inflater.inflate(R.layout.item, parent, false);
        return new MeuViewHolder(v);
    }

    @Override
    // Recebe como parametro a interface gráfica e preenche-a com os itens
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Dados do item a ser preenchido
        MeuItem item = itens.get(position);

        // View que estava armazenada
        View v = holder.itemView;

        // Obtendo os elementos de dentro da View v guardada pelo holder
        TextView tvTitulo = v.findViewById(R.id.tvTitulo);
        TextView tvDescricao = v.findViewById(R.id.tvDescricao);
        ImageView imgView = v.findViewById(R.id.imvFoto);

        // Indicando os elementos do item a ser criado com os dados de dentro da view
        tvTitulo.setText(item.titulo);
        tvDescricao.setText(item.descricao);
        imgView.setImageBitmap(item.img);

    }
    @Override
    // Retorna o total de itens
    public int getItemCount() {
        return itens.size();
    }
}
