package br.com.dev.appclientes.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilBkpDb;
import br.com.dev.appclientes.api.AppUtilSharedPreferences;
import br.com.dev.appclientes.api.AppUtilToast;
import br.com.dev.appclientes.controller.AppController;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.datasource.AppDataBase;
import br.com.dev.appclientes.service.SincronizarSistema;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView txtVersao;

    AppDataBase db;

    String[] permissoesRequiridas = {Manifest.permission.SEND_SMS, Manifest.permission.INTERNET,
                                     Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    SincronizarSistema sincronizarSistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponentes();

        if(AppController.verificarGooglePlayServices(SplashActivity.this)){

            if(verificaPermissoes()){
                inicializarAplicativo();
            }else{
                //TODO: Colocar mensagem com as permissões negadas
                AppUtilToast.toastMessage(getApplicationContext(),"Verifique as permissões antes de continuar");
            }

        }

    }

    private void sincronizarDados() {
        db.dropTable(ClienteDataModel.TABELA);
        db.createTabela(ClienteDataModel.criarTabela());

        db.dropTable(UsuarioDataModel.TABELA);
        db.createTabela(UsuarioDataModel.criarTabela());

        String idUserLog = String.valueOf(AppUtilSharedPreferences.getIdUserByPref(SplashActivity.this));

        if (!idUserLog.isEmpty()){
            sincronizarSistema.execute(idUserLog);
        }
        


    }

    private boolean getDadosPref() {

        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP,MODE_PRIVATE);

        return sharedPreferences.getBoolean("chklembrardados",false);
    }

    private void inicializarAplicativo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                db = new AppDataBase(getApplicationContext());

                AppUtilBkpDb.bkpDataBase();

                sincronizarDados();



                if(getDadosPref()){
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, AppUtil.TIME_SPLASH);

    }

    //TODO: Ajustar para ao permitir as permissões, o APP carregar a tela inicial
    private boolean verificaPermissoes(){

        List<String> permissoesNegadas = new ArrayList<>();

        int tipoPermissão;

        for (String permissãoRequirida : permissoesRequiridas) {

            tipoPermissão = ContextCompat.checkSelfPermission(this,permissãoRequirida);

            if(tipoPermissão != PackageManager.PERMISSION_GRANTED){
                permissoesNegadas.add(permissãoRequirida);
            }

        }

        if(!permissoesNegadas.isEmpty()){
            ActivityCompat.requestPermissions(this,permissoesNegadas.toArray(new String[permissoesNegadas.size()]),AppUtil.REQUEST_CODE_APP);
            return false;
        }else{
            return true;
        }

    }

    private void initComponentes(){
        txtVersao = findViewById(R.id.txtVersao);

        txtVersao.setText(AppUtil.VERSION);

        sincronizarSistema = new SincronizarSistema(SplashActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == AppUtil.REQUEST_CODE_APP) {
            boolean todasPermissoesConcedidas = true;

            for (int resultado : grantResults) {
                if (resultado != PackageManager.PERMISSION_GRANTED) {
                    todasPermissoesConcedidas = false;
                    break;
                }
            }

            if (todasPermissoesConcedidas) {
                inicializarAplicativo();
            } else {
                AppUtilToast.toastMessage(getApplicationContext(), "Permissões necessárias não concedidas!");
                finish();
            }
        }
    }

}