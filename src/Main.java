import core.GestorJoc;
import configuracio.ConfiguracioPartida;
import core.Masmorra;
import model.Personatge;
import utils.ConsoleUtils;
import utils.Missatges;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		ConsoleUtils.saltarPagina();

		boolean debug = Missatges.imprimirMissatgeBenvinguda(teclado);

		ConfiguracioPartida config = new ConfiguracioPartida(teclado, debug);
		Personatge personatge = config.crearPersonatge();
		Masmorra masmorra = config.crearMasmorra(personatge);

		new GestorJoc(teclado, personatge, masmorra).iniciar();
	}
}