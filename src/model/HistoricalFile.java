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

import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 * This class defines the model for representing the information of an historical file of a buoy.
 * 
 */
public class HistoricalFile {
    
    
    /**
     * File's name.
     */
    private String fileName;

    
    /**
     * File's attributes.
     */
    private ArrayList<String> attributes;

    
    /**
     * File's instances.
     */    
    private ArrayList<model.Instance> instances;

    

    /* Methods of the class */
    
    
    
    /**
     * Constructor.
     */
    public HistoricalFile(){
        
        /* Initializes to default values. */
        
        this.fileName=null;
        this.attributes=new ArrayList<>();
        this.instances=new ArrayList<>();

    }
    
    
    /**
     * Gets file's name.
     * @return File's name.
     */    
    public String getFileName() {
        
        return fileName;
        
    }


    /**
     * Sets file's name.
     * @param fileName File's name.
     */    
    public void setFileName(String fileName) {
        
        this.fileName = fileName;
        
    }
               
        
    /**
     * Gets file's attributes.
     * @return File's attributes.
     */    
    public ArrayList<String> getAttributes() {
        
        return attributes;
        
    }
    
        
    /**
     * Sets file's attributes.
     * @param attributes File's attributes.
     */    
    public void setAttributes(ArrayList<String> attributes) {
        
        this.attributes=attributes;

    }
        
    
    /**
     * Adds a new attribute.
     * @param attribute Attribute's name.
     */
    public void addAttribute(String attribute){
        
        if (getAttributes().contains(attribute) == true ){
            
            /* Attribute already exists. */

            JOptionPane.showMessageDialog(null, "Attribute < "+ attribute + " > was added but already exits.", "Error", JOptionPane.ERROR_MESSAGE);
        
        }
            
        getAttributes().add(attribute);
                
    }

    
    /**
     * Gets attribute's name in given index.
     * @param index Index of attribute's name to get.
     * @return Attribute's name.
     */
    public String getAttribute(int index){
        
        return getAttributes().get(index);
        
    }
    
    
    /**
     * Modifies attribute's name in given index by new attribute's name.
     * @param index Index of attribute's name to modify.
     * @param attribute New attribute's name.
     */
    public void setAttribute(int index, String attribute){
        
        if (getAttributes().contains(attribute) == true ){
            
            /* Attribute already exists. */

            JOptionPane.showMessageDialog(null, "Attribute < "+ attribute + " > was changed to " + index + " position but already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        
        }
        
        getAttributes().set(index, attribute);
        
    }
    
    
    /**
     * Gets number of attributes.
     * @return Number of attributes.
     */
    public int numAttributes(){
        
        return getAttributes().size();
        
    }
    
    
    /**
     * Returns attribute's index of given attribute's name.
     * @param attribute Attribute's name.
     * @return Index of attribute's name.
     */
    public int indexOfAttribute(String attribute){
        
        return getAttributes().indexOf(attribute);
        
    }
    

    /**
     * Gets instances.
     * @return Instances.
     */    
    public ArrayList<model.Instance> getInstances() {
        
        return instances;
        
    }
    
        
    /**
     * Sets instances.
     * @param instances Instances.
     */    
    public void setInstances(ArrayList<model.Instance> instances) {
        
        this.instances=instances;

    }
    
    
    /**
     * Adds a new instance.
     * @param instance Instance to add.
     */
    public void addInstance(model.Instance instance){
        
        getInstances().add(instance);
        
    }
    
    
    /**
     * Adds a new instance in a given index.
     * @param index Index where adding the new instance.
     * @param instance Instance to add.
     */
    public void addInstance(int index, model.Instance instance){
        
        getInstances().add(index, instance);
        
    }
    
    
    /**
     * Modifies the instance in given index by a new instance.
     * @param index Index of the instance to modify.
     * @param instance New instance.
     */
    public void setInstance(int index, model.Instance instance){
        
        getInstances().set(index, instance);
        
    }
    
    
    /**
     * Gets instance in given index.
     * @param index Index of the instance to get.
     * @return Instance.
     */
    public model.Instance getInstance(int index){
        
        return getInstances().get(index);
        
    }
    
    
    /**
     * Deletes instance from given index.
     * @param index Index of the instance to delete.
     */
    public void delInstance(int index){
        
        getInstances().remove(index);
        
    }
    
    
    /**
     * Gets number of instances.
     * @return Number of instances.
     */
    public int numInstances(){
        
        return getInstances().size();
        
    }
    
    
    /**
     * Gets the value of an attribute that indicates that it has a missing value.
     * @param attribute Attribute's name.
     * @return The value that indicates that the attribue has a missing value.
     */
    public double getValueOfMissingValue(String attribute) {
        
        /* Value of the attribute when is has a missing value. */
        double missingValue=0;
             
        /* Checks the attribute and gets the original missing value. */
        switch (attribute) {

            case "WDIR":
                missingValue=999;
                break;

            case "WSPD":
                missingValue=99;
                break;

            case "GST":
                missingValue=99;
                break;

            case "WVHT":
                missingValue=99;
                break;

            case "DPD":
                missingValue=99;
                break;

            case "APD":
                missingValue=99;
                break;

            case "MWD":
                missingValue=999;
                break;

            case "PRES":
                missingValue=9999;
                break;

            case "ATMP":
                missingValue=999;
                break;

            case "WTMP":
                missingValue=999;
                break;

            case "DEWP":
                missingValue=999;
                break;

            case "VIS":
                missingValue=99;
                break;

            case "TIDE":
                missingValue=99;
                break;
                
            case "FLUXOFENERGY":
                missingValue=99999;
                break;                
        }
    
        return missingValue;
    }
    
}
