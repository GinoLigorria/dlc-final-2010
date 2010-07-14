/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Gestores;

/**
 *
 * @author Mateo Guzman
 */

import Dominio.ListaPosteo;
import Dominio.NodoListaPosteo;
import Dominio.Termino;
import Persistencia.Rutas;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

public final class GestorListaPosteo {


    public void ordenarListaPosteo()
    {

        //Levantar el vocabulario
        Collection c = Rutas.getVocabulario().getVocabulario().values();
        Iterator itr = c.iterator();
        System.out.println("Grabando Lista de Posteo Ordenada termino: " );
        //por cada termino
        while(itr.hasNext())
        {
            Termino term = (Termino) itr.next();
            //obtener la lista de posteo y guardarla en un vector
            ListaPosteo lposteo = term.getListaPosteo();
            Vector nodos = lposteo.getVectorDeNodos();

            //ordenar el vector (ver que método se puede usar)

            //declaro un comparator para ordenar al revés
            Comparator comparator = Collections.reverseOrder();

            Collections.sort(nodos, comparator);

            //Recorro el vector de nodos ordenados y los grabo en otro archivo

            Iterator itr2 = nodos.iterator();

            System.out.println(term.getTermino());

            while(itr2.hasNext())
            {
                NodoListaPosteo nlp = (NodoListaPosteo)itr2.next();
                Rutas.getRFListaPosteoOrdenada().append(nlp);//cambiar a append mejora performance
                System.out.println (nlp.getFrecTermino());


            }

        }

    }

}
