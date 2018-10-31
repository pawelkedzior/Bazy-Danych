package bazy;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import bazy.ObslugaBazy.Uzytkownik;

public class Menu extends JPanel{

	private static final long serialVersionUID = 1273945096139815355L;
	public static JFrame ramka;
	private static AkcjeMenu nasluchiwaczAkcji=new AkcjeMenu();
	public static ObslugaBazy interfejsObslugi=new ObslugaBazy();
	public static JTextField poleTekstoweLoginu=new JTextField(20);
	public static JTextField poleTekstoweHasla=new JPasswordField(20);
	public static Uzytkownik zalogowanyUzytkownik;
	public static JTable tabela;
	public static JPanel informacjeOLogowaniu, narzedzia;
	private static ButtonGroup grupaWyswietlania=new ButtonGroup();
	
	private static class EkranLogowania extends JPanel{

		private static final long serialVersionUID = -7299537420596805986L;
		public EkranLogowania()
		{
			super();
			GroupLayout uklad=new GroupLayout(this);
			setLayout(uklad);
			JButton przyciskLogowania=new JButton("Zaloguj");
			JButton przyciskWyjscia=new JButton("Wyjdź");
			JLabel etykietaLoginu=new JLabel("Login");
			JLabel etykietaHasla=new JLabel("Hasło");
			
			przyciskLogowania.setActionCommand("LOGOWANIE");
			przyciskLogowania.addActionListener(nasluchiwaczAkcji);
			
			przyciskWyjscia.setActionCommand("WYJSCIE");
			przyciskWyjscia.addActionListener(nasluchiwaczAkcji);
			
			uklad.setHorizontalGroup(
					uklad.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addGroup(uklad.createParallelGroup()
							.addGroup(uklad.createSequentialGroup()
											.addComponent(etykietaLoginu)
											.addComponent(poleTekstoweLoginu))
							.addGroup(uklad.createSequentialGroup()
											.addComponent(etykietaHasla)
											.addComponent(poleTekstoweHasla)))
						.addGroup(uklad.createSequentialGroup()
								.addComponent(przyciskLogowania)
					    		.addComponent(przyciskWyjscia))
			);
			uklad.setVerticalGroup(
					uklad.createSequentialGroup()
					.addGroup(uklad.createSequentialGroup()
					    .addGroup(uklad.createParallelGroup(GroupLayout.Alignment.CENTER)
								           .addComponent(etykietaLoginu)
								           .addComponent(poleTekstoweLoginu))
					    .addGroup(uklad.createParallelGroup(GroupLayout.Alignment.CENTER)
								           .addComponent(etykietaHasla)
								           .addComponent(poleTekstoweHasla)))
				    .addGroup(uklad.createParallelGroup()
				    		.addComponent(przyciskLogowania)
				    		.addComponent(przyciskWyjscia))
			);		
			
			uklad.setAutoCreateContainerGaps(true);
			uklad.setAutoCreateGaps(true);
		}
	}
	
	public static void wyswietlInformacjeOLogowaniu()
	{
		String[] daneUzytkownika=zalogowanyUzytkownik.daneZalogowanegoUzytkownika();
		JLabel etykieta=new JLabel("Jesteś zalogowany jako:      "+daneUzytkownika[0]);
		informacjeOLogowaniu.add(etykieta);
		if(!(daneUzytkownika[0]).equals("wlasciciel"))
		{
			etykieta=new JLabel("Twój sklep to:                        "+daneUzytkownika[2]);
			informacjeOLogowaniu.add(etykieta);
		}
	}
	
	public static void wyswietlPanelNarzedzi()
	{
		switch(zalogowanyUzytkownik.typKonta)
		{
			case "Właściciel":
				narzedziaWlasciciela();
			break;
			case "Koordynator":
				narzedziaKoordynatoraSklepu();
			break;
			case "Pracownik":
				narzedziaPracownikaSklepu();
			break;
		}
	}
	
