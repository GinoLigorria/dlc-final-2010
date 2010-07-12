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
import java.util.Date;

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
    //calcularTamanioTotalArchivos();
        

          

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
         Date dateobj = new Date();
         long startTime = dateobj.getTime();
         System.out.println("Indización iniciada: " + startTime);

         GestorDirectorio.indizar(DirectorioInicial);

         Date dateobj2 = new Date();
         long endTime = dateobj2.getTime();
         System.out.println("Inidzacion finalizada: " + endTime);
         System.out.println("Duracion: " + ((endTime-startTime)/1000) );

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

    public static void calcularTamanioTotalArchivos()
    {
        File DirectorioInicial = new File("C:\\Users\\Mateo Guzman\\Documents\\Faku\\DLC\\TPFinal\\DLC-Final-2010\\rfc");
        //
        long tamanioTotal =0;
        if (DirectorioInicial.isDirectory())
        {
            int cantArchivos=0;
             File[] archivos = DirectorioInicial.listFiles();
             for (int i = 0; i < archivos.length; i++) {
                File file = archivos[i];
                if (file.isFile()){
                System.out.println("Archivo: " + file.getName()+ "\t tamanio: " + (file.length()/1024));
                tamanioTotal+=file.length()/1024;
                cantArchivos++;

                }

             }
             System.out.println("Cantidad de archivos: " + cantArchivos);
             System.out.println("Promedio de tamanio de archivos: " + (tamanioTotal/cantArchivos));

             System.out.println("Tamanio total: " + tamanioTotal +"kb");


        }
    }


}
