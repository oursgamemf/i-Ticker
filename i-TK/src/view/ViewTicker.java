/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static controller.ManageExcel.getAllDataFromFile;
import static controller.ManageExcel.getHeaderList;
import static controller.ManageExcel.setInputFile;
import controller.RowTicker;
import controller.TickerController;
import static controller.TickerController.getRowTickerArray;
import static controller.TickerController.runMeAtStart;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import model.DBtkEvo;
import static controller.ManageExcel.getAllDataFromFile;
import static controller.ManageExcel.getAllDataFromTKFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author virginia
 */
public class ViewTicker extends javax.swing.JFrame {

    public static TickerController tkC = new TickerController();

    /**
     * Creates new form ViewTicker
     */
    public ViewTicker() {
        // Inizialize data from config file: Create or COnn DB - Create two tables return the list of config data.
        DBtkEvo myStmtDB = (DBtkEvo) runMeAtStart().get(0);
        ArrayList<String> setList = new ArrayList<>();
        ArrayList<Object> subSetList = runMeAtStart();
        for (int ii = 1; ii < runMeAtStart().size(); ii++) {
            setList.add((String) subSetList.get(ii));
        } // From here setList has all config data
        
        //RowTicker rrt = myStmtDB.getAllFromDBData(); // get data from DB
        
        
        
        // Initialize the UI
        initComponents();
        
        // Enable or disable the search Button
        String pathTKsaved = setList.get(8);
        buttonEnabling(pathTKsaved);
        
    }

    public void write2configFile(String selectedPath) throws FileNotFoundException, IOException{
        File inputFile = new File("i_tk_prova.config");
        File tempFile = new File("myTempFile.config");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToReplace = "savedTickerPath=";
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            String[] rowElement = trimmedLine.split(";");
            if(rowElement[0].equals(lineToReplace)){
                writer.write(rowElement[0] + ";" + selectedPath + ";" + System.getProperty("line.separator"));
            }else{
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close(); 
        reader.close();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jSplitPane4 = new javax.swing.JSplitPane();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane2.setRightComponent(jButton1);

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField1.setText("e.g.GBS.MI");

        jButton4.setText("Sel Cartella Dest");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jSplitPane4.setLeftComponent(jButton4);

        jButton5.setText("Avvia Scaricamento");
        jButton5.setToolTipText("");
        jSplitPane4.setRightComponent(jButton5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSplitPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(jPanel1);

        jSplitPane1.setTopComponent(jSplitPane2);
        jSplitPane1.setRightComponent(jScrollPane2);

        jScrollPane1.setViewportView(jSplitPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //In response to a button click:
        int result = fc.showOpenDialog(jButton4);
        
        if (result == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fc.getSelectedFile().getAbsolutePath();
            System.out.println("Open was selected: " + selectedPath);
            try {
                write2configFile(selectedPath);
            } catch (IOException ex) {
                Logger.getLogger(ViewTicker.class.getName()).log(Level.SEVERE, null, ex);
            }
            jButton5.setEnabled(true);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            String selectedPath = fc.getCurrentDirectory().getAbsolutePath();
            System.out.println("Cancel was selected: " + "none");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void buttonEnabling(String pathConfigFile){
        if (pathConfigFile.equals("none")){
            jButton5.setEnabled(false);
        }else{
            jButton5.setEnabled(true);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
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

        //DBtkEvo myStmtDB = (DBtkEvo) runMeAtStart().get(0);
        //ArrayList<String> setList = new ArrayList<>();
        //ArrayList<Object> subSetList = runMeAtStart();
        //subSetList = subSetList.subList(1,runMeAtStart().size());
        //for (int ii = 1; ii < runMeAtStart().size(); ii++) {
        //    setList.add((String) subSetList.get(ii));
        //}
        //String testTKSearch = "PHAU.MI";
        //String myTKs = TickerController.makeURL(testTKSearch);
        //TickerController.searchSaveTK(myTKs, testTKSearch);
        //ArrayList<ArrayList<String>> data = getAllDataFromTKFile(testTKSearch, ',');
        //ArrayList<RowTicker> myTicker = getRowTickerArray(data);
        //myStmtDB.insertRowTKinDB(myTicker, myStmtDB.getQuery());// Use default query -NEED override this method!!
     

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTicker().setVisible(true);
            }
        });
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
