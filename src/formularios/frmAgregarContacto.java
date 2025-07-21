package formularios;

import clases.clsContacto;
import clases.clsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class frmAgregarContacto {
    public JPanel pnlAgregarContacto;

    // Componentes visuales
    private JLabel lblNombre;
    private JLabel lblProvincia;
    private JLabel lblCanton;
    private JLabel lblDistrito;
    private JLabel lblConsulta;
    private JLabel lblAccion;
    private JLabel lblDireccion;
    private JLabel lblAgregarTelefono;
    private JLabel lblAgregarEmail;

    private JTextField txtNombre;
    private JTextArea textAreaDireccion;

    private JComboBox<String> cmbProvincia;
    private JComboBox<String> cmbCanton;
    private JComboBox<String> cmbDistrito;

    private JTable tlbMostrarPersona;

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnRegresar;
    private JLabel txtareaDireccion;
    private JTextField txtAgregarEmail;
    private JTextField txtAgregarTelefono;
    private DefaultTableModel modeloTabla;


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
        tlbMostrarPersona.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                inicializarTablaPersonas();

            }
        });
    }
        private void inicializarTablaPersonas() {
            modeloTabla = new DefaultTableModel(
                    new String[]{"Nombre", "Dirección", "Provincia", "Cantón", "Distrito", "Correo", "Teléfono"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // ❌ tabla solo visual
                }
            };

            tlbMostrarPersona.setModel(modeloTabla);

        List<clsContacto> lista = clsUtils.cargarContactos("agenda.txt");
        modeloTabla.setRowCount(0);

        for (clsContacto c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getNombre(), c.getDireccion(), c.getProvincia(),
                    c.getCanton(), c.getDistrito(), c.getCorreoElectronico(), c.getTelefono()
            });
        }

        if (tlbMostrarPersona.getColumnCount() >= 8) {
            tlbMostrarPersona.getColumnModel().getColumn(6).setPreferredWidth(160); // Correo
            tlbMostrarPersona.getColumnModel().getColumn(7).setPreferredWidth(100); // Teléfono
        }
    }



    private void precargarDatos(clsContacto c) {
        txtNombre.setText(c.getNombre());
        txtAgregarEmail.setText(c.getCorreoElectronico());
        txtAgregarTelefono.setText(c.getTelefono());
        textAreaDireccion.setText(c.getDireccion());

        cmbProvincia.setSelectedItem(c.getProvincia());
        clsUtils.llenarCantones(cmbCanton, ubicaciones, c.getProvincia());
        cmbCanton.setSelectedItem(c.getCanton());
        clsUtils.llenarDistritos(cmbDistrito, ubicaciones, c.getProvincia(), c.getCanton());
        cmbDistrito.setSelectedItem(c.getDistrito());
    }

    private void guardarContacto() {
        String nombre = txtNombre.getText().trim();
        String correo = txtAgregarEmail.getText().trim();
        String telefono = txtAgregarTelefono.getText().trim();
        String direccion = textAreaDireccion.getText().trim();

        Object provinciaObj = cmbProvincia.getSelectedItem();
        Object cantonObj = cmbCanton.getSelectedItem();
        Object distritoObj = cmbDistrito.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "El nombre no puede estar vacío.");
            return;
        }

        if (provinciaObj == null || cantonObj == null || distritoObj == null) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "Selecciona provincia, cantón y distrito.");
            return;
        }

        if (!correo.isEmpty() && !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "Correo electrónico inválido.");
            return;
        }

        if (telefono.isEmpty() || !telefono.matches("^\\d{4}-\\d{4}$")) {
            JOptionPane.showMessageDialog(pnlAgregarContacto, "Teléfono inválido. Usa ####-####.");
            return;
        }

        String provincia = provinciaObj.toString();
        String canton = cantonObj.toString();
        String distrito = distritoObj.toString();

        clsContacto nuevo = new clsContacto(nombre, direccion, provincia, canton, distrito, correo, telefono);

        List<clsContacto> lista = clsUtils.cargarContactos("agenda.txt");

        if (modoEdicion) {
            lista.removeIf(c -> c.getNombre().equals(contactoActual.getNombre()));
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
        if (onGuardarCallback != null) {
            onGuardarCallback.run(); // ← esto ahora redibuja el panel principal
        }
    }

}
