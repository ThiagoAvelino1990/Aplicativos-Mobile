package br.com.dev.appclientes.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import br.com.dev.appclientes.R;

public class AdicionarClientelFragment extends Fragment {

    // Fragment - Classe responsável pela camada de VISÃO (Layout)
    View view;
    TextView textView;


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

        return view;
    }

    /**
     *
     */
    public void inicializarComponentesDeLayout(){

        textView = view.findViewById(R.id.txtAdicionarCliente);
        textView.setText(R.string.novo_cliente);

    }


}