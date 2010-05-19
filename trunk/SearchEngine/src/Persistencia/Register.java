package Persistencia;

/**
 * Una clase para describir en forma gen�rica un registro capaz de ser almacenado en un RegisterFile. Contiene una 
 * propiedad "activo" que indica si el registro es v�lido o no dentro del archivo. Se  usa para indicar si un 
 * registro fue marcado como borrado o no, permitiendo de esta forma implementar borrados por marcado l�gico dentro 
 * de un RegisterFile. Si un registro es v�lido (o sea, no fue marcado como borrado), entonces activo = true. Si el 
 * registro no es v�lido (est� marcado como borrado), entonces activo = false.  
 * 
 * @author Ing. Valerio Frittelli
 * @version Marzo de 2008
 */

import java.io.*;
import javax.swing.*;

public class Register < E extends Grabable > implements Grabable
{
     private boolean activo;    // marca de registro activo. Ocupa 1 byte en disco
     private E datos;    // los datos puros que ser�n grabados
     
     
     /**
      *  Crea un Registro con datos, marc�ndolo como activo.
      *  @param d los datos a almacenar en el nuevo registro.
      */
     public Register (E  d)
     {
         activo = true;
         datos = d;
     }
     
     
     /**
      *  Determina si el registro es activo o no.
      *  @return true si es activo, false si no.
      */
     public boolean isActive ()
     {
         return activo;
     }
     
     /**
      *  Cambia el estado del registro, en memoria.
      *  @param x el nuevo estado.
      */
     public void setActive(boolean x)
     {
        activo = x;    
     }
     
     /**
      *  Cambia los datos del registro en memoria.
      *  @param d los nuevos datos.
      */
     public void setData(E d)
     {
        datos = d;    
     }
     
     /**
      *  Accede a los datos del registro en memoria.
      *  @return una referencia a los datos del registro.
      */
     public E getData()
     {
         return datos;
     }
     
     /**
      *  Calcula el tama�o en bytes del objeto, tal como ser� grabado en disco. Pedido por Grabable.
      *  @return el tama�o en bytes del objeto como ser� grabado.
      */
     public int sizeOf()
     {
        return datos.sizeOf() + 1;   // suma 1 por el atributo "activo", que es boolean
     }
     
     /**
      *  Especifica c�mo se graba un Register en disco. Pedido por Grabable.
      *  @param a el manejador del archivo de disco donde se har� la grabaci�n.
      */
     public void grabar (RandomAccessFile a)
     {
         try
         {
             a.writeBoolean(activo);
             datos.grabar(a);
         }
         catch(IOException e)
         {
             JOptionPane.showMessageDialog(null, "Error al grabar el estado del registro: " + e.getMessage());
             System.exit(1);
         }
     }

     /**
      *  Especifica c�mo se lee un Register desde disco. Pedido por Grabable.
      *  @param a el manejador del archivo de disco desde donde se har� la lectura.
      */
     public void leer (RandomAccessFile a)
     {
         try
         {
             activo = a.readBoolean();
             datos.leer(a);
         }
         catch(IOException e)
         {
             JOptionPane.showMessageDialog(null, "Error al leer el estado del registro: " + e.getMessage());
             System.exit(1);
         }
     }    
     
     /**
      * Redefine el m�todo heredado desde Object. Retorna una cadena con el contenido del objeto, incluida la marca
      * de borrado.
      * @return una cadena con el estado del objeto.
      */
     public String toString()
     {
        return getData().toString() + "\tActivo?: " + isActive();    
     }
     
     /**
      * Compara dos objetos de la clase Register. Devuelve el resultado de comparar los
      * datos contenidos en el registro. Lanzar� una excepci�n ClassCastException si el objeto enviado x 
      * no puede convertirse a un Register v�lido.
      * @return 0 si los objetos eran iguales, 1 si el primero era mayor, -1 en caso contrario.
      * @param x el objeto contra el cual se compara.
      * @throws ClassCastException si x no puede convertirse en un objeto Register.
      */
     public int compareTo (Object x)
     {
         Register r = (Register) x;
         return datos.compareTo(r.datos);
     }
}
