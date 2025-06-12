package br.com.dev.appclientes.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
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
import br.com.dev.appclientes.datasource.AppDataBase;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView txtVersao;

    AppDataBase db;

    String[] permissoesRequiridas = {Manifest.permission.SEND_SMS, Manifest.permission.INTERNET,
                                     Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

        txtVersao = findViewById(R.id.txtVersao);

        txtVersao.setText(AppUtil.VERSION);

        inicializarAplicativo();

        getDadosPref();
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

                if(verificaPermissoes()){
                    if(getDadosPref()){
                        intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        intent = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    //TODO: Colocar mensagem com as permissões negadas
                    Toast.makeText(getApplicationContext(),"Verifique as permissões antes de continuar", Toast.LENGTH_SHORT).show();
                }

            }
        }, AppUtil.TIME_SPLASH);

    }

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
}