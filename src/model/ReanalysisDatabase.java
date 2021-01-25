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

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


/**
 * This class is to manage the connection and queries with the database that stores the information of the reanalysis data files.
 * 
 */
public class ReanalysisDatabase {
    
    /**
     * Database's URL.
     */
    private String dbURL;
    
    
    /**
     * Connection established with database.
     */
    private Connection connection;

    
    /* Methods of the class */
    
    
    /**
     * Constructor.
     */
    public ReanalysisDatabase(){
        
        /* Sets database's URL. */
        setDbURL();

    }

        
    /**
     * Returns database's URL.
     * @return Database's URL.
     */
    private String getDbURL() {
        
        return dbURL;
        
    }
    
        
    /**
     * Sets database's URL.
     */
    private void setDbURL() {
        
        /* Full path to database. */
        String path = System.getProperty("user.dir", ".")
                      +File.separator+"DB"+File.separator+"reanalysisDatabase";
        
        /* 
            Database's URL.
            create=true: Creates database if does not exist.
        */
        String URL = "jdbc:derby:" + path + ";create=true;user=user;password=1234";
        
        /* Sets database's URL. */
        this.dbURL=URL;

    }
    
    
    /**
     * Returns connection established with database.
     * @return Connection established with database.
     */
    private Connection getConnection() {
        
        return connection;
        
    }


    /**
     * Sets connection established with database.
     * @param connection Connection established with database.
     */
    private void setConnection(Connection connection) {
        
        this.connection=connection;
        
    }
    
    
    /**
     * Connects to database.
     * @return True if the connection was established or False if not.
     */    
    public boolean connect() {
        
        /* To check if the connection was established. */
        boolean success;
                
        try{
            
            /* Performs connection to the database. */
            
            setConnection(DriverManager.getConnection(getDbURL()));
            
            /* The connection was established. */
            success = true;
            
        }
        catch (SQLException sqlExcept)
        {
            success = false;
            
            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);

        }
        
