package com.compscitutorials.basigarcia.projetopilates;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.compscitutorials.basigarcia.navigationdrawervideotutorial.R;
import com.compscitutorials.basigarcia.projetopilates.database.DataBase;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdia;
import com.compscitutorials.basigarcia.projetopilates.domain.RepositorioContato;


/**
 * Created by Evandro on 12/05/2016.
 */
public class MainMesDia extends Fragment implements AdapterView.OnItemClickListener {

    private RepositorioContato repositorioContato;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private ListView lstCadMesAno;
    private ArrayAdapter<Cadmesdia> adpCadmesano;
    private String valor;

    public MainMesDia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_act_cadastro_aula_mes_dia, container, false);
        valor =  getArguments().getString("MesANO");


        lstCadMesAno = (ListView) rootView.findViewById(R.id.lstCadMesAno);
        lstCadMesAno.setOnItemClickListener(this);

        try {
            final Context ctx = this.getActivity();
            dataBase = new DataBase(ctx);
            conn = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(conn);

            adpCadmesano = repositorioContato.buscaCadDiaMesAno(ctx, valor);
            lstCadMesAno.setAdapter(adpCadmesano);

        } catch (SQLException ex){

        }
        // Inflate the layout for this fragment
        return rootView;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cadmesdia cadmesdia = new Cadmesdia();
        cadmesdia=adpCadmesano.getItem(position);
        MainMesDiaHora newFragment = new MainMesDiaHora();
        Bundle args = new Bundle();
        args.putString("MesANO", cadmesdia.getCadmesAno());
        args.putString("DiaMesANO", cadmesdia.getCadmesdiaDate());
        args.putString("CadMesDia_ID", String.valueOf(cadmesdia.getCadmesdiaId()));
        newFragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();



    }
}
