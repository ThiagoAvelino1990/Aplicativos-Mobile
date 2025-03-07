package br.com.dev.applistadecompras.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {

    public static final String TAG = "APP_LISTA_DE_COMPRAS";

    public static String getDataAtual(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();

        return dateFormat.format(data);
    }

}
