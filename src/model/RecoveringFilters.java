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



/**
 * This class defines the model for applying recovering data tasks over the information of an intermediate or pre-processed dataset file.
 * 
 */
public class RecoveringFilters {
    
    
    /**
     * Instances in WEKA format.
     */
    private InstancesWeka instancesWEKA;

    
    /**
     * Number of hours to use.
     */
    private int numOfHours;

    
    /**
     * Constructor.
     */
    public RecoveringFilters(){
        
        /* Initializes to default values. */
        
        this.instancesWEKA = new InstancesWeka();
        this.numOfHours=0;

    }

    
    /**
     * Constructor.
     * @param numOfHours Number of hours to use.
     */
    public RecoveringFilters(int numOfHours){
        
        /* Initializes to default values. */
        
        this.instancesWEKA = new InstancesWeka();
        this.numOfHours=numOfHours;

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
     * Sets instances in WEKA format.
     * @param instancesWEKA Instances in WEKA format.
     */
    public void setInstancesWEKA(InstancesWeka instancesWEKA) {
        
        getInstancesWEKA().setInstances(new weka.core.Instances(instancesWEKA.getInstances()));
        
    }                       
        

    /**
     * Sets instances in WEKA format.
     * @param instancesWEKA Instances in WEKA format.
     */
    public void setInstancesWEKA(weka.core.Instances instancesWEKA) {
        
        getInstancesWEKA().setInstances(new weka.core.Instances(instancesWEKA));
        
    }                       
        
    
    /**
     * Returns number of hours.
     * @return Number of hours.
     */    
    public int getNumOfHours() {
        
        return this.numOfHours;
        
    }        
                
        
    /**
     * Gets the indexes of the attributes to check: all the attributes, excepts DATE, that don't have all their values as missing values.
     * @return Indexes of attributes to check.
     */
    private ArrayList<Integer> getIndexesOfAttributesToCheck(){
        
        /* Gets number of attributes. */
        int numAttributes = getInstancesWEKA().getNumberOfAttributes();
        
        /* Indexes of the attributes to check. */
        ArrayList<Integer> indexes = new ArrayList<>();
        
        /* Gets number of instances. */
        int numInstances = getInstancesWEKA().getNumberOfInstances();

        
        /* Checks each attribute. */
        /* DATE attribute is the first and it is not necessary to check. */
        for(int i=1;i<numAttributes;i++){
            
            /* Checks if all the values of the attribute are missing values. */
            if(getInstancesWEKA().getInstances().attributeStats(i).missingCount!=numInstances){
                
                /* The attribute has at least one non missing value. */
                indexes.add(i);
            
            }
        
        }
        
        return indexes;

    }            

        
    /**
     * Filter: Sets missing values to next hours mean.
     */
    public void missingToNextNonMissinghoursMean(){
        
        /* Gets the number of instances. */
        int numInstances=getInstancesWEKA().getNumberOfInstances();
        
        /* Gets the number of hours to use: + 1 -> because it goes from 1 to < numOfHours. */
        int numberOfHours=getNumOfHours() + 1;
        
        /* Gets indexes of attributes to check. */
        ArrayList<Integer> indexesOfAttributes = getIndexesOfAttributesToCheck();
        
        /* Checks all instances. */
        for(int i=0;i<numInstances;i++){
            
            /* Checks all attributes of every instance. */
            for(Integer j: indexesOfAttributes){
                
                if(getInstancesWEKA().getInstance(i).isMissing(j)){
                    
                    /* The attribute has a missing value. */
                    
                    double mean=0.0;
                    int numNonMissing=0;
                    
                    for(int k=1;k<numberOfHours && (i + k) < numInstances;k++){                                        
                        
                        if(!getInstancesWEKA().getInstance(i+k).isMissing(j)){
                            mean+=getInstancesWEKA().getInstance(i+k).value(j);
                            numNonMissing++;
                        }
                    }
                    
                    if(numNonMissing!=0){                
                        /* Replaces missing value with new value calculated. */
                        getInstancesWEKA().getInstance(i).setValue(j, mean/numNonMissing);
                    }
                }
            }
        }
    }
    
            
    
    /**
     * Filter: Sets missing values to previous hours mean.
     */
    public void missingToPreviousNonMissinghoursMean(){
        
        /* Gets number of instances. */
        int numInstances=getInstancesWEKA().getNumberOfInstances();
        
        /* Gets the number of hours to use: + 1 -> because it goes from 1 to < numOfHours. */
        int numberOfHours=getNumOfHours() + 1;
        
        /* Gets indexes of attributes to check. */
        ArrayList<Integer> indexesOfAttributes = getIndexesOfAttributesToCheck();
        
        /* Checks all instances. */
        for(int i=0;i<numInstances;i++){
            
            /* Checks all attributes of every instance. */
            for(Integer j: indexesOfAttributes){
                                
                if(getInstancesWEKA().getInstance(i).isMissing(j)){
                    
                    /* The attribute has a missing value. */
                    
                    double mean=0.0;
                    int numNonMissing=0;
                    
                    for(int k=1;k<numberOfHours && (i - k) >= 0;k++){
                        
                        if(!getInstancesWEKA().getInstance(i-k).isMissing(j)){
                            mean+=getInstancesWEKA().getInstance(i-k).value(j);
                            numNonMissing++;
                        }
                    }
                    
                    if(numNonMissing!=0){
                        /* Replaces missing value with new value calculated. */
                        getInstancesWEKA().getInstance(i).setValue(j, mean/numNonMissing);
                    }
                }
            }
        }
    }

    

    /**
     * Filter: Sets missing values to previous nearest non missing value.
     */
    public void missingToPreviousNearestNonMissing(){
        
        /* Gets number of instances. */
        int numInstances=getInstancesWEKA().getNumberOfInstances();
        
        /* Gets indexes of attributes to check. */
        ArrayList<Integer> indexesOfAttributes = getIndexesOfAttributesToCheck();
        
        /* Checks all instances. */
        for(int i=0;i<numInstances;i++){
            
            /* Checks all attributes of every instance. */
            for(Integer j: indexesOfAttributes){
                
                if(getInstancesWEKA().getInstance(i).isMissing(j)){
                    
                    /* The attribute has a missing value. */
                    boolean found=false;
                    
                    for(int k=1;k<6 && found==false && (i-k)>=0;k++){

                        if(!getInstancesWEKA().getInstance(i-k).isMissing(j)){
                            
                            /* Replaces missing value with new calculated. */
                            getInstancesWEKA().getInstance(i).setValue(j, getInstancesWEKA().getInstance(i-k).value(j));
                            found=true;
                        }
                    }
                }
            }
        }
    }
            
    
    
    /**
     * Filter: Sets missing values to next nearest non missing value.
     */
    public void missingToNextNearestNonMissing(){
        
        /* Gets number of instances. */
        int numInstances=getInstancesWEKA().getNumberOfInstances();
        
        /* Gets indexes of attributes to check. */
        ArrayList<Integer> indexesOfAttributes = getIndexesOfAttributesToCheck();
        
        /* Checks all instances. */
        for(int i=0;i<numInstances;i++){
            
            /* Checks all attributes of every instance. */
            for(Integer j: indexesOfAttributes){
                
                if(getInstancesWEKA().getInstance(i).isMissing(j)){
                    
                    /* The attribute has a missing value. */
                    boolean found=false;
                    
                    for(int k=1;k<6 && found==false && (i+k)<numInstances;k++){

                        if(!getInstancesWEKA().getInstance(i+k).isMissing(j)){
                            
                            /* Replaces missing value with new calculated. */
                            getInstancesWEKA().getInstance(i).setValue(j, getInstancesWEKA().getInstance(i+k).value(j));
                            found=true;
                        }
                    }
                }
            }
        }
    }

    
        
    /**
     * Filter: Sets missing values to symmetric (previous and next) hours mean.
     */
    public void missingToSymmetricNonMissinghoursMean(){
        
        /* Gets number of instances. */
        int numInstances=getInstancesWEKA().getNumberOfInstances();
        
        /* Gets the number of hours to use: + 1 -> because it goes from 1 to < numOfHours. */
        int numberOfHours=getNumOfHours() + 1;
        
        /* Gets indexes of attributes to check. */
        ArrayList<Integer> indexesOfAttributes = getIndexesOfAttributesToCheck();
        
        /* Checks all instances. */
        for(int i=0;i<numInstances;i++){
            
            /* Checks all attributes of every instance. */
            for(Integer j: indexesOfAttributes){
                                
                if(getInstancesWEKA().getInstance(i).isMissing(j)){
                    
                    /* The attribute has a missing value. */
                    
                    double mean=0.0;
                    int numNonMissing=0;
                    
                    /* Previous hours. */
                    for(int k=1;k<numberOfHours && (i - k) >= 0;k++){                    
                        
                        if(!getInstancesWEKA().getInstance(i-k).isMissing(j)){
                            mean+=getInstancesWEKA().getInstance(i-k).value(j);
                            numNonMissing++;
                        }
                    }
                    
                    /* Next hours. */
                    for(int k=1;k<numberOfHours && (i + k) < numInstances;k++){                        
                        
                        if(!getInstancesWEKA().getInstance(i+k).isMissing(j)){
                            mean+=getInstancesWEKA().getInstance(i+k).value(j);
                            numNonMissing++;
                        }
                    }

                    
                    if(numNonMissing!=0){
                        /* Replaces missing value with new value calculated. */
                        getInstancesWEKA().getInstance(i).setValue(j, mean/numNonMissing);
                    }
                }
            }
        }
    }

}
