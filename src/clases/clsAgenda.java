package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class clsAgenda {
    private final List<clsContacto> contactos = new ArrayList<>();

    // Operaciones básicas
    public void agregarContacto(clsContacto contacto) {
        contactos.add(contacto);
    }

    public void eliminarContacto(String nombre) {
        contactos.removeIf(c -> c.getNombre().equalsIgnoreCase(nombre));
    }

    public clsContacto buscarContacto(String nombre) {
        return contactos.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public List<clsContacto> listarContactos() {
        return new ArrayList<>(contactos); // Retorno seguro
    }

    // Persistencia
    public void guardarEnArchivo(String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (clsContacto c : contactos) {
                writer.write(c.toLine());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al guardar contactos: " + ex.getMessage());
        }
    }

    public void cargarDesdeArchivo(String rutaArchivo) {
        contactos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                clsContacto contacto = clsContacto.fromLine(linea);
                if (contacto != null) {
                    contactos.add(contacto);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar contactos: " + ex.getMessage());
        }
    }
}
