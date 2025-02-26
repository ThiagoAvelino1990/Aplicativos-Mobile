package br.com.dev.appgeolocalizacao

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.DecimalFormat
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var txtValorLatitute : TextView? = null
    var txtValorLongitute : TextView? = null

    var latitude : Double = 0.00
    var longitute : Double = 0.00

    var locationManager : LocationManager? = null

    //Buscar Manifest android
    //Criando um vetor com as permissoes
    var permissoesRequeridas = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    val APP_PERMISSOES_ID : Int = 2025;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtValorLatitute = findViewById(R.id.txtValorLatitude)
        txtValorLongitute = findViewById(R.id.txtValorLongitude)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MainActivity)

        //Conferir serviços disponíveis via LocationManager
        locationManager = application.getSystemService(LOCATION_SERVICE) as LocationManager

        if(locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            obterCoordenadas()
        }else{
            latitude = 0.00
            longitute = 0.00

            txtValorLatitute!!.setText(latitude.toString())
            txtValorLongitute!!.setText(longitute.toString())

            Toast.makeText(this@MainActivity,"GPS Não ativado",Toast.LENGTH_SHORT).show();

        }


    }

    private fun obterCoordenadas(){

        if(requestPermissaoLocalizacao())
            Toast.makeText(this@MainActivity, "Coordenadas obtidas com sucesso", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this@MainActivity, "Não foi possível obter as coordenadas", Toast.LENGTH_LONG).show()

    }

    private fun requestPermissaoLocalizacao() : Boolean{

        var permissoesNegadas : MutableList<String> = ArrayList()
        var verificarPermissoes : Int

        for(permissoes in permissoesRequeridas){
            verificarPermissoes = ContextCompat.checkSelfPermission(this@MainActivity,permissoes)

            if(verificarPermissoes != PackageManager.PERMISSION_GRANTED){
                permissoesNegadas.add(permissoes)
            }

        }

        return if(!permissoesNegadas.isEmpty()){
            ActivityCompat.requestPermissions(this@MainActivity, permissoesNegadas.toTypedArray(), APP_PERMISSOES_ID)
            getUltimaLocalizacao()
        }else{
            getUltimaLocalizacao()
        }


    }

    private fun getUltimaLocalizacao() : Boolean{

        @SuppressLint("MissingPermission")
        val location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        var coordenadasOk : Boolean

        if(location != null){
            latitude = location.latitude
            longitute = location.longitude
            coordenadasOk = true
        }else{
            latitude = 0.00
            longitute = 0.00
            coordenadasOk = false
        }

        txtValorLatitute!!.setText(formatarGeoPoint(latitude))
        txtValorLongitute!!.setText(formatarGeoPoint(longitute))

        return coordenadasOk
    }

    private fun formatarGeoPoint(valor: Double) : String?{

        var decimalFormat = DecimalFormat("#.#####")

        return decimalFormat.format(valor)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val localizacaoCelular = LatLng(latitude, longitute)
        mMap.addMarker(MarkerOptions().position(localizacaoCelular).title("Seu celular está aqui"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacaoCelular))
        mMap.uiSettings.setZoomControlsEnabled(true)
        mMap.setMinZoomPreference(5.5f)
    }
}