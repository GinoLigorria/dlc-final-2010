/*
 * Creado el 20-Jun-2010
 * 
 */
package Persistencia.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Dominio.NodoListaPosteo;

//import java.lang.Object.org.apache.xerces.internal.dom.DocumentImpl;

//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * @author Administrador
 * 
 *  
 */
public class ParserListaPosteoXML implements Serializable
{
    private File archivo;

    private static final String RUTA_LISTAS = "." + File.separator + "terminos";

    private static final String FMAX = "FMAX";

    private static final String NR = "NR";

    public static final String POSICION = "POSICION";

    public static final String TF = "TF";

    public static final String DOCUMENTO = "DOCUMENTO";

    private static final String TERMINO = "TERMINO";

    private static final String VALOR = "VALOR";

    public static final String TIPO = "TIPO";

    /**
     * @param termino
     *  
     */
    public ParserListaPosteoXML(String termino)
    {
        super();
        File ruta = new File(RUTA_LISTAS);
        ruta.mkdir();

        int end = 2;
        if (termino.length() < 2)
        {
            end = 1;
        }

        File subDir = new File(RUTA_LISTAS + File.separator
                + termino.substring(0, end));
        subDir.mkdir();

        File f = new File(subDir.getAbsolutePath() + File.separator + termino
                + "_lp.xml");
/*
        try
        {
            archivo = f;
            f.delete();
            f.createNewFile();

            Element e = null;
            Node n = null;
            //         Document (Xerces implementation only).
            Document xmldoc = new DocumentImpl();
            //         Root element.
            Element root = xmldoc.createElement(TERMINO);
            root.setAttribute(VALOR, termino);
            root.setAttribute(FMAX, "0");
            root.setAttribute(NR, "0");

            xmldoc.appendChild(root);

            serializarXML(archivo, xmldoc);
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
*/
    }

    /**
     * @param f
     * @param xmldoc
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void serializarXML(File f, Document xmldoc)
            throws FileNotFoundException, IOException
    {
        /*
        FileOutputStream fos = new FileOutputStream(f);
        //         XERCES 1 or 2 additionnal classes.
        OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
        of.setIndent(1);
        of.setIndenting(true);

        File fi = f.getParentFile().getParentFile().getParentFile();
        // String ruta = "file://" +
        // fi.getAbsolutePath().replace('\\','/')+'/'+"terminos.dtd";
        // of.setDoctype(null, ruta);
        XMLSerializer serializer = new XMLSerializer(fos, of);
        //         As a DOM Serializer
        serializer.asDOMSerializer();
        serializer.serialize(xmldoc.getDocumentElement());
        fos.close();
         *
         */
    }

    /**
     * @param nodo
     */
    public void insertar(NodoListaPosteo nodo)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try
        {
            builder = factory.newDocumentBuilder();
            Document xmldoc = builder.parse(archivo);

            Element root = xmldoc.getDocumentElement();
            long fmax = Long.valueOf(root.getAttribute(FMAX)).longValue();
            long nr = Long.valueOf(root.getAttribute(NR)).longValue();
            root.setAttribute(NR, (nr + 1) + "");

            NodeList rootlist = root.getChildNodes();

            int j = 0;
            Element nodoActual = null;
            for (int i = 0; i < rootlist.getLength(); i++)
            {
                Node n = rootlist.item(i);

                if (n instanceof Element)
                {
                    nodoActual = (Element) n;

                    long tf = Long.valueOf(nodoActual.getAttribute(TF))
                            .longValue();

                    if (tf < nodo.getFrecTermino())
                    {
                        break;
                    }
                    j++;
                }
            }

            if (j == 0)
            {
                root.setAttribute(FMAX, nodo.getFrecTermino() + "");
            }

            Element nuevoNodo = xmldoc.createElement(DOCUMENTO);
            nuevoNodo.setAttribute(POSICION, nodo.getPosicion() + "");
            nuevoNodo.setAttribute(TF, nodo.getFrecTermino() + "");
            nuevoNodo.setAttribute(TIPO, nodo.getTipo());
            root.insertBefore(nuevoNodo, nodoActual);

            serializarXML(archivo, xmldoc);
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        } catch (SAXException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Vector buscarNodos(int cantidad)
    {
        Vector r = null;

        SAXParserFactory spf = SAXParserFactory.newInstance();
        try
        {
            SAXParser sp = spf.newSAXParser();
            HandlerListaPosteoXML handler = new HandlerListaPosteoXML(cantidad);
            sp.parse(archivo, handler);

            r = handler.getVNodos();

        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        } catch (SAXException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return r;
    }

    public static File getFileListas()
    {
        return new File(RUTA_LISTAS);
    }
}