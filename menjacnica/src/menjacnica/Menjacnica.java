package menjacnica;

import java.util.LinkedList;
import menjacnica.sistemske_operacije.SODodajValutu;
import menjacnica.sistemske_operacije.SOIzvrsiTransakciju;
import menjacnica.sistemske_operacije.SOObrisiValutu;
import menjacnica.sistemske_operacije.SOSacuvajUFajl;
import menjacnica.sistemske_operacije.SOUcitajIzFajla;
import menjacnica.sistemske_operacije.SOVratiKursnuListu;

public class Menjacnica implements MenjacnicaInterface {

	private LinkedList<Valuta> kursnaLista = new LinkedList<Valuta>();

	@Override
	public void dodajValutu(Valuta valuta) {
		SODodajValutu.izvrsi(valuta, kursnaLista);
	}

	@Override
	public void obrisiValutu(Valuta valuta) {
		SOObrisiValutu.izvrsi(valuta, kursnaLista);
	}

	@Override
	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos) {
		return SOIzvrsiTransakciju.izvrsi(valuta, prodaja, iznos);
	}

	@Override
	public LinkedList<Valuta> vratiKursnuListu() {
		return SOVratiKursnuListu.izvrsi(kursnaLista);
	}

	@Override
	public void ucitajIzFajla(String putanja) {
		kursnaLista = SOUcitajIzFajla.izvrsi(putanja, kursnaLista);
	}

	@Override
	public void sacuvajUFajl(String putanja) {
		SOSacuvajUFajl.izvrsi(putanja, kursnaLista);
	}

}
