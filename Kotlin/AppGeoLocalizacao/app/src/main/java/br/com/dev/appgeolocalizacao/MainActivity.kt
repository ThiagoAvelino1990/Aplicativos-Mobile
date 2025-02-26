package br.com.dev.appgeolocalizacao

import android.Manifest
import android.icu.text.DecimalFormat
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    var permissoesRequeridas = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun obterCoordenadas(){

        if(requestPermissaoLocalizacao())
            Toast.makeText(this, "Coordenadas obtidas com sucesso", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "Não foi possível obter as coordenadas", Toast.LENGTH_LONG).show()

    }

    private fun requestPermissaoLocalizacao() : Boolean{

        var permissoesNegadas : MutableList<String> = ArrayList()

        return true
    }

    private fun getUltimaLocalizacao() : Boolean{
        return true
    }

    private fun formatarGeoPoint(valor: Double) : String?{

        var decimalFormat = DecimalFormat("#.#####")

        return decimalFormat.format(valor)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.uiSettings.setZoomControlsEnabled(true)
        mMap.setMinZoomPreference(5.5f)
    }
}