/*
 * Creado el 22-feb-2007
 * 
 */
package Gestores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import Dominio.Busqueda;
import Dominio.Documento;
import Dominio.NodoListaPosteo;
import Dominio.Termino;
import Persistencia.Rutas;
import java.util.Hashtable;

/**
 * @author Administrador
 * 
 *  
 */
public final class GestorBusqueda
{

    /**
     *  
     */
    public GestorBusqueda()
    {
        super();
    }

    /**
     * @param b
     * @return Vector de Documentos
     */
    public Vector buscar(Busqueda b)
    {
        long cantDocs = this.contarDocumentos();

         Vector r = null;

        Vector vSimples = extraerPalabras(' ', b.getCriterio());
        //Vector vMas = extraerPalabras('+', b.getCriterio());
        //Vector vMenos = extraerPalabras('-', b.getCriterio());

        HashMap vocabulario = Rutas.getVocabulario().getVocabulario();

        //Obtenemos los objetos términos de las palabras ingresadas
        vSimples = GestorTermino.buscarTerminos(vSimples, vocabulario);
        //chequear si encontro terminos para todos las palabras

        if (vSimples == null)
        {
            // No se han encontrado resultados para la búsqueda
            return null;
        }

        //Calcular las frec Inversas

        //Ordenarlos x Idf decre

        //Obtener los primero R documentos
        
        // Obtener el módulo de la consulta
        
        
        //Por cada documento obtenido
        
            //Calcular el modulo del documento para la query
        
        //Aplicar la funcion de Similitud


        //vMas = GestorTermino.buscarTerminos(vMas, vocabulario);
        //vMenos = GestorTermino.buscarTerminos(vMenos, vocabulario);

        


        //busca los nodos de las palabras
        Vector vNodoSimples = buscarNodos(vSimples);
        //Vector vNodoMas = buscarNodos(vSimples);
        //Vector vNodoMenos = buscarNodos(vSimples);

        Vector vNodosFinal = new Vector();
        vNodosFinal.addAll(vNodoSimples);
        //vNodosFinal.addAll(vNodoMas); // xq mierda duplica
        //vNodosFinal.removeAll(vNodoMenos);

        r = buscarDocumentos(vNodosFinal);

        return r;
    }

    /**
     *
     * @param nodosFinal
     * @return
     */
    private Vector buscarDocumentos(Vector v)
    {

        //ordenar el vector crecientemente de acuerdo a su idf
        Vector r = new Vector();
        Iterator iter = v.iterator();

        while (iter.hasNext())
        {
            NodoListaPosteo n = (NodoListaPosteo) iter.next();
            Documento d = n.buscarDocumento();
            if (!r.contains(d))
            {
                r.add(d);
            }
        }

        return r;
    }

    /**
     * Busca los Nodos en los que se encuentran el vector de terminos.
     * @param Vector de términos
     * @return Vector nodosListaPosteo de los términos
     */
    private Vector buscarNodos(Vector v)
    {
        Vector r = new Vector();
        Iterator iter = v.iterator();

        while (iter.hasNext())
        {
            Termino t = (Termino) iter.next();
            r.addAll(t.buscarNodos());
        }

        return r;
    }

    /**
     * Devuelve un vector de palabras en lowercase
     * @param criterio
     * @return
     */
    private Vector extraerPalabras(char prefijo, StringBuffer criterio)
    {
        Vector r = new Vector();
        criterio.insert(0, ' ');
        criterio.append(" ");

        StringBuffer buffer = new StringBuffer();
        boolean b = false;

        //recorro el string
        for (int i = 0; i < criterio.length(); i++)
        {
            char c = criterio.charAt(i);
            c = Character.toLowerCase(c);

            if (c == prefijo)
            {
                b = true;
            }
            //agrego únicamente letras y dígitos
            if (b && (Character.isLetterOrDigit(c) || c == '-'))
            {
                buffer.append(c);
            } else
            {
                //
                if (buffer.length() > 2)
                {
                    //verifico que no sea stop word
                    String palabra = buffer.toString();
                    if(!esStopWord(palabra))
                    {
                        Termino t = new Termino();
                        t.setTermino(palabra);
                        r.add(t);
                        buffer = new StringBuffer();    
                    }

                }
                if (c == prefijo)
                {
                    b = true;
                } else
                {
                    b = false;
                }
            }
        }

        return r;
    }
    /**
     * Cantidad de Documentos en la base
     * @return La cantidad de documentos de la base
     */
    public long contarDocumentos()
    {
        return Rutas.getArchivoDocu().registerCount();
    }

    private boolean esStopWord(String palabra) {

        Hashtable<String, String> hStopWords = Rutas.getStopWords();
        if (hStopWords.containsKey(palabra))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

}