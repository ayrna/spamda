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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;


/**
 * This class is to manage the connection and queries with the database that stores the information of the buoys.
 * 
 */
public class BuoyDatabase {
    
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
    public BuoyDatabase(){
        
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
                      +File.separator+"DB"+File.separator+"buoysDatabase";
        
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
            
            /* Performs connection to database. */
            
            setConnection(DriverManager.getConnection(getDbURL()));
            
            /* The connection was established. */
            success = true;
            
        }
        catch (SQLException sqlExcept)
        {
            success = false;
            
            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);

        }
        
        /* Try again if connection failed. */
        if(success==false){
            
            try{

                /* Performs connection to database. */

                setConnection(DriverManager.getConnection(getDbURL()));

                /* The connection was established. */
                success = true;

            }
            catch (SQLException sqlExcept)
            {
                success = false;

                Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);

            }
        
        }
        
        return success;

    }
    
    
    /**
     * Disconnects from database.
     */    
    public void disconnect()
    {
        
        /* Closes database's connection. */
        
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
                Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
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
                Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
            }

        }

    }    
    
    
    /**
     * Creates the table BUOY, if does not exist, that contains buoys's information.
     */    
    public void createTableBUOYifNotExists(){
        
        /* SQL statement for creating the table that contains the
           information about buoys. */
        
        /* ID field is auto-increment. */
        
        String statement = "CREATE TABLE BUOY("
                            + "ID int NOT NULL primary key GENERATED ALWAYS AS IDENTITY"
                            + "(START WITH 1,INCREMENT BY 1),"
                            + "STATIONID varchar(20) NOT NULL,"                
                            + "DESCRIPTION varchar(60) NOT NULL,"                
                            + "LATITUDE double NOT NULL,"
                            + "LATITUDENS varchar(1) NOT NULL,"
                            + "LONGITUDE double NOT NULL,"
                            + "LONGITUDEWE varchar(1) NOT NULL,"
                            + "TXTNUMFILES integer NOT NULL)";
       
        Statement stmt;
        
        try{
            
            DatabaseMetaData dbm = getConnection().getMetaData();

            ResultSet tables;
            tables = dbm.getTables(null, null, "BUOY", null);
            
            if (!tables.next()) {
                
                /* The table BUOY does not exist. */

                /* Creates the table BUOY. */
                stmt=getConnection().createStatement();
                stmt.execute(statement);
                stmt.close();
                
            }
            
            tables.close();

        }
        catch (SQLException sqlExcept){
            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }
    }
    
    
    /**
     * Inserts a new buoy in database.
     * @param stationid StationID of the buoy.
     * @param description Litle description about the buoy.
     * @param latitude Latitude of the buoy.
     * @param latitudeNS Hemisphere: North / South
     * @param longitude Longitude of the buoy.
     * @param longitudeWE Hemisphere: West / East
     * @param txtnumfiles Number of TXT files added to the buoy.
     * @return ID of the new buoy inserted in database or -1 in case of error.
     */      
    public int insertBuoy(String stationid, String description, double latitude, String latitudeNS, double longitude, String longitudeWE, int txtnumfiles){
        
        /* SQL statement for inserting a new buoy in database. */
        /* ID field is auto-increment. */       
        String statement = "insert into BUOY (STATIONID,DESCRIPTION,LATITUDE,LATITUDENS,LONGITUDE,LONGITUDEWE,TXTNUMFILES)"
                            + " values (" + "'"+ stationid + "','" + description + "',"
                            + latitude +",'" + latitudeNS + "'," + longitude + ",'" + longitudeWE + "',"+ txtnumfiles +")";

        Statement stmt;

        /* New buoy's ID. */
        int id=-1;
        
        try{
            
            ResultSet data;
            
            /* Inserts new buoy. */
            stmt=getConnection().createStatement();
            stmt.execute(statement, Statement.RETURN_GENERATED_KEYS);
            
            /* Gets ID. */
            data=stmt.getGeneratedKeys();
            data.next();
            id = data.getInt(1);
            
            stmt.close();
        }
        catch (SQLException sqlExcept){
            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }

        return id;
    }
    
        
    /**
     * Updates a buoy in database.
     * @param id ID of the buoy.
     * @param stationid StationID of the buoy.
     * @param description Litle description about the buoy.
     * @param latitude Latitude of the buoy.
     * @param latitudeNS Hemisphere: North / South
     * @param longitude Longitude of the buoy.
     * @param longitudeWE Hemisphere: West / East
     * @param txtnumfiles Number of TXT files added to the buoy.
     * @return True if the buoy was successfully updated or False if not.
     */      
    public boolean updateBuoy(int id, String stationid, String description, double latitude, String latitudeNS, double longitude, String longitudeWE, int txtnumfiles){
        
        /* SQL statement for updating a buoy in database. */
        String statement = "update BUOY set STATIONID='" + stationid + "',"
                                         + "DESCRIPTION='" + description + "',"
                                         + "LATITUDE=" + latitude + ","
                                         + "LATITUDENS='" + latitudeNS + "',"
                                         + "LONGITUDE=" + longitude + ","
                                         + "LONGITUDEWE='" + longitudeWE + "',"
                                         + "TXTNUMFILES=" + txtnumfiles + "where ID="+id;

        Statement stmt;
        
        boolean result=false;
        
        try{
                      
            /* Updates buoy information. */
            stmt=getConnection().createStatement();
            stmt.execute(statement);
            stmt.close();
            
            result=true;
        }
        catch (SQLException sqlExcept){
            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }
        
        return result;

    }
   

    /**
     * Deletes a buoy from database.
     * @param id ID of the buoy to delete.
     * @return True if the buoy was successfully deleted or False if not.
     */      
    public boolean deleteBuoy(int id){
        
        /* SQL statement for deleting a buoy from database. */
        String statement = "delete from BUOY where ID="+id;

        Statement stmt;
        
        boolean result=false;
        
        try{
                      
            /* Deletes buoy from database. */
            stmt=getConnection().createStatement();
            stmt.execute(statement);
            stmt.close();
            
            result=true;
        }
        catch (SQLException sqlExcept){
            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }
        
        return result;

    }

    
    /**
     * Updates buoys's information.
     * @param datamodel Buoys's information to update.
     */
    public void selectData(DefaultTableModel datamodel){
               
        try{
            try (Statement stmt = getConnection().createStatement()) {
                
                /* To show latitude and longitude with 15 decimals and point separator. */
                DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                decimalFormat.setMaximumFractionDigits(15);
                        
                /* Information about each buoy. */
                int id, txtnumfiles;
                String stationid, description, latitudeNS, longitudeWE;
                double latitude, longitude;                
                
                /* Resultset that contains buoys's information. */
                ResultSet data;
                data = stmt.executeQuery("select * from BUOY order by STATIONID");
        
                try {

                    /* Reads each record from dataset. */
                    
                    while (data.next()) {
                
                        try {

                            /* Adds each record to the datamodel. */
                            
                            id = data.getInt("ID");
                            stationid = data.getString("STATIONID");
                            description = data.getString("DESCRIPTION");
                            latitude = data.getDouble("LATITUDE");
                            latitudeNS = data.getString("LATITUDENS");
                            longitude = data.getDouble("LONGITUDE");
                            longitudeWE = data.getString("LONGITUDEWE");
                            txtnumfiles = data.getInt("TXTNUMFILES");
                                                                            
                            datamodel.addRow(new Object[]{id, stationid, description, decimalFormat.format(latitude), latitudeNS, decimalFormat.format(longitude), longitudeWE, txtnumfiles});
                    
                        } catch (SQLException ex) {
                            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }            
                } catch (SQLException sqlExcept) {
                    Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
                }
                
                /* Closes the resultset and statement. */
                data.close();
                //stmt.close(); //auto-closeable.
            }
        }catch (SQLException sqlExcept){
            Logger.getLogger(BuoyDatabase.class.getName()).log(Level.SEVERE, null, sqlExcept);
        }

    }
    
}
