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
import utils.Log;
import utils.NoSpecifiedPermsException;

public class PermissionsModify_Frame extends javax.swing.JInternalFrame {

    /**
     * Creates new form PermissionsModify_Frame
     */
    public PermissionsModify_Frame() {
        initComponents();

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
        if (this.addCheckBox.isSelected() || this.modifyCheckBox.isSelected() || this.removeCheckBox.isSelected()) {
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
            PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("UPDATE administrators SET perms = ? WHERE id = ?");
            stmt.setString(1, perms);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt = Main.getMySQLConnection().prepareStatement("SELECT a.* FROM administrators a");
            Main.getMain_Frame().clearTable();
            
            try (ResultSet rs = stmt.executeQuery()) {
                List<Administrator> list = new ArrayList<>();
                while (rs.next()) {
                    List<String> permssions = new ArrayList<>();
                    if (rs.getString("a.perms").contains(",")) {
                        String[] split = rs.getString("a.perms").split("\\,");
                        permssions.addAll(Arrays.asList(split));
                    } else {
                        if (rs.getString("a.perms").equals("add") || rs.getString("a.perms").equals("modify") || rs.getString("a.perms").equals("remove")) {
                            permssions.add(rs.getString("a.perms"));
                        } else {
                            throw new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again.");
                        }
                    }
                    Administrator admin = new Administrator(rs.getInt("a.id"), rs.getString("a.name"), rs.getString("a.mail"), rs.getString("a.password"), rs.getString("a.address"), permssions, "");
                    stmt = Main.getMySQLConnection().prepareStatement("SELECT al.date FROM " + rs.getString("a.name") + "_log al ORDER BY date DESC LIMIT 1");
                    try (ResultSet rs2 = stmt.executeQuery()) {
                        String lastSession = "None";
                        if (rs2.next()) {
                            lastSession = rs2.getString("al.date");
                        }
                        admin.setLast_session(lastSession);
                    }
                    list.add(admin);
                }

                Main.getMain_Frame().fillTable(list);
                
                JOptionPane.showMessageDialog(null, "Los permisos del administrador fueron correctamente modificados.", "Modificar", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
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
        jLabel1 = new javax.swing.JLabel();
        addCheckBox = new javax.swing.JCheckBox();
        modifyCheckBox = new javax.swing.JCheckBox();
        removeCheckBox = new javax.swing.JCheckBox();
        modifyButton = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 10))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setText("Seleccione los nuevos permisos:");

        addCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        addCheckBox.setText("Agregar");

        modifyCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        modifyCheckBox.setText("Modificar");

        removeCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        removeCheckBox.setText("Borrar");

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(addCheckBox)
                .addGap(48, 48, 48)
                .addComponent(modifyCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(removeCheckBox)
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(modifyButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCheckBox)
                    .addComponent(modifyCheckBox)
                    .addComponent(removeCheckBox))
                .addGap(30, 30, 30)
                .addComponent(modifyButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        } catch (SQLException | NoSpecifiedPermsException  ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Log.write(this.getClass(), ex.getLocalizedMessage(), 3);
        }
    }//GEN-LAST:event_modifyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox addCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton modifyButton;
    private javax.swing.JCheckBox modifyCheckBox;
    private javax.swing.JCheckBox removeCheckBox;
    // End of variables declaration//GEN-END:variables

}
