package com.example.carteraclientes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.carteraclientes.BaseDatos.DatosOpenHelper;
import com.example.carteraclientes.BaseDatos.BaseDatosClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ActMain extends AppCompatActivity {
    private ListView lstDatos;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> clientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton mostrar = (FloatingActionButton) findViewById(R.id.buscar);
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActMain.this, ActBuscar.class);
                startActivityForResult(it,0);
            }
        });

        actualizar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActMain.this, ActNuevoCliente.class);
                startActivityForResult(it,0);
            }
        });

        actualizar();
    }

    private void actualizar(){

        lstDatos = (ListView) findViewById(R.id.lstDatos);

        try {
            DatosOpenHelper dbHelper = new DatosOpenHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {
                    BaseDatosClass.DATOS.COLUMN_NOMBRE,
                    BaseDatosClass.DATOS.COLUMN_TELEFONO
            };

            String selection = null;
            String[] selectionArgs = null;

            String sortOrder = BaseDatosClass.DATOS.COLUMN_TELEFONO + " DESC";

            Cursor cursor = db.query(
                    BaseDatosClass.DATOS.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );

            if(cursor.getCount() >= 0) {
                clientes = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String sNombre = cursor.getString(cursor.getColumnIndexOrThrow(BaseDatosClass.DATOS.COLUMN_NOMBRE));
                    String sTelefono = cursor.getString(cursor.getColumnIndex(BaseDatosClass.DATOS.COLUMN_TELEFONO));
                    clientes.add(sNombre + ": " + sTelefono);
                }
                cursor.close();
            }
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clientes);
            lstDatos.setAdapter(adaptador);

        }catch (Exception ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        actualizar();
    }

}