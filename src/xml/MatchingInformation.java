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

package xml;

import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This class defines the information that is saved in a XML file
 * related to a matching process.
 * 
 */

@XmlRootElement
public class MatchingInformation {

    
    /**
     * Buoy's ID.
     */
    private int idBuoy;
    
    
    /**
     * Station ID of the buoy.
     */
    private String stationID;
    
    
    /**
     * Reanalysis data's files.
     */
    private ArrayList<String> reanalysisFiles;


    /**
     * Reanalysis data's files vars.
     */
    private ArrayList<String> reanalysisVars;


    /**
     * Dataset file's name.
     */
    private String datasetFileName;
        

    /**
     * Dataset file's path.
     */
    private String datasetFilePath;
    
    
    /**
     * Type (Dataset/Preprocessed) of the dataset file.
     */
    private String typeOfDatasetFile;
    
    
    /**
     * Date of creation.
     */
    private Calendar creationDate;
    
    
    /**
     * Short description typed by user.
     */
    private String shortDescription;
    

    /**
     * Attribute's name to predict.
     */
    private String attributeNameToPredict;

        
    /**
     * Indicates if flux of energy will be used as output.
     */
    private boolean calculateFluxOfEnergy;
    
    
    /**
     * Names of the variables of the buoy to include.
     */
    private ArrayList<String> buoyVariables;
        
    
    /**
     * Indicates if user selected 'Include missing dates' or not.
     */    
    private boolean includeMissingDates;

    
    /**
     * Number of nearest geopoints to the buoy to consider.
     */
    private int numberOfNearestGeopointsToConsider;
    
    
    /**
     * Type of matching: 'Direct matching', 'Prediction' or 'Forecasting'.
     */
    private String typeOfMatching;
    
    
    /**
     * Output files to create.
     */
    private int outputFilesToCreate;

    
    /**
     * Output folder where saving the output files.
     */
    private String outputFolder;
    
    
    /**
     * Format of the output files to create.
     */    
    private String outputFilesFormat;
    
    
    /**
     * Indicates if user selected 'Open files with Weka' or not.
     */
    private boolean openDatabasesWithWEKA;
    
  
    /**
     * Class of the thresholds typed by the user.
     */
    private ArrayList<String> thresholdsClass;


    /**
     * Description of each class of the thresholds typed by the user.
     */
    private ArrayList<String> thresholdsDescription;
    
    
    /**
     * Value of each threshold typed by the user.
     */
    private ArrayList<Double> thresholdsValues;


    /**
     * Value of the prediction horizon typed by the user.
     */
    private int predictionHorizon;

    
    /**
     * Indicates if user selected 'Synchronise the reanalysis data' or not.
     */
    private boolean synchroniseRData;


    /**
     * Constructor.
     */
    public MatchingInformation(){

        /* Initializes to default values. */
        
        this.idBuoy=0;
        this.stationID="";
        this.reanalysisFiles = new ArrayList<>();
        this.reanalysisVars = new ArrayList<>();
        this.datasetFileName=null;
        this.datasetFilePath="";
        this.typeOfDatasetFile=null;
        this.creationDate = null;   
        this.shortDescription = "";
        this.calculateFluxOfEnergy=false;
        this.attributeNameToPredict="";
        this.buoyVariables = new ArrayList<>();
        this.includeMissingDates=false;
        this.numberOfNearestGeopointsToConsider=0;
        this.typeOfMatching="";
        this.outputFilesToCreate=0;
        this.outputFilesFormat=null;
        this.outputFolder=null;
        this.openDatabasesWithWEKA=false;        
        this.thresholdsClass = new ArrayList<>();
        this.thresholdsDescription = new ArrayList<>();
        this.thresholdsValues = new ArrayList<>();    
        this.predictionHorizon=0;
        this.synchroniseRData=false;
        
    }
    
       
    /**
     * Sets buoy's ID.
     * @param idBuoy Buoy's ID.
     */    
    @XmlAttribute
    public void setIDBuoy(int idBuoy) {
        
        this.idBuoy = idBuoy;
    }


