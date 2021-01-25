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

import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * This class defines the model for representing the information of a final dataset.
 *
 */
public class MatchingFinalDataset extends HistoricalFile {
        
    
    /**
     * Instances in WEKA format.
     */
    private InstancesWeka instancesWEKA;
    

    /**
     * Constructor.
     */
    public MatchingFinalDataset(){
        
        /* Calls MemoryFile constructor. */
        super();

        /* Initializes to default values. */
        
        this.instancesWEKA = new InstancesWeka();
        
    }
    
    
    /**
     * Constructor.
     * @param matchingDataBase Matching database to clone.
     */
    public MatchingFinalDataset(MatchingFinalDataset matchingDataBase){
        
        /* Calls MemoryFile constructor. */
        super();

        /* Initializes matching database received. */
        
        this.instancesWEKA = new InstancesWeka();
        this.instancesWEKA.setInstances(new weka.core.Instances(matchingDataBase.getInstancesWEKA().getInstances()));
        
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
     * Converts original data (attributes and instances) to Weka format.
     */
    public void converToWekaFormat(){
         
        /* Number of attributes of the database. */
        int numAttributes=numAttributes();
        
        /* Attributes of the database. */
        ArrayList<Attribute> attributesWEKA = new ArrayList<>(numAttributes);
        
        for(int i=0;i<numAttributes;i++){
                
            /* Add all the attributes of the database. */

            attributesWEKA.add(new Attribute( getAttribute(i) ));                
                
        }

        if(getInstancesWEKA().getInstances()!=null){
            
            /* Deletes all previous instances. */            
            
            getInstancesWEKA().getInstances().clear();
            
        }
        
        /* Sets new instances. */        
        getInstancesWEKA().setInstances(new Instances("relationName",attributesWEKA,0));
        
        for(model.Instance instance : getInstances()){
            
            /* Adds all instances of the database. */
            
            Instance newInstanceWEKA = new DenseInstance(numAttributes);
            
            for(int i=0;i<instance.numFieldsValues();i++){
                
                /* Adds all the fields of each instance of the database. */
                
                newInstanceWEKA.setValue(i, instance.getFieldValue(i).doubleValue());
                
            }
            
            getInstancesWEKA().getInstances().add(newInstanceWEKA);
            
        }
        
        /* Converts missing values of instances to missing values according to Weka */                
        setMissingDataToWekaFormat();        
        
    }


    /**
     * Converts missing values of one instance to missing values according to Weka.
     */
    private void setMissingDataToWekaFormat() {
        
        /* Gets the instances. */
        weka.core.Instances newInstances = getInstancesWEKA().getInstances();

        /* Gets the number of instances. */
        int numInstances = getInstancesWEKA().getNumberOfInstances();
        
        /* Gets the number of attributes. */
        int numAttributes = getInstancesWEKA().getNumberOfAttributes();
        
        /* Checks each field value in all instances. */
        for (int i = 0; i < numInstances; i++) {
            
            /* The first attribute is: TIME (it is not necessary to check). */
            for (int j = 1; j < numAttributes; j++) {
                
                /* Gets attribute's name. */               
                String attName = newInstances.get(i).attribute(j).name();
                
                /* Checks if it contains a missing value. */
                if (newInstances.get(i).value(j)!=0.0 && 
                    (newInstances.get(i).value(j)== getValueOfMissingValue(attName) || Double.isNaN(newInstances.get(i).value(j)))) {
                    
                    newInstances.get(i).setMissing(j);
                }                        

            }
        }
    }
        
}
