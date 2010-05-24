package Persistencia;

/**
 * Una clase para representar un archivo binario de registros de longitud fija, con acceso directo. El contenido de un
 * RegisterFile son registros uniformes (del mismo tipo) y de la misma longitud, lo cual favorece el seeking. El archivo 
 * no permite grabar objetos cuyo tipo (y tama�o) no coincida con los que se indicaron al crear el objeto.
 * @author  Ing. Valerio Frittelli
 * @version Marzo de 2008
 */

import Persistencia.Grabable;
import java.io.*;
import javax.swing.*;
import Exceptions.*;
import Persistencia.Register;
import Persistencia.StrategySort;

public class RegisterFile < E extends Grabable >
{
    protected File fd;                   // descriptor del archivo para acceder a sus propiedades externas
    protected RandomAccessFile maestro;  // manejador del archivo
    
    protected Grabable testigo = null;          // usado para registrar la clase de los objetos que se graban en el archivo
    //private StrategySort ss =  new Insercion();
    
    /**
     * Crea un manejador para el RegisterFile, asociando al mismo el nombre "newfile.dat " a modo de file descriptor. 
     * Abre el archivo en disco asociado con ese file descriptor, en el directorio default, y en modo "r" (tal como se 
     * se usa en la clase RandomAccessFile). El par�metro obj se usa para tomar la clase de los objetos que ser�n 
     * almacenados, pero ese objeto no se graba en el archivo (puede venir con valores por default: s�lo importa su
     * clase).
     * @param obj un objeto de la clase base para el archivo.
     */
    public RegisterFile ( E obj )
    {        
        this ("newfile.dat", "r", obj);
    }  
    
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
            JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo: " + e.getMessage());
            System.exit(1);
        }
    }  
   
    
    /**
     *  Accede al descriptor del archivo.
     *  @return un objeto de tipo File representando el pathname abstracto del archivo.
     */
    public File getFileDescriptor()
    {
        return fd;   
    }
    
    /**
     *  Acceso al manejador del archivo binario.
     *  @return un objeto de tipo RandomAccessFile que permite acceder al bloque f�sico de datos en disco, en forma directa.
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
    public Register getRegister(int index)throws RegistroInexistenteException
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
     
    public void writeRegister(Register reg,int k) throws RegistroInexistenteException
    {
        
        if(this.length()<k*reg.sizeOf())
            {
                throw new RegistroInexistenteException();
            }
        
        seekRegister(k);
        write(reg);
    }
    
    /**
     * Borra el archivo del disco.
     * @return true si se pudo borrar, o false en caso contrario.
     */
    public boolean delete()
    {
        return fd.delete();

    }
    
    /**
     * Cambia el nombre del archivo.
     * @param nuevo otro RegisterFile, cuyo nombre (o file descriptor) ser� dado al actual.
     * @return true si el cambio de nombre pudo hacerse, false en caso contrario.
     */
    public boolean rename(RegisterFile nuevo)
    {
        return fd.renameTo(nuevo.fd); 
    }
    
  
    /**
     * Cierra el archivo asociado con el RegisterFile.
     */
    public void close()
    {
        try
        {
            maestro.close();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al cerrar el archivo " + fd.getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }
    
    
    /**
     * Ubica el puntero de registro activo en la posici�n del byte n�mero b. Esa posici�n podr�a no coincidir con el 
     * inicio de un registro.
     * @param b n�mero del byte que se quiere acceder, contando desde el principio del archivo.
     */
    public void seekByte (long b)
    {
        try
        {
            maestro.seek(b);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al posicionar en el byte n�mero " + b + ": " + e.getMessage());    
            System.exit(1);
        }
    }
    
    /**
     * Ubica el puntero de registro activo en la posici�n de inicio del Register n�mero r. 
     * @param r n�mero del Registro (no el n�mero de byte) que se quiere acceder, contando desde el principio del archivo.
     */
    public void seekRegister(long r)
    {
        Register reg = new Register (testigo);
        try
        {
            maestro.seek(r * reg.sizeOf());
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al posicionar en el registro n�mero " + r + ": " + e.getMessage());    
            System.exit(1);
        }
    }
    
    /**
     * Rebobina el archivo: ubica el puntero de registro activo en la posici�n cero.
     */
    public void rewind()
    {
        try
        {
            maestro.seek(0);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al rebobinar el archivo: " + e.getMessage());            
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
            JOptionPane.showMessageDialog(null, "Error al intentar devolver el n�mero de byte: " + e.getMessage());   
            System.exit(1);
        }
        
        return -1;
    }
    
    /**
     * Devuelve el n�mero relativo de registro en el cual est� posicionado el archivo en este momento.
     * @return el n�mero de registro de posicionamiento actual.
     */
    public long registerPos () 
    {
        Register reg = new Register(testigo);
        try
        {
            return maestro.getFilePointer() / reg.sizeOf();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al intentar devolver el n�mero de registro: " + e.getMessage());   
            System.exit(1);
        }
        
        return -1;
    }
    
    /**
     * Posiciona el file pointer al final del archivo.
     */
    public void goFinal () 
    {
        try
        {
            maestro.seek(maestro.length());
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al posicionar al final: " + e.getMessage());            
            System.exit(1);
        }
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
            JOptionPane.showMessageDialog(null, "Error al calcular el n�mero de registros: " + e.getMessage()); 
            System.exit(1);
        }
        
        return 0;
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
            JOptionPane.showMessageDialog(null, "Error al medir la cantidad de regsitros del archivo " + fd.getName() + ": " + e.getMessage());
            System.exit(1);
        }
        
        return n;
    }

    
    /**
     * Determina si se ha llegado al final del archivo o no.
     * @return true si se lleg� al final - false en caso contrario.
     */
    public boolean eof ()
    {
        try
        {
            if (maestro.getFilePointer() < maestro.length()) return false;
            else return true;
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al determinar el fin de archivo: " + e.getMessage());  
            System.exit(1);
        }
        
        return true;
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
                JOptionPane.showMessageDialog(null, "Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
            return true;
        }
        return false;
    }
    
    /**
     * Lee un registro del archivo, a partir de la posici�n del file pointer en ese momento. El archivo se supone 
     * abierto.
     * @param obj el registro mediante el cual se har� la lectura. Los valores leidos vuelven en ese mismo objeto.
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
                System.out.println("Error al leer el registro: " + e.getMessage());   
                System.exit(1);
            }
            return true;
        }
        return false;
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
             JOptionPane.showMessageDialog(null, "Error al buscar el registro: " + e.getMessage());
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
             JOptionPane.showMessageDialog(null, "Error al buscar el registro: " + e.getMessage());
             System.exit(1);
        }
        return pos;
    }
      
    /**
     * Nuevo!!
     * M�todo para setear la estrategia de b�squeda a utilizar en el m�todo ordenar.
     * @param ss StrategySort que se va a setear.
     */
        
