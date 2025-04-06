package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;

public class LoginActivity extends AppCompatActivity {

    TextView txtLogin, txtLoginEmail, txtLoginSenha, txtVerifiqueDados;
    EditText editLoginEmail, editLoginSenha;
    CheckBox chkLembrarDados;
    Button btnLogin, btnEsqueceuSenha, btnCadastrar;

    SharedPreferences sharedPreferences;
    UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initComponentesLayout();

        btnEsqueceuSenha.setVisibility(View.VISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if(validarDadosFormulario()) {

                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                    startActivity(intent);
                finish();
            }
        });

        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void getDataSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        editLoginEmail.setText(sharedPreferences.getString("email",""));
        editLoginSenha.setText(sharedPreferences.getString("senha",""));
        chkLembrarDados.setChecked(sharedPreferences.getBoolean("chklembrardados",false));

    }

    private void saveDataSharedPreferences() {

        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dadosSalvos = sharedPreferences.edit();

        int idUser = usuarioController.readObjetcIdByEmail(UsuarioDataModel.TABELA,sharedPreferences.getString("email",""));


        dadosSalvos.putBoolean("chklembrardados",chkLembrarDados.isChecked());
        dadosSalvos.putString("idUsuario",String.valueOf(idUser));



        dadosSalvos.apply();
    }

    private boolean validarDadosFormulario() {

        sharedPreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        String senhaCriptDigitada = criptografarSenhaDigitada(editLoginSenha.getText().toString());

        boolean isDadosOK = true;

        if(editLoginEmail.getText().toString().isEmpty() || !editLoginEmail.getText().toString().equals(sharedPreferences.getString("email",""))){
            editLoginEmail.setError("*");
            editLoginEmail.requestFocus();
            txtVerifiqueDados.setVisibility(View.VISIBLE);
            btnEsqueceuSenha.setVisibility(View.VISIBLE);
            isDadosOK = false;
        }else if (editLoginSenha.getText().toString().isEmpty() || !senhaCriptDigitada.equals(sharedPreferences.getString("senha",""))){
            editLoginSenha.setError("*");
            editLoginSenha.requestFocus();
            txtVerifiqueDados.setVisibility(View.VISIBLE);
            btnEsqueceuSenha.setVisibility(View.VISIBLE);
            isDadosOK = false;
        }else{
            saveDataSharedPreferences();
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


        usuarioController = new UsuarioController(LoginActivity.this);

    }

    private String criptografarSenhaDigitada(String senhaDigitada){

        return AppUtil.criptografarPass(senhaDigitada);
    }

}