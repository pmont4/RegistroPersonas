package ventanas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.App;
import objetos.Persona;

public class VentanaBorrar extends javax.swing.JFrame {

    public VentanaBorrar() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Eliminar");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelIngresar = new javax.swing.JLabel();
        fieldIngresar = new javax.swing.JTextField();
        btnBorrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        labelIngresar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        labelIngresar.setText("Ingrese el ID de la persona:");

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(labelIngresar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(fieldIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(labelIngresar)
                .addGap(18, 18, 18)
                .addComponent(fieldIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        try {
            if (!fieldIngresar.getText().isEmpty()) {
                Persona persona = (Persona) App.getPersonaControlador().getEntidad(fieldIngresar.getText());
                if (App.getPersonaControlador().existsEntidad(persona.getId().toString())) {
                    App.getPersonaControlador().deleteEntidad(persona.getId().toString());

                    App.getVentanaPrincipal().limpiar_tabla();
                    App.getVentanaPrincipal().llenar_tabla();

                    JOptionPane.showMessageDialog(null, persona.getNombre() + " fue correctamente eliminado/a de la base de datos", "Eliminado", JOptionPane.INFORMATION_MESSAGE);

                    if (App.getAdminOnline() != null) {
                        String last_registro = "";
                        Integer numero_registros = 0;
                        StringBuilder nuevo_registro = new StringBuilder();
                        try (PreparedStatement stmt = App.getMySQL().getConnection().prepareStatement("SELECT * FROM " + App.getAdminOnline().getNombre() + "_log WHERE fecha=?")) {
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            LocalDateTime now = LocalDateTime.now();
                            stmt.setString(1, format.format(now));
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    if (!rs.getString("registro").equals("")) {
                                        last_registro = rs.getString("registro") + ", ";
                                    }
                                    numero_registros = rs.getInt("cantidad_registros");
                                    try (PreparedStatement stmt2 = App.getMySQL().getConnection().prepareStatement("UPDATE " + App.getAdminOnline().getNombre() + "_log SET registro=?, cantidad_registros=? WHERE fecha=?")) {
                                        nuevo_registro.append(last_registro).append("Se elimino a ").append(persona.getNombre()).append(" de la base de datos");
                                        stmt2.setString(1, nuevo_registro.toString());
                                        stmt2.setInt(2, numero_registros + 1);
                                        stmt2.setString(3, format.format(now));
                                        stmt2.executeUpdate();
                                    }

                                    App.getAdminOnline().setNumero_registros(numero_registros);
                                    String texto = new String(nuevo_registro);
                                    if (texto.contains(",")) {
                                        String[] split = texto.split("\\,");
                                        App.getAdminOnline().setLista_registro(Arrays.asList(split));
                                    } else {
                                        App.getAdminOnline().setLista_registro(Arrays.asList(texto));
                                    }

                                    System.out.println("Registros de " + App.getAdminOnline().getNombre() + ": ");
                                    App.getAdminOnline().getLista_registro().forEach(t -> {
                                        System.out.println(t.trim());
                                    });
                                }
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El ID '" + persona.getId().toString() + "' no fue encontrado", "No encontrado", JOptionPane.WARNING_MESSAGE);
                }
                fieldIngresar.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Un error ha ocurrido >> " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JTextField fieldIngresar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelIngresar;
    // End of variables declaration//GEN-END:variables

}
