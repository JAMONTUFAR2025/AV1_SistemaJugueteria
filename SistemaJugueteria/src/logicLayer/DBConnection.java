/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author aleja
 */
public class DBConnection
{
    public static Connection connect()
    {
        Connection conn = null;

        String server = "192.168.100.130";
        String database = "JugueteriaDB";
        String user = "sa";
        String password = "admin";

        String url = "jdbc:sqlserver://" + server + ":1433;"
                + "databaseName=" + database + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=10;"; // A los 10 segundos se saldra y enviara error si no entra

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Successful connection with sa");
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Connection error: " + e.getMessage());
        }

        return conn;
    }
    
    //public static void main(String args[])
    //{
       // DAO.connect();
   // }
}
