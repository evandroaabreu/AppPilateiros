package com.compscitutorials.basigarcia.projetopilates;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compscitutorials.basigarcia.navigationdrawervideotutorial.R;
import com.compscitutorials.basigarcia.projetopilates.database.BDMysql;
import com.compscitutorials.basigarcia.projetopilates.database.DataBase;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiahora;
import com.compscitutorials.basigarcia.projetopilates.domain.Entidades.Cadmesdiapessoas;
import com.compscitutorials.basigarcia.projetopilates.domain.RepositorioContato;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by Evandro on 27/05/2016.
 */
public class MainSinc extends Fragment {

    private DataBase dataBase;
    private SQLiteDatabase conn;
    RepositorioContato repositorioContato = null;
    private ProgressDialog dialog;
    private final String TAG = "Http";
    public final int TIMEOUT_MILLIS = 15000;
    public boolean LOG_ON = false;
    private String contentType;
    private String charsetToEncode;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sinc, container, false);
        FragmentActivity ctx = this.getActivity();
        this.dataBase = new DataBase((Context)ctx);
        this.conn = this.dataBase.getWritableDatabase();
        this.repositorioContato = new RepositorioContato(this.conn);
        this.dialog = new ProgressDialog((Context)this.getActivity());
        ///rootView.findViewById(2131492996).setOnClickListener((View.OnClickListener)new /* Unavailable Anonymous Inner Class!! */);

        rootView.findViewById(R.id.btnInserir).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    repositorioContato.testeInsertContatos();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        rootView.findViewById(R.id.btnSincronizar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.setMessage("Loading..");
                dialog.setTitle("Sincronizando os dados.");
                dialog.setIndeterminate(false);
                dialog.setCancelable(true);
                dialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sincDados();
                        } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });


        return rootView;
    }

    public void sincDados() throws java.sql.SQLException {
        try {
            this.doDelete("http://192.168.1.38:8087/WebServiceAndroid/web");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Object stcadmesdiapessoas = null;
        String q = "SELECT _id,CADMES_ID,CADMESDIA_DATE,CADMESDIA_TYPE,CAD_MES_ANO FROM CADMESDIA WHERE STATUS = ?";
        Cursor cCadmesDia = this.conn.rawQuery(q, new String[]{"P"});
        if (cCadmesDia.moveToFirst()) {
            do {
                Integer vidCadMesDia;
                String qPessoas;
                Cursor cCadmesDiaPessoas;
                if (!(cCadmesDiaPessoas = this.conn.rawQuery(qPessoas = "SELECT P._id,P.CADMESDIA_ID,P.NOME, H.CADMESDIAHORA, H.CADMESDIA_TYPE FROM CADMESDIAPESSOAS P, CADMESDIAHORA H WHERE  P.STATUS = ? AND P.CADMESDIA_ID = ? AND H._id= P.IDHORA", new String[]{"P", String.valueOf(vidCadMesDia = Integer.valueOf(cCadmesDia.getString(0)))})).moveToFirst()) continue;
                do {
                    String cadMesDiaId = "";
                    Integer idHora = 0;
                    try {
                        Statement stSelect = null;
                        stSelect = BDMysql.getConexao().createStatement();
                        ResultSet rs = stSelect.executeQuery("SELECT c.id,h.id, h.cadmesdiatype FROM Cadmesdia c, cadmesdiahora h where c.cadmesdiaDate = '" + cCadmesDia.getString(2) + "' and h.cadmesdiahora='" + cCadmesDiaPessoas.getString(3) + "' and c.id=h.cadmesdiaid");
                        ResultSetMetaData rsmd = rs.getMetaData();
                        while (rs.next()) {
                            cadMesDiaId = rs.getString(1);
                            idHora = rs.getInt(2);
                            Log.d((String) "idhora", (String) String.valueOf(rs.getInt(2)));
                        }
                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    Cadmesdiapessoas pessoas = new Cadmesdiapessoas();
                    try {
                        Cadmesdiahora cadmesdiahora = new Cadmesdiahora();
                        cadmesdiahora.setCadmesdiaId(Integer.valueOf(cadMesDiaId));
                        cadmesdiahora.setCadmesdiaType(cCadmesDiaPessoas.getString(4));
                        cadmesdiahora.setCadmesdiahora(cCadmesDiaPessoas.getString(3));
                        cadmesdiahora.setIdHora(idHora);
                        this.gravarWebServiceHoraPost(cadmesdiahora);
                        Cadmesdiapessoas cadmesdiapessoas = new Cadmesdiapessoas();
                        cadmesdiapessoas.setCadmesdiaId(cadMesDiaId);
                        cadmesdiapessoas.setIdHora(idHora);
                        cadmesdiapessoas.setNome(cCadmesDiaPessoas.getString(2));
                        cadmesdiapessoas.setCadmesdiadate(cCadmesDia.getString(2));
                        this.gravarWebServicePost(cadmesdiapessoas);
                        String find = "SELECT _id,CADMESDIA_ID,NOME FROM CADMESDIAPESSOAS WHERE _id = " + cCadmesDiaPessoas.getString(0) + "";
                        Cursor cPessoas = this.conn.rawQuery(find, null);
                        if (cPessoas.moveToFirst()) {
                            do {
                                pessoas.setCadmesdiaPessoasId(Integer.valueOf(cPessoas.getInt(0)));
                                pessoas.setCadmesdiaId(cPessoas.getString(1));
                                pessoas.setNome(cPessoas.getString(2));
                                pessoas.setStatus("C");
                            } while (cPessoas.moveToNext());
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.repositorioContato.buscarPorPessoas(pessoas);
                } while (cCadmesDiaPessoas.moveToNext());
                this.repositorioContato.alterarCadmesdia(vidCadMesDia, cCadmesDia.getString(3), "C");
            } while (cCadmesDia.moveToNext());
        }
        this.dialog.dismiss();
    }

    public void readWebServiceGet() {
        HttpGet request = new HttpGet("http://192.168.0.101:8087/WebServiceAndroid/web");
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            CloseableHttpResponse response = httpclient.execute((HttpUriRequest)request);
            Log.d((String)"asfasasdf", (String)"afadd");
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                Log.d((String)"asfasasdf", (String)line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gravarWebServiceHoraPost(Cadmesdiahora cadmesdiahora) {
        try {
            String line;
            JSONObject obj = new JSONObject();
            obj.put("cadmesdiaid", (Object)cadmesdiahora.getCadmesdiaId());
            obj.put("cadmesdiahora", (Object)cadmesdiahora.getCadmesdiahora());
            obj.put("cadmesdiatype", (Object)cadmesdiahora.getCadmesdiaType());
            obj.put("idHora", (Object)cadmesdiahora.getIdHora());
            HttpURLConnection connection = null;
            URL url = new URL("http://192.168.1.38:8087/WebServiceAndroid/web/salvaHora");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(obj.toString().getBytes("UTF-8"));
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void gravarWebServicePost(Cadmesdiapessoas cadmesdiapessoas) {
        try {
            String line;
            JSONObject obj = new JSONObject();
            obj.put("cadmesdiaId", (Object)cadmesdiapessoas.getCadmesdiaId());
            obj.put("nome", (Object)cadmesdiapessoas.getNome());
            obj.put("cadmesdiadate", (Object)cadmesdiapessoas.getCadmesdiadate());
            obj.put("idhora", (Object)cadmesdiapessoas.getIdHora());
            obj.put("status", (Object)"C");
            HttpURLConnection connection = null;
            URL url = new URL("http://192.168.1.38:8087/WebServiceAndroid/web");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(obj.toString().getBytes("UTF-8"));
            wr.flush();
            wr.close();
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void doDelete(String url) throws IOException, java.sql.SQLException {
        String q = "SELECT _id, DATA,HORA,NOME FROM CADMESDIAPESSOASEXCLUI";
        Cursor cPessoas = this.conn.rawQuery(q, null);
        if (cPessoas.moveToFirst()) {
            do {
                Statement stSelect = null;
                try {
                    stSelect = BDMysql.getConexao().createStatement();
                    ResultSet rs = stSelect.executeQuery("SELECT c.id FROM cadmesdiapessoas c, cadmesdiahora h where c.cadmesdiadate = '" + cPessoas.getString(1).trim() + "' and h.cadmesdiahora='" + cPessoas.getString(2) + "' and c.idhora=h.id and c.nome='" + cPessoas.getString(3) + "'");
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()) {
                        this.doDelete(url, rs.getInt(1), "UTF-8");
                        try {
                            this.repositorioContato.AlunosExcluidos((long)cPessoas.getInt(0));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            } while (cPessoas.moveToNext());
        }
    }

    public void doDelete(String url, Integer id, String charset) throws IOException {
        url = url + "/" + id;
        URL u = new URL(url);
        HttpURLConnection conn = null;
        Object s = null;
        try {
            conn = (HttpURLConnection)u.openConnection();
            if (this.contentType != null) {
                conn.setRequestProperty("Content-Type", this.contentType);
            }
            conn.setRequestMethod("DELETE");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.connect();
            InputStream in = conn.getInputStream();
            in.close();
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
