package frames;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Main;
import utils.HikariConnection;

public class MySQLConfig_Frame extends javax.swing.JFrame {

    private final HikariConnection hikari;

    public MySQLConfig_Frame() {
        initComponents();

        hikari = new HikariConnection();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Credenciales MySQL");

        JOptionPane.showMessageDialog(null, "Antes de inicar la aplicacion, es requisito contar con una conexion a MySQL, por favor rellene los campos solicitados.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean isWorking = false;

    private String user;
    private String pass;
    private String database;
    private String host;
    private int port;
    private String ssl;

    public void setConnectionInformation() {
        boolean portFieldContainsLetters = false;
        for (int i = 0; i < this.portField.getText().length(); i++) {
            char c = this.portField.getText().charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                portFieldContainsLetters = true;
            }
        }
        
        if (!portFieldContainsLetters) {
            if (!(this.userField.getText().isEmpty() && this.passField.getText().isEmpty() && this.databaseField.getText().isEmpty())) {
                try {
                    user = this.userField.getText();
                    pass = this.passField.getText();
                    database = this.databaseField.getText();
                    if (!this.hostField.getText().isEmpty()) {
                        host = this.hostField.getText();
                    } else {
                        host = "localhost";
                        this.hostField.setText("localhost");
                    }
                    if (!this.portField.getText().isEmpty()) {
                        port = Integer.parseInt(this.portField.getText());
                    } else {
                        port = 3306;
                        this.portField.setText("3306");
                    }

                    if (this.sslCheckBox.isSelected()) {
                        ssl = "true";
                    } else {
                        ssl = "false";
                    }

                    hikari.setUser(user);
                    hikari.setPassword(pass);
                    hikari.setDatabase(database);
                    hikari.setPort(port);
                    hikari.setHost(host);
                    hikari.setSsl(ssl);

                    this.waitingLabel.setText("Comprobando informacion...");

                    hikari.configureProperties();
                    hikari.connect();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Algunos campos necesarios parecen no haber sido rellenados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo del puerto no puede contener letras", "Advertencia", JOptionPane.WARNING_MESSAGE);
            this.portField.setText("");
        }
    }

    private Optional<Connection> checkConnection() throws SQLException {
        if (hikari.getConnection() != null) {
            return Optional.of(hikari.getConnection());
        } else {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        userField = new javax.swing.JTextField();
        passLabel = new javax.swing.JLabel();
        passField = new javax.swing.JTextField();
        hostLabel = new javax.swing.JLabel();
        hostField = new javax.swing.JTextField();
        databaseLabel = new javax.swing.JLabel();
        databaseField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        sslLabel = new javax.swing.JLabel();
        sslCheckBox = new javax.swing.JCheckBox();
        saveButton = new javax.swing.JButton();
        checkConnButton = new javax.swing.JButton();
        waitingLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setText("INFORMACION");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel1)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MySQL:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        userLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        userLabel.setText("Usuario:");

        userField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        userField.setToolTipText("Requerido");

        passLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        passLabel.setText("Contraseña:");

        passField.setToolTipText("Requerido");

        hostLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        hostLabel.setText("Host:");

        hostField.setToolTipText("Opcional (Si no se llena sera localhost)");

        databaseLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        databaseLabel.setText("Database:");

        databaseField.setToolTipText("Requerido");

        portLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        portLabel.setText("Puerto:");

        portField.setToolTipText("Opcional (Si no se llena sera 3306)");

        sslLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        sslLabel.setText("SSL:");

        sslCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        sslCheckBox.setText("Requerido");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userLabel)
                            .addComponent(passLabel)
                            .addComponent(hostLabel)
                            .addComponent(databaseLabel)
                            .addComponent(portLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userField)
                            .addComponent(passField)
                            .addComponent(hostField)
                            .addComponent(databaseField)
                            .addComponent(portField, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(sslLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sslCheckBox)
                        .addGap(31, 31, 31)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel)
                    .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hostLabel)
                    .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(databaseLabel)
                    .addComponent(databaseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(portLabel)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sslLabel)
                    .addComponent(sslCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        saveButton.setText("Guardar");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        checkConnButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        checkConnButton.setText("Probar");
        checkConnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkConnButtonActionPerformed(evt);
            }
        });

        waitingLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        waitingLabel.setText("Esperando datos...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(waitingLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkConnButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(checkConnButton)
                    .addComponent(waitingLabel))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkConnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkConnButtonActionPerformed
        try {
            this.setConnectionInformation();
            Optional<Connection> conn = this.checkConnection();
            if (conn.isPresent()) {
                this.isWorking = true;
                this.waitingLabel.setText("Conexion correctamente establecida.");
            } else {
                this.waitingLabel.setText("Conexion invalida.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "La conexion no fue establecida, por favor comprobar los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_checkConnButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (this.isWorking) {
            if (this.userField.getText().equals(user) && this.passField.getText().equals(pass) && this.databaseField.getText().equals(database) && this.portField.getText().equals(String.valueOf(port)) && this.hostField.getText().equals(host)) {
                if (this.ssl.equals("true") && this.sslCheckBox.isSelected()) {
                    try {
                        Main.getJSON_Configuration().writeJsonConfigMySQL(user, pass, host, database, port, ssl);

                        JOptionPane.showMessageDialog(null, "Los datos han sido correctamente guardados, reinicie el programa.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else if (this.ssl.equals("false") && !this.sslCheckBox.isSelected()) {
                    try {
                        Main.getJSON_Configuration().writeJsonConfigMySQL(user, pass, host, database, port, ssl);

                        JOptionPane.showMessageDialog(null, "Los datos han sido correctamente guardados, reinicie el programa.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cambios fueron detectaos en los datos ingresados, por favor volver a comprobar la conexion antes de seguir", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    this.waitingLabel.setText("Esperando datos...");
                    this.isWorking = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cambios fueron detectaos en los datos ingresados, por favor volver a comprobar la conexion antes de seguir", "Advertencia", JOptionPane.WARNING_MESSAGE);
                this.waitingLabel.setText("Esperando datos...");
                this.isWorking = false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Todavia no se ha verificado si la conexion puede ser establecida, por favor antes de guardar los datos presione el boton de prueba.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkConnButton;
    private javax.swing.JTextField databaseField;
    private javax.swing.JLabel databaseLabel;
    private javax.swing.JTextField hostField;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField passField;
    private javax.swing.JLabel passLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JCheckBox sslCheckBox;
    private javax.swing.JLabel sslLabel;
    private javax.swing.JTextField userField;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel waitingLabel;
    // End of variables declaration//GEN-END:variables

}
