package com.compscitutorials.basigarcia.projetopilates.domain.Entidades;

import java.io.Serializable;

/**
 * Created by eabreu on 16/10/2015.
 */
public class Cadmesdiapessoasexclui implements Serializable {
    public static String TABELA = "CADMESDIAPESSOASEXCLUI";
    public static String ID = "_id";
     public static String DATA = "DATA";
    public static String HORA = "HORA";


    private static final long serialVersionUID = 1L;

    private Integer id;
    private String data;
    private String hora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
