package br.com.dev.appclientes.api;

import android.content.Context;
import android.widget.Toast;

public class AppUtilToast {

    public static void toastMessage(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

}
