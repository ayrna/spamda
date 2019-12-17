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


package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import xml.DatasetInformation;



/**
 * This class defines the model for pre-processing the information of an intermediate or pre-processed dataset file.
 *
 */
public class PreprocessDatasetFile extends HistoricalFile {

    
    /**
     * Instances in WEKA format.
     */
    private InstancesWeka instancesWEKA;
    
    
    /**
     * Backup of instances in WEKA format.
     */
    private InstancesWeka backupInstancesWEKA;
    
    
    /**
     * Preprocessed dataset file's header.
     */    
    private String headerPreprocessedDatasetFile;


    /**
     * Constructor.
     */
    public PreprocessDatasetFile(){
        
        /* Calls MemoryFile constructor. */
        super();

        /* Initializes to default values. */
        
        this.instancesWEKA = new InstancesWeka();
        this.backupInstancesWEKA = new InstancesWeka();
        this.headerPreprocessedDatasetFile = null;
        
    }
    
    
    /* Methods of the class */
   
    
    /**
     * Returns instances in WEKA format.
     * @return Instances in WEKA format.
     */
    public InstancesWeka getInstancesWEKA() {
        
        return this.instancesWEKA;        
        
    }
    
    
    /**
     * Returns backup of instances in WEKA format.
     * @return Backup of instances in WEKA format.
     */    
    private InstancesWeka getBackupInstancesWEKA() {
        
        return this.backupInstancesWEKA;
        
    }

    
    /**
     * Sets the backup of instances in WEKA format.
     * @param backupInstancesWEKA Backup of instances in WEKA format.
     */    
    private void setBackupInstancesWEKA(InstancesWeka backupInstancesWEKA) {
        
        this.backupInstancesWEKA.setInstances(backupInstancesWEKA.getInstances());

    }
    
    /**
     * Returns preprocessed dataset file's header.
     * @return preprocessed dataset file's header.
     */    
    public String getHeaderPreprocessedDatasetFile() {
        
        return this.headerPreprocessedDatasetFile;
        
    }

    
    /**
     * Sets preprocessed dataset file's header.
     * @param headerPreprocessedDatasetFile Preprocessed dataset file's header.
     */    
    private void setHeaderPreprocessedDatasetFile(String headerPreprocessedDatasetFile) {
        
        this.headerPreprocessedDatasetFile=headerPreprocessedDatasetFile;

    }       
            
               
    /**
     * Makes a backup of instances in WEKA format.
     */    
    public void makeBackupOfWEKAInstances() {
        
        setBackupInstancesWEKA(getInstancesWEKA());
        
    }
    
    
    /**
     * Restores backup of instances in WEKA format.
     */    
    public void restoreBackupOfWEKAInstances() {
        
        getInstancesWEKA().setInstances(getBackupInstancesWEKA().getInstances());
        
    }
    
    
    /**
     * Restores to original instances in WEKA format.
     */    
    public void restoreToOriginalWEKAInstances() {
        
        /* 
            Clears previous data and converts original attributes 
            and instances of dataset file to Weka format.
        */
        
        converToWekaFormat();
        
    }
                        
        
    /**
     * Converts the dataset file received to Weka format.
     * @param datasetFile Dataset file to convert to Weka format.
     */
    public void setDatasetFile(DatasetFile datasetFile){

        /* Sets the original attributes. */
        setAttributes(datasetFile.getAttributes());
        
        /* Sets the original instances. */
        setInstances(datasetFile.getInstances());
        
        /* Converts attributes and intances of dataset file to Weka format. */
        converToWekaFormat();
                
    }

       
    /**
     * Converts original data (attributes and instances) to Weka format.
     */
    private void converToWekaFormat(){
         
        /* Number of attributes of the file. */
        int numAttributes=numAttributes();
        
        /* Attributes of the file. */
        ArrayList<Attribute> attributesWEKA = new ArrayList<>(numAttributes);
        
        for(int i=0;i<numAttributes;i++){
                
            /* Adds all the attributes of the database. */

            attributesWEKA.add(new Attribute( getAttribute(i) ));                
                
        }

        if(getInstancesWEKA().getInstances()!=null){
            
            /* Deletes all previous instances. */            
            
            getInstancesWEKA().getInstances().clear();
            
        }
        
        /* Sets new instances. */        
        getInstancesWEKA().setInstances(new Instances("relationName",attributesWEKA,0));
        
        for(model.Instance instance : getInstances()){
            
            /* Adds all instances of the file. */
            
            Instance newInstanceWEKA = new DenseInstance(numAttributes);
            
            for(int i=0;i<instance.numFieldsValues();i++){
                
                /* Adds all fields of each instance of the file. */
                
                newInstanceWEKA.setValue(i, instance.getFieldValue(i).doubleValue());
                
            }
            
            getInstancesWEKA().getInstances().add(newInstanceWEKA);
            
        }
        
        /* Converts missing values of instances in missing values according to Weka. */
        setMissingDataToWekaFormat();        
        
    }
    

