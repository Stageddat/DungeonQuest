package utils;

public class Estils {
	// secciones
	public static final String TITOL = "\u001B[48;5;236m" + Colors.CIAN + Estils.NEGRETA; // fondo gris + cian + negrita
	public static final String PREGUNTA = Colors.CIAN + "❯ " + Colors.RESET + Estils.NEGRETA;
	public static final String RESPOSTA = Colors.BLANC + "┃ " + Colors.RESET;

	// estilos
	public static final String NEGRETA = "\u001B[1m";
	public static final String SUBRATLLAT = "\u001B[4m";
}
