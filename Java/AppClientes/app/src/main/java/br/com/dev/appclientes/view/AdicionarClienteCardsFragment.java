package br.com.dev.appclientes.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.controller.ClienteORMController;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.model.Cliente;
import br.com.dev.appclientes.model.ClienteORM;

public class AdicionarClienteCardsFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;
    TextView textView;

    EditText editNomeCompleto, editTelefone, editEmail, editCep, editLogradouro, editComplemento, editNumero, editBairro, editCidade, editEstado, editPais, editDocumento;

    CheckBox chkTermosDeUso;

    AppCompatButton btnCancelar, btnSalvar;

    SwitchCompat switchPjPFCli;

    Cliente cliente;
    ClienteController clienteController;

    ClienteORM clienteORM;
    ClienteORMController clienteORMController;

    UsuarioController usuarioController;

    //Construtor
    public AdicionarClienteCardsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_adicionar_cliente_cards, container, false);

        inicializarComponentesDeLayout();
        btnSalvar(btnSalvar);
        btnCancelar(btnCancelar);



        switchPjPFCli.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(switchPjPFCli.isChecked()){
                switchPjPFCli.setText("Pessoa Jurídica");
                editNomeCompleto.setHint("Razão Social");
                editDocumento.setHint("CNPJ");
                cleanComponent();
            }else{
                switchPjPFCli.setText("Pessoa Física");
                editNomeCompleto.setHint("Nome Completo");
                editDocumento.setHint("CPF");
                cleanComponent();
            }
        });

        return view;
    }

    private void cleanComponent() {
        editNomeCompleto.setText("");
        editTelefone.setText("");
        editEmail.setText("");
        editCep.setText("");
        editLogradouro.setText("");
        editComplemento.setText("");
        editNumero.setText("");
        editBairro.setText("");
        editCidade.setText("");
        editBairro.setText("");
        editEstado.setText("");
        editPais.setText("");
        editDocumento.setText("");
    }

    /**
     * Método para inicializar os componentes
     */
    public void inicializarComponentesDeLayout(){

        textView = view.findViewById(R.id.txtViewCliente);
        textView.setText(R.string.adicionar_cliente_cards);


        editNomeCompleto = view.findViewById(R.id.editNomeCompleto);
        editTelefone = view.findViewById(R.id.editTelefone);
        editEmail = view.findViewById(R.id.editEmail);
        editCep = view.findViewById(R.id.editCep);
        editLogradouro = view.findViewById(R.id.editLogradouro);
        editComplemento = view.findViewById(R.id.editComplemento);
        editNumero = view.findViewById(R.id.editNumero);
        editBairro = view.findViewById(R.id.editBairro);
        editCidade = view.findViewById(R.id.editCidade);
        editEstado = view.findViewById(R.id.editEstado);
        editPais = view.findViewById(R.id.editPais);
        editDocumento = view.findViewById(R.id.editDocumento);

        btnSalvar = view.findViewById(R.id.btnSalvar);
        btnCancelar = view.findViewById(R.id.bntCancelar);

        chkTermosDeUso = view.findViewById(R.id.chkTermosDeUso);

        switchPjPFCli = view.findViewById(R.id.switchPjPFCli);

        cliente = new Cliente();
        clienteController = new ClienteController(getContext());

        clienteORM = new ClienteORM();
        clienteORMController = new ClienteORMController();

        usuarioController = new UsuarioController(getContext());

    }

    public void eventoButton(){

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isDadosOK = true;

                if(TextUtils.isEmpty(editNomeCompleto.getText().toString())){
                    isDadosOK = false;
                    editNomeCompleto.setError("*");
                    editNomeCompleto.requestFocus();
                }

                if(TextUtils.isEmpty(editTelefone.getText().toString())){
                    isDadosOK = false;
                    editTelefone.setError("*");
                    editTelefone.requestFocus();
                }

                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    isDadosOK = false;
                    editEmail.setError("*");
                    editEmail.requestFocus();
                }

                if(TextUtils.isEmpty(editCep.getText().toString())){
                    isDadosOK = false;
                    editCep.setError("*");
                    editCep.requestFocus();
                }

                if(TextUtils.isEmpty(editLogradouro.getText().toString())){
                    isDadosOK = false;
                    editLogradouro.setError("*");
                    editLogradouro.requestFocus();
                }

                /** Removido. Complemento não deve ser um campo obrigatório
                 *

                if(TextUtils.isEmpty(editComplemento.getText().toString())){
                    isDadosOK = false;
                    editComplemento.setError("*");
                    editComplemento.requestFocus();
                }*/

                if(TextUtils.isEmpty(editNumero.getText().toString())){
                    isDadosOK = false;
                    editNumero.setError("*");
                    editNumero.requestFocus();
                }

                if(TextUtils.isEmpty(editBairro.getText().toString())){
                    isDadosOK = false;
                    editBairro.setError("*");
                    editBairro.requestFocus();
                }

                if(TextUtils.isEmpty(editCidade.getText().toString())){
                    isDadosOK = false;
                    editCidade.setError("*");
                    editCidade.requestFocus();
                }

                if(TextUtils.isEmpty(editEstado.getText().toString())){
                    isDadosOK = false;
                    editEstado.setError("*");
                    editEstado.requestFocus();
                }

                if(TextUtils.isEmpty(editPais.getText().toString())){
                    isDadosOK = false;
                    editPais.setError("*");
                    editPais.requestFocus();
                }



                if(isDadosOK){

                    cliente.setNome(editNomeCompleto.getText().toString());
                    cliente.setTelefone(editTelefone.getText().toString());
                    cliente.setEmail(editEmail.getText().toString());
                    cliente.setCep(Integer.parseInt(editCep.getText().toString()));
                    cliente.setLogradouro(editLogradouro.getText().toString());
                    cliente.setComplemento(editComplemento.getText().toString());
                    cliente.setNumero(editNumero.getText().toString());
                    cliente.setBairro(editBairro.getText().toString());
                    cliente.setCidade(editCidade.getText().toString());
                    cliente.setEstado(editEstado.getText().toString());
                    cliente.setPais(editPais.getText().toString());
                    cliente.setTermosDeUso(chkTermosDeUso.isChecked());

                    clienteController.insertObject(cliente);


                    /*Inclusão de dados ORM*/
                    clienteORM.setNome(editNomeCompleto.getText().toString());
                    clienteORM.setTelefone(editTelefone.getText().toString());
                    clienteORM.setEmail(editEmail.getText().toString());
                    clienteORM.setCep(Integer.parseInt(editCep.getText().toString()));
                    clienteORM.setLogradouro(editLogradouro.getText().toString());
                    clienteORM.setComplemento(editComplemento.getText().toString());
                    clienteORM.setNumero(editNumero.getText().toString());
                    clienteORM.setBairro(editBairro.getText().toString());
                    clienteORM.setCidade(editCidade.getText().toString());
                    clienteORM.setEstado(editEstado.getText().toString());
                    clienteORM.setPais(editPais.getText().toString());
                    clienteORM.setTermosDeUso(chkTermosDeUso.isChecked());

                    clienteORM.toString();
                    /** Verificar Erro ao salvar os dados
                    clienteORMController.insertORM(clienteORM);
                    */

                    Toast.makeText(getContext(),"Dados salvos com sucesso...", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Verifique os campos...", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    public void btnCancelar(View view) {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void btnSalvar(View view) {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDadosOK = true;

                if(TextUtils.isEmpty(editNomeCompleto.getText().toString())){
                    isDadosOK = false;
                    editNomeCompleto.setError("*");
                    editNomeCompleto.requestFocus();
                }

                if(TextUtils.isEmpty(editTelefone.getText().toString())){
                    isDadosOK = false;
                    editTelefone.setError("*");
                    editTelefone.requestFocus();
                }

                if(TextUtils.isEmpty(editEmail.getText().toString())){
                    isDadosOK = false;
                    editEmail.setError("*");
                    editEmail.requestFocus();
                }

                if(TextUtils.isEmpty(editCep.getText().toString())){
                    isDadosOK = false;
                    editCep.setError("*");
                    editCep.requestFocus();
                }

                if(TextUtils.isEmpty(editLogradouro.getText().toString())){
                    isDadosOK = false;
                    editLogradouro.setError("*");
                    editLogradouro.requestFocus();
                }

                if(TextUtils.isEmpty(editNumero.getText().toString())){
                    isDadosOK = false;
                    editNumero.setError("*");
                    editNumero.requestFocus();
                }

                if(TextUtils.isEmpty(editBairro.getText().toString())){
                    isDadosOK = false;
                    editBairro.setError("*");
                    editBairro.requestFocus();
                }

                if(TextUtils.isEmpty(editCidade.getText().toString())){
                    isDadosOK = false;
                    editCidade.setError("*");
                    editCidade.requestFocus();
                }

                if(TextUtils.isEmpty(editEstado.getText().toString())){
                    isDadosOK = false;
                    editEstado.setError("*");
                    editEstado.requestFocus();
                }

                if(TextUtils.isEmpty(editPais.getText().toString())){
                    isDadosOK = false;
                    editPais.setError("*");
                    editPais.requestFocus();
                }

                if(TextUtils.isEmpty(editDocumento.getText().toString()) || !AppUtil.validaCnpjCpf(editDocumento.getText().toString())){
                    isDadosOK = false;
                    editDocumento.setHint("*");
                    editDocumento.requestFocus();
                }



                if(isDadosOK){

                    if(setClienteSQLLite()){
                        Toast.makeText(getContext(),"Erro ao salvar os dados SQLLITE...", Toast.LENGTH_LONG).show();
                    }else if(setClienteORM()){
                        Toast.makeText(getContext(),"Erro ao salvar os dados ORM...", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),"Dados salvos com sucesso...", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(getContext(),"Verifique os campos...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean setClienteSQLLite(){


        try{
            cliente.setNome(editNomeCompleto.getText().toString());
            cliente.setTelefone(editTelefone.getText().toString());
            cliente.setEmail(editEmail.getText().toString());
            cliente.setDocumento(editDocumento.getText().toString());
            cliente.setCep(Integer.parseInt(editCep.getText().toString()));
            cliente.setLogradouro(editLogradouro.getText().toString());
            cliente.setComplemento(editComplemento.getText().toString());
            cliente.setNumero(editNumero.getText().toString());
            cliente.setBairro(editBairro.getText().toString());
            cliente.setCidade(editCidade.getText().toString());
            cliente.setEstado(editEstado.getText().toString());
            cliente.setPais(editPais.getText().toString());
            cliente.setTermosDeUso(chkTermosDeUso.isChecked());

            clienteController.insertObject(cliente);
            return true;
        }catch(Exception err){
            Log.e(AppUtil.TAG,"Erro ao salvar o cliente[SQL LITE] "+err.getMessage());
            return false;
        }
    }

    public boolean setClienteORM(){
        try{
            /*Inclusão de dados ORM*/
            clienteORM.setNome(editNomeCompleto.getText().toString());
            clienteORM.setTelefone(editTelefone.getText().toString());
            clienteORM.setEmail(editEmail.getText().toString());
            clienteORM.setCep(Integer.parseInt(editCep.getText().toString()));
            clienteORM.setLogradouro(editLogradouro.getText().toString());
            clienteORM.setComplemento(editComplemento.getText().toString());
            clienteORM.setNumero(editNumero.getText().toString());
            clienteORM.setBairro(editBairro.getText().toString());
            clienteORM.setCidade(editCidade.getText().toString());
            clienteORM.setEstado(editEstado.getText().toString());
            clienteORM.setPais(editPais.getText().toString());
            clienteORM.setTermosDeUso(chkTermosDeUso.isChecked());

            clienteORM.toString();

            clienteORMController.insertORM(clienteORM);
            return true;
        }catch (Exception err){
            Log.e(AppUtil.TAG,"Erro ao salvar o cliente[ORM] "+err.getMessage());
            return false;
        }
    }

}