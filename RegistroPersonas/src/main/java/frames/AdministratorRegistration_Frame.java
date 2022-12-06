package frames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Main;

public class AdministratorRegistration_Frame extends javax.swing.JFrame {

    public AdministratorRegistration_Frame() {
        initComponents();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Registro administradores");
    }

    public void registerAdmin() throws SQLException {
        if (!(this.nameField.getText().isEmpty() && this.mailField.getText().isEmpty() && this.passField.getPassword().length > 0)) {
            if (this.mailField.getText().contains("@")) {
                if (this.passField.getPassword().length >= 8) {
                    if (this.addCheckBox.isSelected() || this.removeCheckBox.isSelected() || this.modifyCheckBox.isSelected()) {
                        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators WHERE mail=?")) {
                            stmt.setString(1, this.mailField.getText());
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (!rs.next()) {
                                    String name = this.nameField.getText();
                                    String mail = this.mailField.getText();
                                    String pass = new String(this.passField.getPassword());
                                    String address;
                                    if (!this.addressField.getText().isEmpty()) {
                                        address = this.addressField.getText();
                                    } else {
                                        address = "None";
                                    }
                                    
                                    StringBuilder sb = new StringBuilder();
                                    
                                    if (this.addCheckBox.isSelected()) {
                                        sb.append("add").append(",");
                                    }
                                    if (this.removeCheckBox.isSelected()) {
                                        sb.append("remove").append(",");
                                    }
                                    if (this.modifyCheckBox.isSelected()) {
                                        sb.append("modify").append(",");
                                    }
                                    
                                    String perms = sb.toString();
                                    
                                    String new_perms = "";
                                    char last_char = perms.substring(perms.length() - 1).charAt(0);
                                    if (last_char == ',') {
                                        new_perms = perms.substring(0, perms.length() - 1);
                                    }
                                    
                                    try (PreparedStatement stmt2 = Main.getMySQLConnection().prepareStatement("INSERT INTO administrators (name, mail, password, address, perms) VALUES (?,?,?,?,?)")) {
                                        stmt2.setString(1, name);
                                        stmt2.setString(2, mail);
                                        stmt2.setString(3, pass);
                                        stmt2.setString(4, address);
                                        stmt2.setString(5, new_perms);
                                        stmt2.execute();
                                    }
                                    
                                    JOptionPane.showMessageDialog(null, "El administrador " + name + " fue registrado exitosamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                    this.clear();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Ya existe un administrador registrado con esa direccion de correo electronico.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Al menos 1 permiso debe ser seleccionado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La contraseña introducida es demasiado corta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La direccion de correo electronico introducida no es valida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Algunos campos requeridos no han sido rellenados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clear() {
        this.nameField.setText("");
        this.mailField.setText("");
        this.passField.setText("");
        this.addressField.setText("");
        this.addCheckBox.setSelected(false);
        this.removeCheckBox.setSelected(false);
        this.modifyCheckBox.setSelected(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        mailLabel = new javax.swing.JLabel();
        mailField = new javax.swing.JTextField();
        passLabel = new javax.swing.JLabel();
        passField = new javax.swing.JPasswordField();
        addressLabel = new javax.swing.JLabel();
        addressField = new javax.swing.JTextField();
        permsLabel = new javax.swing.JLabel();
        addCheckBox = new javax.swing.JCheckBox();
        removeCheckBox = new javax.swing.JCheckBox();
        modifyCheckBox = new javax.swing.JCheckBox();
        registerButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setText("REGISTRO");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel2.setText("(Administradores)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(29, 29, 29))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameLabel.setText("Nombre:");

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        mailLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        mailLabel.setText("Correo:");

        mailField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        passLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        passLabel.setText("Contraseña:");

        addressLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        addressLabel.setText("Direccion:");

        addressField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        permsLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        permsLabel.setText("Permisos:");

        addCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        addCheckBox.setText("Agregar");

        removeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        removeCheckBox.setText("Borrar");

        modifyCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        modifyCheckBox.setText("Modificar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(mailLabel)
                    .addComponent(passLabel)
                    .addComponent(addressLabel)
                    .addComponent(permsLabel))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nameField)
                            .addComponent(mailField)
                            .addComponent(passField)
                            .addComponent(addressField, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(addCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(removeCheckBox)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(modifyCheckBox)
                .addGap(54, 54, 54))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mailLabel)
                    .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(permsLabel)
                    .addComponent(addCheckBox)
                    .addComponent(removeCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modifyCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        registerButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        registerButton.setText("Registrar");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        exitButton.setText("Salir");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        clearButton.setText("Limpiar");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(registerButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerButton)
                    .addComponent(exitButton)
                    .addComponent(clearButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        try {
            this.registerAdmin();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox addCheckBox;
    private javax.swing.JTextField addressField;
    private javax.swing.JLabel addressLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField mailField;
    private javax.swing.JLabel mailLabel;
    private javax.swing.JCheckBox modifyCheckBox;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPasswordField passField;
    private javax.swing.JLabel passLabel;
    private javax.swing.JLabel permsLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JCheckBox removeCheckBox;
    // End of variables declaration//GEN-END:variables

}
