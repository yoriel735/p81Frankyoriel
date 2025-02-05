
package Conexion;

import java.sql.Connection;

import Modelos.VeterinariosDTO;
import Controladores.VeterinarioDAO;
import java.sql.SQLException;
import java.util.List;

public class Pruebas {

    public static void main(String[] args) {
        
    try{
            
  // Crear una instancia de VeterinarioDAO
    VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    System.out.println("Obteniendo todos los veterinarios...");
    // Llamar al método getAll() de la instanc System.out.println("Obteniendo todos los veterinarios...");ia
    List<VeterinariosDTO> veterinarios = veterinarioDAO.getAll();

    // Imprimir todos los veterinarios
    for (VeterinariosDTO v : veterinarios) {
        System.out.println(v);
    }
  

            // Prueba de insertar un veterinario
            VeterinariosDTO nuevoVeterinario = new VeterinariosDTO();
            nuevoVeterinario.setNifVeterinario("12345");
            nuevoVeterinario.setNombreVeterinario("Juan Pérez");
            nuevoVeterinario.setDireccion("Calle Falsa 123");
            nuevoVeterinario.setTelefono("555-1234");
            nuevoVeterinario.setEmail("juan.perez@veterinario.com");

            veterinarioDAO.insertVeterinario(nuevoVeterinario);
            System.out.println("Veterinario insertado correctamente");

            // Prueba de obtener un veterinario por id
            VeterinariosDTO veterinario = veterinarioDAO.findByPk(1); // Asumiendo que hay un veterinario con id 1
            System.out.println("Veterinario encontrado: " + veterinario);

            // Prueba de eliminar un veterinario
            veterinarioDAO.deleteVeterinario(veterinario);
            System.out.println("Veterinario eliminado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
