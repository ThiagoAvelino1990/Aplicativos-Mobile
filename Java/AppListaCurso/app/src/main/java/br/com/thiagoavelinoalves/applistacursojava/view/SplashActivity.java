package br.com.thiagoavelinoalves.applistacursojava.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.thiagoavelinoalves.applistacursojava.R;
import br.com.thiagoavelinoalves.applistacursojava.database.ListaCursoDB;

public class SplashActivity extends AppCompatActivity {

    public static final int TIME_OUT_SPLASH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        comutarTelaSplah();

    }

    public void comutarTelaSplah(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ListaCursoDB db = new ListaCursoDB(SplashActivity.this);

                Intent telaPrincipal = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(telaPrincipal);
                finish();
            }
        },TIME_OUT_SPLASH);
    }
}