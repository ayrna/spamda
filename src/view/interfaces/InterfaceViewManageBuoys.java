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

package view.interfaces;

import controller.ControllerViewManageBuoys;
import java.awt.Frame;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


/**
 * This interface defines the methods that the controller needs to communicate with the view ManageBuoys.
 * 
 */
public interface InterfaceViewManageBuoys {
    
        
    /* Methods of the interface. */
    
    
    /**
     * Sets the controller that will manage each event of the view.
     * 
     * @param controller Controller that will manage each event of the view.
     */
    void setController(ControllerViewManageBuoys controller);
    
    
    /**
     * Shows the view.
     */
    void showView();

    
    /**
     * Closes the view.
     */
    void closeView();
    
    
    /**
     * Gets the parent that created the view.
     * 
     * @return Parent that created the view.
     */
    Frame getParent();
    

    /**
     * Sets selected tab.
     * 
     * @param index Index of the tab to select.
     */
    void setSelectedTab(int index);
    
    
    /**
     * Gets selected tab.
     * 
     * @return Index of the selected tab.
     */
    int getSelectedTab();
    
    
    /**
     * Sets enabled/disabled the received tab number.
     * 
     * @param index Index of the tab to enable/disable.
     * @param enabledDisabled True/False.
     */
    void setEnabledTab(int index, boolean enabledDisabled);
                           
                            
    
    /*  
        Tab: Buoys
    */
    
    
    /**
     * Returns the buoys's information of tab 'Buoys'.
     * 
     * @return Buoys's information of tab 'Buoys'.
     */
    DefaultTableModel getModelBuoys();
    
    
    /**
     * Shows the buoys's information of tab 'Buoys'.
     * 
     * @param datamodel Buoys's information.
     */
    void setModelBuoys(DefaultTableModel datamodel);


    /**
     * Gets the selected row of tab 'Buoys'.
     * 
     * @return Selected row of tab 'Buoys'.
     */
    int getSelectedRowBuoys();

    
    
    
    /*  
        Tab: Datasets
    */
    
    
    /**
     * Returns the buoys's information of tab 'Datasets'.
     * 
     * @return Buoys's information of tab 'Datasets'.
     */
    DefaultTableModel getModelBuoysDatasets();
    
    
    /**
     * Shows the buoys's information of tab 'Datasets'.
     * 
     * @param datamodel Buoys's information.
     */
    void setModelBuoysDatasets(DefaultTableModel datamodel);

    
    /**
     * Gets the selected row of tab 'Datasets'.
     * 
     * @return Selected row of tab 'Datasets'.
     */
    int getSelectedRowBuoysDatasets();
    
    
    /**
     * Sets the selected row of tab 'Datasets'.
     * 
     * @param row Row to select.
     */
    void setSelectedRowBuoysDatasets(int row);    

    
    /**
     * Shows the dataset files that belong to the selected buoy of tab 'Datasets'.
     * 
     * @param listmodel Dataset files that belong to the selected buoy.
     */
    void setModelDataset(DefaultListModel<String> listmodel);
            

    /**
     * Gets the selected dataset file of tab 'Datasets'.
     * 
     * @return Selected dataset file of tab 'Datasets'.
     */
    int getSelectedIndexDataset();
    
    
    /**
     * Sets the selected dataset file of tab 'Datasets'.
     * 
     * @param index Index of the dataset file to select.
     */
    void setSelectedIndexDataset(int index);

    
    /**
     * Shows the preprocessed dataset files that belong to the selected dataset file of tab 'Datasets'.
     * 
     * @param listmodel Preprocessed dataset files that belong to the selected dataset file.
     */
    void setModelPreprocessedDataset(DefaultListModel<String> listmodel);    
    
    
    /**
     * Returns the pre-processed dataset's information of tab 'Preprocess'.
     * 
     * @return Pre-processed dataset's information of tab 'Preprocess'.
     */
    DefaultTableModel getModelPreprocessedData();
    

    /**
     * Gets selected preprocessed dataset file of tab 'Datasets'.
     * 
     * @return Selected preprocessed dataset file of tab 'Datasets'.
     */
    int getSelectedIndexPreprocessedDataset();
    
        
    /**
     * Sets the selected preprocessed dataset file of tab 'Datasets'.
     * 
     * @param preprocessedDataset Preprocessed dataset file.
     */
    void setSelectedPreprocessedDataset(String preprocessedDataset);
        

