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

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ReanalysisDatabase;
import view.interfaces.InterfaceViewSelectReanalysisFiles;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import utils.Utils;


/**
 * This class defines the controller for managing the events generated by the view SelectReanalysisFiles.
 * 
 */
public class ControllerViewSelectReanalysisFiles implements ActionListener, ListSelectionListener, MouseListener {
    
    /**
     * View.
     */
    private final InterfaceViewSelectReanalysisFiles view;
           
    
    /**
     * Reanalysis data files's information.
     */        
    private DefaultTableModel modelReanalysisFiles;
            
    
    /**
     * Selected reanalysis data files by user.
     */            
    private ArrayList<String> selectedReanalysisFiles;
    
    
    /**
     * Selected reanalysis variable of each reanalysis file.
     */            
    private ArrayList<String> selectedReanalysisVars;
    
    
    /**
     * Number of geopoints in selected reanalysis data files.
     */
    private int numberOfGeopoints;    

    
    /**
     * Rows of compatibles reanalysis data files.
     */
    private ArrayList<Integer> rowsOfCompatiblesReanalysisFiles;
       
    
    /**
     * To know if user confirmed reanalysis data files selection.
     */    
    private boolean userConfirmedSelection;

    
        
    /* Methods of the class */
    
    
    /**
     * Constructor.
     * @param view View that this controller will manage.
     * @param datamodel Reanalysis data files's information.
     * @param alreadySelectedFiles Already selected reanalysis data files in tab: Matching (ManageBuoys view).
     * @param alreadySelectedVars Name of the var of each already selected reanalysis data file.
     * @param numberOfGeopoints Number of geopoints in selected reanalysis data files.
     */
    public ControllerViewSelectReanalysisFiles(InterfaceViewSelectReanalysisFiles view, DefaultTableModel datamodel, ArrayList<String> alreadySelectedFiles, ArrayList<String> alreadySelectedVars, int numberOfGeopoints){    
        
        /* Sets View */
        this.view = view;
        
        /* Rows of compatibles reanalysis files. */
        this.rowsOfCompatiblesReanalysisFiles = new ArrayList<>();        
        
        /* To know if user confirmed reanalysis files selection. */
        this.userConfirmedSelection=false;        
        
        /* Sets the model of the table that will show the information about reanalysis files. */
        this.modelReanalysisFiles=getReanalysisFiles(datamodel, alreadySelectedFiles);
        this.view.setModelReanalysisFiles(this.modelReanalysisFiles);
        
        /* Moves column 'Selected' to first position. */
        this.view.moveColumnTo(11, 0);
        
        /* Sets selected reanalysis files by user. */
        this.selectedReanalysisFiles = alreadySelectedFiles;
        
        /* Sets selected reanalysis variable of each reanalysis file. */
        this.selectedReanalysisVars = alreadySelectedVars;
                
        /* Number of geopoints in selected reanalysis files. */
        this.numberOfGeopoints=numberOfGeopoints;

    }

    
    /**
     * Returns view.
     * @return View.
     */
    private InterfaceViewSelectReanalysisFiles getView(){
        
        /* Returns view */
        return view;
        
    }        
    
    
    /**
     * Returns reanalysis data files's information.
     * @return Reanalysis data files's information.
     */
    private DefaultTableModel getModelReanalysis(){
        
        /* Returns model */
        return this.modelReanalysisFiles;
        
    }


    /**
     * Returns rows of compatibles reanalysis data files.
     * @return Rows of compatibles reanalysis data files.
     */
    private ArrayList<Integer> getRowsOfCompatiblesReanalysisFiles(){
        
        /* Rows of compatibles reanalysis files. */
        return this.rowsOfCompatiblesReanalysisFiles;
        
    }

    
    /**
     * Returns true if user confirmed reanalysis data files selection or False if not.
     * @return True if user confirmed reanalysis data files selection or False if not.
     */
    public boolean getUserConfirmedSelection(){
        
        /* Gets True if user confirmed reanalysis files selection or False if not. */
        return this.userConfirmedSelection;
        
    }    
    
    
    /**
     * Sets true if user confirmed reanalysis data files selection or False if not.
     * @param userConfirmedSelection True if user confirmed reanalysis data files selection or False if not.
     */
    private void setUserConfirmedSelection(boolean userConfirmedSelection){
        
        /* Sets True if user confirmed reanalysis data files selection or False if not. */
        this.userConfirmedSelection=userConfirmedSelection;
        
    }    

        
    /**
     * Returns selected reanalysis data files by user.
     * @return Selected reanalysis data files by user.
     */
    public ArrayList<String> getSelectedReanalysisFiles(){
        
        /* Gets selected reanalysis files by user. */
        
        return this.selectedReanalysisFiles;
        
    }
    

