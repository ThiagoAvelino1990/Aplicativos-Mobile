package br.com.dev.applistadecompras.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import br.com.dev.applistadecompras.R;
import br.com.dev.applistadecompras.controller.CategoriaController;
import br.com.dev.applistadecompras.controller.ProdutoController;
import br.com.dev.applistadecompras.model.Categoria;
import br.com.dev.applistadecompras.model.Produto;
import br.com.dev.applistadecompras.util.AppUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;

    NavigationView navigationView;

    Menu menu;
    MenuItem nav_compartilhar;
    MenuItem nav_minhas_compras;
    MenuItem nav_meus_produtos;
    MenuItem nav_sair;

    TextView txtMeusProdutos, txtMinhasCompras, txtCompartilhar;

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtMeusProdutos = findViewById(R.id.txtMeusProdutos);
        txtMinhasCompras = findViewById(R.id.txtMinhasCompras);
        txtCompartilhar = findViewById(R.id.txtCompartilhar);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_fragment, new MeusProdutosFragment()).commit();

        //Testes em categoria com o banco realm
        manterCategorias();

        //Testes em produto com banco realm
        manterPordutos();

    }

    private void manterPordutos() {
        ProdutoController produtoController=  new ProdutoController();
        Produto produto = new Produto();

        /**
         * Inclusão de registros
         */
        /*for(int i =0;i<11;i++){

            produto.setDescricao("DESC PRODUTO "+i);
            produto.setUn("KG");
            produto.setQuantidade(30+i);
            produto.setDataDeInclusao(new Date());
            produto.setPreco(22.2 + i);
            produto.setCodigoDeBarra("0123456789"+i);


            if(produtoController.insert(produto)){
                Log.i(AppUtil.TAG, "manterCategorias: "+produto.toString());
            };
        }*/

        /**
         *Exclusão de registro por ID
         */
        /*if(produtoController.deleteByID(2)){
            Log.i("db_log", "Exclusão dos dados");
        };*/

        /**
         *Exclusão de todos os registros
         */
        /*List<Produto> listaDeProdutos = produtoController.read();
        for (Produto obj: listaDeProdutos) {
            produtoController.delete(obj);
        }*/


        /**
         * Alteração dos registros
         */
        /*produto.setId(6);
        produto.setDescricao("ALTERAÇÃO PRODUTO DESC");
        produto.setDataDeInclusao(new Date());
        produto.setUn("CX");
        produto.setDataAlteracao(Apputil.getDataAtual());
        produtoController.update(produto);*/

        /**
         * Listar objetos
         */
        /*List<Produto> listaDeProdutos = produtoController.read();
        for (Produto obj: listaDeProdutos) {
            Log.i("db_log",obj.toString());
        }*/


    }

    private void manterCategorias() {
        CategoriaController categoriaController = new CategoriaController();
        Categoria categoria = new Categoria();

        /**
         * Inclusão de registros
         */
        /*for(int i =0;i<8;i++){

            categoria.setNome("TESTE CATEGORIA: "+i);

            if(categoriaController.insert(categoria)){
                Log.i("db_log", "manterCategorias: "+categoria.toString());
            };
        }*/

        /**
         *Exclusão de registro por ID
         */
        /*if(categoriaController.deleteByID(2)){
            Log.i("db_log", "Exclusão dos dados");
        };*/

        /**
         *Exclusão de todos os registros
         */
        /*List<Categoria> listaDeCategorias = categoriaController.read();
        for (Categoria obj: listaDeCategorias) {
            categoriaController.delete(obj);
        }*/


        /**
         * Alteração dos registros
         */
        /*categoria.setId(6);
        categoria.setNome("ALTERAÇÃO CATIGURIA");
        categoriaController.update(categoria);*/

        /**
         * Listar objetos
         */
        /*List<Categoria> listaDeCategorias = categoriaController.read();
        for (Categoria obj: listaDeCategorias) {
            Log.i("db_log",obj.toString());
        }*/

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // TODO: opter ID para a opção selecionada no MENU DRAWER
        if (id == R.id.nav_meus_produtos) {

            menu = navigationView.getMenu();

            nav_meus_produtos = menu.findItem(R.id.nav_meus_produtos);
            nav_meus_produtos.setTitle("[Meus Produtos]");

            nav_minhas_compras = menu.findItem(R.id.nav_minhas_compras);
            nav_minhas_compras.setTitle(R.string.txtMinhasCompras);

            nav_compartilhar = menu.findItem(R.id.nav_compartilhar);
            nav_compartilhar.setTitle(R.string.txtCompartilhar);

            nav_sair = menu.findItem(R.id.nav_sair);
            nav_sair.setTitle(R.string.txtSairDoApp);

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new MeusProdutosFragment()).commit();

        } else if (id == R.id.nav_minhas_compras) {

            menu = navigationView.getMenu();

            nav_meus_produtos = menu.findItem(R.id.nav_meus_produtos);
            nav_meus_produtos.setTitle(R.string.txtMeusProdutos);

            nav_minhas_compras = menu.findItem(R.id.nav_minhas_compras);
            nav_minhas_compras.setTitle("[Minhas Compras]");

            nav_compartilhar = menu.findItem(R.id.nav_compartilhar);
            nav_compartilhar.setTitle(R.string.txtCompartilhar);

            nav_sair = menu.findItem(R.id.nav_sair);
            nav_sair.setTitle(R.string.txtSairDoApp);

            // TODO: Mudar a cor de todos os itens do menu programaticamente
            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new MinhasComprasFragment()).commit();

        } else if (id == R.id.nav_compartilhar) {

            menu = navigationView.getMenu();

            nav_meus_produtos = menu.findItem(R.id.nav_meus_produtos);
            nav_meus_produtos.setTitle(R.string.txtMeusProdutos);

            nav_minhas_compras = menu.findItem(R.id.nav_minhas_compras);
            nav_minhas_compras.setTitle(R.string.txtMinhasCompras);

            nav_compartilhar = menu.findItem(R.id.nav_compartilhar);
            nav_compartilhar.setTitle("[Compartilhar]");

            nav_sair = menu.findItem(R.id.nav_sair);
            nav_sair.setTitle(R.string.txtSairDoApp);

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

            fragmentManager.beginTransaction().replace(R.id.content_fragment, new CompartilharFragment()).commit();

        }else if (id == R.id.nav_sair){
            menu = navigationView.getMenu();

            nav_meus_produtos = menu.findItem(R.id.nav_meus_produtos);
            nav_meus_produtos.setTitle(R.string.txtMeusProdutos);

            nav_minhas_compras = menu.findItem(R.id.nav_minhas_compras);
            nav_minhas_compras.setTitle(R.string.txtMinhasCompras);

            nav_compartilhar = menu.findItem(R.id.nav_compartilhar);
            nav_compartilhar.setTitle(R.string.txtCompartilhar);

            nav_sair = menu.findItem(R.id.nav_sair);
            nav_sair.setTitle("[Sair]");

            navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
