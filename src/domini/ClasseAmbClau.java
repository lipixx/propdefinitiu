/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domini;

/**
 *
 * @author Àlvar
 */

/* Interfície auxiliar que hauran d'implementar totes aquelles classes d'objectes que vulguin tenir un
 *  controlador a la capa de dades.
 * 
 * T -> tipus de la clau que tindra la classe (p. ex.: String, Integer, Double, etc.)
 */
public interface ClasseAmbClau<T>
{
    /* getClau: -Obte el valor de la clau d'aquell objecte-
     */
    
    // Pre: objecte implicit != null
    public T getClau();
    // Post: Retorna el valor de la clau de l'objecte
    
    /* setCopia: -Crea una copia de l'objecte a copiar en l'objecte implicit-
     */
    // Pre: objecteACopiar != null
    public void setCopia(ClasseAmbClau<T> objecteACopiar);
    // Post: l'objecte implicit es una copia d'"objecteACopiar"

}