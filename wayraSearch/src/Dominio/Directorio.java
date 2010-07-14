/*
 * Creado el 19-jun-2010
 * 
 */
package Dominio;

import java.io.File;

import Gestores.GestorDirectorio;

/**
 * @author Administrador
 * 
 *  
 */
public final class Directorio
{
    private File raiz;

    /**
     *  
     */
    public Directorio(File f)
    {
        super();
        raiz = f;
    }

    public void indizar()
    {
        GestorDirectorio.indizar(raiz);

    }
}