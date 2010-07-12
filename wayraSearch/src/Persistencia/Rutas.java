/*
 * Creado el 21-feb-2007
 * 
 */
package Persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Dominio.Documento;
import Dominio.NodoListaPosteo;
import Dominio.Vocabulario;
import Gestores.GestorDirectorio;
import Persistencia.xml.ParserLectorXML;
import Persistencia.xml.ParserListaPosteoXML;

/**
 * @author Administrador
 * 
 *  
 */
public class Rutas
{
    private static RegisterFile archivoDocu;

    private static ParserLectorXML parserLectorXML;

    private static Vocabulario vocabulario;

    private static File archivoVocabulario = new File("vocabulario.dat");

    private static  RegisterFile<NodoListaPosteo> rfListasPosteo;

    private static RegisterFile<NodoListaPosteo> rfListaPosteoOrdenada;

    /**
     *  
     */
    public Rutas()
    {
        super();

    }

    public static RegisterFile getArchivoDocu()
    {
                 
       archivoDocu = new RegisterFile("docs.dat", "rw", new Documento());                  
        return archivoDocu;
    }

    public static void setArchivoDocu(RegisterFile archivoDocu)
    {
        Rutas.archivoDocu = archivoDocu;

    }

    public static ParserLectorXML getParserLectorXML()
    {
        if (parserLectorXML == null)
        {
            parserLectorXML = new ParserLectorXML();
        }

        return parserLectorXML;
    }

    public static Vocabulario getVocabulario()
    {
        if (vocabulario == null)
        {
            vocabulario = new Vocabulario();
        }
        return vocabulario;
    }

    public static void setParserLectorXML(ParserLectorXML parserLectorXML)
    {
        Rutas.parserLectorXML = parserLectorXML;
    }

    public static void setVocabulario(Vocabulario v)
    {
        Rutas.vocabulario = v;
    }

    public static void serializarVocabulario()
    {
        try
        {
            File f = archivoVocabulario;
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(vocabulario);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void materializarVocabulario()
    {
        File f = archivoVocabulario;
        try
        {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            vocabulario = (Vocabulario) ois.readObject();

            ois.close();
            fis.close();

        } catch (FileNotFoundException e)
        {

            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *  
     */
    public static void borrar()
    {
        if (getArchivoDocu().exists() && getArchivoDocu().canDelete())
        {
         if (!getArchivoDocu().delete())
         {
             System.out.println("No se pudo borrar el archivo ");
         }
        }
        if (archivoVocabulario.exists() && archivoVocabulario.canWrite())
        {
            if(!archivoVocabulario.delete())
            {
                 System.out.println("No se pudo borrar el archivo vocabulario ");
            }
        }

        if (rfListasPosteo.exists() && rfListasPosteo.canDelete())
        {
            if(!rfListasPosteo.delete())
            {
                 System.out.println("No se pudo borrar el archivo listaPosteo ");
            }
        }
       
    }

    public  static RegisterFile<NodoListaPosteo> getListaPosteo()
    {
        if (rfListasPosteo == null)
        {
             rfListasPosteo =  new RegisterFile<NodoListaPosteo>("listasPosteo.dat", "rw", new NodoListaPosteo());
        }
        return rfListasPosteo;
    }

    public static RegisterFile<NodoListaPosteo> getRFListaPosteoOrdenada()
    {
        if (rfListaPosteoOrdenada == null)
        {
            rfListaPosteoOrdenada = new RegisterFile<NodoListaPosteo>("listaPosteoOrdenada.dat", "rw", new NodoListaPosteo());
        }
        return rfListaPosteoOrdenada;
    }
}