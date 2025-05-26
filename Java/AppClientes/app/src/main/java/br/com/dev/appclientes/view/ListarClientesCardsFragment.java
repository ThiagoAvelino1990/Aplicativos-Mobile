package br.com.dev.appclientes.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.adapter.ClienteAdapter;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.model.Cliente;

public class ListarClientesCardsFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;

    EditText editPesquisarCliente;

    ListView listViewCliente;

    ArrayList<Cliente> clienteList;

    List<String> clienteListString;

    //ArrayAdapter<String> clienteAdapter;

    ArrayList<HashMap<String, String>> filtroClientes;

    ClienteController clienteController;

    Cliente cliente;

    ClienteAdapter clienteAdapter;

    public ListarClientesCardsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_listar_cliente_cards, container, false);

        editPesquisarCliente = view.findViewById(R.id.editPesquisarCliente);
        clienteController = new ClienteController(getContext());

        //Casting de ListView
        listViewCliente = (ListView) view.findViewById(R.id.listViewCliente);

        clienteList = clienteController.getallClientesByIdUser(1);

        /*configurar o adpter*/
        clienteAdapter = new ClienteAdapter(clienteList, getContext());

        /*Injetar o adpter no listView*/
        listViewCliente.setAdapter(clienteAdapter);

        //clienteListString = clienteController.getAllClientesListView();

        editPesquisarCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                ListarClientesCardsFragment.this.clienteAdapter.getFilter().filter(charSequence);

                Log.i(AppUtil.TAG,"Valor pesquisado: "+charSequence);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }


}