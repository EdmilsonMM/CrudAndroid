package com.example.proybasedatos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class adminSQLiteOpenHelper extends SQLiteOpenHelper{

    public adminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase Proyecto) {

        Proyecto.execSQL("create table articulos(id int primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
