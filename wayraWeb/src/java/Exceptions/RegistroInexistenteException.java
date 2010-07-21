/*
 * RegistroInexistenteException.java
 *
 * Created on 5 de abril de 2009, 22:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Exceptions;

/**
 *
 * @author Nicolas
 */
public class RegistroInexistenteException extends Exception
{
   	private String mensaje = "El registro no existe en el archivo o el archivo es demasiado chico!!";
    
	public RegistroInexistenteException()
	{
	}

	public RegistroInexistenteException(String mens)
	{
	    mensaje = mens;
	}
	
	public String getMessage()
	{
	   return mensaje;   
	}
}

