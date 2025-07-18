package formularios;

import clases.clsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class frmPersona {
    public JPanel frmPanel;
    private JTextField txtNombre;
    private JComboBox cmbCanton;
    private JComboBox cmbProvincia;
    private JLabel lblNombre;
    private JLabel lblProvincia;
    private JLabel lblCanton;
    private JLabel lblDistrito;
    private JComboBox cmbDistrito;
    private JTable tlbMostrarPersona;
    private JLabel lblConsulta;
    public clsUtils util= new clsUtils();
    public DefaultTableModel modelo = new DefaultTableModel();

    public frmPersona() {
        txtNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmbProvincia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmbCanton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tlbMostrarPersona.addKeyListener(new KeyAdapter() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
    }
}
