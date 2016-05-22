package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	private static MenjacnicaGUI glavniProzor;
	protected static Menjacnica sistem;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					glavniProzor = new MenjacnicaGUI();
					glavniProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(null,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(glavniProzor);
		prozor.setVisible(true);
	}
	
	public static  void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavniProzor,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl(Menjacnica sistem) {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, double prodajni, double srednji, double kupovni){
		try {
			Valuta valuta = new Valuta();
			
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			sistem.dodajValutu(valuta);
			
			glavniProzor.prikaziSveValute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(glavniProzor, e.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			sistem.obrisiValutu(valuta);
			
			glavniProzor.prikaziSveValute();
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static double izracunajIznos(Valuta valuta,boolean prodaja, double iznos){
		try{
			double konacniIznos = 
					sistem.izvrsiTransakciju(valuta,prodaja,iznos);
		return konacniIznos;
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
		return 0;
	}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				glavniProzor.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
public static void prikaziObrisiKursGUI() {
		
		if (glavniProzor.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(glavniProzor.table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(
					model.vratiValutu(glavniProzor.table.getSelectedRow()));
			prozor.setLocationRelativeTo(glavniProzor);
			prozor.setVisible(true);
		}
	}

public static void prikaziIzvrsiZamenuGUI() {
	if (glavniProzor.table.getSelectedRow() != -1) {
		MenjacnicaTableModel model = (MenjacnicaTableModel)(glavniProzor.table.getModel());
		IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(
				model.vratiValutu(glavniProzor.table.getSelectedRow()));
		prozor.setLocationRelativeTo(glavniProzor);
		prozor.setVisible(true);
	}
}

}
