package pakAG;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PaketeHistoriala {

	public void PaketeHis(JPanel informazioPanela) {
		JPanel historialaPanel = new JPanel(new BorderLayout());

		informazioPanela.removeAll();

		informazioPanela.add(new JScrollPane(historialaPanel), BorderLayout.CENTER);

		JPanel botoiakPanel = new JPanel(new BorderLayout());
		historialaPanel.add(botoiakPanel, BorderLayout.NORTH);

		JPanel ordenatuPanel = new JPanel(new BorderLayout());
		botoiakPanel.add(ordenatuPanel, BorderLayout.EAST);

		JPanel filtratuPanel = new JPanel(new BorderLayout());
		botoiakPanel.add(filtratuPanel, BorderLayout.WEST);

		JComboBox<String> filtratuComboBox = new JComboBox<String>();
		filtratuPanel.add(filtratuComboBox, BorderLayout.NORTH);

		List<String> historialaOrdenatuta = dbKontsultak.historialaZutabeak();
		for (String historialaFiltratu : historialaOrdenatuta) {
			filtratuComboBox.addItem(historialaFiltratu);
		}

		JLabel filtratuLabel = new JLabel("Filtratu:");
		filtratuPanel.add(filtratuLabel, BorderLayout.WEST);
		JTextField filrtoaField = new JTextField(10);
		filtratuPanel.add(filrtoaField, BorderLayout.CENTER);

		JButton filtratu = new JButton("Filtratu");
		filtratuPanel.add(filtratu, BorderLayout.SOUTH);

		JComboBox<String> ordenatuComboBox = new JComboBox<String>();
		ordenatuPanel.add(ordenatuComboBox, BorderLayout.NORTH);

		List<String> historiala = dbKontsultak.historialaZutabeak();
		for (String historialaFiltratu : historiala) {
			ordenatuComboBox.addItem(historialaFiltratu);
		}

		JButton ordenatu = new JButton("Ordenatu");
		ordenatuPanel.add(ordenatu, BorderLayout.SOUTH);

		DefaultTableModel tableModel = new DefaultTableModel(); //taulako zutabeen izena jarri

		tableModel.addColumn("ID");
		tableModel.addColumn("Langilea");
		tableModel.addColumn("Bezeroa");
		tableModel.addColumn("Helbidea");
		tableModel.addColumn("Entrega Data");
		tableModel.addColumn("Oharra");

		List<Historiala> historialaList = dbKontsultak.paketeHistoriala();

		for (Historiala historiala2 : historialaList) { //Lerroetan baloreak jarri
			Object[] rowData = { historiala2.getID(), historiala2.getLangileaIzena(), historiala2.getBezeroaIzena(),
			historiala2.getBezeroaHelbidea(), historiala2.getEntregaData(), historiala2.getOharra() };
			tableModel.addRow(rowData);
		}

		JTable historialaTable = new JTable(tableModel);
		historialaPanel.add(new JScrollPane(historialaTable), BorderLayout.CENTER);

		informazioPanela.revalidate();
		informazioPanela.repaint();

		ordenatu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);

				List<Historiala> historialaList = dbKontsultak
						.historialaOrdenatuta((String) ordenatuComboBox.getSelectedItem());

				for (Historiala historiala2 : historialaList) { //Lerroetan baloreak jarri
					 Object[] rowData = { historiala2.getID(), historiala2.getLangileaIzena(), historiala2.getBezeroaIzena(),
					 historiala2.getBezeroaHelbidea(), historiala2.getEntregaData(), historiala2.getOharra() };
					 tableModel.addRow(rowData);
				}

				historialaTable.repaint();
			}
		});

		filtratu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);

				List<Historiala> historialaList = dbKontsultak
						.historialaFiltratu((String) filtratuComboBox.getSelectedItem(), filrtoaField.getText());

				for (Historiala historiala2 : historialaList) { //Lerroetan baloreak jarri
					 Object[] rowData = { historiala2.getID(), historiala2.getLangileaIzena(), historiala2.getBezeroaIzena(),
					 historiala2.getBezeroaHelbidea(), historiala2.getEntregaData(), historiala2.getOharra() };
					 tableModel.addRow(rowData);
				}

				historialaTable.repaint();
			}
		});
	}

}