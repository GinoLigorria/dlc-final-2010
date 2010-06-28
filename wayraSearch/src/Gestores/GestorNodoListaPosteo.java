/*
 * Creado el 23-feb-2007
 * 
 */
package Gestores;

import Dominio.ListaPosteo;
import Dominio.NodoListaPosteo;
import Dominio.Termino;
import Persistencia.xml.ParserListaPosteoXML;

/**
 * @author Administrador
 *
 * 
 */
public class GestorNodoListaPosteo
{

    /**
     * 
     */
    public GestorNodoListaPosteo()
    {
        super();        
    }
    
    /**
     * @param nodo
     */
    public static void insertarNodo(Termino t, NodoListaPosteo nodo)
    {
        if (t.getFrecMaxima() < nodo.getFrecTermino())
        {
            t.setFrecMaxima(nodo.getFrecTermino());
        }
        t.addCantidadDocs();

        if (t.getListaPosteo() == null) //si no tiene lista de posteo
        {
            t.setListaPosteo(new ListaPosteo(t.getTermino())); //creo una
        }

        t.getListaPosteo().insertar(nodo); //si tiene le agrego un nodo
    } 
    
}
