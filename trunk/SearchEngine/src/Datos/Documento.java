/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import Persistencia.Grabable;
import java.io.IOException;
import java.io.RandomAccessFile;
import Persistencia.RegisterFile;

/**
 *
 * @author Mateo Guzman
 */
public class Documento implements Grabable{

   //atributes
    private String ruta; //256 caracteres
    private String descripcion;//128 caracteres
    private String tipo;//4 caracteres = 8 bytes
    private long modificado; //fecha modificado = 8 bytes
    private long creado; //fecha creado = 8 bytes
    private static final int tam_ruta = 256;
    private static final int tam_descripcion =  128;


    //constructor
    public Documento()
    {
        super();
        ruta="";
        descripcion = "";
        tipo = "";
    }

    //gets sets

    public String getRuta()
    {
        return this.ruta;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }

    public String getTipo()
    {
        return this.tipo;
    }

    public long getModificado()
    {
        return this.modificado;
    }

    public long getCreado()
    {
        return this.creado;
    }

    public void setRuta(String ruta)
    {
        this.ruta = ruta;
    }

    public void setDescripcion (String descripcion)
    {
        this.descripcion =  descripcion;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public void setCreado(long creado)
    {
        this.creado= creado;
    }

    public void setModificado(long modificado)
    {
        this.modificado = modificado;
    }

    public int sizeOf() {

        return tam_ruta * 2 + tam_descripcion * 2 + 8 + 8 + 8;
    }

    public void grabar(RandomAccessFile raf) {
        try
        {
            RegisterFile.writeString(raf, ruta, tam_ruta);
            RegisterFile.writeString(raf, descripcion, tam_descripcion);
            raf.writeLong(creado);
            raf.writeLong(modificado);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void leer(RandomAccessFile raf) {
        try
        {
            ruta = RegisterFile.readString(raf, tam_ruta).trim();
            descripcion = RegisterFile.readString(raf, tam_descripcion).trim();
            creado = raf.readLong();
            modificado = raf.readLong();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public int compareTo(Object o) {
        Documento other = (Documento) o;
        return this.ruta.compareTo(other.ruta);
    }
}
