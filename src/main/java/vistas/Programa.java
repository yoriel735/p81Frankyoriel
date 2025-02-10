/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;
import Controladores.MascotasDAO;
import Controladores.VeterinarioDAO;
import Modelos.MascotaDTO;
import Modelos.VeterinariosDTO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author yoriel
 */
public class Programa {
  

    public static void main(String[] args) {
        // Mostrar el menú principal
        String[] opcionesMenu = {"Gestionar Mascotas", "Gestionar Veterinarios", "Salir"};
        int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Menú Principal",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesMenu, opcionesMenu[0]);

        switch (opcion) {
            case 0:
                // Gestionar mascotas
                gestionarMascotas();
                break;
            case 1:
                // Gestionar veterinarios
                gestionarVeterinarios();
                break;
            case 2:
                // Salir
                JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                System.exit(0);
                break;
            default:
                break;
        }
    }

    // Método para gestionar mascotas
    public static void gestionarMascotas() {
        String[] opcionesMascotas = {"Añadir Mascota", "Ver Todas las Mascotas", "Modificar Mascota", "Eliminar Mascota", "Volver"};
        int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Mascotas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesMascotas, opcionesMascotas[0]);

        switch (opcion) {
            case 0:
                // Añadir una nueva mascota
                agregarMascota();
                break;
            case 1:
                // Ver todas las mascotas
                verMascotas();
                break;
            case 2:
                // Modificar una mascota
                modificarMascota();
                break;
            case 3:
                // Eliminar una mascota
                eliminarMascota();
                break;
            case 4:
                // Volver al menú principal
                main(null);
                break;
            default:
                break;
        }
    }

    // Método para gestionar veterinarios
    public static void gestionarVeterinarios() {
        String[] opcionesVeterinarios = {"Añadir Veterinario", "Ver Todos los Veterinarios", "Modificar Veterinario", "Eliminar Veterinario", "Volver"};
        int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Veterinarios",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesVeterinarios, opcionesVeterinarios[0]);

        switch (opcion) {
            case 0:
                // Añadir un nuevo veterinario
                agregarVeterinario();
                break;
            case 1:
                // Ver todos los veterinarios
                verVeterinarios();
                break;
            case 2:
                // Modificar un veterinario
                modificarVeterinario();
                break;
            case 3:
                // Eliminar un veterinario
                eliminarVeterinario();
                break;
            case 4:
                // Volver al menú principal
                main(null);
                break;
            default:
                break;
        }
    }

    // Métodos para las operaciones con Mascotas

    public static void agregarMascota() {
        // Obtener los datos de la mascota
        String nombre = JOptionPane.showInputDialog("Nombre de la mascota:");
        int numeroChip = Integer.parseInt(JOptionPane.showInputDialog("Número de chip de la mascota:"));
        double peso = Double.parseDouble(JOptionPane.showInputDialog("Peso de la mascota:"));
        String tipo = JOptionPane.showInputDialog("Tipo de mascota (Perro, Gato, Otros):");
        String fechaNacimientoStr = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-mm-dd):");
        String idVeterinarioStr = JOptionPane.showInputDialog("ID del veterinario:");
        
        // Convertir la fecha y obtener el id del veterinario
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        int idVeterinario = Integer.parseInt(idVeterinarioStr);

        // Crear la mascota
        MascotaDTO mascota = new MascotaDTO();
        mascota.setNombreMascota(nombre);
        mascota.setNumeroChip(numeroChip);
        mascota.setPeso(peso);
        mascota.setTipo(tipo);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setId_veterinario(idVeterinario);

        try {
            // Insertar la mascota en la base de datos
            MascotasDAO dao = new MascotasDAO();
            dao.insertMascota(mascota);
            JOptionPane.showMessageDialog(null, "Mascota añadida correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al añadir la mascota: " + e.getMessage());
        }
    }

    public static void verMascotas() {
        try {
            // Obtener todas las mascotas
            MascotasDAO dao = new MascotasDAO();
            List<MascotaDTO> mascotas = dao.getAll();
            StringBuilder sb = new StringBuilder();

            for (MascotaDTO mascota : mascotas) {
                sb.append(mascota.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Mascotas", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las mascotas: " + e.getMessage());
        }
    }

    public static void modificarMascota() {
        // Solicitar el ID de la mascota que se quiere modificar
        int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Introduce el ID de la mascota a modificar:"));
        
        // Obtener los nuevos datos de la mascota
        String nombre = JOptionPane.showInputDialog("Nuevo nombre de la mascota:");
        double peso = Double.parseDouble(JOptionPane.showInputDialog("Nuevo peso de la mascota:"));
        String tipo = JOptionPane.showInputDialog("Nuevo tipo de mascota (Perro, Gato, Otros):");
        
        try {
            // Obtener la mascota de la base de datos
            MascotasDAO dao = new MascotasDAO();
            MascotaDTO mascota = dao.findByPk(idMascota);

            if (mascota != null) {
                // Modificar los datos de la mascota
                mascota.setNombreMascota(nombre);
                mascota.setPeso(peso);
                mascota.setTipo(tipo);
                dao.updateMascota(idMascota, mascota);
                JOptionPane.showMessageDialog(null, "Mascota modificada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la mascota: " + e.getMessage());
        }
    }

    public static void eliminarMascota() {
        // Solicitar el ID de la mascota que se quiere eliminar
        int idMascota = Integer.parseInt(JOptionPane.showInputDialog("Introduce el ID de la mascota a eliminar:"));
        
        try {
            // Obtener la mascota de la base de datos
            MascotasDAO dao = new MascotasDAO();
            MascotaDTO mascota = dao.findByPk(idMascota);

            if (mascota != null) {
                // Eliminar la mascota
                dao.deleteMascota(mascota);
                JOptionPane.showMessageDialog(null, "Mascota eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la mascota: " + e.getMessage());
        }
    }

    // Métodos para las operaciones con Veterinarios (similar a las operaciones de Mascotas)
    public static void agregarVeterinario() {
        // Implementa lo mismo que en agregarMascota pero para veterinarios
    }

    public static void verVeterinarios() {
        // Implementa lo mismo que en verMascotas pero para veterinarios
    }

    public static void modificarVeterinario() {
        // Implementa lo mismo que en modificarMascota pero para veterinarios
    }

    public static void eliminarVeterinario() {
        // Implementa lo mismo que en eliminarMascota pero para veterinarios
    }
}
