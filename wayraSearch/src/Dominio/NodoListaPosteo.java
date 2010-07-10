/*
 * Creado el 19-jun-2010
 * 
 */
package Dominio;

import Gestores.GestorDocumento;
import Persistencia.Grabable;
import Persistencia.RegisterFile;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Administrador
 * 
 *  
 */
public class NodoListaPosteo implements Grabable
{
    private long posicion; // 8 bytes // es la posición donde está el nombre del archivo

    private long frecTermino; //8 bytes
    
    private String tipo; // tam= 4 * 2 bytes= 8

    private static final int tam_tipo = 4; // no se almacena

    private long next; // 8 bytes

    public NodoListaPosteo()
    {
        next = -1;
    }
    
    public long getFrecTermino()
    {
        return frecTermino;
    }
    public void setFrecTermino(long frecTermino)
    {
        this.frecTermino = frecTermino;
    }
    public long getPosicion()
    {
        return posicion;
    }
    public void setPosicion(long posicion)
    {
        this.posicion = posicion;
    }
    public String getTipo()
    {
        return tipo;
    }
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    /**
     * @return
     */
    public Documento buscarDocumento()
    {       
        return GestorDocumento.buscar(posicion);
    }

    public int sizeOf() {
        return 32;
    }

    public long getNext()
    {
        return next;
    }

    public void setNext(long next)
    {
        this.next = next;
    }

    public void grabar(RandomAccessFile raf) {
        try {
            raf.writeLong(posicion);
            raf.writeLong(frecTermino);
            RegisterFile.writeString(raf, tipo, tam_tipo);
            raf.writeLong(next);
        } catch (IOException ex) {
            Logger.getLogger(NodoListaPosteo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void leer(RandomAccessFile raf) {
        try {
            posicion = raf.readLong();
            frecTermino = raf.readLong();
            tipo = RegisterFile.readString(raf, tam_tipo);
            next = raf.readLong();
        } catch (IOException ex) {
            Logger.getLogger(NodoListaPosteo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // we need to improve this
    // we can´t compare if we don´t have the term
    public int compareTo(Object o) {
       NodoListaPosteo ndlp2 = (NodoListaPosteo) o;
       return -1;
    }
}