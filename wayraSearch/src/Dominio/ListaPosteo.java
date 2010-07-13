/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dominio;

import Exceptions.RegistroInexistenteException;
import Persistencia.Grabable;
import Persistencia.Register;
import Persistencia.Rutas;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import utiles.ComparadorNodosListaPosteo;

/**
 *
 * @author Mateo Guzman
 */
public class ListaPosteo implements Serializable {


private long posicionInicial;
private long ultimaPosicion;


//Constructor
public ListaPosteo()
{
    super();
   
    ultimaPosicion = -1;


}

public ListaPosteo(String termino)
{
    super();
 
    ultimaPosicion = -1;

}

public ListaPosteo(int posInicial)
{
    super();

    try {
         NodoListaPosteo nodo = new NodoListaPosteo();//creo un NodoListaPosteo
        //raf.seek(posInicial * nodo.sizeOf()); //pongo el puntero al primer nodo
        //nodo.leer(raf);

        //cargo la lista
        }
    catch (Exception e) {
    }


}



public void ordenarListaDePosteo()
{
    //lógica de ordenamiento
    //recorrer cada uno de los nodos de posteo y ordenar por frecuencia
    //indicesListaPosteoOrdenada = indicesListaPosteo;
    //Collections.sort(indicesListaPosteoOrdenada,new ComparadorNodosListaPosteo());


}

public void cargarListaPosteo(RandomAccessFile raf)
{
    //recorrer los índices
    //seek a la posición
    //leer nodolistaposteo

}
/**
 * Busca los nodos en la lista de posteo.
 * La cantidad de nodos a devolver está indicada por el parametro cantidad.
 * @param cantidad de nodos a devolver
 * @return Vector de NodosListaPosteo
 */
public Vector buscarNodos(int cantidad)
{

 try {
            //declaro el vector
            Vector nodos = new Vector();
            //obtener nodos hasta que el next sea -1

            //
            if (posicionInicial != -1 && nodos.size() <= cantidad)
            {
                //obtengo el primer nodo
                NodoListaPosteo nodo = (NodoListaPosteo) Rutas.getListaPosteo().getRegister(posicionInicial).getData();
                nodos.add(nodo);
                //mientras existan nodos
                while (nodo.getNext()!= -1 && nodos.size() <= cantidad)
                {
                    nodo = (NodoListaPosteo) Rutas.getListaPosteo().getRegister(nodo.getNext()).getData();
                    nodos.add(nodo);
                }
                // tengo el vector con todos los nodos
            }

            return nodos;

        } catch (RegistroInexistenteException ex) {
            Logger.getLogger(ListaPosteo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
}


public Vector getVectorDeNodos()
{
      try {
            //declaro el vector
            Vector nodos = new Vector();
            //obtener nodos hasta que el next sea -1

            //
            if (posicionInicial != -1)
            {
                //obtengo el primer nodo
                NodoListaPosteo nodo = (NodoListaPosteo) Rutas.getListaPosteo().getRegister(posicionInicial).getData();
                nodos.add(nodo);
                //mientras existan nodos
                while (nodo.getNext()!= -1)
                {
                    nodo = (NodoListaPosteo) Rutas.getListaPosteo().getRegister(nodo.getNext()).getData();
                    nodos.add(nodo);
                }
                // tengo el vector con todos los nodos
            }

            return nodos;

        } catch (RegistroInexistenteException ex) {
            Logger.getLogger(ListaPosteo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

}

public void insertar(Dominio.NodoListaPosteo node)
{
        try {
            //obtener posición de último nodo grabado
            //insertar en archivo
            long posicionActual = Rutas.getListaPosteo().alta(node);
            //setear el next del último nodo grabado con la posición del reciente grabado
            if (ultimaPosicion == -1)
            {
                posicionInicial = posicionActual;
            }
            else
            {
                //acceso a disco DIRECTO
            Dominio.NodoListaPosteo nodoAnterior = (NodoListaPosteo) Rutas.getListaPosteo().getRegister(ultimaPosicion).getData();
            nodoAnterior.setNext(posicionActual);
            Rutas.getListaPosteo().writeRegister(new Register(nodoAnterior), ultimaPosicion);
            }
            ultimaPosicion = posicionActual;
            //indicesListaPosteo.add(posicionActual);

        } catch (RegistroInexistenteException ex) {
            Logger.getLogger(ListaPosteo.class.getName()).log(Level.SEVERE, null, ex);
        }

}


}
