package br.com.dev.appgeolocalizacao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;

    TextView txtValorLatitude, txtValorLongitude;

    double latitude, longitude;

    LocationManager locationManager;


    //Buscar Manifest android
    //Criando um vetor com as permissoes
    String[] permissoesRequiridas = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final int APP_PERMISSOES_ID = 2025;

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

        txtValorLatitude = findViewById(R.id.txtValorLatitude);
        txtValorLongitude = findViewById(R.id.txtValorLongitude);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Conferir serviços disponíveis via LocationManager
        locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);

        //Verificar se o serviço GPS_PROVIDER está ativado
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            obterCoordenadas();
        }else{
            latitude = 0.00;
            longitude = 0.00;

            txtValorLatitude.setText(String.valueOf(latitude));
            txtValorLongitude.setText(String.valueOf(longitude));

            Toast.makeText(MainActivity.this,"GPS Não ativado",Toast.LENGTH_SHORT).show();
        }

    }

    private void obterCoordenadas() {

        if(requestPermissaoLocalizacao()){
            getUltimaLocalizacao();
        }else{
            Toast.makeText(MainActivity.this,"Não foi possível obter as coordenadas",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean requestPermissaoLocalizacao() {

        List<String> permissoesNegadas = new ArrayList<>();

        int verificarPermissoes;

        for (String permissoes: this.permissoesRequiridas) {

            verificarPermissoes = ContextCompat.checkSelfPermission(MainActivity.this, permissoes);

            if(verificarPermissoes != PackageManager.PERMISSION_GRANTED){
                permissoesNegadas.add(permissoes);
            }
        }

        if(!permissoesNegadas.isEmpty()){

            ActivityCompat.requestPermissions(MainActivity.this, permissoesNegadas.toArray(new String[permissoesNegadas.size()]), APP_PERMISSOES_ID);

            return false;
        }else{
            return true;
        }

    }

    private void getUltimaLocalizacao() {

        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        boolean coordenadasOK;

        if(location != null){
            //GeoPoint
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            coordenadasOK = true;
            Toast.makeText(MainActivity.this,"Coordenadas obtidas com sucesso",Toast.LENGTH_LONG).show();

        }else{
            latitude = 0.00;
            longitude = 0.00;
            coordenadasOK = false;
            Toast.makeText(MainActivity.this,"Não foi possível obter as coordenadas",Toast.LENGTH_LONG).show();
        }

        txtValorLatitude.setText(formatarGeoPoint(latitude));
        txtValorLongitude.setText(formatarGeoPoint(longitude));

    }

    private String formatarGeoPoint(double valor){

        DecimalFormat decimalFormat = new DecimalFormat("#.#####");

        return decimalFormat.format(valor);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        LatLng localizacaoCeluar = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(localizacaoCeluar).title("Seu celular está aqui !"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacaoCeluar));
    }
}