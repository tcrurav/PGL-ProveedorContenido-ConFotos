package com.example.tiburcio.ejemploproveedorcontenido.pojos;

import android.graphics.Bitmap;

import com.example.tiburcio.ejemploproveedorcontenido.constantes.G;

/**
 * Created by Tiburcio on 31/07/2016.
 */
public class Ciclo {
    private int ID;
    private String nombre;
    private String abreviatura;
    private Bitmap imagen;

    public Ciclo(){
        this.ID = G.SIN_VALOR_INT;
        this.nombre = G.SIN_VALOR_STRING;
        this.setAbreviatura(G.SIN_VALOR_STRING);
        this.setImagen(null);
    };

    public Ciclo(int ID, String nombre, String abreviatura, Bitmap imagen) {
        this.ID = ID;
        this.nombre = nombre;
        this.setAbreviatura(abreviatura);
        this.setImagen(imagen);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
