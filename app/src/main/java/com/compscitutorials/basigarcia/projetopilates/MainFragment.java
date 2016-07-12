package com.compscitutorials.basigarcia.projetopilates;


import android.content.Context;
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
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmes;
import com.compscitutorials.basigarcia.projetopilates.domain.RepositorioContato;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener  {

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private ListView lstCadMes;
    private ArrayAdapter<Cadmes> adpCadmes;
    RepositorioContato repositorioContato = null;



    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final Context ctx = this.getActivity();
        dataBase = new DataBase(ctx);
        conn = dataBase.getWritableDatabase();
        repositorioContato = new RepositorioContato(conn);

        lstCadMes = (ListView)rootView.findViewById(R.id.lstCadMes);
        lstCadMes.setOnItemClickListener(this);

        adpCadmes = repositorioContato.buscaCadMes(ctx);
        lstCadMes.setAdapter(adpCadmes);


        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    /*    Intent it = new Intent(getActivity(), actCadastroAulaMesDia.class);

        Cadmes cadmes = new Cadmes();
        cadmes=adpCadmes.getItem(position);
        it.putExtra("MesANO", cadmes.getCadMesANO());
        ///Log.d("**********", cadmes.);
        startActivity(it);*/

/*
        MainMesDia newFragment = new MainMesDia();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_POSITION, position);
        newFragment.setArguments(args);
*/
        Cadmes cadmes = new Cadmes();
        cadmes=adpCadmes.getItem(position);
        MainMesDia newFragment = new MainMesDia();
        Bundle args = new Bundle();
        args.putString("MesANO", cadmes.getCadMesANO());
        newFragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
