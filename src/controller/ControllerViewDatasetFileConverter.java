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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import utils.Utils;
import model.BuoyDatabase;
import view.interfaces.InterfaceViewDatasetFileConverter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.DatasetFile;
import model.PreprocessDatasetFile;
import xml.DatasetInformation;
import xml.XMLFile;


/**
 * This class defines the controller for managing the events generated by the view DatasetFileConverter.
 * 
 */
public class ControllerViewDatasetFileConverter implements ActionListener, ListSelectionListener {    
    
    /**
     * View.
     */
    private final InterfaceViewDatasetFileConverter view;
    
    
    /**
     * Dataset files's names that belong to the selected buoy.
     */        
    private DefaultListModel<String> modelDatasetFiles;
    
    
    /**
     * Path of each dataset file that belongs to the selected buoy.
     */            
    private ArrayList<String> pathDatasetFiles;
        
    
    /**
     * Preprocessed dataset file's names that belong to the selected dataset and buoy.
     */            
    private DefaultListModel<String> modelPreprocessedDatasetFiles;
    
    
    /**
     * Path of each preprocessed dataset file of the selected dataset file and buoy.
     */                    
    private ArrayList<String> pathPreprocessedDatasetFiles;
    
    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     * @param view View that this controller will manage.
     * @param datamodel Buoys's information.
     */
    public ControllerViewDatasetFileConverter(InterfaceViewDatasetFileConverter view, DefaultTableModel datamodel){

        /* Sets View */
        this.view = view;
        
        /* Sets the model of the table that will show the information about buoys. */
        this.view.setModelBuoys(getDataFromDatabase(datamodel));
        
        
        /* Initializes to default values. */
        this.pathDatasetFiles=new ArrayList<>();
        this.modelDatasetFiles=new DefaultListModel<>();
        
        this.pathPreprocessedDatasetFiles=new ArrayList<>();
        this.modelPreprocessedDatasetFiles=new DefaultListModel<>();
                
    }

    
    /**
     * Returns view.
     * @return View.
     */
    private InterfaceViewDatasetFileConverter getView(){
        
        /* Returns view */
        return this.view;
        
    }

    
    /**
     * Returns path of each dataset file that belongs to the selected buoy.
     * @return Path of each dataset file that belongs to the selected buoy.
     */
    public ArrayList<String> getPathDatasetFiles(){
    
        /* Gets path of each dataset file that belongs to the selected buoy. */
        
        return this.pathDatasetFiles;

    }

    
    /**
     * Returns dataset files's names that belong to the selected buoy.
     * @return Dataset files's names that belong to the selected buoy.
     */
    public DefaultListModel<String> getModelDatasetFiles(){
    
        /* Gets the model of the list that shows the dataset filenames that belong to the selected buoy. */
        
        return this.modelDatasetFiles;

    }
    
    
    /**
     * Returns path of each preprocessed dataset file of the selected dataset file and buoy.
     * @return Path of each preprocessed dataset file of the selected dataset file and buoy.
     */
    public ArrayList<String> getPathPreprocessedDatasetFiles(){
    
        /* Gets path of each preprocessed dataset file of the selected dataset file and buoy. */
        
        return this.pathPreprocessedDatasetFiles;

    }

    
    /**
     * Returns preprocessed dataset files's names that belong to the selected dataset and buoy.
     * @return Preprocessed dataset files's names that belong to the selected dataset and buoy.
     */
    public DefaultListModel<String> getModelPreprocessedDatasetFiles(){
    
        /* Gets the model of the list that shows the preprocessed dataset filenames
           that belong to the selected dataset and buoy. */
        
        return this.modelPreprocessedDatasetFiles;

    }    
    

    
    /**
     * Returns selected id Buoy by user to create preprocessed dataset files.
     * @return Selected id Buoy by user to create preprocessed dataset files.
     */
    public int getSelectedIdBuoy(){
    
        /* Gets the selected id Buoy by user to create preprocessed dataset files. */
        
        /* Selected row. */
        int selectedRow = getView().getSelectedRowBuoy();
        
        /* Selected id Buoy. */
        int idBuoy = (int) getView().getModelBuoys().getValueAt(selectedRow, 0);
        
        return idBuoy;
        
    }


