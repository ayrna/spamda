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
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.interfaces.InterfaceViewManageReanalysisData;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ReanalysisDatabase;
import model.ReanalysisFile;
import utils.Utils;


/**
 * This class defines the controller for managing the events generated by the view ManageReanalysisData.
 * 
 */
public class ControllerViewManageReanalysisData implements ActionListener {
    
    /**
     * View.
     */
    private final InterfaceViewManageReanalysisData view;
    

    /**
     * Reanalysis data files's information.
     */        
    private DefaultTableModel modelReanalysisData;
        
        
    /* Methods of the class */
    
    
    /**
     * Constructor.
     * @param view View that this controller will manage.
     * @param datamodel Reanalysis data files's information.
     */
    public ControllerViewManageReanalysisData(InterfaceViewManageReanalysisData view, DefaultTableModel datamodel){

        /* Sets View */
        this.view = view;
               
        /* Sets the model of the table that will show the information about reanalysis data. */
        this.modelReanalysisData=getDataFromDatabase(datamodel);
        this.view.setModelReanalysisData(this.modelReanalysisData);

    }

    
    /**
     * Return view.
     * @return View.
     */
    private InterfaceViewManageReanalysisData getView(){
        
        /* Returns view */
        return view;
        
    }

    
    /**
     * Sets reanalysis data files's information.
     * @param datamodel Reanalysis data files's information.
     */
    public void setModelReanalysis(DefaultTableModel datamodel){
        
        /* Sets the model */
        
        this.modelReanalysisData = datamodel;
        
    }
        
    
    /**
     * Returns reanalysis data files's information.
     * @return Reanalysis data files's information.
     */
    public DefaultTableModel getModelReanalysis(){
        
        /* Returns model */
        return this.modelReanalysisData;
        
    }



    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void actionPerformed(ActionEvent event){

        /* Manages the event generated in the view. */
        
        /* Gets the event name.*/
        String eventName = event.getActionCommand();
        
        switch (eventName) {

            case InterfaceViewManageReanalysisData.MAIN_MENU:
                
                /* Action MAIN_MENU clicked. */
                doMainMenu();
                break;

                                        
            case InterfaceViewManageReanalysisData.HELP:
                
                /* Action HELP clicked. */
                doHelp();
                break;


            case InterfaceViewManageReanalysisData.ADD_REANALYSIS_FILE:
                
                /* Action ADD_REANALYSIS_FILE clicked. */
                doAddReanalysisFile();
                
                break;
    

            case InterfaceViewManageReanalysisData.DELETE_REANALYSIS_FILE:
                
                /* Action DELETE_REANALYSIS_FILE clicked. */
                doDeleteReanalysisFile();
                                
                break;


            default:
                
                /* Event not defined. */
                
                JOptionPane.showMessageDialog(null, "Event not defined:" + eventName + ".", "Message", JOptionPane.INFORMATION_MESSAGE);
                break;
        }

    }



    /**
     * Returns reanalysis data files's information.
     * @param datamodel Reanalysis data files's information to update.
     * @return Reanalysis data files's information.
     */    
    private DefaultTableModel getDataFromDatabase(DefaultTableModel datamodel){
        
        /* Gets the information about reanalysis data. */
        
        /* Connects to database and updates the model 
           with the information stored in database. */
        ReanalysisDatabase reanalysisDatabase = new ReanalysisDatabase();
        
        if(reanalysisDatabase.connect()==true){

            reanalysisDatabase.selectData(datamodel);
            reanalysisDatabase.disconnect();
            
        }else{
        
            JOptionPane.showMessageDialog(null, "There was an error when connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
            
        }
       
        /* Returns the model with the information. */
        return datamodel;
        
    }


    
    /**
     * Shows a dialog for choosing the reanalysis data file to add.
     */    
    private void doAddReanalysisFile(){
        
        /* Shows a dialog for choosing the NC files to add. */

        /* Dialog. */
        javax.swing.JFileChooser txtFileChooser = new javax.swing.JFileChooser();
        
        /* Enables multiselect files. */
        txtFileChooser.setMultiSelectionEnabled(true);
        
        /* NC, nc file extension filter. */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("NC Files", "NC", "nc");

        /* Path for searching NC files. */
        String path = System.getProperty("user.dir", ".");
              
        /* Sets path and filter to the dialog. */
        txtFileChooser.setCurrentDirectory(new File(path));
        txtFileChooser.setFileFilter(filter);  
                
        /* Shows dialog and waits for user selection. */       
        int returnVal = txtFileChooser.showOpenDialog(null);

        /* Checks user selection. */
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            /* The user selected a/several files. */
            
            /* Add the selected file/s. */

            addSelectionFiles(txtFileChooser.getSelectedFiles());

        }        

    }

    

