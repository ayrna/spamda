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
 * This class defines the information that is saved in the XML file associated
 * to each intermediate or pre-processed dataset file created.
 * 
 */

@XmlRootElement
public class DatasetInformation {
    
    
    /**
    * This class defines the information collected for a statistical metric
    * of each attribute of a intermediate dataset or pre-processed dataset file.
    * 
    * @author Antonio Manuel Gómez Orellana
    */
    public static class Statistic{
        
        /**
         * Statistic's name.
         */
        private String name;
        
        
        /**
         * Statistic's value.
         */
        private double value;

        
        /**
         * Constructor.
         */
        public Statistic(){
        
            /* Initializes to default values. */
            
            this.name=null;
            this.value=0.0;
        }


        /**
         * Sets statistic's name.
         * @param name Statistic's name.
         */
        @XmlElement
        public void setName(String name){
            
            this.name = name;
        
        }

        
        /**
         * Gets statistic's name.
         * @return Statistic's name.
         */
        public String getName(){
            
            return this.name;
        
        }

        
        /**
         * Sets statistic's value.
         * @param value Statistic's value.
         */
        @XmlElement
        public void setValue(Double value){
            
            this.value = value;
        
        }


        /**
         * Gets statistic's value.
         * @return Statistic's value.
         */
        public Double getValue(){
            
            return this.value;
        
        }

    }

    
    /**
    * This class defines a set of statistical metrics of an attribute
    * of a intermediate dataset or pre-processed dataset file.
    * 
    * @author Antonio Manuel Gómez Orellana
    */   
    public static class AttributeStatistics{
        
        /**
         * Attribute's name.
         */
        private String attributeName;
        
        
        /**
         * Attribute's statistics.
         */
        private ArrayList<Statistic> attributeStatistics;
        
        
        /**
         * Constructor.
         */
        public AttributeStatistics(){
            
            /* Initializes to default values. */
            
            this.attributeName = null;
            this.attributeStatistics = new ArrayList<>();        
        }
        
        
        /**
         * Sets attribute's name.
         * @param attributeName Attribute's name.
         */
        @XmlElement
        public void setAttributeName(String attributeName){
            
            this.attributeName = attributeName;
        
        }


        /**
         * Gets attribute's name.
         * @return Attribute's name.
         */
        public String getAttributeName(){
            
            return this.attributeName;
        
        }
        
        
        /**
         * Sets attribute's statistics.
         * @param attributeStatistics Attribute's statistics.
         */
        @XmlElement
        public void setAttributeStatistics(ArrayList<Statistic> attributeStatistics){
            
            this.attributeStatistics = attributeStatistics;
        
        }        
        
        
        /**
         * Gets attribute's statistics.
         * @return Attribute's statistics.
         */
        public ArrayList<Statistic> getAttributeStatistics(){
            
            return this.attributeStatistics;
        
        }
                        
    }
    
    
    /**
     * Dataset file's name.
     */
    private String datasetFileName;
    
    
    /**
     * Dataset file's header.
     */
    private String datasetFileHeader;
    
    
    /**
     * Creation date.
     */
    private Calendar creationDate;
    
    
    /**
     * TXT files merged in dataset file.
     */
    private ArrayList<String> txtFiles;
    
    
    /**
     * Missing or duplicated dates found.
     */
    private ArrayList<String> missingDates;
    
    
    /**
     * Short description typed by user.
     */
    private String shortDescription;
    
    
    /**
     * First date in dataset file.
     */
    private String firstDate;
    
    
    /**
     * Last date in dataset file.
     */
    private String lastDate;
    
    
    /**
     * Number of instances in dataset file.
     */
    private int numInstances;
    
    
    /**
     * Statistics of each attribute in dataset file.
     */
    private ArrayList<AttributeStatistics> statistics;
                                      
    
    /**
     * Preprocessing applied to dataset file.
     */
    private ArrayList<String> preprocessing;
  

    /**
     * Constructor.
     */
    public DatasetInformation(){

        /* Initializes to default values. */
        
        this.datasetFileName = null;
        this.datasetFileHeader = null;
        this.creationDate = null;
        this.txtFiles = new ArrayList<>();
        this.missingDates = new ArrayList<>();
        this.shortDescription = "";
        this.firstDate=null;
        this.lastDate=null;
        this.numInstances=0;        
        this.statistics = new ArrayList<>();
        this.preprocessing = new ArrayList<>();
        
    }
    
    
    /**
     * Sets dataset file's name.
     * @param datasetFileName Dataset file's name.
     */    
    @XmlAttribute
    public void setFileNameDataset(String datasetFileName) {
        
        this.datasetFileName = datasetFileName;
    }


