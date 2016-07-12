package com.compscitutorials.basigarcia.projetopilates.domain.Entidades;

import com.compscitutorials.basigarcia.projetopilates.domain.ServicoAula;

import java.io.Serializable;


/**
 * Created by eabreu on 06/10/2015.
 */
public class Cadmesdiahora implements Serializable {

    public static String TABELA = "CADMESDIAHORA";

    public static String ID = "_id";
    public static String CADMESDIAHORA_ID = "CADMESDIA_ID";
    public static String CADMESDIAHORA = "CADMESDIAHORA";
    public static String CADMESDIA_TYPE = "CADMESDIA_TYPE";

    private static final long serialVersionUID = 1L;

    private Integer idHora;
    private Integer cadmesdiaId;
    private String cadmesdiahora;
    private String cadmesdiaType;
    private String dataatual;

    public Integer getCadmesdiaId() {
        return cadmesdiaId;
    }

    public void setCadmesdiaId(Integer cadmesdiaId) {
        this.cadmesdiaId = cadmesdiaId;
    }

    public String getCadmesdiahora() {
        return cadmesdiahora;
    }

    public void setCadmesdiahora(String cadmesdiahora) {
        this.cadmesdiahora = cadmesdiahora;
    }

    public String getCadmesdiaType() {
        return cadmesdiaType;
    }

    public void setCadmesdiaType(String cadmesdiaType) {
        this.cadmesdiaType = cadmesdiaType;
    }

    public Integer getIdHora() {
        return idHora;
    }

    public void setIdHora(Integer idHora) {
        this.idHora = idHora;
    }


    public String getDataatual() {
        return dataatual;
    }

    public void setDataatual(String dataatual) {
        this.dataatual = dataatual;
    }



    @Override
    public String toString() {
        ServicoAula servicoAula = new ServicoAula();
        String tpAula;
        if(servicoAula.processoTpAula(getDataatual(),cadmesdiahora).equals(0))
            tpAula= "Equipamento"; else tpAula="Aula de Solo";

        return cadmesdiahora+" ( "+tpAula+" ) ";
    }
}
