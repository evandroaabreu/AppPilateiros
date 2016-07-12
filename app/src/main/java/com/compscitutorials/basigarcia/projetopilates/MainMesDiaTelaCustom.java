package com.compscitutorials.basigarcia.projetopilates;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.compscitutorials.basigarcia.navigationdrawervideotutorial.R;
import com.compscitutorials.basigarcia.projetopilates.database.DataBase;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmes;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiapessoas;
import com.compscitutorials.basigarcia.projetopilates.domain.RepositorioContato;
import com.compscitutorials.basigarcia.projetopilates.domain.ServicoAula;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Evandro on 12/05/2016.
 */
public class MainMesDiaTelaCustom extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private String cadMesDiaId;
    private TextView txtDiaMesAno;
    private EditText edtNomeAluno;
    private RepositorioContato repositorioContato;
    private ServicoAula servicoAula;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private ListView lstAluno;
    private ArrayAdapter<Cadmesdiapessoas> adpAluno;

    private ListaPessoasAdapter adpPessoas;
    private Spinner spTpAulaCadMes;
    private Spinner spTpAulaCadMesDia;
    private Spinner spTpHora;
    private Spinner spTpAula;

    private Context ctx = null;

    List<String> lblDias;

    private String array_spinner[];

    private String array_spinnerhora[];

    private String DiaMesANO;
    private String hora;

    private Button btnSalvar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_act_cadastro_aula_mes_dia_tela_custom, container, false);

        ctx = this.getActivity();
        dataBase = new DataBase(ctx);
        conn = dataBase.getWritableDatabase();
        repositorioContato = new RepositorioContato(conn);

        List<String> labels = repositorioContato.lstCadMes(ctx);

        txtDiaMesAno = (TextView) rootView.findViewById(R.id.edtdata);

        btnSalvar = (Button) rootView.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);



        spTpAulaCadMes = (Spinner) rootView.findViewById(R.id.spTpAulaCadMes);
        ArrayAdapter adapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, labels);
        spTpAulaCadMes.setAdapter(adapter);
        spTpAulaCadMes.setOnItemSelectedListener(this);

        spTpAulaCadMesDia = (Spinner) rootView.findViewById(R.id.spTpAulaCadMesDia);
        spTpAulaCadMesDia.setOnItemSelectedListener(this);

        spTpHora = (Spinner) rootView.findViewById(R.id.spHoras);
        spTpHora.setOnItemSelectedListener(this);

        spTpAula = (Spinner) rootView.findViewById(R.id.spTpAula);


        lblDias = new ArrayList<>();

        // size of the array.
        array_spinner = new String[6];
        array_spinner[0]="";
        array_spinner[1]="16:00";
        array_spinner[2]="17:00";
        array_spinner[3]="18:00";
        array_spinner[4]="19:00";
        array_spinner[5]="20:00";

        spTpHora = (Spinner) rootView.findViewById(R.id.spHoras);
        ArrayAdapter adapterHora = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, array_spinner);
        spTpHora.setAdapter(adapterHora);

        DiaMesANO = "";
        hora = "";

        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.spTpAulaCadMes)
        {
            lblDias = repositorioContato.lstCadMesDia(spinner.getItemAtPosition(position).toString());
            ArrayAdapter adapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, lblDias);
            spTpAulaCadMesDia.setAdapter(adapter);

        }

        if(spinner.getId() == R.id.spTpAulaCadMesDia)
        {
            DiaMesANO = spinner.getItemAtPosition(position).toString();
        }

        if(spinner.getId() == R.id.spHoras)
        {

            hora = spinner.getItemAtPosition(position).toString();
            // size of the array.
            array_spinnerhora = new String[2];
            array_spinnerhora[0]="Equipamento";
            array_spinnerhora[1]="Aula de Solo";

            servicoAula = new ServicoAula();

            ArrayAdapter adapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, array_spinnerhora);
            spTpAula.setAdapter(adapter);
            spTpAula.setSelection(servicoAula.processoTpAula(DiaMesANO, hora));
            spTpAula.setEnabled(false);



        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalvar:
                try {
                    dataBase = new DataBase(ctx);
                    conn = dataBase.getWritableDatabase();
                    repositorioContato = new RepositorioContato(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }
    }
}
