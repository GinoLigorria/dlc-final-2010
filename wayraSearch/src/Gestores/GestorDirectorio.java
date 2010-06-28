/*
 * Creado el 22-feb-2007
 * 
 */
package Gestores;

import java.io.File;

import Dominio.Directorio;
import Persistencia.Rutas;

/**
 * @author Administrador
 * 
 *  
 */
public class GestorDirectorio
{

    /**
     *  
     */
    public GestorDirectorio()
    {
        super();
    }

    public static void indizar(File raiz)
    {
        File vectorFile[] = raiz.listFiles();

        File aux = null;
        GestorDocumento gestor = null;

         for (int i = 0; i < vectorFile.length; i++)
        {
            aux = vectorFile[i];

            if (aux.isDirectory())
            {
                Directorio dir = new Directorio(aux);
                dir.indizar();
            } else
            {
                gestor = new GestorDocumento(aux);               
            }
        }
        Rutas.serializarVocabulario(); //(nuevo Mateo)
    }

    public static void borrarTodo(File raiz)
    {
        File vectorFile[] = raiz.listFiles();

        File aux = null;

        for (int i = 0; i < vectorFile.length; i++)
        {
            aux = vectorFile[i];

            if (aux.isDirectory())
            {
                borrarTodo(aux);
                aux.delete();
            } else
            {
                aux.delete();
            }
        }
    }

}