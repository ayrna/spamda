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

package view.interfaces;

import controller.ControllerViewOpenDatasetFile;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 * This interface defines the methods that the controller needs to communicate with the view OpenDatasetFile.
 * 
 */
public interface InterfaceViewOpenDatasetFile {

    
    /* Methods of the interface. */

    
    /**
     * Sets the controller that will manage each event of the view.
     * 
     * @param controller Controller that will manage each event of the view.
     */
    void setController(ControllerViewOpenDatasetFile controller);

    
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
     * Shows the dataset files that belong to the selected buoy.
     * 
     * @param listmodel Dataset files that belong to the selected buoy.
     */
    void setModelDatasets(DefaultListModel<String> listmodel);
    
    
    /**
     * Shows the preprocessed dataset files that belong to the selected buoy and dataset file.
     * 
     * @param listmodel Preprocessed dataset files that belong to the selected buoy dataset file.
     */
    void setModelPreprocessedDatasets(DefaultListModel<String> listmodel);    
    
    
    /**
     * Gets selected dataset file.
     * 
     * @return Selected dataset file.
     */        
    int getSelectedIndexDataset();
    
    
    /**
     * Gets selected preprocessed dataset file.
     * 
     * @return Selected preprocessed dataset file.
     */        
    int getSelectedIndexPreprocessedDatasets();
    
        

    /* Constants that define each event in OpenDatasetFile view. */    
    
    
    /**
     * Event: 'Open' in OpenDatasetFile view.
     */    
    static final String OPEN_DATASET="Open";
    
    
    /**
     * Event: 'Back' in OpenDatasetFile view.
     */    
    static final String BACK="Back";
    
    
    /**
     * Event: 'Help (User manual)' in OpenDatasetFile view.
     */    
    static final String HELP="Help (User manual)";
    
}
