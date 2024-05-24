package pakAG;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class Kudeatu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Kudeatu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 851, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel botoiPanela = new JPanel();
		botoiPanela.setBounds(31, 129, 184, 345);
		contentPane.add(botoiPanela);
		botoiPanela.setLayout(null);

		JPanel informazioPanela = new JPanel();
		informazioPanela.setBounds(238, 82, 573, 453);
		contentPane.add(informazioPanela);
		informazioPanela.setLayout(new CardLayout(0, 0));

		JButton bantzaileaKudeatu = new JButton("Banatzaileak Kudeatu");
		bantzaileaKudeatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
					}
				});
			}
		});

		bantzaileaKudeatu.setBounds(11, 30, 163, 42);
		botoiPanela.add(bantzaileaKudeatu);

		JButton paketeakKudeatuBotoia = new JButton("Paketeak Kudeatu");
		paketeakKudeatuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel paketeaPanel = new JPanel(new BorderLayout());

				paketeaPanel.add(new JLabel("PAKETEAK KUDEATU:"), BorderLayout.NORTH);

				List<Paketea> paketeaList = dbKontsultak.getPaketeak();

				DefaultTableModel tableModel = new DefaultTableModel();
				tableModel.addColumn("ID");
				tableModel.addColumn("Entrega Data");
				tableModel.addColumn("Helbidea");
				tableModel.addColumn("Banatzailea");

				for (Paketea paketea : paketeaList) {//Lerroetan baloreak jarri
					tableModel.addRow(new Object[] { paketea.getID(), paketea.getEntrega_data(),
							paketea.getHelbidea(), paketea.getLangileaIzena() });
				}

				JTable table = new JTable(tableModel);

				JScrollPane scrollPane = new JScrollPane(table);

				paketeaPanel.add(scrollPane, BorderLayout.CENTER);

				JPanel botoiakPanel = new JPanel(new BorderLayout());

				JButton sartuBtn = new JButton("Sartu");
				botoiakPanel.add(sartuBtn, BorderLayout.EAST);

				JButton ezabatuBtn = new JButton("Ezabatu");
				botoiakPanel.add(ezabatuBtn, BorderLayout.CENTER);

				JButton editatuBtn = new JButton("Editatu");
				botoiakPanel.add(editatuBtn, BorderLayout.WEST);

				paketeaPanel.add(botoiakPanel, BorderLayout.SOUTH);

				sartuBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JPanel panel = new JPanel(new GridLayout(0, 2));
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
							String data = dataField.getText().toUpperCase();
							String izenaBezeroa = (String) bezeroaComboBox.getSelectedItem();
							String nanBezeroa = "";
							String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
							String nanLangilea = "";

							String[] nanB = izenaBezeroa.split(",\\s*");
							if (nanB.length > 1) {
								nanBezeroa = nanB[1];
							} //Bezeroaren nan-a atera

							String[] nanL = izenaLangilea.split(",\\s*");
							if (nanL.length > 1) {
								nanLangilea = nanL[1];
							}//Langilearen nan-a atera
							
							if (data.isEmpty() || !data.matches("\\d{4}/\\d{2}/\\d{2}")) {
								JOptionPane.showMessageDialog(null, "Dataren eremua ez dago beteta.");
							} else {
								Paketea pak = new Paketea(data, nanBezeroa, nanLangilea);
								dbKontsultak.paketeaSartu(pak);
							}
						}
					}

				});

				editatuBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();

						if (selectedRow != -1) { //aukeartutako lerroen baloreak ezarri
							String entregaData = table.getValueAt(selectedRow, 1).toString();
							String bezeroa = table.getValueAt(selectedRow, 2).toString();
							String banatzailea = table.getValueAt(selectedRow, 3).toString();

							JFrame editatuFrame = new JFrame("Editatu Paketea");
							editatuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

							JPanel panel = new JPanel(new GridLayout(0, 2));
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
								
								int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

								String newData = dataField.getText().toUpperCase(); 							
								String newBezeroa = (String) bezeroaComboBox.getSelectedItem();
								String newBanatzailea = (String) banatzaileaComboBox.getSelectedItem();
								String[] bezeroaNAN = newBezeroa.split(",\\s*");
								
								if (bezeroaNAN.length > 1) {
									newBezeroa = bezeroaNAN[1];
								} //Bezeroaren nan-a atera

								String[] banatzaileaNAN = newBanatzailea.split(",\\s*");
								if (banatzaileaNAN.length > 1) {
									newBanatzailea = banatzaileaNAN[1]; 
								} //Langilearen nan-a atera

								dbKontsultak.paketeaEditatu(newData, newBezeroa, newBanatzailea, id);
								
								editatuFrame.dispose();
							});

							editatuFrame.getContentPane().add(panel, BorderLayout.CENTER);
							editatuFrame.getContentPane().add(BtnEditatu, BorderLayout.SOUTH);

							editatuFrame.pack();
							editatuFrame.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Aukeratu editatzeko paketea.");
						}
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
					}
				});

				informazioPanela.add(paketeaPanel, "paketeaPanel");
				CardLayout cardLayout = (CardLayout) informazioPanela.getLayout();
				cardLayout.show(informazioPanela, "paketeaPanel");
			}
		});

		paketeakKudeatuBotoia.setBounds(10, 83, 164, 42);
		botoiPanela.add(paketeakKudeatuBotoia);

		JButton paketeHistorialaBotoia = new JButton("Pakete Historiala");
		paketeHistorialaBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

				List<String> historialaO = dbKontsultak.historialaZutabeak();
				for (String historialaF : historialaO) {
					filtratuComboBox.addItem(historialaF);
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
				for (String historialaF : historiala) {
					ordenatuComboBox.addItem(historialaF);
				}

				JButton ordenatu = new JButton("Ordenatu");
				ordenatuPanel.add(ordenatu, BorderLayout.SOUTH);

                DefaultTableModel tableModel = new DefaultTableModel(); //taulako zutabeen izena jarri
				tableModel.addColumn("ID");
				tableModel.addColumn("NAN langilea");
				tableModel.addColumn("NAN bezeroa");
				tableModel.addColumn("Entrega Data");
				tableModel.addColumn("Oharra");

				List<Historiala> historialaList = dbKontsultak.paketeHistoriala();

				for (Historiala historiala2 : historialaList) { //Lerroetan baloreak jarri
					Object[] rowData = { historiala2.getLangileaIzena(), historiala2.getBezeroaIzena(),
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
		                    Object[] rowData = { historiala2.getLangileaIzena(), historiala2.getBezeroaIzena(),
		                            historiala2.getBezeroaHelbidea(), historiala2.getEntregaData(),
		                            historiala2.getOharra() };
		                    tableModel.addRow(rowData);
		                }

		                historialaTable.repaint();
		            }
		        });

				filtratu.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                tableModel.setRowCount(0);

		                List<Historiala> historialaList = dbKontsultak.historialaFiltratu(
		                        (String) filtratuComboBox.getSelectedItem(), filrtoaField.getText());


		                for (Historiala historiala2 : historialaList) { //Lerroetan baloreak jarri
		                    Object[] rowData = { historiala2.getLangileaIzena(), historiala2.getBezeroaIzena(),
		                            historiala2.getBezeroaHelbidea(), historiala2.getEntregaData(),
		                            historiala2.getOharra() };
		                    tableModel.addRow(rowData);
		                }

		                historialaTable.repaint();
		            }
		        });
			}

		});

		paketeHistorialaBotoia.setBounds(10, 133, 164, 42);
		botoiPanela.add(paketeHistorialaBotoia);

		paketeHistorialaBotoia.setBounds(10, 133, 164, 42);
		botoiPanela.add(paketeHistorialaBotoia);

		JButton langileHistorialaBotoia = new JButton("Langile Historiala");
		langileHistorialaBotoia.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
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
		                tableModel.setRowCount(0);

		                String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
		                String[] nanAtera = izenaLangilea.split(",\\s*");
		                if (nanAtera.length > 1) {
		                    nanLangilea[0] = nanAtera[1];
		                }

		                List<Langilea> langileHistorialaList = dbKontsultak.getLangileHistoriala(nanLangilea[0]);

		                for (Langilea historiala2 : langileHistorialaList) {
		                    Object[] rowData = { historiala2.getID(), historiala2.getBezeroaHelbidea(), historiala2.getEntregaData() };
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
		                    Object[] rowData = { historiala2.getID(), historiala2.getBezeroaHelbidea(), historiala2.getEntregaData() };
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

		                List<Langilea> historialaList = dbKontsultak.langileHistorialaFiltratu(aukera, filrtoaField.getText(), nanLangilea[0]);
		                tableModel.setRowCount(0);

		                for (Langilea historiala2 : historialaList) {
		                    Object[] rowData = { historiala2.getID(), historiala2.getBezeroaHelbidea(), historiala2.getEntregaData() };
		                    tableModel.addRow(rowData);
		                }
		            }
		        });

		        informazioPanela.revalidate();
		        informazioPanela.repaint();
		    }
		});

		langileHistorialaBotoia.setBounds(10, 185, 164, 42);
		botoiPanela.add(langileHistorialaBotoia);

		JButton saioaItxiBotoia = new JButton("Saioa Itxi");
		saioaItxiBotoia.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        dispose();

		        Nagusia paginaAnterior = new Nagusia();
		        paginaAnterior.setVisible(true);
		    }
		});
		saioaItxiBotoia.setBounds(47, 282, 89, 23);
		botoiPanela.add(saioaItxiBotoia);


		JLabel kudeatuLabel = new JLabel("KUDEATU");
		kudeatuLabel.setFont(new Font("Verdana", Font.BOLD, 35));
		kudeatuLabel.setBounds(323, 11, 193, 45);
		contentPane.add(kudeatuLabel);

		JPanel banatzaileakPanela = new JPanel();
		informazioPanela.add(banatzaileakPanela, "name_157157680636800");

		JPanel paketeaPanela = new JPanel();
		informazioPanela.add(paketeaPanela, "name_157175992375700");

		JPanel paketeHisPanela = new JPanel();
		informazioPanela.add(paketeHisPanela, "name_157206857685400");

		JPanel langileHisPanela = new JPanel();
		informazioPanela.add(langileHisPanela, "name_157220874027500");
	}
}