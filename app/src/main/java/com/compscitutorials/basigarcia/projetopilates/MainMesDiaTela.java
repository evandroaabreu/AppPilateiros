package com.compscitutorials.basigarcia.projetopilates;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiapessoas;
import com.compscitutorials.basigarcia.projetopilates.domain.RepositorioContato;
import com.compscitutorials.basigarcia.projetopilates.domain.ServicoAula;

import java.util.List;


/**
 * Created by Evandro on 12/05/2016.
 */
public class MainMesDiaTela extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String MesAno;
    private String DiaMesANO;
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
    private String array_spinner[];

    private Button btnSalvar;
    private Spinner spTpAula;

    private Integer idHora;
    private String hora;

    private ListView listaDeCursos;
    private Context ctx = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_act_cadastro_aula_mes_dia_tela, container, false);

        ctx = this.getActivity();
        txtDiaMesAno = (TextView) rootView.findViewById(R.id.edtdata);

        ListView listaDeCursos = (ListView) rootView.findViewById(R.id.lstAluno);

        btnSalvar = (Button) rootView.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);

        edtNomeAluno = (EditText) rootView.findViewById(R.id.edtNomeAluno);

        lstAluno = (ListView)rootView.findViewById(R.id.lstAluno);
        lstAluno.setOnItemClickListener(this);

        hora=  getArguments().getString("Hora");
        idHora =  getArguments().getInt("IdHora");
        cadMesDiaId =  getArguments().getString("CadMesDia_ID");
        MesAno =  getArguments().getString("MesANO");
        txtDiaMesAno.setText( getArguments().getString("DiaMesANO")+" - Hora:"+ getArguments().getString("Hora"));
        DiaMesANO =  getArguments().getString("DiaMesANO");



        // size of the array.
        array_spinner = new String[2];
        array_spinner[0]="Equipamento";
        array_spinner[1]="Aula de Solo";

        servicoAula = new ServicoAula();

        spTpAula = (Spinner) rootView.findViewById(R.id.spTpAula);
        ArrayAdapter adapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, array_spinner);
        spTpAula.setAdapter(adapter);
        spTpAula.setSelection(servicoAula.processoTpAula(DiaMesANO, hora));
        spTpAula.setEnabled(false);


        carregaListView(Integer.valueOf(cadMesDiaId), idHora, listaDeCursos);
        return rootView;
    }



        @Override
    public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnSalvar:

                    try {
                        dataBase = new DataBase(ctx);
                        conn = dataBase.getWritableDatabase();
                        repositorioContato = new RepositorioContato(conn);
                        ListView listaDeCursos = (ListView) getActivity().findViewById(R.id.lstAluno);
                        repositorioContato.salvarTela(cadMesDiaId, edtNomeAluno.getText().toString(), "P", idHora);
                        repositorioContato.alterarCadmesdia(Integer.valueOf(cadMesDiaId), spTpAula.getSelectedItem().toString(), "P");
                        repositorioContato.alterarCadHora(idHora, Integer.valueOf(cadMesDiaId), spTpAula.getSelectedItem().toString(), hora);
                        edtNomeAluno.setText("");
                        Toast.makeText(ctx, "Registro salvo com sucesso!!!",Toast.LENGTH_SHORT).show();

                        carregaListView(Integer.valueOf(cadMesDiaId), idHora, listaDeCursos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Cadmesdiapessoas cadmesdiapessoas = new Cadmesdiapessoas();
        //cadmesdiapessoas=adpAluno.getItem(position);
        cadmesdiapessoas=adpPessoas.getItem(position);
        dataBase = new DataBase(ctx);
        conn = dataBase.getWritableDatabase();
        repositorioContato = new RepositorioContato(conn);
        ListView listaDeCursos = (ListView) getActivity().findViewById(R.id.lstAluno);
        repositorioContato.excluirAlunos(cadmesdiapessoas.getCadmesdiaPessoasId());
        if (cadmesdiapessoas.getStatus().equals("C")) {
            repositorioContato.salvarPessoasExcluidas(DiaMesANO.substring(0, 11), hora, cadmesdiapessoas.getNome());
        }
        carregaListView(Integer.valueOf(cadmesdiapessoas.getCadmesdiaId()), idHora, listaDeCursos);

    }


    public void carregaListView(Integer valor, Integer idHora, ListView listaDeCursos) {
        try {
            dataBase = new DataBase(ctx);
            conn = dataBase.getWritableDatabase();
            repositorioContato = new RepositorioContato(conn);
//            adpAluno = repositorioContato.buscaAlunos(this, valor,idHora);
//            lstAluno.setAdapter(adpAluno);

            List<Cadmesdiapessoas> pessoas = repositorioContato.buscaAlunosNovo(valor,idHora);
            adpPessoas = new ListaPessoasAdapter(ctx,  pessoas);
            listaDeCursos.setAdapter(adpPessoas);


        } catch (Exception ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
            dlg.setMessage("ERROOOOOOO");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }
}