    /**
     * Returns dataset file's name.
     * @return Dataset file's name.
     */
    public String getFileNameDataset() {
        
        return this.datasetFileName;
    }
    
    
    /**
     * Sets dataset file's header.
     * @param datasetFileHeader Dataset file's header.
     */    
    @XmlAttribute
    public void setHeaderDatasetFile(String datasetFileHeader) {
        
        this.datasetFileHeader = datasetFileHeader;
    }


    /**
     * Returns dataset file's header.
     * @return Dataset file's header.
     */
    public String getHeaderDatasetFile() {
        
        return this.datasetFileHeader;
    }
    

    /**
     * Sets date when dataset file was created.
     * @param dateCreation Date when dataset file was created.
     */    
    @XmlElement
    public void setDateCreation(Calendar dateCreation) {
        
        this.creationDate = dateCreation;
    }

    
    /**
     * Returns date when dataset file was created.
     * @return Date when dataset file was created.
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
     * Sets statistics of each attribute.
     * @param statistics Statistics of each attribute.
     */       
    @XmlElement
    public void setStatistics(ArrayList<AttributeStatistics> statistics) {
        
        this.statistics = statistics;
    }

    
    /**
     * Returns statistics of each attribute.
     * @return Statistics of each attribute.
     */     
    public ArrayList<AttributeStatistics> getStatistics() {
        
        return this.statistics;
    }
    
    
    /**
     * Sets TXT files merged in dataset file.
     * @param txtFiles TXT files merged in dataset file.
     */       
    @XmlElement
    public void setTXTFiles(ArrayList<String> txtFiles) {
        
        this.txtFiles = txtFiles;
    }

    
    /**
     * Returns TXT files merged in dataset file.
     * @return TXT files merged in dataset file.
     */     
    public ArrayList<String> getTXTFiles() {
        
        return this.txtFiles;
    }
    
    
    /**
     * Sets missing or duplicated dates found.
     * @param missingDates Missing or duplicated dates found.
     */       
    @XmlElement
    public void setMissingDates(ArrayList<String> missingDates) {
        
        this.missingDates = missingDates;
    }

    
    /**
     * Returns missing or duplicated dates found.
     * @return Missing or duplicated dates found.
     */     
    public ArrayList<String> getMissingDates() {
        
        return this.missingDates;
    }
    

    /**
     * Sets preprocessing applied to dataset file.
     * @param preprocessing Preprocessing applied to dataset file.
     */       
    @XmlElement
    public void setPreprocessing(ArrayList<String> preprocessing) {
        
        this.preprocessing = preprocessing;
    }

    
    /**
     * Returns preprocessing applied to dataset file.
     * @return Preprocessing applied to dataset file.
     */     
    public ArrayList<String> getPreprocessing() {
        
        return this.preprocessing;
    }
    
    
    /**
     * Sets number of instances in dataset file.
     * @param numInstances Number of instances in dataset file.
     */       
    @XmlElement
    public void setNumInstances(int numInstances) {
        
        this.numInstances = numInstances;
    }

    
    /**
     * Returns number of instances in dataset file.
     * @return Number of instances in dataset file.
     */     
    public int getNumInstances() {
        
        return this.numInstances;
    }

    
    /**
     * Sets first date in dataset file.
     * @param firstDate First date in dataset file.
     */       
    @XmlElement
    public void setFirstDate(String firstDate) {
        
        this.firstDate = firstDate;
    }

    
    /**
     * Returns first date in dataset file.
     * @return First date in dataset file.
     */     
    public String getFirstDate() {
        
        return this.firstDate;
    }
    

    /**
     * Sets last date in dataset file.
     * @param lastDate Last date in dataset file.
     */       
    @XmlElement
    public void setLastDate(String lastDate) {
        
        this.lastDate = lastDate;
    }

    
    /**
     * Returns last date in dataset file.
     * @return Last date in dataset file.
     */     
    public String getLastDate() {
        
        return this.lastDate;
    }
    
}

