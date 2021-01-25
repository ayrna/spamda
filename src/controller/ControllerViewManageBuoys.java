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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BuoyDatabase;
import view.BuoyData;
import view.interfaces.InterfaceViewBuoyData;
import view.interfaces.InterfaceViewManageBuoys;
import view.OpenDatasetFile;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import utils.Utils;
import view.interfaces.InterfaceViewNewDatasetFile;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import view.NewDatasetFile;
import view.interfaces.InterfaceViewNewPreprocessedDatasetFile;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import model.DatasetFile;
import model.PreprocessDatasetFile;
import view.NewPreprocessedDatasetFile;
import weka.core.Instances;
import xml.DatasetInformation;
import xml.XMLFile;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RemoveByName;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;
import weka.filters.unsupervised.instance.RemoveDuplicates;
import weka.filters.unsupervised.instance.RemoveWithValues;
import weka.filters.unsupervised.instance.SubsetByExpression;
import weka.gui.GenericObjectEditor;
import weka.gui.PropertyDialog;
import view.interfaces.InterfaceViewOpenDatasetFile;
import model.RecoveringFilters;
import view.NewMatchingFile;
import view.LoadMatchingFile;
import view.RunMatching;
import xml.MatchingInformation;
import view.interfaces.InterfaceViewNewMatchingFile;
import view.SelectReanalysisFiles;
import view.interfaces.InterfaceViewSelectReanalysisFiles;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import model.MatchingFinalDataset;
import utils.WekaExplorer;
import view.SelectBuoyAttributes;
import view.interfaces.InterfaceViewLoadMatchingFile;              
import weka.core.Attribute;
import weka.core.Instance;
import view.interfaces.InterfaceViewSelectBuoyAttributes;


/**
 * This class defines the controller for managing the events generated by the view ManageBuoys.
 * 
 */
