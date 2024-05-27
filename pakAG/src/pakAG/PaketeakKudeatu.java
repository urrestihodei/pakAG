package pakAG;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PaketeakKudeatu {

	public void PaketeakKud(JPanel informazioPanela) {

		JPanel paketeaPanel = new JPanel(new BorderLayout());

		paketeaPanel.add(new JLabel("PAKETEAK KUDEATU:"), BorderLayout.NORTH);

		List<Paketea> paketeaList = dbKontsultak.getPaketeak();

		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Entrega Data");
		tableModel.addColumn("Helbidea");
		tableModel.addColumn("Bezeroa");
		tableModel.addColumn("Banatzailea");

		for (Paketea paketea : paketeaList) {// Lerroetan baloreak jarri
			tableModel.addRow(new Object[] { paketea.getID(), paketea.getEntrega_data(), paketea.getHelbidea(), paketea.getBezeroIzena(),
					paketea.getLangileaIzena() });
		}

		JTable table = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		paketeaPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel botoiakPanel = new JPanel(new BorderLayout());
		paketeaPanel.add(botoiakPanel, BorderLayout.SOUTH);

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
				panel.add(new JLabel("Idatzi paketearen id-a(zenabkiak bakarrik):"));
				JTextField idField = new JTextField(10);
				panel.add(idField);
				
				panel.add(new JLabel("Entrega data(UUUU/HH/EE):"));
				JTextField dataField = new JTextField(10);
				panel.add(dataField);

				panel.add(new JLabel("Bezeroa:"));
				JComboBox<String> bezeroaComboBox = new JComboBox<String>();
				panel.add(bezeroaComboBox);

				List<String> bezeroaList = dbKontsultak.getBezeroaIzena();

				for (String bezeroa : bezeroaList) {
					bezeroaComboBox.addItem(bezeroa);
				}

				panel.add(new JLabel("Banatzailea"));
				JComboBox<String> banatzaileaComboBox = new JComboBox<String>();
				panel.add(banatzaileaComboBox);

				List<String> banatzaileaList = dbKontsultak.getLangileIzenak();

				for (String banatzailea : banatzaileaList) {
					banatzaileaComboBox.addItem(banatzailea);
				}

				int result = JOptionPane.showConfirmDialog(null, panel, "Informazioa sartu",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					int id = 0;
					
					try {
			            id = Integer.parseInt(idField.getText());
			        } catch (NumberFormatException e1) {
			        	JOptionPane.showMessageDialog(null, "Zenbakiak bakarrik sar daitezke");
			        }
			            
					String data = dataField.getText().toUpperCase();
					String izenaBezeroa = (String) bezeroaComboBox.getSelectedItem();
					String nanBezeroa = "";
					String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
					String nanLangilea = "";

					String[] nanB = izenaBezeroa.split(",\\s*");
					if (nanB.length > 1) {
						nanBezeroa = nanB[1];
					} // Bezeroaren nan-a atera

					String[] nanL = izenaLangilea.split(",\\s*");
					if (nanL.length > 1) {
						nanLangilea = nanL[1];
					} // Langilearen nan-a atera

					if (data.isEmpty() || !data.matches("\\d{4}/\\d{2}/\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Dataren eremua ez dago beteta.");
					} else {
						Paketea pak = new Paketea(data, nanBezeroa, nanLangilea, id);
						dbKontsultak.paketeaSartu(pak);
					}
				}
				paketeaPanel.revalidate();
			}

		});

		editatuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();

				if (selectedRow != -1) { // aukeartutako lerroen baloreak ezarri
					String id = table.getValueAt(selectedRow, 0).toString();
					String entregaData = table.getValueAt(selectedRow, 1).toString();
					String bezeroa = table.getValueAt(selectedRow, 2).toString();
					String banatzailea = table.getValueAt(selectedRow, 3).toString();

					JFrame editatuFrame = new JFrame("Editatu Paketea");
					editatuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					JPanel panel = new JPanel(new GridLayout(0, 2));
					panel.add(new JLabel("Idatzi paketearen id-a(zenabkiak bakarrik):"));
					JTextField idField = new JTextField(10);
					idField.setText(id);
					panel.add(idField);
					
					panel.add(new JLabel("Entrega data (UUUU/HH/EE):"));
					JTextField dataField = new JTextField(10);
					dataField.setText(entregaData);
					panel.add(dataField);

					panel.add(new JLabel("Bezeroa:"));
					JComboBox<String> bezeroaComboBox = new JComboBox<String>();
					panel.add(bezeroaComboBox);

					List<String> bezeroaList = dbKontsultak.getBezeroaIzena();
					for (String bezeroaName : bezeroaList) {
						bezeroaComboBox.addItem(bezeroaName);
					}

					bezeroaComboBox.setSelectedItem(bezeroa);

					panel.add(new JLabel("Banatzailea:"));
					JComboBox<String> banatzaileaComboBox = new JComboBox<String>();
					panel.add(banatzaileaComboBox);

					List<String> banatzaileaList = dbKontsultak.getLangileIzenak();
					for (String banatzaileaName : banatzaileaList) {
						banatzaileaComboBox.addItem(banatzaileaName);
					}
					banatzaileaComboBox.setSelectedItem(banatzailea);

					JButton BtnEditatu = new JButton("Editatu");
					BtnEditatu.addActionListener(actionEvent -> {
						int oldId = Integer.parseInt(idField.getText());

						int newid = Integer.parseInt(idField.getText());
						String newData = dataField.getText().toUpperCase();
						String newBezeroa = (String) bezeroaComboBox.getSelectedItem();
						String newBanatzailea = (String) banatzaileaComboBox.getSelectedItem();
						String[] bezeroaNAN = newBezeroa.split(",\\s*");

						if (bezeroaNAN.length > 1) {
							newBezeroa = bezeroaNAN[1];
						} // Bezeroaren nan-a atera

						String[] banatzaileaNAN = newBanatzailea.split(",\\s*");
						if (banatzaileaNAN.length > 1) {
							newBanatzailea = banatzaileaNAN[1];
						} // Langilearen nan-a atera

						dbKontsultak.paketeaEditatu(newData, newBezeroa, newBanatzailea, oldId, newid);

						editatuFrame.dispose();
					});

					editatuFrame.getContentPane().add(panel, BorderLayout.CENTER);
					editatuFrame.getContentPane().add(BtnEditatu, BorderLayout.SOUTH);
					paketeaPanel.revalidate();
					editatuFrame.pack();
					editatuFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Aukeratu editatzeko paketea.");
				}
				paketeaPanel.revalidate();
			}
		});

		ezabatuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();

				if (selectedRow != -1) {

					int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

					int confirm = JOptionPane.showConfirmDialog(null, "Paketea ezabatu nahi duzu?");
					if (confirm == JOptionPane.YES_OPTION) {

						dbKontsultak.paketeaEzabatu(id);
						JOptionPane.showMessageDialog(null, "Paketea ezabatu da.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Paketea hautatu behar duzu ezabatzeko.");
				}
				paketeaPanel.revalidate();
			}
		});

		informazioPanela.add(paketeaPanel, "paketeaPanel");
		CardLayout cardLayout = (CardLayout) informazioPanela.getLayout();
		cardLayout.show(informazioPanela, "paketeaPanel");
	}

}