package br.com.dev.appclientes.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import java.util.ArrayList;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.datamodel.ClienteDataModel;
import br.com.dev.appclientes.model.Cliente;
import br.com.dev.appclientes.view.AlterarClienteCardsFragment;

/**
 * Adapter para se conectar com a lista de clientes com a interface gráfica
 * Controla exibições dos dados e também os botões de ação
 */
public class ClienteAdapter extends ArrayAdapter<Cliente> implements View.OnClickListener{
    //Contexto da Activity/Fragment onde o adapter será usado
    Context contexto;

    //Array com a lista de clites
    ArrayList<Cliente> dataSet;

    //Controladora de clientes
    ClienteController clienteController;

    /**
     * Classe interna ViewHolder para otimização e performance
     */
    private static class ViewHolder{
        //Somente os componentes iremos utilizar
        TextView txtViewDescricao, txtViewDocumento,txtViewTelefone;
        ImageView imgEye, imgEdit, imgDelete;
    }

    //Construtor da classe que recebe os dados da Acitivty e o contexto
    public ClienteAdapter(ArrayList<Cliente> dataSet, Context context) {
        super(context, R.layout.fragment_listar_cliente_item, dataSet);

        this.contexto = context;
        this.dataSet = dataSet;

    }

    /**
     *
     * @param position A posição do item dentro do conjunto de dados do adaptador do item cuja visualização queremos.
     * @param convertView A visualização antiga deve ser reutilizada, se possível. Observação: Você deve verificar se esta visualização
     *                    não é nula e é de um tipo apropriado antes de usar. Se não for possível converter
     *                    esta visualização para exibir os dados corretos, este método pode criar uma nova visualização.
     *                    Listas heterogêneas podem especificar seu número de tipos de visualização, de modo que esta visualização seja
     *                    sempre do tipo correto (see {@link #getViewTypeCount()} and {@link #getItemViewType(int)}).
     * @param parent O pai ao qual est[a view está eventuamente anexada
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        //Coleta o cliente selecionado na lista
        Cliente cliente = getItem(position);
        ViewHolder dadosLinhaLista;

        //Se não existir uma view para reutilizar, será criado uma nova
        if(convertView == null){
            dadosLinhaLista = new ViewHolder();

            //Inflar o layout XML e transformar em objeto View
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_listar_cliente_item,parent,false);

            //Inicializa os componentes. e liga os elementos da tela ao ViewHolder
            dadosLinhaLista.txtViewDescricao = convertView.findViewById(R.id.txtViewDescricao);
            dadosLinhaLista.txtViewDocumento = convertView.findViewById(R.id.txtViewDocumento);
            dadosLinhaLista.txtViewTelefone = convertView.findViewById(R.id.txtViewTelefone);
            dadosLinhaLista.imgEye = convertView.findViewById(R.id.imgEye);
            dadosLinhaLista.imgEdit = convertView.findViewById(R.id.imgEdit);
            dadosLinhaLista.imgDelete = convertView.findViewById(R.id.imgDelete);

            //Salvar o ViewHolder dentro da View para reaproveitamento
            convertView.setTag(dadosLinhaLista);

        }else{
            //Se a view fora criada, então apenas recupera o ViewHolder
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

        //instancia a controller com acesso ao banco
        clienteController = new ClienteController(getContext());


        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag(); // pega a posição clicada na lista

        Object objeto = getItem(position); // Pega o endereço da posição clicada na lista

        Cliente cliente = (Cliente) objeto; // Cast do objeto clicado para transformar em um Cliente(Polimorfismo)

        int id = v.getId(); //Identifica qual botão foi clicado

        // Esconde os campos
        if(id == R.id.imgEye){
            View parent = (View) v.getParent().getParent();
            TextView txtViewDocumento = parent.findViewById(R.id.txtViewDocumento);
            txtViewDocumento.setVisibility(View.VISIBLE);

            TextView txtViewDescricao = parent.findViewById(R.id.txtViewDescricao);
            txtViewDescricao.setVisibility(View.VISIBLE);

            TextView txtViewTelefone = parent.findViewById(R.id.txtViewTelefone);
            txtViewTelefone.setVisibility(View.VISIBLE);

            ImageView imgEye = parent.findViewById(R.id.imgEye);
            imgEye.setImageResource(R.drawable.eye_hide);

            //Botão abre o framento para poder editar os campos do cliente
        }else if (id == R.id.imgEdit){
           AlterarClienteCardsFragment alterarClienteCardsFragment = new AlterarClienteCardsFragment();

           //Passar os dados via Bundle
            Bundle bundle = new Bundle();
            bundle.putSerializable("cliente", cliente);
            alterarClienteCardsFragment.setArguments(bundle);

            //Troca de tela(fragment)
            ((AppCompatActivity) contexto).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_fragment,alterarClienteCardsFragment)
                    .addToBackStack(null)
                    .commit();

            //Alerta para exclusão dos dados do cliente
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
                        try {
                            clienteController.deleteDados(ClienteDataModel.TABELA, cliente.getId());
                            dataSet.remove(position);
                            notifyDataSetChanged();
                        }catch (Exception err){
                            Toast.makeText(getContext(),"Erro ao excluir cliente ",Toast.LENGTH_LONG).show();
                            Log.e(AppUtil.TAG,"Erro ao excluir clinte [ClienteAdapter - onClick] "+err.getMessage());

                        }
                    })
                    .onNegativeClicked(dialog -> {
                        Toast.makeText(getContext(),"Operação cancelada",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .build()
                    .show();

        }

    }


}
