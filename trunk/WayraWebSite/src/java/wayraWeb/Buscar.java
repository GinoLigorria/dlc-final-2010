/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wayraWeb;

import Dominio.Busqueda;
import Dominio.Documento;
import Dominio.Resultado;
import Gestores.GestorBusqueda;
import Persistencia.Rutas;
import java.util.ArrayList;
import javax.faces.FacesException;
import java.util.Vector;
import java.util.Iterator;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version Buscar.java
 * @version Created on 18/07/2010, 23:12:00
 * @author Rocchietti Martin
 */
public class Buscar  {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private ArrayList<Documento> documentos = new ArrayList <Documento>();
    private String cadenaDeBusqueda;
    Vector <Resultado> v;

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Buscar() {
    }



    public ArrayList<Documento> hacerBusqueda(String b)
    {

        Busqueda busqueda = new Busqueda();
        StringBuffer criterio = new StringBuffer(b);
        busqueda.setCriterio(criterio);
        busqueda.setCantidadResultados(5000);
        //levanto el archivo de vocabulario
        Rutas.materializarVocabulario();
        GestorBusqueda gbusqueda = new GestorBusqueda();
        v=gbusqueda.buscar(busqueda);
        Iterator i = v.iterator();
        while (i.hasNext())
        {
            Documento d=((Resultado)i.next()).getDoc();
            documentos.add(d);
        }


        //debo llenar el arraylist documentos
        return documentos;
    }

    /**
     * @return the documentos
     */
    public // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">
    ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(ArrayList<Documento> documentos) {
        this.setDocumentos(documentos);
    }

    /**
     * @return the cadenaDeBusqueda
     */
    public String getCadenaDeBusqueda() {
        return cadenaDeBusqueda;
    }

    /**
     * @param cadenaDeBusqueda the cadenaDeBusqueda to set
     */
    public void setCadenaDeBusqueda(String cadenaDeBusqueda) {
        this.cadenaDeBusqueda = cadenaDeBusqueda;
    }



}


