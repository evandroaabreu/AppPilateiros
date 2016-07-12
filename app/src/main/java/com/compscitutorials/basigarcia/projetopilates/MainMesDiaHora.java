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
import android.widget.TextView;

import com.compscitutorials.basigarcia.navigationdrawervideotutorial.R;
import com.compscitutorials.basigarcia.projetopilates.database.DataBase;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdia;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiahora;
import com.compscitutorials.basigarcia.projetopilates.domain.RepositorioContato;

/**
 * Created by Evandro on 12/05/2016.
 */
public class MainMesDiaHora  extends Fragment implements AdapterView.OnItemClickListener{

    private String MesAno;
    private String cadMesDiaId;
    private RepositorioContato repositorioContato;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private ListView lstCadMesAnoHora;
    private ArrayAdapter<Cadmesdiahora> adpCadmesanoHora;
    private TextView txtData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_act_cadastro_aula_mes_dia_hora, container, false);
        txtData = (TextView) rootView.findViewById(R.id.txtData);

        MesAno =  getArguments().getString("MesANO");
        cadMesDiaId = getArguments().getString("CadMesDia_ID");
        Cadmesdia cadmesdia = new Cadmesdia();

        txtData.setText(getArguments().getString("DiaMesANO") + " ( " + (cadmesdia.pesquisarDiaSemana(cadmesdia.diaDaSemana(getArguments().getString("DiaMesANO")))) + " )");


        lstCadMesAnoHora = (ListView)rootView.findViewById(R.id.listView);
        lstCadMesAnoHora.setOnItemClickListener(this);

        try {

            final Context ctx = this.getActivity();
            dataBase = new DataBase(ctx);
            conn = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(conn);
            ///repositorioContato.testeInsertContatos();

            adpCadmesanoHora = repositorioContato.buscaHora(ctx, cadMesDiaId,getArguments().getString("DiaMesANO"));
            lstCadMesAnoHora.setAdapter(adpCadmesanoHora);



        } catch (SQLException ex){
        }


        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cadmesdiahora cadmesdiahora = new Cadmesdiahora();
        cadmesdiahora=adpCadmesanoHora.getItem(position);

        MainMesDiaTela newFragment = new MainMesDiaTela();
        Bundle args = new Bundle();
        args.putInt("IdHora", cadmesdiahora.getIdHora());
        args.putString("Hora", cadmesdiahora.getCadmesdiahora());
        args.putString("MesANO", MesAno);
        args.putString("DiaMesANO", txtData.getText().toString());
        args.putString("CadMesDia_ID", String.valueOf(cadmesdiahora.getCadmesdiaId()));
        newFragment.setArguments(args);


        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
