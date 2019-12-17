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

import controller.ControllerViewSelectBuoyAttributes;
import javax.swing.table.DefaultTableModel;

/**
 * This interface defines the methods that the controller needs to communicate with the view SelectBuoyAttributes.
 * 
 */
public interface InterfaceViewSelectBuoyAttributes {
    
    
    /* Methods of the interface. */
    
    
    /**
     * Sets the controller that will manage each event of the view.
     * 
     * @param controller Controller that will manage each event of the view.
     */
    void setController(ControllerViewSelectBuoyAttributes controller);

    
    /**
     * Shows the view.
     */
    void showView();


    /**
     * Closes the view.
     */   
    void closeView();
    
    
    /**
     * Returns information of the attributes of the buoy.
     * 
     * @return Information of the attributes of the buoy.
     */                
    DefaultTableModel getModelBuoyAttributes();
    
    
    /**
     * Sets the information of the attributes of the buoy.
     * 
     * @param datamodel Information of the attributes of the buoy.
     */    
    void setModelBuoyAttributes(DefaultTableModel datamodel);
            
    
    /**
     * Repaints the table that shows the attributes of the buoy.
     */                
    void tblBuoyAttributesRepaint();
    
    
    /**
     * Gets the selected row.
     * 
     * @return Selected row.
     */
    int getSelectedRow();

    
    /**
     * Gets the selected column.
     * 
     * @return Selected column.
     */
    int getSelectedColumn();
            
    
    
    /* Constants that define each event in SelectBuoyAttributes view. */
    
    /**
     * Event: 'Include all' in SelectBuoyAttributes view.
     */
    static final String SELECT_ALL="Select all";
    
    
    /**
     * Event: 'Clear selection' in SelectBuoyAttributes view.
     */    
    static final String CLEAR_SELECTION="Clear selection";
    
    
    /**
     * Event: 'Confirm selection' in SelectBuoyAttributes view.
     */    
    static final String CONFIRM_SELECTION="Confirm selection";
    
    
    /**
     * Event: 'Back' in SelectBuoyAttributes view.
     */    
    static final String BACK="Back";
    
    
    /**
     * Event: 'Help (User manual)' in SelectBuoyAttributes view.
     */    
    static final String HELP="Help (User manual)";
    
}
