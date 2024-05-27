package pakAG;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BanatzaileHistoriala {

	public void BanatzaileHis(JPanel informazioPanela) {
		informazioPanela.removeAll();

		JPanel langileHistorialaPanel = new JPanel(new BorderLayout());
		informazioPanela.add(langileHistorialaPanel, BorderLayout.CENTER);

		JPanel langileakPanel = new JPanel(new BorderLayout());
		langileHistorialaPanel.add(langileakPanel, BorderLayout.NORTH);

		JComboBox<String> banatzaileaComboBox = new JComboBox<>();
		langileakPanel.add(banatzaileaComboBox, BorderLayout.NORTH);

		List<String> banatzaileaList = dbKontsultak.getLangileIzenak();
		for (String banatzailea : banatzaileaList) {
			banatzaileaComboBox.addItem(banatzailea);
		}

		JButton langileH = new JButton("Historiala ikusi");
		langileakPanel.add(langileH, BorderLayout.CENTER);

		JPanel historialaIkusiPanel = new JPanel(new BorderLayout());
		langileHistorialaPanel.add(historialaIkusiPanel, BorderLayout.CENTER);
		historialaIkusiPanel.setVisible(false);
		
		JPanel botoiakPanel = new JPanel(new BorderLayout());
		historialaIkusiPanel.add(botoiakPanel, BorderLayout.NORTH);

		JPanel ordenatuPanel = new JPanel(new BorderLayout());
		botoiakPanel.add(ordenatuPanel, BorderLayout.EAST);

		JComboBox<String> ordenatuComboBox = new JComboBox<>();
		ordenatuPanel.add(ordenatuComboBox, BorderLayout.NORTH);

		List<String> historialao = dbKontsultak.langileaHistorialaZutabeak();
		for (String historialaF : historialao) {
			ordenatuComboBox.addItem(historialaF);
		}

		JButton ordenatu = new JButton("Ordenatu");
		ordenatuPanel.add(ordenatu, BorderLayout.SOUTH);

		JPanel filtratuPanel = new JPanel(new BorderLayout());
		botoiakPanel.add(filtratuPanel, BorderLayout.WEST);

		JComboBox<String> filtratuComboBox = new JComboBox<>();
		filtratuPanel.add(filtratuComboBox, BorderLayout.NORTH);

		List<String> historiala = dbKontsultak.langileaHistorialaZutabeak();
		for (String historialaF : historiala) {
			filtratuComboBox.addItem(historialaF);
		}

		JTextField filrtoaField = new JTextField(10);
		filtratuPanel.add(filrtoaField, BorderLayout.CENTER);

		JButton filtratu = new JButton("Filtratu");
		filtratuPanel.add(filtratu, BorderLayout.SOUTH);

		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Helbidea");
		tableModel.addColumn("Entrega Data");

		JTable historialaTable = new JTable(tableModel);
		historialaIkusiPanel.add(new JScrollPane(historialaTable), BorderLayout.CENTER);

		final String[] nanLangilea = { "" };

		banatzaileaComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
				String[] nanAtera = izenaLangilea.split(",\\s*");
				if (nanAtera.length > 1) {
					nanLangilea[0] = nanAtera[1];
				}
			}
		});

		langileH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historialaIkusiPanel.revalidate();
				historialaIkusiPanel.setVisible(true);
				
				tableModel.setRowCount(0);

				String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
				String[] nanAtera = izenaLangilea.split(",\\s*");
				if (nanAtera.length > 1) {
					nanLangilea[0] = nanAtera[1];
				}

				List<Langilea> langileHistorialaList = dbKontsultak.getLangileHistoriala(nanLangilea[0]);

				for (Langilea historiala2 : langileHistorialaList) {
					Object[] rowData = { historiala2.getID(), historiala2.getBezeroaHelbidea(),
							historiala2.getEntregaData() };
					tableModel.addRow(rowData);
				}

				informazioPanela.revalidate();
				informazioPanela.repaint();
			}
		});

		ordenatu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String aukera = (String) ordenatuComboBox.getSelectedItem();
				if ("entrega_data".equals(aukera)) {
					aukera = "historiala." + aukera;
				}

				List<Langilea> historialaList = dbKontsultak.langileHistorialaOrdenatu(aukera, nanLangilea[0]);
				tableModel.setRowCount(0);

				for (Langilea historiala2 : historialaList) {
					Object[] rowData = { historiala2.getID(), historiala2.getBezeroaHelbidea(),
							historiala2.getEntregaData() };
					tableModel.addRow(rowData);
				}
			}
		});

		filtratu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String aukera = (String) filtratuComboBox.getSelectedItem();
				if ("entrega_data".equals(aukera)) {
					aukera = "historiala." + aukera;
				}

				List<Langilea> historialaList = dbKontsultak.langileHistorialaFiltratu(aukera, filrtoaField.getText(),
						nanLangilea[0]);
				tableModel.setRowCount(0);

				for (Langilea historiala2 : historialaList) {
					Object[] rowData = { historiala2.getID(), historiala2.getBezeroaHelbidea(),
							historiala2.getEntregaData() };
					tableModel.addRow(rowData);
				}
			}
		});

		informazioPanela.revalidate();
		informazioPanela.repaint();

	}

}