package br.com.dev.appclientes.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilSharedPreferences;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.model.Usuario;

public class RecuperarSenhaActivity extends AppCompatActivity {

    EditText editRecuperarSenha;
    Button btnRecuperarSenha, btnVoltar;

    Usuario usuario;
    UsuarioController usuarioController;

    SmsManager smsManager;

    String[] permissoesApp = {Manifest.permission.SEND_SMS};

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_senha);

        initComponentesLayout();

        btnVoltarMain(btnVoltar);

        btnRecuperarSenha(btnRecuperarSenha);


    }

    private boolean requestPermissoes() {
        List<String> permissoesNegadas = new ArrayList<>();

        int verificarPermissoes;

        for (String permissoes : this.permissoesApp) {

            verificarPermissoes = ContextCompat.checkSelfPermission(RecuperarSenhaActivity.this, permissoes);

            if (verificarPermissoes != PackageManager.PERMISSION_GRANTED) {
                permissoesNegadas.add(permissoes);
            }
        }

        if (!permissoesNegadas.isEmpty()) {
            ActivityCompat.requestPermissions(RecuperarSenhaActivity.this, permissoesNegadas.toArray(new String[permissoesNegadas.size()]), 2025);
            return true;
        } else {
            return false;
        }

    }

    private boolean enviarSenha() {

        try {
            smsManager.sendTextMessage(editRecuperarSenha.getText().toString(), null, "Nova senha: ",
                    null, null);
            if (requestPermissoes()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception err) {
            return false;
        }
    }

    private boolean validaTelefone() {
        boolean existeCadastro = false;


        if (usuarioController.readObjetByTelefone(editRecuperarSenha.getText().toString()) > -1) {
            existeCadastro = true;
        }

        return existeCadastro;

    }

    private void initComponentesLayout() {

        editRecuperarSenha = findViewById(R.id.editRecuperarSenha);
        btnRecuperarSenha = findViewById(R.id.btnRecuperarSenha);
        btnVoltar = findViewById(R.id.btnVoltar);


        usuario = new Usuario();
        usuarioController = new UsuarioController(getApplicationContext());
    }

    public void btnVoltarMain(View view) {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(RecuperarSenhaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void btnRecuperarSenha(View view) {
        btnRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                String novaSenha = "";

                if (validaEmailInformado(editRecuperarSenha.getText().toString())) {

                    novaSenha = AppUtil.radomPass();

                    if (atualizarSenha(novaSenha)) {
                        intent = new Intent(Intent.ACTION_SEND);

                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{editRecuperarSenha.getText().toString()}); // Destinatário

                        intent.putExtra(Intent.EXTRA_SUBJECT, "Recuperação de senha - AppClientes"); // Assunto

                        intent.putExtra(Intent.EXTRA_TEXT, "Nova senha gerada: " + novaSenha); //Corpo do e-mail

                        intent.setType("message/rfc822"); //Estilo de mensagem

                        startActivity(Intent.createChooser(intent, "Seleciona aplicativo para enviar o e-mail"));
                        finish();
                    } else {
                        Toast.makeText(RecuperarSenhaActivity.this, "Erro ao atualizar senha. \nPor favor tente mais tarde....", Toast.LENGTH_LONG).show();
                    }


                } else {
                    editRecuperarSenha.setError("*");
                    editRecuperarSenha.requestFocus();
                    Toast.makeText(RecuperarSenhaActivity.this, "Favor informar um email válido", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validaEmailInformado(String emailInformado) {
        preferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dadosPreferences = preferences.edit();


        int userID = usuarioController.readObjetcIdByEmail(emailInformado);

        if (userID > -1) {

            usuario.setId(userID);
            dadosPreferences.putString("idUsuario", String.valueOf(userID));
            dadosPreferences.apply();


            return true;
        }

        return false;
    }

    private boolean atualizarSenha(String novaSenha) {

        usuario.setSenha(AppUtil.criptografarPass(novaSenha));
        usuario.setAtualizaSenha("S");

        try {
            usuarioController.updateObject(usuario);
            return true;
        } catch (Exception err) {
            Log.e(AppUtil.TAG, "Erro ao atualizar o usuário. " + err.getMessage());
            return false;
        }

    }
}