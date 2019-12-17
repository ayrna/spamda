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

import controller.ControllerViewDatasetFileConverter;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 * This interface defines the methods that the controller needs to communicate with the view DatasetFileConverter.
 * 
 */
public interface InterfaceViewDatasetFileConverter {
    
    
    /* Methods of the interface. */
    
    
    /**
     * Sets the controller that will manage each event of the view.
     * 
     * @param controller Controller that will manage each event of the view.
     */
    void setController(ControllerViewDatasetFileConverter controller);
    
    
    /**
     * Shows the view.
     */
    void showView();

    
    /**
     * Closes the view.
     */   
    void closeView();
    

    /**
     * Returns buoys's information.
     * 
     * @return Buoys's information.
     */        
    DefaultTableModel getModelBuoys();
    
    
    /**
     * Sets buoys's information.
     * 
     * @param datamodel Buoys's information.
     */            
    void setModelBuoys(DefaultTableModel datamodel);

    
    /**
     * Gets the selected row.
     * 
     * @return Selected row.
     */
    int getSelectedRowBuoy();

        
    /**
     * Sets the model of the list that shows the dataset files that belong to the selected buoy.
     * 
     * @param listmodel Model with dataset files that belong to the selected buoy.
     */    
    void setModelDatasets(DefaultListModel<String> listmodel);
    
        
    /**
     * Sets the model of the list that shows the preprocessed dataset files
     * that belong to the selected buoy and dataset.
     * 
     * @param listmodel Model with preprocessed dataset files that belong to the selected buoy and dataset.
     */        
    void setModelPreprocessedDatasets(DefaultListModel<String> listmodel);    
    
    
    /**
     * Gets the the selected index of the dataset file.
     * 
     * @return Selected index of the dataset file.
     */        
    int getSelectedIndexDataset();
    

    /**
     * Gets the the selected index of the preprocessed dataset file.
     * 
     * @return Selected index of the preprocessed dataset file.
     */        
    int getSelectedIndexPreprocessedDataset();
    
        

    /* Constants that define each event in DatasetFileConverter view. */    
    
    
    /**
     * Event: 'Convert dataset to ARFF' in DatasetFileConverter view.
     */
    static final String CONVERT_DATASET_TO_ARFF="Convert dataset to ARFF";
    
    
    /**
     * Event: 'Convert dataset to CSV' in DatasetFileConverter view.
     */    
    static final String CONVERT_DATASET_TO_CSV="Convert dataset to CSV";
    
    
    /**
     * Event: 'Convert preprocessed to ARFF' in DatasetFileConverter view.
     */    
    static final String CONVERT_PREPROCESSED_TO_ARFF="Convert preprocessed to ARFF";
    
    
    /**
     * Event: 'Convert preprocessed to CSV' in DatasetFileConverter view.
     */    
    static final String CONVERT_PREPROCESSED_TO_CSV="Convert preprocessed to CSV";
    
    
    /**
     * Event: 'Main menu' in DatasetFileConverter view.
     */    
    static final String MAIN_MENU="Main menu";
    
    
    /**
     * Event: 'Help (User manual)' in DatasetFileConverter view.
     */    
    static final String HELP="Help (User manual)";
    
}
