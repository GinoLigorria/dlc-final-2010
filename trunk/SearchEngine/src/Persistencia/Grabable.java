package Persistencia;

/**
 * Indica el comportamiento que debe ser capaz de mostrar un objeto que vaya a ser grabado en 
 * un RegisterFile. Observar que un Grabable ES UN Comparable que se sabe almacenar en un 
 * RegisterFile, se sabe leer desde un RegisterFile, y sabe indicar su tama�o en bytes cuando
 * se almacena en un RegisterFile. 
 * El m�todo compareTo() se deja para ser implementado por las clases que implementen
 * Grabable, con lo cual se obliga a todas ellas a contar con el m�todo.
 * 
 * @author Ing. Valerio Frittelli
 * @version Marzo de 2008
 */

import java.io.*;
public interface Grabable extends Comparable
{
    /**
     *  Calcula el tama�o en bytes del objeto, tal como ser� grabado en un RegisterFile. 
     *  @return el tama�o en bytes del objeto.
     */
    int sizeOf();
    
    /**
     *  Indica c�mo grabar un objeto en un RandomAccessFile. Este m�todo ser� invocado por los 
     *  m�todos de la clase RegisterFile para almacenar el objeto en disco.
     *  @param el RandomAccessFile donde ser� grabado el objeto
     */
    void grabar (RandomAccessFile a);
    
    /**
     *  Indica c�mo leer un objeto desde un RandomAccessFile. Este m�todo ser� invocado por los 
     *  m�todos de la clase RegisterFile para leer el objeto desde disco.
     *  @param a el archivo donde se har� la lectura
     */
    void leer (RandomAccessFile a);
}
