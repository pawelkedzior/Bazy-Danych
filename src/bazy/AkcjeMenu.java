package bazy;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import bazy.ObslugaBazy.Tabela;
import bazy.ObslugaBazy.Uzytkownik;

public class AkcjeMenu implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent wydarzenie) {
		String typWydarzenia=wydarzenie.getActionCommand();
		switch(typWydarzenia)
		{
		case "LOGOWANIE":
			JComponent zrodlo=(JComponent) wydarzenie.getSource();
			Menu rodzic=(Menu) zrodlo.getParent().getParent();
			boolean czyPowodzenie=Menu.interfejsObslugi.polaczenieZBaza.zainicjujPolaczenie(
					Menu.poleTekstoweLoginu.getText(),
					Menu.poleTekstoweHasla.getText());
			if(czyPowodzenie)
			{
				if(Menu.poleTekstoweLoginu.getText().equals("wlasciciel"))
				{
					Menu.zalogowanyUzytkownik=Uzytkownik.Wlasciciel;
				}
				else
				{
					String przelozony="";
					try
					{
						PreparedStatement instrukcja=
								Menu.interfejsObslugi.polaczenieZBaza.polaczenie.prepareStatement(
										"SELECT * FROM Pracownicy WHERE IdPracownika = '"+
										Menu.poleTekstoweLoginu.getText()+"'");
						ResultSet wyniki=instrukcja.executeQuery();
						wyniki.next();
						przelozony=wyniki.getString("Przelozony");
						if(przelozony.equals("wlasciciel"))
						{
							Menu.zalogowanyUzytkownik=Uzytkownik.KoordynatorSklepu;
						}
						else
						{
							Menu.zalogowanyUzytkownik=Uzytkownik.Pracownik;
						}
						Menu.zalogowanyUzytkownik.ustawSklep(wyniki.getString("Sklep"));
					}catch(SQLException e){e.printStackTrace();}
				}
				Menu.zalogowanyUzytkownik.ustawLogin(Menu.poleTekstoweLoginu.getText());
				CardLayout uklad=(CardLayout) rodzic.getLayout();
				uklad.show(rodzic,"EKRANAPLIKACJI");
				Menu.wyswietlInformacjeOLogowaniu();
				Menu.wyswietlPanelNarzedzi();
				Menu.ramka.setJMenuBar(rodzic.tworzPasekMenu());
				Menu.ramka.setSize(1200,800);
				Menu.ramka.setLocationRelativeTo(null);
			}
		break;
		case "WYLOGOWANIE":
			//TODO
		break;
		case "WYSWMAGPRAC":
			Tabela.zbudujWyswietlanaTabele(Tabela.pobierzTabele("Produkty", 
					Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie(), 
					"SELECT * FROM Produkty WHERE MagazynDocelowy='"+
							Menu.zalogowanyUzytkownik.sklep+"' AND NOT MiejscePrzebywania='Sprzedane'"),
					Menu.tabela);
		break;
		case "WYSWZAMPRAC":
			Tabela.zbudujWyswietlanaTabele(Tabela.pobierzTabele("Zamowienia", 
					Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie(), 
					"SELECT * FROM Zamowienia WHERE CelZamowienia='"+
							Menu.zalogowanyUzytkownik.sklep+"' ORDER BY DataZlozenia DESC"),
					Menu.tabela);
		break;
		case "WYSWDOSPRAC":
			Tabela.zbudujWyswietlanaTabele(Tabela.pobierzTabele("Dostawy", 
					Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie(), 
					"SELECT * FROM Dostawy WHERE CelDostawy='"+
							Menu.zalogowanyUzytkownik.sklep+"' ORDER BY DataDostawy DESC"),
					Menu.tabela);
		break;
		case "NOWEZAMOWIENIE":
			//TODO
		break;
		case "NOWADOSTAWA":
			//TODO
		break;
		case "DOSTARCZONO":
			//TODO
		break;
		case "SPRZEDANO":
			//TODO
		break;
		case "WYSWPRZYCHKOORD":
			Tabela.zbudujWyswietlanaTabele(Tabela.pobierzTabele("Przychody", 
					Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie(), 
					"SELECT * FROM Przychody WHERE Zrodlo='"+
							Menu.zalogowanyUzytkownik.sklep+"' ORDER BY Data DESC"),
					Menu.tabela);
		break;
		case "WYSWUTRZKOORD":
			Tabela.zbudujWyswietlanaTabele(Tabela.pobierzTabele("Utrzymania", 
					Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie(), 
					"SELECT * FROM Utrzymania WHERE Zrodlo='"+
							Menu.zalogowanyUzytkownik.sklep+"' ORDER BY DataZaplaty DESC"),
					Menu.tabela);
		break;
		case "WYSWPRACKOORD":
			String pokaz="SELECT * FROM Pracownicy WHERE Sklep='"+
					Menu.zalogowanyUzytkownik.sklep+"' ORDER BY Stanowisko DESC, "
					+ "Nazwisko, Imie, IdPracownika";
			int a=0;
			a++;
			if(a==0)
			{}
			Tabela.zbudujWyswietlanaTabele(Tabela.pobierzTabele("Pracownicy", 
					Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie(), 
					"SELECT * FROM Pracownicy WHERE Sklep='"+
							Menu.zalogowanyUzytkownik.sklep+"' ORDER BY Stanowisko DESC, "
									+ "Nazwisko, Imie, IdPracownika"),
					Menu.tabela);
		break;
		case "PODWPRACKOORD":
			//TODO
		break;
		case "PREMIAPRACKOORD":
			//TODO
		break;
		case "NOWYPRACKOORD":
			//TODO
		break;
		case "LISTASKLEP":
			//TODO
		break;
		case "WYJSCIE":
			Connection polaczenie=Menu.interfejsObslugi.polaczenieZBaza.dajPolaczenie();
			try
			{
				if(!polaczenie.isClosed())
					polaczenie.close();
			}
			catch(SQLException|NullPointerException e)
			{
			}
			System.exit(0);
		break;
		}
	}
}
