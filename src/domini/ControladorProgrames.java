/**
 * La classe ControladorDominiProgrames representa el Controlador de la capa
 * de domini, exclusivament per els programes.
 * 
 * @author  Felip Moll
 * @version 0.1, 30 Maig 2008 
 * 
 * Versió preliminar.
 */
package domini;

import dades.RepositoriFranges;
import dades.RepositoriTematiques;
import dades.RepositoriProgrames;
import dades.GestorDiscException;
import domini.programa.*;
import java.util.Calendar;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class ControladorProgrames {

    private RepositoriProgrames<String, domini.Programa> RepoProg;
    private RepositoriTematiques RepoTemes;
    private RepositoriFranges RepoFranges;

    public ControladorProgrames() throws Exception {
        RepoProg = new RepositoriProgrames();
        RepoTemes = new RepositoriTematiques();
        RepoFranges = new RepositoriFranges();
    }

    /**
     *  Afegeix un programa al repositori de programes en memoria.  
     *  @param  nou Es una tupla amb els parametres del nou programa.
     *  @pre    El programa no existeix al repositori de programes i les dades
     *          tenen el format correcte.
     *  @post   S'ha inserit el programa amb parametres "nou" al repositori.
     *  @return Cert si el programa s'ha introduït correctament. Fals altrament.
     */
    public boolean afegirPrograma(tuplaPrograma nou) {
        if (RepoProg.existeixPrograma(nou.nom.toLowerCase()) != null) {
            return false;
        }
        Programa p;
        switch (nou.format) {
            /*Normal*/
            case 0:
                switch (nou.categoria) {
                    case 0: //Altres
                        p = new AltresN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 1: //Adults
                        p = new AdultsN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 2: //Concurs
                        p = new ConcursN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 3: //Documental
                        p = new DocumentalN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 4: //Esport
                        //Uncomment per opcional
                        //p = new EsportN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio, nou.attrc1);
                        p = new EsportN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 5: //Infantil
                        p = new InfantilN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 6: //Musica
                        p = new MusicaN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 8: //Pelicula Director - Any - String True VO.
                        //Uncomment per opcional
                        //p = new PeliculaN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio, nou.attrc1, nou.attrc2, Boolean.valueOf(nou.attrc3));
                        p = new PeliculaN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 9: //Serie
                        //Uncomment per opcional 
                        //p = new SerieN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio, Integer.parseInt(nou.attrc3), nou.attrc2, nou.attrc1);
                        p = new SerieN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    case 10: //Tertulia
                        //Uncomment per opcional
                        //p = new TertuliaN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio, nou.attrc1);
                        p = new TertuliaN(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.duracio);
                        break;
                    default:
                        return false;
                }
                break;

            /*Continu*/
            case 1:

                switch (nou.categoria) {
                    case 0: //Altres
                        p = new AltresC(nou.nom, nou.dataCad, nou.descripcio, nou.preu);
                        break;
                    case 2: //Concurs
                        p = new ConcursC(nou.nom, nou.dataCad, nou.descripcio, nou.preu);
                        break;
                    case 4: //Esport
                        //Uncomment per opcional
                        //p = new EsportC(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.attrc1);
                        p = new EsportC(nou.nom, nou.dataCad, nou.descripcio, nou.preu);
                        break;
                    case 6: //Musica
                        p = new MusicaC(nou.nom, nou.dataCad, nou.descripcio, nou.preu);
                        break;
                    case 7: //Noticies
                        p = new NoticiesC(nou.nom, nou.dataCad, nou.descripcio, nou.preu);
                        break;
                    default:
                        return false;
                }
                break;

            /*Directe*/
            case 2:
                switch (nou.categoria) {
                    case 0: //Altres
                        p = new AltresD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio);
                        break;
                    case 2: //Concurs
                        p = new ConcursD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio);
                        break;
                    case 4: //Esport
                        //Uncomment per opcional
                        //p = new EsportD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio, nou.attrc1);
                        p = new EsportD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio);
                        break;
                    case 7: //Noticies
                        p = new NoticiesD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio);
                        break;
                    case 10: //Tertulia
                        //Uncomment per opcional
                        //p = new TertuliaD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio, nou.attrc1);
                        p = new TertuliaD(nou.nom, nou.dataCad, nou.descripcio, nou.preu, nou.iniciEmissio, nou.duracio);
                        break;
                    default:
                        return false;
                }
                break;
            default:
                return false;
        }
        addTematicaRepo(nou.tematiques);
        addTematicaProg(nou.tematiques, p);
        RepoProg.afegirPrograma(p, nou.format, nou.categoria);
        return true;
    }

    /**
     * Afegeix una llista de tematiques al programa.
     * @param temes String amb els noms dels temes que volem afegir al programa
     * @param p Programa al que volem afegir-hi els temes
     * @pre Tots els temes estan al repositori
     * @post S'han afegit aquests temes a la llista de temes del programa
     */
    private void addTematicaProg(String temes[], Programa p) {
        int nTemes = temes.length;
        for (int i = 0; i < nTemes; i++) {
            Tematica t = RepoTemes.obteElement(temes[i].toLowerCase());
            if (t != null) {
                p.addTematica(t);
            }
        }
    }

    /**
     *  Elimina un programa del repositori de programes en memoria.  
     *  @param  nomPrograma Parametres del programa a eliminar.
     *  @pre    -
     *  @post   S'ha eliminat el programa amb parametres "nomPrograma" al repositori.
     *  @return Cert si el programa s'ha eliminat correctament. Fals altrament.
     */
    public boolean eliminaPrograma(String nomPrograma) {
        RepoProg.eliminarPrograma(nomPrograma);
        return true;
    }

    /**
     *  Modifica un programa concret canviant-li els atributs.
     *  @param  nou Es una tupla amb els parametres del nou programa.
     *  @pre    El programa existeix.
     *  @post   S'ha modificat el programa amb els nous atributs.
     *  @return Cert si la operació ha tingut èxit. Fals altrament.
     */
    public boolean modificarPrograma(tuplaPrograma nou) {
        /*Es modifica per punter*/
        Programa p = (Programa) RepoProg.getPrograma(nou.nom.toLowerCase());
        if (p != null) {
            p.setDataCaducitat(nou.dataCad);
            p.setDescripcio(nou.descripcio);
            p.setNom(nou.nom);
            p.setPreubase(nou.preu);
            p.clearTemes();
            addTematicaProg(nou.tematiques,p);
            
            String tipus = p.getClass().getName();
            char tipusc = tipus.charAt(tipus.length() - 1);

            if (tipusc == 'N') {
                ((Normal) p).setDuracio(nou.duracio);
            } else if (tipusc == 'D') {
                ((Directe) p).setDuracio(nou.duracio);
                ((Directe) p).setIniciEmissio(nou.iniciEmissio);
            }
            //Si es continu no fer res mes
            return true;
        }
        return false;
    }

    /**
     *  Cerca un programa al repositori de programes.  
     *  @param  nom El nom del programa a cercar.
     *  @pre -
     *  @post -
     *  @return Cert si el programa existeix, Fals altrament.
     */
    public boolean existeixPrograma(String nom) {
        if (RepoProg.existeixPrograma(nom) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *  Exporta el repositori de programes a un fitxer.  
     *  @param  rutaFitxer Es un string amb la ruta i nom del fitxer a guardar.
     *  @pre    El fitxer no esta protegit contra escriptura.
     *  @post   S'ha guardat el repositori al fitxer.
     *  @return Cert si el fitxer s'ha guardat correctament. Fals altrament.
     */
    public boolean exportarLlProgrames(String rutaFitxer) throws GestorDiscException {
        return RepoProg.exportaProgrames(rutaFitxer);
    }

    /**
     *  Importa des de un fitxer, un repositori de programes.  
     *  @param  rutaFitxer Es un string amb la ruta i nom del fitxer a carregar.
     *  @pre    El fitxer no esta protegit contra lectura i existeix.
     *  @post   S'ha creat un nou repositori amb els programes del fitxer.
     *  @return Cert si el fitxer s'ha importat correctament. Fals altrament.
     */
    public boolean importarLlistaProgrames(String rutaFitxer) throws GestorDiscException {
        return RepoProg.importaProgrames(rutaFitxer);
    }

    /**
     *  Filtra una llista de programes a partir d'uns filtres passats. 
     *  @param  tipusFiltre es filtrar per:
     *          Format, Categoria, Tematica, Nom, Tots.
     *  @param  valorFiltre Es el valor que agafa el filtre, que sera 0..2 si es
     * un format, 0..10 si es una categoria, i un String si Tematica o Nom. null si Tots.
     *  @pre    tipusFiltre pren com a valor Format, Categoria, 
     *          Tematica, Nom o Tots.
     *  @post   -
     *  @return Una llista filtrada amb els noms dels programes que
     *          compleixen els criteris especificats per els filtres. Null si hi ha
     *          hagut un error.
     */
    public String[] getllistaFiltrada(String tipusFiltre, String valorFiltre) {
        LinkedList<Programa> resultat[];
        LinkedList<Programa> resultatT[][];
        String arraySortida[] = null;
        int nProgs = 0;
        int index = 0;
        Programa p;

        if (tipusFiltre.equalsIgnoreCase("format")) {
            int f = Integer.parseInt(valorFiltre);
            if (f < 0 || f > 2) {
                return null;
            }
            resultat = RepoProg.llistarProgrames(f, true);

            /**Aqui tenim una llista dividida per categories, totes del mateix format.
            Mirem a veure quants de programes te aquesta llista:
             */
            for (int i = 0; i < resultat.length; i++) {
                nProgs = nProgs + resultat[i].size();
            }

            arraySortida = new String[nProgs];

            for (int i = 0; i < resultat.length; i++) {
                for (int j = 0; j < resultat[i].size(); j++) {
                    p = (Programa) resultat[i].get(j);
                    arraySortida[index] = p.getNom();
                    index++;
                }
            }

        } else {

            if (tipusFiltre.equalsIgnoreCase("categoria")) {
                int c = Integer.parseInt(valorFiltre);
                if (c < 0 || c > 10) {
                    return null;
                }

                resultat = RepoProg.llistarProgrames(c);

                for (int i = 0; i < resultat.length; i++) {
                    nProgs = nProgs + resultat[i].size();
                }

                arraySortida = new String[nProgs];

                for (int i = 0; i < resultat.length; i++) {
                    for (int j = 0; j < resultat[i].size(); j++) {
                        p = (Programa) resultat[i].get(j);
                        arraySortida[index] = p.getNom();
                        index++;
                    }
                }
            } else {
                if (tipusFiltre.equalsIgnoreCase("tematica")) {
                    resultatT = RepoProg.llistarProgrames();

                    /**L'array de sortida maxim tindra nProgs*/
                    nProgs = RepoProg.getListSize();
                    arraySortida = new String[nProgs];
                    String[] arrayTemes;

                    /**Dins el Format*/
                    for (int i = 0; i < 3; i++) {
                        /**Dins la categoria*/

                        for (int j = 0; j < 11; j++) {
                            /**Dins la llista F-C
                            /**Per totes les llistes de format i i categoria j fer,
                                per cada programa que hi ha, fins a veure'ls tots (resultatT[i][j].size()):
                             */
                            for (int k = 0; k < resultatT[i][j].size(); k++)
                            {
                                /**Obte el programa K de la llista format i, cat j.*/
                                p = (Programa) resultatT[i][j].get(k);
                                
                                /**N'extreu els temes*/
                                arrayTemes = p.getTemes();
                                
                                /**Per tots els temes d'aquest programa, mirar si n'hi ha cap
                                 * que sigui igual al que ens demanen.
                                 */
                                for (int o = 0; o < arrayTemes.length; o++) 
                                {
                                    if (arrayTemes[o].compareToIgnoreCase(valorFiltre) == 0) 
                                    {
                                        /**Si hi es l'afegim a l'array de sortida
                                         * i incrementem l'index, que correspon al num
                                         * de programes trobats fins al moment.
                                         */
                                        arraySortida[index] = p.getNom();
                                        index++;
                                        
                                        /*Si l'hem trobat, no fa falta cercar mes, llavors
                                         per sortir del bucle fem*/
                                        o = arrayTemes.length;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (tipusFiltre.equalsIgnoreCase("tots")) {
                        resultatT = RepoProg.llistarProgrames();
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 11; j++) {
                                nProgs = nProgs + resultatT[i][j].size();
                            }
                        }


                        arraySortida = new String[nProgs];

                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 11; j++) {
                                for (int k = 0; k < resultatT[i][j].size(); k++) {
                                    p = (Programa) resultatT[i][j].get(k);
                                    arraySortida[index] = p.getNom();
                                    index++;
                                }
                            }
                        }

                    } else {
                        if (tipusFiltre.equalsIgnoreCase("nom")) {
                            if (existeixPrograma(valorFiltre)) {
                                arraySortida = new String[1];
                                arraySortida[0] = valorFiltre;
                            }
                        } else {
                            return null;
                        }
                    }
                }

            }

        }
        return arraySortida;
    }

    /**
     *  Retorna la llista amb els noms de les tematiques disponibles
     *  que hi ha al repositori. 
     *  @pre    -
     *  @post   -
     *  @return Una llista amb les tematiques disponibles
     *          al repositori.
     */
    public String[] getLTematiques() {
        String[] resultat;
        LinkedList<Tematica> llistaTemes = RepoTemes.listaObject();
        int mida = llistaTemes.size();
        resultat = new String[mida];
        for (int i = 0; i < mida; i++) {
            resultat[i] = llistaTemes.get(i).getTema();
        }
        return resultat;
    }

    /**
     *  Retorna la llista amb els tipus de formats disponibles.
     *  @pre    -
     *  @post   -
     *  @return Una llista amb els formats disponibles.
     */
    public String[] getLFormat() {
        String[] arrayFormats = {"Normal", "Continu", "Directe"};
        return arrayFormats;
    }

    /**
     *  Retorna la llista amb els noms de les categories disponibles
     *  que hi ha. 
     *  @pre    -
     *  @post   -
     *  @return Una llista amb els noms de les categories disponibles.
     *          
     */
    public String[] getLCategories() {
        String[] arrayCategories = {"Altres", "Adults", "Concurs", "Documental", "Esport",
            "Infantil", "Musica", "Noticies", "Pelicula", "Serie", "Tertulia"
        };
        return arrayCategories;
    }

    /**
     *  Aquesta funcio retorna les dades d'un programa concret.
     *  @param  nomPrograma Parametres del programa a consultar les dades.
     *  @pre    -
     *  @post   -
     *  @return Una tupla amb les dades del programa. Null
     *          si el programa no existeix.
     */
    public tuplaPrograma veureFitxa(String nomPrograma) {
        //if nomPrograma == ""; .. else
        tuplaPrograma fitxa = new tuplaPrograma();
        int midaTipus;
        Programa p = (Programa) RepoProg.getPrograma(nomPrograma);

        if (p == null) {
            return null;
        }

        String tipus = p.getClass().getName();
        midaTipus = tipus.length();

        fitxa.nom = p.getNom();
        fitxa.preu = p.getPreuBase();
        fitxa.dataCad = p.getDataCaducitat();
        fitxa.descripcio = p.getDescripcio();


        if (tipus.charAt(midaTipus - 1) == 'N') {
            fitxa.format = 0;
            fitxa.duracio = ((Normal) p).getDuracio();
        }
        if (tipus.charAt(midaTipus - 1) == 'C') {
            fitxa.format = 1;
        }
        if (tipus.charAt(midaTipus - 1) == 'D') {
            fitxa.format = 2;
            fitxa.duracio = ((Directe) p).getDuracio();
            fitxa.iniciEmissio = ((Directe) p).getIniciEmissio();
        }

        /**"Altres", "Adults", "Concurs", "Documental", "Esport","Infantil", 
         * "Musica", "Noticies", "Pelicula", "Serie", "Tertulia";
         *  No agafem els atributs de les categories, es deixa com opcional.
         */
        tipus = tipus.substring(0, midaTipus - 1);

        if (tipus.compareTo("domini.programa.Altres") == 0) {
            fitxa.categoria = 0;
        }
        if (tipus.compareTo("domini.programa.Adults") == 0) {
            fitxa.categoria = 1;
        }
        if (tipus.compareTo("domini.programa.Concurs") == 0) {
            fitxa.categoria = 2;
        }
        if (tipus.compareTo("domini.programa.Documental") == 0) {
            fitxa.categoria = 3;
        }
        if (tipus.compareTo("domini.programa.Esport") == 0) {
            fitxa.categoria = 4;
        }
        if (tipus.compareTo("domini.programa.Infantil") == 0) {
            fitxa.categoria = 5;
        }
        if (tipus.compareTo("domini.programa.Musica") == 0) {
            fitxa.categoria = 6;
        }
        if (tipus.compareTo("domini.programa.Noticies") == 0) {
            fitxa.categoria = 7;
        }
        if (tipus.compareTo("domini.programa.Pelicula") == 0) {
            fitxa.categoria = 8;
        }
        if (tipus.compareTo("domini.programa.Serie") == 0) {
            fitxa.categoria = 9;
        }
        if (tipus.compareTo("domini.programa.Tertulia") == 0) {
            fitxa.categoria = 10;
        }
        //Rellenar tematiques
        fitxa.tematiques = p.getTemes();
        return fitxa;
    }

    /**
     *  Aquesta funcio agafa totes les franges de la llista de franges del
     *  repositori de franges, i les inclou dins un string per enviar-les
     *  al controlador de vistes.
     *  @pre    -
     *  @post   -
     *  @return Una llista de franges amb, per cada una:
     *          HInici:MInici, HFinal:Mfinal, Taxa a aplicar.
     */
    public String[][] getFranges() {
        int nFranges = RepoFranges.mida();
        String frangesActuals[][] = new String[nFranges][3];
        FranjaHoraria f;
        int horaInici, horaFi, minutInici, minutFi;
        float taxa;

        for (int i = 0; i < nFranges; i++) {
            f = (FranjaHoraria) RepoFranges.getFranja(i);
            horaInici = f.getHoraInici().get(Calendar.HOUR_OF_DAY);
            minutInici = f.getHoraInici().get(Calendar.MINUTE);
            horaFi = f.getHoraFi().get(Calendar.HOUR_OF_DAY);
            minutFi = f.getHoraFi().get(Calendar.MINUTE);
            taxa = f.getTaxa();

            frangesActuals[i][0] = "" + Integer.toString(horaInici) + ":" + Integer.toString(minutInici);
            frangesActuals[i][1] = "" + Integer.toString(horaFi) + ":" + Integer.toString(minutFi);
            frangesActuals[i][2] = "" + Float.toString(taxa);
        }

        return frangesActuals;
    }

    /**
     *  Aquesta funció substitueix totes les franges del repositori 
     *  agafant l'string de franges i convertint-lo en un array
     *  de franges, comprovant llavors la seva coherència i guardant-les al
     *  repositori.
     *   @param  nFranges Nombre de franges en que es dividirà un dia.
     *   @param  franges String amb les franges i els seus valors:
     *           horaInici, minutInici, horaFi, minutFi, taxa
     *   @pre    Les dades són correctes:
     *           nFranges menor o = que 24, i per tota Franja :
     *           { horaInici menor horaFi, i taxa >= 0.0 }, i venen ordenades
     *           de menor a major, sent horaFi de franges[i] UN minut abans que
     *           horaInici de franges[i+1]. La última és coincident amb un 
     *           minut de diferència amb la primera. Tot això es comprova a les Vistes. 
     *   @post   S'han introduit les noves franges al repositori de franges,
     *           sense haver-n'hi cap de solapada.
     *   @return Cert si la operació ha tingut èxit, fals sino.
     */
    public boolean setFranges(int nFranges, String[][] franges) {
        FranjaHoraria f;
        int horaInici, horaFi, minutInici, minutFi;
        float taxa;
        boolean possible;

        RepoFranges.esborrarLlista();

        for (int i = 0; i < nFranges; i++) {
            /* Una franjaHoraria es definia com:
             * Calendar horaInici, Calendar horaFi, float taxa
             */
            horaInici = Integer.parseInt(franges[i][0]);
            minutInici = Integer.parseInt(franges[i][1]);
            horaFi = Integer.parseInt(franges[i][2]);
            minutFi = Integer.parseInt(franges[i][3]);
            taxa = Float.parseFloat(franges[i][4]);

            /*Seria bo fer un set dels altres camps del Calendar per tenir
             * franges al mateix temps.
             */
            Calendar horaMinInici = Calendar.getInstance();
            horaMinInici.set(Calendar.MINUTE, minutInici);
            horaMinInici.set(Calendar.HOUR_OF_DAY, horaFi);

            Calendar horaMinFi = Calendar.getInstance();
            horaMinFi.set(Calendar.MINUTE, minutFi);
            horaMinFi.set(Calendar.HOUR_OF_DAY, horaFi);

            if (!RepoFranges.afegirElement(f = new FranjaHoraria(horaMinInici, horaMinFi, taxa))) {
                return false;
            }
        }

        return true;
    }

    /**
     *  Esborra tots els programes de la llista en memoria.
     *  @pre    S'ha inicialitzat previament la llista en memoria.
     *  @post   S'han esborrat tots els programes de la llista.
     *  @return Cert si la neteja ha tingut èxit. Fals altrament.
     */
    public void netejaLlistaProgrames() {
        RepoProg.buidarLlista();
    }

    /**
     *  Aquesta funcio agafa totes les estructures de les que s'encarrega
     *  la gestio de clients, i les guarda a disc. LlistaProgrames, LlistaFranges,
     *  LlistaTematiques.
     *  @pre    -
     *  @post   S'han guardat a disc el repositori de clients, de temes i de franges
     *          amb els noms RepoProgrames.db, RepoTemes.db
     *          i RepoFranges.db, al directori actual.
     *  @return Cert si la operació ha tingut èxit. Fals altrament.
     */
    public boolean saveGclientsAll() throws GestorDiscException {
        return RepoProg.saveAll() &&
                RepoTemes.exportaTemes("RepositoriTemes.db") &&
                RepoFranges.exportaFranges("RepositoriFranges.db");
    }

    /**
     *  Aquesta funcio carrega en memoria tots els repositoris estàndard 
     *  que hi ha guardats a disc amb els noms de RepoClients.db, RepoTemes.db
     *  i RepoFranges.db
     * @pre     Els fitxers existeixen.
     * @post    S'han carregat tots els repositoris en memoria.
     */
    public boolean carregaRepositoris() throws GestorDiscException {
        return RepoProg.importaProgrames("RepositoriProgrames.db") &&
                RepoTemes.importaTemes("RepositoriTemes.db") &&
                RepoFranges.importaFranges("RepositoriFranges.db");
    }

    /**
     *  Afageix una llista de tematiques a la llista de tematiques.  
     *  @param  tematiques Es un array de noms de tematiques que es volen introduir.
     *  @pre    -
     *  @post   S'ha afegit la tematica a la llista.
     */
    private void addTematicaRepo(String[] tematiques) 
    {
        int nTemes = tematiques.length;
        for (int i = 0; i < nTemes; i++) {
            Tematica T = new Tematica(tematiques[i].toLowerCase());
            RepoTemes.afegirElement(T);
        }
    }

    /**
     *  Cerca de la  llista de programes del repositori els que tenen el mateix
     *  nom que els que hi ha a la llista de noms d'entrada.
     *  @param  llistaNoms Un array de noms dels que volem obtenir-ne els programes.
     *  @pre    -
     *  @post   -
     *  @return Una llista de programes tals que per tot nom del programa "i", coincideix
     *  amb el nom de la llistaNoms[i].
     */
    private LinkedList<Programa> llistarProgramesNom(String[] llistaNoms) {
        int nProgs = llistaNoms.length;
        LinkedList<Programa> llista = new LinkedList<Programa>();
        Programa p;

        for (int i = 0; i < nProgs; i++) {
            p = (Programa) RepoProg.getPrograma(llistaNoms[i]);
            llista.add(p);
        }
        return llista;
    }
    /** Not Supported Yet.
     *  Cerca de la  llista de programes del repositori, aquells que compleixen els filtres
     *  demanats. Un filtre pot ser d'un tipus i té un valor. Es cridarà a getllistaFiltrada
     *  tantes vegades com diferents filtres hi hagi, i es concatenaran els resultats.
     *  @param  filtreIValor és una matriu tal que filtreIValor[N][2], sent N el nombre de
     *  tipus de filtres, i [N][0] el tipus de filtre i [N][1] el valor del filtre.
     *  @pre    filtreIValor té una dimensió de [N][2]
     *  @post   -
     *  @return Un array de noms que representen tots els programes coincidents a la cerca.
    public String[][] getLlistaMultiFiltre(String[][] filtreIValor) {
    String[][] resultat = null;
    for (int i = 0; i < filtreIValor.length; i++) {
    resultat[i] = getllistaFiltrada(filtreIValor[i][0], filtreIValor[i][1]);
    }
    return resultat;
    }
     */
}
