package br.com.dev.appclientes.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.BufferOverflowException;

import br.com.dev.appclientes.R;
import br.com.dev.appclientes.api.AppUtil;
import br.com.dev.appclientes.api.AppUtilSharedPreferences;
import br.com.dev.appclientes.api.AppUtilWebService;
import br.com.dev.appclientes.controller.ClienteController;
import br.com.dev.appclientes.controller.ClienteORMController;
import br.com.dev.appclientes.model.ClienteORM;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Gerenciamento dos fragmentos
    FragmentManager fragmentManager;

    // Gerenciamento do menu drawer
    NavigationView navigationView;

    // Gerenciamento do menu action bar
    Menu menu;

    // Gerenciamento de cada item do menu drawer
    MenuItem nav_preto;
    MenuItem nav_vermelho;
    MenuItem nav_azul;

    //Componentes do nav_header_main.xml
    TextView txtViewClienteNav, txtClienteNav;

    //SharedPreferences
    SharedPreferences sharedPreferences;

    //ClienteController
    ClienteController clienteControler;

    //TaskSincronizar
    SincronizarSistema task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =   findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Action Button Clicado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        task = new SincronizarSistema();

        initComponentesDeLayout();

        // drawer_Layout é o layout padrão do aplicativo
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // nav_view contém o layout do menu
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        // content_fragment usado para receber os layouts dos fragmentos
        // tela principal do aplicativo
        fragmentManager.beginTransaction().replace(R.id.content_fragment, new ListarClientesFragment()).commit();

    }

    private void initComponentesDeLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewNavHeaderMain = inflater.inflate(R.layout.nav_header_main, null);

        sharedPreferences = getSharedPreferences(AppUtilSharedPreferences.PREF_APP, MODE_PRIVATE);

        txtViewClienteNav = viewNavHeaderMain.findViewById(R.id.txtViewClienteNav);
        txtClienteNav = viewNavHeaderMain.findViewById(R.id.txtClienteNav);

        txtViewClienteNav.setText(sharedPreferences.getString("nome",null));
        txtClienteNav.setText(sharedPreferences.getString("email",null));

        clienteControler = new ClienteController(getApplicationContext());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            FancyAlertDialog.Builder
                    .with(MainActivity.this)
                    .setTitle("Sair do Aplicativo")
                    .setBackgroundColorRes(R.color.splash_bgr)
                    .setMessage("Deseja Sair do aplicativo?")
                    .setNegativeBtnText("Não")
                    .setPositiveBtnBackgroundRes(R.color.splash_bgr)
                    .setPositiveBtnText("Sim")
                    .setNegativeBtnBackgroundRes(R.color.splash_bgr)
                    .setAnimation(Animation.POP)
                    .isCancellable(true)
                    .setIcon(R.mipmap.ic_launcher_round, View.VISIBLE)
                    .onPositiveClicked(dialog -> {
                        finish();
                    })
                    .onNegativeClicked(dialog -> closeContextMenu())
                    .build()
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.adicionar_cliente_nav){

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new AdicionarClienteFragment()).commit();

        }else if(id == R.id.listar_clientes_nav){

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ListarClientesFragment()).commit();

        }else if(id == R.id.listar_clientes_cards_nav){

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new ListarClientesCardsFragment()).commit();

        }else if(id == R.id.adicionar_cliente_cards_nav){

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new AdicionarClienteCardsFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SincronizarSistema extends AsyncTask<String, String, String>{

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        HttpURLConnection conn;
        URL url = null;
        Uri.Builder builder;

        public SincronizarSistema(){
            this.builder = new Uri.Builder();

            builder.appendQueryParameter("app","Cliente");
        }

        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Sincronizando dados");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            //Montar URL com o endereço do script php
            try{
                url = new URL(AppUtilWebService.URL_WEB_SERVICE+"nomedoScript.php");//TODO:Ajustar URL correta
            }catch(MalformedURLException err){
                Log.e(AppUtil.TAG,"MalformedURLException - "+err.getMessage());
            }catch(Exception err){
                Log.e(AppUtil.TAG,"Exception - "+err.getMessage());
            }

            //Conectando com o servidor apache
            try{
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(AppUtilWebService.CONNECTION_TIMEOUT);
                conn.setReadTimeout(AppUtilWebService.READ_TIMEOUT);
                conn.setRequestMethod("POST");//"GET", "PUT", "POST", "DELETE"
                conn.setRequestProperty("chatset", "utf-8");

                conn.setDoInput(true);
                conn.setDoOutput(true);

                String query = builder.build().getEncodedQuery();

                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();

                conn.connect();

            }catch(IOException err){
                Log.e(AppUtil.TAG,"IOException -"+err.getMessage());
            }catch(Exception err){
                Log.e(AppUtil.TAG,"Exceptio -"+err.getMessage());
            }

            try{
                /**
                 * 200 - OK
                 * 403 - Forbideen
                 * 404 - página não encontrada
                 * 500 - erro interno do servidor
                 */
                int response_code = conn.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = conn.getInputStream();

                    BufferedReader reader = new BufferedReader((new InputStreamReader(input)));
                    StringBuilder result = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());

                }else{
                    return "Erro de conexão";
                }

            }catch(Exception err){

            }finally {
                conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){

        }
    }

}
