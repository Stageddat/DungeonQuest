package utils;

import java.util.Scanner;

import core.Config;
import model.Atributs;
import model.Monstre;
import model.Personatge;

public class Missatges {
	public static boolean imprimirMissatgeBenvinguda(Scanner teclado) {
		System.out.println(Colors.CIAN);
		System.out.println(" ____  _   _ _   _  ____ _____ ___  _   _    ___  _   _ _____ ____ _____ ");
		System.out.println("|  _ \\| | | | \\ | |/ ___| ____/ _ \\| \\ | |  / _ \\| | | | ____/ ___|_   _|");
		System.out.println("| | | | | | |  \\| | |  _|  _|| | | |  \\| | | | | | | | |  _| \\___ \\ | |  ");
		System.out.println("| |_| | |_| | |\\  | |_| | |__| |_| | |\\  | | |_| | |_| | |___ ___) || |  ");
		System.out.println("|____/ \\___/|_| \\_|\\____|_____\\___/|_| \\_|  \\__\\_\\\\___/|_____|____/ |_|  ");
		System.out.println(Colors.VERD + "Fet per: Javi i Marc");
		System.out.println("DAM 1M - Programació");
		System.out.println("CURS 2025-2026");
		System.out.println(Colors.RESET);
		System.out.println("Cliqueu \"Enter\" per començar...");
		String entrada = teclado.nextLine();
		if (entrada.startsWith("d"))
			return true;
		return false;
	}

	public static void menuDificultats() {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Escollir dificultat ===" + Colors.RESET);

		System.out.println(Colors.VERD + "  1. Fàcil");
		System.out.println(Colors.GROC + "  2. Normal");
		System.out.println(Colors.VERMELL + "  3. Difícil" + Colors.RESET);

		System.out.println(Estils.PREGUNTA + "Quina dificultat vols? (1-3): ");
		System.out.print(Estils.RESPOSTA);
	}

	public static void menuRepartirPunts(Personatge personatge) {
		System.out.println(Estils.TITOL + "=== Repartiment de punts ===" + Colors.RESET);
		System.out.println("Punts disponibles: " + personatge.getPuntsDisponibles() + "\n");

		System.out.println(Estils.PREGUNTA + "Quin atribut vols millorar? (V/A/G/F | Q Per sortir)" + Colors.RESET);
		System.out.println(Colors.VIDA + "  V. Vida");
		System.out.println(Colors.ATAC + "  A. Atac");
		System.out.println(Colors.AGILITAT + "  G. Agilitat");
		System.out.println(Colors.FORSA + "  F. Força");
		System.out.print(Estils.RESPOSTA);
	}

	public static void mostrarBarra(String nom, String color, int valor, int maxValor) {
		int plens = (int) Math.round((double) valor / maxValor * Config.MOSTRAR_BARRA_ATRIBUTS_MIDA);
		int buits = Config.MOSTRAR_BARRA_ATRIBUTS_MIDA - plens;

		String barra = color + "█".repeat(plens) + Colors.RESET + "░".repeat(buits);
		System.out.println(
				color + nom + " | " + Colors.RESET + "[" + barra + "] " + color + valor + "/" + maxValor + Colors.RESET);
	}

	public static void mostrarBarra(String nom, String color, int valor, int maxValor, int midaBarra,
			boolean mostrarValors) {
		int plens = (int) Math.round((double) valor / maxValor * midaBarra);
		int buits = midaBarra - plens;

		String barra = color + "█".repeat(plens) + Colors.RESET + "░".repeat(buits);
		String sufix = mostrarValors ? color + valor + "/" + maxValor + Colors.RESET : "";

		System.out.println(color + nom + " | " + Colors.RESET + "[" + barra + "] " + sufix);
	}

	public static String generarBarra(String nom, String color, int valor, int maxValor) {
		int plens = (int) Math.round((double) valor / maxValor * Config.GENERAR_BARRA_ATRIBUTS_MIDA);
		int buits = Config.GENERAR_BARRA_ATRIBUTS_MIDA - plens;

		String barra = color + "█".repeat(plens) + Colors.RESET + "░".repeat(buits);
		return (color + nom + " | " + Colors.RESET + "[" + barra + "] " + color + valor + "/" + maxValor + Colors.RESET);
	}

