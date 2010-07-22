/*
 * Creado el 19-jun-2010
 * 
 */
package Gestores;

import java.io.File;
import Dominio.Directorio;
import Persistencia.Rutas;

/**
 * @author Rocchietti Martin
 * 
 *  
 */
public final class GestorDirectorio
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
                gestor.analizar();
            }
        }
        //ordenar la lista de posteo

        GestorListaPosteo glp = new GestorListaPosteo();
        glp.ordenarListaPosteo();

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