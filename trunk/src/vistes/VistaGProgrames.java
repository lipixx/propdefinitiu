/**
 * La classe VistaGProgrames ens proporciona la pantalla que du tota la gestio
 * de programes de televisio.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 6 Juny 2008 
 * 
 */
package vistes;

import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

public class VistaGProgrames extends javax.swing.JPanel {

    public VistaGProgrames() {
        initComponents();
    }
    
    
    
    /**Modificadores dels atributs de les llistes i cuadres de text*/
    public void setLlistaProgrames(String[] strProgrames)
    {
        llistaProgrames.setListData(strProgrames);
    }

    public void setLlistaFiltres(String[] llistaEFiltres) {
        /*Afegim "Tots"*/
        String [] llistaAux = new String[llistaEFiltres.length+1];
        llistaAux[0] = "Tots";
        System.arraycopy(llistaEFiltres, 0, llistaAux, 1, llistaEFiltres.length);
        llistaFiltres.setListData(llistaAux);
    }
 
    public void setCuadreFitxa(String fitxa) 
    {
        fitxaPrograma.setText(fitxa);
    }
    
    public void clearFitxa() {
        fitxaPrograma.setText("");
    }

    
    /**Consultores de botons seleccionats i elements clicats a la llista*/
    public String getFClickedStr() 
    {
        if (buttonFiltreFormat.isSelected())  return "format";
        if (buttonFiltreCategoria.isSelected()) return  "categoria";
        if (buttonFiltreNom.isSelected()) return  "nom";
        if (buttonFiltreTematica.isSelected()) return  "tematica";
        return "";
    }

    public int getFClickedInt()
    {
        if (buttonFiltreFormat.isSelected()) return 0;
        if (buttonFiltreCategoria.isSelected()) return 1;
        if (buttonFiltreNom.isSelected()) return 2;
        if (buttonFiltreTematica.isSelected()) return 3;
        return -1;
    }

    public String getProgramaSelected() 
    {
        String programaSelected = (String) llistaProgrames.getSelectedValue();
        if (programaSelected == null) return "";
       return programaSelected;  
    }
       
    public String getFiltreSelected(boolean esTematica)
    {
        String result;
        if (!esTematica)
        {
        String filtreSelected = String.valueOf(llistaFiltres.getSelectedIndex()-1);
        if (filtreSelected == null) return "";
        return String.valueOf(llistaFiltres.getSelectedIndex()-1);
        }

            result = (String) llistaFiltres.getSelectedValue();
        
            if (result == null) return "";
        
            return result;
    }
   

    /**Inicialitzadores*/
    public void setListeners(ActionListener[] accions, ListSelectionListener selFiltres, ListSelectionListener selProgs) 
    {
        /**Associem cada ActionListener als botons, i el listener de seleccio a les llistes*/     
        buttonFiltreFormat.addActionListener(accions[0]);
        buttonFiltreCategoria.addActionListener(accions[1]);
        buttonFiltreNom.addActionListener(accions[2]);
        buttonFiltreTematica.addActionListener(accions[3]);
        buttonAddPrograma.addActionListener(accions[4]);
        buttonModificaPrograma.addActionListener(accions[5]);
        buttonEliminaPrograma.addActionListener(accions[6]);
        buttonSaveAll.addActionListener(accions[7]);
        
        llistaFiltres.getSelectionModel().addListSelectionListener(selFiltres);
        llistaProgrames.getSelectionModel().addListSelectionListener(selProgs);
    }  
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        llistaProgrames = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        llistaFiltres = new javax.swing.JList();
        buttonFiltreFormat = new javax.swing.JToggleButton();
        buttonFiltreCategoria = new javax.swing.JToggleButton();
        buttonFiltreNom = new javax.swing.JToggleButton();
        buttonFiltreTematica = new javax.swing.JToggleButton();
        buttonModificaPrograma = new javax.swing.JButton();
        buttonEliminaPrograma = new javax.swing.JButton();
        buttonAddPrograma = new javax.swing.JButton();
        buttonSaveAll = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        fitxaPrograma = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(0, 0));

        llistaProgrames.setModel(new javax.swing.DefaultListModel() {
            String[] strings = { "LLista no inicialitzada" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        llistaProgrames.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(llistaProgrames);

        llistaFiltres.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Tots" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        llistaFiltres.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        llistaFiltres.setSelectedIndex(0);
        jScrollPane2.setViewportView(llistaFiltres);

        buttonGroup1.add(buttonFiltreFormat);
        buttonFiltreFormat.setSelected(true);
        buttonFiltreFormat.setText("Format");

        buttonGroup1.add(buttonFiltreCategoria);
        buttonFiltreCategoria.setText("Categoria");

        buttonGroup1.add(buttonFiltreNom);
        buttonFiltreNom.setText("Cerca per Nom");

        buttonGroup1.add(buttonFiltreTematica);
        buttonFiltreTematica.setText("Tematica");

        buttonModificaPrograma.setText("Modificar");

        buttonEliminaPrograma.setText("Eliminar");

        buttonAddPrograma.setText("Afegir nou Programa");

        buttonSaveAll.setText("Guardar Canvis");

        fitxaPrograma.setColumns(1);
        fitxaPrograma.setEditable(false);
        fitxaPrograma.setRows(6);
        fitxaPrograma.setAutoscrolls(false);
        jScrollPane3.setViewportView(fitxaPrograma);

        jLabel1.setText("Fitxa del programa:");

        jLabel2.setText("Llista de programes:");

        jLabel3.setText("Filtre");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonFiltreFormat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(buttonFiltreCategoria, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonFiltreNom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                                    .addComponent(buttonFiltreTematica, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(buttonEliminaPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonModificaPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(buttonSaveAll)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addComponent(buttonAddPrograma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(436, 436, 436)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonModificaPrograma)
                                    .addComponent(buttonEliminaPrograma))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonAddPrograma))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(buttonFiltreFormat)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(buttonFiltreCategoria)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonFiltreTematica)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(buttonSaveAll))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(buttonFiltreNom)))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddPrograma;
    private javax.swing.JButton buttonEliminaPrograma;
    private javax.swing.JToggleButton buttonFiltreCategoria;
    private javax.swing.JToggleButton buttonFiltreFormat;
    private javax.swing.JToggleButton buttonFiltreNom;
    private javax.swing.JToggleButton buttonFiltreTematica;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonModificaPrograma;
    private javax.swing.JButton buttonSaveAll;
    private javax.swing.JTextArea fitxaPrograma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList llistaFiltres;
    private javax.swing.JList llistaProgrames;
    // End of variables declaration//GEN-END:variables
}
