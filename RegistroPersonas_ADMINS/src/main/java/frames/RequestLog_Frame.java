package frames;

import entities.Administrator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import lombok.Getter;
import main.Main;

public class RequestLog_Frame extends javax.swing.JInternalFrame {

    /**
     * Creates new form RequestLog_Frame
     */
    @Getter
    HashMap<Administrator, HashMap<String, List<String>>> adminLogMap;

    @Getter
    private File logger_directory;

    public RequestLog_Frame() {
        initComponents();

        adminLogMap = new HashMap<>();
        Main.getAdministratorManager().getAdministrator_list().forEach(a -> {
            this.getAdminLogMap().put(a, new HashMap<>());
        });

        logger_directory = new File(Main.getJSON_Configuration().getMain_directory().getAbsolutePath() + "\\logger\\");
        if (!logger_directory.exists()) {
            if (logger_directory.mkdir());
        }

        this.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (!adminLogMap.isEmpty()) {
                    adminLogMap.clear();
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

    public TableModel getTableModel() {
        return this.dataTable.getModel();
    }

    public void updateTableModel(TableModel model) {
        this.dataTable.setModel(model);
    }

    public void fillTable(String admin_name) throws SQLException {
        Optional<Administrator> opt = Main.getAdministratorManager().getAdministrator(admin_name);
        if (opt.isPresent()) {
            DefaultTableModel newModel = (DefaultTableModel) this.getTableModel();
            Object[] data = new Object[this.dataTable.getColumnCount()];
            Administrator admin = opt.get();
            if (Main.tableExists(admin.getName() + "_log")) {
                try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM " + admin.getName() + "_log")) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        HashMap<String, List<String>> mapLogger = new HashMap<>();
                        while (rs.next()) {
                            if (rs.getString("log").contains(",")) {
                                String[] split = rs.getString("log").split("\\,");
                                List<String> arr = Arrays.asList(split);
                                mapLogger.put(rs.getString("date"), arr);
                            } else {
                                mapLogger.put(rs.getString("date"), Arrays.asList(rs.getString("log")));
                            }
                        }

                        if (!mapLogger.isEmpty()) {
                            for (Map.Entry<String, List<String>> map : mapLogger.entrySet()) {
                                for (String s : map.getValue()) {
                                    data[0] = map.getKey();
                                    String log = s.trim();
                                    data[1] = log + ".";
                                    newModel.addRow(data);
                                }
                            }
                            this.getAdminLogMap().put(admin, mapLogger);
                            this.updateTableModel(newModel);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La del administrador no fue encontrada en la base de datos.", "Tabla no encontrada", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El administrador no fue encontrado en la base de datos.", "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateLogFile(String name) throws IOException {
        Optional<Administrator> opt = Main.getAdministratorManager().getAdministrator(name);
        if (opt.isPresent()) {
            Administrator admin = opt.get();
            if (this.getAdminLogMap().containsKey(admin)) {
                File file = new File(this.getLogger_directory().getAbsolutePath() + "\\" + admin.getName() + "_log.txt");
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        HashMap<String, List<String>> loggerMap = this.getAdminLogMap().get(admin);
                        if (loggerMap != null) {
                            if (!loggerMap.isEmpty()) {
                                for (Map.Entry<String, List<String>> map : loggerMap.entrySet()) {
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                        for (String s : map.getValue()) {
                                            String log = s.trim();
                                            String toWrite = "Fecha: " + map.getKey() + "|- \t" + log + "." + "\n";
                                            writer.write(toWrite);
                                        }
                                        writer.flush();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    HashMap<String, List<String>> loggerMap = this.getAdminLogMap().get(admin);
                    BufferedWriter clean = new BufferedWriter(new FileWriter(file));
                    clean.write("");
                    clean.flush();
                    clean.close();
                    if (loggerMap != null) {
                        if (!loggerMap.isEmpty()) {
                            for (Map.Entry<String, List<String>> map : loggerMap.entrySet()) {
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                    for (String s : map.getValue()) {
                                        String log = s.trim();
                                        String toWrite = "Fecha: " + map.getKey() + "|- \t" + log + "." + "\n";
                                        writer.write(toWrite);
                                    }
                                    writer.flush();
                                }
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "El archivo fue generado en la ruta: " + file.getAbsolutePath(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El administrador no fue encontrado en la base de datos.", "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void clearTable() {
        DefaultTableModel newModel = (DefaultTableModel) this.getTableModel();
        newModel.setRowCount(0);
        this.updateTableModel(newModel);
    }

    public void clear() {
        this.clearTable();
        this.nameField.setText("");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        showButton = new javax.swing.JButton();
        fileButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Solicitar log administrador");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameLabel.setText("Nombre del administrador:");

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        dataTable.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Dato"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(dataTable);

        showButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        showButton.setText("Mostrar");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        fileButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        fileButton.setText("Generar archivo");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
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
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton)
                        .addGap(0, 84, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(fileButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showButton)
                    .addComponent(clearButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(fileButton)
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

    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed
        try {
            if (!this.nameField.getText().isEmpty()) {
                if (this.getTableModel().getRowCount() > 0) {
                    this.fillTable(this.nameField.getText());
                } else {
                    this.clearTable();
                    this.fillTable(this.nameField.getText());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Se debe ingresar un nombre para mostrar los datos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_showButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        if (!this.nameField.getText().isEmpty()) {
            if (this.getTableModel().getRowCount() > 0) {
                try {
                    this.generateLogFile(this.nameField.getText());
                    this.clear();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cargue los datos en la tabla para poder generar el archivo.", "Datos", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Se debe ingresar un nombre para generar el archivo del logger.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_fileButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton fileButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton showButton;
    // End of variables declaration//GEN-END:variables

}
