package br.com.dev.appclientes.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import br.com.dev.appclientes.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_senha);

        initComponentesLayout();

        btnRecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if(validaTelefone()){
                    if(enviarSenha()){
                        Toast.makeText(RecuperarSenhaActivity.this,"Dados enviados para o telefone informado",Toast.LENGTH_LONG).show();

                        intent = new Intent(RecuperarSenhaActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(RecuperarSenhaActivity.this,"[ERRO] - Falha ao enviar a senha",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(RecuperarSenhaActivity.this,"Verifique se o telefone informado está correto e tente novamente",Toast.LENGTH_LONG).show();
                }

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

    private boolean requestPermissoes() {
        List<String> permissoesNegadas = new ArrayList<>();

        int verificarPermissoes;

        for(String permissoes : this.permissoesApp){

            verificarPermissoes = ContextCompat.checkSelfPermission(RecuperarSenhaActivity.this,permissoes);

            if(verificarPermissoes != PackageManager.PERMISSION_GRANTED){
                permissoesNegadas.add(permissoes);
            }
        }

        if(!permissoesNegadas.isEmpty()){
            ActivityCompat.requestPermissions(RecuperarSenhaActivity.this,permissoesNegadas.toArray(new String[permissoesNegadas.size()]), 2025);
            return true;
        }else{
            return false;
        }

    }

    private boolean enviarSenha() {

        try{
            smsManager.sendTextMessage(editRecuperarSenha.getText().toString(), null, "Nova senha: ",
                    null, null);
            if(requestPermissoes()){
                return true;
            }else{
                return false;
            }
        }catch(Exception err){
            return false;
        }
    }

    private boolean validaTelefone() {
        boolean existeCadastro = false;


        if(usuarioController.readObjetByTelefone(UsuarioDataModel.TABELA, editRecuperarSenha.getText().toString()) > -1 ){
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
}