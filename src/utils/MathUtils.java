package utils;

public class MathUtils {
	private MathUtils() {
		// No permetre que es crei un objecte de la classe
	}

	/**
	 * Ajustar que el valor estigui entre el mínim i el màxim.
	 * Si el valor és inferior al mínim, retorna el mínim.
	 * Si és superior al màxim, retorna el màxim.
	 *
	 * @param min   El valor límit inferior.
	 * @param max   El valor límit superior.
	 * @param valor El valor a ajustar.
	 * @return El valor ajustat dins de l'interval mínim i màxim.
	 */
	public static int ajustarRang(int min, int max, int valor) {
		return Math.min(Math.max(min, valor), max);
	}

	/**
	 * Genera un número aleatori sencer entre el valor mínim i el màxim.
	 * 
	 * @param min El valor límit inferior.
	 * @param max El valor límit superior.
	 * @return El número aleatori generat.
	 */
	public static int generarNumeroAleatori(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}	
}
