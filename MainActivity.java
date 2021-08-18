package com.example.proybasedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtDesc, txtPrecio;
    Button btnSAVE, btnUpdate, btnDelete, btnBuscar;
    ListView lst;
    adminSQLiteOpenHelper admin = new adminSQLiteOpenHelper(this,"administracion",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.txtCodigo);
        txtDesc = findViewById(R.id.txtDescripcion);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnSAVE = findViewById(R.id.btnGuardar);
        btnUpdate = findViewById(R.id.btnActualizar);
        btnDelete = findViewById(R.id.btnEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);
        lst = findViewById(R.id.lst);

        llenarlist();


    }

    public void Guardar (View view){
        SQLiteDatabase BD =  admin.getWritableDatabase();

        String id = txtID.getText().toString();
        String descripcion = txtDesc.getText().toString();
        String precio = txtPrecio.getText().toString();

        if (!id.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("id", id);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BD.insert("articulos", null, registro);

            BD.close();
            txtID.setText("");
            txtDesc.setText("");
            txtPrecio.setText("");

            Toast.makeText(this, "Registro Existoso", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Debes Llenar Todos los Campos", Toast.LENGTH_SHORT).show();
        }
        llenarlist();
    }

    void llenarlist(){

        SQLiteDatabase BD =  admin.getWritableDatabase();

        Cursor fila = BD.rawQuery("select descripcion from articulos", null);

        ArrayList<String> art = new ArrayList<>();

        if (fila.moveToFirst()){
            do {
                art.add(fila.getString(0));
            }
            while (fila.moveToNext());
        }
        BD.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,art);
        lst.setAdapter(adapter);

    }

    public void Buscar (View view){
        SQLiteDatabase BD =  admin.getWritableDatabase();

        String id =  txtID.getText().toString();

        if (!id.isEmpty()){
            Cursor fila = BD.rawQuery("select descripcion, precio from articulos where id="+id, null);

            if (fila.moveToFirst()){
                txtDesc.setText(fila.getString(0));
                txtPrecio.setText(fila.getString(1));
                BD.close();

            }
            else{
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Actualizar (View view){
        SQLiteDatabase BD =  admin.getWritableDatabase();

        String id = txtID.getText().toString();
        String descripcion = txtDesc.getText().toString();
        String precio = txtPrecio.getText().toString();

        if (!id.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("descripcion", descripcion);
            registro.put("precio", precio);


            int row = BD.update("articulos",registro,"id="+id,null);
            BD.close();

            txtID.setText("");
            txtDesc.setText("");
            txtPrecio.setText("");

            if(row==1){
                Toast.makeText(this, "Articulo Modificado Correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }

        llenarlist();

    }

    public void Eliminar (View view){
        SQLiteDatabase BD =  admin.getWritableDatabase();

        String id = txtID.getText().toString();

        if (!id.isEmpty()){
            int row = BD.delete("articulos","id="+id,null);
            BD.close();

            txtID.setText("");
            txtDesc.setText("");
            txtPrecio.setText("");

            if(row == 1){
                Toast.makeText(this, "Articulo Eliminado Correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes  introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
        llenarlist();
    }


}