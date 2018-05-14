package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logikaIgre.Igra;
import logikaIgre.Polje;
import logikaIgre.TipPolja;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	//mogoce bi se lahko odlocila za en jezik
	private Okno master;
	//line width nekoliko manjsi kot v tictactoe primeru
	private final static double LINE_WIDTH = 0.025;
	// cez padding bodo prisle ovire, kjer bodo pac generirane.
	private final static double PADDING = 0.1;
	private int oznaceno_i;
	private int oznaceno_j;
	private boolean oznaci = false;
	

	public IgralnoPolje(Okno master) {
		super();
		this.master = master;
		setBackground(Color.GRAY);
		this.addMouseListener(this);
		}

	//naslikaj CRNO in BELO vzeto iz tictactoe, tako kot vecina tega dela kode.
	private void naslikajCRNO(Graphics2D g2, int i, int j) {
		double w = sirina_polja();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	private void naslikajBELO(Graphics2D g2, int i, int j) {
		double w = sirina_polja();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	// manjka metoda za ovire
	
	private void naslikajOVIRO(Graphics2D g2, int i, int j) {
		double h = sirina_polja();
		double w = PADDING;
		g2.setColor(Color.blue); //morda bi lahko zamenjala barvo.
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH))); //rabiva tu sploh stroke, pravokotnik bo itak nafilan?
		g2.fillRect((int)i, (int)j, (int)w, (int)h);
		
	}
	

	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	private double sirina_polja() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
}
	
	protected void paintComponent(Graphics g) {
		// se nepopravljeno za najino igro.
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		// širina kvadratka
		double w = sirina_polja();
		// črte
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		// naraisi polje
		g2.drawLine(0, 0, 0, (int)((Igra.N - LINE_WIDTH) * w));
		g2.drawLine(0, 0, (int)((Igra.N - LINE_WIDTH) * w), 0);
		g2.drawLine((int)((Igra.N - LINE_WIDTH) * w), 0, (int)((Igra.N - LINE_WIDTH) * w), (int)((Igra.N - LINE_WIDTH) * w));
		g2.drawLine(0, (int)((Igra.N - LINE_WIDTH) * w), (int)((Igra.N - LINE_WIDTH) * w), (int)((Igra.N - LINE_WIDTH) * w));
		for (int k = 1; k < Igra.N; k++) {
			// tu manjkajo se stranske crte.
			g2.drawLine((int)(k * w),
					    (int)(LINE_WIDTH * w),
					    (int)(k * w),
					    (int)((Igra.N - LINE_WIDTH) * w));
			g2.drawLine((int)(LINE_WIDTH * w),
					    (int)(k * w),
					    (int)((Igra.N - LINE_WIDTH) * w),
					    (int)(k * w));
		}
		if(oznaci) {
			g2.setColor(Color.orange);
			//y crta
			g2.drawLine((int)(oznaceno_i * w),
				    (int)(oznaceno_j * w),
				    (int)(oznaceno_i * w),
				    (int)((oznaceno_j + 1) * w));
			//x crta
			g2.drawLine((int)(oznaceno_i * w),
				    (int)(oznaceno_j * w),
				    (int)((oznaceno_i + 1) * w),
				    (int)(oznaceno_j * w));
			g2.drawLine((int)((oznaceno_i+1) * w),
				    (int)(oznaceno_j * w),
				    (int)((oznaceno_i+1) * w),
				    (int)((oznaceno_j + 1) * w));
			g2.drawLine((int)(oznaceno_i * w),
				    (int)((oznaceno_j+1) * w),
				    (int)((oznaceno_i + 1) * w),
				    (int)((oznaceno_j+1) * w));
		}

		
		// bele crne
		Polje[][] plosca = master.getPlosca();
		if (plosca != null) {
			for (int k = 0; k < Igra.N; k++) {
				for (int l = 0; l < Igra.N; l++) {
					switch(plosca[k][l].tip) {
					case BELO: naslikajBELO(g2, k, l); break;
					case CRNO: naslikajCRNO(g2, k, l); break;
					default: break;
					}
				}
			}
		}
		// dodati morava se ovire in neko metodo da popravi aktivno polje., verjetno mora biti torej tu nekje aktivno?
	}
	@Override // Isto kot pri TicTacToe
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int w = (int)(sirina_polja());
		int i = x / w;
		int j = y / w;
		double di = (x % w) / sirina_polja() ;
		double dj = (y % w) / sirina_polja() ;
		if(0<=i && i<Igra.N &&
		   0<=j && j<Igra.N &&
		   0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
		   0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
			master.klikniPolje(i, j);
			oznaci_polje(i, j);
		}
		
	}

	private void oznaci_polje(int i, int j) {
		Polje[][] plosca = master.getPlosca();
		if(plosca[i][j].tip == TipPolja.BELO || plosca[i][j].tip == TipPolja.CRNO) {
			if(i!=oznaceno_i || j!=oznaceno_j) {
				oznaceno_i = i;
				oznaceno_j = j;
				oznaci = true;
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
