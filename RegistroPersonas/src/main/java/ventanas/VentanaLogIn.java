package ventanas;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.App;
import objetos.Administrador;

public class VentanaLogIn extends javax.swing.JFrame {

    public VentanaLogIn() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Inicio de sesión");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fieldNombre = new javax.swing.JTextField();
        fieldContrasena = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Inicie sesión");

        fieldNombre.setToolTipText("Nombre");

        fieldContrasena.setToolTipText("Contraseña");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Contraseña");

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(153, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fieldNombre)
                                .addComponent(fieldContrasena)))
                        .addGap(143, 143, 143))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnIngresar)
                        .addGap(157, 157, 157))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnIngresar)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        if (!(fieldNombre.getText().isEmpty() && fieldContrasena.getPassword().length < 0)) {
            try {
                String nombre = fieldNombre.getText();
                String contrasena = new String(fieldContrasena.getPassword());
                if (this.comprobar_contrasena(nombre, contrasena)) {
                    try (PreparedStatement stmt = App.getMySQL().getConnection().prepareStatement("SELECT * FROM admins WHERE nombre=?")) {
                        stmt.setString(1, nombre);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                Administrador admin = new Administrador();
                                admin.setNombre(rs.getString("nombre"));
                                admin.setContrasena(rs.getString("contrasena"));
                                admin.setCorreo(rs.getString("correo"));

                                if (rs.getString("permisos").contains(",")) {
                                    String[] permisos = rs.getString("permisos").split("\\,");
                                    admin.setPermisos(Arrays.asList(permisos));
                                } else {
                                    if (rs.getString("permisos").equals("agregar") || rs.getString("permisos").equals("modificar") || rs.getString("permisos").equals("borrar")) {
                                        admin.setPermisos(Arrays.asList(rs.getString("permisos")));
                                    } else {
                                        throw new NullPointerException();
                                    }
                                }

                                App.setAdminOnline(admin);
                                App.getJSON_Util().escribir_archivo(admin);
                                
                                if (App.getJSON_Util().getJSON_Admin() != null) {
                                    String a = "JSON: " + App.getJSON_Util().getJSON_Admin().toString();
                                    System.out.println(a);
                                }
                                
                                try (PreparedStatement stmt2 = App.getMySQL().getConnection().prepareStatement("SELECT * FROM " + admin.getNombre() + "_log WHERE fecha=?")) {
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    LocalDateTime now = LocalDateTime.now();
                                    stmt2.setString(1, format.format(now));
                                    try (ResultSet rs2 = stmt2.executeQuery()) {
                                        if (!rs2.next()) {
                                            admin.setUltima_sesion(now);
                                            PreparedStatement stmt3 = App.getMySQL().getConnection().prepareStatement("INSERT INTO " + admin.getNombre() + "_log (fecha, registro, cantidad_registros) VALUES (?,?,?)");
                                            stmt3.setString(1, new String(format.format(now)));
                                            stmt3.setString(2, "");
                                            stmt3.setInt(3, 0);
                                            stmt3.executeUpdate();
                                            stmt3.close();
                                        } else {
                                            admin.setUltima_sesion(now);
                                            admin.setNumero_registros(rs2.getInt("cantidad_registros"));
                                            
                                            String registro = rs2.getString("registro");
                                            if (registro.contains(",")) {
                                                String[] split = registro.split("\\,");
                                                admin.setLista_registro(Arrays.asList(split));                                
                                            } else {
                                                admin.setLista_registro(Arrays.asList(registro));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    App.getVentanaPrincipal().actualizar_texto_admin();
                    App.getVentanaPrincipal().setVisible(true);

                    System.out.println(App.getAdminOnline().toString());
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Nombre o contraseña incorrectos! intente de nuevo", "Usuario no encontrado", JOptionPane.WARNING_MESSAGE);
                    fieldNombre.setText("");
                    fieldContrasena.setText("");
                }
            } catch (Exception ex) {
                System.out.println("Un error ha ocurrido >> " + ex.getClass().getName() + " ERROR: " + ex.getMessage());
                try {
                    App.getMySQL().getConnection().rollback();
                } catch (Exception ex1) {
                    Logger.getLogger(VentanaLogIn.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    public boolean comprobar_contrasena(String nombre, String contrasena) throws Exception {
        try {
            try (PreparedStatement pstmt = App.getMySQL().getConnection().prepareStatement("SELECT * FROM admins WHERE nombre=? AND contrasena=?")) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, contrasena);
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getClass().getName() + " ERROR: " + ex.getMessage());
        }
        return false;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JPasswordField fieldContrasena;
    private javax.swing.JTextField fieldNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
