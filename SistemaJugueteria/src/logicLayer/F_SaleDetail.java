/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import dataLayer.E_SaleDetail;
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
public class F_SaleDetail {
    // Cambiamos el parámetro a int saleId, ya que listaremos los detalles de una venta específica
    public DefaultTableModel listData(int saleId) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Esto hace que TODAS las celdas sean de solo lectura
            }
        };
        model.addColumn("ID");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Toy ID");
        model.addColumn("Sale ID");

        String sql = "SELECT id, price, quantity, toy_id, sale_id "
                   + "FROM sales_details WHERE sale_id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, saleId);
            ResultSet rs = ps.executeQuery();

            String[] datos = new String[5];
            while (rs.next()) {
                datos[0] = rs.getString("id"); 
                datos[1] = rs.getString("price");
                datos[2] = rs.getString("quantity");
                datos[3] = rs.getString("toy_id");
                datos[4] = rs.getString("sale_id");
                model.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar detalles de venta: " + e.getMessage());
        }
        return model;
    }

    public boolean insert(E_SaleDetail detail) {
        String sql = "INSERT INTO sales_details (price, quantity, toy_id, sale_id) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, detail.getPrice());
            ps.setDouble(2, detail.getQuantity());
            ps.setInt(3, detail.getToyId());
            ps.setInt(4, detail.getSaleId());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar detalle de venta: " + e.getMessage());
            return false;
        }
    }

    public boolean update(E_SaleDetail detail) {
        String sql = "UPDATE sales_details SET price = ?, quantity = ?, toy_id = ?, sale_id = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, detail.getPrice());
            ps.setDouble(2, detail.getQuantity());
            ps.setInt(3, detail.getToyId());
            ps.setInt(4, detail.getSaleId());
            ps.setInt(5, detail.getId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar detalle de venta: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM sales_details WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar detalle de venta: " + e.getMessage());
            return false;
        }
    }
}
