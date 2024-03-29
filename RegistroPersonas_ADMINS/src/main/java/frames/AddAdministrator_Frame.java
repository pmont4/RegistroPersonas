package frames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import main.Main;
import utils.Log;

public class AddAdministrator_Frame extends javax.swing.JInternalFrame {

    /**
     * Creates new form AddAdministrator_Frame
     */
    public AddAdministrator_Frame() {
        initComponents();
    }

    public void clear() {
        this.nameField.setText("");
        this.passField.setText("");
        this.mailField.setText("");
        this.addressField.setText("");
        this.addCheckBox.setSelected(false);
        this.removeCheckBox.setSelected(false);
        this.modifyCheckBox.setSelected(false);
    }

    private boolean checkPassword(char[] pass) {
        Pattern pCapL = Pattern.compile("([A-Z])");
        Matcher mCapL = pCapL.matcher(new String(pass));
        Pattern pNum = Pattern.compile("([0-9])");
        Matcher mNum = pNum.matcher(new String(pass));
        
        return mCapL.find() && mNum.find();
    }

    public void saveAdministrator() throws Exception {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators WHERE mail=?")) {
            stmt.setString(1, this.mailField.getText());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    if (!(this.nameField.getText().isEmpty() && this.mailField.getText().isEmpty() && this.passField.getPassword().length > 0)) {
                        if (this.checkPassword(this.passField.getPassword())) {
                            if (this.mailField.getText().contains("@")) {
                                if (this.addCheckBox.isSelected() || this.removeCheckBox.isSelected() || this.modifyCheckBox.isSelected()) {

                                    String name = this.nameField.getText();
                                    String mail = this.mailField.getText();
                                    String pass = new String(this.passField.getPassword());
                                    String address = "None";

                                    if (!this.addressField.getText().isEmpty()) {
                                        address = this.addressField.getText();
                                    }

                                    StringBuilder sb = new StringBuilder();
                                    if (this.addCheckBox.isSelected()) {
                                        sb.append("add").append(",");
                                    }
                                    if (this.modifyCheckBox.isSelected()) {
                                        sb.append("modify").append(",");
                                    }
                                    if (this.removeCheckBox.isSelected()) {
                                        sb.append("remove").append(",");
                                    }
                                    String perms = sb.toString().substring(0, sb.toString().length() - 1);

                                    try (Statement st = Main.getMySQLConnection().createStatement()) {
                                        st.execute("CREATE TABLE IF NOT EXISTS " + name + "_log (date VARCHAR(45) NOT NULL, log TEXT NOT NULL, PRIMARY KEY(date))");
                                    }

                                    Main.getAdministratorManager().createAdministrator(name, mail, pass, address, perms);
                                    JOptionPane.showMessageDialog(null, "El administrador " + name + " fue agregado a la base de datos.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                    this.clear();

                                    Main.getMain_Frame().clearTable();
                                    Main.getMain_Frame().fillTable(Main.getAdministratorManager().getAdministrator_list());

                                } else {
                                    JOptionPane.showMessageDialog(null, "Se requiere al menos 1 permiso para registrar al administrador.", "Permisos", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "La direccion de correo electronico no es valida.", "Correo electronico", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "La contraseña debe contener al menos un caracter en mayuscula y un numero.", "Contraseña", JOptionPane.WARNING_MESSAGE);
                            this.passField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Alguno de los campos necesarios no han sido llenados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El administrador ya existe en la base de datos.", "Administrador", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        mailLabel = new javax.swing.JLabel();
        mailField = new javax.swing.JTextField();
        passLabel = new javax.swing.JLabel();
        passField = new javax.swing.JPasswordField();
        addressLabel = new javax.swing.JLabel();
        addressField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        addCheckBox = new javax.swing.JCheckBox();
        removeCheckBox = new javax.swing.JCheckBox();
        modifyCheckBox = new javax.swing.JCheckBox();
        saveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Agregar administrador");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameLabel.setText("Nombre:");

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        mailLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        mailLabel.setText("Correo electronico:");

        passLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        passLabel.setText("Contraseña:");

        addressLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        addressLabel.setText("Direccion:");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel1.setText("Permisos:");

        addCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        addCheckBox.setText("Agregar");

        removeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        removeCheckBox.setText("Borrar");

        modifyCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        modifyCheckBox.setText("Modificar");

        saveButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        saveButton.setText("Registrar");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        clearButton.setText("Limpiar");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(mailLabel)
                            .addComponent(passLabel)
                            .addComponent(addressLabel)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(addCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addComponent(removeCheckBox))
                            .addComponent(nameField)
                            .addComponent(mailField)
                            .addComponent(passField)
                            .addComponent(addressField))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(modifyCheckBox)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mailLabel)
                    .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addCheckBox)
                        .addComponent(removeCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modifyCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(clearButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            this.saveAdministrator();

            Main.getMain_Frame().closeAllInternalFrames();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Un error ha sido encontrado: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Log.write(this.getClass(), ex.getLocalizedMessage(), 3);
        }
    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox addCheckBox;
    private javax.swing.JTextField addressField;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField mailField;
    private javax.swing.JLabel mailLabel;
    private javax.swing.JCheckBox modifyCheckBox;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPasswordField passField;
    private javax.swing.JLabel passLabel;
    private javax.swing.JCheckBox removeCheckBox;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

}
