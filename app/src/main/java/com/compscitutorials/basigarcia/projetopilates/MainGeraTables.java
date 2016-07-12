package com.compscitutorials.basigarcia.projetopilates;

import android.net.ParseException;
import android.support.v4.app.Fragment;
import android.view.View;

import com.compscitutorials.basigarcia.projetopilates.database.CreateTables;

/**
 * Created by Evandro on 01/06/2016.
 */
public class MainGeraTables extends Fragment implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        try {
            CreateTables createTables = new CreateTables();
            createTables.createIP();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
