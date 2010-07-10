/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wayrasearch;

import Dominio.Busqueda;
import Dominio.NodoListaPosteo;
import Gestores.GestorBusqueda;
import Gestores.GestorDirectorio;
import Persistencia.Register;
import Persistencia.Rutas;
import java.io.Console;
import java.io.File;

/**
 *
 * @author Mateo Guzman
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

    //testInsercionListaPosteo();
    //Rutas.borrar();
    testIndexar();
    //testBuscar();

          

    }

    public static void testBuscar()
    {
                // BUSCAR

        //genero un objeto búsqueda
        Busqueda busqueda = new Busqueda();
        StringBuffer criterio = new StringBuffer("loop");
        busqueda.setCriterio(criterio);
        busqueda.setCantidadResultados(10);
        //levanto el archivo de vocabulario
        Rutas.materializarVocabulario();
        GestorBusqueda gbusqueda = new GestorBusqueda();
        gbusqueda.buscar(busqueda);


    }

    public static void testIndexar()
    {
            //INDIZAR
        //Seteo el Directorio a indizar
        File DirectorioInicial = new File("C:\\Users\\Mateo Guzman\\Documents\\Faku\\DLC\\TPFinal\\DLC-Final-2010\\test");
        //
        if (DirectorioInicial.isDirectory())
        {
         GestorDirectorio.indizar(DirectorioInicial);
        }
        else
        {
            System.out.print("Hey you entered a non directory file. Check it out bro!");
        }

    }


    public static void testInsercionListaPosteo()
    {
        //crear nodosListaPosteo
        NodoListaPosteo nlp = new NodoListaPosteo();
        nlp.setFrecTermino(2);
        nlp.setPosicion(2);
        nlp.setTipo("txt");

        //creo register File
        Rutas.getListaPosteo().add(nlp);


        //ahora pruebo leer

        for (int i = 0; i < Rutas.getListaPosteo().registerCount(); i++) {


            NodoListaPosteo nlp2 = new NodoListaPosteo();
            Register reg = new Register(nlp2);

            Rutas.getListaPosteo().rewind();
              while (!Rutas.getListaPosteo().eof())
              {
                   Rutas.getListaPosteo().read(reg);
                   System.out.println(reg.getData().toString());
              }
        }

    }


}
