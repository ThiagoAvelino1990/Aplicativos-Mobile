package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;


import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;

import org.w3c.dom.Text;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.controller.UsuarioORMController;
import br.com.dev.appclientes.model.Usuario;
import br.com.dev.appclientes.model.UsuarioORM;

public class CadastroActivity extends AppCompatActivity {

    ImageView imgAppClienteCadastro;
    SwitchCompat switchPjPF;
    TextView txtViewCadastro, txtCadastroNovoUsuario;
    EditText editNomeCompleto, editCadastroEmail, editSenha, editConfirmarSenha, editCpfCnpj, editEndereco, editComplementoEnd;
    Button btnConfirmarCadastro, btnCancelar;

    SharedPreferences preferencesCadastro;

    Usuario usuario;
    UsuarioController controller;

    UsuarioORM usuarioORM;
    UsuarioORMController controllerORM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initComponentesLayout();

        usuario = new Usuario();
        controller = new UsuarioController(CadastroActivity.this);

        switchPjPF.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(switchPjPF.isChecked()){
                switchPjPF.setText("Pessoa Jurídica");
                editNomeCompleto.setHint("Razão Social");
                editCpfCnpj.setHint("CNPJ");
                editNomeCompleto.setText("");
                editCpfCnpj.setText("");
                editEndereco.setText("");
                editComplementoEnd.setText("");
                editCadastroEmail.setText("");
                editSenha.setText("");
                editConfirmarSenha.setText("");
            }else{
                switchPjPF.setText("Pessoa Física");
                editNomeCompleto.setHint("Nome Completo");
                editCpfCnpj.setHint("CPF");
                editNomeCompleto.setText("");
                editCpfCnpj.setText("");
                editEndereco.setText("");
                editComplementoEnd.setText("");
                editCadastroEmail.setText("");
                editSenha.setText("");
                editConfirmarSenha.setText("");
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
                            setUsuarioSQL();
                            setUsuarioORM();

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
        }else if (editCpfCnpj.getText().toString().isEmpty() || !AppUtil.validaCnpjCpf(editCpfCnpj.getText().toString())) {
            isDadosOK = false;
            editCpfCnpj.requestFocus();
            editCpfCnpj.setError("*");
            Toast.makeText(CadastroActivity.this,"* Documento Inválido",Toast.LENGTH_LONG).show();
        }else if(editEndereco.getText().toString().isEmpty()){
            editEndereco.requestFocus();
            editEndereco.setError("*");
            Toast.makeText(CadastroActivity.this,"Endereço deve ser informado",Toast.LENGTH_LONG).show();
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

        try{
            if(switchPjPF.isChecked()){
                data.putString("tipo_pessoa","1");
            }else{
                data.putString("tipo_pessoa","0");
            }
            data.putString("descricao",editNomeCompleto.getText().toString());
            data.putString("documento",AppUtil.formataDocumento(editCpfCnpj.getText().toString(),editCpfCnpj.getText().toString().length()));
            data.putString("logradouro",editEndereco.getText().toString());
            data.putString("complemento",editComplementoEnd.getText().toString());
            data.putString("email",editCadastroEmail.getText().toString());
            data.putString("senha",editSenha.getText().toString());


            data.apply();
        }catch(SecurityException err){
            Log.e(AppUtil.TAG,"Erro ao gravar os dados no sharedPreferens "+err.getMessage());
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro ao genérico no sharedPreferens "+err.getMessage());
        }

    }

    private void initComponentesLayout() {

        imgAppClienteCadastro = findViewById(R.id.imgAppClienteCadastro);

        txtViewCadastro = findViewById(R.id.txtViewCadastro);
        txtCadastroNovoUsuario = findViewById(R.id.txtCadastroNovoUsuario);

        switchPjPF = findViewById(R.id.switchPjPF);

        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        editCpfCnpj = findViewById(R.id.editCpfCnpj);
        editEndereco = findViewById(R.id.editEndereco);
        editComplementoEnd = findViewById(R.id.editComplementoEnd);

        btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnCancelar = findViewById(R.id.btnCancelar);

    }

    public void setUsuarioSQL(){

        usuario.setNome(preferencesCadastro.getString("descricao",null));
        usuario.setCpfCnpj(preferencesCadastro.getString("documento", null));
        usuario.setLogradouro(preferencesCadastro.getString("logradouro", null));
        usuario.setComplemento(preferencesCadastro.getString("complemento", null));
        usuario.setEmail(preferencesCadastro.getString("email",null));
        usuario.setSenha(preferencesCadastro.getString("senha", null));

        if(preferencesCadastro.getString("tipo_pessoa",null)== "1"){
            usuario.setPessoaFisica(true);
        }else{
            usuario.setPessoaFisica(false);
        }

        controller.insertObject(usuario);

    }

    public void setUsuarioORM(){

        usuarioORM.setNome(preferencesCadastro.getString("descricao",null));
        usuarioORM.setCpfCnpj(preferencesCadastro.getString("documento", null));
        usuarioORM.setLogradouro(preferencesCadastro.getString("logradouro", null));
        usuarioORM.setComplemento(preferencesCadastro.getString("complemento", null));
        usuarioORM.setEmail(preferencesCadastro.getString("email",null));
        usuarioORM.setSenha(preferencesCadastro.getString("senha", null));

        if(preferencesCadastro.getString("tipo_pessoa",null)== "1"){
            usuarioORM.setPessoaFisica(true);
        }else{
            usuarioORM.setPessoaFisica(false);
        }

        controllerORM.insertORM(usuarioORM);

    }



}