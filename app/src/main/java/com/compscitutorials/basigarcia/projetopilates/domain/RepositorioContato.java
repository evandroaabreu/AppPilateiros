package com.compscitutorials.basigarcia.projetopilates.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;


import com.compscitutorials.basigarcia.navigationdrawervideotutorial.R;
import com.compscitutorials.basigarcia.projetopilates.database.BDMysql;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmes;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdia;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiahora;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiapessoas;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiapessoasexclui;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by eabreu on 23/09/2015.
 */
public class RepositorioContato {


    private SQLiteDatabase conn;


    public RepositorioContato(SQLiteDatabase conn) {

        this.conn = conn;

    }


    public void testeInsertContatos()  throws ParseException {

        long valueId=0;
        long valueIddia=0;
        long valueIddiahora=0;

        Date data=new Date();
        Format formatter;
        DateFormat dfMesAno = new SimpleDateFormat("MM/yyyy");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = df.parse ("01/01/2016");
        Date dt2 = df.parse("31/12/2016");
        Calendar cal = Calendar.getInstance();
        cal.setTime (dt1);



        int dia_da_semana;
        formatter = new SimpleDateFormat("MMMM");
        String mesInicial;
        String mesAno;
        String mesFinal = null;
        for (Date dt = dt1; dt.compareTo (dt2) <= 0; ) {
            dia_da_semana = dt.getDay();
            mesInicial = String.valueOf(formatter.format(dt));
            mesAno = dfMesAno.format(dt);

            if (!mesInicial.equals(mesFinal)) {
                valueId=0;
                ContentValues values = new ContentValues();
                values.put("CAD_NAME", String.valueOf(formatter.format(dt)));
                values.put("CAD_MES_ANO", mesAno);
                valueId = conn.insertOrThrow(Cadmes.TABELA, null, values);

                mesFinal = String.valueOf(formatter.format(dt));
            }

            if (dia_da_semana==0 | dia_da_semana==6){
            }
            else
            {
                ContentValues valuesdia = new ContentValues();
                valuesdia.put("CADMES_ID", valueId);
                valuesdia.put("CADMESDIA_TYPE", "-");
                valuesdia.put("CADMESDIA_DATE", df.format (dt));
                valuesdia.put("CAD_MES_ANO", dfMesAno.format (dt));
                valuesdia.put("STATUS", "");
                valueIddia= conn.insertOrThrow(Cadmesdia.TABELA, null, valuesdia);
                int i = 0;
                while (i <= 4) {
                    ContentValues valuesdiahora = new ContentValues();
                    valuesdiahora.put("CADMESDIA_ID", valueIddia);
                    if (i == 0) {
                        valuesdiahora.put("CADMESDIAHORA", "16:00");
                    }

                    if (i == 1) {
                        valuesdiahora.put("CADMESDIAHORA", "17:00");
                    }

                    if (i == 2) {
                        valuesdiahora.put("CADMESDIAHORA", "18:00");
                    }

                    if (i == 3) {
                        valuesdiahora.put("CADMESDIAHORA", "19:00");
                    }

                    if (i == 4) {
                        valuesdiahora.put("CADMESDIAHORA", "20:00");
                    }

                    valueIddiahora = conn.insertOrThrow(Cadmesdiahora.TABELA, null, valuesdiahora);
                    i++;

                }


            }
            cal.add (Calendar.DATE, +1);

            dt = cal.getTime();
        }


    }


    public  void selecionaDiaMesAno() {
        Cursor c = conn.rawQuery("SELECT _id, CAD_NAME, CAD_MES_ANO FROM CADMES ", null);
        if(c.moveToFirst()){
            do{
                Integer vidCadMes = Integer.valueOf(c.getString(0));

                String q = "SELECT _id,CADMES_ID,CADMESDIA_DATE,CADMESDIA_TYPE,CAD_MES_ANO FROM CADMESDIA WHERE CADMES_ID = " + vidCadMes;

                Cursor cCadmesDia = conn.rawQuery(q, null);
                if(cCadmesDia.moveToFirst()){
                    do{
                        Log.d("_id", cCadmesDia.getString(0));
                        Log.d("CADMES_ID", cCadmesDia.getString(1));
                        Log.d("CADMESDIA_DATE", cCadmesDia.getString(2).toString());
                        Log.d("CADMESDIA_TYPE", cCadmesDia.getString(3));
                        Log.d("CAD_MES_ANO", cCadmesDia.getString(4));

                        Integer vidCadMesDia = Integer.valueOf(cCadmesDia.getString(0));

                        String qPessoas = "SELECT _id,CADMESDIA_ID,NOME FROM CADMESDIAPESSOAS WHERE CADMESDIA_ID = " + vidCadMesDia;
                        Cursor cCadmesDiaPessoas = conn.rawQuery(qPessoas, null);
                        if(cCadmesDiaPessoas.moveToFirst()){
                            do{
                                Log.d("CADMES_ID", cCadmesDiaPessoas.getString(2));

                            }while(cCadmesDiaPessoas.moveToNext());

                        }




                            }while(cCadmesDia.moveToNext());
                }

            }while(c.moveToNext());
        }

    }

