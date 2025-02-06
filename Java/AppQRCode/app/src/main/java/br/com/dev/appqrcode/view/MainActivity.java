package br.com.dev.appqrcode.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.com.dev.appqrcode.R;


public class MainActivity extends AppCompatActivity {

    EditText editQrCode;
    Button btnGerarQRCOde;
    Button btnLimparQRCode;
    ImageView imgQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponentes();

        btnGerarQRCOde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editQrCode.getText().toString().isEmpty()){
                    editQrCode.setError("*");
                    editQrCode.requestFocus();
                    Toast.makeText(MainActivity.this,"Favor informar um conteúdo...", Toast.LENGTH_LONG).show();
                }else{
                    gerarQRCode(editQrCode.getText().toString());
                }

            }
        });

        btnLimparQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editQrCode.setText("");
                imgQRCode.setImageDrawable(null);
            }
        });


    }

    private void initComponentes() {

        editQrCode = findViewById(R.id.editQrCode);
        btnGerarQRCOde = findViewById(R.id.btnGerarQRCOde);
        btnLimparQRCode = findViewById(R.id.btnLimparQRCode);
        imgQRCode = findViewById(R.id.imgQRCode);

    }


    private void gerarQRCode(String qrcode) {

        QRCodeWriter qrCode = new QRCodeWriter();

        try{

            BitMatrix bitMatrix = qrCode.encode(qrcode, BarcodeFormat.QR_CODE, 196,196);

            int largura = bitMatrix.getWidth();
            int altura = bitMatrix.getHeight();;

            Bitmap bitmap = Bitmap.createBitmap(largura, altura, Bitmap.Config.RGB_565);

            for(int x = 0; x < largura; x++){

                for(int y = 0; y < altura; y++){

                    bitmap.setPixel(x,y, bitMatrix.get(x,y) ? Color.BLACK : Color.WHITE);

                }
            }


            imgQRCode.setImageBitmap(bitmap);

        }catch(WriterException w){
            Log.e("AppQRCode",w.getMessage());
        }
    }

}