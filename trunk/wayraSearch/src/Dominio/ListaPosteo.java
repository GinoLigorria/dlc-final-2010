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

Vector indicesListaPosteo ; //Estos son los índices para recuperar la lista de posteo
Vector indicesListaPosteoOrdenada  = new Vector();
private  NodoListaPosteo[] listaPosteo;
private long posicionInicial;
private long ultimaPosicion;


//Constructor
public ListaPosteo()
{
    super();
    indicesListaPosteo = new Vector();
    indicesListaPosteoOrdenada = new Vector();
    ultimaPosicion = -1;


}

public ListaPosteo(String termino)
{
    super();
    indicesListaPosteo = new Vector();
    indicesListaPosteoOrdenada = new Vector();
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

public void setIndicesListaPosteo (Vector indicesListaPosteo)
{
    this.indicesListaPosteo = indicesListaPosteo;
}

public Vector getIndicesListaPosteo()
{
    return this.indicesListaPosteo;
}

public void setIndicesListaPosteoOrdenada( Vector indicesListaPosteoOrdenada)
{
    this.indicesListaPosteoOrdenada = indicesListaPosteoOrdenada;
}

public Vector getIndicesListaPosteoOrdenada()
{
    return this.indicesListaPosteoOrdenada;
}

public void ordenarListaDePosteo()
{
    //lógica de ordenamiento
    //recorrer cada uno de los nodos de posteo y ordenar por frecuencia
    indicesListaPosteoOrdenada = indicesListaPosteo;
    Collections.sort(indicesListaPosteoOrdenada,new ComparadorNodosListaPosteo());


}

public void cargarListaPosteo(RandomAccessFile raf)
{
    //recorrer los índices
    //seek a la posición
    //leer nodolistaposteo

}

public Vector buscarNodos(int cantidad)
{
 return new Vector();
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
            Dominio.NodoListaPosteo nodoAnterior = (NodoListaPosteo) Rutas.getListaPosteo().getRegister(ultimaPosicion).getData();
            nodoAnterior.setNext(posicionActual);
            Rutas.getListaPosteo().update(nodoAnterior);
            }
            ultimaPosicion = posicionActual;
            indicesListaPosteo.add(posicionActual);

        } catch (RegistroInexistenteException ex) {
            Logger.getLogger(ListaPosteo.class.getName()).log(Level.SEVERE, null, ex);
        }

}


}