public class ControllerViewManageBuoys implements ActionListener, ListSelectionListener, MouseListener,
                                                  TreeSelectionListener, ChangeListener, FocusListener {
    
    /**
     * View.
     */
    private final InterfaceViewManageBuoys view;
    
    
    /* Tab: Buoys */

    
    /**
     * Buoys's information.
     * 
     * Each component of this view will use this model to show information buoys's information.
     */
    private DefaultTableModel modelBuoys;


    
    /* Tab: Datasets & Preprocess */

    
    /**
     * Selected buoy to create preprocessed dataset files.
     */
    private int selectedIdBuoy;

    
    /**
     * Selected original (source) dataset file's path to create preprocessed dataset files.
     */
    private String pathOfselectedOriginalDataset;
    
    
    /**
     * Selected Dataset/preprocessed dataset file's path to preprocess.
     */
    private String pathOfselectedDatasetToPreprocess;

    
    /**
     * Dataset files's names that belong to the selected buoy.
     */
    private DefaultListModel<String> modelDatasetFiles;
    
    
    /**
     * Dataset files's path that belong to the selected buoy.
     */
    private ArrayList<String> pathDatasetFiles;

    
    /**
     * Preprocessed dataset files's names that belong to the selected dataset file.
     */
    private DefaultListModel<String> modelPreprocessedDatasetFiles;

    
    /**
     * Preprocessed dataset files's path that belong to the selected dataset file.
     */
    private ArrayList<String> pathPreprocessedDatasetFiles;

    
    /**
     * Data contained in the dataset/preprocessed dataset file loaded to work with.
     */
    private PreprocessDatasetFile preprocessDataset;

    
    /**
     * Summary information about the selected dataset file loaded in memory.
     */
    private DatasetInformation datasetInformationLoaded;

    
    /**
     * Indicates if user is preprocessing a dataset/preprocessed dataset file.
     */
    private boolean preprocessingStarted;
    
    
    /**
     * Indicates if data contained in tab 'View data' has to be reloaded because 
     * has changed since last time it was showed.
     */
    private boolean reloadViewData;
    
    
    /**
     * Configuration (number of hour to use) of each recovering filter.
     */
    private ArrayList<Integer> recoveringFiltersConfiguration;
    
    
    
    /* Tab: Matching configuration. */
    
    
    /**
     * Selected buoy to use in matching process.
     */
    private int idBuoyToMatching;

    
    /**
     * Latitude North of the selected buoy.
     */
    private double latitudeNorth;

    
    /**
     * Longitude East of the selected buoy.
     */
    private double longitudeEast;
    
    
    /**
     * Dataset files's names that belong to the selected buoy to use in matching process.
     */
    private DefaultListModel<String> modelMatchingDatasetFiles;
    
    
    /**
     * Dataset files's path that belong to the selected buoy to use in matching process.
     */
    private ArrayList<String> pathMatchingDatasetFiles;
    
    
    /**
     * Type of the selected datase file 'Dataset/Preprocessed' to use in matching process.
     */    
    private String typeOfSelectedDatasetToMatching;

    
    /**
     * Preprocessed dataset files's names that belong to the selected dataset file.
     */    
    private DefaultListModel<String> modelMatchingPreprocessedDatasetFiles;
    
    
    /**
     * Preprocessed dataset files's path that belong to the selected dataset file.
     */
    private ArrayList<String> pathMatchingPreprocessedDatasetFiles;
    
    
    /**
     * Dataset / Preprocessed dataset file's path to use in matching process.
     */
    private String pathOfselectedDatasetToMatching;
    
    
    /**
     * Reanalysis data files to use in matching process.
     */
    private DefaultListModel<String> modelReanalysisFiles;
    
    
    /**
     * Variables of the buoy to use in matching process.
     */
    private DefaultListModel<String> modelBuoyVariables;
    
  
    /**
     * Max number of nearest geopoints to consider.
     */
    private int maxNearestGeopoints;
    
    
    /**
     * Indicates if necessary attributes for calculating flux of energy are present
     * in selected dataset.
     */
    private boolean attributtesForFluxOfEnergy;
            
    
    /**
     * Selected reanalysis files to use in matching process.
     */    
    private ArrayList<String> selectedReanalysisFiles;

    
    /**
     * Selected reanalysis vars to use in matching process.
     */    
    private ArrayList<String> selectedReanalysisVars;
    
    
    /**
     * Selected variables of the buoy to use in matching process.
     */    
    private ArrayList<String> selectedBuoyVariables;
    
    
    /**
     * Available variables of the buoy to use in matching process.
     */    
    private ArrayList<String> availableBuoyVariables;
      

    /**
     * Matching configuration of the current matching process.
     */
    private MatchingInformation currentMatchingConfiguration;
    
    
    /**
     * Matched final datasets obtained in matching process.
     */
    private ArrayList<MatchingFinalDataset> matchedFinalDatasets;
    
    
    /**
     * Backup of matched final datasets obtained in matching process.
     */
    private ArrayList<MatchingFinalDataset> backupOfMatchedFinalDatasets;
    
    
    
    /**
     * Missing dates that were found in matching process.
     */
    private ArrayList<String> missingDates;
    
    
    /**
     * Copy of missing dates that were found in matching process.
     */
    private ArrayList<String> copyOfmissingDates;
    
    
    /**
     * Number of reanalysis variables used in matching process.
     */
    private int numberOfReanalysisVariablesUsed;
    

    
    /* Tab: Final datasets */
    
    
    /**
     * Final datasets's name created.
     */
    private ArrayList<String> finalDatasetsName;


    /**
     * Row of the selected threshold is being modified.
     */
    private int rowOfThresholdToModify;

    
    /**
     * Min value of the final dataset after matching process.
     */
    private double minOfFinalDataset;


    /**
     * Max value of the final dataset after matching process.
     */
    private double maxOfFinalDataset;

    
    /**
     * Number of instances in the final dataset after matching process.
     */
    private int numInstancesOfFinalDataset;

    
    /**
     * Interval of the prediction horizon.
     */
    private final int predictionHorizonInterval;
    
   
    
    /**
     * Esta clase tiene como misión definir un filtro genérico que pueda ser
     * instanciado con cualquier filtro elegido por el usuario, también se podrán
     * configurar sus parámetros antes de su aplicación sobre el dataset elegido.
     * 
     * @author Antonio Manuel Gómez Orellana
     * 
     * @param <T> Instanced class.
     */
    public class GenericFilter<T>{
        
        /**
        * Filter to apply.
        */
        private T filter;

        
        /**
        * Filter's name.
        */
        private String filterName;
        
        
        /**
         * Constructor.
         * @param filter Selected filter to apply.
         * @param filterName Name of the selected filter to apply.
         */
        public GenericFilter(T filter, String filterName){
        
            /* Initializes to default values. */
            
            this.filter=filter;
            this.filterName=filterName;

        }        
        
        
        /**
         * Sets filter to apply.
         * @param filter Filter to apply.
         */        
        public void setFilter(T filter){
            
            /* Sets the filter. */
            
            this.filter = filter;
        
        }
        
        
        /**
         * Sets the name of the filter to apply.
         * @param filterName Name of the filter to apply.
         */        
        public void setFilterName(String filterName){
            
            /* Sets the name of the filter. */
            
            this.filterName = filterName;
        
        }

        
        /**
         * Returns the filter to apply.
         * @return The filter to apply.
         */               
        public T getFilter(){
            
            /* Gets the filter to apply. */
            
            return this.filter;
        
        }
        
        
        /**
         * Returns the name of the filter to apply.
         * @return The name of the filter to apply.
         */               
        public String getFilterName(){
            
            /* Gets the name of the filter to apply. */
            
            return this.filterName;
        
        }
        
    }

    
    /**
     * Generic filter to apply: when user selects a filter it is instantiated.
     */
    private GenericFilter<?> selectedFilter;

    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     * @param view View that this controller will manage.
     * @param datamodel Buoys's information.
     */
    public ControllerViewManageBuoys(InterfaceViewManageBuoys view, DefaultTableModel datamodel){

        /* Sets view */
        this.view = view;

        
        /* Tab: Buoys */
        
            /* Sets the model of the table that will show the information about buoys. */
            this.modelBuoys=getDataFromDatabase(datamodel);
            this.view.setModelBuoys(this.modelBuoys);
        
            
        
        /* Tab: Datasets & Preprocess */

            /* Initializes to default values. */
            
            /* Selected buoy to create preprocessed dataset files. */
            this.selectedIdBuoy=-1;
        
            /* Selected original (source) dataset file's path to create preprocessed dataset files. */
            this.pathOfselectedOriginalDataset="";
                        
            /* Selected Dataset/preprocessed dataset file's path to preprocess. */
            this.pathOfselectedDatasetToPreprocess="";
            
            /* Has the same information but only shows Station ID. */
            this.view.setModelBuoysDatasets(this.modelBuoys);
            
            /* Dataset files that belong to the selected buoy. */
            this.modelDatasetFiles=new DefaultListModel<>();
            this.pathDatasetFiles=new ArrayList<>();            
            
            /* Preprocessed dataset files that belong to the selected dataset file. */
            this.modelPreprocessedDatasetFiles=new DefaultListModel<>();
            this.pathPreprocessedDatasetFiles=new ArrayList<>();
            
            /* Data contained in the dataset/preprocessed dataset file loaded to work with. */
            this.preprocessDataset = new PreprocessDatasetFile();
            
            /* Summary information about the selected dataset file loaded in memory. */
            this.datasetInformationLoaded = new DatasetInformation();
            
            /* Indicates if user is preprocessing a dataset/preprocessed dataset file. */
            this.preprocessingStarted=false;
            
            /*
                Indicates if data contained in tab 'View data' has to be reloaded because 
                has changed since last time it was showed.
            */
            this.reloadViewData=false;
            
            /* Generic filter to apply: when user selects a filter it is
                                        instantiated. */
            this.selectedFilter = null;
                
            /* Configuration (number of hours to use) of each recovering filter. */
                        
                    /*
                        "Replace missing values with next hours mean"       -> index 0
                        "Replace missing values with previous hours mean"   -> index 1
                        "Replace missing values with symmetric hours mean"   -> index 2
                    */
            this.recoveringFiltersConfiguration=new ArrayList<>(Collections.nCopies(3, 5));            

            
        /* Tab: Matching configuration */
        
            /* Selected buoy to use in matching process. */
            this.idBuoyToMatching=-1;
            
            /* Latitude North of the selected buoy. */
            this.latitudeNorth=0.0;

            /* Longitude East of the selected buoy. */
            this.longitudeEast=0.0;            
            
            /* Has the same information but only shows Station ID. */
            this.view.setModelBuoysMatching(this.modelBuoys);

            /* Dataset files's names that belong to the selected buoy to use in matching process. */
            this.modelMatchingDatasetFiles=new DefaultListModel<>();

            /* Dataset files's path that belong to the selected buoy to use in matching process. */
            this.pathMatchingDatasetFiles=new ArrayList<>();

            /* Preprocessed dataset files's names that belong to the selected dataset file. */
            this.modelMatchingPreprocessedDatasetFiles=new DefaultListModel<>();

            /* Preprocessed dataset files's path that belong to the selected dataset file. */
            this.pathMatchingPreprocessedDatasetFiles=new ArrayList<>();
            
            /* Dataset / Preprocessed dataset file's path to use in matching process. */
            this.pathOfselectedDatasetToMatching=null;
            
            /* Type of the selected datase file 'Dataset/Preprocessed' to use in matching process. */
            this.typeOfSelectedDatasetToMatching=null;            
            
            /* Reanalysis data files to use matching process. */
            this.modelReanalysisFiles=new DefaultListModel<>();           
            
            /* Selected reanalysis data files to use in matching process. */
            this.selectedReanalysisFiles = new ArrayList<>();

            /* Selected reanalysis data vars to use in matching process. */
            this.selectedReanalysisVars = new ArrayList<>();
            
            /* Variables of the buoy to use in matching process. */
            this.modelBuoyVariables=new DefaultListModel<>();           
            
            /* Selected variables of the buoy to use in matching process. */
            this.selectedBuoyVariables = new ArrayList<>();

            /* Available variables of the buoy to use in matching process. */
            this.availableBuoyVariables = new ArrayList<>();
            
            /* Max number of nearest geopoints to consider. */
            this.maxNearestGeopoints=0;
            
            /* Indicates if necessary attributes for calculating flux of energy are present
               in selected dataset. */
            this.attributtesForFluxOfEnergy=false;
            
            /* Matching configuration of the current matching process. */
            this.currentMatchingConfiguration = new MatchingInformation();
            
            /* Missing dates that were found in matching process. */
            this.missingDates=new ArrayList<>();
            
            /* Copy of missing dates that were found in matching process. */
            this.copyOfmissingDates=new ArrayList<>();
                   
            /* Matched final datasets obtained in matching process. */
            this.matchedFinalDatasets = new ArrayList<>();
            
            /* Backup of matched final datasets obtained in matching process. */
            this.backupOfMatchedFinalDatasets = new ArrayList<>();
            
            /* Number of reanalysis variables used in matching process. */
            this.numberOfReanalysisVariablesUsed=0;
            
            
            
        /* Tab: Final datasets */
       
            
            /* Final datasets's name created. */
            this.finalDatasetsName = new ArrayList<>();
            
            /* Row of the selected threshold to modify. */
            this.rowOfThresholdToModify=-1;
            
            /* Min value of the final dataset after matching process. */
            this.minOfFinalDataset = 0.0;

            /* Max value of the final dataset after matching process. */
            this.maxOfFinalDataset = 0.0;
            
             /* Number of instances in the final dataset after matching process. */
            this.numInstancesOfFinalDataset=0;
            
            /* Interval of the prediction horizon: Fixed to 6 hours. */
            this.predictionHorizonInterval = 6;
            
    }

    
    /**
     * Returns view.
     * @return View.
     */
    private InterfaceViewManageBuoys getView(){
        
        /* Returns view */
        return view;
        
    }

    
    /* Tab: Buoys */
         
    
    
    /**
     * Returns buoy's information.
     * @return Buoy's information.
     */
    private DefaultTableModel getModelBuoys(){
        
        /* Returns model */
        return this.modelBuoys;
        
    }

    
    /* Tab: Datasets */


    
    /**
     * Returns selected buoy to create preprocessed dataset files.
     * @return Selected buoy to create preprocessed dataset files.
     */
    private int getSelectedIdBuoy(){
    
        /* Gets selected ID Buoy by user to create dataset files. */
        
        return this.selectedIdBuoy;

    }
    
    
    /**
     * Sets selected buoy to create preprocessed dataset files.
     * @param selectedIdBuoy Selected buoy to create preprocessed dataset files.
     */
    private void setSelectedIdBuoy(int selectedIdBuoy){
    
        /* Sets selected ID Buoy by user to create dataset files. */
        
        this.selectedIdBuoy = selectedIdBuoy;

    }
    
    
    /**
     * Returns selected original (source) dataset file's path to create preprocessed dataset files.
     * @return Selected original (source) dataset file's path to create preprocessed dataset files.
     */
    private String getPathOfSelectedOriginalDataset(){
    
        /* Gets path of selected original dataset file by user to create dataset files. */
        
        return this.pathOfselectedOriginalDataset;

    }
    
    
    /**
     * Sets selected original (source) dataset file's path to create preprocessed dataset files.
     * @param pathOfSelectedOriginalDataset Selected original (source) dataset file's path to create preprocessed dataset files.
     */
    private void setPathOfSelectedOriginalDataset(String pathOfSelectedOriginalDataset){
    
        /* Sets path of selected dataset file by user to create dataset files. */
        
        this.pathOfselectedOriginalDataset = pathOfSelectedOriginalDataset;
                
    }
    
    
    /**
     * Returns selected Dataset/preprocessed dataset file's path to preprocess.
     * @return Selected Dataset/preprocessed dataset file's path to preprocess.
     */
    private String getPathOfSelectedDatasetToPreprocess(){
    
        /* Gets path of selected Dataset/preprocessed dataset file by user to preprocess. */
        
        return this.pathOfselectedDatasetToPreprocess;

    }
    
    
    /**
     * Sets selected Dataset/preprocessed dataset file's path to preprocess.
     * @param pathOfSelectedDatasetToPreprocess Selected Dataset/preprocessed dataset file's path to preprocess.
     */
    private void setSelectedDatasetToPreprocess(String pathOfSelectedDatasetToPreprocess){
    
        /* Sets path of selected Dataset/preprocessed dataset file by user to preprocess. */
        
        this.pathOfselectedDatasetToPreprocess = pathOfSelectedDatasetToPreprocess;
        
    }
    
   
    /**
     * Returns dataset files's path that belong to the selected buoy.
     * @return Dataset files's path that belong to the selected buoy.
     */
    private ArrayList<String> getPathDatasetFiles(){
    
        /* Gets full path of the dataset files that belong to the buoy. */
        
        return this.pathDatasetFiles;

    }
    
    
    /**
     * Returns dataset files that belong to the selected buoy.
     * @return Dataset files that belong to the selected buoy.
     */
    private DefaultListModel<String> getModelDatasetFiles(){
    
        /* Gets the model of the list that shows the dataset files that belong to the buoy. */
        
        return this.modelDatasetFiles;

    }


    /**
     * Returns preprocessed dataset files's path that belong to the selected dataset file.
     * @return Preprocessed dataset files's path that belong to the selected dataset file.
     */
    private ArrayList<String> getPathPreprocessedDatasetFiles(){
    
        /* Gets full path of the preprocess files that belong to the selected dataset file. */
        
        return this.pathPreprocessedDatasetFiles;

    }    

    
    /**
     * Returns preprocessed dataset files that belong to the selected dataset file.
     * @return Preprocessed dataset files that belong to the selected dataset file.
     */
    private DefaultListModel<String> getModelPreprocessedDatasetFiles(){
    
        /* Gets the model of the list that shows the preprocess files that belong to the selected dataset file. */
        
        return this.modelPreprocessedDatasetFiles;

    }


    /**
     * Returns data contained in the dataset/preprocessed dataset file loaded to work with.
     * @return Data contained in the dataset/preprocessed dataset file loaded to work with.
     */
    private PreprocessDatasetFile getPreprocessDataset(){
    
        /* Gets the information of the dataset/preprocessed file loaded. */
        
        return this.preprocessDataset;

    }
    
    
    /**
     * Sets data of the dataset/preprocessed dataset file loaded to work with.
     * @param preprocessDataset Data of the dataset/preprocessed dataset file loaded to work with.
     */
    private void setPreprocessDataset(PreprocessDatasetFile preprocessDataset){
    
        /* Sets the information of the dataset/preprocessed file loaded. */
        
        this.preprocessDataset=preprocessDataset;

    }

    
    /**
     * Returns summary information about the selected dataset file loaded in memory.
     * @return Summary information about the selected dataset file loaded in memory.
     */
    private DatasetInformation getDatasetInformationLoaded(){
    
        /* Gets the summary information about the dataset file loaded in memory. */
        
        return this.datasetInformationLoaded;

    }
    
    
    /**
     * Sets summary information about the selected dataset file loaded in memory.
     * @param datasetInfoLoaded Summary information about the selected dataset file loaded in memory.
     */
    private void setDatasetInformationLoaded(DatasetInformation datasetInfoLoaded){
    
        /* Sets the summary information about the dataset file loaded in memory. */
        
        this.datasetInformationLoaded=datasetInfoLoaded;

    }    
    
    
    /**
     * Returns True if user is preprocessing a dataset/preprocessed dataset file or False if not.
     * @return True if user is preprocessing a dataset/preprocessed dataset file or False if not.
     */
    private boolean getPreprocessingStarted(){
    
        /* Gets True if user is preprocessing a dataset/preprocessed dataset file or False if not. */
        
        return this.preprocessingStarted;

    }    

    
    /**
     * Sets True if user is preprocessing a dataset/preprocessed dataset file or False if not.
     * @param preprocessingStarted True if user is preprocessing a dataset/preprocessed dataset file or False if not.
     */
    private void setPreprocessingStarted(boolean preprocessingStarted){
    
        /* Sets True if user is preprocessing a dataset/preprocessed dataset file or False if not. */
        
        this.preprocessingStarted=preprocessingStarted;

    }
    
    
    /**
     * Returns True if data contained in tab 'View data' has to be reloaded
     *         because has changed since last time it was showed, or False if not.
     * @return True if data contained in tab 'View data' has to be reloaded
     *         because has changed since last time it was showed, or False if not.
     */
    private boolean getReloadViewData(){
        
        /* Gets True if data contained in Tab: View data has to be reloaded or false if not. */
        
        return this.reloadViewData;

    }
    
    
    /**
     * Sets True if data contained in tab 'View data' has to be reloaded or False if not.
     * @param reloadViewData True if data contained in tab 'View data' has to be reloaded or False if not.
     */
    private void setReloadViewData(boolean reloadViewData){
    
        /* Sets True if data contained in tab 'View data' has to be reloaded or False if not. */
        
        this.reloadViewData=reloadViewData;

    }            


    /**
     * Returns generic filter to apply.
     * @return Generic filter to apply.
     */
    private GenericFilter <?> getSelectedFilter(){
    
        /* Gets the generic filter to apply. */
        
        return this.selectedFilter;

    }    

    
    /**
     * Sets generic filter to apply.
     * @param genericFilter Generic filter to apply.
     */
    private void setSelectedFilter(GenericFilter<?> genericFilter){
    
        /* Sets the generic filter to apply. */
        
        this.selectedFilter=genericFilter;

    }


    /**
     * Gets the configuration (number of hours to use) of the recovering filter received.
     * @param index Index of the recovering filter.
     * @return The configuration (number of hours to use) of the recovering filter received.
     */
    private int getRecoveringFilterConfiguration(int index){
    
        /* Gets the configuration (number of hours to use) of the recovering filter received. */
        
        return (int) this.recoveringFiltersConfiguration.get(index);

    }
    
    
    /**
     * Sets the configuration (number of hours to use) of the recovering filter received.
     * @param index Index of the recovering filter.
     * @param newConfiguration New configuration (number of hours to use) of the recovering filter received.
     */
    private void setRecoveringFilterConfiguration(int index, int newConfiguration){
    
        /* Gets the configuration (number of hours to use) of the recovering filter received. */
        
        this.recoveringFiltersConfiguration.set(index, (Integer) newConfiguration);

    }
    

    
    /* Tab: Matching configuration */
    
        
    /**
     * Returns selected buoy to use in matching process.
     * @return Selected buoy to use in matching process.
     */
    private int getIdBuoyToMatching(){
    
        /* Gets selected ID Buoy by user to use in matching. */
        
        return this.idBuoyToMatching;

    }
    
    
    /**
     * Sets selected buoy to use in matching process.
     * @param idBuoyToMatching Selected buoy to use in matching process.
     */
    private void setIdBuoyToMatching(int idBuoyToMatching){
    
        /* Sets selected ID Buoy by user to use in matching. */
        
        this.idBuoyToMatching = idBuoyToMatching;

    }

    
    /**
     * Returns Latitude North of the selected buoy.
     * @return Latitude North of the selected buoy.
     */
    private double getLatitudeBuoyToMatching(){
    
        /* Gets Latitude North of the selected buoy. */
        
        return this.latitudeNorth;

    }
    
    
    /**
     * Sets Latitude North of the selected buoy.
     * @param latitudeNorth Latitude North of the selected buoy.
     */
    private void setLatitudeBuoyToMatching(double latitudeNorth){
    
        /* Sets Latitude North of the selected buoy. */
        
        this.latitudeNorth = latitudeNorth;

    }
    
    
    /**
     * Returns Longitude East of the selected buoy.
     * @return Longitude East of the selected buoy.
     */
    private double getLongitudeBuoyToMatching(){
    
        /* Gets Longitude East of the selected buoy. */
        
        return this.longitudeEast;

    }
    
    
    /**
     * Sets Longitude East of the selected buoy.
     * @param longitudeEast Longitude East of the selected buoy.
     */
    private void setLongitudeBuoyToMatching(double longitudeEast){
    
        /* Sets Longitude East of the selected buoy. */
        
        this.longitudeEast = longitudeEast;

    }
    
                    
    /**
     * Returns dataset files's path that belong to the selected buoy to use in matching process.
     * @return Dataset files's path that belong to the selected buoy to use in matching process.
     */
    private ArrayList<String> getPathMatchingDatasetFiles(){
    
        /* Gets full path of the dataset files that belong to the buoy. */
        
        return this.pathMatchingDatasetFiles;

    }
    
    
    /**
     * Returns dataset files's names that belong to the selected buoy to use in matching process.
     * @return Dataset files's names that belong to the selected buoy to use in matching process.
     */
    private DefaultListModel<String> getModelMatchingDatasetFiles(){
    
        /* Gets the model of the list that shows the dataset files that belong to the buoy. */
        
        return this.modelMatchingDatasetFiles;

    }


    /**
     * Returns preprocessed dataset files's path that belong to the selected dataset file.
     * @return Preprocessed dataset files's path that belong to the selected dataset file.
     */
    private ArrayList<String> getPathMatchingPreprocessedDatasetFiles(){
    
        /* Gets preprocessed dataset files's path that belong to the selected dataset file. */
        
        return this.pathMatchingPreprocessedDatasetFiles;

    }
    
    
    /**
     * Returns preprocessed dataset files's names that belong to the selected dataset file.
     * @return Preprocessed dataset files's names that belong to the selected dataset file.
     */
    private DefaultListModel<String> getModelMatchingPreprocessedDatasetFiles(){
    
        /* Gets the model of the list that shows the preprocess files that belong to the selected dataset file. */
        
        return this.modelMatchingPreprocessedDatasetFiles;

    }
    
    
    /**
     * Returns reanalysis data files to use matching process.
     * @return Reanalysis data files to use matching process.
     */
    private DefaultListModel<String> getModelReanalysisFiles(){
    
        /* Gets the model of the list that shows the reanalysis .NC files to use in matching. */
        
        return this.modelReanalysisFiles;

    }
            
        
    /**
     * Returns dataset / Preprocessed dataset file's path to use in matching process.
     * @return Dataset / Preprocessed dataset file's path to use in matching process.
     */
    private String getPathOfSelectedDatasetToMatching(){
    
        /* Gets path of selected Dataset/preprocessed dataset file by user to use in matching. */
        
        return this.pathOfselectedDatasetToMatching;

    }
    
    
    /**
     * Sets dataset / Preprocessed dataset file's path to use in matching process.
     * @param pathOfSelectedDatasetToMatching Dataset / Preprocessed dataset file's path to use in matching process.
     */
    private void setPathOfSelectedDatasetToMatching(String pathOfSelectedDatasetToMatching){
    
        /* Sets path of selected Dataset/preprocessed dataset file by user to use in matching. */
                                
        this.pathOfselectedDatasetToMatching = pathOfSelectedDatasetToMatching;
        
    }
    

    /**
     * Returns type of the selected datase file 'Dataset/Preprocessed' to use in matching process.
     * @return Type of the selected datase file 'Dataset/Preprocessed' to use in matching process.
     */
    private String getTypeOfSelectedDatasetToMatching(){
    
        /* Gets type (dataset/preprocessed) of selected dataset file by user to use in matching. */

        return this.typeOfSelectedDatasetToMatching;

    }
    
    
    /**
     * Sets type of the selected datase file 'Dataset/Preprocessed' to use in matching process.
     * @param typeOfSelectedDatasetToMatching Type of the selected datase file 'Dataset/Preprocessed' to use in matching process.
     */
    private void setTypeOfSelectedDatasetToMatching(String typeOfSelectedDatasetToMatching){
    
        /* Sets type (dataset/preprocessed) of selected dataset file by user to use in matching. */
        
        this.typeOfSelectedDatasetToMatching = typeOfSelectedDatasetToMatching;
        
    }

    
    /**
     * Returns max number of nearest geopoints to consider.
     * @return Max number of nearest geopoints to consider.
     */
    private int getMaxNearestGeopoints(){
    
        /* Gets max of nearest geopoints to consider. */
        
        return this.maxNearestGeopoints;

    }
                      
    
    /**
     * Sets max number of nearest geopoints to consider.
     * @param maxNearestGeopoints Max number of nearest geopoints to consider.
     */
    private void setMaxNearestGeopoints(int maxNearestGeopoints){
    
        /* Sets max of nearest geopoints to consider. */
        
        this.maxNearestGeopoints = maxNearestGeopoints;

    }    

   
    /**
     * Returns True if necessary attributes for calculating flux of energy are present in selected dataset, or False if not.
     * @return True if necessary attributes for calculating flux of energy are present in selected dataset, or False if not.
     */
    private boolean getAttributtesForFluxOfEnergy(){
    
        /* 
            Gets if necessary attributes for calculating flux of energy are present
            in selected dataset.
        */
        
        return this.attributtesForFluxOfEnergy;

    }
                      
    
    /**
     * Sets True if necessary attributes for calculating flux of energy are present in selected dataset, or False if not.
     * @param attributtesForFluxOfEnergy True if necessary attributes for calculating flux of energy are present in selected dataset, or False if not.
     */
    private void setAttributtesForFluxOfEnergy(boolean attributtesForFluxOfEnergy){
    
        /* 
            Sets if necessary attributes for calculating flux of energy are present
            in selected dataset.
        */
        
        this.attributtesForFluxOfEnergy = attributtesForFluxOfEnergy;

    }
    

    /**
     * Returns selected reanalysis data files to use in matching process.
     * @return Selected reanalysis data files to use in matching process.
     */
    private ArrayList<String> getSelectedReanalysisFiles(){
    
        /* Gets selected reanalysis data files to use in matching process. */
        
        return this.selectedReanalysisFiles;

    }
    
    
    /**
     * Sets selected reanalysis data files to use in matching process.
     * @param selectedReanalysisFiles Selected reanalysis data files to use in matching process.
     */
    private void setSelectedReanalysisFiles(ArrayList<String> selectedReanalysisFiles){
    
        /* Sets selected reanalysis data files to use in matching process. */
        
        this.selectedReanalysisFiles = selectedReanalysisFiles;

    }


    /**
     * Returns selected reanalysis vars to use in matching process.
     * @return Selected reanalysis vars to use in matching process.
     */
    private ArrayList<String> getSelectedReanalysisVars(){
    
        /* Gets selected reanalysis vars to use in matching process. */
        
        return this.selectedReanalysisVars;

    }

    
    /**
     * Sets selected reanalysis data vars to use in matching process.
     * @param selectedReanalysisVars Selected reanalysis data vars to use in matching process.
     */
    private void setSelectedReanalysisVars(ArrayList<String> selectedReanalysisVars){
    
        /* Sets selected reanalysis data vars to use in matching process. */
        
        this.selectedReanalysisVars = selectedReanalysisVars;

    }
    
    
    /**
     * Returns variables of the buoy to use in matching process.
     * @return Variables of the buoy to use in matching process.
     */
    private DefaultListModel<String> getModelBuoyVariables(){
    
        /* Gets the model of the list that shows the variables of the buoy to use in matching. */
        
        return this.modelBuoyVariables;

    }
    
    
    /**
     * Returns selected variables of the buoy to use in matching.
     * @return Selected variables of the buoy to use in matching.
     */
    private ArrayList<String> getSelectedBuoyVariables(){
    
        /* Gets the selected variables of the buoy to use in matching process. */
        
        return this.selectedBuoyVariables;

    }
    
    
    /**
     * Returns available variables of the buoy to use in matching.
     * @return Available variables of the buoy to use in matching.
     */
    private ArrayList<String> getAvailableBuoyVariables(){
    
        /* Gets the available variables of the buoy to use in matching process. */
        
        return this.availableBuoyVariables;

    }
        
    
    /**
     * Sets selected variables of the buoy to use in matching.
     * @param selectedBuoyVariables Selected variables of the buoy to use in matching process.
     */
    private void setSelectedBuoyVariables(ArrayList<String> selectedBuoyVariables){
    
        /* Sets selected variables of the buoy to use in matching process. */
        
        this.selectedBuoyVariables = selectedBuoyVariables;

    }    

    
    /**
     * Returns matching configuration of the current matching process.
     * @return Matching configuration of the current matching process.
     */
    private MatchingInformation getCurrentMatchingConfiguration(){

        /* Gets matching configuration of the current matching process. */
        
        return this.currentMatchingConfiguration;

    }        
    
    
    /**
     * Return the missing dates that were found in matching.
     * @return The missing dates that were found in matching.
     */    
    private ArrayList<String> getMissingDates() {
        
        return this.missingDates;
        
    }        

    
    /**
     * Sets the missing dates that were found in matching.
     * @param missingDates Missing dates that were found in matching.
     */    
    private void setMissingDates(ArrayList<String> missingDates) {
        
        this.missingDates=missingDates;
        
    }        
    
    
    /**
     * Return the copy of the missing dates that were found in matching.
     * @return the copy of the missing dates that were found in matching.
     */    
    private ArrayList<String> getCopyOfMissingDates() {
        
        return this.copyOfmissingDates;
        
    }        
    
    
    /**
     * Returns matched final datasets obtained in matching process.
     * @return Matched final datasets obtained in matching process.
     */
    private ArrayList<MatchingFinalDataset> getMatchedFinalDatasets(){
    
        /* Gets matched final datasets obtained in matching process. */
        
        return this.matchedFinalDatasets;

    }
    
    
    /**
     * Sets matched final datasets obtained in matching process.
     * @param matchedFinalDatasets Matched final datasets obtained in matching process.
     */
    private void setMatchedFinalDatasets(ArrayList<MatchingFinalDataset> matchedFinalDatasets){
    
        /* Sets matched final datasets obtained in matching process. */
        
        this.matchedFinalDatasets = matchedFinalDatasets;

    }


    /**
     * Returns backup of matched final datasets obtained in matching process.
     * @return Backup of matched final datasets obtained in matching process.
     */
    private ArrayList<MatchingFinalDataset> getBackupOfMatchedFinalDatasets(){
    
        /* Gets backup of matched final datasets obtained in matching process. */
        
        return this.backupOfMatchedFinalDatasets;

    }
    
    
    /**
     * Sets backup of matched final datasets obtained in matching process.
     * @param backupOfMatchedFinalDatasets Backup of matched final datasets obtained in matching process.
     */
    private void setBackupOfMatchedFinalDatasets(ArrayList<MatchingFinalDataset> backupOfMatchedFinalDatasets){
    
        /* Sets backup of matched final datasets obtained in matching process. */
        
        this.backupOfMatchedFinalDatasets = backupOfMatchedFinalDatasets;

    }
    
    
    /**
     * Sets the number of the reanalysis variables used in mathicng process.
     * @param numberOfReanalysisVariablesUsed Number of the reanalysis variables used in mathicng process.
     */
    private void setNumberOfReanalysisVariablesUsed(int numberOfReanalysisVariablesUsed){
    
        /* Sets the number of the reanalysis variables used in mathicng process. */
        
        this.numberOfReanalysisVariablesUsed = numberOfReanalysisVariablesUsed;

    }

    
    /**
     * Gets the number of the reanalysis variables used in matching process.
     * @return Number of the reanalysis variables used in matching process.
     */
    private int getNumberOfReanalysisVariablesUsed(){
    
        /* Gets the number of the reanalysis variables used in matching process. */
        
        return this.numberOfReanalysisVariablesUsed;

    }    
     
        
    /* Tab: Final datasets */
    
    
    
    /**
     * Returns final datasets name created.
     * @return Final datasets name created.
     */    
    private ArrayList<String> getFinalDatasetsName() {
        
        /* Gets final datasets name created. */
        
        return this.finalDatasetsName;
        
    }
    
    
    /**
     * Sets row of the selected threshold that is being modified.
     * @param selectedRow Row of the selected threshold that is being modified.
     */  
    private void setRowOfThresholdToModify(int selectedRow){
        
        /* Sets the row of the selected threshold that is being modified. */
        
        this.rowOfThresholdToModify=selectedRow;
        
    }

    
    /**
     * Returns row of the selected threshold that is being modified.
     * @return Row of the selected threshold that is being modified.
     */    
    private int getRowOfThresholdToModify(){
        
        /* Gets the row of the selected threshold that is being modified. */
        
        return this.rowOfThresholdToModify;
        
    }
    
    
    /**
     * Sets the min value of the final dataset after matching process.
     * @param minOfFinalDataset Min value of the final dataset after matching process.
     */  
    private void setMinOfFinalDataset(double minOfFinalDataset){
        
        /* Sets the min value of the final dataset after matching process. */
        
        this.minOfFinalDataset=minOfFinalDataset;
        
    }
    
    
    /**
     * Returns the min value of the final dataset after matching process.
     * @return Min value of the final dataset after matching process.
     */  
    private double getMinOfFinalDataset(){
        
        /* Gets the min value of the final dataset after matching process. */
        
        return this.minOfFinalDataset;
        
    }
    
    
    /**
     * Sets the max value of the final dataset after matching process.
     * @param maxOfFinalDataset Max value of the final dataset after matching process.
     */  
    private void setMaxOfFinalDataset(double maxOfFinalDataset){
        
        /* Sets the max value of the final dataset after matching process. */
        
        this.maxOfFinalDataset=maxOfFinalDataset;
        
    }
    
    
    /**
     * Returns the max value of the final dataset after matching process.
     * @return Max value of the final dataset after matching process.
     */  
    private double getMaxOfFinalDataset(){
        
        /* Gets the max value of the final dataset after matching process. */
        
        return this.maxOfFinalDataset;
        
    }
    
    
    /**
     * Sets the number of instances in the final dataset after matching process.
     * @param numInstancesOfFinalDataset Number of instances in the final dataset after matching process.
     */  
    private void setNumInstancesOfFinalDataset(int numInstancesOfFinalDataset){
        
        /* Sets the number of instances in the final dataset after matching process. */
        
        this.numInstancesOfFinalDataset=numInstancesOfFinalDataset;
        
    }
    
    
    /**
     * Returns the number of instances in the final dataset after matching process.
     * @return Number of instances in the final dataset after matching process.
     */  
    private int getNumInstancesOfFinalDataset(){
        
        /* Gets the number of instances in the final dataset after matching process. */
        
        return this.numInstancesOfFinalDataset;
        
    }
    
    
    /**
     * Returns the interval of the prediction horizon.
     * @return Interval of the prediction horizon.
     */  
    private int getPredictionHorizonInterval(){
        
        /* Gets the interval of the prediction horizon. */
        
        return this.predictionHorizonInterval;
        
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

            
            /* General events. */
            
            case InterfaceViewManageBuoys.BACK:
                
                /* Action Back clicked. */
                doBack();
                break;
                
            case InterfaceViewManageBuoys.MAIN_MENU:
                
                /* Action MAIN_MENU clicked. */
                doMainMenu();
                break;
            
            
                
            /* Menu events. */
            
            
            case InterfaceViewManageBuoys.BUOYS:
                
                /* Action BUOYS clicked. */
                doGotoTab("Buoys");
                break;
                
            case InterfaceViewManageBuoys.DATASETS:
                
                /* Action DATASETS clicked. */
                doGotoTab("Datasets");
                break;
                
            case InterfaceViewManageBuoys.PREPROCESS:
                
                /* Action PREPROCESS clicked. */
                doGotoTab("Pre-process");
                break;
                
            case InterfaceViewManageBuoys.MATCHING_CONFIGURATION:
                
                /* Action MATCHING clicked. */
                doGotoTab("Matching");
                break;
                
            case InterfaceViewManageBuoys.HELP:
                
                /* Action HELP clicked. */
                doHelp();
                break;
            
            
            /* Events of Tab: Buoys */
            
            
            case InterfaceViewManageBuoys.NEW_BUOY:
                
                /* Action NEW_BUOY clicked. */
                doNewBuoy();
                break;
    
            case InterfaceViewManageBuoys.VIEW_MODIFY_BUOY:
                
                /* Action MODIFY_BUOY clicked. */
                doModifyBuoy();
                break;

            case InterfaceViewManageBuoys.DELETE_BUOY:
                
                /* Action DELETE_BUOY clicked. */
                doDeleteBuoy();
                break;

                
                
            /* Events of Tab: Datasets */
            
            
            case InterfaceViewManageBuoys.OPEN_DATASET_FILE:
                
                /* Action OPEN_DATASET_FILE clicked. */
                
                doDoubleClickonJlstOriginalDatasets();
                break;
                
            case InterfaceViewManageBuoys.NEW_DATASET_FILE:
                
                /* Action NEW_DATASET_FILE clicked. */
                doNewDatasetFile();
                break;
                    
            case InterfaceViewManageBuoys.DELETE_DATASET_FILE:
                
                /* Action DELETE_DATASET_FILE clicked. */
                doDeleteDatasetFile();
                break;
                
            case InterfaceViewManageBuoys.OPEN_PREPROCESSED_FILE:
                
                /* Action OPEN_PREPROCESSED_FILE clicked. */
                doDoubleClickonJlstPreprocessedDatasets();
                break;                

            case InterfaceViewManageBuoys.DELETE_PREPROCESSED_FILE:
                
                /* Action DELETE_PREPROCESSED_FILE clicked. */
                doDeletePreprocessedFile();
                break;                
                
                
                
            /* Events of Tab: Preprocess. */

                
            case InterfaceViewManageBuoys.OPEN_DATASET:
                
                /* Action OPEN clicked. */
                doOpenDataset();
                break;
                
            case InterfaceViewManageBuoys.SAVE_PREPROCESS:
                
                /* Action SAVE clicked. */
                doSavePreprocessedFile();
                break;
               
            case InterfaceViewManageBuoys.APPLY_FILTER:
                
                /* Action APPLY_FILTER clicked. */
                doApplyFilter();
                break;
                
            case InterfaceViewManageBuoys.UNDO:
                
                /* Action UNDO clicked. */
                doUndo();
                break;
                
            case InterfaceViewManageBuoys.RESTORE_DATA:
                
                /* Action RESTORE_DATA clicked. */
                doRestoreData();
                break;

            case InterfaceViewManageBuoys.CONFIGURE_FILTER:
                
                /* Action CONFIGURE_FILTER clicked. */
                doConfigureFilter();
                break;
                
            case InterfaceViewManageBuoys.PREVIOUS_DATE_PREPROCESS:
                
                /* Action PREVIOUS_DATE_PREPROCESS clicked. */
                doPreviousDatePreprocess();
                break;

            case InterfaceViewManageBuoys.NEXT_DATE_PREPROCESS:
                
                /* Action NEXT_DATE_PREPROCESS clicked. */
                doNextDatePreprocess();
                break;

                
            /* Events of Tab: Matching configuration. */

                
            case InterfaceViewManageBuoys.SELECT_DATASET_FILE:
                
                /* Action SELECT_DATASET_FILE clicked. */
                doSelectMatchingDatasetFile();
                break;
                
            case InterfaceViewManageBuoys.SELECT_PREPROCESSED_DATASET_FILE:
                
                /* Action SELECT_PREPROCESSED_DATASET_FILE clicked. */
                doSelectMatchingPreprocessedDatasetFile();
                break;

            case InterfaceViewManageBuoys.MODIFY_REANALYSIS_FILES:
                
                /* Action MODIFY_REANALYSIS_FILES clicked. */
                doModifySelectionOfSelectReanalysisFiles();
                break;                

            case InterfaceViewManageBuoys.MODIFY_BUOY_VARIABLES:
                
                /* Action MODIFY_BUOY_VARIABLES clicked. */
                doModifySelectionOfBuoyVariables();
                break;                

            case InterfaceViewManageBuoys.FLUX_OF_ENERGY_CHANGED:
                
                /* Action FLUX_OF_ENERGY_CHANGED. */
                doCheckCalculateFluxOfEnergy();
                break;

            case InterfaceViewManageBuoys.ATTRIBUTE_TO_PREDICT_CHANGED:
                
                /* Action ATTRIBUTE_TO_PREDICT_CHANGED. */
                doAttributeToPredictChanged();
                break;                         

            case InterfaceViewManageBuoys.LOAD_MATCHING:
                
                /* Action LOAD_MATCHING clicked. */
                doLoadMatchingFile();
                break;

            case InterfaceViewManageBuoys.RUN_MATCHING:
                
                /* Action RUN_MATCHING clicked. */
                doRunMatching();
                break;                                                                     

                
            /* Events of Tab: Final datasets. */
                                                
            case InterfaceViewManageBuoys.DELETE_THRESHOLD:
                
                /* Action DELETE_THRESHOLD clicked. */
                doDeleteThreshold();
                
                break;
                
            case InterfaceViewManageBuoys.ADD_THRESHOLD:
                
                /* Action ADD_THRESHOLD clicked. */
                doAddThreshold();                
                
                break;
                
            case InterfaceViewManageBuoys.MODIFY_THRESHOLD:
                
                /* Action MODIFY_THRESHOLD clicked. */
                doModifyThreshold();
                
                /* Disables button 'Create final datasets'. */
                getView().setEnablebtnCreateFinalDatasets(false);
                getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");

                break;

            case InterfaceViewManageBuoys.UPDATE_THRESHOLD:
                
                /* Action UPDATE_THRESHOLD clicked. */
                doUpdateThreshold();
                                
                break;                
                
            case InterfaceViewManageBuoys.INCREASE_PREDICTION_HORIZON:
                
                /* Action INCREASE_PREDICTION_HORIZON clicked. */
                doIncreasePredictionHorizon();                
                break;
                
            case InterfaceViewManageBuoys.DECREASE_PREDICTION_HORIZON:
                
                /* Action DECREASE_PREDICTION_HORIZON clicked. */
                doDecreasePredictionHorizon();                
                break;
                
            case InterfaceViewManageBuoys.SYNCHRONISE_R_DATA:
                
                /* Action SYNCHRONISE_R_DATA clicked. */
                doSynchroniseRDataSelectionChanged();
                break;
                
            case InterfaceViewManageBuoys.UPDATE_FINAL_DATASET:
                
                /* Action UPDATE_PREDICTION_HORIZON clicked. */
                doUpdateFinalDataset(true);
                break;
                
            case InterfaceViewManageBuoys.SHOW_DATE_ATTRIBUTE:
                
                /* Action DATE_ATTRIBUTE clicked. */
                doVisualiseDateAttribute();
                break;                
                
            case InterfaceViewManageBuoys.PREVIOUS_DATE_FINAL_DATASET:
                
                /* Action PREVIOUS_DATE_FINAL_DATASET clicked. */
                doPreviousDateFinalDataset();
                break;

            case InterfaceViewManageBuoys.NEXT_DATE_FINAL_DATASET:
                
                /* Action NEXT_DATE_FINAL_DATASET clicked. */
                doNextDateFinalDataset();
                break;               
                
            case InterfaceViewManageBuoys.OUTPUT_FILE:
                
                /* Action OUTPUT_FOLDER. */
                doSelectOutputFile();
                break;
                
            case InterfaceViewManageBuoys.SAVE_MATCHING:
                
                /* Action SAVE_MATCHING clicked. */
                doSaveMatchingFile();
                break;

            case InterfaceViewManageBuoys.FINAL_DATASETS_FORMAT_CHANGED:
                
                /* Action OUTPUT_FILE_FORMAT_CHANGED. */
                doCheckOutputFileFormatSelected();
                break;
                
            case InterfaceViewManageBuoys.FINAL_DATASET_TO_VISUALISE_CHANGED:
                
                /* Action FINAL_DATASET_TO_VISUALISE_CHANGED. */
                doVisualiseSelectedFinalDataset();
                break;                         

            case InterfaceViewManageBuoys.CREATE_FINAL_DATASETS:
                
                /* Action CREATE_FINAL_DATASETS. */
                doCreateFinalDatasets();
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
        
        if(event.getClickCount() == 1){
            
            /* One click. */
            
            /* The event is captured here because valueChanged event only fires if
               user changes selection, so information is not updated properly when user selects
               dataset/preprocessed file in the same row.
            */
            
            switch (component) {
                
                /*  Tab: Datasets */
                    
                case "jlstOriginalDatasets":
                
                    /* One click on dataset files created list. */
                    
                    datasetSelectionChanged();
                    break;

                    
                /* Tab: Preprocess (Tab: View table) */
            
                case "jTabbedPreprocess":
                
                    /* One click for selecting tab: File information or View data */
                    
                    tabbedPreprocessSelectionChanged();
                    break;                    

            }
        }


        if(event.getClickCount() == 2){
            
            /* Double click. */
            
            switch (component) {

                /*  Tab: Buoys */
                
                case "tblBuoys":
                
                    /* Double click in Table that contains information about buoys. */
                    
                    doDoubleClickonTblBuoys();
                    break;

                    
                /*  Tab: Datasets */
                    
                case "jlstOriginalDatasets":
                
                    /* Double click in dataset files created list. */
                    
                    doDoubleClickonJlstOriginalDatasets();
                    break;
                    
                case "jlstPreprocessedDatasets":
                
                    /* Double click in preprocessed dataset created list. */
                    
                    doDoubleClickonJlstPreprocessedDatasets();
                    break;    

                    
                /*  Tab: Matching */
                    
                case "jlstMatchingDatasets":
                
                    /* Double click in dataset files created list. */
                    
                    doSelectMatchingDatasetFile();
                    break;
                    
                case "jlstMatchingPreprocessedDatasets":
                
                    /* Double click in preprocessed dataset files created list. */
                    
                    doSelectMatchingPreprocessedDatasetFile();
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
        
        /* To know if event was generated in jlstPreprocessFiles or jlstDatasetFiles */
        String objectClass = event.getSource().getClass().getSimpleName();
        
        if (event.getValueIsAdjusting() == false){        
        
            switch (objectClass) {

                case "DefaultListSelectionModel":
                    
                    /* Gets tab where event was generated. */
                    int selectedTab=getView().getSelectedTab();
                    
                    switch (selectedTab) {
                        
                        case 0:  /* Tab: Buoys */
                            
                            /*  tblBuoys */

                            /* A new buoy was selected in tblBuoys by double click. */

                            /* Shows dataset files (in tab Preprocess) that belong to the selected buoy. */
                            buoyDatasetsSelectionChanged();
                                                        
                            break;
                            
                        case 1:  /* Tab: Datasets */
                            
                            /*  tblBuoysDatasets */

                            /* A new buoy was selected in tblBuoysDatasets. */                        

                            /* Shows dataset files (in tab Preprocess) that belong to the selected buoy. */
                            buoyDatasetsSelectionChanged();
                                                        
                            break;
                                                    
                        case 2: /* Tab: Preprocess */                           
                        
                            /*  tblAttributtes */
                        
                            /* A new attribute was selected by user. */
                
                            /* Shows statistics of the selected attribute. */
                            attributeSelectionChanged();
                            
                            break;
                            
                        case 3:  /* Tab: Matching configuration. */
                            
                            /*  tblBuoysMatching */
                            
                            /* A new buoy was selected in tblBuoysMatching. */

                            /* Shows dataset files that belong to the selected buoy. */
                            buoyMatchingSelectionChanged();
                            
                            break;                            
                            
                    }

                    break;
                    
                case "JList":
                    
                    if(event.getSource().toString().contains("jlstOriginalDatasets")){
                        
                        /* Tab: Datasets: jlstOriginalDatasets */
                    
                        /* A new dataset file was selected by user. */
                    
                        /* Shows .XML data of the dataset file selected and
                           preprocess files that belong to the selected dataset file. */
                        datasetSelectionChanged();
                        
                    }else if(event.getSource().toString().contains("jlstPreprocessedDatasets")){
                        
                        /* Tab: Datasets: jlstPreprocessedDatasets */
                    
                        /* A new preprocessed dataset file was selected by user. */
                    
                        /* Shows .XML data of the preprocessed dataset file selected. */
                        preprocessedSelectionChanged();
                    
                    }else if(event.getSource().toString().contains("jlstMatchingDatasets")){
                        
                        /* Tab: Matching: jlstMatchingDatasets */
                    
                        /* A new dataset file was selected by user. */
                    
                        /* Updates preprocessed datasets list in Tab: Matching.*/
                        datasetMatchingSelectionChanged();
                    
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
    public void valueChanged(TreeSelectionEvent event){
        
        /*  Tab: Preprocess */
                    
        /* User has selected another filter to apply. */

        filterSelectionChanged();

    }
    
    
    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void stateChanged(ChangeEvent event) {
    
        /* The user changed tab selection. */
        
        /* 
            Checks if user selected tab 'Final datasets' and changed
            matching configuration of tab: 'Matching configuration' without
            clicking on 'Next' button.
        */
        
        checksCurrentTabMatchingConfWithTypedConf();
        
    }    
    
    
    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void focusGained(FocusEvent event) {
    
        
    }    
    
    
    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void focusLost(FocusEvent event) {
    
        /*  Tab: Matching configuration */
        
          /* NumberOfNearestGeopoints */
          
        /* Checks if the number of geopoints typed by the user is equal to 1 for
           changing the selection in 'Number of final datasets'. */
        
        checkNumberOfTypedGeopoints();
        
    }    
    
    
    
    
    /* Methods for processing each event (what user clicked on view). */
    

    /*  Tab: Buoys */
    
      
    
    /**
     * Returns buoy's information from database.
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
     * Opens BuoyData view to insert a new buoy.
     */
    private void doNewBuoy(){
        
        /* Opens BuoyData view to insert a new buoy. */
        
        /* Creates the view and controller. */
        InterfaceViewBuoyData newBuoyView = new BuoyData(getView().getParent(), false);
            /* -1: in selected row parameter means: newBouy*/
        ControllerViewBuoyData controller = new ControllerViewBuoyData(newBuoyView, getView().getModelBuoys(), -1);
        
        /* Sets the controller that will manage all events and shows the view. */
        newBuoyView.setController(controller);
        newBuoyView.clearFields();
        newBuoyView.showView("SPAMDA 1.0-New buoy");

    }
                    
    
    /**
     * Opens BuoyData view to modify the selected buoy.
     */
    private void doModifyBuoy(){
        
        /* Opens BuoyData view to modify the selected buoy. */
        
        /* Gets the selected row to modify. */
        
        int selectedRow = getView().getSelectedRowBuoys();
                
        if(selectedRow==-1){
            
            /* None row is selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a buoy to modify.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Information about buoys. */
            DefaultTableModel datamodel = getView().getModelBuoys();

            /* Creates the view and controller. */
            InterfaceViewBuoyData modifyBuoyView = new BuoyData(getView().getParent(), false);
            ControllerViewBuoyData controller = new ControllerViewBuoyData(modifyBuoyView, datamodel, selectedRow);
        
            /* Sets the controller that will manage all events */
            modifyBuoyView.setController(controller);

            /* Sets fields values. */
            modifyBuoyView.setBuoyStationID(datamodel.getValueAt(selectedRow, 1).toString());
            modifyBuoyView.setBuoyDescription(datamodel.getValueAt(selectedRow, 2).toString());
            modifyBuoyView.setBuoyLatitude(datamodel.getValueAt(selectedRow, 3).toString());
            modifyBuoyView.setBuoyLongitude(datamodel.getValueAt(selectedRow, 5).toString());
            modifyBuoyView.setBuoyLatitudeNS(datamodel.getValueAt(selectedRow, 4).toString());            
            modifyBuoyView.setBuoyLongitudeEW(datamodel.getValueAt(selectedRow, 6).toString());            
            
            /* Shows the view. */
            modifyBuoyView.showView("SPAMDA 1.0-Modify buoy");
            
            /* Checks if the buoy is selected in Preprocess. */            
            if( (int) datamodel.getValueAt(selectedRow, 0) == getSelectedIdBuoy()){

                /* Updates station id in Tab: Preprocess. */
                getView().setSelectedStationIDPreprocess(datamodel.getValueAt(selectedRow, 1).toString());
            }            
            
            /* Checks if the buoy is selected in Matching. */            
            if( (int) datamodel.getValueAt(selectedRow, 0) == getIdBuoyToMatching()){

                /* Updates buoys information in Tab: Matching. */
                setSelectedBuoyToMatching(getIdBuoyToMatching());
            }
        
        }
                
    }

        
    /**
     * Deletes the selected buoy.
     */
    private void doDeleteBuoy(){
        
        /* Deletes selected buoy. */

        /* Gets the selected row to delete. */
        
        int selectedRow = getView().getSelectedRowBuoys();
                
        if(selectedRow==-1){
            
            /* None row is selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a buoy to delete.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Checks if user selected to delete the same buoy is working with. */
            
            if(getSelectedIdBuoy()==(int)getModelBuoys().getValueAt(selectedRow, 0)){
                
                JOptionPane.showMessageDialog(null, "You can not delete the same buoy you are currently working in Preprocess.", "Message", JOptionPane.WARNING_MESSAGE);
                
            }else if(getIdBuoyToMatching()==(int)getModelBuoys().getValueAt(selectedRow, 0)){
                
                JOptionPane.showMessageDialog(null, "You can not delete the same buoy you are currently working in Matching.", "Message", JOptionPane.WARNING_MESSAGE);
            
            }else{
                    
                Object[] options = {"Cancel", "Delete"};

                if (JOptionPane.showOptionDialog(null, "Attention !!\n\nAll information and files in the "
                        +"selected buoy will be deleted:\n\n    -> Annual text files\n    -> Intermediate datasets\n"
                        +"    -> Pre-processed dataset\n    -> Matching configuration files\n\nClick on Cancel to abort or Delete to delete it.\n\n","Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1){

                    /* Deletes the buoy and files with information about it. */              
                    deleteBuoyAndFiles(selectedRow, getView().getSelectedRowBuoysDatasets(), getView().getSelectedRowBuoysMatching());

                }else{

                    /* Cancelled by user. */
                    JOptionPane.showMessageDialog(null, "Operation cancelled by user.", "Message", JOptionPane.INFORMATION_MESSAGE);

                }  
            }
            
        }            
        
    }

    
    /**
     * Deletes the selected buoy from database and all files and information that belong to the buoy.
     * 
     * @param selectedRowtabBuoys Selected buoy to delete.
     * @param selectedRowtabDatasets Selected buoy int tab 'Datasets'
     * @param selectedRowtabMatching Selected buoy int tab 'Matching Configuration'
     */
    private void deleteBuoyAndFiles(int selectedRowtabBuoys, int selectedRowtabDatasets, int selectedRowtabMatching){
           
        /* Id (buoy) selected by user to delete. */
        int idBuoy = (int) this.getView().getModelBuoys().getValueAt(selectedRowtabBuoys, 0);
        
        /* Number of buoys after deleting the selected buoy. */
        int numBuoys;
        
        boolean result;
        
        /* Connects to database and updates the buoy. */
        BuoyDatabase buoyDatabase = new BuoyDatabase();
        
        if(buoyDatabase.connect()==true){
            
            result=buoyDatabase.deleteBuoy(idBuoy);
            buoyDatabase.disconnect();

            /* Checks if the buoy was successfully deleted from database. */
            if(!result){
                JOptionPane.showMessageDialog(null, "The buoy was not deleted from database.", "Error", JOptionPane.ERROR_MESSAGE);
            }else{

                /* Deletes all folders and files with information about the buoy.            

                            idBuoy
                                | -> TXTinFiles
                                | -> Datasets (of TXTinFiles)
                                | -> Preprocess (of Datasets)
                                | -> Matching
                */

                /* Utilities. */
                Utils util = new Utils();

                /* Root that contains all folders and files about buoy. */
                String root = System.getProperty("user.dir", ".")+File.separator+"DB"
                                +File.separator+"id"+Integer.toString(idBuoy);

                util.deleteDirectory(root);

                /* Deletes the buoy from the model. */
                getView().getModelBuoys().removeRow(selectedRowtabBuoys);

                JOptionPane.showMessageDialog(null, "The buoy was successfully deleted from database.", "Message", JOptionPane.INFORMATION_MESSAGE);


                /* Updates intermediate dataset files in tab 'Datasets' and 'Matching Configuration' */

                /* Gets the current number of buoys. */
                numBuoys=getView().getModelBuoys().getRowCount();

                /* Checks if there is one buoy at least. */
                if(numBuoys>0){

                    /* Checks if the selected buoy in tab 'Datasets' was not the first one. */
                    if(selectedRowtabDatasets>0){

                        selectedRowtabDatasets=selectedRowtabDatasets-1;
                    }

                    /* Checks if the selected buoy in tab 'Matching Configuration' was not the first one. */
                    if(selectedRowtabMatching>0){

                        selectedRowtabMatching=selectedRowtabMatching-1;
                    }


                    /* Updates the selected buoy in tab 'Datasets' and 'Matching Configuration' .*/
                    getView().setSelectedRowBuoysDatasets(selectedRowtabDatasets);
                    getView().setSelectedRowBuoysMatching(selectedRowtabMatching);

                }

                /* Updates the intermediate datasets of the new selected buy in tab 'Matching Configuration'.*/
                buoyMatchingSelectionChanged();

            }
        
        }else{

            JOptionPane.showMessageDialog(null, "There was an error when connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);

        }
        
    }
    
        
    
    /**
     * User selected a buoy in tab 'Buoys'.
     */
    private void doDoubleClickonTblBuoys(){

        /* Double click on tblBuoys */
        
        /* Buoy where user double clicked on. */
        int row = getView().getSelectedRowBuoys();
        
        /* In tab Datasets sets the selected buoy by user. */
        getView().setSelectedRowBuoysDatasets(row);
        
        /* In tab Matching sets the selected buoy by user. */
        getView().setSelectedRowBuoysMatching(row);
        
        /* Shows dataset files that belong to the selected buoy in tab 'Matching configuration'. */
        buoyMatchingSelectionChanged();        

        /* And moves to tab Datasets. */
        getView().setSelectedTab(1);

    }
                  
    
    
    /*  Tab: Datasets */
    
    
    
    /**
     * User selected a dataset file in tab 'Datasets'.
     */
    private void doDoubleClickonJlstOriginalDatasets(){

        /* Double click on jlstDatasetFiles */
        
        /* In tab Preprocess sets the selected dataset file to preprocess. */
        
        /* File name where user double clicked on. */
        int index = getView().getSelectedIndexDataset();

        if(index!=-1 && (getPreprocessingStarted()==false || closingCurrentPreprocessing()==true)){

            /* There is a selected dataset file. */
            
            /* 
               Sets the selected id Buoy by user to create dataset files.
               To know idBuoy and dataset file that belongs the preprocessed file created.
            
            */

            /* selected buoy. */
            int selectedRow = getView().getSelectedRowBuoysDatasets();                
            int idBuoy = (int) getView().getModelBuoysDatasets().getValueAt(selectedRow, 0);
            setSelectedIdBuoy(idBuoy);
            
            /* Sets the selected id Buoy by user to create dataset files. */
            setPathOfSelectedOriginalDataset(getPathDatasetFiles().get(index));
            
            /* Also in tab: Preprocess. */
            getView().setSelectedOriginalDataset(getModelDatasetFiles().get(index));
            getView().setSelectedStationIDPreprocess( (String) getView().getModelBuoysDatasets().getValueAt(selectedRow, 1));
            getView().setOpenedDataset(getModelDatasetFiles().get(index));
                                   
            
            /* Sets the selected dataset path file name by double click. */
            setSelectedDatasetToPreprocess(getPathDatasetFiles().get(index));                        

            
            /* Moves to tab Preprocess. */
            getView().setSelectedTab(2);   
            
            /* Sets Save button enabled.*/
            getView().setEnableSave(true);
            
            /* Enables 'Previous date' and 'Next date' button. */
            getView().setEnablePreviousDatePreprocess(true);
            getView().setEnableNextDatePreprocess(true);
            
            /* Gests the information to show abut the dataset file. */
            DatasetInformation datasetInfoLoaded = loadDatasetFile(getPathOfSelectedDatasetToPreprocess(),"Dataset");
            
            /* Sets datasetInfo loaded in memory. */
            setDatasetInformationLoaded(datasetInfoLoaded);
                        
            /* Shows data in tab: File information. */
            showDatasetFileSummary(datasetInfoLoaded);
            
            /* Checks if tab: View data has to reload the data. */
            if(getView().getTabbedPreprocessSelectedIndex() == 1){
                
                /* Data in tab: View data has to be reloaded because is selected. */
                
                reloadViewData();
                
            }else{
                
                /* Data in tab: View data has to be reloaded next time user click on it. */
                
                setReloadViewData(true);
            
            }
       
        }else{
        
            JOptionPane.showMessageDialog(null, "Please, select an intermediate dataset to open.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }
                
    }
    
    
    /**
     * User selected a preprocessed dataset file in tab 'Datasets'.
     */
    private void doDoubleClickonJlstPreprocessedDatasets(){

        /* Double click on jlstPreprocessFiles */
        
        /* In tab Preprocess sets the selected preprocess file to preprocess. */
        
        /* File name where user double clicked on. */
        int index = getView().getSelectedIndexPreprocessedDataset();

        if(index!=-1 && (getPreprocessingStarted()==false || closingCurrentPreprocessing()==true)){
            
            /* There is a selected preprocess file. */
              
            
            /* 
               Sets the selected id Buoy by user to create dataset files.
               To know idBuoy and dataset file that belongs the preprocessed file created.
            
            */                        
                        
            /* selected buoy. */
            int selectedRow = getView().getSelectedRowBuoysDatasets();                
            int idBuoy = (int) getView().getModelBuoysDatasets().getValueAt(selectedRow, 0);
            setSelectedIdBuoy(idBuoy);
            
            /* Sets the selected id Buoy by user to create dataset files. */
            setPathOfSelectedOriginalDataset(getPathDatasetFiles().get(getView().getSelectedIndexDataset()));
            
            /* Also in tab: Preprocess. */
            getView().setSelectedOriginalDataset(getModelDatasetFiles().get(getView().getSelectedIndexDataset()));
            getView().setSelectedStationIDPreprocess( (String) getView().getModelBuoysDatasets().getValueAt(selectedRow, 1));
            getView().setOpenedDataset(getModelPreprocessedDatasetFiles().get(index));
                        
            
            /* Sets the selected dataset path file name by double click. */
            setSelectedDatasetToPreprocess(getPathPreprocessedDatasetFiles().get(index));
            
            /* Moves to tab Preprocess. */
            getView().setSelectedTab(2);   
            
            /* Sets Save button enabled.*/
            getView().setEnableSave(true);
            
            /* Enables 'Previous date' and 'Next date' button. */
            getView().setEnablePreviousDatePreprocess(true);
            getView().setEnableNextDatePreprocess(true);
            
            
            /* Gests the information to show abut the dataset file. */
            DatasetInformation datasetInfoLoaded = loadDatasetFile(getPathOfSelectedDatasetToPreprocess(),"Preprocessed");
            
            /* Sets datasetInfo loaded in memory. */
            setDatasetInformationLoaded(datasetInfoLoaded);
                        
            /* Shows data in tab: File information. */
            showDatasetFileSummary(datasetInfoLoaded);
            
            /* Checks if tab: View data has to reload the data. */
            if(getView().getTabbedPreprocessSelectedIndex() == 1){
                
                /* Data in tab: View data has to be reloaded because is selected. */
                
                reloadViewData();
                
            }else{
                
                /* Data in tab: View data has to be reloaded next time user click on it. */
                
                setReloadViewData(true);
            
            }            
       
        }else{

            JOptionPane.showMessageDialog(null, "Please, select a pre-processed dataset to open.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }
                
    }
               
    
    /**
     * Opens NewDatasetFile view to create a new dataset file.
     */
    private void doNewDatasetFile(){
    
        /* Opens NewDatasetFile view to create a new dataset file. */
        
        /* Selected row. */
        int selectedRow = getView().getSelectedRowBuoysDatasets();
        
        if(selectedRow==-1){
            
            /* None buoy is selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a buoy.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }else{

        
            /* selected buoy. */
            int idBuoy = (int) getView().getModelBuoysDatasets().getValueAt(selectedRow, 0);
            
            /* Selected dataset file name to preprocess. */
            String selectedDatasetFilename=getPathOfSelectedOriginalDataset();
            

            /* Creates the view and controller. */
            InterfaceViewNewDatasetFile newDatasetFile = new NewDatasetFile(getView().getParent(), false);
            ControllerViewNewDatasetFile controller = new ControllerViewNewDatasetFile(newDatasetFile, idBuoy, selectedDatasetFilename);

            /* Sets the controller that will manage all events and shows the view. */
            newDatasetFile.setController(controller);

            /* Shows the view. */
            newDatasetFile.showView();

            /* Updates dataset files created. */
            buoyDatasetsSelectionChanged();
            

            /* Checks if the user has selected the same buoy in tab Datasets and Matching. */
            if(getView().getSelectedRowBuoysDatasets()==getView().getSelectedRowBuoysMatching()){
                
                /* Updates datasets list in Tab: Matching.*/
                buoyMatchingSelectionChanged();
            }
            
        }
    }
    
    
    /**
     * User selected a buoy in tab 'Datasets'.
     */
    private void buoyDatasetsSelectionChanged(){
    
        /* The user changed buoy selection. */
        
        /* Shows dataset files that belong to the selected buoy. */
        
        /* Clear previous data of the intermediate dataset files of the previous selected buoy. */
        getModelDatasetFiles().clear();
        getPathDatasetFiles().clear();
        
        /* Clear previous data of the preprocessed dataset files of the previous selected intermediate dataset file. */
        getModelPreprocessedDatasetFiles().clear();
        getPathPreprocessedDatasetFiles().clear();
        
        /* Selected buoy. */
        int selectedBuoy = getView().getSelectedRowBuoysDatasets();
                        
        if(selectedBuoy!=-1 && getView().getModelBuoysDatasets().getRowCount()>0){
            
            /* Checks if selected buoy was deleted. */
                         
            /* Id of the buoy selected. */
            int idBuoy = (int) getView().getModelBuoysDatasets().getValueAt(selectedBuoy, 0);
            
            /* Utilities. */
            Utils util = new Utils();

            
            /* A new buoy was selected. */

            
            /* Search for the .db Dataset files that belong to the buoy. */        
            util.searchFiles(idBuoy, getModelDatasetFiles(), new ArrayList<>(Arrays.asList("Datasets")), getPathDatasetFiles(), new ArrayList<>(Arrays.asList("DB Files", "DB", "db")));
            
            /* Updates in view the dataset files that belong to the buoy. */
            getView().setModelDataset(getModelDatasetFiles());
            
            /* Clear previous data of the dataset description. */
            getView().clearSummaryDatasets();
            
            /* Updates in view the dataset files that belong to the selected dataset file. */
            getView().setModelPreprocessedDataset(getModelPreprocessedDatasetFiles());

        }
    }


    /**
     * Deletes selected dataset file.
     */
    private void doDeleteDatasetFile(){
        
        /* Deletes the selected dataset file by user. */
        
        /* File to be deleted. */
        int selectedIndex = getView().getSelectedIndexDataset();
        
        if(selectedIndex == -1){
            
            JOptionPane.showMessageDialog(null, "Please, select an intermediate dataset file to delete.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Checks if user selected to delete the same dataset is working with. */
            
            if(getPathOfSelectedOriginalDataset().equals(getPathDatasetFiles().get(selectedIndex))){
                
                JOptionPane.showMessageDialog(null, "You can not delete the same intermediate dataset that you are currently pre-processing.", "Message", JOptionPane.WARNING_MESSAGE);
            
            }else{            

                Object[] options = {"Cancel", "Delete"};

                /* Ask the user that really want to delete the selected file. */

                /* Gets the file to delete. */
                File file = new File(getPathDatasetFiles().get(selectedIndex));

                if (JOptionPane.showOptionDialog(null, "Attention !!\n\nThe information of the intermediate dataset "+file.getName()+" and \n"
                        +"all the pre-processed datasets that belong to it will be erased."
                        +"\n\nClick on Cancel to abort or Delete to delete it.\n\n","Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1){

                    try {                    

                        /* Deletes selected file and preprocess files that belong to it. */

                        if (file.delete()){

                            /* success. */

                            /* Deletes preprocess files that belong to it. */

                            /* Utilities. */
                            Utils util = new Utils();

                            /* Selected row. */
                            int selectedRow = getView().getSelectedRowBuoysDatasets();

                            /* selected buoy. */
                            int idBuoy = (int) getView().getModelBuoysDatasets().getValueAt(selectedRow, 0);

                            /* Root that contains the folder of preprocess file of the selected dataset file. */
                            String root = System.getProperty("user.dir", ".")+File.separator+"DB"
                                +File.separator+"id"+Integer.toString(idBuoy)+File.separator+
                                "Preprocess"+File.separator+file.getName().substring(0, file.getName().length() - 3);


                            util.deleteDirectory(root);


                            /* Clear previous data of the preprocessed dataset files of the selected dataset file. */
                            getModelPreprocessedDatasetFiles().clear();
                            getPathPreprocessedDatasetFiles().clear();

                            /* Updates in view the dataset files that belong to the selected dataset file. */
                            getView().setModelPreprocessedDataset(getModelPreprocessedDatasetFiles());


                            /* Also deletes the .XML file. */
                            String filename = file.getCanonicalPath();

                            filename = filename.substring(0, filename.length() - 3) + ".xml";

                            File fileXML = new File(filename);

                            /* Deletes .XML file. */
                            fileXML.delete();

                            JOptionPane.showMessageDialog(null, "The intermediate dataset "+file.getName()+" and all the pre-processed datasets that belong to it have been deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);

                            /* Updates listModelDatasetFiles. */  
                            getModelDatasetFiles().remove(selectedIndex);

                            /* Removes deleted file. */
                            getPathDatasetFiles().remove(selectedIndex);

                            /* Clear previous data of the dataset description. */
                            getView().clearSummaryDatasets();
                            
                            /* Checks if the user has selected the same buoy in tab Datasets and Matching. */
                            if(getView().getSelectedRowBuoysDatasets()==getView().getSelectedRowBuoysMatching()){
                
                                /* Updates datasets list in Tab: Matching.*/
                                buoyMatchingSelectionChanged();
                            }

                        }else{

                            JOptionPane.showMessageDialog(null, "The intermediate dataset "+ file.getName() +" could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);

                        }

                    } catch (IOException ex) {
                        Logger.getLogger(ControllerViewManageBuoys.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }                       
        }
    }
    

    /**
     * Deletes selected preprocessed dataset file.
     */
    private void doDeletePreprocessedFile(){
        
        /* Deletes the selected preprocessd dataset file by user. */
        
        /* File to be deleted. */
        int selectedIndex = getView().getSelectedIndexPreprocessedDataset();
        
        if(selectedIndex == -1){
            
            JOptionPane.showMessageDialog(null, "Please, select a pre-processed dataset file to delete.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Checks if user selected to delete the same preprocessed dataset is working with. */
            
            if(getPathOfSelectedDatasetToPreprocess().equals(getPathPreprocessedDatasetFiles().get(selectedIndex))){
                
                JOptionPane.showMessageDialog(null, "You can not delete the same pre-processed dataset that you are currently pre-processing.", "Message", JOptionPane.WARNING_MESSAGE);
            
            }else{            

                Object[] options = {"Cancel", "Delete"};

                /* Ask the user that really want to delete the selected file. */

                /* Gets the file to delete. */
                File file = new File(getPathPreprocessedDatasetFiles().get(selectedIndex));

                if (JOptionPane.showOptionDialog(null, "Attention !!\n\nThe pre-processed dataset "+file.getName()+" will be erased."
                        +"\n\nClick on Cancel to abort or Delete to delete it.\n\n","Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1){

                    try {

                        /* Deletes selected preprocess file. */

                        if (file.delete()){

                            /* success. */

                            /* Also deletes the .XML file. */
                            String filename = file.getCanonicalPath();

                            filename = filename.substring(0, filename.length() - 3) + ".xml";

                            File fileXML = new File(filename);

                            /* Deletes .XML file. */
                            fileXML.delete();

                            JOptionPane.showMessageDialog(null, "The pre-processed dataset "+file.getName()+" has been deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);

                            /* Updates listModelDatasetFiles. */  
                            getModelPreprocessedDatasetFiles().remove(selectedIndex);

                            /* Removes deleted file. */
                            getPathPreprocessedDatasetFiles().remove(selectedIndex);

                            /* Clear previous data of the dataset description. */
                            getView().clearSummaryDatasets();
                            
                            /* Checks if the user has selected the same buoy in tab Datasets and Matching. */
                            if(getView().getSelectedRowBuoysDatasets()==getView().getSelectedRowBuoysMatching()){
                
                                /* Updates preprocessed datasets list in Tab: Matching.*/
                                datasetMatchingSelectionChanged();
                            }                            

                        }else{

                            JOptionPane.showMessageDialog(null, "The pre-processed dataset "+ file.getName() +" could not be deleted.", "Error", JOptionPane.ERROR_MESSAGE);

                        }

                    } catch (IOException ex) {
                        Logger.getLogger(ControllerViewManageBuoys.class.getName()).log(Level.SEVERE, null, ex);
                    }                                        
                }
            }
        }                       
    }
    

    /**
     * User selected a different dataset file in tab 'Datasets'.
     */
    private void datasetSelectionChanged(){
    
        /* Updates the information about the dataset file selected. */
        
        /* Gets the dataset file. */
        int selectedIndex = getView().getSelectedIndexDataset();
        
        if(selectedIndex != -1){
            
            /* Gets the name of the XML file. */
            String filename = getPathDatasetFiles().get(selectedIndex);
            
            /* Gets the information to show abut the preprocessed file. */
            DatasetInformation datasetInfo = getDatasetXMLInformation(filename);
            
            /* Clear previous data. */
            getView().clearSummaryDatasets();

            
            /* Shows the information. */

            if(datasetInfo!=null){

                /* Shows the XML file data. */

                /* Gets the view. */
                InterfaceViewManageBuoys theView = getView();

                theView.addLineSummaryDataset("File name dataset: " + datasetInfo.getFileNameDataset());
                theView.addLineSummaryDataset("\n\nDate of creation: " + datasetInfo.getDateCreation().getTime());
                theView.addLineSummaryDataset("\n\nDescription:\n" + datasetInfo.getShortDescription());

                theView.addLineSummaryDataset("\n\nFirst date: " + datasetInfo.getFirstDate());
                theView.addLineSummaryDataset("\nLast date: " + datasetInfo.getLastDate());                                
                theView.addLineSummaryDataset("\nNumber of instances: " + datasetInfo.getNumInstances());

                theView.addLineSummaryDataset("\n\nAnnual text files merged:\n");

                /* Annual text files merged in dataset file. */
                for (String fileMerged : datasetInfo.getTXTFiles()) {

                    theView.addLineSummaryDataset("  " + fileMerged + "\n");

                }

                /* Missing or duplicated dates founds. */
                ArrayList<String> missingDates = datasetInfo.getMissingDates();
                
                if(missingDates.isEmpty()==true){
                    
                    theView.addLineSummaryDataset("\nMissing or duplicated dates:\n  Not found.\n");
                
                }else{
                    
                    theView.addLineSummaryDataset("\nMissing or duplicates dates: " + missingDates.size() + "\n");
                    
                    /* Missing or duplicates dates. */
                    for (String date : missingDates) {

                        theView.addLineSummaryDataset("  " + date + "\n");

                    }
                }
            }
            
            
            /* Shows preprocess files that belong to the selected dataset file. */

            
            /* Gets the name of the selected dataset file. */
            filename = getModelDatasetFiles().get(selectedIndex);
            filename = filename.substring(0, filename.length() - 3);

            /* Utilities. */
            Utils util = new Utils();


            /* A new dataset file was selected. */

            /* Preprocess files */               
            getModelPreprocessedDatasetFiles().clear();
            getPathPreprocessedDatasetFiles().clear();
            
            /* Selected row. */
            int selectedRow = getView().getSelectedRowBuoysDatasets();
                        
            /* selected buoy. */
            int idBuoy = (int) getView().getModelBuoysDatasets().getValueAt(selectedRow, 0);

            /* Search for the .db Preprocess files that belong to the buoy. */        
            util.searchFiles(idBuoy, getModelPreprocessedDatasetFiles(), new ArrayList<>(Arrays.asList("Preprocess"+File.separator+filename)), getPathPreprocessedDatasetFiles(), new ArrayList<>(Arrays.asList("DB Files", "DB", "db")));

            /* Updates in view the dataset files that belong to the selected dataset file. */
            getView().setModelPreprocessedDataset(getModelPreprocessedDatasetFiles());

        }
    }
    

    /**
     * User selected a different preprocessed dataset file in tab 'Datasets'.
     */
    private void preprocessedSelectionChanged(){
    
        /* Updates the information about the preprocess file selected. */
        
        /* Gets the preprocess file. */
        int selectedIndex = getView().getSelectedIndexPreprocessedDataset();
        
        if(selectedIndex != -1){            
            
            /* Gets the name of the XML file. */
            String filename = getPathPreprocessedDatasetFiles().get(selectedIndex);

            /* Gets the information to show abut the preprocessed file. */
            DatasetInformation datasetInfo = getDatasetXMLInformation(filename);
            
            /* Clear previous data. */
            getView().clearSummaryDatasets();
            
            /* Shows the information. */
              
            if(datasetInfo!=null){

                /* Shows the XML file data. */

                /* Gets the view. */
                InterfaceViewManageBuoys theView = getView();
                                       
                theView.addLineSummaryDataset("File name dataset: " + datasetInfo.getFileNameDataset());
                theView.addLineSummaryDataset("\n\nDate of creation: " + datasetInfo.getDateCreation().getTime());
                theView.addLineSummaryDataset("\n\nDescription:\n" + datasetInfo.getShortDescription());
                    
                theView.addLineSummaryDataset("\n\nFirst date: " + datasetInfo.getFirstDate());
                theView.addLineSummaryDataset("\nLast date: " + datasetInfo.getLastDate());                
                theView.addLineSummaryDataset("\nNumber of instances: " + datasetInfo.getNumInstances());
                    
                theView.addLineSummaryDataset("\n\nAnnual text files merged:\n");

                /* Annual text files merged in dataset file. */
                for (String fileMerged : datasetInfo.getTXTFiles()) {

                    theView.addLineSummaryDataset("  " + fileMerged + "\n");

                }
                
                /* Missing or duplicated dates founds. */
                ArrayList<String> missingDates = datasetInfo.getMissingDates();
                
                if(missingDates.isEmpty()==true){
                    
                    theView.addLineSummaryDataset("\nMissing or duplicated dates:\n  Not found.\n");
                
                }else{
                    
                    theView.addLineSummaryDataset("\nMissing or duplicates dates: " + missingDates.size() + "\n");
                    
                    /* Missing or duplicates dates. */
                    for (String date : missingDates) {

                        theView.addLineSummaryDataset("  " + date + "\n");

                    }
                }
                
                theView.addLineSummaryDataset("\nPre-processing applied:\n");
            
                /* Preprocessing applied to dataset file. */
                for (String preprocessing : datasetInfo.getPreprocessing()) {

                    theView.addLineSummaryDataset("  " + preprocessing + "\n");

                }
            }
        }
    }

        
    
    
    /*  Tab: Preprocess */    
    
    
    /**
     * Opens OpenDatasetFile view to select a file to preprocess.
     */
    private void doOpenDataset(){
        
        /* Opens OpenDatasetFile view to select a file to preprocess. */
        
        
        /* Checks if user is preprocessing a dataset/preprocessed dataset file. */
        
        Object[] options = {"Cancel", "Open dataset"};
        
        if(getPreprocessingStarted()==false ||          
                
           JOptionPane.showOptionDialog(null, "Attention !!\n\nThe current pre-processed information will be lost. Please, save it before continuing."
                +"\n\nClick on Cancel to abort or Open dataset to continue.\n\n","Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1
            ){

                /* Opens OpenDatasetFile view to select a file to preprocess. */
        
                /* Creates the view and controller. */
                InterfaceViewOpenDatasetFile newOpenDataset = new OpenDatasetFile(getView().getParent(), false);
                ControllerViewOpenDatasetFile controller = new ControllerViewOpenDatasetFile(newOpenDataset, getModelBuoys());

                /* Sets the controller that will manage all events and shows the view. */
                newOpenDataset.setController(controller);

                /* Shows the view. */
                newOpenDataset.showView();

                /* Checks if user selected a dataset file. */
                if(controller.getUserSelectedAFile() == true){
                                        
                    /* After opening a new dataset/preprocessed dataset file there is not a preprocessing in progress. */
                    closeCurrentPreproccessing();
                    
                    /* Sets Save button enabled.*/
                    getView().setEnableSave(true);

                    /* 
                       Sets the selected id Buoy by user to create dataset files.
                       To know idBuoy and dataset file that belongs the preprocessed file created.

                    */

                    /* selected buoy. */
                    setSelectedIdBuoy(controller.getSelectedIdBuoy());

                    /* Sets the selected dataset by user to create dataset files. */
                    setPathOfSelectedOriginalDataset(controller.getPathDatasetFile());
                    
                    /* Sets the dataset file selected by user. */
                    setSelectedDatasetToPreprocess(controller.getPathPreprocessedDatasetFile());

                    /* Also in tab: Preprocess. */
                    getView().setSelectedStationIDPreprocess(controller.getSelectedStationID());
                    getView().setSelectedOriginalDataset(controller.getDatasetFilename());
                    getView().setOpenedDataset(controller.getPreprocessedDatasetFilename());
                    
                    /* Enables 'Previous date' and 'Next date' button. */
                    getView().setEnablePreviousDatePreprocess(true);
                    getView().setEnableNextDatePreprocess(true);

                    /* Gets the information to show abut the dataset file. */
                    DatasetInformation datasetInfoLoaded = loadDatasetFile(getPathOfSelectedDatasetToPreprocess(), controller.getTypeSelectedDatasetFile());

                    /* Sets datasetInfo loaded in memory. */
                    setDatasetInformationLoaded(datasetInfoLoaded);

                    /* Shows data in tab: File information. */
                    showDatasetFileSummary(datasetInfoLoaded);
                    
                    /* Checks if tab: View data has to reload the data. */
                    if(getView().getTabbedPreprocessSelectedIndex() == 1){

                        /* Data in tab: View data has to be reloaded because is selected. */

                        reloadViewData();

                    }else{

                        /* Data in tab: View data has to be reloaded next time user click on it. */

                        setReloadViewData(true);

                    }  
                    
                    /* Sets buoy selection. */
                    getView().setSelectedRowBuoysDatasets(controller.getView().getSelectedRowBuoy());
                    buoyDatasetsSelectionChanged();                    
                    
                    /* Sets dataset selection. */
                    getView().setSelectedIndexDataset(controller.getView().getSelectedIndexDataset());
                    datasetSelectionChanged();

                }
        }
    }

        
    /**
     * Saves preprocessed dataset file on disk.
     */
    private void doSavePreprocessedFile(){
    
        /* Checks if there are instances to write on dataset file. */
        
        if(getPreprocessDataset().getInstancesWEKA().getNumberOfInstances()==0){
            
            /* No instances to save. */
            
            JOptionPane.showMessageDialog(null, "The are no instances in pre-processed dataset to save.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }else{    
            
            /* There are instances to write. */
            
            /* Opens NewPreprocessedDatasetFile view to create a new preprocessed dataset file. */

            /* Creates the view and controller. */
            InterfaceViewNewPreprocessedDatasetFile newPreprocessedDatasetFile = new NewPreprocessedDatasetFile(getView().getParent(), false);            
            ControllerViewNewPreprocessedDatasetFile controller = new ControllerViewNewPreprocessedDatasetFile(newPreprocessedDatasetFile, getSelectedIdBuoy(), getView().getSelectedOriginalDataset(), getDatasetInformationLoaded(), getPreprocessDataset());

            /* Sets the controller that will manage all events and shows the view. */
            newPreprocessedDatasetFile.setController(controller);

            /* By default, short description of current selected dataset is showed. */
            newPreprocessedDatasetFile.setShortDescription(getDatasetInformationLoaded().getShortDescription());

            /* Shows the view. */
            newPreprocessedDatasetFile.showView();

            /* Checks if user saved the preprocessed dataset file. */

            if(controller.getUserSavedPreprocessedDataset()==true){

                /* After saving there is not a preprocessing in progress. */
                closeCurrentPreproccessing();            

                /* Updates preprocessed dataset files created. */
                datasetSelectionChanged();

                /* Selects the preprocessed dataset file saved. */
                getView().setSelectedPreprocessedDataset(controller.getPreprocessedDatasetFilename());

                /* Opens it. */
                doDoubleClickonJlstPreprocessedDatasets();
                
                /* Checks if the user has selected the same buoy and Dataset file
                   int tab Matching. */
                if(getView().getSelectedRowBuoysDatasets()==getView().getSelectedRowBuoysMatching() &&
                   getView().getSelectedIndexDataset()==getView().getSelectedIndexMatchingDataset()){

                    /* Updates preprocessed datasets list in Tab: Matching.*/
                    datasetMatchingSelectionChanged();
                }

            }
                
        }
                        
    }
    

    /**
     * Loads dataset file's summary.
     * @param filename Dataset file's name.
     * @param type Type of the datase file 'Dataset/Preprocessed' to load.
     * @return Dataset file's summary.
     */
    private DatasetInformation loadDatasetFile(String filename, String type){
        
        /* 
           Loads dataset file data into memory, 
           and returns the information stored in .XML file.
        */
        
        /* Information to show about the dataset file. */
        DatasetInformation datasetInfoLoaded = getDatasetXMLInformation(filename);
      
        /* Loads the information. */
        
        if(datasetInfoLoaded!=null){
            
            /* Loads dataset file information in memory to work with. */
            
            /* Dataset file to load. */
            DatasetFile fileDataset = new DatasetFile();

            /* To store the information contained in the dataset/preprocessed file. */
            PreprocessDatasetFile preprocessDatasetLoaded = new PreprocessDatasetFile();

            /* Gets the dataset file selected by user and reads it. */
            fileDataset.setFileName(filename);

            if (fileDataset.readFile(type, datasetInfoLoaded.getHeaderDatasetFile()) == true){

                /* Gets into memory information of dataset/preprocessed file received. */
                preprocessDatasetLoaded.setDatasetFile(fileDataset);

                /* Sets information to work with. */
                setPreprocessDataset(preprocessDatasetLoaded);
            
                /* Shows the XML file data. */
            
                /* Checks if statistics about each attribute were calculated. */
            
                if(datasetInfoLoaded.getStatistics().isEmpty()){
                
                    /* Statistics were not calculated. */
                
                    /* Calculates statistics and save them in .XML file */
                        
                    /* To store the statistics. */
                    ArrayList<DatasetInformation.AttributeStatistics> statistics;

                    /* Calculates statistics of dataset file. */
                    statistics=getPreprocessDataset().calculateAttributeStatistics();

                    /* Sets the statistics to be saved in XML file. */
                    datasetInfoLoaded.setStatistics(statistics);

                    /* Gets the XML file name. */
                    String filenameXML= filename.substring(0, filename.length() - 3) + ".xml";

                    /* Creates the .XML file with the previous data and the statistics. */
                    File xml = new File(filenameXML);

                    XMLFile<DatasetInformation> xmlFile = new XMLFile<>(DatasetInformation.class, xml);

                    if (xmlFile.writeXMLFile(datasetInfoLoaded) == false){

                        /* There was an error while creating the .XML file. */ 
                        JOptionPane.showMessageDialog(null, "The file "+ xml.getName() +" could not be created.", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }else{
                
                /* There was an error while reading the selected dataset file. */
                
                JOptionPane.showMessageDialog(null, "The file "+ filename +" could not be readed.", "Error", JOptionPane.ERROR_MESSAGE);
            }                        
        }
        
        return datasetInfoLoaded;
        
    }        
    

    /**
     * Shows dataset file's summary.
     * @param datasetInfo Dataset file's summary.
     */
    private void showDatasetFileSummary(DatasetInformation datasetInfo){
           
        /* Shows summary in tab: File information. */
        
        /* Clear previous data. */
        getView().clearSummaryPreprocessed();
        
        /* Shows the information. */
        
        if(datasetInfo!=null){

            /* Shows the XML file data. */

            /* Gets the view. */
            InterfaceViewManageBuoys theView = getView();

            theView.addLineSummaryPreprocessed("File name dataset: " + datasetInfo.getFileNameDataset());
            theView.addLineSummaryPreprocessed("\n\nDate of creation: " + datasetInfo.getDateCreation().getTime());
            theView.addLineSummaryPreprocessed("\n\nDescription:\n" + datasetInfo.getShortDescription());

            theView.addLineSummaryPreprocessed("\n\nFirst date: " + datasetInfo.getFirstDate());
            theView.addLineSummaryPreprocessed("\nLast date: " + datasetInfo.getLastDate());            
            theView.addLineSummaryPreprocessed("\nNumber of instances: " + datasetInfo.getNumInstances());

            theView.addLineSummaryPreprocessed("\n\nAnnual text files merged:\n");

            /* Annual text files merged in dataset file. */
            for (String fileMerged : datasetInfo.getTXTFiles()) {

                theView.addLineSummaryPreprocessed("  " + fileMerged + "\n");

            }
            
            /* Missing or duplicated dates founds. */
            ArrayList<String> missingDates = datasetInfo.getMissingDates();

            if(missingDates.isEmpty()==true){

                theView.addLineSummaryPreprocessed("\nMissing or duplicated dates:\n  Not found.\n");

            }else{

                theView.addLineSummaryPreprocessed("\nMissing or duplicates dates: " + missingDates.size() + "\n");

                /* Missing or duplicates dates. */
                for (String date : missingDates) {

                    theView.addLineSummaryPreprocessed("  " + date + "\n");

                }
            }

            theView.addLineSummaryPreprocessed("\nPre-processing applied:\n");
            
            /* Preprocessing applied to dataset file. */
            for (String preprocessing : datasetInfo.getPreprocessing()) {

                theView.addLineSummaryPreprocessed("  " + preprocessing + "\n");

            }            
            
            /* Shows statistics about each attribute. */
            showDatasetFileStatistics(datasetInfo.getStatistics());            
            
        }
    }
    
            
    /**
     * Shows attributes's statistics of dataset file.
     * @param attributeStatistics Attributes's statistics
     */
    private void showDatasetFileStatistics(ArrayList<DatasetInformation.AttributeStatistics> attributeStatistics){
           
        /* Shows statistics about each attribute in tab: File information. */
        
        /* Gets the view. */
        InterfaceViewManageBuoys theView = getView();
                   
        /* Datamodel of table that shows attribute's name.*/
        DefaultTableModel modelAttributes = theView.getModelAttributes();

        /* Deletes previous data. */
        modelAttributes.setRowCount(0);

        /* Datamodel of table that shows statistic's attribute. */
        DefaultTableModel modelStatistics = theView.getModelStatistics();

        /* Deletes previous data. */
        modelStatistics.setRowCount(0);


        /* Sets all attributes and statistics.*/
        for (DatasetInformation.AttributeStatistics attribute : attributeStatistics) {

            /* Adds a attribute's name. */
            modelAttributes.addRow(new Object[]{attribute.getAttributeName()});                

            /* Statistics of attribute. */
            ArrayList<DatasetInformation.Statistic> statistics = attribute.getAttributeStatistics();

            for (DatasetInformation.Statistic statistic : statistics) {

                /* Adds attribute's name, statistic's name and value. */
                modelStatistics.addRow(new Object[]{attribute.getAttributeName(), statistic.getName(), statistic.getValue()});

            }                
        }           

        /* Sets both model to show the information. */
        theView.setModelAttributes(modelAttributes);
        theView.setModelStatistics(modelStatistics);

        /* Selects the first attribute in table. */
        theView.setSelectedRowAttributes(0);

    }    
                        

    /**
     * User selected a differente attribute.
     */
    private void attributeSelectionChanged(){
        
        /* The user changed attribute selection. */
        
        /* Filters by attribute's name. */
        
        /* Selected attribute. */
        int selectedAttribute = getView().getSelectedRowAttributes();
        
        if(selectedAttribute!=-1){
            
            /* Attribute's name. */
            String attribute = (String) getView().getModelAttributes().getValueAt(selectedAttribute, 0);

            /* Filter. */
            getView().setStatisticsFilterByAttribute(attribute);

        }        
        
    }
    

/*  Tab: Preprocess */
    

    /**
     * User selected a different filter.
     */
    private void filterSelectionChanged(){
        
        /* Sets the selected filter by user. */
      
        /* Gets selected filter. */
        String filterName=getView().getSelectedFilterName();
                
        switch (filterName) {
                
            
            /* Filters for attributes. */
            

            case "Normalize":
                {
                    /* Creates the selected filter by user. */
                    Normalize filter = new Normalize();                    
                    
                    /* There is no class. */
                    filter.setIgnoreClass(true);
                    
                    /* Sets the filter. */
                    GenericFilter<Normalize> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);
                    
                }
                break;
                
            case "Remove":
                {
                    /* Creates the selected filter by user. */
                    Remove filter = new Remove();                    
                    
                    /* Sets the filter. */
                    GenericFilter<Remove> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);

                }
                break;

            case "RemoveByName":
                {
                    /* Creates the selected filter by user. */
                    RemoveByName filter = new RemoveByName();                        
                    
                    /* Sets the filter. */
                    GenericFilter<RemoveByName> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);
                      
                }
                break;

            case "ReplaceMissingValues":
                {
                    /* Creates the selected filter by user. */
                    ReplaceMissingValues filter = new ReplaceMissingValues();
                    
                    /* There is no class. */
                    filter.setIgnoreClass(true);                    
                    
                    /* Sets the filter. */
                    GenericFilter<ReplaceMissingValues> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);
                    
                }    
                break;
                
            case "ReplaceMissingWithUserConstant":
                {
                    /* Creates the selected filter by user. */
                    ReplaceMissingWithUserConstant filter = new ReplaceMissingWithUserConstant();
                    
                    /* There is no class. */
                    filter.setIgnoreClass(true);                                        
                    
                    /* Sets the filter. */
                    GenericFilter<ReplaceMissingWithUserConstant> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);
                    
                }    
                break;
                
                
            /* Filters for instances. */

                
            case "RemoveDuplicates":
                {
                    /* Creates the selected filter by user. */
                    RemoveDuplicates filter = new RemoveDuplicates();                      
                                        
                    /* Sets the filter. */
                    GenericFilter<RemoveDuplicates> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);

                }    
                break;

            case "RemoveWithValues":
                {
                    /* Creates the selected filter by user. */
                    RemoveWithValues filter = new RemoveWithValues();
                    
                    /* Filter instances after applying it. */
                    filter.setDontFilterAfterFirstBatch(false);                    
                    
                    /* Sets the filter. */
                    GenericFilter<RemoveWithValues> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);
                    
                }    
                break;

            case "SubsetByExpression":
                {
                    /* Creates the selected filter by user. */
                    SubsetByExpression filter = new SubsetByExpression();
                    
                    /* Filter instances after applying it. */
                    filter.setFilterAfterFirstBatch(true);                                        
                                        
                    /* Sets the filter. */
                    GenericFilter<SubsetByExpression> generic = new GenericFilter<>(filter, filterName);
                    setSelectedFilter(generic);

                }    
                break;

            default: 
                
                if(getSelectedFilter()!=null) {
                    
                    /* There was selected a filter. */
                    
                    /* Now, none filter is selected. */
                    getSelectedFilter().setFilterName("");
                
                }

        }
                
    }
                                

    
    /**
     * Applies the selected filter by user.
     */
    private void doApplyFilter(){
    
        /* Applies the selected filter by user. */

        /* Checks if there is a selected filter to apply. */
        
        if(getView().getSelectedRowFilter()==-1 || getView().getSelectedFilterName().equals("Attribute") ||
           getView().getSelectedFilterName().equals("Instance") ||
           getView().getSelectedFilterName().equals("Recover missing data")){
            
            /* There is not a selected filter. */            
            JOptionPane.showMessageDialog(null, "Please, select a filter to apply.", "Message", JOptionPane.INFORMATION_MESSAGE);                        
                    
        }else{
            
            /* Checks if there is a selected dataset/preprocessed dataset to work with. */
        
            if(getPathOfSelectedDatasetToPreprocess().equals("")){
            
                /* There is not a selected dataset/preprocessed dataset to work with. */
            
                JOptionPane.showMessageDialog(null, "Please, select an intermediate or pre-processed dataset to work with.", "Message", JOptionPane.INFORMATION_MESSAGE);            
        
            }else{
                
                if(getPreprocessDataset().getInstancesWEKA().getAttributes().size()==1){
                
                    /* The dataset only contains the attribute: DATE */
                    JOptionPane.showMessageDialog(null, "The current pre-processed dataset only contains the attribute: DATE", "Message", JOptionPane.INFORMATION_MESSAGE);
                
                }else{
            
                    /* There is a selected a filter and a dataset/preprocessed dataset to work with. */
        
                    /* To know if the filter was properly applied. */
                    boolean filterApplied=true;
                    
                    try{

                        /* To check if attribute WVHT has been deleted after applying the filter. */
                        int indexOfAttributeWVHT = getPreprocessDataset().getInstancesWEKA().getIndexOfAttribute("WVHT");

                        /* To check if attribute APD has been deleted after applying the filter. */
                        int indexOfAttributeAPD = getPreprocessDataset().getInstancesWEKA().getIndexOfAttribute("APD");                                        

                        /* Gets the instances to work with. */
                        Instances instances = getPreprocessDataset().getInstancesWEKA().getInstances();

                        /* New intances after applying the filter. */
                        Instances newInstances=null;

                        /* Gets selected filter. */
                        String filterName=getView().getSelectedFilterName();                    



                        /* Filters for attributes. */


                        switch (filterName) {

                            case "Normalize":
                                {
                                    /* Filter to apply. */
                                    Normalize normalize = (Normalize)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    normalize.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, normalize);


                                        /* DATE field can not be normalized. */

                                        /* Restores previous values. */

                                        int numInstances = instances.size();

                                        for(int i=0; i<numInstances; i++){

                                            newInstances.get(i).setValue(0, instances.get(i).value(0));

                                        }
                                    }

                                }
                                break;


                            case "Remove":
                                {
                                    /* Filter to apply. */
                                    Remove remove = (Remove)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    remove.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, remove);
                                        
                                    }

                                }
                                break;


                            case "RemoveByName":
                                {
                                    /* Filter to apply. */
                                    RemoveByName removebyName = (RemoveByName)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    removebyName.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, removebyName);
                                        
                                    }

                                }
                                break;


                            case "ReplaceMissingValues":
                                {
                                    /* Filter to apply. */
                                    ReplaceMissingValues replaceMissingValues = (ReplaceMissingValues)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    replaceMissingValues.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, replaceMissingValues);                                
                                        
                                    }

                                }    
                                break;


                            case "ReplaceMissingWithUserConstant":
                                {
                                    /* Filter to apply. */
                                    ReplaceMissingWithUserConstant replaceMissingWithUserConstant = (ReplaceMissingWithUserConstant)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    replaceMissingWithUserConstant.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, replaceMissingWithUserConstant);
                                        
                                    }
                                }    
                                break;



                            /* Filters for instances. */


                            case "RemoveDuplicates":
                                {
                                    /* Filter to apply. */
                                    RemoveDuplicates removeDuplicates = (RemoveDuplicates)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    removeDuplicates.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, removeDuplicates);
                                        
                                    }
                                    
                                }    
                                break;


                            case "RemoveWithValues":
                                {
                                    /* Filter to apply. */
                                    RemoveWithValues removeWithValues = (RemoveWithValues)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    removeWithValues.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, removeWithValues);
                                        
                                    }
                                    
                                }    
                                break;


                            case "SubsetByExpression":
                                {
                                    /* Filter to apply. */
                                    SubsetByExpression subsetByExpression = (SubsetByExpression)getSelectedFilter().getFilter();

                                    /* Loads instances into filter. */            
                                    subsetByExpression.setInputFormat(instances);
                                    
                                    if(filterApplied==true){

                                        /* Applies the filter and gets new instances. */
                                        newInstances =  Filter.useFilter(instances, subsetByExpression);
                                        
                                    }
                                    
                                }    
                                break;


                            /* Filter: Recover missing data. */


                            case "Replace missing values with next nearest hour":
                                {

                                    /* Filter to apply and sets instances. */
                                    RecoveringFilters nextNearestNonMissingHour = new RecoveringFilters();
                                    nextNearestNonMissingHour.setInstancesWEKA(instances);
                                    

                                    /* Applies the filter. */
                                    nextNearestNonMissingHour.missingToNextNearestNonMissing();

                                    /* Gets new instances. */
                                    newInstances = nextNearestNonMissingHour.getInstancesWEKA().getInstances();

                                }    
                                break;


                            case "Replace missing values with previous nearest hour":
                                {
                                    /* Filter to apply and sets instances. */
                                    RecoveringFilters previousNearestNonMissingHour = new RecoveringFilters();
                                    previousNearestNonMissingHour.setInstancesWEKA(instances);

                                    /* Applies the filter. */
                                    previousNearestNonMissingHour.missingToPreviousNearestNonMissing();

                                    /* Gets new instances. */
                                    newInstances = previousNearestNonMissingHour.getInstancesWEKA().getInstances();

                                }    
                                break;                            


                            case "Replace missing values with next hours mean":
                                {
                                    /* Filter to apply and sets instances and the number of next hours to use. */
                                    RecoveringFilters fiveNextHoursMean = new RecoveringFilters(getRecoveringFilterConfiguration(0));
                                    fiveNextHoursMean.setInstancesWEKA(instances);

                                    /* Applies the filter. */
                                    fiveNextHoursMean.missingToNextNonMissinghoursMean();

                                    /* Gets new instances. */
                                    newInstances = fiveNextHoursMean.getInstancesWEKA().getInstances();
                                }    
                                break;


                            case "Replace missing values with previous hours mean":
                                {
                                    /* Filter to apply and sets instances and the number of previous hours to use. */
                                    RecoveringFilters fivePreviousHoursMean = new RecoveringFilters(getRecoveringFilterConfiguration(1));
                                    fivePreviousHoursMean.setInstancesWEKA(instances);

                                    /* Applies the filter. */
                                    fivePreviousHoursMean.missingToPreviousNonMissinghoursMean();

                                    /* Gets new instances. */
                                    newInstances = fivePreviousHoursMean.getInstancesWEKA().getInstances();
                                }
                                break;


                            case "Replace missing values with symmetric hours mean":
                                {
                                    /* Filter to apply and sets instances and the number of symmetric hours to use. */
                                    RecoveringFilters symmetricThreeHoursMean = new RecoveringFilters(getRecoveringFilterConfiguration(2));
                                    symmetricThreeHoursMean.setInstancesWEKA(instances);

                                    /* Applies the filter. */
                                    symmetricThreeHoursMean.missingToSymmetricNonMissinghoursMean();

                                    /* Gets new instances. */
                                    newInstances = symmetricThreeHoursMean.getInstancesWEKA().getInstances();
                                }
                                break;

                        }
                        
                        /* Checks if the filter was properly applied. */
                        if(filterApplied==true && newInstances!=null){
                            
                            /* The filter was properly applied. */

                            /* Checks if DATE field has been removed after applying the selected filter. */

                            if(!newInstances.attribute(0).name().equals("DATE")) {

                                /* DATE field was removed. */

                                JOptionPane.showMessageDialog(null, "The selected filter can not be applied because DATE field can not be removed.", "Message", JOptionPane.WARNING_MESSAGE);

                            } else {

                                /* DATE field was not removed. */

                                /* Makes a backup of instances loaded in memory. */
                                getPreprocessDataset().makeBackupOfWEKAInstances();

                                /* Sets the new instances in PreprocessDataset loaded in memory. */
                                getPreprocessDataset().getInstancesWEKA().setInstances(newInstances);

                                /* Updates the new summary information after applying the filter. */
                                DatasetInformation datasetInfo = getDatasetInformationLoaded();

                                /* Calculates new statistics. */
                                datasetInfo.setNumInstances(newInstances.size());
                                datasetInfo.setStatistics(getPreprocessDataset().calculateAttributeStatistics());

                                /* Check missing or duplicated dates in dataset created. */
                                ArrayList<String> missingDatesFound = getPreprocessDataset().checkMissingDates();

                                /* Sets missing dates in dataset created. */
                                datasetInfo.setMissingDates(missingDatesFound);


                                /* Updates preprocessing information. */
                                String preprocessing = "Filter: " + filterName;
                                datasetInfo.getPreprocessing().add(preprocessing);

                                /* Sets the new summary after applying the filter. */
                                setDatasetInformationLoaded(datasetInfo);

                                /* Shows statistics about each attribute. */
                                showDatasetFileStatistics(datasetInfo.getStatistics());

                                /* Updates information in tab: Preprocess. */
                                Calendar today = GregorianCalendar.getInstance();

                                getView().addLineSummaryPreprocessed("\n    "+today.getTime());
                                getView().addLineSummaryPreprocessed("\n       "+ preprocessing);
                                getView().addLineSummaryPreprocessed("\n       Current number of instances: " + datasetInfo.getNumInstances());
                                getView().addLineSummaryPreprocessed("\n       Current missing or duplicates dates: " + missingDatesFound.size() + "\n");

                                /* Enables Undo button. */
                                getView().setEnableUndo(true);

                                /* Enables Restore data button. */
                                getView().setEnableRestoreData(true);

                                /* The user started the preprocessing of a selected dataset/preprocessed dataset file. */
                                setPreprocessingStarted(true);

                                /* Checks if tab: View data has to reload the data. */
                                if(getView().getTabbedPreprocessSelectedIndex() == 1){

                                    /* Data in tab: View data has to be reloaded because is selected. */

                                    reloadViewData();

                                }else{

                                    /* Data in tab: View data has to be reloaded next time user click on it. */

                                    setReloadViewData(true);

                                }            

                                JOptionPane.showMessageDialog(null, preprocessing+" has been properly applied.", "Message", JOptionPane.INFORMATION_MESSAGE);


                                /* Checks if APD attribute was removed. */
                                if(indexOfAttributeAPD>0 && getPreprocessDataset().getInstancesWEKA().getIndexOfAttribute("APD")<0){

                                    JOptionPane.showMessageDialog(null, "Attention !!\n\nThe APD attribute has been removed.\nThis attribute is necessary in matching process for calculating the flux of energy.", "Message", JOptionPane.WARNING_MESSAGE);

                                }

                                /* Checks if WVHT attribute was removed. */
                                if(indexOfAttributeWVHT>0 && getPreprocessDataset().getInstancesWEKA().getIndexOfAttribute("WVHT")<0){

                                    JOptionPane.showMessageDialog(null, "Attention !!\n\nThe WVHT attribute has been removed.\nThis attribute is necessary in matching process for calculating the flux of energy.", "Message", JOptionPane.WARNING_MESSAGE);

                                }

                            }
                            
                        }

                    } catch (Exception ex) {
                        
                        /* There was an error while applying the filter. */
                        filterApplied=false;

                        JOptionPane.showMessageDialog(null, "Filter: " + getView().getSelectedFilterName()+" could not be applied.\n\nCause of error: "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        }
    }
    
    
    /**
     * Undoes the last filter applied.
     */
    private void doUndo(){
    
        /* Undoes the last filter applied. */
                       
        /* Restores instances backup of the PreprocessDataset loaded in memory. */
        getPreprocessDataset().restoreBackupOfWEKAInstances();
            
        /* Gets the current summary information (after applying the filter). */
        DatasetInformation datasetInfo = getDatasetInformationLoaded();

        /* Calculates older statistics, the same before applying the filter. */
        datasetInfo.setNumInstances(getPreprocessDataset().getInstancesWEKA().getNumberOfInstances());
        datasetInfo.setStatistics(getPreprocessDataset().calculateAttributeStatistics());
        
        /* Check missing or duplicated dates in dataset created. */
        ArrayList<String> missingDates = getPreprocessDataset().checkMissingDates();

        /* Sets missing dates in dataset created. */
        datasetInfo.setMissingDates(missingDates);
        
        
        /* Removes last filter applied from preprocessing dataset information. */
        int numPreprocessing = datasetInfo.getPreprocessing().size();
        datasetInfo.getPreprocessing().remove(numPreprocessing-1);
        
        /* Sets the new summary after undoing the filter. */
        setDatasetInformationLoaded(datasetInfo);
            
        /* Shows statistics about each attribute. */
        showDatasetFileStatistics(datasetInfo.getStatistics());
        
        /* Updates information in tab: Preprocess. */
        Calendar today = GregorianCalendar.getInstance();

        getView().addLineSummaryPreprocessed("\n    "+today.getTime());        
        getView().addLineSummaryPreprocessed("\n       Undo last filter applied.");
        getView().addLineSummaryPreprocessed("\n       Current number of instances: " + datasetInfo.getNumInstances());
        getView().addLineSummaryPreprocessed("\n       Current missing or duplicates dates: " + missingDates.size() + "\n");
        
        /* Disables Undo button. It will be enabled next time user applies a filter. */
        getView().setEnableUndo(false);
        
        /* Checks if tab: View data has to reload the data. */
        if(getView().getTabbedPreprocessSelectedIndex() == 1){

            /* Data in tab: View data has to be reloaded because is selected. */

            reloadViewData();

        }else{

            /* Data in tab: View data has to be reloaded next time user click on it. */

            setReloadViewData(true);

        }            
        
        JOptionPane.showMessageDialog(null, "Undo last filter properly applied.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        
    }
    

    /**
     * Restores original data.
     */
    private void doRestoreData(){
    
        /* Restores original data. */
        
        /* Ask the user that really want to restore the original data. */
        
        Object[] options = {"Cancel", "Restore data"};
        
        if(JOptionPane.showOptionDialog(null, "Attention !!\n\nThe current pre-processed information will be lost. Please, save it before continuing."
                +"\n\nClick on Cancel to abort or Restore data to continue.\n\n","Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1
            ){

                /* Restores data to original data. */
        
                /* Restores original instances of the PreprocessDataset loaded in memory. */
                getPreprocessDataset().restoreToOriginalWEKAInstances();

                /* Reads the original information of the dataset file. */
                DatasetInformation datasetInfo = getDatasetXMLInformation(getPathOfSelectedDatasetToPreprocess());

                /* Calculates new statistics. */
                datasetInfo.setStatistics(getPreprocessDataset().calculateAttributeStatistics());                                               
                
                /* Check missing or duplicated dates in dataset created. */
                ArrayList<String> missingDates = getPreprocessDataset().checkMissingDates();

                /* Sets missing dates in dataset created. */
                datasetInfo.setMissingDates(missingDates);


                /* Sets the dataset information after restoring original data. */
                setDatasetInformationLoaded(datasetInfo);

                /* Shows statistics about each attribute. */
                showDatasetFileStatistics(datasetInfo.getStatistics());

                /* Updates information in tab: Preprocess. */
                Calendar today = GregorianCalendar.getInstance();

                getView().addLineSummaryPreprocessed("\n    "+today.getTime());
                getView().addLineSummaryPreprocessed("\n       Restore original data applied.");
                getView().addLineSummaryPreprocessed("\n       Current number of instances: " + datasetInfo.getNumInstances());
                getView().addLineSummaryPreprocessed("\n       Current missing or duplicates dates: " + missingDates.size() + "\n");                

                /* Disables Restore data button. */
                getView().setEnableRestoreData(false);

                /* Disables Undo button. */
                getView().setEnableUndo(false);
                
                /* Checks if tab: View data has to reload the data. */
                if(getView().getTabbedPreprocessSelectedIndex() == 1){

                    /* Data in tab: View data has to be reloaded because is selected. */

                    reloadViewData();

                }else{

                    /* Data in tab: View data has to be reloaded next time user click on it. */

                    setReloadViewData(true);

                }            

                JOptionPane.showMessageDialog(null, "Restore original data properly applied.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
        
            /* Cancelled by user. */
            JOptionPane.showMessageDialog(null, "Operation cancelled by user.", "Message", JOptionPane.INFORMATION_MESSAGE);

        }        
                        
    }
    

    /**
     * Shows a dialog for configuring the selected filter by user.
     */
    private void doConfigureFilter(){                
        
        /* Shows a dialog to configure the selected filter by user. */

        /* Name of the selected filter. */
        String filterName;
        
        /* Checks the user selected a filter. */                
        
        if(getView().getSelectedRowFilter()!=-1){
        
            /* User selected a filter. */
            filterName=getView().getSelectedFilterName();
            
        }else{
            
            /* User dit not selected a filter. */
            
            filterName = "";
        
        }
        
        /* Checks if user selection is ok. */
        
        switch (filterName) {
            
            case "":
                
                /* User did not select a filter to configure. */                
                JOptionPane.showMessageDialog(null, "Please, select a filter to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                break;
                
            case "Attribute":
                
                /* User did not select a filter to configure. */                
                JOptionPane.showMessageDialog(null, "Please, select a filter to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                break;

            case "Instance":
                
                /* User did not select a filter to configure. */                
                JOptionPane.showMessageDialog(null, "Please, select a filter to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                break;

            case "Recover missing data":
                
                /* User did not select a filter to configure. */                
                JOptionPane.showMessageDialog(null, "Please, select a filter to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                break;
                                                  
            case "Replace missing values with next nearest hour":
                
                /* User select a filter to configure. */                
                JOptionPane.showMessageDialog(null, "This filter replaces the missing values of each attribute with the next nearest non missing value.\n\nThis filter has no parameters to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                break;
                
            case "Replace missing values with previous nearest hour":
                                    
                /* User select a filter to configure. */                
                JOptionPane.showMessageDialog(null, "This filter replaces the missing values of each attribute with the previous nearest non missing value.\n\nThis filter has no parameters to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                break;

            case "Replace missing values with next hours mean":

                /* Configures the selected recovering filter. */
                configureRecoveringFilter(filterName);
                
                break;
                
            case "Replace missing values with previous hours mean":
                
                /* Configures the selected recovering filter. */
                configureRecoveringFilter(filterName);
                
                break;
                
            case "Replace missing values with symmetric hours mean":
                
                /* Configures the selected recovering filter. */
                configureRecoveringFilter(filterName);

                break;

                
            default:
                
                /* Gets the selected filter by user to configure. */
                final GenericFilter<?> filterToConfigure = new GenericFilter<>(getSelectedFilter().getFilter(), getSelectedFilter().getFilterName());


                /* Checks if user selected a filter. */

                if(filterToConfigure.getFilterName().isEmpty() == true){

                    /* Filter no selected. */

                    JOptionPane.showMessageDialog(null, "Please, select a filter to configure.", "Message", JOptionPane.INFORMATION_MESSAGE);

                }else{

                    /* Filter selected. */

                    /* Object editor to configure the selected filter. */
                    final GenericObjectEditor objectEditor = new GenericObjectEditor(true);            

                    try {

                            /* Sets class and filter to configure. */
                            objectEditor.setClassType(filterToConfigure.getFilter().getClass());
                            objectEditor.setValue(filterToConfigure.getFilter());

                            /* Configure generic editor options. */
                            objectEditor.removeCapabilitiesFilter();
                            objectEditor.setCanChangeClassInDialog(false);

                        }catch (Exception ex) {
                            Logger.getLogger(ControllerViewManageBuoys.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    /* Dialog to configure the selected filter by user. */
                    PropertyDialog dialog;

                    /* Creates a dialog for showing filter properties. */
                    if (PropertyDialog.getParentDialog( (view.ManageBuoys) getView()) != null){                        

                        dialog = new PropertyDialog(PropertyDialog.getParentDialog( (view.ManageBuoys) getView()), objectEditor, 200, 200);

                    }else{

                        dialog = new PropertyDialog(PropertyDialog.getParentFrame( (view.ManageBuoys) getView()), objectEditor, 200, 200);

                    }

                    /* Shows the dialog */               
                    dialog.setTitle("Configuring filter: " + filterToConfigure.getFilterName());
                    dialog.setVisible(true);

                    /* Gets the filter configurated by user. */
                    final GenericFilter<?> filterConfigurated = new GenericFilter<>(objectEditor.getValue(), filterToConfigure.getFilterName());

                    /* Sets the filter configurated as selected filter to apply. */
                    setSelectedFilter(filterConfigurated);

                }
                                
        }
                                   
    }
    
    
    /**
     * Moves to the previous 'Missing' , 'Unexpected' or 'Duplicated' date in the current pre-processed dataset.
     */
    private void doPreviousDatePreprocess(){
    
        /* Moves to the previous 'Missing', 'Unexpected' or 'Duplicated' date. */
        
        /* The row of the new 'Missing', 'Unexpected' or 'Duplicated' date. */
        int newRow=-1;
        
        /* Gets the selected row. */
        int startingRow=getView().getSelectedRowPreprocessedData();
        
        /* Gets the information of the pre-processed dataset. */
        DefaultTableModel datamodel = getView().getModelPreprocessedData();
        
        /* Checks if none row is selected. */
        if(startingRow==-1){
            
            /* Gets the last row. */
            startingRow=getView().getModelPreprocessedData().getRowCount();
        
        }
        
        /* Searchs for the previous date. */
        for(int i=startingRow-1;i>=0 && newRow == -1;i--){
            
            /* Gets the type of the date. */
            String typeOfDate=datamodel.getValueAt(i, 0).toString();
                       
            /* Checks if the current date is a 'Missing', 'Unexpected' or 'Duplicated' one. */
            if(typeOfDate.equals("Missing") || typeOfDate.equals("Duplicated") || typeOfDate.equals("Unexpected")){
                
                /* A new 'Missing', 'Unexpected' or 'Duplicated' date was found. */
                newRow=i;
            
            }
            
        }
        
        /* Checks if a new date was found. */
        if(newRow==-1){
            
            JOptionPane.showMessageDialog(null, "None previous 'Missing', 'Unexpected' or 'Duplicated' date was found.", "Message", JOptionPane.INFORMATION_MESSAGE);
                   
        }else{
            
            /* Set the row as selected. */
            getView().setSelectedRowPreprocessedData(newRow);
                
        }                             
                        
    }
    
    
    /**
     * Moves to the next 'Missing' , 'Unexpected' or 'Duplicated' date in the current pre-processed dataset.
     */
    private void doNextDatePreprocess(){
    
        /* Moves to the next 'Missing' , 'Unexpected' or 'Duplicated' date. */
        
        /* The row of the new 'Missing', 'Unexpected' or 'Duplicated' date. */
        int newRow=-1;
        
        /* Gets the last row. */
        int lastRow=getView().getModelPreprocessedData().getRowCount();        
        
        /* Gets the selected row. */
        int startingRow=getView().getSelectedRowPreprocessedData();
        
        /* Gets the information of the pre-processed dataset. */
        DefaultTableModel datamodel = getView().getModelPreprocessedData();
        
        /* Checks if none row is selected. */
        if(startingRow==-1){
            
            /* Sets the first row. */
            startingRow=0;
        
        }
        
        /* Searchs for the previous date. */
        for(int i=startingRow+1;i<lastRow && newRow == -1;i++){
            
            /* Gets the type of the date. */
            String typeOfDate=datamodel.getValueAt(i, 0).toString();
            
            /* Checks if the current date is a 'Missing', 'Unexpected' or 'Duplicated' one. */
            if(typeOfDate.equals("Missing") || typeOfDate.equals("Duplicated") || typeOfDate.equals("Unexpected")){
                
                /* A new 'Missing', 'Unexpected' or 'Duplicated' date was found. */
                newRow=i;
            
            }
            
        }
        
        /* Checks if a new date was found. */
        if(newRow==-1){
            
            JOptionPane.showMessageDialog(null, "None next 'Missing', 'Unexpected' or 'Duplicated' date was found.", "Message", JOptionPane.INFORMATION_MESSAGE);
                   
        }else{
            
            /* Set the row as selected. */
            getView().setSelectedRowPreprocessedData(newRow);
                
        }                             
                        
    }
    
    

    
    /**
     * Reads dataset file's summary from XML file.
     * @param filename Dataset file's name.
     * @return Dataset file's summary.
     */
    private DatasetInformation getDatasetXMLInformation(String filename){
    
        /* Gets the information in .XML file about the dataset file selected. */
        
        /* Information about the dataset file. */
        DatasetInformation datasetInfo = null;
              
        /* Gets the name of the XML file. */
        filename = filename.substring(0, filename.length() - 3) + ".xml";
                        
        /* Reads the xml file. */
        File xml = new File(filename);
                       
        if(xml.exists()){
                
            XMLFile<DatasetInformation> xmlFile = new XMLFile<>(DatasetInformation.class, xml);

            datasetInfo = xmlFile.readXMLFile();
               
        }
        
        return datasetInfo;
                
    }

    
    /**
     * Asks user confirmation for closing current preprocessing.
     * @return True or False.
     */
    private boolean closingCurrentPreprocessing(){
        
        /* Asks user confirmation for closing current preprocessing. */
        
        boolean closePreprocessing = true;
        
        Object[] options = {"Cancel", "Select dataset"};
        
        if(JOptionPane.showOptionDialog(null, "Attention !!\n\nThe current pre-processed information will be lost. Please, save it before continuing."
                +"\n\nClick on Cancel to abort or Select dataset to continue.\n\n","Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==0){

                /* User does not want to close current preprocessing. */
                closePreprocessing = false;
                
                /* Cancelled by user. */
                JOptionPane.showMessageDialog(null, "Operation cancelled by user.", "Message", JOptionPane.INFORMATION_MESSAGE);
                
        }else{
                
                /* User wants to close current preprocessing. */
                closeCurrentPreproccessing();
                
        }
        
        return closePreprocessing;
                    
    }
    
    
    /**
     * Closes current preprocessing.
     */
    private void closeCurrentPreproccessing(){
    
        /*
           This method is called when:
             - user saves a preprocessed dataset file
             - user closes (without saving) current preprocessed dataset file for opening a new one.
        */
        
        /* No preprocessing started. */
        setPreprocessingStarted(false);
        
        /* These buttons will be enabled when user applies a filter. */
        getView().setEnableUndo(false);
        getView().setEnableRestoreData(false);
        
    }
       
    
    /**
     * User selected tab 'File information' or 'View data'.
     */
    private void tabbedPreprocessSelectionChanged(){
        
        /* User clicked on jTabbedPreprocess for selecting tab: File information or View data */
                   
        if(getView().getTabbedPreprocessSelectedIndex() == 1 && getReloadViewData()==true){
                
            /* User selected tab: View data and data changed. */
                
            reloadViewData();
            
        }    
        
    }
        
    
    /**
     * Shows preprocessed data in tab: 'View data'.
     */
    private void reloadViewData(){
    
        /* 
            Shows preprocessed data in tab: 'View data' and checks all instances looking for missing or duplicated dates.
        */                
        
        /* To convert from Unix seconds to date. */
        Utils util = new Utils();
        
        /* Attributes. */
        ArrayList<weka.core.Attribute> attributes = getPreprocessDataset().getInstancesWEKA().getAttributes();
        
        /* Number of attributes. */
        int numAttributes = attributes.size();
        
        /* Gets the instances to show in table. */
        weka.core.Instances instances = getPreprocessDataset().getInstancesWEKA().getInstances();
        
        /* Table model. */       
        DefaultTableModel datamodel;
        datamodel = new DefaultTableModelImpl();
               
        /* 
            The first column indicates if the date is 'Missing', 'Unexpected' or 'Duplicated' it is only used
            for filtering the 'Missing' dates and for using a different colour for each case.
        */
        datamodel.addColumn("TypeOfDate");
        
        /* Adds columns to table model, one per attribute. */
        for (weka.core.Attribute attribute : attributes) {

            datamodel.addColumn(attribute.name());

        }                   
        
        
        /* 
            *************************************************************
            Any change in this method has to be also done in the methods: 
                checkMissingDates() of RunCreationDataset.java
                checkMissingDates() of PreprocessDatasetFile.java
            *************************************************************
        */

        
        /* To convert from int to String with two digits. */
        DecimalFormat intFormatter = new DecimalFormat("00");
                                                
        /* Number of instances to check. */
        int numberOfInstances = getPreprocessDataset().getInstancesWEKA().getNumberOfInstances();
        
        /* Indicates if a missing date has been found. */
        boolean missingDateFound;
                
        /* Format of the date. */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm");
        
        /* NDBC dates are in UTC time zone. */
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        
        /* For getting correct time according to UTC time zone. */
        dateFormat.setTimeZone(timeZone);

        /* Time interval between date: 1 hour. */
        int timeInterval = 1;
        
        /* Maximum seconds of delay for a measurement: 360s --> 6 minutes: --> 10% of 60 minutes (1 hour). */
        long maxDelayInterval = 360;
        
        
        if(numberOfInstances>1){
        
            /* There are instances to check. */        
        
            /* Gets the first date. */
            String firstDateInStringFormat = util.unixSecondsToString( (long) (instances.get(0).value(0)));
            Calendar firstDateInDateFormat = util.convertDateFromStringToCalendar(firstDateInStringFormat);

            /* The second date. */
            String secondDateInStringFormat;
            Calendar secondDateInDateFormat;


            /* Check all instances. */
            for(int i=1;i<numberOfInstances;i++){
                
                /* To store all attributes values of one record. */
                ArrayList<Object> row = new ArrayList<>();

                /* Gets the second date. */
                secondDateInStringFormat = util.unixSecondsToString( (long) (instances.get(i).value(0)));
                secondDateInDateFormat = util.convertDateFromStringToCalendar(secondDateInStringFormat);

                /* Checks if both dates are equals. */
                if(dateFormat.format(firstDateInDateFormat.getTime()).equals(dateFormat.format(secondDateInDateFormat.getTime()))==true){

                    /* Equals: -> 'Duplicated' */                    
                    row.add("Duplicated");

                }else{

                    /* Not equals. */

                    do{                                    

                        /* Sets previous expected date. */
                        String previousFirstDateInStringFormat = firstDateInDateFormat.get(Calendar.YEAR)+" "+
                                                                  intFormatter.format((firstDateInDateFormat.get(Calendar.MONTH)+1))+" "+
                                                                  intFormatter.format(firstDateInDateFormat.get(Calendar.DATE))+" "+
                                                                  intFormatter.format(firstDateInDateFormat.get(Calendar.HOUR_OF_DAY))+" "+
                                                                  intFormatter.format(firstDateInDateFormat.get(Calendar.MINUTE));

                        /* Gets the next expected date. */
                        firstDateInDateFormat.add(Calendar.HOUR_OF_DAY, timeInterval);
                        
                        /* Checks if second date is the expected one. */
                        if(dateFormat.format(firstDateInDateFormat.getTime()).equals(dateFormat.format(secondDateInDateFormat.getTime()))==false){

                            /* 
                                Checks if the second date matchs with the maximum delay.
                            */
                            if(Math.abs(firstDateInDateFormat.getTime().getTime()-secondDateInDateFormat.getTime().getTime()) / 1000  > maxDelayInterval){

                                /* The second date exceeds the maximum delay. */
                                
                                if(secondDateInDateFormat.getTime().before(firstDateInDateFormat.getTime()) == false){

                                    /* Missing date found. */
                                    
                                    row.add("Missing");
                                    
                                    /* Adds the attribute DATE in human readable format. */
                                    row.add(firstDateInDateFormat.get(Calendar.YEAR)+" "+
                                                intFormatter.format((firstDateInDateFormat.get(Calendar.MONTH)+1))+" "+
                                                intFormatter.format(firstDateInDateFormat.get(Calendar.DATE))+" "+
                                                intFormatter.format(firstDateInDateFormat.get(Calendar.HOUR_OF_DAY))+" "+
                                                intFormatter.format(firstDateInDateFormat.get(Calendar.MINUTE)));
                                    
                                    
                                    /* Stores the values of the attributes. */
                                    for(int k=1;k<numAttributes;k++){

                                        /* As it is a missing date stores the value of each attribute with a ' ' . */
                                        row.add(" ");

                                    }        

                                    /* Adds a row with the values of the instance. */
                                    datamodel.addRow(row.toArray());
                                    
                                    /* Cleans the data for the next instance. */
                                    row = new ArrayList<>();
                                                                        
                                    missingDateFound=true;
                                    
                                }else{

                                    /* An unexpected date. */
                                    
                                    row.add("Unexpected");
                                    
                                    missingDateFound=false;

                                    /* Sets the previous expected second date. */
                                    secondDateInStringFormat= previousFirstDateInStringFormat;
                                    
                                }
                                
                            }else{
                                    
                                /* 
                                    The buoy took the measurement from: hh:50 to: hh:49, hh:48, hh:47, hh:46, hh:45, hh:44.
                                                                                    or
                                                                                  hh:51, hh:52, hh:53, hh:54, hh:55, hh:56.
                                */
                                missingDateFound=false;                                
                                                               
                                /* Sets the expected second date. */
                                secondDateInStringFormat= firstDateInDateFormat.get(Calendar.YEAR)+" "+
                                                                intFormatter.format((firstDateInDateFormat.get(Calendar.MONTH)+1))+" "+
                                                                intFormatter.format(firstDateInDateFormat.get(Calendar.DATE))+" "+
                                                                intFormatter.format(firstDateInDateFormat.get(Calendar.HOUR_OF_DAY))+" "+
                                                                intFormatter.format(firstDateInDateFormat.get(Calendar.MINUTE));
                                
                                /* The date is correct. */
                                row.add(" ");
                            }

                        }else{

                            /* Ok. */
                            missingDateFound=false;
                            
                            /* The date is correct. */
                            row.add(" ");
                            
                        }
                        
                    }while(missingDateFound==true);

                }

                /* Sets first date. */
                firstDateInStringFormat = secondDateInStringFormat;
                firstDateInDateFormat = util.convertDateFromStringToCalendar(firstDateInStringFormat);
                
                /* Adds the attribute DATE in human readable format. */
                //row.add(util.unixSecondsToString( (long) instances.get(i).value(0)));
                row.add(util.unixSecondsToString( (long) instances.get(i-1).value(0)));

                /* Gets the values of the attributes. */
                for(int k=1;k<numAttributes;k++){

                    /* Stores each value. */
                    //row.add(instances.get(i).value(k));
                    row.add(instances.get(i-1).value(k));

                }        

                /* Adds a row with the values of the instance. */
                datamodel.addRow(row.toArray());

            }
            
        }
        
        /* (Special case) Adds the last instance. */
        
        /* To store all the attributes values of the instance. */
        ArrayList<Object> row = new ArrayList<>();
        
        /* The date is correct. */
        row.add(" ");

        /* Adds the attribute DATE in human readable format. */
        row.add(util.unixSecondsToString( (long) instances.get(numberOfInstances-1).value(0)));

        /* Gets the values of the attributes. */
        for(int k=1;k<numAttributes;k++){

            /* Stores each value. */
            row.add(instances.get(numberOfInstances-1).value(k));

        }

        /* Adds a row with the values of the instance. */
        datamodel.addRow(row.toArray());
        
        /* Shows data. */        
        getView().setModelPreprocessedData(datamodel);
        
        
        /* 
            Hides the first column. That column indicates if the date is 'Missing', 'Unexpected' or 'Duplicated' it is only used
            for filtering the 'Missing' dates and for using a different colour for each case.
        */
        getView().hideColumnOfPreprocessedData(0);

        /* Data in tab: View data has not to be reloaded. */
        setReloadViewData(false);   
                
    }                                       



    /**
     * Configures the received recovering filter.
     * 
     * @param filterToConfigure The filter to configure.
     */
    private void configureRecoveringFilter(String filterToConfigure){
    
        /* Configures the received recovering filter. */
        
        /* Available values (hours) of the configuration. */
        String availableValues [] = {"1", "2", "3", "4", "5"};
        
        /* Description of the filter. */
        String description="";
        
        /* Index of the filter to configure. */
        int index=0;
        
        /* New configuration typed by the user. */
        String newConfiguration;

        
        /* Current configuration of the filter to configure. */
        
            /*  
                For each filter:
        
                    Value: 1 -> index 0
                    Value: 2 -> index 1
                    Value: 3 -> index 2
                    Value: 4 -> index 3
                    Value: 5 -> index 4
            */
        int currentConfiguration=0;                                
        
        
        /* Checks the filter received to configure. */
        switch(filterToConfigure){
            
            case "Replace missing values with next hours mean":
                
                /* Sets index of the filter. */
                index=0;
                
                /* Sets the description. */
                description="This filter replaces the missing values of each attribute with the next nearest non missing values mean,\n"
                          + "so these values may not coincide with next hours.\n\nSelect the number of the next nearest hours to use.";

                /* Gets current value of the filter to configure. */
                currentConfiguration=getRecoveringFilterConfiguration(index)-1;
                
                break;
                
                
            case "Replace missing values with previous hours mean":
                
                /* Sets index of the filter. */
                index=1;
                
                /* Sets the description. */
                description="This filter replaces the missing values of each attribute with the previous nearest non missing values mean,\n"
                          + "so these values may not coincide with previous hours.\n\nSelect the number of the previous nearest hours to use.";

                /* Gets current value of the filter to configure. */
                currentConfiguration=getRecoveringFilterConfiguration(index)-1;
                
                break;
                
                
            case "Replace missing values with symmetric hours mean":
                
                /* Sets index of the filter. */
                index=2;
                
                /* Sets the description. */
                description="This filter replaces the missing values of each attribute with the symmetric previous and next (non missing) values mean,\n"
                          + "so these values may not coincide with symmetric previous and next hours.\n\nSelect the number of the symmetric previous and next hours to use.";

                /* Gets current value of the filter to configure. */
                currentConfiguration=getRecoveringFilterConfiguration(index)-1;
                
                break;                                                
           
        }
        
        /* Shows the dialog to the user. */
        newConfiguration=(String)JOptionPane.showInputDialog(null, description, "Configuring filter.", JOptionPane.QUESTION_MESSAGE, null, availableValues, availableValues[currentConfiguration]);
        
        /* Checks if the user changed the configuration. */
        if(newConfiguration!=null){
            
            /*
                The user changed the configuration of the filter.
                Sets the new configuration.            
            */
            setRecoveringFilterConfiguration(index, Integer.parseInt(newConfiguration) );
                    
        }

    }
    
    
    
/*  Tab: Matching configuration. */

    
    /**
     * User selected a different buoy in tab 'Matching'.
     */
    private void buoyMatchingSelectionChanged(){
    
        /* The user changed buoy selection. */
        
        /* Shows dataset files that belong to the selected buoy. */
        
        /* Clear previous data of the intermediate dataset files of the previous selected buoy. */
        getModelMatchingDatasetFiles().clear();
        getPathMatchingDatasetFiles().clear();
        
        /* Clear previous data of the preprocessed dataset files of the previous selected dataset file. */
        getModelMatchingPreprocessedDatasetFiles().clear();
        getPathMatchingPreprocessedDatasetFiles().clear();
        
        /* Selected buoy. */
        int selectedBuoy = getView().getSelectedRowBuoysMatching();

        if(selectedBuoy!=-1 && getView().getModelBuoysMatching().getRowCount()>0){
            
            /* Checks if the selected buoy was deleted. */
                        
            /* Id of the buoy selected. */
            int idBuoy = (int) getView().getModelBuoysMatching().getValueAt(selectedBuoy, 0);
            
            /* Utilities. */
            Utils util = new Utils();

            
            /* A new buoy was selected. */

            /* Search for the .db Dataset files that belong to the buoy. */        
            util.searchFiles(idBuoy, getModelMatchingDatasetFiles(), new ArrayList<>(Arrays.asList("Datasets")), getPathMatchingDatasetFiles(), new ArrayList<>(Arrays.asList("DB Files", "DB", "db")));
            
            /* Updates in view the dataset files that belong to the buoy. */
            getView().setModelMatchingDataset(getModelMatchingDatasetFiles());
            
        }
    }
    

    /**
     * User selected a different dataset file in tab 'Matching'.
     */
    private void datasetMatchingSelectionChanged(){
    
        /* Updates the information about the dataset file selected. */
        
        /* Gets the dataset file. */
        int selectedIndex = getView().getSelectedIndexMatchingDataset();
        
        if(selectedIndex != -1){
            
            /* Shows preprocessed files that belong to the selected dataset file. */
            
            /* Gets the name of the selected dataset file. */
            String filename;
            filename = getModelMatchingDatasetFiles().get(selectedIndex);
            filename = filename.substring(0, filename.length() - 3);

            /* Utilities. */
            Utils util = new Utils();


            /* A new dataset file was selected. */

            /* Preprocess files */               
            getModelMatchingPreprocessedDatasetFiles().clear();
            getPathMatchingPreprocessedDatasetFiles().clear();
            
            /* Selected row. */
            int selectedRow = getView().getSelectedRowBuoysMatching();
                        
            /* selected buoy. */
            int idBuoy = (int) getView().getModelBuoysMatching().getValueAt(selectedRow, 0);

            /* Search for the .db Preprocess files that belong to the buoy. */        
            util.searchFiles(idBuoy, getModelMatchingPreprocessedDatasetFiles(), new ArrayList<>(Arrays.asList("Preprocess"+File.separator+filename)), getPathMatchingPreprocessedDatasetFiles(), new ArrayList<>(Arrays.asList("DB Files", "DB", "db")));

            /* Updates in view the dataset files that belong to the selected dataset file. */
            getView().setModelMatchingPreprocessedDataset(getModelMatchingPreprocessedDatasetFiles());

        }
    }


    /**
     * Selects dataset file to use in matching process.
     */
    private void doSelectMatchingDatasetFile(){
    
        /* Selects dataset file to use in matching process. */
        
        /* Selected dataset file. */
        int selectedIndex = getView().getSelectedIndexMatchingDataset();
        
        if(selectedIndex == -1){
            
            JOptionPane.showMessageDialog(null, "Please, select an intermediate dataset.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Sets ID Buoy. */            
            
                /* Selected row. */
            int selectedRow = getView().getSelectedRowBuoysMatching();                        
                /* Selected buoy. */
            int idBuoy = (int) getView().getModelBuoysMatching().getValueAt(selectedRow, 0);
            
            setSelectedBuoyToMatching(idBuoy);            
            
            /* Selects dataset file. */
            getView().setSelectedMatchingDataset(getModelMatchingDatasetFiles().get(selectedIndex));
            
            /* Sets the relative path of selected dataset. */

                /* Utilities.*/
                Utils util =  new Utils();
                
                /* Root. */
                String root = System.getProperty("user.dir", ".")+File.separator+"DB"+File.separator;
                
                /* Absolute path. */
                String abolsultePath=getPathMatchingDatasetFiles().get(selectedIndex);
                
                /* Relative path. */                
                String relativePath=abolsultePath.substring(util.lastCharacterInCommon(root, abolsultePath)+1);
        
                setPathOfSelectedDatasetToMatching(relativePath);
            

            /* Sets type 'Dataset' of selected dataset file. */
            setTypeOfSelectedDatasetToMatching("Dataset");
            
            /* Sets the name of each attribute to predict. */
            getView().deleteAllAttributeNameToPredict();            
            setAttributesNameOfMatching(getPathMatchingDatasetFiles().get(selectedIndex));

        }            
    
    }
            
    
    /**
     * Selects preprocessed dataset file to use in matching process.
     */
    private void doSelectMatchingPreprocessedDatasetFile(){
    
        /* Selects preprocessed dataset file to matching process. */
        
        /* Selected preprocssed dataset file. */
        int selectedIndex = getView().getSelectedIndexMatchingPreprocessedDataset();
        
        if(selectedIndex == -1){
            
            JOptionPane.showMessageDialog(null, "Please, select a pre-processed dataset.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* Sets ID Buoy. */            
            
                /* Selected row. */
            int selectedRow = getView().getSelectedRowBuoysMatching();                        
                /* Selected buoy. */
            int idBuoy = (int) getView().getModelBuoysMatching().getValueAt(selectedRow, 0);
                        
            setSelectedBuoyToMatching(idBuoy);
            
            
            /* Selects dataset file. */
            getView().setSelectedMatchingDataset(getModelMatchingPreprocessedDatasetFiles().get(selectedIndex));
            
            /* Sets the relative path of selected preprocessed dataset. */

                /* Utilities.*/
                Utils util =  new Utils();
                
                /* Root. */
                String root = System.getProperty("user.dir", ".")+File.separator+"DB"+File.separator;
                
                /* Absolute path. */
                String abolsultePath=getPathMatchingPreprocessedDatasetFiles().get(selectedIndex);
                
                /* Relative path. */                
                String relativePath=abolsultePath.substring(util.lastCharacterInCommon(root, abolsultePath)+1);
        
                setPathOfSelectedDatasetToMatching(relativePath);
            
            
            /* Sets type 'Preprocessed' of selected dataset file. */
            setTypeOfSelectedDatasetToMatching("Preprocessed");
            
            /* Sets the name of each attribute to predict. */
            getView().deleteAllAttributeNameToPredict();
            setAttributesNameOfMatching(getPathMatchingPreprocessedDatasetFiles().get(selectedIndex));
        }            
    
    }
       
    
    /**
     * Opens SelectReanalysisFiles view to select reanalysis data files.
     */
    private void doModifySelectionOfSelectReanalysisFiles(){
        
        /* Modify selection of renalysis files to use in matching. */
        
        /* Current selected files. */
        ArrayList<String> currentSelectedFiles = new ArrayList<>();
        
        for(String selectedFile: getSelectedReanalysisFiles()){
            
            currentSelectedFiles.add(selectedFile);
        
        }
        
        /* Current selected vars. */
        ArrayList<String> currentSelectedVars = new ArrayList<>();
        
        for(String selectedVar: getSelectedReanalysisVars()){
            
            currentSelectedVars.add(selectedVar);
        
        }
       
        /* Opens SelectReanalysisFiles view. */
        
        /* Creates the view and the controller. */
        InterfaceViewSelectReanalysisFiles selectReanalysisFilesView = new SelectReanalysisFiles(getView().getParent(), false);
        ControllerViewSelectReanalysisFiles controller = new ControllerViewSelectReanalysisFiles(selectReanalysisFilesView, selectReanalysisFilesView.getModelReanalysisFiles(), currentSelectedFiles, currentSelectedVars, getMaxNearestGeopoints());
                
        /* Checks if selection is possible. */
        if (controller.numberOfReanalysisFiles() == 0){
            
            /* No files to select. */
            JOptionPane.showMessageDialog(null, "Reanalysis files database is empty.\nClick on 'Manage reanalysis data' in main menu to add files." , "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            /* There are reanalysis files to select. */
                        
            /* Sets TableCellRenderer for tblReanalysisFiles. */
            controller.setTblReanalysisFilesCellRenderer();
            
            /* Sets the controller that will manage all events and shows the view. */
            selectReanalysisFilesView.setController(controller);
            selectReanalysisFilesView.showView();
            
            /* Checks if user confirmed selection of reanalysis files.*/
            
            if(controller.getUserConfirmedSelection()==true){
             
                /* Sets selection of reanalysis files made by user. */
                
                /* Sets the new text of the button. */
                getView().setBtnModifyReanalysisFiles("Modify");
                
                /* Clears older selection. */
                getModelReanalysisFiles().clear();
                
                /* Sets new selected files and vars. */
                setSelectedReanalysisFiles(controller.getSelectedReanalysisFiles());

                /* Sets reanalysis data vars used in matching. */
                setSelectedReanalysisVars(controller.getSelectedReanalysisVars());
                        
                for(int i=0;i<getSelectedReanalysisFiles().size();i++){
                    
                    /* Adds var + file. */
                    getModelReanalysisFiles().addElement("("+getSelectedReanalysisVars().get(i)+") " + getSelectedReanalysisFiles().get(i));
                
                }                                
                
                /* Sets selection in view. */
                getView().setModelReanalysisFiles(getModelReanalysisFiles());
                
                /* Sets max nearest geopoints to consider. */
                setMaxNearestGeopoints(controller.getNumberOfGeopoints());
                
                /* Sets text of max of nearest geopoints to consider. */
                getView().setTextOfNumberNearestGeopointsToConsider(controller.getNumberOfGeopoints());
                
            }
        
        }

    }
    
    
    /**
     * Opens SelectBuoyVariables view to select the variables of the buoy to use in matching.
     */
    private void doModifySelectionOfBuoyVariables(){
        
        /* Modify the selection of the variables of the buoy to use in matching. */ 
        
        /* Checks if user selected an intermediate or pre-processed dataset to use in macthing. */
        if(getAvailableBuoyVariables().isEmpty()==true){
            
            JOptionPane.showMessageDialog(null, "Select an intermediate or pre-processed dataset to apply matching.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
        
            /* Current selected variables. */
            ArrayList<String> currentSelectedVariables = new ArrayList<>();

            for(String selectedVariable: getSelectedBuoyVariables()){

                currentSelectedVariables.add(selectedVariable);

            }

            /* Available variables of the buoy to select. */        
            ArrayList<String> availableVariables = new ArrayList<>();

            for(String availableVariable: getAvailableBuoyVariables()){

                availableVariables.add(availableVariable);

            }

            /* If flux of energy is not selected as attribute to predict, removes from the available variables the selected one to predict. */
            if(getView().getCalculateFluxOfEnergy()==false){

                /* Flux of energy is not selected, removes the selected attribute to predict from the available variables. */
                availableVariables.remove(getView().getSelectedAttributeToPredict());

            }

            /* Creates the view and the controller. */
            InterfaceViewSelectBuoyAttributes selectBuoyVariablesView =  new SelectBuoyAttributes(getView().getParent(), false);        
            ControllerViewSelectBuoyAttributes controller = new ControllerViewSelectBuoyAttributes(selectBuoyVariablesView , selectBuoyVariablesView.getModelBuoyAttributes(), availableVariables, currentSelectedVariables);

            /* Sets the controller that will manage all events and shows the view. */
            selectBuoyVariablesView.setController(controller);

            /* Shows the view. */
            selectBuoyVariablesView.showView();

            /* Checks if the user confirmed the selection of the variables of the buoy. */
            if(controller.getUserConfirmedSelection()==true){

                /* Sets the selection of the variables of the buoy made by user. */

                /* Clears older selection. */
                getSelectedBuoyVariables().clear();

                /* Sets reanalysis data vars used in matching. */
                setSelectedBuoyVariables(controller.getSelectedBuoyVariables());

                /* Updates the model in the view. */
                updateSelectionOfBuoyVariables();
                
                /* Sets the new text of the button to modify the current selection of the variables of the buoy to use in mtaching. */
                if(getSelectedBuoyVariables().isEmpty()){
                    
                    /* Sets the new text. */
                    getView().setBtnModifyBuoyVariables("Add");
                    
                }else{
                    
                    /* Sets the new text. */
                    getView().setBtnModifyBuoyVariables("Modify");
                
                }                

            }
            
        }

    }
    
    
    
    /**
     * Opens RunMatching view to run matching process.
     */
    private void doRunMatching(){    
        
        /* Runs matching with the configuration typed by user in tab: 'Matching configuration'. */
        
        /* To check if user changed the attribute to predict. */
        boolean attributeToPredictChanged = true;
        
        /* Gets the attribute to predict used last time. */
        String oldAttributeToPredict = getCurrentMatchingConfiguration().getAttributeNameToPredict();
        
        /* Gets the path of the dataset used last time. */
        String oldPathDataset = getCurrentMatchingConfiguration().getDatasetFilePath();
        
        if(checkMatchingConfigurationOfTabMatching()==true){
            
            /* User typed all input data. */
            
            /* Sets current matching configuration of tab: 'Matching configuration' typed by user. */
            setCurrentMatchingConfigurationOfTabMatching();
            
            /* Checks if user changed the attribute to predict. */
            if( oldAttributeToPredict.equals(getCurrentMatchingConfiguration().getAttributeNameToPredict()) && 
                oldPathDataset.equals(getCurrentMatchingConfiguration().getDatasetFilePath())
              ){
                
                /* The user did not change the attribute to predict.*/
                attributeToPredictChanged = false;
            
            }
                                   
            
            /* Opens RunMatching view to run the matching procees. */
            
            
            /* Creates the view. */
            RunMatching newRunMatching = new RunMatching(null, true, getCurrentMatchingConfiguration(), getLatitudeBuoyToMatching(), getLongitudeBuoyToMatching(), getView().getSelectedStationIDMatching());
            
            /* Run matching. */
            newRunMatching.startMatching();

            /* Shows the view. */
            newRunMatching.setVisible(true);
            
            
            /* Checks if matching process properly finished. */
            if(newRunMatching.getPressedCancel()==true){
                
                /* There was an error when running matching process or it was cancelled by the user. */
                
                JOptionPane.showMessageDialog(null, "There was an error when running matching process or it was cancelled.", "Message", JOptionPane.WARNING_MESSAGE);
                
                /* Disables tab: Final datasets. */
                getView().setEnabledTab(4, false);
            
            }else{
            
                /* The matching process properly finished. */
                
                /* Sets matched final datasets obtained in matching process. */
                setMatchedFinalDatasets(newRunMatching.getMatchedDataBases());

                /* Sets backup of matched final datasets obtained in matching process. */
                setBackupOfMatchedFinalDatasets(newRunMatching.getMatchedDataBases());

                /* Sets the missing dates that were found in matching process. */
                setMissingDates(newRunMatching.getMissingDates());
                
                /* Shows the available final datasets to visualise and table header. */
                /* By default the content of the first final dataset is showed. */
                showFinalDatasetsSelection(1);
                
                /* Sets the number of the reanalysis variables used in matching process. */
                setNumberOfReanalysisVariablesUsed(getModelReanalysisFiles().size());
                
                /* Sets the number of instances in final datasets. */
                setNumInstancesOfFinalDataset(getMatchedFinalDatasets().get(0).getInstancesWEKA().getInstances().size());
                
                /* Sets the tool tip text of the label 'lblMissingDateFinalDataset'. */
                if(getCurrentMatchingConfiguration().getIncludeMissingDates()==true){
                    
                    getView().setlblMissingDateFinalDatasetToolTipText("Missing date (included in the final dataset)");
                
                }else{
                
                    getView().setlblMissingDateFinalDatasetToolTipText("Missing date (not included in the final dataset)");
                
                }
                                                                
                /* Gets index of the variable to predict. */
                int indexVariableToPredict = getMatchedFinalDatasets().get(0).getInstancesWEKA().getInstances().attribute(getCurrentMatchingConfiguration().getAttributeNameToPredict()).index();
                
                /* Checks if the values of the attribute are all missing values. */
                if(getMatchedFinalDatasets().get(0).getInstancesWEKA().getInstances().attributeStats(indexVariableToPredict).missingCount==getNumInstancesOfFinalDataset()){
                
                    /* The selected attribute as output only has missing values. */
                    JOptionPane.showMessageDialog(null, "The selected attribute as output only has missing values.", "Message", JOptionPane.WARNING_MESSAGE);
                    
                    /* 
                        Disables tab: Final datasets.
                    */
                    getView().setEnabledTab(4, false);

                }else{
                
                    /* The selected attribute as output has at least one non missing value. */
                    
                    /* Sets min of the variable to predict. */
                    double min=getMatchedFinalDatasets().get(0).getInstancesWEKA().getInstances().attributeStats(indexVariableToPredict).numericStats.min;
                        setMinOfFinalDataset(min);

                    /* Sets max of the variable to predict. */
                    double max=getMatchedFinalDatasets().get(0).getInstancesWEKA().getInstances().attributeStats(indexVariableToPredict).numericStats.max;
                        setMaxOfFinalDataset(max);


                    /* Enables/disables panels and configuration in tab: Final datasets depending on type of matching. */
                    if(enablePanelsTabFinalDatasets(attributeToPredictChanged)==true){
                        
                        /* Goes to tab: . */
                        doGotoTab("Final datasets");
                    
                    }else{
                        
                        /* Disables tab: Final datasets. */
                        getView().setEnabledTab(4, false);
                    
                    }
                
                }
                                                
            }

        }

    }               
    
        
    /**
     * Opens LoadMatchingFile view to load a matching file already created.
     */
    private void doLoadMatchingFile(){    
            
        /* Opens LoadMatchingFile view to select a matching file already created. */

        /* Creates the view and controller. */
        InterfaceViewLoadMatchingFile openMatchingFile = new LoadMatchingFile(getView().getParent(), false);        
        ControllerViewLoadMatchingFile controller = new ControllerViewLoadMatchingFile(openMatchingFile, getModelBuoys());

        /* Sets the controller that will manage all events and shows the view. */
        openMatchingFile.setController(controller);

        /* Shows the view. */
        openMatchingFile.showView();
                
        /* Checks if user selected a matching file. */
        if(controller.getUserSelectedAFile() == true){
            
            /* Gets view. */
            InterfaceViewManageBuoys theView = getView();
            
            /* Configuration of matching. */
            MatchingInformation matchingConfiguration = controller.getMatchingConfiguration();

            /* Sets the configuration of selected matching file. */
                        
            /* Sets Buoy. */
            setSelectedBuoyToMatching(matchingConfiguration.getIDBuoy());
            
            /* Sets StationID of the buoy. */
            theView.setSelectedStationIDMatching(matchingConfiguration.getStationID());
                            
            /* Sets dataset/preprocessed dataset used. */
            theView.setSelectedMatchingDataset(matchingConfiguration.getDatasetFileName());
            
            /* Sets path or the dataset/preprocessed dataset. */
            setPathOfSelectedDatasetToMatching(matchingConfiguration.getDatasetFilePath());
            
            /* Sets type (Dataset/Preprocessed) of the dataset file. */
            setTypeOfSelectedDatasetToMatching(matchingConfiguration.getTypeDatasetFile());
            
            /* Clears the current content of the reanalysis data files used in matching. */
            getModelReanalysisFiles().clear();
            
            /* Sets the new text of the button. */
            getView().setBtnModifyReanalysisFiles("Modify");
            
            for(int i=0;i<matchingConfiguration.getReanalysisFiles().size();i++){
                
                /* Adds var + file. */
                getModelReanalysisFiles().addElement("("+matchingConfiguration.getReanalysisVars().get(i)+") " + matchingConfiguration.getReanalysisFiles().get(i));
                
            }                                
            
            theView.setModelReanalysisFiles(getModelReanalysisFiles());
            
            /* Sets reanalysis data files used in matching. */
            setSelectedReanalysisFiles(matchingConfiguration.getReanalysisFiles());

            /* Sets reanalysis data vars used in matching. */
            setSelectedReanalysisVars(matchingConfiguration.getReanalysisVars());
            
            /* Sets include missing dates. */
            theView.setIncludeMissingDates(matchingConfiguration.getIncludeMissingDates());

            /* Sets number of near points to consider. */
            theView.setNumberNearestGeopointsToConsider(String.valueOf(matchingConfiguration.getNumberOfNearestGeopointsToConsider()));
            
            /* Sets output files to create. */
            theView.setOutputFilesToCreate(matchingConfiguration.getOutputFilesToCreate());
            
            /* Checks if the number of geopoints loaded is equal to 1 */
            checkNumberOfTypedGeopoints();

            /* Sets type of matching. */
            setTypeOfMatching(matchingConfiguration.getTypeOfMatching());
            
            /* Sets output files format to create. */
            theView.setOutputFilesFormat(matchingConfiguration.getOutputFilesFormat());
            
            /* Sets output folder to save the output files. */
            theView.setOutputFolder(matchingConfiguration.getOutputFolder());
            
            /* Sets open databases with Weka when finished. */
            theView.setOpenDatabasesWithWeka(matchingConfiguration.getOpenDatabasesWithWEKA());

            /* Sets 'Synchronise the reanalysis data' option. */
            theView.setSynchroniseReanalysisData(matchingConfiguration.getSynchroniseRData());

            /* Sets flux of energy. */
            theView.setCalculateFluxOfEnergy(matchingConfiguration.getCalculateFluxOfEnergy());
            
            
            /* Sets the name of the available attributes to predict. */ 
                
                /* Root. */
                String root = System.getProperty("user.dir", ".")+File.separator+"DB"+File.separator;
                
                setAttributesNameOfMatching(root+matchingConfiguration.getDatasetFilePath());            
            
                
            /* Sets name of the attribute to predict depending on 'Flux of energy' selection. */
            if(theView.getCalculateFluxOfEnergy()==false){

                theView.setSelectedAttributeToPredict(matchingConfiguration.getAttributeNameToPredict());
                
            }
            
            
            /*
                Sets current selection of attribute to predict and the path of the selected dataset, in order to detect if user changes it
                and to create a default threshold.
            */
            getCurrentMatchingConfiguration().setAttributeNameToPredict(matchingConfiguration.getAttributeNameToPredict());
            getCurrentMatchingConfiguration().setDatasetFilePath(matchingConfiguration.getDatasetFilePath());
            
            
            /* Enables or disables 'Attribute to predict' depending on 'Flux of energy' selection. */
            doCheckCalculateFluxOfEnergy();
            
            /* Sets the names of the variables of the buoy to use in matching. */
            setSelectedBuoyVariables(matchingConfiguration.getBuoyVariables());
            
            /* Checks that the selected dataset contains the selected variables of the buoy. */
            checkSelectedBuoyVariables();

            /* Sets matching configuration loaded depending on type of matching. */            
            switch (matchingConfiguration.getTypeOfMatching()) {

                case "Direct matching":

                    /*  Nothing else to set.  */

                    break;

                case "Classification":
                    
                    /* Sets the thresholds in matching configuration. */
                    getCurrentMatchingConfiguration().setThresholdsClass(matchingConfiguration.getThresholdsClass());
                    getCurrentMatchingConfiguration().setThresholdsDescription(matchingConfiguration.getThresholdsDescription());
                    getCurrentMatchingConfiguration().setThresholdsValues(matchingConfiguration.getThresholdsValues());
                                        
                    /* Clears current thresholds and sets new ones in tab 'Final datasets'. */
                    setThresholdFromMatchingConfiguration();
                    
                    /* Sets current prediction horizon. */
                    getCurrentMatchingConfiguration().setPredictionHorizon(matchingConfiguration.getPredictionHorizon());
                    
                    /* Shows the current prediction horizon. */
                    showPredictionHorizon();
                   
                    break;
                    
                case "Regression":

                    /* Sets current prediction horizon. */
                    getCurrentMatchingConfiguration().setPredictionHorizon(matchingConfiguration.getPredictionHorizon());
                    
                    /* Shows the current prediction horizon. */
                    showPredictionHorizon();
                    
                    break;

            }

            
            /* 
                Finally checks if the configuration of tab: 'Matching configuration' is Ok.
                Matching configuration of tab: 'Final datasets' will be checked when user click on
                'Create final datasets' or 'Save' button.
            */
            
            checkMatchingConfigurationOfTabMatching();
            
            /* 
                Disables tab: 'Final datasets'.
                User must click on 'Next' button to run the loaded matching configuration.
            */
            getView().setEnabledTab(4, false);
            
        }        
                        
    }
    

    /**
     * Loads selected dataset file for matching and sets each attribute's name.
     * Checks that necessary attributes for calculating flux of energy are present in selected dataset.
     * Checks that the selected dataset contains the selected variables of the buoy.
     * 
     * @param filename Dataset file's name.
     */
    private void setAttributesNameOfMatching(String filename){
        
        /* 
            Loads selected dataset file for matching and sets in comboBox the name of each attribute.
        
            Checks that necessary attributes for calculating flux of energy are present in selected dataset.
        */
        
        /* Information of selected dataset. */
        DatasetInformation datasetInfo = getDatasetXMLInformation(filename);
      
        /* Loads the information. */        
        if(datasetInfo!=null){
            
            /* Checks if statistics about each attribute were calculated. */
                
            ArrayList<DatasetInformation.AttributeStatistics> attributeStatistics = datasetInfo.getStatistics();
            
            if(attributeStatistics.isEmpty()){
                    
                /* Statistics were not calculated. */
                                
                JOptionPane.showMessageDialog(null, "The statistics of selected dataset were not calculated, you must first open it in Preprocess tab.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    
            }else{
                    
                /* Statistics were calculated. */
                    
                /* Name of the attribute. */
                String attributeName;
                
                /* Number of necessary attributes for calculating flux of energy that were found. */
                int foundAttributes=0;
                
                /* Clears current available variables of the buoy to select. */
                getAvailableBuoyVariables().clear();
                
                /* Sets all attributes's name.*/
                for (DatasetInformation.AttributeStatistics attribute : attributeStatistics) {

                    /* Gets the name of the attribute. */
                    attributeName=attribute.getAttributeName();
                            
                    /* DATE attribute can not be predicted. */
                    if(!attributeName.equals("DATE")){
                        
                        /* Adds the variable as available to select. */
                        getAvailableBuoyVariables().add(attributeName);
                        
                        /* Adds one attribute's name. */
                        getView().addAttributeNameToPredict(attributeName);
                        
                        
                        /* 
                            Checks that necessary attributes for calculating flux of energy are present in selected dataset.
                                - WVHT          must be present once.
                                - APD           must be present once.
                                - FLUXOFENERGY  not present, because it will be added in matching process.
                        
                            So number of necessary attributes must be 2.
                        */
                        
                        if(attributeName.equals("WVHT")){
                            
                            /* Checks WVHT attribute. */
                            foundAttributes=foundAttributes+1;
                        
                        }else if(attributeName.equals("APD")){
                            
                            /* Checks APD attribute. */
                            
                            foundAttributes=foundAttributes+1;
                            
                        }else if(attributeName.equals("FLUXOFENERGY")){
                            
                            /* Checks FLUXOFENERGY attribute. */
                            
                            foundAttributes=foundAttributes+1;
                        }
                                            
                    }

                }
                
                /* Checks if flux of energy can be calculated. */
                if(foundAttributes==2){
                    
                    /* Flux of energy can be calculated. */
                    setAttributtesForFluxOfEnergy(true);
                
                }else{
                
                    /* Flux of energy can not be calculated. */
                    setAttributtesForFluxOfEnergy(false);
                }
                
                /* Checks that the selected dataset contains the selected variables of the buoy. */
                checkSelectedBuoyVariables();
                
            }
        }else{
            
            /* There was an error while reading the selected dataset file. */
                
            JOptionPane.showMessageDialog(null, "The file "+ filename +" could not be readed.", "Error", JOptionPane.ERROR_MESSAGE);
                    
        }

    }    
    
    
    /**
     * Checks the current selection of the variables of the buoy to include 
     * and updates the model of the view that shows the name of the variables.
     */
    private void checkSelectedBuoyVariables(){
        
        /*
            It may happens that the intermediate or pre-processed dataset opened may contain different variables,
            so it is necessary to check that the selected variables of the buoy used are currently in the dataset.
        */
        ArrayList<String> checkedBuoyVariables = new ArrayList<>();
        
        /* Clears the current content of the model of the view that shows the selected variables. */
        getModelBuoyVariables().clear();

        /* Checks all the variables used and creates the ckecked ones. */
        for(String variable : getSelectedBuoyVariables()){

            /* Checks the variable. */
            if(getAvailableBuoyVariables().contains(variable)==true){

                /* The variable is correct. */

                /* Adds the variable. */
                getModelBuoyVariables().addElement(variable);

                checkedBuoyVariables.add(variable);

            }else{

                /* The variable is not correct. */
                JOptionPane.showMessageDialog(null, "The variable " + variable + " was not found in the dataset "+getView().getOpenedDataset()+".\n"+
                        "The variable will not be included as input.", "Message", JOptionPane.INFORMATION_MESSAGE);

            }

        }
        
        /* Sets the model with the names of the variables of the buoy to include. */
        getView().setModelBuoyVariables(getModelBuoyVariables());
                           
        /* Sets the names of the variables of the buoy to use in matching. */
        setSelectedBuoyVariables(checkedBuoyVariables);

        /* Sets the new text of the button. */
        if(checkedBuoyVariables.isEmpty()==true){

            getView().setBtnModifyBuoyVariables("Add");

        }else{

            getView().setBtnModifyBuoyVariables("Modify");

        }

    }    
   
                 
    /**
     * Checks if user typed all input data of matching configuration in Tab: Matching configuration.
     * @return True if user typed all input data of matching configuration in Tab: Matching configuration or False if not.
     */
    private boolean checkMatchingConfigurationOfTabMatching(){
        
        /* 
            Checks if user typed all input data.
        */
                
        boolean result = true;
        
        /* To check selected dataset/preprocessed dataset and reanalysis data files. */
        File filename;
        
        /* Gets view. */
        InterfaceViewManageBuoys theView = getView();
        
        /* Checks dataset/preprocessed dataset. */
        if(theView.getSelectedMatchingDataset().isEmpty()){
            
            JOptionPane.showMessageDialog(null, "Select an intermediate or pre-processed dataset to apply matching.", "Message", JOptionPane.INFORMATION_MESSAGE);
            result = false;
            
        }else{
            
            /* Root. */
            String root = System.getProperty("user.dir", ".")+File.separator+"DB"+File.separator;
            
            filename = new File(root+getPathOfSelectedDatasetToMatching());
        
            /* Checks if dataset/preprocessed dataset file exists.*/
            if (filename.exists() == false){
                
                JOptionPane.showMessageDialog(null, "The selected dataset "+filename.getName()+" does not exist.", "Message", JOptionPane.INFORMATION_MESSAGE);
                result = false;
                
            }else{
                
                /* Checks attribute to predict. */
                if(theView.getSelectedAttributeToPredict().isEmpty()){
            
                    JOptionPane.showMessageDialog(null, "Select an attribute to predict.", "Message", JOptionPane.INFORMATION_MESSAGE);                                
                    result = false;                    
                }else{
                    
                    /* Checks if all selected reanalysis data files exist. */

                    /* Path to reanalysis files. */
                    String path = System.getProperty("user.dir", ".")
                          +File.separator+"DB"+File.separator+"reanalysisFiles"+File.separator;

                    for (String oneFile : getSelectedReanalysisFiles()) {

                        /* Gets reanalysis data file.  */
                        filename = new File(path+oneFile);

                        /* Checks if exists the file. */
                        if (filename.exists() == false){

                            JOptionPane.showMessageDialog(null, "The selected reanalysis data file "+filename.getName()+" does not exist.", "Message", JOptionPane.INFORMATION_MESSAGE);
                            result = false;
                        }
                    }

                    if(result==true){

                        /*
                            Checks if reanalysis data .NC files have the same times and coordinates.
                        
                            Also calculates the max of number of nearest geopoints to consider,
                            that's why the function checkCompatibilityReanalysisFiles() is called
                            when there is one reanalysis data file selected.
                        
                        */
                        
                        if(getSelectedReanalysisFiles().size()>0){

                            result=checkCompatibilityReanalysisFiles(getSelectedReanalysisFiles());

                            if(result==false){

                                JOptionPane.showMessageDialog(null, "The selected reanalysis data files don't have the same dates and coordinates.\nOr the reanalysis data variable is the same.\n\nPlease check your selected reanalysis data files.", "Message", JOptionPane.INFORMATION_MESSAGE);

                            }else{

                                /* Checks number of nearest geopoints to consider. */
                                if(isValidNumberNearestGeopoints(theView.getNumberNearestGeopointsToConsider())==false){

                                    JOptionPane.showMessageDialog(null, "The number of nearest reanalysis nodes is not valid.", "Message", JOptionPane.INFORMATION_MESSAGE);
                                    result = false;
                                }

                            }                                                                    
                        }else{

                            /* None reanalysis file was selected. */
                            JOptionPane.showMessageDialog(null, "Please, select a reanalysis data file to use in Matching.", "Message", JOptionPane.INFORMATION_MESSAGE);
                            result = false;
                            
                        }if(theView.getCalculateFluxOfEnergy()==true && getAttributtesForFluxOfEnergy()==false) {
                              
                            /* Flux of energy can not be calculated because necessary attributes are not present in selected dataset. */

                            JOptionPane.showMessageDialog(null, "\nThe flux of energy can not be calculated. Please, check that: \n\n  ->  WVHT and APD attribrutes are present in the selected dataset.\n  ->  FLUXOFENERGY attribute is not present in the selected dataset.\n\n", "Message", JOptionPane.INFORMATION_MESSAGE);
                            
                            result=false;
                                
                        }
                    }
                }
            }                 
        }
        
        return result;
    }    

    
    
    /**
     * Checks if the typed matching configuration in Tab: 'Final datasets' is correct.
     * 
     * @return True if the typed matching configuration in Tab: 'Final datasets' is correct or False if not.
     */
    private boolean checkMatchingConfigurationOfTabFinalDatasets(){
        
        /* 
            Checks if user typed all input data.
        */
                
        boolean result = true;
        
        /* Type of matching. */
        String typeOfMatching = getCurrentMatchingConfiguration().getTypeOfMatching();
        
        /* Checks matching configuration depending on type of matching. */
        switch (typeOfMatching) {
            
            case "Direct matching":
                
                /* Only the output folder. */                
                /* Nothing else to check. */
                
                break;
                
            case "Classification":
                                    
                /* Checks if prediction horizon is correct. */
                if( (getCurrentMatchingConfiguration().getPredictionHorizon()/getPredictionHorizonInterval()) > (getNumInstancesOfFinalDataset() -1) ){

                    JOptionPane.showMessageDialog(null, "The current prediction horizon exceeds the number of instances,\nas a consequence there are no instances to show.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    result = false;

                }else{
                
                    /* Checks the inferior value and the superior value of each threshold. */
                    result = checkThresholdsIntervals();                    
                
                }                
                
                break;

            case "Regression":
                
                /* Checks if prediction horizon is correct. */
                if( (getCurrentMatchingConfiguration().getPredictionHorizon()/getPredictionHorizonInterval()) > (getNumInstancesOfFinalDataset() -1) ){                    

                    JOptionPane.showMessageDialog(null, "The current prediction horizon exceeds the number of instances,\nas a consequence there are no instances to show.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    result = false;

                }
               
                break;

        }        
        
        return result;
        
    }    
    
    
  
    /**
     * Sets current matching configuration of tab: 'Matching configuration' typed by the user.
     */
    private void setCurrentMatchingConfigurationOfTabMatching(){
        
        /* Gets view. */
        InterfaceViewManageBuoys theView = getView();        
        
        /* Gets current configuration of matching. */
        MatchingInformation matchingConfiguration = getCurrentMatchingConfiguration();
       

        /* Sets current matching configuration of tab: 'Matching configuration' typed by the user. */
        
        
        /* Sets Id of the selected buoy. */
        matchingConfiguration.setIDBuoy(getIdBuoyToMatching());
        
        /* Sets StationID of the selected buoy. */
        matchingConfiguration.setStationID(theView.getSelectedStationIDMatching());

        /* Sets date of creation. */
        matchingConfiguration.setDateCreation(GregorianCalendar.getInstance());

        /* Sets dataset/preprocessed dataset used in matching. */
        matchingConfiguration.setDatasetFileName(theView.getSelectedMatchingDataset());

        /* Sets dataset/preprocessed dataset file path used in matching. */
        matchingConfiguration.setDatasetFilePath(getPathOfSelectedDatasetToMatching());            
        
        /* Sets type (Dataset/Preprocessed) dataset file path used in matching. */
        matchingConfiguration.setTypeDatasetFile(getTypeOfSelectedDatasetToMatching());            

        /* Sets reanalysis data files used in matching. */
        matchingConfiguration.setReanalysisFiles(getSelectedReanalysisFiles());
        
        /* Sets reanalysis data files vars used in matching. */
        matchingConfiguration.setReanalysisVars(getSelectedReanalysisVars());
                
        /* Sets flux of energy. */
        matchingConfiguration.setCalculateFluxOfEnergy(theView.getCalculateFluxOfEnergy());

        /* Sets name of the attribute to predict depending on 'Flux of energy' selection. */
        if(theView.getCalculateFluxOfEnergy()==true){
            
            /* Flux of energy will be used as output. */
            matchingConfiguration.setAttributeNameToPredict("FLUXOFENERGY");
        
        }else{
                            
            /* Sets name of the attribute to predict. */
            matchingConfiguration.setAttributeNameToPredict(theView.getSelectedAttributeToPredict());
                
        }

        /* Sets the names of the variables of the buoy. */
        matchingConfiguration.setBuoyVariables(getSelectedBuoyVariables());

        /* Sets include missing dates. */
        matchingConfiguration.setIncludeMissingDates(theView.getIncludeMissingDates());

        /* Sets number of near points to consider. */
        matchingConfiguration.setNumberOfNearestGeopointsToConsider(Integer.parseInt(theView.getNumberNearestGeopointsToConsider()));

        /* Sets output files to create. */
        matchingConfiguration.setOutputFilesToCreate(theView.getOutputFilesToCreate());
        
        /* Sets type of matching. */
        matchingConfiguration.setTypeOfMatching(getTypeOfMatching());
                
    }
    
    
        
    /**
     * Sets current matching configuration of tab: 'Final datasets' typed by user.
     */
    private void setCurrentMatchingConfigurationOfTabFinalDatasets(){
        
        /* Gets view. */
        InterfaceViewManageBuoys theView = getView();        
        
        /* Gets current configuration of matching (of tab 'Matching configuration'). */
        MatchingInformation matchingConfiguration = getCurrentMatchingConfiguration();
        
        /* Sets date of creation. */
        getCurrentMatchingConfiguration().setDateCreation(GregorianCalendar.getInstance());

        /* Sets output folder to save the final datasets. */
        matchingConfiguration.setOutputFolder(theView.getOutputFolder());
        
        /* Sets output files format to create. */
        matchingConfiguration.setOutputFilesFormat(theView.getOutputFilesFormat());

        /* Sets open databases with Weka when finished. */
        matchingConfiguration.setOpenDatabasesWithWEKA(theView.getOpenDatabasesWithWeka());       
        
    }

    
    /**
     * Checks current matching configuration of tab: 'Matching configuration' with typed in view.
     */
    private void checksCurrentTabMatchingConfWithTypedConf(){
   
        /* 
            Checks if user selected tab 'Final datasets' and changed
            matching configuration of tab: 'Matching configuration' without
            clicking on 'Next' button.
        */
    
        /* Gets view. */
        InterfaceViewManageBuoys theView = getView();        
        
        /* Gets current configuration of matching. */
        MatchingInformation matchingConfiguration = getCurrentMatchingConfiguration();
        
        /* Checks tab: 'Final datasets' is selected. */
        if(getView().getSelectedTab()==4){

            /* Checks if user changed the configuration of matching but did not click on 'Next' button. */
            
            if(matchingConfiguration.getIDBuoy() != getIdBuoyToMatching() ||
               !matchingConfiguration.getStationID().equals(theView.getSelectedStationIDMatching()) ||
               !matchingConfiguration.getDatasetFileName().equals(theView.getSelectedMatchingDataset()) ||
               !matchingConfiguration.getDatasetFilePath().equals(getPathOfSelectedDatasetToMatching()) ||
               !matchingConfiguration.getTypeDatasetFile().equals(getTypeOfSelectedDatasetToMatching()) ||
               !matchingConfiguration.getReanalysisFiles().equals(getSelectedReanalysisFiles()) ||
               !matchingConfiguration.getReanalysisVars().equals(getSelectedReanalysisVars()) ||
               matchingConfiguration.getCalculateFluxOfEnergy()!=theView.getCalculateFluxOfEnergy() ||
               (matchingConfiguration.getCalculateFluxOfEnergy()==false && 
                 !matchingConfiguration.getAttributeNameToPredict().equals(theView.getSelectedAttributeToPredict())) ||
                !matchingConfiguration.getBuoyVariables().equals(getSelectedBuoyVariables()) ||
                matchingConfiguration.getIncludeMissingDates() != theView.getIncludeMissingDates() ||                
                matchingConfiguration.getNumberOfNearestGeopointsToConsider() != Integer.parseInt(theView.getNumberNearestGeopointsToConsider()) ||
                matchingConfiguration.getOutputFilesToCreate() != theView.getOutputFilesToCreate() ||
               !matchingConfiguration.getTypeOfMatching().equals(getTypeOfMatching())){
                
                /*  
                    User changed matching configuration of tab: 'Matching configuration' without
                    clicking on 'Next' button.    
                */
            
                JOptionPane.showMessageDialog(null, "The matching configuration have been changed in tab: 'Final datasets'.\n\nPlease click on 'Next' button to run the matching process again.", "Message", JOptionPane.INFORMATION_MESSAGE);

            }        
        
        }
    
    }
        
    
    /**
     * Sets information of the selected buoy to use in matching process.
     * @param idBuoy Buoy's id.
     */
    private void setSelectedBuoyToMatching(int idBuoy){
        
        /* Sets information of the selected buoy to matching. */
               
        /* Number of buoys. */
        int numBuoys = getView().getModelBuoys().getRowCount();
        
        /* Row of the buoy. */
        int row=-1;
                
        /* Search idBuoy in tableBuoys. */
        for(int i=0;(i<numBuoys && row==-1);i++){
            
            if ( (int) getView().getModelBuoys().getValueAt(i, 0)==idBuoy){

                /* Row of the buoy. */
                row=i;
            
            }        
        }
                    
        /* Sets information of the buoy. */
                    
        /* Sets ID. */            
        setIdBuoyToMatching(idBuoy);

        /* Sets Station ID. */            
        getView().setSelectedStationIDMatching( (String) getView().getModelBuoys().getValueAt(row, 1));
        
        /* Sets Latitude North (.NC files have latitude North). */
              
        if( ((String) getView().getModelBuoys().getValueAt(row, 4)).equals("S") ){

            /* South: negative */
            setLatitudeBuoyToMatching((0 - Double.parseDouble( (String) getView().getModelBuoys().getValueAt(row, 3))));

        }else{

            /* North: positive */
            setLatitudeBuoyToMatching(Double.parseDouble( (String) getView().getModelBuoys().getValueAt(row, 3)));

        }                        
        
        
        /* Sets Longitude East. (.NC files have longitude East). */
        
        if( ((String) getView().getModelBuoys().getValueAt(row, 6)).equals("W")){

            /* Longitude in East degrees. */
            setLongitudeBuoyToMatching((360 - Double.parseDouble( (String) getView().getModelBuoys().getValueAt(row, 5))));

        }else{

            /* Longitude in East degrees. */
            setLongitudeBuoyToMatching(180 + Double.parseDouble( (String) getView().getModelBuoys().getValueAt(row, 5)));

        }

    }
    
    
    /**
     * Checks if reanalysis data files are compatibles and sets max number of nearest geopoints to consider.
     * @param reanalysisFiles Reanalysis data files to check.
     * @return True if reanalysis data files are compatibles or False if not.
     */
    private boolean checkCompatibilityReanalysisFiles(ArrayList<String> reanalysisFiles){
        
        /* 
            Checks if reanalysis data .NC files have the same times and coordinates and
            different reanalysis variables. Also gets the max of nearest geopoints to consider.        
        */
        
        boolean result = true;

        /* Utilities. */
        Utils util = new Utils();

        /* Number of geopoints. */
        int numberOfGeopoints=util.numberOfGeopoints(reanalysisFiles);
        
        /* Sets max of nearest geopoints to consider. */
        setMaxNearestGeopoints(numberOfGeopoints);

        /* Sets text of max of nearest geopoints to consider. */
        getView().setTextOfNumberNearestGeopointsToConsider(numberOfGeopoints);
        
        /* Checks if reanalysis files are compatibles. */
        if(numberOfGeopoints==0){
            
            /* Selected reanalysis files are not compatibles. */
            result=false;
        
        }
        
        return result;
        
    }
    
            
    /**
     * Checks a valid number of nearest geopoints to consider.
     * @param numberNearestGeopoints Number of nearest geopoints to consider.
     * @return True/False if the number of nearest geopoints to consider is valid or not.
     */
    private boolean isValidNumberNearestGeopoints(String numberNearestGeopoints){

        /* Regular expression to check. */
        
        String regex="[0-9]{1,}";
        
        /* Checks regular expression and value. */
        boolean result = numberNearestGeopoints.matches(regex) && 
                        (Integer.parseInt(numberNearestGeopoints)>0 && 
                         Integer.parseInt(numberNearestGeopoints)<=getMaxNearestGeopoints());

        return result;
        
    }
    
        
    /**
     * Checks if flux of energy was selected or unselected by user.
     */
    private void doCheckCalculateFluxOfEnergy(){
                            
        /* When flux of energy is used as output, the attribute to predict selection is disabled,
           and vice versa. */
                
        getView().setEnabledAttributeToPredict(!getView().getCalculateFluxOfEnergy());
        
        /* 
            If flux of energy is not selected removes the current attribute to predict from the selected variables of the buoy to select.
            (Just in case it was selected. )
        */
        if(getView().getCalculateFluxOfEnergy()==false){
            
            doAttributeToPredictChanged();
        
        }                        
        
    }
    
    
    /**
     * Removes selected attribute to predict from current selected variables to predict.
     */
    private void doAttributeToPredictChanged(){

        /* Removes selected attribute to predict from current selected variables to predict. */
        
        /* Gets the current attribute to predict. */
        String attribute = getView().getSelectedAttributeToPredict();
        
        /* Checks if the current attribute is already selected for using it in matching process. */
        if(getSelectedBuoyVariables().indexOf(attribute) != -1){
            
            JOptionPane.showMessageDialog(null, "The variable "+attribute+" has been deleted from the current selection of variables to use as inputs\n"+
                    "because it has been selected as attribute to predict.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            /* Removes the attribute because it was already selected. */
            getSelectedBuoyVariables().remove(attribute);
        
            /* Updates the model in the view. */
            updateSelectionOfBuoyVariables();            

        }
        
    }    

    
    /**
     * Updates in the view the current selected variables of the buoy to predict.
     */
    private void updateSelectionOfBuoyVariables(){
        
        /* Updates in the view the current selected variables of the buoy to predict. */
        
        /* Clears older selection. */
        getModelBuoyVariables().clear();
        
        /* Gets current selection. */
        for(int i=0;i<getSelectedBuoyVariables().size();i++){

            /* Adds the selected variable of the buoy. */
            getModelBuoyVariables().addElement(getSelectedBuoyVariables().get(i));

        }                                

        /* Sets selection in view. */
        getView().setModelBuoyVariables(getModelBuoyVariables());    
      
    }
        
        
    /**
     * Gets type of matching selected by user.
     * @return Type of matching selected by user.
     */
    private String getTypeOfMatching(){
        
        /* Type of matching selected by user. */
        String typeOfMatching;
        
        
        /* Checks user selection. */
        if (getView().getDirectMatching()==true){
            
            /* User selected Direct matching. */
            typeOfMatching="Direct matching";
        
        }else{
            
            if (getView().getClassification()==true){

                /* User selected Classification. */
                typeOfMatching="Classification";
                
            }else{
                
                /* User selected Regression. */
                typeOfMatching="Regression";
           
            }
       
        }
        
        /* User selection. */
        return typeOfMatching;
               
    }
    
        
    /**
     * Sets type of matching selected by user.
     * @param typeOfMatching Type of matching selected by user.
     */
    private void setTypeOfMatching(String typeOfMatching){
                
        /* Checks user selection. */
        if (typeOfMatching.equals("Direct matching")){
            
            /* User selected Direct matching. */
            getView().setDirectMatching();
        
        }else{
            
            if (typeOfMatching.equals("Classification")){

                /* User selected Prediction. */
                getView().setClassification();
                
            }else{
                
                /* User selected Regression. */
                getView().setRegression();
           
            }
       
        }
                       
    }


    /**
     * Enables/disables panels in tab: 'Final datasets' depending on type of matching.
     * @param attributeToPredictChanged True if the user changed the attribute to predict or False if not.
     * 
     * @return True if there are instances to show or False if not.
     */
    private boolean enablePanelsTabFinalDatasets(boolean attributeToPredictChanged){
        
        /* To know if there are instances to show. */
        boolean result=true;
        
        /* Type of matching. */
        String typeOfMatching = getTypeOfMatching();
        
        /* Enables/disables panels in tab: 'Final datasets' depending on type of matching. */
        
        /* Disable both panels. (Direct matching) */
        getView().setEnabledPanelClassificationThresholds(false);
        getView().setEnabledPanelPredictionHorizon(false);
        
        switch (typeOfMatching) {            
            
            case "Direct matching":
                
                /*  All panels are disabled. */

                getView().setTitlePanelThresholds("Thresholds for classification.");
                               
                /* Clear content of classification panel. */
                getView().getModelThresholds().setRowCount(0);
                getCurrentMatchingConfiguration().getThresholdsClass().clear();
                getCurrentMatchingConfiguration().getThresholdsDescription().clear();
                getCurrentMatchingConfiguration().getThresholdsValues().clear();
                                
                /* Sets the prediction horizon to 0. */
                getCurrentMatchingConfiguration().setPredictionHorizon(0);
                
                /* Shows current prediction horizon. */
                showPredictionHorizon();
                
                /* Enables create final datasets button. */
                getView().setEnablebtnCreateFinalDatasets(true);
                getView().setBtnCreateFinalDatasetsToolTipText("Create final datasets");
                
                /* Visualises / Hides DATE attribute depending on user selection. */
                doVisualiseDateAttribute();
                                
                break;

                
            case "Classification":

                /* Gets min in string format with five digits after decimal. */
                String minInStringFormat = new DecimalFormat("#############.#####").format(getMinOfFinalDataset());

                /* Gets max in string format with five digits after decimal. */
                String maxInStringFormat = new DecimalFormat("#############.#####").format(getMaxOfFinalDataset());

                /*  Enables both panels. */
                getView().setEnabledPanelClassificationThresholds(true);
                getView().setEnabledPanelPredictionHorizon(true);
                
                /* Clears current content of the threshold.*/
                getView().setThresholdClass("");
                getView().setThresholdDescription("");
                getView().setThresholdValue("");

                /* Sets buttons for adding a new threshold.*/
                getView().setBtnAddThresholdActionCommand("Add threshold");
                getView().setBtnAddThresholdText("Add");
                getView().setBtnAddThresholdToolTipText("Add new threshold");

                
                /* Sets the information of the thresholds. */
                
                /* Checks if the user changed the attribute to predict or Direct matching / Regression -> Classification. */
                if(attributeToPredictChanged==true || getCurrentMatchingConfiguration().getThresholdsValues().isEmpty()==true){
                    
                    /* Clears current thresholds. */
                    getCurrentMatchingConfiguration().getThresholdsClass().clear();
                    getCurrentMatchingConfiguration().getThresholdsDescription().clear();
                    getCurrentMatchingConfiguration().getThresholdsValues().clear();
                    getCurrentMatchingConfiguration().getThresholdsValues().clear();

                    /* Creates a default threshold with min and max value. */
                    getCurrentMatchingConfiguration().getThresholdsClass().add("A");
                    getCurrentMatchingConfiguration().getThresholdsDescription().add("Default class");
                    getCurrentMatchingConfiguration().getThresholdsValues().add(getMinOfFinalDataset());
                    getCurrentMatchingConfiguration().getThresholdsValues().add(getMaxOfFinalDataset());                                        
                    
                }
                                   
                /* Sets thresholds in tab: 'Final datasets' */
                setThresholdFromMatchingConfiguration();
                
                /* Orders thresholds by the inferior value of the threshold. */
                getView().setOrderThresholds(3);
                
                /* Sets the title of the panel. */
                getView().setTitlePanelThresholds("Attribute: "+getCurrentMatchingConfiguration().getAttributeNameToPredict()+"    min: "+minInStringFormat+
                                                  "    max: "+maxInStringFormat+"    #instances:"+ getNumInstancesOfFinalDataset());
                
                /* Checks if use changed from 'Direct Matching' to: 'Classification' or 'Regression'. */
                if(getCurrentMatchingConfiguration().getPredictionHorizon()==0){
                    
                    /* Sets prediction horizon to the current prediction interval. */
                    getCurrentMatchingConfiguration().setPredictionHorizon(getPredictionHorizonInterval());
                    
                    /* Shows the current prediction horizon. */
                    showPredictionHorizon();
                
                }
                
                /* Updates final dataset with current configuration. */
                result=doUpdateFinalDataset(false);
                
                break;

            case "Regression":
                
                /*  Enables 'Prediction horizon' panel. */
                getView().setEnabledPanelPredictionHorizon(true);
                getView().setTitlePanelThresholds("Thresholds for classification.");
                
                /* Clear content of classification panel. */
                getView().getModelThresholds().setRowCount(0);
                getCurrentMatchingConfiguration().getThresholdsClass().clear();
                getCurrentMatchingConfiguration().getThresholdsDescription().clear();
                getCurrentMatchingConfiguration().getThresholdsValues().clear();
                
                /* Disables create final datasets button. */
                getView().setEnablebtnCreateFinalDatasets(false);
                getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
                
                /* Checks if use changed from 'Direct Matching' to: 'Classification' or 'Regression'. */
                if(getCurrentMatchingConfiguration().getPredictionHorizon()==0){
                    
                    /* Sets prediction horizon to the current prediction interval. */
                    getCurrentMatchingConfiguration().setPredictionHorizon(getPredictionHorizonInterval());
                    
                    /* Shows the current prediction horizon. */
                    showPredictionHorizon();
                
                }
                
                /* Updates final dataset with current configuration. */
                result=doUpdateFinalDataset(false);
               
                break;

        }
        
        /* Checks if there are instances to show. */
        if(result==true){
            
            /* Enables tab: 'Final datasets'. */
            getView().setEnabledTab(4, true);            
        
        }
        
        return result;
        
    }

        
    /**
     * Checks if the number of geopoints typed by the user is equal to 1 for 
     * selecting 'N' final datasets (one per each geopoint).
     */
    private void checkNumberOfTypedGeopoints(){

        /* Checks the number of geopoints typed by the user. */
        if(Integer.parseInt(getView().getNumberNearestGeopointsToConsider())==1){
            
            /* The user typed '1' nearest geopoint to consider. */
            
            /* Sets 'N' final datasets (one per each geopoint) in 'Number of final datasets' option. */
            getView().setOutputFilesToCreate(1);
            
            /* Disables 'Number of final datasets' option. */
            getView().setEnabledOutputFilesToCreate(false);
            
        
        }else{
            
            /* Enables 'Number of final datasets' option. */
            getView().setEnabledOutputFilesToCreate(true);
        
        }
        
    }
    

    
/*  Tab: Final datasets. */
    
    
    /**
     * Deletes the selected threshold.
     */
    private void doDeleteThreshold(){
        
        /* Deletes the selected threshold. */

        /* Gets the selected row to delete. */
        int selectedRow = getView().getSelectedRowThresholds();
                
        if(selectedRow==-1){
            
            /* None row is selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a threshold to delete.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
            
            if(selectedRow==0){
            
                JOptionPane.showMessageDialog(null, "The first threshold can not be deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);
                
            }else{
                
                /* Asks the user that really want to delete the selected threshold. */                
                Object[] options = {"Cancel", "Delete"};
                            
                if (JOptionPane.showOptionDialog(null, "Please, click on 'Delete' to confirm or click on 'Cancel' to abort.\n","Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1){

                    /* Deletes selected threshold. */
                            
                    /* Deletes threshold from matching configuration. */
                    getCurrentMatchingConfiguration().getThresholdsValues().remove(selectedRow);

                    /* Deletes the selected threshold from model. */
                    selectedRow=getView().getRowIndexOfSortedModelofThresholds(selectedRow);
                    getView().getModelThresholds().removeRow(selectedRow);

                    /* Updates current thresholds configuration. */
                    updateThresholdsInMatchingConfiguration();

                    /* Shows thresholds intervals Inferior[, Superior) in tab 'Final datasets'. */            
                    showThresholdsIntervals();

                    JOptionPane.showMessageDialog(null, "The threshold has been deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    
                    /* Disables button 'Create final datasets'. */
                    getView().setEnablebtnCreateFinalDatasets(false);
                    getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
                    
                }
            }
            
        }
        
    }
    
    
    /**
     * Adds a new threshold.
     */
    private void doAddThreshold(){
        
        /* Adds a new threshold. */
        
        /* Gets the view. */
        InterfaceViewManageBuoys theView = getView();
            
        /* Information about the threshold. */
        String className = theView.getThresholdClass();
        String description = theView.getThresholdDescription();
        String value = theView.getThresholdValue();                                             

        /* Checks if all fields are correctly filled. */
        if(checkValuesThreshold(className, description, value, "Add") == true){

            /* Updates the model with the new threshold. */
            theView.getModelThresholds().addRow(new Object[]{className, description, 0, Double.parseDouble(value), 0});
            
            /* Adds the new threshold. */
            getCurrentMatchingConfiguration().getThresholdsValues().add(Double.parseDouble(value));
            
            /* Updates current thresholds in matching configuration. */
            updateThresholdsInMatchingConfiguration();
            
            /* Shows thresholds intervals Inferior[, Superior) in tab 'Final datasets'. */            
            showThresholdsIntervals();
            
            /* Clears current content of the threshold. */
            theView.setThresholdClass("");
            theView.setThresholdDescription("");
            theView.setThresholdValue("");
            
            JOptionPane.showMessageDialog(null, "The threshold has been added.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            /* Disables button 'Create final datasets'. */
            getView().setEnablebtnCreateFinalDatasets(false);
            getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
            
        }
                
    }
    
        
    /**
     * Modify the selected threshold.
     */
    private void doModifyThreshold(){
        
        /* Modify the selected threshold. */
        
        /* Gets the view. */
        InterfaceViewManageBuoys theView = getView();
        
        /* Gets the selected row to delete. */        
        int selectedRow = theView.getSelectedRowThresholds();
                
        if(selectedRow==-1){
            
            /* None row is selected. */
            
            JOptionPane.showMessageDialog(null, "Please, select a threshold to modify.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else{
                        
            /* Checks if user selected the first threshold. */
            if(selectedRow==0){
                
                /* The first threshold can not be modified, only class name and description. */              
                theView.setJtfValueThreshold(false);
                
            }
                        
            /* Sets the row of the selected threshold for modifying. */
            setRowOfThresholdToModify(selectedRow);
          
            /* Gets index of the row in the sorted model. */
            selectedRow=getView().getRowIndexOfSortedModelofThresholds(selectedRow);
                        
            /* Gets data of the selected threshold. */
            theView.setThresholdClass(theView.getModelThresholds().getValueAt(selectedRow, 0).toString());
            theView.setThresholdDescription(theView.getModelThresholds().getValueAt(selectedRow, 1).toString());
            theView.setThresholdValue(theView.getModelThresholds().getValueAt(selectedRow, 3).toString());
            
            /* Enables button btnAddThreshold for updating the selected threshold. */
            theView.setBtnAddThresholdActionCommand("Update threshold");
            theView.setBtnAddThresholdText("Update");
            theView.setBtnAddThresholdToolTipText("Update the selected threshold");
            
            /* Disables buttons 'Modify threshold' and 'Delete'. */
            theView.setBtnModifyThresholdEnabled(false);
            theView.setBtnDeleteThresholdEnabled(false);                        

        }
                
    }
    
    
    /**
     * Updates the selected threshold.
     */
    private void doUpdateThreshold(){
               
        /* Updates the selected threshold. */

        /* Gets the view. */
        InterfaceViewManageBuoys theView = getView();
            
        /* Information about the threshold. */
        String className = theView.getThresholdClass();
        String description = theView.getThresholdDescription();
        String value = theView.getThresholdValue();                                                

        /* Checks if all fields are correctly filled. */
        if(checkValuesThreshold(className, description, value, "Update") == true){
            
            /* Orders thresholds by the inferior value of the threshold. */
            getView().setOrderThresholds(3);

            /* Gets the selected row to modify. */
            int selectedRow = getRowOfThresholdToModify();
            
            /* Updates threshold value. */
            getCurrentMatchingConfiguration().getThresholdsValues().set(selectedRow, Double.parseDouble(value));
          
            /* Gets index of the row in the sorted model. */
            selectedRow=theView.getRowIndexOfSortedModelofThresholds(selectedRow);
            
            /* Sets data of the modified threshold. */
            theView.getModelThresholds().setValueAt(Double.parseDouble(value), selectedRow, 3);
            theView.getModelThresholds().setValueAt(className, selectedRow, 0);
            theView.getModelThresholds().setValueAt(description, selectedRow, 1);
            
            /* Clears current content of the threshold. */
            theView.setThresholdClass("");
            theView.setThresholdDescription("");
            theView.setThresholdValue("");

            /* Enables button btnAddThreshold for adding a new threshold. */
            theView.setBtnAddThresholdActionCommand("Add threshold");
            theView.setBtnAddThresholdText("Add");
            theView.setBtnAddThresholdToolTipText("Add new threshold");

            /* Enables buttons 'Modify threshold' and 'Delete'. */
            theView.setBtnModifyThresholdEnabled(true);
            theView.setBtnDeleteThresholdEnabled(true);
            
            /* Updates current thresholds configuration. */
            updateThresholdsInMatchingConfiguration();
            
            /* Shows thresholds intervals Inferior[, Superior) in tab 'Final datasets'. */            
            showThresholdsIntervals();

            JOptionPane.showMessageDialog(null, "The threshold has been modified.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            /* Enables setJtfValueThreshold for adding o updating again. */
            theView.setJtfValueThreshold(true);
            
        }
        
    }
    
    
    
    /**
     * Checks the values of the threshold typed by the user.
     * @param className Name of the class.
     * @param description Description of the class.
     * @param value Value of the threshold.
     * @param typeOfChecking Type of the checking 'Add' or 'Update'.
     * @return True if each field's value is valid or false if not.
     */
    private boolean checkValuesThreshold(String className, String description, String value, String typeOfChecking){
                
        /* Result of checking fields's values. */
        boolean result = false;                   
        
        /* Generic checking, for 'Add' and 'Update'. */
        
        /* Checks if all values are correctly filled. */
        if(className.trim().equals("") || description.trim().equals("")){

            JOptionPane.showMessageDialog(null, "The Class and Description of the threshold must be filled.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        
        }else if(className.length()>15){
            
            JOptionPane.showMessageDialog(null, "Maximum length for Class is 20 characters.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }else if(description.length()>50){
            
            JOptionPane.showMessageDialog(null, "Maximum length for Description is 50 characters.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        
        }else if(isValidThresholdValue(value) == false){
            
            JOptionPane.showMessageDialog(null, "The value of the threshold is not correct, please check it.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }else if(Double.parseDouble(value) < getMinOfFinalDataset()){
            
            JOptionPane.showMessageDialog(null, "The value of the threshold can not be lower than the current min of the final dataset.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }else if(Double.parseDouble(value) > getMaxOfFinalDataset()){
            
            JOptionPane.showMessageDialog(null, "The value of the threshold can not be greather than the current max of the final dataset.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }else{
            
            /* All values are correctly filled. */
            result = true;
        
        }
        
        
        if(result==true){
            
            /* Specific checking, for 'Add' and 'Update'. */
            
            result=false;
            
            switch (typeOfChecking) {

                case "Add":

                    if(getCurrentMatchingConfiguration().getThresholdsClass().contains(className) == true){
            
                        JOptionPane.showMessageDialog(null, "The Class already exists.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        
                    }else if(getCurrentMatchingConfiguration().getThresholdsDescription().contains(description) == true){
            
                        JOptionPane.showMessageDialog(null, "The Description already exists.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
                    }else if(getCurrentMatchingConfiguration().getThresholdsValues().contains((Double) Double.parseDouble(value)) == true){
            
                        JOptionPane.showMessageDialog(null, "The value of the threshold already exists, please check it.", "Message", JOptionPane.INFORMATION_MESSAGE);            
                        
                    }else{
                        
                        /* All values are correctly filled. */
                        result = true;
                    
                    }

                    break;

                    
                case "Update":
                    
                    /* Gets the selected row to modify. */
                    int selectedRow = getRowOfThresholdToModify();
                                        
                    if(getCurrentMatchingConfiguration().getThresholdsClass().contains(className) == true && 
                            getCurrentMatchingConfiguration().getThresholdsClass().indexOf(className) != selectedRow){

                        JOptionPane.showMessageDialog(null, "The Class already exists.", "Message", JOptionPane.INFORMATION_MESSAGE);

                    }else if(getCurrentMatchingConfiguration().getThresholdsDescription().contains(description) == true &&
                            getCurrentMatchingConfiguration().getThresholdsDescription().indexOf(description) != selectedRow){

                        JOptionPane.showMessageDialog(null, "The Description already exists.", "Message", JOptionPane.INFORMATION_MESSAGE);

                    }else if(isValidThresholdValue(value) == false){

                        JOptionPane.showMessageDialog(null, "The value of the threshold is not correct, please check it.", "Message", JOptionPane.INFORMATION_MESSAGE);

                    }else if(getCurrentMatchingConfiguration().getThresholdsValues().contains((Double) Double.parseDouble(value)) == true &&
                             getCurrentMatchingConfiguration().getThresholdsValues().indexOf((Double) Double.parseDouble(value)) != selectedRow){

                        JOptionPane.showMessageDialog(null, "The value of the threshold already exists, please check it.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        
                    }else{
                        
                        /* All values are correctly filled. */
                        result = true;
                    
                    }
                    
                    break;
                    
            }
                        
        }                
                        
        return result;
    
    }
    
    
    /**
     * Checks a valid value of a threshold.
     * @param threshold Threshold to check.
     * @return True if the threshold is valid or False if not.
     */
    private boolean isValidThresholdValue(String threshold){
        
        /* Regular expression to check the threshold. */
        
        String regex="[-]?[0-9]*\\.?[0-9]+";
        
        /* Checks regular expression. */
        boolean result = threshold.matches(regex);

        return result;
        
    }        
    
    
    /**
     * Updates current thresholds in matching configuration.
     */
    private void updateThresholdsInMatchingConfiguration(){

        /* Updates current thresholds configuration. */
        
        /* Orders thresholds by the inferior value of the threshold. */
        getView().setOrderThresholds(3);
        
        /* Information about thresholds. */
        DefaultTableModel datamodel = getView().getModelThresholds();
        
        /* Gets number of thresholds. */
        int numThresholds = datamodel.getRowCount();
        
        /* Gets current configuration of matching. */
        MatchingInformation matchingConfiguration = getCurrentMatchingConfiguration();
        
        /* Deletes current configuration of the thresholds. */
        matchingConfiguration.getThresholdsClass().clear();
        matchingConfiguration.getThresholdsDescription().clear();
        
        /* Row in the sorted model of the thresholds. */
        int row;
        
        /* Adds current configuration of the thresholds. */
        for(int i=0;i<numThresholds;i++){
            
            /* Gets row in the sorted model. */
            row=getView().getRowIndexOfSortedModelofThresholds(i);
            
            /* Adds class. */
            matchingConfiguration.getThresholdsClass().add(datamodel.getValueAt(row, 0).toString());

            /* Adds description of the class. */
            matchingConfiguration.getThresholdsDescription().add(datamodel.getValueAt(row, 1).toString());
            
        }
            
    }
    

    /**
     * Sets thresholds from matching configuration in tab 'Final datasets'.
     */
    private void setThresholdFromMatchingConfiguration(){

        /* Sets thresholds intervals Inferior[, Superior) in tab 'Final datasets'. */
        
        /* Information about thresholds. */
        DefaultTableModel datamodel = getView().getModelThresholds();
        
        /* Gets current configuration of matching. */
        MatchingInformation matchingConfiguration = getCurrentMatchingConfiguration();
                
        /* Gets the thresholds.*/
        ArrayList<Double> thresholdsValues = getCurrentMatchingConfiguration().getThresholdsValues();
        
        /* Gets number of thresholds. */
        int numThresholds = thresholdsValues.size() - 1;
        
        /* Inferior value of the threshold. */
        double inferiorValue=thresholdsValues.get(0);

        /* Superior value of the threshold. */
        double superiorValue;
        
        /* Deletes current thresholds. */
        datamodel.setRowCount(0);        
                        
        /* Sets intervals Inferior[, Superior) of each threshold. */
        for(int i=0;i<numThresholds;i++){
            
            /* Superior value of the threshold. */
            superiorValue=thresholdsValues.get(i+1);
            
            /* Adds a new threshold. */
            datamodel.addRow(new Object[]{matchingConfiguration.getThresholdsClass().get(i),
                                          matchingConfiguration.getThresholdsDescription().get(i), 0, inferiorValue, superiorValue});
                                                            
            /* Sets inferior value fo next threshold. */
            inferiorValue=superiorValue;

        }                            

    }
        
    
    /**
     * Updates thresholds intervales (min, max] in tab 'Classification'.
     */
    private void showThresholdsIntervals(){

        /* Shows thresholds intervals Inferior[, Superior) in tab 'Final datasets'. */
                
        /* Information about thresholds. */
        DefaultTableModel datamodel = getView().getModelThresholds();
        
        /* Row in sorted model. */
        int row;
        
        /* Gets the thresholds.*/
        ArrayList<Double> thresholdsValues = getCurrentMatchingConfiguration().getThresholdsValues();
        
        /* Gets number of thresholds. */
        int numThresholds = thresholdsValues.size() - 1;
        
        /* Inferior value of the threshold. */
        double inferiorValue=thresholdsValues.get(0);

        /* Superior value of the threshold. */
        double superiorValue;
        
        /* Sorts current thresholds in ascending order. */
        Collections.sort(getCurrentMatchingConfiguration().getThresholdsValues());
                        
        /* Sets intervals Inferior[, Superior) of each threshold. */
        for(int i=0;i<numThresholds;i++){
            
            /* Superior value of the threshold. */
            superiorValue=thresholdsValues.get(i+1);
            
            /* Gets row in the sorted model. */
            row=getView().getRowIndexOfSortedModelofThresholds(i);
                                    
            /* Updates the model. */
            datamodel.setValueAt((Double) inferiorValue, row, 3);
            datamodel.setValueAt((Double) superiorValue, row, 4);
            
            /* Sets 0 the number of instances. */
            datamodel.setValueAt(0, row, 2);
            
            /* Sets inferior value fo next threshold. */
            inferiorValue=superiorValue;

        }                            
            
    }
        
    
    /**
     * Shows number of instances of each class in thresholds typed by the user.
     * 
     * @param numberOfInstancesInEachClass Number of instances in each class.
     */
    private void showNumberOfInstancesInThresholds(ArrayList<Integer> numberOfInstancesInEachClass){

        /* Shows number of instances of each class in thresholds typed by the user. */
                
        /* Information about thresholds. */
        DefaultTableModel datamodel = getView().getModelThresholds();
        
        /* Row in sorted model. */
        int row;
        
        /* Gets number of thresholds. */
        int numThresholds = datamodel.getRowCount();
                                
        /* Sets number of instances of each class. */
        for(int i=0;i<numThresholds;i++){
            
            /* Gets row in the sorted model. */
            row=getView().getRowIndexOfSortedModelofThresholds(i);
                                    
            /* Updates the model. */
            datamodel.setValueAt((int) numberOfInstancesInEachClass.get(i), row, 2);

        }                            
            
    }
        
    
    /**
     * Checks that each class has one instance at least.
     * 
     * @return True if the typed thresholds has at least one instance or False if not.
     */
    private boolean checkThresholdsNumInstances(){

        /*
            Checks that each class has one instance at least.
        */

        boolean result=true;
        
        /* To store the errors that have been found in checking.  */
        ArrayList<String> errorsFound = new ArrayList<>();
        
        /* Orders thresholds by the inferior value of the threshold. */
        getView().setOrderThresholds(3);
        
        /* Information about thresholds. */
        DefaultTableModel datamodel = getView().getModelThresholds();
        
        /* Row in sorted model. */
        int row;
        
        /* Gets the number of the thresholds. */
        int numThresholds = datamodel.getRowCount();
        
        /* Inferior value of the threshold. */
        String className;
        
        /* Number of instances in each class. */
        int numOfInstances;
                
        /* Checks each threshold. */
        for(int i=0;i<numThresholds;i++){

            /* Gets row in the sorted model. */
            row=getView().getRowIndexOfSortedModelofThresholds(i);
            
            /* Gets name of the class. */
            className=(String) datamodel.getValueAt(row, 0);
            
            /* Gets number of instances in threshold. */
            numOfInstances = (int) datamodel.getValueAt(row, 2);

            /* Checks that the class has one instance at least. */
            if(numOfInstances == 0){

                /* The threshold has 0 instances. */
                errorsFound.add("The class: " + className + " has 0 instances.");

                result=false;

            }

        }                            

        
        /* Checks if there were any error. */
        if(result==false){
            
            /* Gets each error message. */
            String message="";
            
            for(String errorMessage: errorsFound){
                
                message=message + "        " + errorMessage +"\n";
            
            }
            
            /* Shows the erros that have been found. */
            JOptionPane.showMessageDialog(null, "The number of instances in each class have been checked and\nthe following erros have been found:\n\n" + message, "Message", JOptionPane.INFORMATION_MESSAGE);                        
        
        }

        return result;
    
    }
    
    
    /**
     * Checks that inferior value and superior value of each threshold is correct.
     * 
     * @return True if the typed interval of thresholds are correct or False if not.
     */
    private boolean checkThresholdsIntervals(){

        /*
            Checks that inferior value and superior value of each threshold is correct.
        */

        boolean result=true;
        
        /* To store the errors that have been found in checking.  */
        ArrayList<String> errorsFound = new ArrayList<>();
        
        /* Orders thresholds by the inferior value of the threshold. */
        getView().setOrderThresholds(3);
        
        /* Information about thresholds. */
        DefaultTableModel datamodel = getView().getModelThresholds();
        
        /* Row in sorted model. */
        int row=getView().getRowIndexOfSortedModelofThresholds(0);
        
        /* Gets the number of the thresholds. */
        int numThresholds = datamodel.getRowCount();
        
        /* Inferior value of the threshold. */
        String className="";
        
        /* Checks that inferior value matchs with min value in final dataset. */
        if((Double) datamodel.getValueAt(0, 3) != getMinOfFinalDataset()){
            
            /* Inferior value does not match with min value in final dataset. */
            errorsFound.add("The inferior value of the class: " + (String) datamodel.getValueAt(0, 0) + " does not match with the min value of the final dataset.");
            
            result=false;
            
        }
        
        /* Checks each threshold. */
        for(int i=0;i<numThresholds;i++){

            /* Gets row in the sorted model. */
            row=getView().getRowIndexOfSortedModelofThresholds(i);
            
            /* Gets name of the class. */
            className=(String) datamodel.getValueAt(row, 0);
                                            
            /* Checks that the inferior value if less than the superior value. */
            if((Double) datamodel.getValueAt(row, 3) >= (Double) datamodel.getValueAt(row, 4)){

                /* The inferior value if greather than the superior value. */
                errorsFound.add("The inferior value of the class: " + className + " is greather than the superior value.");

                result=true;

            }

        }                            

        /* Checks that superior value matchs with max value in final dataset. */       
        if((Double) datamodel.getValueAt(row, 4) != getMaxOfFinalDataset()){
            
            /* Superior value does not match with max value in final dataset. */
            errorsFound.add("The superior value of the class: " + className + " does not match with the max value of the final dataset.");
            
            result=false;
            
        }
          
        /* Checks if there were any error. */
        if(result==false){
            
            /* Gets each error message. */
            String message="";
            
            for(String errorMessage: errorsFound){
                
                message=message + "        " + errorMessage +"\n";
            
            }
            
            /* Shows the erros that have been found. */
            JOptionPane.showMessageDialog(null, "The intervals of each threshold have been checked and\nthe following errors have been found:\n\n" + message, "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }

        return result;
    
    }


        
    /**
     * Sets the cell renderer in the table that shows the thresholds typed by user.
     */    
    public void setTblThresholdsCellRenderer() {
    
        /* Sets TableCellRenderer for tblThresholds. */
        
        getView().setTblThresholdsCellRenderer(new DefaultTableCellRenderer() {
                        
            /* Generated by NetBeans. */            
            private static final long serialVersionUID = -347184672129699492L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                /* Component of table. */
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                /* Checks columns and values. */
                
                if(column==2 && (int) value==0 ){
                    
                    /* #Instances = 0 */
                    c.setBackground(Color.lightGray);
                
                }else if(row!=getView().getSelectedRowThresholds()){

                    /* Remaining cells. */
                    
                    /* default color. */
                    
                    c.setBackground(Color.WHITE);
                
                }

                return c;
            }
        });
               
    }
    
        
    /**
     * Sets the cell renderer in the table that shows the content of the final datasets.
     */    
    public void setTblFinalDatasetCellRenderer() {
    
        /* Sets TableCellRenderer for tblFinalDataset. */
        
        getView().setTblFinalDatasetCellRenderer(new DefaultTableCellRenderer() {
            
            /* Generated by NetBeans. */
            private static final long serialVersionUID = -347184672129699492L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                /* Component of the table. */
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                /* Checks if the date is a missing date.  */
                if( column==1 && ( ((String)table.getModel().getValueAt(row, 0)).equals("MissingIncludedDate")  ||
                                   ((String)table.getModel().getValueAt(row, 0)).equals("MissingNotIncludedDate") )
                                  ){

                    /* It is a missing date. */
                    c.setBackground(new Color(212,141,249));
                    
                }else if( column>1 && ((String)table.getModel().getValueAt(row, 0)).equals("MissingNotIncludedDate") && value.equals(" ") ){
                    
                    /* It is a missing date. */
                    c.setBackground(new Color(212,141,249));
                    
                }else{
                
                    /* Checks type of matching. */
                    if(getCurrentMatchingConfiguration().getTypeOfMatching().equals("Classification")==true){

                        /* Classification. */

                        /* Checks if the class is missing. */
                        if(column == table.getColumnCount()-1){

                            if(((String) value).equals("?")==true){

                                /* The class is missing. */
                                c.setBackground(new Color(255, 72, 71));

                            }else if(row!=getView().getSelectedRowFinalDataset()){

                                /* Default color. */                            
                                c.setBackground(Color.WHITE);

                            }
                        /* Missing values of: 'MatchedDate' and 'MissingIncludedDate' */
                        }else if(column>1 && !((String)table.getModel().getValueAt(row, 0)).equals("MissingNotIncludedDate") && ((Double) value).isNaN()){

                            /* It is a missing value. */
                            c.setBackground(new Color(255, 72, 71));

                        }else if(row!=getView().getSelectedRowFinalDataset()){

                            /* Default color. */                            
                            c.setBackground(Color.WHITE);

                        }
                    /* Missing values of: 'MatchedDate' and 'MissingIncludedDate' */
                    }else if(column>1 && !((String)table.getModel().getValueAt(row, 0)).equals("MissingNotIncludedDate") && ((Double) value).isNaN()){

                        /* It is a missing value. */
                        c.setBackground(new Color(255, 72, 71));

                    }else if(row!=getView().getSelectedRowFinalDataset()){

                        /* Remaining cells. */

                        /* Default color. */
                        c.setBackground(Color.WHITE);

                    }
                }

                return c;
            }
        });
       
        
    }
    
        
    /**
     * Sets the cell renderer in the table that shows the statistics of each attribute of the pre-processed dataset.
     */    
    public void setTblStatisticsCellRenderer() {
    
        /* Sets TableCellRenderer for tblFinalDataset. */
        
        getView().setTblStatisticsCellRenderer(new DefaultTableCellRenderer() {
                        
            /* Generated by NetBeans. */
            private static final long serialVersionUID = -347184672129699492L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                /* Component of the table. */
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                /* The column that shows the statistical value. */
                if(column==2 && ((Double) value).isNaN()){
                    
                    /* It is a missing value. */
                    c.setBackground(new Color(255, 72, 71));

                /* The column and row that shows the number of missing values. */
                }else if(column==2 && row==4 && ((Double) value) > 0.0){
                    
                    /* The selected attribute has at least one missing value. */
                    c.setBackground(new Color(255, 72, 71));

                }else if(row!=getView().getSelectedRowStatisticalValues()){

                    /* Default color. */                            
                    c.setBackground(Color.WHITE);

                }

                return c;
            }
        });
       
        
    }
            

    /**
     * 
     * Sets the cell renderer in the table that shows the content of the pre-processed dataset.
     */    
    public void setTblPreprocessedDataCellRenderer() {
    
        /* Sets TableCellRenderer for tblPreprocessedData. */
        
        getView().setTblPreprocessedDataCellRenderer(new DefaultTableCellRenderer() {
                        
            /* Generated by NetBeans. */                       
            private static final long serialVersionUID = -347184672129699492L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                /* Component of the table. */
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                /* If the date is not a missing date, so it has values, checks if any of them is a missing value. */
                if( column==1 && ((String)table.getModel().getValueAt(row, 0)).equals("Missing") ){
                    
                    /* It is a missing date. */
                    c.setBackground(new Color(212,141,249));
                    
                }else if( column>1 && ((String)table.getModel().getValueAt(row, 0)).equals("Missing") && value.equals(" ") ){
                    
                    /* It is a missing date. */
                    c.setBackground(new Color(212,141,249));

                }else if( column==1 && ((String)table.getModel().getValueAt(row, 0)).equals("Duplicated") ){
                    
                    /* It is a duplicated date. */
                    c.setBackground(new Color(242,186,166));

                }else if( column==1 && ((String)table.getModel().getValueAt(row, 0)).equals("Unexpected") ){
                    
                    /* It is an unexpected date. */
                    c.setBackground(new Color(252,227,198));

                }else if( column>1 && !((String)table.getModel().getValueAt(row, 0)).equals("Missing") && ((Double) value).isNaN()){
                    /* If the date is not a missing date, so it has values, checks if any of them is a missing value. */                
                    
                    /* It is a missing value. */
                    c.setBackground(new Color(255, 72, 71));

                }else if(row!=getView().getSelectedRowPreprocessedData()){

                    /* Default color. */                            
                    c.setBackground(Color.WHITE);

                }
                
                return c;
            }
        });
               
    }        
        
    
    /**
     * Increases the current prediction horizon.
     */
    private void doIncreasePredictionHorizon(){
        
        /* Increases the current prediction horizon. */
        
        /* Gets the current prediction horizon. */
        int predictionHorizon = getCurrentMatchingConfiguration().getPredictionHorizon();
        
                        
        /* Checks if prediction horizon can be increased. */
        if( (predictionHorizon/getPredictionHorizonInterval()) < (getNumInstancesOfFinalDataset() -1) ){
            
            /* Increases prediction horizon by prediction horizon interval (in hours) and sets it. */
            getCurrentMatchingConfiguration().setPredictionHorizon(predictionHorizon + getPredictionHorizonInterval());
            
            /* Shows current prediction horizon. */
            showPredictionHorizon();
            
            /* Disables button 'Create final datasets'. */
            getView().setEnablebtnCreateFinalDatasets(false);
            getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
                                
        }else{

            JOptionPane.showMessageDialog(null, "The current prediction horizon exceeds the number of instances.", "Message", JOptionPane.INFORMATION_MESSAGE);                        
        
        }                

    }
    
    
    /**
     * Decreases the current prediction horizon.
     */
    private void doDecreasePredictionHorizon(){
        
        /* Decreases the current prediction horizon. */
        
        /* Gets the current prediction horizon. */
        int predictionHorizon = getCurrentMatchingConfiguration().getPredictionHorizon();
        
        /* 
            Checks if prediction horizon is greather than the prediction horizon interval.
            Currently the min prediction horizon for classification and regression is 6 h.
        */
        if(predictionHorizon!=getPredictionHorizonInterval()){
            
            /* Decreases prediction horizon by prediction horizon interval (in hours) and sets it. */
            getCurrentMatchingConfiguration().setPredictionHorizon(predictionHorizon - getPredictionHorizonInterval());
            
            /* Disables button 'Create final datasets'. */
            getView().setEnablebtnCreateFinalDatasets(false);
            getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
            
            /* Shows current prediction horizon. */
            showPredictionHorizon();
        
        }else{
        
            JOptionPane.showMessageDialog(null, "The minimum prediction horizon is 6 hours.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
        }
        
    }
    

    /**
     * The user changed the 'Synchronise the reanalysis data' selection.
     */
    private void doSynchroniseRDataSelectionChanged(){
        
        /* Disables button 'Create final datasets'. */
        getView().setEnablebtnCreateFinalDatasets(false);
        getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
       
    }
        
    
    /**
     * Shows the current prediction horizon.
     */
    private void showPredictionHorizon(){
        
        /* Shows the current prediction horizon. */
        
        /* Gets the current prediction horizon. */
        int predictionHorizon = getCurrentMatchingConfiguration().getPredictionHorizon();
        
        /* Gets days of prediction horizon. */
        int days=predictionHorizon/24;
                
        /* Gets hours of prediction horizon. */
        int hours=predictionHorizon%24;
        
        /* Text with the prediction horizon. */
        String prediction=days + " days and " + hours + " hours";
        
        /* Sets the text with current prediction. */
        getView().setTextOfPredictionHorizon(prediction);

    }
    

    /**
     * Updates final datasets of matching process for classification / rRegression.
     * 
     * @param showMessage True is a message has to be showed to the user or False if not.
     * @return True if the typed matching configuration in Tab: 'Final datasets' is correct or False if not.
     */
    private boolean doUpdateFinalDataset(boolean showMessage){
                                                  
        /* Updates final datasets of matching process for classification / regression. */                        
        
        boolean result;
        
        /*
            Checks if matching configuration of tab 'Final datasets' is ok.
            (matching configuration of tab 'Matching configuration' was checked when user clicked on 'Next' button).
        */       

        result=checkMatchingConfigurationOfTabFinalDatasets();
        
        if(result==true){

            /* Checks type: 'Classification' or 'Regression' */
            
            switch(getCurrentMatchingConfiguration().getTypeOfMatching()){
            
                case "Classification":

                        
                        /* Updates final dataset with the configuration of prediction horizon. */
                        updateFinalDatasetWithPredictionHorizon();

                    /* Sets 'Synchronise the reanalysis data'. */
                    getCurrentMatchingConfiguration().setSynchroniseRData(getView().getSynchroniseReanalysisData());
                        
                    /* Updates final dataset with the configuration of classification. */
                    updateFinalDatasetWithClassification();
                    
                    /* Checks if a message has to be showed. */
                    if(showMessage==true){
                        
                        JOptionPane.showMessageDialog(null, "Final datasets have been updated with the configuration of classification and prediction horizon.", "Message", JOptionPane.INFORMATION_MESSAGE);
                    }
                                                            
                    /* Checks that each class has at least one instance. */
                    if(checkThresholdsNumInstances() == true){
                        
                        /* Enables button 'Create final datasets'. */
                        getView().setEnablebtnCreateFinalDatasets(true);
                        getView().setBtnCreateFinalDatasetsToolTipText("Create final datasets");
                    
                    }else{
                        
                        /* Disalbes button 'Create final datasets'. */
                        getView().setEnablebtnCreateFinalDatasets(false);
                        getView().setBtnCreateFinalDatasetsToolTipText("Press 'Update final dataset' to generate the final datasets");
                    
                    }
                    
                    /* Shows available final datasets to visualise and table header. */
                    /* By default the content of the selected final dataset is showed. */
                    showFinalDatasetsSelection(getView().getSelectedIndexFinalDatasetToVisualise());
                    
                    /* Visualises / Hides DATE attribute depending on user selection. */
                    doVisualiseDateAttribute();

                    /* Hides output attribute depending on user selection. */
                    doHideOutputAttribute();
                                        
                    break;
                    
                case "Regression":

                        /* Sets 'Synchronise the reanalysis data'. */
                        getCurrentMatchingConfiguration().setSynchroniseRData(getView().getSynchroniseReanalysisData());
                        
                        /* Updates final dataset with the configuration of prediction horizon. */
                        updateFinalDatasetWithPredictionHorizon();
                        
                        /* Checks if a message has to be showed. */
                        if(showMessage==true){

                            JOptionPane.showMessageDialog(null, "Final datasets have been updated with the configuration of prediction horizon.", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                    
                    /* Enables button 'Create final datasets'. */
                    getView().setEnablebtnCreateFinalDatasets(true);
                    getView().setBtnCreateFinalDatasetsToolTipText("Create final datasets");
                    
                    /* Shows available final datasets to visualise and table header. */
                    /* By default the content of the selected final dataset is showed. */
                    showFinalDatasetsSelection(getView().getSelectedIndexFinalDatasetToVisualise());
                    
                    /* Visualises / Hides DATE attribute depending on user selection. */
                    doVisualiseDateAttribute();
                    
                    break;
            
            }
                        
        }
        
        return result;
                        
    }
    
    
    /**
     * Updates final dataset with the configuration of prediction horizon.
     */
    private void updateFinalDatasetWithPredictionHorizon(){
        
        /* 
            Updates final dataset with the configuration of prediction horizon:
            
                - Moves backward the output attribute.
                - Moves backward the reanalysis data (is selected by the user).
        */
        
        /* Gets matched databases created. */
        ArrayList<MatchingFinalDataset> matchedFinalDatasetsCreated = getBackupOfMatchedFinalDatasets();

        /* Matched databases obtained after time horizon applied. */
        ArrayList<MatchingFinalDataset> newMatchedFinalDatasetsCreated = new ArrayList<>();
        
        /* Gets index of the last attribute in the final dataset. */
        int indexLastAttribute=matchedFinalDatasetsCreated.get(0).getInstancesWEKA().getAttributes().size()-1;
        
        /* Gets prediction horizon. */
        int predictionHorizon = getCurrentMatchingConfiguration().getPredictionHorizon() / getPredictionHorizonInterval();
               
        /* Gets number of the instances in the final dataset depending on prediction horizon. */
        int numInstances=matchedFinalDatasetsCreated.get(0).getInstancesWEKA().getInstances().size() - predictionHorizon;
        
        /* New value for each instance depending on prediction horizon. */
        double valueOfAttributeInPredictionHorizon;

        /* Checks if user selected: 'Synchronise the reanalysis data' */
        if(getView().getSynchroniseReanalysisData()==true){
            
            /* The user selected 'Synchronise the reanalysis data'. */
            
                /*
                    - Moves backward the output attribute.
                    - Moves backward the reanalysis data.
                */
                
            /* Gets the number of the reanalysis variables used in matching process. */
            int numberOfReanalysisVariables = getNumberOfReanalysisVariablesUsed();
            
            /* Process each final dataset created in matching process. */
            for(int i=0;i<matchedFinalDatasetsCreated.size();i++){

                /* Gets a clone of the final dataset to process. */
                MatchingFinalDataset finalDataset = new MatchingFinalDataset(matchedFinalDatasetsCreated.get(i));

                /* Applies time horizon in each instance. */
                for(int j=0;j<numInstances;j++){

                    /* 
                        Moves backward the output attribute.
                    */
                    
                        /* Gets the value of the last attribute (output attribute) depending on time horizon. */
                        valueOfAttributeInPredictionHorizon=finalDataset.getInstancesWEKA().getInstances().get(j+predictionHorizon).value(indexLastAttribute);

                        /* Sets the new value. */
                        finalDataset.getInstancesWEKA().getInstances().get(j).setValue(indexLastAttribute, valueOfAttributeInPredictionHorizon);
                    
                    /* 
                        Moves backward the reanalysis data.
                    */
                    
                        /*
                            Processes each reanalysis variable.
                            The reanalysis variables are placed from index 1.
                        */
                        for(int indexVarOfReanalysisis=1;indexVarOfReanalysisis<=numberOfReanalysisVariables;indexVarOfReanalysisis++){
                            
                            /* Gets the value of the reanalysis variable depending on time horizon. */
                            valueOfAttributeInPredictionHorizon=finalDataset.getInstancesWEKA().getInstances().get(j+predictionHorizon).value(indexVarOfReanalysisis);

                            /* Sets the new value. */
                            finalDataset.getInstancesWEKA().getInstances().get(j).setValue(indexVarOfReanalysisis, valueOfAttributeInPredictionHorizon);

                        }

                }

                /* Deletes instances. */
                for(int j=0;j<predictionHorizon;j++){

                    /* Deletes always the last instance. */
                    finalDataset.getInstancesWEKA().getInstances().delete(numInstances);

                }

                /* Adds the final dataset processed. */
                newMatchedFinalDatasetsCreated.add(finalDataset);

            }
        
        }else{
        
            /* The user did not select 'Move backward the reanalysis data. */
            
                /*
                    - Moves backward the output attribute.
                */

            /* Process each final dataset created in matching process. */
            for(int i=0;i<matchedFinalDatasetsCreated.size();i++){

                /* Gets a clone of the final dataset to process. */
                MatchingFinalDataset finalDataset = new MatchingFinalDataset(matchedFinalDatasetsCreated.get(i));

                /* Applies time horizon in each instance. */
                for(int j=0;j<numInstances;j++){

                    /* Gets the value of the last attribute (output attribute) depending of time horizon. */
                    valueOfAttributeInPredictionHorizon=finalDataset.getInstancesWEKA().getInstances().get(j+predictionHorizon).value(indexLastAttribute);

                    /* Sets the new value. */
                    finalDataset.getInstancesWEKA().getInstances().get(j).setValue(indexLastAttribute, valueOfAttributeInPredictionHorizon);

                }

                /* Deletes instances. */
                for(int j=0;j<predictionHorizon;j++){

                    /* Deletes always the last instance. */
                    finalDataset.getInstancesWEKA().getInstances().delete(numInstances);

                }

                /* Adds the final dataset processed. */
                newMatchedFinalDatasetsCreated.add(finalDataset);

            }
        
        }
        
        /* Sets new matched final datasets obtained. */
        setMatchedFinalDatasets(newMatchedFinalDatasetsCreated);
                       
    }

     
    
    /**
     * Updates final dataset with the configuration of final datasets.
     */
    private void updateFinalDatasetWithClassification(){
        
        /* Updates final dataset with the configuration of classification/regression. */
        
        /* Gets the name of the classes typed by the user. */
        ArrayList<String> classNames = getCurrentMatchingConfiguration().getThresholdsClass();
        
        /* Gets the number of the classes typed by the user.  */
        int numClasses=classNames.size();
        
        /* To store the number of instances in each class. */
        ArrayList<Integer> numberOfInstancesInEachClass = new ArrayList<>(Collections.nCopies(numClasses, 0));
        
        /* Gets the values of the thresholds typed by the user. */
        ArrayList<Double> thresholdsValues = getCurrentMatchingConfiguration().getThresholdsValues();
        
        /* Gets the final datasets obtained in matching process. */
        ArrayList<MatchingFinalDataset> currentFinalDatasets = getMatchedFinalDatasets();
        
        /* Creates the attribute of the class with the classes typed by the user. */
        Attribute classAttribute = new Attribute("Class_"+getCurrentMatchingConfiguration().getAttributeNameToPredict(), classNames);
        
        /* Gets the index of the last attribute, which will be use to discretize each final dataset. */
        int indexLastAttribute=currentFinalDatasets.get(0).getInstancesWEKA().getAttributes().size()-1;

        /* The index of the attribute of the class is the last one. */
        int indexOfAttributeOfTheClass=indexLastAttribute + 1;

        /* Gets the number of the instances in the final dataset depending on prediction horizon. */
        int numInstances=currentFinalDatasets.get(0).getInstancesWEKA().getInstances().size();
        
        /* To store the missing instances. */
        int missingInstances = 0;
                
        /* To check if an instance have been classified. */
        boolean classFound;

        /* Value of each instance. */
        double value;

        /* Processes each final dataset obtained after prediction horizon. */
        for(int i=0;i<currentFinalDatasets.size();i++){
            
            /* Gets the final dataset to process. */
            MatchingFinalDataset finalDataset = currentFinalDatasets.get(i);
            
            /* Adds the attribute of the class. */
            finalDataset.getInstancesWEKA().getAttributes().add(classAttribute);
            finalDataset.getInstancesWEKA().getInstances().insertAttributeAt(classAttribute, indexOfAttributeOfTheClass);
            
            /* Sets the attribute as class in the final dataset. */            
            finalDataset.getInstancesWEKA().getInstances().setClassIndex(indexOfAttributeOfTheClass);
           
            /* Sets the class of each instance. */
            for(int j=0;j<numInstances;j++){
                
                /* The instance if not classified. */
                classFound=false;                                
                
                /* Gets the instance. */
                Instance instance = finalDataset.getInstancesWEKA().getInstances().get(j);
            
                /* Checks if the attribute has a missing value. */
                if(instance.isMissing(indexLastAttribute) == true){
                    
                    /* The attribute has a missing value. */
                    instance.setClassMissing();
                    
                    /* Adds one missing instance. */
                    missingInstances = missingInstances + 1;
                
                }else{
                    
                    /* Gets the value of the instance. */
                    value = instance.value(indexLastAttribute);

                    /* Checks the classes typed by user. */
                    for(int k=0;k<numClasses && classFound ==false;k++){

                        /* Checks the value of the attribute with each threshold typed by the user. */
                        if(value>=thresholdsValues.get(k) && value<thresholdsValues.get(k+1) ){

                            /* The class has been found. */
                            classFound=true;

                            /* Sets the class in the instance. */
                            instance.setClassValue(classNames.get(k));
                            
                            /* Adds one instance matched in the class. */
                            numberOfInstancesInEachClass.set(k, numberOfInstancesInEachClass.get(k) + 1);

                        }else if(value==thresholdsValues.get(numClasses)){
                            
                            /* The value is the max. */
                            
                            /* The class has been found. */
                            classFound=true;
                           
                            /* Sets the class in the instance. */
                            instance.setClassValue(classNames.get(numClasses-1));
                            
                            /* Adds one instance matched in the class. */
                            numberOfInstancesInEachClass.set(numClasses-1, numberOfInstancesInEachClass.get(numClasses-1) + 1);
                            
                        }                        

                    }

                    /* Checks if the instance was classified or not. */
                    if(classFound==false){

                        /* Sets the class in the instance. */
                        instance.setClassMissing();
                        
                        /* Adds one missing instance. */
                        missingInstances = missingInstances + 1;

                    }
                
                }
                                                
            }
        }    
        
        /* Divides by the number of the final datasets created. */
        for(int i=0;i<numClasses;i++){
            numberOfInstancesInEachClass.set(i, numberOfInstancesInEachClass.get(i) / currentFinalDatasets.size());
        }
        
        /* Updates #Instances in thresholds. */
        showNumberOfInstancesInThresholds(numberOfInstancesInEachClass);        
        
        /* Checks if missing instances were found. */
        if(missingInstances!=0){
            
            /* Gets the number of missing instances. */
            missingInstances = missingInstances / currentFinalDatasets.size();
            
            JOptionPane.showMessageDialog(null,"The final dataset has been checked and "+missingInstances+" missing instances were found.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        }                

    }
    
    
    /**
     * Checks a valid output folder to save the final datasets.
     * @param outputFolder Output folder selected by user.
     * @return True/False if the output folder is valid or not.
     */
    private boolean isValidOutputFolder(String outputFolder){

        /* Checks valid output folder. */
        
        boolean result=true;
        
        /* Utilities. */
        Utils util = new Utils();
                
        /* Checks valid output folder. */
        if(outputFolder.isEmpty()==true || util.existsDirectory(new File(outputFolder).getParent())==false){
        
            /* Output folder selected is not valid. */
            result = false;
    
        }
                                                
        return result;
        
    }    


    
    /**
     * Checks output file's format selected by user.
     */
    private void doCheckOutputFileFormatSelected(){
                            
        /* Checks output file format selected by user. */
        
        /* Only ARFF files can be opened with WEKA*/
        
        if(getView().getOutputFilesFormat().equals("ARFF")){
            
            /* ARFF output file format selected by user. */
            
            /* Enables selection: open files with Weka. */
            
            getView().setEnabledOpenDatabasesWithWeka(true);
            
        }else{
            
            /* CSV output file format selected by user. */
            
            /* Disables selection: open files with Weka. */
            
            getView().setEnabledOpenDatabasesWithWeka(false);
            
            /* Set false: Open datatases with Weka. */
            getView().setOpenDatabasesWithWeka(false);
                            
        }
    
    }

    
    /**
     * Shows final datasets selection and table header.
     * 
     * @param index Index of the final dataset to visualizae.
     */
    private void showFinalDatasetsSelection(int index){

        /* Shows final datasets selection and table header. */


        /* Defines the tablemodel for showing the information of the final datasets created. */
        
            /* Attributes. */
            ArrayList<weka.core.Attribute> attributes = getMatchedFinalDatasets().get(0).getInstancesWEKA().getAttributes();                   

            /* Table model. */       
            DefaultTableModel datamodel;
            datamodel = new DefaultTableModelImpl();
        
        /* Clear older final dataset available to visualise. */
        getView().deleteAllFinalDatasetToVisualise();

        /* To indicate that user must select a final dataset to visualise. */
        getView().addFinalDatasetToVisualise("-- Select --");
        
        /* Sets final dataset available to visualise. */
        for(int i=0;i<getMatchedFinalDatasets().size();i++){

            /* Adds a new final dataset.  */
            getView().addFinalDatasetToVisualise("Final dataset: " + (i+1));

        }
        
        
        /* Sets table header. */
        
        /* 
            The first column indicates if the date is 'Missing' it is only used
            for filtering the 'Missing' dates and for using a different colour for each case.
        */
        datamodel.addColumn("TypeOfDate");
       
        
        /* Adds columns to table model, one per attribute. */
        for (weka.core.Attribute attribute : attributes) {

                datamodel.addColumn(attribute.name());

        }                   
        
        /* Shows table header. */
        getView().setModelFinalDataset(datamodel);
        
        /* Visualises the content of the received index final dataset. */
        getView().setSelectedIndexFinalDatasetToVisualise(index);
    
    }

    
    /**
     * Visualises the data of the final dataset selected by user.
     */
    private void doVisualiseSelectedFinalDataset(){

        /* Visualises the data of the final dataset selected by user. */
        
        /* Utilities. */
        Utils util = new Utils();
        
        
        /* 
            Makes a copy of the missing dates found in matching process.
            This copy is used for identifying the missing dates.
        */
        
        getCopyOfMissingDates().clear();
        
        for(String missingDate: getMissingDates()){

            /* Remove useless characters. */
            missingDate = missingDate.replace('-', ' ');
            missingDate = missingDate.replace(':', ' ');
            missingDate = missingDate.substring(0, 16);
            
            /* Adds the missing date. */
            getCopyOfMissingDates().add(missingDate);
        
        }                
        
        /* Number of missing dates. */
        int numMissingDates=getCopyOfMissingDates().size();
        
        /* Index of the final dataset selected. */
        int index = getView().getSelectedIndexFinalDatasetToVisualise();
        
        if(index>=0){

            /* Gets the instances to show in the table. */
                        
            /* Table model. */       
            DefaultTableModel datamodel = getView().getModelFinalDataset();

            /* Number of columns (-1 because the first column is 'TypeOfDate'). */
            int numColumns = datamodel.getColumnCount()-1;
            
            /* Deletes the data of the previously selected final dataset. */
            datamodel.setRowCount(0);
                        
            if(index>0){
                
                /*

                    index: 0  ->  -- Select --
                    index: 1  ->  Final dataset: 1

                    (index-1) ->  Correct index of final dataset to visualise.

                */
                
                /* Enables 'Previous date' and 'Next date' buttons. */
                getView().setEnablePreviousDateFinalDataset(true);
                getView().setEnableNextDateFinalDataset(true);
                
                /* Gets the instances to show in the table. */
                weka.core.Instances instances = getMatchedFinalDatasets().get(index-1).getInstancesWEKA().getInstances();
                
                /* Format of the date. */
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm");

                /* NDBC dates are in UTC time zone. */
                TimeZone timeZone = TimeZone.getTimeZone("UTC");

                /* For getting correct time according to UTC time zone. */
                dateFormat.setTimeZone(timeZone);
                
                /* Date of each instance. */
                String dateOfInstanceInStringFormat;
                Calendar dateOfInstanceInDateFormat;

                /* Date of each missing date. */
                String dateOfMissingDateInStringFormat;
                Calendar dateOfMissingDateInDateFormat;
                                                
                /* Adds each record to the datamodel taking into account the missing dates. */
                for(weka.core.Instance instance : instances){

                    /* To store the value of each attribute of one record. */
                    ArrayList<Object> row = new ArrayList<>();
                    
                    /* Checks if there is any missing date. */
                    if(numMissingDates>0){
                        
                        /* Gets the date of the instance. */
                        dateOfInstanceInStringFormat = util.unixSecondsToString( (long) (instance.value(0)));
                        dateOfInstanceInDateFormat = util.convertDateFromStringToCalendar(dateOfInstanceInStringFormat);
                        
                        /* Checks both dates. */
                        
                        do{
                            
                            /* Gets the date of the missing date (always is the first one). */
                            dateOfMissingDateInStringFormat = getCopyOfMissingDates().get(0);
                            dateOfMissingDateInDateFormat = util.convertDateFromStringToCalendar(dateOfMissingDateInStringFormat);
                        
                            /* Checks if both dates are equals. */
                            if(dateFormat.format(dateOfInstanceInDateFormat.getTime()).equals(dateFormat.format(dateOfMissingDateInDateFormat.getTime()))==true){

                                /* 
                                    Sets the attribute 'TypeOfDate' as missing date.
                                    This attribute will be used by the renderer to show the instances in a different colour when they are
                                    missing dates.
                                */
                                row.add("MissingIncludedDate");

                                /* Deletes the missing date. */
                                getCopyOfMissingDates().remove(0);

                                /* Updates the number of missing dates. */
                                numMissingDates=numMissingDates-1;

                            }else if(dateOfInstanceInDateFormat.getTime().after(dateOfMissingDateInDateFormat.getTime())==true){

                                /* 
                                    The date of the instance is after that the date of the missing date, so
                                    the missing date was not included.
                                */

                                /* Adds the missing date. */
                                row.add("MissingNotIncludedDate");
                                row.add(dateOfMissingDateInStringFormat);

                                /* Deletes the missing date. */
                                getCopyOfMissingDates().remove(0);

                                /* Updates the number of missing dates. */
                                numMissingDates=numMissingDates-1;

                                /* Stores the values of the attributes. */
                                for(int i=1;i<numColumns;i++){

                                    /* As it is a missing date stores the value of each attribute with a ' ' . */
                                    row.add(" ");

                                }        

                                /* Adds a row with the values of the instance. */
                                datamodel.addRow(row.toArray());

                                /* Cleans the data for the next instance. */
                                row = new ArrayList<>();

                            }else{

                                /* The instance is a matched date. */
                                row.add("MatchedDate");

                            }
                            
                        }while(numMissingDates > 0 && dateOfInstanceInDateFormat.getTime().after(dateOfMissingDateInDateFormat.getTime())==true);
                        
                        /* Special case. */
                        if(numMissingDates == 0 && dateOfInstanceInDateFormat.getTime().after(dateOfMissingDateInDateFormat.getTime())==true){
                            
                            /* The instance is a matched date. */
                            row.add("MatchedDate");
                        }
                                                
                    }else{
                        
                        /* The instance is a matched date. */
                        row.add("MatchedDate");                        
                    
                    }

                    /* Checks if the last one attribute is the class. */
                    if(instance.classIndex()!=-1){

                        /* The last one attribute is the class. */

                        /* 
                            The first attribute is the time used in matching process, which was changed from 1800-01-01 to Unix Seconds.
                            Now it is changed from Unix Seconds to String.
                        */
                        row.add(util.unixSecondsToString((long)instance.value(0)));

                        /* Gets attributes values. */
                        for(int i=1;i<numColumns-1;i++){

                            /* Stores each value. */
                            row.add(instance.value(i));

                        }

                        /* Checks if class is missing. */
                        if(instance.classIsMissing()){

                            /* */
                            row.add("?");

                        }else{

                            /* Add the value of the class. */
                            row.add(instance.classAttribute().value((int)instance.classValue()));

                        }

                    }else{

                        /* The first attribute is the time (used in matching process), changes it from Unix Seconds to String. */
                        row.add(util.unixSecondsToString((long)instance.value(0)));

                        /* Gets attributes values. */
                        for(int i=1;i<numColumns;i++){

                            /* Stores each value. */
                            row.add(instance.value(i));

                        }        

                    }
                    
                    /* Adds a row with the data of each attribute. */
                    datamodel.addRow(row.toArray());
                    
                }      
                
                /* Adds the remaining missing dates. */
                if(numMissingDates>0){

                    /* Number of the remaining dates to include. */
                    int remainingDatesToInclude;

                    /* Checks type of matching. */
                    if(getCurrentMatchingConfiguration().getTypeOfMatching().equals("Direct matching")){

                        /* All the remaining missing dates. */
                        remainingDatesToInclude=numMissingDates;

                    }else{

                        /* 
                            Classification and Regression.
                            Adds all the remaining missing dates except the removed ones due to the prediction horizon.
                        */

                        remainingDatesToInclude=numMissingDates - getCurrentMatchingConfiguration().getPredictionHorizon()/getPredictionHorizonInterval();

                    }

                    /* Adds all the remaining missing dates. */

                    /* To store the value of each attribute of one record. */
                    ArrayList<Object> row = new ArrayList<>();

                    /* Adds all the remaining missing dates. */
                    for(int i=0;i<remainingDatesToInclude;i++){

                        /* To store the value of each attribute of one record. */

                        /* Adds the missing date. */
                        row.add("MissingNotIncludedDate");
                        row.add(getCopyOfMissingDates().get(i));

                        /* Stores the values of the attributes. */
                        for(int k=1;k<numColumns;k++){

                            /* As it is a missing date stores the value of each attribute with a ' ' . */
                            row.add(" ");

                        }

                        /* Adds a row with the data of each attribute. */
                        datamodel.addRow(row.toArray());          

                        /* Clear previous data. */
                        row = new ArrayList<>();
                    }
                }
                
                /* Hides the attribute 'TypeOfDate' (column: 0) in the final dataset. */
                getView().hideColumnOfFinalDataset(0);

            }else{
                
                /* Disables 'Previous date' and 'Next date' buttons. */
                getView().setEnablePreviousDateFinalDataset(false);
                getView().setEnableNextDateFinalDataset(false);
                            
            }
                                    
        }
                                
    }    
    

    
    /**
     * Visualises the date attribute in final dataset depending on user selection.
     */
    private void doVisualiseDateAttribute(){
        
        /* Visualises the date attribute in final dataset depending on user selection. */
        
        /* Checks if visualise 'Date attribute' is selected or not. */
        
        if(getView().getVisualiseDateAttribute()==true){
        
            /* Visualises the date attribute (column: 0) in final dataset. */
            getView().showColumnOfFinalDatasetToVisualise(1);
        
        }else{
            
            /* Hides the date attribute (column: 1) in final dataset. */
            getView().hideColumnOfFinalDataset(1);
            
        }
        
    }
    
    
    /**
     * Moves to the previous 'MissingIncludedDate' or 'MissingNotIncludedDate' in the selected final dataset to visualise.
     */
    private void doPreviousDateFinalDataset(){
    
        /* Moves to the previous 'MissingIncludedDate' or 'MissingNotIncludedDate'. */
        
        /* The row of the new 'MissingIncludedDate' or 'MissingNotIncludedDate'. */
        int newRow=-1;
        
        /* Gets the selected row. */
        int startingRow=getView().getSelectedRowFinalDataset();
        
        /* Gets the information of the final dataset. */
        DefaultTableModel datamodel = getView().getModelFinalDataset();
        
        /* Checks if none row is selected. */
        if(startingRow==-1){
            
            /* Gets the last row. */
            startingRow=getView().getModelFinalDataset().getRowCount();
        
        }
        
        /* Searchs for the previous date. */
        for(int i=startingRow-1;i>=0 && newRow == -1;i--){
            
            /* Gets the type of the date. */
            String typeOfDate=datamodel.getValueAt(i, 0).toString();
                       
            /* Checks if the current date is a 'Missing', 'Unexpected' or 'Duplicated' one. */
            if(typeOfDate.equals("MissingIncludedDate") || typeOfDate.equals("MissingNotIncludedDate") ){
                
                /* A new 'MissingIncludedDate' or 'MissingNotIncludedDate' was found. */
                newRow=i;
            
            }
            
        }
        
        /* Checks if a new date was found. */
        if(newRow==-1){
            
            JOptionPane.showMessageDialog(null, "None previous 'Missing date' was found.", "Message", JOptionPane.INFORMATION_MESSAGE);
                   
        }else{
            
            /* Sets the row as selected. */
            getView().setSelectedRowFinalDataset(newRow);
                
        }                             
                        
    }
    
    
    /**
     * Moves to the next 'MissingIncludedDate' or 'MissingNotIncludedDate' in the selected final dataset to visualise.
     */
    private void doNextDateFinalDataset(){
    
        /* Moves to the previous 'MissingIncludedDate' or 'MissingNotIncludedDate'. */
        
        /* The row of the new 'MissingIncludedDate' or 'MissingNotIncludedDate'. */
        int newRow=-1;
        
        /* Gets the last row. */
        int lastRow=getView().getModelFinalDataset().getRowCount();        
        
        /* Gets the selected row. */
        int startingRow=getView().getSelectedRowFinalDataset();
        
        /* Gets the information of the final dataset. */
        DefaultTableModel datamodel = getView().getModelFinalDataset();
        
        /* Checks if none row is selected. */
        if(startingRow==-1){
            
            /* Sets the first row. */
            startingRow=0;
        
        }
        
        /* Searchs for the previous date. */
        for(int i=startingRow+1;i<lastRow && newRow == -1;i++){
            
            /* Gets the type of the date. */
            String typeOfDate=datamodel.getValueAt(i, 0).toString();
            
            /* Checks if the current date is a 'Missing', 'Unexpected' or 'Duplicated' one. */
            if(typeOfDate.equals("MissingIncludedDate") || typeOfDate.equals("MissingNotIncludedDate") ){
                
                /* A new 'MissingIncludedDate' or 'MissingNotIncludedDate' was found. */
                newRow=i;
            
            }
            
        }
        
        /* Checks if a new date was found. */
        if(newRow==-1){
            
            JOptionPane.showMessageDialog(null, "None next 'Missing date' was found.", "Message", JOptionPane.INFORMATION_MESSAGE);
                   
        }else{
            
            /* Sets the row as selected. */
            getView().setSelectedRowFinalDataset(newRow);
                
        }                             
                        
    }
    
    

    /**
     * Hides the output attribute in final dataset.
     */
    private void doHideOutputAttribute(){
        
        /* Hides the output ttribute in final dataset. */
        
        /* Gets the name of the attribute to predict. */
        String attributeToPredict = getCurrentMatchingConfiguration().getAttributeNameToPredict();
        
        /* Gets the index of the attribute to predict. */
        int indexAttributeToPredict = getView().getModelFinalDataset().findColumn(attributeToPredict);
                    
        /* Hides the output attribute in final dataset. */
        getView().hideColumnOfFinalDataset(indexAttributeToPredict );            
        
    }
    
    
    /**
     * Shows a dialog for selecting the file name and folder to save the final datasets.
     */
    private void doSelectOutputFile(){
        
        /* Selects folfer to save the final datasets. */
        
        /* Shows a dialog for choosing the file name and folder. */
        
        /* Utilities. */
        Utils util = new Utils();
        
        /* Default file name. */
        String defaultFileName;

        /* Default path. */
        String path;
        
        /* Dialog. */
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();

        /* Only files. */
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        /* Disables All files option. */    
        fileChooser.setAcceptAllFileFilterUsed(false);                
        
        /* Checks is already exists a file name. */
        if(getView().getOutputFolder().isEmpty()){
            
            /* Sets the default file name and path. */
            
            /* To convert from int to String with two digits. */
            DecimalFormat intFormatter = new DecimalFormat("00");

            /* Gets current date. */
            Calendar date=GregorianCalendar.getInstance();


            /* String: yyyy-MM dd HH mm with date format. */

                                /* yyyy */
            String timeStamp = date.get(Calendar.YEAR) + "_" +

                                /* Month is base0. */
                                /* Month: + 1 because 0 -> January ... 11 -> December. */

                                intFormatter.format(date.get(Calendar.MONTH) + 1) + "_" +

                                /* dd */
                                intFormatter.format(date.get(Calendar.DAY_OF_MONTH)) + "_" +

                                /* HH */
                                intFormatter.format(date.get(Calendar.HOUR_OF_DAY)) + "_" +

                                /* mm:ss */
                                intFormatter.format(date.get(Calendar.MINUTE));
            
            /* Sets the default file name as StationID + current date. */
            defaultFileName = getCurrentMatchingConfiguration().getStationID() + "_" + timeStamp;
            defaultFileName=defaultFileName.replace(' ', '_');
            
            /* Sets the default path. */
            path = System.getProperty("user.dir", ".")+File.separator+"DB"
                   +File.separator+"finalDatasets"+File.separator;
        
        }else{
                        
            /* Sets the default file name. */
            defaultFileName=getView().getOutputFolder();
            
            /* Sets the default path. */
            path = getView().getOutputFolder();
        
        }
        
        /* Sets the default file name.*/
        fileChooser.setSelectedFile(new File(defaultFileName));
        
        /* Sets the default path. */
        fileChooser.setCurrentDirectory(new File(path));
                
        /* Shows dialog and waits for user selection. */
        int returnVal = fileChooser.showOpenDialog(null);

        /* Checks user selection. */
        if (returnVal == JFileChooser.APPROVE_OPTION) {
                        
            /* Checks if file name is correct. */
            if(util.isValidFilename(fileChooser.getSelectedFile().getName())==true){
                
                /* Sets user selection. */
                getView().setOutputFolder(fileChooser.getSelectedFile().getPath());
                           
            }else{
                
                /* Error. File name is not valid. */
                JOptionPane.showMessageDialog(null, "The file name is not valid. Please, checks it has valid characters and no extension.", "Message", JOptionPane.INFORMATION_MESSAGE);
            
            }

        }        
        
    }

    
    
    /**
     * Opens NewMatchingFile view to save reanalysis matching configuration.
     */
    private void doSaveMatchingFile(){    
        
        /* Saves on a XML file the matching configuration typed by user. */
               
        /*
            Only checks tab: 'Final datasets' because tab: 'Matching configuration' 
            was checked when user clicked on 'Next' button.
        */
        
        /* Checks if output folder is ok. */
        if(isValidOutputFolder(getView().getOutputFolder())==false){

            JOptionPane.showMessageDialog(null, "The output folder does not exists.", "Message", JOptionPane.INFORMATION_MESSAGE);

        }else if(checkMatchingConfigurationOfTabFinalDatasets()==true){
            
            /* User typed all input data. */

            /* Sets current matching configuration of tab: 'Final datasets' typed by user. */
            setCurrentMatchingConfigurationOfTabFinalDatasets();

            
            /* Opens NewMatchingFile view to create a new matching file. */

            
            /* Creates the view and controller. */
            InterfaceViewNewMatchingFile newMatchingFile = new NewMatchingFile(getView().getParent(), false);
            ControllerViewNewMatchingFile controller = new ControllerViewNewMatchingFile(newMatchingFile, getCurrentMatchingConfiguration());
            
            /* Sets the controller that will manage all events and shows the view. */
            newMatchingFile.setController(controller);

            /* Shows the view. */
            newMatchingFile.showView();
                            
        }
                        
    }
    
        
    /**
     * Creates final datasets with the configuration typed by the user.
     */
    private void doCreateFinalDatasets(){

        /* Creates final datasets with the configuration typed by the user. */
        
        /* Name of each final dataset saved on disk. */
        String finalDatasetsNameSaved="\n";

        /*
            Matching configuration of tab 'Final datasets' was checked when user pressed 'Update' button.
        */
        
        /* Checks if output folder is ok. */
        if(isValidOutputFolder(getView().getOutputFolder())==false){

            JOptionPane.showMessageDialog(null, "The output folder does not exist.", "Message", JOptionPane.INFORMATION_MESSAGE);

        }else{

            /* Sets current matching configuration of tab: 'Final datasets' typed by user. */
            setCurrentMatchingConfigurationOfTabFinalDatasets();
            
            /* Saves final datasets on disk. */
            if(saveFinalDatasetsDisk()==true){

                /* Final datasets saved properly on disk. */

                /* Saves matching summary info on disk. */
                saveMatchingSummaryInfo();
                
                /* Gets each final dataset name saved on disk. */
                for(String finalDatasetName : getFinalDatasetsName()){

                    /* Adds final dataset name.*/
                    finalDatasetsNameSaved = finalDatasetsNameSaved + "     " + finalDatasetName + "\n";
                
                }

                JOptionPane.showMessageDialog(null, "The following final datasets were saved on disk: \n" + finalDatasetsNameSaved, "Message", JOptionPane.INFORMATION_MESSAGE);
                
                if(getView().getOpenDatabasesWithWeka()==true){

                    /* Opens final datasets with WEKA. */
                    openFinalDatasetsWithWeka();
                            
                }

            }
        }
                
    }    

    
    /**
     * Saves each final dataset created on disk.
     * @return True if final datasets were properly saved or False if the process failed.
     */
    private boolean saveFinalDatasetsDisk(){
                   
        boolean result=true;
        
        /* Gets file name selected by the user. */
        File file = new File(getCurrentMatchingConfiguration().getOutputFolder());
        
        /* Path to save the final datasets. */
        String path=file.getParent()+File.separator;
                        
        /* Database name prefix. */
        String fileName = file.getName();
        
        /* Final dataset name. */
        String finalDatasetName="";
                
        /* Gets matched databases created. */
        ArrayList<MatchingFinalDataset> matchedFinalDatasetsCreated = getMatchedFinalDatasets();
        
        /* Clear final datasets name. */
        getFinalDatasetsName().clear();

        /* Saves each matched database on disk. */
        for(int i=0;i<matchedFinalDatasetsCreated.size() && result==true;i++){

            /* Gets a clone of the final dataset to save. */
            MatchingFinalDataset finalDataset = new MatchingFinalDataset(matchedFinalDatasetsCreated.get(i));

            /* Deletes attribute DATE because it is useless. */
            finalDataset.getInstancesWEKA().deleteAttribute("DATE");
            
            /* 
                Deletes attribute to predict if user selected 'Prediction' because the attribute for prediction
                task will be the class.
            */
            if(getCurrentMatchingConfiguration().getTypeOfMatching().equals("Classification")){
                
                /* Deletes the attribute to predict. */
                finalDataset.getInstancesWEKA().deleteAttribute(getCurrentMatchingConfiguration().getAttributeNameToPredict());
            
            }
                        
            /* Write database in ARFF / CSV format. */
            switch(getCurrentMatchingConfiguration().getOutputFilesFormat()){

                case "ARFF":
                                      
                    /* Sets the name of matched database to save. */
                    finalDatasetName=fileName + "_" + Integer.toString(i+1)+".arff";

                    /* Sets relation name. */
                    finalDataset.getInstancesWEKA().setRelationName(finalDatasetName);

                    /* Saves database on disk. */
                    result=finalDataset.getInstancesWEKA().convertInstancesToARFFFile(new File(path + finalDatasetName));
                   
                    break;

                case "CSV":
                    
                    /* Sets the name of matched database to save. */
                    finalDatasetName=fileName + "_" + Integer.toString(i+1)+".csv";

                    /* Saves database on disk. */
                    result=finalDataset.getInstancesWEKA().convertInstancesToCSVFile(new File(path + finalDatasetName));

                    break;
            }
            
            /* Checks if final dataset was saved properly on disk. */
            if(result==true){

                /* Final dataset saved on disk. */
                getFinalDatasetsName().add(finalDatasetName);

            }else{

                /* Error when saving final dataset on disk. */
                JOptionPane.showMessageDialog(null, "Final dataset: "+finalDatasetName+" could not be saved.", "Message", JOptionPane.WARNING_MESSAGE);
                
            }

        }
        
        return result;
    
    }
    
    
    
    /**
     * Saves matching's summary info on disk.
     * @return True if summary info was properly saved or False if the process failed.
     */
    private boolean saveMatchingSummaryInfo(){
        
        /* Saves matching summary info on disk. */
        
        boolean result=false;
        
        /* Matching configuration (of tab: 'Matching configuration' and 'Final datasets') used in matching process. */
        MatchingInformation matchingConfiguration = getCurrentMatchingConfiguration();
                
        /* Path of the file to save the summary info. */
        String fileName = matchingConfiguration.getOutputFolder()+"_Matching.info";
        
        /* Gets the file. */
        File summaryMatching = new File(fileName);

        /* File writer. */
        FileWriter fwSummaryMatching;
        
        try {

            /* Sets file writer. */

            fwSummaryMatching = new FileWriter(summaryMatching, false);            

            /* Saves summary info. */
            try (BufferedWriter bwSummaryMatching = new BufferedWriter(fwSummaryMatching)) {
                                                
                /* Writes header. */
                bwSummaryMatching.write("   ---- Begin of the matching process summary ----");
                bwSummaryMatching.newLine();
                bwSummaryMatching.newLine();
                
                /* Writes date of creation. */
                bwSummaryMatching.write("Date of creation ................................: "+ matchingConfiguration.getDateCreation().getTime());
                bwSummaryMatching.newLine();
                
                
                /* Writes short description typed by user. */
                bwSummaryMatching.write("Short description ...............................:");
                bwSummaryMatching.newLine();
                bwSummaryMatching.write(matchingConfiguration.getShortDescription());
                bwSummaryMatching.newLine();
                bwSummaryMatching.newLine();
                
                bwSummaryMatching.write("Type of matching ................................: "+ matchingConfiguration.getTypeOfMatching());
                bwSummaryMatching.newLine();
                
                    if(matchingConfiguration.getTypeOfMatching().equals("Classification")==true){
                        
                        /* Writes the configuration of Prediction. */
                        bwSummaryMatching.newLine();
                        bwSummaryMatching.write("     Class  , Description  , #Instances , Inferior[ , Superior)");
                        bwSummaryMatching.newLine();
                        bwSummaryMatching.write("     ------ , ------------ , ---------- , --------- , ---------");
                        bwSummaryMatching.newLine();
                        
                        /* Orders thresholds by the inferior value of the threshold. */
                        getView().setOrderThresholds(3);
        
                        /* Information about thresholds. */
                        DefaultTableModel datamodel = getView().getModelThresholds();
        
                        /* Row in sorted model. */
                        int row;
        
                        /* Gets the number of the thresholds. */
                        int numThresholds = datamodel.getRowCount();
                
                        /* Writes the information of each threshold. */
                        for(int i=0;i<numThresholds;i++){

                            /* Gets row in the sorted model. */
                            row=getView().getRowIndexOfSortedModelofThresholds(i);
                            
                            bwSummaryMatching.write("     " + datamodel.getValueAt(row, 0) + " , " + datamodel.getValueAt(row, 1) + " , " +
                                                    datamodel.getValueAt(row, 2) + " , " + datamodel.getValueAt(row, 3) + " , " +
                                                    datamodel.getValueAt(row, 4));
                            bwSummaryMatching.newLine();

                        }
                        
                        bwSummaryMatching.newLine();
                        bwSummaryMatching.write("     Prediction horizon: "+ getView().getTextOfPredictionHorizon());
                        bwSummaryMatching.newLine();
                        bwSummaryMatching.newLine();
                                            
                    }
                    
                    if(matchingConfiguration.getTypeOfMatching().equals("Regression")==true){
                        
                        bwSummaryMatching.write("                                                   Prediction horizon: "+ getView().getTextOfPredictionHorizon());
                        bwSummaryMatching.newLine();
                        bwSummaryMatching.newLine();
                        
                    }
                                                
                /* Writes Station ID. */
                bwSummaryMatching.write("Station ID ......................................: "+ matchingConfiguration.getStationID());
                bwSummaryMatching.newLine();
                                                                                              
                /* Writes file name dataset used. */
                bwSummaryMatching.write("File name dataset used ..........................: "+ matchingConfiguration.getDatasetFileName());
                
                if(matchingConfiguration.getTypeDatasetFile().equals("Pre-processed")){
                
                    /* Writes preprocessed dataset file. */
                    bwSummaryMatching.write(" (Pre-processed)");
                }
                
                bwSummaryMatching.newLine();
                                
                /* Writes name of the attribute predicted. */
                bwSummaryMatching.write("Attribute predicted .............................: " + matchingConfiguration.getAttributeNameToPredict());
                bwSummaryMatching.newLine();
                
                /* Writes the included attributes as inputs. */
                bwSummaryMatching.write("Included buoy attributes as inputs ..............: ");
                
                if(matchingConfiguration.getBuoyVariables().isEmpty() == true){
                    
                    /* Writes included attributes as inputs or not. */
                    bwSummaryMatching.write("None.");
                    bwSummaryMatching.newLine();
                
                }else{
                    
                    bwSummaryMatching.newLine();
                    
                    /* Writes each selected variable of the buoy. */
                    for(String variable : matchingConfiguration.getBuoyVariables()){

                        /* Writes file name.*/
                        bwSummaryMatching.write("                                                   -> " + variable);
                        bwSummaryMatching.newLine();

                    }
                
                }
                                                                                                
                /* Writes reanalysis files used. */
                bwSummaryMatching.write("Reanalysis data files used ......................:");
                bwSummaryMatching.newLine();
                
                for(int i=0;i<matchingConfiguration.getReanalysisFiles().size();i++){

                    /* Writes file name.*/
                    bwSummaryMatching.write("                                                   -> (" + matchingConfiguration.getReanalysisVars().get(i)+") " + matchingConfiguration.getReanalysisFiles().get(i));
                    bwSummaryMatching.newLine();
                
                }
                
                /* Writes included missing dates or not. */
                bwSummaryMatching.write("Included missing dates ..........................: " + matchingConfiguration.getIncludeMissingDates());
                
                if(getMissingDates().isEmpty()==true){
                    
                    /* None missing date was found. */
                    bwSummaryMatching.write(" (None missing dates were found)");
                    bwSummaryMatching.newLine();
                    
                }else{

                    /* None missing date was found. */
                    bwSummaryMatching.write(". The following missing dates were found:");
                    bwSummaryMatching.newLine();
                    
                    /* Writes each missing date found. */
                    for(String missingDate : getMissingDates()){

                        /* Writes file name.*/
                        bwSummaryMatching.write("                                                   -> " + missingDate);
                        bwSummaryMatching.newLine();

                    }
                                    
                }                                                

                                                                                
                /* Writes number of nearest geopoints to the buoy considered. */
                bwSummaryMatching.write("Nearest reanalysis nodes to the buoy considered .: " + matchingConfiguration.getNumberOfNearestGeopointsToConsider());
                bwSummaryMatching.newLine();
                
                /* Writes output folder where files were saved. */
                bwSummaryMatching.write("Output folder ...................................: " + matchingConfiguration.getOutputFolder());
                bwSummaryMatching.newLine();
                
                /* Writes format of output files. */
                bwSummaryMatching.write("Output file format ..............................: " + matchingConfiguration.getOutputFilesFormat());
                bwSummaryMatching.newLine();

                /* Writes output files created. */
                
                bwSummaryMatching.write("Output files created ............................: ");
                
                if(matchingConfiguration.getOutputFilesToCreate()==0){
                    
                    bwSummaryMatching.write("one final dataset using weighted mean of nearest reanalysis nodes");
                    bwSummaryMatching.newLine();
                
                    /* Writes database name. */
                    bwSummaryMatching.write("                                                   -> " + getFinalDatasetsName().get(0));
                    bwSummaryMatching.newLine();
                    
                }else{
                    
                    bwSummaryMatching.write("'n' final datasets (one per each reanalysis node)");
                    bwSummaryMatching.newLine();

                        /* Writes each database name. */
                        for(String database : getFinalDatasetsName()){

                            /* Writes database name.*/
                            bwSummaryMatching.write("                                                   -> " + database);
                            bwSummaryMatching.newLine();

                        }
                }
                                
                /* Writes LF. */
                bwSummaryMatching.newLine();                
                
                /* Writes footer. */
                bwSummaryMatching.write("   ----  End of the matching process summary  ----");
                bwSummaryMatching.newLine();
                bwSummaryMatching.newLine();
                                                
                /* Summary info have been saved. */
                //bwSummaryMatching.close(); /* auto-closeable. */
                //fwSummaryMatching.close(); /* auto-closeable. */
                
                /* Process properly finished . */
                result = true;

            }catch (IOException e){

                Logger.getLogger(RunMatching.class.getName()).log(Level.SEVERE, null, e);

            }

        }catch (IOException ex){

            Logger.getLogger(RunMatching.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return result;
    
    }
    
    
    /**
     * Opens with Weka each final dataset created.
     * @return True if each final dataset was properly opened with Weka or False if the process failed.
     */
    private boolean openFinalDatasetsWithWeka(){
            
        /* Opens with Weka each final dataset created. */
        
        boolean result=true;
        
        
        /* Opens with Weka each database created. */
        for(int i=0;i<getFinalDatasetsName().size();i++){
            
            /* Gets ARFF file name to open. */
            String fileName = getCurrentMatchingConfiguration().getOutputFolder() + "_" + Integer.toString(i+1)+".arff";

            /* Object that will run Weka explorer. */
            WekaExplorer wekaExplorer=new utils.WekaExplorer();

            /* Sets the file to open. */
            wekaExplorer.setFileName(fileName);


            /* Opens the ARFF file. */

            try {

                /* Start the thread. */
                wekaExplorer.start();

                /* Wait for the thread to finish. */
                wekaExplorer.join();

            } catch (InterruptedException ex) {

                result=false;
                
                JOptionPane.showMessageDialog(null, "The final dataset: "+getFinalDatasetsName().get(i)+" could not be opened with WEKA.", "Message", JOptionPane.WARNING_MESSAGE);                
                
                Logger.getLogger(RunMatching.class.getName()).log(Level.SEVERE, null, ex);
            }       

        }
        
      
        return result;
    
    }    
    
                 
    /**
     * Closes the view.
     */
    private void doMainMenu(){
        
        /* Closes the view. */       

        /* Checks if user is preprocessing a dataset/preprocessed dataset file. */
        
        Object[] options = {"Cancel", "Main menu"};
        
        if(getPreprocessingStarted()==false ||          
                
           JOptionPane.showOptionDialog(null, "Attention !!\n\nThe current pre-processed information will be lost. Please, save it before continuing."
                +"\n\nClick on Cancel to abort or Main menu to continue.\n\n","Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,options, options[0])==1
            ){

                /* Closes the view. */
                getView().closeView();            
            
        }
    
    }


    /**
     * Backs to previous tab.
     */
    private void doBack(){
        
        /* Back to previous tab. */
        
        /* Current tab. */
        int currentTab = getView().getSelectedTab();
        
        /* Select previous tab. */
        getView().setSelectedTab(currentTab - 1);
            
    }


    /**
     * Goes to specific tab.
     * @param tab Tab where going to.
     */        
    private void doGotoTab(String tab){
        
        /* Go to received tab. */
        
        switch (tab) {
            
            case "Buoys":
                
                /*  Tab: Buoys */                
                getView().setSelectedTab(0);
                break;

            case "Datasets":
                
                /*  Tab: Datasets */                
                getView().setSelectedTab(1);
                break;

            case "Pre-process":
                
                /*  Tab: Pre-process */
                getView().setSelectedTab(2);
                break;

            case "Matching":
                
                /*  Tab: Matching configuration */
                getView().setSelectedTab(3);
                break;
                
            case "Final datasets":
                
                /*  Tab: Final datasets */
                getView().setSelectedTab(4);
                break;
        }
    
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
     * This class defines a DefaultTableModel with non editable rows and columns.
     */
    private static class DefaultTableModelImpl extends DefaultTableModel {
                        
        /* Generated by NetBeans. */
        private static final long serialVersionUID = -980191972182241794L;

        public DefaultTableModelImpl() {
        }

        @Override
        public boolean isCellEditable(int row, int column){
            
            /* None row/column is editable. */
            
            return false;
            
        }
        
    }
    
}
