package formularios;

import clases.clsAgenda;
import clases.clsContacto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class plnPrincipal extends JFrame {
    public JPanel plnPrincipal;

    // Componentes visuales del .form
    private JLabel lblBusqueda;
    private JTextField txtPincipalBusqueda;
    private JLabel lblPrincipalAction;
    private JButton btnAgregar;
    private JButton btnPrincipalEditar;
    private JTable tblPrincipalMostrarPersona;

    private clsAgenda agenda;
    private DefaultTableModel modelo;

    public plnPrincipal() {
        // Si tienes custom-create: createUIComponents(); (ya incluido si el botón se declara manualmente)
        initAgenda();
        initTabla();
        initListeners();
    }

    private void initAgenda() {
        agenda = new clsAgenda();
        agenda.cargarDesdeArchivo("agenda.txt");
    }

    private void initTabla() {
        modelo = new DefaultTableModel(new String[]{"Nombre", "Dirección", "Provincia", "Cantón", "Distrito", "Correo"}, 0);
        tblPrincipalMostrarPersona.setModel(modelo);
        actualizarTabla(agenda.listarContactos());
    }

    private void actualizarTabla(List<clsContacto> lista) {
        modelo.setRowCount(0);
        for (clsContacto c : lista) {
            modelo.addRow(new Object[]{
                    c.getNombre(), c.getDireccion(), c.getProvincia(),
                    c.getCanton(), c.getDistrito(), c.getCorreoElectronico()
            });
        }
    }

    private void initListeners() {
        // 🔧 Acción botón Agregar
        btnAgregar.addActionListener(e -> {
            frmAgregarContacto formulario = new frmAgregarContacto(null, () -> {
                agenda.cargarDesdeArchivo("agenda.txt");
                actualizarTabla(agenda.listarContactos());
            });

            JFrame ventana = new JFrame("Agregar Contacto");
            ventana.setContentPane(formulario.pnlAgregarContacto);
            ventana.setSize(500, 500);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });

        // 🔧 Acción botón Editar
        btnPrincipalEditar.addActionListener(e -> {
            int fila = tblPrincipalMostrarPersona.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(plnPrincipal, "Seleccione un contacto para editar.");
                return;
            }

            String nombre = modelo.getValueAt(fila, 0).toString();
            clsContacto contacto = agenda.buscarContacto(nombre);
            if (contacto == null) {
                JOptionPane.showMessageDialog(plnPrincipal, "No se encontró el contacto.");
                return;
            }

            frmAgregarContacto formulario = new frmAgregarContacto(contacto, () -> {
                agenda.cargarDesdeArchivo("agenda.txt");
                actualizarTabla(agenda.listarContactos());
            });

            JFrame ventana = new JFrame("Editar Contacto");
            ventana.setContentPane(formulario.pnlAgregarContacto);
            ventana.setSize(500, 500);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });

        // 🔍 Filtro de búsqueda por nombre (enter o tipeo)
        txtPincipalBusqueda.addActionListener(e -> filtrarPorNombre());
        txtPincipalBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarPorNombre();
            }
        });
    }

    private void filtrarPorNombre() {
        String texto = txtPincipalBusqueda.getText().trim();
        if (texto.isEmpty()) {
            actualizarTabla(agenda.listarContactos());
            return;
        }

        List<clsContacto> filtrados = agenda.listarContactos().stream()
                .filter(c -> c.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .toList();

        actualizarTabla(filtrados);
    }
}
