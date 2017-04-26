package GUI;

/*
 * DBTable.java
 *
 * Author: Wael Abouelsaadat
 * Email:  t2aboels@cdf.toronto.edu
 * Course: CSC309
 * 
 * This program demonstrates how to add/delete tables
 * and how to insert/query data.
 *
 */
 

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBTable extends javax.swing.JPanel
implements java.io.Serializable
{ 

  /** Creates new DBTable class */
  public DBTable(  )
  {
  }
    

  public void init( ) throws Exception
  {
      String strDriver,
             strURL,
             strUserName,
             strPassword;

      strDriver = "com.pointbase.jdbc.jdbcUniversalDriver";
      Class.forName( strDriver ).newInstance( );

      // Working with "sample" database
      strURL      = "jdbc:pointbase:embedded:sample";
      strUserName = "public";  // default username
      strPassword = "public";  // defualt password
      _conn       = DriverManager.getConnection( strURL, strUserName, strPassword
);    
      _stmt = _conn.createStatement(); 
  } 

  public void create( )throws Exception
  {
     // Creating the table coffees 
      String strCreateTable = "CREATE TABLE COFFEES "  +
                              "(COF_NAME VARCHAR(32)," +
                              " SUP_ID   INTEGER,"     +
                              " PRICE    FLOAT,"       +
                              " SALES    INTEGER, "    +
                              " TOTAL    INTEGER) ";
      _stmt.executeUpdate( strCreateTable );
  } 

   public void insert( ) throws Exception
   {
     // Inserting data into table Coffees
     _stmt.executeUpdate("INSERT INTO COFFEES " +
                  "VALUES ('French_Roast', 49, 8.99, 0, 0)");
     _stmt.executeUpdate("INSERT INTO COFFEES " +
                  "VALUES ('Espresso', 150, 9.99, 0, 0)");
     _stmt.executeUpdate("INSERT INTO COFFEES " +
                  "VALUES ('Colombian_Decaf', 101, 8.99, 0, 0)");
     _stmt.executeUpdate("INSERT INTO COFFEES " +
                  "VALUES ('French_Roast_Decaf', 49, 9.99, 0, 0)");
   }

   public void query( ) throws Exception
   {
     // Retreiving data from tables
     String strQuery = "SELECT COF_NAME, PRICE FROM COFFEES";
     ResultSet rs    = _stmt.executeQuery( strQuery );
     while (rs.next())
     {
        String s = rs.getString("COF_NAME");
        float n = rs.getFloat("PRICE");
        System.out.println(s + "   " + n);
     }
     rs.close( );
   } 
 
   public void close( ) throws Exception
   {
      _stmt.close( );   
      _conn.close( );
   } 

   public void delete( ) throws Exception
   {
      // deleting the table coffees
      String strDelTable = "DROP TABLE COFFEES "; 
      _stmt.executeUpdate( strDelTable ); 
   }

   public static void main( String strarrArgs[] )
   {
      try
      {
         DBTable dbTable;

         dbTable = new DBTable( );
 
         dbTable.init( );

         dbTable.create( );

         dbTable.insert( );

         dbTable.query( );

         dbTable.delete( );

         dbTable.close( );
      }
      catch( Throwable thr )
      {
         thr.printStackTrace( );
      }
   }

     
  /* protected attributes */
 
  protected Connection  _conn;

  protected Statement   _stmt;
 
}
