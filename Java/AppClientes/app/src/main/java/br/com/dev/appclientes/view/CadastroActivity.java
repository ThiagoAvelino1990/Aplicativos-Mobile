package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;

import org.w3c.dom.Text;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    ImageView imgAppClienteCadastro;
    TextView txtViewCadastro, txtCadastroNovoUsuario;
    EditText editNomeCompleto, editCadastroEmail, editSenha, editConfirmarSenha;
    Button btnConfirmarCadastro, btnVoltarCadastro, btnCancelar;

    SharedPreferences preferencesCadastro;

    Usuario usuario;
    UsuarioController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initComponentesLayout();

        usuario = new Usuario();
        controller = new UsuarioController(getApplicationContext());



        btnVoltarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder
                        .with(CadastroActivity.this)
                        .setTitle("Termos de uso")
                        .setBackgroundColorRes(R.color.splash_bgr)
                        .setMessage(AppUtil.TERMOS_DE_USO)
                        .setNegativeBtnText("Não")
                        .setPositiveBtnBackgroundRes(R.color.splash_bgr)
                        .setPositiveBtnText("Sim")
                        .setNegativeBtnBackgroundRes(R.color.splash_bgr)
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                        .onPositiveClicked(dialog -> {
                            if(validarDados()){
                            setDataPreferences();

                            controller.insertObject(usuario);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CadastroActivity.this,"Usuário cadastrado com sucesso !",Toast.LENGTH_LONG).show();
                                    Intent intent;
                                    intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },AppUtil.TIME_CADASTRO);
                        }})
                        .onNegativeClicked(dialog -> {
                            Toast.makeText(CadastroActivity.this, "Desculpa, o cadastro só pode ser concluído aceitando os termos", Toast.LENGTH_SHORT).show();
                            closeContextMenu();})
                        .build()
                        .show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder
                        .with(CadastroActivity.this)
                        .setTitle("Cancelar Operação")
                        .setBackgroundColorRes(R.color.splash_bgr)
                        .setMessage("Deseja cancelar a operação ?")
                        .setNegativeBtnText("Não")
                        .setPositiveBtnBackgroundRes(R.color.splash_bgr)
                        .setPositiveBtnText("Sim")
                        .setNegativeBtnBackgroundRes(R.color.splash_bgr)
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                        .onPositiveClicked(dialog -> {Toast.makeText(CadastroActivity.this, "Operação Cancelada", Toast.LENGTH_SHORT).show();
                            intent = new Intent(CadastroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();})
                        .onNegativeClicked(dialog -> closeContextMenu())
                        .build()
                        .show();
            }
        });


    }

    private boolean validarDados() {

        boolean isDadosOK = true;

        if(editNomeCompleto.getText().toString().isEmpty()){
            isDadosOK = false;
            editNomeCompleto.requestFocus();
            editNomeCompleto.setError("*");
            Toast.makeText(CadastroActivity.this,"* Nome não pode ser vazio",Toast.LENGTH_LONG).show();
        }else if(editCadastroEmail.getText().toString().isEmpty()){
            isDadosOK = false;
            editCadastroEmail.requestFocus();
            editCadastroEmail.setError("*");
            Toast.makeText(CadastroActivity.this,"* Email não pode ser vazio",Toast.LENGTH_LONG).show();
        }else if((editSenha.getText().toString().isEmpty()) || (!editSenha.getText().toString().equals(editConfirmarSenha.getText().toString()))){
            isDadosOK = false;
            editSenha.requestFocus();
            editSenha.setError("*");
            editSenha.setText(null);
            editConfirmarSenha.requestFocus();
            editConfirmarSenha.setError("*");
            editConfirmarSenha.setText(null);
            Toast.makeText(CadastroActivity.this,"* Senhas não coeincidem",Toast.LENGTH_LONG).show();
        }

    return isDadosOK;

    }

    private void setDataPreferences() {

        preferencesCadastro = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor data = preferencesCadastro.edit();

        data.putString("nome",editNomeCompleto.getText().toString());
        data.putString("email",editCadastroEmail.getText().toString());
        data.putString("senha",editSenha.getText().toString());

        data.apply();

        usuario.setNome(preferencesCadastro.getString("nome",null));
        usuario.setEmail(preferencesCadastro.getString("email",null));
        usuario.setSenha(preferencesCadastro.getString("senha", null));

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
        btnCancelar = findViewById(R.id.btnCancelar);


    }
}