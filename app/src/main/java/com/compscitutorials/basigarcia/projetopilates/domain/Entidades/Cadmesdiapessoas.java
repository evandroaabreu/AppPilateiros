package com.compscitutorials.basigarcia.projetopilates.domain.Entidades;

import java.io.Serializable;

/**
 * Created by eabreu on 16/10/2015.
 */
public class Cadmesdiapessoas implements Serializable {
    public static String TABELA = "CADMESDIAPESSOAS";
    public static String ID = "_id";
    public static String CADMESDIA_ID = "CADMESDIA_ID";
    public static String NOME = "NOME";
    public static String STATUS = "STATUS";


    private static final long serialVersionUID = 1L;

    private Integer cadmesdiaPessoasId;
    private String cadmesdiaId;
    private String nome;
    private String cadmesdiadate;
    private Integer idHora;
    private String status;
    private int imagem;


    public Integer getCadmesdiaPessoasId() {
        return cadmesdiaPessoasId;
    }

    public void setCadmesdiaPessoasId(Integer cadmesdiaPessoasId) {
        this.cadmesdiaPessoasId = cadmesdiaPessoasId;
    }

    public String getCadmesdiaId() {
        return cadmesdiaId;
    }

    public void setCadmesdiaId(String cadmesdiaId) {
        this.cadmesdiaId = cadmesdiaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nome;
    }


    public String getCadmesdiadate() {
        return cadmesdiadate;
    }

    public void setCadmesdiadate(String cadmesdiadate) {
        this.cadmesdiadate = cadmesdiadate;
    }

    public Integer getIdHora() {
        return idHora;
    }

    public void setIdHora(Integer idHora) {
        this.idHora = idHora;
    }


    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
