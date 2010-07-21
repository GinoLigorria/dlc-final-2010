/*
 * Creado el 23-feb-2007
 * 
 */
package Gestores;

import java.util.Comparator;

import Dominio.Termino;

/**
 * @author Administrador
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
