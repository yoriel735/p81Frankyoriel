package vistas;

import Controladores.MascotasDAO;
import Controladores.VeterinarioDAO;
import Modelos.MascotaDTO;
import Modelos.VeterinariosDTO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author yoriel
 */
public class Programa {

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            // Mostrar el menú principal
            String[] opcionesMenu = {"Gestionar Mascotas", "Gestionar Veterinarios", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Menú Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesMenu, opcionesMenu[0]);

            switch (opcion) {
                case 0 -> // Gestionar mascotas
                    gestionarMascotas();
                case 1 -> // Gestionar veterinarios
                    gestionarVeterinarios();
                case 2 -> {
                    // Salir
                    JOptionPane.showMessageDialog(null, "¡Hasta la vista Baby👋👋!👋👋");
                    salir = true;  // Cambiar la variable de control para salir del bucle
                }
                default -> {
                }
            }
        }
    }

    // Método para gestionar mascotas
    public static void gestionarMascotas() {
        boolean volver = false;
        while (!volver) {
            String[] opcionesMascotas = {"Añadir Mascota", "Ver Todas las Mascotas", "Ver Mascotas por veterinario", "Modificar Mascota", "Eliminar Mascota", "Volver"};
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Mascotas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesMascotas, opcionesMascotas[0]);

            switch (opcion) {
                case 0 ->
                    agregarMascota();
                case 1 ->
                    verMascotas();
                case 2 ->
                    verMascotasPorVeterinario();
                case 3 ->
                    modificarMascota();
                case 4 ->
                    eliminarMascota();
                case 5 ->
                    volver = true;
                default -> {
                }
            }
        }
    }

    // Método para gestionar veterinarios
    public static void gestionarVeterinarios() {
        boolean volver = false;
        while (!volver) {
            String[] opcionesVeterinarios = {"Añadir Veterinario", "Ver Todos los Veterinarios", "Modificar Veterinario", "Eliminar Veterinario", "Volver"};
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Gestión de Veterinarios",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesVeterinarios, opcionesVeterinarios[0]);

            switch (opcion) {
                case 0 -> // Añadir un nuevo veterinario
                    agregarVeterinario();
                case 1 -> // Ver todos los veterinarios
                    verVeterinarios();
                case 2 -> // Modificar un veterinario
                    modificarVeterinario();
                case 3 -> // Eliminar un veterinario
                    eliminarVeterinario();
                case 4 -> // Volver al menú principal
                    volver = true;
                default -> {
                }
            }
        }
    }

    // Métodos para las operaciones con Mascotas
    public static void agregarMascota() {
        // Obtener los datos de la mascota
        String nombre = JOptionPane.showInputDialog("Nombre de la mascota:");

        // Validar el número de chip
        String numeroChipStr = JOptionPane.showInputDialog("Número de chip de la mascota (dejar vacío si no tiene):");
        Integer numeroChip = null;

        if (numeroChipStr != null && !numeroChipStr.trim().isEmpty()) {
            try {
                numeroChip = Integer.parseInt(numeroChipStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número de chip no válido.");
                return;  // Salir si el número de chip no es válido
            }
        }

        // Validar el peso
        double peso = 0;
        try {
            peso = Double.parseDouble(JOptionPane.showInputDialog("Peso de la mascota:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Peso no válido.");
            return;
        }

        String tipo = JOptionPane.showInputDialog("Tipo de mascota (Perro, Gato, Otros):");
        String fechaNacimientoStr = JOptionPane.showInputDialog("Fecha de nacimiento (yyyy-mm-dd):");
        String idVeterinarioStr = JOptionPane.showInputDialog("ID del veterinario (dejar vacío si no tiene):");

        // Convertir la fecha
        LocalDate fechaNacimiento = null;
        try {
            fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fecha de nacimiento no válida.");
            return;
        }

        // Comprobar si el ID es nulo o vacío
        Integer idVeterinario = null;  // Utilizamos Integer para permitir valores nulos

        if (idVeterinarioStr != null && !idVeterinarioStr.trim().isEmpty()) {
            try {
                idVeterinario = Integer.parseInt(idVeterinarioStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID del veterinario no válido.");
                return;  // Salir si el ID no es válido
            }
        }

        // Crear la mascota
        MascotaDTO mascota = new MascotaDTO();
        mascota.setNombreMascota(nombre);
        mascota.setNumeroChip(numeroChip);  // Puede ser null si no se ingresa
        mascota.setPeso(peso);
        mascota.setTipo(tipo);
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setId_veterinario(idVeterinario);  // Asignar el ID del veterinario (puede ser null)

        try {
            // Insertar la mascota en la base de datos
            MascotasDAO dao = new MascotasDAO();
            dao.insertMascota(mascota);
            JOptionPane.showMessageDialog(null, "Mascota añadida correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al añadir la mascota: ");
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
            JOptionPane.showMessageDialog(null, "Error al obtener las mascotas: ");
        }
    }

    public static void modificarMascota() {
        // Solicitar el ID de la mascota a modificar
        String idMascotaStr = JOptionPane.showInputDialog("Introduce el ID de la mascota a modificar:");

        // Validar si el usuario presionó "Cancelar" o dejó el campo vacío
        if (idMascotaStr == null || idMascotaStr.trim().isEmpty()) {
            // Si el valor es null o vacío, mostrar un mensaje y salir
            JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un ID de mascota.");
            return;  // Salir del método sin hacer nada
        }

        try {
            // Convertir el ID a entero
            int idMascota = Integer.parseInt(idMascotaStr);

            // Obtener la mascota de la base de datos
            MascotasDAO dao = new MascotasDAO();
            MascotaDTO mascota = dao.findByPk(idMascota);

            // Verificar si la mascota existe
            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna mascota con ese ID.");
                return;  // Salir si no se encuentra la mascota
            }

            // Obtener los nuevos datos de la mascota
            String nombre = JOptionPane.showInputDialog("Nuevo nombre de la mascota:", mascota.getNombreMascota());
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un nuevo nombre.");
                return;
            }

            String pesoStr = JOptionPane.showInputDialog("Nuevo peso de la mascota:", mascota.getPeso());
            if (pesoStr == null || pesoStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un nuevo peso.");
                return;
            }
            double peso = Double.parseDouble(pesoStr);

            String tipo = JOptionPane.showInputDialog("Nuevo tipo de mascota (Perro, Gato, Otros):", mascota.getTipo());
            if (tipo == null || tipo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un nuevo tipo.");
                return;
            }

            // Modificar los datos de la mascota
            mascota.setNombreMascota(nombre);
            mascota.setPeso(peso);
            mascota.setTipo(tipo);

            // Actualizar la mascota en la base de datos
            dao.updateMascota(idMascota, mascota);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Mascota modificada correctamente.");

        } catch (NumberFormatException e) {
            // Si el ID no es un número válido, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de mascota válido.");
        } catch (SQLException e) {
            // Manejar cualquier error relacionado con la base de datos
            JOptionPane.showMessageDialog(null, "Error al modificar la mascota: ");
        }
    }

    public static void eliminarMascota() {
    // Solicitar el ID de la mascota a eliminar
    String idMascotaStr = JOptionPane.showInputDialog("Introduce el ID de la mascota a eliminar:");

    // Validar si el usuario presionó "Cancelar" o dejó el campo vacío
    if (idMascotaStr == null || idMascotaStr.trim().isEmpty()) {
        // Si el valor es null o vacío, mostrar un mensaje y salir
        JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un ID de mascota.");
        return;  // Salir del método sin hacer nada
    }

    try {
        // Convertir el ID a entero
        int idMascota = Integer.parseInt(idMascotaStr);

        // Obtener la mascota de la base de datos
        MascotasDAO dao = new MascotasDAO();
        MascotaDTO mascota = dao.findByPk(idMascota);

        // Verificar si la mascota existe
        if (mascota == null) {
            JOptionPane.showMessageDialog(null, "No se encontró ninguna mascota con ese ID.");
            return;  // Salir si no se encuentra la mascota
        }

        // Confirmación de eliminación
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar esta mascota?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.NO_OPTION) {
            return;  // Si el usuario cancela la eliminación, salir del método
        }

        // Eliminar la mascota de la base de datos
        dao.deleteMascota(mascota);

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(null, "Mascota eliminada correctamente.");

    } catch (NumberFormatException e) {
        // Si el ID no es un número válido, mostrar un mensaje de error
        JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de mascota válido.");
    } catch (SQLException e) {
        // Manejar cualquier error relacionado con la base de datos
        JOptionPane.showMessageDialog(null, "Error al eliminar la mascota: ");
    }


    }

    public static void agregarVeterinario() {
        // Pedir los datos a agregar de los veterinarios
        String nombre = JOptionPane.showInputDialog("Nombre del veterinario:");

        // El NIF y teléfono pueden ser opcionales, por lo que si no se proporcionan, se asigna null
        String nifVeterinario = JOptionPane.showInputDialog("NIF del veterinario (dejar vacío si no tiene):");
        String direccion = JOptionPane.showInputDialog("Introduzca la dirección del veterinario:");
        String email = JOptionPane.showInputDialog("Introduzca el email del veterinario:");
        String telefonoVeterinario = JOptionPane.showInputDialog("Número de teléfono del veterinario (dejar vacío si no tiene):");

        // Si el teléfono es vacío, lo dejamos como null
        if (telefonoVeterinario != null && telefonoVeterinario.trim().isEmpty()) {
            telefonoVeterinario = null;
        }

        // Si el NIF es vacío, lo dejamos como null
        if (nifVeterinario != null && nifVeterinario.trim().isEmpty()) {
            nifVeterinario = null;
        }

        // Crear el objeto VeterinarioDTO
        VeterinariosDTO veterinario = new VeterinariosDTO();
        veterinario.setNombreVeterinario(nombre);
        veterinario.setNifVeterinario(nifVeterinario);  // NIF puede ser null
        veterinario.setDireccion(direccion);
        veterinario.setEmail(email);
        veterinario.setTelefono(telefonoVeterinario);  // Teléfono puede ser null

        try {
            // Insertar el veterinario en la base de datos
            VeterinarioDAO dao = new VeterinarioDAO();
            dao.insertVeterinario(veterinario);
            JOptionPane.showMessageDialog(null, "Veterinario añadido correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al añadir el veterinario: ");
        }
    }

    public static void verVeterinarios() {
        try {
            // Ver la informacion de todos los veterinarios
            VeterinarioDAO dao = new VeterinarioDAO();
            List<VeterinariosDTO> veterinarios = dao.getAll();
            StringBuilder sb = new StringBuilder();

            for (VeterinariosDTO veterinario : veterinarios) {
                sb.append(veterinario.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Mascotas", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las mascotas: ");
        }
    }

   public static void modificarVeterinario() {
    // Solicitar el ID del veterinario a modificar
    String idVeterinarioStr = JOptionPane.showInputDialog("Introduce el ID del veterinario a modificar:");

    // Validar si el usuario presionó "Cancelar" o dejó el campo vacío
    if (idVeterinarioStr == null || idVeterinarioStr.trim().isEmpty()) {
        // Si el valor es null o vacío, mostrar un mensaje y salir
        JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un ID de veterinario.");
        return;  // Salir del método sin hacer nada
    }

    try {
        // Convertir el ID a entero
        int idVeterinario = Integer.parseInt(idVeterinarioStr);

        // Obtener el veterinario de la base de datos
        VeterinarioDAO dao = new VeterinarioDAO();
        VeterinariosDTO veterinario = dao.findByPk(idVeterinario);

        // Verificar si el veterinario existe
        if (veterinario == null) {
            JOptionPane.showMessageDialog(null, "No se encontró ningún veterinario con ese ID.");
            return;  // Salir si no se encuentra el veterinario
        }

        // Obtener los nuevos datos del veterinario
        String nombre = JOptionPane.showInputDialog("Nuevo nombre del veterinario:", veterinario.getNombreVeterinario());
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un nuevo nombre.");
            return;
        }

        String telefono = JOptionPane.showInputDialog("Nuevo teléfono del veterinario:", veterinario.getTelefono());
        if (telefono == null || telefono.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un nuevo teléfono.");
            return;
        }

        // Modificar los datos del veterinario
        veterinario.setNombreVeterinario(idVeterinarioStr);
        
        veterinario.setTelefono(telefono);

        // Actualizar el veterinario en la base de datos
        dao.updateVeterinario(idVeterinario, veterinario);

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(null, "Veterinario modificado correctamente.");

    } catch (NumberFormatException e) {
        // Si el ID no es un número válido, mostrar un mensaje de error
        JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de veterinario válido.");
    } catch (SQLException e) {
        // Manejar cualquier error relacionado con la base de datos
        JOptionPane.showMessageDialog(null, "Error al modificar el veterinario.");
    }
}


    public static void eliminarVeterinario() {
    // Solicitar el ID del veterinario a eliminar
    String idVeterinarioStr = JOptionPane.showInputDialog("Introduce el ID del veterinario a eliminar:");

    // Validar si el usuario presionó "Cancelar" o dejó el campo vacío
    if (idVeterinarioStr == null || idVeterinarioStr.trim().isEmpty()) {
        // Si el valor es null o vacío, mostrar un mensaje y salir
        JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un ID de veterinario.");
        return;  // Salir del método sin hacer nada
    }

    try {
        // Convertir el ID a entero
        int idVeterinario = Integer.parseInt(idVeterinarioStr);

        // Obtener el veterinario de la base de datos
        VeterinarioDAO dao = new VeterinarioDAO();
        VeterinariosDTO veterinario = dao.findByPk(idVeterinario);

        // Verificar si el veterinario existe
        if (veterinario == null) {
            JOptionPane.showMessageDialog(null, "No se encontró ningún veterinario con ese ID.");
            return;  // Salir si no se encuentra el veterinario
        }

        // Confirmación de eliminación
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres eliminar este veterinario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.NO_OPTION) {
            return;  // Si el usuario cancela la eliminación, salir del método
        }

        // Eliminar el veterinario de la base de datos
        dao.deleteVeterinario(veterinario);

        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(null, "Veterinario eliminado correctamente.");

    } catch (NumberFormatException e) {
        // Si el ID no es un número válido, mostrar un mensaje de error
        JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de veterinario válido.");
    } catch (SQLException e) {
        // Manejar cualquier error relacionado con la base de datos
        JOptionPane.showMessageDialog(null, "Error al eliminar el veterinario, tiene mascotas asignadas");
    }
    }

    public static void verMascotasPorVeterinario() {
        // Solicitar el ID del veterinario
        String idVeterinarioStr = JOptionPane.showInputDialog("Introduce el ID del veterinario:");

        // Validar si el usuario presionó "Cancelar" o dejó el campo vacío
        if (idVeterinarioStr == null || idVeterinarioStr.trim().isEmpty()) {
            // Si el valor es null o vacío, mostrar un mensaje y salir
            JOptionPane.showMessageDialog(null, "Operación cancelada. No se ha proporcionado un ID de veterinario.");
            return;  // Salir del método sin hacer nada
        }

        try {
            // Convertir el ID a entero
            int idVeterinario = Integer.parseInt(idVeterinarioStr);

            // Obtener las mascotas tratadas por ese veterinario
            MascotasDAO dao = new MascotasDAO();
            List<MascotaDTO> mascotas = dao.getMascotasByVeterinarioId(idVeterinario);

            // Mostrar las mascotas encontradas
            if (mascotas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron mascotas tratadas por este veterinario.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (MascotaDTO mascota : mascotas) {
                    sb.append(mascota.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString(), "Mascotas tratadas por el veterinario", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Si el ID no es un número válido, mostrar un mensaje de error
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un ID de veterinario válido.");
        } catch (SQLException e) {
            // Manejar cualquier error relacionado con la base de datos
            JOptionPane.showMessageDialog(null, "Error al obtener las mascotas: ");
        }
    }
}