    /**
     * Deletes selected reanalysis data file.
     */        
    private void doDeleteReanalysisFile(){
        
        /* Deletes selected reanalysis file. */
        
        /* Gets the row of the file to be deleted. */
        int selectedRow = getView().getSelectedRow();

        if(selectedRow==-1){
            
            JOptionPane.showMessageDialog(null, "Please, select a reanalysis data file to delete.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{

            /* Name of the file to delete. */
            String filename = (String) getModelReanalysis().getValueAt(selectedRow, 1);
            
            /* ID of the file to delete from database. */
            int id = (int) getModelReanalysis().getValueAt(selectedRow, 0);
            
                        
            /* Ask the user that really want to delete the selected file. */                
                
            Object[] options = {"Cancel", "Delete"};

            if (JOptionPane.showOptionDialog(null, "Attention !!\nThe information of the reanalysis data file "+filename+" will be erased.\n"
                    +"¿Do you to want to delete it?\n\n","Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1){

                /* Connects to database. */
                ReanalysisDatabase reanalysisDatabase = new ReanalysisDatabase();
                
                if(reanalysisDatabase.connect()==true){
                
                    /* Deletes file from database. */
                    if(reanalysisDatabase.deleteReanalysisFile(id) == true){

                        /* Deletes file from disk. */

                        /* Gets the path of the reanalysis file to delete. */                
                        String pathFileToDelete = System.getProperty("user.dir", ".")
                              +File.separator+"DB"+File.separator+"reanalysisFiles"+File.separator+filename;

                        /* Gets the file to delete. */
                        File file = new File(pathFileToDelete);

                        /* Deletes the file. */
                        if (file.delete() == true){

                            /* The file was properly deleted. */

                            /* Updates de the model. */
                            getModelReanalysis().removeRow(selectedRow);

                            JOptionPane.showMessageDialog(null, "The reanalysis data file "+filename+" was properly deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);

                        }else{

                            JOptionPane.showMessageDialog(null, "The reanalysis data file "+filename+" could not be deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }

                    /* Disconnects from database. */
                    reanalysisDatabase.disconnect();
                    
                }else{

                    JOptionPane.showMessageDialog(null, "There was an error when connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }
        }
        
    }
                
    
    
    /**
     * Closes the view.
     */            
    private void doMainMenu(){
        
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
    
    
    
    /**
     * Adds the selected reanalysis data files by user.
     * @param filesPathSelected Reanalysis data files selected by user.
     */    
    private void addSelectionFiles(File[] filesPathSelected){
        
        /* Adds the selected file/s by user. */
        
        /* To show all files copied in a message. */
        String filesCopied = "";
        
        for (File file : filesPathSelected) {
            
            /* Selected file. */
            String filename=file.getName();
            
            /* Checks if file exists. */            
            if (file.exists()==true){
            
                if(filename.toLowerCase().endsWith(".nc")){

                    /* Selected file has .NC extension. */

                    /* Copy the selected file by user. */

                    if(checkSelectedFile(file)==true && addSelectedFile(file)==true){

                        /* Selected file was copied and inserted in database. */

                        filesCopied = filesCopied + "      " + filename + "\n";

                    }else{

                        /* Selected file could not be copied. */
                        
                        JOptionPane.showMessageDialog(null, "The reanalysis data file "+ filename +" could not be copied.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }else{

                    /* Selected file has not .NC extension. */

                    JOptionPane.showMessageDialog(null, "The reanalysis data file " + filename + " has not NC extension.", "Warning", JOptionPane.WARNING_MESSAGE);

                }        
            }else{
            
                /* Selected file does not exist. */

                JOptionPane.showMessageDialog(null, "The reanalysis data file " + filename + " does not exist.", "Warning", JOptionPane.WARNING_MESSAGE);
            
            }
        }  
        
        if(filesCopied.isEmpty()==false){
            
            JOptionPane.showMessageDialog(null, "The following reanalysis data files:\n\n"+filesCopied+"\nhave been inserted in database.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }        
        
    }

    
    /**
     * Checks that the received reanalysis data file has three dimensions (time, lat, lon).
     * @param source Reanalysis data file to check.
     * @return True if the received reanalysis data file has three dimensions or False if not.
     */    
    private boolean checkSelectedFile(File source){
        
        /* Checks that the received file has three dimensions. */
        
        /* To check if file has three dimensiones. */
        boolean result = true;
        
        /* Name of the file to check. */
        String fileName = source.getName();
        
        /* Number of dimensions. */
        int numDimensions;
        
        /* The reanalysis file. */
        ReanalysisFile rf = new ReanalysisFile();

        /* Checks if the reanalysis file was properly opened. */
        if (rf.openFile(source.getPath())==true){

            /* Gets the number of dimensions. */
            numDimensions=rf.getReanalysisVariableNumDimensions();

            /* Closes reanalysis file. */
            rf.closeFile();

            /* Checks number of dimensions. */
            if(numDimensions!=3){

                /* 
                    The reanalysis data file can not be copied because it 
                    has not three dimensions.
                */

                JOptionPane.showMessageDialog(null, "The reanalysis data file: " +fileName+ " can not be copied because it has not three dimensions.", "Warning", JOptionPane.WARNING_MESSAGE);

                result=false;

            }
        }else{
            
            /* The file could not be opened. */
            result=false;
        
        }
        
        return result;
        
    }    
    
    
    /**
     * Inserts selected reanalysis data file in database.
     * @param source Reanalysis data file to add.
     * @return True if selected reanalysis data file was properly inserted in database or False if not.
     */    
    private boolean addSelectedFile(File source){
        
        /* Copies and inserts in database the received file. */
        
        /* To check if file was properly copied and inserted in database. */
        boolean result = false;
        
        /* Name of the file to be copied. */
        String fileName = source.getName();
        
        /* To know if the file was copied or overwritted. */
        String action = "";
                
        /* Path of the file to be copied. */
        String pathTargetFile = System.getProperty("user.dir", ".")
                      +File.separator+"DB"+File.separator+"reanalysisFiles"+File.separator+fileName;
                                
        /* Gets target file. */
        File target = new File(pathTargetFile);
        
        /* Row of the file, if already exists, in datamodel. */
        int row=-1;
        
        /* TableModel that contains the information about reanalysis files in database. */
        DefaultTableModel model = getModelReanalysis();
                
        try {
            
            /* Checks if file already exits. */    
            
            if(target.exists()){
                
                Object[] options = {"Cancel", "Overwrite"};
            
                if (JOptionPane.showOptionDialog(null, "Attention !!\nThe selected reanalysis data file "+fileName+" already exists.\n"
                        +"¿Do you to want to overwrite it?\n\n","Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1){
                
                    /* Overwrite the file. */
                    action="Overwrite";
                    Files.copy(source.toPath(), target.toPath(), REPLACE_EXISTING);                                    
                    
                    
                    /* Gets the row of the file to update in datamodel. */
                    
                    
                    /* Number of reanalysis files in datamodel. */
                    int numReanalysisFiles = model.getRowCount();

                    /* Checks the file name. */
                    for(int i=0;(i<numReanalysisFiles && row==-1);i++){

                        if ( ((String) model.getValueAt(i, 1)).equalsIgnoreCase(fileName)) {

                            /* Row to update. */
                            row=i;

                        }        
                    }

                    /* Success. */
                    result=true;      
                    
                }
            }else{
                
                Files.copy(source.toPath(), target.toPath());
                
                action="Copy";
                
                /* Success. */
                result=true;
                
            }
            
            if(result==true){
                
                /* The file was overwritted or copied. */
                
                /* Utilities. */
                Utils util = new Utils();

                
                /* 
                    Opens the reanalysis file and gets the summary information
                    for being inserted in database.
                */
                                
                ReanalysisFile rf = new ReanalysisFile();
                if(rf.openFile(target.getPath())==true){

                        /* Variable of reanalysis. */
                        String variable=rf.getReanalysisVariableName();

                        /* Variable time. */                    
                        int numInstances = rf.getSizeVariable("time");


                        /* 
                           NetCDF establishes 1800-01-01 as date base, so it is necessary to 
                           convert to Unix seconds.
                        */


                        /* First date in file. */
                        String timeFrom = util.unixSecondsToTimeStamp(util.secondsFrom1800ToUnix(rf.getFirstDataVariableTime()));

                        /* Last date in file. */
                        String timeTo = util.unixSecondsToTimeStamp(util.secondsFrom1800ToUnix(rf.getLastDataVariableTime()));

                        /* Variable latitude. */
                        float latitudeFrom = rf.getFirstDataVariable("lat");
                        float latitudeTo = rf.getLastDataVariable("lat");

                        /* Variable longitude. */
                        float longitudeFrom = rf.getFirstDataVariable("lon");
                        float longitudeTo = rf.getLastDataVariable("lon");

                        /* Number of geopoints. */                    
                        int numGeopoints = rf.getSizeVariable("lat") * rf.getSizeVariable("lon");

                    /* Closes reanalysis file. */
                    rf.closeFile();


                    /* Connects to database. */
                    ReanalysisDatabase reanalysisDatabase = new ReanalysisDatabase();
                    
                    if(reanalysisDatabase.connect()==true){

                        /* Checks if reanalysis file was copied or overwritted. */
                        if(action.equals("Copy")==true){

                            /* The reanalysis file was copied. */

                            /* ID of the new reanalysis file in database. */
                            int id;

                            /* Inserts new reanalysis file into database. */
                            id=reanalysisDatabase.insertReanalysisFile(fileName, variable, numInstances, numGeopoints, timeFrom, timeTo, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo);

                            if(id==-1){

                                /* There was an error when inserting reanalysis file in database. */

                                JOptionPane.showMessageDialog(null, "The new reanalysis data file was not inserted in database.", "Error", JOptionPane.ERROR_MESSAGE);

                                result=false;

                            }else{

                                /* Updates the model with the new reanalysis file. */

                                model.addRow(new Object[]{id, fileName, variable, numInstances, numGeopoints, timeFrom, timeTo, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo});

                            }

                        }else{

                            /* The reanalysis file was overwritted. */

                            /* Updates new reanalysis file into database. */
                            result=reanalysisDatabase.updateReanalysisFile(fileName, variable, numInstances, numGeopoints, timeFrom, timeTo, latitudeFrom, latitudeTo, longitudeFrom, longitudeTo);                    

                            if(result==false){

                                /* There was an error when updating reanalysis file in database. */

                                JOptionPane.showMessageDialog(null, "The new reanalysis data file was not updated in database.", "Error", JOptionPane.ERROR_MESSAGE);

                                result=false;

                            }else{

                                /* Updates the model with the new reanalysis file. */

                                model.setValueAt(variable, row, 2);
                                model.setValueAt(numInstances, row, 3);
                                model.setValueAt(numGeopoints, row, 4);
                                model.setValueAt(timeFrom, row, 5);
                                model.setValueAt(timeTo, row, 6);
                                model.setValueAt(latitudeFrom, row, 7);
                                model.setValueAt(latitudeTo, row, 8);
                                model.setValueAt(longitudeFrom, row, 9);
                                model.setValueAt(longitudeTo, row, 10);

                            }                    

                        }

                        /* Disconnects from database. */
                        reanalysisDatabase.disconnect();            
                    
                    }else{

                        JOptionPane.showMessageDialog(null, "There was an error when connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);

                    }
               
                }else{
                
                    /* The file could not be opened. */
                    result=false;
                    
                    /* Deletes the file because it was properly copied. */
                    target.delete();
                
                }
            
            }
                     
        } catch (IOException ex) {
            
            Logger.getLogger(ControllerViewManageReanalysisData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
    }

}
