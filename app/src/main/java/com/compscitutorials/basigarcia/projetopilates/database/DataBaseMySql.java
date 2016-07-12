package com.compscitutorials.basigarcia.projetopilates.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Evandro on 08/03/2016.
 */
public class DataBaseMySql {

    public static Connection getConexao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://cursojavaee-mysql.cjoajkzab8cl.us-west-2.rds.amazonaws.com:3306/android";

            Connection c = DriverManager.getConnection(url, "algaworks", "algaworks");

            return c;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
