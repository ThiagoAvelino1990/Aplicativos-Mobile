package br.com.dev.applistadecompras.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dev.applistadecompras.R;


public class MeusProdutosFragment extends Fragment {

    View view;

    public MeusProdutosFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_meus_produtos, container, false);

        TextView txtTitulo = view.findViewById(R.id.txtMeusProdutos);

        txtTitulo.setTextColor(ColorStateList.valueOf(Color.WHITE));
        txtTitulo.setText(R.string.txtMeusProdutos);

        return view;
    }


}
