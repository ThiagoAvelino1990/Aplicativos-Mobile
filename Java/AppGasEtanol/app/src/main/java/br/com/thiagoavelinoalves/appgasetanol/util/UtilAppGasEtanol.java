package br.com.thiagoavelinoalves.appgasetanol.util;

public class UtilAppGasEtanol {

    public static String CalcularMelhorOpcao(double precoGasolina, double precoEtanol){
        String mensagemDeRetorno;

        if (precoEtanol <= (precoGasolina * 0.70)){
            mensagemDeRetorno = "ABASTECER COM ETANOL";
        }else{
            mensagemDeRetorno = "ABASTECER COM GASOLINA";
        }

        return mensagemDeRetorno;
    }

}
