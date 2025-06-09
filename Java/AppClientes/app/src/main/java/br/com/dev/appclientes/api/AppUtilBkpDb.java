package br.com.dev.appclientes.api;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import br.com.dev.appclientes.datasource.AppDataBase;

public class AppUtilBkpDb {

    public static void bkpDataBase(){
        File sd; // Caminho de destino
        File data; //Caminho de origem

        File arquivoDataBase;
        File arquivoDataBaseBkp;

        FileChannel fileOrig;
        FileChannel fileDest;

        try{
            sd = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);

            data = Environment.getDataDirectory();

            Log.v("DB", "SD - " + sd.getAbsolutePath());
            Log.v("DB", "DATA - " + data.getAbsolutePath());

            if (sd.canWrite()) {

                String nomeDoBancoDeDados ="//data//appclientes//databases/" + AppDataBase.DB_NAME;

                String nomeDoArquivoDeBackup ="bkp_" + AppDataBase.DB_NAME;

                arquivoDataBase = new File(data, nomeDoBancoDeDados);
                arquivoDataBaseBkp = new File(sd, nomeDoArquivoDeBackup);

                if (arquivoDataBase.exists()) {

                    fileOrig = new FileInputStream(
                            arquivoDataBase).getChannel();

                    fileDest = new FileOutputStream(
                            arquivoDataBaseBkp).getChannel();

                    fileDest.transferFrom(fileOrig, 0, fileOrig.size());

                    fileOrig.close();
                    fileDest.close();

                }

            }
        }catch(Exception err){
            Log.e(AppUtil.TAG,"[AppUtilBkpDb - bkpDataBase]Erro ao realizar backup do banco "+err.getMessage());
        }

    }
}
