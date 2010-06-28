/*
 * Creado el 20-feb-2007
 * 
 */
package Lectores;

import java.io.File;
import java.util.Vector;

import Dominio.Documento;
import Dominio.Termino;

/**
 * @author Administrador
 * 
 *  
 */
public class Lector
{
    protected Vector tipos;

    /**
     * @param tipos
     */
    public Lector()
    {
        super();
        this.tipos = new Vector();
    }

    public StringBuffer getTexto(File f)
    {
        StringBuffer r = new StringBuffer();
        int ind = f.getName().lastIndexOf(".");
        r.append(f.getName().substring(0, ind));
        r.append(" ");

        return r;
    }

    public Vector getTipos()
    {
        return tipos;
    }

   /**
    * Lee letra por letra del archivo y arma un vector con los términos no
    * repetidos en lowercase
    * @param f: File
    * @param documento El Documento
    * @return
    */
    public Vector extraerTerminos(File f, Documento documento)
    {
        Vector r = new Vector(); //vector para almacenar los términos
        //Se podría modificar e implementar con hash! (Mateo)

        StringBuffer texto = getTexto(f);

        if (texto != null)
        {
            int fin = Documento.TAM_BRIEF;
            if (texto.length() < fin)
            {
                fin = texto.length();
            }
            documento.setBrief(texto.substring(0, fin));

            texto.append(" ");
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < texto.length(); i++)
            {
                char c = texto.charAt(i);
                c = Character.toLowerCase(c);

                if (Character.isLetterOrDigit(c) || c == '-')
                {
                    buffer.append(c);
                } else
                {
                    if (buffer.length() > 2 && buffer.length() < 30) // creo cada termino
                    {
                        Termino t = new Termino();
                        t.setTermino(buffer.toString());

                        if (r.contains(t))
                        {
                            int ind = r.indexOf(t);
                            t = (Termino) r.get(ind);
                        } else
                        {
                            r.add(t);
                        }
                        t.addFrecuencia();
                    }
                    buffer = new StringBuffer();
                }
            }
        }

        return r;
    }
}