        /* Try again if connection failed. */
        if(success==false){
            
            try{

                /* Performs connection to the database. */

                setConnection(DriverManager.getConnection(getDbURL()));

                /* The connection was established. */
                success = true;

            }
            catch (SQLException sqlExcept)
            {
                success = false;

                Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);

            }
            
        }
        
        return success;

    }
    
    
    /**
     * Disconnects from database.
     */    
    public void disconnect()
    {
        
        /* Closes database connection. */
        
        try{
            
            if (getConnection() != null){

                /* Closes connection. */
                getConnection().close();
                setConnection(null);
                
                /* Closes database. */
                DriverManager.getConnection(getDbURL()+";shutdown=true");

            }           
            
        }
        catch (SQLException sqlExcept)
        {
            
            /* Shutdown commands always raise SQLExceptions:
                08006 SQLState
                45000 Error code
                are expected when shutdown only an individual database.
            */

            if("08006".equals(sqlExcept.getSQLState()) &&
               sqlExcept.getErrorCode()==45000){
                
                /* Database was closed succesfully. */
                
            } else {
                Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
            }

        }
    }
        
        
    /**
     * Shutdown Derby system.
     */    
    public void shutdown()
    {
        
        /* Shutdown Derby system. */
        
        try{
            
            if (getConnection() != null){
                
                /* Closes database connection. */
                disconnect();
                
            }

            /* Closes Derby system. */
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
            
        }
        catch (SQLException sqlExcept)
        {
            
            /* Shutdown commands always raise SQLExceptions:
                XJ015 SQLState
                50000 Error code
                are expected when shutdown the Derby system.
            */
            
            if("XJ015".equals(sqlExcept.getSQLState()) &&
               sqlExcept.getErrorCode()==50000){
                
                /* Derby system was closed succesfully. */
                
            } else {
                Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
            }

        }

    }    
    
    
    /**
     * Create the table REANALYSIS, if does not exist, that contains reanalysis data files's information.
     */    
    public void createTableREANALYSISifNotExists(){
        
        /* SQL statement for creating the table that contains the
           information about reanalysis data. */
        
        /* ID field is auto-increment. */
        
        String statement = "CREATE TABLE REANALYSIS("
                            + "ID int NOT NULL primary key GENERATED ALWAYS AS IDENTITY"
                            + "(START WITH 1,INCREMENT BY 1),"
                            + "FILENAME varchar(60) NOT NULL,"
                            + "VARIABLE varchar(30) NOT NULL,"
                            + "NUMINSTANCES integer NOT NULL,"
                            + "NUMGEOPOINTS integer NOT NULL,"
                            + "TIMEFROM timestamp NOT NULL,"
                            + "TIMETO timestamp NOT NULL,"
                            + "LATITUDEFROM float NOT NULL,"
                            + "LATITUDETO float NOT NULL,"
                            + "LONGITUDEFROM float NOT NULL,"
                            + "LONGITUDETO float NOT NULL)";
       
        Statement stmt;
        
        try{
            
            DatabaseMetaData dbm = getConnection().getMetaData();

            ResultSet tables;
            tables = dbm.getTables(null, null, "REANALYSIS", null);
            
            if (!tables.next()) {
                
                /* The table REANALYSIS does not exist. */

                /* Creates the table REANALYSIS. */
                stmt=getConnection().createStatement();
                stmt.execute(statement);
                stmt.close();
                
            }
            
            tables.close();

        }
        catch (SQLException sqlExcept){
            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }
    }            
    
    
    /**
     * Inserts a new reanalysis data file in database.
     * @param fileName File name.
     * @param variable Variable name.
     * @param numInstances Number of instances.
     * @param numGeopoints Number of geopoints (latitudes x longitudes).
     * @param timeFrom Time from of data contained in reanalysis data file.
     * @param timeTo Time to of data contained in reanalysis data file.
     * @param latitudeFrom Latitude from of data contained in reanalysis data file.
     * @param latitudeTo Latitude to of data contained in reanalysis data file.
     * @param longitudeFrom Longitude from of data contained in reanalysis data file.
     * @param longitudeTo Longitude to of data contained in reanalysis data file.
     * @return ID of the new reanalysis data file inserted in database or -1 in case of error.
     */      
    public int insertReanalysisFile(String fileName, String variable, int numInstances, int numGeopoints, String timeFrom, String timeTo, float latitudeFrom, float latitudeTo, float longitudeFrom, float longitudeTo){    
        
        /* SQL statement for inserting a new reanalysis file in database. */
        
        /* ID field is auto-increment. */

        String statement = "insert into REANALYSIS (FILENAME,VARIABLE,NUMINSTANCES,NUMGEOPOINTS,TIMEFROM,TIMETO,LATITUDEFROM,LATITUDETO,LONGITUDEFROM,LONGITUDETO)"
                            + " values (" + "'"+ fileName + "','" + variable + "'," + numInstances + "," + numGeopoints + ","
                            + "'" + timeFrom + "'"+ "," + "'" + timeTo + "'"+ "," + latitudeFrom +"," + latitudeTo + ","
                            + longitudeFrom + "," + longitudeTo +")";

        Statement stmt;

        /* New reanalysis data file's ID. */
        int id=-1;
        
        try{
            
            ResultSet data;
            
            /* Inserts new reanalysis data file. */
            stmt=getConnection().createStatement();
            stmt.execute(statement, Statement.RETURN_GENERATED_KEYS);
            
            /* Gets ID. */
            data=stmt.getGeneratedKeys();
            data.next();
            id = data.getInt(1);
            
            stmt.close();
        }
        catch (SQLException sqlExcept){
            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }

        return id;
    }
    

    /**
     * Deletes a reanalysis data file from database.
     * @param id ID of the reanalysis data file to delete.
     * @return True if the reanalysis data file was successfully deleted or False if not.
     */      
    public boolean deleteReanalysisFile(int id){
        
        /* SQL statement for deleting a reanalysis data file from database. */
        String statement = "delete from REANALYSIS where ID="+id;

        Statement stmt;
        
        boolean result=false;
        
        try{
                      
            /* Deletes reanalysis data file from database. */
            stmt=getConnection().createStatement();
            stmt.execute(statement);
            stmt.close();
            
            result=true;
        }
        catch (SQLException sqlExcept){
            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }
        
        return result;

    }
    
    
    /**
     * Updates a reanalysis data file in database.
     * @param fileName File name.
     * @param variable Variable name.
     * @param numInstances Number of instances.
     * @param numGeopoints Number of geopoints (latitudes x longitudes).
     * @param timeFrom Time from of data contained in reanalysis data file.
     * @param timeTo Time to of data contained in reanalysis data file.
     * @param latitudeFrom Latitude from of data contained in reanalysis data file.
     * @param latitudeTo Latitude to of data contained in reanalysis data file.
     * @param longitudeFrom Longitude from of data contained in reanalysis data file.
     * @param longitudeTo Longitude to of data contained in reanalysis data file.
     * @return True if the reanalysis file was successfully updated or False if not.
     */      
    public boolean updateReanalysisFile(String fileName, String variable, int numInstances, int numGeopoints, String timeFrom, String timeTo, float latitudeFrom, float latitudeTo, float longitudeFrom, float longitudeTo){    
        
        /* SQL statement for updating a reanalysis data file in database. */
        String statement = "update REANALYSIS set VARIABLE='" + variable + "',"
                                         + "NUMINSTANCES=" + numInstances + ","
                                         + "NUMGEOPOINTS=" + numGeopoints + ","                
                                         + "TIMEFROM='" + timeFrom + "',"
                                         + "TIMETO='" + timeTo + "',"
                                         + "LATITUDEFROM=" + latitudeFrom + ","
                                         + "LATITUDETO=" + latitudeTo + ","
                                         + "LONGITUDEFROM=" + longitudeFrom + ","
                                         + "LONGITUDETO=" + longitudeTo + "where FILENAME='"+fileName+"'";

        Statement stmt;
        
        boolean result=false;
        
        try{
                      
            /* Updates reanalysis data file information. */
            stmt=getConnection().createStatement();
            stmt.execute(statement);
            stmt.close();
            
            result=true;
        }
        catch (SQLException sqlExcept){
            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }
        
        return result;

    }
    

    /**
     * Updates reanalysis data files's information.
     * @param datamodel Reanalysis data files's information to update.
     */      
    public void selectData(DefaultTableModel datamodel){
               
        try{
            try (Statement stmt = getConnection().createStatement()) {
                
                /* To show latitude and longitude with 15 decimals and point separator. */
                DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                decimalFormat.setMaximumFractionDigits(15);
                        
                
                /* Information about each reanalysis data file. */
                int id, numInstances, numGeopoints;
                String fileName, variable;
                Timestamp timeFrom;
                Timestamp timeTo;
                float latitudeFrom, latitudeTo, longitudeFrom, longitudeTo;                                
                
                
                /* Resultset that contains the information about reanalysis data files. */
                ResultSet data;
                data = stmt.executeQuery("select * from REANALYSIS order by FILENAME,VARIABLE");
        
                try {

                    /* Reads each record from dataset. */
                    
                    while (data.next()) {
                
                        try {

                            /* Adds each record to the datamodel. */
                                                        
                            id = data.getInt("ID");
                            fileName = data.getString("FILENAME");
                            variable = data.getString("VARIABLE");
                            numInstances = data.getInt("NUMINSTANCES");
                            numGeopoints = data.getInt("NUMGEOPOINTS");
                            timeFrom=data.getTimestamp("TIMEFROM");
                            timeTo=data.getTimestamp("TIMETO");
                            latitudeFrom = data.getFloat("LATITUDEFROM");
                            latitudeTo = data.getFloat("LATITUDETO");
                            longitudeFrom = data.getFloat("LONGITUDEFROM");
                            longitudeTo = data.getFloat("LONGITUDETO");
                                                                                                        
                            datamodel.addRow(new Object[]{id, fileName, variable, numInstances, numGeopoints, timeFrom, timeTo, decimalFormat.format(latitudeFrom), decimalFormat.format(latitudeTo), decimalFormat.format(longitudeFrom), decimalFormat.format(longitudeTo)});
                    
                        } catch (SQLException ex) {
                            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }            
                } catch (SQLException sqlExcept) {
                    Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
                }
                
                /* Closes the resultset and statement. */
                data.close();
                //stmt.close(); /* auto-closeable. */
            }
        }catch (SQLException sqlExcept){
            Logger.getLogger(ReanalysisDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }

    }
    
}
