/**
 * La classe VistaSelectProgrames ens proporciona la pantalla en le que s'escullen
 * els programes que es volen introduir individualment a una planificacio. El generador
 * agafara aquesta llista de programes i generara planificacions d'acord amb ella i amb
 * els criteris passats previament.
 * 
 * @author  Felip Moll 41743858P
 * @version 1.0, 6 Juny 2008 
 * 
 */
package vistes;

import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;

public class VistaSelectProgrames extends javax.swing.JDialog {

    Vector<String> seleccionats;

    public VistaSelectProgrames(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        seleccionats = new Vector<String>();
    }

    /**Modificadores dels atributs de les llistes i cuadres de text*/
    public void setLlistaProgrames(String[] strProgrames) {
        llistaProgrames.setListData(strProgrames);
    }

    public void setLlistaFiltres(String[] llistaEFiltres) {
        /*Afegim "Tots"*/
        String[] llistaAux = new String[llistaEFiltres.length + 1];
        llistaAux[0] = "Tots";
        System.arraycopy(llistaEFiltres, 0, llistaAux, 1, llistaEFiltres.length);
        llistaFiltres.setListData(llistaAux);
    }

    public void setCuadreFitxa(String fitxa) {
        fitxaPrograma.setText(fitxa);
    }

    public void neteja() {
        seleccionats.clear();
        llistaSeleccionats.setListData(seleccionats);
        clearFitxa();
    }

    public void clearFitxa() {
        fitxaPrograma.setText("");
    }

    /**Consultores dels camps de la finestra*/
    public int getFClickedInt() {
        if (buttonFiltreFormat.isSelected()) {
            return 0;
        }
        if (buttonFiltreCategoria.isSelected()) {
            return 1;
        }
        if (buttonFiltreNom.isSelected()) {
            return 2;
        }
        if (buttonFiltreTematica.isSelected()) {
            return 3;
        }
        return -1;
    }

    public String getFClickedStr() {
        if (buttonFiltreFormat.isSelected()) {
            return "format";
        }
        if (buttonFiltreCategoria.isSelected()) {
            return "categoria";
        }
        if (buttonFiltreNom.isSelected()) {
            return "nom";
        }
        if (buttonFiltreTematica.isSelected()) {
            return "tematica";
        }
        return "";
    }

    public String getFiltreSelected(boolean esTematica) {
        String result;
        if (!esTematica) {
            String filtreSelected = String.valueOf(llistaFiltres.getSelectedIndex() - 1);
            if (filtreSelected == null) {
                return "";
            }
            return String.valueOf(llistaFiltres.getSelectedIndex() - 1);
        }

        result = (String) llistaFiltres.getSelectedValue();

        if (result == null) {
            return "";
        }

        return result;
    }

    public String getProgramaSelected() {
        String programaSelected = (String) llistaProgrames.getSelectedValue();
        if (programaSelected == null) {
            return "";
        }
        return programaSelected;
    }

    public Vector<String> getLlistaSeleccionats() {
        return seleccionats;
    }

    String getSeleccioSelected() {
        String seleccioSelected = (String) llistaSeleccionats.getSelectedValue();
        if (seleccioSelected == null) {
            return "";
        }
        return seleccioSelected;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        llistaFiltres = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        llistaProgrames = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        llistaSeleccionats = new javax.swing.JList();
        botoEliminar = new javax.swing.JButton();
        buttonAddPrograma = new javax.swing.JButton();
        botoGenerar = new javax.swing.JButton();
        botoCancelar = new javax.swing.JButton();
        buttonFiltreFormat = new javax.swing.JToggleButton();
        buttonFiltreCategoria = new javax.swing.JToggleButton();
        buttonFiltreTematica = new javax.swing.JToggleButton();
        buttonFiltreNom = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        fitxaPrograma = new javax.swing.JTextArea();
        neteja = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        llistaFiltres.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Tots" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        }
    );
    llistaFiltres.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane1.setViewportView(llistaFiltres);

