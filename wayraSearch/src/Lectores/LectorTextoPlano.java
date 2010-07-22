/*
 * Creado el 19-jun-2010
 * 
 */
package Lectores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rocchietti Martin
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
    
    public Hashtable<String, String> getPalabras(String nombreDoc)
    {
        BufferedReader br = null;
        Hashtable<String, String> htReturn  = new Hashtable<String, String>();
        try {
            File f = new File(nombreDoc);
            br = new BufferedReader(new FileReader(f));
            String aux = br.readLine();
            htReturn.put(aux, aux);    
             while(aux !=null)
            {
                aux = br.readLine();
                htReturn.put(aux, aux);                
            }
            
        } catch (Exception ex) {            
            Logger.getLogger(LectorTextoPlano.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(LectorTextoPlano.class.getName()).log(Level.SEVERE, null, ex);
            }
            return htReturn;
        }                      
    }



}
