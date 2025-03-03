package br.com.dev.appclientes.view;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppApplication extends Application {

    public static final String DB_NAME = "CLIENTES.realm";
    public static final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .schemaVersion(DB_VERSION)
                .allowWritesOnUiThread(true)
                .build();

        Realm realm = Realm.getInstance(configuration);


        Log.d("db_log", "onCreate: Realm com sucesso: "+DB_NAME+" versão: "+DB_VERSION);
    }
}

