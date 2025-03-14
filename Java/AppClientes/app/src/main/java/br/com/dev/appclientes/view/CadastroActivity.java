package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;

public class CadastroActivity extends AppCompatActivity {

    ImageView imgAppClienteCadastro;
    TextView txtViewCadastro, txtCadastroNovoUsuario;
    EditText editNomeCompleto, editCadastroEmail, editSenha, editConfirmarSenha;
    Button btnConfirmarCadastro, btnVoltarCadastro;

    SharedPreferences preferencesCadastro;

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

        btnConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if(validarDados()){
                    setDataPreferences();
                    intent = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean validarDados() {

        boolean isDadosOK = true;

        if(editNomeCompleto.getText().toString().isEmpty() || editCadastroEmail.getText().toString().isEmpty()){
            isDadosOK = false;
            editNomeCompleto.requestFocus();
            editNomeCompleto.setError("*");
            editCadastroEmail.requestFocus();
            editCadastroEmail.setError("*");

        }

        if((editSenha.getText().toString().isEmpty()) || (!editSenha.getText().toString().equals(editConfirmarSenha.getText().toString()))){
            editSenha.requestFocus();
            editSenha.setError("*");
            editConfirmarSenha.requestFocus();
            editConfirmarSenha.setError("*");
            isDadosOK = false;
        }

        if(!isDadosOK){
            Toast.makeText(CadastroActivity.this,"* Favor verificar os dados",Toast.LENGTH_LONG).show();
        }

    return isDadosOK;

    }

    private void setDataPreferences() {

        preferencesCadastro = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor data = preferencesCadastro.edit();

        data.putString("nome",editNomeCompleto.getText().toString());
        data.putString("email",editCadastroEmail.getText().toString());
        data.putString("senha",editSenha.getText().toString());

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