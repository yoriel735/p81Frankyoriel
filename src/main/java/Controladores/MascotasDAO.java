/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;


import Conexion.Conexion;
import Modelos.Imascota;
import Modelos.MascotaDTO;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author yoriel
 */
    
public class MascotasDAO implements Imascota {

    // Método para obtener todas las mascotas
    @Override
    public List<MascotaDTO> getAll() throws SQLException {
        List<MascotaDTO> mascotas = new ArrayList<>();
        Connection conn = Conexion.getInstance();
        String sql = "SELECT * FROM mascota"; // Asegúrate de que la tabla y columnas coincidan
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MascotaDTO mascota = new MascotaDTO();
                mascota.setIdMascota(rs.getInt("id"));
                mascota.setNumeroChip(rs.getInt("numero_chip"));
                mascota.setNombreMascota(rs.getString("nombre"));
                mascota.setPeso((int) rs.getDouble("peso"));
                mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                mascota.setTipo(rs.getString("tipo"));
                mascota.setId_veterinario(rs.getInt("id_veterinario"));
                mascotas.add(mascota);
            }
        }
        return mascotas;
    }

    // Método para obtener una mascota por su id (clave primaria)
    @Override
    public MascotaDTO findByPk(int pk) throws SQLException {
        MascotaDTO mascota = null;
        Connection conn = Conexion.getInstance();
        String sql = "SELECT * FROM mascota WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pk);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mascota = new MascotaDTO();
                    mascota.setIdMascota(rs.getInt("id"));
                    mascota.setNumeroChip(rs.getInt("numero_chip"));
                    mascota.setNombreMascota(rs.getString("nombre"));
                    mascota.setPeso((int) rs.getDouble("peso"));
                    mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    mascota.setTipo(rs.getString("tipo"));
                    mascota.setId_veterinario(rs.getInt("id_veterinario"));
                }
            }
        }
        return mascota;
    }

    // Método para insertar una nueva mascota
    @Override
    public int insertMascota(MascotaDTO mascota) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "INSERT INTO mascota (numero_chip, nombre, peso, fecha_nacimiento, tipo, id_veterinario) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mascota.getNumeroChip());
            stmt.setString(2, mascota.getNombreMascota());
            stmt.setDouble(3, mascota.getPeso());
            stmt.setDate(4, Date.valueOf(mascota.getFechaNacimiento()));  // Convierte LocalDate a Date
            stmt.setString(5, mascota.getTipo());
            stmt.setInt(6, mascota.getId_veterinario());
            return stmt.executeUpdate(); // Retorna el número de filas afectadas
        }
    }

    // Método para insertar varias mascotas
    @Override
    public int insertMascota(List<MascotaDTO> lista) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "INSERT INTO mascota (numero_chip, nombre, peso, fecha_nacimiento, tipo, id_veterinario) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (MascotaDTO mascota : lista) {
                stmt.setInt(1, mascota.getNumeroChip());
                stmt.setString(2, mascota.getNombreMascota());
                stmt.setDouble(3, mascota.getPeso());
                stmt.setDate(4, Date.valueOf(mascota.getFechaNacimiento()));
                stmt.setString(5, mascota.getTipo());
                stmt.setInt(6, mascota.getId_veterinario());
                stmt.addBatch(); // Agrega la mascota al batch
            }
            int[] rows = stmt.executeBatch(); // Ejecuta todos los inserts a la vez
            return rows.length; // Devuelve cuántos registros fueron insertados
        }
    }

    // Método para eliminar una mascota
    @Override
    public int deleteMascota(MascotaDTO mascota) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "DELETE FROM mascota WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, mascota.getIdMascota());
            return stmt.executeUpdate();
        }
    }

    // Método para eliminar todas las mascotas
    @Override
    public int deleteMascotas() throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "DELETE FROM mascota";
        
        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    // Método para actualizar una mascota, dado su id (pk) y los nuevos datos
    @Override
    public int updateMascota(int pk, MascotaDTO nuevosDatos) throws SQLException {
        Connection conn = Conexion.getInstance();
        String sql = "UPDATE mascota SET numero_chip = ?, nombre = ?, peso = ?, fecha_nacimiento = ?, tipo = ?, id_veterinario = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nuevosDatos.getNumeroChip());
            stmt.setString(2, nuevosDatos.getNombreMascota());
            stmt.setDouble(3, nuevosDatos.getPeso());
            stmt.setDate(4, Date.valueOf(nuevosDatos.getFechaNacimiento())); // Convierte LocalDate a Date
            stmt.setString(5, nuevosDatos.getTipo());
            stmt.setInt(6, nuevosDatos.getId_veterinario());
            stmt.setInt(7, pk);
            return stmt.executeUpdate();
        }
    }

    // Método para obtener todas las mascotas tratadas por un veterinario (con su id)
    @Override
    public List<MascotaDTO> getMascotasByVeterinarioId(int idVeterinario) throws SQLException {
        List<MascotaDTO> mascotas = new ArrayList<>();
        Connection conn = Conexion.getInstance();
        String sql = "SELECT * FROM mascota WHERE id_veterinario = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeterinario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MascotaDTO mascota = new MascotaDTO();
                    mascota.setIdMascota(rs.getInt("id"));
                    mascota.setNumeroChip(rs.getInt("numero_chip"));
                    mascota.setNombreMascota(rs.getString("nombre"));
                    mascota.setPeso((int) rs.getDouble("peso"));
                    mascota.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    mascota.setTipo(rs.getString("tipo"));
                    mascota.setId_veterinario(rs.getInt("id_veterinario"));
                    mascotas.add(mascota);
                }
            }
        }
        return mascotas;
    }
}
