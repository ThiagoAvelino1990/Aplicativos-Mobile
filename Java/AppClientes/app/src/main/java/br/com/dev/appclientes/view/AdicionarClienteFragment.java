package br.com.dev.appclientes.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.model.Cliente;

public class AdicionarClienteFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;
    TextView textView;

    EditText editNomeCompleto, editTelefone, editEmail, editCep, editLogradouro, editComplemento, editNumero, editBairro, editCidade, editEstado, editPais;

    CheckBox chkTermosDeUso;

    Button btnCancelar, btnSalvar;

    Cliente cliente;
    ClienteController clienteController;


    public AdicionarClienteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_adicionar_cliente, container, false);

        inicializarComponentesDeLayout();

        eventoButton();

        return view;
    }

    /**
     * Método para inicializar os componentes
     */
    public void inicializarComponentesDeLayout(){

        textView = view.findViewById(R.id.txtAdicionarCliente);
        textView.setText(R.string.novo_cliente);


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

        btnSalvar = view.findViewById(R.id.btnSalvar);
        btnCancelar = view.findViewById(R.id.bntCancelar);

        chkTermosDeUso = view.findViewById(R.id.chkTermosDeUso);

        cliente = new Cliente();
        clienteController = new ClienteController(getContext());

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

                if(TextUtils.isEmpty(editComplemento.getText().toString())){
                    isDadosOK = false;
                    editComplemento.setError("*");
                    editComplemento.requestFocus();
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
                    Toast.makeText(getContext(),"Dados salvos com sucesso...", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Verifique os campos...", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}