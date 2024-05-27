package pakAG;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BanatzaileakKudeatu {
	
	public void BanatzaileakKud(JPanel informazioPanela) {
		
		JPanel banatzaileaPanel = new JPanel(new BorderLayout());
		informazioPanela.add(banatzaileaPanel, "banatzaileaPanel");
		CardLayout cardLayout = (CardLayout) informazioPanela.getLayout();
		cardLayout.show(informazioPanela, "banatzaileaPanel");

		banatzaileaPanel.add(new JLabel("Banatzaileak:"), BorderLayout.NORTH);

		List<Langilea> banatzaileaList = dbKontsultak.getLangileak();
		String[] columnNames = { "NAN", "Izena eta Abizena", "Telefonoa" }; //Taulako zutabeak
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		for (Langilea langilea : banatzaileaList) { //Taulan informazioa sartu
			Object[] row = { langilea.getLangileaNAN(), langilea.getIzena() + " " + langilea.getAbizena(),
					langilea.getTelefonoa(), };
			tableModel.addRow(row);
		}

		JTable banatzaileaTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(banatzaileaTable);
		banatzaileaPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel botoiakPanel = new JPanel(new BorderLayout());
		banatzaileaPanel.add(botoiakPanel, BorderLayout.SOUTH);

		JButton sartuBtn = new JButton("Sartu");
		botoiakPanel.add(sartuBtn, BorderLayout.EAST);

		JButton ezabatuBtn = new JButton("Ezabatu");
		botoiakPanel.add(ezabatuBtn, BorderLayout.CENTER);

		JButton editatuBtn = new JButton("Editatu");
		botoiakPanel.add(editatuBtn, BorderLayout.WEST);

		sartuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JPanel panel = new JPanel(new GridLayout(0, 2));
				panel.add(new JLabel("NAN (8 digitu + letra mayuskula):"));
				JTextField nanField = new JTextField(10);
				panel.add(nanField);

				panel.add(new JLabel("Izena:"));
				JTextField izenaField = new JTextField(10);
				panel.add(izenaField);

				panel.add(new JLabel("Abizena:"));
				JTextField abizenaField = new JTextField(10);
				panel.add(abizenaField);

				panel.add(new JLabel("Telefonoa:"));
				JTextField telefonoaField = new JTextField(10);
				panel.add(telefonoaField);

				panel.add(new JLabel("Erabiltzailea:"));
				JTextField erabiltzaileaField = new JTextField(10);
				panel.add(erabiltzaileaField);

				panel.add(new JLabel("Pasahitza:"));
				JTextField pasahitzaField = new JTextField(10);
				panel.add(pasahitzaField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Informazioa sartu",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					String nan = nanField.getText().toUpperCase();
					String izena = izenaField.getText();
					String abizena = abizenaField.getText();
					String erabiltzailea = erabiltzaileaField.getText();
					String pasahitza = pasahitzaField.getText();
					String telefonoa = telefonoaField.getText();

					if (nan.isEmpty() || izena.isEmpty() || abizena.isEmpty() || erabiltzailea.isEmpty()
							|| pasahitza.isEmpty() || telefonoa.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Eremu guztiak ez daude beteta.");
					} else {
						if (nan.matches("\\d{8}[A-Z]") && !izena.matches(".*\\d.*")
								&& !abizena.matches(".*\\d.*") && telefonoa.matches("\\d{9}")) {
							if (dbKontsultak.erablitzaileaBadago(erabiltzailea)) {
								JOptionPane.showMessageDialog(null,
										"Erabiltzailea badago, sartu beste erabiltzaile bat.");
							} else {
								Langilea erab = new Langilea(nan, izena, abizena, erabiltzailea, pasahitza,
										telefonoa);
								dbKontsultak.langileaSartu(erab);
								Object[] row = { nan, izena + " " + abizena, telefonoa };
								tableModel.addRow(row);
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Datuak ez dira zuzenak. Mesedez, ziurtatu sartutako datuak.");
						}
					}
				}
				banatzaileaPanel.revalidate();
			}
		});

		editatuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = banatzaileaTable.getSelectedRow();
				if (selectedRow != -1) {
					String nan = (String) tableModel.getValueAt(selectedRow, 0);
					Langilea selectedLangilea = null;
					for (Langilea langilea : banatzaileaList) {
						if (langilea.getLangileaNAN().equals(nan)) {
							selectedLangilea = langilea;
							break;
						}
					}

					if (selectedLangilea != null) {
						String izena = selectedLangilea.getIzena();
						String abizena = selectedLangilea.getAbizena();
						String telefonoa = selectedLangilea.getTelefonoa();
						String erabiltzailea = selectedLangilea.getErabiltzailea();
						String pasahitza = selectedLangilea.getPasahitza();

						JFrame editFrame = new JFrame("Editatu langilea");
						editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						JPanel panel = new JPanel(new GridLayout(0, 2));

						panel.add(new JLabel("NAN (8 digitu + letra mayuskula):"));
						JTextField nanField = new JTextField(10);
						nanField.setText(nan);
						panel.add(nanField);

						panel.add(new JLabel("Izena:"));
						JTextField izenaField = new JTextField(10);
						izenaField.setText(izena);
						panel.add(izenaField);

						panel.add(new JLabel("Abizena:"));
						JTextField abizenaField = new JTextField(10);
						abizenaField.setText(abizena);
						panel.add(abizenaField);

						panel.add(new JLabel("Telefonoa:"));
						JTextField telefonoaField = new JTextField(10);
						telefonoaField.setText(telefonoa);
						panel.add(telefonoaField);

						panel.add(new JLabel("Erabiltzailea:"));
						JTextField erabiltzaileaField = new JTextField(10);
						erabiltzaileaField.setText(erabiltzailea);
						panel.add(erabiltzaileaField);

						panel.add(new JLabel("Pasahitza:"));
						JTextField pasahitzaField = new JTextField(10);
						pasahitzaField.setText(pasahitza);
						panel.add(pasahitzaField);

						JPanel buttonPanel = new JPanel(new BorderLayout());
						JButton BtnEditatu = new JButton("Editatu");
						
						BtnEditatu.addActionListener(actionEvent -> {
							
							String nanBerria = nanField.getText();
							String izenaBerria = izenaField.getText();
							String abizenaBerria = abizenaField.getText();
							String telefonoaBerria = telefonoaField.getText();
							String erabiltzaileaBerria = erabiltzaileaField.getText();
							String pasahitzaBerria = pasahitzaField.getText();

							dbKontsultak.langileaEditatu(nan, izenaBerria, abizenaBerria, telefonoaBerria,
									erabiltzaileaBerria, pasahitzaBerria, nanBerria);
							JOptionPane.showMessageDialog(null, "Erabiltzailea editatu da.");

							tableModel.setValueAt(nanBerria, selectedRow, 0);
							tableModel.setValueAt(izenaBerria + " " + abizenaBerria, selectedRow, 1);
							tableModel.setValueAt(telefonoaBerria, selectedRow, 2);

							editFrame.dispose();
						});
						buttonPanel.add(BtnEditatu, BorderLayout.CENTER);

						editFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

						editFrame.getContentPane().add(panel);
						editFrame.pack();
						editFrame.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Langilea hautatu behar duzu editatzeko.");
				}
				banatzaileaPanel.revalidate();
			}
		});

		ezabatuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = banatzaileaTable.getSelectedRow();
				if (selectedRow != -1) {
					String nan = (String) tableModel.getValueAt(selectedRow, 0);

					int confirm = JOptionPane.showConfirmDialog(null, "Langilea ezabatu nahi duzu?");
					if (confirm == JOptionPane.YES_OPTION) {
						dbKontsultak.langileaEzabatu(nan);
						tableModel.removeRow(selectedRow); //tauleko lerroa ezabatu
						JOptionPane.showMessageDialog(null, "Langilea ezabatu da.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Langilea hautatu behar duzu ezabatzeko.");
				}
				banatzaileaPanel.revalidate();
			}
		});
		
	}

}