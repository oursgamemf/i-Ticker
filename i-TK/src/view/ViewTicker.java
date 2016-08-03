/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ManageExcel;
import static controller.ManageExcel.checkIfExists;
import controller.RowTicker;
import controller.TickerController;
import static controller.TickerController.getRowTickerArray;
import static controller.TickerController.runMeAtStart;
import java.io.IOException;
import java.util.ArrayList;
import model.DBtkEvo;
import static controller.ManageExcel.getAllDataFromTKFile;
import controller.OutputMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import controller.RowChoosenTks;
import static controller.TickerController.sortTicker;
import java.awt.BorderLayout;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author virginia
 */
public class ViewTicker extends javax.swing.JFrame {

    public static TickerController tkC = new TickerController();
    public static String outputExcelFile = "none";
    public static String choosedTKTable = "";
    public static DBtkEvo myStmtDB = null;
    public static String queryToInsChTK = "";
    public static JTable myTable;

    /**
     * Creates new form ViewTicker
     */
    public ViewTicker() {
        // UI setting at Start

        // Inizialize data from config file: Create or COnn DB - Create two tables return the list of config data.
        myStmtDB = (DBtkEvo) runMeAtStart().get(0);
        ArrayList<String> setList = new ArrayList<>();
        ArrayList<Object> subSetList = runMeAtStart();
        for (int ii = 1; ii < runMeAtStart().size(); ii++) {
            setList.add((String) subSetList.get(ii));
        } // From here setList has all config data
        outputExcelFile = setList.get(8);
        //RowTicker rrt = myStmtDB.getAllFromDBData(); // get data from DB

        // Set source/target table for choosed TK
        choosedTKTable = setList.get(4);

        // Query Insert data in CH TK table
        queryToInsChTK = setList.get(6);

        // Initialize the UI
        initComponents();

        // Enable or disable the search Button
        // String pathTKsaved = setList.get(8);
        //buttonEnabling(pathTKsaved);
        buttonEnabling();

        // set Table Model
        myTable = setTableAtStart();

        // fill Table Model
        fillTableFromDB(choosedTKTable, myStmtDB, myTable);
    }