//    public void setOrdenador (StrategySort ss)
//    {
//        this.ss = ss;
//    }
    
   /**
    * Metodo que ordena el RegisterFile utilizando el StrategySort seteado.
    */
//    public void Ordenar()
//    {
//        try{
//            close();
//            maestro = new RandomAccessFile(fd, "rw");
//
//            ss.ordenar(this);
//        }catch(Exception e)
//        {
//            JOptionPane.showMessageDialog(null, "Error al ordenar con Selecci�n Directa: " + e.getMessage());
//            System.exit(1);
//        }
//
//    }
   
   
    @SuppressWarnings("empty-statement")
//    public void shellSort()
//    {
//        try{
//            close();
//            maestro=new RandomAccessFile(fd,"rw");
//            Register ri=new Register(testigo.getClass().newInstance());
//            Register rj=new Register(testigo.getClass().newInstance());
//            int h,n=(int)registerCount();
//            for (h=1; h <= n/9; h = 3*h + 1);
//            //for(h=1;h<=n/3;h=3*h+1)
//            for(;h>0;h/=3)
//            {
//
//                for(int j=h;j<n;j++)
//                {
//                    int k;
//                    ri=getRegister(j);
//
//                    for(k=j-h;k>=0&&ri.compareTo(getRegister(k))<0;k-=h)
//                    {
//                        rj=getRegister(k);
//                        writeRegister(rj,k+h);
//                    }
//                    writeRegister(ri,k+h);
//                }
//            }
//        }catch(Exception e){System.out.println(e.getMessage());}
//    }
    
    
    
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
                JOptionPane.showMessageDialog(null, "Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
    }

    /**
     * Agrega un registro en el archivo, sin controlar repetici�n. El archivo debe estar abierto en modo de grabaci�n.
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
                JOptionPane.showMessageDialog(null, "Error al grabar el registro: " + e.getMessage());
                System.exit(1);
            }
        }
        return resp;
    }
    
      /**
       * Borra un registro del archivo. El archivo debe estar abierto en modo de grabaci�n. El registro se marca como 
       * borrado, aunque sigue f�sicamente ocupando lugar en el archivo. El archivo vuelve abierto.
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
                JOptionPane.showMessageDialog(null,"Error al eliminar el registro: " + e.getMessage());   
                System.exit(1);
            }
        }
        return resp;
      }
      /**
       * NUEVO!!
       * Borra l�gicamente todos los registros del archivo. El archivo debe estar abierto en modo de grabaci�n. Los registros se marcan como
       * borrados, aunque siguen f�sicamente ocupando lugar en el archivo. El archivo vuelve abierto.
       * @return True si se pudieron borrar todos los registros.
       */
     public boolean removeAll(E obj)
     {
          boolean resp = false;
         try{
             Grabable r2 = obj.getClass().newInstance();
             Register reg = new Register(r2);
             long cant = registerCount();
            
          for (int i =0; i< cant; i++)
            {
              seekRegister(i);
              read(reg);
              reg.setActive(false);
              writeRegister(reg, i);

            }

            resp = true;
            }
          catch (Exception e){

              JOptionPane.showMessageDialog(null ,"Error: " + e.getMessage());
              resp = false;
          }
          return resp;
                  
                   
          
     }
      /**
       * Modifica un registro en el archivo. Reemplaza el registro en una posici�n dada, cambiando sus datos por otros 
       * tomados como par�metro. El objeto que viene como par�metro se busca en el archivo, y si se encuentra entonces 
       * el que estaba en el disco es reemplazado por el que entr� a modo de par�metro. El archivo debe estar abierto en 
       * modo de grabaci�n. El archivo vuelve abierto.
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
                JOptionPane.showMessageDialog(null,"Error al modificar el registro: " + e.getMessage());   
                System.exit(1);
            }
        }
        return resp;
      } 
      
    
      /**
       * Elimina f�sicamente los registros que estuvieran marcados como borrados. El archivo queda limpio, s�lo con
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
               JOptionPane.showMessageDialog(null,"Error al querer borrar el archivo original");
               return;
               }
         
          if (temp.rename(this)== false){
               JOptionPane.showMessageDialog(null,"Error al renombrar el archivo");
               return;
          }
           JOptionPane.showMessageDialog(null,"Arvhivo borrado exitosamente");

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error de tipo de dato con el archivo temporal: " + e.getMessage());   
            System.exit(1);
        }
      }

      
     /**
      * Lee desde un archivo un String de "tam" caracteres. Se declara static para que pueda ser usado en forma global 
      * por cualquier clase que requiera leer una cadena de longitud fija desde un archivo manejado a trav�s de un objeto
      * RandomAcessFile. Se supone que la cadena est� grabada a partir de la posici�n actual dentro del RandomAccessFile,
      * y que fue grabada tal como indica el m�todo writeString(). La cadena se retorna tal como se lee, sin remover los 
      * espacios en blanco a�adidos al final por writeString() cuando la grab�.
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
            JOptionPane.showMessageDialog(null, "Error al leer una cadena: " + e.getMessage());
            System.exit(1);
         }
         
         return cad;
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
             JOptionPane.showMessageDialog(null, "Error al grabar una cadena: " + e.getMessage());
             System.exit(1);
         }
     } 
}