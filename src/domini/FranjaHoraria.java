/**
 * La classe Franja Horaria proveeix l'objecte amb els atributs i estructures de dades,
 * aixi com les operacions necessaries per accedir i modificar els camps.
 * 
 * Una Franja Horaria s'identifica per la seva horaInici i horaFi, també té l'atribut taxa.
 * 
 * @author  Felip Moll 41743858P
 * @author  Josep Marti 41743212Y
 * @version 1.0, 6 Juny 2008 
 * 
 * Versio Final
 */
package domini;

import java.util.Calendar;
import java.io.Serializable;

public class FranjaHoraria implements ClasseAmbClau<Calendar>, Serializable {

    private Calendar horaInici;
    private Calendar horaFi;
    private float taxa;

    /**
     *  Cerca un programa al repositori de programes.  
     *  @param  NhoraInici Hora d'inici de la franja.
     *  @param  NhoraFi Hora de fi de la franja.
     *  @param  Ntaxa Taxa que s'ha d'aplicar a la franja.
     *  @pre    horaInici menor igual que horaFi, i taxa major igual que 0.
     *  @post   -
     */
    public FranjaHoraria(Calendar NhoraInici, Calendar NhoraFi, float Ntaxa) {
        if (NhoraInici != null && NhoraFi != null) {
            if (horaMajor(NhoraInici, NhoraFi) || taxa < 0) {
                horaInici = null;
                horaFi = null;
                taxa = 0;
            } else {
                horaInici = (Calendar) NhoraInici.clone();
                horaFi = (Calendar) NhoraFi.clone();
                taxa = Ntaxa;
            }
        } else {
            horaInici = null;
            horaFi = null;
            taxa = 0;
        }
    }

    /**
     *  Consultora de l'atribut horaInici.
     *  @return L'hora d'inici d'aquesta franja.
     */
    public Calendar getHoraInici() {
        return (Calendar) horaInici.clone();
    }

    /**
     *  Consultora de l'atribut horaFi.
     *  @return L'hora de fi d'aquesta franja.
     */
    public Calendar getHoraFi() {
        return (Calendar) horaFi.clone();
    }

    /**
     *  Consultora de l'atribut taxa.
     *  @return La Taxa que s'aplica en aquesta franja horaria.
     */
    public float getTaxa() {
        return taxa;
    }

    /**
     *  Modificadora de l'atribut horaInici.
     *  @param nhoraInici Nova hora d'inici de la franja.
     *  @pre    Hora de Inici es major que hora de Fi.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraInici(Calendar nhoraInici) {
        if (!horaInici.after(this.horaFi)) {
            horaInici = (Calendar) nhoraInici.clone();
        }
    }

    /**
     *  Modificadora de l'atribut horaFi.
     *  @param nhoraFi Es l'hora de fi d'aquesta franja.
     *  @pre    Hora de Fi es major que hora de Inici.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraFi(Calendar nhoraFi) {
        if (!nhoraFi.before(horaInici)) {
            horaFi = (Calendar) nhoraFi.clone();
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

    /**
     * Es una funcio que compara dues hores. No mira res mes que HH i MM.
     * @param hora1 Hora a comparar amb hora2
     * @param hora2 Hora a comparar amb hora1
     * @return cert hora1 > hora2, fals altrament
     */
    public boolean horaMajor(Calendar hora1, Calendar hora2) {
        if (hora1 == null || hora2 == null) {
            return false;
        }
        int h1 = hora1.get(Calendar.HOUR_OF_DAY);
        int h2 = hora2.get(Calendar.HOUR_OF_DAY);
        int m1 = hora1.get(Calendar.MINUTE);
        int m2 = hora2.get(Calendar.MINUTE);

        if (h1 > h2) {
            return true;
        }
        if (h1 == h2) {
            if (m1 > m2) {
                return true;
            }
        }
        return false;
    }

    /** Implementacio dels metodes abstractes de ClasseAmbClau*/
    public Calendar getClau() {
        return horaInici;
    }

    public void setCopia(ClasseAmbClau objecteACopiar) {
        objecteACopiar = new FranjaHoraria(horaInici, horaFi, taxa);
    }
}
