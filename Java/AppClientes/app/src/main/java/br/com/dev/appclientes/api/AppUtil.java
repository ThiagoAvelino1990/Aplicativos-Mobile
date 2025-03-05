package br.com.dev.appclientes.api;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

    public static final String TAG = "APP_CLIENTES";

    public static String getDataAtual(){

        Calendar.getInstance();

        String dia, mes, ano, hora, minuto, segundo,dataAtual;



        try{

            dia = (Calendar.DAY_OF_MONTH < 10) ? "0"+Calendar.DAY_OF_MONTH : ""+Calendar.DAY_OF_MONTH;
            mes = ((Calendar.MONTH)+1 < 10) ? "0"+(Calendar.MONTH)+1 : ""+(Calendar.MONTH)+1;
            ano = ""+(Calendar.YEAR);
            hora = (Calendar.HOUR_OF_DAY <= 9) ? "0"+Calendar.HOUR_OF_DAY : ""+Calendar.HOUR_OF_DAY;
            minuto = (Calendar.MINUTE <= 9) ? "0"+Calendar.MINUTE : ""+Calendar.MINUTE;
            segundo = (Calendar.SECOND <= 9) ? "0"+Calendar.SECOND : ""+Calendar.SECOND;

            dataAtual = dia+"/"+mes+"/"+ano+" "+hora+":"+minuto+":"+segundo;

        }catch(Exception err){
            Log.e(TAG,"Class: AppUtil [getDataAtual] "+err.getMessage());
            dataAtual = "00/00/0000 00:00:00";
        }

        return dataAtual;
    }

    /**
     *
     * @return Retorna a data no formato "dd/MM/yyyy 00:00:00"
     */
    public static String getDataFormat(){

        String dataAtualFormat;

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        dataAtualFormat = dateFormat.format(date);

        return dataAtualFormat;

    }






}