    /**
     * Writes the preprocessed information (WEKA instances) in TXT format on disk.
     * @param preprocessedDatasetFile File's name to write on disk.
     * @return True if the process was properly finished or False if the process failed.
     */
    public boolean writePreprocessedDatasetFile(File preprocessedDatasetFile) {
              
        /* Result of the process. */
        boolean result = false;
        
        /* To convert from Unix seconds to date. */
        Utils util = new Utils();


        FileWriter fwDatasetFile;

        try {

            /* Header of the preprocessed dataset file. */
            String headerOfPreprocessedDatasetFile;

            fwDatasetFile = new FileWriter(preprocessedDatasetFile, true);

            try (BufferedWriter bwPreprocessedDatasetFile = new BufferedWriter(fwDatasetFile)) {

                /* Gets attributes. */
                
                ArrayList<weka.core.Attribute> attributes = getInstancesWEKA().getAttributes();
                
                /* Gets number of attributes. */
                int numAttributes = attributes.size();

                /* Saves header of the preprocessed dataset file with the name of each attribute. */
                
                /* First field: Date. */
                headerOfPreprocessedDatasetFile="YY  MM DD hh mm ";
                
                for(int i=1; i<numAttributes; i++){

                    /* Writes the name of each attribute. */

                    headerOfPreprocessedDatasetFile+=attributes.get(i).name() + " ";

                }
                
                /* Sets header. */                
                setHeaderPreprocessedDatasetFile(headerOfPreprocessedDatasetFile);
                
                /* Writes header. */
                bwPreprocessedDatasetFile.write(headerOfPreprocessedDatasetFile);

                /* Writes LF. */
                bwPreprocessedDatasetFile.newLine();

                /* Saves all instances of the preprocessed dataset file. */
                for(weka.core.Instance instance : getInstancesWEKA().getInstances()){
                                                                                
                    /* Saves all fields values of each instance. */
                    
                    /* First field: Date. */
                    bwPreprocessedDatasetFile.write(util.unixSecondsToString( (long) instance.value(0)));
                    
                    /* Value field separator. */
                    bwPreprocessedDatasetFile.write(" ");                                                    
                    
                    /* Remaining fields. */
                    for(int i=1; i<numAttributes; i++){

                        /* Writes the value of each attribute. */

                        if(instance.isMissing(i) == true){

                            /* Saves original missing value */

                            String attribute = instance.attribute(i).name();

                            bwPreprocessedDatasetFile.write(String.valueOf(getValueOfMissingValue(attribute)));

                        }else{

                            bwPreprocessedDatasetFile.write(String.valueOf(instance.value(i)));

                        }

                        /* Value field separator. */
                        bwPreprocessedDatasetFile.write(" ");                                
                    }

                    /* Writes LF. */
                    bwPreprocessedDatasetFile.newLine();
                }
                
                /* All instances have been saved. */
                //bwPreprocessedDatasetFile.close(); /* auto-closeable. */
                //fwDatasetFile.close();
                
                /* Process properly finished . */
                result = true;

            }catch (IOException e){

                Logger.getLogger(PreprocessDatasetFile.class.getName()).log(Level.SEVERE, null, e);

            }

        }catch (IOException ex){

            Logger.getLogger(PreprocessDatasetFile.class.getName()).log(Level.SEVERE, null, ex);

        }

        return result;
        
    }       
    
    
    /**
     * Converts missing values of one instance to missing values according to Weka.
     */
    private void setMissingDataToWekaFormat() {
        
        /* Gets the instances. */
        weka.core.Instances newInstancesWEKA = getInstancesWEKA().getInstances();

        /* Gets the number of instances. */
        int numInstances = getInstancesWEKA().getNumberOfInstances();
        
        /* Gets the number of attributes. */
        int numAttributes = getInstancesWEKA().getNumberOfAttributes();
        
        /* Checks each field value in all instances. */
        for (int i = 0; i < numInstances; i++) {
            
            /* The first attribute is: DATE (it is not necessary to check). */
            for (int j = 1; j < numAttributes; j++) {
                
                /* Gets attribute's name. */               
                String attName = newInstancesWEKA.get(i).attribute(j).name();
                
                
                /* Checks if it contains a missing value. */
                if (newInstancesWEKA.get(i).value(j)!=0.0 && 
                    (newInstancesWEKA.get(i).value(j)== getValueOfMissingValue(attName) || Double.isNaN(newInstancesWEKA.get(i).value(j)))) {

                    newInstancesWEKA.get(i).setMissing(j);
                }                        

            }
        }
    }

    
    /**
     * Calculates statistics of each field.
     * @return Statistics calculated of each field.
     */
    public ArrayList<DatasetInformation.AttributeStatistics> calculateAttributeStatistics() {
        
        /* To store the statistics of all attributes. */
        ArrayList<DatasetInformation.AttributeStatistics> statistics = new ArrayList<>();
      
        /* Calculates statistics of each attribute. */
        for(int i=0;i<getInstancesWEKA().getInstances().numAttributes();i++){
        
            /* Statistics of one attribute. */
            DatasetInformation.AttributeStatistics attributeSTAT = new DatasetInformation.AttributeStatistics();
            
            /* Gets the attribute. */
            weka.core.AttributeStats attributeWEKA = getInstancesWEKA().getInstances().attributeStats(i);
            
            /* Name of the attribute. */
            attributeSTAT.setAttributeName(getInstancesWEKA().getInstances().attribute(i).name());
          
           
                /* Gets statistics. */
                
            
                /* Minimum */
                DatasetInformation.Statistic statMin = new DatasetInformation.Statistic();
                statMin.setName("Minimum");
                statMin.setValue(attributeWEKA.numericStats.min);
                attributeSTAT.getAttributeStatistics().add(statMin);
            
                
                /* Maximum */
                DatasetInformation.Statistic statMax = new DatasetInformation.Statistic();
                statMax.setName("Maximum");
                statMax.setValue(attributeWEKA.numericStats.max);
                attributeSTAT.getAttributeStatistics().add(statMax);

                
                /* Mean */
                DatasetInformation.Statistic statMean = new DatasetInformation.Statistic();
                statMean.setName("Mean");
                statMean.setValue(attributeWEKA.numericStats.mean);
                attributeSTAT.getAttributeStatistics().add(statMean);

                
                /* StdDev. */
                DatasetInformation.Statistic statStdDev = new DatasetInformation.Statistic();                
                statStdDev.setName("StdDev");
                statStdDev.setValue(attributeWEKA.numericStats.stdDev);
                attributeSTAT.getAttributeStatistics().add(statStdDev);

                
                /* statMissingCount */
                DatasetInformation.Statistic statMissingCount = new DatasetInformation.Statistic();
                statMissingCount.setName("Missing count");
                statMissingCount.setValue( (double) attributeWEKA.missingCount);
                attributeSTAT.getAttributeStatistics().add(statMissingCount);
                               
            /* Adds the statistics of the attribute. */
            statistics.add(attributeSTAT);

        }
    
        return statistics;
        
    }
    
    
    