	private static void narzedziaWlasciciela()
	{
		narzedzia.setLayout(new BoxLayout(narzedzia, BoxLayout.Y_AXIS));
		JLabel etykieta=new JLabel(" ");
		narzedzia.add(etykieta);
		/*JSeparator separator=new JSeparator(SwingConstants.HORIZONTAL);
		JPanel dlaSeparatora=new JPanel();
		dlaSeparatora.add(separator);
		dlaSeparatora.setSize(narzedzia.getWidth(), 5);
		narzedzia.add(dlaSeparatora);*/
		etykieta=new JLabel("Wybierz sklep dla poniższych akcji:");
		narzedzia.add(etykieta);
		String sklepyLista[]={"Bli"};
		JComboBox<String> listaSklepow = new JComboBox<String>(sklepyLista);
			listaSklepow.setSelectedIndex(0);
			listaSklepow.setActionCommand("LISTASKLEP");
			listaSklepow.addActionListener(nasluchiwaczAkcji);
		narzedzia.add(listaSklepow);
		etykieta=new JLabel(" ");
		narzedzia.add(etykieta);
		narzedziaKoordynatoraSklepu();
	}
	
	private static void narzedziaKoordynatoraSklepu()
	{
		narzedziaPracownikaSklepu();
		JLabel etykieta=new JLabel(" ");
			narzedzia.add(etykieta);
		etykieta=new JLabel("Narzędzia koordynatora");
			etykieta.setForeground(Color.RED);
			etykieta.setBackground(Color.WHITE);
			narzedzia.add(etykieta);
		etykieta=new JLabel("Wyświetl (ciąg dalszy)");
			narzedzia.add(etykieta);
		JRadioButton wyborWidoku=new JRadioButton("Przychody sklepu");
			wyborWidoku.setActionCommand("WYSWPRZYCHKOORD");
			wyborWidoku.addActionListener(nasluchiwaczAkcji);
			grupaWyswietlania.add(wyborWidoku);
			narzedzia.add(wyborWidoku);
		wyborWidoku=new JRadioButton("Utrzymanie sklepu");
			wyborWidoku.setActionCommand("WYSWUTRZKOORD");
			wyborWidoku.addActionListener(nasluchiwaczAkcji);
			grupaWyswietlania.add(wyborWidoku);
			narzedzia.add(wyborWidoku);
		wyborWidoku=new JRadioButton("Lista pracowników");
			wyborWidoku.setActionCommand("WYSWPRACKOORD");
			wyborWidoku.addActionListener(nasluchiwaczAkcji);
			grupaWyswietlania.add(wyborWidoku);
			narzedzia.add(wyborWidoku);
		etykieta=new JLabel("Operacje");
			narzedzia.add(etykieta);
		JButton przycisk=new JButton("Zaproponuj podwyżkę dla pracownika");
			przycisk.setActionCommand("PODWPRACKOORD");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
		przycisk=new JButton("Zaproponuj premię dla pracownika");
			przycisk.setActionCommand("PREMIAPRACKOORD");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
		przycisk=new JButton("Zgłoś nowego pracownika");
			przycisk.setActionCommand("NOWYPRACKOORD");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
	}
	