    /**
     * Clears the information showed about the selected dataset file of tab 'Datasets'.
     */
    void clearSummaryDatasets();
    
    
    /**
     * Adds a line with information about the selected dataset file of tab 'Datasets'.
     * 
     * @param information Information to add.
     */
    void addLineSummaryDataset(String information);

    
    

    /*
        Tab: Preprocess
    */

    
    /**
     * Returns attributes's name of the opened dataset of tab 'Preprocess'.
     * 
     * @return Attributes's name of the opened dataset of tab 'Preprocess'.
     */
    DefaultTableModel getModelAttributes();
        

    /**
     * Sets attributes's name of the opened dataset of tab 'Preprocess'.
     * 
     * @param datamodel Attributes's name.
     */
    void setModelAttributes(DefaultTableModel datamodel);
           
    
    /**
     * Gets selected row of attributes of tab 'Preprocess'.
     * 
     * @return Selected row of attributes of tab 'Preprocess'.
     */
    int getSelectedRowAttributes();
   
    
    /**
     * Sets selected row of attributes of tab 'Preprocess'.
     * 
     * @param row Row of attributes to select.
     */
    void setSelectedRowAttributes(int row);
    
    
    /**
     * Returns selected attribute's statistics of tab 'Preprocess'.
     * 
     * @return Selected attribute's statistics of tab 'Preprocess'.
     */
    DefaultTableModel getModelStatistics();
    
    
    /**
     * Shows selected attribute's statistics of tab 'Preprocess'.
     * 
     * @param datamodel Selected attribute's statistics.
     */
    void setModelStatistics(DefaultTableModel datamodel);

    
    /**
     * Gets the selected row of the statistical values.
     * 
     * @return Selected row of the statistical values.
     */
    int getSelectedRowStatisticalValues();
    
    
    /**
     * Filters attributes's statistics by attribute's name.
     * 
     * @param attribute Attribute's name.
     */    
    void setStatisticsFilterByAttribute(String attribute);
    
    
    /**
     * Sets the cell renderer for the table that shows the statistics of each attribute of the pre-processed dataset.
     * 
     * @param tableCellRenderer Cell rendeder.
     */        
    void setTblStatisticsCellRenderer(DefaultTableCellRenderer tableCellRenderer);        

    
    /**
     * Adds a line with information about the selected preprocessed dataset file of tab 'Preprocess'.
     * 
     * @param information Information to add.
     */
    void addLineSummaryPreprocessed(String information);
    
        
    /**
     * Clears the information showed about the selected preprocessed dataset file of tab 'Preprocess'.
     */
    void clearSummaryPreprocessed();
    
    
    /**
     * Sets button 'Undo' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnableUndo(boolean state);

    
    /**
     * Sets button 'Restore data' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnableRestoreData(boolean state);
    
    
    /**
     * Sets button 'Previous date' of tab 'Preprocess' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnablePreviousDatePreprocess(boolean state);
    
    
    /**
     * Sets button 'Next date' of tab 'Preprocess' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnableNextDatePreprocess(boolean state);
    
    
    /**
     * Sets button 'Save' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnableSave(boolean state);
      
    
    /**
     * Sets the selected StationID of tab 'Preprocess' by user to create preprocessed dataset files.
     * 
     * @param selectedStationID StationID of the selected buoy of tab 'Preprocess'.
     */
    void setSelectedStationIDPreprocess(String selectedStationID);
    
    
    /**
     * Sets the selected original (source) dataset file of tab 'Preprocess' by user to create new preprocessed dataset files.
     * 
     * @param selectedOriginalDataset Original (source) dataset file of tab 'Preprocess' selected by user.
     */
    void setSelectedOriginalDataset(String selectedOriginalDataset);

    
    /**
     * Gets the selected original (source) dataset file of tab 'Preprocess' by user to create new preprocessed dataset files.
     * 
     * @return Selected original (source) dataset file of tab 'Preprocess' by user to create new preprocessed dataset files.
     */
    String getSelectedOriginalDataset();    
        
    
    /**
     * Sets opened dataset file's name by user to preprocess.
     * 
     * @param openedDataset Dataset file's name.
     */
    void setOpenedDataset(String openedDataset);
        
    
    /**
     * Gets opened dataset file's name by user to preprocess.
     * 
     * @return Opened dataset file's name by user to preprocess.
     */
    String getOpenedDataset();

    
    /**
     * Gets the selected row of filters.
     * 
     * @return Selected row of filters.
     */
    int getSelectedRowFilter();

    
    /**
     * Gets selected filter's name.
     * 
     * @return Selected filter's name.
     */
    String getSelectedFilterName();
    
        
    /**
     * Gets the selected tab inside tab 'Preprocess'.
     * 
     * @return Index of the selected tab.
     */
    int getTabbedPreprocessSelectedIndex();

    
    /**
     * Shows data of the dataset file that is being preprocessed.
     * 
     * @param datamodel Dataset file's data.
     */    
    void setModelPreprocessedData(DefaultTableModel datamodel);
    
    
    /**
     * Hides the received column of the table that shows the preprocessed dataset.
     * 
     * @param numColumn Number of the column to hide.
     */    
    void hideColumnOfPreprocessedData(int numColumn);
          
       
    /**
     * Sets cell renderer for the table that shows the content of the pre-processed dataset.
     * 
     * @param tableCellRenderer Cell rendeder.
     */        
    void setTblPreprocessedDataCellRenderer(DefaultTableCellRenderer tableCellRenderer);        
    
    
    /**
     * Gets the selected row in the table that shows the content of the pre-processed dataset.
     * 
     * @return Selected row in the table that shows the content of the pre-processed dataset.
     */
    int getSelectedRowPreprocessedData();

    
    /**
     * Sets the received row as selected in the pre-processed dataset.
     * 
     * @param row Row to select.
     */
    void setSelectedRowPreprocessedData(int row);    
    

 
    
