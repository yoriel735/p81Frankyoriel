/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.MascotaDTO;
import Modelos.VeterinariosDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author yoriel
 */
public class MainPrueba {
    public static void main(String[] args) {

        try {

            // ---------------------- PRUEBAS CON VETERINARIOS ----------------------

            // Crear una instancia de VeterinarioDAO
            VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
            System.out.println("Obteniendo todos los veterinarios...");
            // Llamar al método getAll() de la instancia
            List<VeterinariosDTO> veterinarios = veterinarioDAO.getAll();

            // Imprimir todos los veterinarios
            for (VeterinariosDTO v : veterinarios) {
                System.out.println(v);
            }

            // Prueba de insertar un veterinario
            VeterinariosDTO nuevoVeterinario = new VeterinariosDTO();
            nuevoVeterinario.setNifVeterinario("12346");
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

            // ---------------------- PRUEBAS CON MASCOTAS ----------------------

            // Crear una instancia de MascotaDAO
            MascotasDAO mascotaDAO = new MascotasDAO();
            System.out.println("Obteniendo todas las mascotas...");
            // Llamar al método getAll() de la instancia
            List<MascotaDTO> mascotas = mascotaDAO.getAll();

            // Imprimir todas las mascotas
            for (MascotaDTO m : mascotas) {
                System.out.println(m);
            }

            // Prueba de insertar una nueva mascota
            MascotaDTO nuevaMascota = new MascotaDTO();
            nuevaMascota.setNumeroChip(123456);
            nuevaMascota.setNombreMascota("Rex");
            nuevaMascota.setPeso((int) 15.5);
            nuevaMascota.setFechaNacimiento(java.time.LocalDate.of(2020, 5, 10));
            nuevaMascota.setTipo("Perro");
            nuevaMascota.setId_veterinario(1); // Asumiendo que el veterinario con id 1 existe

            mascotaDAO.insertMascota(nuevaMascota);
            System.out.println("Mascota insertada correctamente");

            // Prueba de obtener una mascota por id
            MascotaDTO mascota = mascotaDAO.findByPk(1); // Asumiendo que hay una mascota con id 1
            System.out.println("Mascota encontrada: " + mascota);

            // Prueba de eliminar una mascota
            mascotaDAO.deleteMascota(mascota);
            System.out.println("Mascota eliminada correctamente");

            // Prueba de obtener todas las mascotas tratadas por un veterinario
            List<MascotaDTO> mascotasPorVeterinario = mascotaDAO.getMascotasByVeterinarioId(1); // Asumiendo que el veterinario con id 1 tiene mascotas
            System.out.println("Mascotas tratadas por el veterinario con id 1:");
            for (MascotaDTO m : mascotasPorVeterinario) {
                System.out.println(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    