    llistaProgrames.setModel(new javax.swing.AbstractListModel() {
        String[] strings = { "Llista no inicialitzada" };
        public int getSize() { return strings.length; }
        public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPane3.setViewportView(llistaProgrames);

    llistaSeleccionats.setModel(new javax.swing.AbstractListModel() {
        String[] strings = { null };
        public int getSize() { return strings.length; }
        public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPane4.setViewportView(llistaSeleccionats);

    botoEliminar.setText("<<");
    botoEliminar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            botoEliminarActionPerformed(evt);
        }
    });

    buttonAddPrograma.setText(">>");
    buttonAddPrograma.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buttonAddProgramaActionPerformed(evt);
        }
    });

    botoGenerar.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
    botoGenerar.setText("Generar");

    botoCancelar.setText("Cancel·la");
    botoCancelar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            botoCancelarActionPerformed(evt);
        }
    });

    buttonGroup1.add(buttonFiltreFormat);
    buttonFiltreFormat.setSelected(true);
    buttonFiltreFormat.setText("Format");

    buttonGroup1.add(buttonFiltreCategoria);
    buttonFiltreCategoria.setText("Categoria");

    buttonGroup1.add(buttonFiltreTematica);
    buttonFiltreTematica.setText("Tematica");

    buttonGroup1.add(buttonFiltreNom);
    buttonFiltreNom.setText("Cerca per nom");

    fitxaPrograma.setColumns(1);
    fitxaPrograma.setEditable(false);
    fitxaPrograma.setRows(6);
    fitxaPrograma.setAutoscrolls(false);
    jScrollPane2.setViewportView(fitxaPrograma);

    neteja.setText("Neteja");
    neteja.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            netejaActionPerformed(evt);
        }
    });

    jLabel1.setText("Fitxa del programa:");

    jLabel2.setText("Llista de programes:");

    jLabel3.setText("Filtre:");

    jLabel4.setText("Escollits:");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(34, 34, 34)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addComponent(buttonFiltreNom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addComponent(buttonFiltreFormat, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addComponent(buttonFiltreTematica, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addComponent(buttonFiltreCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                    .addGap(47, 47, 47)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(jLabel2)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(botoEliminar)
                                .addComponent(buttonAddPrograma))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(botoCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(neteja)
                                .addComponent(botoGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))))
            .addGap(44, 44, 44))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(23, 23, 23)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(143, 143, 143)
                            .addComponent(buttonAddPrograma)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(botoEliminar))
                        .addComponent(jScrollPane4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoCancelar)
                            .addComponent(botoGenerar))))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jLabel3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addGap(12, 12, 12)
                    .addComponent(buttonFiltreFormat)
                    .addGap(18, 18, 18)
                    .addComponent(buttonFiltreCategoria)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(buttonFiltreTematica)
                    .addGap(12, 12, 12)
                    .addComponent(buttonFiltreNom)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
            .addGap(30, 30, 30))
        .addGroup(layout.createSequentialGroup()
            .addGap(384, 384, 384)
            .addComponent(neteja)
            .addContainerGap(147, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents
    /**Accions dels botons*/
    private void buttonAddProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddProgramaActionPerformed
        Object[] nomsProgs = llistaProgrames.getSelectedValues();

        for (int i = 0; i < nomsProgs.length; i++) {
            if (!seleccionats.contains(nomsProgs[i])) {
                seleccionats.addElement((String) nomsProgs[i]);
            }
        }
        llistaSeleccionats.setListData(seleccionats); 
    }//GEN-LAST:event_buttonAddProgramaActionPerformed

    private void botoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoEliminarActionPerformed
        Object[] nomsProgs = llistaSeleccionats.getSelectedValues();

        for (int i = 0; i < nomsProgs.length; i++) {
            if (seleccionats.contains(nomsProgs[i])) {
                seleccionats.removeElement((String) nomsProgs[i]);
            }
        }
        llistaSeleccionats.setListData(seleccionats);
        llistaSeleccionats.setSelectedIndex(llistaSeleccionats.getMinSelectionIndex() + 1);

    }//GEN-LAST:event_botoEliminarActionPerformed

    private void netejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_netejaActionPerformed
        seleccionats.clear();
        llistaSeleccionats.setListData(seleccionats);
}//GEN-LAST:event_netejaActionPerformed

    private void botoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoCancelarActionPerformed
        seleccionats.clear();
        llistaSeleccionats.setListData(seleccionats);
        this.setVisible(false);
    }//GEN-LAST:event_botoCancelarActionPerformed

    void setListeners(ActionListener[] accions, ListSelectionListener selFiltres, ListSelectionListener selProgs, ListSelectionListener selSel) {
        /**Associem cada ActionListener als botons, i el listener de seleccio a les llistes*/
        buttonFiltreFormat.addActionListener(accions[0]);
        buttonFiltreCategoria.addActionListener(accions[1]);
        buttonFiltreNom.addActionListener(accions[2]);
        buttonFiltreTematica.addActionListener(accions[3]);

        botoGenerar.addActionListener(accions[4]);

        llistaFiltres.getSelectionModel().addListSelectionListener(selFiltres);
        llistaProgrames.getSelectionModel().addListSelectionListener(selProgs);
        llistaSeleccionats.getSelectionModel().addListSelectionListener(selSel);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botoCancelar;
    private javax.swing.JButton botoEliminar;
    private javax.swing.JButton botoGenerar;
    private javax.swing.JButton buttonAddPrograma;
    private javax.swing.JToggleButton buttonFiltreCategoria;
    private javax.swing.JToggleButton buttonFiltreFormat;
    private javax.swing.JToggleButton buttonFiltreNom;
    private javax.swing.JToggleButton buttonFiltreTematica;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea fitxaPrograma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList llistaFiltres;
    private javax.swing.JList llistaProgrames;
    private javax.swing.JList llistaSeleccionats;
    private javax.swing.JButton neteja;
    // End of variables declaration//GEN-END:variables
}