	private static void narzedziaPracownikaSklepu()
	{
		narzedzia.setLayout(new BoxLayout(narzedzia, BoxLayout.Y_AXIS));
		JLabel etykieta=new JLabel("Narzędzia pracownika");
			etykieta.setForeground(Color.RED);
			etykieta.setBackground(Color.WHITE);
			narzedzia.add(etykieta);
		etykieta=new JLabel("Wyświetl");
			narzedzia.add(etykieta);
		JRadioButton wyborWidoku=new JRadioButton("Zawartość magazynu");
			wyborWidoku.setActionCommand("WYSWMAGPRAC");
			wyborWidoku.addActionListener(nasluchiwaczAkcji);
			grupaWyswietlania.add(wyborWidoku);
			narzedzia.add(wyborWidoku);
		wyborWidoku=new JRadioButton("Lista zamówień");
			wyborWidoku.setActionCommand("WYSWZAMPRAC");
			wyborWidoku.addActionListener(nasluchiwaczAkcji);
			grupaWyswietlania.add(wyborWidoku);
			narzedzia.add(wyborWidoku);
		wyborWidoku=new JRadioButton("Lista dostaw");
			wyborWidoku.setActionCommand("WYSWDOSPRAC");
			wyborWidoku.addActionListener(nasluchiwaczAkcji);
			grupaWyswietlania.add(wyborWidoku);
			narzedzia.add(wyborWidoku);
		etykieta=new JLabel("Operacje");
			narzedzia.add(etykieta);
		JButton przycisk=new JButton("Złóż zamówienie");
			przycisk.setActionCommand("NOWEZAMOWIENIE");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
		przycisk=new JButton("Zgłoś umówioną dostawę");
			przycisk.setActionCommand("NOWADOSTAWA");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
		przycisk=new JButton("Zgłoś dostarczenie produktów");
			przycisk.setActionCommand("DOSTARCZONO");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
		przycisk=new JButton("Zgłoś sprzedaż produktow");
			przycisk.setActionCommand("SPRZEDANO");
			przycisk.addActionListener(nasluchiwaczAkcji);
			narzedzia.add(przycisk);
	}
	
	private static class EkranAplikacji extends JPanel{

		private static final long serialVersionUID = -5556652521014911425L;
		public EkranAplikacji()
		{
			super();
			GroupLayout uklad=new GroupLayout(this);
			setLayout(uklad);
			JSeparator separatorPionowy=new JSeparator(SwingConstants.VERTICAL);
			JSeparator separatorPoziomy=new JSeparator(SwingConstants.HORIZONTAL);
			informacjeOLogowaniu=new JPanel();
			informacjeOLogowaniu.setLayout(new BoxLayout(informacjeOLogowaniu, BoxLayout.Y_AXIS));
			informacjeOLogowaniu.setPreferredSize(new Dimension(800,100));
			informacjeOLogowaniu.setBackground(Color.WHITE);
			JPanel widokTabeli=new JPanel();
			widokTabeli.setPreferredSize(new Dimension(800,700));
			narzedzia=new JPanel();
			
			//Tabela
	        tabela = new JTable();
	        tabela.setPreferredScrollableViewportSize(new Dimension(800, 700));    
	        JScrollPane scrollPane = new JScrollPane(tabela);
	        widokTabeli.add(scrollPane);
				
			uklad.setHorizontalGroup(
					uklad.createSequentialGroup()
						.addGroup(uklad.createParallelGroup()
								.addComponent(informacjeOLogowaniu)
								.addComponent(separatorPoziomy)
								.addComponent(widokTabeli))
						.addComponent(separatorPionowy)
						.addComponent(narzedzia)
			);
			uklad.setVerticalGroup(
					uklad.createParallelGroup()
					.addGroup(uklad.createSequentialGroup()
							.addComponent(informacjeOLogowaniu)
							.addComponent(separatorPoziomy)
							.addComponent(widokTabeli))
					.addComponent(separatorPionowy)
					.addComponent(narzedzia)
			);		
			
			uklad.setAutoCreateContainerGaps(true);
			uklad.setAutoCreateGaps(true);
			/*
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
				GridLayout gl=new GridLayout(1, 1, 2, 2);
				mapa=new PanelMapy(gl);
				mapa.setMinimumSize(new Dimension(1200,800));
				mapa.setPreferredSize(new Dimension(1200,800));
				mapa.setBackground(Color.WHITE);
				mapa.setOpaque(true);
				JScrollPane mapaPrzewijana=new JScrollPane(mapa);
				add(mapaPrzewijana);
				add(Box.createHorizontalStrut(5));
				JSeparator sep=new JSeparator(SwingConstants.VERTICAL);
				sep.setPreferredSize(new Dimension(3,1200));
				sep.setMaximumSize(new Dimension(3,2000));
				sep.setMinimumSize(new Dimension(3,20));
				add(sep);
				add(Box.createHorizontalStrut(5));
				gl=new GridLayout(3, 1, 2, 2);
				pocz=new JTextField(30);
				pocz.addKeyListener(this);
				JLabel tekst=new JLabel("Początek trasy");
				JPanel wyszukiwarka=new JPanel(gl);
				gl=new GridLayout(10, 1, 2, 2);
				JPanel wyszukiwarkag=new JPanel(gl);
				wyszukiwarkag.add(new JLabel());
				wyszukiwarkag.add(tekst);
				wyszukiwarkag.add(pocz);
				tekst=new JLabel("Koniec trasy");
				kon=new JTextField(30);
				kon.addKeyListener(this);
				wyszukiwarkag.add(tekst);
				wyszukiwarkag.add(kon);
				JPanel panel=new JPanel(new GridLayout(1, 3, 0, 0));
				panel.add(new JLabel());
				panel.add(new JLabel());
				przyc=new JButton("Wyszukaj");
					przyc.addActionListener(this);
					przyc.setActionCommand("4");
				panel.add(przyc);
				wyszukiwarkag.add(panel);
				wynikowa=new JTextArea();
				wynikowa.setMaximumSize(new Dimension(30,10000));
				wynikowa.setEditable(false);
				wynikowa.setEnabled(false);
				wynikowa.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
				wynikowa.setBackground(Color.WHITE);
				wynikowa.setOpaque(true);
				wyszukiwarka.add(wyszukiwarkag);
				wyszukiwarka.add(wynikowa,-1);
				wyszukiwarka.setSize(new Dimension(300,800));
				wyszukiwarka.setMaximumSize(new Dimension(300,800));
				wyszukiwarka.setMinimumSize(new Dimension(300,800));
				add(wyszukiwarka);		 
			 */
		}
	}
	