    public List<String> lstCadMesDia(String mesAtual) {
        List<String> listaCadMesDia = new ArrayList<>();
        String qDias = "SELECT  DIA.CADMESDIA_DATE FROM CADMES C , CADMESDIA DIA WHERE C.CAD_NAME = '" + mesAtual + "' AND C._id = DIA.CADMES_ID";
        Cursor cursor = conn.rawQuery(qDias, null);
        Cadmesdia cadmes = new Cadmesdia();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listaCadMesDia.add(cursor.getString(cursor.getColumnIndex("CADMESDIA_DATE"))+ " ( "+ cadmes.pesquisarDiaSemana(cadmes.diaDaSemana(cursor.getString(cursor.getColumnIndex("CADMESDIA_DATE"))))+" )");
            }while (cursor.moveToNext());
        }


        return listaCadMesDia;

    }

    public List<String> lstCadMes(Context context){
        List<String> listaCadMes = new ArrayList<>();
        Cadmes cadmes = new Cadmes();
        Cursor cursor = conn.query(Cadmes.TABELA, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                listaCadMes.add(cursor.getString(cursor.getColumnIndex(cadmes.CAD_NAME)));
            }while (cursor.moveToNext());
        }
        return listaCadMes;
    }


     public ArrayAdapter<Cadmes> buscaCadMes(Context context) {
         ArrayAdapter<Cadmes> adpCadMes = new ArrayAdapter<Cadmes>(context, android.R.layout.simple_list_item_1);

         Cursor cursor = conn.query(Cadmes.TABELA, null, null, null, null, null, null);

         if (cursor.getCount() > 0) {
             cursor.moveToFirst();
             do {
                 Cadmes cadmes = new Cadmes();
                 cadmes.setCadId(cursor.getInt(cursor.getColumnIndex(cadmes.ID)));
                 cadmes.setCadName(cursor.getString(cursor.getColumnIndex(cadmes.CAD_NAME)));
                 cadmes.setCadMesANO(cursor.getString(cursor.getColumnIndex(cadmes.CAD_MES_ANO)));


                 adpCadMes.add(cadmes);
             }while (cursor.moveToNext());
         }
         return adpCadMes;
     }

    public ArrayAdapter<Cadmesdiapessoas> buscaAlunos(Context context, Integer vcod, Integer idHora) {
        ArrayAdapter<Cadmesdiapessoas> adpAlunos = new ArrayAdapter<Cadmesdiapessoas>(context, android.R.layout.simple_list_item_1);

        String qPessoas = "SELECT _id,CADMESDIA_ID,NOME FROM CADMESDIAPESSOAS WHERE CADMESDIA_ID = " + vcod + " AND IDHORA = "+idHora+" ";
        Cursor cursor = conn.rawQuery(qPessoas, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Cadmesdiapessoas cadmesdiapessoas = new Cadmesdiapessoas();
                cadmesdiapessoas.setCadmesdiaPessoasId(cursor.getInt(cursor.getColumnIndex(cadmesdiapessoas.ID)));
                cadmesdiapessoas.setCadmesdiaId(cursor.getString(cursor.getColumnIndex(cadmesdiapessoas.CADMESDIA_ID)));
                cadmesdiapessoas.setNome(cursor.getString(cursor.getColumnIndex(cadmesdiapessoas.NOME)));

                adpAlunos.add(cadmesdiapessoas);
            }while (cursor.moveToNext());
        }
        return adpAlunos;
    }

    public List<Cadmesdiapessoas> buscaAlunosNovo(Integer vcod, Integer idHora) {
        List<Cadmesdiapessoas> pessoas = new ArrayList<>();
        String qPessoas = "SELECT _id,CADMESDIA_ID,NOME,STATUS FROM CADMESDIAPESSOAS WHERE CADMESDIA_ID = " + vcod + " AND IDHORA = "+idHora+" ";
        Cursor cursor = conn.rawQuery(qPessoas, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Cadmesdiapessoas cadmesdiapessoas = new Cadmesdiapessoas();
                cadmesdiapessoas.setCadmesdiaPessoasId(cursor.getInt(cursor.getColumnIndex(cadmesdiapessoas.ID)));
                cadmesdiapessoas.setCadmesdiaId(cursor.getString(cursor.getColumnIndex(cadmesdiapessoas.CADMESDIA_ID)));
                cadmesdiapessoas.setNome(cursor.getString(cursor.getColumnIndex(cadmesdiapessoas.NOME)));
                cadmesdiapessoas.setStatus(cursor.getString(cursor.getColumnIndex(cadmesdiapessoas.STATUS)));

                if (cursor.getString(cursor.getColumnIndex(cadmesdiapessoas.STATUS)).equals("P")) {
                    cadmesdiapessoas.setImagem(R.drawable.icon_form_errado);
                } else {
                    cadmesdiapessoas.setImagem(R.drawable.icon_form_certo);
                }

                pessoas.add(cadmesdiapessoas);
            }while (cursor.moveToNext());
        }

        return pessoas;
    }


    public ArrayAdapter<Cadmesdiahora> buscaHora(Context context, String codigo, String data) {
        ArrayAdapter<Cadmesdiahora> adpHora = new ArrayAdapter<Cadmesdiahora>(context, android.R.layout.simple_list_item_1);

        String select = "SELECT _id,CADMESDIA_ID,CADMESDIAHORA,CADMESDIA_TYPE FROM CADMESDIAHORA WHERE CADMESDIA_ID = '"+ codigo + "' ORDER BY CADMESDIAHORA" ;

        Cursor cursor = conn.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Cadmesdiahora cadmesdiahora= new Cadmesdiahora();
                cadmesdiahora.setIdHora(cursor.getInt(cursor.getColumnIndex(cadmesdiahora.ID)));
                cadmesdiahora.setCadmesdiaId(cursor.getInt(cursor.getColumnIndex(cadmesdiahora.CADMESDIAHORA_ID)));
                cadmesdiahora.setCadmesdiahora(cursor.getString(cursor.getColumnIndex(cadmesdiahora.CADMESDIAHORA)));
                cadmesdiahora.setCadmesdiaType(cursor.getString(cursor.getColumnIndex(cadmesdiahora.CADMESDIA_TYPE)));
                cadmesdiahora.setDataatual(data);
                adpHora.add(cadmesdiahora);
            }while (cursor.moveToNext());
        }

        return adpHora;
    }


    public ArrayAdapter<Cadmesdia> buscaCadDiaMesAno(Context context, String vcodMesAno) {
        ArrayAdapter<Cadmesdia> adpCadMesAno = new ArrayAdapter<Cadmesdia>(context, android.R.layout.simple_list_item_1);

        String select = "SELECT _id,CADMES_ID,CADMESDIA_TYPE,CADMESDIA_DATE,CAD_MES_ANO FROM CADMESDIA WHERE CAD_MES_ANO = '"+ vcodMesAno + "' ORDER BY CADMESDIA_DATE" ;

        Cursor cursor = conn.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Cadmesdia cadmesdia = new Cadmesdia();
                cadmesdia.setCadmesdiaId(cursor.getInt(cursor.getColumnIndex(cadmesdia.ID)));
                cadmesdia.setCadmesdiaType(cursor.getString(cursor.getColumnIndex(cadmesdia.CADMESDIA_TYPE)));
                cadmesdia.setCadmesdiaDate((cursor.getString(cursor.getColumnIndex(cadmesdia.CADMESDIA_DATE))));
                cadmesdia.setCadmesAno(cursor.getString(cursor.getColumnIndex(cadmesdia.CADMESANO)));

                adpCadMesAno.add(cadmesdia);
            }while (cursor.moveToNext());
        }



        return adpCadMesAno;
    }

    public void excluir()
    {
        conn.delete(Cadmes.TABELA,null,null);
        conn.delete(Cadmesdia.TABELA,null,null);
        conn.delete(Cadmesdiapessoas.TABELA,null,null);

    }

    public void salvarPessoasExcluidas(String data, String hora, String nome){
        try {
            ContentValues valuesdiapessoasexcluidas = new ContentValues();
            valuesdiapessoasexcluidas.put("DATA", data);
            valuesdiapessoasexcluidas.put("HORA", hora);
            valuesdiapessoasexcluidas.put("NOME", nome);

            conn.insertOrThrow(Cadmesdiapessoasexclui.TABELA, null, valuesdiapessoasexcluidas);
        } catch (SQLException ex){
            Log.d("CADMES_ID", "Erroo");
        }
    }

    public  void salvarTela(String cadMesDiaId, String nome, String status, Integer idHora){
        try {
            ContentValues valuesdiapessoas = new ContentValues();
            valuesdiapessoas.put("CADMESDIA_ID", cadMesDiaId);
            valuesdiapessoas.put("NOME", nome);
            valuesdiapessoas.put("STATUS", status);
            valuesdiapessoas.put("IDHORA", idHora);


            conn.insertOrThrow(Cadmesdiapessoas.TABELA, null, valuesdiapessoas);
        } catch (SQLException ex){
            Log.d("CADMES_ID", "Erroo");
        }
    }

    public void excluirAlunos(long id) {
        conn.delete(Cadmesdiapessoas.TABELA, " _id = ? ", new String[]{String.valueOf(id)});
    }

    public void AlunosExcluidos(long id) {
        conn.delete(Cadmesdiapessoasexclui.TABELA," _id = ? ", new String[]{String.valueOf(id)});
    }


    public void alterarCadmesdia(Integer vidCadMesAno, String typeCadMesAno, String status) {
        Cadmesdia cadmesdia = new Cadmesdia();

        cadmesdia = buscarPorCadMesdia(vidCadMesAno);
        cadmesdia.setCadmesdiaType(typeCadMesAno);
        cadmesdia.setStatus(status);
        ContentValues values = preencheContentValues(cadmesdia);
        conn.update(Cadmesdia.TABELA, values, "_id = ? ", new String[]{String.valueOf(cadmesdia.getCadmesdiaId())});
    }

    public void alterarCadHora(Integer idHora, Integer idCadMesid, String typeCadMesAno, String hora) {
        Cadmesdiahora cadmesdiahora= new Cadmesdiahora();
        cadmesdiahora.setIdHora(idHora);
        cadmesdiahora.setCadmesdiaId(idCadMesid);
        cadmesdiahora.setCadmesdiaType(typeCadMesAno);
        cadmesdiahora.setCadmesdiahora(hora);
        ContentValues values = ContentValuesHoras(cadmesdiahora);
        conn.update(cadmesdiahora.TABELA, values, "_id = ? ", new String[]{String.valueOf(idHora)});
    }

    private ContentValues preencheContentValues(Cadmesdia cadmesdia)
    {
        ContentValues values = new ContentValues();
        values.put(cadmesdia.ID, cadmesdia.getCadmesdiaId());
        values.put(cadmesdia.CADMESDIA_TYPE, cadmesdia.getCadmesdiaType());
        values.put(cadmesdia.CADMESDIA_DATE, cadmesdia.getCadmesdiaDate());
        values.put(cadmesdia.CADMESANO, cadmesdia.getCadmesAno());
        values.put(cadmesdia.STATUS, cadmesdia.getStatus());

        return values;

    }


    private ContentValues ContentValuesHoras(Cadmesdiahora cadmesdiahora)
    {
        ContentValues values = new ContentValues();

        values.put(cadmesdiahora.ID, cadmesdiahora.getIdHora());
        values.put(cadmesdiahora.CADMESDIAHORA_ID, cadmesdiahora.getCadmesdiaId());
        values.put(cadmesdiahora.CADMESDIAHORA, cadmesdiahora.getCadmesdiahora());
        values.put(cadmesdiahora.CADMESDIA_TYPE, cadmesdiahora.getCadmesdiaType());

        return values;

    }

    private ContentValues ContentValuesPessoas(Cadmesdiapessoas cadmesdiapessoas)
    {
        ContentValues values = new ContentValues();

        values.put(cadmesdiapessoas.ID, cadmesdiapessoas.getCadmesdiaPessoasId());
        values.put(cadmesdiapessoas.CADMESDIA_ID, cadmesdiapessoas.getCadmesdiaId());
        values.put(cadmesdiapessoas.NOME, cadmesdiapessoas.getNome());
        values.put(cadmesdiapessoas.STATUS, cadmesdiapessoas.getStatus());

        return values;

    }


    public void buscarPorPessoas(Cadmesdiapessoas pessoas) {
        ContentValues values = ContentValuesPessoas(pessoas);
        conn.update(Cadmesdiapessoas.TABELA, values, "_id = ? ", new String[]{String.valueOf(pessoas.getCadmesdiaPessoasId())});

    }

    public Cadmesdia buscarPorCadMesdia(Integer vidCadMesAno) {
        String q = "SELECT _id,CADMES_ID,CADMESDIA_DATE,CADMESDIA_TYPE,CAD_MES_ANO FROM CADMESDIA WHERE _id = " + vidCadMesAno;

        Cursor cCadmesDia = conn.rawQuery(q, null);
        Cadmesdia cadmesdia = new Cadmesdia();
        if(cCadmesDia.moveToFirst()){
            do{

                cadmesdia.setCadmesdiaId(Integer.valueOf(cCadmesDia.getString(0)));
                cadmesdia.setCadmesdiaDate(cCadmesDia.getString(2).toString());
                cadmesdia.setCadmesdiaType(cCadmesDia.getString(3));
                cadmesdia.setCadmesAno(cCadmesDia.getString(4));
                /*Log.d("_id", cCadmesDia.getString(0));
                Log.d("CADMES_ID", cCadmesDia.getString(1));
                Log.d("CADMESDIA_DATE", cCadmesDia.getString(2).toString());
                Log.d("CADMESDIA_TYPE", cCadmesDia.getString(3));
                Log.d("CAD_MES_ANO", cCadmesDia.getString(4));*/
            }while(cCadmesDia.moveToNext());
        }

    return  cadmesdia;
    }

    public Cadmesdiahora buscarPorIdHora(Integer idHora) {
        String select = "SELECT _id,CADMESDIA_ID,CADMESDIAHORA,CADMESDIA_TYPE FROM CADMESDIAHORA WHERE _id = "+ idHora + " ORDER BY CADMESDIAHORA" ;
        Cadmesdiahora cadmesdiahora= new Cadmesdiahora();
        Cursor cursor = conn.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                cadmesdiahora.setIdHora(cursor.getInt(cursor.getColumnIndex(cadmesdiahora.ID)));
                cadmesdiahora.setCadmesdiaId(cursor.getInt(cursor.getColumnIndex(cadmesdiahora.CADMESDIAHORA_ID)));
                cadmesdiahora.setCadmesdiahora(cursor.getString(cursor.getColumnIndex(cadmesdiahora.CADMESDIAHORA)));
                cadmesdiahora.setCadmesdiaType(cursor.getString(cursor.getColumnIndex(cadmesdiahora.CADMESDIA_TYPE)));
            }while (cursor.moveToNext());
        }

        return cadmesdiahora;
    }

    public void selectCadmesdia() {
        PreparedStatement stcadmesdiapessoas = null;

        try {

            Statement stSelect = null;
            stSelect = BDMysql.getConexao().createStatement();

            ResultSet rs = stSelect.executeQuery("SELECT id FROM cadmesdia");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
//                Log.d("safdsfas",  rs.getString(1));

                int i = 0;
                while (i <= 4) {
                    String hora= "";
                    if (i == 0) {
                        hora = "16:00";
                    }

                    if (i == 1) {
                        hora = "17:00";
                    }

                    if (i == 2) {
                        hora = "18:00";
                    }

                    if (i == 3) {
                        hora = "19:00";
                    }

                    if (i == 4) {
                        hora = "20:00";
                    }

                    stcadmesdiapessoas = BDMysql.getConexao().prepareStatement("insert into cadmesdiahora(cadmesdiaid,cadmesdiahora) values(?,?)");
                    stcadmesdiapessoas.setInt(1, rs.getInt(1));
                    stcadmesdiapessoas.setString(2, hora);
                    stcadmesdiapessoas.executeUpdate();

                    i++;
                }




            }
        } catch (java.sql.SQLException e1) {
            e1.printStackTrace();
        }

    }

}
