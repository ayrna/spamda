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

package controller;

import view.interfaces.InterfaceViewAbout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 * This class defines the controller for managing the events generated by the view About.
 * 
 */
public class ControllerViewAbout implements ActionListener {
    
    /**
     * View.
     */
    private final InterfaceViewAbout view;
    
    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     * @param view View that this controller will manage.
     */
    public ControllerViewAbout(InterfaceViewAbout view){

        /* Sets View */        
        this.view = view;

    }

    
    /**
     * Returns view.
     * @return View.
     */
    private InterfaceViewAbout getView(){
        
        return view;
        
    }
    
    
    /**
     * Manages events generated in the view.
     * @param event Event generated in the view.
     */
    @Override
    public void actionPerformed(ActionEvent event){

        /* Manage the event generated in the view. */
        
        /* gets the event name.*/
        String eventName = event.getActionCommand();
        
        switch (eventName) {                
    
            case InterfaceViewAbout.WEB_SITE_UCO:
                
                /* Action WEB_SITE_UCO clicked. */
                doOpenWebSite(getView().getWebSiteUCO());
                break;
                
            case InterfaceViewAbout.WEB_SITE_DIAN:
                
                /* Action WEB_SITE_DIAN clicked. */
                doOpenWebSite(getView().getWebSiteDIAN());
                break;
                
            case InterfaceViewAbout.WEB_SITE_AYRNA_GROUP:
                
                /* Action WEB_SITE_UCO clicked. */
                doOpenWebSite(getView().getWebSiteAYRNAGroup());
                break;

            default:
                
                /* Event not defined. */               
                JOptionPane.showMessageDialog(null, "Event not defined:" + eventName + ".", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

    }
    
    
    /* Methods for processing each event (what user clicked on view). */    
    
    
    /**
     * Launchs web browser to open website received.
     * @param webSite Website to open.
     */        
    private void doOpenWebSite(String webSite){
        
        /* Launchs web browser to open website received. */
        
        /* Gets web site to open. */
        
        try {
            
            /* Launchs web browser. */
            Desktop.getDesktop().browse(new URI(webSite));
            
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ControllerViewAbout.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
