/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metadatos;

import datos.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import oracle.jdbc.OracleResultSetMetaData;

/**
 *
 * @author rperez
 */
public class TestMetadatos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = Conexion.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM employees");
            
            OracleResultSetMetaData oracleResultSetMetaData = (OracleResultSetMetaData)resultSet.getMetaData();
            
            if(oracleResultSetMetaData != null){
                int columnCount = oracleResultSetMetaData.getColumnCount();
                System.out.println("No. columnas " + columnCount);
                
                for (int i = 1; i <= columnCount; i++) {
                    
                    System.out.println("Nombre de columna " + oracleResultSetMetaData.getColumnName(i)); 
                    System.out.println("Tipo de columna " + oracleResultSetMetaData.getColumnTypeName(i));
                    
                    switch(oracleResultSetMetaData.isNullable(i)){
                        case OracleResultSetMetaData.columnNoNulls:
                            System.out.println("No acepta nulos");
                            break;
                        case OracleResultSetMetaData.columnNullable:
                            System.out.println("Si acepta nulos");
                            break;
                        case OracleResultSetMetaData.columnNullableUnknown:
                            System.out.println("No se sabe");
                            break;
                    }
                }
                System.out.println("");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            Conexion.close(resultSet);
            Conexion.close(connection);
        }
    }
    
}
