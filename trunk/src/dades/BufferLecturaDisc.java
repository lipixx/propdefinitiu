package dades;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.InputStream;

/**
 * Redefinicion de la clase ObjectInputStream para poder leer ficheros con
 * objetos guardados de forma concatenada.
 * En realidad esta clase es la encargada de transformar los bytes de disco
 * que recibe en los objetos correspondientes.
 * 
 * @author Jordi Cuadrado
 */
public class BufferLecturaDisc extends ObjectInputStream {

    /**
     * Constructor de BufferLecturaDisc
     * @param in
     * Es el Stream de entrada asociado al fichero que lee los datos fisicamente
     * @throws java.io.IOException
     */
    public BufferLecturaDisc(InputStream in) throws IOException //Esto originalmente es un ObjectInputStream
    {
        super(in);
    }

    /**
     * Redefinicion del metodo de lectura de cabecera del fichero de disco.
     * Lo redefinimos sin código para que no lea la cabecera, puesto que no esta.
     * @throws java.io.IOException
     */
    @Override
    protected void readStreamHeader() throws IOException {
    }
}