    /*
        Tab: Matching configuration
    */

    
    /**
     * Returns the buoys's information of tab 'Matching configuration'.
     * 
     * @return Buoys's information of tab 'Matching configuration'.
     */
    DefaultTableModel getModelBuoysMatching();

    
    /**
     * Shows the buoys's information of tab 'Matching configuration'.
     * 
     * @param datamodel Buoys's information.
     */
    void setModelBuoysMatching(DefaultTableModel datamodel);
    
        
    /**
     * Gets the selected row of tab 'Matching'.
     * 
     * @return Selected row of tab 'Matching'.
     */
    int getSelectedRowBuoysMatching();
    
    
    /**
     * Sets the selected row of tab 'Matching configuration'.
     * 
     * @param row Row to select.
     */
    void setSelectedRowBuoysMatching(int row);   

    
    /**
     * Shows the dataset files that belong to the selected buoy of tab 'Matching configuration'.
     * 
     * @param listmodel Dataset files that belong to the selected buoy.
     */
    void setModelMatchingDataset(DefaultListModel<String> listmodel);

    
    /**
     * Shows the preprocessed dataset files that belong to the selected dataset of tab 'Matching configuration'.
     * 
     * @param listmodel Preprocessed dataset files that belong to the selected dataset file.
     */
    void setModelMatchingPreprocessedDataset(DefaultListModel<String> listmodel);


    /**
     * Gets the selected dataset file of tab 'Matching configuration'.
     * 
     * @return Selected dataset file of tab 'Matching configuration'.
     */
    int getSelectedIndexMatchingDataset();
    
    
    /**
     * Sets the selected dataset file of tab 'Matching configuration'.
     * 
     * @param index Index of the dataset file to select.
     */
    void setSelectedIndexMatchingDataset(int index);
                   
    
    /**
     * Gets the selected preprocessed dataset file of tab 'Matching configuration'.
     * 
     * @return Selected preprocessed dataset file of tab 'Matching configuration'.
     */
    int getSelectedIndexMatchingPreprocessedDataset();

    
    /**
     * Sets the selected preprocessed dataset file of tab 'Matching configuration'.
     * 
     * @param index Index of the preprocessed dataset file to select.
     */
    void setSelectedIndexMatchingPreprocessedDataset(int index);
    
    
    /**
     * Sets the selected dataset file's name to use in matching process.
     * 
     * @param selectedMatchingDataset Dataset file's name.
     */
    void setSelectedMatchingDataset(String selectedMatchingDataset);

    
    /**
     * Gets the selected dataset file's name to use in matching process.
     * 
     * @return Selected dataset file's name to use in matching process.
     */
    String getSelectedMatchingDataset();
    
    
    /**
     * Sets the selected Station ID to use in matching process.
     * 
     * @param selectedStationID Selected Station ID.
     */
    void setSelectedStationIDMatching(String selectedStationID);   
    
    
    /**
     * Gets the selected Station ID to use in matching.
     * 
     * @return Selected Station ID to use in matching.
     */
    String getSelectedStationIDMatching();    
    
    
    /**
     * Adds attribute's name to the list of available attributes for classification.
     * 
     * @param attribute Attribute's name.
     */
    void addAttributeNameToPredict(String attribute);
    
    
    /**
     * Deletes all attribute's names from the list of available attributes for classification.
     */
    void deleteAllAttributeNameToPredict();
    
    
    /**
     * Gets the selected attribute's name to predict.
     * 
     * @return Selected attribute's name to predict.
     */
    String getSelectedAttributeToPredict();
    

