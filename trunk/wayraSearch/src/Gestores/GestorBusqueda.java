/*
 * Creado el 19-jun-2010
 * 
 */
package Gestores;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import Dominio.Busqueda;
import Dominio.Documento;
import Dominio.NodoListaPosteo;
import Dominio.Resultado;
import Dominio.Termino;
import Persistencia.Rutas;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Rocchietti Martin
 * 
 *  
 */
public final class GestorBusqueda
{

    /**
     *  
     */
    public GestorBusqueda()
    {
        super();
    }

    /**
     * @param b
     * @return Vector de Documentos
     */
    public Vector<Resultado> buscar(Busqueda b)
    {
        

        Vector r = null;

        Vector vPalabras = extraerPalabras(' ', b.getCriterio());
        //Vector vMas = extraerPalabras('+', b.getCriterio());
        //Vector vMenos = extraerPalabras('-', b.getCriterio());

        HashMap vocabulario = Rutas.getVocabulario().getVocabulario();

        //Obtenemos los objetos términos de las palabras ingresadas
        Vector vTerminos = GestorTermino.buscarTerminos(vPalabras, vocabulario);
        //chequear si encontro terminos para todos las palabras

        //Ordenar vector por frecuencias inversas //Decreciente
        vTerminos = ordenarTerminosPorIDF(vTerminos);


        //Obtener los primero R primeros Documentos con sus NLP de cada término
        Hashtable<Documento, Hashtable<Termino, NodoListaPosteo>> htDocumentos = obternerNodosListaPosteo(vTerminos, b.getCantidadResultados());

        // Obtener el módulo de la consulta

        Hashtable<Termino, Double> htPesosConsult = obtenerPesoConsulta(vTerminos);

        //obtenemos el módulo de cada Documento

        Hashtable<Documento, Hashtable<Termino, Double>> htPesosDocumentos = obtenerPesosDocumentos(htDocumentos);

        //Aplicar la funcion de Similitud

        Vector<Resultado> vResultados = obtenerSimilitud(htPesosConsult, htPesosDocumentos);

        //ordenar los archivos



        //vMas = GestorTermino.buscarTerminos(vMas, vocabulario);
        //vMenos = GestorTermino.buscarTerminos(vMenos, vocabulario);

        return vResultados;
    }

    /**
     *
     * @param nodosFinal
     * @return
     */
    private Vector buscarDocumentos(Vector v)
    {

        //ordenar el vector crecientemente de acuerdo a su idf
        Vector r = new Vector();
        Iterator iter = v.iterator();

        while (iter.hasNext())
        {
            NodoListaPosteo n = (NodoListaPosteo) iter.next();
            Documento d = n.buscarDocumento();
            if (!r.contains(d))
            {
                r.add(d);
            }
        }

        return r;
    }

    /**
     * Busca los Nodos en los que se encuentran el vector de terminos.
     * @param Vector de términos
     * @return Vector nodosListaPosteo de los términos
     */
    private Vector buscarNodos(Vector v)
    {
        Vector r = new Vector();
        Iterator iter = v.iterator();

        while (iter.hasNext())
        {
            Termino t = (Termino) iter.next();
            r.addAll(t.buscarNodos());
        }

        return r;
    }

    /**
     * Devuelve un vector de palabras en lowercase
     * @param criterio
     * @return
     */
    private Vector extraerPalabras(char prefijo, StringBuffer criterio)
    {
        Vector r = new Vector();
        criterio.insert(0, ' ');
        criterio.append(" ");

        StringBuffer buffer = new StringBuffer();
        boolean b = false;

        //recorro el string
        for (int i = 0; i < criterio.length(); i++)
        {
            char c = criterio.charAt(i);
            c = Character.toLowerCase(c);

            if (c == prefijo)
            {
                b = true;
            }
            //agrego únicamente letras y dígitos
            if (b && (Character.isLetterOrDigit(c) || c == '-'))
            {
                buffer.append(c);
            } else
            {
                //
                if (buffer.length() > 2)
                {
                    //verifico que no sea stop word
                    String palabra = buffer.toString();
                    if(!esStopWord(palabra))
                    {
                        Termino t = new Termino();
                        t.setTermino(palabra);
                        r.add(t);
                        buffer = new StringBuffer();    
                    }

                }
                if (c == prefijo)
                {
                    b = true;
                } else
                {
                    b = false;
                }
            }
        }

        return r;
    }
    /**
     * Cantidad de Documentos en la base
     * @return La cantidad de documentos de la base
     */
    public long contarDocumentos()
    {
        return Rutas.getArchivoDocu().registerCount();
    }

    private boolean esStopWord(String palabra) {

        Hashtable<String, String> hStopWords = Rutas.getStopWords();
        if (hStopWords.containsKey(palabra))
        {
            return true;
        }
        else
        {
            return false;
        }

    }


