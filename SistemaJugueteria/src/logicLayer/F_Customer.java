/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import dataLayer.E_Customer;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author aleja
 */
public class F_Customer {
    public DefaultTableModel listData(String search) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        model.addColumn("ID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("DNI");
        model.addColumn("Phone Number");
        model.addColumn("Address");

        String sql = "SELECT id, first_name, last_name, age, gender, dni, phone_number, address "
                   + "FROM customers WHERE  dni LIKE ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchParam = "%" + search + "%";
            ps.setString(1, searchParam);
            
            ResultSet rs = ps.executeQuery();

            String[] data = new String[8];
            while (rs.next()) {
                data[0] = rs.getString("id");
                data[1] = rs.getString("first_name");
                data[2] = rs.getString("last_name");
                data[3] = rs.getString("age");
                data[4] = rs.getString("gender");
                data[5] = rs.getString("dni");
                data[6] = rs.getString("phone_number");
                data[7] = rs.getString("address");
                model.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error listing customers: " + e.getMessage());
        }
        return model;
    }

    public boolean insert(E_Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, age, gender, dni, phone_number, address) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setInt(3, customer.getAge());
            ps.setString(4, customer.getGender());
            ps.setString(5, customer.getDni());
            ps.setString(6, customer.getPhoneNumber());
            ps.setString(7, customer.getAddress());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting customer: " + e.getMessage());
            return false;
        }
    }

    public boolean update(E_Customer customer) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, age = ?, gender = ?, "
                   + "dni = ?, phone_number = ?, address = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setInt(3, customer.getAge());
            ps.setString(4, customer.getGender());
            ps.setString(5, customer.getDni());
            ps.setString(6, customer.getPhoneNumber());
            ps.setString(7, customer.getAddress());
            ps.setInt(8, customer.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating customer: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    public int getNextID() {
        int nextID = 1;
        String sql = "SELECT " +
                     "  CASE " +
                     "    WHEN NOT EXISTS (SELECT 1 FROM customers) THEN CAST(IDENT_SEED('customers') AS INT) " +
                     "    ELSE CAST(IDENT_CURRENT('customers') + IDENT_INCR('customers') AS INT) " +
                     "  END AS NextID";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                nextID = rs.getInt("NextID");
            }
        } catch (SQLException e) {
            System.out.println("Error obtaining customer ID: " + e.getMessage());
        }
        return nextID;
    }
}