    /**
     * Sets the selected attribute's name to predict.
     * 
     * @param attribute Attribute's name.
     */
    void setSelectedAttributeToPredict(String attribute);
    

    /**
     * Sets check box 'Attribute to predict' enabled or disabled.
     * 
     * @param enabledDisabled True / False.
     */
    void setEnabledAttributeToPredict(boolean enabledDisabled);

    
    /**
     * Sets the selected reanalysis data files to use in matching process.
     * 
     * @param listmodel Selected reanalysis data files to use in matching process.
     */        
    void setModelReanalysisFiles(DefaultListModel<String> listmodel);              
    
    
    /**
     * Sets the selected variables of the buoy to use in matching process.
     * 
     * @param listmodel Selected variables of the buoy to use in matching process.
     */        
    void setModelBuoyVariables(DefaultListModel<String> listmodel);
    

    /**
     * Sets the text of the button 'Modify' reanalysis files selection.
     * 
     * @param text Text to show.
     */
    void setBtnModifyReanalysisFiles(String text);
    
    
    /**
     * Sets the text of the button 'Modify' variables of the buoy.
     * 
     * @param text Text to show.
     */
    void setBtnModifyBuoyVariables(String text);
    
           
    /**
     * Gets True if user selected 'Flux of energy' or False if not.
     * @return True if user selected 'Flux of energy' or False if not.
     */
    boolean getCalculateFluxOfEnergy();
    
    
    /**
     * Sets True if user selected 'Flux of energy' or False if not.
     * @param calculateFluxOfEnergy True if user selected 'Flux of energy' or False if not.
     */
    void setCalculateFluxOfEnergy(boolean calculateFluxOfEnergy);
       

    /**
     * Gets True if user selected 'Include missing dates' or False if not.
     * @return True if user selected 'Include missing dates' or False if not.
     */
    boolean getIncludeMissingDates();

    
    /**
     * Sets True if user selected 'Include missing dates' or False if not.
     * @param includeMissingDates True if user selected 'Include missing dates' or False if not.
     */
    void setIncludeMissingDates(boolean includeMissingDates);       
    
        
    /**
     * Gets True if user selected 'Direct matching' or False if not.
     * @return True if user selected 'Direct matching' or False if not.
     */
    public boolean getDirectMatching();
        
    
    /**
     * Sets True 'Direct matching' selection.
     */
    public void setDirectMatching();
            
    
    /**
     * Gets True if user selected 'Classification' or False if not.
     * @return True if user selected 'Classification' or False if not.
     */
    public boolean getClassification();
        
    
    /**
     * Sets True 'Classification' selection.
     */
    public void setClassification();
        

    /**
     * Gets True if user selected 'Regression' or False if not.
     * @return True if user selected 'Regression' or False if not.
     */
    public boolean getRegression();

    
    /**
     * Sets True 'Regression' selection.
     */
    public void setRegression();
        
    
    /**
     * Gets the number of nearest geopoints to the buoy to consider.
     * @return Number of nearest geopoints to the buoy to consider.
     */
    String getNumberNearestGeopointsToConsider();
    

    /**
     * Sets the number of nearest geopoints to the buoy to consider.
     * @param numberNearestGeopointsToConsider Number of nearest geopoints to the buoy to consider.
     */
    void setNumberNearestGeopointsToConsider(String numberNearestGeopointsToConsider);    
    
    
    /**
     * Sets text with max of nearest geopoints to consider.
     * @param maxOfNumberNearestGeopointsToConsider Max number of nearest geopoints to the buoy to consider.
     */
    void setTextOfNumberNearestGeopointsToConsider(int maxOfNumberNearestGeopointsToConsider);
    

