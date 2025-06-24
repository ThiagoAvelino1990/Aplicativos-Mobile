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
import br.com.dev.appclientes.api.AppUtilToast;
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

        editAltCliNomeCompleto = view.findViewById(R.id.editAltCliNomeCompleto);
        editAltCliTelefone = view.findViewById(R.id.editAltCliTelefone);
        editAltCliEmail = view.findViewById(R.id.editAltCliEmail);
        editAltCliCep = view.findViewById(R.id.editAltCliCep);
        editAltCliLogradouro = view.findViewById(R.id.editAltCliLogradouro);
        editAltCliComplemento = view.findViewById(R.id.editAltCliComplemento);
        editAltCliNumero = view.findViewById(R.id.editAltCliNumero);
        editAltCliBairro = view.findViewById(R.id.editAltCliBairro);
        editAltCliCidade = view.findViewById(R.id.editAltCliCidade);
        editAltCliEstado = view.findViewById(R.id.editAltCliEstado);
        editAltCliPais = view.findViewById(R.id.editAltCliPais);
        editAltCliDocumento = view.findViewById(R.id.editAltCliDocumento);

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

                    AppUtilToast.toastMessage(getContext(),"Dados salvos com sucesso...");
                }else{
                    AppUtilToast.toastMessage(getContext(),"Verifique os campos...");
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
                        AppUtilToast.toastMessage(getContext(),"Erro ao salvar os dados SQLLITE...");
                    }else if(!setClienteORM()){
                        AppUtilToast.toastMessage(getContext(),"Erro ao salvar os dados ORM...");
                    }else{
                        AppUtilToast.toastMessage(getContext(),"Dados salvos com sucesso...");
                    }


                }else{
                    AppUtilToast.toastMessage(getContext(),"Verifique os campos...");
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