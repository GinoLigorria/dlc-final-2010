/*
 * Creado el 19-jun-2010
 * 
 */

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Administrador
 * 
 *  
 */
public final class Documento
{
    private String ruta;

    private String brief;

    private long modificado;
    
    private long creado;
    
    private long tamanio;
    
    private String tipo;

    private static final int TAM_RUTA = Integer.valueOf("256").intValue();

    public static final int TAM_BRIEF = Integer.valueOf("128").intValue();

    /**
     *  
     */
    public Documento()
    {
        super();
        ruta = "";
        brief = "";
        tipo = "";
    }

    /*
     * (sin Javadoc)
     * 
     * @see ar.com.matheu.buscador.pers.Grabable#sizeOf()
     */
    public int sizeOf()
    {
        return TAM_RUTA * 2 + TAM_BRIEF * 2 + 8 + 8 + 8;
    }

    /*
     * (sin Javadoc)
     * 
     * @see ar.com.matheu.buscador.pers.Grabable#grabar(java.io.RandomAccessFile)
     */
    public void grabar(RandomAccessFile raf)
    {
        try
        {
            //Register.writeString(raf, ruta, TAM_RUTA);
            //Register.writeString(raf, brief, TAM_BRIEF);
            raf.writeLong(creado);
            raf.writeLong(modificado);
            raf.writeLong(tamanio);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /*
     * (sin Javadoc)
     * 
     * @see ar.com.matheu.buscador.pers.Grabable#leer(java.io.RandomAccessFile)
     */
    public void leer(RandomAccessFile raf)
    {
        try
        {
            //ruta = Register.readString(raf, TAM_RUTA).trim();
            //brief = Register.readString(raf, TAM_BRIEF).trim();
            creado = raf.readLong();
            modificado = raf.readLong();
            tamanio = raf.readLong();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
     * (sin Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object arg0)
    {
        Documento aux = (Documento) arg0;
        return this.ruta.compareTo(aux.ruta);
    }

    public boolean equals(Object obj)
    {
        Documento aux = (Documento) obj;
        return this.ruta.equals(aux.ruta);
    }

    public int hashCode()
    {
        return ruta.hashCode();
    }

    public long getModificado()
    {
        return modificado;
    }

    public void setModificado(long modificado)
    {
        this.modificado = modificado;
    }

    public String getRuta()
    {
        return ruta;
    }

    public void setRuta(String ruta)
    {
        this.ruta = ruta;
    }

    public String getBrief()
    {
        return brief;
    }

    public void setBrief(String brief)
    {
        this.brief = brief;
    }
    public String getTipo()
    {
        return tipo;
    }
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    public long getCreado()
    {
        return creado;
    }
    public void setCreado(long creado)
    {
        this.creado = creado;
    }
    public long getTamanio()
    {
        return tamanio;
    }
    public void setTamanio(long tamanio)
    {
        this.tamanio = tamanio;
    }
}