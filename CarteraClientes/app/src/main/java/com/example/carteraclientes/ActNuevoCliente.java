package com.example.carteraclientes;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.carteraclientes.BaseDatos.DatosOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActNuevoCliente extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtDireccion;
    private EditText edtEmail;
    private EditText edtTelefono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nuevo_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTelefono = (EditText) findViewById(R.id.edtTelefono);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_nuevo_cliente,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                final DatosOpenHelper datosOpenHelper=new DatosOpenHelper(getApplicationContext());
                datosOpenHelper.agregarClientes( edtNombre.getText().toString(),edtDireccion.getText().toString(),edtEmail.getText().toString(),edtTelefono.getText().toString());
                Toast.makeText(getApplicationContext(),"Cliente agregado correctamente",Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_cancelar:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}