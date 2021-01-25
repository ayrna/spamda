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
import java.util.ArrayList;
import utils.Utils;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import view.interfaces.InterfaceViewSelectBuoyAttributes;


/**
 * This class defines the controller for managing the events generated by the view SelectBuoyAttributes.
 * 
 */
public class ControllerViewSelectBuoyAttributes implements ActionListener, MouseListener {
    
    /**
     * View.
     */
    private final InterfaceViewSelectBuoyAttributes view;
    
    
    /**
     * Information of the attributes of the buoy.
     */        
    private DefaultTableModel modelBuoyVariables;

    
    /**
     * Available attributes of the buoy to select.
     */            
    private ArrayList<String> availableBuoyVariables;
    
        
    /**
     * Selected attributes.
     */            
    private ArrayList<String> selectedBuoyVariables;
            
    
    /**
     * To know if the user confirmed the selection of attributes of the buoy.
     */    
    private boolean userConfirmedSelection;
        
    
    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     * @param view View that this controller will manage.
     * @param datamodel Information of the attributes of the buoy.
     * @param availableVariables Variables of the buoy that are available to select.
     * @param alreadySelectedVariables Already selected attributes of the buoy in tab: Matching (ManageBuoys view).
     */
    public ControllerViewSelectBuoyAttributes(InterfaceViewSelectBuoyAttributes view, DefaultTableModel datamodel, ArrayList<String> availableVariables, ArrayList<String> alreadySelectedVariables){

        /* Sets View */
        this.view = view;
        
        /* Initializes to default values. */
        
        /* To know if user confirmed the selection of the variables of the buoy. */
        this.userConfirmedSelection=false;        

        /* Sets the model of the table that shows the variables of the buoy. */
        this.modelBuoyVariables=getBuoyVariables(datamodel, alreadySelectedVariables, availableVariables);
        this.view.setModelBuoyAttributes(this.modelBuoyVariables);

        /* Sets the available variables of the buoy to select. */
        this.availableBuoyVariables = availableVariables;
        
        /* Sets the selected variables of the buoy by user. */
        this.selectedBuoyVariables = alreadySelectedVariables;
                
    }

    
    /**
     * Returns view.
     * @return View.
     */
    private InterfaceViewSelectBuoyAttributes getView(){
        
        /* Returns view */
        return this.view;
        
    }

    
    /**
     * Returns variables of the buoys.
     * @return Variables of the buoys.
     */
    private DefaultTableModel getModelBuoyVariables(){
        
        /* Returns model */
        return this.modelBuoyVariables;
        
    }

    
    /**
     * Returns true if the user confirmed the selection of the variables of the buoy or False if not.
     * @return True if the user confirmed the selection of the variables of the buoy or False if not.
     */
    public boolean getUserConfirmedSelection(){
        
        /* Gets True if user confirmed the variables selection or False if not. */
        return this.userConfirmedSelection;
        
    }    
        
    
    /**
     * Sets true if user confirmed variables selection or False if not.
     * @param userConfirmedSelection True if user confirmed variables selection or False if not.
     */
    private void setUserConfirmedSelection(boolean userConfirmedSelection){
        
        /* Sets True if user confirmed variables selection or False if not. */
        this.userConfirmedSelection=userConfirmedSelection;
        
    }    
    

