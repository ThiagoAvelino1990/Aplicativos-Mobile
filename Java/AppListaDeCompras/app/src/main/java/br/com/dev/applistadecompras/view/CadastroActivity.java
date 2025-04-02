package br.com.dev.applistadecompras.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import br.com.dev.applistadecompras.R;
import br.com.dev.applistadecompras.controller.UsuarioController;
import br.com.dev.applistadecompras.model.Usuario;
import br.com.dev.applistadecompras.util.AppUtil;

public class CadastroActivity extends AppCompatActivity {

    TextView txtViewCadastro, txtCadastroNovoUsuario;
    EditText editNomeCadastro, editSobrenome, editEndereco, editComplemento, editCpf, editCadastroEmail, editSenha, editConfirmarSenha;
    Button btnConfirmarCadastro, btnVoltarCadastro;
    CheckBox chkTermos;

    Usuario usuario;
    UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initLayout();
        btnConfirmarCadastro(btnConfirmarCadastro);
        btnCancelarCadastro(btnVoltarCadastro);

    }

    private void initLayout() {

        txtCadastroNovoUsuario = findViewById(R.id.txtCadastroNovoUsuario);
        txtViewCadastro = findViewById(R.id.txtViewCadastro);

        editNomeCadastro = findViewById(R.id.editNomeCadastro);
        editSobrenome = findViewById(R.id.editSobrenome);
        editEndereco = findViewById(R.id.editEndereco);
        editComplemento = findViewById(R.id.editComplemento);
        editCpf = findViewById(R.id.editCpf);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);

        btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnVoltarCadastro = findViewById(R.id.btnVoltarCadastro);

        chkTermos = findViewById(R.id.chkTermos);

        usuario = new Usuario();
        usuarioController = new UsuarioController();

    }


    public void btnCancelarCadastro(View view) {
        btnVoltarCadastro.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder
                        .with(CadastroActivity.this)
                        .setTitle("Cancelar Cadastro")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                        .setMessage("Deseja Cancelar o cadastro ?")
                        .setNegativeBtnText("Não")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                        .setPositiveBtnText("Sim")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                        .onPositiveClicked(dialog -> {
                            intent = new Intent(CadastroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();})
                        .onNegativeClicked(dialog -> closeContextMenu())
                        .build()
                        .show();
            }
        });
    }

    public void btnConfirmarCadastro(View view) {
        btnConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder
                        .with(CadastroActivity.this)
                        .setTitle("Confirmar Cadastro")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                        .setMessage(AppUtil.TERMOS_DE_USO)
                        .setNegativeBtnText("Não")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                        .setPositiveBtnText("Sim")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                        .onPositiveClicked(dialog -> {
                            if(validaDados()){

                                if(setUsuario()){
                                    Toast.makeText(CadastroActivity.this,"Cadastro realizado com sucesso !",Toast.LENGTH_LONG).show();
                                    intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else{
                                Toast.makeText(CadastroActivity.this,"Verifique os dados e tente novamente !",Toast.LENGTH_LONG).show();
                                chkTermos.setChecked(false);
                                closeContextMenu();
                            }
                        })
                        .onNegativeClicked(dialog -> {
                            chkTermos.setChecked(false);
                            closeContextMenu();
                        })
                        .build()
                        .show();
            }
        });
    }

    private boolean setUsuario() {

        try{
            usuario.setNome(editNomeCadastro.getText().toString());
            usuario.setSobrenome(editSobrenome.getText().toString());
            usuario.setEndereco(editEndereco.getText().toString());
            usuario.setComplemento(editComplemento.getText().toString());;
            usuario.setCpf(AppUtil.formataDocumento(editCpf.getText().toString(), editCpf.getText().toString().length()));
            usuario.setEmail(editCadastroEmail.getText().toString());
            usuario.setSenha(AppUtil.criptografarPass(editConfirmarSenha.getText().toString()));
            usuario.setDataInclusao(AppUtil.getDataAtual());
            usuario.setTermos(chkTermos.isChecked());

            usuarioController.insert(usuario);

            Log.i(AppUtil.TAG,usuarioController.toString());

            return true;

        }catch(Exception err){
            Toast.makeText(CadastroActivity.this,"Não foi possível inserir os dados",Toast.LENGTH_LONG).show();
            Log.e(AppUtil.TAG,"Erro ao inserir os dados do usuário "+err.getMessage());
            return false;
        }


    }

    public boolean validaDados(){

        boolean isDadosOK = true;

        chkTermos.setChecked(true);

        if(editNomeCadastro.getText().toString().isEmpty()){
            editNomeCadastro.setError("*");
            editNomeCadastro.requestFocus();
            isDadosOK = false;
        }

        if(editSobrenome.getText().toString().isEmpty()){
            editSobrenome.setError("*");
            editSobrenome.requestFocus();
            isDadosOK = false;
        }

        if(editEndereco.getText().toString().isEmpty()){
            editEndereco.setError("*");
            editEndereco.requestFocus();
            isDadosOK = false;
        }

        if(editCpf.getText().toString().isEmpty() || !AppUtil.validaCnpjCpf(editCpf.getText().toString())){
            editCpf.setError("*");
            editCpf.requestFocus();
            isDadosOK = false;
            Toast.makeText(CadastroActivity.this,"* Documento inválido",Toast.LENGTH_LONG).show();
        }

        if(editCadastroEmail.getText().toString().isEmpty()){
            editCadastroEmail.setError("*");
            editCadastroEmail.requestFocus();
            isDadosOK = false;
        }

        if(editSenha.getText().toString().isEmpty()){
            editSenha.setError("*");
            editSenha.requestFocus();
            isDadosOK = false;
        }

        if(!editSenha.getText().toString().equals(editConfirmarSenha.getText().toString())){
            editSenha.setError("*");
            editSenha.requestFocus();
            editSenha.setText("");
            editConfirmarSenha.setError("*");
            editConfirmarSenha.setText("");
            isDadosOK = false;
        }

        if(!AppUtil.validaEmail(editCadastroEmail.getText().toString())){
            isDadosOK = false;
            editCadastroEmail.setError("*");
            editCadastroEmail.requestFocus();
            Toast.makeText(CadastroActivity.this,"Por favor informar um e-mail válido",Toast.LENGTH_SHORT).show();
        }




        return isDadosOK;
    }
}