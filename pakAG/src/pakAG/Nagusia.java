package pakAG;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Nagusia extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField erabiltzaileaField;
    private JPasswordField pasahitzaField;
    private JLabel messageLabel;

    public Nagusia() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1068, 593);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel labelLogin = new JLabel("LOGIN");
        labelLogin.setBounds(484, 22, 63, 22);
        contentPane.add(labelLogin);

        JLabel labelErabiltzailea = new JLabel("Erabiltzailea");
        labelErabiltzailea.setBounds(397, 71, 116, 14);
        contentPane.add(labelErabiltzailea);

        JLabel labelPasahitza = new JLabel("Pasahitza");
        labelPasahitza.setBounds(407, 96, 74, 14);
        contentPane.add(labelPasahitza);

        erabiltzaileaField = new JTextField();
        erabiltzaileaField.setBounds(491, 68, 116, 20);
        contentPane.add(erabiltzaileaField);
        erabiltzaileaField.setColumns(10);

        pasahitzaField = new JPasswordField();
        pasahitzaField.setBounds(491, 93, 116, 20);
        contentPane.add(pasahitzaField);

        JButton btnNewButton = new JButton("Sartu");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {          	
                String erabiltzailea = erabiltzaileaField.getText();
                String pasahitza = new String(pasahitzaField.getPassword());

                if (dbKontsultak.saioaHasi(erabiltzailea, pasahitza)) {            	
                	 Kudeatu kudeatu = new Kudeatu();
                     kudeatu.setVisible(true);
                } else {
                    messageLabel.setText("<html><font color = 'red'> Erabiltzaile edo pasahitza okerrak.</font></html>");
                }
                dispose();
            }
        });
        btnNewButton.setBounds(458, 137, 89, 23);
        contentPane.add(btnNewButton);

        // Erabiltzailea edo pasahitza gaizki jartzen bada mezua hemen aterako da.
        messageLabel = new JLabel("");
        messageLabel.setBounds(407, 171, 209, 25);
        contentPane.add(messageLabel);

        // Furgonetaren irudia.
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(Nagusia.class.getResource("/img/furgo.png")));
        lblNewLabel_3.setBounds(187, 196, 597, 261);
        contentPane.add(lblNewLabel_3);
    }


    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	UIManager.setLookAndFeel(new FlatLightLaf());
                    Nagusia frame = new Nagusia();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
