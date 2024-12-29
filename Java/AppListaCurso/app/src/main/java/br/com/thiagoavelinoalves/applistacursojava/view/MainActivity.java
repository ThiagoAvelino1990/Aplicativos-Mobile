package br.com.thiagoavelinoalves.applistacursojava.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import br.com.thiagoavelinoalves.applistacursojava.R;
import br.com.thiagoavelinoalves.applistacursojava.controller.CursoController;
import br.com.thiagoavelinoalves.applistacursojava.controller.PessoaControler;
import br.com.thiagoavelinoalves.applistacursojava.model.Curso;
import br.com.thiagoavelinoalves.applistacursojava.model.Pessoa;
import br.com.thiagoavelinoalves.applistacursojava.util.FormataDadosUtil;

public class MainActivity extends AppCompatActivity {

    EditText editTextCpf;
    EditText editTextNome;
    EditText editTextSobrenome;
    EditText editTextTelefone;
    EditText editTextEmail;
    Button btnSalvar;
    Button btnLimpar;
    Button btnFinalizar;
    Spinner spinner;
    boolean dadosOk = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Pessoa pessoa = new Pessoa();
        PessoaControler pessoaControler = new PessoaControler(MainActivity.this);
        pessoaControler.buscar(pessoa);// Irá buscar os dados salvos no arquivo sharedpreferences

        List<String> listaDeCursos;

        CursoController cursoController = new CursoController(MainActivity.this);
        listaDeCursos = cursoController.retornarDadosSpinner();

        FormataDadosUtil validador = new FormataDadosUtil();

        editTextCpf = findViewById(R.id.edit_txt_cpf);
        editTextNome = findViewById(R.id.edit_txt_nome);
        editTextSobrenome = findViewById(R.id.edit_txt_sobrenome);
        editTextTelefone = findViewById(R.id.edit_txt_telefone);
        editTextEmail = findViewById(R.id.edit_txt_email);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnLimpar = findViewById(R.id.btn_limpar);
        btnFinalizar = findViewById(R.id.btn_finalizar);
        spinner = findViewById(R.id.spinner);

        // Adapter
        // Layout
        // Injetar o Adapter ao Spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                cursoController.retornarDadosSpinner());

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        spinner.setAdapter(adapter);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextNome.getText().toString().isEmpty() ||
                    editTextSobrenome.getText().toString().isEmpty()||
                    (!FormataDadosUtil.validarTelefone(editTextTelefone.getText().toString()))||
                    (!FormataDadosUtil.validarEmail(editTextEmail.getText().toString()))||
                    editTextCpf.getText().toString().isEmpty()){

                    dadosOk = false;
                }



                if(!dadosOk){
                    Toast.makeText(MainActivity.this,"Favor verificar os campos",Toast.LENGTH_LONG).show();
                }else{

                    pessoa.setCpf(Long.parseLong(editTextCpf.getText().toString()));
                    pessoa.setNome(editTextNome.getText().toString());
                    pessoa.setSobrenome(editTextSobrenome.getText().toString());
                    pessoa.setTelefone(editTextTelefone.getText().toString());
                    pessoa.setEmail(editTextEmail.getText().toString());
                    pessoa.setIdCursoPessoa(spinner.getSelectedItemPosition());

                    pessoaControler.salvar(pessoa, MainActivity.this);

                    Toast.makeText(MainActivity.this,"Dados salvos com sucesso",Toast.LENGTH_LONG).show();
                }


            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNome.setText("");
                editTextSobrenome.setText("");
                editTextTelefone.setText("");
                editTextEmail.setText("");
                editTextCpf.setText("");
                spinner.setSelection(0);

                pessoaControler.limpar(MainActivity.this);

                Toast.makeText(MainActivity.this,"Dados apagados com sucesso",Toast.LENGTH_LONG).show();

            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"App encerrado",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}