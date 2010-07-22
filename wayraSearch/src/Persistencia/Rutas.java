/*
 * Creado el 19-jun-2010
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
import Lectores.Lector;
import Lectores.LectorTextoPlano;
import Persistencia.xml.ParserLectorXML;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rocchietti Martin
 * 
 *  
 */
public class Rutas
{
    private static RegisterFile archivoDocu;

    private static ParserLectorXML parserLectorXML;

    private static Vocabulario vocabulario;

    private static File archivoVocabulario = new File("Indizador_archivos\\vocabulario.dat");

    private static  RegisterFile<NodoListaPosteo> rfListasPosteo;

    private static RegisterFile<NodoListaPosteo> rfListaPosteoOrdenada;

    private static Hashtable<String, String> htStopWords;



    /**
     *  
     */
    public Rutas()
    {
        super();

    }

    public static RegisterFile getArchivoDocu()
    {
       if (archivoDocu == null || !archivoDocu.exists())
       {
       archivoDocu = new RegisterFile("Indizador_archivos\\docs.dat", "rw", new Documento());
       }
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
            if (archivoVocabulario.exists())
            {
                materializarVocabulario();
            }
            else
            {
                vocabulario = new Vocabulario();
            }
            
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

    public static boolean serializarVocabulario()
    {
        try
        {
            File f = archivoVocabulario;
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fos);//mejora performance
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(vocabulario);

            oos.flush();
            fos.flush();
            oos.close();
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }

        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
/**
 * Materializa desde el archivoVocabulario el objeto Vocabulario
 * @return true si se pudo materializar, false si no pudo
 */
    public static boolean materializarVocabulario()
    {
        File f = archivoVocabulario;

        try
        {
            f.getAbsolutePath();
            FileInputStream fis = new FileInputStream(f);
            //BufferedInputStream bis = new BufferedInputStream(fis); //mejora performance
            //ObjectInputStream ois = new ObjectInputStream(bis);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object ob = ois.readObject();
            vocabulario = (Vocabulario) ob;

            ois.close();
            fis.close();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
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

        if (getRFListaPosteo().exists() && getRFListaPosteo().canDelete())
        {
            if(!rfListasPosteo.delete())
            {
                 System.out.println("No se pudo borrar el archivo listaPosteo ");
            }
        }

        if (getRFListaPosteoOrdenada().exists() && getRFListaPosteoOrdenada().canDelete())
        {
            if(!rfListaPosteoOrdenada.delete())
            {
                 System.out.println("No se pudo borrar el archivo listaPosteo ordenada");
            }
        }
       
    }

    public  static RegisterFile<NodoListaPosteo> getRFListaPosteo()
    {
        if (rfListasPosteo == null || !rfListasPosteo.exists())
        {
             rfListasPosteo =  new RegisterFile<NodoListaPosteo>("Indizador_archivos\\listasPosteo.dat", "rw", new NodoListaPosteo());
        }
        return rfListasPosteo;
    }

    public static RegisterFile<NodoListaPosteo> getRFListaPosteoOrdenada()
    {
        if (rfListaPosteoOrdenada == null || !rfListaPosteoOrdenada.exists())
        {
            rfListaPosteoOrdenada = new RegisterFile<NodoListaPosteo>("Indizador_archivos\\listaPosteoOrdenada.dat", "rw", new NodoListaPosteo());
        }
        return rfListaPosteoOrdenada;
    }

    public static long getCantDocsBase()
    {
        return getArchivoDocu().registerCount();
    }

    public static Hashtable<String, String> getStopWords() {

        if (htStopWords == null)
        {
            LectorTextoPlano l = new LectorTextoPlano();
            String s="Indizador_archivos\\stopWords.dat";
            htStopWords = l.getPalabras(s);
        }

            return htStopWords;
    }
}