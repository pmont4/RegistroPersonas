package frames;

import entities.Administrator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import lombok.Getter;
import lombok.Setter;
import main.Main;
import org.jetbrains.annotations.Nullable;

public class Main_Frame extends javax.swing.JFrame {

    @Getter
    private AddAdministrator_Frame addInternal;

    @Getter
    private DeleteAdministrator_Frame deleteInternal;

    @Getter
    private RequestLog_Frame requestLogInternal;

    private void initInternalFrames() {
        addInternal = new AddAdministrator_Frame();
        this.mainPane.add(addInternal);
        deleteInternal = new DeleteAdministrator_Frame();
        this.mainPane.add(deleteInternal);
        requestLogInternal = new RequestLog_Frame();
        this.mainPane.add(requestLogInternal);
    }
    
    public void closeAllInternalFrames() {
        JInternalFrame[] internalFrames = {this.getAddInternal(), this.getDeleteInternal(), this.getRequestLogInternal()};
        for (JInternalFrame internal : internalFrames) {
            if (internal.isVisible()) {
                internal.dispose();
            }
        }
    }

    @Getter
    @Setter
    private boolean modifying;

    @Getter
    @Setter
    private boolean canModify;

    @Getter
    @Setter
    @Nullable
    private String modify;

    public Main_Frame() {
        initComponents();

        this.setTitle("Registro administradores");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Main.getMySQLConnection().close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

        });

        this.fillTable(Main.getAdministratorManager().getAdministrator_list());
        this.initInternalFrames();
    }

    public void updateAdministratorTableModel(TableModel model) {
        this.administratorTable.setModel(model);
    }

    public TableModel getAdministratorTableModel() {
        return this.administratorTable.getModel();
    }

    public void fillTable(List<Administrator> list) {
        Object[] data = new Object[this.administratorTable.getColumnCount()];

        DefaultTableModel newModel = (DefaultTableModel) this.getAdministratorTableModel();
        list.forEach(a -> {
            data[0] = a.getId();
            data[1] = a.getName();
            data[2] = a.getMail();
            if (a.getAddress().equalsIgnoreCase("None")) {
                data[3] = "No registrada";
            } else {
                data[3] = a.getAddress();
            }
            String perms = a.getPerms().toString().replace("[", "").replace("]", "").replace("add", "Agregar").replace("remove", "Remover").replace("modify", "Modificar") + ".";
            data[4] = perms;
            data[5] = a.getLast_session();

            newModel.addRow(data);
        });
        this.updateAdministratorTableModel(newModel);
    }

    public void clearTable() {
        DefaultTableModel newModel = (DefaultTableModel) this.getAdministratorTableModel();
        newModel.setRowCount(0);
        this.updateAdministratorTableModel(newModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        administratorTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        optionsMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        adminsMenu = new javax.swing.JMenu();
        addAdminMenuItem = new javax.swing.JMenuItem();
        deleteAdminMenuItem = new javax.swing.JMenuItem();
        modifyAdminMenuItem = new javax.swing.JMenuItem();
        requestLogAdminMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPane.setBackground(new java.awt.Color(153, 153, 153));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setText("TABLA DE DATOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setText("(Administradores)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(477, 477, 477)
                        .addComponent(jLabel2)))
                .addContainerGap(356, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        administratorTable.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        administratorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Correo", "Direccion", "Permisos", "Ultima sesion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        administratorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                administratorTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(administratorTable);

        mainPane.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mainPane.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        optionsMenu.setText("Opciones");

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(exitMenuItem);

        jMenuBar1.add(optionsMenu);

        adminsMenu.setText("Administradores");

        addAdminMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        addAdminMenuItem.setText("Agregar administrador");
        addAdminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAdminMenuItemActionPerformed(evt);
            }
        });
        adminsMenu.add(addAdminMenuItem);

        deleteAdminMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        deleteAdminMenuItem.setText("Borrar administrador");
        deleteAdminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAdminMenuItemActionPerformed(evt);
            }
        });
        adminsMenu.add(deleteAdminMenuItem);

        modifyAdminMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        modifyAdminMenuItem.setText("Modificar administrador");
        modifyAdminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyAdminMenuItemActionPerformed(evt);
            }
        });
        adminsMenu.add(modifyAdminMenuItem);

        requestLogAdminMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        requestLogAdminMenuItem.setText("Solicitar log administrador");
        requestLogAdminMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLogAdminMenuItemActionPerformed(evt);
            }
        });
        adminsMenu.add(requestLogAdminMenuItem);

        jMenuBar1.add(adminsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void addAdminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAdminMenuItemActionPerformed
        this.getAddInternal().show();
    }//GEN-LAST:event_addAdminMenuItemActionPerformed

    private void deleteAdminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAdminMenuItemActionPerformed
        this.getDeleteInternal().show();
    }//GEN-LAST:event_deleteAdminMenuItemActionPerformed

    private void requestLogAdminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestLogAdminMenuItemActionPerformed
        this.getRequestLogInternal().show();
    }//GEN-LAST:event_requestLogAdminMenuItemActionPerformed

    private void administratorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_administratorTableMouseClicked
        if (this.isCanModify()) {
            int row = this.administratorTable.getSelectedRow();
            int colum = this.administratorTable.getSelectedColumn();

            if (row != -1) {
                if (colum != 0) {
                    if (colum != 5) {
                        if (!this.isModifying()) {
                            int id = Integer.parseInt(this.administratorTable.getValueAt(row, 0).toString());

                            switch (colum) {
                                case 1: {
                                    this.setModify("name");
                                    break;
                                }
                                case 2: {
                                    this.setModify("mail");
                                    break;
                                }
                                case 3: {
                                    this.setModify("address");
                                    break;
                                }
                                case 4: {
                                    this.setModify("perms");
                                    break;
                                }
                            }

                            switch (this.getModify()) {
                                case "name":
                                case "mail":
                                case "address": {
                                    StringModifying_Frame s_modify = new StringModifying_Frame();
                                    this.mainPane.add(s_modify);
                                    s_modify.setId(id);
                                    s_modify.setTitle("Modificar (ID: " + id + ")");
                                    s_modify.show();
                                    this.setModifying(true);
                                    break;
                                }
                                case "perms": {
                                    PermissionsModify_Frame perm_modify = new PermissionsModify_Frame();
                                    this.mainPane.add(perm_modify);
                                    perm_modify.setId(id);
                                    perm_modify.setTitle("Modificar (ID: " + id + ")");
                                    perm_modify.show();
                                    this.setModifying(true);
                                    break;
                                }

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pueden modificar dos atributos a la vez.", "Modificar", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Este atributo no puede ser modificado.", "Modificar", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Este atributo no puede ser modificado.", "Modificar", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_administratorTableMouseClicked

    private void modifyAdminMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyAdminMenuItemActionPerformed
        if (!this.isCanModify()) {
            this.setCanModify(true);
            JOptionPane.showMessageDialog(null, "Para modificar a un administrador, seleccione en la tabla el atributo que desea modificar, seguido de eso, ingrese el nuevo dato.", "Modificar", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Esta opcion ya esta activa.", "Modificar", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_modifyAdminMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addAdminMenuItem;
    private javax.swing.JTable administratorTable;
    private javax.swing.JMenu adminsMenu;
    private javax.swing.JMenuItem deleteAdminMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JDesktopPane mainPane;
    private javax.swing.JMenuItem modifyAdminMenuItem;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem requestLogAdminMenuItem;
    // End of variables declaration//GEN-END:variables

}
