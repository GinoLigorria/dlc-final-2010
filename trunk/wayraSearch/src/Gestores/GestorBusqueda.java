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

/**
 * @author Administrador
 * 
 *  
 */
public class GestorBusqueda
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
        Vector r = null;

        Vector vSimples = extraerPalabras(' ', b.getCriterio());
        Vector vMas = extraerPalabras('+', b.getCriterio());
        Vector vMenos = extraerPalabras('-', b.getCriterio());

        HashMap vocabulario = Rutas.getVocabulario().getVocabulario();

        vSimples = GestorTermino.buscarTerminos(vSimples, vocabulario);
        vMas = GestorTermino.buscarTerminos(vMas, vocabulario);
        vMenos = GestorTermino.buscarTerminos(vMenos, vocabulario);

        Vector vNodoSimples = buscarNodos(vSimples);
        Vector vNodoMas = buscarNodos(vSimples);
        Vector vNodoMenos = buscarNodos(vSimples);

        Vector vNodosFinal = new Vector();
        vNodosFinal.addAll(vNodoSimples);
        vNodosFinal.addAll(vNodoMas);
        vNodosFinal.removeAll(vNodoMenos);

        r = buscarDocumentos(vNodosFinal);

        return r;
    }

    /**
     * @param nodosFinal
     * @return
     */
    private Vector buscarDocumentos(Vector v)
    {
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
     * @param simples
     * @return
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

        for (int i = 0; i < criterio.length(); i++)
        {
            char c = criterio.charAt(i);
            c = Character.toLowerCase(c);

            if (c == prefijo)
            {
                b = true;
            }

            if (b && (Character.isLetterOrDigit(c) || c == '-'))
            {
                buffer.append(c);
            } else
            {
                if (buffer.length() > 0)
                {
                    Termino t = new Termino();
                    t.setTermino(buffer.toString());
                    r.add(t);
                    buffer = new StringBuffer();
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

}