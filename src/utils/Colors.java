package utils;

public final class Colors {
	// evitar que pueda instanciarse
	private Colors() {
	}

	public static final String RESET = "\u001B[0m";

	// colores text
	public static final String NEGRE = "\u001B[38;5;235m";
	public static final String VERMELL = "\u001B[38;5;196m"; // Rojo vibrante
	public static final String VERD = "\u001B[38;5;82m"; // Verde neón
	public static final String GROC = "\u001B[38;5;226m"; // Amarillo puro
	public static final String TARONJA = "\u001B[38;5;208m"; // Naranja eléctrico
	public static final String BLAU = "\u001B[38;5;33m"; // Azul Azure
	public static final String MAGENTA = "\u001B[38;5;201m"; // Rosa/Fucsia
	public static final String CIAN = "\u001B[38;5;51m"; // Cian brillante
	public static final String BLANC = "\u001B[38;5;255m"; // Blanco puro
	public static final String GRIS = "\u001B[90m"; // Blanco puro

	// color stats
	public static final String VIDA = VERMELL;
	public static final String ATAC = TARONJA;
	public static final String AGILITAT = MAGENTA;
	public static final String FORSA = GROC;
	public static final String MANA = BLAU;
	public static final String EXPERIENCIA = CIAN;


	// colores fondo
	public static final String FONS_NEGRE = "\u001B[40m";
	public static final String FONS_VERMELL = "\u001B[48;5;88m"; // Granate
	public static final String FONS_VERD = "\u001B[48;5;22m"; // Verde bosque
	public static final String FONS_GROC = "\u001B[48;5;136m"; // Oro viejo
	public static final String FONS_BLAU = "\u001B[48;5;18m"; // Azul marino
	public static final String FONS_MAGENTA = "\u001B[48;5;89m"; // Púrpura oscuro
	public static final String FONS_CIAN = "\u001B[48;5;24m"; // Petróleo
	public static final String FONS_BLANC = "\u001B[47m";
}