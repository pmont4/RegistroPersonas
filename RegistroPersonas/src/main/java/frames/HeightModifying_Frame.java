package frames;

import entities.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class HeightModifying_Frame extends JInternalFrame {

    /** Creates new form NameModifying_Frame */
    public HeightModifying_Frame() {
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
                if (Main.getMain_frame().isModifying() && Main.getMain_frame().isCanModify()) {
                    Main.getMain_frame().setModifying(false);
                    Main.getMain_frame().setCanModify(false);
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
    
    @Getter
    @Setter
    private int id;
    
    public void modify(int id) throws SQLException {
        if (!this.heightField.getText().isEmpty()) {
            Pattern p = Pattern.compile("([a-z].*[A-Z])");
            Matcher m = p.matcher(this.heightField.getText());
            if (!m.find()) {
                String newHeight = null;
                switch (this.measurementUnitComboBox.getSelectedIndex()) {
                    case 0: {
                        newHeight = this.buildHeight(this.heightField.getText(), this.measurementUnitComboBox.getSelectedIndex());
                        break;
                    }
                    case 1: {
                        if (this.heightField.getText().contains(".")) {
                            newHeight = this.buildHeight(this.heightField.getText(), this.measurementUnitComboBox.getSelectedIndex());
                        }
                        break;
                    }
                }
                try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("UPDATE people SET height = ? WHERE id = ?")) {
                    stmt.setString(1, newHeight);
                    stmt.setInt(2, id);
                    stmt.execute();
                    
                    PreparedStatement stmt2 = Main.getMySQLConnection().prepareStatement("SELECT p.* FROM people p");
                    try (ResultSet rs = stmt2.executeQuery()) {
                        List<Person> list = new ArrayList<>();
                        while (rs.next()) {
                            Person person = new Person(rs.getInt("p.id"), rs.getString("p.name"), rs.getString("p.birth_date"), rs.getString("p.height"), rs.getString("p.gender").charAt(0));
                            list.add(person);
                        }
                        Main.getPersonManager().getPerson_list().clear();
                        Main.getPersonManager().getPerson_list().addAll(list);
                    }
                    
                    JOptionPane.showMessageDialog(null, "La altura del ID " + id + " fue correctamente modificada.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    Main.getAdministratorManager().submitLog(Main.getAdministratorOnline(), "Modifico la altura del ID " + id + " (Nueva altura: " + newHeight + ")");
                    
                    Main.getMain_frame().clearRowsInTable();
                    Main.getMain_frame().fillTable_People(Main.getPersonManager().getPerson_list());
                    Main.getMain_frame().setModifying(false);
                    
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "La altura no puede contener letras.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo de la altura no puede estar vacio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        heightLabel = new javax.swing.JLabel();
        heightField = new javax.swing.JTextField();
        modifyButton = new javax.swing.JButton();
        measurementUnitComboBox = new javax.swing.JComboBox<>();

        setClosable(true);
        setTitle("Modificar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 10))); // NOI18N

        heightLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        heightLabel.setText("Ingrese la altura:");

        heightField.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        modifyButton.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        modifyButton.setText("Modificar");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        measurementUnitComboBox.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        measurementUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cm", "ft" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(modifyButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(measurementUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(heightLabel)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(heightLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(measurementUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(modifyButton)
                .addContainerGap(22, Short.MAX_VALUE))
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
    private javax.swing.JTextField heightField;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> measurementUnitComboBox;
    private javax.swing.JButton modifyButton;
    // End of variables declaration//GEN-END:variables

}
