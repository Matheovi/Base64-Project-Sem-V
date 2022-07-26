/** View is responsible for displaying info and taking input from a user
 *
 */
package pl.polsl.lab.wiatrok.mateusz.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pl.polsl.lab.wiatrok.mateusz.annotations.URLsafe;

/**
 * Class responsible for interaction with user
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
public class View extends JFrame{


        /**
         * encode button
         */
        private javax.swing.JButton encodeButton;
        
        /**
         * decode button
         */
        private javax.swing.JButton decodeButton;
        
        /**
         * here is file menu
         */
        private javax.swing.JMenu fileMenu;
        
        /**
         * here is edit menu
         */
        private javax.swing.JMenu editMenu;
        
        /**
         * here is bar, on which menu tiles lay
         */
        private javax.swing.JMenuBar jMenuBar1;
        
        
        /**
         * here is checkbox in menu, that controls the safeurl value
         */
        private javax.swing.JCheckBoxMenuItem safeURL;
        
        /**
         * here is panel, on which program is
         */
        private javax.swing.JPanel programPanel;
        
        /**
         * here is scroll pane, that helps with smaller program windows
         */
        private javax.swing.JScrollPane jScrollPane1;
        
        /**
         * here is scroll pane, that helps with smaller program windows
         */
        private javax.swing.JScrollPane jScrollPane2;
        
        /**
         * here is main tab, with whole gui
         */
        private javax.swing.JTabbedPane mainTabbedPane;
        
        /**
         * in these area input text should be inputted
         */
        private javax.swing.JTextArea inputArea;
        
        /**
         * here output string is displayed
         */
        private javax.swing.JTextArea outputArea;
        
        /**
         * this array is responsible for feeding data to table
         */
         private DefaultTableModel array;
        
         /**
          * this panel is for storing logs
          */
        private javax.swing.JPanel logPanel;
        
        /**
         * this scroll helps with logs seeing on smaller windows
         */
        private javax.swing.JScrollPane logScroll;
        
        /**
         * this table is for logs
         */
        private javax.swing.JTable logTable;
        
        /**
         * this is frame
         */
        private Component frame;

        /**
         * Default View class constructor. creates new scanner object.
         */
        public View() {
                initComponents();
        }


        /**
         * Function seting raw input taken from user. Doesnt check correctness
         * of string.
         *
         * @return String returns string taken from user
         */
        public String takeInputFromUser() {
                String rawinput;
                rawinput = inputArea.getText();
                return rawinput;
        }

        /**
         * function printing error to the GUI
         *
         * @param exception error to show message from
         */
        public void printError(Exception exception) {
                JOptionPane.showMessageDialog(frame,
                exception.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        /**
         * Function displaying result to the user in GUI
         *
         * @param string string to be shown in the GUI
         */
        public void display(String string) {
                outputArea.setText(string);
        }

        
        /**
         * Function initializing whole GUI
         */
        private void initComponents() {


                mainTabbedPane = new javax.swing.JTabbedPane();
                programPanel = new javax.swing.JPanel();
                encodeButton = new javax.swing.JButton();
                decodeButton = new javax.swing.JButton();
                jScrollPane1 = new javax.swing.JScrollPane();
                inputArea = new javax.swing.JTextArea();
                jScrollPane2 = new javax.swing.JScrollPane();
                outputArea = new javax.swing.JTextArea();
                jMenuBar1 = new javax.swing.JMenuBar();
                fileMenu = new javax.swing.JMenu();
                safeURL = new javax.swing.JCheckBoxMenuItem();
                editMenu = new javax.swing.JMenu();

                       

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                encodeButton.setText("Encode");
                decodeButton.setText("Decode");
                inputArea.setColumns(20);
                inputArea.setRows(5);
                jScrollPane1.setViewportView(inputArea);

                outputArea.setColumns(20);
                outputArea.setRows(5);
                jScrollPane2.setViewportView(outputArea);

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(programPanel);
                programPanel.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(encodeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decodeButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jScrollPane2)
                );
                jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(encodeButton)
                .addComponent(decodeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                );

                
                
                
                
                
                


                logPanel = new javax.swing.JPanel();
                logScroll = new javax.swing.JScrollPane();
                logTable = new javax.swing.JTable();

                
               array = new DefaultTableModel();
                
                
                array.addColumn("Date");
                array.addColumn("Input");
                array.addColumn("Output");
                array.addColumn("URLSafe");
               // array.addRow(new Object[]{"Date", "Input", "Output", "URLSafe"});
                
                
                
                
                
                
                logTable.setModel(array);
                logScroll.setViewportView(logTable);

                javax.swing.GroupLayout logPanelLayout = new javax.swing.GroupLayout(logPanel);
                logPanel.setLayout(logPanelLayout);
                logPanelLayout.setHorizontalGroup(
                        logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(logScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                );
                logPanelLayout.setVerticalGroup(
                        logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(logScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                );




                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                mainTabbedPane.addTab("program", programPanel);


                mainTabbedPane.addTab("logs", logPanel);



                fileMenu.setText("File");

                safeURL.setText("safeURL");
                fileMenu.add(safeURL);

                jMenuBar1.add(fileMenu);

                editMenu.setText("Edit");
                jMenuBar1.add(editMenu);

                setJMenuBar(jMenuBar1);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainTabbedPane)
                );
                layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainTabbedPane)
                );

  
                
                pack();
        }

        
        /**
         * Function that returns button object
         * @return JButton returns button object
         */
        public JButton getDecodeButton()
        {
                return decodeButton;
        }
        
         /**
         * Function that returns button object
         * @return JButton returns button object
         */
        public JButton getEncodeButton()
        {
                return encodeButton;
        }
        
        
        /**
         * function returning menu item object
         * @return JCheckBoxMenuItem returns menu item object
         */
        public JCheckBoxMenuItem getMenuItem()
        {
                return safeURL;
        }
        
        
        /**
         * Function filling the log table with result
         */
        public void printLogToTable()
    {
        array.addRow(
        new Object[]
        {
                java.util.Calendar.getInstance().getTime(), 
                inputArea.getText(), 
                outputArea.getText(), 
                safeURL.getState()
        });
    }
        
        
}
