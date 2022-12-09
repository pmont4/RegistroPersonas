package frames;

import entities.Person;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import lombok.Getter;
import main.Main;
import utils.PeopleTableFilters;

public class Main_Frame extends javax.swing.JFrame {

    @Getter
    private final PeopleTableFilters filters;

    public Main_Frame() {
        initComponents();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.filters = new PeopleTableFilters();

        this.fillTable_People(Main.getPersonManager().getPerson_list());

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Main.getMySQLConnection().close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    private boolean hasFilterApplied = false;

    public void updateTitle(String s) {
        this.setTitle(s);
    }

    public TableModel getPeopleTableModel() {
        return this.peopleTable.getModel();
    }

    public void updateTableModel(TableModel model) {
        this.peopleTable.setModel(model);
    }

    public void fillTable_People(List<Person> list) {
        Object[] data = new Object[this.peopleTable.getColumnCount()];
        DefaultTableModel newModel = (DefaultTableModel) this.peopleTable.getModel();
        list.forEach(p -> {
            data[0] = p.getId();
            data[1] = p.getName();
            data[2] = p.getBirth_date();
            data[3] = Main.getPersonManager().getPersonAge(p);
            if (!p.getHeight().equals("None")) {
                String string_height = "";
                
                String[] split = p.getHeight().split("\\-");
                switch (split[1]) {
                    case "cm": {
                        double ft = (Double.parseDouble(split[0]) / 30.48);
                        BigDecimal height = new BigDecimal(ft);
                        string_height = p.getHeight().replace("-", "") + " (" + height.setScale(2, BigDecimal.ROUND_HALF_UP) + "ft)";
                        break;
                    }
                    case "ft": {
                        double cm = (Double.parseDouble(split[0]) * 30.48);
                        BigDecimal height = new BigDecimal(cm);
                        string_height = p.getHeight().replace("-", "") + " (" + height.setScale(1, BigDecimal.ROUND_HALF_UP) + "cm)";
                        break;
                    }
                }
                data[4] = string_height;
            } else {
                data[4] = "None";
            }
            switch (p.getGender()) {
                case 'M': {
                    data[5] = "Masculino";
                    break;
                }
                case 'F': {
                    data[5] = "Femenino";
                    break;
                }
                case 'N':
                default: {
                    data[5] = "No especificado";
                    break;
                }
            }

            newModel.addRow(data);
        });

        this.peopleTable.setModel(newModel);
    }

    public void clearRowsInTable() {
        DefaultTableModel model = (DefaultTableModel) this.peopleTable.getModel();
        model.setRowCount(0);
        this.peopleTable.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        peopleTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        optionsMenu = new javax.swing.JMenu();
        closeSessionMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        personsMenu = new javax.swing.JMenu();
        addPersonMenuItem = new javax.swing.JMenuItem();
        removePersonMenuItem = new javax.swing.JMenuItem();
        searchPersonMenuItem = new javax.swing.JMenuItem();
        filtersMenu = new javax.swing.JMenu();
        adultFilterMenuItem = new javax.swing.JMenuItem();
        youngerPeopleFilterMenuItem = new javax.swing.JMenuItem();
        tallerPeopleFilterMenuItem = new javax.swing.JMenuItem();
        smallerPeopleFilterMenuItem = new javax.swing.JMenuItem();
        removeFiltersMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPane.setBackground(new java.awt.Color(153, 153, 153));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setText("TABLA DE DATOS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(215, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(210, 210, 210))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(36, 36, 36))
        );

        peopleTable.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        peopleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Fecha de nacimiento", "Edad", "Altura", "Sexo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(peopleTable);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        optionsMenu.setText("Opciones");
        optionsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsMenuActionPerformed(evt);
            }
        });

        closeSessionMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        closeSessionMenuItem.setText("Cerrar sesion");
        closeSessionMenuItem.setToolTipText("Cerrar la sesion actualmente activa.");
        closeSessionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeSessionMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(closeSessionMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(exitMenuItem);

        jMenuBar1.add(optionsMenu);

        personsMenu.setText("Personas");

        addPersonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        addPersonMenuItem.setText("Agregar persona");
        addPersonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPersonMenuItemActionPerformed(evt);
            }
        });
        personsMenu.add(addPersonMenuItem);

        removePersonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        removePersonMenuItem.setText("Borrar persona");
        removePersonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePersonMenuItemActionPerformed(evt);
            }
        });
        personsMenu.add(removePersonMenuItem);

        searchPersonMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        searchPersonMenuItem.setText("Mostar persona");
        searchPersonMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPersonMenuItemActionPerformed(evt);
            }
        });
        personsMenu.add(searchPersonMenuItem);

        jMenuBar1.add(personsMenu);

        filtersMenu.setText("Filtros");

        adultFilterMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        adultFilterMenuItem.setText("Filtrar mayores de edad");
        adultFilterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adultFilterMenuItemActionPerformed(evt);
            }
        });
        filtersMenu.add(adultFilterMenuItem);

        youngerPeopleFilterMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        youngerPeopleFilterMenuItem.setText("Filtrar menores de edad");
        youngerPeopleFilterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                youngerPeopleFilterMenuItemActionPerformed(evt);
            }
        });
        filtersMenu.add(youngerPeopleFilterMenuItem);

        tallerPeopleFilterMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        tallerPeopleFilterMenuItem.setText("Filtrar personas altas");
        tallerPeopleFilterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tallerPeopleFilterMenuItemActionPerformed(evt);
            }
        });
        filtersMenu.add(tallerPeopleFilterMenuItem);

        smallerPeopleFilterMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        smallerPeopleFilterMenuItem.setText("Filtrar personas bajas");
        smallerPeopleFilterMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smallerPeopleFilterMenuItemActionPerformed(evt);
            }
        });
        filtersMenu.add(smallerPeopleFilterMenuItem);

        removeFiltersMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_0, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        removeFiltersMenuItem.setText("Remover filtros");
        removeFiltersMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFiltersMenuItemActionPerformed(evt);
            }
        });
        filtersMenu.add(removeFiltersMenuItem);

        jMenuBar1.add(filtersMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void optionsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsMenuActionPerformed
        System.exit(0);
    }//GEN-LAST:event_optionsMenuActionPerformed

    private void closeSessionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeSessionMenuItemActionPerformed
        if (Main.getJSON_Configuration().existsSessionFile()) {
            CloseSession_Frame closeSession = new CloseSession_Frame();
            this.mainPane.add(closeSession);
            closeSession.show();
        } else {
            JOptionPane.showMessageDialog(null, "No hay ninguna sesion actualmente registrada en los archivos.", "Sesion", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_closeSessionMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void addPersonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPersonMenuItemActionPerformed
        if (Main.getAdministratorOnline().getPerms().contains("add")) {
            AddPerson_Frame addperson = new AddPerson_Frame();
            this.mainPane.add(addperson);
            addperson.show();
        } else {
            JOptionPane.showMessageDialog(null, "No tienes permisos para abrir esta ventana.", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addPersonMenuItemActionPerformed

    private void adultFilterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adultFilterMenuItemActionPerformed
        if (!this.getFilters().filterAge("adult").isEmpty()) {
            this.clearRowsInTable();
            this.fillTable_People(this.getFilters().filterAge("adult"));
            this.hasFilterApplied = true;
        } else {
            JOptionPane.showMessageDialog(null, "Ningun adulto fue encontrado en la lista actual.", "Filtros", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_adultFilterMenuItemActionPerformed

    private void youngerPeopleFilterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_youngerPeopleFilterMenuItemActionPerformed
        if (!this.getFilters().filterAge("young").isEmpty()) {
            this.clearRowsInTable();
            this.fillTable_People(this.getFilters().filterAge("young"));
            this.hasFilterApplied = true;
        } else {
            JOptionPane.showMessageDialog(null, "Ningun menor de edad fue encontrado en la lista actual.", "Filtros", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_youngerPeopleFilterMenuItemActionPerformed

    private void removeFiltersMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFiltersMenuItemActionPerformed
        if (this.hasFilterApplied) {
            try {
                this.clearRowsInTable();
                this.getFilters().removeFiltres();
                this.fillTable_People(Main.getPersonManager().getPerson_list());
                
                this.hasFilterApplied = false;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Un error ha ocurrido: " + ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ningun filtro fue previamente aplicado.", "Filtros", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_removeFiltersMenuItemActionPerformed

    private void searchPersonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPersonMenuItemActionPerformed
        SearchPerson_Frame search = new SearchPerson_Frame();
        this.mainPane.add(search);
        search.show();
    }//GEN-LAST:event_searchPersonMenuItemActionPerformed

    private void tallerPeopleFilterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tallerPeopleFilterMenuItemActionPerformed
        if (!this.getFilters().filterHeightPeople("taller").isEmpty()) {
            this.clearRowsInTable();
            this.fillTable_People(this.getFilters().filterHeightPeople("taller"));
            this.hasFilterApplied = true;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro gente alta en la lista actual.", "Filtros", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tallerPeopleFilterMenuItemActionPerformed

    private void smallerPeopleFilterMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smallerPeopleFilterMenuItemActionPerformed
        if (!this.getFilters().filterHeightPeople("smaller").isEmpty()) {
            this.clearRowsInTable();
            this.fillTable_People(this.getFilters().filterHeightPeople("smaller"));
            this.hasFilterApplied = true;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro gente baja en la lista actual.", "Filtros", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_smallerPeopleFilterMenuItemActionPerformed

    private void removePersonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePersonMenuItemActionPerformed
        if (Main.getAdministratorOnline().getPerms().contains("remove")) {
            DeletePerson_Frame delete = new DeletePerson_Frame();
            this.mainPane.add(delete);
            delete.show();
        } else {
            JOptionPane.showMessageDialog(null, "No tienes permisos para abrir esta ventana.", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removePersonMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addPersonMenuItem;
    private javax.swing.JMenuItem adultFilterMenuItem;
    private javax.swing.JMenuItem closeSessionMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu filtersMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JDesktopPane mainPane;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JTable peopleTable;
    private javax.swing.JMenu personsMenu;
    private javax.swing.JMenuItem removeFiltersMenuItem;
    private javax.swing.JMenuItem removePersonMenuItem;
    private javax.swing.JMenuItem searchPersonMenuItem;
    private javax.swing.JMenuItem smallerPeopleFilterMenuItem;
    private javax.swing.JMenuItem tallerPeopleFilterMenuItem;
    private javax.swing.JMenuItem youngerPeopleFilterMenuItem;
    // End of variables declaration//GEN-END:variables

}
