/*
 * Creado el 20-feb-2007
 * 
 */
package Lectores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Administrador
 *
 * 
 */
public final class LectorTextoPlano extends Lector
{


    /* (sin Javadoc)
     * @see ar.com.matheu.buscador.ges.lect.Lector#getTexto(java.io.File)
     */
    public StringBuffer getTexto(File f)
    {
        StringBuffer r = super.getTexto(f);
        
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(f));           
            
            String aux = br.readLine();
            
            while(aux !=null)
            {
            r.append(aux);
            r.append(" ");
            aux = br.readLine();
            }
            
        } catch (FileNotFoundException e)
        {            
            e.printStackTrace();
        } catch (IOException e)
        {            
            e.printStackTrace();
        }
        return r;
    }

}
