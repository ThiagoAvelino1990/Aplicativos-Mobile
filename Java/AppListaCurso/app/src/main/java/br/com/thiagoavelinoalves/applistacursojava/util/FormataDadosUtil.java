package br.com.thiagoavelinoalves.applistacursojava.util;

// TODO: Finalizar classe

public class FormataDadosUtil {

    public String formatarTelefone(String telefone){

        String padraoTelefone = "\\+?\\d{0,2}\\s?\\(?\\d{2}\\)?\\s?9?\\d{4}-\\d{4}";
        String telefoneFormatado;

        if(padraoTelefone.matches(telefone)){
            return telefone;
        }else{
            return null;
        }
    }
}
