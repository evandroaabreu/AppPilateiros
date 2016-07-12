package com.compscitutorials.basigarcia.projetopilates.database;

/**
 * Created by Evandro on 22/09/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context) {
        super(context,"CADAULA",null,7);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateCadmes());
        db.execSQL(ScriptSQL.getCreateCadmesDia());
        db.execSQL(ScriptSQL.getCreateCadmesDiaHora());
        db.execSQL(ScriptSQL.getCreateCadmesDiaPessoas());
        db.execSQL(ScriptSQL.getCreateCadmesDiaPessoasExclui());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
