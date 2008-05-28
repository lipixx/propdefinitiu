package vistes;

import java.util.Calendar;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * gestionarClientes.java
 *
 * @author Francisco Javier Rojas (e6464692)
 */
public class VistaGestionaCliente extends javax.swing.JFrame {

    private static String[] cabecerasTabla = {"ID", "Nombre", "Apellidos"};
    private static char[] caracteresPermitidos = new String(" ������������������������\'���-").toCharArray();

    /** Creates new form gestionarClientes */
    public VistaGestionaCliente() {
        initComponents();
    }

    public void clearDatosConsulta() {
        IDField00.setText("");
        IDField01.setText("");
        IDField02.setText("");
        IDField03.setText("");
        ApField00.setText("");
        ApField01.setText("");
        ApField02.setText("");
        ApField03.setText("");
        NoField00.setText("");
        NoField01.setText("");
        NoField02.setText("");
        NoField03.setText("");
        AAAAField00.setText("");
        AAAAField01.setText("");
        AAAAField02.setText("");
        AAAAField03.setText("");
        MMField00.setText("");
        MMField01.setText("");
        MMField02.setText("");
        MMField03.setText("");
        DDField00.setText("");
        DDField01.setText("");
        DDField02.setText("");
        DDField03.setText("");
        AFChkBox00.getModel().setSelected(false);
        AFChkBox01.getModel().setSelected(false);
        AFChkBox02.getModel().setSelected(true);
        AFChkBox03.getModel().setSelected(false);
        GuButton01.setEnabled(false);
        GuButton03.setEnabled(false);
        IntFactCombo00.setSelectedIndex(0);
        IntFactCombo01.setSelectedIndex(0);
        IntFactCombo02.setSelectedIndex(0);
        IntFactCombo03.setSelectedIndex(0);
        tablaDatos.clearSelection();
    }

    public void setDatosConsulta(
            String ID, String Nombre, String Apellidos, Calendar FechaNacimiento,
            boolean autoFact, int intFact) {
        IDField00.setText(ID);
        IDField01.setText(ID);
        IDField02.setText("");
        IDField03.setText(ID);
        ApField00.setText(Apellidos);
        ApField01.setText(Apellidos);
        ApField02.setText("");
        ApField03.setText(Apellidos);
        NoField00.setText(Nombre);
        NoField01.setText(Nombre);
        NoField02.setText("");
        NoField03.setText(Nombre);
        AAAAField00.setText("" + FechaNacimiento.get(Calendar.YEAR));
        AAAAField01.setText("" + FechaNacimiento.get(Calendar.YEAR));
        AAAAField02.setText("");
        AAAAField03.setText("" + FechaNacimiento.get(Calendar.YEAR));
        MMField00.setText("" + (FechaNacimiento.get(Calendar.MONTH) + 1));
        MMField01.setText("" + (FechaNacimiento.get(Calendar.MONTH) + 1));
        MMField02.setText("");
        MMField03.setText("" + (FechaNacimiento.get(Calendar.MONTH) + 1));
        DDField00.setText("" + FechaNacimiento.get(Calendar.DATE));
        DDField01.setText("" + FechaNacimiento.get(Calendar.DATE));
        DDField02.setText("");
        DDField03.setText("" + FechaNacimiento.get(Calendar.DATE));
        AFChkBox00.getModel().setSelected(autoFact);
        AFChkBox01.getModel().setSelected(autoFact);
        AFChkBox02.getModel().setSelected(true);
        AFChkBox03.getModel().setSelected(autoFact);
        IntFactCombo00.setSelectedIndex(intFact);
        IntFactCombo01.setSelectedIndex(intFact);
        IntFactCombo02.setSelectedIndex(0);
        IntFactCombo03.setSelectedIndex(intFact);
        GuButton01.setEnabled(true);
        GuButton03.setEnabled(true);

    }

