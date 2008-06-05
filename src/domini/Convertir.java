/**
 * La classe Convertir ens proporciona un metode comode per treballar amb les
 * dates que nosaltres necessitem. Es proporcionen varies funcions de comparacio, debug,
 * assignacions, sumes, restes de dies, etc.
 * 
 * Nota: Per utilitzar aquesta classe, nomes es necessari crear com a variable global
 * dins una altre classe, una variable tal que:
 * 
 * private Convertir Conv;
 * 
 * I a la constructora d'aquella classe inicialitzar Conv:
 * 
 * Conv = new Convertir();
 * 
 * Un cop fet aixo es pot utilitzar en tot el codi, per exemple, si tenim un String cadena
 * de la forma HH:MM o dd/MM/yyyy indiferentment podem fer el seguent:
 * 
 * Calendar prova = Conv.strToCalendar(cadena);
 * 
 * I ja tenim un Calendar nou setejat a "cadena".
 * 
 * Normalment les qui tenen la paraula "Data" al nom de la funcio, son perque treballen amb
 * strings de format dd/MM/yyyy. Recomanable mirar el codi de la funcio que s'utilitzara en
 * cada cas.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 6 Juny 2008 
 * 
 * Versio Final
 */
package domini;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Convertir {

    Calendar dataIni;
    Calendar dataFi;
    SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatCalendari = new SimpleDateFormat("dd/MM/yyyy");

    public Convertir() {
    }


    /*****************************************/
    /**OBTENIR STRINGS A PARTIR DE CALENDARS*/
    /***************************************/
    /**
     * Aquesta funcio genera un string de la forma:
     *  "dd/MM/yyyy a dd/MM/yyyy" a partir de dues dates
     * @param data1 Data que va a l'esquerra
     * @param data2 Data que va a la dreta
     * @return un string representatiu de la setmana, null si alguna data no es valida
     */
    public String setmana(Calendar data1, Calendar data2) {
        if (data1 == null || data2 == null) {
            return null;
        }

        Date aux = new Date(data1.getTimeInMillis());
        Date aux2 = new Date(data2.getTimeInMillis());

        String setmana = "" + formatCalendari.format(aux) + " a " + formatCalendari.format(aux2);

        return setmana;
    }

    /** Donat un Calendar ens retorna HH:MM
     * 
     * @param data1 El calendar del que volem la hora
     * @return Un string HH:MM 
     */
    public String getHora(Calendar data1) {
        if (data1 == null) {
            return null;
        }
        Date aux = new Date(data1.getTimeInMillis());
        return "" + formatHora.format(aux);
    }

    /**
     * Donat un Calendar el pasa a format dd/MM/yyyy
     * @param data1 Es la data que es vol obtenir
     * @return Un string que conte el dia, mes i any de data1. dd/MM/yyyy
     */
    public String dateToStr(Calendar data1) {
        if (data1 == null) {
            return null;
        }
        Date aux = new Date(data1.getTimeInMillis());

        return "" + formatCalendari.format(aux);
    }

    /**
     * Genera un string tal que "dd/MM/yyyy - dd/MM/yyyy" a partir
     * de dues dates.
     * @param data1 Data que va a l'esquerra
     * @param data2 Data que va a la dreta
     * @return l'string que representa l'id de la planificacio, null si
     * alguna data no es valida.
     */
    public String idPlanificacio(Calendar data1, Calendar data2) {
        if (data1 == null || data2 == null) {
            return null;
        }

        Date aux = new Date(data1.getTimeInMillis());
        Date aux2 = new Date(data2.getTimeInMillis());

        String setmana = "" + formatCalendari.format(aux) + " - " + formatCalendari.format(aux2);

        return setmana;
    }
    
    

    /*****************************************/
    /**OBTENIR CALENDARS A PARTIR DE STRINGS */
    /***************************************/
    
    
    /**
     *  Aquesta funcio retorna un Calendar nou a partir d'una
     * data en format string t.q. dd/MM/yyyy o HH:MM indiferentment
     */
    public Calendar strToCalendar(String data) {
        Date dia;
        Calendar out = Calendar.getInstance();
        try {
            dia = formatCalendari.parse(data);

        } catch (ParseException ex) {
            try {
                dia = formatHora.parse(data);
            } catch (ParseException ex2) {
                System.out.println("Error parsejant l'string strToCalendar " + ex2.getMessage());
                return null;
            }
        }
        out.setTime(dia);
        return out;
    }

    
     /**
     *  Aquesta funcio retorna un Calendar nou a partir d'una
     * data en format string t.q. dd/MM/yyyy o HH:MM indiferentment
      * @param data Es la data a la que volem setejar-li la hora
      * @param hora Es la hora que li volem posar
      * @pre -
      * @post S'ha setejat la hora del calendari a l'especificada a
      * l'string en format HH:MM
     */
    public void setHora(Calendar data, String hora) 
    {
        Calendar aux = strToCalendar(hora);
        int iHora = aux.get(Calendar.HOUR_OF_DAY);
        int min = aux.get(Calendar.MINUTE);
        int seg = 0;
        
        data.set(Calendar.HOUR_OF_DAY, iHora);
        data.set(Calendar.MINUTE, min);
        data.set(Calendar.SECOND, seg);
    }
    /**
     * Donat un string del format "dd/MM/yyyy - dd/MM/yyyy"
     * retorna dos calendars setejats a n'aquetes dates.
     * @param idPlanificacio
     * @return Array de 2 calendaris, el que esta a l'esquerra correspon a
     * [0], el de la dreta a [1]
     */
    public Calendar[] idPlanificacio(String idPlanificacio) {
        Calendar[] resultat;
        try {
            resultat = new Calendar[2];
            resultat[0] = Calendar.getInstance();
            resultat[1] = Calendar.getInstance();

            if (idPlanificacio.length() != 23) {
                return null;
            }
            idPlanificacio = idPlanificacio + " ";
            Date iniP = formatCalendari.parse(idPlanificacio.substring(0, 10));
            resultat[0].setTime(iniP);

            Date fiP = formatCalendari.parse(idPlanificacio.substring(13, 23));
            Calendar fiPlaniAux = Calendar.getInstance();
            resultat[1].setTime(fiP);

        } catch (ParseException ex) {
            System.out.println("Format incorrecte al convertir un idPlanificacio a Calendar[]");
            return null;
        }

        return resultat;
    }

    
    
    
     /*****************************************/
    /**             COMPARACIONS             */
    /***************************************/
    
    
    /**
     * Agafa dos calendaris i comprova si son iguals amb Any, Mes i Dia unicament
     * @param data1 Data del calendari 1 que volem comparar amb el calendari 2
     * @param data2 Data del calendari 2 que volem comparar amb el calendari 1
     * @return Cert si son iguals, fals altrament.
     */
    public boolean sonIgualsData(Calendar data1, Calendar data2) {
        int any1 = data1.get(Calendar.YEAR);
        int mes1 = data1.get(Calendar.MONTH);
        int dia1 = data1.get(Calendar.DATE);
        int any2 = data2.get(Calendar.YEAR);
        int mes2 = data2.get(Calendar.MONTH);
        int dia2 = data2.get(Calendar.DATE);

        return (any1 == any2 && dia1 == dia2 && mes1 == mes2);
    }

    /**
     * Agafa dos calendaris i els compara amb Any, Mes i Dia unicament
     * @param data1 Data del calendari 1 que volem comparar amb el calendari 2
     * @param data2 Data del calendari 2 que volem comparar amb el calendari 1
     * @return 0 si son iguals, 1 si data1 > data 2, -1 si data1 < data2
     */
    public int comparacioData(Calendar data1, Calendar data2) {
        int any1 = data1.get(Calendar.YEAR);
        int mes1 = data1.get(Calendar.MONTH);
        int dia1 = data1.get(Calendar.DATE);
        int any2 = data2.get(Calendar.YEAR);
        int mes2 = data2.get(Calendar.MONTH);
        int dia2 = data2.get(Calendar.DATE);

        if (any1 == any2 && dia1 == dia2 && mes1 == mes2) {
            return 0;
        }

        if (any1 == any2) {
            if (mes1 == mes2) {
                if (dia1 < dia2) {
                    return -1;
                }
                if (dia1 > dia2) {
                    return 1;
                }
            }

            if (mes1 < mes2) {
                return -1;
            }
            if (mes1 > mes2) {
                return 1;
            }
        }
        if (any1 < any2) {
            return -1;
        }
        if (any2 > any1) {
            return 1;
        }

        return 2;
    }

    /**
     * Es una funcio que compara dues hores. No mira res mes que HH i MM.
     * @param hora1 Hora a comparar amb hora2
     * @param hora2 Hora a comparar amb hora1
     * @return cert si son iguals en HH:MM, fals altrament.
     */
    public boolean horesDiferents(Calendar hora1, Calendar hora2) {
        int h1 = hora1.get(Calendar.HOUR_OF_DAY);
        int h2 = hora2.get(Calendar.HOUR_OF_DAY);

        if (h1 != h2) {
            return false;
        }

        int m1 = hora1.get(Calendar.MINUTE);
        int m2 = hora2.get(Calendar.MINUTE);

        if (m1 != m2) {
            return false;
        }
        return true;
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

        if (h1 >= h2) {
            if (m1 > m2) {
                return true;
            }
        }
        return false;
    }

    
    
    /*****************************************/
    /**        ALTRES OPERACIONS            */
    /***************************************/
    
    
    
    /** 
     * Aquesta funcio suma n dies al calendar passat
     * @param data1 Es la data a la que volem sumar els dies
     * @param nDies El nombre de dies a sumar
     * @pre -
     * @post S'han sumat nDies a la data1
     */
    public void sumaDies(Calendar data1, int nDies) {
        if (data1 != null) {
            data1.add(Calendar.DATE, nDies);
        }
    }

    /**
     * Aquesta funcio ens servira com a metode per pintar en pantalla una
     * data amb dd/MM/yyyy HH:MM i opcionalment un index i
     * @param data1 Calendari que es vol pintar.
     * @param index Index que volem que es pinti al costat de la informacio de debug.
     * Si val -1 no es pinta.
     *
     */
    public void debugCalendar(Calendar data1, int i) {
        if (i == -1) {
            System.out.println("Debug: " + dateToStr(data1) + " " + getHora(data1));
        } else {
            System.out.println("Debug " + i + ": " + dateToStr(data1) + " " + getHora(data1));
        }
    }
}
