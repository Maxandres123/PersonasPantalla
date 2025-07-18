package clases;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class clsUtils {

    /**
     * Carga contactos desde un archivo .txt
     * @param rutaArchivo Ruta del archivo
     * @return Lista de contactos
     */
    public static List<clsContacto> cargarContactos(String rutaArchivo) {
        List<clsContacto> lista = new ArrayList<>();
        try (BufferedReader lectura = new BufferedReader(new FileReader(rutaArchivo))) {
            String renglon;
            while ((renglon = lectura.readLine()) != null) {
                String[] partes = renglon.split(";");
                if (partes.length == 6) {
                    clsContacto contacto = new clsContacto(
                            partes[0], partes[1], partes[2],
                            partes[3], partes[4], partes[5]
                    );
                    lista.add(contacto);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer contactos: " + ex.getMessage());
        }
        return lista;
    }

    /**
     * Guarda una lista de contactos en archivo .txt
     * @param rutaArchivo Ruta del archivo
     * @param contactos Lista de contactos
     */
    public static void guardarContactos(String rutaArchivo, List<clsContacto> contactos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (clsContacto c : contactos) {
                writer.write(c.getNombre() + ";" +
                        c.getDireccion() + ";" +
                        c.getProvincia() + ";" +
                        c.getCanton() + ";" +
                        c.getDistrito() + ";" +
                        c.getCorreoElectronico());
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar contactos: " + ex.getMessage());
        }
    }

    /**
     * Llena un JComboBox con nombres de contactos
     */
    public static void llenarComboConContactos(JComboBox<String> combo, List<clsContacto> contactos) {
        combo.removeAllItems();
        for (clsContacto c : contactos) {
            combo.addItem(c.getNombre());
        }
    }
    /**
     * Lee ubicaciones (Provincia,Cantón,Distrito) desde archivo CSV y las guarda como objetos string[]
     */
    public static List<String[]> cargarUbicaciones(String rutaArchivo) {
        List<String[]> lista = new ArrayList<>();
        try (BufferedReader lectura = new BufferedReader(new FileReader(rutaArchivo))) {
            String renglon;
            while ((renglon = lectura.readLine()) != null) {
                String[] partes = renglon.split(",");
                if (partes.length == 3) {
                    lista.add(new String[]{partes[0].trim(), partes[1].trim(), partes[2].trim()});
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer ubicaciones: " + ex.getMessage());
        }
        return lista;
    }

    /**
     * Llena combo de provincias sin repetir
     */
    public static void llenarProvincias(JComboBox<String> combo, List<String[]> ubicaciones) {
        combo.removeAllItems();
        List<String> provinciasAgregadas = new ArrayList<>();
        for (String[] fila : ubicaciones) {
            String provincia = fila[0];
            if (!provinciasAgregadas.contains(provincia)) {
                provinciasAgregadas.add(provincia);
                combo.addItem(provincia);
            }
        }
    }

    /**
     * Llena combo de cantones para una provincia
     */
    public static void llenarCantones(JComboBox<String> combo, List<String[]> ubicaciones, String provinciaSeleccionada) {
        combo.removeAllItems();
        List<String> cantonesAgregados = new ArrayList<>();
        for (String[] fila : ubicaciones) {
            if (fila[0].equalsIgnoreCase(provinciaSeleccionada)) {
                String canton = fila[1];
                if (!cantonesAgregados.contains(canton)) {
                    cantonesAgregados.add(canton);
                    combo.addItem(canton);
                }
            }
        }
    }

    /**
     * Llena combo de distritos para una provincia y cantón
     */
    public static void llenarDistritos(JComboBox<String> combo, List<String[]> ubicaciones, String provincia, String canton) {
        combo.removeAllItems();
        for (String[] fila : ubicaciones) {
            if (fila[0].equalsIgnoreCase(provincia) && fila[1].equalsIgnoreCase(canton)) {
                combo.addItem(fila[2]);
            }
        }
    }

}
