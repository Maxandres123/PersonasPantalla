package clases;

public class clsContacto {
    private String nombre;
    private String direccion;
    private String provincia;
    private String canton;
    private String distrito;
    private String correoElectronico;

    // Constructor vacío
    public clsContacto() {}

    // Constructor completo
    public clsContacto(String nombre, String direccion, String provincia, String canton, String distrito, String correoElectronico) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.provincia = provincia;
        this.canton = canton;
        this.distrito = distrito;
        this.correoElectronico = correoElectronico;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    // Representación en texto
    @Override
    public String toString() {
        return String.format(
                "clsContacto{nombre='%s', direccion='%s', provincia='%s', canton='%s', distrito='%s', correoElectronico='%s'}",
                nombre, direccion, provincia, canton, distrito, correoElectronico
        );
    }

    // Método auxiliar para exportar como línea de texto
    public String toLine() {
        return String.join(";", nombre, direccion, provincia, canton, distrito, correoElectronico);
    }

    // Método auxiliar para importar desde línea de texto
    public static clsContacto fromLine(String line) {
        String[] partes = line.split(";");
        if (partes.length == 6) {
            return new clsContacto(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]);
        }
        return null;
    }
}