    /**
     * Gets the output files to create.
     * @return The output files to create.
     */
    int getOutputFilesToCreate();
    
    
    /**
     * Sets the output files to create.
     * @param outputFilesToCreate Output files to create.
     */
    void setOutputFilesToCreate(int outputFilesToCreate);            

    
    /**
     * Sets combo box 'Number of final datasets' enabled or disabled.
     * 
     * @param enabledDisabled True / False.
     */
    void setEnabledOutputFilesToCreate(boolean enabledDisabled);
    
    
    
    /*
        Tab: Final datasets
    */    
    

    /**
     * Gets Model of the table that will show the information about thresholds.
     * @return Model of the table that will show the information about thresholds.
     */
    DefaultTableModel getModelThresholds();
    
    
    /**
     * Gets the row of the selected threshold.
     * 
     * @return Row of the selected threshold.
     */
    int getSelectedRowThresholds();
    
    
    /**
     * Orders thresholds by a specific column.
     * @param index Index of the column to sort the model.
     */
    void setOrderThresholds(int index);

    
    /**
     * Gets Index row in the sorted model of the thresholds.
     * @param row Row to get in the sorted model of the thresholds.
     * 
     * @return Index of the received row in the sorted model.
     */
    int getRowIndexOfSortedModelofThresholds(int row);
    

    /**
     * Gets the class of the threshold.
     * 
     * @return Class of the threshold.
     */
    String getThresholdClass();
    
    
    /**
     * Sets the class of the threshold.
     * 
     * @param className Class of the threshold.
     */
    void setThresholdClass(String className);
        
    
    /**
     * Gets the description of the threshold.
     * 
     * @return Description of the threshold.
     */
    String getThresholdDescription();
        
        
    /**
     * Sets the description of the threshold.
     * 
     * @param description Description of the threshold.
     */
    void setThresholdDescription(String description);

        
    /**
     * Gets the value of the threshold.
     * 
     * @return Value of the threshold.
     */
    String getThresholdValue();
        
        
    /**
     * Sets the value of the threshold.
     * 
     * @param value Value of the threshold.
     */
    void setThresholdValue(String value);    
    
    
    /**
     * Sets the tool tip text of the button 'Add' threshold.
     * 
     * @param tiptext Tool tip text.
     */
    void setBtnAddThresholdToolTipText(String tiptext);
        
    
    /**
     * Sets the action command of the button 'Add' threshold.
     * 
     * @param actionCommand Text with the action command.
     */
    void setBtnAddThresholdActionCommand(String actionCommand);
        

    /**
     * Sets the text of the button 'Add' threshold.
     * 
     * @param text Text to show.
     */
    void setBtnAddThresholdText(String text);
    

    /**
     * Sets btnModifyThreshold enabled or disabled.
     * 
     * @param enabledDisabled True or False.
     */
    void setBtnModifyThresholdEnabled(boolean enabledDisabled);


    /**
     * Sets btnDeleteThreshold enabled or disabled.
     * 
     * @param enabledDisabled True or False.
     */
    void setBtnDeleteThresholdEnabled(boolean enabledDisabled);
    

    /**
     * Sets jtfValueThreshold enabled or disabled.
     * 
     * @param enabledDisabled True or False.
     */
    void setJtfValueThreshold(boolean enabledDisabled);
        
       
    /**
     * Sets cell renderer for the table that shows the thresholds typed by user.
     * 
     * @param tableCellRenderer Cell rendeder.
     */        
    public void setTblThresholdsCellRenderer(DefaultTableCellRenderer tableCellRenderer);        
        
        
    /**
     * Sets enabled/disabled all elements in panel 'Classification Thresholds'.
     * 
     * @param enabledDisabled True / False.
     */
     void setEnabledPanelClassificationThresholds(boolean enabledDisabled);

     
    /**
     * Sets enabled/disabled all elements in panel 'Prediction horizon'.
     * 
     * @param enabledDisabled True / False.
     */
     void setEnabledPanelPredictionHorizon(boolean enabledDisabled);
     
     
    /**
     * Sets the title of the panel of thresholds.
     * 
     * @param title Title of the panel of thresholds.
     */
    void setTitlePanelThresholds(String title);
    
        
    /**
     * Sets the text with the current prediction.
     * 
     * @param text Text with the current prediction.
     */
    void setTextOfPredictionHorizon(String text);   
    
    
    /**
     * Gets the text with the current prediction.
     * 
     * @return The text with the current prediction.
     */
    String getTextOfPredictionHorizon();
    
    
    /**
     * Gets True if user selected 'Synchronise the reanalysis data' or False if not.
     * 
     * @return True if user selected 'Synchronise the reanalysis data' or False if not.
     */
    boolean getSynchroniseReanalysisData();
    
    
    /**
     * Sets True if user selected 'Synchronise the reanalysis data' or False if not.
     * @param synchroniseReanalysisData True if user selected 'Synchronise the reanalysis data' or False if not.
     */
    void setSynchroniseReanalysisData(boolean synchroniseReanalysisData);
               
    
    /**
     * Adds a final dataset to the list of available final datasets to visualise.
     * 
     * @param finalDataset Final dataset's name.
     */
    void addFinalDatasetToVisualise(String finalDataset);
    
    
    /**
     * Deletes all final dataset's names from the list of available final datasets to visualise.
     */
    void deleteAllFinalDatasetToVisualise();
    
    
    /**
     * Gets the index of the selected final dataset to visualise.
     * 
     * @return Selected index of the selected final dataset to visualise.
     */
    int getSelectedIndexFinalDatasetToVisualise();
    

