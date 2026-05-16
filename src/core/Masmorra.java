package core;

import java.util.Random;

import model.Direccions;
import model.Monstre;
import model.Personatge;
import model.Tresor;
import sala.Sala;
import sala.SalaComuna;
import sala.SalaJefe;
import sala.SalaPont;
import sala.SalaTeranyina;
import utils.Colors;
import utils.Estils;

public class Masmorra {
	// Matriu sala
	private int x;
	private int y;
	public static final int MIN_MIDA_MASMORRA = 3;
	public static final int MAX_MIDA_MASMORRA = 20;

	private Sala[][] mapa = new Sala[x][y];
	private int salesExplorades = 0;
	private int salesTotals;

	// Posicio del jefe
	private int[] posicioJefe;

	// Personatge
	private Personatge personatge;

	// Tresors posibles
	private static final Tresor[] LLISTA_TRESORS = {
			new Tresor("Copa d'Or", 100, 2.5),
			new Tresor("Anell de Plata", 50, 0.5),
			new Tresor("Diamant Brut", 500, 1.0)
	};

	// Monstres posibles
	private static final Monstre[] LLISTA_MONSTRES = {
			new Monstre("Zombie", 2, 0, 2),
			new Monstre("Aranya", 3, 0, 1),
			new Monstre("Esquelet", 1, 0, 2)
	};

	public Masmorra(int x, int y, Personatge personatge) {
		this.x = x;
		this.y = y;
		this.salesTotals = this.x * this.y;
		this.personatge = personatge;
		this.posicioJefe = new int[] { x / 2, y / 2 };
		this.mapa = generarMasmorra();
	}

	private Tresor generarTresor() {
		if (Math.random() < 0.30) {
			Tresor plantillaTresor = LLISTA_TRESORS[(int) (Math.random() * LLISTA_TRESORS.length)];
			return new Tresor(plantillaTresor.getNom(), plantillaTresor.getValor(), plantillaTresor.getPes());
		}
		return null;
	}

	private Monstre generarMonstre() {
		if (Math.random() < 0.40) {
			Monstre plantillaMonstre = LLISTA_MONSTRES[(int) (Math.random() * LLISTA_MONSTRES.length)];
			return new Monstre(plantillaMonstre.getNom(), plantillaMonstre.getVida(), plantillaMonstre.getAtac(),
					plantillaMonstre.getPenalitzacio());
		}
		return null;
	}

	private Monstre generarJefe() {
		String[] nombres = {
				"Capitan Solnan",
				"Stageddat"
		};

		Random rand = new Random();
		String nombreRandom = nombres[rand.nextInt(nombres.length)];

		return new Monstre(nombreRandom, 30, 5, 9999);
	}

	public Sala[][] generarMasmorra() {
		Sala[][] nouMapa = new Sala[this.x][this.y];
		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {

				// Sala del jefe al centre
				if (i == posicioJefe[0] && j == posicioJefe[1]) {
					nouMapa[i][j] = new SalaJefe(new Tresor("Tresor del jefe", 1000, 5.0), generarJefe(), false);
					continue;
				}

				double r = Math.random();
				// 60% sala comuna
				// 20% pont
				// 20% teranyina
				if (r < 0.60) {
					nouMapa[i][j] = new SalaComuna(generarTresor(), generarMonstre(), false);
				} else if (r < 0.80) {
					nouMapa[i][j] = new SalaPont(generarTresor(), generarMonstre(), false, personatge);
				} else {
					nouMapa[i][j] = new SalaTeranyina(generarTresor(), generarMonstre(), false, personatge);
				}
			}
		}
		return nouMapa;
	}

	public void mostrarMasmorra(Personatge personatge) {
		int[] posPersonatge = personatge.getPosicio();

		for (int i = 0; i < this.x; i++) {
			for (int j = 0; j < this.y; j++) {
				if (posPersonatge[0] == i && posPersonatge[1] == j) {
					System.out.print(Colors.VERD + Estils.NEGRETA + "[ & ]" + Colors.RESET);
				} else if (i == posicioJefe[0] && j == posicioJefe[1]) {
					System.out.print(Colors.VERMELL + Estils.NEGRETA + "[ J ]" + Colors.RESET);
				} else if (mapa[i][j].estaExplorada()) {
					System.out.print(Colors.BLANC + "[ * ]" + Colors.RESET);
				} else {
					System.out.print(Colors.GRIS + "[ · ]" + Colors.RESET);
				}
			}
			System.out.println();
		}
	}

	public String[] generarLiniesMapa(Personatge personatge) {
		String[] linies = new String[this.x];
		int[] posicio = personatge.getPosicio();

		for (int i = 0; i < this.x; i++) {
			StringBuilder fila = new StringBuilder();
			for (int j = 0; j < this.y; j++) {
				if (posicio[0] == i && posicio[1] == j) {
					fila.append(Colors.VERD).append(Estils.NEGRETA).append("[ & ]").append(Colors.RESET);
				} else if (i == posicioJefe[0] && j == posicioJefe[1]) {
					// Marcar sala jefe (J si no explorada, * si ja explorada)
					if (mapa[i][j].estaExplorada()) {
						fila.append(Colors.VERMELL).append("[ * ]").append(Colors.RESET);
					} else {
						fila.append(Colors.VERMELL).append(Estils.NEGRETA).append("[ J ]").append(Colors.RESET);
					}
				} else if (mapa[i][j].estaExplorada()) {
					fila.append(Colors.BLAU).append("[ * ]").append(Colors.RESET);
				} else {
					fila.append(Colors.GRIS).append("[ · ]").append(Colors.RESET);
				}
			}
			linies[i] = fila.toString();
		}
		return linies;
	}

	public boolean estaForaMapa(int x, int y) {
		return x < 0 || x >= this.x
				|| y < 0 || y >= this.y;
	}

	/**
	 * Calcula la nova posicio segons la direcció.
	 * 
	 * @param posicio  Posició actual.
	 * @param direccio Direcció del moviment.
	 * @return Nova posició segons la direcció o null en cas que estigui fora del
	 *         mapa.
	 */
	public int[] calcularNovaPosicio(int[] posicio, Direccions direccio) {
		int[] novaPosicio = switch (direccio) {
			case NORD -> new int[] { posicio[0] - 1, posicio[1] };
			case SUD -> new int[] { posicio[0] + 1, posicio[1] };
			case EST -> new int[] { posicio[0], posicio[1] + 1 };
			case OEST -> new int[] { posicio[0], posicio[1] - 1 };
		};

		if (estaForaMapa(novaPosicio[0], novaPosicio[1])) {
			return null;
		} else {
			return novaPosicio;
		}
	}

	public void sumarSalesExplorades() {
		this.salesExplorades++;
	}

	public int getPercentatgeSalesExplorades() {
		return (int) ((this.salesExplorades * 100) / this.salesTotals);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Sala getSalaActual() {
		int[] pos = personatge.getPosicio();
		return mapa[pos[0]][pos[1]];
	}

	public int[] getPosicioJefe() {
		return posicioJefe;
	}

	public Personatge getPersonatge() {
		return personatge;
	}

	@Override
	public String toString() {
		return "Masmorra [" + x + "x" + y + "] " + salesExplorades + "/" + salesTotals;
	}
}