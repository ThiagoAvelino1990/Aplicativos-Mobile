package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilSharedPreferences;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    TextView txtLogin, txtLoginEmail, txtLoginSenha, txtVerifiqueDados;
    EditText editLoginEmail, editLoginSenha;
    CheckBox chkLembrarDados;
    Button btnLogin, btnEsqueceuSenha, btnCadastrar;

    SharedPreferences sharedPreferences;
    Usuario usuario;
    UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        initComponentesLayout();

        btnEsqueceuSenha.setVisibility(View.VISIBLE);

        btnLogin(btnLogin);
        btnCadastrar(btnCadastrar);
        btnEsqueceuSenha(btnEsqueceuSenha);

    }

    public void btnLogin(View view) {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                validarTrocaDeSenha();

                if (validarDadosFormulario()) {

                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    public void btnCadastrar(View view) {
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void btnEsqueceuSenha(View view) {
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
        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);

        editLoginEmail.setText(sharedPreferences.getString("email", ""));
        editLoginSenha.setText(sharedPreferences.getString("senha", ""));
        chkLembrarDados.setChecked(sharedPreferences.getBoolean("chklembrardados", false));

    }

    private void saveDataSharedPreferences() {

        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dadosSalvos = sharedPreferences.edit();

        int idUser = usuarioController.readObjetcIdByEmail(sharedPreferences.getString("email", ""));


        dadosSalvos.putBoolean("chklembrardados", chkLembrarDados.isChecked());
        dadosSalvos.putString("idUsuario", String.valueOf(idUser));


        dadosSalvos.apply();
    }

    private boolean validarDadosFormulario() {

        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);

        boolean isDadosOK = true;

        List<Usuario> usuarioList = new ArrayList<>();
        try{
            usuarioList = usuarioController.readObjectByEmail(editLoginEmail.getText().toString());
            for (Usuario usuario: usuarioList) {
                if(usuario.getEmail().isEmpty()){
                    editLoginEmail.setError("*");
                    editLoginEmail.requestFocus();
                    txtVerifiqueDados.setVisibility(View.VISIBLE);
                    btnEsqueceuSenha.setVisibility(View.VISIBLE);
                    isDadosOK = false;
                    Toast.makeText(getApplicationContext(),"Verifique os dados e tente novamente", Toast.LENGTH_SHORT).show();
                    break;
                } else if (usuario.getSenha().isEmpty()) {
                    editLoginSenha.setError("*");
                    editLoginSenha.requestFocus();
                    txtVerifiqueDados.setVisibility(View.VISIBLE);
                    btnEsqueceuSenha.setVisibility(View.VISIBLE);
                    isDadosOK = false;
                    Toast.makeText(getApplicationContext(),"Verifique os dados e tente novamente", Toast.LENGTH_SHORT).show();
                } else {
                    saveDataSharedPreferences();
                }
            }
        }catch(Exception err){
            Toast.makeText(getApplicationContext(),"Erro ao validar os dados",Toast.LENGTH_LONG).show();
            isDadosOK = false;
            Log.e(AppUtil.TAG,"[LoginActivity - validarDadosFormulario]" + err.getMessage());
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

        usuario = new Usuario();
        usuarioController = new UsuarioController(LoginActivity.this);

    }

    private String criptografarSenhaDigitada(String senhaDigitada) {

        return AppUtil.criptografarPass(senhaDigitada);
    }

    public void validarTrocaDeSenha() {
        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);

        List<Usuario> usuarioLista = new ArrayList<>();

        try{
            usuarioLista = usuarioController.readObjectByEmail(sharedPreferences.getString("email",null));
            for (Usuario usuario : usuarioLista) {
                if (usuario.getAtualizaSenha().equals("S")) {
                    atualizarSenha();
                }
            }
        }catch(Exception err){
            Log.e(AppUtil.TAG,"[LoginActivity - validarTrocaDeSenha] -Erro ao realizar a validação de torca de senha "+err.getMessage());
        }



    }

    public void atualizarSenha() {
        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor dadosSalvos = sharedPreferences.edit();

        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Atualizar Senha");


            //Criando o layout
            LinearLayout linearLayout = new LinearLayout(LoginActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(50, 40, 50, 10);

            //Campos para senha
            final EditText novaSenha = new EditText(LoginActivity.this);
            novaSenha.setHint("Nova senha");
            novaSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            linearLayout.addView(novaSenha);

            //Confirmação da senha
            final EditText confirmarSenha = new EditText(LoginActivity.this);
            confirmarSenha.setHint("Confirmar senha");
            confirmarSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            linearLayout.addView(confirmarSenha);

            builder.setView(linearLayout);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String senhaNova = novaSenha.getText().toString();
                String senhaConfirmar = confirmarSenha.getText().toString();

                if (senhaNova.isEmpty() || (!senhaNova.equals(senhaConfirmar))) {
                    Toast.makeText(LoginActivity.this, "As senhas não conferem", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        dadosSalvos.putString("senha", AppUtil.criptografarPass(senhaConfirmar));
                        dadosSalvos.apply();

                        editLoginSenha.setText(sharedPreferences.getString("senha", null));


                        usuario.setId(Integer.parseInt(sharedPreferences.getString("idUsuario", String.valueOf(-1))));
                        usuario.setEmail(sharedPreferences.getString("email", null));
                        usuario.setSenha(sharedPreferences.getString("senha", null));
                        usuario.setAtualizaSenha("N");

                        usuarioController.insertObject(usuario);

                        //Limpar o campo de senha
                        txtLoginSenha.setText("");

                        Toast.makeText(LoginActivity.this, "Senha atualizada com sucesso", Toast.LENGTH_LONG).show();


                    } catch (Exception err) {
                        Log.e(AppUtil.TAG, "Erro ao atualizar nova senha " + err.getMessage());
                    }

                }
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }catch(NullPointerException err){
            Toast.makeText(getApplicationContext(),"Não foi possível realizar a troca de senha.",Toast.LENGTH_LONG).show();
            Log.e(AppUtil.TAG,"Não foi possível realizar a troca de senha(NullPointerException).[LoginActivity - atualizarSenha] "+err.getMessage());
        }catch(Exception err){
            Toast.makeText(getApplicationContext(),"Não foi possível criar a tela para troca de senha.",Toast.LENGTH_LONG).show();
            Log.e(AppUtil.TAG,"Não foi possível realizar a troca de senha.[LoginActivity - atualizarSenha] "+err.getMessage());
        }
    }


}