    /**
     * Sets the index of the selected final dataset to visualise.
     * 
     * @param index Index of the selected final dataset to visualise.
     */
    void setSelectedIndexFinalDatasetToVisualise(int index);

        
    /**
     * Returns the data of the final dataset to visualise.
     * 
     * @return The data of the final dataset to visualise.
     */
    DefaultTableModel getModelFinalDataset();

    
    /**
     * Shows data of the final dataset selected by user to visualise.
     * 
     * @param datamodel Final dataset data.
     */    
    void setModelFinalDataset(DefaultTableModel datamodel);           
    
    
    /**
     * Sets cell renderer for the table that shows the content of the final datasets.
     * 
     * @param tableCellRenderer Cell rendeder.
     */        
    void setTblFinalDatasetCellRenderer(DefaultTableCellRenderer tableCellRenderer);        
    
    
    /**
     * Gets the row of the table that show the content of the selected final dataset.
     * 
     * @return Selected row in the table that shows the content of the selected final dataset.
     */
    int getSelectedRowFinalDataset();
    
    
    /**
     * Sets the received row as selected in the final dataset.
     * 
     * @param row Row to select.
     */
    void setSelectedRowFinalDataset(int row);    

       
    /**
     * Hides the received column of the table of final datasets to visualise.
     * 
     * @param numColumn Number of the column to hide.
     */    
    void hideColumnOfFinalDataset(int numColumn);
    
    
    /**
     * Shows the received column of the table of final datasets to visualise.
     * 
     * @param numColumn Number of the column to show.
     */    
    void showColumnOfFinalDatasetToVisualise(int numColumn);
    

    /**
     * Gets True if user selected visualise 'Date attribute' or False if not.
     * 
     * @return True if user selected visualise 'Date attribute' or False if not.
     */
    boolean getVisualiseDateAttribute();
    
    
    /**
     * Sets button 'Previous date'  'Final dataset' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnablePreviousDateFinalDataset(boolean state);
    
    
    /**
     * Sets button 'Next date' of tab 'Final dataset' enabled or disabled.
     * 
     * @param state True / False.
     */
    void setEnableNextDateFinalDataset(boolean state);
    
    
    /**
     * Sets the tool tip text of the label 'lblMissingDateFinalDataset'.
     * 
     * @param tiptext Tool tip text.
     */
    void setlblMissingDateFinalDatasetToolTipText(String tiptext);
                       
    
    /**
     * Sets button 'Create final datasets' enabled or disabled.
     * 
     * @param enabledDisabled True or False.
     */
    void setEnablebtnCreateFinalDatasets(boolean enabledDisabled);
    

