//bartosz.porebski@gmail.com
package bazy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import bazy.RekordyTabelPomocniczych.*;

public class RekordyBazy {
	
	public static class Adres
	{
		String ulica;
		String numerDomuMieszkania;
		String kodPocztowy;
		String miejscowosc;
		public Adres(String ulica, String numer, String kodPocztowy, String miejscowosc)
		{
			this.ulica=ulica;
			this.numerDomuMieszkania=numer;
			this.kodPocztowy=kodPocztowy;
			this.miejscowosc=miejscowosc;
		}
		public Adres(String numer, String kodPocztowy, String miejscowosc)
		{
			this(miejscowosc, numer, kodPocztowy, miejscowosc);
		}
	}
	
	public static abstract class Rekord
	{
		public static Rekord instancja;
		public int liczbaKolumn;
		
		public Rekord(int lK)
		{
			instancja=this;
			liczbaKolumn=lK;
		}
		
		public static Rekord stworzRekordZWynikow(ResultSet wyniki, String nazwaTabeli) {
			switch(nazwaTabeli)
			{
				case "Sklepy":
					return Sklep.instancja.nowyRekordZWynikow(wyniki);
				case "Pracownicy":
					return Pracownik.instancja.nowyRekordZWynikow(wyniki);
				case "Dostawy":
					return Dostawa.instancja.nowyRekordZWynikow(wyniki);
				case "Zamowienia":
					return Zamowienie.instancja.nowyRekordZWynikow(wyniki);
				case "Produkty":
					return Produkty.instancja.nowyRekordZWynikow(wyniki);
				case "Magazyny":
					return Magazyn.instancja.nowyRekordZWynikow(wyniki);
				case "Wydatki":
					return Wydatki.instancja.nowyRekordZWynikow(wyniki);
				case "Przychody":
					return Przychod.instancja.nowyRekordZWynikow(wyniki);
				case "Wyplaty":
					return Wyplata.instancja.nowyRekordZWynikow(wyniki);
				case "Utrzymania":
					return Utrzymanie.instancja.nowyRekordZWynikow(wyniki);
				case "proponowaniPracownicy":
					return ProponowaniPracownicy.instancja.nowyRekordZWynikow(wyniki);
				case "proponowanePremie":
					return ProponowanePremie.instancja.nowyRekordZWynikow(wyniki);
				case "proponowanePodwyzki":
					return ProponowanePodwyzki.instancja.nowyRekordZWynikow(wyniki);
				default:
					return null;
			}
		}
		
		public abstract Rekord nowyRekordZWynikow(ResultSet wyniki);
		
		public abstract Object[] wierszZRekordu();
		
		public abstract String[] naglowkiTabeli();
	}
	
	public static class Sklep extends Rekord
	{
		public static Sklep instancja=new Sklep(null, null);
		
		Adres adres;
		String lokalizacja;
		public Sklep(Adres adres, String lokalizacja)
		{
			super(5);
			this.adres=adres;
			this.lokalizacja=lokalizacja;
		}
		
		public Przychod generujprzychod()
		{
			Przychod nowyPrzychod = null;
			return nowyPrzychod;
		}
		
		public Utrzymanie generujUtrzymanie()
		{
			Utrzymanie noweKosztyUtrzymania = null;
			return noweKosztyUtrzymania;
		}

		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Sklep zwracanyRekord=null;
			try
			{
				Adres adres=new Adres(wyniki.getString("Ulica"), wyniki.getString("Numer"), 
					wyniki.getString("KodPocztowy"), wyniki.getString("Miejscowosc"));
				zwracanyRekord=new Sklep(adres, wyniki.getString("Lokalizacja"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}

		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=lokalizacja;
			wiersz[1]=adres.ulica;
			wiersz[2]=adres.numerDomuMieszkania;
			wiersz[3]=adres.kodPocztowy;
			wiersz[4]=adres.miejscowosc;
			return wiersz;
		}

		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="Lokalizacja (skrót)";
			naglowki[1]="Ulica";
			naglowki[2]="Numer domu";
			naglowki[3]="Kod pocztowy";
			naglowki[4]="Miejscowowść";
			return naglowki;
		}
	}
	
