package br.com.dev.appclientes.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import br.com.dev.appclientes.R;

public class AdicionarClientelFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;
    TextView textView;

    EditText editNomeCompleto, editTelefone, editEmail, editCep, editLogradouro, editComplemento, editNumero, editBairro, editCidade, editEstado, editPais;

    CheckBox chkTermosDeUso;

    Button btnCancelar, btnSalvar;


    public AdicionarClientelFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.adicionar_cliente, container, false);

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

    }

    public void eventoButton(){

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}