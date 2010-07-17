/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dominio;

/**
 *
 * @author Mateo Guzman
 */
public class Resultado {
    private Documento doc;
    private double similitud;

    /**
     * @return the doc
     */
    public Documento getDoc() {
        return doc;
    }

    /**
     * @param doc the doc to set
     */
    public void setDoc(Documento doc) {
        this.doc = doc;
    }

    /**
     * @return the similitud
     */
    public double getSimilitud() {
        return similitud;
    }

    /**
     * @param similitud the similitud to set
     */
    public void setSimilitud(double similitud) {
        this.similitud = similitud;
    }



}
