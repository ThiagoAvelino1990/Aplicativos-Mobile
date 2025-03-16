package br.com.dev.appclientes.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.dev.appclientes.R;

public class RecuperarSenhaActivity extends AppCompatActivity {

    EditText editRecuperarSenha;
    Button btnRecuperarSenha, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_senha);

        initComponentesLayout();

        btnRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(RecuperarSenhaActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initComponentesLayout() {

        editRecuperarSenha = findViewById(R.id.editRecuperarSenha);
        btnRecuperarSenha = findViewById(R.id.btnRecuperarSenha);
        btnVoltar = findViewById(R.id.btnVoltar);
    }
}