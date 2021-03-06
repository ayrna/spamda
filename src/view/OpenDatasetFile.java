/*
SPAMDA: Software for Pre-processing and Analysis of Meteorological DAta to build datasets

Copyright (c) 2017-2021 by AYRNA Research Group. https://www.uco.es/ayrna/
    Authors: 
      Gómez-Orellana, A.M.; Fernández, J.C.; Dorado-Moreno, M.; Gutiérrez, P.A.; Hervás-Martínez, C.
      Building Suitable Datasets for Soft Computing and Machine Learning Techniques from Meteorological
       Data Integration: A Case Study for Predicting Significant Wave Height and Energy Flux.
      Energies 2021, 14, 468. https://doi.org/10.3390/en14020468                                                                       

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
Antonio Manuel Gomez Orellana, Mr.
email: am[dot]gomez[at]uco[dot]es
Address: University of Cordoba, Department of Computer Science
and Numerical Analysis, AYRNA Research Group, Rabanales Campus,
Einstein Building, 3rd floor. Road Madrid-Cadiz, Km 396-A.
14071 - Cordoba (Spain).

Juan Carlos Fernandez Caballero, PhD.
email: jfcaballero[at]uco[dot]es
Address: University of Cordoba, Department of Computer Science
and Numerical Analysis, AYRNA Research Group, Rabanales Campus,
Einstein Building, 3rd floor. Road Madrid-Cadiz, Km 396-A.
14071 - Cordoba (Spain).
 */

package view;

import controller.ControllerViewOpenDatasetFile;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import view.interfaces.InterfaceViewOpenDatasetFile;

/**
 * This class defines the view to open an intermediate or pre-processed dataset file for being pre-processed.
 * 
 */
public class OpenDatasetFile extends javax.swing.JDialog implements InterfaceViewOpenDatasetFile {
                      
    /* Generated by NetBeans. */
    private static final long serialVersionUID = -8460487540000383020L;
    

    /**
     * Creates new dialog OpenDatasetFile
     * 
     * @param parent Jframe that creates this dialog.
     * @param modal Indicates if the dialog is modal or not.
     */
    public OpenDatasetFile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        
        /* Sets columns size (grid with information about buoys.). */
        tblBuoys.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblBuoys.getColumnModel().getColumn(1).setPreferredWidth(192);

