package ventanas;

import conector.MySQLConector;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.App;

public class VentanaDatosMySQL extends javax.swing.JFrame {

    public VentanaDatosMySQL() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Configuracion");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public File archivo = new File(App.directorio_config.getAbsolutePath() + "\\config.txt");

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fieldHost = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fieldBaseDatos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldUsuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldPuerto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        checkBoxSSL = new javax.swing.JCheckBox();
        fieldContrasena = new javax.swing.JPasswordField();
        btnGuardar = new javax.swing.JButton();
        labelConexion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setText("BASE DE DATOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("Host:");

        fieldHost.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        fieldHost.setToolTipText("Si se deja en blanco, su valor sera \"localhost\"");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("Base de datos:");

        fieldBaseDatos.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setText("Usuario:");

        fieldUsuario.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setText("Contraseña:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setText("Puerto:");

        fieldPuerto.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        fieldPuerto.setToolTipText("Si se deja en blanco, su valor sera 3306");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setText("SSL:");

        checkBoxSSL.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        checkBoxSSL.setText("Requerido");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldHost)
                    .addComponent(fieldBaseDatos)
                    .addComponent(fieldUsuario)
                    .addComponent(fieldPuerto)
                    .addComponent(checkBoxSSL)
                    .addComponent(fieldContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldBaseDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fieldContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(fieldPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(checkBoxSSL))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        labelConexion.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        labelConexion.setText("Estado conexion: DESCONECTADO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelConexion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(labelConexion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (!(fieldBaseDatos.getText().equals("") && fieldContrasena.getPassword().length < 0 && fieldUsuario.getText().equals(""))) {
            try {
                StringBuilder linea_conexion = new StringBuilder();

                linea_conexion.append("usuario:").append(fieldUsuario.getText()).append(",");
                linea_conexion.append("contraseña:").append(new String(fieldContrasena.getPassword())).append(",");
                linea_conexion.append("database:").append(fieldBaseDatos.getText()).append(",");

                if (!(fieldHost.getText().isEmpty())) {
                    linea_conexion.append("host:").append(fieldHost.getText()).append(",");
                } else {
                    linea_conexion.append("host:localhost,");
                }

                if (!(fieldPuerto.getText().isEmpty())) {
                    linea_conexion.append("port:").append(fieldPuerto.getText()).append(",");
                } else {
                    linea_conexion.append("port:3306,");
                }

                if (checkBoxSSL.isSelected()) {
                    linea_conexion.append("ssl:true");
                } else {
                    linea_conexion.append("ssl:false");
                }

                if (archivo.exists()) {
                    if (archivo.delete()) {
                        if (archivo.createNewFile()) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                                writer.write(linea_conexion.toString());

                                MySQLConector mySQL = new MySQLConector();
                                mySQL.setDatabase(fieldBaseDatos.getText());
                                mySQL.setUser(fieldUsuario.getText());
                                mySQL.setHost(fieldHost.getText());
                                mySQL.setPassword(new String(fieldContrasena.getPassword()));
                                mySQL.setPort(Integer.parseInt(fieldPuerto.getText()));
                                if (checkBoxSSL.isSelected()) {
                                    mySQL.setSSL("true");
                                } else {
                                    mySQL.setSSL("false");
                                }
                                mySQL.configure();
                                mySQL.connect();

                                if (mySQL.checkConnection()) {
                                    JOptionPane.showMessageDialog(null, "La configuracion de la base datos fue correctamente guardada y se comprobo la conexion, se reiniciara el programa", "Configuracion", JOptionPane.INFORMATION_MESSAGE);
                                    this.labelConexion.setText("Estado conexión: CONECTADO");
                                }
                                this.dispose();
                            } catch (Exception ex) {
                                Logger.getLogger(VentanaDatosMySQL.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } else {
                    if (archivo.createNewFile()) {
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                            writer.write(linea_conexion.toString());

                            MySQLConector mySQL = new MySQLConector();
                            mySQL.setDatabase(fieldBaseDatos.getText());
                            mySQL.setUser(fieldUsuario.getText());
                            mySQL.setHost(fieldHost.getText());
                            mySQL.setPassword(new String(fieldContrasena.getPassword()));
                            mySQL.setPort(Integer.parseInt(fieldPuerto.getText()));
                            if (checkBoxSSL.isSelected()) {
                                mySQL.setSSL("true");
                            } else {
                                mySQL.setSSL("false");
                            }
                            mySQL.configure();
                            mySQL.connect();

                            if (mySQL.checkConnection()) {
                                this.labelConexion.setText("Estado conexión: CONECTADO");
                                JOptionPane.showMessageDialog(null, "La configuracion de la base datos fue correctamente guardada y se comprobo la conexion, se reiniciara el programa", "Configuracion", JOptionPane.INFORMATION_MESSAGE);
                            }
                            this.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Algunos elementos importantes en la conexion no pueden ser dejados en blanco", "Conexion", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox checkBoxSSL;
    private javax.swing.JTextField fieldBaseDatos;
    private javax.swing.JPasswordField fieldContrasena;
    private javax.swing.JTextField fieldHost;
    private javax.swing.JTextField fieldPuerto;
    private javax.swing.JTextField fieldUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelConexion;
    // End of variables declaration//GEN-END:variables

}
