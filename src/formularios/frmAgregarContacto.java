package formularios;

import clases.clsContacto;
import clases.clsUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class frmAgregarContacto {
    public JPanel pnlAgregarContacto;

    // Componentes del .form
    private JLabel lblNombre;
    private JLabel lblProvincia;
    private JLabel lblCanton;
    private JLabel lblDistrito;
    private JLabel lblConsulta;
    private JLabel lblAccion;
    private JLabel lblDireccion;
    private JLabel txtareaDireccion;

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextArea textArea1; // Dirección

    private JComboBox<String> cmbProvincia;
    private JComboBox<String> cmbCanton;
    private JComboBox<String> cmbDistrito;

    private JTable tlbMostrarPersona;

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnRegresar;

    // Lógica
    private boolean modoEdicion = false;
    private clsContacto contactoActual = null;
    private Runnable onGuardarCallback;
    private List<String[]> ubicaciones;

    public frmAgregarContacto(clsContacto contacto, Runnable onGuardarCallback) {
        this.onGuardarCallback = onGuardarCallback;
        ubicaciones = clsUtils.cargarUbicaciones("ubicacionesCR.txt");

        clsUtils.llenarProvincias(cmbProvincia, ubicaciones);

        cmbProvincia.addActionListener(e -> {
            String provincia = (String) cmbProvincia.getSelectedItem();
            clsUtils.llenarCantones(cmbCanton, ubicaciones, provincia);
            cmbDistrito.removeAllItems();
        });

        cmbCanton.addActionListener(e -> {
            String provincia = (String) cmbProvincia.getSelectedItem();
            String canton = (String) cmbCanton.getSelectedItem();
            clsUtils.llenarDistritos(cmbDistrito, ubicaciones, provincia, canton);
        });

        if (contacto != null) {
            modoEdicion = true;
            contactoActual = contacto;
            precargarDatos(contacto);
            btnAgregar.setText("Actualizar");
        } else {
            btnAgregar.setText("Agregar");
        }

        btnAgregar.addActionListener(e -> guardarContacto());

        btnEliminar.addActionListener(e -> eliminarContacto());

        btnRegresar.addActionListener(e -> cerrarVentana());
    }

    private void precargarDatos(clsContacto c) {
        txtNombre.setText(c.getNombre());
        cmbProvincia.setSelectedItem(c.getProvincia());
        clsUtils.llenarCantones(cmbCanton, ubicaciones, c.getProvincia());
        cmbCanton.setSelectedItem(c.getCanton());
        clsUtils.llenarDistritos(cmbDistrito, ubicaciones, c.getProvincia(), c.getCanton());
        cmbDistrito.setSelectedItem(c.getDistrito());
        textArea1.setText(c.getDireccion());
        txtEmail.setText(c.getCorreoElectronico());
    }

    private void guardarContacto() {
        String nombre = txtNombre.getText().trim();
        String direccion = textArea1.getText().trim();
        String correo = txtEmail.getText().trim();

        Object provinciaSel = cmbProvincia.getSelectedItem();
        Object cantonSel = cmbCanton.getSelectedItem();
        Object distritoSel = cmbDistrito.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "El nombre no puede estar vacío.");
            return;
        }

        if (provinciaSel == null || cantonSel == null || distritoSel == null) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "Debes seleccionar provincia, cantón y distrito.");
            return;
        }

        if (!correo.isEmpty() && !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "Correo electrónico inválido.");
            return;
        }

        String provincia = provinciaSel.toString();
        String canton = cantonSel.toString();
        String distrito = distritoSel.toString();

        clsContacto nuevo = new clsContacto(nombre, direccion, provincia, canton, distrito, correo);

        List<clsContacto> lista = clsUtils.cargarContactos("agenda.txt");
        if (modoEdicion) {
            lista.removeIf(c -> c.getNombre().equalsIgnoreCase(contactoActual.getNombre()));
        }

        lista.add(nuevo);
        clsUtils.guardarContactos("agenda.txt", lista);

        if (onGuardarCallback != null) {
            onGuardarCallback.run();
        }

        cerrarVentana();
    }

    private void eliminarContacto() {
        if (modoEdicion && contactoActual != null) {
            List<clsContacto> lista = clsUtils.cargarContactos("agenda.txt");
            lista.removeIf(c -> c.getNombre().equalsIgnoreCase(contactoActual.getNombre()));
            clsUtils.guardarContactos("agenda.txt", lista);
            if (onGuardarCallback != null) {
                onGuardarCallback.run();
            }
            cerrarVentana();
        }
    }

    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(pnlAgregarContacto).dispose();
    }
}