    public void setAction(ActionListener[] actList,
            ListSelectionListener rowList,
            WindowListener winList) {
        GuButton01.addActionListener(actList[0]);
        GuButton02.addActionListener(actList[1]);
        GuButton03.addActionListener(actList[2]);
        //menuCargar.addActionListener(actList[3]);
        menuGuardar.addActionListener(actList[3]);
        menuSalir.addActionListener(actList[4]);
        tablaDatos.getSelectionModel().addListSelectionListener(rowList);
        this.addWindowListener(winList);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void setListado(Object[][] listaDatos) {
        ((DefaultTableModel) (tablaDatos.getModel())).setDataVector(listaDatos, cabecerasTabla);
        tablaDatos.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        clearDatosConsulta();

    }
/*
    public void actListado(Object[][] listaDatos) {
        ((DefaultTableModel) (tablaDatos.getModel())).setDataVector(listaDatos, cabecerasTabla);
        tablaDatos.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        clearDatosConsulta();
    }
*/
    public String getIDFilaSeleccionada() {
        if (tablaDatos.getSelectedRowCount() == 1) {
            return (String) tablaDatos.getModel().getValueAt(tablaDatos.getSelectedRow(), 0);
        } else {
            return null;
        }
    }

    /**
    public String getIDAgregar() {
    if (checkDatosAgregar()) {
    return IDField02.getText();
    } else {
    return null;
    }
    }
    public String getNombreAgregar() {
    if (checkDatosAgregar()) {
    return NoField02.getText();
    } else {
    return null;
    }
    }
    public String getApellidoAgregar() {
    if (checkDatosAgregar()) {
    return ApField02.getText();
    } else {
    return null;
    }
    }
     */
    private Calendar getFeNacAgregar() {
        if (checkDatosAgregar()) {
            Calendar aux = Calendar.getInstance();
            aux.set(Integer.parseInt(AAAAField02.getText()), Integer.parseInt(MMField02.getText()) - 1, Integer.parseInt(DDField02.getText()));
            return aux;
        } else {
            return null;
        }
    }

    /**
    public boolean getAutoFactAgregar() {
    if (checkDatosAgregar()) {
    return this.AFChkBox01.isSelected();
    } else {
    return false;
    }
    }
    public int getIntervalFactAgregar() {
    if (checkDatosAgregar()) {
    return IntFactCombo02.getSelectedIndex();
    } else {
    return 0;
    }
    }*/
    public Object[] getDatosAgregar() {
        if (checkDatosAgregar()) {
            return new Object[]{
                IDField02.getText(),
                NoField02.getText(),
                ApField02.getText(),
                getFeNacAgregar(),
                AFChkBox02.isSelected(),
                IntFactCombo02.getSelectedIndex()
            };
        } else {
            return null;
        }
    }

    private boolean checkDatosAgregar() {
        boolean b0 = !IDField02.getText().isEmpty();
        boolean b1 = chkAlfabetico(NoField02.getText());
        boolean b2 = chkAlfabetico(ApField02.getText());
        int b3 = chkFecha(AAAAField02.getText(), MMField02.getText(), DDField02.getText());
        if (!b0) {
            alerta("Identificador incorrecto:\nEl campo ID no puede estar vac�o");
        }
        if (!b1) {
            alerta("Formato de Nombre incorrecto\nSolo puede haber car�ceres alfab�ticos, espacios, o los siguientes s�mbolos: \' � � � -");
        }
        if (!b2) {
            alerta("Formato de Apellido incorrecto:\nSolo puede haber car�ceres alfab�ticos, espacios, o los siguientes s�mbolos: \' � � � -");
        }
        if (b3 == -1) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nEl formato correcto es dd/mm/aaaa");
        } else if (b3 == 1) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nA�o fuera de rango");
        } else if (b3 == 2) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nMes fuera de rango");
        } else if (b3 == 1) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nDia fuera de rango");
        }
        return b0 && b1 && b2 && (b3 == 0);
    }

    /**
    public String getIDModificar() {
    return IDField01.getText();
    }
    public String getNombreModificar() {
    return NoField01.getText();
    }
    public String getApellidoModificar() {
    return ApField01.getText();
    }
     */
    private Calendar getFeNacModificar() {
        Calendar aux = Calendar.getInstance();
        aux.set(Integer.parseInt(AAAAField01.getText()), Integer.parseInt(MMField01.getText()) - 1, Integer.parseInt(DDField01.getText()));
        return aux;
    }

    /**
    public boolean getAutoFactModificar() {
    return this.AFChkBox02.isSelected();
    }
    public int getIntervalFactModificar() {
    return IntFactCombo01.getSelectedIndex();
    }*/
    public Object[] getDatosModificar() {
        if (checkDatosModificar()) {
            return new Object[]{
                //IDField01.getText(),
                NoField01.getText(),
                ApField01.getText(),
                getFeNacModificar(),
                AFChkBox01.isSelected(),
                IntFactCombo01.getSelectedIndex()
            };
        } else {
            return null;
        }
    }

    private boolean checkDatosModificar() {
        boolean b1 = chkAlfabetico(NoField01.getText());
        boolean b2 = chkAlfabetico(ApField01.getText());
        int b3 = chkFecha(AAAAField01.getText(), MMField01.getText(), DDField01.getText());
        if (!b1) {
            alerta("Formato de Nombre incorrecto\nSolo puede haber car�ceres alfab�ticos, espacios, o los siguientes s�mbolos: \' � � � -");
        }
        if (!b2) {
            alerta("Formato de Apellido incorrecto:\nSolo puede haber car�ceres alfab�ticos, espacios, o los siguientes s�mbolos: \' � � � -");
        }
        if (b3 == -1) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nEl formato correcto es dd/mm/aaaa");
        } else if (b3 == 1) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nA�o fuera de rango");
        } else if (b3 == 2) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nMes fuera de rango");
        } else if (b3 == 1) {
            alerta("Formato de Fecha de Nacimiento incorrecto:\nDia fuera de rango");
        }
        return b1 && b2 && (b3 == 0);
    }

    protected boolean chkAlfabetico(String Frase) {
        if (Frase.isEmpty()) {
            return false;
        }
        boolean correcto = true;
        int i = 0;
        char[] auxData = Frase.toCharArray();
        while (correcto != false && i < auxData.length) {
            correcto = false;
            if ((auxData[i] >= 'a' && auxData[i] <= 'z') || (auxData[i] >= 'A' && auxData[i] <= 'Z')) {
                correcto = true;
            } else {
                for (char c : caracteresPermitidos) {
                    if (auxData[i] == c) {
                        correcto = true;
                        break;
                    }
                }
            }
            i++;
        }
        return correcto;
    }

    protected boolean chkNumNatural(String Numero) {
        if (Numero.isEmpty()) {
            return false;
        }
        boolean correcto = true;
        int i = 0;
        char[] auxData = Numero.toCharArray();
        while (correcto != false && i < auxData.length) {
            if (auxData[i] < '0' || auxData[i] > '9') {
                correcto = false;
            }
            i++;
        }
        return correcto;

    }

    protected int chkFecha(String Anyo, String Mes, String Dia) {
        if (!chkNumNatural(Anyo) || !chkNumNatural(Mes) || !chkNumNatural(Dia)) {
            return -1;
        }
        int a = Integer.parseInt(Anyo);
        int m = Integer.parseInt(Mes);
        int d = Integer.parseInt(Dia);
        int dias_mes = 31;
        java.util.GregorianCalendar hoy = (java.util.GregorianCalendar) java.util.GregorianCalendar.getInstance();
        if (a < 1890 || a > hoy.get(java.util.GregorianCalendar.YEAR)) {
            return 1;
        }
        if (m < 1 || m > 12) {
            return 2;
        }

        if (m == 2) {
            if (hoy.isLeapYear(a)) {
                dias_mes = 29;
            } else {
                dias_mes = 28;
            }
        }
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            dias_mes = 31;
        }
        if (m == 4 || m == 6 || m == 9 || m == 11) {
            dias_mes = 30;
        }
        hoy.set(a, m, 1);
        if (d < 1 || d > dias_mes) {
            return 3;
        }
        return 0;
    }
    /**
    protected int conversionMes(int mes){
    switch(mes){
    case Calendar.JANUARY: return 1;
    case Calendar.FEBRUARY: return 2;
    case Calendar.MARCH: return 3;
    case Calendar.APRIL: return 4;
    case Calendar.MAY: return 5;
    case Calendar.JUNE: return 6;
    case Calendar.JULY: return 7;
    case Calendar.AUGUST: return 8;
    case Calendar.SEPTEMBER: return 9;
    case Calendar.OCTOBER: return 10;
    case Calendar.NOVEMBER: return 11;
    case Calendar.DECEMBER: return 12;
    default:
    return 0;
    }
    }*/


    public static void alerta(String mensaje) {
        JOptionPane.showMessageDialog(null, (Object) mensaje);
    }

    public static boolean confirmacion(String mensaje) {
        int resp = JOptionPane.showConfirmDialog(null, mensaje, "Confirmaci�n", JOptionPane.OK_CANCEL_OPTION);
        return (resp == JOptionPane.OK_OPTION);

    }

    public static int pregunta(String mensaje) {
        int resp = JOptionPane.showConfirmDialog(null, mensaje, "Confirmaci�n", JOptionPane.YES_NO_CANCEL_OPTION);
        if (resp == JOptionPane.CANCEL_OPTION) {
            return -1;
        }
        if (resp == JOptionPane.NO_OPTION) {
            return 0;
        }
        if (resp == JOptionPane.YES_OPTION) {
            return 1;
        }
        return 0;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelConsultar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        ApField00 = new javax.swing.JTextField();
        NoField00 = new javax.swing.JTextField();
        IDField00 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        DDField00 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        MMField00 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        AAAAField00 = new javax.swing.JTextField();
        AFChkBox00 = new javax.swing.JCheckBox();
        IntFactCombo00 = new javax.swing.JComboBox();
        panelModificar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        GuButton01 = new javax.swing.JButton();
        ApField01 = new javax.swing.JTextField();
        NoField01 = new javax.swing.JTextField();
        IDField01 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        DDField01 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        MMField01 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        AAAAField01 = new javax.swing.JTextField();
        AFChkBox01 = new javax.swing.JCheckBox();
        IntFactCombo01 = new javax.swing.JComboBox();
        panelAgregar = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        GuButton02 = new javax.swing.JButton();
        ApField02 = new javax.swing.JTextField();
        NoField02 = new javax.swing.JTextField();
        IDField02 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        DDField02 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        MMField02 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        AAAAField02 = new javax.swing.JTextField();
        AFChkBox02 = new javax.swing.JCheckBox();
        IntFactCombo02 = new javax.swing.JComboBox();
        panelEliminar = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        GuButton03 = new javax.swing.JButton();
        ApField03 = new javax.swing.JTextField();
        NoField03 = new javax.swing.JTextField();
        IDField03 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        DDField03 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        MMField03 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        AAAAField03 = new javax.swing.JTextField();
        AFChkBox03 = new javax.swing.JCheckBox();
        IntFactCombo03 = new javax.swing.JComboBox();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuGuardar = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );

        jMenu1.setText("Menu");

        jMenuItem1.setText("Agregar Cliente...");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Eliminar Cliente");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Modificar Cliente...");
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Item");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "DNI", "Apellidos", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatos.setPreferredSize(null);
        tablaDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablaDatos);
        tablaDatos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaDatos.getColumnModel().getColumn(0).setResizable(false);
        tablaDatos.getColumnModel().getColumn(1).setResizable(false);
        tablaDatos.getColumnModel().getColumn(2).setResizable(false);

        jLabel9.setText("ID:");

        jLabel10.setText("Apellidos:");

        jLabel11.setText("Fecha de Nacimiento:");

        jLabel12.setText("Nombre:");

        ApField00.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ApField00.setEnabled(false);

        NoField00.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NoField00.setEnabled(false);

        IDField00.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IDField00.setEnabled(false);
        IDField00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDField00ActionPerformed(evt);
            }
        });

        DDField00.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        DDField00.setEnabled(false);
        DDField00.setPreferredSize(new java.awt.Dimension(15, 20));
        DDField00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDField00ActionPerformed(evt);
            }
        });

        jLabel19.setText("/");

        MMField00.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        MMField00.setEnabled(false);

        jLabel20.setText("/");

        AAAAField00.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        AAAAField00.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(DDField00, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MMField00, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AAAAField00, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(AAAAField00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel20)
                .addComponent(MMField00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel19)
                .addComponent(DDField00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        AFChkBox00.setText("Activar Auto-Facturación");
        AFChkBox00.setEnabled(false);
        AFChkBox00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AFChkBox00ActionPerformed(evt);
            }
        });

        IntFactCombo00.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mensual", "Semanal", "Diaria" }));
        IntFactCombo00.setEnabled(false);

        javax.swing.GroupLayout panelConsultarLayout = new javax.swing.GroupLayout(panelConsultar);
        panelConsultar.setLayout(panelConsultarLayout);
        panelConsultarLayout.setHorizontalGroup(
            panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultarLayout.createSequentialGroup()
                .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConsultarLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(NoField00, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(ApField00, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(IDField00, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
                    .addGroup(panelConsultarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelConsultarLayout.createSequentialGroup()
                                .addComponent(AFChkBox00)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IntFactCombo00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelConsultarLayout.setVerticalGroup(
            panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(IDField00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(ApField00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(NoField00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConsultarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AFChkBox00)
                    .addComponent(IntFactCombo00, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Consultar", panelConsultar);

        jLabel5.setText("ID:");

        jLabel6.setText("Apellidos:");

        jLabel7.setText("Fecha de Nacimiento:");

        jLabel8.setText("Nombre:");

        GuButton01.setText("Guardar");
        GuButton01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuButton01ActionPerformed(evt);
            }
        });

        IDField01.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IDField01.setEnabled(false);

        DDField01.setPreferredSize(new java.awt.Dimension(15, 20));
        DDField01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDField01ActionPerformed(evt);
            }
        });

        jLabel21.setText("/");

        jLabel22.setText("/");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(DDField01, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MMField01, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AAAAField01, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(AAAAField01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel22)
                .addComponent(MMField01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel21)
                .addComponent(DDField01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        AFChkBox01.setText("Activar Auto-Facturación");
        AFChkBox01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AFChkBox01ActionPerformed(evt);
            }
        });

        IntFactCombo01.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mensual", "Semanal", "Diaria" }));

        javax.swing.GroupLayout panelModificarLayout = new javax.swing.GroupLayout(panelModificar);
        panelModificar.setLayout(panelModificarLayout);
        panelModificarLayout.setHorizontalGroup(
            panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModificarLayout.createSequentialGroup()
                .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelModificarLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NoField01, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(ApField01, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(IDField01, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
                    .addGroup(panelModificarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelModificarLayout.createSequentialGroup()
                                .addComponent(AFChkBox01)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IntFactCombo01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(GuButton01))))
                .addContainerGap())
        );
        panelModificarLayout.setVerticalGroup(
            panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModificarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(IDField01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(ApField01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(NoField01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AFChkBox01)
                    .addComponent(IntFactCombo01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GuButton01)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modificar", panelModificar);

        jLabel13.setText("ID:");

        jLabel14.setText("Apellidos:");

        jLabel15.setText("Fecha de Nacimiento:");

        jLabel16.setText("Nombre:");

        GuButton02.setText("Guardar");
        GuButton02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuButton02ActionPerformed(evt);
            }
        });

        DDField02.setPreferredSize(new java.awt.Dimension(15, 20));
        DDField02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDField02ActionPerformed(evt);
            }
        });

        jLabel27.setText("/");

        jLabel28.setText("/");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(DDField02, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MMField02, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AAAAField02, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(AAAAField02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel28)
                .addComponent(MMField02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel27)
                .addComponent(DDField02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        AFChkBox02.setText("Activar Auto-Facturación");
        AFChkBox02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AFChkBox02ActionPerformed(evt);
            }
        });

        IntFactCombo02.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mensual", "Semanal", "Diaria" }));

        javax.swing.GroupLayout panelAgregarLayout = new javax.swing.GroupLayout(panelAgregar);
        panelAgregar.setLayout(panelAgregarLayout);
        panelAgregarLayout.setHorizontalGroup(
            panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarLayout.createSequentialGroup()
                .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAgregarLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(NoField02, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(ApField02, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(IDField02, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
                    .addGroup(panelAgregarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelAgregarLayout.createSequentialGroup()
                                .addComponent(AFChkBox02)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IntFactCombo02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(GuButton02))))
                .addContainerGap())
        );
        panelAgregarLayout.setVerticalGroup(
            panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAgregarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(IDField02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(ApField02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(NoField02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AFChkBox02)
                    .addComponent(IntFactCombo02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GuButton02)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Agregar", panelAgregar);

        jLabel23.setText("ID:");

        jLabel24.setText("Apellidos:");

        jLabel25.setText("Fecha de Nacimiento:");

        jLabel26.setText("Nombre:");

        GuButton03.setText("Eliminar");
        GuButton03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuButton03ActionPerformed(evt);
            }
        });

        ApField03.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ApField03.setEnabled(false);

        NoField03.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NoField03.setEnabled(false);

        IDField03.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IDField03.setEnabled(false);

        DDField03.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        DDField03.setEnabled(false);
        DDField03.setPreferredSize(new java.awt.Dimension(15, 20));
        DDField03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DDField03ActionPerformed(evt);
            }
        });

        jLabel33.setText("/");

        MMField03.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        MMField03.setEnabled(false);

        jLabel34.setText("/");

        AAAAField03.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        AAAAField03.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(DDField03, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MMField03, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AAAAField03, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(AAAAField03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel34)
                .addComponent(MMField03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel33)
                .addComponent(DDField03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        AFChkBox03.setText("Activar Auto-Facturación");
        AFChkBox03.setEnabled(false);
        AFChkBox03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AFChkBox03ActionPerformed(evt);
            }
        });

        IntFactCombo03.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mensual", "Semanal", "Diaria" }));
        IntFactCombo03.setEnabled(false);

        javax.swing.GroupLayout panelEliminarLayout = new javax.swing.GroupLayout(panelEliminar);
        panelEliminar.setLayout(panelEliminarLayout);
        panelEliminarLayout.setHorizontalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEliminarLayout.createSequentialGroup()
                .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEliminarLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IDField03, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(ApField03, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(NoField03, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
                    .addGroup(panelEliminarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelEliminarLayout.createSequentialGroup()
                                .addComponent(AFChkBox03)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IntFactCombo03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(GuButton03))))
                .addContainerGap())
        );
        panelEliminarLayout.setVerticalGroup(
            panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEliminarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(IDField03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(ApField03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(NoField03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEliminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AFChkBox03)
                    .addComponent(IntFactCombo03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GuButton03)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Eliminar", panelEliminar);

        jMenu2.setText("Archivo");

        menuGuardar.setText("Guardar Listado");
        jMenu2.add(menuGuardar);

        menuSalir.setText("Salir");
        jMenu2.add(menuSalir);

        jMenuBar2.add(jMenu2);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void DDField00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDField00ActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_DDField00ActionPerformed

    private void GuButton03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuButton03ActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_GuButton03ActionPerformed

    private void GuButton02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuButton02ActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_GuButton02ActionPerformed

    private void GuButton01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuButton01ActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_GuButton01ActionPerformed

    private void IDField00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDField00ActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_IDField00ActionPerformed

    private void DDField01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDField01ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_DDField01ActionPerformed

    private void DDField02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDField02ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_DDField02ActionPerformed

    private void DDField03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DDField03ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_DDField03ActionPerformed

    private void AFChkBox03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AFChkBox03ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_AFChkBox03ActionPerformed

    private void AFChkBox00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AFChkBox00ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_AFChkBox00ActionPerformed

    private void AFChkBox01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AFChkBox01ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_AFChkBox01ActionPerformed

    private void AFChkBox02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AFChkBox02ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_AFChkBox02ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new VistaGestionaCliente().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AAAAField00;
    private javax.swing.JTextField AAAAField01;
    private javax.swing.JTextField AAAAField02;
    private javax.swing.JTextField AAAAField03;
    private javax.swing.JCheckBox AFChkBox00;
    private javax.swing.JCheckBox AFChkBox01;
    private javax.swing.JCheckBox AFChkBox02;
    private javax.swing.JCheckBox AFChkBox03;
    private javax.swing.JTextField ApField00;
    private javax.swing.JTextField ApField01;
    private javax.swing.JTextField ApField02;
    private javax.swing.JTextField ApField03;
    private javax.swing.JTextField DDField00;
    private javax.swing.JTextField DDField01;
    private javax.swing.JTextField DDField02;
    private javax.swing.JTextField DDField03;
    private javax.swing.JButton GuButton01;
    private javax.swing.JButton GuButton02;
    private javax.swing.JButton GuButton03;
    private javax.swing.JTextField IDField00;
    private javax.swing.JTextField IDField01;
    private javax.swing.JTextField IDField02;
    private javax.swing.JTextField IDField03;
    private javax.swing.JComboBox IntFactCombo00;
    private javax.swing.JComboBox IntFactCombo01;
    private javax.swing.JComboBox IntFactCombo02;
    private javax.swing.JComboBox IntFactCombo03;
    private javax.swing.JTextField MMField00;
    private javax.swing.JTextField MMField01;
    private javax.swing.JTextField MMField02;
    private javax.swing.JTextField MMField03;
    private javax.swing.JTextField NoField00;
    private javax.swing.JTextField NoField01;
    private javax.swing.JTextField NoField02;
    private javax.swing.JTextField NoField03;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JPanel panelAgregar;
    private javax.swing.JPanel panelConsultar;
    private javax.swing.JPanel panelEliminar;
    private javax.swing.JPanel panelModificar;
    private javax.swing.JTable tablaDatos;
    // End of variables declaration//GEN-END:variables
}
