/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dominio;

import Persistencia.Grabable;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Mateo Guzman
 */
public class ListaPosteo implements Serializable {

private int[] indicesListaPosteo; //Estos son los índices para recuperar la lista de posteo
private int[] indicesListaPosteoOrdenada;
private  NodoListaPosteo[] listaPosteo;

//Constructor
public ListaPosteo()
{
    super();

}

public ListaPosteo(String termino)
{
    super();

}

public ListaPosteo(int posInicial, RandomAccessFile raf)
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

public void setIndicesListaPosteo (int[] indicesListaPosteo)
{
    this.indicesListaPosteo = indicesListaPosteo;
}

public int[] getIndicesListaPosteo()
{
    return this.indicesListaPosteo;
}

public void setIndicesListaPosteoOrdenada( int[] indicesListaPosteoOrdenada)
{
    this.indicesListaPosteoOrdenada = indicesListaPosteoOrdenada;
}

public int[] getIndicesListaPosteoOrdenada()
{
    return this.indicesListaPosteoOrdenada;
}

public void ordenarListaDePosteo()
{
    //lógica de ordenamiento
    //recorrer cada uno de los nodos de posteo y ordenar por frecuencia
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
    //insertar en archivo

}




}
