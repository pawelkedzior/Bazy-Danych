package bazy;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import bazy.RekordyBazy.*;

public class ObslugaBazy {
	public PolaczenieZBaza polaczenieZBaza;
	
	public ObslugaBazy(){
		polaczenieZBaza=new PolaczenieZBaza();
	}
	
	public static class Uzytkownik
	{
		public String login;
		public String typKonta;
		public String sklep;
		
		private Uzytkownik(String login, String konto, String sklep)
		{
			this.login=login;
			this.typKonta=konto;
			this.sklep=sklep;
		}
		
		private Uzytkownik(String login, String konto)
		{
			this.login=login;
			this.typKonta=konto;
			this.sklep=null;
		}
		
		public static Uzytkownik Wlasciciel=new Uzytkownik("","Właściciel");
		public static Uzytkownik KoordynatorSklepu=new Uzytkownik("","Koordynator","");
		public static Uzytkownik Pracownik=new Uzytkownik("","Pracownik","");
		
		public void ustawSklep(String lokalizacja)
		{
			this.sklep=lokalizacja;
		}
		
		public void ustawLogin(String login)
		{
			this.login=login;
		}
		
		public String[] daneZalogowanegoUzytkownika()
		{
			String dane[]=new String[3];
			dane[0]=login;
			dane[1]=typKonta;
			dane[2]=sklep;
			return dane;
		}
	}
	
	public class PolaczenieZBaza
	{
		public Connection polaczenie;
			
		public boolean zainicjujPolaczenie(String uzytkownik, String haslo)
		{
			try {
				Class.forName("com.mysql.jdbc.Driver");
				polaczenie=DriverManager.getConnection(
						"jdbc:mysql://localhost/siecsklepow?user="+uzytkownik+"&password="+haslo+
						"&autoReconnect=true&useSSL=false");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				nieudanePolaczenie();
				return false;
			}
			return true;
		}
		
		public Connection dajPolaczenie()
		{
			return polaczenie;
		}

		private void nieudanePolaczenie()
		{
			JOptionPane.showMessageDialog(Menu.ramka, "Połączenie nieudane.", "Błąd Połączenia", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static class Tabela
	{		
		public static Rekord[] pobierzTabele(String nazwaTabeli, Connection polaczenie, String instr)
		{
			Rekord wierszeTabeli[] = null;
			try {
				PreparedStatement instrukcja=polaczenie.prepareStatement("CREATE TEMPORARY TABLE "
						+ "Tymczasowa LIKE "+nazwaTabeli);
				instrukcja.executeUpdate();
				instrukcja=polaczenie.prepareStatement("INSERT INTO Tymczasowa ("
						+instr+")");
				instrukcja.executeUpdate();
				instrukcja=polaczenie.prepareStatement("SELECT COUNT(*) FROM Tymczasowa"
						+ " AS LiczbaWierszy");
				ResultSet wyniki=instrukcja.executeQuery();
				wyniki.next();
				wierszeTabeli=new Rekord[wyniki.getInt("LiczbaWierszy")];
				instrukcja=polaczenie.prepareStatement(instr);
				wyniki=instrukcja.executeQuery();
				int i=0;
				while(!wyniki.isLast())
				{
					wyniki.next();
					Rekord nastepnyWiersz=Rekord.stworzRekordZWynikow(wyniki, nazwaTabeli);
					wierszeTabeli[i]=nastepnyWiersz;
					i++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return wierszeTabeli;
		}
	
		public static JTable zbudujWyswietlanaTabele(Rekord[] wiersze, JTable tabela)
		{
			Object dane[][]=new Object[wiersze.length][wiersze[0].liczbaKolumn];
			for (int i=0;i<wiersze.length;i++)
			{
				dane[i]=wiersze[i].wierszZRekordu();
			}
			String naglowki[]=wiersze[0].naglowkiTabeli();
			TableModel modelTabeli=new AbstractTableModel() {

				private static final long serialVersionUID = -1828468825563542445L;
				private String[] nazwyKolumn=naglowki;
				private Object[][] daneTabeli=dane;
			
				public String getColumnName(int col) {
			        return nazwyKolumn[col].toString();
			    }
			    public int getRowCount() { return daneTabeli.length; }
			    public int getColumnCount() { return nazwyKolumn.length; }
			    public Object getValueAt(int row, int col) {
			        return daneTabeli[row][col];
			    }
			    public boolean isCellEditable(int row, int col)
			        { return false; }
			    public void setValueAt(Object value, int row, int col) {
			        daneTabeli[row][col] = value;
			        fireTableCellUpdated(row, col);
			    }
			};
			tabela.setModel(modelTabeli);
			return tabela;
		}
	}
}
