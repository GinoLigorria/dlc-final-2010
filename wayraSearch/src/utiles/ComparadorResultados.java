/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

import Dominio.Resultado;
import java.util.Comparator;

/**
 *
 * @author Mateo Guzman
 */
public class ComparadorResultados  implements Comparator{



    public int compare(Object o1, Object o2)  {

            //comparo los resultados
            Double r1 = ((Resultado) o1).getSimilitud();
            Double r2 = ((Resultado) o2).getSimilitud();
            double resultado = r2 - r1;
            return (int) resultado;
    }


}

