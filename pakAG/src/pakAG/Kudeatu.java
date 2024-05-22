package pakAG;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;

public class Kudeatu extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JFrame	frame;
    private Langilea aukeratutakoErabiltzailea;
    private Historiala aukeratutakoPaketea;

	public static void main(String[] args) {
		try {
			Kudeatu dialog = new Kudeatu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

		        dispose();

		        Nagusia paginaAnterior = new Nagusia();
		        paginaAnterior.setVisible(true);
			}
		});
		
		contentPanel.setLayout(null);
		contentPanel.add(cancelButton);
		
		
		JButton bantzaileaKudeatu = new JButton("Banatzaileak Kudeatu");
		bantzaileaKudeatu.setBounds(10, 108, 163, 35);
		contentPanel.add(bantzaileaKudeatu);
		
		bantzaileaKudeatu.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFrame frame = new JFrame("Banatzaileak Kudeatu");

		        JPanel banatzaileaPanel = new JPanel(new BorderLayout());
		        frame.add(banatzaileaPanel);

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

            	            editFrame.add(buttonPanel, BorderLayout.SOUTH);

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

		        frame.pack();
		        frame.setVisible(true);
		    }
		});


		
		JButton paketeaKudeatu = new JButton("Paketeak Kudeatu");
		paketeaKudeatu.setBounds(10, 166, 163, 35);
		contentPanel.add(paketeaKudeatu);
		
		paketeaKudeatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 JFrame frame = new JFrame("Paketeak Kudeatu");

			        JPanel paketeaPanel = new JPanel(new BorderLayout());
			        frame.add(paketeaPanel);
			        
			        paketeaPanel.add(new JLabel("Paketeak:"), BorderLayout.NORTH);

			        List<Paketea> paketeaList = dbKontsultak.getPaketeak();
			        DefaultListModel<Paketea> paketeaListModel = new DefaultListModel<>();
			        for (Paketea Paketea : paketeaList) {
			            paketeaListModel.addElement(Paketea);
			        }

			        JList<Paketea> paketeaJList = new JList<>(paketeaListModel);
			        JScrollPane scrollPane = new JScrollPane(paketeaJList);
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


	        			        int result = JOptionPane.showConfirmDialog(null, panel, "Informazioa sartu", JOptionPane.OK_CANCEL_OPTION);

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
	        			            }  
	        			            else {
	        			            	Paketea pak = new Paketea(data, nan, nanLangilea);
        			                	dbKontsultak.paketeaSartu(pak);
	        			           }
	        			     }
	        			}
	        		
	        		});
	            	
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
	        			            }  
	        			            else {
	        			            	dbKontsultak.paketeaEditatu(data, nan, nanLangilea, id);
	        			           }

	            	                editFrame.dispose();
	            	            });
	            	            
	            	            buttonPanel.add(BtnEditatu, BorderLayout.CENTER);

	            	            editFrame.add(buttonPanel, BorderLayout.SOUTH);

	            	            editFrame.getContentPane().add(panel);
	            	            editFrame.pack();
	            	            editFrame.setVisible(true);
	            	        } else {
	            	            JOptionPane.showMessageDialog(null, "Paketea hautatu behar duzu editatzeko.");
	            	        }
	            	    
	            	    }

	            	});
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

			        frame.pack();
			        frame.setVisible(true);
			    }
		});
		
		JButton historiala = new JButton("Historiala");
		historiala.setBounds(10, 225, 163, 35);
		contentPanel.add(historiala);
		
		historiala.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        frame = new JFrame("HISTORIALA");

		        JPanel historialaPanel = new JPanel(new BorderLayout());
		        frame.add(historialaPanel);
		        
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
		        
		        new JLabel("Filtratu:");
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
		        


		        historialaPanel.add(new JLabel("Historiala:"), BorderLayout.CENTER);

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
		                List<Historiala> historialaList = dbKontsultak.historialaOrdenatuta((String) ordenatuComboBox.getSelectedItem());
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
		                List<Historiala> historialaList = dbKontsultak.historialaFiltratu((String) filtratuComboBox.getSelectedItem(), filrtoaField.getText());
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

		        frame.pack();
		        frame.setVisible(true);
		    }
		});
		
		JButton langileHistoriala = new JButton("Langile Historiala");
		langileHistoriala.setBounds(10, 284, 163, 35);
		contentPanel.add(langileHistoriala);
		langileHistoriala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				  frame = new JFrame("LANGILE HISTORIALA");

			        JPanel langileHistorialaPanel = new JPanel(new BorderLayout());
			        frame.add(langileHistorialaPanel);
			        
			        JLabel lblHistoriala = new JLabel();

			        JPanel langileakPanel = new JPanel(new BorderLayout());
			        langileHistorialaPanel.add(langileakPanel, BorderLayout.NORTH);
			        
			        JPanel historialaIkusiPanel = new JPanel(new BorderLayout());
			        
			        JComboBox<String> banatzaileaComboBox = new JComboBox<String>();
			        langileakPanel.add(banatzaileaComboBox, BorderLayout.NORTH);
			        
			        
			        List<String> banatzaileaList = dbKontsultak.getLangileIzenak();

			        for (String banatzailea : banatzaileaList) {
			        	banatzaileaComboBox.addItem(banatzailea);
			        }

                    JButton langileH = new JButton("Historiala ikusi");
                    langileakPanel.add(langileH, BorderLayout.CENTER);

                    langileH.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				langileHistorialaPanel.add(historialaIkusiPanel, BorderLayout.CENTER);
                            langileHistorialaPanel.revalidate(); 
                            frame.pack(); 
                            frame.setVisible(true);
                            
            		        JPanel botoiakPanel = new JPanel(new BorderLayout());
            		        historialaIkusiPanel.add(botoiakPanel, BorderLayout.NORTH);
            		        
            		        
            		        JPanel ordenatuPanel = new JPanel(new BorderLayout());
            		        botoiakPanel.add(ordenatuPanel, BorderLayout.EAST);
            		        
            		        JComboBox<String> ordenatuComboBox = new JComboBox<String>();
            		        ordenatuPanel.add(ordenatuComboBox, BorderLayout.NORTH);
            		        
            		        List<String> historialao = dbKontsultak.langileaHistorialaZutabeak();
            		        for (String historialaF : historialao) {
            		            ordenatuComboBox.addItem(historialaF);
            		        }
            		        
            		        JButton ordenatu = new JButton("Ordenatu");
            		        ordenatuPanel.add(ordenatu, BorderLayout.SOUTH);  
            		                    		        		
            		        
            		        JPanel filtratuPanel = new JPanel(new BorderLayout());
            		        botoiakPanel.add(filtratuPanel, BorderLayout.WEST);
            		        
            		        JComboBox<String> filtratuComboBox = new JComboBox<String>();
            		        filtratuPanel.add(filtratuComboBox, BorderLayout.NORTH);
            		        
            		        List<String> historiala = dbKontsultak.langileaHistorialaZutabeak();
            		        for (String historialaF : historiala) {
            		        	filtratuComboBox.addItem(historialaF);
            		        }
            		        
            		        new JLabel("Filtratu:");
            		        JTextField filrtoaField = new JTextField(10);
            		        filtratuPanel.add(filrtoaField, BorderLayout.CENTER);
            		        
            		        JButton filtratu = new JButton("Filtratu");
            		        filtratuPanel.add(filtratu, BorderLayout.SOUTH);
            		        		        
            		                    		                  				
            				
            				historialaIkusiPanel.add(lblHistoriala, BorderLayout.SOUTH);
            				
        			        String izenaLangilea = (String) banatzaileaComboBox.getSelectedItem();
        			        String nanLangilea = "";
        			        
        			        String[] nanAtera = izenaLangilea.split(",\\s*");
                            if (nanAtera.length > 1) {
                            	nanLangilea = nanAtera[1];
                            }
                                       		                    				
                            List<Langilea> langileHistorialaList = dbKontsultak.getLangileHistoriala(nanLangilea);

                            StringBuilder labelText = new StringBuilder("<html>");
                            for (Langilea historiala2 : langileHistorialaList) {
                                labelText.append(historiala2.toString2()).append("<br>");
                            }
                            labelText.append("</html>");

                            lblHistoriala.setText(labelText.toString());
     		                  		                           			
            				final String nan = nanLangilea;
            				
            				historialaIkusiPanel.invalidate();
            				historialaIkusiPanel.validate();
            				historialaIkusiPanel.repaint();
            				
            				ordenatu.addActionListener(new ActionListener() {
            		            @Override
            		            public void actionPerformed(ActionEvent e) {
            		            	String aukera = (String) ordenatuComboBox.getSelectedItem();
            		            	if("entrega_data".equals(aukera)) {
            		            		aukera = "historiala." + aukera;
            		            	}
            		            	
            		                List<Langilea> historialaList = dbKontsultak.langileHistorialaOrdenatu(aukera, nan);
            		                StringBuilder labelText = new StringBuilder("<html>");
            		                for (Langilea historiala2 : historialaList) {
            		                    labelText.append(historiala2.toString2()).append("<br>");
            		                }
            		                labelText.append("</html>");

            		                lblHistoriala.setText(labelText.toString());

            		                historialaIkusiPanel.invalidate();
            		                historialaIkusiPanel.validate();
            		                historialaIkusiPanel.repaint();
            		            }
            		        });
            		        
            		        filtratu.addActionListener(new ActionListener() {
            		            @Override
            		            public void actionPerformed(ActionEvent e) {
            		            	String aukera = (String) filtratuComboBox.getSelectedItem();
            		            	if("entrega_data".equals(aukera)) {
            		            		aukera = "historiala." + aukera;
            		            	}
            		            	
            		                List<Langilea> historialaList = dbKontsultak.langileHistorialaFiltratu(aukera, filrtoaField.getText(), nan);
            		                StringBuilder labelText = new StringBuilder("<html>");
            		                for (Langilea historiala2 : historialaList) {
            		                    labelText.append(historiala2.toString2()).append("<br>");
            		                }
            		                labelText.append("</html>");

            		                lblHistoriala.setText(labelText.toString());

            		                historialaIkusiPanel.invalidate();
            		                historialaIkusiPanel.validate();
            		                historialaIkusiPanel.repaint();
            		            }
            		        });
            			}
            		});
                    
                    frame.pack();
    		        frame.setVisible(true);
			}
		});


	}
}