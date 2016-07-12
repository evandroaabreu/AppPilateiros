package com.compscitutorials.basigarcia.projetopilates.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.msebera.android.httpclient.ParseException;

/**
 * Created by Evandro on 14/04/2016.
 */
public class ServicoAula {

    public Integer processoTpAula(String Data, String hora){
        return tpAula(diaDaSemana(Data),hora);
    }

    public Integer tpAula(Integer diaSemana, String hora){
        //2 - segunda
        //3 - terça
        //4 - quarta
        //5- quinta
        //6- sexta

        Integer comboTpAula=-1;

        if (diaSemana.equals(2)) {
            comboTpAula = 0;
        }

        if (diaSemana.equals(3)){
            if ((hora.equals("16:00"))
                    ||(hora.equals("17:00"))
                    || (hora.equals("18:00"))
                    || (hora.equals("19:00")))  {
                comboTpAula = 0;
            } else {comboTpAula = 1;}
        }

        if (diaSemana.equals(4)){
            if ((hora.equals("19:00"))
                    || (hora.equals("20:00")))  {
                comboTpAula = 0;
            } else {comboTpAula = 1;}
        }

        if (diaSemana.equals(5)){
            if ((hora.equals("16:00"))
                    || (hora.equals("18:00"))
                    || (hora.equals("19:00")))  {
                comboTpAula = 0;
            } else {comboTpAula = 1;}
        }

        if (diaSemana.equals(6)){
            if ((hora.equals("18:00"))
                    || (hora.equals("19:00")))  {
                comboTpAula = 0;
            } else {comboTpAula = 1;}
        }


        return comboTpAula;
    }

    public Integer diaDaSemana(String Data) {
        int day = 0;
        try {
            String data = Data;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(data));
            day = cal.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return day;
    }

    public String pesquisarDiaSemana(int _diaSemana)
    {
        String diaSemana = null;

        switch (_diaSemana)
        {

            case 2:
            {
                diaSemana = "Segunda-Feira";
                break;
            }
            case 3:
            {
                diaSemana = "Terça-Feira";
                break;
            }
            case 4:
            {
                diaSemana = "Quarta-Feira";
                break;
            }
            case 5:
            {
                diaSemana = "Quinta-Feira";
                break;
            }
            case 6:
            {
                diaSemana = "Sexta-Feira";
                break;
            }

        }
        return diaSemana;

    }

}
