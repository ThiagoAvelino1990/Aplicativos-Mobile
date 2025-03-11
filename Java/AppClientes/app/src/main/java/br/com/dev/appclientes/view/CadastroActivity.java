package br.com.dev.appclientes.view;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dev.appclientes.R;

public class CadastroActivity extends AppCompatActivity {

    ImageView imgAppClienteCadastro;
    TextView txtViewCadastro, txtCadastroNovoUsuario;
    EditText editNomeCompleto, editCadastroEmail, editSenha, editConfirmarSenha;
    Button btnConfirmarCadastro, btnVoltarCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initComponentesLayout();

        btnVoltarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initComponentesLayout() {

        imgAppClienteCadastro = findViewById(R.id.imgAppClienteCadastro);

        txtViewCadastro = findViewById(R.id.txtViewCadastro);
        txtCadastroNovoUsuario = findViewById(R.id.txtCadastroNovoUsuario);

        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);

        btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnVoltarCadastro = findViewById(R.id.btnVoltarCadastro);

    }
}