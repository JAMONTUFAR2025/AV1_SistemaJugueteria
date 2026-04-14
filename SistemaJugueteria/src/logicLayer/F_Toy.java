/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicLayer;

import dataLayer.E_Toy;
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
public class F_Toy {
    public DefaultTableModel listData(String busqueda) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Esto hace que TODAS las celdas sean de solo lectura
            }
        };
        model.addColumn("ID");
        model.addColumn("Description");
        model.addColumn("Age");
        model.addColumn("Category");
        model.addColumn("Brand");
        model.addColumn("Supplier");
        model.addColumn("Sale Price");
        model.addColumn("Purchase Price");
        model.addColumn("Stock");

        String sql = "SELECT id, description, recommended_age, category, brand, supplier, sale_price, purchase_price, stock "
                   + "FROM toys WHERE description LIKE ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + busqueda + "%");
            ResultSet rs = ps.executeQuery();

            String[] datos = new String[9];
            while (rs.next()) {
                datos[0] = rs.getString("id"); // Convertimos el int a String para la tabla
                datos[1] = rs.getString("description");
                datos[2] = rs.getString("recommended_age") + "+";
                datos[3] = rs.getString("category");
                datos[4] = rs.getString("brand");
                datos[5] = rs.getString("supplier");
                datos[6] = rs.getString("sale_price");
                datos[7] = rs.getString("purchase_price");
                datos[8] = rs.getString("stock");
                model.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar juguetes: " + e.getMessage());
        }
        return model;
    }

    public E_Toy getToy(int id)
    {
        E_Toy toy = null;
        String sql = "SELECT id, description, sale_price, stock FROM toys WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                toy = new E_Toy();
                toy.setId(rs.getInt("id"));
                toy.setDescription(rs.getString("description"));
                toy.setSalePrice(rs.getDouble("sale_price"));
                toy.setStock(rs.getInt("stock"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos del juguete: " + e.getMessage());
        }

        return toy;
    }
    
    public boolean insert(E_Toy toy) {
        String sql = "INSERT INTO toys (description, recommended_age, category, brand, supplier, sale_price, purchase_price, stock) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Empezamos desde el índice 1 con la descripción
            ps.setString(1, toy.getDescription());
            ps.setInt(2, toy.getRecommendedAge());
            ps.setString(3, toy.getCategory());
            ps.setString(4, toy.getBrand());
            ps.setString(5, toy.getSupplier());
            ps.setDouble(6, toy.getSalePrice());
            ps.setDouble(7, toy.getPurchasePrice());
            ps.setInt(8, toy.getStock());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar juguete: " + e.getMessage());
            return false;
        }
    }

    public boolean update(E_Toy toy) {
        String sql = "UPDATE toys SET description = ?, recommended_age = ?, category = ?, brand = ?, "
                   + "supplier = ?, sale_price = ?, purchase_price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, toy.getDescription());
            ps.setInt(2, toy.getRecommendedAge());
            ps.setString(3, toy.getCategory());
            ps.setString(4, toy.getBrand());
            ps.setString(5, toy.getSupplier());
            ps.setDouble(6, toy.getSalePrice());
            ps.setDouble(7, toy.getPurchasePrice());
            ps.setInt(8, toy.getStock());
            ps.setInt(9, toy.getId()); // Usamos setInt para el WHERE id = ?

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar juguete: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) { // Cambiado de String a int
        String sql = "DELETE FROM toys WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); // Usamos setInt

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar juguete: " + e.getMessage());
            return false;
        }
    }

    public int getNextID() {
        int siguienteID = 1;
        String sql = "SELECT " +
                     "  CASE " +
                     "    WHEN NOT EXISTS (SELECT 1 FROM toys) THEN CAST(IDENT_SEED('toys') AS INT) " +
                     "    ELSE CAST(IDENT_CURRENT('toys') + IDENT_INCR('toys') AS INT) " +
                     "  END AS NextID";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                siguienteID = rs.getInt("NextID");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID de juguetes: " + e.getMessage());
        }
        return siguienteID;
    }
    
    public boolean updateStock(int toyId, int quantitySold)
    {
        String sql = "UPDATE toys SET stock = stock - ? WHERE id = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1. El primer '?' es la cantidad a restar
            ps.setInt(1, quantitySold); 

            // 2. El segundo '?' es el ID del juguete que queremos afectar
            ps.setInt(2, toyId); 

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
}
