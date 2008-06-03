package domini;


//import dades.Factura;
import domini.programa.*;
import java.io.Serializable;
import java.util.Calendar;

/*
 * Cliente.java
 *
 * Created on 17 / abril / 2008, 10:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.util.LinkedList;
/**
 *
 * @author Francisco Javier Rojas (e6464692)
 */
@SuppressWarnings("unchecked")
public class Cliente implements Serializable, ClasseAmbClau<String>{

    protected String Nombre;
    protected String Apellidos;
    protected Calendar FechaNacimiento;
    protected String Domicilio;
    protected String ID;
    protected boolean autoFact;
    protected int intervalFact; //0: MES |  1: SETMANA | 2:DIA
    protected Calendar ultFact;
    protected LinkedList<Factura> listFact;
    protected LinkedList<Planificacio> listPlan;

    /** Creates a new instance of Cliente */
    public Cliente(
            String ID, String Nombre, String Apellidos, Calendar FechaNacimiento,
            boolean autoFact, int intervalFact) {
        this.ID = new String(ID);
        this.Nombre = new String(Nombre);
        this.Apellidos = new String(Apellidos);
        this.FechaNacimiento = (Calendar) FechaNacimiento.clone();
        this.autoFact = autoFact;
        this.intervalFact = intervalFact;
        ultFact = Calendar.getInstance();
        listFact = new LinkedList<Factura>();
        listPlan = new LinkedList<Planificacio>();
        testingFactures();

    }  
    
    public Cliente(){

    }

    public void testingFactures()
    {
        //Cream Emissio
        Calendar data1 = Calendar.getInstance();
        Calendar data2 = (Calendar) data1.clone();
        data2.add(Calendar.MINUTE,20);
        
        float k = 10;
        AltresC programa = new AltresC("Test-1",data1,"Descripcio",k);
        FranjaHoraria nouFranja = new FranjaHoraria(data1,data2,k);
        Emissio emi = new Emissio(data1, true, false, programa,nouFranja,data1,data2);
        //Fi Crear Emissio
        
        Planificacio P;
        
       for (int ka=0; ka<3; ka++)
       {
           Calendar data3 = (Calendar) data2.clone();
           data3.add(Calendar.MINUTE, 210);
           P = new Planificacio(data1,data3);
           
        for (int i=0; i<1; i++)
        {
            programa = new AltresC("Prog Num: "+ i +"-"+ ka, data1,"Descripcio",k);
            emi = new  Emissio(data1, false, false, programa,nouFranja,data1,data2);
            emi.setPreu(emi.getPreu()+i);
            P.addEmissioPlanificacio(emi);
            System.out.println("Hora ini "+data1.getTime()+" de prog "+i);
            System.out.println("Hora Fi"+data2.getTime()+" de prog "+i);
            data1.add(Calendar.MINUTE, 10);
            data2.add(Calendar.MINUTE, 20);
        }
           listPlan.add(P);
       }

    }

    public String getId() {
        return ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public Calendar getFechaNacimiento() {
        return FechaNacimiento;
    }

    public boolean getAutoFact() {
        return autoFact;
    }

    public int getIntervaloFactura() {
        return intervalFact;
    }

    public Calendar getUltimaFactura() {
        return ultFact;
    }

    public LinkedList<Factura> getListFact()
    {
        return listFact;
    }
    
    public boolean setId(String nuevoID) {
        ID = new String(nuevoID);
        return true;
    }

    public boolean setNombre(String nuevoNombre) {
        Nombre = new String(nuevoNombre);
        return true;
    }

    public boolean setApellidos(String nuevosApellidos) {
        Apellidos = new String(nuevosApellidos);
        return true;
    }

    public boolean setFechaNacimiento(Calendar nuevaFechaNacimiento) {
        this.FechaNacimiento = Calendar.getInstance();
        this.FechaNacimiento.setTime(nuevaFechaNacimiento.getTime());
        return true;
    }

    public boolean setAutoFact(boolean nuevoAutoFact) {
        this.autoFact = nuevoAutoFact;
        return true;
    }

    public boolean setIntervaloFactura(int nuevoIntervalFact) {
        this.intervalFact = nuevoIntervalFact;
        return true;
    }

    public boolean setUltimaFactura(Calendar nuevoUltFact) 
    {
        this.ultFact = (Calendar) nuevoUltFact.clone();
        return true;
    }
            
    public boolean addFactura(Factura F)
    {
        listFact.add(F);
        return true;
    }
    
    public Factura getFactura(int id)
    {
        for(Factura f : listFact){
            if(f.getId()==id) return f;
        }
        return null;
    }
    
    public boolean clearFactura(){
        listFact = new LinkedList<Factura>();
        return true;
    }
    
    public boolean addPlanificacio(Planificacio P){
        listPlan.add(P);
        return true;
    }
    public Planificacio getPlanificacio(int id)
    {
        for(Planificacio p : listPlan){
            if(p.getId()==id) return p;
        }
        return null;
    }
    
    /** AFEGIDAAAAAAA CERCA PER DATA INI DATA FI**/
    public Planificacio getPlanificacio(Calendar dataIni, Calendar dataFi)
    {
        for (Planificacio p : listPlan)
        {
            if (p.getDataInici().equals(dataIni) && p.getDataFi().equals(dataFi))
            {
                return p;
            }
        }
        return null;
    }
    
    public LinkedList<Planificacio> getLlistaPlan()
    {
        return listPlan;
    }
    
    /*************************************************/
    
    public boolean clearPlanificacio(){
        listPlan = new LinkedList<Planificacio>();
        return true;
    }

    public void setCopia(ClasseAmbClau objecteACopiar2) {
        Cliente objecteACopiar = (Cliente) objecteACopiar2;
        ID = new String(objecteACopiar.ID);
        Nombre = new String(objecteACopiar.Nombre);
        Apellidos = new String(objecteACopiar.Apellidos);
        FechaNacimiento = (Calendar) objecteACopiar.FechaNacimiento.clone();
        autoFact = objecteACopiar.autoFact;
        intervalFact = objecteACopiar.intervalFact;
        ultFact = (Calendar) objecteACopiar.ultFact.clone();
        listFact = (LinkedList<Factura>) objecteACopiar.getListFact().clone();
        listPlan = (LinkedList<Planificacio>) objecteACopiar.listPlan.clone();        
    }

    @Override
    public String getClau() {
        return this.ID;
    }

    public void delPlanificacio(Planificacio P) {
        listPlan.remove(P);
    }
}