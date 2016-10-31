package com.example.tiburcio.ejemploproveedorcontenido.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.tiburcio.ejemploproveedorcontenido.constantes.Utilidades;
import com.example.tiburcio.ejemploproveedorcontenido.pojos.Ciclo;

import java.io.IOException;

/**
 * Created by Tiburcio on 15/10/2016.
 */

public class CicloProveedor {
    static public void insert(ContentResolver resolvedor, Ciclo ciclo, Context contexto){
        Uri uri = Contrato.Ciclo.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Ciclo.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Ciclo.ABREVIATURA, ciclo.getAbreviatura());

        Uri returnUri = resolvedor.insert(uri, values);

        if(ciclo.getImagen()!=null){
            try {
                Utilidades.storeImage(ciclo.getImagen(), contexto, "img_" + returnUri.getLastPathSegment() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Hubo un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public void delete(ContentResolver resolver, int cicloId){
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Ciclo ciclo, Context contexto){
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + ciclo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Ciclo.NOMBRE, ciclo.getNombre());
        values.put(Contrato.Ciclo.ABREVIATURA, ciclo.getAbreviatura());

        resolver.update(uri, values, null, null);

        if(ciclo.getImagen()!=null){
            try {
                Utilidades.storeImage(ciclo.getImagen(), contexto, "img_" + ciclo.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto, "Hubo un error al guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public Ciclo read(ContentResolver resolver, int cicloId) {
        Uri uri = Uri.parse(Contrato.Ciclo.CONTENT_URI + "/" + cicloId);

        String[] projection = {Contrato.Ciclo._ID,
                Contrato.Ciclo.NOMBRE,
                Contrato.Ciclo.ABREVIATURA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Ciclo ciclo = new Ciclo();
            ciclo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Ciclo._ID)));
            ciclo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.NOMBRE)));
            ciclo.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.Ciclo.ABREVIATURA)));
            return ciclo;
        }

        return null;

    }
}
