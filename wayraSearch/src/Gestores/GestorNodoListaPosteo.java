/*
 * Creado el 19-jun-2010
 * 
 */
package Gestores;

import Dominio.ListaPosteo;
import Dominio.NodoListaPosteo;
import Dominio.Termino;


/**
 * @author Rocchietti Martin
 *
 * 
 */
public final class GestorNodoListaPosteo
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

        t.getListaPosteo().insertar(nodo); //le agrego un nodo a la lista posteo
    } 
    
}