    /**
     * Returns available variables of the buoy to select.
     * @return Available variables of the buoy to select.
     */
    public ArrayList<String> getAvailableBuoyVariables(){
        
        /* Gets available variables of the buoy to select. */
        
        return this.availableBuoyVariables;
        
    }

    
    /**
     * Returns selected variables of the buoy by user.
     * @return Selected variables of the buoy by user.
     */
    public ArrayList<String> getSelectedBuoyVariables(){
        
        /* Gets selected variables of the buoy by user. */
        
        return this.selectedBuoyVariables;
        
    }
    
    
    /**
     * Returns number of the variables of the buoy to select.
     * @return Number of the variables of the buoy to select.
     */
    public int numberOfBuoyVariables(){
        
        /* Number of the variables of the buoy to select. */
        return getModelBuoyVariables().getRowCount();
        
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
            
            
            case InterfaceViewSelectBuoyAttributes.CONFIRM_SELECTION:
                
                /* Action CONFIRM_SELECTION clicked. */
                doConfirmSelection();
                break;    

                           
            case InterfaceViewSelectBuoyAttributes.SELECT_ALL:
                
                /* Action SELECT_ALL. */
                doSelectAllVariables();

                break;
                
            case InterfaceViewSelectBuoyAttributes.CLEAR_SELECTION:
                
                /* Action CLEAR_SELECTION. */
                doUnSelectAllVariables();

                break;
                
            case InterfaceViewSelectBuoyAttributes.BACK:
                
                /* Action BACK clicked. */
                doBack();
                break;
                
            case InterfaceViewSelectBuoyAttributes.HELP:
                
                /* Action HELP clicked. */
                doHelp();
                break;
                
            default:
                
                /* Event not defined. */
                
                JOptionPane.showMessageDialog(null, "Event not defined:" + eventName + ".", "Error", JOptionPane.ERROR_MESSAGE);
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

                case "tblBuoyVariables":
                
                    /* One click on table that shows the variables of the buoy. */
                                       
                    /* Checks if 'Selected' column was clicked to Select / Unselect the variable. */
                    checkSelectedColumnClicked();
                    
                    break;
                    
            }
        }        

        
        /* Checks double click. */
        if(event.getClickCount() == 2){
            
            /* Double click by user. */
            
            switch (component) {

                case "tblBuoyVariables":
                
                    /* Double click on table that shows the variables of the buoy. */
                    
                    /* Select / unselect the variable to use in matching. */        
                    doSelectUnselectBuoyVariable();
                    
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
    
    
    
    /* Methods for processing each event (what user clicked on view). */    

    
    /**
     * Returns information of the variables of the buoy.
     * @param datamodel Information of the Variables of the buoy.
     * @param alreadySelectedVariables Already selected variables of the buoy in tab: Matching (ManageBuoys view).
     * @param availableVariables Variables of the buoy that are available to select.
     * @return Variables of the buoy.
     */    
    private DefaultTableModel getBuoyVariables(DefaultTableModel datamodel, ArrayList<String> alreadySelectedVariables, ArrayList<String> availableVariables){
        
        /* Gets the available variables of the buoy. */
        for(String variable: availableVariables){
            
            datamodel.addRow(new Object[]{false, variable});
        
        }
        
        /* Number of rows. */
        int rows=datamodel.getRowCount();
        
        /* Sets 'Selected' column true if the variable is already selected. */
        for(int i=0;i<rows;i++){
                                    
            if(alreadySelectedVariables.contains((String)datamodel.getValueAt(i, 1)) == true){                                                
                        
                /* Sets 'Selected' column to true. */
                datamodel.setValueAt(true, i, 0);                
            
            }else{
                
                /* Sets 'Selected' column to false. */
                datamodel.setValueAt(false, i, 0);               
            
            }
        
        }        
        
        /* Returns the model with the information. */
        return datamodel;
        
    }
    
    
    /**
     * Checks if 'Selected' column was clicked to Select / Unselect the variable.
     */
    private void checkSelectedColumnClicked(){
        
        /* Checks if 'Selected' column was clicked to Select / Unselect the variable. */
        
        /* Gets the selected column. */
        int selectedColumn = getView().getSelectedColumn();
        
        /* Checks if user selected a variable. */
        if(selectedColumn==0){
            
            /* User clicked on 'Selected' column. */
            doSelectUnselectBuoyVariable();
                
        }        
        
    }

    
    /**
     * Selects / unselects the variable of the buoy to use in matching.
     */
    private void doSelectUnselectBuoyVariable(){
        
        /* Selects / unselects the variable of the buoy to use in matching. */
        
        /* Gets selected variable to select / unselect. */
        int selectedRow = getView().getSelectedRow();

        /* Checks if user selected a variable. */
        if(selectedRow==-1){

            /* None variable was selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a variable of the buoy.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Selects / unselects variable. */
                        
            /* Gets the variable. */
            String variable = (String) getModelBuoyVariables().getValueAt(selectedRow, 1);
            
            /* Gets current value. */
            boolean isSelected = (Boolean) getModelBuoyVariables().getValueAt(selectedRow, 0);
            
            /* Checks action: Select / Unselect */
            if(isSelected==true){
                
                /* Unselects the variable. */
                
                /* Gets index to delete. */
                int index=getSelectedBuoyVariables().indexOf(variable);
                
                /* Changes the current value: True -> False */
                getModelBuoyVariables().setValueAt(false, selectedRow, 0);
                
                /* Removes the variable from the current selection. */
                getSelectedBuoyVariables().remove(index);
                                            
            }else{
                
                /* Selects the variable. */
            
                /* Changes the current value: False -> True */
                getModelBuoyVariables().setValueAt(true, selectedRow, 0);
                
                /* Adds the variable to the current selection. */
                getSelectedBuoyVariables().add(variable);                
                
            }   
                                    
        }

    }
    
    
    /**
     * Selects all the variables of the buoy.
     */
    private void doSelectAllVariables(){

        /* Selects all the variables of the buoy. */
        
        /* Clears the current selected variables. */
        getSelectedBuoyVariables().clear();
               
        /* Gets the model. */
        DefaultTableModel datamodel = getModelBuoyVariables();
        
        /* Selects all the variables of the buoy. */
        for(int i=0;i<datamodel.getRowCount();i++){
            
            /* Sets 'Selected' column to true. */
            datamodel.setValueAt(true, i, 0);
            
            /* Adds the current variable of the buoy to the current selection. */
            getSelectedBuoyVariables().add( (String) getModelBuoyVariables().getValueAt(i, 1));
        
        }        
    
    }    
        

    /**
     * Unselects all the variables of the buoy.
     */
    private void doUnSelectAllVariables(){

        /* Unselects all the variables of the buoy. */
        
        /* Clears the current selected variables. */
        getSelectedBuoyVariables().clear();        
               
        /* Gets the model. */
        DefaultTableModel datamodel = getModelBuoyVariables();
        
        /* Unselect all the variables of the buoy. */
        for(int i=0;i<datamodel.getRowCount();i++){
            
            /* Sets 'Selected' column to false. */
            datamodel.setValueAt(false, i, 0);
        
        }                   
    
    }       
        
    
    /**
     * Confirms the selection of the variables of the buoy made by user.
     */
    private void doConfirmSelection(){

        /* User confirmed the current. */
        setUserConfirmedSelection(true);
            
        /* Closes the view. */
        getView().closeView();
    
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
