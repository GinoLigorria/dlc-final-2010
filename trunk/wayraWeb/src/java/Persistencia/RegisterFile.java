package Persistencia;

/**
 * Una clase para representar un archivo binario de registros de longitud fija, con acceso directo. El contenido de un
 * RegisterFile son registros uniformes (del mismo tipo) y de la misma longitud, lo cual favorece el seeking. El archivo
 * no permite grabar objetos cuyo tipo (y tama�o) no coincida con los que se indicaron al crear el objeto.
 * @author  Ing. Valerio Frittelli
 * @version Marzo de 2008
 */


import Exceptions.RegistroInexistenteException;
import java.io.*;

public class RegisterFile < E extends Grabable >
{
    private File fd; // descriptor del archivo para acceder a sus propiedades

    // externas

    private RandomAccessFile maestro; // objeto para acceder al contenido del

    // archivo

    private Grabable testigo; // representa al contenido grabado en el archivo

    //private Register reg; // auxiliar para operaciones internas



    /**
     * Crea un manejador para el RegisterFile, asociando al mismo el nombre "newfile.dat " a modo de file descriptor.
     * Abre el archivo en disco asociado con ese file descriptor, en el directorio default, y en modo "r" (tal como se
     * se usa en la clase RandomAccessFile). El par�metro obj se usa para tomar la clase de los objetos que ser�n
     * almacenados, pero ese objeto no se graba en el archivo (puede venir con valores por default: s�lo importa su
     * clase).
     * @param obj un objeto de la clase base para el archivo.
     */
    public RegisterFile( E obj )
    {
        this ("newfile.dat", "r", obj);
    }

    /**
     * Crea un manejador para el RegisterFile, asociando al mismo un nombre a modo de
     * file descriptor, y un tipo de contenido al que quedar� fijo. El segundo
     * par�metro se usa para fijar el tipo de registro que ser� aceptado para
     * grabar en el archivo. No se crea el archivo en disco, ni se abre. S�lo se
     * crea un descriptor general para �l. La apertura y eventual creaci�n, debe
     * hacerse con el m�todo openForReadWrite().
     * 
     * @param nombre
     *            es el nombre f�sico del archivo a crear
     * @param r
     *            una instancia de la clase a la que pertenecen los objetos
     *            cuyos datos ser�n grabados. La instancia referida por r NO
     *            ser� grabada en el archivo
     * @throws ClassNotFoundException
     *             si no se informa correctamente el tipo de registro a grabar
     */

        /**
     * Crea un manejador para el RegisterFile, asociando al mismo un nombre a modo de file descriptor. Abre el archivo en
     * disco asociado con ese file descriptor, en el modo indicado por el segundo par�metro. El modo de apertura es tal y
     * como se usa en la clase RandomAccessFile: "r" para s�lo lectura y "rw" para lectura y grabaci�n. Si el modo de
     * apertura enviado es null, o no es "r" ni "rw", se asumir� "r". El par�metro obj se usa para tomar la clase de los
     * objetos que ser�n almacenados, pero ese objeto no se graba en el archivo (puede venir con valores por default: s�lo
     * importa su clase).
     * @param nombre es el nombre f�sico y ruta del archivo a crear.
     * @param modo es el modo de apertura del archivo: "r" ser� s�lo lectura y "rw" ser� lectura y grabaci�n.
     * @param obj un objeto de la clase base para el archivo.
     */
    public RegisterFile (String nombre, String modo, E obj)
    {
        this (new File(nombre), modo, obj);
    }