    public void write2configFile(String selectedPath) throws FileNotFoundException, IOException {
        File inputFile = new File(TickerController.getConfigFullPath());
        File tempFile = new File(TickerController.getConfigTempFullPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8")) //BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                ) {

            String lineToReplace = "savedTickerPath=";
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] rowElement = trimmedLine.split(";");
                if (rowElement[0].equals(lineToReplace)) {
                    writer.write(rowElement[0] + ";" + selectedPath + ";" + System.getProperty("line.separator"));
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
        }
        //BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        System.out.println(successful);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane4 = new javax.swing.JSplitPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSplitPane5 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jButton1.setText("Sel. Cartella Destinazione");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jSplitPane4.setLeftComponent(jButton1);

        jButton2.setText("Avvia Scaricamento");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jSplitPane4.setRightComponent(jButton2);

        jSplitPane3.setRightComponent(jSplitPane4);

        jTextField1.setText("e.g.PHAU.MI");
        jSplitPane3.setLeftComponent(jTextField1);

        jSplitPane2.setTopComponent(jSplitPane3);

        jSplitPane5.setDividerLocation(230);
        jSplitPane5.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane5.setTopComponent(jScrollPane1);

        jTextField2.setText("jTextField2");
        jSplitPane5.setRightComponent(jTextField2);

        jSplitPane2.setRightComponent(jSplitPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //In response to a button click:
        int result = fc.showOpenDialog(jButton1);

        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fc.getSelectedFile().getAbsolutePath();
            System.out.println("Open was selected: " + selectedPath);
            try {
                write2configFile(selectedPath);
                outputExcelFile = selectedPath;

            } catch (IOException ex) {
                Logger.getLogger(ViewTicker.class.getName()).log(Level.SEVERE, null, ex);
            }
            jButton2.setEnabled(true);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            String selectedPath = fc.getCurrentDirectory().getAbsolutePath();
            System.out.println("Cancel was selected: " + "none");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private JTable setTableAtStart() {
        String[] columnNames = {"Codice", "Ultimo Download", "Auto-Download", "Periodo", ""};
        Object[][] data = {
            {null, null, null, null, null}
        };
        JTable table = new javax.swing.JTable();
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setVisible(true);
        jScrollPane1.getViewport().add(table);
        table.setFillsViewportHeight(true);
        return table;
    }

    private JTable recreateUpdatedTable(String tableDBName, DBtkEvo myDB) {
        ResultSet rs = myDB.getAllRowChoosenDBDataRS(tableDBName);
        JTable table = new JTable();
        try {
            table = new JTable(myDB.buildTableModel(rs));
        } catch (SQLException ex) {
            System.out.println("Unable to reach the DB");
        }
        return table;
    }

    private void fillTableFromDB(String tableDBName, DBtkEvo myDB, JTable tableUI) {
        ArrayList<RowChoosenTks> allChosenTKs = myDB.getAllRowChoosenDBData(tableDBName);
        for (RowChoosenTks tcTK : allChosenTKs) {
            DefaultTableModel modelDef = (DefaultTableModel) tableUI.getModel();
            Object[][] data = {
                {null, null, null, null, null}
            };
            modelDef.addRow(data);

            tableUI.getModel().setValueAt(tcTK.getTickerName(), tableUI.getModel().getRowCount() - 1, 0);
            tableUI.getModel().setValueAt(tcTK.getLastDownloadDate(), tableUI.getModel().getRowCount() - 1, 1);
            tableUI.getModel().setValueAt(tcTK.getAutomaticRefresh(), tableUI.getModel().getRowCount() - 1, 2);
            tableUI.getModel().setValueAt(tcTK.getRefreshPeriod(), tableUI.getModel().getRowCount() - 1, 3);
        }

        //column = tableUI.getColumnModel().getColumn(i);
    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String tickerName = jTextField1.getText();
        Boolean isWebConn = TickerController.getWebConnection();
        if (isWebConn) {
            String myTKs = TickerController.makeURL(tickerName);
            TickerController.searchSaveTK(myTKs, tickerName, jTextField2);
            ArrayList<ArrayList<String>> data = getAllDataFromTKFile(tickerName, ',');
            ArrayList<RowTicker> myTicker = getRowTickerArray(data);
            ArrayList<RowTicker> mySortedTicker = sortTicker(myTicker);
            // Add Choosen TK
            ArrayList<RowChoosenTks> information = new ArrayList<>();
            RowChoosenTks myRowCh = TickerController.addTkChoosenInOBJ(myStmtDB, choosedTKTable, tickerName);
            information.add(myRowCh);
            Boolean allIn = myStmtDB.insRowChoosenTKinDB(information, queryToInsChTK, choosedTKTable);
            if (allIn) {
                // If data are correctly setted in the DB add the Row Into the table

            }

            // end
//            ManageExcel.createExcel(myTicker, outputExcelFile, tickerName);
            boolean fileAlreadyExists = checkIfExists(tickerName, outputExcelFile);

            if (fileAlreadyExists) {

                ManageExcel.modifyExcel(mySortedTicker, outputExcelFile, tickerName, jTextField2);

            } else {

                ManageExcel.createExcel(mySortedTicker, outputExcelFile, tickerName, jTextField2);
                //TickerController.addTkChoosenInOBJ();
            }

        } else {
            System.out.println("Controllare la connessione ad internet");
            String outMsg = "Impossibile scaricare il file: \'" + tickerName + "\' . Controllare la connessione ad internet";
            OutputMessage.setOutputText(outMsg, jTextField2, 2);
        }
        fillTableFromDB(choosedTKTable, myStmtDB, myTable); // Da spostare!!!

    }//GEN-LAST:event_jButton2ActionPerformed

    private void buttonEnabling() {
        if (outputExcelFile.equals("none")) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
    }

    private void buttonEnabling(String pathConfigFile) {
        if (pathConfigFile.equals("none")) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //ArrayList<RowTicker> myTicker = getRowTickerArray(data);
        //myStmtDB.insertRowTKinDB(myTicker, myStmtDB.getQuery());// Use default query -NEED override this method!!
        java.awt.EventQueue.invokeLater(() -> {
            new ViewTicker().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
