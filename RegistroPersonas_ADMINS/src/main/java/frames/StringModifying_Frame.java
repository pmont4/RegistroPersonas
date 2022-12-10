package frames;

import entities.Administrator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import lombok.Getter;
import lombok.Setter;
import main.Main;
import utils.NoSpecifiedPermsException;

public class StringModifying_Frame extends javax.swing.JInternalFrame {

    /**
     * Creates new form StringModifying_Frame
     */
    public StringModifying_Frame() {
        initComponents();

        switch (Main.getMain_Frame().getModify()) {
            case "name": {
                this.informationLabel.setText("Ingrese el nombre:");
                break;
            }
            case "mail": {
                this.informationLabel.setText("Ingrese el correo:");
                break;
            }
            case "address": {
                this.informationLabel.setText("Ingrese la direccion:");
                break;
            }
        }

        this.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (Main.getMain_Frame().isModifying() && Main.getMain_Frame().isCanModify()) {
                    Main.getMain_Frame().setModifying(false);
                    Main.getMain_Frame().setCanModify(false);
                }
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {

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

    public void modify(int id) throws SQLException, NoSpecifiedPermsException {
        if (!this.informationLabel.getText().isEmpty()) {
            boolean correctlyUpdated = false;
            PreparedStatement stmt = null;

            switch (Main.getMain_Frame().getModify()) {
                case "name": {
                    String newName = this.informationField.getText();
                    String oldName = null;

                    stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators WHERE id = ?");
                    stmt.setInt(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            oldName = rs.getString("name");
                            stmt.execute();
                            stmt = Main.getMySQLConnection().prepareStatement("ALTER TABLE " + oldName.toLowerCase() + "_log RENAME TO " + newName.toLowerCase() + "_log");
                            stmt.execute();
                            stmt = Main.getMySQLConnection().prepareStatement("UPDATE administrators SET name = ? WHERE id = ?");
                            stmt.setString(1, newName);
                            stmt.setInt(2, id);
                            stmt.executeUpdate();
                            stmt.close();

                            JOptionPane.showMessageDialog(null, "El nombre del administrador fue correctamente modificado", "Modificar", JOptionPane.INFORMATION_MESSAGE);
                            correctlyUpdated = true;
                        }
                    }
                    break;
                }
                case "mail": {
                    if (this.informationField.getText().contains("@")) {
                        stmt = Main.getMySQLConnection().prepareStatement("UPDATE administrators SET mail = ? WHERE id = ?");
                        stmt.setString(1, this.informationField.getText());
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                        stmt.close();

                        JOptionPane.showMessageDialog(null, "La direccion de correo del administrador fue correctamente modificada.", "Modificar", JOptionPane.INFORMATION_MESSAGE);
                        correctlyUpdated = true;
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "La direccion de correo electronico ingresada no es valida.", "Modificar", JOptionPane.WARNING_MESSAGE);
                    }
                }
                case "address": {
                    stmt = Main.getMySQLConnection().prepareStatement("UPDATE administrators SET address = ? WHERE id = ?");
                    stmt.setString(1, this.informationField.getText());
                    stmt.setInt(2, id);
                    stmt.executeUpdate();
                    stmt.close();

                    JOptionPane.showMessageDialog(null, "La direccion del administrador fue correctamente modificada.", "Modificar", JOptionPane.INFORMATION_MESSAGE);
                    correctlyUpdated = true;
                    break;
                }
            }

            if (correctlyUpdated) {
                Main.getMain_Frame().clearTable();
                PreparedStatement stmt2 = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators");
                try (ResultSet rs = stmt2.executeQuery()) {
                    List<Administrator> list = new ArrayList<>();
                    while (rs.next()) {
                        List<String> perms = new ArrayList<>();
                        if (rs.getString("perms").contains(",")) {
                            String[] split = rs.getString("perms").split("\\,");
                            perms.addAll(Arrays.asList(split));
                        } else {
                            if (rs.getString("perms").equals("add") || rs.getString("perms").equals("modify") || rs.getString("perms").equals("remove")) {
                                perms.add(rs.getString("perms"));
                            } else {
                                throw new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again.");
                            }
                        }
                        Administrator admin = new Administrator(rs.getInt("id"), rs.getString("name"), rs.getString("mail"), rs.getString("password"), rs.getString("address"), perms, "");
                        stmt = Main.getMySQLConnection().prepareStatement("SELECT date FROM " + rs.getString("name") + "_log" + " ORDER BY date DESC LIMIT 1");
                        try (ResultSet rs2 = stmt.executeQuery()) {
                            String lastSession = "None";
                            if (rs2.next()) {
                                lastSession = rs2.getString("date");
                            }
                            admin.setLast_session(lastSession);
                        }
                        list.add(admin);
                    }

                    Main.getMain_Frame().fillTable(list);
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo de la informacion no puede estar vacio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        informationLabel = new javax.swing.JLabel();
        informationField = new javax.swing.JTextField();
        modifyButton = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 10))); // NOI18N

        informationLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        informationLabel.setText("Ingrese aqui");

        informationField.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(informationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(informationField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(modifyButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(informationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modifyButton))
                .addContainerGap(24, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        try {
            this.modify(this.getId());
        } catch (SQLException | NoSpecifiedPermsException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_modifyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField informationField;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton modifyButton;
    // End of variables declaration//GEN-END:variables

}
