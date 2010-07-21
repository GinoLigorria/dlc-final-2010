/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wayrasearch;

import Dominio.Busqueda;
import Dominio.Documento;
import Dominio.NodoListaPosteo;
import Dominio.Vocabulario;
import Exceptions.RegistroInexistenteException;
import Gestores.GestorBusqueda;
import Gestores.GestorDirectorio;
import Persistencia.Register;
import Persistencia.RegisterFile;
import Persistencia.Rutas;
import java.io.Console;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    //  testIndexar();
    //testBuscar();
    //calcularTamanioTotalArchivos();

    comprobarSanidadArchivos();
        

          

    }

    public static void testBuscar()
    {
                // BUSCAR

        //genero un objeto búsqueda
        Busqueda busqueda = new Busqueda();
        StringBuffer criterio = new StringBuffer("possibilities");
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
        File DirectorioInicial = new File("D:\\Facu\\2009\\DLC\\DLC-Final-2010\\test");
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
        Rutas.getRFListaPosteo().add(nlp);


        //ahora pruebo leer

        for (int i = 0; i < Rutas.getRFListaPosteo().registerCount(); i++) {


            NodoListaPosteo nlp2 = new NodoListaPosteo();
            Register reg = new Register(nlp2);

            Rutas.getRFListaPosteo().rewind();
              while (!Rutas.getRFListaPosteo().eof())
              {
                   Rutas.getRFListaPosteo().read(reg);
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


    public static void comprobarSanidadArchivos()
    {
        String mensaje;
        RegisterFile<Documento> rfArchivoDocu = Rutas.getArchivoDocu();
        RegisterFile<NodoListaPosteo> rfListaPosteo = Rutas.getRFListaPosteo();
        Vocabulario vocabulario = Rutas.getVocabulario();
        long cantDocs = rfArchivoDocu.registerCount();
        //archivo Documentos
        if (rfArchivoDocu.exists())
        {
            //lo recorro y muestro su contenido

            System.out.println("Cantidad de Documentos: " + cantDocs);
            for (long i = 0; i < cantDocs; i++) {
                try {
                    Documento d = (Documento) rfArchivoDocu.getRegister(i).getData();
                    System.out.println("Documento: " + d.toString());
                } catch (RegistroInexistenteException ex) {
                    System.out.println("Error al leer Documento nro: " + i);
                }
            }

            System.out.println("Termino de verificar Archivo de Documentos");
        }
        else
        {
         mensaje = "No existe el archivo de Documentos\n"  ;
        }

        //archivo vocabulario

        if (vocabulario.getCantDocumentos()!= cantDocs)
        {
            System.out.println("La cantidad de Documentos del Vocabulario no coincide con la del archivo Documento");
        }
        else
        {
            if (!vocabulario.verificarSanidad())
            {
                System.out.println("El archivo de vocabulario contiene errores");
            }
        }

        //archivo listasDePosteo

        if (rfListaPosteo.exists())
        {
            //recorro la lista de posteo
            long cantNLP = rfListaPosteo.registerCount();
            System.out.println("Cantidad de Documentos: " + cantDocs);
            for (long i = 0; i < cantNLP; i++) {
                try {
                    NodoListaPosteo nlp = (NodoListaPosteo) rfListaPosteo.getRegister(i).getData();
                    System.out.println("Nodo LP: " + nlp.toString());
                } catch (RegistroInexistenteException ex) {
                    System.out.println("Error al leer el NLP nro: " + i);
                }
            }

            System.out.println("Termino de verificar Archivo de NLP");

        }
        else
        {
            System.out.println("No existe el archivo de Lista de Posteo");
        }

        //archivo listaDePosteoOrdenada



    }


}
