package br.com.thiagoavelinoalves.applistacursojava.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.thiagoavelinoalves.applistacursojava.R;
import br.com.thiagoavelinoalves.applistacursojava.controller.PessoaControler;
import br.com.thiagoavelinoalves.applistacursojava.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextSobrenome;
    EditText editTextCurso;
    EditText editTextTelefone;
    Button btnSalvar;
    Button btnLimpar;
    Button btnFinalizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Pessoa pessoa = new Pessoa();
        PessoaControler pessoaControler = new PessoaControler(MainActivity.this);
        pessoaControler.buscar(pessoa);// Irá buscar os dados salvos no arquivo sharedpreferences

        editTextNome = findViewById(R.id.edit_txt_nome);
        editTextSobrenome = findViewById(R.id.edit_txt_sobrenome);
        editTextCurso = findViewById(R.id.edit_txt_curso_desejado);
        editTextTelefone = findViewById(R.id.edit_txt_telefone);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnLimpar = findViewById(R.id.btn_limpar);
        btnFinalizar = findViewById(R.id.btn_finalizar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pessoa.setNome(editTextNome.getText().toString());
                pessoa.setNome(editTextSobrenome.getText().toString());
                pessoa.setNome(editTextTelefone.getText().toString());

                pessoaControler.salvar(pessoa);

                Toast.makeText(MainActivity.this,"Dados Salvos Com sucesso",Toast.LENGTH_LONG).show();
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNome.setText("");
                editTextSobrenome.setText("");
                editTextTelefone.setText("");

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