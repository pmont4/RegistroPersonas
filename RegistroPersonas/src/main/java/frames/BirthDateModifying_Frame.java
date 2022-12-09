package frames;

import entities.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import lombok.Getter;
import lombok.Setter;
import main.Main;

public class BirthDateModifying_Frame extends JInternalFrame {

    /**
     * Creates new form NameModifying_Frame
     */
    public BirthDateModifying_Frame() {
        initComponents();

        this.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (Main.getMain_frame().isModifying()) {
                    Main.getMain_frame().setModifying(false);
                }
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {

            }
        });
    }

    @Getter
    @Setter
    private int id;

    public void modify(int id) throws SQLException {
        if (!(this.dayField.getText().isEmpty() && this.monthField.getText().isEmpty() && this.yearField.getText().isEmpty())) {
            Pattern p = Pattern.compile("([A-Z].*[a-z])");
            Matcher day_m = p.matcher(this.dayField.getText());
            Matcher month_m = p.matcher(this.monthField.getText());
            Matcher year_m = p.matcher(this.yearField.getText());

            if (!(day_m.find() && month_m.find() && year_m.find())) {
                if (this.dayField.getText().length() <= 2 && this.monthField.getText().length() <= 2 && this.yearField.getText().length() == 4) {
                    int day = Integer.parseInt(this.dayField.getText());
                    int month = Integer.parseInt(this.monthField.getText());
                    int year = Integer.parseInt(this.yearField.getText());
                    if ((day > 0 && day <= 31) && (month > 0 && month <= 12) && (year > 1910 && year <= LocalDateTime.now().getYear())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(year).append("-");
                        if (month < 10) {
                            if (!this.monthField.getText().contains("0")) {
                                sb.append("0").append(month).append("-");
                            } else {
                                sb.append(month).append("-");
                            }
                        } else {
                            sb.append(month).append("-");
                        }
                        if (day < 10) {
                            if (!this.dayField.getText().contains("0")) {
                                sb.append("0").append(day);
                            } else {
                                sb.append(day);
                            }
                        } else {
                            sb.append(day);
                        }
                        
                        
                        String newDate = sb.toString();
                        
                        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("UPDATE people SET birth_date = ? WHERE id = ?")) {
                            stmt.setString(1, newDate);
                            stmt.setInt(2, id);
                            stmt.execute();

                            PreparedStatement stmt2 = Main.getMySQLConnection().prepareStatement("SELECT * FROM people");
                            try (ResultSet rs = stmt2.executeQuery()) {
                                List<Person> list = new ArrayList<>();
                                while (rs.next()) {
                                    Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("birth_date"), rs.getString("height"), rs.getString("gender").charAt(0));
                                    list.add(person);
                                }
                                Main.getPersonManager().getPerson_list().clear();
                                Main.getPersonManager().getPerson_list().addAll(list);
                            }

                            JOptionPane.showMessageDialog(null, "La fecha de nacimiento del ID " + id + " fue correctamente modificada.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                            Main.getAdministratorManager().submitLog(Main.getAdministratorOnline(), "Modifico la fecha de nacimiento del ID " + id + " (Nueva fecha: " + newDate + ")");

                            Main.getMain_frame().clearRowsInTable();
                            Main.getMain_frame().fillTable_People(Main.getPersonManager().getPerson_list());
                            Main.getMain_frame().setModifying(false);

                            this.dispose();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "La fecha ingresada es invalida.", "Fecha", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La fecha no puede contener letras.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor proporcione todos los datos solicitados..", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        birtDateLabel = new javax.swing.JLabel();
        modifyButton = new javax.swing.JButton();
        dayField = new javax.swing.JTextField();
        dayLabel = new javax.swing.JLabel();
        monthField = new javax.swing.JTextField();
        monthLabel = new javax.swing.JLabel();
        yearField = new javax.swing.JTextField();
        yearLabel = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Modificar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 10))); // NOI18N

        birtDateLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        birtDateLabel.setText("Ingrese la fecha:");

        modifyButton.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        modifyButton.setText("Modificar");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        dayLabel.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        dayLabel.setText("Día");

        monthLabel.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        monthLabel.setText("Mes");

        yearLabel.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        yearLabel.setText("Año");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dayField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(monthField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(modifyButton)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(monthLabel)
                            .addGap(26, 26, 26)
                            .addComponent(yearLabel))
                        .addComponent(birtDateLabel)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(birtDateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dayLabel)
                    .addComponent(monthLabel)
                    .addComponent(yearLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modifyButton)
                .addContainerGap(21, Short.MAX_VALUE))
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

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        try {
            this.modify(this.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_modifyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel birtDateLabel;
    private javax.swing.JTextField dayField;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton modifyButton;
    private javax.swing.JTextField monthField;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JTextField yearField;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables

}
