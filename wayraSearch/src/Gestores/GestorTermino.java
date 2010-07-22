/*
 * Creado el 19-jun-2010
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
 * @author Rocchietti Martin
 * 
 *  
 */
public final class GestorTermino
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

        long cantPal = 0;
        while (iter.hasNext())
        {
            Termino t = (Termino) iter.next();
            NodoListaPosteo nodo = new NodoListaPosteo();
            nodo.setFrecTermino(t.getFrecMaxima());
            nodo.setPosicion(pos);
            nodo.setTipo(documento.getTipo());
           //inserto en Vocabulario si no existe (Mateo)
            cantPal+=1;
            System.out.println("Analizando archivo: " +documento.getRuta());
            System.out.println("Palabra: " + t.getTermino() + " "+ cantPal);
            
            if (Rutas.getVocabulario().containsValue(t))
            {               
                t = (Termino) Rutas.getVocabulario().get(t); 
                
            } else
            {
                Rutas.getVocabulario().put(t,t);                
            }
            //inserta el nodolistaPosteo
            GestorNodoListaPosteo.insertarNodo(t, nodo);
        }   
       
    }
    
    /**
     * Busca los objetos Terminos en el vocabulario
     * @param vPal: vector con palabras (string) a buscar
     * @param vocabulario HashMap del vocabulario.
     */
    public static Vector buscarTerminos(Vector vPal, HashMap vocabulario)
    {
        Vector r = new Vector();
        
        Iterator iter = vPal.iterator();
        while (iter.hasNext())
        {
            Termino t = (Termino) vocabulario.get(iter.next());

            if (t != null && ((double)t.getCantDoc()/(double)Rutas.getCantDocsBase() < 0.8)) //chequea que no sea un stop word (0.5 indice stop word)
            {
                r.add(t);
            }
           
        }
        
        Comparator comp = new ComparadorTerminoCantidadDocs();
        Collections.sort(r,comp);

        return r;
    }

}