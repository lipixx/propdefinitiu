/**
 * La classe Programa proveeix l'objecte amb els atributs i estructures de dades,
 * aixi com les operacions necessaries per accedir i modificar els camps.
 * 
 * Un programa es identificat per un Nom, i te una Data de caducitat, una Descripcio
 * un preu i una llista de tematiques.
 * 
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini;

import java.util.Calendar;
import java.util.LinkedList;
import java.io.Serializable;

public class Programa implements ClasseAmbClau<String>, Serializable {

    private String nom;
    private Calendar dataCaducitat;
    private String descripcio;
    private float preuBase;
    private LinkedList<Tematica> llistaTematica;

    public Programa(String Nnom, Calendar NdataCaducitat, String Ndescripcio, float NpreuBase) {
        nom = new String(Nnom);
        dataCaducitat = (Calendar) NdataCaducitat.clone();
        descripcio = new String(Ndescripcio);
        if (NpreuBase < 0) {
            preuBase = 0;
        } else {
            preuBase = NpreuBase;
        }
        llistaTematica = new LinkedList<Tematica>();
    }

    /**
     *  Consultora de l'atribut nom.
     *  @return El nom del programa.
     */
    public String getNom() {
        return nom;
    }

    /**
     *  Consultora de la data de caducitat.
     *  @return La data de caducitat del programa.
     */
    public Calendar getDataCaducitat() {
        return dataCaducitat;
    }

    /**
     *  Consultora de la descripcio.
     *  @return La descripcio del programa.
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     *  Consultora del preu.
     *  @return El preu del programa.
     */
    public float getPreuBase() {
        return preuBase;
    }

    /**  Afegeix una tematica a la llista de temes del programa.
     *  @param  tema Tematica a afegir a la llista
     *  @pre    -
     *  @post   S'ha afegit la tematica a la llista de tematiques
     */
    public void addTematica(Tematica T) {
        /** No ens interessa fer un new, ja que volem que agafi el valor del
         *  repositori de tematiques general.
         */
        if (!llistaTematica.contains(T)) {
            llistaTematica.add(T);
        }

    }

    /**  Elimina una tematica de la llista de temes del programa. 
     *  @param  T Tematica a eliminar de la llista
     *  @pre    La llista de temes no es buida.
     *  @post   S'ha eliminat la tematica a la llista de tematiques
     */
    public void removeTematica(Tematica T) {

        int sizeList = llistaTematica.size();
        String nomTema = T.getTema();
        int i = 0;
        boolean trobat = false;

        if (sizeList > 0) {
            while (i < sizeList && !trobat) {
                if (nomTema.equalsIgnoreCase(llistaTematica.get(i).getTema())) {
                    trobat = true;
                } else {
                    i++;
                }
            }

            if (trobat) {
                llistaTematica.remove(i);
            }
        }
    }

    /**  Elimina una tematica de la llista de temes del programa.
     *  @param  nomTema Nom de la tematica a eliminar de la llista
     *  @pre    La llista de temes no es buida.
     *  @post   S'ha eliminat la tematica a la llista de tematiques
     */
    public void removeTematica(String nomTema) {
        int sizeList = llistaTematica.size();
        int i = 0;
        boolean trobat = false;

        if (sizeList > 0) {
            while (i < sizeList && !trobat) {
                if (nomTema.equalsIgnoreCase(llistaTematica.get(i).getTema())) {
                    trobat = true;
                } else {
                    i++;
                }
            }

            if (trobat) {
                llistaTematica.remove(i);
            }
        }
    }

    /**
     *   Canvia el nom al programa.
     *  @param  nouNom es el nou nom que tindra el programa.
     *  @pre    -
     *  @post   S'ha modificat el nom del programa.
     */
    public void setNom(String nouNom) {
        nom = nouNom;
    }

    /**
     *   Canvia la data de caducitat del programa.
     *  @param  novaCaducitat es la nova data de caducitat que tindra el programa.
     *  @pre    -
     *  @post   La data de caducitat del programa s'ha modificat.
     *  @see    java.util.Calendar
     */
    public void setDataCaducitat(Calendar novaCaducitat) {
        dataCaducitat = novaCaducitat;
    }

    /**
     *   Modifica la descripcio del programa.
     *  @param  novaDescr es la nova descripcio del programa.
     *  @pre    -
     *  @post   S'ha canviat la descripcio del programa.
     */
    public void setDescripcio(String novaDescr) {
        descripcio = novaDescr;
    }

    /**
     *   Canvia el preu del programa.
     *  @param  nouPreu Es el nou preu que tindra el programa.
     *  @pre    nouPreu es major o igual que 0.
     *  @post   Aquest programa costa nouPreu.
     *  @return Cert si s'ha pogut assignar, fals si no.
     */
    public boolean setPreubase(float nouPreu) {
        if (nouPreu < 0) {
            return false;
        }
        preuBase = nouPreu;
        return true;
    }

    /**
     *    Cerca i retorna tots els noms dels temes que te aquest programa.
     *  @pre    La llista de temes del programa no es buida.
     *  @post   -
     *  @return Un String amb els noms de tots els temes del programa.
     */
    public String[] getTemes() {
        int sizeList = llistaTematica.size();
        
        if (sizeList > 0) {
            String[] temes = new String[sizeList];
             /*Aqui hauria de ser sizeList.. pero peta*/
            for (int i = 0; i < sizeList; i++) {
                temes[i] = new String("" + llistaTematica.get(i).getTema());
            }
            return temes;
        }
        return null;
    }

    public void clearTemes()
    {
        llistaTematica.clear();
    }
    
    public String getClau() {
        return nom.toLowerCase();
    }

    public void setCopia(ClasseAmbClau objecteACopiar) {
        throw new UnsupportedOperationException("Operation not supported.");
    }
}
