package domini;

/**
 * La classe Emissio proveeix l'objecte amb els atributs i estructures de dades,
 * aixi com les operacions necessaries per accedir i modificar els camps.
 * 
 * Una Emissio es identificat per la seva dataEmissio, 
 * encara que tambe te els camps emes, facturat, programa i franja.
 * 
 * @author  Josep Marti 41743212Y
 * @version 2.0, 30 Abril 2008 
 * 
 *
 */
import java.io.Serializable;
import java.util.Calendar;

public class Emissio extends ServeiPendent implements Serializable {

    private Calendar dataEmissio;
    private Calendar horaInici;
    private Calendar horaFi;
    private boolean emes;
    private double preuEmissio;
    private Programa programa;
    private FranjaHoraria franja;

    public Emissio(Calendar novaDataEmissio, boolean nouEmes, boolean nouFacturat, Programa nouPrograma, FranjaHoraria nouFranja, Calendar novaHoraInici, Calendar novaHoraFi) {
        super(nouFacturat, 0, nouPrograma.getNom(), novaDataEmissio);
        dataEmissio = (Calendar) novaDataEmissio.clone();
        emes = nouEmes;
        programa = nouPrograma;
        franja = nouFranja;
        horaInici = (Calendar) novaHoraInici.clone();
        horaFi = (Calendar) novaHoraFi.clone();
        setPreuEmissio(nouPrograma, nouFranja);
    }

    /**
     *  Consultora de l'atribut dataEmissio.
     *  @return La data d'emissio del programa.
     */
    public Calendar getDataEmissio() {
        return (Calendar) dataEmissio.clone();
    }

    /**
     *  Consultora de l'atribut horaInici.
     *  @return L'hora d'inici d'emissio del programa.
     */
    public Calendar getHoraInici() {
        return (Calendar) horaInici.clone();
    }

    /**
     *  Consultora de l'atribut horaFi.
     *  @return L'hora de fi d'emissio del programa.
     */
    public Calendar getHoraFi() {
        return (Calendar) horaFi.clone();
    }

    /**
     *  Consultora de l'atribut emes.
     *  @return Un boolea que es cert si l'emissio ja ha estat emes,
     *  en cas contrari retorna fals.
     */
    public boolean getEmes() {
        return emes;
    }

    /**
     *  Consultora de l'atribut preuEmissio.
     *  @return El preu de l'emissio.
     */
    public double getPreuEmissio() {
        return preuEmissio;
    }

    /**
     *  Consultora de l'objecte Programa.
     *  @return El programa associat a l'emissio.
     */
    public Programa getPrograma() {
        return programa;
    }

    /**
     *  Consultora de l'objecte Franja.
     *  @return La franja associada a l'emissio.
     */
    public FranjaHoraria getFranja() {
        return franja;
    }

    /**
     *  Modificadora de l'atribut preuEmissio.
     *  @pre    L'emissio ha de tenir associat un programa amb un preu fixat,
     * i esta associada a una franja que també ha de tenir una taxa fixada.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setPreuEmissio(Programa prog, FranjaHoraria franja) {
        double preuF = franja.getTaxa();
        double ppreuP = prog.getPreuBase();
        preuEmissio = prog.getPreuBase() + franja.getTaxa();
        super.setPreu(preuEmissio);
    }

    /**
     *  Modificadora de l'atribut dataEmissio.
     *  @pre    El parametre novaDataEmissio es una data de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setDataEmissio(Calendar novaDataEmissio) {
        dataEmissio = (Calendar) novaDataEmissio.clone();
    }

    /**
     *  Modificadora de l'atribut horaInici.
     *  @pre    El parametre novaHoraInici es una hora de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraInici(Calendar novaHoraInici) {
        horaInici = (Calendar) novaHoraInici.clone();
    }

    /**
     *  Modificadora de l'atribut horaFi.
     *  @pre    El parametre novaHoraFi es una hora de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraFi(Calendar novaHoraFi) {
        horaFi = (Calendar) novaHoraFi.clone();
    }

    /**
     *  Modificadora de l'atribut emes.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setEmes(boolean nouEmes) {
        emes = nouEmes;
    }
}
