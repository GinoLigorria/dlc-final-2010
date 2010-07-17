/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Gestores;

import Dominio.Termino;
import java.util.Comparator;

/**
 *
 * @author Mateo Guzman
 */
public class ComparatorIDF implements Comparator{

    public ComparatorIDF()
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

        return (int) (t1.getIdf() - t0.getIdf());
    }

}
