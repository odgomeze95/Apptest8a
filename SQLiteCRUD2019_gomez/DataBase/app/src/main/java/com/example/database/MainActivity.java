package com.example.database;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.database.Base.connectionDB;
import com.example.database.utilidades.Utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listItems;
    private listaAdaptador adaptador;
    connectionDB conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItems = (ListView) findViewById(R.id.listUsers);
        adaptador = new listaAdaptador(this, obtenerDatos());

        listItems.setAdapter(adaptador);
    }

    private ArrayList<fuente> obtenerDatos (){
        ArrayList<fuente> listItems = new ArrayList<>();

        conn=new connectionDB(this,"market",null,1);
        SQLiteDatabase market=conn.getReadableDatabase();

        Cursor cursor2=market.rawQuery("SELECT "+ Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_APELLIDO + ", "+Utilidades.CAMPO_CONTRASENA +
                " FROM "+Utilidades.TABLA_USUARIO, null);

        String[] campos = new String[] {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_APELLIDO, Utilidades.CAMPO_EMAIL};

        Cursor c = market.query(Utilidades.TABLA_USUARIO, campos, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                listItems.add(new fuente(c.getString(0),c.getString(1),c.getString(2)));
            } while(c.moveToNext());
        }

        c.close();
        return listItems;
    }

    private ArrayList<fuente> obtenerMujeres (){
        ArrayList<fuente> listItems = new ArrayList<>();
        String [] gener = {"Mujer"};

        conn=new connectionDB(this,"market",null,1);
        SQLiteDatabase market=conn.getReadableDatabase();

        Cursor cursor2=market.rawQuery("SELECT "+ Utilidades.CAMPO_NOMBRE + ", "+ Utilidades.CAMPO_APELLIDO + ", "+Utilidades.CAMPO_CONTRASENA +
                " FROM "+Utilidades.TABLA_USUARIO+ " WHERE " + Utilidades.CAMPO_GENERO+"=? ", gener);

        String[] campos = new String[] {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_APELLIDO, Utilidades.CAMPO_EMAIL};

        Cursor c = market.query(Utilidades.TABLA_USUARIO, campos, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                listItems.add(new fuente(c.getString(0),c.getString(1),c.getString(2)));
            } while(c.moveToNext());
        }

        c.close();
        return listItems;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.iteFemale :
                listItems = (ListView) findViewById(R.id.listUsers);
                adaptador = new listaAdaptador(this, obtenerMujeres());

                listItems.setAdapter(adaptador);
                return true;
            case R.id.iteMale :

                return true;
            case R.id.iteAllUsers :

                return true;
            case R.id.iteAbout :

                return true;
            case R.id.iteLogout :

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }


