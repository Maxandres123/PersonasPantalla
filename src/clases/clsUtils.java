package clases;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class clsUtils {

    public List<clsContacto> personas = new ArrayList<>();

    public void cargarClientes(JComboBox cmb, String archivo)
    {
        try (BufferedReader lectura = new BufferedReader(new FileReader(archivo)))
        {
            String renglon;
            while ((renglon = lectura.readLine())!= null)
            {
                cmb.addItem(renglon);
            }
        }catch(IOException ex) {
            System.out.println("Error al intentar manipular el archivo "+ex.getMessage());
        }
    }

    clsProducto producto= new clsProducto();

    public void cargarProductos(JComboBox cmb, String archivo)
    {

        try (BufferedReader lectura = new BufferedReader(new FileReader(archivo)))
        {
            String renglon;
            while ((renglon = lectura.readLine())!= null)
            {
                String[] parte= renglon.split(",");
                producto=new clsProducto();
                producto.setNombre(parte[0].toString());
                producto.setCantidad(Integer.parseInt(parte[1].toString()));

                productos.add(producto);

                cmb.addItem(producto.getNombre());
            }
        }catch(IOException ex) {
            System.out.println("Error al intentar manipular el archivo"+ex.getMessage());
        }
    }

}
