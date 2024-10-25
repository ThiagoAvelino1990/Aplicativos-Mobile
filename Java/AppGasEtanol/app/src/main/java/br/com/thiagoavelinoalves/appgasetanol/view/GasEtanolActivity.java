package br.com.thiagoavelinoalves.appgasetanol.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.thiagoavelinoalves.appgasetanol.R;
import br.com.thiagoavelinoalves.appgasetanol.util.UtilAppGasEtanol;

public class GasEtanolActivity extends AppCompatActivity {

    EditText editTextGasolina;
    EditText editTextEtanol;
    TextView txtResultado;
    Button btnCalcular;
    Button btnSalvar;
    Button btnLimpar;
    Button btnFinalizar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gasetanol);

        editTextGasolina = findViewById(R.id.edit_txt_gasolina);
        editTextEtanol = findViewById(R.id.edit_txt_etanol);
        txtResultado = findViewById(R.id.txt_resultado);
        btnCalcular = findViewById(R.id.btn_calcular);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnLimpar = findViewById(R.id.btn_limpar);
        btnFinalizar = findViewById(R.id.btn_finalizar);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isDadosOk = true;

                if (TextUtils.isEmpty(editTextGasolina.getText())) {
                    editTextGasolina.setError(" * Campo Obrigatório");
                    editTextGasolina.requestFocus();
                    isDadosOk = false;
                }

                if (TextUtils.isEmpty(editTextEtanol.getText())) {
                    editTextEtanol.setError(" * Campo Obrigatório");
                    editTextEtanol.requestFocus();
                    isDadosOk = false;
                }

                if (isDadosOk) {
                    txtResultado.setText(UtilAppGasEtanol.CalcularMelhorOpcao(Double.parseDouble(editTextGasolina.getText().toString()),
                            Double.parseDouble(editTextEtanol.getText().toString())));
                } else {
                    Toast.makeText(GasEtanolActivity.this, "Por favor, verifice os dados informados", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GasEtanolActivity.this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextGasolina.setText("");
                editTextEtanol.setText("");
                txtResultado.setText("");

                Toast.makeText(GasEtanolActivity.this, "Dados apagados com sucesso", Toast.LENGTH_LONG).show();
            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GasEtanolActivity.this, "App encerrado", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}
