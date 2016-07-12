package com.compscitutorials.basigarcia.projetopilates.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Evandro on 18/04/2016.
 */
public class CreateTables {

    SQLiteDatabase sampleDB = SQLiteDatabase.openDatabase((String)"/data/data/com.compscitutorials.basigarcia.projetopilates/databases/CADAULA", (SQLiteDatabase.CursorFactory)null, (int)0);

    public void createIP() {
        String sqlDrop = "DROP TABLE CADIPWEBSERVICE";
        try {
            this.sampleDB.execSQL(sqlDrop);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "CREATE TABLE IF NOT EXISTS CADIPWEBSERVICE ( _id                INTEGER       NOT NULL PRIMARY KEY AUTOINCREMENT, IP VARCHAR (20) ); ";
        try {
            this.sampleDB.execSQL(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
