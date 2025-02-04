/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;
import java.util.List;
/**
 *
 * @author yoriel
 */
public class IVeterinario {

  // Obtener todos los registros de la tabla veterinario
    List<VeterinarioDTO> getAll() throws SQLException;

    // Obtener un veterinario por su clave primaria (id)
    VeterinarioDTO findByPk(int pk) throws SQLException;

    // Insertar un nuevo veterinario en la base de datos
    int insertVeterinario(VeterinarioDTO veterinario) throws SQLException;

    // Insertar varios veterinarios a la vez
    int insertVeterinario(List<VeterinarioDTO> lista) throws SQLException;

    // Eliminar un veterinario de la base de datos
    int deleteVeterinario(VeterinarioDTO veterinario) throws SQLException;

    // Eliminar todos los veterinarios de la tabla
    int deleteVeterinarios() throws SQLException;

    // Actualizar un veterinario, dado su id (pk) y los nuevos datos
    int updateVeterinario(int pk, VeterinarioDTO nuevosDatos) throws SQLException;
}
}