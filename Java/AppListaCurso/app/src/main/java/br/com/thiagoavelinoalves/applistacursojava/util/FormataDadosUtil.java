package br.com.thiagoavelinoalves.applistacursojava.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormataDadosUtil {

    public static boolean validarTelefone(String telefone){

        Pattern pattern = Pattern.compile("\\(\\d{2}\\)\\d{5}-\\d{4}");
        Matcher matcher = pattern.matcher(telefone);

        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }


    public static boolean validarEmail(String email){
        int indice = email.indexOf("@");

        if( indice > -1 ){
            return true;
        }else{
            return false;
        }
    }

}
