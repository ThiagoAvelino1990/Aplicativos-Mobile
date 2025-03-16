package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.model.Usuario;

public class RecuperarSenhaActivity extends AppCompatActivity {

    EditText editRecuperarSenha;
    Button btnRecuperarSenha, btnVoltar;
    Usuario usuario;
    UsuarioController usuarioController;


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

                if(validaEmail()){

                    Toast.makeText(RecuperarSenhaActivity.this,"Dados enviados para o e-mail informado",Toast.LENGTH_LONG).show();

                    intent = new Intent(RecuperarSenhaActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RecuperarSenhaActivity.this,"Verifique se o e-mail digitado está correto e tente novamente",Toast.LENGTH_LONG).show();
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

    private boolean validaEmail() {
        boolean existeCadastro = false;
        usuario = new Usuario();
        usuarioController = new UsuarioController(getApplicationContext());

        if(!usuarioController.readObjectByEmail(UsuarioDataModel.TABELA, editRecuperarSenha.getText().toString()).isEmpty()){
            existeCadastro = true;
        }

        return existeCadastro;

    }

    private void initComponentesLayout() {

        editRecuperarSenha = findViewById(R.id.editRecuperarSenha);
        btnRecuperarSenha = findViewById(R.id.btnRecuperarSenha);
        btnVoltar = findViewById(R.id.btnVoltar);
    }
}