     /**
     * Crea un manejador para el RegisterFile, asociando al mismo un file descriptor. Abre el archivo en disco asociado
     * con ese file descriptor, en el modo indicado por el segundo par�metro. El modo de apertura es tal y como se usa
     * en la clase RandomAccessFile: "r" para s�lo lectura y "rw" para lectura y grabaci�n. Si el modo de apertura
     * enviado es null, o no es "r" ni "rw", se asumir� "r". El par�metro obj se usa para tomar la clase de los
     * objetos que ser�n almacenados, pero ese objeto no se graba en el archivo (puede venir con valores por default:
     * s�lo importa su clase).
     * @param file es el pathname del archivo a crear.
     * @param modo es el modo de apertura del archivo: "r" ser� s�lo lectura y "rw" ser� lectura y grabaci�n.
     * @param obj un objeto de la clase base para el archivo.
     */
    public RegisterFile (File file, String modo, E obj)
    {
        if (obj == null) { throw new NullPointerException ("No se indic� la clase a grabar..."); }
        testigo = obj;
        fd = file;
        if ( modo == null || ( !modo.equals("r") && !modo.equals("rw") ) )
        {
            modo = "r";
        }

        try
        {
            maestro = new RandomAccessFile(fd, modo);
        }
        catch(IOException e)
        {
            System.out.println( "No se pudo abrir el archivo: " + e.getMessage());
            System.exit(1);
        }
    }

    //obsoleto
//    public RegisterFile(String nombre, Grabable r) throws ClassNotFoundException
//    {
//        if (r == null)
//            throw new ClassNotFoundException(
//                    "Clase incorrecta o inexistente para el tipo de registro");
//
//        testigo = r;
//        reg = new Register(r);
//
//        fd = new File(nombre);
//    }

    /**
     * Acceso al descriptor del archivo
     * 
     * @return un objeto de tipo File con las propiedades de file system del
     *         archivo
     */
    public File getFileDescriptor()
    {
        return fd;
    }

    /**
     * Acceso al manejador del archivo binario
     * 
     * @return un objeto de tipo RandomAccessFile que permite acceder al bloque
     *         f�sico de datos en disco, en forma directa
     */
    public RandomAccessFile getMasterFile()
    {

        return maestro;
    }


