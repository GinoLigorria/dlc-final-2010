/*
 * Creado el 23-feb-2007
 * 
 */
package Persistencia.xml;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Dominio.NodoListaPosteo;

/**
 * @author Administrador
 * 
 *  
 */
public class HandlerListaPosteoXML extends DefaultHandler
{

    private Vector vNodos;

    private long cantidad;

    /**
     * @param cantidad
     *  
     */
    public HandlerListaPosteoXML(int cantidad)
    {
        super();
        vNodos = new Vector();
        this.cantidad = cantidad;
    }

    public Vector getVNodos()
    {
        return vNodos;
    }

    public void setVNodos(Vector documentos)
    {
        vNodos = documentos;
    }

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException
    {
        if (qName.equalsIgnoreCase(ParserListaPosteoXML.DOCUMENTO) && vNodos.size() < cantidad)
        {           
            NodoListaPosteo nodo = new NodoListaPosteo();
            nodo.setPosicion(Long.valueOf(attributes.getValue(ParserListaPosteoXML.POSICION)).longValue());
            nodo.setFrecTermino(Long.valueOf(attributes.getValue(ParserListaPosteoXML.TF)).longValue());
            nodo.setTipo(attributes.getValue(ParserListaPosteoXML.TIPO));            
            
            vNodos.add(nodo);
        }
    }

    public long getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(long cantidad)
    {
        this.cantidad = cantidad;
    }
}