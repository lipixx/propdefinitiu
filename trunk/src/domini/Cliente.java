package domini;


//import dades.Factura;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * Cliente.java
 *
 * Created on 17 / abril / 2008, 10:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author Francisco Javier Rojas (e6464692)
 */
public class Cliente implements Serializable, ClasseAmbClau<String>{

    protected String Nombre;
    protected String Apellidos;
    protected Calendar FechaNacimiento;
    protected String Domicilio;
    protected String ID;
    protected boolean autoFact;
    protected int intervalFact; //0: MES |  1: SETMANA | 2:DIA
    protected Calendar ultFact;
    //protected ArrayList<Factura> listFact;
    protected ArrayList<Planificacio> listPlan;

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
      //  listFact = new ArrayList<Factura>();
        listPlan = new ArrayList<Planificacio>();

    }  
    
    public Cliente(){

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

    public boolean setUltimaFactura(Calendar nuevoUltFact) {
        this.ultFact = Calendar.getInstance();
        this.ultFact.setTime(nuevoUltFact.getTime());
        return true;
    }/*
    public boolean addFactura(Factura F){
        listFact.ensureCapacity(listFact.size()+1);
        listFact.add(F);
        return true;
    }
    public Factura getFactura(int id){
        for(Factura f : listFact){
            if(f.getId()==id) return f;
        }
        return null;
    }
    public boolean clearFactura(){
        listFact = new ArrayList<Factura>();
        return true;
    }*/
    public boolean addPlanificacio(Planificacio P){
        listPlan.ensureCapacity(listPlan.size()+1);
        listPlan.add(P);
        return true;
    }
    public Planificacio getPlanificacio(int id){
        for(Planificacio p : listPlan){
            if(p.getId()==id) return p;
        }
        return null;
    }
    public boolean clearPlanificacio(){
        listPlan = new ArrayList<Planificacio>();
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
      //  listFact = new ArrayList<Factura>();
        listPlan = (ArrayList<Planificacio>) objecteACopiar.listPlan.clone();        
        //objecteACopiar = new Cliente(this.ID,this.Nombre,this.Apellidos,this.FechaNacimiento,this.autoFact, this.intervalFact);    
    }

    @Override
    public String getClau() {
        return this.ID;
    }
}