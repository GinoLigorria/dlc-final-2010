/*
 * Creado el 19-jun-2010
 * 
 */
package Dominio;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Gestores.ComparadorTerminoCantidadDocs;
import java.util.Set;

/**
 * @author Administrador
 * 
 *  
 */
public final class Vocabulario implements Serializable
{
    private long cantTerminos;

    private long cantDocumentos;

    private HashMap vocabulario;

    private double coeficienteStopWords;  // coeficiente utilizado para determinar
                                         //  si términos son stop words (Mateo)

    /**
     *  
     */
    public Vocabulario()
    {
        super();
        vocabulario = new HashMap();
        coeficienteStopWords = 1;
    }

    public void addCantTerminos()
    {
        cantTerminos++;
    }

    public void addCantDocumentos()
    {
        cantDocumentos++;
    }

    public HashMap getVocabulario()
    {
        return vocabulario;
    }

    public void setVocabulario(HashMap vocabulario)
    {
        this.vocabulario = vocabulario;
    }

    public long getCantDocumentos()
    {
        return cantDocumentos;
    }

    public void setCantDocumentos(long cantDocumentos)
    {
        this.cantDocumentos = cantDocumentos;
    }

    public long getCantTerminos()
    {
        return cantTerminos;
    }

    public void setCantTerminos(long cantTerminos)
    {
        this.cantTerminos = cantTerminos;
    }

    /**
     * @param t
     * @return
     */
    public boolean containsValue(Termino t)
    {
        return vocabulario.containsValue(t);
    }

    /**
     * @param t
     * @return
     */
    public Termino get(Termino t)
    {
        return (Termino) vocabulario.get(t);
    }

    /**
     * @param t
     * @param t2
     */
    public void put(Termino t, Termino t2)
    {
        vocabulario.put(t, t2);
        addCantTerminos();
    }

    public void limpiarStopWords()
    {
        List v = (List) vocabulario.values();
        Comparator comp = new ComparadorTerminoCantidadDocs();
       // Collections.sort(v, comp);

        Iterator iter = v.iterator();

        while (iter.hasNext())
        {
            Termino t = (Termino) iter.next();
            long cant = t.getCantDoc();
            double p = (double) cant / (double) cantDocumentos;

            if (p >= coeficienteStopWords)
            {
                vocabulario.remove(t);
            }
        }

    }

    public double getCoeficienteStopWords()
    {
        return coeficienteStopWords;
    }

    public void setCoeficienteStopWords(double coeficienteStopWords)
    {
        this.coeficienteStopWords = coeficienteStopWords;

        
    }

    public boolean verificarSanidad()
    {
        Boolean respuesta = false;
        Set s = vocabulario.keySet();
        Iterator i = s.iterator();
        while(i.hasNext())
        {
            Termino t = (Termino) i.next();
            System.out.println("Termino : " + t.getTermino() );
            respuesta = true;
        }

        return respuesta;
    }

}