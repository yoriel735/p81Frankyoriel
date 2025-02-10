/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;


import Conexion.Conexion;
import Modelos.IVeterinario;
import Modelos.VeterinariosDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author yoriel
 */

public class VeterinarioDAO implements IVeterinario {

    // Método para obtener todos los veterinarios
    @Override
    public List<VeterinariosDTO> getAll() throws SQLException {
        List<VeterinariosDTO> veterinarios = new ArrayList<>();
        Connection conn = Conexion.getInstance(); // Asumiendo que tienes esta clase Conexion con getInstance()
        String sql = "SELECT * FROM veterinario"; // Reemplaza con tu tabla y columnas
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                VeterinariosDTO veterinario = new VeterinariosDTO();
                veterinario.setIdVeterinario(rs.getInt("id"));
                veterinario.setNifVeterinario(rs.getString("nif"));
                veterinario.setNombreVeterinario(rs.getString("nombre"));
                veterinario.setDireccion(rs.getString("direccion"));
                veterinario.setTelefono(rs.getString("telefono"));
                veterinario.setEmail(rs.getString("email"));
                veterinarios.add(veterinario);
            }
        }
        return veterinarios;
    }

    // Método para obtener un veterinario por su id (primary key)
    @Override
    public VeterinariosDTO findByPk(int pk) throws SQLException {
        VeterinariosDTO veterinario = null;
        Connection conn = Conexion.getInstance();
        String sql = "SELECT * FROM veterinario WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pk);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    veterinario = new VeterinariosDTO();
                    veterinario.setIdVeterinario(rs.getInt("id"));
                    veterinario.setNifVeterinario(rs.getString("nif"));
                    veterinario.setNombreVeterinario(rs.getString("nombre"));
                    veterinario.setDireccion(rs.getString("direccion"));
                    veterinario.setTelefono(rs.getString("telefono"));
                    veterinario.setEmail(rs.getString("email"));
                }
            }
        }
        return veterinario;
    }

    // Método para insertar un veterinario
    @Override
    public int insertVeterinario(VeterinariosDTO veterinario) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "INSERT INTO veterinario ( ID, nif, nombre, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, veterinario.getIdVeterinario());
            stmt.setString(2, veterinario.getNifVeterinario());
            stmt.setString(3, veterinario.getNombreVeterinario());
            stmt.setString(4, veterinario.getDireccion());
            stmt.setString(5, veterinario.getTelefono());
            stmt.setString(6, veterinario.getEmail());
            return stmt.executeUpdate(); // Retorna el número de filas afectadas
        }
    }

    // Método para insertar varios veterinarios
    @Override
    public int insertVeterinario(List<VeterinariosDTO> lista) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "INSERT INTO veterinario (ID,nif, nombre, direccion, telefono, email) VALUES (?, ?, ?, ?, ?,?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (VeterinariosDTO veterinario : lista) {
                stmt.setInt(1, veterinario.getIdVeterinario());
                stmt.setString(2, veterinario.getNifVeterinario());
                stmt.setString(3, veterinario.getNombreVeterinario());
                stmt.setString(4, veterinario.getDireccion());
                stmt.setString(5, veterinario.getTelefono());
                stmt.setString(6, veterinario.getEmail());
                stmt.addBatch(); // Agrega el veterinario al batch
            }
            int[] rows = stmt.executeBatch(); // Ejecuta todos los inserts a la vez
            return rows.length; // Devuelve cuántos registros fueron insertados
        }
    }

    // Método para eliminar un veterinario
    @Override
    public int deleteVeterinario(VeterinariosDTO veterinario) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "DELETE FROM veterinario WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, veterinario.getIdVeterinario());
            return stmt.executeUpdate();
        }
    }

    // Método para eliminar todos los veterinarios
    @Override
    public int deleteVeterinarios() throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "DELETE FROM veterinario";
        
        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    // Método para actualizar un veterinario
    @Override
    public int updateVeterinario(int pk, VeterinariosDTO nuevosDatos) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "UPDATE veterinario SET nif = ?, nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevosDatos.getNifVeterinario());
            stmt.setString(2, nuevosDatos.getNombreVeterinario());
            stmt.setString(3, nuevosDatos.getDireccion());
            stmt.setString(4, nuevosDatos.getTelefono());
            stmt.setString(5, nuevosDatos.getEmail());
            stmt.setInt(6, pk);
            return stmt.executeUpdate();
        }
    }
}
