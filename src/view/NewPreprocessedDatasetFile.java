/*
SPAMDA: Software for Pre-processing and Analysis of Meteorological DAta to build datasets

Copyright (c) 2017-2018 by AYRNA Research Group. https://www.uco.es/ayrna/
	Authors: 
		Antonio Manuel Gomez Orellana, Juan Carlos Fernandez Caballero,
		Manuel Dorado Moreno, Pedro Antonio Gutierrez Peña and 
		Cesar Hervas Martinez.

This program is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software Foundation,
either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program,
in the file COPYING. If not, see <http://www.gnu.org/licenses/>.

Additional permissions under GNU GPL version 3 section 7:
1. Redistributions of source code, with or without modification, must retain
the above full copyright notice as author attributions.

2. Redistributions in binary form and/or the use of the documentation,
with or without modification, must reproduce the above full copyright notice
as author attributions in the documentation and/or materials provided with
the distribution.

3. Modified versions of source code and/or documentation, as well as binary
distributions, must be marked in reasonable ways as different from the original version.

4. Neither name of copyright holders nor the names of its contributors may be used
to endorse or promote products derived from this software for publicity purposes
without specific prior written permission.

5. Redistribution and/or use of source code, binary format and documentation,
with or without modification, could require indemnification of licensors
and authors by anyone who conveys the material (or modified versions of it)
with contractual assumptions of liability to the recipient, for any liability
that these contractual assumptions directly impose on those licensors and authors.

SPAMDA uses some external libraries. You can see their respective notices about license,
copyright and disclaimer in the following files. For a more complete information about
such licenses, see the distributions provided by their authors:
-Library NetCDF Java, version 4.6.10
	Notice of license in the file NetCDF-LICENSE
-Library SLF4J, version 1.7.25
	Notice of license in the file SLF4J-LICENSE
-Library WEKA, version 3.8.1
	Notice of license in the file WEKA-LICENSE

Contact information:
Juan Carlos Fernandez Caballero, PhD.
email: jfcaballero[at]uco[dot]es
Address: University of Cordoba, Department of Computer Science
and Numerical Analysis, AYRNA Research Group, Rabanales Campus,
Einstein Building, 3rd floor. Road Madrid-Cadiz, Km 396-A.
14071 - Cordoba (Spain).
 */

package view;

import controller.ControllerViewNewPreprocessedDatasetFile;
import view.interfaces.InterfaceViewNewPreprocessedDatasetFile;


/**
 * This class defines the view to create a new pre-processed dataset file.
 * 
 */
public class NewPreprocessedDatasetFile extends javax.swing.JDialog implements InterfaceViewNewPreprocessedDatasetFile {
        
    /* Generated by NetBeans. */
    private static final long serialVersionUID = -2978137943753574902L;
        
    
    /**
     * Creates new dialog NewPreprocessedDatasetFile
     * 
     * @param parent Jframe that creates this dialog.
     * @param modal Indicates if the dialog is modal or not.
     */
    public NewPreprocessedDatasetFile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();             

    }
    
    
    /* Methods of InterfaceViewNewPreprocessedDatasetFile */

    

    @Override
    public void setController(ControllerViewNewPreprocessedDatasetFile controller){
        
        /* Buttons. */
        btnCancelDataset.addActionListener(controller);
        btnSaveDataset.addActionListener(controller);        
        
        /* Menu items. */        
        itemCancel.addActionListener(controller);        
        itemHelpManual.addActionListener(controller);   
    
    }

    
    @Override
    public void showView(){        
        
        /* Shows the view. */
        this.setVisible(true);
    
    }

    
    @Override
    public void closeView(){
    
        /* Closes the view. */
        this.dispose();
        
    }
               
    
    @Override
    public String getPreprocessedDatasetFileName(){
        
        /* Gets the dataset file name to create. */
        
        return jtfFileName.getText();
        
    }
    

    @Override
    public String getShortDescription(){
        
        /* Gets the short descrption about the dataset file created. */
        
        return jtaShortDescription.getText();
    
    }
        

    @Override
    public void setShortDescription(String description){
        
        /* Sets the short descrption about the processed dataset file created. */
        
        this.jtaShortDescription.setText(description);
    
    }
    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnPreprocessedDataset = new javax.swing.JPanel();
        lblShortDescription = new javax.swing.JLabel();
        jscrShortDescription = new javax.swing.JScrollPane();
        jtaShortDescription = new javax.swing.JTextArea();
        lblFileName = new javax.swing.JLabel();
        jtfFileName = new javax.swing.JTextField();
        btnCancelDataset = new javax.swing.JButton();
        btnSaveDataset = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jmMenu = new javax.swing.JMenu();
        itemCancel = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        itemHelpManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SPAMDA 1.0-New pre-processed dataset");
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        setResizable(false);

        jpnPreprocessedDataset.setBorder(javax.swing.BorderFactory.createTitledBorder("Pre-processed dataset"));

        lblShortDescription.setText("Short description");

        jtaShortDescription.setColumns(20);
        jtaShortDescription.setRows(5);
        jtaShortDescription.setToolTipText("Short description of the pre-processed dataset");
        jscrShortDescription.setViewportView(jtaShortDescription);

        lblFileName.setText("File name");

        jtfFileName.setToolTipText("File name of the pre-processed dataset to create");

        javax.swing.GroupLayout jpnPreprocessedDatasetLayout = new javax.swing.GroupLayout(jpnPreprocessedDataset);
        jpnPreprocessedDataset.setLayout(jpnPreprocessedDatasetLayout);
        jpnPreprocessedDatasetLayout.setHorizontalGroup(
            jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnPreprocessedDatasetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblShortDescription)
                    .addGroup(jpnPreprocessedDatasetLayout.createSequentialGroup()
                        .addComponent(jscrShortDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFileName))))
                .addContainerGap())
        );
        jpnPreprocessedDatasetLayout.setVerticalGroup(
            jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnPreprocessedDatasetLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblShortDescription)
                .addGroup(jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnPreprocessedDatasetLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jscrShortDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnPreprocessedDatasetLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblFileName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        btnCancelDataset.setText("Cancel");
        btnCancelDataset.setToolTipText("Cancel action and back to Preprocess");

        btnSaveDataset.setText("Save");
        btnSaveDataset.setToolTipText("Save pre-processed dataset");
        btnSaveDataset.setActionCommand("Save dataset");

        jmMenu.setText("Menu");

        itemCancel.setText("Cancel");
        itemCancel.setToolTipText("Cancel action and back to Preprocess.");
        jmMenu.add(itemCancel);

        jMenuBar.add(jmMenu);

        jmHelp.setText("Help");

        itemHelpManual.setText("Help (User manual)");
        jmHelp.add(itemHelpManual);

        jMenuBar.add(jmHelp);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnPreprocessedDataset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelDataset, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSaveDataset, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnPreprocessedDataset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveDataset)
                    .addComponent(btnCancelDataset))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelDataset;
    private javax.swing.JButton btnSaveDataset;
    private javax.swing.JMenuItem itemCancel;
    private javax.swing.JMenuItem itemHelpManual;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenu jmMenu;
    private javax.swing.JPanel jpnPreprocessedDataset;
    private javax.swing.JScrollPane jscrShortDescription;
    private javax.swing.JTextArea jtaShortDescription;
    private javax.swing.JTextField jtfFileName;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblShortDescription;
    // End of variables declaration//GEN-END:variables
}