    /**
     * Sets the tool tip text of the button 'Create final dataset'.
     * 
     * @param tiptext Tool tip text.
     */
    void setBtnCreateFinalDatasetsToolTipText(String tiptext);

    
    /**
     * Gets the format of the output files to create.
     * @return The format of the output files to create.
     */
    String getOutputFilesFormat();
    
    
    /**
     * Sets the format of the output files to create.
     * @param outputFilesFormat The format of the output files to create.
     */
    void setOutputFilesFormat(String outputFilesFormat);
    
    
    /**
     * Sets the output folder where saving the output files.
     * @param outputFolder The output folder where saving the output files.
     */
    void setOutputFolder(String outputFolder);

    
    /**
     * Gets the output folder where saving the output files.
     * @return The output folder where saving the output files.
     */
    String getOutputFolder();
    
    
    /**
     * Gets True if user selected 'Open files with Weka' or False if not.
     * @return True if user selected 'Open files with Weka' or False if not.
     */
    boolean getOpenDatabasesWithWeka();
    
    
    /**
     * Sets True if user selected 'Open files with Weka' or False if not.
     * @param openDatabasesWithWEKA True if user selected 'Open files with Weka' or False if not.
     */
    void setOpenDatabasesWithWeka(boolean openDatabasesWithWEKA);
    
    
    /**
     * Sets check box 'Open files with Weka' enabled or disabled.
     * 
     * @param enabledDisabled True / False.
     */
    void setEnabledOpenDatabasesWithWeka(boolean enabledDisabled);


    
    /* Constants that define each event in ManageBuoys view. */
    
    /**
     * Event: 'Back' in ManageBuoys view.
     */
    static final String BACK="Back";
    
    
    /**
     * Event: 'Main menu' in ManageBuoys view.
     */
    static final String MAIN_MENU="Main menu";
    
    
    /**
     * Event: 'Buoys' in ManageBuoys view.
     */
    static final String BUOYS="Buoys";
    
    
    /**
     * Event: 'Datasets' in ManageBuoys view.
     */    
    static final String DATASETS="Datasets";
    
    
    /**
     * Event: 'Pre-process' in ManageBuoys view.
     */
    static final String PREPROCESS="Pre-process";
    
    
    /**
     * Event: 'Matching' in ManageBuoys view.
     */    
    static final String MATCHING_CONFIGURATION="Matching configuration";
    
    
    /**
     * Event: 'Help (User manual)' in ManageBuoys view.
     */
    static final String HELP="Help (User manual)";

    
    
    /* Events of Tab: Buoys */    
    
    
    
    /**
     * Event: 'New buoy' in ManageBuoys view.
     */
    static final String NEW_BUOY="New buoy";
    
    
    /**
     * Event: 'View / Modify' in ManageBuoys view.
     */    
    static final String VIEW_MODIFY_BUOY="View / Modify";
    
    
    /**
     * Event: 'Delete buoy' in ManageBuoys view.
     */
    static final String DELETE_BUOY="Delete buoy";
    
    
    
    /* Events of Tab: Datasets */
    
    
    
    /**
     * Event: 'Open dataset file' in ManageBuoys view.
     */
    static final String OPEN_DATASET_FILE="Open dataset file";
    
    
    /**
     * Event: 'New dataset file' in ManageBuoys view.
     */    
    static final String NEW_DATASET_FILE="New dataset file";
    
    
    /**
     * Event: 'Delete dataset file' in ManageBuoys view.
     */
    static final String DELETE_DATASET_FILE="Delete dataset file";
    
    
    /**
     * Event: 'Open preprocessed file' in ManageBuoys view.
     */
    static final String OPEN_PREPROCESSED_FILE="Open preprocessed file";
    
    
    /**
     * Event: 'Delete preprocessed file' in ManageBuoys view.
     */
    static final String DELETE_PREPROCESSED_FILE="Delete preprocessed file";

    
    
    /* Events of Tab: Preprocess. */
    
    

    /**
     * Event: 'Open dataset' in ManageBuoys view.
     */
    static final String OPEN_DATASET="Open dataset";
    
    
    /**
     * Event: 'Save preprocess' in ManageBuoys view.
     */
    static final String SAVE_PREPROCESS="Save preprocess";
    
    
    /**
     * Event: 'Apply filter' in ManageBuoys view.
     */
    static final String APPLY_FILTER="Apply filter";
    
    
    /**
     * Event: 'Undo filter' in ManageBuoys view.
     */
    static final String UNDO="Undo filter";
    
    
    /**
     * Event: 'Restore data' in ManageBuoys view.
     */
    static final String RESTORE_DATA="Restore data";
    
    
    /**
     * Event: 'Configure filter' in ManageBuoys view.
     */
    static final String CONFIGURE_FILTER="Configure filter";
    

    /**
     * Event: 'PreviousDatePreprocess' in ManageBuoys view.
     */
    static final String PREVIOUS_DATE_PREPROCESS="PreviousDatePreprocess";


