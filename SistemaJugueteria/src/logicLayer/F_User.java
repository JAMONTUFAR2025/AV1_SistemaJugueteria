/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import java.sql.*;
import dataLayer.E_User;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class F_User {
    
    public DefaultTableModel listData(String busqueda) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Role");

        String sql = "SELECT id, first_name, last_name, username, password, role FROM users WHERE username LIKE ?";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + busqueda + "%");
            ResultSet rs = ps.executeQuery();

            String[] datos = new String[6];
            while (rs.next()) {
                datos[0] = rs.getString("id");
                datos[1] = rs.getString("first_name");
                datos[2] = rs.getString("last_name");
                datos[3] = rs.getString("username");
                datos[4] = rs.getString("password");
                datos[5] = rs.getString("role");
                model.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error listing users: " + e.getMessage());
        }
        return model;
    }
    
    public boolean insert(E_User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password, role) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting user: " + e.getMessage());
            return false;
        }
    }

    public boolean update(E_User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, role = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setInt(6, user.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    public int getNextID() {
        int siguienteID = 1;
        String sql = "SELECT " +
                     "CASE " +
                     "  WHEN NOT EXISTS (SELECT 1 FROM users) THEN CAST(IDENT_SEED('users') AS INT) " +
                     "  ELSE CAST(IDENT_CURRENT('users') + IDENT_INCR('users') AS INT) " +
                     "END AS NextID";

        try (java.sql.Connection conn = DBConnection.connect();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                siguienteID = rs.getInt("NextID");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Error obtaining user ID: " + e.getMessage());
        }
        return siguienteID;
    }
    
    public E_User login(String user, String pass) {
        E_User userFound = null;
        
        String sql = "SELECT id, first_name, last_name, username, password, role FROM users WHERE username = ? AND password = ?";

        try (Connection cn = DBConnection.connect();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userFound = new E_User();
                userFound.setId(rs.getInt("id"));
                userFound.setFirstName(rs.getString("first_name"));
                userFound.setLastName(rs.getString("last_name"));
                userFound.setUsername(rs.getString("username"));
                userFound.setPassword(rs.getString("password"));
                userFound.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println("Error connecting SQL Server: " + e.getMessage());
        }
        return userFound;
    }
}