        /* When sets the datamodel will only show the columns created in the view. */
        tblBuoys.setAutoCreateColumnsFromModel(false);

    }
    
    
    /* Methods of InterfaceViewOpenDatasetFile */

    

    @Override
    public void setController(ControllerViewOpenDatasetFile controller){
        
        /* Buttons. */
        btnOpenDataset.addActionListener(controller);        
        btnBack.addActionListener(controller);
        
        /* Menu items. */        
        itemBack.addActionListener(controller);        
        itemHelpManual.addActionListener(controller);
        
        /* Tables. */
        tblBuoys.getSelectionModel().addListSelectionListener(controller);
        
        /* List. */
        jlstDatasetFiles.addMouseListener(controller);
        jlstDatasetFiles.addListSelectionListener(controller);
        jlstPreprocessedDataset.addMouseListener(controller);
        jlstPreprocessedDataset.addListSelectionListener(controller);        

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
    public DefaultTableModel getModelBuoys(){
    
        /* Returns DefaultTableModel of the table that will show the id and name about each buoy. */
        return (DefaultTableModel) tblBuoys.getModel();
        
    }
    
    
    @Override
    public void setModelBuoys(DefaultTableModel datamodel){
        
        /* Shows the StationID about each buoy. */
        
        tblBuoys.setModel(datamodel);
        
    }

    
    @Override
    public int getSelectedRowBuoy(){
        
        /* Gets the selected row to show all dataset files created. */
        
        return tblBuoys.getSelectedRow();
    
    }

    
    @Override
    public void setModelDatasets(DefaultListModel<String> listmodel){
        
        /* Shows the dataset files that belong to the buoy. */
        
        jlstDatasetFiles.setModel(listmodel);      
        
    }

    
    @Override
    public void setModelPreprocessedDatasets(DefaultListModel<String> listmodel){
        
        /* Show the preprocess files that belong to dataset file buoy. */
        
        jlstPreprocessedDataset.setModel(listmodel);      
        
    }
    
        
    @Override
    public int getSelectedIndexDataset(){
        
        /* Gets the selected dataset file to open. */
        
        return jlstDatasetFiles.getSelectedIndex();
    
    }
    
    
    @Override
    public int getSelectedIndexPreprocessedDatasets(){
        
        /* Gets the selected preprocess file. */
        
        return jlstPreprocessedDataset.getSelectedIndex();
    
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnOpenDataset = new javax.swing.JButton();
        jscrBuoys = new javax.swing.JScrollPane();
        tblBuoys = new javax.swing.JTable();
        jpnDatasetFiles = new javax.swing.JPanel();
        jscrDatasetFiles = new javax.swing.JScrollPane();
        jlstDatasetFiles = new javax.swing.JList<>();
        jpnPreprocessedDataset = new javax.swing.JPanel();
        jscrPreprocessedDataset = new javax.swing.JScrollPane();
        jlstPreprocessedDataset = new javax.swing.JList<>();
        jMenuBar = new javax.swing.JMenuBar();
        jmMenu = new javax.swing.JMenu();
        itemBack = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        itemHelpManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SPAMDA 1.0-Open an intermediate or pre-processed dataset");
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        setResizable(false);

        btnBack.setText("Back");
        btnBack.setToolTipText("Back to Preprocess");

        btnOpenDataset.setText("Open");
        btnOpenDataset.setToolTipText("Open the selected intermediate or pre-processed dataset");

        jscrBuoys.setBorder(null);

        tblBuoys.setBorder(null);
        tblBuoys.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Station ID"
            }
        ) {

            /* Generated by NetBeans. */
            private static final long serialVersionUID = -1006288877740180822L;

            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBuoys.setToolTipText("Select a buoy to show intermediate datasets");
        tblBuoys.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblBuoys.getTableHeader().setReorderingAllowed(false);
        jscrBuoys.setViewportView(tblBuoys);
        if (tblBuoys.getColumnModel().getColumnCount() > 0) {
            tblBuoys.getColumnModel().getColumn(0).setMinWidth(0);
            tblBuoys.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblBuoys.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jpnDatasetFiles.setBorder(javax.swing.BorderFactory.createTitledBorder("Intermediate datasets"));

        jlstDatasetFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlstDatasetFiles.setToolTipText("Double click to open the selected intermediate dataset");
        jlstDatasetFiles.setName("jlstDatasetFiles"); // NOI18N
        jscrDatasetFiles.setViewportView(jlstDatasetFiles);
        jlstDatasetFiles.getAccessibleContext().setAccessibleName("jlstDatasetFiles");
        jlstDatasetFiles.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jpnDatasetFilesLayout = new javax.swing.GroupLayout(jpnDatasetFiles);
        jpnDatasetFiles.setLayout(jpnDatasetFilesLayout);
        jpnDatasetFilesLayout.setHorizontalGroup(
            jpnDatasetFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrDatasetFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpnDatasetFilesLayout.setVerticalGroup(
            jpnDatasetFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrDatasetFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jpnPreprocessedDataset.setBorder(javax.swing.BorderFactory.createTitledBorder("Pre-processed datasets"));

        jlstPreprocessedDataset.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlstPreprocessedDataset.setToolTipText("Double click to open the selected pre-processed dataset");
        jlstPreprocessedDataset.setName("jlstPreprocessedDataset"); // NOI18N
        jscrPreprocessedDataset.setViewportView(jlstPreprocessedDataset);
        jlstPreprocessedDataset.getAccessibleContext().setAccessibleName("jlstPreprocessFiles");

        javax.swing.GroupLayout jpnPreprocessedDatasetLayout = new javax.swing.GroupLayout(jpnPreprocessedDataset);
        jpnPreprocessedDataset.setLayout(jpnPreprocessedDatasetLayout);
        jpnPreprocessedDatasetLayout.setHorizontalGroup(
            jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrPreprocessedDataset, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpnPreprocessedDatasetLayout.setVerticalGroup(
            jpnPreprocessedDatasetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrPreprocessedDataset, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jscrBuoys, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnDatasetFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnPreprocessedDataset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnOpenDataset)))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jpnDatasetFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpnPreprocessedDataset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jscrBuoys, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpenDataset)
                    .addComponent(btnBack))
                .addContainerGap())
        );

        jmMenu.setText("Menu");

        itemBack.setText("Back");
        itemBack.setToolTipText("Back to Preprocess");
        jmMenu.add(itemBack);

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
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnOpenDataset;
    private javax.swing.JMenuItem itemBack;
    private javax.swing.JMenuItem itemHelpManual;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel;
    private javax.swing.JList<String> jlstDatasetFiles;
    private javax.swing.JList<String> jlstPreprocessedDataset;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenu jmMenu;
    private javax.swing.JPanel jpnDatasetFiles;
    private javax.swing.JPanel jpnPreprocessedDataset;
    private javax.swing.JScrollPane jscrBuoys;
    private javax.swing.JScrollPane jscrDatasetFiles;
    private javax.swing.JScrollPane jscrPreprocessedDataset;
    private javax.swing.JTable tblBuoys;
    // End of variables declaration//GEN-END:variables
}