	public static void mostrarAtributs(Personatge personatge) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Atributs ===" + Colors.RESET);
		mostrarBarra("Vida    ", Colors.VIDA, personatge.getVida(), Atributs.VIDA.getMaxim());
		mostrarBarra("Atac    ", Colors.ATAC, personatge.getAtac(), Atributs.ATAC.getMaxim());
		mostrarBarra("Agilitat", Colors.AGILITAT, personatge.getAgilitat(), Atributs.AGILITAT.getMaxim());
		mostrarBarra("Força   ", Colors.FORSA, personatge.getForsa(), Atributs.FORSA.getMaxim());
		System.out.println(Colors.RESET);
	}

	public static String generarBarra(String nom, String color, int valor, int maxValor, int midaBarra, boolean mostrarValors) {
	int plens = (int) Math.round((double) valor / maxValor * midaBarra);
	int buits = midaBarra - plens;

	String barra = color + "█".repeat(plens) + Colors.RESET + "░".repeat(buits);
	String sufix = mostrarValors ? color + valor + "/" + maxValor + Colors.RESET : "";

	return color + nom + " | " + Colors.RESET + "[" + barra + "] " + sufix;
	}

	public static String[] generarLiniesStats(Personatge personatge) {
		return new String[] {
				Colors.CIAN + "Heroi: " + Estils.NEGRETA + Colors.BLANC + personatge.getNom() + Colors.RESET + Colors.CIAN + " | Nivell: " + Estils.NEGRETA + Colors.BLANC + personatge.getLevel() + Colors.RESET,
				"─".repeat(50),
				generarBarra("Vida     ", Colors.VIDA, personatge.getVida(), Atributs.VIDA.getMaxim()),
				generarBarra("Atac     ", Colors.ATAC, personatge.getAtac(), Atributs.ATAC.getMaxim()),
				generarBarra("Agilitat ", Colors.AGILITAT, personatge.getAgilitat(), Atributs.AGILITAT.getMaxim()),
				generarBarra("Força    ", Colors.FORSA, personatge.getForsa(), Atributs.FORSA.getMaxim()),
				"─".repeat(50),
				"Punts:  " + Colors.GROC + personatge.getPuntsDisponibles() + Colors.RESET 
				+ " | " + generarBarra("Experiencia ", Colors.EXPERIENCIA, personatge.getExperiencia(), personatge.getLevelExperiencia(), 15, true),
		};
	}

	public static void mostrarMenuCombat(Personatge personatge, Monstre monstre) {
		ConsoleUtils.saltarPagina(Estils.TITOL + "=== Combat ===" + Colors.RESET);
 
		System.out.println(Colors.VERMELL + Estils.NEGRETA + monstre.getNom() + Colors.RESET);
		mostrarBarra("  Vida enemic: ", Colors.VERMELL, monstre.getVida(), Math.max(monstre.getVida(), 20));
		System.out.println();
		mostrarBarra("  Vida pròpia: ", Colors.VERD, personatge.getVida(), Atributs.VIDA.getMaxim());
		System.out.println();
 
		System.out.println(Estils.PREGUNTA + "Opcions de combat:" + Colors.RESET);
		System.out.println(Colors.TARONJA + "  A. Atacar");
		System.out.println(Colors.VERD + "  F. Fugir" + Colors.RESET);
		System.out.print(Estils.RESPOSTA);
	}

	public static void mostrarMenuCombatJefe(Personatge personatge, Monstre jefe) {
		System.out.println(Estils.TITOL + "=== COMBAT FINAL ===" + Colors.RESET);
 
		System.out.println(Colors.VERMELL + Estils.NEGRETA + jefe.getNom() + Colors.RESET);
		mostrarBarra("  Vida enemic: ", Colors.VERMELL, jefe.getVida(), Math.max(jefe.getVida(), 50));
		System.out.println();
 		mostrarBarra("  Vida pròpia: ", Colors.VERD, personatge.getVida(), Atributs.VIDA.getMaxim());
		System.out.println();
 
		System.out.println(Estils.PREGUNTA + "Opcions:" + Colors.RESET);
		System.out.println(Colors.TARONJA + "  A. Atacar");
		System.out.println(Colors.GRIS + "  F. Intentar fugir (impossiblee!)" + Colors.RESET);
		System.out.print(Estils.RESPOSTA);
	}

}
