/*
 * Creado el 19-jun-2010
 * 
 */
package Gestores;

import java.util.Comparator;

import Dominio.Termino;

/**
 * @author Rocchietti Martin
 *
 * 
 */
public final class ComparadorTerminoCantidadDocs implements Comparator
{

    /**
     * 
     */
    public ComparadorTerminoCantidadDocs()
    {
        super();        
    }

    /* (sin Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object arg0, Object arg1)
    {
        Termino t0 = (Termino) arg0;
        Termino t1 = (Termino) arg1;

        return (int) (t1.getCantDoc() - t0.getCantDoc());
    }

}
