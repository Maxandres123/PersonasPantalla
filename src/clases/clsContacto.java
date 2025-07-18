package clases;

public class clsContacto {
    private String nombre, direccion, provincia, canton;

    public clsContacto() {
    }

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

    @Override
    public String toString() {
        return "clsPersona{" +
                "canton='" + canton + '\'' +
                ", provincia='" + provincia + '\'' +
                ", direccion='" + direccion + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
