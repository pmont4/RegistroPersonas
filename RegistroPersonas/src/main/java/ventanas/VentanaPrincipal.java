package ventanas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.App;
import objetos.Administrador;

public class VentanaPrincipal extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    
    public VentanaPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Control de personas");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            Integer personas = 0;
            if (App.getMySQL().checkConnection()) {
                try (PreparedStatement stmt = App.getMySQL().getConnection().prepareStatement("SELECT * FROM personas")) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            personas++;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Correctamente conectado a la base de datos, actualemente se cuentan con " + personas + " en la base de datos", "Conectado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos! por favor verificar la conexion con la misma para poder ejecutar el programa", "Base de datos no encontrada", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } catch (Exception ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
        }
        
        try {
            this.llenar_tabla();
        } catch (Exception ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getClass() + " ERROR: " + ex.getMessage());
        }
    }
    
    public void llenar_tabla() throws Exception {
        Object[] data = new Object[5];
        this.tableModel = (DefaultTableModel) this.tableDatos.getModel();
        
        try (PreparedStatement stmt = App.getMySQL().getConnection().prepareStatement("SELECT * FROM personas")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    data[0] = rs.getInt("id");
                    data[1] = rs.getString("nombre");
                    data[2] = rs.getString("edad");
                    data[3] = rs.getString("altura");
                    data[4] = rs.getString("genero");
                    
                    this.tableModel.addRow(data);
                }
            }
        }
        this.tableDatos.setModel(this.tableModel);
    }
    
    public void actualizar_texto_admin() {
        if (App.getAdminOnline() != null) {
            labelBienvenida.setText("Bienvenido/a, " + App.getAdminOnline().getNombre());
        } else {
            labelBienvenida.setText("Corriendo desde el IDE");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDatos = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        labelBienvenida = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        tableDatos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Edad", "Altura", "Genero"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableDatos.setFocusable(false);
        jScrollPane1.setViewportView(tableDatos);

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBorrar.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        labelBienvenida.setText("jLabel1");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("PERSONAS REGISTRADAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelBienvenida)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregar)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(58, 58, 58)
                        .addComponent(btnBorrar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(labelBienvenida)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (App.getAdminOnline() != null) {
            Administrador admin = App.getAdminOnline();
            if (admin.getPermisos().contains("agregar")) {
                App.getVentanaRegistro().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Lo sentimos, no cuentas con los permisos para hacer esto", "Sin permiso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        if (App.getAdminOnline() != null) {
            Administrador admin = App.getAdminOnline();
            if (admin.getPermisos().contains("borrar")) {
                App.getVentanaBorrar().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Lo sentimos, no cuentas con los permisos para hacer esto", "Sin permiso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBorrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelBienvenida;
    private javax.swing.JTable tableDatos;
    // End of variables declaration//GEN-END:variables

}
