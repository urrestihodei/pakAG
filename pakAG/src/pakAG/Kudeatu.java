package pakAG;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Kudeatu extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Kudeatu dialog = new Kudeatu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Kudeatu() {
		setBounds(100, 100, 691, 517);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("KUDEATU");
		lblNewLabel.setFont(new Font("Source Serif Pro Semibold", Font.BOLD, 18));
		lblNewLabel.setBounds(292, 21, 98, 35);
		contentPanel.add(lblNewLabel);
		
		
		JButton cancelButton = new JButton("Saioa Itxi");
		cancelButton.setBounds(292, 444, 98, 23);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cerrar la página actual
		        dispose();
		        // Abrir la página anterior
		        Nagusia paginaAnterior = new Nagusia();
		        paginaAnterior.setVisible(true);
			}
		});
		
		contentPanel.setLayout(null);
		contentPanel.add(cancelButton);
		
		
		JButton btnNewButton = new JButton("Banatzaileak Kudeatu");
		btnNewButton.setBounds(10, 108, 163, 35);
		contentPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Paketeak Kudeatu");
		btnNewButton_1.setBounds(10, 166, 163, 35);
		contentPanel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Historiala");
		btnNewButton_2.setBounds(10, 225, 163, 35);
		contentPanel.add(btnNewButton_2);
		
		
	}
}
