package br.com.dev.appclientes.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import java.util.ArrayList;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.model.Cliente;

public class ClienteAdapter extends ArrayAdapter<Cliente> implements View.OnClickListener{

    Context contexto;
    ArrayList<Cliente> dataSet;
    ClienteController clienteController;

    private static class ViewHolder{
        //Somente os componentes iremos utilizar
        TextView txtViewDescricao, txtViewDocumento,txtViewTelefone;
        ImageView imgEye, imgEdit, imgDelete;
    }

    public ClienteAdapter(ArrayList<Cliente> dataSet, Context context) {
        super(context, R.layout.fragment_listar_cliente_item, dataSet);

        this.contexto = context;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        Cliente cliente = getItem(position);

        ViewHolder dadosLinhaLista;

        if(convertView == null){
            dadosLinhaLista = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.fragment_listar_cliente_item,parent,false);

            //Inicializa os componentes
            dadosLinhaLista.txtViewDescricao = convertView.findViewById(R.id.txtViewDescricao);
            dadosLinhaLista.txtViewDocumento = convertView.findViewById(R.id.txtViewDocumento);
            dadosLinhaLista.txtViewTelefone = convertView.findViewById(R.id.txtViewTelefone);
            dadosLinhaLista.imgEye = convertView.findViewById(R.id.imgEye);
            dadosLinhaLista.imgEdit = convertView.findViewById(R.id.imgEdit);
            dadosLinhaLista.imgDelete = convertView.findViewById(R.id.imgDelete);

            convertView.setTag(dadosLinhaLista);

        }else{
            dadosLinhaLista = (ViewHolder) convertView.getTag();
        }

        //Popular os campos
        dadosLinhaLista.txtViewDocumento.setText(cliente.getDocumento());
        dadosLinhaLista.txtViewDescricao.setText(cliente.getNome());
        dadosLinhaLista.txtViewTelefone.setText(cliente.getTelefone());

        //Instanciar imagens
        dadosLinhaLista.imgEye.setOnClickListener(this);
        dadosLinhaLista.imgEye.setTag(position);

        dadosLinhaLista.imgEdit.setOnClickListener(this);
        dadosLinhaLista.imgEdit.setTag(position);

        dadosLinhaLista.imgDelete.setOnClickListener(this);
        dadosLinhaLista.imgDelete.setTag(position);



        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag(); // pega a posição clicada na lista

        Object objeto = getItem(position); // Pega o endereço da posição clicada na lista

        Cliente cliente = (Cliente) objeto; // Cast do objeto clicado para transformar em um Cliente(Polimorfismo)

        int id = v.getId();

        if(id == R.id.imgEye){
            View parent = (View) v.getParent().getParent();
            TextView txtViewDocumento = parent.findViewById(R.id.txtViewDocumento);
            txtViewDocumento.setVisibility(View.VISIBLE);

            TextView txtViewDescricao = parent.findViewById(R.id.txtViewDocumento);
            txtViewDescricao.setVisibility(View.VISIBLE);

            TextView txtViewTelefone = parent.findViewById(R.id.txtViewDocumento);
            txtViewTelefone.setVisibility(View.VISIBLE);

        }else if (id == R.id.imgEdit){
            //TODO : Criar tela para editar com nova activity
        }else if (id == R.id.imgDelete){

            FancyAlertDialog.Builder
                    .with(ClienteAdapter.this.getContext())
                    .setTitle("Deletar Cliente")
                    .setBackgroundColorRes(R.color.splash_bgr)
                    .setMessage("Deseja excluir o cliente ?")
                    .setPositiveBtnText("Sim")
                    .setPositiveBtnBackgroundRes(R.color.splash_bgr)
                    .setNegativeBtnText("Não")
                    .setNegativeBtnBackgroundRes(R.color.splash_bgr)
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                    .onPositiveClicked(dialog -> {
                        clienteController = new ClienteController(getContext());
                        try {
                            clienteController.deleteDados(ClienteDataModel.TABELA, cliente.getId());
                            dataSet.remove(position);
                            notifyDataSetChanged();
                        }catch (Exception err){
                            Toast.makeText(getContext(),"Erro ao excluir cliente ",Toast.LENGTH_LONG).show();
                            Log.e(AppUtil.TAG,"Erro ao excluir clinte [ClienteAdapter - onClick] "+err.getMessage());

                        }
                    })
                    .onNegativeClicked(dialog -> {})
                    .build()
                    .show();

        }

    }


}
