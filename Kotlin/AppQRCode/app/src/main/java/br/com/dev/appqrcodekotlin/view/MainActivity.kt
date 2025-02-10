package br.com.dev.appqrcodekotlin.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.dev.appqrcodekotlin.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {


    var editQrCode: EditText? = null
    var btnGerarQRCOde: Button? = null
    var imgQRCode: ImageView? = null
    var btnLimparQRCode: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponentes()

        btnGerarQRCOde!!.setOnClickListener{

            if(TextUtils.isEmpty(editQrCode!!.text.toString())){
                editQrCode!!.error = "*"
                editQrCode!!.requestFocus()
            }else{
                gerarQRCode(editQrCode!!.text.toString())
            }
        }


        btnLimparQRCode!!.setOnClickListener{

            editQrCode!!.text = null

            imgQRCode!!.setImageBitmap(null)


        }


    }

    private fun gerarQRCode(conteudo: String) {

        val qrCode = QRCodeWriter() // Instanciando o objeto. Em Java: QRCodeWriter qrCode = new QRCodeWriter();

        try{

            val bitMatrix = qrCode.encode(conteudo, BarcodeFormat.QR_CODE, 196, 196)

            val tamanho = bitMatrix.width
            val altura = bitMatrix.height

            val bitmap = Bitmap.createBitmap(tamanho, altura, Bitmap.Config.RGB_565)

            for(x in 0 until tamanho){
                for(y in 0 until altura){
                    bitmap.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }

            imgQRCode!!.setImageBitmap(bitmap)

        }catch(e: WriterException){
            Log.e("AppQRCode", e!!.message.toString())
        }
    }

    private fun initComponentes() {
        editQrCode = findViewById(R.id.editQrCode)
        btnGerarQRCOde = findViewById(R.id.btnGerarQRCOde)
        imgQRCode = findViewById(R.id.imgQRCode)
        btnLimparQRCode = findViewById(R.id.btnLimparQRCode)
    }
}