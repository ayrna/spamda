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

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ReanalysisFile;




/**
 * This class defines a set of useful methods that are used by different classes.
 * 
 */
public class Utils {
    
    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     */
    public Utils(){
        

    }

    
    /**
     * Searchs for the files that belong to the buoy.
     * @param idBuoy ID of the buoy.
     * @param listModel Files's name to update with each file found.
     * @param folders Directories where searching for.
     * @param pathFiles Files's path to update with each file found.
     * @param filterInfo Files name's extension to filter.
     */
    public void searchFiles(int idBuoy, DefaultListModel<String> listModel, ArrayList<String> folders, ArrayList<String> pathFiles, ArrayList<String> filterInfo){
    
        /* Search for the files that belong to the buoy. */
        
            /* Folders where searching in:
                    idBuoy
                        | -> folders received
            */
        
        /* Root where the folders are. */
        String root = System.getProperty("user.dir", ".")+File.separator+"DB"
                                  +File.separator+"id"+idBuoy+File.separator;
       
        /* Does the searching. */
        
        for(int i=0;i<folders.size();i++){
            
            /* Gets path to the folder. */
            File folder = new File(root + folders.get(i));
            
            /* Sets files name's extension filter in folder. */
            final FileNameExtensionFilter filter = new FileNameExtensionFilter(filterInfo.get(0),
                        filterInfo.get(1), filterInfo.get(2));
            
            /* Gets files in folder. */            
            File[] files = folder.listFiles(new FileFilter(){
                                      
                    @Override
                    public boolean accept(File file) {
                        return filter.accept(file);
                    } });
            
            /* Sorts files by name. */
            Arrays.sort(files);
            
            /* Updates the files's name in ListModel. */
            for (File file : files) {
                
                /* File's name. */
                listModel.addElement(file.getName());
                
                /* Path in case user wants to delete it. */
                pathFiles.add(file.toString());
                
            }
        }
    }
    
        
    /**
     * Creates a directory.
     * @param path Directory's path to create.
     * @return True if the directory was properly created or False if the process failed.
     */
    public boolean createDirectory(String path){

        /* Creates a directory. */
        
        /* Gets the directory to create. */
        File directory = new File(path);
        
        boolean result = false;
                
        /* Creates the directory if does no exist. */
        if (!directory.exists()) {

            try{
                directory.mkdir();
                result = true;
            } 
            catch(SecurityException ex){
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        
        return result;
        
    }
        

    /**
     * Deletes a directory and all its data.
     * @param path Directory's path to delete.
     */
    public void deleteDirectory(String path){

        /* Deletes recursively the directory received. */
        
        /* Gets the directory to delete. */
        File directory = new File(path);
                       
        /* Deletes the directory if exists. */
        if (directory.exists()) {
            
            /* Deletes all files/directories included in it. */
            File[] files = directory.listFiles();

            if(files!=null) {
                for(File f: files) {
                    if(f.isDirectory()) {
                        
                        /* Deletes a directory. */
                        deleteDirectory(f.toString());
                    } else {
                        
                        /* Deletes a file. */
                        f.delete();
                    }
                }
            }
            
            /* Finally deletes the empty directory. */
            directory.delete();
        }
    }
    
        
    /**
     * Checks if a directory is empty or not.
     * @param path Directory's path to check.
     * @return True if the directory is empty or False if not.
     */
    public boolean isEmptyDirectory(String path){

        /* Checks if the directory received is empty or not. */
        
        /* The directory is empty. */
        Boolean result=true;
        
        /* Gets the directory to check. */
        File directory = new File(path);
                       
        /* Checks if the directory exists. */
        if (directory.exists()) {
            
            /* Checks if it empty or not. */
            File[] files = directory.listFiles();

            if(files.length>0) {
                
                /* The directory is not empty. */
                
                result=false;
            }

        }
        
        return result;
    }
    
        
    /**
     * Checks if a directory exists or not.
     * @param path Directory's path to check.
     * @return True if the directory exists or False if not.
     */
    public boolean existsDirectory(String path){

        /* Checks if the directory received exists or not. */
        
        /* The directory exists. */
        Boolean result=true;
        
        /* Gets the directory to check. */
        File directory = new File(path);
                       
        /* Checks if the directory exists. */
        if (directory.exists() == false) {
            
            /* The directory does not exists. */
            result=false;
            
        }
        
        return result;
    }
    

    /**
     * Checks a valid file's name.
     * @param filename File's name to check.
     * @return True if the file's name is valid or False if not.
     */
    public boolean isValidFilename(String filename){

        /* Regular expression to check file's name. */        
        String regex="^["+"\\"+"w]{1,60}";
        
        /* Checks regular expression. */
        boolean result = filename.matches(regex);
        
        return result;
        
    }


    /**
     * Checks TXT file's header.
     * @param header Header to check.
     * @return True if header is correct or False if not.
     */
    public boolean isValidTXTFileHeader(String header){

        /* Header OK */
        final String headerOK="#YY  MM DD hh mm WDIR WSPD GST  WVHT   DPD   APD MWD   PRES  ATMP  WTMP  DEWP  VIS  TIDE";
        
        /* Result of checking. */
        boolean result=false;
       
        /* Checks header. */
        if (headerOK.equals(header)){
            
            /* Header is OK. */
            
            result = true;
        
        }
        
        return result;
        
    }
    
    
    /**
     * Checks the header and each line of the annual text file received.
     * @param fileToCheck File to check.
     * @return 0 if the header and each line of the received file have the expected format or the number of the wrong line in case of error.
     */
    public int isValidAnnualTextFile(File fileToCheck){

        /* The number of the wrong line (0 -> no error). */
        int numLine=1;

        /* Result of checking. */
        boolean error=false;
                
        /* File to check. */
        FileReader frFileToCheck;
        
        /* Regular expression to check each instance of the received annual text file. */
        String regex="\\d{4,4}\\s\\d{2,2}\\s\\d{2,2}\\s\\d{2,2}\\s\\d{2,2}\\s{1,}\\d{1,3}\\s{1,}\\d{1,2}.\\d\\s{1,}\\d{1,2}.\\d\\s{1,}\\d{1,2}.\\d{2,2}\\s{1,}\\d{1,2}.\\d{2,2}\\s{1,}\\d{1,2}.\\d{2,2}\\s{1,}\\d{1,3}\\s{1,}\\d{1,4}.\\d\\s{1,}\\-?\\d{1,3}.\\d\\s{1,}\\-?\\d{1,3}.\\d\\s{1,}\\-?\\d{1,3}.\\d\\s{1,}\\d{1,2}.\\d\\s{1,}\\d{1,2}.\\d{2,2}";


        /* Checks the header and each instance of the received file. */
        try {
            
            /* Opens the file */
            frFileToCheck = new FileReader(fileToCheck);
            
            try (BufferedReader brFileToChek = new BufferedReader(frFileToCheck)) {

                /* Line1 with the Header of the file. */
                String header=brFileToChek.readLine();
                
                /* Number of the current line. */
                numLine=1;
                                
                /* To check the header. */
                Utils util = new Utils();                                
                                
                if (util.isValidTXTFileHeader(header) == true){
                    
                    /* Header Ok. */
                    
                    /* Number of instances readed. */
                    int numInstances=0;
                    
                    /* Expected length of each instance. */
                    int lengthInstance=88;       

                    /* Reads the first instance. */
                    String newLine=brFileToChek.readLine();
                    
                    /* Checks all the instances. */
                    while (newLine != null && error==false) {

                        numLine = numLine + 1;
                        
                        /* # -> The line is a comment. */
                        if (!newLine.startsWith("#")){
                                                
                            /* Checks the instance. */
                            if(newLine.length()!= lengthInstance || newLine.matches(regex) == false){
                            
                                error=true;
                        
                            }else{
                                
                                /* An instance have been properly readed. */
                                numInstances=numInstances+1;
                            
                            }

                        }
                        
                        /* Reads the next instance. */
                        newLine=brFileToChek.readLine();
                                                    
                    }
                    
                    /* Checks if the file is empty. */
                    if(numInstances==0){
            
                        error=true;
            
                    }
                                        
                }else{
                    
                    error=true;
                
                }
                
                /* The file has been properly checked. */
                //brFileToChek.close();   /* auto-closeable. */
                frFileToCheck.close();

            }catch (IOException e){

                error=true;
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
                
            }                        
            
        }catch (IOException e){

            error=true;
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, e);
        }
        

        if(error==false){
            
            /* None error was found. */
            numLine=0;
        
        }
        
        return numLine;
        
    }

    
    /**
      * Converts a string with a date to seconds UNIX.
      * @param dateToConvert String with a date to convert.
      * @return Seconds Unix of received date.
      */
    public long dateToUnixSeconds(String dateToConvert) {
                
        /* Seconds obtained after conversion. */
        long seconds = 0;
        
        try {

            /* Format of the date. */
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm");
            
            /* NDBC dates are in UTC time zone. */
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            
            /* For getting correct time according to UTC time zone. */
            dateFormat.setTimeZone(timeZone);
            
            /* Gets seconds Unix. */
            Date date = dateFormat.parse(dateToConvert);
            seconds = date.getTime() / 1000;            
            
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Seconds Unix. */
        return seconds;
    }   
    
        
    /**
      * Converts UNIX seconds to a string.
      * @param unixSeconds Unix seconds to convert.
      * @return Date in 'yyyy MM dd HH mm' format.
      */
    public String unixSecondsToString(long unixSeconds) {

        /* To convert from int to String with two digits. */
        DecimalFormat intFormatter = new DecimalFormat("00");
        
        /* Format of the date. */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm");
        
        /* NDBC dates are in UTC time zone. */
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        
        /* Calendar to obtain date from Unix seconds received. */
        Calendar date=GregorianCalendar.getInstance(timeZone);
        
        /* For getting correct time according to UTC time zone. */
        dateFormat.setTimeZone(timeZone);
                       
        /* Sets Unix seconds. */
        date.setTimeInMillis(unixSeconds * 1000);


        /* String: yyyy MM dd HH mm with date format. */       
                            
                            /* yyyy */
        String stringDate = date.get(Calendar.YEAR) + " " +
                
                            /* Month is base0. */
                            /* Month: + 1 because 0 -> January ... 11 -> December. */
                
                            intFormatter.format(date.get(Calendar.MONTH) + 1) + " " +
                
                            /* dd */
                            intFormatter.format(date.get(Calendar.DAY_OF_MONTH)) + " " +
                
                            /* HH */
                            intFormatter.format(date.get(Calendar.HOUR_OF_DAY)) + " " +
                
                            /* mm */
                            intFormatter.format(date.get(Calendar.MINUTE));
        
        return stringDate;
        
    }   
    
    
    
    /**
     * Returns year-month-date-min-hour of date received.
     * @param dateToConvert Date in string format to convert.
     * @return Year-month-date-min-hour of date received.
     */
    public Calendar convertDateFromStringToCalendar(String dateToConvert){
        
        /* NDBC dates are in UTC time zone. */
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        
        /* Calendar to obtain date from instance. */
        Calendar date=GregorianCalendar.getInstance(timeZone);
        
            //2011 12 31 23 50 -> field
            //0123456789012345 -> position

        /* Year */
        int year = Integer.parseInt(dateToConvert.substring(0, 4));

        /* Month */
        int month = Integer.parseInt(dateToConvert.substring(5, 7));

        /* Day */
        int day = Integer.parseInt(dateToConvert.substring(8, 10));

        /* Hour */
        int hour = Integer.parseInt(dateToConvert.substring(11, 13));

        /* Minute */
        int minute = Integer.parseInt(dateToConvert.substring(14, 16));
                
                   /* month is base0 */
        date.set(year, (month-1), day, hour, minute, 0);
        
        return date;

    }    
    
        
    /**
      * Converts from 1800-01-01 seconds to UNIX seconds.
      * @param secondsFrom1800 Seconds from 1800-01-01 to convert.
      * @return Unix seconds.
      */
    public double secondsFrom1800ToUnix(double secondsFrom1800) {

        /* Gets seconds Unix. */
        double secondsUnix = (secondsFrom1800-1490184)*3600;

        return secondsUnix;
        
    }
    
    
    /**
      * Converts UNIX seconds to timestamp.
      * @param unixSeconds Unix seconds to convert.
      * @return Timestamp in 'yyyy-MM-dd HH:mm:ss' format.
      */
    public String unixSecondsToTimeStamp(double unixSeconds) {

        /* To convert from int to String with two digits. */
        DecimalFormat intFormatter = new DecimalFormat("00");
        
        /* Format of the date. */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm");
        
        /* ESRL dates are in UTC time zone. */
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        
        /* Calendar to obtain date from unix seconds received. */
        Calendar date=GregorianCalendar.getInstance(timeZone);
        
        /* For getting correct time according to UTC time zone. */
        dateFormat.setTimeZone(timeZone);

        /* Sets Unix seconds. */
        date.setTimeInMillis((long)unixSeconds * 1000);


        /* String: yyyy-MM dd HH mm with date format. */
                            
                            /* yyyy */
        String timeStamp = date.get(Calendar.YEAR) + "-" +
                
                            /* Month is base0. */
                            /* Month: + 1 because 0 -> January ... 11 -> December. */
                
                            intFormatter.format(date.get(Calendar.MONTH) + 1) + "-" +
                
                            /* dd */
                            intFormatter.format(date.get(Calendar.DAY_OF_MONTH)) + " " +
                
                            /* HH */
                            intFormatter.format(date.get(Calendar.HOUR_OF_DAY)) + ":" +
                
                            /* mm:ss */
                            intFormatter.format(date.get(Calendar.MINUTE)) + ":00";
                
        return timeStamp;
        
    }   
    
    
    /**
      * Compares two strings and finds last character in common.
      * @param string1 First string to compare.
      * @param string2 Second string to compare.
      * @return Index of last character in common between both strings, or -1 if both strings
      *         don't have characters in common.
      */
    public int lastCharacterInCommon(String string1, String string2) {

        /* Index of last character in common. */
        int index=-1;
        
        /* Checks if any string is null or empty. */
        if( string1 == null || string2 == null || string1.isEmpty() || string2.isEmpty() ) {
            
            /* None character in common. */
            index=-1;
            
        }else{
            
            /* Checks if both string are equal. */
            if( string1.equals(string2) ) {
            
                /* All characters in common. */
                index = string1.length()-1;
            
            }else{
            
                
                /* Finds last character in common. */

                if(string1.length() < string2.length()){
                    
                    for(int i=0;i<string1.length() && string1.charAt(i) == string2.charAt(i) ;i++) {

                        /* Character in common. */
                        index=i;

                    }                

                }else{

                    for(int i=0;i<string2.length() && string1.charAt(i) == string2.charAt(i) ;i++) {

                        /* Character in common. */
                        index=i;

                    }                

                }
                
            }

        }                                
           
        return index;
        
    }
    
    
    /**
      * Calculates number of geopoints contained in reanalysis data's files received.
      * @param reanalysisFiles Reanalysis data's files to check.
      * @return Number of geopoints if received files are compatibles or 0 it they aren't.
      */   
    public int numberOfGeopoints(ArrayList<String> reanalysisFiles){
        
        /* 
            Checks if reanalysis data's files are compatibles (have the same times and coordinates, and
            different reanalysis variable's name) and gets the number of geopoints, 
            or 0 if files are not compatibles.
        
        */
        
        /* Number of geopoints. */
        int numberOfGeopoints;
        
        /* To know if files are compatibles or not. */
        boolean compatibles = true;
                
        /* To store each reanalysis variable name. */
        ArrayList<String> reanalysisVariables = new ArrayList<>();
                        
        /* Path to reanalysis files. */
        String path = System.getProperty("user.dir", ".")
              +File.separator+"DB"+File.separator+"reanalysisFiles"+File.separator;
                

        /* 
            Gets information of the first reanalysis data file and compare it
            with remaining files.
        */
                
        ReanalysisFile rf = new ReanalysisFile();
        rf.openFile(path+reanalysisFiles.get(0));

        /* Number of instances. */
        int numInstances = rf.getSizeVariable("time");
        
        /* Reanalysis variable name. */
        reanalysisVariables.add(rf.getReanalysisVariableName());
        
            /* 
               NetCDF establishes 1800-01-01 as date base, so it is necessary to 
               convert to Unix seconds.
            */

        /* First date in file. */
        String timeFrom = unixSecondsToTimeStamp(secondsFrom1800ToUnix(rf.getFirstDataVariableTime()));        

        /* Last date in file. */
        String timeTo = unixSecondsToTimeStamp(secondsFrom1800ToUnix(rf.getLastDataVariableTime()));        
                                        
        /* Variable latitude. */
        float latitudeFrom = rf.getFirstDataVariable("lat");
        float latitudeTo = rf.getLastDataVariable("lat");

        /* Variable longitude. */
        float longitudeFrom = rf.getFirstDataVariable("lon");
        float longitudeTo = rf.getLastDataVariable("lon");
                
        /* /* Number of geopoints: in case selected reanalysis files are compatibles. */
        numberOfGeopoints = rf.getSizeVariable("lat") * rf.getSizeVariable("lon");
        
        /* Closes file. */
        rf.closeFile();
                  
        
        /* Compare the information with remaining reanalysis data files. */
                        
        for(int i=1;i<reanalysisFiles.size() && compatibles==true ;i++) {
            
            /* Opens the reanalysis file. */
            rf.openFile(path+reanalysisFiles.get(i));

            /* Checks number of instances. */
            if(numInstances != rf.getSizeVariable("time") ||
                    
                
                /* Checks reanalysis variable name. */    
                reanalysisVariables.contains(rf.getReanalysisVariableName()) ||
                    
                /* 
                   NetCDF establishes 1800-01-01 as date base, so it is necessary to 
                   convert to Unix seconds.
                */

                /* Checks time. */
                (!timeFrom.equals(unixSecondsToTimeStamp(secondsFrom1800ToUnix(rf.getFirstDataVariableTime())))) ||
                (!timeTo.equals(unixSecondsToTimeStamp(secondsFrom1800ToUnix(rf.getLastDataVariableTime())))) ||
                    
                /* Checks latitude. */
                (latitudeFrom != rf.getFirstDataVariable("lat")) ||
                (latitudeTo != rf.getLastDataVariable("lat")) ||

                /* Checks longitude. */
                (longitudeFrom != rf.getFirstDataVariable("lon")) ||
                (longitudeTo != rf.getLastDataVariable("lon"))) {
                
                    /* The information is different. */
                    compatibles=false;
            }else{
            
                /* Reanalysis file is compatible. */
                
                /* Adds reanalysis variable name. */
                reanalysisVariables.add(rf.getReanalysisVariableName());

            }
                                       
            /* Closes file. */
            rf.closeFile();

        }
        
        /* Checks if reanalysis files are compatibles. */
        if(compatibles==false){
            
            /* Received reanalysis files are not compatibles. */
            numberOfGeopoints=0;
        
        }
                
        return numberOfGeopoints;
        
    }
    
    
    /**
      * Opens the file (in PDF format) that contains the user manual.
      */   
    public void openHelpFile(){
        
        /* Opens the file that contains the user manual. */
        
        try {
            
            /* Path of the .pdf file. */
            String filename = System.getProperty("user.dir", ".")
                              +File.separator+"help"+File.separator+"userManual.pdf";
            
            /* Gets the .PDF file */
            File file = new File (filename);
            
            /* Checks if the file exists. */
            if(file.exists()==true){
                
                /* The file exists, opens the file. */
                Desktop.getDesktop().open(file);
                
            }else{
                
                /* The file does not exist. */
                JOptionPane.showMessageDialog(null, "The file "+file.getName()+" does not exist.", "Error", JOptionPane.ERROR_MESSAGE);

            }
            
        }catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }                    
    
}
