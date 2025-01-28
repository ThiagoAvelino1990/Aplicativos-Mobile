package br.com.dev.appclientes.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.model.Cliente;

public class ListarClientesFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;

    EditText editPesquisarCliente;

    ListView listViewCliente;

    List<Cliente> clienteList;

    List<String> clienteListString;

    ArrayAdapter<String> clienteAdapter;

    ArrayList<HashMap<String, String>> filtroClientes;

    ClienteController clienteController;

    Cliente cliente;

    public ListarClientesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_listar_cliente, container, false);

        TextView txtTitulo = view.findViewById(R.id.txtViewCliente);

        //txtTitulo.setText(R.string.listar_clientes_fragment);

        // Trocar a cor da propriedade texto (setTextColor)
        txtTitulo.setTextColor(ColorStateList.valueOf(Color.CYAN));
        clienteController = new ClienteController(getContext());

        //Casting de ListView
        listViewCliente = (ListView) view.findViewById(R.id.listViewCliente);

        editPesquisarCliente = view.findViewById(R.id.editPesquisarCliente);

        clienteList = clienteController.readObject();

        clienteListString = new ArrayList<>();

        //TODO: Implementar regra de negócio na controladora
        for (Cliente objCliente: clienteList) {
            clienteListString.add(objCliente.getId()+" "+objCliente.getNome());
        }

        clienteAdapter = new ArrayAdapter<>(getContext(), R.layout.fragment_listar_cliente_item, R.id.txtViewItemCliente, clienteListString);

        listViewCliente.setAdapter(clienteAdapter);


        return view;
    }


}