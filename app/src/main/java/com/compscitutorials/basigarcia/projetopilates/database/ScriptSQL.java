package com.compscitutorials.basigarcia.projetopilates.database;

/**
 * Created by Evandro on 22/09/2015.
 */
public class ScriptSQL {

    public static String getCreateCadmes(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS CADMES ( ");
        stringBuilder.append("_id                INTEGER       NOT NULL ");
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("CAD_NAME           VARCHAR (100), ");
        stringBuilder.append("CAD_MES_ANO         VARCHAR (20) ");
        stringBuilder.append("); ");

        return  stringBuilder.toString();
    }

    public static String getCreateCadmesDia(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS CADMESDIA ( ");
        stringBuilder.append("_id                INTEGER       NOT NULL ");
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("CADMES_ID INTEGER NOT NULL, ");
        stringBuilder.append(" CADMESDIA_TYPE VARCHAR(100), ");
        stringBuilder.append(" CADMESDIA_DATE DATE DEFAULT NULL, ");
        stringBuilder.append("CAD_MES_ANO         VARCHAR (20), ");
        stringBuilder.append(" STATUS VARCHAR(1) NOT NULL ");

        stringBuilder.append("); ");

        return  stringBuilder.toString();
    }

    public static String getCreateCadmesDiaHora(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS CADMESDIAHORA ( ");
        stringBuilder.append("_id                INTEGER       NOT NULL ");
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("CADMESDIA_ID INTEGER NOT NULL, ");
        stringBuilder.append(" CADMESDIA_TYPE VARCHAR(100), ");
        stringBuilder.append(" CADMESDIAHORA VARCHAR(100) ");

        stringBuilder.append("); ");

        return  stringBuilder.toString();
    }


    public static String getCreateCadmesDiaPessoas(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS CADMESDIAPESSOAS ( ");
        stringBuilder.append("_id                INTEGER       NOT NULL ");
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("CADMESDIA_ID INTEGER NOT NULL, ");
        stringBuilder.append(" NOME VARCHAR(100) NOT NULL, ");
        stringBuilder.append(" IDHORA INTEGER NOT NULL, ");
        stringBuilder.append(" STATUS VARCHAR(1) NOT NULL ");

        stringBuilder.append("); ");

        return  stringBuilder.toString();
    }


    public static String getCreateCadmesDiaPessoasExclui(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE IF NOT EXISTS CADMESDIAPESSOASEXCLUI ( ");
        stringBuilder.append("_id                INTEGER       NOT NULL ");
        stringBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        stringBuilder.append("DATA VARCHAR(100), ");
        stringBuilder.append("HORA VARCHAR (20) ");
        stringBuilder.append("); ");

        return  stringBuilder.toString();
    }

}
