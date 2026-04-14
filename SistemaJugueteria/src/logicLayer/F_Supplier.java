/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import dataLayer.E_Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class F_Supplier {
    public DefaultTableModel listData(String busqueda) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Esto hace que TODAS las celdas sean de solo lectura
            }
        };
        model.addColumn("ID");
        model.addColumn("Company Name");
        model.addColumn("Phone Number");
        model.addColumn("E-mail");

        String sql = "SELECT id, company_name, phone_number, email "
                   + "FROM suppliers WHERE company_name LIKE ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + busqueda + "%");
            ResultSet rs = ps.executeQuery();

            String[] datos = new String[4];
            while (rs.next()) {
                datos[0] = rs.getString("id"); // Convertimos el int a String para la tabla
                datos[1] = rs.getString("company_name");
                datos[2] = rs.getString("phone_number");
                datos[3] = rs.getString("email");
                model.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error listing suppliers: " + e.getMessage());
        }
        return model;
    }

    public boolean insert(E_Supplier supplier) {
        String sql = "INSERT INTO suppliers (company_name, phone_number, email) "
               + "VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, supplier.getCompanyName());
            ps.setString(2, supplier.getPhoneNumber());
            ps.setString(3, supplier.getEmail());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting supplier: " + e.getMessage());
            return false;
        }
    }

    public boolean update(E_Supplier supplier) {
        String sql = "UPDATE suppliers SET company_name = ?, phone_number = ?, email = ?"
                   + " WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, supplier.getCompanyName());
            ps.setString(2, supplier.getPhoneNumber());
            ps.setString(3, supplier.getEmail());
            ps.setInt(4, supplier.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating supplier: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting supplier: " + e.getMessage());
            return false;
        }
    }

    public int getNextID() {
        int siguienteID = 1;
        String sql = "SELECT " +
                     "  CASE " +
                     "    WHEN NOT EXISTS (SELECT 1 FROM suppliers) THEN CAST(IDENT_SEED('suppliers') AS INT) " +
                     "    ELSE CAST(IDENT_CURRENT('suppliers') + IDENT_INCR('suppliers') AS INT) " +
                     "  END AS NextID";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                siguienteID = rs.getInt("NextID");
            }
        } catch (SQLException e) {
            System.out.println("Error obtaining supplier IDs: " + e.getMessage());
        }
        return siguienteID;
    }
}
