package ventanas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.App;
import objetos.Persona;

public class VentanaRegistro extends javax.swing.JFrame {

    public VentanaRegistro() {
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Registro de personas");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelNombre = new javax.swing.JLabel();
        txtfieldNombre = new javax.swing.JTextField();
        labelEdad = new javax.swing.JLabel();
        txtfieldEdad = new javax.swing.JTextField();
        labelAltura = new javax.swing.JLabel();
        txtfieldAltura = new javax.swing.JTextField();
        comboGenero = new javax.swing.JComboBox<>();
        labelGenero = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        labelEjemploAltura = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("REGISTRO DE PERSONAS");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        labelNombre.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        labelNombre.setText("Ingresar el nombre:");

        labelEdad.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        labelEdad.setText("Ingresar la edad:");

        labelAltura.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        labelAltura.setText("Ingresar la altura (Opcional):");

        comboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", "Sin especificar", " " }));

        labelGenero.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        labelGenero.setText("Seleccionar el genero:");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        labelEjemploAltura.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        labelEjemploAltura.setForeground(new java.awt.Color(102, 102, 102));
        labelEjemploAltura.setText("Ejemplo: (180cm o 511ft)");

        jButton1.setText("Limpiar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelEdad)
                                    .addComponent(labelAltura))
                                .addGap(169, 169, 169)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboGenero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtfieldNombre)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtfieldAltura, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelEjemploAltura))
                                            .addComponent(txtfieldEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelGenero, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNombre, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(111, 111, 111))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRegistrar)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(txtfieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEdad)
                    .addComponent(txtfieldEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAltura)
                    .addComponent(txtfieldAltura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEjemploAltura))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGenero))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Persona persona = new Persona();

        boolean borrarDatosIngresados = true;
        if (!(txtfieldNombre.getText().isEmpty() && txtfieldEdad.getText().isEmpty())) {
            try {
                persona.setNombre(txtfieldNombre.getText());
                persona.setEdad(Integer.parseInt(txtfieldEdad.getText()));
                persona.setId(0);

                boolean alturaAceptada = false;
                if (!txtfieldAltura.getText().isEmpty()) {
                    if (txtfieldAltura.getText().contains("0") || txtfieldAltura.getText().contains("1") || txtfieldAltura.getText().contains("2")
                            || txtfieldAltura.getText().contains("3") || txtfieldAltura.getText().contains("4") || txtfieldAltura.getText().contains("5")
                            || txtfieldAltura.getText().contains("6") || txtfieldAltura.getText().contains("7") || txtfieldAltura.getText().contains("8")
                            || txtfieldAltura.getText().contains("9")) {
                        if (txtfieldAltura.getText().contains("cm") || txtfieldAltura.getText().contains("ft")) {
                            alturaAceptada = true;
                            borrarDatosIngresados = true;
                            if (txtfieldAltura.getText().contains(" ")) {
                                String nuevaAltura = txtfieldAltura.getText().replaceAll("\\s", "");
                                persona.setAltura(nuevaAltura.trim());
                            } else {
                                persona.setAltura(txtfieldAltura.getText().trim());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Por favor ingresar una altura valida, Ejemplo: (180cm o 511ft)", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            borrarDatosIngresados = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor ingresar una altura valida, Ejemplo: (180cm o 511ft)", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        borrarDatosIngresados = false;
                    }
                } else {
                    persona.setAltura("0");
                }
                switch (comboGenero.getSelectedIndex()) {
                    case 0: {
                        persona.setGenero('M');
                        break;
                    }
                    case 1: {
                        persona.setGenero('F');
                        break;
                    }
                    case 2:
                    default: {
                        persona.setGenero('I');
                        break;
                    }

                }
                
                String[] propiedades = {String.valueOf(persona.getId()), persona.getNombre(), String.valueOf(persona.getEdad()), persona.getAltura(), persona.getGenero().toString()};
                
                if (!txtfieldAltura.getText().isEmpty()) {
                    if (alturaAceptada) {
                        App.getPersonaControlador().addEntidad(propiedades);

                        JOptionPane.showMessageDialog(null, persona.getNombre() + " fue correctamente registrado/a", "Registro", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    App.getPersonaControlador().addEntidad(propiedades);

                    JOptionPane.showMessageDialog(null, persona.getNombre() + " fue correctamente registrado/a", "Registro", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Un error ha ocurrido >> " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (borrarDatosIngresados) {
                    txtfieldNombre.setText("");
                    txtfieldEdad.setText("");
                    txtfieldAltura.setText("");
                    comboGenero.setSelectedIndex(0);
                } else {
                    txtfieldAltura.setText("");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, no dejar campos necesarios vacios", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtfieldNombre.setText("");
        txtfieldEdad.setText("");
        txtfieldAltura.setText("");
        comboGenero.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
        App.getVentanaPrincipal().setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> comboGenero;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelAltura;
    private javax.swing.JLabel labelEdad;
    private javax.swing.JLabel labelEjemploAltura;
    private javax.swing.JLabel labelGenero;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JTextField txtfieldAltura;
    private javax.swing.JTextField txtfieldEdad;
    private javax.swing.JTextField txtfieldNombre;
    // End of variables declaration//GEN-END:variables

}