	public static class Przychod extends Rekord
	{
		public static Przychod instancja=new Przychod(null, 0.0, null, null);
		
		String idPrzychodu;
		double wysokosc;
		String sklepZrodlowyPrzychodu;
		GregorianCalendar data;
		public Przychod(String id, double wysokosc, String zrodlo, GregorianCalendar data)
		{
			super(4);
			this.idPrzychodu=id;
			this.wysokosc=wysokosc;
			this.sklepZrodlowyPrzychodu=zrodlo;
			this.data=data;
		}
		
		public String pokazZrodloPrzychodu()
		{
			return sklepZrodlowyPrzychodu;
		}

		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Przychod zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getTimestamp("Data"));
				zwracanyRekord=new Przychod(wyniki.getString("IdPrzychodu"), 
							wyniki.getDouble("Wysokosc"), wyniki.getString("Zrodlo"), data);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}

		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=idPrzychodu;
			wiersz[1]=wysokosc;
			wiersz[2]=sklepZrodlowyPrzychodu;
			wiersz[3]=data;
			return wiersz;
		}

		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="ID Przychodu";
			naglowki[1]="Wysokość";
			naglowki[2]="Źródło";
			naglowki[3]="Data zainkasowania";
			return naglowki;
		}
	}
	
	public static class Utrzymanie extends Rekord
	{
		public static Utrzymanie instancja=new Utrzymanie(0, 0.0, null, null);
		
		int idUtrzymania;
		double wysokosc;
		String sklepZrodlowyUtrzymania;
		GregorianCalendar dataZaplaty;
		Utrzymanie(int id, double wysokosc, String zrodlo, GregorianCalendar data)
		{
			super(4);
			this.idUtrzymania=id;
			this.wysokosc=wysokosc;
			this.sklepZrodlowyUtrzymania=zrodlo;
			this.dataZaplaty=data;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Utrzymanie zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getTimestamp("DataZaplaty"));
				zwracanyRekord=new Utrzymanie(wyniki.getInt("Lp"), 
							wyniki.getDouble("Wysokosc"), wyniki.getString("Zrodlo"), data);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}			
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=idUtrzymania;
			wiersz[1]=wysokosc;
			wiersz[2]=sklepZrodlowyUtrzymania;
			wiersz[3]=dataZaplaty;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="ID Utrzymania";
			naglowki[1]="Wysokość";
			naglowki[2]="Źródło";
			naglowki[3]="Data zapłaty";
			return naglowki;
		}
	}
	
	public static class Wyplata extends Rekord
	{
		public static Wyplata instancja=new Wyplata(null, 0.0, 0.0, null, null);
		
		String idWyplaty;
		double wysokoscWyplaty;
		double wysokoscPremii;
		String idPracownikaOtrzymujacego;
		GregorianCalendar dataOtrzymaniaWyplaty;
		Wyplata(String id, double wysokosc, double premia, String pracownik, GregorianCalendar data)
		{
			super(5);
			this.idWyplaty=id;
			this.wysokoscWyplaty=wysokosc;
			this.wysokoscPremii=premia;
			this.idPracownikaOtrzymujacego=pracownik;
			this.dataOtrzymaniaWyplaty=data;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Wyplata zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getTimestamp("DataWyplaty"));
				zwracanyRekord=new Wyplata(wyniki.getString("IdWyplaty"), 
							wyniki.getDouble("Wysokosc"), wyniki.getDouble("Premia"),
							wyniki.getString("IdPracownika"), data);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=idWyplaty;
			wiersz[1]=wysokoscWyplaty;
			wiersz[2]=wysokoscPremii;
			wiersz[3]=idPracownikaOtrzymujacego;
			wiersz[4]=dataOtrzymaniaWyplaty;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="ID Wypłaty";
			naglowki[1]="Wysokość";
			naglowki[2]="Premia";
			naglowki[3]="Otrzymujący";
			naglowki[4]="Data przeksięgowania";
			return naglowki;
		}
	}
	
	public static class Pracownik extends Rekord
	{
		public static Pracownik instancja=new Pracownik(null, null, null, null, null, 0.0, null);
		
		String imie;
		String nazwisko;
		String idPracownika;
		String stanowisko;
		String przelozony;
		double wysokoscNormalnejPensji;
		String sklep;
		//private static String ostatnieID="Prac000";
		
		public Pracownik(String imie, String nazwisko, String id, String stanowisko, String przelozony, double pensja, String sklep)
		{
			super(7);
			this.imie=imie;
			this.nazwisko=nazwisko;
			this.idPracownika=id;
			this.stanowisko=stanowisko;
			this.przelozony=przelozony;
			this.wysokoscNormalnejPensji=pensja;
			this.sklep=sklep;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Pracownik zwracanyRekord=null;
			try
			{
				zwracanyRekord=new Pracownik(wyniki.getString("Imie"), wyniki.getString("Nazwisko"),
							wyniki.getString("IdPracownika"), wyniki.getString("Stanowisko"),
							wyniki.getString("Przelozony"), wyniki.getDouble("Wyplata"), 
							wyniki.getString("Sklep"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=imie;
			wiersz[1]=nazwisko;
			wiersz[2]=idPracownika;
			wiersz[3]=stanowisko;
			wiersz[4]=przelozony;
			wiersz[5]=wysokoscNormalnejPensji;
			wiersz[6]=sklep;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="Imię";
			naglowki[1]="Nazwisko";
			naglowki[2]="Identyfikator";
			naglowki[3]="Stanowisko";
			naglowki[4]="Przełożony";
			naglowki[5]="Wysokość Pensji";
			naglowki[6]="Sklep zatrudniający";
			return naglowki;
		}
		
		/*private String uzyskajID() {
			String noweID=ostatnieID;
			String numer=noweID.substring(4);
			numer=Integer.toString(Integer.parseInt(numer)+1);
			noweID=noweID.substring(0, 7-numer.length())+numer;
			ostatnieID=noweID;
			return noweID;
		}*/
	}
	
	public static class Wydatki extends Rekord
	{
		public static Wydatki instancja=new Wydatki(null, 0.0, null, null);
		
		String idWydatkow;
		double wysokosc;
		String zrodloWydatkow;
		GregorianCalendar dataZaplaty;
		public Wydatki(String id, double wysokosc, String zrodlo, GregorianCalendar data)
		{
			super(4);
			this.idWydatkow=id;
			this.wysokosc=wysokosc;
			this.zrodloWydatkow=zrodlo;
			this.dataZaplaty=data;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Wydatki zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getTimestamp("Data"));
				zwracanyRekord=new Wydatki(wyniki.getString("IdWydatku"), 
						wyniki.getDouble("Wysokosc"), wyniki.getString("Zrodlo"), data);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=idWydatkow;
			wiersz[1]=wysokosc;
			wiersz[2]=zrodloWydatkow;
			wiersz[3]=dataZaplaty;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="ID Wydatków";
			naglowki[1]="Wysokość";
			naglowki[2]="Źródło";
			naglowki[3]="Data zapłaty";
			return naglowki;
		}
	}
	
	public static class Zamowienie extends Rekord
	{
		public static Zamowienie instancja=new Zamowienie(null, null, null, null);
		
		String idZamowienia;
		String celZamowienia;
		GregorianCalendar dataZlozeniaZamowienia;
		String zleceniodawca;
		public Zamowienie(String id, String cel, GregorianCalendar data, String zleceniodawca)
		{
			super(4);
			this.idZamowienia=id;
			this.celZamowienia=cel;
			this.dataZlozeniaZamowienia=data;
			this.zleceniodawca=zleceniodawca;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Zamowienie zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getTimestamp("DataZlozenia"));
				zwracanyRekord=new Zamowienie(wyniki.getString("IdZamowienia"),
								wyniki.getString("CelZamowienia"), data, 
								wyniki.getString("Zleceniodawca"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=idZamowienia;
			wiersz[1]=celZamowienia;
			wiersz[2]=dataZlozeniaZamowienia;
			wiersz[3]=zleceniodawca;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="ID Zamówienia";
			naglowki[1]="Sklep docelowy";
			naglowki[2]="Data złożenia";
			naglowki[3]="Zleceniodawca";
			return naglowki;
		}
	}
	
	public static class Produkty extends Rekord
	{
		public static Produkty instancja=new Produkty(null, null, null, null, 0.0, null);
		
		String magazynDocelowy;
		String numerZamowienia;
		String numerDostawy;
		String obecnePolozenie;
		double iloscZamowionychProduktow;
		String rodzajProduktow;
		public Produkty(String cel, String zamowienie, String dostawa, String polozenie, double ilosc, String rodzaj)
		{
			super(6);
			this.magazynDocelowy=cel;
			this.numerZamowienia=zamowienie;
			this.numerDostawy=dostawa;
			this.obecnePolozenie=polozenie;
			this.iloscZamowionychProduktow=ilosc;
			this.rodzajProduktow=rodzaj;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Produkty zwracanyRekord=null;
			try
			{
				zwracanyRekord=new Produkty(wyniki.getString("MagazynDocelowy"), 
						wyniki.getString("NumerZamowienia"), wyniki.getString("NumerDostawy"), 
						wyniki.getString("MiejscePrzebywania"), wyniki.getDouble("Ilosc"), 
						wyniki.getString("Rodzaj"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=magazynDocelowy;
			wiersz[1]=numerZamowienia;
			wiersz[2]=numerDostawy;
			wiersz[3]=obecnePolozenie;
			wiersz[4]=rodzajProduktow;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="Magazyn docelowy";
			naglowki[1]="Numer zamówienia";
			naglowki[2]="Numer dostawy";
			naglowki[3]="Obecne położenie";
			naglowki[4]="Rodzaj";
			return naglowki;
		}
	}
	
	public static class Dostawa extends Rekord
	{
		public static Dostawa instancja=new Dostawa(null, null, null, 0.0, null);
		
		String idDostawy;
		String celDostawy;
		GregorianCalendar dataDostarczenia;
		double wysokoscOplaty;
		String numerZamowienia;
		public Dostawa(String id, String cel, GregorianCalendar data, double oplata, String zamowienie)
		{
			super(5);
			this.idDostawy=id;
			this.celDostawy=cel;
			this.dataDostarczenia=data;
			this.wysokoscOplaty=oplata;
			this.numerZamowienia=zamowienie;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Dostawa zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getTimestamp("DataDostawy"));
				zwracanyRekord=new Dostawa(wyniki.getString("IdDostawy"), 
						wyniki.getString("CelDostawy"), data, wyniki.getDouble("Oplata"), 
						wyniki.getString("NumerZamowienia"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[liczbaKolumn];
			wiersz[0]=idDostawy;
			wiersz[1]=celDostawy;
			wiersz[2]=dataDostarczenia;
			wiersz[3]=wysokoscOplaty;
			wiersz[4]=numerZamowienia;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="ID dostawy";
			naglowki[1]="Sklep docelowy";
			naglowki[2]="Data dostarczenia";
			naglowki[3]="Wysokość opłaty";
			naglowki[4]="Numer zamówienia";
			return naglowki;
		}
	}
	
	public static class Magazyn extends Rekord
	{
		public static Magazyn instancja=new Magazyn(null);
		
		String sklep;
		//Produkty zawartoscMagazynu[];
		public Magazyn(String sklep)
		{
			super(1);
			this.sklep=sklep;
		}
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			Magazyn zwracanyRekord=null;
			try
			{
				zwracanyRekord=new Magazyn(wyniki.getString("Sklep"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}
		@Override
		public Object[] wierszZRekordu() {
			Object wiersz[]=new Object[1];
			wiersz[0]=sklep;
			return wiersz;
		}
		@Override
		public String[] naglowkiTabeli() {
			String naglowki[]=new String[liczbaKolumn];
			naglowki[0]="Lokalizacja sklepu";
			return naglowki;
		}
	}
}
