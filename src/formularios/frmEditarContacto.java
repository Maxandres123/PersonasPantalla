package formularios;

import clases.clsContacto;
import clases.clsUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class frmEditarContacto {
    public JPanel pnlEditarContacto;

    // Componentes vinculados desde el .form
    private JLabel lblEditNombre;
    private JLabel lblEditProvincia;
    private JLabel lblEditDistrito;
    private JLabel lblEditCanton;
    private JLabel lblEditAction;
    private JLabel lblEditarDireccion;
    private JLabel lblEditarEmail;

    private JTextField txtEditNombre;
    private JTextField textField1; // Email

    private JComboBox<String> cmbEditProvincia;
    private JComboBox<String> cmbEditCanton;
    private JComboBox<String> cmbEditDistrito;

    private JTextArea textArea1; // Dirección

    private JButton BtnEditEdit;
    private JButton btnEditRegresar;
    private JButton btnEliminar;

    private clsContacto contactoActual = null;
    private Runnable onGuardarCallback;

    public frmEditarContacto() {
        BtnEditEdit.addActionListener(e -> editarContacto());
        btnEliminar.addActionListener(e -> eliminarContacto());
        btnEditRegresar.addActionListener(e -> cerrarVentana());

        txtEditNombre.addActionListener(e -> buscarContacto());
    }

    public void setOnGuardarCallback(Runnable callback) {
        this.onGuardarCallback = callback;
    }

    private void buscarContacto() {
        String nombreBuscado = txtEditNombre.getText().trim();
        clsContacto contacto = clsUtils.cargarContactos("agenda.txt").stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreBuscado))
                .findFirst()
                .orElse(null);

        if (contacto != null) {
            contactoActual = contacto;
            cmbEditProvincia.setSelectedItem(contacto.getProvincia());
            cmbEditCanton.setSelectedItem(contacto.getCanton());
            cmbEditDistrito.setSelectedItem(contacto.getDistrito());
            textArea1.setText(contacto.getDireccion());
            textField1.setText(contacto.getCorreoElectronico());
        } else {
            JOptionPane.showMessageDialog(pnlEditarContacto, "Contacto no encontrado.");
        }
    }

    private void editarContacto() {
        if (contactoActual == null) {
            JOptionPane.showMessageDialog(pnlEditarContacto, "Debe buscar un contacto primero.");
            return;
        }

        contactoActual.setProvincia(cmbEditProvincia.getSelectedItem().toString());
        contactoActual.setCanton(cmbEditCanton.getSelectedItem().toString());
        contactoActual.setDistrito(cmbEditDistrito.getSelectedItem().toString());
        contactoActual.setDireccion(textArea1.getText().trim());
        contactoActual.setCorreoElectronico(textField1.getText().trim());

        List<clsContacto> lista = clsUtils.cargarContactos("agenda.txt");
        lista.removeIf(c -> c.getNombre().equalsIgnoreCase(contactoActual.getNombre()));
        lista.add(contactoActual);
        clsUtils.guardarContactos("agenda.txt", lista);

        if (onGuardarCallback != null) {
            onGuardarCallback.run();
        }

        JOptionPane.showMessageDialog(pnlEditarContacto, "Contacto actualizado.");
        cerrarVentana();
    }

    private void eliminarContacto() {
        if (contactoActual == null) {
            JOptionPane.showMessageDialog(pnlEditarContacto, "Debe buscar un contacto primero.");
            return;
        }

        List<clsContacto> lista = clsUtils.cargarContactos("agenda.txt");
        lista.removeIf(c -> c.getNombre().equalsIgnoreCase(contactoActual.getNombre()));
        clsUtils.guardarContactos("agenda.txt", lista);

        if (onGuardarCallback != null) {
            onGuardarCallback.run();
        }

        JOptionPane.showMessageDialog(pnlEditarContacto, "Contacto eliminado.");
        cerrarVentana();
    }

    private void cerrarVentana() {
        SwingUtilities.getWindowAncestor(pnlEditarContacto).dispose();
    }
}
