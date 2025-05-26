package br.com.dev.appclientes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.model.Cliente;

public class ClienteAdapter extends ArrayAdapter<Cliente> implements View.OnClickListener{

    Context contexto;
    ArrayList<Cliente> dataSet;
    ListView listView;
    ClienteController clienteController;

    private static class ViewHolder{
        //Somente os componentes iremos utilizar
        TextView txtViewDescricao, txtViewDocumento,txtViewTelefone;
    }

    public ClienteAdapter(ArrayList<Cliente> dataSet, Context context) {
        super(context, R.layout.fragment_listar_cliente_item, dataSet);

        this.contexto = context;

    }


    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag(); // pega a posição clicada na lista

        Object objeto = getItem(position); // Pega o endereço da posição clicada na lista

        Cliente cliente = (Cliente) objeto; // Cast do objeto clicado para transformar em um Cliente(Polimorfismo)

       /* switch ((v.getId())){
            Snackbar.make(v,"Click...",Snackbar.LENGTH_LONG).setAction("No action",null).show();

            break;
        }*/


    }

    @NonNull
    @Override
    public View getView(int position, View dataSet, @NonNull ViewGroup parent){

        Cliente cliente = getItem(position);

        ViewHolder dadosLinhaLista;

        if(dataSet == null){
            dadosLinhaLista = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            dataSet = inflater.inflate(R.layout.fragment_listar_cliente_item,parent,false);

            //Inicializa os componentes
            dadosLinhaLista.txtViewDescricao = dataSet.findViewById(R.id.txtViewDescricao);
            dadosLinhaLista.txtViewDocumento = dataSet.findViewById(R.id.txtViewDocumento);
            dadosLinhaLista.txtViewTelefone = dataSet.findViewById(R.id.txtViewTelefone);

            dataSet.setTag(dadosLinhaLista);

        }else{
            dadosLinhaLista = (ViewHolder) dataSet.getTag();
        }

        //Popular os campos
        dadosLinhaLista.txtViewDocumento.setText(cliente.getDocumento());
        dadosLinhaLista.txtViewDescricao.setText(cliente.getNome());
        dadosLinhaLista.txtViewTelefone.setText(cliente.getTelefone());

        return dataSet;
    }

}
