/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

import Dominio.NodoListaPosteo;
import Exceptions.RegistroInexistenteException;
import Persistencia.Grabable;
import Persistencia.RegisterFile;
import Persistencia.Rutas;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mateo Guzman
 */
public class ComparadorNodosListaPosteo implements Comparator {

    RegisterFile<NodoListaPosteo> rfnlp;
    public ComparadorNodosListaPosteo()
    {
        rfnlp = Rutas.getListaPosteo();
    }

    public int compare(Object o1, Object o2)  {
        try {
            //comparo los nodos por frecuencia
            int inlp1 = ((Integer) o1).intValue();
            int inlp2 = ((Integer) o2).intValue();
            NodoListaPosteo nlp1 = (NodoListaPosteo) rfnlp.getRegister(inlp1).getData();
            NodoListaPosteo nlp2 = (NodoListaPosteo) rfnlp.getRegister(inlp2).getData();
            long resultado = nlp1.getFrecTermino() - nlp2.getFrecTermino();
            return (int) resultado;
        } catch (RegistroInexistenteException ex) {
            return -1;
        }



    }


}
