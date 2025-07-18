package formularios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class frmEditarContacto {
    private JTextField txtEditNombre;
    private JComboBox cmbEditProvincia;
    private JComboBox cmbEditCanton;
    private JComboBox cmbEditDistrito;
    private JButton BtnEditEdit;
    private JPanel pnlEditarContacto;
    private JLabel lblEditNombre;
    private JLabel lblEditProvincia;
    private JLabel lblEditDistrito;
    private JLabel lblEditCanton;
    private JLabel lblEditAction;
    private JButton btnEditRegresar;

    public frmEditarContacto() {
        txtEditNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmbEditProvincia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmbEditCanton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cmbEditDistrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        BtnEditEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnEditRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pnlEditarContacto.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }
        });
    }
}
