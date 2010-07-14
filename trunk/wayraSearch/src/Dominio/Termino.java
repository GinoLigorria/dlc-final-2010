/*
 * Creado el 19-jun-2010
 * 
 */
package Dominio;

import java.io.Serializable;
import java.util.Vector;


/**
 * @author Administrador
 * 
 *  
 */
public final class Termino implements Comparable, Serializable
{

    private String termino;

    private long frecMaxima; //tf_max maxima cantidad de apariciones en un

    // documento

    private long cantDoc; // nr Cantidad de documentos en los que aparece
   

    private ListaPosteo listaPosteo; // Posición de comienzo de la lista de posteo



    /**
     *  
     */
    public Termino()
    {
        super();
        frecMaxima = 0;
        cantDoc = 0;

    }

    public long getCantDoc()
    {
        return cantDoc;
    }

    public void setCantDoc(long cantDoc)
    {
        this.cantDoc = cantDoc;
    }

    public long getFrecMaxima()
    {
        return frecMaxima;
    }

    public void setFrecMaxima(long frecMaxima)
    {
        this.frecMaxima = frecMaxima;
    }

    public String getTermino()
    {
        return termino;
    }

    public void setTermino(String termino)
    {
        this.termino = termino;
    }

      public boolean equals(Object obj)
    {
        Termino t = (Termino) obj;
        return this.termino.equalsIgnoreCase(t.termino);
    }

    public int hashCode()
    {
        return this.termino.hashCode();
    }

    /**
     *  
     */
    public void addFrecuencia()
    {
        frecMaxima++;
    }

    public void addCantidadDocs()
    {
        cantDoc++;
    }


    public ListaPosteo getListaPosteo()
    {

      return listaPosteo;

    }

    public void setListaPosteo(ListaPosteo listaPosteo)
    {
        this.listaPosteo = listaPosteo;
    }

   
    public int compareTo(Object arg0)
    {
        Termino t = (Termino) arg0;
        return termino.compareTo(t.termino);
    }

    public Vector buscarNodos(int cantidad)
    {
        return listaPosteo.buscarNodos(cantidad);
    }

    public Vector buscarNodos()
    {
        return listaPosteo.buscarNodos((int) cantDoc);
    }

}