    /**
     * Returns buoy's ID.
     * @return Buoy's ID.
     */
    public int getIDBuoy() {
        
        return this.idBuoy;
    }
    
    
    /**
     * Sets the Station ID of the buoy.
     * @param stationID Station ID of the buoy.
     */    
    @XmlAttribute
    public void setStationID(String stationID) {
        
        this.stationID = stationID;
    }
    
    
    /**
     * Gets the Station ID of the buoy.
     * @return The Station ID of the buoy.
     */        
    public String getStationID() {
        
        return this.stationID;
    }
    

    /**
     * Sets reanalysis data's files.
     * @param reanalysisFiles Reanalysis data's files.
     */       
    @XmlElement
    public void setReanalysisFiles(ArrayList<String> reanalysisFiles) {
        
        /* Removes all elements. */
        this.reanalysisFiles.clear();
        
        /* Sets each reanalysis file. */
        for (String file : reanalysisFiles) {

                this.reanalysisFiles.add(file);

        }       

    }

    
    /**
     * Returns reanalysis data's files.
     * @return Reanalysis data's files.
     */     
    public ArrayList<String> getReanalysisFiles() {
        
        return this.reanalysisFiles;
    }
    
    
    /**
     * Sets reanalysis data's files vars.
     * @param reanalysisVars Reanalysis data's files vars.
     */       
    @XmlElement
    public void setReanalysisVars(ArrayList<String> reanalysisVars) {
        
        /* Removes all elements. */
        this.reanalysisVars.clear();

        /* Sets each reanalysis var. */
        for (String var : reanalysisVars) {

                this.reanalysisVars.add(var);

        }       
        
    }

    
    /**
     * Returns reanalysis data's files vars.
     * @return Reanalysis data's files vars.
     */     
    public ArrayList<String> getReanalysisVars() {
        
        return this.reanalysisVars;
    }
       
        
    /**
     * Sets dataset file's name.
     * @param datasetFileName Dataset file's name.
     */    
    @XmlAttribute
    public void setDatasetFileName(String datasetFileName) {
        
        this.datasetFileName = datasetFileName;
    }


    /**
     * Returns dataset file's name.
     * @return Dataset file's name.
     */
    public String getDatasetFileName() {
        
        return this.datasetFileName;
    }    
    
    
    /**
     * Sets dataset file's path.
     * @param datasetFilePath Dataset file's path.
     */    
    @XmlAttribute
    public void setDatasetFilePath(String datasetFilePath) {
        
        this.datasetFilePath = datasetFilePath;
    }


    /**
     * Returns dataset file's path.
     * @return Dataset file's path.
     */
    public String getDatasetFilePath() {
        
        return this.datasetFilePath;
    }

    
    /**
     * Sets the type (dataset/preprocessed dataset) of the dataset file.
     * @param type Type (dataset/preprocessed dataset) of the dataset file.
     */    
    @XmlAttribute
    public void setTypeDatasetFile(String type) {
        
        this.typeOfDatasetFile = type;
    }
    
    
    /**
     * Gets the type (dataset/preprocessed dataset) of the dataset file.
     * @return Type (dataset/preprocessed dataset) of the dataset file.
     */        
    public String getTypeDatasetFile() {
        
        return this.typeOfDatasetFile;
    }

    
    /**
     * Sets the date when matching file was created.
     * @param dateCreation Date when matching file was created.
     */    
    @XmlAttribute
    public void setDateCreation(Calendar dateCreation) {
        
        this.creationDate = dateCreation;
    }

    
    /**
     * Returns date when matching file was created.
     * @return Date when matching file was created.
     */ 
    public Calendar getDateCreation() {
        
        return this.creationDate;
    }
    
    
    /**
     * Sets short description typed by user.
     * @param shortDescription Short description typed by user.
     */       
    @XmlElement
    public void setShortDescription(String shortDescription) {
        
        this.shortDescription = shortDescription;
    }

    
    /**
     * Returns short description typed by user.
     * @return Short description typed by user.
     */     
    public String getShortDescription() {
        
        return this.shortDescription;
    }
    
    
    /**
     * Returns True if user selected 'Flux of energy' as output or False if not.
     * @return True if user selected 'Flux of energy' as output or False if not.
     */
    public boolean getCalculateFluxOfEnergy() {
        
        return this.calculateFluxOfEnergy;
    }

    
    /**
     * Sets True if user selected 'Flux of energy' as output or False if not.
     * @param calculateFluxOfEnergy User selection of 'Flux of energy' option.
     */    
    @XmlAttribute
    public void setCalculateFluxOfEnergy(boolean calculateFluxOfEnergy) {
        
        this.calculateFluxOfEnergy = calculateFluxOfEnergy;
    }       
    

