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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.adapter.ClienteAdapter;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilSharedPreferences;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.controller.UsuarioController;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.model.Cliente;

public class ListarClientesCardsFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;

    EditText editPesquisarCliente;

    ListView listViewCliente;

    ArrayList<Cliente> clienteList;

    //List<String> clienteListString;

    //ArrayAdapter<String> clienteAdapter;

    //ArrayList<HashMap<String, String>> filtroClientes;

    ClienteController clienteController;

    ClienteAdapter clienteAdapter;

    UsuarioController usuarioController;

    public ListarClientesCardsFragment() {
    }

    @Override
    public void onResume(){
        super.onResume();
        atualizarListaClientes();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        //Inflar o layout primeiro e depois inicializar os componentes
        view =  inflater.inflate(R.layout.fragment_listar_cliente_cards, container, false);

        initComponentes();

        //clienteListString = clienteController.getAllClientesListView();

        /*editPesquisarCliente.addTextChangedListener(new TextWatcher() {
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
        });*/

        return view;
    }

    public void initComponentes(){
        editPesquisarCliente = view.findViewById(R.id.editPesquisarCliente);
        clienteController = new ClienteController(getContext());
        //Casting de ListView
        listViewCliente = (ListView) view.findViewById(R.id.listViewCliente);

        usuarioController = new UsuarioController(getContext());
    }

    private void atualizarListaClientes() {

        int idUser = AppUtilSharedPreferences.getIdUserByPref(getContext());

        if (idUser > -1) {
            try{
                clienteList = clienteController.getallClientesByIdUser(idUser);

                /*configurar o adpter*/
                clienteAdapter = new ClienteAdapter(clienteList, getContext());

                /*Injetar o adpter no listView*/
                listViewCliente.setAdapter(clienteAdapter);
            }catch (Exception err){
                Log.e(AppUtil.TAG,"Erro ao processar os dados [ListarClientesCardsFragment - atualizarListaClientes] "+err.getMessage());
                Toast.makeText(getContext(),"Erro ao processar os dados.",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getContext(),"Não foi possível encontrar o usuario para atualizar a lista.",Toast.LENGTH_LONG).show();
        }

    }


}