    private Vector ordenarTerminosPorIDF(Vector vSimples) {

        Comparator comp = new ComparadorTerminoCantidadDocs();
        Collections.sort(vSimples,comp);
        return vSimples;
    }
/**
 * Retorna los R primeros documentos buscando secuencialmente en el vector de
 * términos pasado x parámetro
 * @param cantidadResultados Cantidad de Documentos a devolver
 * @param vTerminos  vector de Términos
 * @return
 */
    private Vector obtenerNLP2(Vector vTerminos, int cantidadResultados) {

        Vector vNlp = new Vector();

        Iterator itr = vTerminos.iterator();

        //Debo obtener R
        int contador = 0;

        while(vNlp.size() < cantidadResultados && itr.hasNext())
        {

            Termino t = (Termino) itr.next();


            vNlp.add(t.getListaPosteo().buscarNodos(cantidadResultados - contador));


        }

        return vNlp;


    }

/**
 * Retorna un hash conteniendo por cada Documento un hash que contiene por cada término
 * un nodolistaposteo
 * @param cantidadResultados Cantidad de Documentos a devolver
 * @param vTerminos  vector de Términos
 * @return Hashtable
 */
    private Hashtable<Documento,Hashtable<Termino, NodoListaPosteo>> obternerNodosListaPosteo(Vector vTerminos, int cantidadResultados)
    {
        Hashtable<Documento,Hashtable<Termino, NodoListaPosteo>> htDocumentos = new Hashtable<Documento, Hashtable<Termino, NodoListaPosteo>>();
        Vector<NodoListaPosteo> vNlp = new Vector();

        Iterator itr = vTerminos.iterator();

        //recorre los términos de la query
        while( itr.hasNext())
        {

            Termino t = (Termino) itr.next();

            vNlp = t.getListaPosteo().buscarNodos(cantidadResultados);
            
            //recorrer el vector de NLP y por cada Documento crear una entrada en el hash
            
            Iterator itr2 = vNlp.iterator();
            while (itr2.hasNext())
            {
              NodoListaPosteo nlp = (NodoListaPosteo) itr2.next();
              Documento doc = nlp.buscarDocumento();
              //si en el hash ya está el documento 
              if (htDocumentos.contains(doc))
              {
                  //actualizo el vector de nodos
                  Hashtable<Termino, NodoListaPosteo> htaux = htDocumentos.get(doc);
                  htaux.put(t, nlp);   
                  htDocumentos.put(doc, htaux);
              }
              else
              {
                  //agrego al hash
                  Hashtable<Termino, NodoListaPosteo> htaux = new Hashtable<Termino, NodoListaPosteo>(); 
                  htaux.put(t, nlp);
                  htDocumentos.put(nlp.buscarDocumento(), htaux);
              }
              
             }
        }


        return htDocumentos;

    }

    private Hashtable<Termino, Double> obtenerPesoConsulta(Vector vTerminos) {

        Iterator itr = vTerminos.iterator();
        Hashtable<Termino,Double> htPesosConsulta = new Hashtable<Termino, Double>();
        Double pesoAbsoluto = 0.0;
        
        //calculo el numerador de la fórmula de peso
        // y la sumatoria para calcular el denominador

        while (itr.hasNext())
        {
            Termino t = (Termino) itr.next();
            double idf = t.getIdf();
            double tf = 1;
            double peso = (tf*idf);
            htPesosConsulta.put(t, peso);
            pesoAbsoluto += Math.pow(peso, 2);

        }
        //calculo peso absoluto

        pesoAbsoluto = Math.sqrt(pesoAbsoluto);

        //recalculo el peso

        while (itr.hasNext())
        {
            Termino t = (Termino) itr.next();
            Double peso = (Double) htPesosConsulta.get(t);
            peso =  peso / pesoAbsoluto;
        }

        return htPesosConsulta;
    }

    private Hashtable<Documento, Hashtable<Termino, Double>> obtenerPesosDocumentos(Hashtable<Documento,Hashtable<Termino, NodoListaPosteo>> htDocumentos) {

        Hashtable <Documento, Hashtable<Termino, Double>> htPesosDocumentos = new Hashtable<Documento, Hashtable<Termino, Double>>();

        Double pesoAbsoluto = 0.0;

        //Por cada Documento

        Enumeration<Documento> enumerador = htDocumentos.keys();
        while (enumerador.hasMoreElements())
        {

            Hashtable<Termino, Double>  htPesosTerminos = new Hashtable<Termino, Double>();
            //Calcular el peso de este Documento

           Documento doc = enumerador.nextElement();
           Hashtable<Termino, NodoListaPosteo> htTerminos= htDocumentos.get(doc);

           Enumeration<Termino> enr = htTerminos.keys();
           //calculo el numerador
           while (enr.hasMoreElements())
           {
               Termino t = (Termino)enr.nextElement();
               NodoListaPosteo NLP = htTerminos.get(t);
               double idf = t.getIdf();
               double tf = NLP.getFrecTermino();
               double peso = (tf*idf);
               htPesosTerminos.put(t, peso);
               htPesosDocumentos.put(doc, htPesosTerminos);
               pesoAbsoluto += Math.pow(peso, 2);
           }
           //calculo peso absoluto
           pesoAbsoluto = Math.sqrt(pesoAbsoluto);

           //recalculo el peso

                while (enr.hasMoreElements())
            {
                Termino t = (Termino) enr.nextElement();
                Double peso = (Double) htPesosDocumentos.get(doc).get(t);
                peso =  peso / pesoAbsoluto;
            }

        }
        return htPesosDocumentos;
    }

    private Vector<Resultado> obtenerSimilitud(Hashtable<Termino, Double> htPesosConsult, Hashtable<Documento, Hashtable<Termino, Double>> htDocumentos) {

        Vector<Resultado> vResult = new Vector<Resultado>();


        //Por cada documento

        Enumeration<Documento> enumer = htDocumentos.keys();
        while(enumer.hasMoreElements())
        {
            Resultado result = new Resultado();
            Documento doc = enumer.nextElement();
            Double sim= 0.0;
            Hashtable<Termino, Double> htPesosDoc = htDocumentos.get(doc);
            //Recorrer el hash de consulta
            Enumeration<Termino> enum2 = htPesosConsult.keys();
            while (enum2.hasMoreElements())
            {
                //Producto escalar
                //sumatoria de los productos de los pesos de los términos

                Termino term = enum2.nextElement();
                sim += (htPesosConsult.get(term) * (htPesosDoc.get(term)== null? 0.0 :htPesosDoc.get(term)));

            }

            result.setDoc(doc);
            result.setSimilitud(sim);
            vResult.add(result);

        }
        return vResult;
    }



}