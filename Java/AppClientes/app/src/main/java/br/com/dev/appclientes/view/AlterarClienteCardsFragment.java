package br.com.dev.appclientes.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.controller.ClienteORMController;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.model.Cliente;
import br.com.dev.appclientes.model.ClienteORM;

//TODO: criar lógica para alteração dos dados de um cliente
public class AlterarClienteCardsFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;
    TextView textView;

    EditText editAltCliNomeCompleto, editAltCliTelefone, editAltCliEmail, editAltCliCep,
             editAltCliLogradouro, editAltCliComplemento, editAltCliNumero, editAltCliBairro,
             editAltCliCidade, editAltCliEstado, editAltCliPais, editAltCliDocumento;


    AppCompatButton btnAltCliCancelar, btnAltCliSalvar;

    Cliente cliente;
    ClienteController clienteController;

    ClienteORM clienteORM;
    ClienteORMController clienteORMController;

    UsuarioController usuarioController;

    //Construtor
    public AlterarClienteCardsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            cliente = (Cliente) getArguments().getSerializable("cliente");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_alterar_cliente_cards, container, false);

        inicializarComponentesDeLayout();
        eventoButton();

        return view;
    }

    private void cleanComponent() {

    }

    /**
     * Método para inicializar os componentes
     */
    public void inicializarComponentesDeLayout(){

        textView = view.findViewById(R.id.txtAltCliViewCliente);
        textView.setText("Alterar Dados Cliente");

        editAltCliNomeCompleto = editAltCliNomeCompleto.findViewById(R.id.editAltCliNomeCompleto);
        editAltCliTelefone = editAltCliTelefone.findViewById(R.id.editAltCliTelefone);
        editAltCliEmail = editAltCliEmail.findViewById(R.id.editAltCliEmail);
        editAltCliCep = editAltCliCep.findViewById(R.id.editAltCliCep);
        editAltCliLogradouro = editAltCliLogradouro.findViewById(R.id.editAltCliLogradouro);
        editAltCliComplemento = editAltCliComplemento.findViewById(R.id.editAltCliComplemento);
        editAltCliNumero = editAltCliNumero.findViewById(R.id.editAltCliNumero);
        editAltCliBairro = editAltCliBairro.findViewById(R.id.editAltCliBairro);
        editAltCliCidade = editAltCliCidade.findViewById(R.id.editAltCliCidade);
        editAltCliEstado = editAltCliEstado.findViewById(R.id.editAltCliEstado);
        editAltCliPais = editAltCliPais.findViewById(R.id.editAltCliPais);
        editAltCliDocumento = editAltCliDocumento.findViewById(R.id.editAltCliDocumento);

        cliente = new Cliente();
        clienteController = new ClienteController(getContext());

        clienteORM = new ClienteORM();
        clienteORMController = new ClienteORMController();

        usuarioController = new UsuarioController(getContext());

    }

    public void eventoButton(){

        btnAltCliSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isDadosOK = true;

                if(isDadosOK){



                    clienteController.insertObject(cliente);


                    /*Inclusão de dados ORM*/


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
        btnAltCliCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void btnSalvar(View view) {
        btnAltCliSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDadosOK = true;


                if(isDadosOK){

                    if(!setClienteSQLLite()){
                        Toast.makeText(getContext(),"Erro ao salvar os dados SQLLITE...", Toast.LENGTH_LONG).show();
                    }else if(!setClienteORM()){
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


            clienteORM.toString();

            clienteORMController.insertORM(clienteORM);
            return true;
        }catch (Exception err){
            Log.e(AppUtil.TAG,"Erro ao salvar o cliente[ORM] "+err.getMessage());
            return false;
        }
    }

}