    /**
     * Checks all instances looking for missing or duplicated dates.
     * @return Missing or duplicated dates found.
     */
    public ArrayList<String> checkMissingDates(){
    
        /* 
            Checks all instances looking for missing
            or duplicated dates.
        */         

        
        /* 
            ************************************************************
            Any change in this method has to be also done in the methods:
                checkMissingDates() of RunCreationDataset.java
                reloadViewData()    of ControllerViewManageBuoys.java
            ************************************************************
        */

        
        /* To convert from int to String with two digits. */
        DecimalFormat intFormatter = new DecimalFormat("00");
        
        /* Utilities. */
        Utils util = new Utils();
                                
        /* Instances to check. */
        weka.core.Instances instancesMerged = getInstancesWEKA().getInstances();
        
        /* Number of instances to check. */
        int numberOfInstances = getInstancesWEKA().getNumberOfInstances();
        
        /* Indicates if a missing date has been found. */
        boolean missingDateFound;
        
        /* Missing or duplicated dates. */
        ArrayList<String> missingDates = new ArrayList<>();
        
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
        
            /* Gets first date. */
            String firstDateInStringFormat = util.unixSecondsToString( (long) (instancesMerged.get(0).value(0)));
            Calendar firstDateInDateFormat = util.convertDateFromStringToCalendar(firstDateInStringFormat);

            /* Second date. */
            String secondDateInStringFormat;
            Calendar secondDateInDateFormat;


            /* Check all instances. */
            for(int i=1;i<numberOfInstances;i++){

                /* Gets second date. */
                secondDateInStringFormat = util.unixSecondsToString( (long) (instancesMerged.get(i).value(0)));
                secondDateInDateFormat = util.convertDateFromStringToCalendar(secondDateInStringFormat);

                /* Checks if both dates are equals. */
                if(dateFormat.format(firstDateInDateFormat.getTime()).equals(dateFormat.format(secondDateInDateFormat.getTime()))==true){

                    /* Equals. */

                    /* Month: + 1 because 0 -> January ... 11 -> December. */                
                    missingDates.add("Duplicated: "+ firstDateInDateFormat.get(Calendar.YEAR)+" "+
                                                                intFormatter.format((firstDateInDateFormat.get(Calendar.MONTH)+1))+" "+
                                                                intFormatter.format(firstDateInDateFormat.get(Calendar.DATE))+" "+
                                                                intFormatter.format(firstDateInDateFormat.get(Calendar.HOUR_OF_DAY))+" "+
                                                                intFormatter.format(firstDateInDateFormat.get(Calendar.MINUTE)));

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

                                    /* Month: + 1 because 0 -> January ... 11 -> December. */                
                                    missingDates.add("Missing: "+firstDateInDateFormat.get(Calendar.YEAR)+" "+
                                                                            intFormatter.format((firstDateInDateFormat.get(Calendar.MONTH)+1))+" "+
                                                                            intFormatter.format(firstDateInDateFormat.get(Calendar.DATE))+" "+
                                                                            intFormatter.format(firstDateInDateFormat.get(Calendar.HOUR_OF_DAY))+" "+
                                                                            intFormatter.format(firstDateInDateFormat.get(Calendar.MINUTE)));
                                    missingDateFound=true;
                                    
                                }else{

                                    /* An expected date. */
                                    
                                    missingDates.add("    Unexpected date: "+secondDateInDateFormat.get(Calendar.YEAR)+" "+
                                                                            intFormatter.format((secondDateInDateFormat.get(Calendar.MONTH)+1))+" "+
                                                                            intFormatter.format(secondDateInDateFormat.get(Calendar.DATE))+" "+
                                                                            intFormatter.format(secondDateInDateFormat.get(Calendar.HOUR_OF_DAY))+" "+
                                                                            intFormatter.format(secondDateInDateFormat.get(Calendar.MINUTE)));

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
                            }

                        }else{

                            /* Ok. */
                            missingDateFound=false;
                        }
                        
                    }while(missingDateFound==true);

                }

                /* Sets first date. */
                firstDateInStringFormat = secondDateInStringFormat;
                firstDateInDateFormat = util.convertDateFromStringToCalendar(firstDateInStringFormat);

            }
            
        }
        
        return missingDates;
                
    }                                       
    
}
