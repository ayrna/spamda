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


/**
 * This class defines the model for representing an instance or set of measurements collected by a buoy in a specific date and time.
 * 
 */
public class Instance {

    
    /**
     * Instance fields's values.
     */
    private ArrayList<Number> fieldsValues;

    
    /* Methods of the class */

    
    /**
     * Constructor.
     */
    public Instance(){

        /* Initializes to default values. */
        
        this.fieldsValues=new ArrayList<>();
        
    }
    
    
    /**
     * Returns instance fields's values.
     * @return Instance fields's values.
     */    
    public ArrayList<Number> getFieldsValues() {
        
        return fieldsValues;
        
    }


    /**
     * Sets instance fields's values.
     * @param fieldsValues Instance fields's values.
     */    
    public void setFieldsValues(ArrayList<Number> fieldsValues) {
        
        this.fieldsValues=fieldsValues;

    }
    

    /**
     * Returns number of fields's values.
     * @return Number of fields's values.
     */
    public int numFieldsValues(){
        
        return getFieldsValues().size();        
        
    }
    
    
    /**
     * Adds a new field's value in given index.
     * @param index Index where adding the new field value.
     * @param fieldValue Field's value to add.
     */
    public void addFieldValue(int index, Number fieldValue){
        
        getFieldsValues().add(index, fieldValue);

    }
    

    /**
     * Adds a new field's value.
     * @param fieldValue Field's value to add.
     */
    public void addFieldValue(Number fieldValue){
        
        getFieldsValues().add(fieldValue);
        
    }
    

    /**
     * Gets field's value in given index.
     * @param index Index of field's value to get.
     * @return Field's value.
     */
    public Number getFieldValue(int index){
        
        return getFieldsValues().get(index);
        
    }
    
    
    /**
     * Modifies the field's value in given index by a new value.
     * @param index Index of the field to modify.
     * @param fieldValue New field's value.
     */
    public void setFieldValue(int index, Number fieldValue){
        
        getFieldsValues().set(index, fieldValue);
        
    }
    

    /**
     * Deletes a field's value from given index.
     * @param index Index of field's value to delete.
     */
    public void deleteFieldValue(int index){
        
        getFieldsValues().remove(index);
        
    }
    

    /**
     * Deletes the first occurrence of the given field's value.
     * @param fieldValue Field's value to delete (only first occurrence).
     */
    public void delField(Number fieldValue){
        
        getFieldsValues().remove(fieldValue);

    }
    
}
