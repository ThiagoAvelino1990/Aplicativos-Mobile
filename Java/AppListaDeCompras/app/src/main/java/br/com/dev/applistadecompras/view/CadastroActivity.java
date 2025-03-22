package br.com.dev.applistadecompras.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import br.com.dev.applistadecompras.R;

public class CadastroActivity extends AppCompatActivity {

    TextView txtCadastroNovoUsuario;
    EditText editNomeCompleto, editCadastroEmail, editSenha, editConfirmarSenha;
    Button btnConfirmarCadastro, btnVoltarCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        initLayout();
        btnCancelarCadastro(btnVoltarCadastro);
    }

    private void initLayout() {

        txtCadastroNovoUsuario = findViewById(R.id.txtCadastroNovoUsuario);

        editNomeCompleto = findViewById(R.id.editNomeCompleto);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editLoginSenha);

        btnConfirmarCadastro = findViewById(R.id.btnConfirmarCadastro);
        btnVoltarCadastro = findViewById(R.id.btnVoltarCadastro);

    }


    public void btnCancelarCadastro(View view) {
        btnVoltarCadastro.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                FancyAlertDialog.Builder
                        .with(CadastroActivity.this)
                        .setTitle("Cancelar Cadastro")
                        .setBackgroundColor(Color.parseColor("#303F9F"))  // for @ColorRes use setBackgroundColorRes(R.color.colorvalue)
                        .setMessage("Deseja Cancelar o cadastro ?")
                        .setNegativeBtnText("Não")
                        .setPositiveBtnBackground(Color.parseColor("#FF4081"))  // for @ColorRes use setPositiveBtnBackgroundRes(R.color.colorvalue)
                        .setPositiveBtnText("Sim")
                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  // for @ColorRes use setNegativeBtnBackgroundRes(R.color.colorvalue)
                        .setAnimation(Animation.POP)
                        .isCancellable(true)
                        .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                        .onPositiveClicked(dialog -> {
                            intent = new Intent(CadastroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();})
                        .onNegativeClicked(dialog -> closeContextMenu())
                        .build()
                        .show();
            }
        });
    }
}