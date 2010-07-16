/*
 * Creado el 19-jun-2010
 * 
 */
package Dominio;

import Persistencia.Rutas;
import java.io.Serializable;
import java.util.Vector;
import java.lang.Math;


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
    /**
     * Calcula la frecuencia inversa del término
     * @param cantDocs: cantidad de Documentos de la base
     * @return frecuencia inversa
     */
    public double getIdf()
    {
        double resp=-1;
        if (cantDoc > 0)
        {
        resp = Math.log10(Rutas.getCantDocsBase()/cantDoc);
        }
        return resp;
    }

}