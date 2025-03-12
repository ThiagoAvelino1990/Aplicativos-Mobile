package br.com.dev.appclientes.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dev.appclientes.R;

public class LoginActivity extends AppCompatActivity {

    TextView txtLogin, txtLoginEmail, txtLoginSenha, txtVerifiqueDados;
    EditText editLoginEmail, editLoginSenha;
    CheckBox chkLembrarDados;
    Button btnLogin, btnEsqueceuSenha, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initComponentesLayout();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarDadosFormulario()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                    startActivity(intent);
            }
        });

    }

    private boolean validarDadosFormulario() {

        boolean isDadosOK = true;

        if(editLoginEmail.getText().toString().isEmpty()){
            editLoginEmail.setError("*");
            editLoginEmail.requestFocus();
            txtVerifiqueDados.setVisibility(View.VISIBLE);
            btnEsqueceuSenha.setVisibility(View.VISIBLE);
            isDadosOK = false;
        }

        if(editLoginSenha.getText().toString().isEmpty()){
            editLoginSenha.setError("*");
            editLoginSenha.requestFocus();
            txtVerifiqueDados.setVisibility(View.VISIBLE);
            btnEsqueceuSenha.setVisibility(View.VISIBLE);
            isDadosOK = false;
        }

        return isDadosOK;
    }

    private void initComponentesLayout() {

        txtLogin = findViewById(R.id.txtLogin);
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginSenha = findViewById(R.id.txtLoginSenha);
        txtVerifiqueDados = findViewById(R.id.txtVerifiqueDados);

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginSenha = findViewById(R.id.editLoginSenha);

        chkLembrarDados = findViewById(R.id.chkLembrarDados);

        btnLogin = findViewById(R.id.btnLogin);
        btnEsqueceuSenha = findViewById(R.id.btnEsqueceuSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);


    }
}