    /**
     * Sets attribute's name to predict.
     * @param attributeNameToPredict Attribute's name to predict.
     */    
    @XmlAttribute
    public void setAttributeNameToPredict(String attributeNameToPredict) {
        
        this.attributeNameToPredict = attributeNameToPredict;
    }


    /**
     * Returns attribute's name to predict.
     * @return Attribute's name to predict.
     */
    public String getAttributeNameToPredict() {
        
        return this.attributeNameToPredict;
    }


    /**
     * Sets the names of the variables of the buoy to include.
     * @param buoyVariables Names of the variables of the buoy to include.
     */       
    @XmlElement
    public void setBuoyVariables(ArrayList<String> buoyVariables) {
        
        /* Removes all elements. */
        this.buoyVariables.clear();
        
        /* Sets each name of the variable. */
        for (String variable : buoyVariables) {

                this.buoyVariables.add(variable);

        }       

    }

    
    /**
     * Returns the names of the variables to include.
     * @return Names of the variables to include.
     */     
    public ArrayList<String> getBuoyVariables() {
        
        return this.buoyVariables;
    }
    
    
    /**
     * Sets True if user selected 'Include missing dates' or False if not.
     * @param includeMissingDates User selection of 'Include missing dates' option.
     */    
    @XmlAttribute
    public void setIncludeMissingDates(boolean includeMissingDates) {
        
        this.includeMissingDates = includeMissingDates;
    }


    /**
     * Returns True if user selected 'Include missing dates' or False if not.
     * @return True if user selected 'Include missing dates' or False if not.
     */
    public boolean getIncludeMissingDates() {
        
        return this.includeMissingDates;
    }   
        
    
    /**
     * Sets number of nearest geopoints to the buoy to consider.
     * @param numberOfNearestGeopointsToConsider Number of nearest geopoints to the buoy to consider.
     */    
    @XmlAttribute
    public void setNumberOfNearestGeopointsToConsider(int numberOfNearestGeopointsToConsider) {
        
        this.numberOfNearestGeopointsToConsider = numberOfNearestGeopointsToConsider;
    }


    /**
     * Returns number of nearest geopoints to the buoy to consider.
     * @return Number of nearest geopoints to the buoy to consider.
     */
    public int getNumberOfNearestGeopointsToConsider() {
        
        return this.numberOfNearestGeopointsToConsider;
    }


    /**
     * Sets output files to create.
     * @param outputFilesToCreate Output files to create.
     */    
    @XmlAttribute
    public void setOutputFilesToCreate(int outputFilesToCreate) {
        
        this.outputFilesToCreate = outputFilesToCreate;
    }

    
    /**
     * Returns output files to create.
     * @return Output files to create.
     */
    public int getOutputFilesToCreate() {
        
        return this.outputFilesToCreate;
    }
        
    
    /**
     * Sets the type of the matching.
     * @param typeOfMatching Type of the matching.
     */    
    @XmlAttribute
    public void setTypeOfMatching(String typeOfMatching) {
        
        this.typeOfMatching = typeOfMatching;
    }
    
    
    /**
     * Gets the type of the matching.
     * @return Type of the matching.
     */        
    public String getTypeOfMatching() {
        
        return this.typeOfMatching;
    }
            
    
    /**
     * Sets format of the output files.
     * @param outputFilesFormat Format of the output files.
     */    
    @XmlAttribute
    public void setOutputFilesFormat(String outputFilesFormat) {
        
        this.outputFilesFormat = outputFilesFormat;
    }


