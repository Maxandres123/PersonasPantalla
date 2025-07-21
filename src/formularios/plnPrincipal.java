package formularios;

import clases.clsAgenda;
import clases.clsContacto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class plnPrincipal extends JFrame {
    public JPanel plnPrincipal;

    private JLabel lblBusqueda;
    private JTextField txtPincipalBusqueda;
    private JLabel lblPrincipalAction;
    private JButton btnAgregar;
    private JButton btnPrincipalEditar;
    private JTable tblPrincipalMostrarPersona;

    private clsAgenda agenda;
    private DefaultTableModel modelo;
    private JPanel panelInicial;

    public plnPrincipal() {
        setTitle("Agenda Azul");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        panelInicial = plnPrincipal;
        setContentPane(panelInicial);

        initAgenda();
        initTabla();
        initListeners();
    }

    private void initAgenda() {
        agenda = new clsAgenda();
        agenda.cargarDesdeArchivo("agenda.txt");
    }

    private void initTabla() {
        modelo = new DefaultTableModel(
                new String[]{"Nombre", "Dirección", "Provincia", "Cantón", "Distrito", "Correo", "Teléfono"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblPrincipalMostrarPersona.setModel(modelo);

        if (tblPrincipalMostrarPersona.getColumnCount() >= 8) {
            tblPrincipalMostrarPersona.getColumnModel().getColumn(6).setPreferredWidth(160);
            tblPrincipalMostrarPersona.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        actualizarTabla(agenda.listarContactos());
    }

    private void actualizarTabla(List<clsContacto> lista) {
        modelo.setRowCount(0);
        for (clsContacto c : lista) {
            modelo.addRow(new Object[]{
                    c.getNombre(), c.getDireccion(), c.getProvincia(),
                    c.getCanton(), c.getDistrito(), c.getCorreoElectronico(), c.getTelefono()
            });
        }
    }

    private void initListeners() {
        btnAgregar.addActionListener(e -> abrirFormularioAgregar());
        btnPrincipalEditar.addActionListener(e -> abrirFormularioEditar());

        txtPincipalBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarBusqueda();
            }
        });
    }

    private void abrirFormularioAgregar() {
        frmAgregarContacto formulario = new frmAgregarContacto(null, () -> {
            agenda.cargarDesdeArchivo("agenda.txt");
            actualizarTabla(agenda.listarContactos());
            volverAlPanelPrincipal();
        });

        cambiarContenido(formulario.pnlAgregarContacto);
    }

    private void abrirFormularioEditar() {
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
            volverAlPanelPrincipal();
        });

        cambiarContenido(formulario.pnlAgregarContacto);
    }

    private void filtrarBusqueda() {
        String texto = txtPincipalBusqueda.getText().trim().toLowerCase();

        List<clsContacto> filtrados = agenda.listarContactos().stream()
                .filter(c ->
                        c.getNombre().toLowerCase().contains(texto) ||
                                c.getCorreoElectronico().toLowerCase().contains(texto) ||
                                c.getTelefono().toLowerCase().contains(texto)
                ).toList();

        actualizarTabla(filtrados);

        if (!texto.isEmpty() && filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(plnPrincipal, "No se encontraron contactos con ese criterio.");
        }
    }

    private void cambiarContenido(JPanel nuevoPanel) {
        setContentPane(nuevoPanel);
        revalidate();
        repaint();
    }

    private void volverAlPanelPrincipal() {
        cambiarContenido(panelInicial);
    }
}
