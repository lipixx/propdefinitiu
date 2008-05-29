package domini;

/**
 * La classe Planificacio proveeix l'objecte amb els atributs i estructures de dades,
 * aixi com les operacions necessaries per accedir i modificar els camps.
 * 
 * Una Planificacio es identificada per la seva dataInici i dataFi, 
 * tambe disposa d'una llista d'emissions que estan associades a la planificacio.
 * 
 * @author  Josep Marti 41743212Y
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */

import java.util.Calendar;
import java.util.LinkedList;

public class Planificacio {

    private int idglobal=0;
    private int id;
    private Calendar dataInici;
    private Calendar dataFi;
    private LinkedList<ServeiPendent> llistaEmissions;

    public Planificacio(Calendar novaDataInici, Calendar novaDataFi) {
        this.id=idglobal++;
        this.dataInici = novaDataInici;
        this.dataFi = novaDataFi;
        llistaEmissions = new LinkedList<ServeiPendent>();
    }
 
    public int getId() {
         return id;
     }
    

    /**
     *  Consultora de l'atribut dataInici.
     *  @return La data d'emissio del programa.
     */
    public Calendar getDataInici() {
        return dataInici;
    }

    /**
     *  Consultora de l'atribut dataFi.
     *  @return La data d'emissio del programa.
     */
    public Calendar getDataFi() {
        return dataFi;
    }

    /**
     *  Consultora de la llista llistaEmissions.
     *  @return Un llistat amb totes les emissions assocides a la planificacio.
     */
    public LinkedList<ServeiPendent> getLlistaEmissions() {
        return llistaEmissions;
    }
    
    public LinkedList<ServeiPendent> getLlistaServeis() {
        return llistaEmissions;
    }

     /**
     *  Modificadora de l'atribut noiaDataInici.
     *  @pre    El parametre novaDataInioi es una data de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setDataInici(Calendar novaDataInici) {
        dataInici = novaDataInici;
    }

     /**
     *  Modificadora de l'atribut novaDataFi.
     *  @pre    El parametre novaDataFi es una data de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setDataFi(Calendar novaDataFi) {
        dataFi = novaDataFi;
    }

}