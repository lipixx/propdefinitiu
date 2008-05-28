package dades;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Redefinicion de la clase ObjectOutputStream para poder concatenar objetos
 * en el fichero de disco, mediante la no escritura de una cabecera al principio del Stream.
 * En realidad esta clase es la encargada de transformar el objeto que recibe
 * en los bytes correspondientes que seran fisicamente escritos por el OutputStream
 * 
 * @author Jordi Cuadrado
 */
public class BufferEscripturaDisc extends ObjectOutputStream {

    /**
     * Constructor de BufferEscripturaDisc
     * @param out
     * Es el Stream de salida asociado al fichero que escibe los datos fisicamente
     * @throws java.io.IOException
     */
    public BufferEscripturaDisc(OutputStream out) throws IOException //Esto originalmente es una ObjectOtputStream
    {
        super(out);
    }

    /**
     * Redefinicion del metodo de escritura de cabecera en el fichero.
     * Lo redefinimos sin codigo para que no nos esriba ninguna cabecera con informacion innecesaria.
     * @throws java.io.IOException
     */
    @Override
    protected void writeStreamHeader() throws IOException {
    }
}
