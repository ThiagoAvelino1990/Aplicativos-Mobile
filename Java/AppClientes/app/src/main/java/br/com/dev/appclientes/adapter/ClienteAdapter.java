package br.com.dev.appclientes.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.model.Cliente;

public class ClienteAdapter extends ArrayAdapter<Cliente> implements View.OnClickListener{

    Context contexto;
    ArrayList<Cliente> dataSet;
    ListView listView;
    ClienteController clienteController;

    private static class ViewHolder{

    }

    public ClienteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"Click no Cliente",Toast.LENGTH_LONG).show();
    }
}
