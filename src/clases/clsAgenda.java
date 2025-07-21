package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class clsAgenda {
    private final List<clsContacto> contactos = new ArrayList<>();

    // ──────────────────────────────────────────────────────────────────────────────
    // 🧩 Operaciones básicas
    // ──────────────────────────────────────────────────────────────────────────────

    public void agregarContacto(clsContacto contacto) {
        if (contacto != null) contactos.add(contacto);
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
        return new ArrayList<>(contactos); // copia segura
    }

    // ──────────────────────────────────────────────────────────────────────────────
    // 💾 Persistencia
    // ──────────────────────────────────────────────────────────────────────────────

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
        List<String> errores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String error = validarLineaContacto(linea);
                if (error != null) {
                    errores.add("⚠️ " + error);
                    continue;
                }

                clsContacto contacto = clsContacto.fromLine(linea);
                if (contacto != null) {
                    contactos.add(contacto);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al cargar contactos: " + ex.getMessage());
        }

        if (!errores.isEmpty()) {
            System.out.println("Se encontraron " + errores.size() + " errores en el archivo:");
            errores.forEach(System.out::println);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────
    // 🔍 Validación de líneas de texto
    // ──────────────────────────────────────────────────────────────────────────────

    private String validarLineaContacto(String linea) {
        String[] partes = linea.split(";");
        if (partes.length != 7) return "Faltan campos (esperados 7): " + linea;

        String correo = partes[5];
        String telefono = partes[6];

        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Correo inválido: " + correo;
        }

        if (!telefono.matches("^\\d{4}-\\d{4}$")) {
            return "Teléfono inválido: " + telefono;
        }

        return null; // válido
    }
}
