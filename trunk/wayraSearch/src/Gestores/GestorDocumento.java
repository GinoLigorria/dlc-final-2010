/*
 * Creado el 20-feb-2007
 * 
 */
package Gestores;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

import Dominio.Documento;
import Lectores.Lector;
import Persistencia.Register;
import Persistencia.Rutas;

/**
 * @author Administrador
 * 
 *  
 */
public class GestorDocumento
{
    private Documento documento;

    private File archivo;

    /**
     *  
     */
    public GestorDocumento(File f)
    {
        super();
        
        
        archivo = f;
        
        documento = new Documento();
        documento.setRuta(archivo.getAbsolutePath());
        documento.setModificado(archivo.lastModified());
        documento.setCreado(archivo.lastModified());
        documento.setTamanio(archivo.length());

      
    }

    /**
     *  Lee el archivo extrae los términos y actualiza el vector de términos
     *
     */
    public void analizar()
    {
        if (buscar(documento) == -1)
        {
        String ext = documento.getRuta().substring(
                documento.getRuta().lastIndexOf(".") + 1);

        documento.setTipo(ext);
        // sacar esto del lector
        //lee el archivo Lectors
        Iterator iter = Rutas.getParserLectorXML().getVectorLectores().iterator();

        Lector lect = null;
        Lector def = null;

        while (iter.hasNext())
        {
            lect = (Lector) iter.next();
            if (lect.getTipos().contains("*"))
            {
                def = lect;
            }
            if (lect.getTipos().contains(ext))
            {
                break;
            }
            lect = null;
        }

        Vector vTerminos = null;

        try
        {
            if (lect != null)
            {
                vTerminos = lect.extraerTerminos(archivo, documento);
            } else
            {
                vTerminos = def.extraerTerminos(archivo, documento);
            }

          
            if (vTerminos.size() > 0)
            {
                //Analiza término por término y arma el vocabulario
                //y la lista de posteo
                GestorTermino.actualizarVecTerminos(vTerminos, documento);
            }

        } catch (NullPointerException e)
        {
        }
        }
    }

    /**
     * @param terminos
     */

    public Documento getDocumento()
    {
        return documento;
    }

    public void setDocumento(Documento documento)
    {
        this.documento = documento;
    }

    /**
     * Suma la cantidad de Documentos
     * Guarda el Documento
     * @param documento2
     * @return devuelve la posición de inserción
     */
    public static long altaArchivo(Documento documento)
    {
        Rutas.getVocabulario().addCantDocumentos();
        return Rutas.getArchivoDocu().altaDirecta(new Register(documento));
    }

    public static Documento buscar(long pos)
    {
        Documento d =null;
        try
        {
            Rutas.getArchivoDocu().openForRead();
            Rutas.getArchivoDocu().seekRegister(pos);
            Register r = Rutas.getArchivoDocu().leer();
            Rutas.getArchivoDocu().close();
            d=(Documento) r.getData();
        } catch (FileNotFoundException e)
        {            
        }


        return d;
    }

    public static long buscar(Documento doc)
    {
        long r =-1;
        try
        {
            Rutas.getArchivoDocu().openForRead();
            r = Rutas.getArchivoDocu().search(doc);
            Rutas.getArchivoDocu().close();
        } catch (FileNotFoundException e)
        {           
        }
        return r;
    }
}