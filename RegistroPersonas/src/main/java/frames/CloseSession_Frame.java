/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import java.io.IOException;
import javax.swing.JOptionPane;
import main.Main;

/**
 *
 * @author paulo
 */
public class CloseSession_Frame extends javax.swing.JInternalFrame {

    /**
     * Creates new form CloseSession_Frame
     */
    public CloseSession_Frame() {
        initComponents();
        
        this.AdminSessionLabel.setText("Administrador en la sesion: " + Main.getAdministratorOnline().getName());
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
        label_one = new javax.swing.JLabel();
        AdminSessionLabel = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cerrar sesion");
        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sesion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        label_one.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        label_one.setText("¿Desea cerrar la sesion actual?");

        AdminSessionLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        AdminSessionLabel.setText("Administrador en la sesion:");

        logOutButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        logOutButton.setText("Cerrar sesion");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logOutButton)
                .addGap(57, 57, 57))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AdminSessionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_one))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_one)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(AdminSessionLabel)
                .addGap(30, 30, 30)
                .addComponent(logOutButton)
                .addGap(25, 25, 25))
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

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        try {
            Main.getJSON_Configuration().removeExistingSessionFile();
            JOptionPane.showMessageDialog(null, "La sesion fue correctamente cerrada, se reiniciara el programa.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_logOutButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdminSessionLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_one;
    private javax.swing.JButton logOutButton;
    // End of variables declaration//GEN-END:variables
}
