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

import controller.ControllerViewLoadMatchingFile;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import view.interfaces.InterfaceViewLoadMatchingFile;


/**
 * This class defines the view to load or delete the information stored in a XML file and related to a matching process.
 * 
 */
public class LoadMatchingFile extends javax.swing.JDialog implements InterfaceViewLoadMatchingFile {
            
    /* Generated by NetBeans. */
    private static final long serialVersionUID = -5779778980508366376L;
    
    

    /**
     * Creates new dialog LoadMatchingFile
     * 
     * @param parent Jframe that creates this dialog.
     * @param modal Indicates if the dialog is modal or not.
     */
    public LoadMatchingFile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
                
        /* Sets columns size (grid with information about buoys.). */
        tblBuoys.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblBuoys.getColumnModel().getColumn(1).setPreferredWidth(192);

        /* When sets the datamodel will only show the columns created in the view. */
        tblBuoys.setAutoCreateColumnsFromModel(false);
                        
    }
    
    
    /* Methods of InterfaceViewLoadMatchingFile */

    

    @Override
    public void setController(ControllerViewLoadMatchingFile controller){
        
        /* Buttons. */
        btnLoadMatching.addActionListener(controller);        
        btnDeleteMatching.addActionListener(controller);
        btnBack.addActionListener(controller);
        
        /* Menu items. */        
        itemBack.addActionListener(controller);        
        itemHelpManual.addActionListener(controller);
        
        /* Tables. */
        tblBuoys.getSelectionModel().addListSelectionListener(controller);
        
        /* List. */
        jlstMatchingFiles.addMouseListener(controller);
        jlstMatchingFiles.addListSelectionListener(controller);                                   

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
        
        /* Gets the selected row to show all matching files created. */
        
        return tblBuoys.getSelectedRow();
    
    }
    
    @Override
    public void setModelMatching(DefaultListModel<String> listmodel){
        
        /* Shows the matching files that belong to the buoy. */
        
        jlstMatchingFiles.setModel(listmodel);      
        
    }
        
    
    @Override
    public int getSelectedIndexMatching(){
        
        /* Gets the selected matching file to load. */
        
        return jlstMatchingFiles.getSelectedIndex();
    
    }
    
    
    @Override
    public void clearMatchingSummary(){
        
        /* Clears the summary about the matching file. */
                
        jtaMatchingSummary.setText(null);
    
    }    
    
    @Override
    public void addLineMatchingSummary(String information){
        
        /* Adds a line with information about the matching file. */
        
        jtaMatchingSummary.append(information);
            
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
        jscrBuoys = new javax.swing.JScrollPane();
        tblBuoys = new javax.swing.JTable();
        jpnMatchingFiles = new javax.swing.JPanel();
        jscrMatchingFiles = new javax.swing.JScrollPane();
        jlstMatchingFiles = new javax.swing.JList<>();
        jpnMatchingSummary = new javax.swing.JPanel();
        jscrMatchingSummary = new javax.swing.JScrollPane();
        jtaMatchingSummary = new javax.swing.JTextArea();
        btnBack = new javax.swing.JButton();
        btnLoadMatching = new javax.swing.JButton();
        btnDeleteMatching = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jmMenu = new javax.swing.JMenu();
        itemBack = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        itemHelpManual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SPAMDA 1.0-Load matching configuration");
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        setResizable(false);

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
        tblBuoys.setToolTipText("Select a buoy to show matching configurations");
        tblBuoys.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblBuoys.getTableHeader().setReorderingAllowed(false);
        jscrBuoys.setViewportView(tblBuoys);
        if (tblBuoys.getColumnModel().getColumnCount() > 0) {
            tblBuoys.getColumnModel().getColumn(0).setMinWidth(0);
            tblBuoys.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblBuoys.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jpnMatchingFiles.setBorder(javax.swing.BorderFactory.createTitledBorder("Matching configurations"));

        jlstMatchingFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlstMatchingFiles.setToolTipText("Double click to load the selected matching configuration");
        jlstMatchingFiles.setName("jlstMatchingFiles"); // NOI18N
        jscrMatchingFiles.setViewportView(jlstMatchingFiles);
        jlstMatchingFiles.getAccessibleContext().setAccessibleName("jlstMatchingFiles");
        jlstMatchingFiles.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jpnMatchingFilesLayout = new javax.swing.GroupLayout(jpnMatchingFiles);
        jpnMatchingFiles.setLayout(jpnMatchingFilesLayout);
        jpnMatchingFilesLayout.setHorizontalGroup(
            jpnMatchingFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrMatchingFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpnMatchingFilesLayout.setVerticalGroup(
            jpnMatchingFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnMatchingFilesLayout.createSequentialGroup()
                .addComponent(jscrMatchingFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jpnMatchingSummary.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary of the selected matching configuration"));

        jtaMatchingSummary.setEditable(false);
        jtaMatchingSummary.setBackground(new java.awt.Color(245, 245, 245));
        jtaMatchingSummary.setColumns(20);
        jtaMatchingSummary.setRows(5);
        jtaMatchingSummary.setToolTipText("Summary of the selected matching configuration");
        jscrMatchingSummary.setViewportView(jtaMatchingSummary);

        javax.swing.GroupLayout jpnMatchingSummaryLayout = new javax.swing.GroupLayout(jpnMatchingSummary);
        jpnMatchingSummary.setLayout(jpnMatchingSummaryLayout);
        jpnMatchingSummaryLayout.setHorizontalGroup(
            jpnMatchingSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrMatchingSummary)
        );
        jpnMatchingSummaryLayout.setVerticalGroup(
            jpnMatchingSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jscrMatchingSummary, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );

        btnBack.setText("Back");
        btnBack.setToolTipText("Back to Matching configuration");

        btnLoadMatching.setText("Open");
        btnLoadMatching.setToolTipText("Open the selected matching configuration");

        btnDeleteMatching.setText("Delete");
        btnDeleteMatching.setToolTipText("Delete the selected matching configuration");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnMatchingSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(jscrBuoys, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnMatchingFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteMatching, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLoadMatching)))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jscrBuoys, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jpnMatchingFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jpnMatchingSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadMatching)
                    .addComponent(btnBack)
                    .addComponent(btnDeleteMatching))
                .addContainerGap())
        );

        btnDeleteMatching.getAccessibleContext().setAccessibleDescription("Delete selected matching file.");

        jmMenu.setText("Menu");

        itemBack.setText("Back");
        itemBack.setToolTipText("Back to Matching configuration");
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
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteMatching;
    private javax.swing.JButton btnLoadMatching;
    private javax.swing.JMenuItem itemBack;
    private javax.swing.JMenuItem itemHelpManual;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel;
    private javax.swing.JList<String> jlstMatchingFiles;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenu jmMenu;
    private javax.swing.JPanel jpnMatchingFiles;
    private javax.swing.JPanel jpnMatchingSummary;
    private javax.swing.JScrollPane jscrBuoys;
    private javax.swing.JScrollPane jscrMatchingFiles;
    private javax.swing.JScrollPane jscrMatchingSummary;
    private javax.swing.JTextArea jtaMatchingSummary;
    private javax.swing.JTable tblBuoys;
    // End of variables declaration//GEN-END:variables
}