      /*
     * NUEVO!!
     *retorna un registro ubicado en la posicion pasada por parametro si la posicion esta fuera o es mayor del
     *tama�o del registro se produce una excepcion
     */
    public Register getRegister(long index)throws RegistroInexistenteException
    {
        Register reg=null;
        try {
            reg = new Register(testigo.getClass().newInstance());
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

         if(this.length()-1<index*reg.sizeOf())
        {
               throw new RegistroInexistenteException();
        }
            seekRegister(index);
            read(reg);


        return reg;
    }


     /*
     * NUEVO!!
     graba un registro pasado por parametro en la posicion pasada por parametro, si la posicion no existe
     *lanza una excepcion*/

    public void writeRegister(Register reg,long k) throws RegistroInexistenteException
    {

        if(this.length()<k*reg.sizeOf())
            {
                throw new RegistroInexistenteException();
            }

        seekRegister(k);
        write(reg);
    }



    /**
     * Borra el RegisterFile del disco
     */
    public boolean  delete()
    {
        try
        {
        if (maestro != null)
        {
            maestro.close();
        }
        }
        catch (IOException e)
        {

        }
        return fd.delete();
    }

    public boolean exists()
    {
        return fd.exists();
    }
    public boolean canDelete()
    {
        return fd.canWrite();
    }
    /**
     * Cambia el nombre del archivo
     * 
     * @param nuevo
     *            otro RegisterFile, cuyo nombre (o file descriptor) ser� dado al
     *            actual
     */
    public boolean rename(RegisterFile nuevo)
    {
        return fd.renameTo(nuevo.fd);
    }

    /**
     * Abre el archivo en modo de s�lo lectura. El archivo en disco debe existir
     * previamente. Queda posicionado al principio del archivo.
     * 
     * @throws FileNotFoundException
     * OBSOLETO
     */
    public void openForRead() throws FileNotFoundException
    {
        maestro = new RandomAccessFile(fd, "r");
    }

    /**
     * Abre el archivo en modo de lectura y grabaci�n. Si el archivo en disco no
     * exist�a, ser� creado. Si exist�a, ser� posicionado al principio del
     * archivo. Mueva el puntero de registro activo con el m�todo seekRegister()
     * o con seekByte().
     * OBSOLETO
     */
    public void openForReadWrite()
    {
        try
        {
            maestro = new RandomAccessFile(fd, "rw");
        } catch (IOException e)
        {
            System.out.println("Error de apertura archivo " + fd.getName()
                    + ": " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Cierra el archivo
     */
    public void close()
    {
        try
        {
            maestro.close();
        } catch (IOException e)
        {
            System.out.println("Error al cerrar el archivo " + fd.getName()
                    + ": " + e.getMessage());
            System.exit(1);
        }
    }



    /**
     * Ubica el puntero de registro activo en la posici�n del registro n�mero i.
     * Se supone que los registros grabados son del mismo tipo, y que la
     * longitud de los registros es uniforme.
     * 
     * @param i
     *            n�mero relativo del registro que se quiere acceder
     */
    public void seekRegister(long i)
    {
        Register reg = new Register (testigo);
        try
        {
            maestro.seek(i * reg.sizeOf());
        } catch (IOException e)
        {
            System.out.println("Error al posicionar en el registro n�mero " + i
                    + ": " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Ubica el puntero de registro activo en la posici�n del byte n�mero b
     * 
     * @param b
     *            n�mero del byte que se quiere acceder, contando desde el
     *            principio del archivo
     * @throws IOException
     *             si hubo problema en el posicionamiento
     */
    public void seekByte(long b)
    {
        try
        {
            maestro.seek(b);
        } catch (IOException e)
        {
            System.out.println("Error al posicionar en el byte n�mero " + b
                    + ": " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Rebobina el archivo: ubica el puntero de registro activo en la posici�n
     * cero
     */
    public void rewind()
    {
        try
        {
            maestro.seek(0);
        } catch (IOException e)
        {
            System.out.println("Error al rebobinar el archivo: "
                    + e.getMessage());
            System.exit(1);
        }
    }



    /**
     * Devuelve el n�mero de byte en el cual est� posicionado el archivo en este momento.
     * @return el n�mero de byte de posicionamiento actual.
     */
    public long bytePos ()
    {
        try
        {
            return maestro.getFilePointer();
        }
        catch(IOException e)
        {
            System.out.println( "Error al intentar devolver el numero de byte: " + e.getMessage());
            System.exit(1);
        }

        return -1;
    }


    /**
     * Devuelve el n�mero relativo del registro en el cual esta posicionado el
     * archivo en este momento
     * 
     * @return el n�mero del registro actual
     */
    public long registerPos()
    {
        Register reg = new Register (testigo); //nuevo Mateo
        try
        {
            return maestro.getFilePointer() / reg.sizeOf();
        } catch (IOException e)
        {
            System.out
                    .println("Error al intentar devolver el n�mero de registro: "
                            + e.getMessage());
            System.exit(1);
        }

        return -1;
    }


    /**
     * Posiciona el puntero de registro activo al final del archivo
     */
    public void goFinal()
    {
        try
        {
            maestro.seek(maestro.length());
        } catch (IOException e)
        {
            System.out.println("Error al posicionar al final: "
                    + e.getMessage());
            System.exit(1);
        }
    }

     /**
     * Retorna la cantidad de registros que tiene el archivo.
     * @return la cantidad de registros del archivo.
     */
    public long registerCount()
    {
        long n = 0;
        Register reg = new Register(testigo);

        try
        {
            n = maestro.length() / reg.sizeOf();
        }
        catch(IOException e)
        {
            System.out.println( "Error al medir la cantidad de regsitros del archivo " + fd.getName() + ": " + e.getMessage());
            System.exit(1);
        }

        return n;
    }

    /**
     * Determina si se ha llegado al final del archivo o no
     * 
     * @return true si se lleg� al final - false en caso contrario
     * @throws IOException
     *             si hubo problema en la operaci�n
     */
    public boolean eof()
    {
        try
        {
            if (maestro.getFilePointer() < maestro.length())
                return false;
            else
                return true;
        } catch (IOException e)
        {
            System.out.println("Error al determinar el fin de archivo: "
                    + e.getMessage());
            System.exit(1);
        }

        return true;
    }

         public void setMasterFile(RandomAccessFile r)
    {
        maestro=r;
    }

    public void setFileDescriptor(File fd)
    {
        this.fd=fd;
    }

    /**
     * Lee un registro del archivo
     * 
     * @return el registro leido
     * @throws IOException
     *             si hubo problema en la operaci�n
     */
    public Register leer()
    {
        Register r = null;

        try
        {
            r = new Register((Grabable) testigo.getClass().newInstance());
            r.leer(maestro);

        } catch (Exception e)
        {
            System.out.println("Error al leer el registro: " + e.getMessage());
            System.exit(1);
        }

        return r;
    }

    /**
     * Lee un registro del archivo, a partir de la posición del file pointer en ese momento. El archivo se supone
     * abierto.
     * @param obj el registro mediante el cual se hará la lectura. Los valores leidos vuelven en ese mismo objeto.
     * @return true si la lectura pudo hacerse - false en caso contrario.
     */

    public boolean read (Register r)
    {
        if(r != null)
        {
            try
            {
                r.leer(maestro);
            }
            catch(Exception e)
            {
                System.out.println( "Error al leer el registro ");

                System.exit(1);
            }
            return true;
        }
        return false;
    }

    /**
     * Busca un registro en el archivo. Si la clase del registro que se busca no
     * coincide con la clase de los registros grabados en el archivo, retorna
     * -1. En general, el retorno de -1 significa que el registro no fue
     * encontrado.
     * 
     * @param r
     *            objeto a buscar en el archivo
     * @return la posici�n de byte del registro en el archivo, si existe, o -1
     *         si no existe
     * OBSOLETO
     */
    public long buscar(Register r)
    { Register reg = new Register(r);
        if (r == null || testigo.getClass() != r.getData().getClass())
            return -1;

        long pos = -1, actual = bytePos();
        rewind();
        while (!eof())
        {
            reg = leer();
            if (reg.getData().equals(r.getData()) && reg.isActive())
            {
                pos = bytePos() - reg.sizeOf();
                break;
            }
        }
        seekByte(actual);
        return pos;
    }



     /**
     * Busca un registro en el archivo. Retorna -1 si el registro no se encuentra, o el n�mero de byte donde comienza la
     * primera ocurrencia del registro en disco si se encontraba en el archivo. En general, el retorno de -1 significa que
     * el registro no fue encontrado, por cualquier causa. El file pointer sale apuntando al mismo byte donde estaba
     * cuando empez� la operaci�n.
     * @param obj el objeto a buscar en el archivo.
     * @return la posici�n de byte de la primera ocurrencia del registro en el archivo, si existe, o -1 si no existe.
     */
    public long search (E obj)
    {
        if( obj == null ) return -1;
        long pos = -1, actual;
        try
        {
            Grabable r2 = obj.getClass().newInstance();
            Register reg = new Register(r2);
            actual = bytePos();

            rewind();
            while (!eof())
            {
                read(reg);
                if (reg.getData().equals(obj) && reg.isActive())
                {
                    pos = bytePos() - reg.sizeOf();
                    break;
                }
            }
            seekByte(actual);
        }
        catch(Exception e)
        {
             System.out.println( "Error al buscar el registro: " + e.getMessage());
             System.exit(1);
        }
        return pos;
    }

    /**
     * Busca un registro en el archivo mediante b�squeda binaria. El archivo se supone ordenado de acuerdo a las
     * convenciones de compareTo(), y efect�a la b�squeda de acuerdo al mismo m�todo de comparaci�n. Retorna -1
     * si el registro no se encuentra, o el n�mero de byte donde comienza la primera ocurrencia del registro en disco
     * si se encontraba en el archivo. En general, el retorno de -1 significa que el registro no fue encontrado, por
     * cualquier causa. El file pointer sale apuntando al mismo byte donde estaba cuando empez� la operaci�n.
     * @param obj el objeto a buscar en el archivo.
     * @return la posici�n de byte de la primera ocurrencia del registro en el archivo, si existe, o -1 si no existe.
     */
    public long binarySearch(E obj)
    {
        if( obj == null ) return -1;
        long pos = -1;
        try
        {
            long n = registerCount();
            long izq = 0, der = n - 1;
            long c;

            long actual = bytePos();
            Grabable r2 = obj.getClass().newInstance();
            Register reg = new Register(r2);

            while ( izq <= der && pos == -1 )
            {
                c = ( izq + der ) / 2;
                seekRegister( c );
                read(reg);

                if (reg.getData().equals(obj) && reg.isActive())
                {
                    pos = bytePos() - reg.sizeOf();
                    break;
                }
                else
                {
                    if( obj.compareTo( reg.getData() ) > 0 ) { izq = c + 1; }
                    else { der = c - 1; }

                }
            }
            seekByte(actual);
        }
        catch(Exception e)
        {
             System.out.println("Error al buscar el registro: " + e.getMessage());
             System.exit(1);
        }
        return pos;
    }

    /**
     * Agrega un registro en el archivo, controlando que no haya repetici�n y
     * que la clase del nuevo registro coincida con la clase indicada para el
     * archivo al invocar al constructor. El archivo debe estar abierto en modo
     * de grabaci�n.
     * 
     * @param r
     *            registro a agregar
     * @return la posicion en la que fue grabado el registro
     */
    public long alta(E obj)
    {
        long pos;
        long ret = -1;

        if (obj != null)
        {
            //openForReadWrite();

            try
            {
                pos = search(obj);
                if (pos == -1)
                {
                    goFinal();
                    ret = registerPos();
                    write(new Register(obj));
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            //close();
        }

        return ret;
    }


    /**
     * Agrega un registro en el archivo, controlando que no haya repetici�n. El archivo debe estar abierto en modo de
     * grabaci�n. El archivo vuelve abierto.
     * @param obj el objeto a agregar.
     * @return true si fue posible agregar el registro - false si no fue posible.
     */
    public boolean add (E obj)
    {
        boolean resp = false;
        long pos;

        if( obj != null )
        {
            try
            {
                pos = search(obj);
                if (pos == -1)
                {
                    goFinal();
                    write(new Register(obj));
                    resp = true;
                }
            }
            catch(Exception e)
            {
                System.out.println( "Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
    }

  
/**
     * Agrega un registro en el archivo, sin controlar repetición. El archivo debe estar abierto en modo de grabación.
     * El archivo vuelve abierto.
     * @param obj el registro a agregar.
     * @return la posición del registro grabado
     */

    public long altaDirecta (E obj)
    {
        long resp = -1;

        if( obj != null )
        {
            try
            {
                goFinal();
                resp = registerPos();
                write(new Register(obj));
            }
            catch(Exception e)
            {
                System.out.println("Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
    }
    /**
     * Agrega un registro en el archivo, sin controlar repetición. El archivo debe estar abierto en modo de grabación.
     * El archivo vuelve abierto.
     * @param obj el registro a agregar.
     * @return true si fue posible agregar el registro - false si no fue posible.
     */
    public boolean append (E obj)
    {
        boolean resp = false;
        if( obj != null )
        {
            try
            {
                goFinal();
                write(new Register(obj));
                resp = true;
            }
            catch(Exception e)
            {
                System.out.println("Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
    }


    /**
     * Borra un registro del archivo. La clase del registro buscado debe
     * coincidir con la clase indicada para el archivo al invocar al
     * constructor. El archivo debe estar abierto en modo de grabaci�n. El
     * registro se marca como borrado, aunque sigue f�sicamente ocupando lugar
     * en el archivo
     * 
     * @param r
     *            registro a buscar y borrar
     * @return true si fue posible borrar el registro - false si no fue posible
     */
    /* OBSOLETO
    public boolean baja(Register r)
    {
        boolean resp = false;
        long pos;

        if (r != null && testigo.getClass() == r.getData().getClass())
        {
            openForReadWrite();

            try
            {
                pos = buscar(r);
                if (pos != -1)
                {
                    seekByte(pos);
                    reg = leer();
                    reg.setActive(false);

                    seekByte(pos);
                    grabar(reg);
                    resp = true;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            close();
        }

        return resp;
    }
    */

          /**
       * Borra un registro del archivo. El archivo debe estar abierto en modo de grabación. El registro se marca como
       * borrado, aunque sigue físicamente ocupando lugar en el archivo. El archivo vuelve abierto.
       * @param obj el registro a buscar y borrar.
       * @return true si fue posible borrar el registro - false si no fue posible.
       */
      public boolean remove (E obj)
      {
        boolean resp = false;
        long pos;

        if( obj != null )
        {
            try
            {
                Grabable r2 = obj.getClass().newInstance();
                Register reg = new Register(r2);

                pos = search(obj);
                if (pos != -1)
                {
                     seekByte(pos);
                     read(reg);
                     reg.setActive(false);

                     seekByte(pos);
                     write(reg);
                     resp = true;
                }
            }
            catch(Exception e)
            {
                System.out.println("Error al eliminar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
      }


          /**
       * Modifica un registro en el archivo. Reemplaza el registro en una posición dada, cambiando sus datos por otros
       * tomados como parámetro. El objeto que viene como parámetro se busca en el archivo, y si se encuentra entonces
       * el que estaba en el disco es reemplazado por el que entró a modo de parámetro. El archivo debe estar abierto en
       * modo de grabación. El archivo vuelve abierto.
       * @param obj el objeto con los nuevos datos.
       * @return true si fue posible modificar el registro - false si no fue posible
       */
      public boolean update (E obj)
      {
        boolean resp = false;
        long pos;

        if( obj != null )
        {
            try
            {
                pos = search(obj);
                if (pos != -1)
                {
                     seekByte(pos);
                     write( new Register(obj) ); // graba el nuevo registro encima del anterior
                     resp = true;
                }
            }
            catch(Exception e)
            {
                System.out.println("Error al modificar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
      }




     /**
     * Elimina f�sicamente los registros que estuvieran marcados como borrados.
     * El RegisterFile queda limpio, pero sale cerrado.
     * Obsoleto
     */
//    public void depurar()
//    {
//        try
//        {
//
//            RegisterFile temp = new RegisterFile("temporal.dat", testigo);
//            temp.openForReadWrite();
//
//            this.openForRead();
//            while (!this.eof())
//            {
//                reg = this.leer();
//                if (reg.isActive())
//                    temp.grabar(reg);
//            }
//
//            this.close();
//            temp.close();
//            this.delete();
//            temp.rename(this);
//        } catch (ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        } catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//    }
//


      /**
       * Elimina físicamente los registros que estuvieran marcados como borrados. El archivo queda limpio, sólo con
       * registros activos (no marcados como borrados). El archivo sale cerrado.
       */
      public void clean()
      {
        try
        {
           Grabable r2 = testigo.getClass().newInstance();
           Register reg = new Register(r2);

           RegisterFile temp = new RegisterFile ("temporal.dat", "rw", testigo);
           temp.maestro.setLength(0);
           this.rewind();
           while (!this.eof())
           {
                  this.read( reg );

                  if ( reg.isActive() )
                  {
                      temp.write(reg);
                  }
           }

           this.close();

           temp.close();
           //verificar q se borre
           if (this.delete()==false){
               System.out.println("Error al querer borrar el archivo original");
               return;
               }

          if (temp.rename(this)== false){
               System.out.println("Error al renombrar el archivo");
               return;
          }
           System.out.println("Arvhivo borrado exitosamente");

        }
        catch(Exception e)
        {
            System.out.println( "Error de tipo de dato con el archivo temporal: " + e.getMessage());
            System.exit(1);
        }
      }


    public void ordenar()
    {
        long i, j, n;
        Register ri, rj;

        //openForReadWrite(); //chequear si hace falta
        n = registerCount();
        for (i = 0; i < n - 1; i++)
        {
            // seekRegister(i); // esta no hace falta...
            ri = leer();

            for (j = i + 1; j < n; j++)
            {
                //seekRegister(j); // esta no hace falta...
                rj = leer();

                if (ri.compareTo(rj) > 0)
                {
                    seekRegister(j);
                    write(ri); // s�... ri
                    ri = rj;
                }
            }

            seekRegister(i);
            write(ri);
        }
        close();
    }

    /**
     * Devuelve la longitud del archivo, en bytes.
     * @return el total de bytes del archivo.
     */
    public long length()
    {
        try
        {
            return maestro.length();
        }
        catch(IOException e)
        {
            System.out.println("Error al calcular el n�mero de registros: " + e.getMessage());
            System.exit(1);
        }

        return 0;
    }

     /**
     * Graba un registro en el archivo, a partir de la posici�n dada por el file pointer en ese momento. El archivo se
     * supone abierto en modo de grabaci�n.
     * @param obj el objeto a grabar.
     * @return true si se pudo grabar - false en caso contrario.
     */
    public boolean write (Register r)
    {
        if(r != null)
        {
            try
            {
                r.grabar(maestro);
            }
            catch(Exception e)
            {
                System.out.println( "Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
            return true;
        }
        return false;
    }

        /**
      * Graba en un archivo un String de "tam" caracteres. Se declara static para que pueda ser usado forma en global por
      * cualquier clase que requiera grabar una cadena de longitud fija en un archivo. La cadena se graba de tal forma que
      * si no llegaba a tener tam caracteres, se agrega la cantidad necesaria de blancos al final de la cadena para llegar
      * a la cantidad tam.
      * @param  arch el manejador del archivo en el cual se graba, que se supone abierto en modo "rw" y posicionado en el lugar
      * correcto.
      * @param  cad la cadena a grabar.
      * @param  tam la cantidad de caracteres a grabar.
      */
     public static final void writeString (RandomAccessFile arch, String cad, int tam)
     {
         try
         {
             int i;
             char vector[] = new char[tam];
             for(i=0; i<tam; i++)
             {
                vector[i]= ' ';
             }
             cad.getChars(0, cad.length(), vector, 0);
             for (i=0; i<tam; i++)
             {
                arch.writeChar(vector[i]);
             }
         }
         catch(IOException e)
         {
             System.out.println("Error al grabar una cadena: " + e.getMessage());
             System.exit(1);
         }
     }

          /**
      * Lee desde un archivo un String de "tam" caracteres. Se declara static para que pueda ser usado en forma global
      * por cualquier clase que requiera leer una cadena de longitud fija desde un archivo manejado a través de un objeto
      * RandomAcessFile. Se supone que la cadena está grabada a partir de la posición actual dentro del RandomAccessFile,
      * y que fue grabada tal como indica el método writeString(). La cadena se retorna tal como se lee, sin remover los
      * espacios en blanco añadidos al final por writeString() cuando la grabó.
      * @param  arch el manejador del archivo desde el cual se lee la cadena. Se supone abierto y posicionado en el lugar
      * correcto.
      * @param  tam la cantidad de caracteres a leer.
      * @return el String leido, sin remover los blancos que pudiera contener al final.
      */
     public static final String readString (RandomAccessFile arch, int tam)
     {
         String cad = "";

         try
         {
             char vector[] = new char[tam];
             for(int i = 0; i<tam; i++)
             {
                vector[i] = arch.readChar();
             }
             cad = new String(vector,0,tam);
         }
         catch(IOException e)
         {
            System.out.println("Error al leer una cadena: " + e.getMessage());
            System.exit(1);
         }

         return cad;
     }

}