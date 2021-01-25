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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;


/**
 * This class defines the model for representing the information of an intermediate or pre-processed dataset file.
 * 
 */
public class DatasetFile extends HistoricalFile {
    
    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     */
    public DatasetFile(){
        
        /* Calls MemoryFile constructor. */
        super();
           
    }
    
        
    /**
     * Reads the intermediate or pre-processed dataset file into memory.
     * @param type Indicates if the dataset file is an intermediate or preprocessed dataset file.
     * @param headerDatasetFile Header of the dataset file to read.
     * @return True if the process was properly finished or False if the process failed.
     */
    public boolean readFile(String type, String headerDatasetFile) {
              
        boolean result;
        
        if(type.equals("Dataset")==true){            
            
            /* Intermediate dataset file. */
            result = readIntermediateDatasetFile(headerDatasetFile);
        
        }else{
                    
            /* Preprocessed dataset file. */           
            result = readPreprocessedDatasetFile(headerDatasetFile);
            
        }
                
        return result;
    }       


    /**
     * Reads the intermediate dataset file into memory.
     * @param headerDatasetFile Header of the intermediate dataset file to read.
     * @return True if the process was properly finished or False if the process failed.
     */
    private boolean readIntermediateDatasetFile(String headerDatasetFile) {
              
        boolean result = false;
        
        /* Gets the file to read. */
        File fileToRead=new File(getFileName());
                
        try {
            
            /* Opens the file and reads it into memory. */
            
            FileReader frFile = new FileReader(fileToRead);
                                    
            try (BufferedReader brFile = new BufferedReader(frFile)) {
                
                /* Header of the file. */
                String header=brFile.readLine();

                /* To check header. */
                Utils util = new Utils();
                                
                if (header.equals(headerDatasetFile) == true){        
                    
                    /* Header Ok. */

                                        
                    /* Adds the name of each attribute. */                    
                    
                    /* Header contains all attributes names. */                    
                    String [] fieldNames=header.split("[ ]+");
                    
                    /* Five first attributes (YY  MM DD hh mm) --> DATE */
                    addAttribute("DATE");
                    
                    /* Remaining attributes. */
                    /* WDIR WSPD GST  WVHT   DPD   APD MWD   PRES  ATMP  WTMP  DEWP  VIS  TIDE */
                    
                    for(int i=5;i<fieldNames.length;i++){
                        addAttribute(fieldNames[i]);
                    }

                                        
                    /* Adds all instances. */
                    
                    /* To read every instance from file. */
                    String newLine=brFile.readLine();
                    
                    while(newLine!=null){
                        
                        /* Splits newLine: one split for each attribute value. */
                        String [] newLineSplited=newLine.split("[ ]+");
                        
                        /* To store the newLine already read. */
                        model.Instance instance=new model.Instance();
                        
                        /* Five first values (YY  MM DD hh mm) --> DATE value*/
                        String date=newLineSplited[0];
                        for(int i=1;i<5;i++){
                            date+=" "+newLineSplited[i];
                        }
                        
                        instance.addFieldValue(util.dateToUnixSeconds(date));
                        
                        /* Remaining attributes values. */
                        /* WDIR WSPD GST  WVHT   DPD   APD MWD   PRES  ATMP  WTMP  DEWP  VIS  TIDE */

                        for(int i=5;i<newLineSplited.length;i++){
                            
                            instance.addFieldValue(Double.parseDouble(newLineSplited[i]));

                        }
                        
                        /* Adds the instance. */
                        addInstance(instance);
                        
                        /* Reads another instance. */
                        newLine=brFile.readLine();                    
                        
                    }
                    
                    /* The file has been read into memory. */
                    //brFile.close(); /* auto-closeable. */
                    frFile.close();
                    
                    result=true;
                        
                }        
            }catch (IOException e){

                Logger.getLogger(DatasetFile.class.getName()).log(Level.SEVERE, null, e);
                
            }
        }catch (IOException e){

            Logger.getLogger(DatasetFile.class.getName()).log(Level.SEVERE, null, e);
                
        }
        
        return result;
    }       
    
    
    /**
     * Reads the preprocessed dataset file into memory.
     * @param headerDatasetFile Header of the preprocessed dataset file to read.
     * @return True if the process was properly finished or False if the process failed.
     */
    private boolean readPreprocessedDatasetFile(String headerDatasetFile) {
              
        boolean result = false;
        
        /* Gets the file to read. */
        File fileToRead=new File(getFileName());
                
        try {
            
            /* Opens the file and reads it into memory. */
            
            FileReader frFile = new FileReader(fileToRead);
                                    
            try (BufferedReader brFile = new BufferedReader(frFile)) {
                
                /* Header of the file. */
                String header=brFile.readLine();

                /* Utilities.*/
                Utils util = new Utils();
                                
                if (header.equals(headerDatasetFile) == true){
                    
                    /* Header Ok. */

                                        
                    /* Adds the name of each attribute. */                    
                    
                    /* Header contains all attributes names. */                    
                    String [] fieldNames=header.split("[ ]+");
                    
                    /* Five first attributes (YY  MM DD hh mm) --> DATE */
                    addAttribute("DATE");
                    
                    /* Remaining attributes. */
                    /* WDIR WSPD GST  WVHT   DPD   APD MWD   PRES  ATMP  WTMP  DEWP  VIS  TIDE */
                    
                    for(int i=5;i<fieldNames.length;i++){
                        addAttribute(fieldNames[i]);
                    }

                                        
                    /* Adds all instances. */
                    
                    /* To read every instance from file. */
                    String newLine=brFile.readLine();
                    

                    while(newLine!=null){
                        
                        /* Splits newLine: one split for each attribute value. */
                        String [] newLineSplited=newLine.split("[ ]+");
                        
                        /* To store the newLine already read. */
                        model.Instance instance=new model.Instance();
                        
                        /* Five first values (YY  MM DD hh mm) --> DATE value*/
                        String date=newLineSplited[0];
                        for(int i=1;i<5;i++){
                            date+=" "+newLineSplited[i];
                        }
                        
                        instance.addFieldValue(util.dateToUnixSeconds(date));
                        
                        /* Remaining attributes values. */
                        /* WDIR WSPD GST  WVHT   DPD   APD MWD   PRES  ATMP  WTMP  DEWP  VIS  TIDE */

                        for(int i=5;i<newLineSplited.length;i++){
                            
                            instance.addFieldValue(Double.parseDouble(newLineSplited[i]));
                            
                        }
                        
                        /* Adds the instance. */
                        addInstance(instance);
                        
                        /* Reads another instance. */
                        newLine=brFile.readLine();                    
                        
                    }
                    
                    /* The file has been read into memory. */
                    //brFile.close();  /* auto-closeable. */
                    frFile.close();
                    
                    result=true;
                        
                }        
            }catch (IOException e){

                Logger.getLogger(DatasetFile.class.getName()).log(Level.SEVERE, null, e);
                
            }
        }catch (IOException e){

            Logger.getLogger(DatasetFile.class.getName()).log(Level.SEVERE, null, e);
                
        }
        
        return result;
    }       

}
