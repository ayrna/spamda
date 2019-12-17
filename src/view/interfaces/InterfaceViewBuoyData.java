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

import controller.ControllerViewBuoyData;
import javax.swing.DefaultListModel;


/**
 * This interface defines the methods that the controller needs to communicate with the view BuoyData.
 * 
 */
public interface InterfaceViewBuoyData {
    
    
    /* Methods of the interface. */
        
    
    /**
     * Sets the controller that will manage each event of the view.
     * 
     * @param controller Controller that will manage each event of the view.
     */
    void setController(ControllerViewBuoyData controller);
    
    
    /**
     * Shows the view.
     * 
     * @param title Title of the view.
     */
    void showView(String title);

    
    /**
     * Closes the view.
     */   
    void closeView();
    
    
    /**
     * Gets the name of the buoy.
     * 
     * @return Name of the buoy.
     */    
    String getBuoyStationID();
    
    
    /**
     * Sets the name of the buoy.
     * 
     * @param name Name of the buoy.
     */            
    void setBuoyStationID(String name);    

    
    /**
     * Gets the description of the buoy.
     * 
     * @return Description of the buoy.
     */            
    String getBuoyDescription();
    
    
    /**
     * Sets the description of the buoy.
     * 
     * @param description Description of the buoy.
     */                
    void setBuoyDescription(String description);    
    
    
    /**
     * Gets the latitude of the buoy.
     * 
     * @return Latitude of the buoy.
     */                
    String getBuoyLatitude();
        
    
    /**
     * Sets the latitude of the buoy.
     * 
     * @param latitude Latitude of the buoy.
     */                        
    void setBuoyLatitude(String latitude);
    
    
    /**
     * Gets the hemisphere North / South of the buoy.
     * 
     * @return Hemisphere North / South of the buoy.
     */
    String getBuoyLatitudeNS();
    
        
    /**
     * Sets the hemisphere North / South of the buoy.
     * 
     * @param NS Hemisphere North / South of the buoy.
     */    
    void setBuoyLatitudeNS(Object NS);
         

    /**
     * Gets the longitude of the buoy.
     * 
     * @return Longitude of the buoy.
     */                    
    String getBuoyLongitude();
    
    
    /**
     * Sets the longitude of the buoy.
     * 
     * @param longitude Longitude of the buoy.
     */
    void setBuoyLongitude(String longitude);
    
        
    /**
     * Gets the hemisphere East / West of the buoy.
     * 
     * @return Hemisphere East / West of the buoy.
     */            
    String getBuoyLongitudeEW();
    
    
    /**
     * Sets the hemisphere East / West of the buoy.
     * 
     * @param EW Hemisphere East / West of the buoy.
     */
    void setBuoyLongitudeEW(Object EW);   
        
        
    /**
     * Clears the content of every field in form.
     */
    void clearFields();
    
    
    /**
     * Sets focus on default component in form.
     */        
    void setDefaultFocus();

        
    /**
     * Shows the files that belong to the buoy.
     * 
     * @param listmodel Files that belong to the buoy.
     */    
    void setListModel(DefaultListModel<String> listmodel);
        
        
    /**
     * Gets the selected file to delete.
     * 
     * @return Selected file to delete.
     */    
    int getSelectedIndex();
    
    
    /**
     * Sets the notations of coordinates typed by user.
     * 
     * @param notations Notations of coordinates typed by user.
     */        
    void setCoordinatesNotations(String notations);
    

    
    /* Constants that define each event in BuoyData view. */    
    
    
    /**
     * Event: 'Save' in BuoyData view.
     */
    static final String SAVE_BUOY="Save";
    
    
    /**
     * Event: 'Clear' in BuoyData view.
     */
    static final String CLEAR_BUOY="Clear";
    
    
    /**
     * Event: 'Cancel' in BuoyData view.
     */
    static final String CANCEL="Cancel";
    
    
    /**
     * Event: 'Add file' in BuoyData view.
     */
    static final String ADD_TXT_FILE="Add file";
    
    
    /**
     * Event: 'Delete file' in BuoyData view.
     */
    static final String DELETE_TXT_FILE="Delete file";
    
    
    /**
     * Event: 'Change latitude notation' in BuoyData view.
     */
    static final String CHANGE_LATITUDE_NOTATION="Change latitude notation";
    
    
    /**
     * Event: 'Change longitude notation' in BuoyData view.
     */
    static final String CHANGE_LONGITUDE_NOTATION="Change longitude notation";
    
    
    /**
     * Event: 'Help (User manual)' in BuoyData view.
     */    
    static final String HELP="Help (User manual)";
    
}
