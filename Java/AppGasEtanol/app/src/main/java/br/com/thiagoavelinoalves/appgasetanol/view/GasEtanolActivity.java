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

import java.util.List;

import br.com.thiagoavelinoalves.appgasetanol.DataBase.GasEtanolDB;
import br.com.thiagoavelinoalves.appgasetanol.R;
import br.com.thiagoavelinoalves.appgasetanol.controller.CombustivelController;
import br.com.thiagoavelinoalves.appgasetanol.model.Combustivel;
import br.com.thiagoavelinoalves.appgasetanol.util.UtilAppGasEtanol;

public class GasEtanolActivity extends AppCompatActivity {

    EditText editTextGasolina;
    EditText editTextEtanol;
    TextView txtResultado;
    Button btnCalcular;
    Button btnSalvar;
    Button btnLimpar;
    Button btnFinalizar;
    Combustivel gasolina = new Combustivel();
    Combustivel etanol = new Combustivel();
    List<Combustivel> listaDeDados;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gasetanol);

        CombustivelController combustivelController = new CombustivelController(GasEtanolActivity.this);
        listaDeDados = combustivelController.getGerarDados();


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
                    gasolina.setPrecoCombustivel(Double.parseDouble(editTextGasolina.getText().toString()));
                    etanol.setPrecoCombustivel(Double.parseDouble(editTextEtanol.getText().toString()));

                    txtResultado.setText(UtilAppGasEtanol.CalcularMelhorOpcao(gasolina.getPrecoCombustivel(),etanol.getPrecoCombustivel()));
                    btnSalvar.setEnabled(true);
                    btnLimpar.setEnabled(true);
                } else {
                    Toast.makeText(GasEtanolActivity.this, "Por favor, verifice os dados informados", Toast.LENGTH_LONG).show();
                    btnSalvar.setEnabled(false);
                    btnLimpar.setEnabled(true);
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gasolina.setNomeCombustivel("GASOLINA");
                etanol.setNomeCombustivel("ETANOL");

                if (txtResultado.getText().toString().indexOf("ETANOL") < 0){
                    gasolina.setRecomendacao(txtResultado.getText().toString());
                    combustivelController.salvar(gasolina);
                }else{
                    etanol.setRecomendacao(txtResultado.getText().toString());
                    combustivelController.salvar(etanol);
                }


                Toast.makeText(GasEtanolActivity.this, "Dados salvos com sucesso", Toast.LENGTH_LONG).show();
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextGasolina.setText("");
                editTextEtanol.setText("");
                txtResultado.setText("");
                btnSalvar.setEnabled(false);
                Toast.makeText(GasEtanolActivity.this, "Dados apagados com sucesso", Toast.LENGTH_LONG).show();
                btnLimpar.setEnabled(false);

                combustivelController.limpar();
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
