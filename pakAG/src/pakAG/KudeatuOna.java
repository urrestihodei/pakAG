package pakAG;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class KudeatuOna extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KudeatuOna frame = new KudeatuOna();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public KudeatuOna() {
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
		        DefaultListModel<Langilea> banatzaileaListModel = new DefaultListModel<>();
		        for (Langilea Erabiltzailea : banatzaileaList) {
		            banatzaileaListModel.addElement(Erabiltzailea);
		        }

		        JList<Langilea> banatzaileaJList = new JList<>(banatzaileaListModel);
		        JScrollPane scrollPane = new JScrollPane(banatzaileaJList);
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

    			        int result = JOptionPane.showConfirmDialog(null, panel, "Informazioa sartu", JOptionPane.OK_CANCEL_OPTION);

    			        if (result == JOptionPane.OK_OPTION) {
    			            String nan = nanField.getText().toUpperCase();
    			            String izena = izenaField.getText();
    			            String abizena = abizenaField.getText();
    			            String erabiltzailea = erabiltzaileaField.getText();
    			            String pasahitza = pasahitzaField.getText();
    			            String telefonoa = telefonoaField.getText();

    			            if (nan.isEmpty() || izena.isEmpty() || abizena.isEmpty() || erabiltzailea.isEmpty() || pasahitza.isEmpty() || telefonoa.isEmpty()) {
    			                JOptionPane.showMessageDialog(null, "Eremu guztiak ez daude beteta.");
    			            } else {
    			                if (nan.matches("\\d{8}[A-Z]") || !izena.matches(".*\\d.*") || !abizena.matches(".*\\d.*") || telefonoa.matches("\\d{9}")) {
    			                    if (dbKontsultak.erablitzaileaBadago(erabiltzailea)) {
    			                        JOptionPane.showMessageDialog(null, "Erabitzailea badago, sartu beste erabiltzaile bat.");
    			                    } else {
    			                        Langilea erab = new Langilea(nan, izena, abizena, erabiltzailea, pasahitza, telefonoa);
    			                        dbKontsultak.langileaSartu(erab);
    			                    }
    			                } else {
    			                    JOptionPane.showMessageDialog(null, "Datuak ez dira zuzenak. Mesedez, ziurtatu sartutako datuak.");
    			                }
    			            }
    			        }
    			}
    		
    		});

		        editatuBtn.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	Langilea selectedZeregina = banatzaileaJList.getSelectedValue();
            	        if (selectedZeregina != null) {

            	            JFrame editFrame = new JFrame("Editatu langilean");
            	            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            	            JPanel panel = new JPanel(new GridLayout(0, 2));
            	            
            	            String nan = selectedZeregina.getNan();
            	            
        			        panel.add(new JLabel("NAN (8 digitu + letra mayuskula):"));
        			        JTextField nanField = new JTextField(10);
        			        nanField.setText(nan);
        			        panel.add(nanField);
        			        
        			        panel.add(new JLabel("Izena:"));
        			        JTextField izenaField = new JTextField(10);
        			        izenaField.setText(selectedZeregina.getIzena());
        			        panel.add(izenaField);
        			        
        			        panel.add(new JLabel("Abizena:"));
        			        JTextField abizenaField = new JTextField(10);
        			        abizenaField.setText(selectedZeregina.getAbizena());
        			        panel.add(abizenaField);
        			        
        			        panel.add(new JLabel("Telefonoa:"));
        			        JTextField telefonoaField = new JTextField(10);
        			        telefonoaField.setText(selectedZeregina.getTelefonoa());
        			        panel.add(telefonoaField);
        			        
        			        panel.add(new JLabel("Erabiltzailea:"));
        			        JTextField erabiltzaileaField = new JTextField(10);
        			        erabiltzaileaField.setText(selectedZeregina.getErabiltzailea());
        			        panel.add(erabiltzaileaField);
        			        
        			        panel.add(new JLabel("Pasahitza:"));
        			        JTextField pasahitzaField = new JTextField(10);
        			        pasahitzaField.setText(selectedZeregina.getPasahitza());
        			        panel.add(pasahitzaField);

            	            JPanel buttonPanel = new JPanel(new BorderLayout());
            	            JButton BtnEditatu = new JButton("Editatu");
            	            BtnEditatu.addActionListener(actionEvent -> {
            	                String nanBerria = (String) nanField.getText();
            	                String izena = (String) izenaField.getText();
            	                String abizena = (String) abizenaField.getText();
            	                String telefonoa = (String) telefonoaField.getText();
            	                String erabiltzailea = (String) erabiltzaileaField.getText();
            	                String pasahitza = (String) pasahitzaField.getText();

            	               
            	                    dbKontsultak.langileaEditatu(nan, izena, abizena, telefonoa, erabiltzailea, pasahitza, nanBerria);
            	                    JOptionPane.showMessageDialog(null, "Erabiltzailea editatu da.");

            	                editFrame.dispose();
            	            });
            	            buttonPanel.add(BtnEditatu, BorderLayout.CENTER);

            	            editFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            	            editFrame.getContentPane().add(panel);
            	            editFrame.pack();
            	            editFrame.setVisible(true);
            	        } else {
            	            JOptionPane.showMessageDialog(null, "Langilea hautatu behar duzu editatzeko.");
            	        }
            	    }

            	});

		        ezabatuBtn.addActionListener(new ActionListener() {
            	    @Override
            	    public void actionPerformed(ActionEvent e) {
                    Langilea selectedErabiltzailea = banatzaileaJList.getSelectedValue();
                    if (selectedErabiltzailea != null) {
                        String nan = selectedErabiltzailea.getNan();

                        int confirm = JOptionPane.showConfirmDialog(null, "Langilea ezabatu nahi duzu?");
                        if (confirm == JOptionPane.YES_OPTION) {
                            dbKontsultak.langileaEzabatu(nan);
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
				// Crear el panel para mostrar la información
				JPanel paketeaPanel = new JPanel(new BorderLayout());

				// Añadir el título al panel
				paketeaPanel.add(new JLabel("PAKETEAK KUDEATU:"), BorderLayout.NORTH);

				// Obtener la lista de paketeak
				List<Paketea> paketeaList = dbKontsultak.getPaketeak();

				// Crear un DefaultListModel para la lista
				DefaultListModel<Paketea> paketeaListModel = new DefaultListModel<>();

				// Llenar el DefaultListModel con los datos de la lista de paketeak
				for (Paketea Paketea : paketeaList) {
					paketeaListModel.addElement(Paketea);
				}

				// Crear un JList con el DefaultListModel
				JList<Paketea> paketeaJList = new JList<>(paketeaListModel);

				// Crear un JScrollPane para el JList
				JScrollPane scrollPane = new JScrollPane(paketeaJList);

				// Añadir el JScrollPane al panel
				paketeaPanel.add(scrollPane, BorderLayout.CENTER);

				// Crear un JPanel para los botones de acción
				JPanel botoiakPanel = new JPanel(new BorderLayout());

				// Añadir los botones al panel de botones
				JButton sartuBtn = new JButton("Sartu");
				botoiakPanel.add(sartuBtn, BorderLayout.EAST);

				JButton ezabatuBtn = new JButton("Ezabatu");
				botoiakPanel.add(ezabatuBtn, BorderLayout.CENTER);

				JButton editatuBtn = new JButton("Editatu");
				botoiakPanel.add(editatuBtn, BorderLayout.WEST);

				// Añadir el panel de botones al panel principal
				paketeaPanel.add(botoiakPanel, BorderLayout.SOUTH);

				// Escuchar el evento del botón "Sartu" y ejecutar la acción correspondiente
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
							String izena = (String) bezeroaComboBox.getSelectedItem();
							String nan = "";
							String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
							String nanLangilea = "";

							String[] parts = izena.split(",\\s*");
							if (parts.length > 1) {
								nan = parts[1];
							}

							String[] parts2 = izenaLangilea.split(",\\s*");
							if (parts2.length > 1) {
								nanLangilea = parts2[1];
							}
							if (data.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Dataren eremua ez dago beteta.");
							} else {
								Paketea pak = new Paketea(data, nan, nanLangilea);
								dbKontsultak.paketeaSartu(pak);
							}
						}
					}

				});

				// Escuchar el evento del botón "Editatu" y ejecutar la acción correspondiente
				editatuBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Paketea selectedZeregina = paketeaJList.getSelectedValue();
						if (selectedZeregina != null) {

							JFrame editFrame = new JFrame("Editatu langilea");
							editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

							JPanel panel = new JPanel(new GridLayout(0, 2));
							panel.add(new JLabel("Entrega data(UUUU/HH/EE):"));
							JTextField dataField = new JTextField(10);
							dataField.setText(selectedZeregina.getEntrega_data());
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

							JPanel buttonPanel = new JPanel(new BorderLayout());
							JButton BtnEditatu = new JButton("Editatu");
							BtnEditatu.addActionListener(actionEvent -> {
								int id = selectedZeregina.getID();
								String data = dataField.getText().toUpperCase();
								String izena = (String) bezeroaComboBox.getSelectedItem();
								String nan = "";
								String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
								String nanLangilea = "";

								String[] parts = izena.split(",\\s*");
								if (parts.length > 1) {
									nan = parts[1];
								}

								String[] parts2 = izenaLangilea.split(",\\s*");
								if (parts2.length > 1) {
									nanLangilea = parts2[1];
								}
								if (data.isEmpty()) {
									JOptionPane.showMessageDialog(null, "Dataren eremua ez dago beteta.");
								} else {
									dbKontsultak.paketeaEditatu(data, nan, nanLangilea, id);
								}

								editFrame.dispose();
							});

							buttonPanel.add(BtnEditatu, BorderLayout.CENTER);

							editFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

							editFrame.getContentPane().add(panel);
							editFrame.pack();
							editFrame.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Paketea hautatu behar duzu editatzeko.");
						}

					}

				});

				// Escuchar el evento del botón "Ezabatu" y ejecutar la acción correspondiente
				ezabatuBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Paketea selectedPaketea = paketeaJList.getSelectedValue();
						if (selectedPaketea != null) {
							int id = selectedPaketea.getID();

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

				// Mostrar el panel con la información de los paquetes
				informazioPanela.add(paketeaPanel, "paketeaPanel");
				CardLayout cardLayout = (CardLayout) informazioPanela.getLayout();
				cardLayout.show(informazioPanela, "paketeaPanel");
			}
		});

		paketeakKudeatuBotoia.setBounds(10, 83, 164, 42);
		botoiPanela.add(paketeakKudeatuBotoia);

		JButton paketeHistorialaBotoia = new JButton("Pakete Historiala");
		// Acción del botón "Pakete Historiala"
		paketeHistorialaBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel historialaPanel = new JPanel(new BorderLayout());

				// Limpiar el panel de información actual
				informazioPanela.removeAll();

				// Añadir el panel de historial al panel de información
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

				JLabel historialaLabel = new JLabel("Historiala:");
				historialaPanel.add(historialaLabel, BorderLayout.CENTER);

				JLabel lblHistoriala = new JLabel();
				historialaPanel.add(lblHistoriala, BorderLayout.SOUTH);

				List<Historiala> historialaList = dbKontsultak.paketeHistoriala();
				StringBuilder labelText = new StringBuilder("<html>");
				for (Historiala historiala2 : historialaList) {
					labelText.append(historiala2.toString()).append("<br>");
				}
				labelText.append("</html>");
				lblHistoriala.setText(labelText.toString());

				ordenatu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						List<Historiala> historialaList = dbKontsultak
								.historialaOrdenatuta((String) ordenatuComboBox.getSelectedItem());
						StringBuilder labelText = new StringBuilder("<html>");
						for (Historiala historiala2 : historialaList) {
							labelText.append(historiala2.toString()).append("<br>");
						}
						labelText.append("</html>");

						lblHistoriala.setText(labelText.toString());

						historialaPanel.invalidate();
						historialaPanel.validate();
						historialaPanel.repaint();
					}
				});

				filtratu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						List<Historiala> historialaList = dbKontsultak.historialaFiltratu(
								(String) filtratuComboBox.getSelectedItem(), filrtoaField.getText());
						StringBuilder labelText = new StringBuilder("<html>");
						for (Historiala historiala2 : historialaList) {
							labelText.append(historiala2.toString()).append("<br>");
						}
						labelText.append("</html>");

						lblHistoriala.setText(labelText.toString());

						historialaPanel.invalidate();
						historialaPanel.validate();
						historialaPanel.repaint();
					}
				});

				// Actualizar el contenido del panel principal
				informazioPanela.revalidate();
				informazioPanela.repaint();
			}
		});

		// Establecer posición y tamaño del botón "Pakete Historiala"
		paketeHistorialaBotoia.setBounds(10, 133, 164, 42);
		botoiPanela.add(paketeHistorialaBotoia);

		paketeHistorialaBotoia.setBounds(10, 133, 164, 42);
		botoiPanela.add(paketeHistorialaBotoia);

		JButton langileHistorialaBotoia = new JButton("Langile Historiala");
		langileHistorialaBotoia.setBounds(10, 185, 164, 42);
		botoiPanela.add(langileHistorialaBotoia);

		JButton saioaItxiBotoia = new JButton("Saioa Itxi");
		saioaItxiBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cerrar la página actual
				dispose();
				// Abrir la página anterior
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