    /**
     * Returns selected Station ID by user to create preprocessed dataset files.
     * @return Selected Station ID by user to create preprocessed dataset files.
     */
    public String getSelectedStationID(){
    
        /* Gets the selected Station ID Buoy by user to create preprocessed dataset files. */
        
        /* Selected row. */
        int selectedRow = getView().getSelectedRowBuoy();
        
        /* Selected id Buoy. */
        String stationID = (String) getView().getModelBuoys().getValueAt(selectedRow, 1);
        
        return stationID;
        
    }

        

    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void valueChanged(ListSelectionEvent event){        
        
        /* To know if event was generated in jlstDatasetFiles */
        String objectClass = event.getSource().getClass().getSimpleName();
        
        if (event.getValueIsAdjusting() == false){        
        
            switch (objectClass) {

                case "DefaultListSelectionModel":

                    /*  tblBuoys */
                        
                    /* A new buoy was selected in tblBuoysDatasets. */                        
                
                    /* Shows dataset files that belong to the selected buoy. */
                    buoySelectionChanged();
                                                               
                    break;
                    
                case "JList":
                            
                    if(event.getSource().toString().contains("jlstDatasetFiles")){
                                               
                        /* jlstDatasetFiles */
                    
                        /* A new dataset file was selected by user. */
                    
                        /* Shows preprocessed files of the selected dataset file. */
                        datasetSelectionChanged();
                    }

                    break;

            }
        }        
        
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
                           
            case InterfaceViewDatasetFileConverter.CONVERT_DATASET_TO_ARFF:
                
                /* Action CONVERT_DATASET_TO_ARFF. */
                doConvertDatasetFile("Dataset","ARFF");
                
                break;
                
            case InterfaceViewDatasetFileConverter.CONVERT_DATASET_TO_CSV:
                
                /* Action CONVERT_DATASET_TO_CSV. */
                doConvertDatasetFile("Dataset","CSV");

                break;
                
            case InterfaceViewDatasetFileConverter.CONVERT_PREPROCESSED_TO_ARFF:
                
                /* Action CONVERT_PREPROCESSED_TO_ARFF. */
                doConvertDatasetFile("Preprocessed","ARFF");

                break;
                
            case InterfaceViewDatasetFileConverter.CONVERT_PREPROCESSED_TO_CSV:
                
                /* Action CONVERT_PREPROCESSED_TO_CSV. */
                doConvertDatasetFile("Preprocessed","CSV");

                break;                
                
            case InterfaceViewDatasetFileConverter.MAIN_MENU:
                
                /* Action MAIN_MENU clicked. */
                doMainMenu();
                
                break;
                
            case InterfaceViewDatasetFileConverter.HELP:
                
                /* Action HELP clicked. */
                doHelp();
                
                break;
                
            default:
                
                /* Event not defined. */
                
                JOptionPane.showMessageDialog(null, "Event not defined:" + eventName + ".", "Error", JOptionPane.ERROR_MESSAGE);
                
                break;
        }

    }

    
    
    /* Methods for processing each event (what user clicked on view). */    

    
    
    /**
     * Updates dataset filenames when user selects a new buoy.
     */        
    private void buoySelectionChanged(){
        
        /* The user changed buoy selection. */
        
        /* Row selected in tablemodel. */        
        int selectedRow = getView().getSelectedRowBuoy();
        
        if(selectedRow!=-1){
            
            /* Id of the buoy selected. */
            int idBuoy = (int) getView().getModelBuoys().getValueAt(selectedRow, 0);

            /* Utilities. */
            Utils util = new Utils();

            /* A new buoy was selected. */
            
            /* Clear intermeidate datasets. */
            getModelDatasetFiles().clear();
            getPathDatasetFiles().clear();
            
            /* Clear pre-processed datasets. */
            getModelPreprocessedDatasetFiles().clear();
            getPathPreprocessedDatasetFiles().clear();
            
            /* Search for the .db dataset files that belong to the buoy. */
            util.searchFiles(idBuoy, getModelDatasetFiles(), new ArrayList<>(Arrays.asList("Datasets")), getPathDatasetFiles(), new ArrayList<>(Arrays.asList("DB Files", "DB", "db")));
            

            /* Updates in view the dataset files that belong to the buoy. */
            getView().setModelDatasets(getModelDatasetFiles());
        
        }
    }
    
    
    
    /**
     * Updates preprocessed dataset filenames when user selects a new dataset.
     */        
    private void datasetSelectionChanged(){
    
        /* Shows preprocessed dataset files that belong to the selected dataset file. */
        
        /* Gets the dataset file. */
        int selectedIndex = getView().getSelectedIndexDataset();
        
        if(selectedIndex != -1){
                       
            /* Gets the name of the selected dataset file. */
            String filename = getModelDatasetFiles().get(selectedIndex);
            filename = filename.substring(0, filename.length() - 3);

           
            /* Utilities. */
            Utils util = new Utils();

            /* A new dataset file was selected. */

            /* Preprocess files */               
            getModelPreprocessedDatasetFiles().clear();
            getPathPreprocessedDatasetFiles().clear();
            
            /* Selected buoy. */
            int idBuoy = getSelectedIdBuoy();

            /* Search for the .db Preprocess files that belong to the buoy. */        
            util.searchFiles(idBuoy, getModelPreprocessedDatasetFiles(), new ArrayList<>(Arrays.asList("Preprocess"+File.separator+filename)), getPathPreprocessedDatasetFiles(), new ArrayList<>(Arrays.asList("DB Files", "DB", "db")));

            /* Updates in view the dataset files that belong to the selected dataset file. */
            getView().setModelPreprocessedDatasets(getModelPreprocessedDatasetFiles());

        }
    }
    


    /**
     * Converts a dataset or preprocessed dataset file to ARFF / CSV format.
     * 
     * @param typeOfDataset Type of the input file: Dataset or Preprocessed dataset.
     * @param typeOfConvertion Output file format: ARFF or CSV.
     */            
    private void doConvertDatasetFile(String typeOfDataset, String typeOfConvertion){

        /* Selected index dataset file. */        
        int selectedIndex;                
        
        /* Path of the dataset file to convert. */
        String selectedPathDatasetFile="";
        
        /* Dataset file name to convert. */
        String selectedDatasetFilename="";
                
        
        
        
        /* Gets name and path of selected dataset/preprocessed dataset to convert. */
        
        
        
        if(typeOfDataset.equals("Dataset")==true){
            
            /* Gets path of selected dataset. */
            
            selectedIndex = getView().getSelectedIndexDataset();
                
            if(selectedIndex!=-1){
                        
                /* User selected a dataset file. */
                
                selectedPathDatasetFile = getPathDatasetFiles().get(selectedIndex);
                selectedDatasetFilename = getModelDatasetFiles().get(selectedIndex);
                
            }else{

                /* None dataset file was selected. */
                
                JOptionPane.showMessageDialog(null, "Please, select an intermediate dataset to convert.", "Message", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        }else{
        
            /* Gets path of selected preprocessed dataset. */
            
            selectedIndex = getView().getSelectedIndexPreprocessedDataset();
                
            if(selectedIndex!=-1){
                    
                /* User selected a preprocessed dataset file. */
                    
                selectedPathDatasetFile = getPathPreprocessedDatasetFiles().get(selectedIndex);
                selectedDatasetFilename = getModelPreprocessedDatasetFiles().get(selectedIndex);
                
            }else{
            
                /* None preprocessed dataset file was selected. */                
                
                JOptionPane.showMessageDialog(null, "Please, select a pre-processed dataset to convert.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            }            
            
        }

        if(selectedIndex!=-1){
            
            /* User selected a dataset file to convert. */

            /* Ask user the name of the target file. */            
            File targetFileName=askUserTargetFilename(typeOfConvertion);                        
                        
            if(targetFileName!=null){            
                
                /* The user selected a file name. */                        
                
                /* Dataset file to load. */
                DatasetFile datasetFile = new DatasetFile();

                /* Preprocess dataset file to store the information contained in the dataset file. */
                PreprocessDatasetFile outputFile = new PreprocessDatasetFile();

                /* Information to show about the dataset file. */
                DatasetInformation datasetInfoLoaded = getDatasetXMLInformation(selectedPathDatasetFile);

                /* Gets the dataset file selected by user and reads it. */
                datasetFile.setFileName(selectedPathDatasetFile);

                if (datasetFile.readFile(typeOfDataset, datasetInfoLoaded.getHeaderDatasetFile()) == true){

                    /* Converts to Weka format. */
                    outputFile.setDatasetFile(datasetFile);
                    
                    /* Sets relation name. */
                    outputFile.getInstancesWEKA().setRelationName(targetFileName.getName());

                    if(typeOfConvertion.equals("ARFF")==true){

                        /* User selected ARFF convertion. */                                                
                        
                        if (outputFile.getInstancesWEKA().convertInstancesToARFFFile(targetFileName)==true){

                            JOptionPane.showMessageDialog(null, "The file "+ targetFileName.getName() +" was properly created.", "Message", JOptionPane.INFORMATION_MESSAGE);

                        }else{

                            /* There was an error while converting the selected dataset file. */

                            JOptionPane.showMessageDialog(null, "The selected dataset "+ selectedDatasetFilename +" could not be converted to ARFF format.", "Error", JOptionPane.ERROR_MESSAGE);

                        }

                    }else{

                        /* User selected CSV convertion. */

                        if (outputFile.getInstancesWEKA().convertInstancesToCSVFile(targetFileName)==true){

                            JOptionPane.showMessageDialog(null, "The file "+ targetFileName.getName() +" was properly created.", "Message", JOptionPane.INFORMATION_MESSAGE);

                        }else{

                            /* There was an error while converting the selected dataset file. */

                            JOptionPane.showMessageDialog(null, "The selected dataset "+ selectedDatasetFilename +" could not be converted to CSV format.", "Error", JOptionPane.ERROR_MESSAGE);

                        }                    
                    }

                }else{

                    /* There was an error while reading the selected dataset file. */

                    JOptionPane.showMessageDialog(null, "The selected dataset "+ selectedDatasetFilename +" could not be readed.", "Error", JOptionPane.ERROR_MESSAGE);
                }                                    
            }
        }
    }
    
    
    
    /**
     * Shows a dialog for asking the user the target file.
     * 
     * @param typeOfConvertion Output file format: ARFF or CSV.
     * @return Selected file by user.
     */
    private File askUserTargetFilename(String typeOfConvertion){
        
        /* Shows a dialog for asking the user the target file. */
        
        /* Utilities. */
        Utils util = new Utils();
        
        /* Target file selected by user. */
        File targetFile=null;
        
        /* Target file name selected by user. */
        String targetFilename;

        /* Dialog. */
        javax.swing.JFileChooser txtFileChooser = new javax.swing.JFileChooser();
                      
        /* ARFF / CSV file extension filter. */
        FileNameExtensionFilter filter;
        
        if(typeOfConvertion.equals("ARFF")==true){
            
            /* ARFF convertion. */
            
            filter = new FileNameExtensionFilter("ARFF Files", "ARFF", "arff");        
            
        }else{
            
            /* CSV convertion. */
            
            filter = new FileNameExtensionFilter("CSV Files", "CSV", "csv");
        
        }

        /* Path for target file name. */
        String path = System.getProperty("user.dir", ".");
              
        /* Sets path and filter to the dialog. */
        txtFileChooser.setCurrentDirectory(new File(path));
        txtFileChooser.setFileFilter(filter);  
                
        /* Shows dialog and waits for user selection. */       
        int returnVal = txtFileChooser.showSaveDialog(null);

        /* Checks user selection. */
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            /* The user selected a file. */
            
            targetFile = txtFileChooser.getSelectedFile();
            targetFilename=targetFile.getName();
            
            if(util.isValidFilename(targetFilename)==false){
                
                /* Error. File name is not valid. */
                
                targetFile=null;
            
                JOptionPane.showMessageDialog(null, "File name not valid. Please, check it has valid characters and no extension.", "Message", JOptionPane.INFORMATION_MESSAGE);                                
                            
            }else{
                
                /* Gets file name with extension. */
                targetFilename=targetFile.getPath()+"."+typeOfConvertion.toLowerCase();
                
                /* Checks is already exists. */
                targetFile = new File(targetFilename);
                
                if(targetFile.exists()){
                    
                    /* The file already exists. */
                    
                    Object[] options = {"Cancel", "Overwrite"};

                    if (JOptionPane.showOptionDialog(null, "File "+targetFile.getName()+" already exists.\n"
                            +"¿Do you to want to overwrite it?\n\n","Warning",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==0){

                        /* User clicked on Cancel. */

                        targetFile=null;
                        
                        JOptionPane.showMessageDialog(null, "Operation cancelled.", "Message", JOptionPane.INFORMATION_MESSAGE);                        

                    }
                }            
            }
        }
                        
        return targetFile;
        
    }
    
        
    
    /**
     * Gets the information in .XML file of the selected dataset file.
     * 
     * @param filename Dataset file name.
     * @return Information in .XML file of the selected dataset file.
     */            
    private DatasetInformation getDatasetXMLInformation(String filename){
    
        /* Gets the information in .XML file of the dataset file selected. */
        
        /* Information about the dataset file. */
        DatasetInformation datasetInfo = null;
              
        /* Gets the name of the XML file. */
        filename = filename.substring(0, filename.length() - 3) + ".xml";
                        
        /* Reads the xml file. */
        File xml = new File(filename);
                       
        if(xml.exists()){

            /* Gets XML file. */
            XMLFile<DatasetInformation> xmlFile = new XMLFile<>(DatasetInformation.class, xml);

            /* Gets information of the dataset file received. */
            datasetInfo = xmlFile.readXMLFile();
               
        }
        
        return datasetInfo;
                
    }

        
    
    /**
     * Returns buoy's information from database.
     * 
     * @param datamodel Buoy's information to update.
     * @return Buoy's information from database.
     */    
    private DefaultTableModel getDataFromDatabase(DefaultTableModel datamodel){
        
        /* Gets the information about buoys. */
        
        /* Connects to database and updates the model 
           with the information stored in database. */
        BuoyDatabase buoyDatabase = new BuoyDatabase();
                
        if(buoyDatabase.connect()==true){
            
            buoyDatabase.selectData(datamodel);
            buoyDatabase.disconnect();
            
        }else{

            JOptionPane.showMessageDialog(null, "There was an error when connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);

        }
            
        
        /* Returns the model with the information. */
        return datamodel;
        
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
    
}
