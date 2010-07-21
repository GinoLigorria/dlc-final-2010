/*
 * Creado el 20-feb-2007
 * 
 */
package Persistencia.xml;

import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Lectores.Lector;

/**
 * @author Administrador
 * 
 *  
 */
public class ParserLectorXML
{
    private Vector vectorLectores;
    /**
     *  
     */
    public ParserLectorXML()
    {
        super();

        vectorLectores = new Vector();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse("Lectores.xml");

            parsearDOM(document);              
           
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
    
    public void parsearDOM(Node node)
    {
        int type = node.getNodeType();
        switch (type)
        {
            // print element with attributes
            case Node.ELEMENT_NODE:
            {
                if (node.getNodeName().equalsIgnoreCase("lector"))
                {
                    NamedNodeMap attrs = node.getAttributes();
                    for (int i = 0; i < attrs.getLength(); i++)
                    {
                        Node attr = attrs.item(i);
                        try
                        {
                            Lector l = (Lector) Class
                                    .forName(attr.getNodeValue().trim())
                                    .newInstance();
                            vectorLectores.add(l);
                        } catch (DOMException e)
                        {
                            e.printStackTrace();
                        } catch (InstantiationException e)
                        {
                            e.printStackTrace();
                        } catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }

                }

                NodeList children = node.getChildNodes();
                if (children != null)
                {
                    int len = children.getLength();
                    for (int i = 0; i < len; i++)
                        parsearDOM(children.item(i));
                }

                break;
            }

            // print text
            case Node.TEXT_NODE:
            {
                if (node.getNodeValue().trim().length() > 0)
                {
                    Lector l = (Lector) vectorLectores.lastElement();
                    l.getTipos().add(node.getNodeValue().trim());                    
                }
                break;
            }

            // print the document element
            default:
            {
                parsearDOM(((Document) node).getDocumentElement());
                break;
            }

        }
    }

    public Vector getVectorLectores()
    {
        return vectorLectores;
    }
    public void setVectorLectores(Vector vectorLectores)
    {
        this.vectorLectores = vectorLectores;
    }
}