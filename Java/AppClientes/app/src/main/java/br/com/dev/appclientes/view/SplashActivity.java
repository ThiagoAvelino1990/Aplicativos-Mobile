package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.datasource.AppDataBase;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    TextView txtVersao;

    AppDataBase db;


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

        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP,MODE_PRIVATE);

        return sharedPreferences.getBoolean("chklembrardados",false);
    }

    private void inicializarAplicativo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                db = new AppDataBase(getApplicationContext());

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
}