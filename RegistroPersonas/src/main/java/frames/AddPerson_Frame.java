package frames;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import main.Main;

public class AddPerson_Frame extends javax.swing.JInternalFrame {

    /**
     * Creates new form AddPerson_Frame
     */
    public AddPerson_Frame() {
        initComponents();
    }

    private boolean checkLettersInDateFields() {
        boolean day = false;
        for (int i = 0; i < this.dayField.getText().length(); i++) {
            char c = this.dayField.getText().charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                day = true;
            }
        }
        boolean month = false;
        for (int i = 0; i < this.monthField.getText().length(); i++) {
            char c = this.monthField.getText().charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                month = true;
            }
        }
        boolean year = false;
        for (int i = 0; i < this.yearField.getText().length(); i++) {
            char c = this.yearField.getText().charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                year = true;
            }
        }

        return (!(day && month && year));
    }

    private boolean checkLettersInHeightField() {
        boolean hasLetters = false;
        for (int i = 0; i < this.heightField.getText().length(); i++) {
            char c = this.heightField.getText().charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                hasLetters = true;
            }
        }
        return !hasLetters;
    }

    private boolean containsNumbers(String s) {
        Pattern p = Pattern.compile("([0-9])");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private String buildHeight(String height, int unit) {
        StringBuilder sb = new StringBuilder();

        switch (unit) {
            case 0: {
                int num = Integer.parseInt(height);
                if (num > 0) {
                    sb.append(num).append("-").append("cm");
                }
                break;
            }
            case 1: {
                if (height.length() <= 4) {
                    double num_f = Double.parseDouble(height);
                    if (num_f > 1.00) {
                        sb.append(num_f).append("-").append("ft");
                    }
                }
                break;
            }
        }
        return sb.toString();
    }

    private void registerPerson() throws SQLException {
        if (!(this.nameField.getText().isEmpty() && this.dayField.getText().isEmpty() && this.monthField.getText().isEmpty() && this.yearField.getText().isEmpty())) {
            if (!this.containsNumbers(this.nameField.getText())) {
                if (this.checkLettersInDateFields() && (this.dayField.getText().length() <= 2 && this.monthField.getText().length() <= 2 && this.yearField.getText().length() == 4)) {
                    int day = Integer.parseInt(this.dayField.getText()), month = Integer.parseInt(this.monthField.getText()), year = Integer.parseInt(this.yearField.getText());
                    if ((day > 0 && day <= 31) && (month > 0 && month <= 12) && (year > 1910 && year <= LocalDateTime.now().getYear())) {
                        String name = this.nameField.getText();

                        StringBuilder sb = new StringBuilder();
                        String day_s = "";
                        String month_s = "";
                        if (day < 10) {
                            if (this.dayField.getText().contains("0")) {
                                day_s = this.dayField.getText();
                            } else {
                                day_s = "0" + this.dayField.getText();
                            }
                        }
                        if (month < 10) {
                            if (this.monthField.getText().contains("0")) {
                                month_s = this.monthField.getText();
                            } else {
                                month_s = "0" + this.monthField.getText();
                            }
                        }
                        sb.append(this.yearField.getText()).append("-").append(month_s).append("-").append(day_s);
                        String date = sb.toString();

                        char gender;
                        switch (this.genderComboBox.getSelectedIndex()) {
                            case 0: {
                                gender = 'M';
                                break;
                            }
                            case 1: {
                                gender = 'F';
                                break;
                            }
                            case 2:
                            default: {
                                gender = 'N';
                                break;
                            }
                        }

                        String height = "None";

                        if (!this.heightField.getText().isEmpty()) {
                            if (this.checkLettersInHeightField()) {
                                switch (this.measurementUnitComboBox.getSelectedIndex()) {
                                    case 0: {
                                        height = this.buildHeight(this.heightField.getText(), this.measurementUnitComboBox.getSelectedIndex());
                                        Main.getPersonManager().createPerson(name, date, height, gender);
                                        break;
                                    }
                                    case 1: {
                                        if (this.heightField.getText().contains(".")) {
                                            height = this.buildHeight(this.heightField.getText(), this.measurementUnitComboBox.getSelectedIndex());
                                            Main.getPersonManager().createPerson(name, date, height, gender);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "La altura introducida no es valida.", "Altura", JOptionPane.WARNING_MESSAGE);
                                        }
                                        break;
                                    }
                                }
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pueden introducir letras en el campo de la altura.", "Altura", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            Main.getPersonManager().createPerson(name, date, height, gender);
                        }

                        JOptionPane.showMessageDialog(null, "La persona " + name + " fue correctamente registrada.", "Informacion", JOptionPane.INFORMATION_MESSAGE);

                        Main.getMain_frame().clearRowsInTable();
                        Main.getMain_frame().fillTable_People(Main.getPersonManager().getPerson_list());
                        Main.getAdministratorManager().submitLog(Main.getAdministratorOnline(), "Agrego a " + name + " a la base de datos");
                        
                        this.clear();
                    } else {
                        JOptionPane.showMessageDialog(null, "La fecha introducida no es valida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La fecha introducida no es valida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El campo del nombre no puede contener numeros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Algunos campos requeridos no han sido rellenados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clear() {
        this.nameField.setText("");
        this.dayField.setText("");
        this.monthField.setText("");
        this.yearField.setText("");
        this.heightField.setText("");
        this.genderComboBox.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        birthdateLabel = new javax.swing.JLabel();
        dayField = new javax.swing.JTextField();
        monthField = new javax.swing.JTextField();
        yearField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        heightLabel = new javax.swing.JLabel();
        heightField = new javax.swing.JTextField();
        genderLabel = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox<>();
        measurementUnitComboBox = new javax.swing.JComboBox<>();
        saveButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Agregar persona");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameLabel.setText("Nombre:");

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameField.setToolTipText("No ingrese numeros en este campo");

        birthdateLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        birthdateLabel.setText("Fecha de nacimiento:");

        dayField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        monthField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        yearField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jLabel3.setText("Día");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jLabel4.setText("Mes");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 11)); // NOI18N
        jLabel5.setText("Año");

        heightLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        heightLabel.setText("Altura:");

        heightField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        heightField.setToolTipText("Ejemplo: 160cm o 5.9ft");

        genderLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        genderLabel.setText("Genero:");

        genderComboBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", "No especifico", " " }));

        measurementUnitComboBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        measurementUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cm", "ft" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(birthdateLabel)
                    .addComponent(heightLabel)
                    .addComponent(genderLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dayField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(monthField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(yearField))))
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(measurementUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthdateLabel)
                    .addComponent(dayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heightLabel)
                    .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(measurementUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderLabel)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(saveButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        try {
            if (!this.nameField.getText().isEmpty()) {
                try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM people WHERE name=?")) {
                    stmt.setString(1, this.nameField.getText());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            this.registerPerson();
                        } else {
                            JOptionPane.showMessageDialog(null, "La persona " + this.nameField + " ya existe en la base de datos.", "Persona ya registrada", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Algunos campos requeridos no han sido rellenados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.clear();
    }//GEN-LAST:event_clearButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel birthdateLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField dayField;
    private javax.swing.JComboBox<String> genderComboBox;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JTextField heightField;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> measurementUnitComboBox;
    private javax.swing.JTextField monthField;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField yearField;
    // End of variables declaration//GEN-END:variables
}
