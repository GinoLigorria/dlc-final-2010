/*
 * Creado el 19-jun-2010
 * 
 */
package Dominio;

/**
 * @author Administrador
 * 
 *  
 */
public class Busqueda
{
    private StringBuffer criterio;
    private int cantidadResultados;

    /**
     *  
     */
    public Busqueda()
    {
        super();
    }

    public StringBuffer getCriterio()
    {
        return criterio;
    }

    public void setCriterio(StringBuffer criterio)
    {
        this.criterio = criterio;
    }

    public int getCantidadResultados()
    {
        return cantidadResultados;
    }
    public void setCantidadResultados(int cantidadResultados)
    {
        this.cantidadResultados = cantidadResultados;
    }
}