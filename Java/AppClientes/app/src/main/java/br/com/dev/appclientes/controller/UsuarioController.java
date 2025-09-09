package br.com.dev.appclientes.controller;

import static io.realm.Realm.getApplicationContext;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.datamodel.UsuarioDataModel;
import br.com.dev.appclientes.datasource.AppDataBase;
import br.com.dev.appclientes.model.Usuario;
import br.com.dev.appclientes.service.AtualizarDadosTask;
import br.com.dev.appclientes.service.DeletarDadosTask;
import br.com.dev.appclientes.service.InserirDadosTask;

public class UsuarioController extends AppDataBase implements ICRUD<Usuario> {

    private Context context;

    ContentValues values;

    public UsuarioController(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean insertObject(Usuario obj) {

        values = new ContentValues();

        if(obj.isPessoaFisica()){
            values.put(UsuarioDataModel.IDTIPOPESSOA, "PJ");
        }else{
            values.put(UsuarioDataModel.IDTIPOPESSOA, "PF");
        }
        values.put(UsuarioDataModel.NOME,obj.getNome());
        values.put(UsuarioDataModel.CPFCNPJ, obj.getCpfCnpj());
        values.put(UsuarioDataModel.LOGRADOURO, obj.getLogradouro());
        values.put(UsuarioDataModel.COMPLEMENTO, obj.getComplemento());
        values.put(UsuarioDataModel.EMAIL, obj.getEmail());
        values.put(UsuarioDataModel.SENHA, obj.getSenha());
        values.put(UsuarioDataModel.TELEFONE, obj.getTelefone());
        values.put(UsuarioDataModel.DATACINLUSAO, AppUtil.getDataFormat());

        values.put(UsuarioDataModel.ATUALIZARSENHA, 0);

        if(obj.isChkLembrarSenha()) {
            values.put(UsuarioDataModel.LEMBRARSENHA, 1);
        }else{
            values.put(UsuarioDataModel.LEMBRARSENHA, 0);
        }

        //Chamando função assícrona
        new InserirDadosTask(context, values).execute("usuario");

        return insertDados(UsuarioDataModel.TABELA, values);
    }

    @Override
    public boolean deleteObject(Usuario obj) {

        //Chamando função assícrona
        new DeletarDadosTask(context).execute("usuario", String.valueOf(obj.getId()));


        return deleteDados(UsuarioDataModel.TABELA, obj.getId());
    }

    @Override
    public boolean updateObject(Usuario obj) {

        values = new ContentValues();

        values.put(UsuarioDataModel.ID, obj.getId());
        values.put(UsuarioDataModel.SENHA, obj.getSenha());
        values.put(UsuarioDataModel.ATUALIZARSENHA, obj.getAtualizaSenha());
        values.put(UsuarioDataModel.LEMBRARSENHA, obj.isChkLembrarSenha());
        values.put(UsuarioDataModel.DATAALTERACAO, AppUtil.getDataFormat());

        //Chamando função assíncrona
        new AtualizarDadosTask(context, values).execute("usuario");

        return updateDados(UsuarioDataModel.TABELA, values);
    }

    @Override
    public List<Usuario> readObject() {
        return getAllUsuarios(UsuarioDataModel.TABELA);
    }

    @Override
    public List<Usuario> readObjectById(int id) {
        return getUsuarioByID(UsuarioDataModel.TABELA, id);
    }

    public List<Usuario> readObjectByEmail(String email){

        return getUsuarioByEmail(UsuarioDataModel.TABELA, email);
    }

    public int readObjetcIdByEmail(String email){

        return getIdUsuarioByEmail(UsuarioDataModel.TABELA, email);
    }

    public int readObjetByTelefone(String telefone){

        return getIdUsuarioByTelefone(UsuarioDataModel.TABELA, telefone);
    }

}
