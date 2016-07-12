package com.compscitutorials.basigarcia.projetopilates.database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Evandro on 08/03/2016.
 */
public class BDMysql {

    public static Connection getConexao() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)policy);
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://192.168.56.1:5432/postgres";
            Connection c = DriverManager.getConnection(url, "postgres", "xavier");
            return c;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
