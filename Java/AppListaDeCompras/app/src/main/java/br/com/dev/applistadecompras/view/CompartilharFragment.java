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


public class CompartilharFragment extends Fragment {

    View view;

    public CompartilharFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_compartilhar, container, false);

        TextView txtTitulo = view.findViewById(R.id.txtCompartilhar);

        txtTitulo.setTextColor(ColorStateList.valueOf(Color.WHITE));
        txtTitulo.setText(R.string.txtCompartilhar);

        return view;
    }


}
