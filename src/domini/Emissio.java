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
import java.util.Calendar;

public class Emissio {

    private Calendar dataEmissio;
    private Calendar horaInici;
    private Calendar horaFi;
    private boolean emes;
    private boolean facturat;
    private float preuEmissio;
    private Programa programa;
    private FranjaHoraria franja;

    public Emissio(Calendar novaDataEmissio, boolean nouEmes, boolean nouFacturat, Programa nouPrograma, FranjaHoraria nouFranja, Calendar novaHoraInici, Calendar novaHoraFi) {
        this.dataEmissio = novaDataEmissio;
        this.emes = nouEmes;
        this.facturat = nouFacturat;
        this.programa = nouPrograma;
        this.franja = nouFranja;
        this.horaInici = novaHoraInici;
        this.horaFi = novaHoraFi;
        System.out.println("EMISSIO: setPreuEmissio ->");
        this.setPreuEmissio(nouPrograma, nouFranja);
        System.out.println("EMISSIO: fi");
    }

    /**
     *  Consultora de l'atribut dataEmissio.
     *  @return La data d'emissio del programa.
     */
    public Calendar getDataEmissio() {
        return this.dataEmissio;
    }

    /**
     *  Consultora de l'atribut horaInici.
     *  @return L'hora d'inici d'emissio del programa.
     */
    public Calendar getHoraInici() {
        return this.horaInici;
    }

    /**
     *  Consultora de l'atribut horaFi.
     *  @return L'hora de fi d'emissio del programa.
     */
    public Calendar getHoraFi() {
        return this.horaFi;
    }

    /**
     *  Consultora de l'atribut emes.
     *  @return Un boolea que es cert si l'emissio ja ha estat emes,
     *  en cas contrari retorna fals.
     */
    public boolean getEmes() {
        return this.emes;
    }

    /**
     *  Consultora de l'atribut facturat.
     *  @return Un boolea que es cert si l'emissio ja ha estat facturada,
     *  en cas contrari retorna fals.
     */
    public boolean getFacturat() {
        return this.facturat;
    }

    /**
     *  Consultora de l'atribut preuEmissio.
     *  @return El preu de l'emissio.
     */
    public float getPreuEmissio() {
        return this.preuEmissio;
    }

    /**
     *  Consultora de l'objecte Programa.
     *  @return El programa associat a l'emissio.
     */
    public Programa getPrograma() {
        return this.programa;
    }

    /**
     *  Consultora de l'objecte Franja.
     *  @return La franja associada a l'emissio.
     */
    public FranjaHoraria getFranja() {
        return this.franja;
    }

    /**
     *  Modificadora de l'atribut preuEmissio.
     *  @pre    L'emissio ha de tenir associat un programa amb un preu fixat,
     * i esta associada a una franja que també ha de tenir una taxa fixada.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setPreuEmissio(Programa prog, FranjaHoraria franja) {
        System.out.println("aban get taxa");
        float preuF = franja.getTaxa();
        System.out.println("aban get preu base");
        float ppreuP = prog.getPreuBase();
        System.out.println("aban assignacio");
        this.preuEmissio = prog.getPreuBase() + franja.getTaxa();
    }

    /**
     *  Modificadora de l'atribut dataEmissio.
     *  @pre    El parametre novaDataEmissio es una data de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setDataEmissio(Calendar novaDataEmissio) {
        this.dataEmissio = novaDataEmissio;
    }

    /**
     *  Modificadora de l'atribut horaInici.
     *  @pre    El parametre novaHoraInici es una hora de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraInici(Calendar novaHoraInici) {
        this.horaInici = novaHoraInici;
    }

    /**
     *  Modificadora de l'atribut horaFi.
     *  @pre    El parametre novaHoraFi es una hora de tipus Calendar valida.
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setHoraFi(Calendar novaHoraFi) {
        this.horaFi = novaHoraFi;
    }

    /**
     *  Modificadora de l'atribut emes.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setEmes(boolean nouEmes) {
        this.emes = nouEmes;
    }

    /**
     *  Modificadora de l'atribut facturat.
     *  @pre    -
     *  @post   S'ha modificat l'atribut amb el nou valor.
     */
    public void setFacturat(boolean nouFacturat) {
        this.facturat = nouFacturat;
    }
}
