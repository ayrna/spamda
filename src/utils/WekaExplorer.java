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

package utils;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import weka.core.converters.ArffLoader;
import weka.gui.explorer.Explorer;


/**
 * This class is to open an ARFF file with the Explorer environment of the WEKA tool.
 * 
 */
public class WekaExplorer extends Thread{
    
    /**
     * File's name to open with Weka Explorer.
     */
    private String fileName;
    
    
    /**
     * Constructor.
     */
    public WekaExplorer(){
        
        /* Initialize to default values. */
        this.fileName="";
        
    }
        
    
    /**
     * Returns file's name.
     * @return File's name.
     */
    public String getFileName(){
        
        return fileName;
        
    }

    
    /**
     * Sets file's name to open.
     * @param fileName File's name.
     */
    public void setFileName(String fileName){
        
        this.fileName=fileName;
        
    }
    
    
    @Override
    /**
     * Runs the thread.
     */
    public void run(){
        
        /* Checks if file's name was set. */
        
        if( getFileName().isEmpty() ){
            
            /* File's name was not set. */
            
            JOptionPane.showMessageDialog(null, "Unknown file's name to open.", "Error", JOptionPane.ERROR_MESSAGE);

        }else{
            
            /* File's name was set. */
            
            try {
                
              /* Frame where showing WekaExplorer. */
              final javax.swing.JFrame frameWekaExplorer = new javax.swing.JFrame("Weka Explorer");
                            
              /* The window won't be blocked by any application-modal dialogs. */
              frameWekaExplorer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);              
                    
              /* 
                 Sets the name of the window:
                    this name will be used in Index view to check if any WekaExplorer frame is active.
              */
              frameWekaExplorer.setName("WekaExplorer");


              /* When a WekaExplorer frame is closed the name is released. */
              frameWekaExplorer.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                  
                  /* Releases the name because the frame is not active. */
                  frameWekaExplorer.setName("");
                }
              });
              
              /* WekaExplorer*/
              Explorer explorer = new Explorer();
              
              /* Loader for reading the file received. */
              ArffLoader loader = new ArffLoader();
              
              /* Sets the file to load. */
              loader.setFile(new File(getFileName()));

              /* Reads instances into explorer. */
              explorer.getPreprocessPanel().setInstancesFromFile(loader);              
              
              
              /* Adds the WekaExplorer to the frame. */
              frameWekaExplorer.getContentPane().setLayout(new BorderLayout());              
              frameWekaExplorer.getContentPane().add(explorer, BorderLayout.CENTER);              

              
              /* Shows the frame. */
              frameWekaExplorer.pack();
              frameWekaExplorer.setSize(800, 600);
              frameWekaExplorer.setVisible(true);

            } catch (IOException ex) {

              Logger.getLogger(WekaExplorer.class.getName()).log(Level.SEVERE, null, ex);
              
            }
        }
    }
    
}
