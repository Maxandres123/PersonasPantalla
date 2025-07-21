import formularios.plnPrincipal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PersonasPantalla {
    public static void main(String[] args) {
        plnPrincipal ventana = new plnPrincipal();
        ventana.setContentPane(ventana.plnPrincipal);
        ventana.setSize(600, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}