package menjacnica.gui;

import java.awt.EventQueue;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	private static MenjacnicaInterface menjacnica;
	private static MenjacnicaGUI menjacnicaGUI;
	private static DodajKursGUI dodajKursGUI;
	private static IzvrsiZamenuGUI izvrsiZamenuGUI;
	private static ObrisiKursGUI obrisiKursGUI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica = new Menjacnica();
					menjacnicaGUI = new MenjacnicaGUI();
					menjacnicaGUI.setVisible(true);
					menjacnicaGUI.setLocationRelativeTo(null);
					menjacnicaGUI.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							ugasiAplikaciju();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static List<Valuta> vratiSveValute() {
		return menjacnica.vratiKursnuListu();
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGUI, "Da li ZAISTA zelite da izadjete iz apliacije",
				"Izlazak", JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGUI);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGUI);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaGUI.prikaziSveValute();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziDodajKursGUI() {
		dodajKursGUI = new DodajKursGUI();
		dodajKursGUI.setLocationRelativeTo(menjacnicaGUI);
		dodajKursGUI.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(JTable table) {

		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			obrisiKursGUI = new ObrisiKursGUI(model.vratiValutu(table.getSelectedRow()));
			obrisiKursGUI.setLocationRelativeTo(menjacnicaGUI);
			obrisiKursGUI.setVisible(true);
		}
	}

	public static void prikaziIzvrsiZamenuGUI(JTable table) {
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			izvrsiZamenuGUI = new IzvrsiZamenuGUI(model.vratiValutu(table.getSelectedRow()));
			izvrsiZamenuGUI.setLocationRelativeTo(menjacnicaGUI);
			izvrsiZamenuGUI.setVisible(true);
		}
	}

	public static void unesiKurs(Valuta valuta) {
		try {
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);
			// Osvezavanje glavnog prozora
			menjacnicaGUI.prikaziSveValute();
			// Zatvaranje DodajValutuGUI prozora
			dodajKursGUI.dispose();
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(menjacnicaGUI, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void obrisiValutu(Valuta valuta) {
		try {
			menjacnica.obrisiValutu(valuta);
			menjacnicaGUI.prikaziSveValute();
			obrisiKursGUI.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGUI, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static double izvrsiZamenu(Valuta valuta, boolean prodaja, double iznos) {
		return menjacnica.izvrsiTransakciju(valuta, prodaja, iznos);
	}

}
