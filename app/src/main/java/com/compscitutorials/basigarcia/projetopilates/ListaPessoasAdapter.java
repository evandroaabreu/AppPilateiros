package com.compscitutorials.basigarcia.projetopilates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compscitutorials.basigarcia.navigationdrawervideotutorial.R;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiapessoas;

import java.util.List;


/**
 * Created by Evandro on 14/04/2016.
 */
public class ListaPessoasAdapter extends ArrayAdapter<Cadmesdiapessoas> {
    private Context context;
    private List<Cadmesdiapessoas> pessoas = null;


    public ListaPessoasAdapter(Context context, List<Cadmesdiapessoas> pessoas) {
        super(context,0, pessoas);
        this.pessoas = pessoas;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Cadmesdiapessoas cadpessoas = pessoas.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_list_pessoas, null);

        ImageView imageViewZombie = (ImageView) view.findViewById(R.id.image_view_zombie);
        imageViewZombie.setImageResource(cadpessoas.getImagem());

        TextView textViewNomeZombie = (TextView) view.findViewById(R.id.text_view_nome_zombie);
        textViewNomeZombie.setText(cadpessoas.getNome());

        return view;
    }
}