	private Menu()
	{
		super();
		setLayout(new CardLayout());
		JPanel kartaLogowania=new EkranLogowania();
		JPanel kartaAplikacji=new EkranAplikacji();
		add(kartaLogowania,"EKRANLOGOWANIA");
		add(kartaAplikacji,"EKRANAPLIKACJI");
	}
	
	public JMenuBar tworzPasekMenu()
	{
		JMenuBar pasekMenu=new JMenuBar();
		
		JMenu plik;
        JMenuItem wiersz;    
        
        plik = new JMenu("Plik");
        pasekMenu.add(plik);
        /*
        wiersz = new JMenuItem("Wczytaj mapę", KeyEvent.VK_M);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
    		wiersz.getAccessibleContext().setAccessibleDescription("Wczytuje mapę");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("1");
    	plik.add(wiersz);	
        wiersz = new JMenuItem("Ustawienia", KeyEvent.VK_U);
        	wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        	wiersz.getAccessibleContext().setAccessibleDescription("Ustawienia programu");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("2");
        plik.add(wiersz);
    	plik.addSeparator();
    	*/
    	wiersz = new JMenuItem("Wyloguj się", KeyEvent.VK_Y);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Zrywa połączenie z bazą i wraca do ekranu logowania");
            wiersz.addActionListener(nasluchiwaczAkcji);
            wiersz.setActionCommand("WYLOGOWANIE");
		plik.add(wiersz);
    	plik.addSeparator();
    	
    	wiersz = new JMenuItem("Wyjdź", KeyEvent.VK_W);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Wychodzi z programu");
            wiersz.addActionListener(nasluchiwaczAkcji);
            wiersz.setActionCommand("WYJSCIE");
		plik.add(wiersz);
		return pasekMenu;		
	}
	
	private static void StworzIPokazGIU()
	{
		Menu menu= new Menu();
        ramka = new JFrame("Baza danych");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setSize(300,150);
        ramka.add(menu);
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StworzIPokazGIU();
            }
        });
	}
}
