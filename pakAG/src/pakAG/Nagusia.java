package pakAG;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class Nagusia extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private static HashMap<String, Erabiltzailea> baseDeDatosUsuarios = new HashMap<>();

    public Nagusia() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 691, 517);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelLogin = new JLabel("LOGIN");
        labelLogin.setBounds(317, 11, 63, 22);
        contentPane.add(labelLogin);

        JLabel labelErabiltzailea = new JLabel("Erabiltzailea");
        labelErabiltzailea.setBounds(230, 60, 116, 14);
        contentPane.add(labelErabiltzailea);

        JLabel labelPasahitza = new JLabel("Pasahitza");
        labelPasahitza.setBounds(240, 85, 74, 14);
        contentPane.add(labelPasahitza);

        textField = new JTextField();
        textField.setBounds(324, 57, 116, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(324, 82, 116, 20);
        contentPane.add(passwordField);

        JButton btnNewButton = new JButton("Sartu");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = textField.getText();
                String contraseña = new String(passwordField.getPassword());

                // Verificar credenciales
                if (verificarCredenciales(nombreUsuario, contraseña)) {
                	
                	 Kudeatu nuevaPagina = new Kudeatu();
                     nuevaPagina.setVisible(true);
                } else {
                    messageLabel.setText("Erabiltzaile edo pasahitza okerrak.");
                }
            }
        });
        btnNewButton.setBounds(291, 126, 89, 23);
        contentPane.add(btnNewButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(240, 160, 209, 25);
        contentPane.add(messageLabel);

        // Furgonetaren irudia.
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(Nagusia.class.getResource("/img/furgo.png")));
        lblNewLabel_3.setBounds(10, 206, 597, 261);
        contentPane.add(lblNewLabel_3);
    }

    public static boolean verificarCredenciales(String nombreUsuario, String contraseña) {
        Erabiltzailea usuario = baseDeDatosUsuarios.get(nombreUsuario);
        return usuario != null && usuario.getPasahitza().equals(contraseña);
    }

    // Método main
    public static void main(String[] args) {
    	
    	// Crear algunos usuarios y almacenarlos en la base de datos
        Erabiltzailea erabiltzailea1 = new Erabiltzailea("hodei", "pvlbtnse");
        baseDeDatosUsuarios.put(erabiltzailea1.getErabiltzaileIzena(), erabiltzailea1);
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Nagusia frame = new Nagusia();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
