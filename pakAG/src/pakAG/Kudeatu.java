package pakAG;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.CardLayout;

public class Kudeatu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Kudeatu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1068, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Botoiak dauden panela.
		JPanel botoiPanela = new JPanel();
		botoiPanela.setBounds(31, 129, 184, 345);
		contentPane.add(botoiPanela);
		botoiPanela.setLayout(null);

		// Informazioa erakusten den panela.
		JPanel informazioPanela = new JPanel();
		informazioPanela.setBounds(238, 82, 804, 453);
		contentPane.add(informazioPanela);
		informazioPanela.setLayout(new CardLayout(0, 0));

		// Kudeatu izenburua.
		JLabel kudeatuLabel = new JLabel("KUDEATU");
		kudeatuLabel.setFont(new Font("Verdana", Font.BOLD, 35));
		kudeatuLabel.setBounds(429, 11, 193, 45);
		contentPane.add(kudeatuLabel);

		// Banatzaileak Kudeatu botoia.
		JButton bantzaileaKudeatu = new JButton("Banatzaileak Kudeatu");
		bantzaileaKudeatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BanatzaileakKudeatu banatzaileak = new BanatzaileakKudeatu();
				banatzaileak.BanatzaileakKud(informazioPanela);
				informazioPanela.revalidate();
			}
		});
		bantzaileaKudeatu.setBounds(11, 30, 163, 42);
		botoiPanela.add(bantzaileaKudeatu);

		// Paketeak Kudeatu botoia.
		JButton paketeakKudeatuBotoia = new JButton("Paketeak Kudeatu");
		paketeakKudeatuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaketeakKudeatu paketeak = new PaketeakKudeatu();
				paketeak.PaketeakKud(informazioPanela);
				informazioPanela.revalidate();
			}
		});
		paketeakKudeatuBotoia.setBounds(10, 83, 164, 42);
		botoiPanela.add(paketeakKudeatuBotoia);

		// Pakete Historiala botoia.
		JButton paketeHistorialaBotoia = new JButton("Pakete Historiala");
		paketeHistorialaBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaketeHistoriala paketeHis = new PaketeHistoriala();
				paketeHis.PaketeHis(informazioPanela);
				informazioPanela.revalidate();
			}
		});
		paketeHistorialaBotoia.setBounds(10, 189, 164, 42);
		botoiPanela.add(paketeHistorialaBotoia);

		// Banatzaile Historiala botoia.
		JButton banatzaileHistorialaBotoia = new JButton("Banatzaile Historiala");
		banatzaileHistorialaBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BanatzaileHistoriala banatzaileHis = new BanatzaileHistoriala();
				banatzaileHis.BanatzaileHis(informazioPanela);
				informazioPanela.revalidate();
			}
		});
		banatzaileHistorialaBotoia.setBounds(10, 136, 164, 42);
		botoiPanela.add(banatzaileHistorialaBotoia);

		// Saioa Itxi botoia.
		JButton saioaItxiBotoia = new JButton("Saioa Itxi");
		saioaItxiBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				Nagusia hasieraOrria = new Nagusia();
				hasieraOrria.setVisible(true);
				dispose();
			}
		});
		saioaItxiBotoia.setBounds(47, 282, 89, 23);
		botoiPanela.add(saioaItxiBotoia);

	}
}