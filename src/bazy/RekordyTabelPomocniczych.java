package bazy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import bazy.RekordyBazy.Rekord;

public class RekordyTabelPomocniczych {

	public static class ProponowaniPracownicy extends RekordyBazy.Rekord
	{
		public static ProponowaniPracownicy instancja=new ProponowaniPracownicy(null, null, null, null, null, 0.0, null);
		
		String imie;
		String nazwisko;
		String idPracownika;
		String stanowisko;
		String proponujacyPracownika;
		double wysokoscNormalnejPensji;
		String sklep;
		
		ProponowaniPracownicy(String imie, String nazwisko, String id, String stanowisko, String proponujacy, double pensja, String sklep)
		{
			super(7);
			this.imie=imie;
			this.nazwisko=nazwisko;
			this.idPracownika=id;
			this.stanowisko=stanowisko;
			this.proponujacyPracownika=proponujacy;
			this.wysokoscNormalnejPensji=pensja;
			this.sklep=sklep;
		}
		
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			ProponowaniPracownicy zwracanyRekord=null;
			try
			{
				zwracanyRekord=new ProponowaniPracownicy(wyniki.getString("Imie"), 
							wyniki.getString("Nazwisko"),
							wyniki.getString("IdPracownika"), wyniki.getString("Stanowisko"),
							wyniki.getString("Proponujacy"), wyniki.getDouble("Wyplata"), 
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] naglowkiTabeli() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class ProponowanePremie extends RekordyBazy.Rekord
	{
		public static ProponowanePremie instancja=new ProponowanePremie(null, 0.0, null, null);
		
		String idPracownikaDoPremii;
		double proponowanaWysokoscPremii;
		GregorianCalendar data;
		String proponujacyPremie;
		
		ProponowanePremie(String idPracownika, double wysokosc, GregorianCalendar data, String proponujacy)
		{
			super(4);
			this.idPracownikaDoPremii=idPracownika;
			this.proponowanaWysokoscPremii=wysokosc;
			this.data=data;
			this.proponujacyPremie=proponujacy;
		}
		
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			ProponowanePremie zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getDate("DataPropozycji"));
				zwracanyRekord=new ProponowanePremie(wyniki.getString("IdPracownika"),
							wyniki.getDouble("Premia"), data, 
							wyniki.getString("Proponujacy"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}

		@Override
		public Object[] wierszZRekordu() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] naglowkiTabeli() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class ProponowanePodwyzki extends RekordyBazy.Rekord
	{
		public static ProponowanePodwyzki instancja=new ProponowanePodwyzki(null, 0.0, 0.0, null, null);
		
		String idPracownikaDoPodwyzki;
		double staraWysokoscPensji;
		double nowaWysokoscPensji;
		GregorianCalendar data;
		String proponujacyPodwyzke;
		
		ProponowanePodwyzki(String idPracownika, double staraWysokosc,double nowaWysokosc, GregorianCalendar data, String proponujacy)
		{
			super(5);
			this.idPracownikaDoPodwyzki=idPracownika;
			this.staraWysokoscPensji=staraWysokosc;
			this.nowaWysokoscPensji=nowaWysokosc;
			this.data=data;
			this.proponujacyPodwyzke=proponujacy;
		}
		
		@Override
		public Rekord nowyRekordZWynikow(ResultSet wyniki) {
			ProponowanePodwyzki zwracanyRekord=null;
			try
			{
				GregorianCalendar data=new GregorianCalendar();
				data.setTime(wyniki.getDate("DataPropozycji"));
				zwracanyRekord=new ProponowanePodwyzki(wyniki.getString("IdPracownika"),
							wyniki.getDouble("WysokoscStara"), wyniki.getDouble("WysokoscNowa"),
							data, wyniki.getString("Proponujacy"));
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return zwracanyRekord;
		}

		@Override
		public Object[] wierszZRekordu() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] naglowkiTabeli() {
			// TODO Auto-generated method stub
			return null;
		}
	}	
}
