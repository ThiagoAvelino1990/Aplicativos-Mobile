package br.com.dev.appclientes.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {

    public static final String TAG = "APP_CLIENTES";
    public static final int TIME_SPLASH = 4 * 1000;
    public static final int TIME_CADASTRO = 1 * 1000;
    public static final String VERSION = "v1.0.0";
    public static final String PREF_APP = "app_clientes_pref";

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
