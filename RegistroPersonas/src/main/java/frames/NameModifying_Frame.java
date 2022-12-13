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
import utils.Log;

public class NameModifying_Frame extends JInternalFrame {

    /** Creates new form NameModifying_Frame */
    public NameModifying_Frame() {
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
    
    @Getter
    @Setter
    private int id;
    
    public void modify(int id) throws SQLException {
        if (!this.nameField.getText().isEmpty()) {
            Pattern p = Pattern.compile("([0-9])");
            Matcher m = p.matcher(this.nameField.getText());
            if (!m.find()) {
                String newName = this.nameField.getText();
                try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("UPDATE people SET name = ? WHERE id = ?")) {
                    stmt.setString(1, newName);
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
                    
                    JOptionPane.showMessageDialog(null, "El nombre del ID " + id + " fue correctamente modificado.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    Main.getAdministratorManager().submitLog(Main.getAdministratorOnline(), "Modifico el nombre del ID " + id + " (Nuevo nombre: " + newName + ")");
                    
                    Main.getMain_frame().clearRowsInTable();
                    Main.getMain_frame().fillTable_People(Main.getPersonManager().getPerson_list());
                    Main.getMain_frame().setModifying(false);
                    
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "El nombre no puede contener numeros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo del nombre no puede estar vacio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        modifyButton = new javax.swing.JButton();

        setClosable(true);
        setTitle("Modificar");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 10))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        nameLabel.setText("Ingrese el nombre:");

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        modifyButton.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        modifyButton.setText("Modificar");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(nameLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(modifyButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addGap(18, 18, 18)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modifyButton)
                .addContainerGap(18, Short.MAX_VALUE))
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
            Log.write(this.getClass(), ex.getLocalizedMessage(), 3);
        }
    }//GEN-LAST:event_modifyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton modifyButton;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables

}
