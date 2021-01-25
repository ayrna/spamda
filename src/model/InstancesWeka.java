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

package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import weka.core.Attribute;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;


/**
 * This class defines the model for representing in WEKA format a set of instances of a buoy.
 *
 */
public class InstancesWeka {
        
    
    /**
     * Instances in Weka format.
     */
    private weka.core.Instances instances;
    

    /**
     * Constructor.
     */
    public InstancesWeka(){
        
        /* Initializes to default values. */
        
        this.instances = null;
        
    }
    
    
    /* Methods of the class */
   
    
    /**
     * Returns instances.
     * @return Instances.
     */
    public weka.core.Instances getInstances() {
        
        return this.instances;
        
    }

    
    /**
     * Sets the instances.
     * @param instances Instances.
     */
    public void setInstances(weka.core.Instances instances) {
        
        this.instances=instances;
    
    }
    
    
    /**
     * Gets the instance in given index.
     * @param index Index of the instance to get.
     * @return Instance.
     */
    public weka.core.Instance getInstance(int index){
        
        return getInstances().get(index);

    }
    
    
    /**
     * Returns number of instances.
     * @return Number of instances.
     */
    public int getNumberOfInstances() {
        
        return getInstances().size();
        
    }


    /**
     * Returns number of attributes of each instance.
     * @return Number of attributes of each instance.
     */
    public int getNumberOfAttributes() {
        
        return getInstances().numAttributes();
        
    }
        
    
    /**
     * Returns the attributes of the instances.
     * @return The attributes of the instances.
     */
    public ArrayList<weka.core.Attribute> getAttributes() {
        
        /* Attributes in WEKA format. */
        ArrayList<weka.core.Attribute> attributesWEKA = new ArrayList<>();
        
        /* Number of attributes. */ 
        int numberOfAttributes = getNumberOfAttributes();
        
        for(int i=0; i<numberOfAttributes; i++){

            /* Gets the attribute. */
            
            attributesWEKA.add(getInstances().attribute(i));

        }                        
        
        return attributesWEKA;
        
    }       

    
    /**
     * Returns index of an attribute.
     * @param attributeName Name of the attribute to search.
     * @return Index of the attribute or -1 if it does not exist.
     */
    public int getIndexOfAttribute(String attributeName) {
        
        /* Index of the attribute. */
        int index=-1;
        
        /* Attributes in WEKA format. */
        ArrayList<weka.core.Attribute> attributesWEKA = getAttributes();
        
        /* Checks each attribute. */
        for(int i=0; i<attributesWEKA.size() && index==-1; i++){

            /* Checks attribute. */            
            if(attributesWEKA.get(i).name().equals(attributeName)){
                
                index=i;
            
            }                        

        }                        
        
        return index;
        
    }    

    
    /**
     * Deletes attribute's name from all instances.
     * @param name Attribute's name to delete.
     */
    public void deleteAttribute(String name){

        /* Gets the attribute to delete. */
        Attribute attributeToDelete = getInstances().attribute(name);        

        if(attributeToDelete==null){

            /* Attribute not found. */

            JOptionPane.showMessageDialog(null, "Attribute < "+ name + " > not found.", "Error", JOptionPane.ERROR_MESSAGE);

        }else{

            /* Deletes the attribute from all instances. */

            getInstances().deleteAttributeAt(attributeToDelete.index());

        }
        
    }

    
    /**
     * Sets realation's name.
     * @param relationName Relation's name.
     */
    public void setRelationName(String relationName) {
        
        /* Sets relation's name. */
        
        getInstances().setRelationName(relationName);
        
    }                       
    
   
    /**
     * Writes instances in ARFF format on disk.
     * @param filename File to write.
     * @return True if the process was properly finished or False if the process failed.
     */
    public boolean convertInstancesToARFFFile(File filename){
        
        /* Result of the process. */
        boolean result = true;
        
        try {
            
            /* To write instances in ARFF file format. */
            ArffSaver saver = new ArffSaver();
            
            /* Sets the instances. */
            saver.setInstances(getInstances());
            
            /* Sets file name. */
            saver.setFile(filename);
            
            /* Writes file on disk. */
            saver.writeBatch();
                        
        } catch (IOException ex) {
            
            /* There was an error. */
            result = false;
            
            Logger.getLogger(InstancesWeka.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
        
    }
    
    
    /**
     * Writes instances in CSV format on disk.
     * @param filename File to write.
     * @return True if the process was properly finished or False if the process failed.
     */
    public boolean convertInstancesToCSVFile(File filename){
        
        /* Result of the process. */
        boolean result = true;       
        
        try {
            
            /* To write instances in CSV file format. */
            CSVSaver saver = new CSVSaver();
            
            /* Sets the instances. */
            saver.setInstances(getInstances());
            
            /* Sets file name. */
            saver.setFile(filename);
            
            /* Writes file on disk. */
            saver.writeBatch();
                        
        } catch (IOException ex) {
            
            /* There was an error. */
            result = false;           
            
            Logger.getLogger(InstancesWeka.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

}