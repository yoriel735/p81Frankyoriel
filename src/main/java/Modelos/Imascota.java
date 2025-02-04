/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author yoriel
 */
public class Imascota {
    


public interface IMascota {
    
   // Obtener todos los registros de la tabla mascota
    List<MascotaDTO> getAll() throws SQLException;

    // Obtener una mascota por su clave primaria (id)
    MascotaDTO findByPk(int pk) throws SQLException;

    // Insertar una nueva mascota en la base de datos
    int insertMascota(MascotaDTO mascota) throws SQLException;

    // Insertar varias mascotas a la vez
    int insertMascota(List<MascotaDTO> lista) throws SQLException;

    // Eliminar una mascota de la base de datos
    int deleteMascota(MascotaDTO mascota) throws SQLException;

    // Eliminar todas las mascotas de la tabla
    int deleteMascotas() throws SQLException;

    // Actualizar una mascota, dado su id (pk) y los nuevos datos
    int updateMascota(int pk, MascotaDTO nuevosDatos) throws SQLException;

    // Obtener todas las mascotas tratadas por un veterinario (con su id)
    List<MascotaDTO> getMascotasByVeterinarioId(int idVeterinario) throws SQLException;
}
}