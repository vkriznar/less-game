package gui;

import javax.swing.SwingWorker;

import inteligenca.AlphaBeta;
import inteligenca.AlphaBeta2;
import inteligenca.Minimax;
import logikaIgre.Igralec;
import logikaIgre.Poteza;


public class Racunalnik extends Strateg {
	private Okno master;
	private Igralec jaz;
	private SwingWorker<Poteza, Object> mislec;
	protected static int stopnja = 4;
	public Racunalnik(Okno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	@Override
	public void na_potezi() {
		// Začnemo razmišljati
		mislec = new AlphaBeta2(master, stopnja, jaz);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
}

	@Override
	public void klik(int i, int j) {
	}
	
	@Override
	public void tezavnost(int k) {
		stopnja = k;
	}

}