    /**
     * Returns format of the output files.
     * @return Format of the output files.
     */
    public String getOutputFilesFormat() {
        
        return this.outputFilesFormat;
    }
    
                
    /**
     * Sets output folder where saving the output files.
     * @param outputFolder Output folder where saving the output files.
     */    
    @XmlAttribute
    public void setOutputFolder(String outputFolder) {
        
        this.outputFolder = outputFolder;
    }


    /**
     * Returns output folder where saving the output files.
     * @return Output folder where saving the output files.
     */
    public String getOutputFolder() {
        
        return this.outputFolder;
    }
                   
    
    /**
     * Sets True if user selected 'Open files with Weka' or False if not.
     * @param openDatabasesWithWEKA User selection of 'Open files with Weka' option.
     */    
    @XmlAttribute
    public void setOpenDatabasesWithWEKA(boolean openDatabasesWithWEKA) {
        
        this.openDatabasesWithWEKA = openDatabasesWithWEKA;
    }


    /**
     * Returns True if user selected 'Open files with Weka' or False if not.
     * @return True if user selected 'Open files with Weka' or False if not.
     */
    public boolean getOpenDatabasesWithWEKA() {
        
        return this.openDatabasesWithWEKA;
    }   
        
    
    /**
     * Returns class of the thresholds typed by the user.
     * @return Class of the thresholds typed by the user.
     */    
    public ArrayList<String> getThresholdsClass() {
        
        /* Gets class of the thresholds typed by the user. */
        
        return this.thresholdsClass;
        
    }
    
        
    /**
     * Sets class of the thresholds typed by the user.
     * @param thresholdsClass Class of the thresholds typed by the user.
     */ 
    @XmlElement
    public void setThresholdsClass(ArrayList<String> thresholdsClass) {
        
        /* Sets class of the thresholds typed by the user. */
        
        this.thresholdsClass = thresholdsClass;
        
    }    


    /**
     * Returns description of each class of the thresholds typed by the user.
     * @return Description of each class of the thresholds typed by the user.
     */    
    public ArrayList<String> getThresholdsDescription() {
        
        /* Gets description of each class of the thresholds typed by the user. */
        
        return this.thresholdsDescription;
        
    }
    
    
    /**
     * Sets description of each class of the thresholds typed by the user.
     * @param thresholdsDescription Description of each class of the thresholds typed by the user.
     */  
    @XmlElement
    public void setThresholdsDescription(ArrayList<String> thresholdsDescription) {
        
        /* Sets description of each class of the thresholds typed by the user. */
        
        this.thresholdsDescription = thresholdsDescription;
        
    }

    
    /**
     * Returns value of each threshold typed by the user.
     * @return Value of each threshold typed by the user.
     */    
    public ArrayList<Double> getThresholdsValues() {
        
        /* Gets value of each threshold typed by the user. */
        
        return this.thresholdsValues;
        
    }
    
    
    /**
     * Sets value of each threshold typed by the user.
     * @param thresholdsValues value of each threshold typed by the user.
     */  
    @XmlElement
    public void setThresholdsValues(ArrayList<Double> thresholdsValues) {
        
        /* Sets value of each threshold typed by the user. */
        
        this.thresholdsValues = thresholdsValues;
        
    }
    
    
    /**
     * Sets prediction horizon for using in regression.
     * @param predictionHorizon Prediction horizon for using in regression.
     */    
    @XmlAttribute
    public void setPredictionHorizon(int predictionHorizon) {
        
        this.predictionHorizon = predictionHorizon;
    }


    /**
     * Returns prediction horizon for using in regression.
     * @return Prediction horizon for using in regression.
     */
    public int getPredictionHorizon() {
        
        return this.predictionHorizon;
    }
    
    
    /**
     * Sets True if user selected 'Synchronise the reanalysis data' or False if not.
     * @param synchroniseRData User selection of 'Synchronise the reanalysis data' option.
     */    
    @XmlAttribute
    public void setSynchroniseRData(boolean synchroniseRData) {
        
        this.synchroniseRData = synchroniseRData;
    }


    /**
     * Returns True if user selected 'Synchronise the reanalysis data' or False if not.
     * @return True if user selected 'Synchronise the reanalysis data' or False if not.
     */
    public boolean getSynchroniseRData() {
        
        return this.synchroniseRData;
    }      
    
}
