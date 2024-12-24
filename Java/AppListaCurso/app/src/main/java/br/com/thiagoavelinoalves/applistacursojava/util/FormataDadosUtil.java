package br.com.thiagoavelinoalves.applistacursojava.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormataDadosUtil {

    public String validarTelefone(String telefone){

        Pattern pattern = Pattern.compile("\\(\\d{2}\\)\\d{5}-\\d{4}");
        Matcher matcher = pattern.matcher(telefone);

        if(matcher.find()){
            return telefone;
        }else{
            return null;
        }
    }

}
