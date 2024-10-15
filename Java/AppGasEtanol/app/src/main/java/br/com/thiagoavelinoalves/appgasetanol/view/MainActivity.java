package br.com.thiagoavelinoalves.appgasetanol.view;

import android.os.Bundle;
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

import br.com.thiagoavelinoalves.appgasetanol.R;
import br.com.thiagoavelinoalves.appgasetanol.controller.CursoController;
import br.com.thiagoavelinoalves.appgasetanol.controller.PessoaControler;
import br.com.thiagoavelinoalves.appgasetanol.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextSobrenome;
    EditText editTextTelefone;
    Button btnSalvar;
    Button btnLimpar;
    Button btnFinalizar;
    Spinner spinner;


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

        CursoController cursoController = new CursoController();
        listaDeCursos = cursoController.retornarDadosSpinner();



        editTextNome = findViewById(R.id.edit_txt_nome);
        editTextSobrenome = findViewById(R.id.edit_txt_sobrenome);
        editTextTelefone = findViewById(R.id.edit_txt_telefone);
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

                pessoa.setNome(editTextNome.getText().toString());
                pessoa.setSobrenome(editTextSobrenome.getText().toString());
                pessoa.setTelefone(editTextTelefone.getText().toString());
                
                if (spinner.getSelectedItemPosition() == 0 ||
                    editTextNome.getText().toString().isEmpty() ||
                    editTextSobrenome.getText().toString().isEmpty()||
                    editTextTelefone.getText().toString().isEmpty()){

                    Toast.makeText(MainActivity.this,"Preencha todos os campos",Toast.LENGTH_LONG).show();

                }else{
                    pessoaControler.salvar(pessoa);

                    Toast.makeText(MainActivity.this,"Dados Salvos Com sucesso",Toast.LENGTH_LONG).show();
                };

            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNome.setText("");
                editTextSobrenome.setText("");
                editTextTelefone.setText("");
                spinner.setSelection(0);

                pessoaControler.limpar();

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