    /**
     * Returns selected reanalysis variable of each reanalysis file.
     * @return Selected reanalysis variable of each reanalysis file
     */
    public ArrayList<String> getSelectedReanalysisVars(){
        
        /* Gets selected reanalysis files by user. */
        
        return this.selectedReanalysisVars;
        
    }    
    
    
    /**
     * Returns number of geopoints in selected reanalysis data files.
     * @return Number of geopoints in selected reanalysis data files.
     */
    public int getNumberOfGeopoints(){
    
        /* Gets number of geopoints in selected reanalysis data files. */
        
        return this.numberOfGeopoints;

    }
                      
    
    /**
     * Sets number of geopoints in selected reanalysis data files.
     * @param numberOfGeopoints Number of geopoints in selected reanalysis data files.
     */
    private void setNumberOfGeopoints(int numberOfGeopoints){
    
        /* Sets number of geopoints in selected reanalysis data files. */
        
        this.numberOfGeopoints = numberOfGeopoints;

    }    
                    
    
    /**
     * Returns number of reanalysis data files to select.
     * @return Number of reanalysis data files to select.
     */
    public int numberOfReanalysisFiles(){
        
        /* Number of reanalysis files to select. */
        return getModelReanalysis().getRowCount();
        
    }
    
        
    /**
     * Sets cell renderer in table that shows reanalysis data files's information.
     */    
    public void setTblReanalysisFilesCellRenderer() {
    
        /* Sets TableCellRenderer for tblReanalysisFiles. */
        
        getView().setTblReanalysisFilesCellRenderer(new DefaultTableCellRenderer() {                                    
                        
            /* Generated by NetBeans. */
            private static final long serialVersionUID = -347184672129699492L;

            /* Selected/Unselected reanalysis files will be displayed in different color. */
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                /* Component of table. */
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                /* 
                    Selected files ...: CYAN color
                    Unselected files .: WHITE color
                    Current row  .....: default color
                */
                
                if(getRowsOfCompatiblesReanalysisFiles().contains(row)){
                    
                    /* Selected files. */
                    
                    c.setBackground(Color.CYAN);
                
                }else if(row!=getView().getSelectedRow()){
                    
                    /* Unselected files. */
                    
                    c.setBackground(Color.WHITE);
                
                }   /* default color. */

                return c;
            }
        });
        
    }
            
    

    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void actionPerformed(ActionEvent event){

        /* Manages the event generated by the view. */
        
        /* Gets the event name.*/
        String eventName = event.getActionCommand();
        
        switch (eventName) {

            case InterfaceViewSelectReanalysisFiles.CONFIRM_SELECTION:
                
                /* Action CONFIRM_SELECTION clicked. */
                doConfirmSelection();
                break;    
                
            case InterfaceViewSelectReanalysisFiles.BACK:
                
                /* Action BACK clicked. */
                doBack();
                break;
            
            case InterfaceViewSelectReanalysisFiles.HELP:
                
                /* Action HELP clicked. */
                doHelp();
                break;

            default:
                
                /* Event not defined. */
                
                JOptionPane.showMessageDialog(null, "Event not defined:" + eventName + ".", "Message", JOptionPane.INFORMATION_MESSAGE);
                break;
        }

    }
    
    
    
    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void mouseClicked(MouseEvent event) {

        /* Component that generated the event. */
        String component = event.getComponent().getName();
        
        /* Checks one click. */
        if(event.getClickCount() == 1){
            
            /* One click by user. */
            
            switch (component) {

                case "tblReanalysisFiles":
                
                    /* One click on table that shows reanalysis files. */
                   
                    /* Highlights in CYAN color compatibles reanalysis files with selected. */
                    highlightCompatiblesReanalysisFiles();
                    
                    /* Checks if 'Selected' column was clicked to Select / Unselect the file. */
                    checkSelectedColumnClicked();
                    
                    break;
                    
            }
        }        

        
        /* Checks double click. */
        if(event.getClickCount() == 2){
            
            /* Double click by user. */
            
            switch (component) {

                case "tblReanalysisFiles":
                
                    /* Double click on table that shows reanalysis files. */
                    
                    /* Select / unselect file to use in matching. */        
                    doSelectUnselectReanalysisFile();
                    
                    break;
                    
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
       
    }
    

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    
    @Override
    public void mouseEntered(MouseEvent e) {
       
    }


    @Override
    public void mouseExited(MouseEvent e) {
       
    }
            
    
    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void valueChanged(ListSelectionEvent event){
        
        /* To know who generated the event. */
        String objectClass = event.getSource().getClass().getSimpleName();
        
        if (event.getValueIsAdjusting() == false){        
        
            switch (objectClass) {

                case "DefaultListSelectionModel":
                                                
                    /* tblReanalysisFiles */

                    /* A new reanalysis file was selected in tblReanalysisFiles. */

                    /* Highlights in CYAN color compatibles reanalysis files with selected. */
                    highlightCompatiblesReanalysisFiles();
                                                        
                    break;                                                        

            }

        }
    }
    
    
    
    /**
     * Returns reanalysis data files's information.
     * @param datamodel Reanalysis data files's information to update.
     * @param alreadySelectedFiles Already selected reanalysis data files in tab: Matching (ManageBuoys view).
     * @return Reanalysis data files's information.
     */    
    private DefaultTableModel getReanalysisFiles(DefaultTableModel datamodel, ArrayList<String> alreadySelectedFiles){    
        
        /* Gets the information about reanalysis files. */
        
        /* Gets information from database. */        
        getDataFromDatabase(datamodel);
        
        /* Number of rows. */
        int rows=datamodel.getRowCount();
        
        /* Sets 'Selected' colum true if file is already selected. */
        for(int i=0;i<rows;i++){
                                    
            if(alreadySelectedFiles.contains((String)datamodel.getValueAt(i, 1)) == true){                                                
                        
                /* Sets 'Selected' colum to true. */
                datamodel.setValueAt(true, i, 11);                
            
            }else{
                
                /* Sets 'Selected' colum to false. */
                datamodel.setValueAt(false, i, 11);               
            
            }
        
        }        
        
        /* Returns the model with the information. */
        return datamodel;
        
    }
    

    
    /**
     * Updates reanalysis data files's information.
     * @param datamodel Reanalysis data files's information to update.
     */        
    private void getDataFromDatabase(DefaultTableModel datamodel){
        
        /* Gets the information about reanalysis files. */
        
        /* Connects to database and updates the model 
           with the information stored in database. */
        ReanalysisDatabase reanalysisDatabase = new ReanalysisDatabase();
        
        if(reanalysisDatabase.connect()==true){
        
            /* Gets information. */
            reanalysisDatabase.selectData(datamodel);
        
            /* Disconnect from database. */
            reanalysisDatabase.disconnect();
            
        }else{

            JOptionPane.showMessageDialog(null, "There was an error when connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }

        
    
    /**
     * Checks if 'Selected' column was clicked to Select / Unselect the file.
     */
    private void checkSelectedColumnClicked(){
        
        /* Checks if 'Selected' column was clicked to Select / Unselect the file. */
        
        /* Gets selected column. */
        int selectedColumn = getView().getSelectedColumn();
        
        /* Checks user selected a file. */
        if(selectedColumn==0){
            
            /* User clicked on 'Selected' column. */
            doSelectUnselectReanalysisFile();
                
        }        
        
    }
    

    /**
     * Highlights in CYAN color compatibles reanalysis data files with selected.
     */
    private void highlightCompatiblesReanalysisFiles(){
        
        /* Highlights in CYAN color compatibles reanalysis data files with selected. */
        
        /* Gets selected file. */
        int selectedRow = getView().getSelectedRow();
        
        /* Gets model. */
        DefaultTableModel datamodel = getModelReanalysis();

        /* Checks user selected a file. */
        if(selectedRow!=-1){
            
            /* User click on a reanalysis file. */
            
            /* Clear current rows of compatibles reanalysis files. */
            getRowsOfCompatiblesReanalysisFiles().clear();
            
            
            /* 
               Gets information to check if remaining reanalysis files are compatibles
               with selected.            
            */
                        
            /* File name.  */
            String fileName = (String) datamodel.getValueAt(selectedRow, 1);
            
            /* Variable of reanalysis. */
            String variableName = (String) datamodel.getValueAt(selectedRow, 2);
                        
            /* First date in file. */
            Timestamp timeFrom = (Timestamp) datamodel.getValueAt(selectedRow, 5);

            /* Last date in file. */
            Timestamp timeTo = (Timestamp) datamodel.getValueAt(selectedRow, 6);

            /* Variable latitude. */
            String latitudeFrom = (String) datamodel.getValueAt(selectedRow, 7);
            String latitudeTo = (String) datamodel.getValueAt(selectedRow, 8);

            /* Variable longitude. */
            String longitudeFrom = (String) datamodel.getValueAt(selectedRow, 9);
            String longitudeTo = (String) datamodel.getValueAt(selectedRow, 10);
            
            /* Number of rows. */
            int rows=datamodel.getRowCount();
                                    
            /* Gets index of remaining compatibles reanalysis files in datamodel. */
            for(int i=0;i<rows;i++){
                                       
                /* File name. */
                if(!fileName.equals( (String) datamodel.getValueAt(i, 1) ) &&

                    /* Variable of reanalysis. */
                    !variableName.equals( (String) datamodel.getValueAt(i, 2) ) &&

                    /* First date in file. */
                    (timeFrom.compareTo( (Timestamp) datamodel.getValueAt(i, 5)) ==0 ) &&

                    /* Last date in file. */
                    (timeTo.compareTo( (Timestamp) datamodel.getValueAt(i, 6)) ==0) &&

                    /* Variable latitude. */
                    (latitudeFrom.equals( (String) datamodel.getValueAt(i, 7) )) &&
                    (latitudeTo.equals( (String) datamodel.getValueAt(i, 8) )) &&

                    /* Variable longitude. */
                    (longitudeFrom.equals( (String) datamodel.getValueAt(i, 9) )) &&
                    (longitudeTo.equals( (String) datamodel.getValueAt(i, 10) )) ){

                    /* The reanalysis file is compatible. */
                    getRowsOfCompatiblesReanalysisFiles().add(i);
                                    
                }

            } 
            
            /* Repaints table. */
            getView().tblReanalysisFilesRepaint();
            
        }
                        
    }    
    
        

    /**
     * Selects / unselects reanalysis data file to use in matching.
     */
    private void doSelectUnselectReanalysisFile(){
        
        /* Selects / unselects reanalysis data file to use in matching. */
        
        /* Gets selected files to select / unselect. */
        int selectedRow = getView().getSelectedRow();

        /* Checks user selected a file. */
        if(selectedRow==-1){

            /* None file was selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a reanalysis data file.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Selects / unselects file. */
            
            /* Utilities. */
            Utils util = new Utils();
            
            /* Gets file name. */
            String filename = (String) getModelReanalysis().getValueAt(selectedRow, 1);
            
            /* Gets current value. */
            boolean isSelected = (Boolean) getModelReanalysis().getValueAt(selectedRow, 11);            
            
            /* Checks action: Select / Unselect */
            if(isSelected==true){
                
                /* Unselects file. */
                
                /* Gets index to delete. */
                int index=getSelectedReanalysisFiles().indexOf(filename);
                
                /* Changes current value: True -> False */
                getModelReanalysis().setValueAt(false, selectedRow, 11);
                
                /* Removes file from selection. */
                getSelectedReanalysisFiles().remove(index);
                
                /* Deletes reanalysis variable. */
                getSelectedReanalysisVars().remove(index);
                            
            }else{
                
                /* Selects file. */
            
                /* Changes current value: False -> True */
                getModelReanalysis().setValueAt(true, selectedRow, 11);                
                
                /* Adds file to selection. */
                getSelectedReanalysisFiles().add(filename);
                
                /* Adds reanalysis variable. */
                getSelectedReanalysisVars().add((String) getModelReanalysis().getValueAt(selectedRow, 2));
                
            }   
            
            /* One file must be selected at least. */
            if(getSelectedReanalysisFiles().size()>0){
                
                /* Number of geopoints in selected reanalysis files. */                
                int geopoints= util.numberOfGeopoints(getSelectedReanalysisFiles());
                        
                /* Checks compatibility of selected reanalysis files.*/
                if(geopoints==0){
                
                    /* Selected files are not compatibles. */
                    JOptionPane.showMessageDialog(null, "The selected reanalysis data files don't have the same dates and coordinates.\nOr the reanalysis data variable is the same.\n\nPlease check your selected reanalysis data files.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    
                    /* Selection can not be confirmed. */
                    getView().setEnabledConfirmSelection(false);
            
                }else{
                    
                    /* Selected files are compatibles. */                    
                    
                    /* Selection can be confirmed. */
                    getView().setEnabledConfirmSelection(true);
                    
                    /* Sets number of geopoints. */
                    setNumberOfGeopoints(geopoints);
                
                }
            
            }
                        
        }

    }


    
    /**
     * Confirms selection of reanalysis data files made by user.
     */
    private void doConfirmSelection(){

        /* Checks if user selected one file at least. */
        if(getSelectedReanalysisFiles().size()>0){
            
            /* User confirmed selection of reanalysis files. */
            setUserConfirmedSelection(true);
            
            /* Closes the view. */
            getView().closeView();    
        
        }else{
        
            /* User did not select any file. */
            JOptionPane.showMessageDialog(null, "None reanalysis data file was selected.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }        
    
    }


    /**
     * Closes the view.
     */
    private void doBack(){

        /* Closes the view. */
        getView().closeView();
    
    }

    
    /**
     * Shows help.
     */
    private void doHelp(){

        /* Opens pdf file that contains the user manual. */
        
        /* Utilities. */
        Utils util = new Utils();
        
        /* Opens user manual. */
        util.openHelpFile();
        
    }
    
}