    /**
     * Event: 'NextDatePreprocess' in ManageBuoys view.
     */
    static final String NEXT_DATE_PREPROCESS="NextDatePreprocess";
                
    
    /* Events of Tab: Matching configuration. */
    
    

    /**
     * Event: 'Load matching' in ManageBuoys view.
     */
    static final String LOAD_MATCHING="Load matching";
    
    
    /**
     * Event: 'Run matching' in ManageBuoys view.
     */
    static final String RUN_MATCHING="Run matching";
    
    
    /**
     * Event: 'Select dataset file' in ManageBuoys view.
     */
    static final String SELECT_DATASET_FILE="Select dataset file";
    
    
    /**
     * Event: 'Select preprocessed dataset file' in ManageBuoys view.
     */
    static final String SELECT_PREPROCESSED_DATASET_FILE="Select preprocessed dataset file";
    
    
    /**
     * Event: 'ModifyRenalysisFiles' in ManageBuoys view.
     */
    static final String MODIFY_REANALYSIS_FILES="ModifyRenalysisFiles";
    
    
    /**
     * Event: 'ModifyBuoyVariables' in ManageBuoys view.
     */
    static final String MODIFY_BUOY_VARIABLES="ModifyBuoyVariables";
    
    
    /**
     * Event: 'Flux of energy changed' in ManageBuoys view.
     */
    static final String FLUX_OF_ENERGY_CHANGED="Flux of energy changed";
    
    
    /**
     * Event: 'Attribute to predict changed' in ManageBuoys view.
     */
    static final String ATTRIBUTE_TO_PREDICT_CHANGED="Attribute to predict changed";
    
       
        
    /* Events of Tab: Final datasets. */
    


    /**
     * Event: 'Delete threshold' in ManageBuoys view.
     */
    static final String DELETE_THRESHOLD="Delete threshold";
    
    
    /**
     * Event: 'Add threshold' in ManageBuoys view.
     */
    static final String ADD_THRESHOLD="Add threshold";


    /**
     * Event: 'Modify threshold' in ManageBuoys view.
     */
    static final String MODIFY_THRESHOLD="Modify threshold";
    
    
    /**
     * Event: 'Update threshold' in ManageBuoys view.
     */
    static final String UPDATE_THRESHOLD="Update threshold";

    
    /**
     * Event: 'Increase prediction horizon' in ManageBuoys view.
     */
    static final String INCREASE_PREDICTION_HORIZON="Increase prediction horizon";
    
    
    /**
     * Event: 'Decrease prediction horizon' in ManageBuoys view.
     */
    static final String DECREASE_PREDICTION_HORIZON="Decrease prediction horizon";

    
    /**
     * Event: 'Synchronise the reanalysis data' in ManageBuoys view.
     */
    static final String SYNCHRONISE_R_DATA="Synchronise the reanalysis data";
    
    
    /**
     * Event: 'Update final dataset' in ManageBuoys view.
     */
    static final String UPDATE_FINAL_DATASET="Update final dataset";
            
    
    /**
     * Event: 'Output file' in ManageBuoys view.
     */
    static final String OUTPUT_FILE="Output file";
    
    
    /**
     * Event: 'Final datasets format changed' in ManageBuoys view.
     */
    static final String FINAL_DATASETS_FORMAT_CHANGED="Final datasets format changed";
    
    
    /**
     * Event: 'Show DATE attribute' in ManageBuoys view.
     */
    static final String SHOW_DATE_ATTRIBUTE="Show DATE attribute";
    
    
    /**
     * Event: 'PreviousDateFinalDataset' in ManageBuoys view.
     */
    static final String PREVIOUS_DATE_FINAL_DATASET="PreviousDateFinalDataset";


    /**
     * Event: 'NextDateFinalDataset' in ManageBuoys view.
     */
    static final String NEXT_DATE_FINAL_DATASET="NextDateFinalDataset";
        
    
    /**
     * Event: 'Final dataset to visualise changed' in ManageBuoys view.
     */
    static final String FINAL_DATASET_TO_VISUALISE_CHANGED="Final dataset to visualise changed";

            
    /**
     * Event: 'Save matching' in ManageBuoys view.
     */
    static final String SAVE_MATCHING="Save matching";

    
    /**
     * Event: 'Create final datasets' in ManageBuoys view.
     */
    static final String CREATE_FINAL_DATASETS="Create final datasets";    
    
}
