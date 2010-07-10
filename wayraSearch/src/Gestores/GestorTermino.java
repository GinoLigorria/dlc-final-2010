/*
 * Creado el 22-feb-2007
 * 
 */
package Gestores;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import Dominio.Documento;
import Dominio.NodoListaPosteo;
import Dominio.Termino;
import Persistencia.Rutas;

/**
 * @author Administrador
 * 
 *  
 */
public class GestorTermino
{

    /**
     *  
     */
    public GestorTermino()
    {
        super();
    }

/**
 * Da de alta un Documento.
 * Actualiza el Vocabulario
 * Crea nodosLista de Posteo
 * @param Vector de términos
 * @param El documento a grabar
 */
    public static void actualizarVecTerminos(Vector terminos,
            Documento documento)
    {        
        long pos = GestorDocumento.altaArchivo(documento) ;
        
        Iterator iter = terminos.iterator();

        while (iter.hasNext())
        {
            Termino t = (Termino) iter.next();
            NodoListaPosteo nodo = new NodoListaPosteo();
            nodo.setFrecTermino(t.getFrecMaxima());
            nodo.setPosicion(pos);
            nodo.setTipo(documento.getTipo());
           //inserto en Vocabulario si no existe (Mateo)
            if (Rutas.getVocabulario().containsValue(t))
            {               
                t = (Termino) Rutas.getVocabulario().get(t); //No debería aumentarle 1 cantDoc y chequear la frecuencia maxima
                
            } else
            {
                Rutas.getVocabulario().put(t,t);                
            }
            //inserta el nodolistaPosteo
            GestorNodoListaPosteo.insertarNodo(t, nodo);
        }   
       
    }
    
    /**
     * @param vSimples
     * @param vocabulario
     */
    public static Vector buscarTerminos(Vector vPal, HashMap vocabulario)
    {
        Vector r = new Vector();
        
        Iterator iter = vPal.iterator();
        while (iter.hasNext())
        {
            Termino t = (Termino) vocabulario.get(iter.next());

            if (t != null)
            {
                r.add(t);
            }
        }
        
        Comparator comp = new ComparadorTerminoCantidadDocs();
        Collections.sort(r,comp);

        return r;
    }

}