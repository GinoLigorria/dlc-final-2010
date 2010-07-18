/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Yaka
 */
@ManagedBean(name="Buscar")
@RequestScoped
public class Buscar {

    ArrayList<Documento> documentos = null;

    /** Creates a new instance of Buscar */
    public Buscar() {
    }
    public String hacerBusqueda(String palabraABuscar)
    {
        //Buscar los documentos en la BD
        //documentos = ManejadorBD.obtenerDocumentos(palabraABuscar);
        return null;
    }

}
