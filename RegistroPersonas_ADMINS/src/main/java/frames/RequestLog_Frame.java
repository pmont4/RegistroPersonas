package frames;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Administrator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import lombok.Getter;
import main.Main;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

        try {
            this.fillNamesComboBox();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

    @Getter
    private String selectedDate;

    public TableModel getTableModel() {
        return this.dataTable.getModel();
    }

    public void updateTableModel(TableModel model) {
        this.dataTable.setModel(model);
    }

    private void fillNamesComboBox() throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT a.name FROM administrators a")) {
            try (ResultSet rs = stmt.executeQuery()) {
                DefaultComboBoxModel model = (DefaultComboBoxModel) this.namesComboBox.getModel();
                while (rs.next()) {
                    model.addElement(rs.getString("a.name"));
                }
                if (model.getSize() > 0) {
                    this.namesComboBox.setModel(model);
                }
            }
        }
    }

    private void fillTable(String admin_name, String date) throws SQLException {
        Optional<Administrator> opt = Main.getAdministratorManager().getAdministrator(admin_name);
        if (opt.isPresent()) {
            DefaultTableModel newModel = (DefaultTableModel) this.getTableModel();
            Object[] data = new Object[this.dataTable.getColumnCount()];
            Administrator admin = opt.get();
            if (Main.tableExists(admin.getName() + "_log")) {
                try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT al.* FROM " + admin.getName() + "_log al WHERE date = ?")) {
                    stmt.setString(1, date);
                    try (ResultSet rs = stmt.executeQuery()) {
                        HashMap<String, List<String>> mapLogger = new HashMap<>();
                        if (rs.next()) {
                            if (rs.getString("al.log").contains(",")) {
                                String[] split = rs.getString("al.log").split("\\,");
                                List<String> arr = Arrays.asList(split);
                                mapLogger.put(rs.getString("al.date"), arr);
                            } else {
                                mapLogger.put(rs.getString("al.date"), Arrays.asList(rs.getString("al.log")));
                            }
                        }

                        if (!mapLogger.isEmpty()) {
                            List<String> logs = mapLogger.get(date);
                            for (String s : logs) {
                                data[0] = date;
                                String log = s.trim();
                                data[1] = log + ".";
                                newModel.addRow(data);
                            }
                        }
                        this.getAdminLogMap().put(admin, mapLogger);
                        this.updateTableModel(newModel);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La del administrador no fue encontrada en la base de datos.", "Tabla no encontrada", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El administrador no fue encontrado en la base de datos.", "No encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private synchronized void generateLogFile(String name, String date, String file_type) throws IOException {
        Optional<Administrator> opt = Main.getAdministratorManager().getAdministrator(name);
        if (opt.isPresent()) {
            Administrator admin = opt.get();
            if (this.getAdminLogMap().containsKey(admin)) {
                HashMap<String, List<String>> loggerMap = this.getAdminLogMap().get(admin);
                if (loggerMap != null) {
                    if (!loggerMap.isEmpty()) {
                        switch (file_type) {
                            case ".txt": {
                                File file = new File(this.getLogger_directory().getAbsolutePath() + "\\" + admin.getName() + "_log-(" + date + ").txt");
                                if (!file.exists()) {
                                    if (file.createNewFile()) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                            if (loggerMap.containsKey(date)) {
                                                for (String s : loggerMap.get(date)) {
                                                    String s_toWrite = s.trim();
                                                    writer.write("Fecha " + date + " |- LOG: \t" + s_toWrite + "." + "\n");
                                                }
                                            }
                                            writer.flush();
                                        }
                                    }
                                } else {
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                        if (loggerMap.containsKey(date)) {
                                            for (String s : loggerMap.get(date)) {
                                                String s_toWrite = s.trim();
                                                writer.write("Fecha " + date + " |- LOG: \t" + s_toWrite + "." + "\n");
                                            }
                                        }
                                        writer.flush();
                                    }
                                }
                                JOptionPane.showMessageDialog(null, "El archivo fue generado en la ruta: " + file.getAbsolutePath(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            case ".json": {
                                JSONObject json_object_arr = new JSONObject();
                                if (loggerMap.containsKey(date)) {
                                    for (String s : loggerMap.get(date)) {
                                        JSONArray arr = new JSONArray();
                                        String text = s.trim();
                                        arr.add(text);
                                        json_object_arr.put(date, arr);
                                    }
                                }
                                JSONObject obj = new JSONObject();
                                obj.put("logger", json_object_arr);
                                File file = new File(this.getLogger_directory().getAbsolutePath() + "\\" + admin.getName() + "_log-(" + date + ").json");
                                if (!file.exists()) {
                                    if (file.createNewFile()) {
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                            String toWrite = gson.toJson(obj);
                                            writer.write(toWrite);
                                        }
                                    }
                                } else {
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                        String toWrite = gson.toJson(obj);
                                        writer.write(toWrite);
                                    }
                                }
                                JOptionPane.showMessageDialog(null, "El archivo fue generado en la ruta: " + file.getAbsolutePath(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            case ".xlsx": {
                                SXSSFWorkbook wb = new SXSSFWorkbook(1);
                                SXSSFSheet sheet = wb.createSheet();
                                Row row = null;
                                Cell cell = null;
                                
                                Object[] header = {"Fecha", "Log"};
                                row = sheet.createRow(0);
                                for (int i = 0; i < header.length; i++) {
                                    cell = row.createCell(i);
                                    cell.setCellValue(header[i].toString());
                                }
                                
                                if (loggerMap.containsKey(date)) {
                                    List<Object> list = new ArrayList<>();
                                    for (int i = 0; i < loggerMap.get(date).size(); i++) {
                                        Object[] newObj = {date, loggerMap.get(date).get(i)};
                                        list.add(newObj);
                                    }
                                    
                                    Iterator<Object> it = list.iterator();
                                    int pageRow = 1;
                                    
                                    while (it.hasNext()) {
                                        Object[] body = (Object[]) it.next();
                                        row = sheet.createRow(pageRow++);
                                        for (int i = 0; i < body.length; i++) {
                                            cell = row.createCell(i);
                                            cell.setCellValue(body[i].toString());
                                        }
                                    }
                                }
                                
                                FileOutputStream file = new FileOutputStream(this.getLogger_directory().getAbsolutePath() + "\\" + admin.getName() + "_log-(" + date + ").xlsx");
                                wb.write(file);
                                file.flush();
                                file.close();
                                wb.dispose();
                                JOptionPane.showMessageDialog(null, "El archivo fue generado en la ruta: " + this.getLogger_directory().getAbsolutePath() + file.getFD(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
                                
                                break;
                            }

                        }
                    }
                }
            }
        }
    }

    public void fillDatesComboBox() throws SQLException {
        String name = this.namesComboBox.getSelectedItem().toString();
        String nameTable = name + "_log";
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT al.date FROM " + nameTable + " al")) {
            DefaultComboBoxModel model = (DefaultComboBoxModel) this.datesComboBox.getModel();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    model.addElement(rs.getString("al.date"));
                }
            }
            if (model.getSize() > 0) {
                this.datesComboBox.setModel(model);
            } else {
                JOptionPane.showMessageDialog(null, "No se detectaron logs en la base de datos del administrador.", "Logger", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void clearTable() {
        DefaultTableModel newModel = (DefaultTableModel) this.getTableModel();
        newModel.setRowCount(0);
        this.updateTableModel(newModel);
    }

    public void clear() {
        this.clearTable();
        DefaultComboBoxModel newModel = (DefaultComboBoxModel) this.datesComboBox.getModel();
        newModel.removeAllElements();
        this.datesComboBox.setModel(newModel);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        getDatesButton = new javax.swing.JButton();
        fileButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        datesLabel = new javax.swing.JLabel();
        datesComboBox = new javax.swing.JComboBox<>();
        showButton = new javax.swing.JButton();
        namesComboBox = new javax.swing.JComboBox<>();
        fileTypeLabel = new javax.swing.JLabel();
        fileTypeComboBox = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setTitle("Solicitar log administrador");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameLabel.setText("Nombre del administrador:");

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
        dataTable.setFocusable(false);
        jScrollPane1.setViewportView(dataTable);

        getDatesButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        getDatesButton.setText("Obtener");
        getDatesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getDatesButtonActionPerformed(evt);
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

        datesLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        datesLabel.setText("Fechas:");

        datesComboBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        showButton.setText("Mostrar");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        namesComboBox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        fileTypeLabel.setText("Tipo de archivo para exportar:");

        fileTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ".txt", ".xlsx", ".json" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(fileButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(nameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(namesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(getDatesButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(datesLabel)
                                .addGap(18, 18, 18)
                                .addComponent(datesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileTypeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 45, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(getDatesButton)
                    .addComponent(clearButton)
                    .addComponent(namesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datesLabel)
                    .addComponent(datesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showButton)
                    .addComponent(fileTypeLabel)
                    .addComponent(fileTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void getDatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getDatesButtonActionPerformed
        try {
            this.fillDatesComboBox();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_getDatesButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        this.clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        if (this.dataTable.getRowCount() > 0) {
            try {
                String name = this.namesComboBox.getSelectedItem().toString();
                String date = this.datesComboBox.getSelectedItem().toString();
                this.generateLogFile(name, date, this.fileTypeComboBox.getSelectedItem().toString());
                this.clear();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha encontrado ningun log en la base de datos del administrador.", "Logger", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_fileButtonActionPerformed

    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed
        if (this.datesComboBox.getItemCount() > 0) {
            try {
                String name = this.namesComboBox.getSelectedItem().toString();
                String date = this.datesComboBox.getSelectedItem().toString();
                this.clearTable();
                this.fillTable(name, date);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha encontrado ningun log en la base de datos del administrador, o aun no se ha generado las fechas en el selector.", "Logger", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_showButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JTable dataTable;
    private javax.swing.JComboBox<String> datesComboBox;
    private javax.swing.JLabel datesLabel;
    private javax.swing.JButton fileButton;
    private javax.swing.JComboBox<String> fileTypeComboBox;
    private javax.swing.JLabel fileTypeLabel;
    private javax.swing.JButton getDatesButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JComboBox<String> namesComboBox;
    private javax.swing.JButton showButton;
    // End of variables declaration//GEN-END:variables

}
