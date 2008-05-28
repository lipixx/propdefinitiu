/**
 * La classe Franja Horaria proveeix l'objecte amb els atributs i estructures de dades,
 * aixi com les operacions necessaries per accedir i modificar els camps.
 * 
 * Una Franja Horaria s'identifica per la seva horaInici i horaFi, també té l'atribut taxa.
 * 
 * @author  Josep Marti 41743212Y
 * @author  Felip Moll 41743858P
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
package domini;

import java.util.Calendar;
import java.io.Serializable;

public class FranjaHoraria implements ClasseAmbClau<Float>, Serializable {

    private Calendar horaInici;
    private Calendar horaFi;
    private float taxa;

    /**
     *  Cerca un programa al repositori de programes.  
     *  @param  horaInici Hora d'inici de la franja.
     *  @param  horaFi Hora de fi de la franja.
     *  @param  taxa Taxa que s'ha d'aplicar a la franja.
     *  @pre    horaInici menor igual que horaFi, i taxa major igual que 0.
     *  @post   -
     */
    public FranjaHoraria(Calendar NhoraInici, Calendar NhoraFi, float Ntaxa) {
        if (NhoraInici.after(NhoraFi) || taxa < 0) {
            horaInici = null;
            horaFi = null;
            taxa = 0;
        } else {
            horaInici = (Calendar) NhoraInici.clone();
            horaFi = (Calendar) NhoraFi.clone();
            taxa = Ntaxa;
        }
    }

    /**
     *  Consultora de l'atribut horaInici.
     *  @return L'hora d'inici d'aquesta franja.
     */
    public Calendar getHoraInici() {
        return this.horaInici;
    }

    /**
     *  Consultora de l'atribut horaFi.
     *  @return L'hora de fi d'aquesta franja.
     */
    public Calendar getHoraFi() {
        return this.horaFi;
    }

    /**
     *  Consultora de l'atribut taxa.
     *  @return La Taxa que s'aplica en aquesta franja horaria.
     */
    public float getTaxa() {
        return this.taxa;
    }

    /**
     *  Modificadora de l'atribut horaInici.
     *  @param horaInici Nova hora d'inici de la franja.
     *  @pre    Hora de Inici es major que hora de Fi.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraInici(Calendar horaInici) {
        if (!horaInici.after(this.horaFi)) {
            this.horaInici = horaInici;
        }
    }

    /**
     *  Modificadora de l'atribut horaFi.
     *  @param horaFi Es l'hora de fi d'aquesta franja.
     *  @pre    Hora de Fi es major que hora de Inici.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraFi(Calendar horaFi) {
        if (!horaFi.before(this.horaInici)) {
            this.horaFi = horaFi;
        }
    }

    /**
     *  Modificadora de l'atribut taxa.
     *  @param taxa Es el valor nou que es vol per el camp taxa.
     *  @pre    La nova taxa te valor >= 0
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setTaxa(float taxa) {
        if (taxa < 0) {
            this.taxa = 0;
        } else {
            this.taxa = taxa;
        }
    }

    public Float getClau() {
        return taxa;
    }

    public void setCopia(ClasseAmbClau objecteACopiar) {
        objecteACopiar = new FranjaHoraria(horaInici, horaFi, taxa);
    }
}
