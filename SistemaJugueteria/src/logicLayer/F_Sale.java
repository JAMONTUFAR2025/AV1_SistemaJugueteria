/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import dataLayer.E_Sale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aleja
 */
public class F_Sale {
    public DefaultTableModel listData(String busqueda) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        model.addColumn("ID");
        model.addColumn("Sale Date");
        model.addColumn("Bill Number");
        model.addColumn("User ID");
        model.addColumn("Customer ID");

        String sql = "SELECT id, sale_date, number_of_bill, user_id, customer_id "
                   + "FROM sales WHERE number_of_bill LIKE ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + busqueda + "%");
            ResultSet rs = ps.executeQuery();

            String[] datos = new String[5];
            while (rs.next()) {
                datos[0] = rs.getString("id"); 
                datos[1] = rs.getString("sale_date");
                datos[2] = rs.getString("number_of_bill");
                datos[3] = rs.getString("user_id");
                datos[4] = rs.getString("customer_id");
                model.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error listing sales: " + e.getMessage());
        }
        return model;
    }

    public int insert(E_Sale sale) {
        int generatedId = -1;
        String sql = "INSERT INTO sales (sale_date, number_of_bill, user_id, customer_id) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             // RETURN_GENERATED_KEYS para obtener el ID de SQL Server
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, sale.getSaleDate());
            ps.setString(2, sale.getNumberOfBill());
            ps.setInt(3, sale.getUserId());
            ps.setInt(4, sale.getCustomerId());

            int rowsInserted = ps.executeUpdate();
            
            if (rowsInserted > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting sale: " + e.getMessage());
        }
        return generatedId;
    }

    public boolean update(E_Sale sale) {
        String sql = "UPDATE sales SET sale_date = ?, number_of_bill = ?, user_id = ?, customer_id = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, sale.getSaleDate());
            ps.setString(2, sale.getNumberOfBill());
            ps.setInt(3, sale.getUserId());
            ps.setInt(4, sale.getCustomerId());
            ps.setInt(5, sale.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating sale: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM sales WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting sale: " + e.getMessage());
            return false;
        }
    }

    public int getNextID() {
        int siguienteID = 1;
        String sql = "SELECT " +
                     "  CASE " +
                     "    WHEN NOT EXISTS (SELECT 1 FROM Sales) THEN CAST(IDENT_SEED('Sales') AS INT) " +
                     "    ELSE CAST(IDENT_CURRENT('Sales') + IDENT_INCR('Sales') AS INT) " +
                     "  END AS NextID";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                siguienteID = rs.getInt("NextID");
            }
        } catch (SQLException e) {
            System.out.println("Error obtaining sale ID: " + e.getMessage());
        }
        return siguienteID;
    }
}
