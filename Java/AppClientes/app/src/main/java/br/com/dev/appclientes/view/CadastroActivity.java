package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


import com.google.gson.JsonObject;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

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
    EditText editNomeCompleto, editCadastroEmail, editSenha, editConfirmarSenha, editCpfCnpj, editCep, editEndereco, editComplementoEnd, editTelefone,
    editBairro, editCidade, editEstado;
    Button btnConfirmarCadastro, btnCancelar;

    SharedPreferences preferencesCadastro;

    Usuario usuario;
    UsuarioController controller;

    UsuarioORM usuarioORM;
    UsuarioORMController controllerORM;

    JsonObject jsonApiEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initComponentesLayout();

        switchPjPF.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchPjPF.isChecked()) {
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
            } else {
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

        formatarCamposFormulario();


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
                            if (validarDados()) {

                                setUsuarioSQL();
                                setUsuarioORM();
                                setDataPreferences();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso !", Toast.LENGTH_LONG).show();
                                        intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, AppUtil.TIME_CADASTRO);
                            }
                        })
                        .onNegativeClicked(dialog -> {
                            Toast.makeText(CadastroActivity.this, "Desculpa, o cadastro só pode ser concluído aceitando os termos", Toast.LENGTH_SHORT).show();
                            closeContextMenu();
                        })
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
                        .onPositiveClicked(dialog -> {
                            Toast.makeText(CadastroActivity.this, "Operação Cancelada", Toast.LENGTH_SHORT).show();
                            intent = new Intent(CadastroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .onNegativeClicked(dialog -> closeContextMenu())
                        .build()
                        .show();
            }
        });


    }

    public void formatarCamposFormulario(){
        //TODO: Manter este processo aqui ?
        //Utiliza o serviço de CEP para trazer os dados necessários
        editCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String cepAtual = s.toString();
                String cepFormatado = AppUtil.formatarCep(editCep.getText().toString());

                //Evitar Loop infinito
                if(!cepAtual.equals(cepFormatado)){
                    editCep.removeTextChangedListener(this);
                    editCep.setText(cepFormatado);
                    editCep.setSelection(cepFormatado.length());
                    editCep.addTextChangedListener(this);
                }

                if(!s.toString().isEmpty()){
                    setInformacoes(s.toString());
                }
            }


        });


        editCpfCnpj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cpfCnpjAtual = s.toString();
                String cpfCnpjAtualFormatado = AppUtil.formatarDocumento(cpfCnpjAtual, cpfCnpjAtual.length());

                //Evitar Loop infinito
                if(!cpfCnpjAtual.equals(cpfCnpjAtualFormatado)){
                    editCpfCnpj.removeTextChangedListener(this);
                    editCpfCnpj.setText(cpfCnpjAtualFormatado);
                    editCpfCnpj.setSelection(cpfCnpjAtualFormatado.length());
                    editCpfCnpj.addTextChangedListener(this);
                }

            }
        });

        editTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String telefoneAtual = s.toString();
                String telefoneFormatado = AppUtil.formatarTelefone(telefoneAtual);

                //Evitar Loop infinito
                if (!telefoneAtual.matches(telefoneFormatado)) {
                    editTelefone.removeTextChangedListener(this);
                    editTelefone.setText(telefoneFormatado);
                    editTelefone.setSelection(telefoneFormatado.length());
                    editEndereco.addTextChangedListener(this);

                }

            }
        });
    }


    public void setInformacoes(String cep){
        new Thread(() -> {
            jsonApiEnd = AppUtil.getEndereco(cep);

            if (jsonApiEnd != null){
                runOnUiThread(() -> {
                    try{
                        editEndereco.setText(jsonApiEnd.get("street").getAsString());
                        editBairro.setText(jsonApiEnd.get("neighborhood").getAsString());
                        editCidade.setText(jsonApiEnd.get("city").getAsString());
                        editEstado.setText(jsonApiEnd.get("state").getAsString());
                    }catch(Exception err){
                        Toast.makeText(this,"Erro ao preencher os campos ", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }).start();

    }

    public void setUsuarioSQL() {

        usuario.setNome(editNomeCompleto.getText().toString().toUpperCase());
        usuario.setCpfCnpj(editCpfCnpj.getText().toString());
        usuario.setLogradouro(editEndereco.getText().toString().toUpperCase());
        usuario.setComplemento(editComplementoEnd.getText().toString().toUpperCase());
        usuario.setTelefone(editTelefone.getText().toString());
        usuario.setEmail(editCadastroEmail.getText().toString());
        usuario.setSenha(AppUtil.criptografarPass(editSenha.getText().toString()));
        usuario.setDataInclusao(AppUtil.getDataFormat());
        usuario.setPessoaFisica(switchPjPF.isChecked());

        controller.insertObject(usuario);

    }
    //TODO:Utilizar dados digitados e não o sharedPreferences
    public void setUsuarioORM() {

        usuarioORM.setNome(preferencesCadastro.getString("descricao", null).toUpperCase());
        usuarioORM.setCpfCnpj(preferencesCadastro.getString("documento", null));
        usuarioORM.setLogradouro(preferencesCadastro.getString("logradouro", null).toUpperCase());
        usuarioORM.setComplemento(preferencesCadastro.getString("complemento", null).toUpperCase());
        usuarioORM.setTelefone(preferencesCadastro.getString("telefone", null));
        usuarioORM.setEmail(preferencesCadastro.getString("email", null));
        usuarioORM.setSenha(preferencesCadastro.getString("senha", null));
        usuarioORM.setDataInclusao(preferencesCadastro.getString("dataInclusao", null));

        if (preferencesCadastro.getString("tipo_pessoa", null).equals("1")) {
            usuarioORM.setPessoaFisica(true);
        } else {
            usuarioORM.setPessoaFisica(false);
        }

        controllerORM.insertORM(usuarioORM);

    }

    private boolean validarDados() {

        boolean isDadosOK = true;

        if (editNomeCompleto.getText().toString().isEmpty()) {
            isDadosOK = false;
            editNomeCompleto.requestFocus();
            editNomeCompleto.setError("*");
            Toast.makeText(CadastroActivity.this, "* Nome não pode ser vazio", Toast.LENGTH_LONG).show();
        } else if (editCadastroEmail.getText().toString().isEmpty()) {
            isDadosOK = false;
            editCadastroEmail.requestFocus();
            editCadastroEmail.setError("*");
            Toast.makeText(CadastroActivity.this, "* Email não pode ser vazio", Toast.LENGTH_LONG).show();
        } else if (editCpfCnpj.getText().toString().isEmpty() || !AppUtil.validaCnpjCpf(editCpfCnpj.getText().toString())) {
            isDadosOK = false;
            editCpfCnpj.requestFocus();
            editCpfCnpj.setError("*");
            Toast.makeText(CadastroActivity.this, "* Documento Inválido", Toast.LENGTH_LONG).show();
        }else if(editCep.getText().toString().isEmpty()){
            editCep.requestFocus();
            editCep.setError("*");
            Toast.makeText(CadastroActivity.this, "Cep deve ser informado", Toast.LENGTH_LONG).show();
        } else if (editEndereco.getText().toString().isEmpty()) {
            editEndereco.requestFocus();
            editEndereco.setError("*");
            Toast.makeText(CadastroActivity.this, "Endereço deve ser informado", Toast.LENGTH_LONG).show();
        } else if(editBairro.getText().toString().isEmpty()) {
            editBairro.requestFocus();
            editBairro.setError("*");
            Toast.makeText(CadastroActivity.this, "Bairro deve ser informado", Toast.LENGTH_LONG).show();
        }else if(editCidade.getText().toString().isEmpty()) {
            editCidade.requestFocus();
            editCidade.setError("*");
            Toast.makeText(CadastroActivity.this, "Cidade deve ser informado", Toast.LENGTH_LONG).show();
        }else if(editEstado.getText().toString().isEmpty()){
            editEstado.requestFocus();
            editEstado.setError("*");
            Toast.makeText(CadastroActivity.this, "Estado deve ser informado", Toast.LENGTH_LONG).show();
        } else if (editTelefone.getText().toString().isEmpty()) {
            isDadosOK = false;
            editTelefone.requestFocus();
            editTelefone.setError("*");
        } else if ((editSenha.getText().toString().isEmpty()) || (!editSenha.getText().toString().equals(editConfirmarSenha.getText().toString()))) {
            isDadosOK = false;
            editSenha.requestFocus();
            editSenha.setError("*");
            editSenha.setText(null);
            editConfirmarSenha.requestFocus();
            editConfirmarSenha.setError("*");
            editConfirmarSenha.setText(null);
            Toast.makeText(CadastroActivity.this, "* Senhas não coeincidem", Toast.LENGTH_LONG).show();
        }

        return isDadosOK;

    }

    private void setDataPreferences() {

        preferencesCadastro = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        SharedPreferences.Editor data = preferencesCadastro.edit();

        try {
            if (switchPjPF.isChecked()) {
                data.putString("tipo_pessoa", "1");
            } else {
                data.putString("tipo_pessoa", "0");
            }
            data.putString("descricao", editNomeCompleto.getText().toString().toUpperCase());
            data.putString("documento", AppUtil.formatarDocumento(editCpfCnpj.getText().toString(), editCpfCnpj.getText().toString().length()));
            data.putString("logradouro", editEndereco.getText().toString().toUpperCase());
            data.putString("complemento", editComplementoEnd.getText().toString().toUpperCase());
            data.putString("telefone", editTelefone.getText().toString());
            data.putString("email", editCadastroEmail.getText().toString());
            data.putString("senha", AppUtil.criptografarPass(editSenha.getText().toString()));
            data.putString("dataInclusao", AppUtil.getDataFormat());


            data.apply();
        } catch (SecurityException err) {
            Log.e(AppUtil.TAG, "Erro ao gravar os dados no sharedPreferens " + err.getMessage());
        } catch (Exception err) {
            Log.e(AppUtil.TAG, "Erro ao genérico no sharedPreferens " + err.getMessage());
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
        editTelefone = findViewById(R.id.editTelefone);
        editCep = findViewById(R.id.editCep);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);

        btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnCancelar = findViewById(R.id.btnCancelar);

        /*Instanciando os objetos*/
        usuario = new Usuario();
        controller = new UsuarioController(CadastroActivity.this);

        usuarioORM = new UsuarioORM();
        controllerORM = new UsuarioORMController();

    }

}